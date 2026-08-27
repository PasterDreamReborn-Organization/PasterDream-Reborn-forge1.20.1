package com.pasterdream.pasterdreammod.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Rarity;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class ModRarities {

    public static final Rarity COMMON    = Rarity.create("pasterdream_common",   ChatFormatting.WHITE);
    public static final Rarity EXCELLENT = Rarity.create("pasterdream_excellent", ChatFormatting.GREEN);
    public static final Rarity SUPERIOR  = Rarity.create("pasterdream_superior",  ChatFormatting.AQUA);
    public static final Rarity MASTER    = Rarity.create("pasterdream_master",    ChatFormatting.BLUE);
    public static final Rarity ANCIENT   = Rarity.create("pasterdream_ancient",   ChatFormatting.DARK_PURPLE);
    public static final Rarity EPIC      = Rarity.create("pasterdream_epic",      ChatFormatting.LIGHT_PURPLE);
    public static final Rarity LEGENDARY = Rarity.create("pasterdream_legendary", ChatFormatting.RED);
    public static final Rarity MIRACLE   = Rarity.create("pasterdream_miracle",   ChatFormatting.GOLD);

    private static final Map<String, Rarity> BY_NAME = Map.ofEntries(
        Map.entry("common", COMMON),
        Map.entry("excellent", EXCELLENT),
        Map.entry("superior", SUPERIOR),
        Map.entry("master", MASTER),
        Map.entry("ancient", ANCIENT),
        Map.entry("epic", EPIC),
        Map.entry("legendary", LEGENDARY),
        Map.entry("miracle", MIRACLE)
    );

    private static final Map<Rarity, Integer> TIER_MAP = Map.ofEntries(
        Map.entry(COMMON,    1),
        Map.entry(EXCELLENT, 2),
        Map.entry(SUPERIOR,  3),
        Map.entry(MASTER,    4),
        Map.entry(ANCIENT,   5),
        Map.entry(EPIC,      6),
        Map.entry(LEGENDARY, 7),
        Map.entry(MIRACLE,   8)
    );

    public static Component qualityTooltip(Rarity rarity) {
        Integer tier = TIER_MAP.get(rarity);
        if (tier == null) {
            return Component.empty();
        }
        MutableComponent stars = Component.literal("★".repeat(tier));
        return Component.translatable("tooltip.pasterdream.quality",
            Component.translatable(rarityTranslationKey(rarity)),
            stars
        ).withStyle(rarity.getStyleModifier());
    }

    public static Rarity byName(String name) {
        if (name == null) {
            return null;
        }
        return BY_NAME.get(name.toLowerCase(Locale.ROOT).replace("pasterdream_", ""));
    }

    public static Integer tierOf(Rarity rarity) {
        return TIER_MAP.get(rarity);
    }

    public static List<String> names() {
        return TIER_MAP.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .map(entry -> entry.getKey().toString().replace("pasterdream_", ""))
            .toList();
    }

    private static String rarityTranslationKey(Rarity rarity) {
        // Rarity.create("pasterdream_common") → rarity.pasterdream.common
        return rarity.toString().replace("pasterdream_", "rarity.pasterdream.");
    }

    private ModRarities() {}
}
