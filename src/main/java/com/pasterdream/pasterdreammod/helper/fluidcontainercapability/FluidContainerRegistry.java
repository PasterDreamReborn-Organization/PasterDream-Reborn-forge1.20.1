package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class FluidContainerRegistry
{
    private static final Map<Item, ContainerEntry> EMPTY_TO_FILL = new HashMap<>();
    private static final Map<Item, ContainerEntry> FILL_TO_EMPTY = new HashMap<>();
    private static final Map<Item, Map<Fluid, ContainerEntry>> EMPTY_FLUID_TO_ENTRY = new HashMap<>();

    public static void register(Item emptyItem, Fluid fluid, int amount, Item filledItem)
    {
        ContainerEntry entry = new ContainerEntry(emptyItem, fluid, amount, filledItem);
        EMPTY_TO_FILL.put(emptyItem, entry);
        FILL_TO_EMPTY.put(filledItem, entry);
        EMPTY_FLUID_TO_ENTRY.computeIfAbsent(emptyItem, item -> new HashMap<>()).put(fluid, entry);
    }

    @Nullable
    public static ContainerEntry getEntryForEmptyToFill(Item item)
    {
        return EMPTY_TO_FILL.get(item);
    }

    @Nullable
    public static ContainerEntry getEntryForFillToEmpty(Item item)
    {
        return FILL_TO_EMPTY.get(item);
    }

    @Nullable
    public static ContainerEntry getEntryForEmptyAndFluid(Item emptyItem, Fluid fluid)
    {
        Map<Fluid, ContainerEntry> map = EMPTY_FLUID_TO_ENTRY.get(emptyItem);
        return map != null ? map.get(fluid) : null;
    }

    @Nullable
    public static ContainerEntry getAnyEntryForEmpty(Item emptyItem)
    {
        return EMPTY_TO_FILL.get(emptyItem);
    }

    public static class ContainerEntry
    {
        public final Item emptyItem;
        public final Fluid fluid;
        public final int amount;
        public final Item filledItem;

        public ContainerEntry(Item emptyItem, Fluid fluid, int amount, Item filledItem)
        {
            this.emptyItem = emptyItem;
            this.fluid = fluid;
            this.amount = amount;
            this.filledItem = filledItem;
        }
    }
}
