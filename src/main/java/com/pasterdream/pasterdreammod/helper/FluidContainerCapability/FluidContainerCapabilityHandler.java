package com.pasterdream.pasterdreammod.helper.FluidContainerCapability;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class FluidContainerCapabilityHandler
{
    public static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event)
    {
        Item item = event.getObject().getItem();
        //判断该物品是否已经注册为空容器或满容器
        if (FluidContainerRegistry.getEntryForEmptyToFill(item) != null || FluidContainerRegistry.getEntryForFillToEmpty(item) != null)
        {
            event.addCapability(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fluid_handler"), new GenericContainerCapabilityProvider(event.getObject()));
        }
    }
}
