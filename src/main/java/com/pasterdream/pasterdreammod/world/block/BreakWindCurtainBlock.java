package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BreakWindCurtainBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    /** 玩家累计穿过破风幕帐次数（玩家持久化数据） */
    private static final String PASS_COUNT_KEY = "pasterdream:break_wind_curtain_passes";
    /** 玩家上一刻被判定为「位于幕帐内」的游戏刻，用于区分「刚进入」与「停留在其中」 */
    private static final String LAST_INSIDE_TICK_KEY = "pasterdream:break_wind_curtain_last_inside_tick";
    /** 玩家上一次被计入穿过的游戏刻，用于计数冷却 */
    private static final String LAST_PASS_TICK_KEY = "pasterdream:break_wind_curtain_last_pass_tick";
    /** 两次计数之间的最小间隔：1 秒（20 刻） */
    private static final long PASS_COUNT_COOLDOWN_TICKS = 20L;

    private static final VoxelShape SHAPE_NORTH_SOUTH = box(0, 0, 7, 16, 16, 9);
    private static final VoxelShape SHAPE_EAST_WEST = box(7, 0, 0, 9, 16, 16);
    private static final VoxelShape SHAPE_UP_DOWN = box(0, 7, 0, 16, 9, 16);

    public BreakWindCurtainBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_CYAN)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(SoundType.GLASS)
                .strength(1f)
                .lightLevel(s -> 10)
                .noCollission()
                .noOcclusion()
                .hasPostProcess((bs, br, bp) -> true)
                .emissiveRendering((bs, br, bp) -> true)
                .isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.getBlock() == this || super.skipRendering(state, adjacentBlockState, side);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case EAST, WEST -> SHAPE_EAST_WEST;
            case UP, DOWN -> SHAPE_UP_DOWN;
            default -> SHAPE_NORTH_SOUTH;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean waterlogged = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState()
                .setValue(FACING, context.getNearestLookingDirection().getOpposite())
                .setValue(WATERLOGGED, waterlogged);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);

        // entityInside 每刻会对实体碰撞箱相交的每个幕帐方块各调用一次：
        // 斜向穿过或多格重叠时同一刻会被多次调用，因此不能用方块位置判定「刚进入」。
        // 这里记录玩家上一刻是否已在幕帐内：只有「上一刻不在、这一刻在」才算真正进入，
        // 持续站在/悬停在幕帐中不会重复计数。
        long gameTime = level.getGameTime();
        ServerPlayer serverPlayer = entity instanceof ServerPlayer ? (ServerPlayer) entity : null;
        boolean justEntered = false;
        if (serverPlayer != null) {
            CompoundTag data = serverPlayer.getPersistentData();
            long lastInsideTick = data.contains(LAST_INSIDE_TICK_KEY)
                    ? data.getLong(LAST_INSIDE_TICK_KEY)
                    : Long.MIN_VALUE;
            // 上一刻（gameTime - 1）及更早都不在幕帐内，说明本刻是刚进入
            justEntered = lastInsideTick < gameTime - 1;
            data.putLong(LAST_INSIDE_TICK_KEY, gameTime);
        }

        if (!entity.onGround()) {
            Vec3 look = entity.getLookAngle();
            entity.setDeltaMovement(new Vec3(look.x * 5, look.y, look.z * 5));

            // 玩家滑翔穿过破风幕帐时触发进度：乘风破幕 / 百幕穿行
            // 必须处于鞘翅滑翔（isFallFlying）状态才计数，其余飞行/坠落方式不计
            if (serverPlayer != null && serverPlayer.isFallFlying()
                    && justEntered && !isPassCountCoolingDown(serverPlayer, gameTime)) {
                CompoundTag data = serverPlayer.getPersistentData();
                data.putLong(LAST_PASS_TICK_KEY, gameTime);
                int count = data.getInt(PASS_COUNT_KEY) + 1;
                data.putInt(PASS_COUNT_KEY, count);
                ModCriteriaTriggers.BREAK_WIND_CURTAIN.trigger(serverPlayer, count);
            }
        }
    }

    /** 计数冷却：同一玩家两次计数至少间隔 {@link #PASS_COUNT_COOLDOWN_TICKS} 刻，避免快速反复进出被连续计数。 */
    private static boolean isPassCountCoolingDown(ServerPlayer player, long gameTime) {
        CompoundTag data = player.getPersistentData();
        return data.contains(LAST_PASS_TICK_KEY)
                && gameTime - data.getLong(LAST_PASS_TICK_KEY) < PASS_COUNT_COOLDOWN_TICKS;
    }
}
