package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.network.skill.TerraBladeSwingPacket;
import com.pasterdream.pasterdreammod.world.entity.TerraswordWaveEntity;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.List;

public class TerraBladeItem extends SwordItem {

    private static final double ENERGY_COST = 0.1;
    private static final double ENERGY_COST_WITH_CHARM = 0.05;

    public TerraBladeItem(Tier tier, int damage, float speed) {
        super(tier, damage, speed, new Properties().fireResistant().rarity(ModRarities.LEGENDARY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
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
        if (attacker instanceof Player player) {
            tryFireSwordWave(player, stack);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    /**
     * Called from hurtEnemy (server-side entity hit) and TerraBladeSwingPacket (client-to-server air swing).
     */
    public static void tryFireSwordWave(Player player, ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.getBoolean("skill_active")) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        Level level = player.level();
        long lastWaveTick = tag.getLong("last_wave_tick");
        if (level.getGameTime() == lastWaveTick) return;
        tag.putLong("last_wave_tick", level.getGameTime());

        boolean hasCharm = CuriosApi.getCuriosInventory(serverPlayer)
                .map(inv -> inv.findFirstCurio(ModItems.TERRA_FLOATING_ISLAND.get()).isPresent())
                .orElse(false);

        double cost = hasCharm ? ENERGY_COST_WITH_CHARM : ENERGY_COST;
        double currentEnergy = MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(serverPlayer);

        if (player.isCreative() || currentEnergy >= cost) {
            if (!player.isCreative()) {
                MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(serverPlayer, -cost);
            }
            executeSkillWave(level, player, stack, hasCharm);
        } else {
            tag.putBoolean("skill_active", false);
            player.displayClientMessage(Component.translatable("tooltip.pasterdream.terra_blade.no_energy"), true);
        }
    }

    private static void executeSkillWave(Level level, Player player, ItemStack stack, boolean hasCharm) {
        Vec3 look = player.getLookAngle();
        double x = player.getX() + look.x;
        double y = player.getY() + 1.5;
        double z = player.getZ() + look.z;

        TerraswordWaveEntity wave = ModEntities.TERRASWORD_WAVE.get().spawn(
                (ServerLevel) level,
                BlockPos.containing(x, y, z),
                MobSpawnType.MOB_SUMMONED);
        if (wave != null) {
            wave.setOwner(player);
            wave.setYRot(player.getYRot());
            wave.setXRot(player.getXRot());
            wave.setDeltaMovement(look.x * 2, look.y * 2, look.z * 2);

            double atk = player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                    + stack.getEnchantmentLevel(Enchantments.SHARPNESS) * 0.5;
            if (hasCharm) {
                atk *= 1.3;
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
                    ModSounds.SWORD_WAVE.get(), SoundSource.PLAYERS, 0.8f, 1.0f);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(ModRarities.qualityTooltip(ModRarities.LEGENDARY));
        tooltip.add(Component.translatable("tooltip.pasterdream.terra_blade.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.terra_blade.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.terra_blade.desc2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.terra_blade.desc3"));
        tooltip.add(Component.translatable("tooltip.pasterdream.terra_blade.desc4"));
        tooltip.add(Component.translatable("tooltip.pasterdream.terra_blade.desc5"));
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
