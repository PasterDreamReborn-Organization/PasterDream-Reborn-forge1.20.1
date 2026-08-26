package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * 萦风合金锄 —— 被动「疾风过境」 + 战技「春风化雨」。
 * <p>
 * 右键催熟面前 3×3 作物（每格消耗 1 耐久，冷却 3 秒）；
 * 潜行左键：作物按以打击点为中心 5×5 范围收获，其它方块沿击打方向连锁 3×3×3 同类方块；
 * 挖掘时掉落物被风卷到玩家身边。
 */
public class FluffyWindAlloyHoeItem extends HoeItem implements FluffyWindAlloyTool {

    private static final int RIPEN_COOLDOWN_TICKS = 60; // 冷却 3s

    public FluffyWindAlloyHoeItem(Tier tier, Properties properties) {
        super(tier, -3, -1.5f, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player == null) return super.useOn(context);

        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (!FluffyWindAlloyToolHelper.isCrop(state)) {
            return super.useOn(context); // 普通锄地
        }

        ItemStack stack = context.getItemInHand();
        if (!level.isClientSide && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            int ripened = FluffyWindAlloyToolHelper.ripenCrops(level, pos);
            if (ripened > 0) {
                stack.hurtAndBreak(ripened, player, p -> p.broadcastBreakEvent(context.getHand()));
                player.getCooldowns().addCooldown(stack.getItem(), RIPEN_COOLDOWN_TICKS);
                level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player && player.isShiftKeyDown()) {
            int extra;
            if (FluffyWindAlloyToolHelper.isCrop(state)) {
                // 作物是瞬破方块（破坏速度 0），单独走 5×5 范围收获
                extra = FluffyWindAlloyToolHelper.breakCropsArea(level, pos, player, stack);
            } else if (state.getDestroySpeed(level, pos) != 0.0F) {
                extra = FluffyWindAlloyToolHelper.breakSameTypeFromHit(level, pos, state, player, stack, entity,
                        s -> FluffyWindAlloyToolHelper.isCrop(s) || stack.isCorrectToolForDrops(s));
            } else {
                extra = 0;
            }
            if (extra > 0) {
                stack.hurtAndBreak(extra, player, p -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
        return super.mineBlock(stack, level, state, pos, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_tool.passive_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_tool.passive1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_tool.passive2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_hoe.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_hoe.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_hoe.desc2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_hoe.desc3"));
    }
}