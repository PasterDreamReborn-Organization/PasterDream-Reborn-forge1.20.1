package com.pasterdream.pasterdreammod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerboundSetStructureBlockPacket.class)
public class ServerboundSetStructureBlockPacketMixin
{
    @ModifyExpressionValue(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At(value = "CONSTANT", args = "intValue=48"))
    private int increaseSizeLimit(int original)
    {
        return 2147483647;
    }

    @ModifyExpressionValue(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At(value = "CONSTANT", args = "intValue=-48"))
    private int decreaseOffsetMin(int original)
    {
        return -2147483648;
    }
}
