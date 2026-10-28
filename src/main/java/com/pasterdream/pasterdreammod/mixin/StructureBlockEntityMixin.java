package com.pasterdream.pasterdreammod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StructureBlockEntity.class)
public class StructureBlockEntityMixin
{
    @ModifyExpressionValue(method = "load", at = @At(value = "CONSTANT", args = "intValue=48"))
    private int increaseSizeLimit(int original)
    {
        return 2147483647;
    }

    @ModifyExpressionValue(method = "load", at = @At(value = "CONSTANT", args = "intValue=-48"))
    private int decreaseOffsetLimit(int original)
    {
        return -2147483648;
    }
}
