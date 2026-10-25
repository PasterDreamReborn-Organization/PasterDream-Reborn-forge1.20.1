package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, PasterDreamMod.MOD_ID);

    public static final RegistryObject<Attribute> BLINK_COOLDOWN_REDUCTION =
            ATTRIBUTES.register("blink_cooldown_reduction", () -> new RangedAttribute(
                    "attribute.pasterdream.blink_cooldown_reduction", 0.0, -10.0, 1.0).setSyncable(true));
    public static final RegistryObject<Attribute> BLINK_CONSUME =
            ATTRIBUTES.register("blink_consume", () -> new RangedAttribute(
                    "attribute.pasterdream.blink_consume", 1.0, 0.0, 10.0).setSyncable(true));
    public static final RegistryObject<Attribute> BLINK_RANGE =
            ATTRIBUTES.register("blink_range", () -> new RangedAttribute(
                    "attribute.pasterdream.blink_range", 1.0, 0.0, 10.0).setSyncable(true));

    public static final RegistryObject<Attribute> SKILL_COOLDOWN_REDUCTION =
            ATTRIBUTES.register("skill_cooldown_reduction", () -> new RangedAttribute(
                    "attribute.pasterdream.skill_cooldown_reduction", 0.0, -10.0, 1.0).setSyncable(true));
    public static final RegistryObject<Attribute> SKILL_DAMAGE_RATE =
            ATTRIBUTES.register("skill_damage_rate", () -> new RangedAttribute(
                    "attribute.pasterdream.skill_damage_rate", 1.0, 0.0, 100.0).setSyncable(true));
    public static final RegistryObject<Attribute> MAGIC_DAMAGE_RATE =
            ATTRIBUTES.register("magic_damage_rate", () -> new RangedAttribute(
                    "attribute.pasterdream.magic_damage_rate", 1.0, 0.0, 100.0).setSyncable(true));

    public static final RegistryObject<Attribute> SAN_VARIABILITY =
            ATTRIBUTES.register("san_variability", () -> new RangedAttribute(
                    "attribute.pasterdream.san_variability", 0.0, -120000.0, 120000.0).setSyncable(true));

    /** 理智值上限的装备加成载体：基础值 0，由护甲等装备以属性修饰器叠加。 */
    public static final RegistryObject<Attribute> MAX_SAN_EXTRA =
            ATTRIBUTES.register("max_san_extra", () -> new RangedAttribute(
                    "attribute.pasterdream.max_san_extra", 0.0, -100000.0, 100000.0).setSyncable(true));
    /** 融梦能量上限的装备加成载体：基础值 0，由护甲等装备以属性修饰器叠加。 */
    public static final RegistryObject<Attribute> MAX_MELT_DREAM_ENERGY_EXTRA =
            ATTRIBUTES.register("max_melt_dream_energy_extra", () -> new RangedAttribute(
                    "attribute.pasterdream.max_melt_dream_energy_extra", 0.0, -100000.0, 100000.0).setSyncable(true));
    /** 融梦能量回复率（每分钟变化量）：基础值 0，由护甲/饰品等装备以属性修饰器叠加。 */
    public static final RegistryObject<Attribute> MELT_DREAM_VARIABILITY =
            ATTRIBUTES.register("melt_dream_variability", () -> new RangedAttribute(
                    "attribute.pasterdream.melt_dream_variability", 0.0, -120000.0, 120000.0).setSyncable(true));

    /**
     * 箭矢速度：基础值 0，值即额外倍率，发射弹射物时速度按 (1 + 值) 缩放。
     */
    public static final RegistryObject<Attribute> ARROW_VELOCITY =
            ATTRIBUTES.register("arrow_velocity", () -> new RangedAttribute(
                    "attribute.pasterdream.arrow_velocity", 0.0, -1024.0, 1024.0).setSyncable(true));

    /**
     * 拉弓与弩装填时间缩减：基础值 0，正值 = 缩减，{@code 0.25} 表示使用时长 ×0.75。
     * 上限 0.95 保证换算出的速度倍率 1/(1-值) 始终有限。
     */
    public static final RegistryObject<Attribute> RANGED_CHARGE_TIME_REDUCTION =
            ATTRIBUTES.register("ranged_charge_time_reduction", () -> new RangedAttribute(
                    "attribute.pasterdream.ranged_charge_time_reduction", 0.0, -0.95, 0.95).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }

    @SubscribeEvent
    public static void addAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, BLINK_COOLDOWN_REDUCTION.get());
        event.add(EntityType.PLAYER, BLINK_CONSUME.get());
        event.add(EntityType.PLAYER, BLINK_RANGE.get());
        event.add(EntityType.PLAYER, SKILL_COOLDOWN_REDUCTION.get());
        event.add(EntityType.PLAYER, SKILL_DAMAGE_RATE.get());
        event.add(EntityType.PLAYER, MAGIC_DAMAGE_RATE.get());
        event.add(EntityType.PLAYER, SAN_VARIABILITY.get());
        event.add(EntityType.PLAYER, MAX_SAN_EXTRA.get());
        event.add(EntityType.PLAYER, MAX_MELT_DREAM_ENERGY_EXTRA.get());
        event.add(EntityType.PLAYER, MELT_DREAM_VARIABILITY.get());
        event.add(EntityType.PLAYER, ARROW_VELOCITY.get());
        event.add(EntityType.PLAYER, RANGED_CHARGE_TIME_REDUCTION.get());
    }
}
