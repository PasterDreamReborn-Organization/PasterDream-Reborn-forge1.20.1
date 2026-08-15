package com.pasterdream.pasterdreammod.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

/** 骨翼/余烬骨翼共用的浮空与环境粒子行为（原作 BoneWingPr0Procedure）。 */
final class BoneWingAmbientBehaviour {
    private BoneWingAmbientBehaviour() {
    }

    static void run(Mob entity) {
        if (entity.getRandom().nextDouble() > 0.2)
            return;
        if (!entity.level().isEmptyBlock(BlockPos.containing(entity.getX(), entity.getY() - 3, entity.getZ()))) {
            entity.setDeltaMovement(new Vec3(0, 0.1, 0));
        }
        if (entity instanceof AshBoneWingEntity && entity.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.SMOKE, entity.getX(), entity.getY(), entity.getZ(), 16, 1, 1, 1, 0.05);
            level.sendParticles(ParticleTypes.FLAME, entity.getX(), entity.getY(), entity.getZ(), 8, 0.6, 0.6, 0.6, 0.05);
        }
    }
}
