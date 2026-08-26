package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.effect.*;
import com.pasterdream.pasterdreammod.world.item.curio.WarFlagItem;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, PasterDreamMod.MOD_ID);

    public static final RegistryObject<MobEffect> TITANIUM_ARMOR =
            EFFECTS.register("titanium_armor", TitaniumArmorEffect::new);
    public static final RegistryObject<MobEffect> SCULK_ARMOR =
            EFFECTS.register("sculk_armor", SculkArmorEffect::new);
    public static final RegistryObject<MobEffect> DYEDREAM_ARMOR =
            EFFECTS.register("dyedream_armor", DyedreamArmorEffect::new);
    public static final RegistryObject<MobEffect> DYEDREAM_UP =
            EFFECTS.register("dyedream_up", DyedreamUpEffect::new);
    public static final RegistryObject<MobEffect> MELT_DREAM_CRYSTAL_ARMOR =
            EFFECTS.register("melt_dream_crystal_armor", MeltDreamCrystalArmorEffect::new);
    public static final RegistryObject<MobEffect> DYEDREAM_PERFUME =
            EFFECTS.register("dyedream_perfume", DyedreamPerfumeEffect::new);
    public static final RegistryObject<MobEffect> GOLDENROD_TEA =
            EFFECTS.register("goldenrod_tea", GoldenrodTeaEffect::new);
    public static final RegistryObject<MobEffect> EVASION =
            EFFECTS.register("evasion", EvasionEffect::new);
    public static final RegistryObject<MobEffect> BLINK_COOLDOWN =
            EFFECTS.register("blink_cooldown", BlinkCooldownEffect::new);
    public static final RegistryObject<MobEffect> SNOW_VOW =
            EFFECTS.register("snow_vow", SnowVowEffect::new);
    public static final RegistryObject<MobEffect> CHEER_UP =
            EFFECTS.register("cheer_up", CheerUpEffect::new);
    public static final RegistryObject<MobEffect> LETHARGY =
            EFFECTS.register("lethargy", LethargyEffect::new);
    public static final RegistryObject<MobEffect> TRANCE =
            EFFECTS.register("trance", TranceEffect::new);
    public static final RegistryObject<MobEffect> INSANE =
            EFFECTS.register("insane", InsaneEffect::new);
    public static final RegistryObject<MobEffect> COOK =
            EFFECTS.register("cook", CookEffect::new);
    public static final RegistryObject<MobEffect> DREAM_WISH =
            EFFECTS.register("dream_wish", DreamWishEffect::new);
    public static final RegistryObject<MobEffect> CECILIA_BLESSING =
            EFFECTS.register("cecilia_blessing", CeciliaBlessingEffect::new);
    public static final RegistryObject<MobEffect> REST =
            EFFECTS.register("rest", RestEffect::new);
    public static final RegistryObject<MobEffect> DREAM_HARP_OF_WANDERER =
            EFFECTS.register("dream_harp_of_wanderer", DreamHarpOfWandererEffect::new);
    public static final RegistryObject<MobEffect> COUNTER_ATTACK =
            EFFECTS.register("counter_attack", CounterAttackEffect::new);
    public static final RegistryObject<MobEffect> MEMENTO =
            EFFECTS.register("memento", MementoEffect::new);
    public static final RegistryObject<MobEffect> GUARD =
            EFFECTS.register("guard", GuardEffect::new);
    public static final RegistryObject<MobEffect> RAPID_REACTION =
            EFFECTS.register("rapid_reaction", RapidReactionEffect::new);
    public static final RegistryObject<MobEffect> HOLY_GRAIL =
            EFFECTS.register("holy_grail", HolyGrailEffect::new);
    public static final RegistryObject<MobEffect> CONFUSION =
            EFFECTS.register("confusion", ConfusionEffect::new);
    public static final RegistryObject<MobEffect> FLARE_UP =
            EFFECTS.register("flare_up", FlareupEffect::new);
    public static final RegistryObject<MobEffect> CONFLICT_MARK =
            EFFECTS.register("conflict_mark", ConflictMarkEffect::new);
    public static final RegistryObject<MobEffect> WAR_FLAG =
            EFFECTS.register("war_flag", WarFlagEffect::new);
    public static final RegistryObject<MobEffect> BIND =
            EFFECTS.register("bind", BindEffect::new);
    public static final RegistryObject<MobEffect> SHADOW_SILENCE =
            EFFECTS.register("shadow_silence", ShadowSilenceEffect::new);
    public static final RegistryObject<MobEffect> SHADOW_SPYON =
            EFFECTS.register("shadow_spyon", ShadowSpyonEffect::new);
    public static final RegistryObject<MobEffect> CALAIS_SPICE_BOTTLE =
            EFFECTS.register("calais_spice_bottle", CalaisSpiceBottleEffect::new);
    public static final RegistryObject<MobEffect> RESTRAINMOVE_BLOCK =
            EFFECTS.register("restrainmove_block", RestrainmoveBlockEffect::new);
    public static final RegistryObject<MobEffect> OPPRESSION =
            EFFECTS.register("oppression", OppressionEffect::new);
    public static final RegistryObject<MobEffect> HIGHLY_TOXIC =
            EFFECTS.register("highly_toxic", HighlyToxicEffect::new);
    public static final RegistryObject<MobEffect> BERSERK =
            EFFECTS.register("berserk",BerserkEffect::new);
    public static final RegistryObject<MobEffect> FROZEN =
            EFFECTS.register("frozen", FrozenEffect::new);
    public static final RegistryObject<MobEffect> VULNERABILITY =
            EFFECTS.register("vulnerability", VulnerabilityEffect::new);
    public static final RegistryObject<MobEffect> TAILWIND =
            EFFECTS.register("tailwind", TailwindEffect::new);
    public static final RegistryObject<MobEffect> DEADWIND =
            EFFECTS.register("deadwind", DeadwindEffect::new);
    public static final RegistryObject<MobEffect> WINDPROOF =
            EFFECTS.register("windproof", WindproofEffect::new);
    public static final RegistryObject<MobEffect> MISTY_DREAM =
            EFFECTS.register("misty_dream", MistyDreamEffect::new);
    public static final RegistryObject<MobEffect> CLOUD_MIST =
            EFFECTS.register("cloud_mist", CloudMistEffect::new);
    public static final RegistryObject<MobEffect> WIND_RUNNER =
            EFFECTS.register("wind_runner", WindRunnerEffect::new);

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
