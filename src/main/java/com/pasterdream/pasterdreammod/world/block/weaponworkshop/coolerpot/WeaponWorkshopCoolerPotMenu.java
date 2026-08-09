package com.pasterdream.pasterdreammod.world.block.weaponworkshop.coolerpot;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidContainer;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.IFluidContainer;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import com.pasterdream.pasterdreammod.init.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class WeaponWorkshopCoolerPotMenu extends AbstractContainerMenuWithFluidSlot
{
    private final WeaponWorkshopCoolerPotBlockEntity blockEntity;
    private final IFluidContainer fluidContainer;

    public WeaponWorkshopCoolerPotMenu(int id, Inventory inventory, WeaponWorkshopCoolerPotBlockEntity blockEntity)
    {
        super(ModMenus.WEAPON_WORKSHOP_COOLER_POT.get(), id);
        this.blockEntity = blockEntity;
        this.fluidContainer = new FluidContainer.GenericFluidContainer(blockEntity.getFluidTanks());

        addFluidSlot(new FluidSlot(fluidContainer, 0, 76, 23));

        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 50, 6));
        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 1, 104, 6)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return false;
            }
        });

        for (int i = 0; i < 9; i++)
        {
            addSlot(new Slot(inventory, i, 5 + i * 18, 118));
        }

        for (int i = 0; i < 27; i++)
        {
            addSlot(new Slot(inventory, i + 9, 5 + (i % 9) * 18, 60 + (i / 9) * 18));
        }

        reBuildLastFluids();
    }

    public WeaponWorkshopCoolerPotBlockEntity getBlockEntity()
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
        if (index >= 1 && index <= 2)
        {
            if (!this.moveItemStackTo(stack, 3, 39, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else    //背包移入输入槽
            if (index >= 3 && index <= 38)
            {
                if (!this.moveItemStackTo(stack, 1, 2, false))
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
