package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, PasterDreamMod.MOD_ID);

    public static final RegistryObject<Potion> LUCK = POTIONS.register("luck",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 3600, 0)));
    public static final RegistryObject<Potion> LONG_LUCK = POTIONS.register("long_luck",
            () -> new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 9600, 0)));
    public static final RegistryObject<Potion> STRONG_LUCK = POTIONS.register("strong_luck",
            () -> new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 1800, 1)));

    // 夜视药水（15 分钟 = 18000 tick）
    public static final RegistryObject<Potion> SUPER_LONG_NIGHT_VISION = POTIONS.register("super_long_night_vision",
            () -> new Potion(new MobEffectInstance(MobEffects.NIGHT_VISION, 18000, 0)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
