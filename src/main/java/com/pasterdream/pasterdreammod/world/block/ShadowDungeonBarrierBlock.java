package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShadowDungeonBarrierBlock extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE_NORTH_SOUTH = box(0, 0, 4, 16, 16, 12);
    private static final VoxelShape SHAPE_EAST_WEST = box(4, 0, 0, 12, 16, 16);

    public ShadowDungeonBarrierBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(SoundType.STONE)
                .strength(-1, 3600000)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return facing == Direction.NORTH || facing == Direction.SOUTH ? SHAPE_NORTH_SOUTH : SHAPE_EAST_WEST;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return 0;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, world, pos, oldState, moving);
        Direction facing = state.getValue(FACING);
        BlockState shellState = ModBlocks.SHADOW_DUNGEON_BARRIER_SHELL.get().defaultBlockState().setValue(FACING, facing);
        boolean isNorthSouth = facing == Direction.NORTH || facing == Direction.SOUTH;
        int z = pos.getZ();
        int x = pos.getX();
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                if (d1 == 0 && d2 == 0) continue;
                BlockPos target;
                if (isNorthSouth) {
                    target = new BlockPos(x + d1, pos.getY() + d2, z);
                } else {
                    target = new BlockPos(x, pos.getY() + d2, z + d1);
                }
                if (world.getBlockState(target).canBeReplaced()) {
                    world.setBlock(target, shellState, 3);
                }
            }
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        boolean result = super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
        destroyBarrier(world, pos, state.getValue(FACING));
        return result;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        world.playSound(null, pos, ModSounds.SHADOW_DOOR.get(), SoundSource.BLOCKS, 1, 1);
        if (!world.isClientSide) {
            destroyBarrier(world, pos, state.getValue(FACING));
        }
        return InteractionResult.SUCCESS;
    }

    static void destroyBarrier(Level world, BlockPos center, Direction facing) {
        boolean isNorthSouth = facing == Direction.NORTH || facing == Direction.SOUTH;
        int z = center.getZ();
        int x = center.getX();
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                BlockPos target;
                if (isNorthSouth) {
                    target = new BlockPos(x + d1, center.getY() + d2, z);
                } else {
                    target = new BlockPos(x, center.getY() + d2, z + d1);
                }
                if (d1 == 0 && d2 == 0) {
                    if (world.getBlockState(target).getBlock() instanceof ShadowDungeonBarrierBlock) {
                        world.destroyBlock(target, false);
                    }
                } else if (world.getBlockState(target).getBlock() instanceof ShadowDungeonBarrierShellBlock) {
                    world.destroyBlock(target, false);
                }
            }
        }
    }
}
