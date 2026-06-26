package com.pasterdream.pasterdreammod.world.block.cropblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class MatureCropItem extends Item
{
    private final Block cropBlock;

    public MatureCropItem(Properties properties, Block cropBlock)
    {
        super(properties);
        this.cropBlock = cropBlock;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        ItemStack stack = context.getItemInHand();

        if (level.getBlockState(pos).canBeReplaced() && cropBlock.defaultBlockState().canSurvive(level, pos))
        {
            if (!level.isClientSide)
            {
                BlockState matureState = cropBlock.defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1);
                level.setBlock(pos, matureState, 3);
                if (!context.getPlayer().isCreative())
                {
                    stack.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }
}
