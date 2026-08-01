package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.helper.ShadowDifficultyHelper;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

public class ModMobSpawnEvents {

    private ModMobSpawnEvents() {}

    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (event.loadedFromDisk()) return;

        if (event.getEntity() instanceof Sheep sheep) {
            var biomeKey = event.getLevel().getBiome(sheep.blockPosition()).unwrapKey();
            if (biomeKey.isPresent() && biomeKey.get().equals(ModBiomes.DYEDREAM_PLAINS)) {
                sheep.setColor(DyeColor.PINK);
            }
            return;
        }

        if (event.getEntity() instanceof LivingEntity living
                && living.getType().is(ModEntityTypeTags.SHADOW_MOB)) {
            applyDifficultyScaling(living);
        }
    }

    private static void applyDifficultyScaling(LivingEntity entity) {
        int tier = ShadowDifficultyHelper.getDifficultyContext(entity);
        double healthMult = ShadowDifficultyHelper.getHealthMultiplier(tier);
        double attackMult = ShadowDifficultyHelper.getAttackMultiplier(tier);
        double speedMult = ShadowDifficultyHelper.getSpeedMultiplier(tier);

        var maxHp = entity.getAttribute(Attributes.MAX_HEALTH);
        if (maxHp != null) {
            maxHp.setBaseValue(maxHp.getBaseValue() * healthMult);
            entity.setHealth(entity.getMaxHealth());
        }
        var attack = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null) {
            attack.setBaseValue(attack.getBaseValue() * attackMult);
        }
        var speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) {
            speed.setBaseValue(speed.getBaseValue() * speedMult);
        }
    }
}
