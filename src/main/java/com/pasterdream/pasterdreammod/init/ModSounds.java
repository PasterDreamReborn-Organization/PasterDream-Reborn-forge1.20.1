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

    public static final RegistryObject<SoundEvent> SWORD1 =
            SOUNDS.register("sword1", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sword1")));
    public static final RegistryObject<SoundEvent> SKILL1 =
            SOUNDS.register("skill1", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "skill1")));
    public static final RegistryObject<SoundEvent> SKILL2 =
            SOUNDS.register("skill2", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "skill2")));
    public static final RegistryObject<SoundEvent> COIN0 =
            SOUNDS.register("coin0", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coin0")));
    public static final RegistryObject<SoundEvent> COIN1 =
            SOUNDS.register("coin1", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coin1")));
    public static final RegistryObject<SoundEvent> SKILL0 =
            SOUNDS.register("skill0", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "skill0")));
    public static final RegistryObject<SoundEvent> PLIERS0 =
            SOUNDS.register("pliers0", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "pliers0")));

    public static final RegistryObject<SoundEvent> DOLL = SOUNDS.register("doll", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "doll")));
    public static final RegistryObject<SoundEvent> DYEDREAM_CRACK = SOUNDS.register("dyedream_crack", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_crack")));
    public static final RegistryObject<SoundEvent> DREAM0 = SOUNDS.register("dream0", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream0")));
    public static final RegistryObject<SoundEvent> EVASION = SOUNDS.register("evasion", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "evasion")));
    public static final RegistryObject<SoundEvent> LOSEMIND0 = SOUNDS.register("losemind0", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "losemind0")));

    public static final RegistryObject<SoundEvent> SWEET_DREAM_MUSIC = SOUNDS.register("sweet_dream_music", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sweet_dream_music")));
    public static final RegistryObject<SoundEvent> SNOWFALL_DREAM_MUSIC = SOUNDS.register("snowfall_dream_music", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "snowfall_dream_music")));
    public static final RegistryObject<SoundEvent> THERMAL_DAGGER = SOUNDS.register("thermal_dagger", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "thermal_dagger")));
    public static final RegistryObject<SoundEvent> SWORD_WAVE = SOUNDS.register("sword_wave", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sword_wave")));
    public static final RegistryObject<SoundEvent> FOX_FIRE = SOUNDS.register("fox_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fox_fire")));
    public static final RegistryObject<SoundEvent> LIFE_CRYSTAL = SOUNDS.register("life_crystal", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "life_crystal")));
    public static final RegistryObject<SoundEvent> MELT_DREAM_CRYSTAL_CHEST_OPEN_RARE = SOUNDS.register("melt_dream_crystal_chest_open_rare", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_crystal_chest_open_rare")));
    public static final RegistryObject<SoundEvent> MELT_DREAM_CRYSTAL_CHEST_OPEN_LEGEND = SOUNDS.register("melt_dream_crystal_chest_open_legend", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_crystal_chest_open_legend")));
    public static final RegistryObject<SoundEvent> DREAM_HARP_OF_WANDERER = SOUNDS.register("dream_harp_of_wanderer", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_harp_of_wanderer")));
    public static final RegistryObject<SoundEvent> DING_0 = SOUNDS.register("ding_0", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "ding_0")));
    public static final RegistryObject<SoundEvent> DREAM_ACCUMULATOR = SOUNDS.register("dream_accumulator", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_accumulator")));
    public static final RegistryObject<SoundEvent> ZIPPER = SOUNDS.register("zipper", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "zipper")));
    public static final RegistryObject<SoundEvent> WIND_CHIME = SOUNDS.register("wind_chime", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_chime")));
    public static final RegistryObject<SoundEvent> ROAR0 = SOUNDS.register("roar0", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "roar0")));
    public static final RegistryObject<SoundEvent> TERRORBEAK_ROAR = SOUNDS.register("terrorbeak_roar", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "terrorbeak_roar")));
    public static final RegistryObject<SoundEvent> GHOST0 = SOUNDS.register("ghost0", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "ghost0")));
    public static final RegistryObject<SoundEvent> SQUEAL_WAVE = SOUNDS.register("squeal_wave", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "squeal_wave")));
    public static final RegistryObject<SoundEvent> BEETLE_ATTACK = SOUNDS.register("beetle_attack", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "beetle_attack")));
    public static final RegistryObject<SoundEvent> BEETLE_SKILL = SOUNDS.register("beetle_skill", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "beetle_skill")));

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
