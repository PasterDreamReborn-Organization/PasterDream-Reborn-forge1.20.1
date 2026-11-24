package com.pasterdream.pasterdreammod.tag;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeTags {

    public static final TagKey<EntityType<?>> SHADOW_MOB = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_mob"));

    public static final TagKey<EntityType<?>> SPECIAL_ENTITY = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "special_entity_tag"));

    /** 不参与灯影之下笔记碎片掉落的暗影生物（亚伦柯斯之触、暗影符文塔、暗影魔法球） */
    public static final TagKey<EntityType<?>> LAMP_SHADOW_BROKEN_NOTE_DROP_EXCLUDED = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_broken_note_drop_excluded"));

    /** 破风骑士召唤的友方单位（雷云）：骑士的 AOE 不会误伤它们 */
    public static final TagKey<EntityType<?>> WIND_KNIGHT_MINION = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_knight_minion"));

}
