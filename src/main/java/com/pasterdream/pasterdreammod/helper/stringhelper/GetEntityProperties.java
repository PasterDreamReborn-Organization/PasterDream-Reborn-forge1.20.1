package com.pasterdream.pasterdreammod.helper.stringhelper;

import com.pasterdream.pasterdreammod.helper.potionhelper.PotionHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import java.util.Collection;

public class GetEntityProperties
{
    public static String getEntityProperties(Entity entity)
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(Component.translatable("message.pasterdream.实体名称:").getString()).append(entity.getName().getString()).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.实体ID:").getString()).append(entity.getId()).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.实体类型:").getString()).append(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType())).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.实体UUID:").getString()).append(entity.getUUID()).append('\n');

        if(entity instanceof LivingEntity livingEntity)
        {
            stringBuilder.append(Component.translatable("message.pasterdream.最大生命值:").getString()).append(livingEntity.getMaxHealth()).append('\n');
            stringBuilder.append(Component.translatable("message.pasterdream.当前生命值:").getString()).append(livingEntity.getHealth()).append('\n');
            stringBuilder.append(Component.translatable("message.pasterdream.伤害吸收:").getString()).append(livingEntity.getAbsorptionAmount()).append('\n');
            stringBuilder.append(Component.translatable("message.pasterdream.生物效果:").getString()).append('\n');
            Collection<MobEffectInstance> effects = livingEntity.getActiveEffects();
            for (MobEffectInstance effect : effects)
            {
                stringBuilder.append(PotionHelper.formatTime(effect.getDuration())).append(Component.translatable(effect.getDescriptionId()).getString()).append(effect.getAmplifier()).append('\n');
            }
        }

        return stringBuilder.toString();
    }
}
