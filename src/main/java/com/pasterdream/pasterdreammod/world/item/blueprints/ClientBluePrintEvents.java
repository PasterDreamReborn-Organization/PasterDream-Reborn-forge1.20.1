package com.pasterdream.pasterdreammod.world.item.blueprints;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "pasterdream", value = Dist.CLIENT)
public class ClientBluePrintEvents
{
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END)
        {
            ClientBluePrintPlacement.tick();
        }
    }
}
