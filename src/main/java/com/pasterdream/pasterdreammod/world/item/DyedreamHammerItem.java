package com.pasterdream.pasterdreammod.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
            BlockHitResult hitResult = (BlockHitResult) entity.pick(20.0, 1.0F, false);
            Direction hitDir = hitResult.getDirection();
            Direction.Axis axis = hitDir.getAxis();
            int stepX = -hitDir.getStepX();
            int stepY = -hitDir.getStepY();
            int stepZ = -hitDir.getStepZ();

            Set<BlockPos> broken = new HashSet<>();
            for (int depth = 0; depth < 3; depth++) {
                for (int a = -1; a <= 1; a++) {
                    for (int b = -1; b <= 1; b++) {
                        int dx, dy, dz;
                        if (axis == Direction.Axis.Y) {
                            dx = a;
                            dy = depth * stepY;
                            dz = b;
                        } else if (axis == Direction.Axis.X) {
                            dx = depth * stepX;
                            dy = a;
                            dz = b;
                        } else {
                            dx = a;
                            dy = b;
                            dz = depth * stepZ;
                        }
                        BlockPos target = pos.offset(dx, dy, dz);
                        if (target.equals(pos)) continue;
                        BlockState targetState = level.getBlockState(target);
                        if (targetState.is(state.getBlock()) && stack.isCorrectToolForDrops(targetState)) {
                            targetState.spawnAfterBreak((ServerLevel) level, target, stack, true);
                            List<ItemStack> drops = Block.getDrops(targetState, (ServerLevel) level, target,
                                    level.getBlockEntity(target), entity, stack);
                            for (ItemStack drop : drops) {
                                Block.popResourceFromFace(level, target, hitDir, drop);
                            }
                            level.setBlockAndUpdate(target, Blocks.AIR.defaultBlockState());
                            broken.add(target);
                        }
                    }
                }
            }
            stack.hurtAndBreak(broken.size() + 1, entity, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }
}
