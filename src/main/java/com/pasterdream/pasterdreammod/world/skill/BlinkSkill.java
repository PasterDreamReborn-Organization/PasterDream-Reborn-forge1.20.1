package com.pasterdream.pasterdreammod.world.skill;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class BlinkSkill {

    public static void execute(Player player) {
        if (player == null || player.level().isClientSide()) return;

        boolean creative = player.getAbilities().instabuild;

        if (!creative) {
            if (player.getFoodData().getFoodLevel() <= 6) return;
            if (player.hasEffect(ModEffects.BLINK_COOLDOWN.get())) return;

            double consume = getAttributeValue(player, ModAttributes.BLINK_CONSUME.get(), 1.0);
            player.causeFoodExhaustion((float) (3 * consume));

            doDisplacement(player);
            applyCooldown(player);
        } else {
            doDisplacement(player);
        }

        applyEvasionBuff(player);
    }

    private static void doDisplacement(Player player) {
        double range = getAttributeValue(player, ModAttributes.BLINK_RANGE.get(), 1.0);
        Vec3 look = player.getLookAngle();
        Vec3 delta;

        if (!player.isShiftKeyDown()) {
            if (player.isInWater()) {
                delta = new Vec3(look.x * range, look.y, look.z * range);
            } else {
                delta = new Vec3(look.x * range, look.y * 0.1 + 0.4, look.z * range);
            }
        } else {
            delta = new Vec3(-look.x * range, -look.y * 0.1 + 0.4, -look.z * range);
        }

        player.setDeltaMovement(delta);
        player.hurtMarked = true;
    }

    private static void applyCooldown(Player player) {
        double cd = getAttributeValue(player, ModAttributes.BLINK_CD.get(), 1.0);
        boolean hasElytra = player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA);
        int duration = hasElytra ? (int) (100 * cd) : (int) (50 * cd);
        player.addEffect(new MobEffectInstance(ModEffects.BLINK_COOLDOWN.get(), duration, 0,
                false, false, false));
    }

    private static void applyEvasionBuff(Player player) {
        player.addEffect(new MobEffectInstance(ModEffects.EVASION.get(), 6, 0,
                false, false));
    }

    private static double getAttributeValue(Player player, net.minecraft.world.entity.ai.attributes.Attribute attr, double fallback) {
        AttributeInstance inst = player.getAttribute(attr);
        return inst != null ? inst.getValue() : fallback;
    }
}
