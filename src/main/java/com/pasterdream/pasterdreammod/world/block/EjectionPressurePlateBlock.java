package com.pasterdream.pasterdreammod.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.Vec3;

/**
 * 弹射压力板：实体踏入时向上弹射（仅感应 MOBS 产生红石信号，但弹射作用于任意实体）。
 */
public class EjectionPressurePlateBlock extends PressurePlateBlock {
    public EjectionPressurePlateBlock(Properties properties) {
        super(Sensitivity.MOBS, properties, BlockSetType.IRON);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);
        if (level.isClientSide()) {
            return;
        }
        Vec3 velocity = entity.getDeltaMovement();
        entity.setDeltaMovement(velocity.x, 0.8D, velocity.z);
        entity.hurtMarked = true;
        entity.fallDistance = 3.0F;
    }
}
