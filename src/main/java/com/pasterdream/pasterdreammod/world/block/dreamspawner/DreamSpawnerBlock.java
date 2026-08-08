package com.pasterdream.pasterdreammod.world.block.dreamspawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DreamSpawnerBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public DreamSpawnerBlock() {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.METAL)
                .strength(5f, 10f)
                .requiresCorrectToolForDrops()
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DreamSpawnerBlockEntity(pos, state);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity be = world.getBlockEntity(pos);
        return be != null && be.triggerEvent(eventID, eventParam);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 10);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        // 始终维持tick循环（即使没有玩家或没有物品）
        world.scheduleTick(pos, this, 10);

        if (!(world.getBlockEntity(pos) instanceof DreamSpawnerBlockEntity be))
            return;

        ItemStack item = be.getItem(0);
        if (item.isEmpty())
            return;

        // 仅检测生存/冒险模式的玩家
        AABB range = AABB.ofSize(pos.getCenter(), be.getPlayerRange(), be.getPlayerRange(), be.getPlayerRange());
        boolean hasValidPlayer = !world.getEntitiesOfClass(Player.class, range,
                p -> !p.isCreative() && !p.isSpectator()).isEmpty();

        if (!hasValidPlayer)
            return;

        if (!be.isFirstSpawn()) {
            be.setFirstSpawn(true);
            spawnEntity(world, item, pos);
            world.sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0);
            world.sendParticles(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0);
        } else if (be.getSpawnCount() < 1) {
            world.setBlock(pos, com.pasterdream.pasterdreammod.init.ModBlocks.FADED_DREAM_SPAWNER.get().defaultBlockState()
                    .setValue(FACING, state.getValue(FACING)), 3);
        } else {
            if (world.random.nextDouble() < 0.1) {
                be.setSpawnCount(be.getSpawnCount() - 1);
                spawnEntity(world, item, pos);
                world.sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0);
                world.sendParticles(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0);
            }
        }
    }

    private void spawnEntity(ServerLevel world, ItemStack item, BlockPos pos) {
        if (item.getItem() instanceof SpawnEggItem egg) {
            egg.getType(item.getOrCreateTag()).spawn(world, item, null, pos,
                    net.minecraft.world.entity.MobSpawnType.SPAWN_EGG, true, true);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        if (world.getBlockEntity(pos) instanceof DreamSpawnerBlockEntity be) {
            ItemStack held = player.getMainHandItem();
            if (held.getItem() instanceof SpawnEggItem) {
                be.setItem(0, held.copyWithCount(1));
                be.setSpawnCount(0);
                be.setFirstSpawn(false);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
