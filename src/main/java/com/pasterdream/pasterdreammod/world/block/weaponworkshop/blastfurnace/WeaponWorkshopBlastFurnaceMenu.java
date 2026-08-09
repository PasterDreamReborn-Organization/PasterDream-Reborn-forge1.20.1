package com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidContainer;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.IFluidContainer;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import com.pasterdream.pasterdreammod.init.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class WeaponWorkshopBlastFurnaceMenu extends AbstractContainerMenuWithFluidSlot
{
    private final WeaponWorkshopBlastFurnaceBlockEntity blockEntity;
    private final IFluidContainer fluidContainer;

    public WeaponWorkshopBlastFurnaceMenu(int id, Inventory inventory, WeaponWorkshopBlastFurnaceBlockEntity blockEntity)
    {
        super(ModMenus.WEAPON_WORKSHOP_BLAST_FURNACE.get(), id);
        this.blockEntity = blockEntity;
        fluidContainer = new FluidContainer.GenericFluidContainer(blockEntity.getFluidTanks());
        IItemHandler handler = blockEntity.getItemHandler();

        addFluidSlot(new FluidSlot(fluidContainer, 0, 129, 30));

        addSlot(new SlotItemHandler(handler, 0, 76, 22));
        addSlot(new SlotItemHandler(handler, 1, 22, 49));
        addSlot(new SlotItemHandler(handler, 2, 76, 76)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return false;
            }
        });

        for (int i = 0; i < 9; i++)
        {
            addSlot(new Slot(inventory, i, 5 + i * 18, 171));
        }

        for (int i = 0; i < 27; i++)
        {
            addSlot(new Slot(inventory, i + 9, 5 + (i % 9) * 18, 113 + (i / 9) * 18));
        }

        reBuildLastFluids();
    }

    public WeaponWorkshopBlastFurnaceBlockEntity getBlockEntity()
    {
        return blockEntity;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return player.level().getBlockEntity(blockEntity.getBlockPos()) == blockEntity && player.distanceToSqr(blockEntity.getBlockPos().getX() + 0.5, blockEntity.getBlockPos().getY() + 0.5, blockEntity.getBlockPos().getZ() + 0.5) <= 64;
    }

    @Override
    public IFluidContainer getFluidContainer()
    {
        return fluidContainer;
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

        //移出到背包
        if (index >= 1 && index <= 3)
        {
            if (!this.moveItemStackTo(stack, 4, 40, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else    //背包移入输入槽
            if (index >= 4 && index <= 39)
            {
                if (!this.moveItemStackTo(stack, 1, 3, false))
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

        if (stack.getCount() == copy.getCount())
        {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);
        return copy;
    }
}
