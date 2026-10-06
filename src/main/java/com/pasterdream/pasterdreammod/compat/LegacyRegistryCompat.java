package com.pasterdream.pasterdreammod.compat;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

/**
 * 旧存档兼容层：把已移除的旧 ID 重映射到当前 ID，避免世界/背包加载时丢失。
 *
 * <p>粉顶菌菌盖与粉顶菌菌孔块已合并为单一的粉顶菌方块，
 * 旧的 {@code pasterdream:pink_mushroom_pores} 统一指向 {@code pasterdream:pink_mushroom_block}。
 *
 * <p>梦笔记解析线按梦境拆分后，旧的通用 ID 统一重映射到灯影之下版本：
 * {@code broken_note → lamp_shadow_broken_note}、{@code unknown_note → lamp_shadow_unknown_note}、
 * {@code dream_notes_story_guide → lamp_shadow_story_guide}。
 */
public final class LegacyRegistryCompat {

    private static final ResourceLocation REMOVED_PINK_MUSHROOM_PORES =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "pink_mushroom_pores");

    private static final ResourceLocation REMOVED_BROKEN_NOTE =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "broken_note");
    private static final ResourceLocation REMOVED_UNKNOWN_NOTE =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "unknown_note");
    private static final ResourceLocation REMOVED_DREAM_NOTES_STORY_GUIDE =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_notes_story_guide");

    private LegacyRegistryCompat() {
    }

    @SubscribeEvent
    public static void onMissingMappings(MissingMappingsEvent event) {
        for (MissingMappingsEvent.Mapping<Block> mapping : event.getAllMappings(ForgeRegistries.Keys.BLOCKS)) {
            if (REMOVED_PINK_MUSHROOM_PORES.equals(mapping.getKey())) {
                mapping.remap(ModBlocks.PINK_MUSHROOM_BLOCK.get());
            }
        }
        for (MissingMappingsEvent.Mapping<Item> mapping : event.getAllMappings(ForgeRegistries.Keys.ITEMS)) {
            if (REMOVED_PINK_MUSHROOM_PORES.equals(mapping.getKey())) {
                mapping.remap(ModItems.PINK_MUSHROOM_BLOCK.get());
            } else if (REMOVED_BROKEN_NOTE.equals(mapping.getKey())) {
                mapping.remap(ModItems.LAMP_SHADOW_BROKEN_NOTE.get());
            } else if (REMOVED_UNKNOWN_NOTE.equals(mapping.getKey())) {
                mapping.remap(ModItems.LAMP_SHADOW_UNKNOWN_NOTE.get());
            } else if (REMOVED_DREAM_NOTES_STORY_GUIDE.equals(mapping.getKey())) {
                mapping.remap(ModItems.LAMP_SHADOW_STORY_GUIDE.get());
            }
        }
    }
}
