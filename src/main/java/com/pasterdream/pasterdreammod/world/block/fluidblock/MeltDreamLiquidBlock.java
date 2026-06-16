package com.pasterdream.pasterdreammod.world.block.fluidblock;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class MeltDreamLiquidBlock extends LiquidBlock
{
    public MeltDreamLiquidBlock()
    {
        super(() -> (FlowingFluid) ModFluids.FLOWING_MELT_DREAM_LIQUID.get(), Properties.of().mapColor(MapColor.FIRE).strength(100.0F).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).noCollission().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
    }

    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving)
    {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 5);
    }

    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random)
    {
        super.tick(blockstate, world, pos, random);
        //粒子效果    //MeltdreamLiquidPr0Procedure.execute(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
        world.scheduleTick(pos, this, 5);
    }
}
