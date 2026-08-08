package com.pasterdream.pasterdreammod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

/** 狂暴粒子 —— 10帧动画，每4tick切换 */
public class FurySpellParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    protected FurySpellParticle(ClientLevel level, double x, double y, double z,
                                 double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.quadSize *= 1.3f;
        this.lifetime = 40 + (this.random.nextInt(11) - 5);
        this.gravity = 0f;
        this.hasPhysics = true;
        this.xd = xSpeed * 0.02;
        this.yd = ySpeed * 0.02;
        this.zd = zSpeed * 0.02;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get((this.age / 4) % 10 + 1, 10));
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public Provider(SpriteSet s) { this.spriteSet = s; }
        public Particle createParticle(SimpleParticleType t, ClientLevel l, double x, double y, double z,
                                       double xs, double ys, double zs) {
            return new FurySpellParticle(l, x, y, z, xs, ys, zs, spriteSet);
        }
    }
}
