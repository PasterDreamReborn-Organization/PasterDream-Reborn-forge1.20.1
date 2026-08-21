package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin {

    @Inject(method = "cookTick", at = @At("TAIL"))
    private static void onServerTick(Level level, BlockPos pos, BlockState state,
                                      CampfireBlockEntity blockEntity, CallbackInfo ci) {
        if (!state.getValue(CampfireBlock.LIT)) return;
        if (level.getGameTime() % 80 != 0) return;

        int range = 5;
        AABB area = new AABB(pos).inflate(range);
        for (Player player : level.getEntitiesOfClass(Player.class, area)) {
            player.addEffect(new MobEffectInstance(ModEffects.REST.get(),
                    200, 0, false, false));
        }
    }
}
