package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.config.PasterDreamClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.Color;
import java.util.Locale;

/**
 * 帕斯特之梦主题玩家血条：替换原版生命值行，绘制一条血条并叠加当前/最大生命值文本。
 * 数值按 {@link PasterDreamClientConfig#pasterHealthHudDecimalPlaces} 限定小数位数并去除末尾零，
 * 避免属性修饰符浮点误差导致的小数位过长（如 22.000002）。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public class PlayerHealthHud implements IGuiOverlay
{
    private static final Minecraft MC = Minecraft.getInstance();
    public static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "textures/screens/paster_widgets.png");

    private double playerHealth = 0;
    private long healthUpdateCounter = 0;

    @Override
    public void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float partialTick, int width, int height)
    {
        if (!PasterDreamClientConfig.pasterHealthHud) return;
        if (!forgeGui.shouldDrawSurvivalElements()) return;

        Font font = forgeGui.getFont();
        Player player = MC.player;
        if (player == null) return;

        int health = Mth.ceil(player.getHealth());
        float playerMaxHealth = player.getMaxHealth();
        float playerOtherHealth = player.getAbsorptionAmount();

        forgeGui.setupOverlayRenderState(true, false);

        MC.getProfiler().push("pd_health");
        RenderSystem.enableBlend();
        {
            int updateCounter = forgeGui.getGuiTicks();
            boolean highlight = healthUpdateCounter > (long) updateCounter
                    && (healthUpdateCounter - (long) updateCounter) / 3L % 2L == 1L;

            if (player.invulnerableTime > 0 && health != playerHealth)
                healthUpdateCounter = updateCounter + (health < playerHealth ? 20 : 10);

            int flagColor = player.hasEffect(MobEffects.POISON) ? 1 : player.hasEffect(MobEffects.WITHER) ? 2 : 0;

            playerHealth = health;
            int startCount = highlight ? 84 : 0;
            int x = width / 2 - 91;
            int y = height - 40;

            /* Render Bar and Health */
            guiGraphics.blit(ICON, x, y, 0, 0, 85, 10);
            guiGraphics.blit(ICON, x + 1, y + 2, startCount + 1, 10 + 6 * flagColor,
                    (int) (83 * (health / playerMaxHealth)), 6);

            /* Render String */
            String text = formatValue(health + playerOtherHealth) + "/" + formatValue(playerMaxHealth);
            guiGraphics.drawString(font, text, x + ((85 / 2) - (font.width(text) / 2)), y,
                    playerOtherHealth > 0.0F ? Color.YELLOW.getRGB() : Color.WHITE.getRGB());
        }
        forgeGui.leftHeight += 11;

        RenderSystem.disableBlend();
        MC.getProfiler().pop();
    }

    /** 按配置的小数位数格式化，并去除末尾多余的零，避免浮点误差产生的过长小数。 */
    private static String formatValue(double value)
    {
        int decimals = PasterDreamClientConfig.pasterHealthHudDecimalPlaces;
        String text = String.format(Locale.ROOT, "%." + decimals + "f", value);
        if (text.indexOf('.') >= 0)
        {
            text = text.replaceAll("0+$", "");
            if (text.endsWith("."))
                text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    /**
     * 启用主题血条时隐藏原版生命值行（含伤害吸收黄心），避免两条血条重叠。
     * 按 overlay id 比较，避免依赖 {@code VanillaGuiOverlay.type()} 的实例身份。
     */
    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event)
    {
        if (!PasterDreamClientConfig.pasterHealthHud) return;
        if (event.getOverlay().id().equals(VanillaGuiOverlay.PLAYER_HEALTH.id()))
            event.setCanceled(true);
    }
}
