package com.pasterdream.pasterdreammod.command.san;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.network.san.LowSanConfigSyncPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class LowSanEffect {

    private static final String PREFIX = "command.pasterdream.lowsan.";

    private static Component onOff(boolean value) {
        return Component.translatable(PREFIX + (value ? "enabled" : "disabled"));
    }

    private static void syncToAll() {
        LowSanConfigSyncPacket.syncToAll(Config.lowSanOverlay, Config.lowSanJitter, Config.lowSanSound);
    }

    public static int setOverlay(CommandContext<CommandSourceStack> context) {
        boolean value = BoolArgumentType.getBool(context, "boolean");
        Config.lowSanOverlay = value;
        syncToAll();
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "overlay.set", onOff(value)), true);
        return 1;
    }

    public static int getOverlay(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "overlay.get",
                Component.translatable(PREFIX + "overlay"), onOff(Config.lowSanOverlay)), true);
        return 1;
    }

    public static int setJitter(CommandContext<CommandSourceStack> context) {
        boolean value = BoolArgumentType.getBool(context, "boolean");
        Config.lowSanJitter = value;
        syncToAll();
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "jitter.set", onOff(value)), true);
        return 1;
    }

    public static int getJitter(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "jitter.get",
                Component.translatable(PREFIX + "jitter"), onOff(Config.lowSanJitter)), true);
        return 1;
    }

    public static int setSound(CommandContext<CommandSourceStack> context) {
        boolean value = BoolArgumentType.getBool(context, "boolean");
        Config.lowSanSound = value;
        syncToAll();
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "sound.set", onOff(value)), true);
        return 1;
    }

    public static int getSound(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "sound.get",
                Component.translatable(PREFIX + "sound"), onOff(Config.lowSanSound)), true);
        return 1;
    }
}
