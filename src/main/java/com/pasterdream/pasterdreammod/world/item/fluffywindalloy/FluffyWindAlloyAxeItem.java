package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * 萦风合金斧 —— 被动「疾风过境」 + 战技「落叶秋风」。
 * <p>
 * 潜行挖掘原木时，连锁破坏相连原木并吹落周围树叶，掉落物被风卷到玩家身边。
 * 近战攻击同样吃到疾风过境：伤害=(1+移动速度)×攻击力，并附带雷电伤害（见 {@link FluffyWindAlloyAttackHandler}）。
 */
public class FluffyWindAlloyAxeItem extends AxeItem implements FluffyWindAlloyTool {

    public FluffyWindAlloyAxeItem(Tier tier, Properties properties) {
        super(tier, 5.5f, -2.5f, properties);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player
                && player.isShiftKeyDown() && state.is(BlockTags.LOGS)) {
            int extra = FluffyWindAlloyToolHelper.breakLogChain(level, pos, state, player, stack);
            if (extra > 0) {
                stack.hurtAndBreak(extra, entity, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
        return super.mineBlock(stack, level, state, pos, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_axe.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_axe.desc1"));
    }
}
