package com.pasterdream.pasterdreammod.world.item.debugtool.menu;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.IFluidContainer;
import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.ClientFluidHandlerContext;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.FluidHandlerContainerWrapper;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.ServerMenuReturnStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.NetworkHooks;

public class FluidHandlerMenu extends AbstractContainerMenuWithFluidSlot
{
    private IFluidHandler fluidHandler;
    private final IFluidContainer container;
    private final int slotCount;
    private final Player player;
    private final Runnable onChanged;

    public FluidHandlerMenu(int id, Inventory playerInventory, IFluidHandler fluidHandler, Runnable onChanged)
    {
        super(ModMenus.FLUID_HANDLER.get(), id);
        System.out.println("服务端创建菜单");
        this.fluidHandler = fluidHandler;
        this.slotCount = fluidHandler.getTanks();
        player = playerInventory.player;
        this.onChanged = onChanged;
        this.container = new FluidHandlerContainerWrapper(fluidHandler, onChanged);
        buildSlots(playerInventory, fluidHandler);
    }

    public FluidHandlerMenu(int id, Inventory playerInventory, FriendlyByteBuf buffer)
    {
        super(ModMenus.FLUID_HANDLER.get(), id);
        System.out.println("客户端创建菜单");
        this.slotCount = buffer.readVarInt();
        fluidHandler = ClientFluidHandlerContext.consume();
        player = playerInventory.player;
        onChanged = null;
        this.container = new FluidHandlerContainerWrapper(fluidHandler, null);
        buildSlots(playerInventory, fluidHandler);
    }

    private void buildSlots(Inventory playerInventory, IFluidHandler handler)
    {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 91 + row * 18));
            }
        }

        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 149));
        }

        for (int i = 0; i < slotCount; i++)
        {
            int x = 7 + (i % 8) * 18;
            int y = 7 + ((i % 32) / 8) * 18;

            addFluidSlot(new FluidSlot(container, i, x, y, onChanged));
        }
    }

    @Override
    public IFluidContainer getFluidContainer()
    {
        return container;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int count)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    @Override
    public void removed(Player player)
    {
        super.removed(player);
        if (player instanceof ServerPlayer serverPlayer)
        {
            MenuProvider parentMenu = ServerMenuReturnStack.pop(player);
            if (parentMenu != null)
            {
                serverPlayer.server.execute(() -> NetworkHooks.openScreen(serverPlayer, parentMenu, buf -> {}));
            }
        }
    }
}
