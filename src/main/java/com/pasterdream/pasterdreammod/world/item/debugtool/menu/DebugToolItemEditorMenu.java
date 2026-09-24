package com.pasterdream.pasterdreammod.world.item.debugtool.menu;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.IFluidContainer;
import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class DebugToolItemEditorMenu extends AbstractContainerMenuWithFluidSlot implements DebugItemEditorMenu
{
    private final Container editorContainer;
    private final Player player;
    private final IFluidContainer fluidContainer;
    public IEnergyStorage energyStorage = null;

    public DebugToolItemEditorMenu(int id, Inventory playerInventory)
    {
        super(ModMenus.DEBUG_TOOL_ITEM_EDITOR.get(), id);

        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(playerInventory, col, 5 + col * 18, 217));
        }

        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 5 + col * 18, 159 + row * 18));
            }
        }

        this.editorContainer = new SimpleContainer(2);
        player = playerInventory.player;
        if (!player.level().isClientSide)
        {
            for(int i = 0; i <= 1; i++)
            {
                ItemStack stored = PlayerEditorSlotData.get(player, i);
                if (!stored.isEmpty())
                {
                    editorContainer.setItem(i, stored.copy());
                }
            }
        }

        addSlot(new Slot(editorContainer, 0, 152, 12)
        {
            @Override
            public void set(ItemStack itemStack)
            {
                super.set(itemStack);
                PlayerEditorSlotData.set(player, itemStack, 0);
                for (Consumer<ItemStack> consumer : editorSlotListeners)
                {
                    consumer.accept(itemStack);
                }
                energyStorage = itemStack.getCapability(ForgeCapabilities.ENERGY).resolve().orElse(null);
            }
        });

        this.fluidContainer = new PlayerFluidSlotContainer(player);
        for (int i = 0; i < fluidContainer.getFluidContainerSize(); i++)
        {
            addFluidSlot(new FluidSlot(fluidContainer, i, 88 - 72 + 18 * i, 88));
        }

        addSlot(new Slot(editorContainer, 1, 152, 128)
        {
            @Override
            public void set(ItemStack itemStack)
            {
                super.set(itemStack);
                PlayerEditorSlotData.set(player, itemStack, 1);
            }
        });

        reBuildLastFluids();
    }

    private final List<Consumer<ItemStack>> editorSlotListeners = new CopyOnWriteArrayList<>();

    public void addEditorSlotListener(Consumer<ItemStack> listener)
    {
        editorSlotListeners.add(listener);
    }

    public void clearEditorSlotListeners()
    {
        editorSlotListeners.clear();
    }

    private void clearContainer(Container container, Player player)
    {
        for (int i = 0; i < container.getContainerSize(); i++)
        {
            ItemStack stack = container.removeItemNoUpdate(i);
            if (!stack.isEmpty())
            {
                if (!player.getInventory().add(stack))
                {
                    player.drop(stack, false);
                }
            }
        }
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

        if ((index == 36 || index == 45))
        {   //从机器移出到背包
            if (!this.moveItemStackTo(stack, 0, 36, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else
            if((index >= 0 && index <= 35))
            {   //从背包移入输入槽
                if (!(this.moveItemStackTo(stack, 36, 37, false) || this.moveItemStackTo(stack, 45, 46, false)))
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
    protected IFluidContainer getFluidContainer()
    {
        return fluidContainer;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    @Override
    public MenuProvider asMenuProvider()
    {
        return new MenuProvider()
        {
            @Override
            public Component getDisplayName()
            {
                return Component.empty();
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player)
            {
                return new DebugToolItemEditorMenu(id, playerInventory);
            }
        };
    }

    @Override
    public ItemHandlerLaunchData provideItemHandlerLaunch()
    {
        ItemStack itemStack = this.editorContainer.getItem(0);
        IItemHandler handler = itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve().orElse(null);
        if(handler != null)
        {
            Runnable onChanged = () -> PlayerEditorSlotData.set(player, itemStack, 0);
            return new ItemHandlerLaunchData(handler, onChanged);
        }
            else
            {
                return null;
            }
    }

    @Override
    public FluidHandlerLaunchData provideFluidHandlerLaunch()
    {
        ItemStack itemStack = this.editorContainer.getItem(0);
        IFluidHandler handler = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElse(null);
        if(handler != null)
        {
            Runnable onChanged = () -> PlayerEditorSlotData.set(player, itemStack, 0);
            return new FluidHandlerLaunchData(handler, onChanged);
        }
            else
            {
                return null;
            }
    }
}
