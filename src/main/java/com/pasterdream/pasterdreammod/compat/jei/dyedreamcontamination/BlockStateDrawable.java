package com.pasterdream.pasterdreammod.compat.jei.dyedreamcontamination;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;

/** 在 JEI 中把方块模型渲染成 16x16 图标（用于无对应物品的方块）。 */
public class BlockStateDrawable implements IDrawable {
    private final BlockState state;
    private final float brightness;

    public BlockStateDrawable(BlockState state) {
        this(state, 1.0F);
    }

    public BlockStateDrawable(BlockState state, float brightness) {
        this.state = state;
        this.brightness = brightness;
    }

    @Override
    public int getWidth() {
        return 16;
    }

    @Override
    public int getHeight() {
        return 16;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(xOffset + 8.0F, yOffset + 8.0F, 150.0F);
        pose.scale(16.0F, -16.0F, 16.0F);
        pose.translate(-0.5F, -0.5F, -0.5F);
        RenderSystem.setShaderColor(brightness, brightness, brightness, 1.0F);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                state, pose, guiGraphics.bufferSource(), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        guiGraphics.flush();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        pose.popPose();
    }
}
