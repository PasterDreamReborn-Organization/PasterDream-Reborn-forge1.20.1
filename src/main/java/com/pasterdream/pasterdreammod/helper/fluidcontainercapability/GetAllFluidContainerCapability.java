package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GetAllFluidContainerCapability
{
    public static List<FluidContainerRelation> allFluidContainerRelation = new ArrayList<>();

    public static List<FluidContainerRelation> getAllContainer()
    {
        allFluidContainerRelation.clear();
        for (Item item : BuiltInRegistries.ITEM)
        {
            ItemStack itemStack = new ItemStack(item);
            Object fluidHandlerCandidate = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElse(null);
            if (fluidHandlerCandidate instanceof IFluidHandlerItem iFluidHandlerItem)
            {
                int fluidTankCount = iFluidHandlerItem.getTanks();
                if (fluidTankCount != 0)
                {

                    ItemStack emptyContainer = iFluidHandlerItem.getContainer();
                    FluidStack fullCapacityFluidStack = FluidStack.EMPTY;
                    ItemStack fullFluidContainer = ItemStack.EMPTY;

                    boolean isGenericFluidContainer = true;
                    boolean canNotFillAnyFluid = true;

                    int fluidTankCapacity = iFluidHandlerItem.getTankCapacity(0);
                    FluidStack currentFluidStack = iFluidHandlerItem.getFluidInTank(0);

                    if(currentFluidStack.isEmpty())
                    {
                        List<FluidStack> fluidStackList = new ArrayList<>();
                        List<ItemStack> fullItemStackList = new ArrayList<>();
                        for(Fluid fluid : BuiltInRegistries.FLUID)
                        {
                            if(fluid != Fluids.EMPTY)
                            {
                                fullCapacityFluidStack = new FluidStack(fluid, fluidTankCapacity);
                                final FluidStack fluidStack = fullCapacityFluidStack;
                                if (iFluidHandlerItem.fill(fullCapacityFluidStack, IFluidHandler.FluidAction.SIMULATE) == fluidTankCapacity)
                                {
                                    AtomicReference<ItemStack> AtomicReferenceFullFluidContainer = new AtomicReference<>(itemStack.copy());
                                    Object fullHandlerCandidate = AtomicReferenceFullFluidContainer.get().getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElse(null);
                                    if (fullHandlerCandidate instanceof IFluidHandlerItem handler)
                                    {
                                        handler.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                                        AtomicReferenceFullFluidContainer.set(handler.getContainer());
                                    }

                                    fullFluidContainer = AtomicReferenceFullFluidContainer.get();

                                    fluidStackList.add(fullCapacityFluidStack);
                                    fullItemStackList.add(fullFluidContainer);
                                    canNotFillAnyFluid = false;
                                }
                                    else
                                    {
                                        isGenericFluidContainer = false;
                                    }
                            }
                        }

                        if(!canNotFillAnyFluid)
                        {
                            if(isGenericFluidContainer)
                            {
                                allFluidContainerRelation.add(new FluidContainerRelation(emptyContainer, new FluidStack(Fluids.WATER, fluidTankCapacity), ItemStack.EMPTY, true));
                            }
                                else
                                {
                                    for(int i = 0; i < fluidStackList.size(); i++)
                                    {
                                        allFluidContainerRelation.add(new FluidContainerRelation(emptyContainer, fluidStackList.get(i), fullItemStackList.get(i), false));
                                    }
                                }
                        }
                    }
                }
            }
        }
        return allFluidContainerRelation;
    }
}
