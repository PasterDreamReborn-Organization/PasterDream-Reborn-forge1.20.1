package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.event.ModWorldGenEvents;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceOrTagKeyArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.server.commands.LocateCommand.class)
public class LocateCommandMixin {

    private static final ResourceKey<Structure> SHADOW_DOOR_KEY =
            ResourceKey.create(Registries.STRUCTURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_world_door"));

    @Inject(method = "locateStructure", at = @At("HEAD"), cancellable = true)
    private static void onLocateStructure(CommandSourceStack source,
                                          ResourceOrTagKeyArgument.Result<Structure> structure,
                                          CallbackInfoReturnable<Integer> cir) {
        boolean isOurStructure = structure.unwrap()
                .map(key -> key.equals(SHADOW_DOOR_KEY), tag -> false);
        if (!isOurStructure) return;

        ServerLevel nether = source.getServer().getLevel(Level.NETHER);
        if (nether == null) {
            source.sendFailure(Component.translatable("commands.locate.structure.not_found"));
            cir.setReturnValue(0);
            return;
        }

        ModWorldGenEvents.ShadowWorldDoorPlacedData data =
                ModWorldGenEvents.ShadowWorldDoorPlacedData.get(nether);
        if (!data.isPlaced()) {
            source.sendFailure(Component.translatable("commands.locate.structure.not_found"));
            cir.setReturnValue(0);
            return;
        }

        BlockPos origin = BlockPos.containing(source.getPosition());
        BlockPos found = new BlockPos(data.getPosX(), 128, data.getPosZ());
        int dist = (int) Math.sqrt(origin.distSqr(found));

        Component coords = ComponentUtils.wrapInSquareBrackets(
                Component.translatable("chat.coordinates", found.getX(), "~", found.getZ()))
                .withStyle(style -> style
                        .withColor(net.minecraft.ChatFormatting.GREEN)
                        .withClickEvent(new net.minecraft.network.chat.ClickEvent(
                                net.minecraft.network.chat.ClickEvent.Action.SUGGEST_COMMAND,
                                "/tp @s " + found.getX() + " ~ " + found.getZ()))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                Component.translatable("chat.coordinates.tooltip"))));

        source.sendSuccess(() -> Component.translatable(
                "commands.locate.structure.success", "pasterdream:shadow_world_door", coords, dist), false);
        cir.setReturnValue(dist);
    }
}
