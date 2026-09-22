package com.pasterdream.pasterdreammod.worldgen.feature;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModTreeDecoratorTypes;
import com.pasterdream.pasterdreammod.world.block.FigVineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 风泊树垂挂装饰器：在树冠下方随机垂挂两种东西，做出「垂叶」效果——
 * 较长的无花果藤（{@link FigVineBlock}）与短串下垂的风泊树叶。
 * 写法与染梦树的 {@link LightBallTreeDecorator} 同款。
 */
public class WindMoorDroopingDecorator extends TreeDecorator {

    public static final WindMoorDroopingDecorator INSTANCE = new WindMoorDroopingDecorator();

    private static final int MIN_VINE_STRANDS = 3;
    private static final int MAX_VINE_STRANDS = 5;
    private static final int MIN_VINE_LENGTH = 2;
    private static final int MAX_VINE_LENGTH = 4;

    private static final int MIN_LEAF_STRANDS = 3;
    private static final int MAX_LEAF_STRANDS = 6;
    private static final int MIN_LEAF_LENGTH = 1;
    private static final int MAX_LEAF_LENGTH = 3;

    @Override
    public void place(Context context) {
        RandomSource random = context.random();

        Set<BlockPos> candidates = new LinkedHashSet<>();
        for (BlockPos leaf : context.leaves()) {
            BlockPos below = leaf.below();
            if (context.isAir(below)) {
                candidates.add(below);
            }
        }
        if (candidates.isEmpty()) {
            return;
        }

        List<BlockPos> positions = new ArrayList<>(candidates);

        // 较长的无花果藤
        int vineStrands = MIN_VINE_STRANDS + random.nextInt(MAX_VINE_STRANDS - MIN_VINE_STRANDS + 1);
        for (int i = 0; i < vineStrands && !positions.isEmpty(); i++) {
            hangVine(context, random, positions.remove(random.nextInt(positions.size())));
        }

        // 下垂的风泊树叶
        int leafStrands = MIN_LEAF_STRANDS + random.nextInt(MAX_LEAF_STRANDS - MIN_LEAF_STRANDS + 1);
        for (int i = 0; i < leafStrands && !positions.isEmpty(); i++) {
            hangLeaves(context, random, positions.remove(random.nextInt(positions.size())));
        }
    }

    private static void hangVine(Context context, RandomSource random, BlockPos top) {
        int length = MIN_VINE_LENGTH + random.nextInt(MAX_VINE_LENGTH - MIN_VINE_LENGTH + 1);
        for (int k = 0; k < length; k++) {
            BlockPos p = top.below(k);
            if (!context.isAir(p)) {
                break;
            }
            boolean head = k == length - 1;
            context.setBlock(p, ModBlocks.FIG_VINE.get().defaultBlockState()
                    .setValue(FigVineBlock.HEAD, head));
        }
    }

    private static void hangLeaves(Context context, RandomSource random, BlockPos top) {
        int length = MIN_LEAF_LENGTH + random.nextInt(MAX_LEAF_LENGTH - MIN_LEAF_LENGTH + 1);
        for (int k = 0; k < length; k++) {
            BlockPos p = top.below(k);
            if (!context.isAir(p)) {
                break;
            }
            BlockState leaves = random.nextBoolean()
                    ? ModBlocks.WIND_MOOR_LEAVES_0.get().defaultBlockState()
                    : ModBlocks.WIND_MOOR_LEAVES_1.get().defaultBlockState();
            context.setBlock(p, leaves);
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecoratorTypes.WIND_MOOR_DROOPING.get();
    }
}
