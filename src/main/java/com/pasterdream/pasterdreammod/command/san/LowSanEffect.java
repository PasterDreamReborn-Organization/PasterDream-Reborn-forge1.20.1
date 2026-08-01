package com.pasterdream.pasterdreammod.command.san;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.network.san.LowSanConfigSyncPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

public class LowSanEffect {

    private static final String PREFIX = "command.pasterdream.lowsan.";

    private static Component onOff(boolean value) {
        return Component.translatable(PREFIX + (value ? "enabled" : "disabled"));
    }

    private static void persistAndSync(CommandContext<CommandSourceStack> context) {
        ServerLevel overworld = context.getSource().getServer().overworld();
        LowSanWorldData data = LowSanWorldData.get(overworld);
        boolean o = Config.lowSanOverlay;
        boolean j = Config.lowSanJitter;
        boolean s = Config.lowSanSound;
        data.setOverlay(o);
        data.setJitter(j);
        data.setSound(s);
        LowSanConfigSyncPacket.syncToAll(o, j, s);
    }

    public static int setOverlay(CommandContext<CommandSourceStack> context) {
        boolean value = BoolArgumentType.getBool(context, "boolean");
        Config.lowSanOverlay = value;
        persistAndSync(context);
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
        persistAndSync(context);
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
        persistAndSync(context);
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "sound.set", onOff(value)), true);
        return 1;
    }

    public static int getSound(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "sound.get",
                Component.translatable(PREFIX + "sound"), onOff(Config.lowSanSound)), true);
        return 1;
    }

    /** 服务端启动/重载时从世界数据恢复 Config 字段 */
    public static void restoreFromWorld(ServerLevel overworld) {
        LowSanWorldData data = LowSanWorldData.get(overworld);
        Config.lowSanOverlay = data.overlay();
        Config.lowSanJitter = data.jitter();
        Config.lowSanSound = data.sound();
    }
}
