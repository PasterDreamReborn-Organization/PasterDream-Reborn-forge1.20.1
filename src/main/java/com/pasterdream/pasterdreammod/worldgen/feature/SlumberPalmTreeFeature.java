package com.pasterdream.pasterdreammod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.world.block.SlumberPalmWallBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 眠椰树
 * <p>
 * 笔直细高的单格主干；主干顶端叉开 {@code branchCount} 条分支（1 = 单干 GT 同款造型），
 * 每条分支顶端带一个扁平的空心星形小树冠（由若干条沿圆周均布的星臂组成，臂越长末端下垂越多）。
 * </p>
 */
public class SlumberPalmTreeFeature extends Feature<SlumberPalmTreeConfiguration> {

    public SlumberPalmTreeFeature(Codec<SlumberPalmTreeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SlumberPalmTreeConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        SlumberPalmTreeConfiguration config = context.config();

        BlockState log = ModBlocks.SLUMBER_PALM_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.SLUMBER_PALM_LEAVES.get().defaultBlockState()
                .setValue(LeavesBlock.PERSISTENT, true);

        int trunkHeight = Math.max(1, config.trunkHeight().sample(random));
        int branchCount = Math.max(1, config.branchCount());
        // 按 forkChance 概率决定是否叉开成多根枝，否则单干
        if (random.nextFloat() >= config.forkChance()) {
            branchCount = 1;
        }

        // ===== 1. 随机弯曲主干路径 =====
        // 随机一个弯曲方向，整棵主干只朝该方向单调弯曲（底部竖直、越往上越弯）
        double leanAngle = random.nextDouble() * Math.PI * 2;
        double leanStrength = 1.0 + random.nextDouble() * 2.0;

        List<BlockPos> trunkCells = new ArrayList<>(trunkHeight);
        int ox = 0, oz = 0;
        for (int y = 0; y < trunkHeight; y++) {
            double t = (double) y / (trunkHeight - 1);
            // 单向弯曲：弯曲量随高度单调加强，方向始终为 leanAngle
            double bend = leanStrength * Math.pow(t, 1.2);
            int targetX = (int) Math.round(bend * Math.cos(leanAngle));
            int targetZ = (int) Math.round(bend * Math.sin(leanAngle));
            // 每步偏移限制在 ±1 以内，保证树干连续
            ox = Math.max(ox - 1, Math.min(ox + 1, targetX));
            oz = Math.max(oz - 1, Math.min(oz + 1, targetZ));
            trunkCells.add(origin.offset(ox, y, oz));
        }

        // 空间检查：整条弯曲树干路径需可放置
        for (BlockPos p : trunkCells) {
            if (!canReplace(level, p)) {
                return false;
            }
        }

        // ===== 2. 主干：放置弯曲单格树干 =====
        Set<BlockPos> trunk = new HashSet<>(trunkCells);
        for (BlockPos p : trunkCells) {
            setBlock(level, p, log);
        }

        BlockPos trunkTop = trunkCells.get(trunkCells.size() - 1);

        if (branchCount == 1) {
            // GT 单干：树冠直接放在主干顶端（紧贴顶部原木，不留空隙）
            placeCrown(level, random, trunk, leaves, config, trunkTop);
            placeCoconuts(level, random, List.of(trunkTop));
            return true;
        }

        // ===== 3. 顶部叉开多根枝：主干顶端分出 branchCount 条分支，每条带小星冠 =====
        BlockPos fork = trunkTop.above(1);
        trunk.add(fork);
        setBlock(level, fork, log);
        int branchLength = config.branchLength().sample(random);
        double branchSpread = config.branchSpread();
        double step = Math.PI * 2.0 / branchCount;
        List<BlockPos> crownAnchors = new ArrayList<>(branchCount);
        for (int i = 0; i < branchCount; i++) {
            double angle = i * step;
            double fx = Math.cos(angle);
            double fz = Math.sin(angle);
            BlockPos tip = fork;
            for (int t = 1; t <= branchLength; t++) {
                // 分支沿水平方向向外倾斜 branchSpread 格/格，并向上 1 格/格
                BlockPos cell = fork.offset(
                        (int) Math.round(fx * branchSpread * t),
                        t,
                        (int) Math.round(fz * branchSpread * t));
                if (trunk.add(cell)) {
                    setBlock(level, cell, log);
                }
                tip = cell;
            }
            placeCrown(level, random, trunk, leaves, config, tip);
            crownAnchors.add(tip);
        }
        placeCoconuts(level, random, crownAnchors);
        return true;
    }

