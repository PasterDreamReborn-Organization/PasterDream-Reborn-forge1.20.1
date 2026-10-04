package com.pasterdream.pasterdreammod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class Dust0Particle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    private float angularVelocity;
    private float angularAcceleration;

    protected Dust0Particle(ClientLevel level, double x, double y, double z,
                            double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.quadSize *= 1.1f;
        this.lifetime = 30;
        this.gravity = 0f;
        this.hasPhysics = true;
        this.xd = xSpeed * 0.3;
        this.yd = ySpeed * 0.3;
        this.zd = zSpeed * 0.3;
        this.angularVelocity = 0.01f;
        this.angularAcceleration = 0f;
        this.pickSprite(spriteSet);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        this.oRoll = this.roll;
        this.roll += this.angularVelocity;
        this.angularVelocity += this.angularAcceleration;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            return new Dust0Particle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
        }
    }
}
