package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.worldgen.feature.CalciteConeClusterFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.CalciteConeFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.CalciteSpikeFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.FloatingLightBallFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.LushCaveMushroomFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.PinkHugeMushroomFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.PinkMushroomCurtainFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.ShadowChainPillarFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.ShadowHandFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.ShadowRuinWallFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.ShadowTombFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.SlumberPalmTreeConfiguration;
import com.pasterdream.pasterdreammod.worldgen.feature.SlumberPalmTreeFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.SmallPinkMushroomFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.StonePillarFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.WindJourneyWaterPoolConfiguration;
import com.pasterdream.pasterdreammod.worldgen.feature.WindJourneyWaterPoolFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {

    private static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, PasterDreamMod.MOD_ID);

    public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> PINK_HUGE_MUSHROOM =
            FEATURES.register("pink_huge_mushroom",
                    () -> new PinkHugeMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CALCITE_SPIKE =
            FEATURES.register("calcite_spike",
                    () -> new CalciteSpikeFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CALCITE_CONE =
            FEATURES.register("calcite_cone",
                    () -> new CalciteConeFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CALCITE_CONE_CLUSTER =
            FEATURES.register("calcite_cone_cluster",
                    () -> new CalciteConeClusterFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> LUSH_CAVE_MUSHROOM =
            FEATURES.register("lush_cave_mushroom",
                    () -> new LushCaveMushroomFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FLOATING_LIGHT_BALL =
            FEATURES.register("floating_light_ball",
                    () -> new FloatingLightBallFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SMALL_PINK_MUSHROOM =
            FEATURES.register("small_pink_mushroom",
                    () -> new SmallPinkMushroomFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> PINK_MUSHROOM_CURTAIN =
            FEATURES.register("pink_mushroom_curtain",
                    () -> new PinkMushroomCurtainFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SHADOW_CHAIN_PILLAR =
            FEATURES.register("shadow_chain_pillar",
                    () -> new ShadowChainPillarFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SHADOW_RUIN_WALL =
            FEATURES.register("shadow_ruin_wall",
                    () -> new ShadowRuinWallFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SHADOW_HAND =
            FEATURES.register("shadow_hand",
                    () -> new ShadowHandFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> STONE_PILLAR =
            FEATURES.register("stone_pillar",
                    () -> new StonePillarFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SHADOW_TOMB =
            FEATURES.register("shadow_tomb",
                    () -> new ShadowTombFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<SlumberPalmTreeConfiguration>> SLUMBER_PALM_TREE =
            FEATURES.register("slumber_palm_tree",
                    () -> new SlumberPalmTreeFeature(SlumberPalmTreeConfiguration.CODEC));

    public static final RegistryObject<Feature<WindJourneyWaterPoolConfiguration>> WIND_JOURNEY_WATER_POOL =
            FEATURES.register("wind_journey_water_pool",
                    () -> new WindJourneyWaterPoolFeature(WindJourneyWaterPoolConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
