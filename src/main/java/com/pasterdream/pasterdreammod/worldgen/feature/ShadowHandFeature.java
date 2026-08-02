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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class ShadowHandFeature extends Feature<NoneFeatureConfiguration> {

    private static final ResourceLocation STRUCTURE_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_hand");

    public ShadowHandFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        StructureTemplate template = level.getLevel().getStructureManager()
                .get(STRUCTURE_ID).orElse(null);
        if (template == null) {
            return false;
        }

        Rotation rotation = Rotation.values()[random.nextInt(4)];
        Vec3i templateSize = template.getSize();
        int footX = rotation.ordinal() % 2 == 0 ? templateSize.getX() : templateSize.getZ();
        int footZ = rotation.ordinal() % 2 == 0 ? templateSize.getZ() : templateSize.getX();
        BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos();

        for (int x = 0; x < footX; x++) {
            for (int z = 0; z < footZ; z++) {
                check.set(origin.getX() + x, origin.getY() - 1, origin.getZ() + z);
                if (level.getBlockState(check).canBeReplaced()) {
                    return false;
                }
            }
        }

        // 替换表层阴影菌岩，让结构底部能正确落位
        for (int x = 0; x < footX; x++) {
            for (int z = 0; z < footZ; z++) {
                check.set(origin.getX() + x, origin.getY(), origin.getZ() + z);
                if (level.getBlockState(check).is(ModBlocks.SHADOW_NYLIUM.get())) {
                    level.setBlock(check, ModBlocks.SHADOW_STONE.get().defaultBlockState(), Block.UPDATE_ALL);
                }
            }
        }

        template.placeInWorld(level, origin, origin,
                new StructurePlaceSettings().setRandom(random).setRotation(rotation),
                random, Block.UPDATE_ALL);
        return true;
    }
}
