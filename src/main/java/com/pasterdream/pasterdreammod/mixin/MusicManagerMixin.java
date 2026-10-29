package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.dimension.DyedreamDimension;
import com.pasterdream.pasterdreammod.world.dimension.WindJourneyDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public class MusicManagerMixin {

    @Shadow @Final private Minecraft minecraft;
    @Shadow private int nextSongDelay;

    @Inject(method = "tick", at = @At("RETURN"))
    private void pasterdream$adjustMusicDelay(CallbackInfo ci) {
        MusicManager musicManager = (MusicManager)(Object)this;

        LocalPlayer player = musicManager.minecraft.player;

        if (player == null) return;
        if (!player.level().dimension().equals(DyedreamDimension.DYEDREAM_WORLD)
                && !player.level().dimension().equals(WindJourneyDimension.WIND_JOURNEY_WORLD)) return;

        // 有一定概率播放MC原版音乐，此时不干预间隔
        if (player.level().random.nextFloat() < 0.35f) {
            return;
        }

        if (musicManager.nextSongDelay > 1200) {
            musicManager.nextSongDelay = 1200;
        }
    }
}
