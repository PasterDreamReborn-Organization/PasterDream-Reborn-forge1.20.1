package com.pasterdream.pasterdreammod.mixin;

import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.util.GsonHelper;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockElement.Deserializer.class)
public class BlockElementMixin
{
    /**
     * @author PasterDream team
     * @reason 移除方块对于-45°,-22.5°,0°,22.5°,45°旋转角度的限制
     */
    @Overwrite
    private float getAngle(JsonObject json)
    {
        return GsonHelper.getAsFloat(json, "angle");
    }

    @Shadow
    private Vector3f getVector3f(JsonObject p_111335_, String p_111336_)
    {
        return null;
    }

    /**
     * @author PasterDream team
     * @reason 移除方块对于[-16,32]像素范围的限制
     */
    @Overwrite
    private Vector3f getTo(JsonObject json)
    {
        return getVector3f(json, "to");
    }

    /**
     * @author PasterDream team
     * @reason 移除方块对于[-16,32]像素范围的限制
     */
    @Overwrite
    private Vector3f getFrom(JsonObject json)
    {
        return getVector3f(json, "from");
    }
}
