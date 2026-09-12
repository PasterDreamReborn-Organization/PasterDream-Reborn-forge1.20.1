package com.pasterdream.pasterdreammod.config;

import com.pasterdream.pasterdreammod.Config;
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

    // === 融梦能量条 HUD 位置（预设1：左下角） ===
    private static final ForgeConfigSpec.IntValue MELT_DREAM_ENERGY_BAR_X = BUILDER
            .comment("融梦能量条距屏幕左侧的距离（像素），预设1：9")
            .defineInRange("meltDreamEnergyBarX", 9, 0, 3840);

    private static final ForgeConfigSpec.IntValue MELT_DREAM_ENERGY_BAR_Y_FROM_BOTTOM = BUILDER
            .comment("融梦能量条距屏幕底部的距离（像素），预设1：17")
            .defineInRange("meltDreamEnergyBarYFromBottom", 17, 0, 2160);

    // === 融梦能量条显示配置 ===
    private static final ForgeConfigSpec.IntValue MELT_DREAM_ENERGY_BAR_DECIMAL_PLACES = BUILDER
            .comment("融梦能量条数值小数位数（0~4），默认 1")
            .defineInRange("meltDreamEnergyBarDecimalPlaces", 1, 0, 4);

    private static final ForgeConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_TEXT_CENTERED = BUILDER
            .comment("融梦能量条数值是否居中在bar正中央，默认 false（在bar上方）")
            .define("meltDreamEnergyBarTextCentered", false);

    private static final ForgeConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_ALWAYS_SHOW = BUILDER
            .comment("融梦能量条是否常态显示，默认 true。设为 false 时以下两项生效")
            .define("meltDreamEnergyBarAlwaysShow", true);

    private static final ForgeConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_SHOW_ON_CHANGE = BUILDER
            .comment("融梦能量条：数值变化时显示（仅 alwaysShow=false 时生效），默认 true")
            .define("meltDreamEnergyBarShowOnChange", true);

    private static final ForgeConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_SHOW_ON_SNEAK = BUILDER
            .comment("融梦能量条：蹲下时显示（仅 alwaysShow=false 时生效），默认 true")
            .define("meltDreamEnergyBarShowOnSneak", true);

    // === 精神值条 HUD 位置（预设1：右下角） ===
    private static final ForgeConfigSpec.IntValue SAN_BAR_X_FROM_RIGHT = BUILDER
            .comment("精神值条距屏幕右侧的距离（像素），预设1：34")
            .defineInRange("sanBarXFromRight", 34, 0, 3840);

    private static final ForgeConfigSpec.IntValue SAN_BAR_Y_FROM_BOTTOM = BUILDER
            .comment("精神值条距屏幕底部的距离（像素），预设1：30")
            .defineInRange("sanBarYFromBottom", 30, 0, 2160);

    // === 精神值条预设 ===
    private static final ForgeConfigSpec.IntValue SAN_BAR_PRESET = BUILDER
            .comment("""
                    精神值条显示预设：\

                    1=默认（右下角，显示小数），\

                    2=紧凑（居中于血量与饥饿值之间，缩小，仅显示整数）""")
            .defineInRange("sanBarPreset", 1, 1, 2);

    private static final ForgeConfigSpec.BooleanValue SAN_BAR_PRESET1_SNEAK_PRECISE = BUILDER
            .comment("精神值条预设1：是否仅在下蹲时显示数值，默认 false（始终显示）")
            .define("sanBarPreset1SneakPrecise", false);

    private static final ForgeConfigSpec.BooleanValue SAN_BAR_PRESET1_SNEAK_SHOW_BAR = BUILDER
            .comment("精神值条预设1：是否仅在下蹲时显示SAN条，默认 false（始终显示）")
            .define("sanBarPreset1SneakShowBar", false);

    private static final ForgeConfigSpec.IntValue SAN_BAR_PRESET1_DECIMAL_PLACES = BUILDER
            .comment("精神值条预设1：数值小数位数（0~4），默认 4")
            .defineInRange("sanBarPreset1DecimalPlaces", 4, 0, 4);

    private static final ForgeConfigSpec.DoubleValue SAN_BAR_PRESET2_SCALE = BUILDER
            .comment("精神值条预设2的缩放比例，默认 0.5（28×26 缩小为 14×13）")
            .defineInRange("sanBarPreset2Scale", 0.5, 0.1, 1.5);

    private static final ForgeConfigSpec.DoubleValue SAN_BAR_PRESET2_LOW_THRESHOLD = BUILDER
            .comment("精神值条预设2的低SAN警告阈值（ratio = 当前SAN / 最大SAN），低于该值时数字变红、bar抖动，默认 0.2（20%）")
            .defineInRange("sanBarPreset2LowThreshold", 0.2, 0.0, 1.0);

    private static final ForgeConfigSpec.BooleanValue SAN_BAR_PRESET2_SNEAK_SHOW_IN_CREATIVE = BUILDER
            .comment("精神值条预设2：创造模式下是否仅在下蹲时显示，默认 true")
            .define("sanBarPreset2SneakShowInCreative", true);

    // === 疯狂状态效果的屏幕效果设置 ===
    private static final ForgeConfigSpec.BooleanValue LOW_SAN_OVERLAY = BUILDER
            .comment("疯狂状态效果的全屏画面叠加效果，默认 true。"
                    + "\n指令 /pasterdreamdebug lowsan overlay 可运行时临时切换，重进存档后按此配置恢复")
            .define("lowSanOverlay", true);

    private static final ForgeConfigSpec.BooleanValue LOW_SAN_JITTER = BUILDER
            .comment("疯狂状态效果的视角抖动，默认 true。"
                    + "\n指令 /pasterdreamdebug lowsan jitter 可运行时临时切换，重进存档后按此配置恢复")
            .define("lowSanJitter", true);

    private static final ForgeConfigSpec.BooleanValue LOW_SAN_SOUND = BUILDER
            .comment("疯狂状态效果的循环音效，默认 true。"
                    + "\n指令 /pasterdreamdebug lowsan sound 可运行时临时切换，重进存档后按此配置恢复")
            .define("lowSanSound", true);

    // === 光影兼容 ===
    public static final ForgeConfigSpec.BooleanValue SHADER_BLOCK_INJECTION = BUILDER
            .comment("检测到光影生效时，把本模组的植物/树叶注入到光影的方块 ID 映射中，让它们跟随光影飘动。"
                    + "\n此操作直接修改 Oculus/Iris 内部状态（非官方 API），默认 true。")
            .define("shaderBlockInjection", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean loadingGuiTips;

    // === 融梦能量条 & 精神值条 HUD ===
    public static int meltDreamEnergyBarX;
    public static int meltDreamEnergyBarYFromBottom;
    public static int meltDreamEnergyBarDecimalPlaces;
    public static boolean meltDreamEnergyBarTextCentered;
    public static boolean meltDreamEnergyBarAlwaysShow;
    public static boolean meltDreamEnergyBarShowOnChange;
    public static boolean meltDreamEnergyBarShowOnSneak;
    public static int sanBarXFromRight;
    public static int sanBarYFromBottom;
    public static int sanBarPreset;
    public static boolean sanBarPreset1SneakPrecise;
    public static boolean sanBarPreset1SneakShowBar;
    public static int sanBarPreset1DecimalPlaces;
    public static double sanBarPreset2Scale;
    public static double sanBarPreset2LowThreshold;
    public static boolean sanBarPreset2SneakShowInCreative;

    // === 疯狂状态效果的屏幕效果设置 ===
    public static boolean lowSanOverlay;
    public static boolean lowSanJitter;
    public static boolean lowSanSound;

    // === 光影兼容 ===
    public static boolean shaderBlockInjection;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) return;

        loadingGuiTips = LOADING_GUI_TIPS.get();
        meltDreamEnergyBarX = MELT_DREAM_ENERGY_BAR_X.get();
        meltDreamEnergyBarYFromBottom = MELT_DREAM_ENERGY_BAR_Y_FROM_BOTTOM.get();
        meltDreamEnergyBarDecimalPlaces = MELT_DREAM_ENERGY_BAR_DECIMAL_PLACES.get();
        meltDreamEnergyBarTextCentered = MELT_DREAM_ENERGY_BAR_TEXT_CENTERED.get();
        meltDreamEnergyBarAlwaysShow = MELT_DREAM_ENERGY_BAR_ALWAYS_SHOW.get();
        meltDreamEnergyBarShowOnChange = MELT_DREAM_ENERGY_BAR_SHOW_ON_CHANGE.get();
        meltDreamEnergyBarShowOnSneak = MELT_DREAM_ENERGY_BAR_SHOW_ON_SNEAK.get();
        sanBarXFromRight = SAN_BAR_X_FROM_RIGHT.get();
        sanBarYFromBottom = SAN_BAR_Y_FROM_BOTTOM.get();
        sanBarPreset = SAN_BAR_PRESET.get();
        sanBarPreset1SneakPrecise = SAN_BAR_PRESET1_SNEAK_PRECISE.get();
        sanBarPreset1SneakShowBar = SAN_BAR_PRESET1_SNEAK_SHOW_BAR.get();
        sanBarPreset1DecimalPlaces = SAN_BAR_PRESET1_DECIMAL_PLACES.get();
        sanBarPreset2Scale = SAN_BAR_PRESET2_SCALE.get();
        sanBarPreset2LowThreshold = SAN_BAR_PRESET2_LOW_THRESHOLD.get();
        sanBarPreset2SneakShowInCreative = SAN_BAR_PRESET2_SNEAK_SHOW_IN_CREATIVE.get();
        lowSanOverlay = LOW_SAN_OVERLAY.get();
        lowSanJitter = LOW_SAN_JITTER.get();
        lowSanSound = LOW_SAN_SOUND.get();

        shaderBlockInjection = SHADER_BLOCK_INJECTION.get();

        // 将客户端配置作为低 San 效果运行时值的初始来源（/pasterdreamdebug lowsan 指令可在运行时覆盖）
        Config.lowSanOverlay = lowSanOverlay;
        Config.lowSanJitter = lowSanJitter;
        Config.lowSanSound = lowSanSound;
    }
}
