package com.pasterdream.pasterdreammod.command.san;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.pasterdream.pasterdreammod.init.ModGameRules;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

import java.util.Map;

public class ShadowDifficulty {

    private static final Map<String, Integer> NAME_TO_TIER = Map.of(
            "very_easy", 0,
            "easy", 1,
            "normal", 2,
            "hard", 3
    );
    private static final String[] TIER_NAMES = {"Very Easy", "Easy", "Normal", "Hard"};
    private static final String[] TIER_NAMES_ZH = {"极简单", "简单", "普通", "困难"};

    public static final SuggestionProvider<CommandSourceStack> TIER_SUGGESTIONS =
            (ctx, builder) -> SharedSuggestionProvider.suggest(
                    new String[]{"very_easy", "easy", "normal", "hard"}, builder);

    /** 根据字符串获取对应的难度等级，找不到返回 -1 */
    public static int tierFromName(String name) {
        return NAME_TO_TIER.getOrDefault(name, -1);
    }

    public static int set(CommandContext<CommandSourceStack> ctx, String name) {
        int tier = tierFromName(name);
        var server = ctx.getSource().getServer();
        server.getGameRules().getRule(ModGameRules.SHADOW_DIFFICULTY).set(tier, server);
        ctx.getSource().sendSuccess(() -> Component.translatable(
                "gamerule.pasterdream.shadowDifficulty.set",
                Component.literal(name + " (" + TIER_NAMES_ZH[tier] + ")")), true);
        return tier;
    }

    public static int get(CommandContext<CommandSourceStack> ctx) {
        int tier = ctx.getSource().getServer().getGameRules()
                .getInt(ModGameRules.SHADOW_DIFFICULTY);
        ctx.getSource().sendSuccess(() -> Component.translatable(
                "gamerule.pasterdream.shadowDifficulty.get",
                Component.literal(TIER_NAMES[tier] + " (" + TIER_NAMES_ZH[tier] + ")")), true);
        return tier;
    }
}
