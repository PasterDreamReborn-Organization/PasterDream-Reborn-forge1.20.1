package com.pasterdream.pasterdreammod.command.san;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.pasterdream.pasterdreammod.helper.ShadowDifficultyHelper;
import com.pasterdream.pasterdreammod.init.ModGameRules;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;

public class ShadowDifficulty {

    private static final Map<String, Integer> NAME_TO_TIER = Map.of(
            "very_easy", 0,
            "easy", 1,
            "normal", 2,
            "hard", 3
    );

    public static final SuggestionProvider<CommandSourceStack> TIER_SUGGESTIONS =
            (ctx, builder) -> SharedSuggestionProvider.suggest(
                    new String[]{"very_easy", "easy", "normal", "hard"}, builder);

    public static int tierFromName(String name) {
        return NAME_TO_TIER.getOrDefault(name, -1);
    }

    /** 生成带翻译的难度等级显示文本 */
    public static MutableComponent tierDisplay(int tier) {
        return Component.translatable("command.pasterdream.shadowDifficulty.tier." + tier);
    }

    /** 设置默认玩家暗影难度 gamerule (playerShadowDifficulty) */
    public static int setPlayerDefault(CommandContext<CommandSourceStack> ctx, String name) {
        int tier = tierFromName(name);
        var server = ctx.getSource().getServer();
        server.getGameRules().getRule(ModGameRules.PLAYER_SHADOW_DIFFICULTY).set(tier, server);
        ctx.getSource().sendSuccess(() -> Component.translatable(
                "command.pasterdream.shadowDifficulty.set.playerDefault",
                tierDisplay(tier)), true);
        return tier;
    }

    /** 设置世界暗影难度 gamerule (shadowDifficulty) */
    public static int setWorld(CommandContext<CommandSourceStack> ctx, String name) {
        int tier = tierFromName(name);
        var server = ctx.getSource().getServer();
        server.getGameRules().getRule(ModGameRules.SHADOW_DIFFICULTY).set(tier, server);
        ctx.getSource().sendSuccess(() -> Component.translatable(
                "command.pasterdream.shadowDifficulty.set.world",
                tierDisplay(tier)), true);
        return tier;
    }

    /** 设置指定玩家的暗影难度覆盖 */
    public static int setForPlayer(CommandContext<CommandSourceStack> ctx, String name) throws CommandSyntaxException {
        int tier = tierFromName(name);
        ServerPlayer target = EntityArgument.getPlayer(ctx, "player");
        ShadowDifficultyHelper.setPlayerDifficulty(target, tier);
        ctx.getSource().sendSuccess(() -> Component.translatable(
                "command.pasterdream.shadowDifficulty.set.forPlayer",
                target.getName(), tierDisplay(tier)), true);
        return tier;
    }

    public static int get(CommandContext<CommandSourceStack> ctx) {
        var rules = ctx.getSource().getServer().getGameRules();
        int worldTier = rules.getInt(ModGameRules.SHADOW_DIFFICULTY);
        int playerDefaultTier = rules.getInt(ModGameRules.PLAYER_SHADOW_DIFFICULTY);

        if (ctx.getSource().getEntity() instanceof ServerPlayer player) {
            int playerTier = ShadowDifficultyHelper.getDifficulty(player);
            boolean hasOverride = ShadowDifficultyHelper.hasPlayerOverride(player);
            MutableComponent source = Component.translatable(
                    hasOverride ? "command.pasterdream.shadowDifficulty.source.personal"
                            : "command.pasterdream.shadowDifficulty.source.playerDefault");
            ctx.getSource().sendSuccess(() -> Component.translatable(
                    "command.pasterdream.shadowDifficulty.get.playerEffective",
                    player.getName(), tierDisplay(playerTier), source), false);
            ctx.getSource().sendSuccess(() -> Component.translatable(
                    "command.pasterdream.shadowDifficulty.get.gameruleSummary",
                    tierDisplay(worldTier), tierDisplay(playerDefaultTier)), true);
            return playerTier;
        }

        ctx.getSource().sendSuccess(() -> Component.translatable(
                "command.pasterdream.shadowDifficulty.get.gameruleSummary",
                tierDisplay(worldTier), tierDisplay(playerDefaultTier)), true);
        return worldTier;
    }
}
