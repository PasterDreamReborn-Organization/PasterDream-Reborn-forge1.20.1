package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.dimension.DyedreamDimension;
import com.pasterdream.pasterdreammod.world.dimension.WindJourneyDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Minecraft.class)
public class SituationalMusicMixin {

    @Inject(method = "getSituationalMusic", at = @At("RETURN"), cancellable = true)
    private void pasterdream$injectDreamMusic(CallbackInfoReturnable<Music> cir) {
        Music result = cir.getReturnValue();
        Minecraft mc = (Minecraft) (Object) this;
        LocalPlayer player = mc.player;
        if (player == null) return;

        if (!player.level().dimension().equals(DyedreamDimension.DYEDREAM_WORLD)
                && !player.level().dimension().equals(WindJourneyDimension.WIND_JOURNEY_WORLD)) return;

        Holder<Biome> biome = player.level().getBiome(player.blockPosition());
        Optional<Music> biomeMusic = biome.value().getBackgroundMusic();

        if (biomeMusic.isPresent()) {
            Music wanted = biomeMusic.get();
            if (result == null || !result.getEvent().unwrapKey()
                    .equals(wanted.getEvent().unwrapKey())) {
                cir.setReturnValue(wanted);
            }
        }
    }
}
