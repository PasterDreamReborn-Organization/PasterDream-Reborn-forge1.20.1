package com.pasterdream.pasterdreammod.world.block.shadowblastfurnace;

import com.pasterdream.pasterdreammod.helper.multiblockproperties._3x3x3Part;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition._3x3x3_CalculatePartPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ShadowBlastFurnaceItem extends BlockItem
{
    public ShadowBlastFurnaceItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState blockState)
    {
        Level level = context.getLevel();
        BlockPos mainPosition = context.getClickedPos();

        for (_3x3x3Part eachPart : _3x3x3Part.values())
        {
            if (eachPart == _3x3x3Part.MAIN)
            {
                continue;
            }

            BlockPos addonPos = _3x3x3_CalculatePartPosition.getPartPos(mainPosition, blockState.getValue(ShadowBlastFurnaceBlock.FACING), eachPart);
            if (!level.getBlockState(addonPos).canBeReplaced())
            {
                return false;
            }
        }
        return super.placeBlock(context, blockState);
    }
}
