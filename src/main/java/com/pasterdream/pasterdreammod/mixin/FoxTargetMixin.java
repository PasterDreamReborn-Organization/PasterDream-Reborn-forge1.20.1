package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.entity.PinkChickenEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Fox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Fox.class)
public class FoxTargetMixin {

    @Inject(method = "setTargetGoals", at = @At("TAIL"))
    private void onSetTargetGoals(CallbackInfo ci) {
        Fox self = (Fox) (Object) this;
        self.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(
                self, PinkChickenEntity.class, 10, true, true, null));
    }
}
