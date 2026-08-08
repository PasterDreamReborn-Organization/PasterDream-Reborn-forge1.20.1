package com.pasterdream.pasterdreammod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

/** 回春治疗粒子 —— 绿色十字，8帧，向上飘 */
public class RejuvenationParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    protected RejuvenationParticle(ClientLevel level, double x, double y, double z,
                                    double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.quadSize *= 0.6f;        // → 0.12
        this.lifetime = 32;
        this.gravity = -0.1f;         // 向上飘
        this.hasPhysics = false;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get((this.age / 4) % 8 + 1, 8));
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
            return new RejuvenationParticle(l, x, y, z, xs, ys, zs, spriteSet);
        }
    }
}
