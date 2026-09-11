package com.pasterdream.pasterdreammod.mixin;

import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.util.GsonHelper;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = BlockElement.Deserializer.class, priority = -2147483648)
public class BlockElementMixin
{
    //移除方块对于-45°,-22.5°,0°,22.5°,45°旋转角度的限制。
    //用 HEAD @Inject 短路，保留原方法体与其他模组对该方法的注入（避免 @Overwrite 冲突）。
    @Inject(method = "getAngle", at = @At("HEAD"), cancellable = true)
    private void shutUpAngleLimit(JsonObject json, CallbackInfoReturnable<Float> cir)
    {
        cir.setReturnValue(GsonHelper.getAsFloat(json, "angle"));
    }

    //这两个直接复制机械动力：汽鸣铁道的mixin，这样能解决之前@Overwrite导致机械动力：汽鸣铁道@Inject注入不进去的问题
    @Inject(method = "getFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/BlockElement$Deserializer;getVector3f(Lcom/google/gson/JsonObject;Ljava/lang/String;)Lorg/joml/Vector3f;", shift = At.Shift.BY, by = 2), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void shutUpSizeLimitFrom(JsonObject json, CallbackInfoReturnable<Vector3f> cir, Vector3f vector3f)
    {
        cir.setReturnValue(vector3f);
    }

    @Inject(method = "getTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/BlockElement$Deserializer;getVector3f(Lcom/google/gson/JsonObject;Ljava/lang/String;)Lorg/joml/Vector3f;", shift = At.Shift.BY, by = 2), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void shutUpSizeLimitTo(JsonObject json, CallbackInfoReturnable<Vector3f> cir, Vector3f vector3f)
    {
        cir.setReturnValue(vector3f);
    }
}
