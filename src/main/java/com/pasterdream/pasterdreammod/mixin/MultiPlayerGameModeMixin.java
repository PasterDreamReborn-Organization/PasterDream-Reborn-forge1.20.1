package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin
{
    @Inject(method = "interact", at = @At("RETURN"), cancellable = true)
    private void pasterdream$forceConsume(Player player, Entity target, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir)
    {
        if (player.getItemInHand(hand).getItem() instanceof DebugToolItem)
        {
            InteractionResult result = cir.getReturnValue();
            if (result == null || !result.consumesAction())
            {
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}
