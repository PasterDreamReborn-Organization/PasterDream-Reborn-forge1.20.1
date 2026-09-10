package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.BushBlock;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DyedreamGrassBlock extends SnowyDirtBlock implements BonemealableBlock {

    private static final int BONEMEAL_RADIUS = 3;

    public DyedreamGrassBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_MAGENTA)
                .sound(SoundType.GRASS)
                .strength(0.75F, 0.5F)
                .randomTicks());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canBeGrass(state, level, pos)) {
            level.setBlockAndUpdate(pos, ModBlocks.DYEDREAM_DIRT.get().defaultBlockState());
            return;
        }
        if (level.getMaxLocalRawBrightness(pos.above()) < 9) return;

        BlockState grassState = this.defaultBlockState();
        for (int i = 0; i < 4; i++) {
            BlockPos targetPos = pos.offset(
                    random.nextInt(3) - 1,
                    random.nextInt(5) - 3,
                    random.nextInt(3) - 1);
            BlockState targetState = level.getBlockState(targetPos);
            if (targetState.is(ModBlocks.DYEDREAM_DIRT.get()) && canPropagate(grassState, level, targetPos)) {
                level.setBlockAndUpdate(targetPos, grassState);
            }
        }
    }

    private static boolean canBeGrass(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        return !aboveState.isSolidRender(level, above) && aboveState.getFluidState().getAmount() < 8;
    }

    private static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
        return canBeGrass(state, level, pos) && !level.getFluidState(pos.above()).is(net.minecraft.tags.FluidTags.WATER);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction direction, IPlantable plantable) {
        if (plantable instanceof BushBlock bush) {
            return bush.mayPlaceOn(state, world, pos);
        }
        return false;
    }

    // ---- BonemealableBlock ----

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int attempt = 0; attempt < 16; attempt++) {
            int dx = random.nextInt(BONEMEAL_RADIUS * 2 + 1) - BONEMEAL_RADIUS;
            int dz = random.nextInt(BONEMEAL_RADIUS * 2 + 1) - BONEMEAL_RADIUS;
            cursor.set(pos.getX() + dx, pos.getY(), pos.getZ() + dz);

            // move up through the dirt column to find the surface
            while (cursor.getY() < level.getMaxBuildHeight() - 1
                    && level.getBlockState(cursor.above()).is(BlockTags.DIRT)) {
                cursor.move(Direction.UP);
            }
            // move down to the top dirt block if we landed above it
            while (cursor.getY() > level.getMinBuildHeight()
                    && !level.getBlockState(cursor).is(BlockTags.DIRT)) {
                cursor.move(Direction.DOWN);
            }
            if (!level.getBlockState(cursor).is(BlockTags.DIRT)) continue;

            BlockPos above = cursor.above();
            if (!level.getBlockState(above).isAir()) continue;

            BlockState plant = pickPlantFromBiome(level, above, random);
            if (plant == null || !plant.canSurvive(level, above)) continue;

            if (plant.getBlock() instanceof DoublePlantBlock) {
                if (!level.isEmptyBlock(above.above())) continue;
                DoublePlantBlock.placeAt(level, plant, above, 3);
            } else {
                level.setBlock(above, plant, 3);
            }
            return;
        }
    }

    /**
     * Gathers small-plant block states from the biome's VEGETAL_DECORATION
     * configured features, bypassing worldgen placement modifiers.
     */
    @Nullable
    private static BlockState pickPlantFromBiome(ServerLevel level, BlockPos pos, RandomSource random) {
        Holder<Biome> biome = level.getBiome(pos);
        BiomeGenerationSettings genSettings = biome.value().getGenerationSettings();
        HolderSet<PlacedFeature> vegetation = genSettings.features()
                .get(GenerationStep.Decoration.VEGETAL_DECORATION.ordinal());
        if (vegetation == null) return null;

        List<BlockState> candidates = new ArrayList<>();
        for (Holder<PlacedFeature> holder : vegetation) {
            if (holder.is(ModPlacedFeatures.CALCITE_STALICRIPE)
                    || holder.is(ModPlacedFeatures.SMALL_CALCITE_STALICRIPE)
                    || holder.is(ModPlacedFeatures.CLOUD_PILLAR_SMALL)
                    || holder.is(ModPlacedFeatures.CLOUD_PILLAR_LARGE)) {
                continue;
            }
            holder.value().getFeatures().forEach(cf -> {
                BlockState bs = extractSingleBlock(cf);
                if (bs != null) candidates.add(bs);
            });
        }
        return candidates.isEmpty() ? null : candidates.get(random.nextInt(candidates.size()));
    }

    /** Extracts the single BlockState from a RANDOM_PATCH or SIMPLE_BLOCK configured feature. */
    @Nullable
    private static BlockState extractSingleBlock(ConfiguredFeature<?, ?> cf) {
        Feature<?> feature = cf.feature();
        if (feature == Feature.RANDOM_PATCH && cf.config() instanceof RandomPatchConfiguration rpc) {
            return rpc.feature().value().getFeatures().findFirst()
                    .map(ConfiguredFeature::config)
                    .filter(SimpleBlockConfiguration.class::isInstance)
                    .map(SimpleBlockConfiguration.class::cast)
                    .map(SimpleBlockConfiguration::toPlace)
                    .map(sp -> sp.getState(null, BlockPos.ZERO))
                    .orElse(null);
        }
        if (feature == Feature.SIMPLE_BLOCK && cf.config() instanceof SimpleBlockConfiguration sbc) {
            return sbc.toPlace().getState(null, BlockPos.ZERO);
        }
        return null;
    }
}
