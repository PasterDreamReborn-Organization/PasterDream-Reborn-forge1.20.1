package com.pasterdream.pasterdreammod.command.san;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.pasterdream.pasterdreammod.init.ModGameRules;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

public class ShadowDifficulty {

    private static final String[] TIER_NAMES = {"极简单", "简单", "普通", "困难"};
    public static final SuggestionProvider<CommandSourceStack> TIER_SUGGESTIONS =
            (ctx, builder) -> SharedSuggestionProvider.suggest(
                    new String[]{"0", "1", "2", "3",
                            "0-极简单", "1-简单", "2-普通", "3-困难"}, builder);

    public static int set(CommandContext<CommandSourceStack> ctx) {
        int tier = IntegerArgumentType.getInteger(ctx, "tier");
        var server = ctx.getSource().getServer();
        server.getGameRules().getRule(ModGameRules.SHADOW_DIFFICULTY).set(tier, server);
        ctx.getSource().sendSuccess(() -> Component.translatable(
                "gamerule.pasterdream.shadowDifficulty.set",
                Component.literal(TIER_NAMES[tier])), true);
        return tier;
    }

    public static int get(CommandContext<CommandSourceStack> ctx) {
        int tier = ctx.getSource().getServer().getGameRules()
                .getInt(ModGameRules.SHADOW_DIFFICULTY);
        ctx.getSource().sendSuccess(() -> Component.translatable(
                "gamerule.pasterdream.shadowDifficulty.get",
                Component.literal(tier + "-" + TIER_NAMES[tier])), true);
        return tier;
    }
}
