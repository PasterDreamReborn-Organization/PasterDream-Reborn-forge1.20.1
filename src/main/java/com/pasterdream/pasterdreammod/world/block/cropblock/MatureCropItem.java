package com.pasterdream.pasterdreammod.world.block.cropblock;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MatureCropItem extends BlockItem {
    private final Block cropBlock;

    public MatureCropItem(Properties properties, Block cropBlock) {
        super(cropBlock, properties);
        this.cropBlock = cropBlock;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockState clickedState = level.getBlockState(context.getClickedPos());
        if (clickedState.getBlock() instanceof FlowerPotBlock) {
            return InteractionResult.PASS;
        }
        return super.useOn(context);
    }

    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState state = this.cropBlock.getStateForPlacement(context);
        if (state != null) {
            state = state.setValue(PasterDreamCropBlock.AGE, 1);
        } else {
            state = this.cropBlock.defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1);
        }
        if (!this.canPlace(context, state)) {
            return null;
        }
        return state;
    }

    @Override
    public String getDescriptionId() {
        return BuiltInRegistries.ITEM.getKey(this).toLanguageKey("item");
    }
}
