package com.pasterdream.pasterdreammod.world.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidType;

public abstract class PasterDreamBaseFluid extends Fluid
{
    @Override
    public Item getBucket()
    {
        return Items.AIR;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction)
    {
        return false;
    }

    @Override
    protected Vec3 getFlow(BlockGetter level, BlockPos pos, FluidState state)
    {
        return Vec3.ZERO;
    }

    @Override
    public int getTickDelay(LevelReader level)
    {
        return 0;
    }

    @Override
    protected float getExplosionResistance()
    {
        return 0;
    }

    @Override
    public float getHeight(FluidState state, BlockGetter level, BlockPos pos)
    {
        return 0;
    }

    @Override
    public float getOwnHeight(FluidState state)
    {
        return 0;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state)
    {
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean isSource(FluidState state)
    {
        return false;
    }

    @Override
    public int getAmount(FluidState state)
    {
        return 0;
    }

    @Override
    public VoxelShape getShape(FluidState state, BlockGetter level, BlockPos pos)
    {
        return Shapes.empty();
    }

    //子类必须实现此方法，返回对应的FluidType
    @Override
    public abstract FluidType getFluidType();
}
