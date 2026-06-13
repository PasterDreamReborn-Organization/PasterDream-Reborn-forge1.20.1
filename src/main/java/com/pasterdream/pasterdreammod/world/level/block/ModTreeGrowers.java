package com.pasterdream.pasterdreammod.world.level.block;

import com.pasterdream.pasterdreammod.worldgen.ModConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import org.jetbrains.annotations.Nullable;

public class ModTreeGrowers {

    public static final AbstractTreeGrower DYEDREAM = new AbstractTreeGrower() {
        @Nullable
        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
            return ModConfiguredFeatures.DYEDREAM_TREE;
        }
    };
}
