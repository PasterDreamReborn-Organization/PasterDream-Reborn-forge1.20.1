package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import com.pasterdream.pasterdreammod.world.block.cropblock.PasterDreamCropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * 萦风合金工具的共享逻辑。
 * <p>
 * 1. 疾风过境：移动速度越快挖掘速度越快（与剑被动"雷随疾风"同源的移速公式）。
 * 2. 风卷掉落：挖掘/连锁产生的掉落物被风卷到玩家身边。
 * 3. 连锁破坏：同类方块（镐共振 / 锹水平连锁）、原木+树叶（斧）、同类作物（锄）。
 */
public final class FluffyWindAlloyToolHelper {

    private FluffyWindAlloyToolHelper() {
    }

    // ===== 疾风过境 =====

    /** 挖掘速度 = 基础 × (1 + 移动速度 × 系数)，步行约 1.4 倍、疾跑约 1.5 倍 */
    public static final double WIND_MINING_SPEED_FACTOR = 4.0;

    /** 与剑被动一致：取"移动速度属性"与"瞬时移动速度"中的较大值 */
    public static float computeMiningMultiplier(Player player) {
        double speed = Math.max(player.getAttributeValue(Attributes.MOVEMENT_SPEED),
                player.getDeltaMovement().horizontalDistance());
        return (float) (1.0 + speed * WIND_MINING_SPEED_FACTOR);
    }

    // ===== 风卷掉落 =====

    private static final double SWEEP_RADIUS = 2.5;

    /** 把 pos 附近已生成的掉落物吹向玩家（用于玩家直接挖掘的方块）。
     *  延迟 1 tick 执行：原版掉落物在 mineBlock 之后才生成，需等其生成后再吹。 */
    public static void sweepDropsToPlayer(Level level, BlockPos pos, Player player) {
        if (level.isClientSide) return;
        if (level instanceof ServerLevel serverLevel) {
            MinecraftServer server = serverLevel.getServer();
            server.tell(new TickTask(server.getTickCount() + 1, () -> {
                if (!player.isAlive()) return;
                Vec3 target = player.getEyePosition(1.0F);
                for (ItemEntity item : serverLevel.getEntitiesOfClass(ItemEntity.class, new AABB(pos).inflate(SWEEP_RADIUS))) {
                    if (!item.isAlive()) continue;
                    blowItemToward(item, target);
                }
            }));
        }
    }

    /** 在 pos 生成掉落物并吹向玩家（用于连锁破坏的方块） */
    public static void spawnDropFlyingToPlayer(Level level, BlockPos pos, ItemStack drop, Player player) {
        if (level.isClientSide) return;
        ItemEntity item = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
        blowItemToward(item, player.getEyePosition(1.0F));
        level.addFreshEntity(item);
    }

    private static void blowItemToward(ItemEntity item, Vec3 target) {
        Vec3 dir = target.subtract(item.position());
        double dist = dir.length();
        if (dist < 0.1) return;
        Vec3 vel = dir.normalize().scale(Math.min(0.6, 0.05 + dist * 0.1));
        item.setDeltaMovement(vel.x, vel.y + 0.15, vel.z);
        item.setPickUpDelay(0);
    }

    // ===== 连锁破坏 =====

    /** 破坏一个方块：按战利品表掉落并把掉落物吹向玩家 */
    private static int breakBlockWithSweep(Level level, BlockPos pos, BlockState state, Player player, ItemStack stack) {
        if (level.isClientSide) return 0;
        state.spawnAfterBreak((ServerLevel) level, pos, stack, true);
        List<ItemStack> drops = Block.getDrops(state, (ServerLevel) level, pos, level.getBlockEntity(pos), player, stack);
        for (ItemStack drop : drops) {
            spawnDropFlyingToPlayer(level, pos, drop, player);
        }
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        return 1;
    }

