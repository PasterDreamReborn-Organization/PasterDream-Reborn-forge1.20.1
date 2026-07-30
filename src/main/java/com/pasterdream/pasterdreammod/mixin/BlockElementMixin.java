package com.pasterdream.pasterdreammod.mixin;

import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.util.GsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockElement.Deserializer.class)
public class BlockElementMixin
{
    @Overwrite
    private float getAngle(JsonObject json)
    {
        return GsonHelper.getAsFloat(json, "angle");
    }
}
