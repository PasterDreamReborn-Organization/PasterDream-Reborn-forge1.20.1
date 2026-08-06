package com.pasterdream.pasterdreammod.world.block.shadowbed;

import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class ShadowBedItem extends BlockItem
{
    public ShadowBedItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state)
    {
        Level level = context.getLevel();
        BlockPos mainPosition = context.getClickedPos();

        Function<BlockState, BlockPos> getAddonPos = (blockState) ->
        {
            Direction facing = blockState.getValue(ShadowBedBlock.FACING);
            return mainPosition.relative(facing.getOpposite());
        };
        BlockPos addonPos = getAddonPos.apply(state);

        if (!level.getBlockState(addonPos).canBeReplaced())
        {
            return false;
        }

        return super.placeBlock(context, state);
    }
}
