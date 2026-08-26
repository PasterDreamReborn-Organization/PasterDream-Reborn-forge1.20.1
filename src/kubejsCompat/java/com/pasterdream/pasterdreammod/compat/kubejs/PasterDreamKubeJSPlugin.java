package com.pasterdream.pasterdreammod.compat.kubejs;

import com.pasterdream.pasterdreammod.PasterDreamTipsManager;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class PasterDreamKubeJSPlugin extends KubeJSPlugin {

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("PasterDreamTips", new TipsBinding());
        event.add("PasterDreamRarities", new RaritiesBinding());
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
}
