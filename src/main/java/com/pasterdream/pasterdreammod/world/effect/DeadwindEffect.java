package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * 逆风 buff：在风之旅途维度逆风行走时获得，降低移速、缩短闪烁距离，增加闪烁冷却。
 * 与顺风 buff 互斥。
 */
public class DeadwindEffect extends MobEffect {

    private static final UUID SPEED_UUID = UUID.fromString("ec261013-84a4-41b1-99ca-89754fd5f1e8");
    private static final UUID RANGE_UUID = UUID.fromString("8c15c7dd-9ea5-4d86-a4ae-b4d10a7a7b36");
    private static final UUID CD_UUID = UUID.fromString("7c9687e5-912e-42e5-a473-c2ada8758c36");

    public DeadwindEffect() {
        super(MobEffectCategory.HARMFUL, -1666709);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        entity.removeEffect(ModEffects.TAILWIND.get());

        double speed = amplifier == 0 ? -0.02 : -0.03;
        double range = amplifier == 0 ? -0.3 : -0.5;
        double cd = amplifier == 0 ? 1.5 : 2.0;

        addModifier(entity, Attributes.MOVEMENT_SPEED, SPEED_UUID, speed, AttributeModifier.Operation.ADDITION);
        addModifier(entity, ModAttributes.BLINK_RANGE.get(), RANGE_UUID, range, AttributeModifier.Operation.ADDITION);
        // 原作数值：MULTIPLY_BASE 语义为 ×(1+amount)
        addModifier(entity, ModAttributes.BLINK_CD.get(), CD_UUID, cd, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        removeModifier(entity, Attributes.MOVEMENT_SPEED, SPEED_UUID);
        removeModifier(entity, ModAttributes.BLINK_RANGE.get(), RANGE_UUID);
        removeModifier(entity, ModAttributes.BLINK_CD.get(), CD_UUID);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInGui(MobEffectInstance effect) {
                return false;
            }
        });
    }

    private static void addModifier(LivingEntity entity, Attribute attribute, UUID uuid, double amount, AttributeModifier.Operation operation) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance != null && instance.getModifier(uuid) == null) {
            instance.addTransientModifier(new AttributeModifier(uuid, "deadwind", amount, operation));
        }
    }

    private static void removeModifier(LivingEntity entity, Attribute attribute, UUID uuid) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance != null) {
            instance.removeModifier(uuid);
        }
    }
}
