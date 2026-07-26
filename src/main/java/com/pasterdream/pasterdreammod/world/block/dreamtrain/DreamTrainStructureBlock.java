package com.pasterdream.pasterdreammod.world.block.dreamtrain;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DreamTrainStructureBlock extends Block {

    public DreamTrainStructureBlock() {
        super(BlockBehaviour.Properties.of()
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(SoundType.STONE)
                .strength(-1, 3600000)
                .noOcclusion()
                .randomTicks()
                .isRedstoneConductor((bs, br, bp) -> false));
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (level.isClientSide()) return;
        MinecraftServer server = level.getServer();
        if (server == null) return;
        ResourceKey<Level> dim = level.dimension();
        server.execute(() -> {
            ServerLevel serverLevel = server.getLevel(dim);
            if (serverLevel != null && serverLevel.getBlockState(pos).getBlock() == this) {
                DreamTrainStructureProcedure.placeTrain(serverLevel, pos);
            }
        });
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        DreamTrainStructureProcedure.placeTrain(level, pos);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }
}
