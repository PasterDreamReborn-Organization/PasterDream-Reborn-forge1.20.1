package com.pasterdream.pasterdreammod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

/**
 * 风之旅途水池生成配置：先按原版湖泊生成水池，再按概率在岸边生成 1~2 棵眠椰树。
 *
 * @param lake       水池本体（流体 + 围边方块）
 * @param treeChance 每个水池生成眠椰树的概率（0~1）
 * @param treeCount  掷中后尝试生成的眠椰树数量
 */
public record WindJourneyWaterPoolConfiguration(
        LakeFeature.Configuration lake,
        float treeChance,
        IntProvider treeCount
) implements FeatureConfiguration {
    public static final Codec<WindJourneyWaterPoolConfiguration> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    LakeFeature.Configuration.CODEC.fieldOf("lake").forGetter(WindJourneyWaterPoolConfiguration::lake),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("tree_chance").forGetter(WindJourneyWaterPoolConfiguration::treeChance),
                    IntProvider.codec(1, 8).fieldOf("tree_count").forGetter(WindJourneyWaterPoolConfiguration::treeCount)
            ).apply(instance, WindJourneyWaterPoolConfiguration::new)
    );
}
