package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DreamNotesBookOverrides extends ItemOverrides
{
    public DreamNotesBookOverrides()
    {
        super();
    }

    @Override
    public BakedModel resolve(BakedModel original, ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed)
    {
        CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains("content"))
        {
            DreamNotesBookInfo info = DreamNotesBookRegistry.getInfo(tag.getString("content"));
            if (info != null && info.itemTexture() != null)
            {
                return Minecraft.getInstance().getModelManager().getModel(info.itemTexture());
            }
                else
                {
                    throw new RuntimeException("寻梦者笔记" + info.title() + "的itemTexture为null");
                }
        }
        return original;
    }
}
