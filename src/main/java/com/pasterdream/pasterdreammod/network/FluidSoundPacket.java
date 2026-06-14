package com.pasterdream.pasterdreammod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class FluidSoundPacket
{
    private final ResourceLocation fluidId;
    private final boolean isFill;

    public FluidSoundPacket(ResourceLocation fluidId, boolean isFill)
    {
        this.fluidId = fluidId;
        this.isFill = isFill;
    }

    public static void encode(FluidSoundPacket message, FriendlyByteBuf friendlyByteBuf)
    {
        friendlyByteBuf.writeResourceLocation(message.fluidId);
        friendlyByteBuf.writeBoolean(message.isFill);
    }

    public static FluidSoundPacket decode(FriendlyByteBuf friendlyByteBuf)
    {
        return new FluidSoundPacket(friendlyByteBuf.readResourceLocation(), friendlyByteBuf.readBoolean());
    }

    public static void handle(FluidSoundPacket message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() ->
        {
            if (context.getDirection().getReceptionSide().isClient())
            {
                Level level = Minecraft.getInstance().level;
                if (level == null)
                {
                    return;
                }

                Player player = Minecraft.getInstance().player;
                if (player == null)
                {
                    return;
                }

                Fluid fluid = ForgeRegistries.FLUIDS.getValue(message.fluidId);
                if (fluid != null)
                {
                    FluidType fluidType = fluid.getFluidType();
                    SoundAction action = message.isFill ? SoundActions.BUCKET_FILL : SoundActions.BUCKET_EMPTY;
                    SoundEvent sound = fluidType.getSound(action);
                    if (sound == null)
                    {
                        sound = message.isFill ? SoundEvents.BUCKET_FILL : SoundEvents.BUCKET_EMPTY;
                    }

                    player.playSound(sound, 1.0F, 1.0F);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
