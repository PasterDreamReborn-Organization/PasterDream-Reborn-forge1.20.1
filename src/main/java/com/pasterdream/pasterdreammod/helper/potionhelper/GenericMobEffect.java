package com.pasterdream.pasterdreammod.helper.potionhelper;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.List;

public record GenericMobEffect(MobEffect effectType, int level, int time)
{
    public static GenericMobEffect fromMobEffectInstanceToGenericMobEffect(MobEffectInstance mobEffectInstance)
    {
        return new GenericMobEffect(mobEffectInstance.getEffect(), mobEffectInstance.getAmplifier(), mobEffectInstance.getDuration());
    }

    public static List<GenericMobEffect> fromListMobEffectInstanceToListGenericMobEffect(List<MobEffectInstance> mobEffectInstanceList)
    {
        List<GenericMobEffect> effectList = new ArrayList<>();
        for (MobEffectInstance effectInstance : mobEffectInstanceList)
        {
            effectList.add(GenericMobEffect.fromMobEffectInstanceToGenericMobEffect(effectInstance));
        }
        return effectList;
    }

    public static MobEffectInstance fromGenericMobEffectToEffectInstance(GenericMobEffect genericMobEffect)
    {
        return new MobEffectInstance(genericMobEffect.effectType(), genericMobEffect.time(), genericMobEffect.level());
    }
}
