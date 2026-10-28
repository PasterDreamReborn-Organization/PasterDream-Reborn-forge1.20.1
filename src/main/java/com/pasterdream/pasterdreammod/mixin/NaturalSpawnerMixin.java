package com.pasterdream.pasterdreammod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.concurrent.ThreadLocalRandom;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {

    @WrapOperation(method = "getRandomPosWithin",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"))
    private static int safeNextInt(RandomSource instance, int bound, Operation<Integer> original) {
        return ThreadLocalRandom.current().nextInt(bound);
    }
}
