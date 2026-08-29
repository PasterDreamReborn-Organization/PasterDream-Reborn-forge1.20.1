package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import com.pasterdream.pasterdreammod.helper.potionhelper.GenericMobEffect;
import com.pasterdream.pasterdreammod.helper.potionhelper.PotionHelper;
import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluidContainerRegistry
{
    private static final Map<Item, ContainerEntry> EMPTY_TO_FILL = new HashMap<>();
    private static final Map<Item, ContainerEntry> FILL_TO_EMPTY = new HashMap<>();
    private static final Map<Item, Map<Fluid, ContainerEntry>> EMPTY_FLUID_TO_ENTRY = new HashMap<>();

    public static void register(Item emptyItem, Fluid fluid, int amount, Item filledItem)
    {
        ContainerEntry entry = new ContainerEntry(new ItemStack(emptyItem), new FluidStack(fluid, amount), new ItemStack(filledItem));
        EMPTY_TO_FILL.put(emptyItem, entry);
        FILL_TO_EMPTY.put(filledItem, entry);
        EMPTY_FLUID_TO_ENTRY.computeIfAbsent(emptyItem, item -> new HashMap<>()).put(fluid, entry);
    }

    @Nullable
    public static ContainerEntry getEntryForEmptyToFill(ItemStack itemStack)
    {
        if(itemStack.getItem() == Items.GLASS_BOTTLE)
        {
            ItemStack waterBottle = new ItemStack(Items.POTION);
            CompoundTag NBT = new CompoundTag();
            NBT.putString("Potion", "minecraft:water");

            return new ContainerEntry(itemStack, new FluidStack(Fluids.WATER, 250), waterBottle);    //仅作为有效空流体容器识别
        }
            else
            {
                return EMPTY_TO_FILL.get(itemStack.getItem());
            }
    }

    @Nullable
    public static ContainerEntry getEntryForFillToEmpty(ItemStack itemStack)
    {
        if(itemStack.getItem() == Items.POTION)
        {
            List<GenericMobEffect> effectList = PotionHelper.getListGenericMobEffectFromPotion(PotionUtils.getPotion(itemStack));
            FluidStack potionFluidStack = (effectList.isEmpty() ? PotionHelper.createNBTPotion(PotionHelper.getListGenericMobEffectFromGenericPotion(itemStack), 250) : PotionHelper.createNBTPotion(effectList, 250));

            return new ContainerEntry(new ItemStack(Items.GLASS_BOTTLE), potionFluidStack, itemStack);
        }
            else
            {
                return FILL_TO_EMPTY.get(itemStack.getItem());
            }
    }

    @Nullable
    public static ContainerEntry getEntryForEmptyAndFluid(ItemStack emptyContainerItemStack, FluidStack fluidStack)
    {
        if(emptyContainerItemStack.getItem() == Items.GLASS_BOTTLE && fluidStack.getFluid() == ModFluids.POTION.get())
        {
            List<GenericMobEffect> effectList = PotionHelper.getEffectType(fluidStack);
            Potion potion = PotionHelper.getPotionFromListGenericMobEffect(effectList);
            ItemStack potionItem = (potion != null ? PotionUtils.setPotion(new ItemStack(Items.POTION), potion) : PotionHelper.getCustomEffectPotion(effectList));
            FluidStack fillFluidStack = new FluidStack(fluidStack.getFluid(), 250);
            fillFluidStack.setTag(fluidStack.getTag());
            return new ContainerEntry(new ItemStack(Items.GLASS_BOTTLE), fillFluidStack, potionItem);
        }
            else
            {
                Map<Fluid, ContainerEntry> map = EMPTY_FLUID_TO_ENTRY.get(emptyContainerItemStack.getItem());
                return map != null ? map.get(fluidStack.getFluid()) : null;
            }
    }

    @Nullable
    public static ContainerEntry getAnyEntryForEmpty(Item emptyItem)
    {
        return EMPTY_TO_FILL.get(emptyItem);
    }

    public static class ContainerEntry
    {
        public final ItemStack emptyContainerItemStack;
        public final FluidStack fluidStack;
        public final ItemStack fullContainerItemStack;

        public ContainerEntry(ItemStack emptyContainerItemStack, FluidStack fluidStack, ItemStack fullContainerItemStack)
        {
            this.emptyContainerItemStack = emptyContainerItemStack;
            this.fluidStack = fluidStack;
            this.fullContainerItemStack = fullContainerItemStack;
        }
    }
}
