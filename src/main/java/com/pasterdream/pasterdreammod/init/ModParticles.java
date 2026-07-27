package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.client.particle.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModParticles {

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticleTypes.LEAVES_PARTICLE.get(), LeavesParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SHARP_SWORD_SLASH.get(), SharpSwordSlashParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.DUST_0_PARTICLE.get(), Dust0Particle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SPORE_PARTICLE.get(), SporeParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.BUFF_0_PARTICLE.get(), Buff0Particle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SOUL_PARTICLE.get(), SoulParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.CRACK_0_PARTICLE.get(), CrackParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.TERRASWORD_WAVE_PARTICLE.get(), TerraswordWaveParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.MELTDREAM_CRYSTAL_PARTICLE.get(), MeltdreamCrystalParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.MELTDREAM_CRYSTAL_BIG_PARTICLE.get(), MeltdreamCrystalBigParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.FOX_FIRE_0_PARTICLE.get(), FoxFire0Particle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.FOX_FIRE_1_PARTICLE.get(), FoxFire1Particle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.DREAMFERTILIZER_PARTICLE.get(), DreamFertilizerParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.FEATHER_WHITE_PARTICLE.get(), FeatherWhiteParticle::provider);
        event.registerSpriteSet(ModParticleTypes.SQUEAL_WAVE_PARTICLE.get(), SquealWaveParticle.Provider::new);
    }
}
