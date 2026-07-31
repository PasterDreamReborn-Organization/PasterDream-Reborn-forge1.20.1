package com.pasterdream.pasterdreammod.mixin;

import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.util.GsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockElement.Deserializer.class)
public class BlockElementMixin
{
    /**
     * @author PasterDream team
     * @reason Remove vanilla angle restriction (-45/-22.5/0/22.5/45) to allow
     *         arbitrary block model rotation angles used by mod features.
     */
    @Overwrite
    private float getAngle(JsonObject json)
    {
        return GsonHelper.getAsFloat(json, "angle");
    }
}
