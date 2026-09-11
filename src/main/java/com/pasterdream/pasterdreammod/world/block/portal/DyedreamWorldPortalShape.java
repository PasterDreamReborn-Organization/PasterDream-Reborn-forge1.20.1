package com.pasterdream.pasterdreammod.world.block.portal;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * 染梦世界传送门框架识别。移植自原版 {@link net.minecraft.world.level.portal.PortalShape}，
 * 仅将框架判定改为染梦世界跃迁石、传送方块改为染梦世界传送门。
 * 内空最小 2×3、最大 21×21（与原版下界一致）。
 */
public class DyedreamWorldPortalShape {
    private static final int MIN_WIDTH = 2;
    public static final int MAX_WIDTH = 21;
    private static final int MIN_HEIGHT = 3;
    public static final int MAX_HEIGHT = 21;

    private static final BlockBehaviour.StatePredicate FRAME = (state, level, pos) ->
            state.is(ModBlocks.DYEDREAM_WORLD_LEAPSTONE.get());

    private final LevelAccessor level;
    private final Direction.Axis axis;
    private final Direction rightDir;
    private int numPortalBlocks;
    @Nullable
    private BlockPos bottomLeft;
    private int height;
    private final int width;

    public static Optional<DyedreamWorldPortalShape> findEmptyPortalShape(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
        return findPortalShape(level, pos, shape -> shape.isValid() && shape.numPortalBlocks == 0, axis);
    }

    public static Optional<DyedreamWorldPortalShape> findPortalShape(LevelAccessor level, BlockPos pos,
                                                                     Predicate<DyedreamWorldPortalShape> predicate,
                                                                     Direction.Axis axis) {
        Optional<DyedreamWorldPortalShape> optional = Optional.of(new DyedreamWorldPortalShape(level, pos, axis)).filter(predicate);
        if (optional.isPresent()) {
            return optional;
        } else {
            Direction.Axis otherAxis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
            return Optional.of(new DyedreamWorldPortalShape(level, pos, otherAxis)).filter(predicate);
        }
    }

    public DyedreamWorldPortalShape(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
        this.level = level;
        this.axis = axis;
        this.rightDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        this.bottomLeft = this.calculateBottomLeft(pos);
        if (this.bottomLeft == null) {
            this.bottomLeft = pos;
            this.width = 1;
            this.height = 1;
        } else {
            this.width = this.calculateWidth();
            if (this.width > 0) {
                this.height = this.calculateHeight();
            }
        }
    }

    @Nullable
    private BlockPos calculateBottomLeft(BlockPos pos) {
        int minY = Math.max(this.level.getMinBuildHeight(), pos.getY() - MAX_HEIGHT);
        while (pos.getY() > minY && isEmpty(this.level.getBlockState(pos.below()))) {
            pos = pos.below();
        }
        Direction opposite = this.rightDir.getOpposite();
        int distance = this.getDistanceUntilEdgeAboveFrame(pos, opposite) - 1;
        return distance < 0 ? null : pos.relative(opposite, distance);
    }

    private int calculateWidth() {
        int width = this.getDistanceUntilEdgeAboveFrame(this.bottomLeft, this.rightDir);
        return width >= MIN_WIDTH && width <= MAX_WIDTH ? width : 0;
    }

    private int getDistanceUntilEdgeAboveFrame(BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int i = 0; i <= MAX_WIDTH; i++) {
            mutable.set(pos).move(direction, i);
            BlockState state = this.level.getBlockState(mutable);
            if (!isEmpty(state)) {
                if (FRAME.test(state, this.level, mutable)) {
                    return i;
                }
                break;
            }
            BlockState below = this.level.getBlockState(mutable.move(Direction.DOWN));
            if (!FRAME.test(below, this.level, mutable)) {
                break;
            }
        }
        return 0;
    }

    private int calculateHeight() {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int height = this.getDistanceUntilTop(mutable);
        return height >= MIN_HEIGHT && height <= MAX_HEIGHT && this.hasTopFrame(mutable, height) ? height : 0;
    }

    private boolean hasTopFrame(BlockPos.MutableBlockPos mutable, int height) {
        for (int i = 0; i < this.width; i++) {
            BlockPos pos = mutable.set(this.bottomLeft).move(Direction.UP, height).move(this.rightDir, i);
            if (!FRAME.test(this.level.getBlockState(pos), this.level, pos)) {
                return false;
            }
        }
        return true;
    }

    private int getDistanceUntilTop(BlockPos.MutableBlockPos mutable) {
        for (int i = 0; i < MAX_HEIGHT; i++) {
            mutable.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, -1);
            if (!FRAME.test(this.level.getBlockState(mutable), this.level, mutable)) {
                return i;
            }
            mutable.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, this.width);
            if (!FRAME.test(this.level.getBlockState(mutable), this.level, mutable)) {
                return i;
            }
            for (int j = 0; j < this.width; j++) {
                mutable.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, j);
                BlockState state = this.level.getBlockState(mutable);
                if (!isEmpty(state)) {
                    return i;
                }
                if (state.is(ModBlocks.DYEDREAM_WORLD_PORTAL.get())) {
                    this.numPortalBlocks++;
                }
            }
        }
        return MAX_HEIGHT;
    }

    private static boolean isEmpty(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.is(ModBlocks.DYEDREAM_WORLD_PORTAL.get());
    }

    public boolean isValid() {
        return this.bottomLeft != null
                && this.width >= MIN_WIDTH && this.width <= MAX_WIDTH
                && this.height >= MIN_HEIGHT && this.height <= MAX_HEIGHT;
    }

    public void createPortalBlocks() {
        BlockState portalState = ModBlocks.DYEDREAM_WORLD_PORTAL.get().defaultBlockState()
                .setValue(DyedreamWorldPortalBlock.AXIS, this.axis);
        BlockPos.betweenClosed(this.bottomLeft,
                        this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1))
                .forEach(pos -> this.level.setBlock(pos, portalState, 18));
    }

    public boolean isComplete() {
        return this.isValid() && this.numPortalBlocks == this.width * this.height;
    }
}
