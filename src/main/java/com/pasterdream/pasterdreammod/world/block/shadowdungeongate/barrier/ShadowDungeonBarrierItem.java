package com.pasterdream.pasterdreammod.world.block.shadowdungeongate.barrier;

import com.pasterdream.pasterdreammod.helper.multiblockproperties._3x3Part;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition._3x3_VerticalCalculatePartPosition;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ShadowDungeonBarrierItem extends BlockItem
{
    public ShadowDungeonBarrierItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState blockState)
    {
        Level level = context.getLevel();
        BlockPos mainPosition = context.getClickedPos().relative(Direction.UP, 1);

        for (_3x3Part eachPart : _3x3Part.values())
        {
            if (eachPart == _3x3Part.MAIN)
            {
                continue;
            }

            BlockPos addonPos = _3x3_VerticalCalculatePartPosition.getPartPos(mainPosition, blockState.getValue(WeaponWorkshopBlastFurnaceBlock.FACING), eachPart);
            if (!level.getBlockState(addonPos).canBeReplaced())
            {
                return false;
            }
        }
        return super.placeBlock(context, blockState);
    }
}
