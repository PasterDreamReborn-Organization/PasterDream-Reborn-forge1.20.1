package com.pasterdream.pasterdreammod.compat.shaders;

import com.mojang.logging.LogUtils;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.config.PasterDreamClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.Map;

/**
 * 客户端光影兼容驱动：
 * <ul>
 *     <li>周期性尝试把本模组植物/树叶注入光影的方块 ID 映射（见 {@link ShaderBlockInjector}）。</li>
 *     <li>进入世界后输出一次光影包配置报告。</li>
 * </ul>
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public final class ShaderCompatEvents
{
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final int INITIAL_DELAY_TICKS = 40;
    private static final int RETRY_DELAY_TICKS = 20;
    private static final int MAX_RETRIES = 5;
    private static final int INJECT_CHECK_INTERVAL = 10;

    private static boolean reported;
    private static int countdown = -1;
    private static int retries;
    private static int injectTicker;

    private ShaderCompatEvents() {}

    @SubscribeEvent
    public static void onClientLogin(ClientPlayerNetworkEvent.LoggingIn event)
    {
        if (!ShaderCompat.isShaderModLoaded()) return;
        reported = false;
        retries = MAX_RETRIES;
        countdown = INITIAL_DELAY_TICKS;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END) return;
        if (!ShaderCompat.isShaderModLoaded()) return;

        // 周期性注入（幂等，换包/重载后会自动重新注入）
        if (++injectTicker >= INJECT_CHECK_INTERVAL)
        {
            injectTicker = 0;
            ShaderBlockInjector.tryInject();
        }

        if (countdown < 0) return;
        if (Minecraft.getInstance().level == null) return;
        if (countdown-- > 0) return;

        if (ShaderCompat.isShaderPackInUse())
        {
            reportIfShadersInUse();
            countdown = -1;
        }
        else if (retries-- > 0)
        {
            countdown = RETRY_DELAY_TICKS;
        }
        else
        {
            countdown = -1;
        }
    }

    /** 若光影正在生效，读取光影包配置并输出一次报告。 */
    public static void reportIfShadersInUse()
    {
        if (reported || !ShaderCompat.isShaderPackInUse()) return;
        reported = true;

        ShaderCompat.getConfiguredPackName().ifPresentOrElse(name ->
        {
            Map<Integer, String> mapped = ShaderPackConfig.findBlockIdsByNamespace(PasterDreamMod.MOD_ID);
            if (mapped.isEmpty())
            {
                LOGGER.info("[ShaderCompat] 光影包 '{}' 生效中：包内 block.properties 未映射 pasterdream 方块；"
                        + "方块动效注入={}。", name, PasterDreamClientConfig.shaderBlockInjection);
            }
            else
            {
                LOGGER.info("[ShaderCompat] 光影包 '{}' 生效中：包内已映射 {} 个 pasterdream 方块 -> {}",
                        name, mapped.size(), mapped);
            }
        }, () -> LOGGER.info("[ShaderCompat] 光影生效中，但无法获取当前光影包名"));
    }
}
