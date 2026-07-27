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
            PARTICLE_TYPES.register("leaves_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SHARP_SWORD_SLASH =
            PARTICLE_TYPES.register("sharp_sword_slash", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DUST_0_PARTICLE =
            PARTICLE_TYPES.register("dust_0_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SPORE_PARTICLE =
            PARTICLE_TYPES.register("spore_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> BUFF_0_PARTICLE =
            PARTICLE_TYPES.register("buff_0_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SOUL_PARTICLE =
            PARTICLE_TYPES.register("soul_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CRACK_0_PARTICLE =
            PARTICLE_TYPES.register("crack_0_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> TERRASWORD_WAVE_PARTICLE =
            PARTICLE_TYPES.register("terrasword_wave_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> MELTDREAM_CRYSTAL_PARTICLE =
            PARTICLE_TYPES.register("meltdream_crystal_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> MELTDREAM_CRYSTAL_BIG_PARTICLE =
            PARTICLE_TYPES.register("meltdream_crystal_big_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FOX_FIRE_0_PARTICLE =
            PARTICLE_TYPES.register("fox_fire_0_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FOX_FIRE_1_PARTICLE =
            PARTICLE_TYPES.register("fox_fire_1_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DREAMFERTILIZER_PARTICLE =
            PARTICLE_TYPES.register("dreamfertilizer_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FEATHER_WHITE_PARTICLE =
            PARTICLE_TYPES.register("feather_white_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SHADOW_STONE_PARTICLE =
            PARTICLE_TYPES.register("shadow_stone_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SQUEAL_WAVE_PARTICLE =
            PARTICLE_TYPES.register("squeal_wave_particle", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
