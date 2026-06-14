package com.pasterdream.pasterdreammod.component;

import com.pasterdream.pasterdreammod.helper.RendFluidStackTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FluidSlot extends AbstractWidget
{
    private static final int WIDTH = 18;
    private static final int HEIGHT = 18;
    private final Supplier<FluidStack> fluidSupplier;
    private final Consumer<Integer> clickHandler;

    public FluidSlot(int x, int y, Supplier<FluidStack> fluidSupplier, Consumer<Integer> clickHandler)
    {
        super(x, y, WIDTH, HEIGHT, Component.empty());
        this.fluidSupplier = fluidSupplier;
        this.clickHandler = clickHandler;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        FluidStack fluidStack = fluidSupplier.get();
        if (!fluidStack.isEmpty())
        {
            RendFluidStackTexture.rendFluidTexture(guiGraphics, getX() + 1, getY() + 1, fluidStack);

            String amountString = fluidStack.getAmount() + "mB";

            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(0.5f, 0.5f, 1f);
            guiGraphics.drawString(Minecraft.getInstance().font, amountString, (2 * getX() + 33) - Minecraft.getInstance().font.width(amountString), (2 * getY() + 34) - Minecraft.getInstance().font.lineHeight, 0xFFFFFF, true);
            guiGraphics.pose().popPose();
        }

        if (isHovered)
        {
            guiGraphics.fill(getX() + 1, getY() + 1, getX() + 17, getY() + 17, 0x7FFFFFFF);
            if (!fluidStack.isEmpty())
            {
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(fluidStack.getDisplayName());
                ResourceLocation fluidId = BuiltInRegistries.FLUID.getKey(fluidStack.getFluid());
                tooltip.add(Component.literal("§8" + fluidId));
                tooltip.add(Component.literal("§7" + fluidStack.getAmount() + " mB"));
                String modId = fluidId.getNamespace();
                String modName = net.minecraftforge.fml.ModList.get().getModContainerById(modId).map(mod -> mod.getModInfo().getDisplayName()).orElse(modId);
                tooltip.add(Component.literal("§9§o" + modName));
                guiGraphics.renderTooltip(Minecraft.getInstance().font, tooltip, Optional.empty(), mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (isHovered)
        {
            clickHandler.accept(button);
            return true;
        }
        return false;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        this.isHovered = mouseX >= getX() && mouseY >= getY() && mouseX < getX() + width && mouseY < getY() + height;
        this.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput)
    {

    }
}
