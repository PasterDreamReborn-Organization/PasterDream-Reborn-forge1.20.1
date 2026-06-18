package com.pasterdream.pasterdreammod.world.item.mortar;

import com.pasterdream.pasterdreammod.init.ModMenus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class MortarMenu extends AbstractContainerMenu
{
    private final MortarInventory inventory;
    private final ItemStack container;
    private final IItemHandler itemHandler;
    private static final int INPUT_FLUID_SLOTS = 4;
    private static final int OUTPUT_FLUID_SLOTS = 2;

    public MortarMenu(int id, Inventory inventory, ItemStack container, InteractionHand hand)
    {
        super(ModMenus.MORTAR.get(), id);
        this.container = container;
        this.inventory = new MortarInventory(container);
        this.itemHandler = new InvWrapper(this.inventory);

        initNBT();

        for (int row = 0; row < 2; row++)
        {
            for (int col = 0; col < 4; col++)
            {
                int index = row * 4 + col;
                addSlot(new SlotItemHandler(itemHandler, index, 73 + col * 18, 6 + row * 18));
            }
        }

        for (int col = 0; col < 4; col++)
        {
            int index = 8 + col;
            addSlot(new SlotItemHandler(itemHandler, index, 73 + col * 18, 68)
            {
                @Override
                public boolean mayPlace(ItemStack stack)
                {
                    return false;
                }
            });
        }

        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(inventory, col, 5 + col * 18, 159));
        }
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(inventory, col + row * 9 + 9, 5 + col * 18, 101 + row * 18));
            }
        }
    }

    private void initNBT()
    {
        CompoundTag tag = container.getOrCreateTag();

        if (!tag.contains("InputFluids"))
        {
            ListTag list = new ListTag();
            for (int i = 0; i < INPUT_FLUID_SLOTS; i++)
            {
                list.add(new CompoundTag());
            }
            tag.put("InputFluids", list);
        }

        if (!tag.contains("OutputFluids"))
        {
            ListTag list = new ListTag();
            for (int i = 0; i < OUTPUT_FLUID_SLOTS; i++)
            {
                list.add(new CompoundTag());
            }
            tag.put("OutputFluids", list);
        }
    }

    private ListTag getFluidList(String key)
    {
        return container.getOrCreateTag().getList(key, Tag.TAG_LIST);
    }

    public FluidStack getInputFluidStack(int index)
    {
        ListTag list = getFluidList("InputFluids");
        if (index < 0 || index >= list.size())
        {
            return FluidStack.EMPTY;
        }
        return FluidStack.loadFluidStackFromNBT(list.getCompound(index));
    }

    public void setInputFluidStack(int index, FluidStack stack)
    {
        ListTag list = getFluidList("InputFluids");
        if (index < 0 || index >= list.size())
        {
            return;
        }
        CompoundTag fluidTag = list.getCompound(index);
        if (stack.isEmpty())
        {
            list.set(index, new CompoundTag());
        }
            else
            {
                stack.writeToNBT(fluidTag);
            }
        container.getOrCreateTag().put("InputFluids", list);
    }

    public FluidStack getOutputFluidStack(int index)
    {
        ListTag list = getFluidList("OutputFluids");
        if (index < 0 || index >= list.size())
        {
            return FluidStack.EMPTY;
        }
        return FluidStack.loadFluidStackFromNBT(list.getCompound(index));
    }

    public void setOutputFluidStack(int index, FluidStack stack)
    {
        ListTag list = getFluidList("OutputFluids");
        if (index < 0 || index >= list.size())
        {
            return;
        }

        CompoundTag fluidTag = list.getCompound(index);
        if (stack.isEmpty())
        {
            list.set(index, new CompoundTag());
        }
        else
            {
                stack.writeToNBT(fluidTag);
            }
        container.getOrCreateTag().put("OutputFluids", list);
    }

    public MortarInventory getInventory()
    {
        return inventory;
    }

    public ItemStack getContainer()
    {
        return container;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        //输入槽0-7,输出槽8-11,玩家背包12-47
        if (index < 12)
        {   //从机器移出到背包
            if (!this.moveItemStackTo(stack, 12, 48, true))
            {
                return ItemStack.EMPTY;
            }
        }
            else
            {   //从背包移入输入槽
                if (!this.moveItemStackTo(stack, 0, 8, false))
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
    public boolean stillValid(Player player)
    {
        return true;
    }
}
