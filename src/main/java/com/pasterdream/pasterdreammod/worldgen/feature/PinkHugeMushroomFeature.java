package com.pasterdream.pasterdreammod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.HugeRedMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class PinkHugeMushroomFeature extends HugeRedMushroomFeature {

    private int cachedHeight = 0;

    public PinkHugeMushroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    protected int getTreeHeight(RandomSource random) {
        cachedHeight = super.getTreeHeight(random);
        return cachedHeight;
    }

    @Override
    public boolean place(FeaturePlaceContext<HugeMushroomFeatureConfiguration> context) {
        if (!super.place(context)) {
            return false;
        }

        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        int yBase = origin.getY();

        // 在菌柄中部到菌盖下方放置菌光体
        for (int y = 2; y < cachedHeight - 1; y++) {
            if (random.nextFloat() > 0.25f) {
                continue;
            }
            Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
            BlockPos target = origin.atY(yBase + y).relative(dir);
            if (level.isEmptyBlock(target)) {
                level.setBlock(target, ModBlocks.PINK_SHROOMLIGHT.get().defaultBlockState(), 3);
            }
        }

        return true;
    }
}
