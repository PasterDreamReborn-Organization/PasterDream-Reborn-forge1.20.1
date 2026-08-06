package com.pasterdream.pasterdreammod.client.audio;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;

public class PostBossMusicInstance extends AbstractTickableSoundInstance {
    private final Mob boss;

    public PostBossMusicInstance(SoundEvent sound, Mob boss, float volume, float pitch) {
        super(sound, SoundSource.RECORDS, SoundInstance.createUnseededRandom());
        this.boss = boss;
        this.x = boss.getX();
        this.y = boss.getY();
        this.z = boss.getZ();
        this.looping = false;
        this.delay = 0;
        this.volume = volume * 0.6F;
        this.pitch = pitch;
    }

    @Override
    public boolean canPlaySound() {
        return BossMusicHandler.currentMusic == null;
    }

    @Override
    public void tick() {
        if (!boss.isAlive() || !canPlaySound()) {
            stop();
        }
        this.x = boss.getX();
        this.y = boss.getY();
        this.z = boss.getZ();
    }
}
