package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class BrightButterflyCurioClientHandler {

    private static boolean wasOverriding = false;
    private static double originalGamma = 0.0;
    private static final double GAMMA_OVERRIDE = 15.0;
    private static Field valueField;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        boolean hasCurio = CuriosApi.getCuriosInventory(mc.player)
                .map(h -> h.findFirstCurio(ModItems.BRIGHT_BUTTERFLY_CURIO.get()).isPresent())
                .orElse(false);

        if (hasCurio && !wasOverriding) {
            originalGamma = mc.options.gamma().get();
            setGammaUnchecked(mc.options.gamma(), GAMMA_OVERRIDE);
            wasOverriding = true;
        } else if (!hasCurio && wasOverriding) {
            setGammaUnchecked(mc.options.gamma(), originalGamma);
            wasOverriding = false;
        }
    }

    private static void setGammaUnchecked(OptionInstance<Double> option, double value) {
        try {
            if (valueField == null) {
                valueField = OptionInstance.class.getDeclaredField("value");
                valueField.setAccessible(true);
            }
            valueField.set(option, value);
        } catch (Exception ignored) {
        }
    }
}
