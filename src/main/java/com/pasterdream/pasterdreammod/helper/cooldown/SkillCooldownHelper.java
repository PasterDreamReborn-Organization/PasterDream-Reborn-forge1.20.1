package com.pasterdream.pasterdreammod.helper.cooldown;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

public class SkillCooldownHelper {

    private SkillCooldownHelper() {}

    /**
     * Apply shared cooldown to ALL items tagged with pasterdream:skill_cooldown,
     * scaled by the player's skill_cooldown attribute.
     */
    public static void applySharedCooldown(Player player, int baseTicks) {
        float multiplier = getSkillCooldownMultiplier(player);
        int finalTicks = Math.max(1, Math.round(baseTicks * multiplier));
        BuiltInRegistries.ITEM.getTagOrEmpty(ModItemTags.SKILL_COOLDOWN)
                .forEach(holder -> player.getCooldowns().addCooldown(holder.value(), finalTicks));
    }

    /**
     * Returns the player's skill cooldown multiplier (default 1.0 = no change).
     */
    public static float getSkillCooldownMultiplier(Player player) {
        return getAttributeValue(player, ModAttributes.SKILL_COOLDOWN_RATE.get(), 1.0f);
    }

    /**
     * Returns the player's skill damage multiplier (default 1.0 = no change).
     * This is layered on top of each weapon's own damage formula.
     */
    public static float getSkillDamageMultiplier(Player player) {
        return getAttributeValue(player, ModAttributes.SKILL_DAMAGE_RATE.get(), 1.0f);
    }

    private static float getAttributeValue(Player player, net.minecraft.world.entity.ai.attributes.Attribute attr, float fallback) {
        AttributeInstance inst = player.getAttribute(attr);
        return inst != null ? (float) inst.getValue() : fallback;
    }
}
