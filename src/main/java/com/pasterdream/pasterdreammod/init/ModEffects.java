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

    public static final RegistryObject<MobEffect> TITANIUM_ARMOR_BUFF =
            EFFECTS.register("titanium_armor_buff", TitaniumArmorBuffEffect::new);
    public static final RegistryObject<MobEffect> SCULK_ARMOR_BUFF =
            EFFECTS.register("sculk_armor_buff", SculkArmorBuffEffect::new);
    public static final RegistryObject<MobEffect> DYEDREAM_ARMOR_BUFF =
            EFFECTS.register("dyedream_armor_buff", DyedreamArmorBuffEffect::new);
    public static final RegistryObject<MobEffect> DYEDREAM_UP_BUFF =
            EFFECTS.register("dyedream_up_buff", DyedreamUpBuffEffect::new);
    public static final RegistryObject<MobEffect> DYEDREAM_PERFUME_BUFF =
            EFFECTS.register("dyedream_perfume_buff", DyedreamPerfumeBuffEffect::new);
    public static final RegistryObject<MobEffect> GOLDENROD_TEA_BUFF =
            EFFECTS.register("goldenrod_tea_buff", GoldenrodTeaBuffEffect::new);
    public static final RegistryObject<MobEffect> EVASION_BUFF =
            EFFECTS.register("evasion_buff", EvasionBuffEffect::new);
    public static final RegistryObject<MobEffect> BLINK_COOLDOWN =
            EFFECTS.register("blink_cooldown", BlinkCooldownEffect::new);
    public static final RegistryObject<MobEffect> SNOW_VOW_BUFF =
            EFFECTS.register("snow_vow_buff", SnowVowBuffEffect::new);
    public static final RegistryObject<MobEffect> CHEER_UP_BUFF =
            EFFECTS.register("cheer_up_buff", CheerUpBuffEffect::new);
    public static final RegistryObject<MobEffect> LETHARGY_BUFF =
            EFFECTS.register("lethargy_buff", LethargyBuffEffect::new);
    public static final RegistryObject<MobEffect> TRANCE_BUFF =
            EFFECTS.register("trance_buff", TranceBuffEffect::new);
    public static final RegistryObject<MobEffect> INSAND_BUFF =
            EFFECTS.register("insand_buff", InsandBuffEffect::new);
    public static final RegistryObject<MobEffect> COOK_BUFF =
            EFFECTS.register("cook_buff", CookBuffEffect::new);
    public static final RegistryObject<MobEffect> DREAM_WISH_BUFF =
            EFFECTS.register("dream_wish_buff", DreamWishBuffEffect::new);
    public static final RegistryObject<MobEffect> CECILIA_BLESSING_BUFF =
            EFFECTS.register("cecilia_blessing_buff", CeciliaBlessingBuff::new);
    public static final RegistryObject<MobEffect> REST_BUFF =
            EFFECTS.register("rest_buff", RestBuffEffect::new);
    public static final RegistryObject<MobEffect> DREAM_HARP_OF_WANDERER_BUFF =
            EFFECTS.register("dream_harp_of_wanderer_buff", DreamHarpOfWandererBuffEffect::new);
    public static final RegistryObject<MobEffect> COUNTER_ATTACK_BUFF =
            EFFECTS.register("counter_attack_buff", CounterAttackBuffEffect::new);
    public static final RegistryObject<MobEffect> MEMENTO_BUFF =
            EFFECTS.register("memento_buff", MementoBuffEffect::new);
    public static final RegistryObject<MobEffect> GUARD_BUFF =
            EFFECTS.register("guard_buff", GuardBuffEffect::new);
    public static final RegistryObject<MobEffect> RAPID_REACTION_BUFF =
            EFFECTS.register("rapid_reaction_buff", RapidReactionEffect::new);
    public static final RegistryObject<MobEffect> HOLY_GRAIL_BUFF =
            EFFECTS.register("holy_grail_buff", HolyGrailEffect::new);
    public static final RegistryObject<MobEffect> CONFUSION_BUFF =
            EFFECTS.register("confusion_buff", ConfusionBuffEffect::new);
    public static final RegistryObject<MobEffect> FLARE_UP_BUFF =
            EFFECTS.register("flare_up_buff", FlareupBuffEffect::new);
    public static final RegistryObject<MobEffect> CONFLICT_MARK =
            EFFECTS.register("conflict_mark", ConflictMarkEffect::new);
    public static final RegistryObject<MobEffect> WAR_FLAG_BUFF =
            EFFECTS.register("war_flag_buff", WarFlagBuffEffect::new);
    public static final RegistryObject<MobEffect> BIND_BUFF =
            EFFECTS.register("bind_buff", BindBuffEffect::new);
    public static final RegistryObject<MobEffect> SHADOW_SILENCE_BUFF =
            EFFECTS.register("shadow_silence_buff", ShadowSilenceBuffEffect::new);
    public static final RegistryObject<MobEffect> CALAIS_SPICE_BOTTLE_BUFF =
            EFFECTS.register("calais_spice_bottle_buff", CalaisSpiceBottleBuff::new);
    public static final RegistryObject<MobEffect> RESTRAINMOVE_BLOCK_BUFF =
            EFFECTS.register("restrainmove_block_buff", RestrainmoveBlockBuffEffect::new);
    public static final RegistryObject<MobEffect> OPPRESSION_BUFF =
            EFFECTS.register("oppression_buff", OppressionBuffEffect::new);

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
