package com.pasterdream.pasterdreammod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
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
 * 阴影古墓（NBT 放置）—— 在阴影古迹群系地表下陷2格放置 shadow_tomb_0/1 结构。
 * 随机选取两个变体之一，替换表层阴影菌岩为阴影石后放置 NBT 模板。
 */
public class ShadowTombFeature extends Feature<NoneFeatureConfiguration> {

    private static final ResourceLocation[] STRUCTURE_IDS = {
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_tomb_0"),
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_tomb_1")
    };

    public ShadowTombFeature(Codec<NoneFeatureConfiguration> codec) {
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

        BlockPos placementPos = origin.below(2);
        Rotation rotation = Rotation.values()[random.nextInt(4)];

        // 替换表层阴影菌岩，让结构底部能正确落位
        Vec3i templateSize = template.getSize();
        int footX = rotation.ordinal() % 2 == 0 ? templateSize.getX() : templateSize.getZ();
        int footZ = rotation.ordinal() % 2 == 0 ? templateSize.getZ() : templateSize.getX();
        BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos();

        for (int x = 0; x < footX; x++) {
            for (int z = 0; z < footZ; z++) {
                check.set(placementPos.getX() + x, placementPos.getY(), placementPos.getZ() + z);
                if (level.getBlockState(check).is(ModBlocks.SHADOW_NYLIUM.get())) {
                    level.setBlock(check, ModBlocks.SHADOW_STONE.get().defaultBlockState(), Block.UPDATE_ALL);
                }
            }
        }

        template.placeInWorld(level, placementPos, placementPos,
                new StructurePlaceSettings().setRandom(random).setRotation(rotation),
                random, Block.UPDATE_ALL);
        return true;
    }
}
