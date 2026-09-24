package com.pasterdream.pasterdreammod.compat.kubejs;

import com.pasterdream.pasterdreammod.PasterDreamTipsManager;
import com.pasterdream.pasterdreammod.compat.kubejs.food.PasterDreamFoodEvents;
import com.pasterdream.pasterdreammod.compat.kubejs.food.PasterDreamFoodRegistry;
import com.pasterdream.pasterdreammod.helper.DreamDimensionHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;
import java.util.Map;

public class PasterDreamKubeJSPlugin extends KubeJSPlugin {

    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register(PasterDreamFoodEvents.class);
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("PasterDreamTips", new TipsBinding());
        event.add("PasterDreamRarities", new RaritiesBinding());
        event.add("PasterDreamSkills", new SkillsBinding());
        event.add("PasterDreamFoods", new PasterDreamFoodsBinding());
        event.add("PasterDreamDimensions", new DreamDimensionsBinding());
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

    /**
     * 外部食物属性绑定：让任意物品（含原版与其他模组食物）获得 SAN 值与融梦能量加成，
     * 进食时自动生效并同步，ToolTip 自动追加加成行。
     *
     * <p>用法示例（startup_scripts）：
     * <pre>{@code
     * PasterDreamFoods.register('minecraft:apple', 1, 0.5);
     * PasterDreamFoods.register('minecraft:golden_apple', { san: 2, meltDreamEnergy: 1.5 });
     * PasterDreamFoods.get('minecraft:apple');
     * PasterDreamFoods.unregister('minecraft:apple');
     * PasterDreamFoods.clear();
     * }</pre>
     */
    public static class PasterDreamFoodsBinding {

        /** 注册食物属性：sanAdd = SAN 加成，meltDreamEnergyAdd = 融梦能量加成（负值扣减）。 */
        public boolean register(String itemId, double sanAdd, double meltDreamEnergyAdd) {
            return PasterDreamFoodRegistry.register(itemId, sanAdd, meltDreamEnergyAdd);
        }

        /** 注册食物属性：对象形式，支持键 san/sanAdd、meltDreamEnergy/meltDreamEnergyAdd。 */
        public boolean register(String itemId, Map<?, ?> properties) {
            return PasterDreamFoodRegistry.register(itemId, properties);
        }

        public boolean unregister(String itemId) {
            return PasterDreamFoodRegistry.unregister(itemId);
        }

        public void clear() {
            PasterDreamFoodRegistry.clear();
        }

        public boolean isRegistered(String itemId) {
            return PasterDreamFoodRegistry.isRegistered(itemId);
        }

        /** 已注册物品的 SAN 加成，未注册返回 0。 */
        public double getSan(String itemId) {
            return PasterDreamFoodRegistry.getSan(itemId);
        }

        /** 已注册物品的融梦能量加成，未注册返回 0。 */
        public double getMeltDreamEnergy(String itemId) {
            return PasterDreamFoodRegistry.getMeltDreamEnergy(itemId);
        }

        /** 返回注册效果对象（getSan() / getMeltDreamEnergy()），未注册返回 null。 */
        public PasterDreamFoodRegistry.FoodEffects get(String itemId) {
            return PasterDreamFoodRegistry.get(itemId);
        }

        /** 返回全部注册项（键为物品 id）。 */
        public Map<?, ?> getAll() {
            return PasterDreamFoodRegistry.getAll();
        }
    }

    /**
     * 梦境维度绑定：让整合包/KubeJS 脚本在启动阶段把额外维度登记为「梦境维度」，
     * 使其享受苍白骨针、琴雨梦套装、融梦能量戒指等梦境专属效果。
     *
     * <p>用法示例（startup_scripts）：
     * <pre>{@code
     * PasterDreamDimensions.register('modid:xxx');
     * PasterDreamDimensions.unregister('modid:xxx');
     * }</pre>
     */
    public static class DreamDimensionsBinding {

        public boolean register(String dimensionId) {
            return DreamDimensionHelper.register(dimensionId);
        }

        public boolean unregister(String dimensionId) {
            return DreamDimensionHelper.unregister(dimensionId);
        }
    }
}
