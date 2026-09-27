package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.IDebugItemHandlerAndFluidHandlerEditorMenu;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.ItemHandlerLaunchData;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.ServerMenuReturnStack;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.ItemHandlerMenu;
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

public class OpenItemHandlerPacket
{
    private final int menuId;

    public OpenItemHandlerPacket(int menuId)
    {
        this.menuId = menuId;
    }

    public void encode(FriendlyByteBuf buf)
    {
        buf.writeVarInt(menuId);
    }

    public static OpenItemHandlerPacket decode(FriendlyByteBuf buffer)
    {
        return new OpenItemHandlerPacket(buffer.readVarInt());
    }

    public static void handle(OpenItemHandlerPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if(player != null && player.containerMenu.containerId == packet.menuId)
            {
                AbstractContainerMenu current = player.containerMenu;
                if ((current instanceof IDebugItemHandlerAndFluidHandlerEditorMenu editor))
                {
                    ItemHandlerLaunchData data = editor.provideItemHandlerLaunch();
                    if(data != null)
                    {
                        ServerMenuReturnStack.push(player, editor.asMenuProvider());
                        final int totalSlots = data.handler().getSlots();
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
                                return new ItemHandlerMenu(id, playerInventory, data.handler(), data.onChanged());
                            }
                        }, buffer -> buffer.writeVarInt(totalSlots));
                    }
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
