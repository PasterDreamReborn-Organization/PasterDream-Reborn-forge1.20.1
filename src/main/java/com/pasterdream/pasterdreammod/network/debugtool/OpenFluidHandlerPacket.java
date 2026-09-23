package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.DebugItemEditorMenu;
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
    private final int slotIndex;

    public OpenFluidHandlerPacket(int menuId, int slotIndex)
    {
        this.menuId = menuId;
        this.slotIndex = slotIndex;
    }

    public void encode(FriendlyByteBuf buf)
    {
        buf.writeVarInt(menuId);
        buf.writeVarInt(slotIndex);
    }

    public static OpenFluidHandlerPacket decode(FriendlyByteBuf buffer)
    {
        return new OpenFluidHandlerPacket(buffer.readVarInt(), buffer.readVarInt());
    }

    public static void handle(OpenFluidHandlerPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if(player != null && player.containerMenu.containerId == packet.menuId)
            {
                AbstractContainerMenu current = player.containerMenu;
                if ((current instanceof DebugItemEditorMenu editor))
                {
                    FluidHandlerLaunchData data = editor.provideFluidHandlerLaunch();
                    if(data != null)
                    {
                        ServerMenuReturnStack.push(player, editor.asMenuProvider());
                        final int totalSlots = data.handler().getTanks();
                        System.out.println("调用NetworkHooks.openScreen");
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