    /** 在某条分支/主干的顶端放一个扁平空心星冠 */
    private void placeCrown(WorldGenLevel level, RandomSource random, Set<BlockPos> trunk,
                            BlockState leaves, SlumberPalmTreeConfiguration config, BlockPos base) {
        BlockPos crown = base.above(1);

        // 冠心顶叶
        placeLeaf(level, crown, leaves);

        // 可选：中心额外叶簇
        if (config.centerFill()) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = 0; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (Math.abs(dx) + Math.abs(dy) + Math.abs(dz) > 2) continue;
                        BlockPos p = crown.offset(dx, dy, dz);
                        if (!trunk.contains(p)) placeLeaf(level, p, leaves);
                    }
                }
            }
        }

        // 各星臂（羽叶）：沿圆周均布。每个格子按最接近的网格点光栅化，
        // 下垂量以该格到冠心的欧氏距离（四舍五入）为基准——这样 8 臂时天然重现 GT
        // 的“主轴 4 格 + 对角 3 格 + 臂末下垂”空心星冠。
        int armCount = config.armCount();
        int maxDroop = config.maxDroop();
        double step = Math.PI * 2.0 / armCount;
        for (int i = 0; i < armCount; i++) {
            double angle = i * step;
            double fx = Math.cos(angle);
            double fz = Math.sin(angle);
            int length = config.armLength().sample(random);
            int lastX = Integer.MIN_VALUE, lastZ = Integer.MIN_VALUE;
            for (int t = 1; t <= length * 2 + 2; t++) {
                int cx = (int) Math.round(fx * t);
                int cz = (int) Math.round(fz * t);
                if (cx == 0 && cz == 0) continue;
                // 超出分支长度（按欧氏距离）即停
                if ((int) Math.round(Math.sqrt(cx * cx + cz * cz)) > length) break;
                // 跳过光栅化产生的重复格
                if (cx == lastX && cz == lastZ) continue;
                lastX = cx;
                lastZ = cz;
                int radius = (int) Math.round(Math.sqrt(cx * cx + cz * cz));
                int high = Math.max(0, radius - (length - maxDroop));
                int low = high == 0 ? 0 : high - 1;
                for (int d = low; d <= high; d++) {
                    BlockPos p = crown.offset(cx, -d, cz);
                    if (trunk.contains(p)) continue;
                    placeLeaf(level, p, leaves);
                }
            }
        }
    }

    /** 在树冠处的枝干（主干顶 / 各分支顶）侧面随机挂 1~3 个眠椰（眠椰块·贴墙形态）。 */
    private void placeCoconuts(WorldGenLevel level, RandomSource random, List<BlockPos> crownAnchors) {
        if (crownAnchors.isEmpty()) {
            return;
        }
        int count = 1 + random.nextInt(3);
        BlockState coconut = ModBlocks.SLUMBER_PALM_WALL_BLOCK.get().defaultBlockState();
        Direction[] directions = Direction.Plane.HORIZONTAL.stream().toArray(Direction[]::new);
        for (int i = 0; i < count; i++) {
            BlockPos anchor = crownAnchors.get(random.nextInt(crownAnchors.size()));
            for (int j = directions.length - 1; j > 0; j--) {
                int k = random.nextInt(j + 1);
                Direction tmp = directions[j];
                directions[j] = directions[k];
                directions[k] = tmp;
            }
            for (Direction direction : directions) {
                BlockPos pos = anchor.relative(direction);
                if (!level.isEmptyBlock(pos)) {
                    continue;
                }
                BlockState state = coconut.setValue(SlumberPalmWallBlock.FACING, direction);
                if (state.canSurvive(level, pos)) {
                    level.setBlock(pos, state, 2);
                    break;
                }
            }
        }
    }

    private static boolean canReplace(WorldGenLevel level, BlockPos pos) {
        return level.isEmptyBlock(pos) || level.getBlockState(pos).canBeReplaced();
    }

    private static void setBlock(WorldGenLevel level, BlockPos pos, BlockState state) {
        if (canReplace(level, pos)) {
            level.setBlock(pos, state, 2);
        }
    }

    private static void placeLeaf(WorldGenLevel level, BlockPos pos, BlockState leaves) {
        setBlock(level, pos, leaves);
    }
}