package com.pasterdream.pasterdreammod.helper.RenderHelper;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public class RendFluidStackTexture
{
    public static void rendFluidTexture(GuiGraphics guiGraphics, int x, int y, FluidStack fluidStack)
    {
        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluidStack.getFluid().getFluidType());
        ResourceLocation stillTexture = extensions.getStillTexture(fluidStack);
        if (stillTexture == null)
        {
            return;
        }

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        if (sprite == null)
        {
            return;
        }

        int color = extensions.getTintColor(fluidStack);
        float alpha = ((color >> 24) & 0xFF) / 255f;
        float red = ((color >> 16) & 0xFF) / 255f;
        float green = ((color >> 8) & 0xFF) / 255f;
        float blue = (color & 0xFF) / 255f;
        RenderSystem.setShaderColor(red, green, blue, alpha);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 0);
        guiGraphics.pose().scale(1, 1, 1);
        guiGraphics.blit(x, y, 0, 16, 16, sprite);
        guiGraphics.pose().popPose();

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
