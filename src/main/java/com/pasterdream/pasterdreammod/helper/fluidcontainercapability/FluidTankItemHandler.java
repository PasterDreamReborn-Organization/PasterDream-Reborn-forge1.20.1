package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 通用的「物品内置流体罐」能力：把 {@link FluidTank} 的内容持久化到物品 NBT 的指定键。
 * <p>
 * 与块实体里的 {@code FluidTank} 用法一致（见 {@code DreamCauldronBlockEntity}），
 * 任何需要承载流体的物品，只要给出容量、流体校验器与 NBT 键即可复用。
 * 可选传入 {@code emptyContainer}：当流体被完全抽空后，把容器换成对应的空容器（如满瓶 → 空瓶）。
 */
public class FluidTankItemHandler implements IFluidHandlerItem
{
    private ItemStack container;
    private final FluidTank tank;
    private final String nbtKey;
    @Nullable
    private final Supplier<ItemStack> emptyContainer;

    public FluidTankItemHandler(ItemStack container, int capacity, Predicate<FluidStack> validator, String nbtKey)
    {
        this(container, capacity, validator, nbtKey, null);
    }

    public FluidTankItemHandler(ItemStack container, int capacity, Predicate<FluidStack> validator, String nbtKey, @Nullable Supplier<ItemStack> emptyContainer)
    {
        this.container = container;
        this.nbtKey = nbtKey;
        this.emptyContainer = emptyContainer;
        this.tank = new FluidTank(capacity, validator)
        {
            @Override
            protected void onContentsChanged()
            {
                FluidTankItemHandler.this.container.getOrCreateTag().put(FluidTankItemHandler.this.nbtKey, writeToNBT(new CompoundTag()));
            }
        };

        CompoundTag tag = container.getTag();
        if (tag != null && tag.contains(nbtKey, Tag.TAG_COMPOUND))
        {
            this.tank.readFromNBT(tag.getCompound(nbtKey));
        }
    }

    @Override
    public int getTanks()
    {
        return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank)
    {
        return this.tank.getFluid();
    }

    @Override
    public int getTankCapacity(int tank)
    {
        return this.tank.getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack)
    {
        return this.tank.isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action)
    {
        return this.tank.fill(resource, action);
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action)
    {
        FluidStack drained = this.tank.drain(resource, action);
        swapToEmptyOnDrain(action, drained);
        return drained;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action)
    {
        FluidStack drained = this.tank.drain(maxDrain, action);
        swapToEmptyOnDrain(action, drained);
        return drained;
    }

    /** 流体被完全抽空后，若配置了空容器，则把容器替换为空容器。 */
    private void swapToEmptyOnDrain(FluidAction action, FluidStack drained)
    {
        if (action.execute() && !drained.isEmpty() && this.tank.getFluidAmount() <= 0 && this.emptyContainer != null)
        {
            this.container = this.emptyContainer.get();
        }
    }

    @Override
    public ItemStack getContainer()
    {
        return container;
    }
}
