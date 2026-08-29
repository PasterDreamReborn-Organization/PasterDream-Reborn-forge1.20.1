package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FluidContainerCapabilityHandler
{
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event)
    {
        ItemStack itemStack = event.getObject();

        //判断该物品是否已经注册为空容器或满容器
        if (FluidContainerRegistry.getEntryForEmptyToFill(itemStack) != null || FluidContainerRegistry.getEntryForFillToEmpty(itemStack) != null)
        {
            event.addCapability(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fluid_handler"), new GenericContainerCapabilityProvider(event.getObject()));
        }
    }
}
