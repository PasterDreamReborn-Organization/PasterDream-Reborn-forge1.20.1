package com.pasterdream.pasterdreammod.compat.kubejs;

import com.pasterdream.pasterdreammod.PasterDreamTipsManager;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class PasterDreamKubeJSPlugin extends KubeJSPlugin {

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("PasterDreamTips", new TipsBinding());
        event.add("PasterDreamRarities", new RaritiesBinding());
        event.add("PasterDreamSkills", new SkillsBinding());
    }

    public static class TipsBinding {

        public List<String> getTips() {
            return PasterDreamTipsManager.INSTANCE.getActiveTips();
        }

        public List<String> getCustomTips() {
            return PasterDreamTipsManager.INSTANCE.getCustomTips();
        }

        public List<String> getDefaultTips() {
            return PasterDreamTipsManager.INSTANCE.getDefaultTips();
        }

        public void add(String tip) {
            PasterDreamTipsManager.INSTANCE.addTip(tip);
        }

        public void remove(int index) {
            PasterDreamTipsManager.INSTANCE.removeTip(index);
        }

        public void clear() {
            PasterDreamTipsManager.INSTANCE.clearCustomTips();
        }

        public void reset() {
            PasterDreamTipsManager.INSTANCE.resetToDefaults();
        }
    }

    public static class RaritiesBinding {

        public List<String> getNames() {
            return ModRarities.names();
        }

        public int getTier(String name) {
            Rarity rarity = ModRarities.byName(name);
            Integer tier = rarity == null ? null : ModRarities.tierOf(rarity);
            return tier == null ? 0 : tier;
        }

        public Rarity getRarity(String name) {
            return ModRarities.byName(name);
        }

        public Component getQualityTooltip(String name) {
            Rarity rarity = ModRarities.byName(name);
            return rarity == null ? Component.empty() : ModRarities.qualityTooltip(rarity);
        }

        public String getQualityTooltipString(String name) {
            return getQualityTooltip(name).getString();
        }
    }

    public static class SkillsBinding {

        /**
         * 对所有 pasterdream:skill_cooldown 物品施加共享冷却，
         * 时长自动按玩家的 SKILL_COOLDOWN_RATE 属性缩放。
         */
        public void startSharedCooldown(Player player, int baseTicks) {
            SkillCooldownHelper.applySharedCooldown(player, baseTicks);
        }

        /** 玩家当前技能冷却倍率（SKILL_COOLDOWN_RATE，默认 1.0）。 */
        public float getSkillCooldownRate(Player player) {
            return SkillCooldownHelper.getSkillCooldownMultiplier(player);
        }

        /** 玩家当前技能伤害倍率（SKILL_DAMAGE_RATE，默认 1.0）。 */
        public float getSkillDamageRate(Player player) {
            return SkillCooldownHelper.getSkillDamageMultiplier(player);
        }
    }
}
