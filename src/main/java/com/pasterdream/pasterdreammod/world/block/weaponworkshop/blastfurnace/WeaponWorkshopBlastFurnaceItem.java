package com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace;

import com.pasterdream.pasterdreammod.helper.multiblockproperties._2x4x2Part;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition._2x4x2_CalculatePartPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WeaponWorkshopBlastFurnaceItem extends BlockItem
{
    public WeaponWorkshopBlastFurnaceItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState blockState)
    {
        Level level = context.getLevel();
        BlockPos mainPosition = context.getClickedPos();

        for (_2x4x2Part eachPart : _2x4x2Part.values())
        {
            if (eachPart == _2x4x2Part.MAIN)
            {
                continue;
            }

            BlockPos addonPos = _2x4x2_CalculatePartPosition.getPartPos(mainPosition, blockState.getValue(WeaponWorkshopBlastFurnaceBlock.FACING), eachPart);
            if (!level.getBlockState(addonPos).canBeReplaced())
            {
                return false;
            }
        }
        return super.placeBlock(context, blockState);
    }
}
