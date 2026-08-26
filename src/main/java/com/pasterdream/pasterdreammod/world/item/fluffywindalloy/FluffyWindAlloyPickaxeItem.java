package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * 萦风合金镐 —— 被动「疾风过境」 + 战技「风蚀共振」。
 * <p>
 * 潜行挖掘时，沿击打面方向共振破坏 3×3×3 同类方块（染梦合金锤式判定），
 * 掉落物被风卷到玩家身边。
 */
public class FluffyWindAlloyPickaxeItem extends PickaxeItem implements FluffyWindAlloyTool {

    public FluffyWindAlloyPickaxeItem(Tier tier, Properties properties) {
        super(tier, 1, -2.3f, properties);
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
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_tool.passive_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_tool.passive1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_tool.passive2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_pickaxe.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_pickaxe.desc1"));
    }
}