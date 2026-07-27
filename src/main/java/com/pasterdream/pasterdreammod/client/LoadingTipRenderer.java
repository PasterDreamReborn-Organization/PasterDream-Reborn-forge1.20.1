package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.PasterDreamTipsManager;
import com.pasterdream.pasterdreammod.config.PasterDreamClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Random;
import java.util.Set;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LoadingTipRenderer {

    private static String currentTip = "";
    private static final Random RANDOM = new Random();

    private static final Set<Class<? extends Screen>> LOADING_SCREENS = Set.of(
            ConnectScreen.class,
            LevelLoadingScreen.class,
            ProgressScreen.class
    );

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        if (PasterDreamClientConfig.LOADING_GUI_TIPS.get()) {
            MinecraftForge.EVENT_BUS.addListener(LoadingTipRenderer::onScreenRenderPost);
        }
    }

    public static void onScreenRenderPost(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        if (!LOADING_SCREENS.contains(screen.getClass())) {
            currentTip = "";
            return;
        }

        if (currentTip.isEmpty()) {
            currentTip = PasterDreamTipsManager.INSTANCE.getRandomTip(RANDOM);
        }

        GuiGraphics graphics = event.getGuiGraphics();
        Font font = Minecraft.getInstance().font;
        int height = screen.height - 20;

        graphics.drawString(font, currentTip, 10, height, 0xFFFFFFFF);
        graphics.drawString(font, "PasterDream Tip:", 10, height - 10, 0xFFFFFF00);
    }
}
