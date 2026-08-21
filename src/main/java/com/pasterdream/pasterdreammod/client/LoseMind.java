package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

/**
 * 低理智时全屏画面叠加 + 视角抖动，根据疯狂等级渐进。
 */
@OnlyIn(Dist.CLIENT)
public class LoseMind {

    private static final Minecraft MC = Minecraft.getInstance();
    private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "textures/screens/lose_mind_gui.png");

    /**
     * 每级的 target mind 值（alpha 上限）和趋近速度。
     * [level] → { targetMind, approachSpeed }
     */
    private static final float[][] MIND_PARAMS = {
            {0.25F, 0.003F},  // Lv0: 轻微
            {0.55F, 0.005F},  // Lv1: 中等
            {1.00F, 0.008F},  // Lv2: 完全
    };

    /** Lv2 音效播放间隔（tick），匹配音效时长 69s */
    private static final int SOUND_INTERVAL = 1380;
    private static int lastSoundTick = -SOUND_INTERVAL;
    private static boolean wasInSoundRange = false;

    public static final IGuiOverlay GUI_OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {
        if (MC.player == null || MC.options.hideGui) return;

        var player = MC.player;
        MobEffectInstance insane = player.getEffect(ModEffects.INSANE.get());
        boolean inSoundRange = insane != null && insane.getAmplifier() >= 1; // II级及以上

        if (inSoundRange && Config.lowSanSound) {
            if (!wasInSoundRange || player.tickCount - lastSoundTick >= SOUND_INTERVAL) {
                player.playSound(ModSounds.LOSEMIND0.get(), 0.8F, 1.0F);
                lastSoundTick = player.tickCount;
            }
        } else if (wasInSoundRange) {
            MC.getSoundManager().stop(ModSounds.LOSEMIND0.getId(), null);
        }
        wasInSoundRange = inSoundRange;

        if (insane != null) {
            int lv = Mth.clamp(insane.getAmplifier(), 0, MIND_PARAMS.length - 1);
            if (lv > 0 && Config.lowSanJitter) {
                applyJitter(player, lv);
            }
            if (Config.lowSanOverlay) {
                renderOverlay(guiGraphics, player, lv, width, height);
            }
        }
    };

    private static void renderOverlay(GuiGraphics guiGraphics, net.minecraft.world.entity.player.Player player,
                                       int lv, int width, int height) {
        float targetMind = MIND_PARAMS[lv][0];
        float speed = MIND_PARAMS[lv][1];

        var nbt = player.getPersistentData();
        float mind = nbt.getFloat("mind");

        if (mind < targetMind) {
            mind = Math.min(mind + speed, targetMind);
        } else {
            // San 恢复后 mind 衰减
            mind = Math.max(mind - 0.01F, 0.0F);
        }

        nbt.putFloat("mind", mind);

        if (mind > 0.01F) {
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, mind);
            guiGraphics.blit(ICON, 0, 0, 0.0F, 0.0F, width, height, width, height);
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private static void applyJitter(net.minecraft.world.entity.player.Player player, int lv) {
        // Lv1: 轻微, Lv2: 中等
        double yawJitter = lv == 1 ? 0.3 : 1.0;
        double pitchJitter = lv == 1 ? 0.2 : 0.7;
        RandomSource random = player.level().getRandom();
        player.setYRot((float) (player.getYRot() + Mth.nextDouble(random, -yawJitter, yawJitter)));
        player.setXRot((float) (player.getXRot() + Mth.nextDouble(random, -pitchJitter, pitchJitter)));
        player.setYBodyRot(player.getYRot());
        player.setYHeadRot(player.getYRot());
        player.yRotO = player.getYRot();
        player.xRotO = player.getXRot();
        player.yBodyRotO = player.getYRot();
        player.yHeadRotO = player.getYRot();
    }
}
