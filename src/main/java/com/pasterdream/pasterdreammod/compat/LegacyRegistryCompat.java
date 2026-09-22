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
 */
public final class LegacyRegistryCompat {

    private static final ResourceLocation REMOVED_PINK_MUSHROOM_PORES =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "pink_mushroom_pores");

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
            }
        }
    }
}
