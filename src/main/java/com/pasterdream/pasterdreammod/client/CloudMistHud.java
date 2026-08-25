package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.world.dimension.WindJourneyDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

/**
 * 云雾 HUD：进入/退出风之旅途时根据玩家高度显示渐进的云雾贴图。
 * 进度由客户端按「效果 + 维度 + 高度」实时计算，不依赖服务端 NBT 同步。
 */
@OnlyIn(Dist.CLIENT)
public class CloudMistHud {

    private static final Minecraft MC = Minecraft.getInstance();
    private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "textures/screens/cloud_mist_hud.png");

    public static final IGuiOverlay GUI_OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {
        if (MC.player == null || MC.options.hideGui) return;
        if (MC.player.getVehicle() instanceof LivingEntity) return;

        double mind = computeCloudMist(MC.player);

        if (mind > 0.01) {
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, (float) mind);
            guiGraphics.blit(ICON, 0, 0, 0.0F, 0.0F, width, height, width, height);
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    };

    private static double computeCloudMist(Player player) {
        // 进入：主世界持有迷梦，高度 260~310 之间
        if (player.hasEffect(ModEffects.MISTY_DREAM.get())
                && player.level().dimension().equals(Level.OVERWORLD)
                && player.getY() > 260 && player.getY() <= 310) {
            return (player.getY() - 260) * 2 / 100.0;
        }
        // 退出：风之旅途持有云雾，高度 0~50 之间
        if (player.hasEffect(ModEffects.CLOUD_MIST.get())
                && player.level().dimension().equals(WindJourneyDimension.WIND_JOURNEY_WORLD)
                && player.getY() > 0 && player.getY() <= 50) {
            return (50 - player.getY()) * 2 / 100.0;
        }
        return 0.0;
    }
}
