package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * 萦风合金锹 —— 被动「疾风过境」 + 战技「沙尘漩涡」。
 * <p>
 * 潜行挖掘时，沿击打方向连锁破坏 3×3×3 同类方块（染梦合金锤式判定），
 * 掉落物被风卷到玩家身边。
 */
public class FluffyWindAlloyShovelItem extends ShovelItem implements FluffyWindAlloyTool {

    public FluffyWindAlloyShovelItem(Tier tier, Properties properties) {
        super(tier, 1.5f, -2.5f, properties);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof net.minecraft.world.entity.player.Player player
                && player.isShiftKeyDown() && state.getDestroySpeed(level, pos) != 0.0F) {
            int extra = FluffyWindAlloyToolHelper.breakSameTypeFromHit(level, pos, state, player, stack, entity,
                    s -> stack.isCorrectToolForDrops(s));
            if (extra > 0) {
                stack.hurtAndBreak(extra, entity, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
        return super.mineBlock(stack, level, state, pos, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_shovel.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_shovel.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_tool.passive_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_tool.passive1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_tool.passive2"));
    }
}