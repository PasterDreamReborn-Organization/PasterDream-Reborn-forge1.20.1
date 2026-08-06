package com.pasterdream.pasterdreammod.client.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;

public class BossMusicInstance extends AbstractTickableSoundInstance {
    private final SoundEvent soundEvent;
    private final Mob boss;
    private final float maxVolume;
    private int fadeTicks;

    public BossMusicInstance(SoundEvent sound, Mob boss, float volume, float pitch) {
        super(sound, SoundSource.RECORDS, SoundInstance.createUnseededRandom());
        this.soundEvent = sound;
        this.boss = boss;
        this.maxVolume = volume;
        this.x = boss.getX();
        this.y = boss.getY();
        this.z = boss.getZ();
        this.looping = true;
        this.delay = 0;
        this.fadeTicks = 80;
        this.volume = 0.01F;
        this.pitch = pitch;
    }

    @Override
    public boolean canPlaySound() {
        return BossMusicHandler.currentMusic == this;
    }

    @Override
    public void tick() {
        if (!boss.isAlive()) {
            fadeTicks = 0;
            if (boss.level().isClientSide) {
                Minecraft mc = Minecraft.getInstance();
                if (!isStopped()) {
                    mc.getSoundManager().play(new PostBossMusicInstance(soundEvent, boss, maxVolume, pitch));
                }
            }
        }

        boolean hasTarget = boss.getTarget() != null && boss.getTarget().isAlive();

        if (hasTarget && !boss.isDeadOrDying() && boss.isAlive()) {
            if (volume < maxVolume) {
                volume = Math.min(maxVolume, volume + maxVolume / 40.0F);
            }
            fadeTicks = 80;
        } else {
            if (fadeTicks > 0) {
                fadeTicks--;
            } else {
                volume = Math.max(0, volume - maxVolume / 40.0F);
            }
        }

        this.x = boss.getX();
        this.y = boss.getY();
        this.z = boss.getZ();

        if (volume < 0.005F) {
            BossMusicHandler.currentMusic = null;
            stop();
        }

    }
}