    /**
     * 镐 · 风蚀共振：沿击打面方向连锁破坏同种方块（染梦合金锤式判定）。
     * <p>
     * 依据准心射线命中的方块面，从被击打的面起向墙内延伸 3 格（深度），
     * 垂直于击打面的两个方向各扩展 1 格，构成 3×3×3 区域。
     * targetCheck 用于判定目标是否可连锁（镐/锹为"正确工具"，锄为"作物或正确工具"）。
     */
    public static int breakSameTypeFromHit(Level level, BlockPos origin, BlockState state, Player player,
                                           ItemStack stack, LivingEntity entity, Predicate<BlockState> targetCheck) {
        if (level.isClientSide) return 0;
        if (!(entity.pick(20.0, 1.0F, false) instanceof BlockHitResult hitResult)) return 0;
        Direction hitDir = hitResult.getDirection();
        Direction.Axis axis = hitDir.getAxis();
        int stepX = -hitDir.getStepX();
        int stepY = -hitDir.getStepY();
        int stepZ = -hitDir.getStepZ();

        int broken = 0;
        for (int depth = 0; depth < 3; depth++) {
            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    int dx, dy, dz;
                    if (axis == Direction.Axis.Y) {
                        dx = a;
                        dy = depth * stepY;
                        dz = b;
                    } else if (axis == Direction.Axis.X) {
                        dx = depth * stepX;
                        dy = a;
                        dz = b;
                    } else {
                        dx = a;
                        dy = b;
                        dz = depth * stepZ;
                    }
                    BlockPos target = origin.offset(dx, dy, dz);
                    if (target.equals(origin)) continue;
                    BlockState targetState = level.getBlockState(target);
                    if (targetState.getDestroySpeed(level, target) == 0.0F) continue;
                    if (!targetState.is(state.getBlock())) continue;
                    if (!targetCheck.test(targetState)) continue;
                    broken += breakBlockWithSweep(level, target, targetState, player, stack);
                }
            }
        }
        return broken;
    }

    // ===== 斧 · 落叶秋风 =====

    private static final int LOG_CHAIN_CAP = 32;   // 连锁原木数量上限
    private static final int LEAF_BREAK_CAP = 64;  // 吹落树叶数量上限
    private static final int LEAF_SCAN_RADIUS = 2; // 围绕每根原木扫描树叶的范围

    /** 潜行挖原木：连锁破坏相连原木并吹落周围树叶，掉落物被风卷到玩家身边 */
    public static int breakLogChain(Level level, BlockPos origin, BlockState logState, Player player, ItemStack stack) {
        if (level.isClientSide) return 0;
        int broken = 0;
        Set<BlockPos> logs = collectConnected(level, origin, logState.getBlock(), LOG_CHAIN_CAP);
        for (BlockPos logPos : logs) {
            if (logPos.equals(origin)) continue;
            BlockState state = level.getBlockState(logPos);
            if (state.getDestroySpeed(level, logPos) == 0.0F) continue;
            broken += breakBlockWithSweep(level, logPos, state, player, stack);
        }
        int leavesBroken = 0;
        for (BlockPos logPos : logs) {
            for (int x = -LEAF_SCAN_RADIUS; x <= LEAF_SCAN_RADIUS && leavesBroken < LEAF_BREAK_CAP; x++) {
                for (int y = -1; y <= 3 && leavesBroken < LEAF_BREAK_CAP; y++) {
                    for (int z = -LEAF_SCAN_RADIUS; z <= LEAF_SCAN_RADIUS && leavesBroken < LEAF_BREAK_CAP; z++) {
                        BlockPos leafPos = logPos.offset(x, y, z);
                        BlockState leafState = level.getBlockState(leafPos);
                        if (leafState.getDestroySpeed(level, leafPos) == 0.0F) continue;
                        if (!leafState.is(BlockTags.LEAVES)) continue;
                        broken += breakBlockWithSweep(level, leafPos, leafState, player, stack);
                        leavesBroken++;
                    }
                }
            }
        }
        return broken;
    }

    /** 从 origin 出发向 6 方向洪泛收集同种方块（上限 cap） */
    private static Set<BlockPos> collectConnected(Level level, BlockPos origin, Block block, int cap) {
        Set<BlockPos> visited = new HashSet<>();
        Deque<BlockPos> queue = new ArrayDeque<>();
        visited.add(origin);
        queue.add(origin);
        while (!queue.isEmpty() && visited.size() < cap) {
            BlockPos cur = queue.poll();
            for (Direction dir : Direction.values()) {
                BlockPos next = cur.relative(dir);
                if (visited.contains(next)) continue;
                BlockState state = level.getBlockState(next);
                if (state.is(block) && state.getDestroySpeed(level, next) != 0.0F) {
                    visited.add(next);
                    if (visited.size() >= cap) break;
                    queue.add(next);
                }
            }
        }
        return visited;
    }

    // ===== 锄 · 春风化雨 =====

    private static final TagKey<Block> FORGE_CROPS =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", "crops"));

    public static boolean isCrop(BlockState state) {
        return state.is(BlockTags.CROPS) || state.is(FORGE_CROPS)
                || state.getBlock() instanceof PasterDreamCropBlock;
    }

    /** 锄 · 收获：以打击点为中心采集 5×5 水平范围内的所有作物，掉落物被风卷到玩家身边 */
    public static int breakCropsArea(Level level, BlockPos center, Player player, ItemStack stack) {
        if (level.isClientSide) return 0;
        int broken = 0;
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos pos = center.offset(x, 0, z);
                if (pos.equals(center)) continue; // 被挖的那格已由玩家破坏
                BlockState state = level.getBlockState(pos);
                if (!isCrop(state)) continue;
                broken += breakBlockWithSweep(level, pos, state, player, stack);
            }
        }
        return broken;
    }

    /** 催熟 3×3 范围内的作物，返回催熟数量（每格消耗 1 耐久） */
    public static int ripenCrops(Level level, BlockPos center) {
        if (level.isClientSide) return 0;
        int ripened = 0;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos pos = center.offset(x, 0, z);
                BlockState state = level.getBlockState(pos);
                if (state.getBlock() instanceof PasterDreamCropBlock) {
                    if (state.getValue(PasterDreamCropBlock.AGE) < 1) {
                        level.setBlock(pos, state.setValue(PasterDreamCropBlock.AGE, 1), 2);
                        level.levelEvent(2005, pos, 0);
                        ripened++;
                    }
                } else if (isCrop(state) && state.getBlock() instanceof BonemealableBlock bonemealable
                        && bonemealable.isValidBonemealTarget(level, pos, state, false)) {
                    bonemealable.performBonemeal((ServerLevel) level, level.random, pos, state);
                    level.levelEvent(2005, pos, 0);
                    ripened++;
                }
            }
        }
        return ripened;
    }
}