package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.network.curio.KaichuOmamoriActivationPacket;
import com.pasterdream.pasterdreammod.network.curio.CloakActivationPacket;
import com.pasterdream.pasterdreammod.network.fluffywindalloy.WindHoverInputPacket;
import com.pasterdream.pasterdreammod.network.skill.BlinkPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeyMappings {

    public static final KeyMapping BLINK = new KeyMapping(
            "key.pasterdream.blink", GLFW.GLFW_KEY_C, "key.categories.pasterdream");
    public static final KeyMapping KAICHU_OMAMORI = new KeyMapping(
            "key.pasterdream.kaichu_omamori", GLFW.GLFW_KEY_V, "key.categories.pasterdream");
    public static final KeyMapping CLOAK_ACTIVATE = new KeyMapping(
            "key.pasterdream.cloak_activate", GLFW.GLFW_KEY_Z, "key.categories.pasterdream");

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(BLINK);
        event.register(KAICHU_OMAMORI);
        event.register(CLOAK_ACTIVATE);
    }

    @Mod.EventBusSubscriber({Dist.CLIENT})
    public static class KeyEventListener {

        /** 上次同步给服务端的跳跃键状态（萦风合金盔甲「滞空」用） */
        private static boolean lastJumpSent = false;

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (Minecraft.getInstance().screen == null && Minecraft.getInstance().player != null) {
                while (ModKeyMappings.BLINK.consumeClick()) {
                    ModNetwork.CHANNEL.sendToServer(new BlinkPacket());
                }
                while (ModKeyMappings.KAICHU_OMAMORI.consumeClick()) {
                    ModNetwork.CHANNEL.sendToServer(new KaichuOmamoriActivationPacket());
                }
                while (ModKeyMappings.CLOAK_ACTIVATE.consumeClick()) {
                    ModNetwork.CHANNEL.sendToServer(new CloakActivationPacket());
                }

                boolean jumping = Minecraft.getInstance().options.keyJump.isDown();
                if (jumping != lastJumpSent) {
                    lastJumpSent = jumping;
                    ModNetwork.CHANNEL.sendToServer(new WindHoverInputPacket(jumping));
                }
            }
        }
    }
}
