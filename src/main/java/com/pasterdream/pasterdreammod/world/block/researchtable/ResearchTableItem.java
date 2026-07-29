package com.pasterdream.pasterdreammod.world.block.researchtable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class ResearchTableItem extends BlockItem
{
    public ResearchTableItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state)
    {
        Level level = context.getLevel();
        BlockPos mainPos = context.getClickedPos();

        ResearchTableBlock block = (ResearchTableBlock) getBlock();
        if (!(state.getBlock() instanceof ResearchTableBlock))
        {
            return super.placeBlock(context, state);
        }

        Function<BlockState, BlockPos> getAddonPos = (blockState) ->
        {
            Direction facing = blockState.getValue(ResearchTableBlock.FACING);
            return mainPos.relative(facing.getCounterClockWise());
        };
        BlockPos addonPos = getAddonPos.apply(state);

        if (!level.getBlockState(addonPos).canBeReplaced())
        {
            return false;
        }

        return super.placeBlock(context, state);
    }
}
