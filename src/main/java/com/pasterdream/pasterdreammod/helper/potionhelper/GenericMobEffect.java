package com.pasterdream.pasterdreammod.helper.potionhelper;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public record GenericMobEffect(MobEffect effectType, int level, int time)
{
    public static GenericMobEffect fromMobEffectInstanceToGenericMobEffect(MobEffectInstance mobEffectInstance)
    {
        return new GenericMobEffect(mobEffectInstance.getEffect(), mobEffectInstance.getAmplifier(), mobEffectInstance.getDuration());
    }

    public static MobEffectInstance fromGenericMobEffectToEffectInstance(GenericMobEffect genericMobEffect)
    {
        return new MobEffectInstance(genericMobEffect.effectType(), genericMobEffect.time(), genericMobEffect.level());
    }
}
