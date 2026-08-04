package com.pasterdream.pasterdreammod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShadowStoneParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    protected ShadowStoneParticle(ClientLevel level, double x, double y, double z,
                                  double vx, double vy, double vz, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.quadSize *= 1.2f;
        this.lifetime = (int) Math.max(1, 15 + (this.random.nextInt(10) - 5));
        this.gravity = 0.2f;
        this.hasPhysics = true;
        this.xd = vx * 0.5 + (random.nextDouble() - 0.5) * 0.1;
        this.yd = vy * 0.3 + random.nextDouble() * 0.1;
        this.zd = vz * 0.5 + (random.nextDouble() - 0.5) * 0.1;
        this.pickSprite(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        this.setAlpha(1.0f - (float) this.age / this.lifetime);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            return new ShadowStoneParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
        }
    }
}
