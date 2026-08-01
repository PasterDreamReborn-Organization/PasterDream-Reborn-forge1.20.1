package com.pasterdream.pasterdreammod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.pasterdream.pasterdreammod.command.meltdreamenergy.IsNotNeed;
import com.pasterdream.pasterdreammod.command.meltdreamenergy.MaxMeltDreamEnergy;
import com.pasterdream.pasterdreammod.command.meltdreamenergy.MeltDreamEnergy;
import com.pasterdream.pasterdreammod.command.music.MusicDebug;
import com.pasterdream.pasterdreammod.command.san.IsSanEnabled;
import com.pasterdream.pasterdreammod.command.san.LowSanEffect;
import com.pasterdream.pasterdreammod.command.san.MaxSan;
import com.pasterdream.pasterdreammod.command.san.San;
import com.pasterdream.pasterdreammod.command.san.ShadowDifficulty;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;

public class PasterDreamDebug
{
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(Commands.literal("pasterdreamdebug")
                .then(Commands.literal("meltdreamenergy")
                        .then(Commands.literal("energyvalue")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.literal("set")
                                                .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                        .executes(MeltDreamEnergy::setMeltDreamEnergy)))
                                        .then(Commands.literal("add")
                                                .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                        .executes(MeltDreamEnergy::addMeltDreamEnergy)))
                                        .then(Commands.literal("get")
                                                .executes(MeltDreamEnergy::getMeltDreamEnergy))))
                        .then(Commands.literal("maxenergyvalue")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.literal("set")
                                                .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                        .executes(MaxMeltDreamEnergy::setMaxMeltDreamEnergy)))
                                        .then(Commands.literal("add")
                                                .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                        .executes(MaxMeltDreamEnergy::addMaxMeltDreamEnergy)))
                                        .then(Commands.literal("get")
                                                .executes(MaxMeltDreamEnergy::getMaxMeltDreamEnergy))))
                        .then(Commands.literal("isornotneed")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.literal("set")
                                                .then(Commands.argument("boolean", BoolArgumentType.bool())
                                                        .executes(IsNotNeed::setIsNotNeed)))
                                        .then(Commands.literal("get")
                                                .executes(IsNotNeed::getIsNotNeed)))))
                .then(Commands.literal("san")
                        .then(Commands.literal("sanvalue")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.literal("set")
                                                .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                        .executes(San::setSan)))
                                        .then(Commands.literal("add")
                                                .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                        .executes(San::addSan)))
                                        .then(Commands.literal("get")
                                                .executes(San::getSan))))
                        .then(Commands.literal("maxsanvalue")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.literal("set")
                                                .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                        .executes(MaxSan::setMaxSan)))
                                        .then(Commands.literal("add")
                                                .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                        .executes(MaxSan::addMaxSan)))
                                        .then(Commands.literal("get")
                                                .executes(MaxSan::getMaxSan))))
                        .then(Commands.literal("issanenabled")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.literal("set")
                                                .then(Commands.argument("boolean", BoolArgumentType.bool())
                                                        .executes(IsSanEnabled::setIsEnabled)))
                                        .then(Commands.literal("get")
                                                .executes(IsSanEnabled::getIsEnabled))))
                        .then(Commands.literal("lowsan")
                                .then(Commands.literal("overlay")
                                        .then(Commands.literal("set")
                                                .then(Commands.argument("boolean", BoolArgumentType.bool())
                                                        .executes(LowSanEffect::setOverlay)))
                                        .then(Commands.literal("get")
                                                .executes(LowSanEffect::getOverlay)))
                                .then(Commands.literal("jitter")
                                        .then(Commands.literal("set")
                                                .then(Commands.argument("boolean", BoolArgumentType.bool())
                                                        .executes(LowSanEffect::setJitter)))
                                        .then(Commands.literal("get")
                                                .executes(LowSanEffect::getJitter)))
                                .then(Commands.literal("sound")
                                        .then(Commands.literal("set")
                                                .then(Commands.argument("boolean", BoolArgumentType.bool())
                                                        .executes(LowSanEffect::setSound)))
                                        .then(Commands.literal("get")
                                                .executes(LowSanEffect::getSound)))))
                .then(Commands.literal("music")
                        .then(Commands.literal("check")
                                .executes(MusicDebug::checkMusic)))
                .then(Commands.literal("shadowDifficulty")
                        .then(Commands.literal("very_easy")
                                .executes(ctx -> ShadowDifficulty.set(ctx, "very_easy")))
                        .then(Commands.literal("easy")
                                .executes(ctx -> ShadowDifficulty.set(ctx, "easy")))
                        .then(Commands.literal("normal")
                                .executes(ctx -> ShadowDifficulty.set(ctx, "normal")))
                        .then(Commands.literal("hard")
                                .executes(ctx -> ShadowDifficulty.set(ctx, "hard")))
                        .executes(ShadowDifficulty::get)));
    }
}
