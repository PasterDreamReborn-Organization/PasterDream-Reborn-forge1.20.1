package com.pasterdream.pasterdreammod.world.block.goldenfoxsculpture;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GoldenFoxSculptureBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty ANIMATION = IntegerProperty.create("animation", 0, 1);
    private static final Map<UUID, Long> COOLDOWNS = new HashMap<>();
    private static final long COOLDOWN_TICKS = 600; // 30 seconds

    private static final VoxelShape SHAPE_NORTH = box(0, 0, 4, 16, 13, 12);
    private static final VoxelShape SHAPE_EAST = box(4, 0, 0, 12, 13, 16);

    public GoldenFoxSculptureBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .sound(SoundType.CALCITE)
                .strength(5f)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ANIMATION, 0));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GoldenFoxSculptureBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ANIMATION);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
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
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case EAST, WEST -> SHAPE_EAST;
            default -> SHAPE_NORTH;
        };
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
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        // Per-player cooldown check
        UUID playerId = player.getUUID();
        long currentTime = world.getGameTime();
        Long lastUsed = COOLDOWNS.get(playerId);
        if (lastUsed != null && currentTime - lastUsed < COOLDOWN_TICKS) {
            long remainingSeconds = (COOLDOWN_TICKS - (currentTime - lastUsed)) / 20;
            player.displayClientMessage(
                    Component.translatable("block.pasterdream.golden_fox_sculpture.cooldown", remainingSeconds), true);
            return InteractionResult.SUCCESS;
        }

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        // Check ritual conditions
        boolean hasFlowers = world.getBlockState(new BlockPos(x + 9, y, z + 9)).is(ModBlocks.MISTY_DREAMING_LOTUS.get())
                && world.getBlockState(new BlockPos(x - 9, y, z - 9)).is(ModBlocks.MISTY_DREAMING_LOTUS.get())
                && world.getBlockState(new BlockPos(x - 9, y, z + 9)).is(ModBlocks.MISTY_DREAMING_LOTUS.get())
                && world.getBlockState(new BlockPos(x + 9, y, z - 9)).is(ModBlocks.MISTY_DREAMING_LOTUS.get());

        boolean hasGlowBerries = player.getMainHandItem().is(Items.GLOW_BERRIES);
        long dayTime = world.dayTime() % 24000;
        boolean isMorningToNoon = dayTime >= 0 && dayTime <= 6000;

        if (hasFlowers && hasGlowBerries && isMorningToNoon) {
            // Consume item
            if (!player.getAbilities().instabuild)
                player.getMainHandItem().shrink(1);

            // Record cooldown
            COOLDOWNS.put(playerId, currentTime);

            // Destroy flowers
            world.destroyBlock(new BlockPos(x + 9, y, z + 9), false);
            world.destroyBlock(new BlockPos(x - 9, y, z - 9), false);
            world.destroyBlock(new BlockPos(x - 9, y, z + 9), false);
            world.destroyBlock(new BlockPos(x + 9, y, z - 9), false);

            // Destroy sculpture
            world.destroyBlock(pos, false);

            // Spawn Golden Fox facing player
            if (world instanceof ServerLevel serverLevel) {
                var fox = ModEntities.GOLDEN_FOX.get().spawn(serverLevel,
                        BlockPos.containing(x + 0.5, y, z + 0.5),
                        MobSpawnType.MOB_SUMMONED);
                if (fox != null) {
                    float yRot = player.getYRot() + 180f;
                    fox.setYRot(yRot);
                    fox.setYHeadRot(yRot);
                }
            }

            // Sound effects
            world.playSound(null, pos, SoundEvents.FOX_AMBIENT, SoundSource.MASTER, 1.2f, 1f);
            world.playSound(null, pos, ModSounds.DING.get(), SoundSource.PLAYERS, 1f, 1f);

            // Particles
            if (world instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                        x + 0.5, y + 0.2, z + 0.5, 12, 0.5, 0.4, 0.5, 0.1);
                serverLevel.sendParticles(ParticleTypes.END_ROD,
                        x + 0.5, y + 0.2, z + 0.5, 12, 0.5, 0.4, 0.5, 0.1);
            }
        } else {
            player.displayClientMessage(Component.translatable("block.pasterdream.golden_fox_sculpture.no_reaction"), true);
        }

        return InteractionResult.SUCCESS;
    }
}
