package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.worldgen.feature.CalciteSpikeFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.PinkHugeMushroomFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.ShadowChainPillarFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.ShadowHandFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.ShadowRuinWallFeature;
import com.pasterdream.pasterdreammod.worldgen.feature.StonePillarFeature;
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

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
