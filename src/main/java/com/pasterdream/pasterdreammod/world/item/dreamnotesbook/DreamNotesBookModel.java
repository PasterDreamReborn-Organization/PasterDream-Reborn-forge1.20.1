package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DreamNotesBookModel implements BakedModel
{
    private final BakedModel original;
    private final ItemOverrides itemOverrides;

    public DreamNotesBookModel(BakedModel original)
    {
        this.original = original;
        this.itemOverrides = new DreamNotesBookOverrides();
    }

    @Override
    public ItemOverrides getOverrides()
    {
        return itemOverrides;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand)
    {
        return original.getQuads(state, side, rand);
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
        return original.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon()
    {
        return original.getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms()
    {
        return original.getTransforms();
    }
}
