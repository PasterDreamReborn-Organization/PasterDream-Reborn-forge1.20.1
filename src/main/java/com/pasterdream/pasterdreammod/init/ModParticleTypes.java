package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, PasterDreamMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> LEAVES_PARTICLE =
            PARTICLE_TYPES.register("leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SHARP_SWORD_SLASH =
            PARTICLE_TYPES.register("sharp_sword_slash", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DUST_0_PARTICLE =
            PARTICLE_TYPES.register("dust", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SPORE_PARTICLE =
            PARTICLE_TYPES.register("spore", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> BUFF_0_PARTICLE =
            PARTICLE_TYPES.register("buff", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SOUL_PARTICLE =
            PARTICLE_TYPES.register("soul", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CRACK_0_PARTICLE =
            PARTICLE_TYPES.register("crack", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> TERRASWORD_WAVE_PARTICLE =
            PARTICLE_TYPES.register("terrasword_wave", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> MELTDREAM_CRYSTAL_PARTICLE =
            PARTICLE_TYPES.register("melt_dream_crystal", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> MELTDREAM_CRYSTAL_BIG_PARTICLE =
            PARTICLE_TYPES.register("melt_dream_crystal_big", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FOX_FIRE_0_PARTICLE =
            PARTICLE_TYPES.register("fox_fire_0", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FOX_FIRE_1_PARTICLE =
            PARTICLE_TYPES.register("fox_fire_1", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DREAMFERTILIZER_PARTICLE =
            PARTICLE_TYPES.register("dream_fertilizer", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FEATHER_WHITE_PARTICLE =
            PARTICLE_TYPES.register("feather_white", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SHADOW_STONE_PARTICLE =
            PARTICLE_TYPES.register("shadow_stone", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SQUEAL_WAVE_PARTICLE =
            PARTICLE_TYPES.register("squeal_wave", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> WHITE_SWORD_SPARK_PARTICLE =
            PARTICLE_TYPES.register("white_sword_spark", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> POISON_GAS_PARTICLE =
            PARTICLE_TYPES.register("poison_gas", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> POISON_GAS_PARTICLE_1 =
            PARTICLE_TYPES.register("poison_gas_1", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> REJUVENATION_PARTICLE =
            PARTICLE_TYPES.register("rejuvenation", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> YELLOW_SMOKE_PARTICLE =
            PARTICLE_TYPES.register("yellow_smoke", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> BERSERK_PARTICLE =
            PARTICLE_TYPES.register("berserk", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SNOWFLAKE_0_PARTICLE =
            PARTICLE_TYPES.register("snowflake_0", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SNOWFLAKE_1_PARTICLE =
            PARTICLE_TYPES.register("snowflake_1", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> INFERNO_PARTICLE =
            PARTICLE_TYPES.register("inferno", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> LIGHTNING_PARTICLE =
            PARTICLE_TYPES.register("lightning", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FIREFLY_GLASS_JAR_PARTICLE =
            PARTICLE_TYPES.register("firefly_glass_jar", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> LIGHT_FIREFLY_GLASS_JAR_PARTICLE =
            PARTICLE_TYPES.register("light_firefly_glass_jar", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FIREFLY_PARTICLE =
            PARTICLE_TYPES.register("firefly", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
