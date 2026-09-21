package com.pasterdream.pasterdreammod.world.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.EnumSet;

/**
 * 让暗影生物主动远离点燃的营火。
 *
 * <p>性能设计：
 * <ul>
 *   <li>方块扫描受 {@link #SCAN_INTERVAL} 节流，扫描间隔内零 block 查询；</li>
 *   <li>只扫描实体周围受限立方体，且用 {@code hasChunkAt} 跳过未加载区块，避免强制加载；</li>
 *   <li>逃离直接写 {@code MoveControl}（直线位移），不触发 A* 寻路。</li>
 * </ul>
 */
public class AvoidLitCampfireGoal extends Goal {

    /** 两次方块扫描之间的最小 tick 间隔 */
    public static final int SCAN_INTERVAL = 20;
    /** 水平侦测半径（方块） */
    public static final int HORIZONTAL_RANGE = 12;
    /** 垂直侦测半径（方块） */
    public static final int VERTICAL_RANGE = 6;
    /** 已开始逃离后维持逃离的水平半径（大于侦测半径，避免刚出范围就回头反击） */
    public static final int RELEASE_RANGE = 20;
    /** 逃离目标点相对实体前进的距离 */
    private static final double FLEE_STEP = 12.0;

    private final PathfinderMob mob;
    private final double speedModifier;

    @Nullable
    private BlockPos campfirePos;
    private int scanCooldown;

    public AvoidLitCampfireGoal(PathfinderMob mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob.level().isClientSide()) {
            return false;
        }
        if (--this.scanCooldown > 0) {
            return false;
        }
        this.scanCooldown = SCAN_INTERVAL;
        this.campfirePos = findNearestLitCampfire(HORIZONTAL_RANGE, VERTICAL_RANGE);
        return this.campfirePos != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.campfirePos != null;
    }

    @Override
    public void start() {
        steerAway();
    }

    @Override
    public void tick() {
        if (--this.scanCooldown <= 0) {
            this.scanCooldown = SCAN_INTERVAL;
            this.campfirePos = findNearestLitCampfire(RELEASE_RANGE, VERTICAL_RANGE + 2);
        }
        if (this.campfirePos != null) {
            steerAway();
        }
    }

    @Override
    public void stop() {
        this.campfirePos = null;
        this.scanCooldown = SCAN_INTERVAL;
    }

    private void steerAway() {
        double centerX = this.campfirePos.getX() + 0.5;
        double centerZ = this.campfirePos.getZ() + 0.5;
        double dx = this.mob.getX() - centerX;
        double dz = this.mob.getZ() - centerZ;
        double len = Math.sqrt(dx * dx + dz * dz);
        if (len < 1.0E-4) {
            float angle = this.mob.getRandom().nextFloat() * ((float) Math.PI * 2.0F);
            dx = Mth.cos(angle);
            dz = Mth.sin(angle);
        } else {
            dx /= len;
            dz /= len;
        }
        this.mob.getMoveControl().setWantedPosition(
                this.mob.getX() + dx * FLEE_STEP,
                this.mob.getY(),
                this.mob.getZ() + dz * FLEE_STEP,
                this.speedModifier);
    }

    @Nullable
    private BlockPos findNearestLitCampfire(int horizontalRange, int verticalRange) {
        Level level = this.mob.level();
        BlockPos origin = this.mob.blockPosition();
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        double rangeSqr = (double) horizontalRange * horizontalRange;
        double best = Double.MAX_VALUE;
        BlockPos bestPos = null;
        for (int dy = -verticalRange; dy <= verticalRange; dy++) {
            for (int dz = -horizontalRange; dz <= horizontalRange; dz++) {
                for (int dx = -horizontalRange; dx <= horizontalRange; dx++) {
                    if ((double) (dx * dx + dz * dz) > rangeSqr) {
                        continue;
                    }
                    cursor.set(origin.getX() + dx, origin.getY() + dy, origin.getZ() + dz);
                    if (!level.hasChunkAt(cursor)) {
                        continue;
                    }
                    BlockState state = level.getBlockState(cursor);
                    if (state.is(BlockTags.CAMPFIRES)
                            && state.hasProperty(CampfireBlock.LIT)
                            && state.getValue(CampfireBlock.LIT)) {
                        double dist = origin.distSqr(cursor);
                        if (dist < best) {
                            best = dist;
                            bestPos = cursor.immutable();
                        }
                    }
                }
            }
        }
        return bestPos;
    }
}
