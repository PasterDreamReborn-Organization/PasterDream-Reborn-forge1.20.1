package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, PasterDreamMod.MOD_ID);

    public static final RegistryObject<SoundEvent> SHARP_MELT_DREAM_SWORD_USE =
            SOUNDS.register("sharp_melt_dream_sword_use", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sharp_melt_dream_sword_use")));
    public static final RegistryObject<SoundEvent> SHARP_MELT_DREAM_SWORD_HIT =
            SOUNDS.register("sharp_melt_dream_sword_hit", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sharp_melt_dream_sword_hit")));
    public static final RegistryObject<SoundEvent> INFERNO_SWORD_SKILL =
            SOUNDS.register("inferno_sword_skill", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "inferno_sword_skill")));

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
