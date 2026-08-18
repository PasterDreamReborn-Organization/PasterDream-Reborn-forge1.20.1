package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.ElixirBottleOfPotionItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
        Item item = event.getObject().getItem();
        // 药水灵药瓶（满瓶）：物品内置 FluidTank 承载「药水」流体（具体药水记录在流体 NBT 的 "Potion" 键）
        if (item == ModItems.ELIXIR_BOTTLE_OF_POTION.get())
        {
            event.addCapability(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "potion_fluid_handler"), new GenericContainerCapabilityProvider(() -> new FluidTankItemHandler(event.getObject(), ElixirBottleOfPotionItem.CAPACITY, fluid -> fluid.getFluid() == ModFluids.POTION.get(), ElixirBottleOfPotionItem.TAG_FLUID, () -> new ItemStack(ModItems.ELIXIR_BOTTLE.get()))));
            return;
        }
        // 空灵药瓶：既可注入通用「药水」流体，也可按容器关系注入狂暴/融梦等流体
        if (item == ModItems.ELIXIR_BOTTLE.get())
        {
            event.addCapability(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "elixir_bottle_fluid_handler"), new GenericContainerCapabilityProvider(() -> new ElixirBottleFluidHandler(event.getObject())));
            return;
        }
        //判断该物品是否已经注册为空容器或满容器
        if (FluidContainerRegistry.getEntryForEmptyToFill(item) != null || FluidContainerRegistry.getEntryForFillToEmpty(item) != null)
        {
            event.addCapability(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fluid_handler"), new GenericContainerCapabilityProvider(event.getObject()));
        }
    }
}
