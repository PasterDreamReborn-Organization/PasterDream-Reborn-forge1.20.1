package com.pasterdream.pasterdreammod.tag;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {

    public static final TagKey<Item> MOD_GLASS_PANE = bind("glass_panes");
    public static final TagKey<Item> MOD_GLASS = bind("glass");
    public static final TagKey<Item> STRIPPED_LOGS = createForgeTag("stripped_logs");
    public static final TagKey<Item> STRIPPED_WOOD = createForgeTag("stripped_wood");
    /** 可放入堆肥桶的物品标签，通过 TagsUpdatedEvent 自动注册到 ComposterBlock */
    public static final TagKey<Item> COMPOSTABLE = bind("compostable");
    /** 技能武器共享冷却标签 —— 持有此标签的武器使用技能时所有同类武器一同进入冷却 */
    public static final TagKey<Item> SKILL_COOLDOWN = bind("skill_cooldown");
    /** 星河果冻强化标签 —— 饰品栏或胸甲栏有此标签的物品时触发强化弹射效果 */
    public static final TagKey<Item> GALAXY_JELLY_BOOST = bind("galaxy_jelly_boost");

    private static TagKey<Item> bind(String pName) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, pName));
    }

    private static TagKey<Item> createForgeTag(String pName){
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", pName));
    }

}
