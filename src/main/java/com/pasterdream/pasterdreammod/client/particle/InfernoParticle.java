package com.pasterdream.pasterdreammod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

/** 狱火粒子 —— 漂浮火焰，4帧动画，向上飘 */
public class InfernoParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    protected InfernoParticle(ClientLevel level, double x, double y, double z,
                               double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.quadSize *= 1.5f;
        this.lifetime = 30 + this.random.nextInt(20);
        this.gravity = -0.02f;
        this.hasPhysics = false;
        this.xd = xSpeed + (Math.random() - 0.5) * 0.02;
        this.yd = ySpeed + 0.03 + Math.random() * 0.05;
        this.zd = zSpeed + (Math.random() - 0.5) * 0.02;
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
            return new InfernoParticle(l, x, y, z, xs, ys, zs, spriteSet);
        }
    }
}
