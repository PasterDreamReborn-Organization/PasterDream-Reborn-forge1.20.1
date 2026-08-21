package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.helper.ShadowDifficultyHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

public class InsaneBuffEffect extends MobEffect {

    private static final String UUID = "a2fe40e7-6ef7-4713-bb35-5717740bc22e";

    public InsaneBuffEffect() {
        super(MobEffectCategory.HARMFUL, -14744315);
        this.addAttributeModifier(ModAttributes.BLINK_CD.get(), UUID, 2, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID, -0.3, AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, UUID, -0.1, AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID, -2, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(ForgeMod.ENTITY_REACH.get(), UUID, -0.2, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(ForgeMod.BLOCK_REACH.get(), UUID, -0.2, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public String getDescriptionId() {
        return "effect.pasterdream.insane_buff";
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!(entity instanceof ServerPlayer player) || !player.isAlive()) return;
        if (player.isCreative() || player.isSpectator()) return;
        if (player.getHealth() <= 1) return;
        if (amplifier < 2) return;
        if (ShadowDifficultyHelper.getDifficulty(player) <= 0) return;
        player.hurt(new DamageSource(player.serverLevel().registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD)), 1);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 10 == 0;
    }
}
