package com.pasterdream.pasterdreammod.world.block.portal;

import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 染梦侵染的共用逻辑。染梦传送门与染梦裂隙都会在随机刻调用 {@link #tick}，
 * 按 {@code pasterdream:dyedream_contamination} 配方由近及远地转换周围方块。
 */
public final class DyedreamContamination {
    public static final int DEFAULT_RADIUS = 5;
    public static final int MAX_PER_TICK = 4;
    /** 按半径缓存的偏移表，按到源方块的距离由近到远排序（不含自身）。 */
    private static final Map<Integer, BlockPos[]> OFFSETS_CACHE = new ConcurrentHashMap<>();

    private DyedreamContamination() {
    }

    public static void tick(ServerLevel level, BlockPos pos, int radius, int maxConversions) {
        if (radius <= 0 || maxConversions <= 0) {
            return;
        }
        if (!level.isAreaLoaded(pos, radius)) {
            return;
        }
        List<DyedreamContaminationRecipe> recipes = level.getRecipeManager()
                .getAllRecipesFor(ModRecipes.DYEDREAM_CONTAMINATION.get());
        if (recipes.isEmpty()) {
            return;
        }
        int converted = 0;
        for (BlockPos offset : offsetsFor(radius)) {
            if (converted >= maxConversions) {
                break;
            }
            BlockPos target = pos.offset(offset);
            BlockState contaminated = getContaminatedState(level.getBlockState(target), recipes);
            if (contaminated != null) {
                if (contaminated.getBlock() instanceof DoublePlantBlock) {
                    // 用 UPDATE_KNOWN_SHAPE 抑制形状更新：否则替换原高草时会因上下半互相 updateShape
                    // 被连锁清除，导致只替换了一部分。
                    DoublePlantBlock.placeAt(level, contaminated, target,
                            Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
                } else {
                    level.setBlockAndUpdate(target, contaminated);
                }
                converted++;
            }
        }
    }

    @Nullable
    private static BlockState getContaminatedState(BlockState state, List<DyedreamContaminationRecipe> recipes) {
        if (state.is(ModBlockTags.DYEDREAM_CONTAMINATION_BLACKLIST)) {
            return null;
        }
        if (state.getBlock() instanceof DoublePlantBlock
                && state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
            return null;
        }
        for (DyedreamContaminationRecipe recipe : recipes) {
            if (recipe.matches(state)) {
                BlockState output = recipe.getOutputState(state);
                if (output != state) {
                    return output;
                }
            }
        }
        return null;
    }

    private static BlockPos[] offsetsFor(int radius) {
        return OFFSETS_CACHE.computeIfAbsent(radius, DyedreamContamination::buildOffsets);
    }

    private static BlockPos[] buildOffsets(int radius) {
        int radiusSq = radius * radius;
        List<BlockPos> offsets = new ArrayList<>();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    int distSq = x * x + y * y + z * z;
                    if (distSq == 0 || distSq > radiusSq) {
                        continue;
                    }
                    offsets.add(new BlockPos(x, y, z));
                }
            }
        }
        offsets.sort(Comparator.comparingInt(o -> o.getX() * o.getX() + o.getY() * o.getY() + o.getZ() * o.getZ()));
        return offsets.toArray(new BlockPos[0]);
    }
}
