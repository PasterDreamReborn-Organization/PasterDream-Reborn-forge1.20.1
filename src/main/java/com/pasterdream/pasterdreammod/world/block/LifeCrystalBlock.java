package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LifeCrystalBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final UUID LIFE_CRYSTAL_MODIFIER_UUID = UUID.fromString("4619a21e-8140-4f6b-a384-5a3e83b65e3c");
    private static final Set<UUID> ABSORBING_PLAYERS = new HashSet<>();

    private static final VoxelShape SHAPE_NORTH = box(3, 2, 5, 13, 14, 11);
    private static final VoxelShape SHAPE_SOUTH = box(3, 2, 5, 13, 14, 11);
    private static final VoxelShape SHAPE_EAST = box(5, 2, 3, 11, 14, 13);
    private static final VoxelShape SHAPE_WEST = box(5, 2, 3, 11, 14, 13);

    public LifeCrystalBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_PINK)
                .sound(SoundType.GLASS)
                .strength(1f)
                .lightLevel(s -> 12)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LifeCrystalBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean waterlogged = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, waterlogged);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof LifeCrystalBlockEntity lcbe) {
                UUID uuid = lcbe.getAbsorbingPlayer();
                if (uuid != null) {
                    ABSORBING_PLAYERS.remove(uuid);
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH, SOUTH -> SHAPE_NORTH;
            case EAST, WEST -> SHAPE_EAST;
            default -> SHAPE_NORTH;
        };
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        for (int l = 0; l < 4; ++l) {
            double x0 = pos.getX() + random.nextFloat();
            double y0 = pos.getY() + random.nextFloat();
            double z0 = pos.getZ() + random.nextFloat();
            double dx = (random.nextFloat() - 0.5D) * 0.5D;
            double dy = (random.nextFloat() - 0.5D) * 0.5D;
            double dz = (random.nextFloat() - 0.5D) * 0.5D;
            world.addParticle((SimpleParticleType) ModParticleTypes.MELTDREAM_CRYSTAL_PARTICLE.get(), x0, y0, z0, dx, dy, dz);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide)
            return InteractionResult.SUCCESS;

        AttributeInstance instance = player.getAttribute(Attributes.MAX_HEALTH);
        if (instance != null && instance.hasModifier(new AttributeModifier(LIFE_CRYSTAL_MODIFIER_UUID, "life_crystal", 2, AttributeModifier.Operation.ADDITION))) {
            player.displayClientMessage(Component.translatable("message.pasterdream.life_crystal.already_used"), false);
            return InteractionResult.SUCCESS;
        }

        if (!ABSORBING_PLAYERS.add(player.getUUID())) {
            player.displayClientMessage(Component.translatable("message.pasterdream.life_crystal.already_absorbing"), false);
            return InteractionResult.SUCCESS;
        }

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof LifeCrystalBlockEntity lcbe) {
            lcbe.setAnimationState(1);
            lcbe.setAbsorbingPlayer(player.getUUID());
        }
        level.scheduleTick(pos, this, 40);
        level.playSound(null, pos, ModSounds.LIFE_CRYSTAL.get(), SoundSource.NEUTRAL, 0.6f, 1.0f);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!(level.getBlockState(pos).getBlock() instanceof LifeCrystalBlock)) {
            return;
        }
        BlockEntity be = level.getBlockEntity(pos);
        UUID playerUUID = null;
        if (be instanceof LifeCrystalBlockEntity lcbe) {
            playerUUID = lcbe.getAbsorbingPlayer();
        }

        // Heart particles
        level.sendParticles(ParticleTypes.HEART, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8, 0.3, 0.3, 0.3, 0.01);
        level.removeBlock(pos, false);

        if (playerUUID != null) {
            ABSORBING_PLAYERS.remove(playerUUID);
            Player player = level.getPlayerByUUID(playerUUID);
            if (player != null) {
                AttributeInstance instance = player.getAttribute(Attributes.MAX_HEALTH);
                AttributeModifier modifier = new AttributeModifier(LIFE_CRYSTAL_MODIFIER_UUID, "life_crystal", 2, AttributeModifier.Operation.ADDITION);
                if (instance != null && !instance.hasModifier(modifier)) {
                    instance.addPermanentModifier(modifier);
                }
                player.displayClientMessage(Component.translatable("message.pasterdream.life_crystal.absorbed"), false);
            }
        }
    }
}
