package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.IDebugItemHandlerAndFluidHandlerEditorMenu;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.FluidHandlerLaunchData;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.ServerMenuReturnStack;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.FluidHandlerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class OpenFluidHandlerPacket
{
    private final int menuId;

    public OpenFluidHandlerPacket(int menuId)
    {
        this.menuId = menuId;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(menuId);
    }

    public static OpenFluidHandlerPacket decode(FriendlyByteBuf buffer)
    {
        return new OpenFluidHandlerPacket(buffer.readVarInt());
    }

    public static void handle(OpenFluidHandlerPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if(player != null && player.containerMenu.containerId == packet.menuId)
            {
                AbstractContainerMenu current = player.containerMenu;
                if ((current instanceof IDebugItemHandlerAndFluidHandlerEditorMenu editor))
                {
                    FluidHandlerLaunchData data = editor.provideFluidHandlerLaunch();
                    if(data != null)
                    {
                        ServerMenuReturnStack.push(player, editor.asMenuProvider());
                        final int totalSlots = data.handler().getTanks();
                        NetworkHooks.openScreen(player, new MenuProvider()
                        {
                            @Override
                            public Component getDisplayName()
                            {
                                return Component.empty();
                            }
                            @Override
                            public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player)
                            {
                                return new FluidHandlerMenu(id, playerInventory, data.handler(), data.onChanged());
                            }
                        }, buffer -> buffer.writeVarInt(totalSlots));
                    }
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
