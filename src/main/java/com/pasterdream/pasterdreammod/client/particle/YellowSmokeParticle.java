package com.pasterdream.pasterdreammod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

/** 黄色烟雾 —— 4帧，向下沉 */
public class YellowSmokeParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    protected YellowSmokeParticle(ClientLevel level, double x, double y, double z,
                                   double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.quadSize *= 2f;          // → 0.4
        this.lifetime = 40 + this.random.nextInt(21); // 40~60
        this.gravity = 0.1f;          // 向下沉
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
            this.setSprite(this.spriteSet.get((this.age / 8) % 4 + 1, 4));
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
            return new YellowSmokeParticle(l, x, y, z, xs, ys, zs, spriteSet);
        }
    }
}
