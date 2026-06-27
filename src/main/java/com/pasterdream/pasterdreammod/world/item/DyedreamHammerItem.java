package com.pasterdream.pasterdreammod.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DyedreamHammerItem extends PickaxeItem {

    public DyedreamHammerItem(Tier tier, Properties properties) {
        super(tier, 5, -3.3f, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_hammer"));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            Set<BlockPos> broken = new HashSet<>();
            for (int y = -1; y <= 1; y++)
                for (int x = -1; x <= 1; x++)
                    for (int z = -1; z <= 1; z++) {
                        BlockPos target = pos.offset(x, y, z);
                        if (target.equals(pos)) continue;
                        BlockState targetState = level.getBlockState(target);
                        if (targetState.is(state.getBlock()) && stack.isCorrectToolForDrops(targetState)) {
                            targetState.spawnAfterBreak((ServerLevel) level, target, stack, true);
                            List<ItemStack> drops = Block.getDrops(targetState, (ServerLevel) level, target,
                                    level.getBlockEntity(target), entity, stack);
                            for (ItemStack drop : drops) {
                                Block.popResourceFromFace(level, target,
                                        ((BlockHitResult) entity.pick(20.0, 1.0F, false)).getDirection(), drop);
                            }
                            level.setBlockAndUpdate(target, Blocks.AIR.defaultBlockState());
                            broken.add(target);
                        }
                    }
            stack.hurtAndBreak(broken.size() + 1, entity, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }
}
