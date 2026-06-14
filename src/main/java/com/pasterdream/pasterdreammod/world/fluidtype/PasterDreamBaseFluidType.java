package com.pasterdream.pasterdreammod.world.fluidtype;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
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
        });
    }
}
