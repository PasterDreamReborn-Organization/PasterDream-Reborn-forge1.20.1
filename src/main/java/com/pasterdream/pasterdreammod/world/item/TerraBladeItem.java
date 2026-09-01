package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.network.skill.TerraBladeSwingPacket;
import com.pasterdream.pasterdreammod.world.entity.TerraswordWaveEntity;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillLockHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.List;

public class TerraBladeItem extends SwordItem {

    private static final double ENERGY_COST = 0.5; // 技能能量消耗
    private static final double ENERGY_COST_WITH_CHARM = 0.4; // 装备泰拉浮岛饰品时的能量消耗
    private static final int SKILL_COOLDOWN_TICKS = 5; // 技能冷却时间(tick)
    private static final double WAVE_SPEED = 2.0; // 剑气波速度
    private static final double SHARPNESS_DAMAGE_BONUS = 0.5; // 锋利附魔每级伤害加成
    private static final double CHARM_DAMAGE_MULTIPLIER = 1.3; // 泰拉浮岛饰品伤害倍率
    private static final double WAVE_SPAWN_Y_OFFSET = 1.5; // 剑气波生成Y偏移
    private static final float SOUND_VOLUME = 0.8f; // 技能音效音量
    private static final float SOUND_PITCH = 1.0f; // 技能音效音高

    public TerraBladeItem(Tier tier, int damage, float speed) {
        super(tier, damage, speed, new Properties().fireResistant().rarity(ModRarities.LEGENDARY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        ItemStack stack = player.getItemInHand(hand);
        if (SkillLockHelper.isSkillLocked(player)) return InteractionResultHolder.fail(stack);
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
                CompoundTag tag = stack.getOrCreateTag();
                boolean active = !tag.getBoolean("skill_active");
                tag.putBoolean("skill_active", active);
                player.displayClientMessage(
                    Component.translatable(active
                        ? "tooltip.pasterdream.terra_blade.skill_on"
                        : "tooltip.pasterdream.terra_blade.skill_off"), true);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return super.hurtEnemy(stack, target, attacker);
    }

    /**
     * Called from TerraBladeSwingPacket (client-to-server) on every left-click while skill is active.
     */
    public static void tryFireSwordWave(Player player, ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.getBoolean("skill_active")) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (SkillLockHelper.isSkillLocked(player)) {
            tag.putBoolean("skill_active", false);
            return;
        }

        Level level = player.level();

        boolean hasCharm = CuriosApi.getCuriosInventory(serverPlayer)
                .map(inv -> inv.findFirstCurio(ModItems.TERRA_FLOATING_ISLAND.get()).isPresent())
                .orElse(false);

        double cost = hasCharm ? ENERGY_COST_WITH_CHARM : ENERGY_COST;
        double currentEnergy = MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(serverPlayer);

        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            return;
        }
        if (player.isCreative() || currentEnergy >= cost) {
            if (!player.isCreative()) {
                MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(serverPlayer, -cost);
            }
            executeSkillWave(level, player, stack, hasCharm);
            player.getCooldowns().addCooldown(stack.getItem(), SKILL_COOLDOWN_TICKS);
        } else {
            tag.putBoolean("skill_active", false);
            player.displayClientMessage(Component.translatable("tooltip.pasterdream.terra_blade.no_energy"), true);
        }
    }

    private static void executeSkillWave(Level level, Player player, ItemStack stack, boolean hasCharm) {
        Vec3 look = player.getLookAngle();
        double x = player.getX() + look.x;
        double y = player.getY() + WAVE_SPAWN_Y_OFFSET;
        double z = player.getZ() + look.z;

        TerraswordWaveEntity wave = ModEntities.TERRASWORD_WAVE.get().spawn(
                (ServerLevel) level,
                BlockPos.containing(x, y, z),
                MobSpawnType.MOB_SUMMONED);
        if (wave != null) {
            wave.setOwner(player);
            wave.setYRot(player.getYRot());
            wave.setXRot(player.getXRot());
            wave.setDeltaMovement(look.x * WAVE_SPEED, look.y * WAVE_SPEED, look.z * WAVE_SPEED);

            double atk = player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                    + stack.getEnchantmentLevel(Enchantments.SHARPNESS) * SHARPNESS_DAMAGE_BONUS;
            if (hasCharm) {
                atk *= CHARM_DAMAGE_MULTIPLIER;
            }
            atk *= SkillCooldownHelper.getSkillDamageMultiplier(player);

            CompoundTag waveData = wave.getPersistentData();
            waveData.putDouble("paster_atk", atk);
            waveData.putInt("sweeping_edge", stack.getEnchantmentLevel(Enchantments.SWEEPING_EDGE));
            waveData.putInt("smite", stack.getEnchantmentLevel(Enchantments.SMITE));
            waveData.putInt("bane_of_arthropods", stack.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS));
            waveData.putInt("fire_aspect", stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT));
            waveData.putInt("knockback", stack.getEnchantmentLevel(Enchantments.KNOCKBACK));
            waveData.putInt("looting", stack.getEnchantmentLevel(Enchantments.MOB_LOOTING));
            waveData.putBoolean("ignore_iframe", hasCharm);

            level.playSound(null, BlockPos.containing(player.getX(), player.getY(), player.getZ()),
                    ModSounds.SWORD_WAVE.get(), SoundSource.PLAYERS, SOUND_VOLUME, SOUND_PITCH);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.terra_blade.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.terra_blade.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.terra_blade.desc2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.terra_blade.desc3"));
        tooltip.add(Component.translatable("tooltip.pasterdream.terra_blade.desc4"));
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientHandler {
        @SubscribeEvent
        public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
            sendSwingPacket(event.getEntity());
        }

        @SubscribeEvent
        public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
            sendSwingPacket(event.getEntity());
        }

        @SubscribeEvent
        public static void onAttackEntity(AttackEntityEvent event) {
            sendSwingPacket(event.getEntity());
        }

        private static void sendSwingPacket(Player player) {
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof TerraBladeItem && stack.getOrCreateTag().getBoolean("skill_active")) {
                ModNetwork.CHANNEL.sendToServer(new TerraBladeSwingPacket());
            }
        }
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        var entity = new IndestructibleItemEntity(level, location.getX(), location.getY(), location.getZ(), stack);
        entity.setDefaultPickUpDelay();
        entity.setDeltaMovement(location.getDeltaMovement());
        return entity;
    }
}
