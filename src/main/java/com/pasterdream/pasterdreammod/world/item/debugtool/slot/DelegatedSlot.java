package com.pasterdream.pasterdreammod.world.item.debugtool.slot;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DelegatedSlot extends Slot
{
    private final Supplier<ItemStack> getter;
    private final Consumer<ItemStack> setter;
    private final Predicate<ItemStack> mayPlaceCheck;
    private boolean active = true;

    public DelegatedSlot(Supplier<ItemStack> getter, Consumer<ItemStack> setter, int x, int y)
    {
        this(getter, setter, itemStack -> true, x, y);
    }

    public DelegatedSlot(Supplier<ItemStack> getter, Consumer<ItemStack> setter, Predicate<ItemStack> mayPlaceCheck, int x, int y)
    {
        super(new SimpleContainer(1), 0, x, y);
        this.getter = getter;
        this.setter = setter;
        this.mayPlaceCheck = mayPlaceCheck;
    }

    @Override
    public ItemStack getItem()
    {
        return getter.get();
    }

    @Override
    public void set(ItemStack itemStack)
    {
        setter.accept(itemStack);
    }

    @Override
    public ItemStack remove(int amount)
    {
        ItemStack current = getter.get();
        if (current.isEmpty())
        {
            return ItemStack.EMPTY;
        }

        int toRemove = Math.min(amount, current.getCount());
        ItemStack result = current.copyWithCount(toRemove);

        if (toRemove >= current.getCount())
        {
            setter.accept(ItemStack.EMPTY);
        }
            else
            {
                ItemStack remaining = current.copy();
                remaining.shrink(toRemove);
                setter.accept(remaining);
            }
        return result;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack)
    {
        return mayPlaceCheck.test(itemStack);
    }

    @Override
    public boolean mayPickup(Player player)
    {
        return !getter.get().isEmpty();
    }

    @Override
    public void setChanged()
    {

    }

    @Override
    public int getContainerSlot()
    {
        return 0;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    @Override
    public boolean isActive()
    {
        return active;
    }
}
