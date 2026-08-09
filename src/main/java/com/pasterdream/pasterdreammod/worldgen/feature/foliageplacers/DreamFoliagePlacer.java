package com.pasterdream.pasterdreammod.worldgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pasterdream.pasterdreammod.init.ModFoliagePlacerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class DreamFoliagePlacer extends FoliagePlacer {
    public static final Codec<DreamFoliagePlacer> CODEC = RecordCodecBuilder.create(instance ->
            foliagePlacerParts(instance)
                    .and(instance.group(
                            Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_chance").forGetter(p -> p.hangingLeavesChance),
                            Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_extension_chance").forGetter(p -> p.hangingLeavesExtensionChance)
                    ))
                    .apply(instance, DreamFoliagePlacer::new)
    );

    private final float hangingLeavesChance;
    private final float hangingLeavesExtensionChance;

    public DreamFoliagePlacer(IntProvider radius, IntProvider offset, float hangingLeavesChance, float hangingLeavesExtensionChance) {
        super(radius, offset);
        this.hangingLeavesChance = hangingLeavesChance;
        this.hangingLeavesExtensionChance = hangingLeavesExtensionChance;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacerTypes.DREAM_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource random,
            TreeConfiguration config,
            int maxFreeTreeHeight,
            FoliageAttachment attachment,
            int foliageHeight,
            int foliageRadius,
            int offset
    ) {
        boolean doubleTrunk = attachment.doubleTrunk();
        BlockPos pos = attachment.pos().above(offset);
        int r = foliageRadius;

        // Acacia-style 3 flat layers, bottom two with cherry-style hanging leaves
        this.placeLeavesRowWithHangingLeavesBelow(level, setter, random, config, pos,
                r + attachment.radiusOffset(), -1 - foliageHeight, doubleTrunk,
                this.hangingLeavesChance, this.hangingLeavesExtensionChance);
        this.placeLeavesRow(level, setter, random, config, pos, r - 1, 0, doubleTrunk);
        this.placeLeavesRowWithHangingLeavesBelow(level, setter, random, config, pos,
                r + attachment.radiusOffset() - 1, 0, doubleTrunk,
                this.hangingLeavesChance, this.hangingLeavesExtensionChance);
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int radius, boolean doubleTrunk) {
        if (localY == 0) {
            return (localX > 1 || localZ > 1) && localX != 0 && localZ != 0;
        }
        return localX == radius && localZ == radius && radius > 0;
    }
}
