package com.pasterdream.pasterdreammod.client.audio;

import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.entity.AaroncosLeftHandEntity;
import com.pasterdream.pasterdreammod.world.entity.AaroncosRightHandEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class BossMusicHandler {
    public static BossMusicInstance currentMusic;

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Mob mob))
            return;
        if (currentMusic != null && currentMusic.isStopped())
            currentMusic = null;
        if (currentMusic != null)
            return;

        if (mob instanceof AaroncosLeftHandEntity || mob instanceof AaroncosRightHandEntity) {
            currentMusic = new BossMusicInstance(
                    ModSounds.AARONCOS_MUSIC.get(), mob, 0.75F, 1.0F);
            Minecraft.getInstance().getSoundManager().play(currentMusic);
        }
    }
}
