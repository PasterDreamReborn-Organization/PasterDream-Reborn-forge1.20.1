package com.pasterdream.pasterdreammod.world.fluidtype;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Consumer;

public abstract class PasterDreamBaseFluidType extends FluidType
{
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final int tintColor;

    public PasterDreamBaseFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture, int tintColor)
    {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.tintColor = tintColor;
    }

    @Override
    public abstract String getDescriptionId();

    /** 按流体堆返回染色；默认返回构造时的固定色，子类可覆写按 NBT 动态染色 */
    protected int getStackTintColor(FluidStack stack)
    {
        return this.tintColor;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
    {
        consumer.accept(new IClientFluidTypeExtensions()
        {
            @Override
            public ResourceLocation getStillTexture()
            {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return flowingTexture;
            }

            @Override
            public int getTintColor()
            {
                return tintColor;
            }

            @Override
            public int getTintColor(FluidStack stack)
            {
                return getStackTintColor(stack);
            }
        });
    }
}
