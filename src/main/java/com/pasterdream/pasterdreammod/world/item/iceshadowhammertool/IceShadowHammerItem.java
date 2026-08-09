package com.pasterdream.pasterdreammod.world.item.iceshadowhammertool;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.entity.shakingcrystal.ShakingCrystalEntity;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class IceShadowHammerItem extends SwordItem {

    private static final double ENERGY_COST = 0.5; // 技能能量消耗
    private static final int COOLDOWN_TICKS = 80; // 技能冷却时间(tick)
    private static final double MAIN_CRYSTAL_Y_OFFSET = 1.0; // 主水晶Y偏移
    private static final double CRYSTAL_LATERAL_OFFSET = 2.0; // 侧面水晶偏移距离
    private static final double CRYSTAL_FRONT_OFFSET_1 = 2.0; // 前方水晶偏移距离(近)
    private static final double CRYSTAL_FRONT_OFFSET_2 = 4.0; // 前方水晶偏移距离(远)
    private static final int CRYSTAL_DELAY_1 = 10; // 水晶延迟生成时间(tick,近)
    private static final int CRYSTAL_DELAY_2 = 20; // 水晶延迟生成时间(tick,远)
    private static final double PLAYER_KNOCKBACK_Y = 0.4; // 玩家使用后Y方向击退
    private static final int SURFACE_SEARCH_RANGE = 5; // 地表搜索范围
    public IceShadowHammerItem(Tier tier, Properties properties) {
        super(tier, 3, -3.3f, properties.fireResistant().rarity(ModRarities.EPIC));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (player == null || context.getHand() == net.minecraft.world.InteractionHand.OFF_HAND)
            return InteractionResult.FAIL;
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        ServerPlayer sp = (ServerPlayer) player;
        ServerLevel serverLevel = (ServerLevel) level;

        if (!player.isCreative()) {
            double energy = MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(sp);
            if (energy < ENERGY_COST) {
                sp.displayClientMessage(Component.translatable("message.pasterdream.ice_shadow_hammer.no_energy"), true);
                return InteractionResult.FAIL;
            }
            MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(sp, -ENERGY_COST);
        }
        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        SkillCooldownHelper.applySharedCooldown(player, COOLDOWN_TICKS);

        double x = context.getClickedPos().getX();
        double y = context.getClickedPos().getY();
        double z = context.getClickedPos().getZ();

        float attackDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                * SkillCooldownHelper.getSkillDamageMultiplier(player);

        ItemStack hammer = context.getItemInHand();

        float yaw = player.getYRot() * Mth.DEG_TO_RAD;
        double facingX = -Mth.sin(yaw);
        double facingZ = Mth.cos(yaw);

        // Main crystal at clicked position
        spawnCrystal(level, player, attackDamage, hammer, x, y + MAIN_CRYSTAL_Y_OFFSET, z);

        // Extra crystals from curio
        boolean hasCurio = CuriosApi.getCuriosInventory(player)
                .map(handler -> handler.findFirstCurio(ModItems.ICE_SHADOW_CURIO.get()).isPresent())
                .orElse(false);

        if (hasCurio) {
            if (player.isShiftKeyDown()) {
                // SHIFT: left and right of main crystal, 10 tick delay
                double sideX = facingZ * CRYSTAL_LATERAL_OFFSET;
                double sideZ = -facingX * CRYSTAL_LATERAL_OFFSET;
                spawnCrystal(level, player, attackDamage, hammer, x + sideX, y, z + sideZ, CRYSTAL_DELAY_1);
                spawnCrystal(level, player, attackDamage, hammer, x - sideX, y, z - sideZ, CRYSTAL_DELAY_1);
            } else {
                // Normal: line along facing direction, 10 tick and 20 tick delays
                spawnCrystal(level, player, attackDamage, hammer, x + facingX * CRYSTAL_FRONT_OFFSET_1, y, z + facingZ * CRYSTAL_FRONT_OFFSET_1, CRYSTAL_DELAY_1);
                spawnCrystal(level, player, attackDamage, hammer, x + facingX * CRYSTAL_FRONT_OFFSET_2, y, z + facingZ * CRYSTAL_FRONT_OFFSET_2, CRYSTAL_DELAY_2);
            }
        }

        player.setDeltaMovement(new Vec3(0, PLAYER_KNOCKBACK_Y, 0));

        level.playSound(null, BlockPos.containing(x, y, z),
                SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 0.7f, 0.5f);
        level.playSound(null, BlockPos.containing(x, y, z),
                SoundEvents.STONE_BREAK, SoundSource.NEUTRAL, 0.7f, 0.8f);

        return InteractionResult.SUCCESS;
    }

    private void spawnCrystal(Level level, Player owner, float attackDamage, ItemStack hammer, double x, double y, double z) {
        spawnCrystal(level, owner, attackDamage, hammer, x, y, z, 0);
    }

    private void spawnCrystal(Level level, Player owner, float attackDamage, ItemStack hammer, double x, double y, double z, int delayTicks) {
        int surfaceY = findSurfaceY(level, (int) x, (int) y, (int) z);
        ShakingCrystalEntity crystal = ModEntities.SHAKING_CRYSTAL.get().create(level);
        if (crystal != null) {
            crystal.moveTo(BlockPos.containing(x, surfaceY + 1, z), level.random.nextFloat() * 360F, 0);
            crystal.setOwner(owner);
            crystal.setAttackDamage(attackDamage);
            crystal.setEnchantments(hammer);
            crystal.setSpawnDelay(delayTicks);
            level.addFreshEntity(crystal);
        }
    }

    private static int findSurfaceY(Level level, int x, int y, int z) {
        for (int dy = 0; dy <= SURFACE_SEARCH_RANGE; dy++) {
            BlockPos pos = new BlockPos(x, y - dy, z);
            if (isSolidSurface(level, pos) && level.getBlockState(pos.above()).isAir()) {
                return y - dy;
            }
        }
        for (int dy = 1; dy <= SURFACE_SEARCH_RANGE; dy++) {
            BlockPos pos = new BlockPos(x, y + dy, z);
            if (isSolidSurface(level, pos) && level.getBlockState(pos.above()).isAir()) {
                return y + dy;
            }
        }
        return y;
    }

    private static boolean isSolidSurface(Level level, BlockPos pos) {
        var state = level.getBlockState(pos);
        return !state.isAir() && !state.getCollisionShape(level, pos).isEmpty();
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(ModRarities.qualityTooltip(ModRarities.EPIC));
        tooltip.add(Component.translatable("tooltip.pasterdream.ice_shadow_hammer.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.ice_shadow_hammer.0"));
        tooltip.add(Component.translatable("tooltip.pasterdream.ice_shadow_hammer.1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.ice_shadow_hammer.2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.ice_shadow_hammer.3"));
        tooltip.add(Component.translatable("tooltip.pasterdream.ice_shadow_hammer.4"));
        tooltip.add(Component.translatable("tooltip.pasterdream.ice_shadow_hammer.5"));
        tooltip.add(Component.translatable("tooltip.pasterdream.ice_shadow_hammer.cost"));
    }
}
