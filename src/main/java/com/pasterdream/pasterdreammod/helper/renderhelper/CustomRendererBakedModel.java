package com.pasterdream.pasterdreammod.helper.renderhelper;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomRendererBakedModel implements IDynamicBakedModel
{
    private final BakedModel original;

    public CustomRendererBakedModel(BakedModel original)
    {
        this.original = original;
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType)
    {
        return original.getQuads(state, side, rand, data, renderType);
    }

    @Override
    public boolean useAmbientOcclusion()
    {
        return original.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d()
    {
        return original.isGui3d();
    }

    @Override
    public boolean usesBlockLight()
    {
        return original.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer()
    {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleIcon()
    {
        return original.getParticleIcon();
    }

    @Override
    public ItemOverrides getOverrides()
    {
        return original.getOverrides();
    }
}
