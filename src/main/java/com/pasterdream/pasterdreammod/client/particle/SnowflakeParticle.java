package com.pasterdream.pasterdreammod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

/** 通用雪花粒子 —— 浮空飘落，渐隐 */
public class SnowflakeParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    protected SnowflakeParticle(ClientLevel level, double x, double y, double z,
                                 double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.quadSize *= 0.8f;
        this.lifetime = 30 + this.random.nextInt(30);
        this.gravity = 0.02f;
        this.hasPhysics = false;
        this.xd = xSpeed * 0.5 + (Math.random() - 0.5) * 0.05;
        this.yd = ySpeed * 0.5;
        this.zd = zSpeed * 0.5 + (Math.random() - 0.5) * 0.05;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSpriteFromAge(this.spriteSet);
            this.alpha = 1.0f * (1.0f - (float) this.age / this.lifetime);
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
            return new SnowflakeParticle(l, x, y, z, xs, ys, zs, spriteSet);
        }
    }
}
