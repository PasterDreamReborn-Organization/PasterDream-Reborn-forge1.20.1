package com.pasterdream.pasterdreammod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

/**
 * 毒气粒子 —— 使用 poison_gas_particle.png（4帧动画），
 * 缓慢上升扩散，用于剧毒药剂瓶的毒雾效果。
 */
public class PoisonGasParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    protected PoisonGasParticle(ClientLevel level, double x, double y, double z,
                                 double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.quadSize *= 2f;
        this.lifetime = 40 + (this.random.nextInt(11) - 5);
        this.gravity = 0f;
        this.hasPhysics = false; // 毒雾穿透方块，不碰撞消失
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
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
        return 15728880; // 全亮度
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
            return new PoisonGasParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
        }
    }
}
