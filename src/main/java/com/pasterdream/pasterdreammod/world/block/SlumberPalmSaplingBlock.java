package com.pasterdream.pasterdreammod.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 眠椰树苗：除原版树苗允许的泥土/耕地外，也允许种在沙子标签方块上（椰子树风格的沙滩生长）。
 */
public class SlumberPalmSaplingBlock extends SaplingBlock {

    public SlumberPalmSaplingBlock(AbstractTreeGrower treeGrower, Properties properties) {
        super(treeGrower, properties);
    }

    @Override
    public boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return super.mayPlaceOn(state, level, pos) || state.is(BlockTags.SAND);
    }
}
