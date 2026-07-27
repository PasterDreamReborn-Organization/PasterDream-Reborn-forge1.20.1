package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

public class InsandBuffEffect extends MobEffect {

    private static final String UUID = "a2fe40e7-6ef7-4713-bb35-5717740bc22e";

    public InsandBuffEffect() {
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
        return "effect.pasterdream.insand_buff";
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!(entity instanceof ServerPlayer player) || !player.isAlive()) return;
        // 画面抖动在客户端 LoseMind#GUI_OVERLAY 中处理
        // TODO: 随机刷怪 — Terrorbeak / ShadowHand / CrazyTerrorbeak 刷怪逻辑尚未实现
        // TODO: Lv2 检测 DegenerateBodys 饰品，未装备时持续扣血
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
