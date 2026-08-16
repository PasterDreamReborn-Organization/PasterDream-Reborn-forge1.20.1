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
 * 顺风 buff：在风之旅途维度顺风行走时获得，提升移速、闪烁距离，降低闪烁冷却。
 * 与逆风 buff 互斥。
 */
public class TailwindBuffEffect extends MobEffect {

    private static final UUID SPEED_UUID = UUID.fromString("a70291bb-c451-4b26-b160-55f7b12c32ed");
    private static final UUID RANGE_UUID = UUID.fromString("78c6cb5b-3f93-4e1c-a22f-6a3f2a3ed1ff");
    private static final UUID CD_UUID = UUID.fromString("5aa8de78-2c53-4e9a-9205-87e71fa62ea1");

    public TailwindBuffEffect() {
        super(MobEffectCategory.BENEFICIAL, -7087682);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        entity.removeEffect(ModEffects.DEADWIND_BUFF.get());

        double speed = amplifier == 0 ? 0.03 : 0.04;
        double range = amplifier == 0 ? 1.0 : 1.5;
        double cd = amplifier == 0 ? 0.7 : 0.6;

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
            instance.addTransientModifier(new AttributeModifier(uuid, "tailwind_buff", amount, operation));
        }
    }

    private static void removeModifier(LivingEntity entity, Attribute attribute, UUID uuid) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance != null) {
            instance.removeModifier(uuid);
        }
    }
}
