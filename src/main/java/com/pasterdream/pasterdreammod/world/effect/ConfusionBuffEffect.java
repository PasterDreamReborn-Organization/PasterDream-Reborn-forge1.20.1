package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.List;

import net.minecraft.world.item.ItemStack;

public class ConfusionBuffEffect extends MobEffect {
    public ConfusionBuffEffect() {
        super(MobEffectCategory.HARMFUL, -12108960);
    }

    @Override
    public String getDescriptionId() {
        return "effect.pasterdream.confusion_buff";
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player) {
            float range = amplifier == 0 ? 1f : 2f;
            entity.setYRot((float) (entity.getYRot() + Mth.nextDouble(RandomSource.create(), -range, range)));
            entity.setXRot((float) (entity.getXRot() + Mth.nextDouble(RandomSource.create(), -range, range)));
            entity.setYBodyRot(entity.getYRot());
            entity.setYHeadRot(entity.getYRot());
            entity.yRotO = entity.getYRot();
            entity.xRotO = entity.getXRot();
            entity.yBodyRotO = entity.getYRot();
            entity.yHeadRotO = entity.getYRot();
        } else {
            entity.setYRot((float) Mth.nextDouble(RandomSource.create(), -180, 180));
            entity.setXRot((float) Mth.nextDouble(RandomSource.create(), -90, 90));
            entity.setYBodyRot(entity.getYRot());
            entity.setYHeadRot(entity.getYRot());
            entity.yRotO = entity.getYRot();
            entity.xRotO = entity.getXRot();
            entity.yBodyRotO = entity.getYRot();
            entity.yHeadRotO = entity.getYRot();
            entity.setDeltaMovement(new Vec3(0, -1, 0));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return Collections.emptyList();
    }
}
