package com.pasterdream.pasterdreammod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/**
 * 方解石尖锥（NBT 放置）—— 原作 stone_pillar_0 / stone_pillar_1 结构的 Feature 封装。
 * 随机选取两个变体之一，在世界表面下方 5 格开始放置，复刻原作 jigsaw 结构的
 * start_height: {absolute: -5} + project_start_to_heightmap: WORLD_SURFACE_WG。
 */
public class StonePillarFeature extends Feature<NoneFeatureConfiguration> {

    private static final ResourceLocation[] STRUCTURE_IDS = {
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "stone_pillar_0"),
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "stone_pillar_1")
    };

    public StonePillarFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        ResourceLocation structureId = STRUCTURE_IDS[random.nextInt(STRUCTURE_IDS.length)];
        StructureTemplate template = level.getLevel().getStructureManager()
                .get(structureId).orElse(null);
        if (template == null) {
            return false;
        }

        BlockPos placementPos = origin.below(5);
        Rotation rotation = Rotation.values()[random.nextInt(4)];

        template.placeInWorld(level, placementPos, placementPos,
                new StructurePlaceSettings().setRandom(random).setRotation(rotation),
                random, Block.UPDATE_ALL);
        return true;
    }
}
