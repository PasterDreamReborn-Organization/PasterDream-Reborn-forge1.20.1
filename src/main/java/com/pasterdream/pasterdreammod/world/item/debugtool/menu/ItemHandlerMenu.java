package com.pasterdream.pasterdreammod.world.item.debugtool.menu;

import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.ClientItemHandlerContext;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.ServerMenuReturnStack;
import com.pasterdream.pasterdreammod.world.item.debugtool.slot.ActiveStatusChangeableSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;

public class ItemHandlerMenu extends AbstractContainerMenu
{
    private IItemHandler itemHandler;
    private final int slotCount;
    private final Player player;
    private final Runnable onChanged;

    public ItemHandlerMenu(int id, Inventory playerInventory, IItemHandler itemHandler, Runnable onChanged)
    {
        super(ModMenus.ITEM_HANDLER.get(), id);
        this.itemHandler = itemHandler;
        this.slotCount = itemHandler.getSlots();
        player = playerInventory.player;
        this.onChanged = onChanged;
        buildSlots(playerInventory, itemHandler);
    }

    public ItemHandlerMenu(int id, Inventory playerInventory, FriendlyByteBuf buffer)
    {
        super(ModMenus.ITEM_HANDLER.get(), id);
        this.slotCount = buffer.readVarInt();
        itemHandler = ClientItemHandlerContext.consume();
        player = playerInventory.player;
        onChanged = null;
        buildSlots(playerInventory, itemHandler);
    }

    private void buildSlots(Inventory playerInventory, IItemHandler handler)
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
            int x = 8 + (i % 8) * 18;
            int y = 8 + ((i % 32) / 8) * 18;
            addSlot(new ActiveStatusChangeableSlot(handler, i, x, y)
            {
                @Override
                public void set(ItemStack stack)
                {
                    super.set(stack);
                    if (!player.level().isClientSide)
                    {
                        onChanged.run();
                    }
                }

                @Override
                public ItemStack remove(int amount)
                {
                    ItemStack result = super.remove(amount);
                    if (!player.level().isClientSide)
                    {
                        onChanged.run();
                    }
                    return result;
                }
            });
        }
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        Slot slot = slots.get(index);
        if (!slot.hasItem())
        {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if ((index >= 36))
        {   //从机器移出到背包
            if (!this.moveItemStackTo(stack, 0, 36, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else
            if((index >= 0 && index <= 35))
            {   //从背包移入机器
                if (!this.moveItemStackTo(stack, 36, 36 + slotCount, false))
                {
                    return ItemStack.EMPTY;
                }
            }

        if (stack.isEmpty())
        {
            slot.set(ItemStack.EMPTY);
        }
            else
            {
                slot.setChanged();
            }
        return copy;
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
