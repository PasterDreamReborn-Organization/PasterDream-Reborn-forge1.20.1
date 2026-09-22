package com.pasterdream.pasterdreammod.world.behavior;

import com.pasterdream.pasterdreammod.worldgen.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class ModTreeGrowers {

    private static final float COLD_TEMPERATURE_THRESHOLD = 0.3f;

    // 眠椰树：双螺旋树干 + 焦点椭圆树冠（见 SlumberPalmTreeFeature）
    public static final AbstractTreeGrower SLUMBER_PALM = new AbstractTreeGrower() {
        @Nullable
        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
            return ModConfiguredFeatures.SLUMBER_PALM_TREE;
        }
    };

    // 风泊树苗：生长成小型风泊树（金合欢树干/树冠塑形器 + 垂叶装饰器）
    public static final AbstractTreeGrower WIND_MOOR = new AbstractTreeGrower() {
        @Nullable
        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
            return ModConfiguredFeatures.SMALL_WIND_MOOR_TREE;
        }
    };

    public static final AbstractTreeGrower DYEDREAM = new AbstractTreeGrower() {
        @Nullable
        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
            return ModConfiguredFeatures.DYEDREAM_TREE;
        }

        @Override
        public boolean growTree(ServerLevel level, ChunkGenerator generator, BlockPos pos, BlockState state, RandomSource random) {
            float temperature = level.getBiome(pos).value().getBaseTemperature();

            ResourceKey<ConfiguredFeature<?, ?>> featureKey;
            if (temperature < COLD_TEMPERATURE_THRESHOLD) {
                featureKey = ModConfiguredFeatures.DYEDREAM_TREE_COLD_SPRUCE;
            } else {
                featureKey = this.getConfiguredFeature(random, level.getBlockState(pos.below()).is(BlockTags.DIRT));
            }

            if (featureKey == null) return false;

            Holder<ConfiguredFeature<?, ?>> holder = level.registryAccess()
                    .registryOrThrow(Registries.CONFIGURED_FEATURE)
                    .getHolder(featureKey).orElse(null);
            if (holder == null) return false;

            ConfiguredFeature<?, ?> cf = holder.value();
            BlockState air = Blocks.AIR.defaultBlockState();
            level.setBlock(pos, air, 4);
            if (cf.place(level, generator, random, pos)) {
                if (level.getBlockState(pos) == air) {
                    level.sendBlockUpdated(pos, state, air, 2);
                }
                return true;
            } else {
                level.setBlock(pos, state, 4);
                return false;
            }
        }
    };
}
