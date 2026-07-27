package com.pasterdream.pasterdreammod.config;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PasterDreamClientConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Boolean> LOADING_GUI_TIPS = BUILDER
            .comment("在加载界面时会弹出帕斯特之梦的tips 默认：true")
            .define("loading_gui_tips", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean loadingGuiTips;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == SPEC) {
            loadingGuiTips = LOADING_GUI_TIPS.get();
        }
    }
}
