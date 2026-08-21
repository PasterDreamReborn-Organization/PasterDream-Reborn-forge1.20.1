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

    public static final RegistryObject<SoundEvent> SWORD_SLASH =
            SOUNDS.register("sword_slash", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sword_slash")));
    public static final RegistryObject<SoundEvent> SKILL_HIT =
            SOUNDS.register("skill_hit", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "skill_hit")));
    public static final RegistryObject<SoundEvent> SKILL_HIT_HEAVY =
            SOUNDS.register("skill_hit_heavy", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "skill_hit_heavy")));
    public static final RegistryObject<SoundEvent> COIN_DROP =
            SOUNDS.register("coin_drop", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coin_drop")));
    public static final RegistryObject<SoundEvent> COIN_EXCHANGE =
            SOUNDS.register("coin_exchange", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coin_exchange")));
    public static final RegistryObject<SoundEvent> SWORD_AWAKEN =
            SOUNDS.register("sword_awaken", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sword_awaken")));
    public static final RegistryObject<SoundEvent> PLIERS =
            SOUNDS.register("pliers", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "pliers")));

    public static final RegistryObject<SoundEvent> DOLL = SOUNDS.register("doll", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "doll")));
    public static final RegistryObject<SoundEvent> DYEDREAM_CRACK = SOUNDS.register("dyedream_crack", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_crack")));
    public static final RegistryObject<SoundEvent> AWAKE = SOUNDS.register("awake", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "awake")));
    public static final RegistryObject<SoundEvent> EVASION = SOUNDS.register("evasion", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "evasion")));
    public static final RegistryObject<SoundEvent> LOSE_MIND = SOUNDS.register("lose_mind", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lose_mind")));

    public static final RegistryObject<SoundEvent> SWEET_DREAM_MUSIC = SOUNDS.register("sweet_dream_music", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sweet_dream_music")));
    public static final RegistryObject<SoundEvent> SNOWFALL_DREAM_MUSIC = SOUNDS.register("snowfall_dream_music", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "snowfall_dream_music")));
    public static final RegistryObject<SoundEvent> THERMAL_DAGGER = SOUNDS.register("thermal_dagger", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "thermal_dagger")));
    public static final RegistryObject<SoundEvent> SWORD_WAVE = SOUNDS.register("sword_wave", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sword_wave")));
    public static final RegistryObject<SoundEvent> FOX_FIRE = SOUNDS.register("fox_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fox_fire")));
    public static final RegistryObject<SoundEvent> LIFE_CRYSTAL = SOUNDS.register("life_crystal", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "life_crystal")));
    public static final RegistryObject<SoundEvent> MELT_DREAM_CRYSTAL_CHEST_OPEN_RARE = SOUNDS.register("melt_dream_crystal_chest_open_rare", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_crystal_chest_open_rare")));
    public static final RegistryObject<SoundEvent> MELT_DREAM_CRYSTAL_CHEST_OPEN_LEGEND = SOUNDS.register("melt_dream_crystal_chest_open_legend", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_crystal_chest_open_legend")));
    public static final RegistryObject<SoundEvent> DREAM_HARP_OF_WANDERER = SOUNDS.register("dream_harp_of_wanderer", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_harp_of_wanderer")));
    public static final RegistryObject<SoundEvent> DING = SOUNDS.register("ding", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "ding")));
    public static final RegistryObject<SoundEvent> DREAM_ACCUMULATOR = SOUNDS.register("dream_accumulator", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_accumulator")));
    public static final RegistryObject<SoundEvent> ZIPPER = SOUNDS.register("zipper", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "zipper")));
    public static final RegistryObject<SoundEvent> WIND_CHIME = SOUNDS.register("wind_chime", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_chime")));
    public static final RegistryObject<SoundEvent> SHADOW_ROAR = SOUNDS.register("shadow_roar", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_roar")));
    public static final RegistryObject<SoundEvent> TERRORBEAK_ROAR = SOUNDS.register("terrorbeak_roar", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "terrorbeak_roar")));
    public static final RegistryObject<SoundEvent> GHOST_WAIL = SOUNDS.register("ghost_wail", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "ghost_wail")));
    public static final RegistryObject<SoundEvent> SQUEAL_WAVE = SOUNDS.register("squeal_wave", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "squeal_wave")));
    public static final RegistryObject<SoundEvent> BEETLE_ATTACK = SOUNDS.register("beetle_attack", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "beetle_attack")));
    public static final RegistryObject<SoundEvent> BEETLE_SKILL = SOUNDS.register("beetle_skill", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "beetle_skill")));
    public static final RegistryObject<SoundEvent> WHITE_SWORD_RAIN = SOUNDS.register("white_sword_rain", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "white_sword_rain")));
    public static final RegistryObject<SoundEvent> SHADOW_SWORD = SOUNDS.register("shadow_sword", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_sword")));
    public static final RegistryObject<SoundEvent> SHADOW_VORTEX = SOUNDS.register("shadow_vortex", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_vortex")));
    public static final RegistryObject<SoundEvent> SHADOW_VORTEX_BOOK = SOUNDS.register("shadow_vortex_book", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_vortex_book")));
    public static final RegistryObject<SoundEvent> AARONCOS_SPAWN = SOUNDS.register("aaroncos_spawn", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_spawn")));
    public static final RegistryObject<SoundEvent> AARONCOS_MUSIC = SOUNDS.register("aaroncos_music", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_music")));
    public static final RegistryObject<SoundEvent> STONE_SHATTER = SOUNDS.register("stone_shatter", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "stone_shatter")));
    public static final RegistryObject<SoundEvent> STONE_CRACK = SOUNDS.register("stone_crack", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "stone_crack")));
    public static final RegistryObject<SoundEvent> SHADOW_OMINOUS = SOUNDS.register("shadow_ominous", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ominous")));
    public static final RegistryObject<SoundEvent> SHADOW_MUSIC = SOUNDS.register("shadow_music", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_music")));
    public static final RegistryObject<SoundEvent> SHADOW_DOOR = SOUNDS.register("shadow_door", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_door")));
    public static final RegistryObject<SoundEvent> LIGHTNING_CHARGE = SOUNDS.register("lightning_charge", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lightning_charge")));
    public static final RegistryObject<SoundEvent> FROZEN_SHOCK = SOUNDS.register("frozen_shock", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "frozen_shock")));
    public static final RegistryObject<SoundEvent> INFERNO_IMPACT = SOUNDS.register("inferno_impact", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "inferno_impact")));
    public static final RegistryObject<SoundEvent> SHADOW_HAND_LANTERN = SOUNDS.register("shadow_hand_lantern", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_hand_lantern")));
    public static final RegistryObject<SoundEvent> SHADOW_TRAP = SOUNDS.register("shadow_trap", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_trap")));
    public static final RegistryObject<SoundEvent> WIND_KNIGHT_SKILL = SOUNDS.register("wind_knight_skill", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_knight_skill")));
    public static final RegistryObject<SoundEvent> THUNDERCLOUD_ATTACK = SOUNDS.register("thundercloud_attack", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "thundercloud_attack")));
    public static final RegistryObject<SoundEvent> BREEZE_WIND = SOUNDS.register("breeze_wind", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "breeze_wind")));
    public static final RegistryObject<SoundEvent> WIND_JOURNEY = SOUNDS.register("wind_journey", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey")));
    public static final RegistryObject<SoundEvent> BONE_WING_FIRE_BALL = SOUNDS.register("bone_wing_fire_ball", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "bone_wing_fire_ball")));

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}