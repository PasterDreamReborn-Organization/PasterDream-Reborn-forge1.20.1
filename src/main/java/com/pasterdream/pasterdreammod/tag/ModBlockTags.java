package com.pasterdream.pasterdreammod.tag;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

    public static final TagKey<Block> MOD_GLASS_PANE = create("glass_panes");
    public static final TagKey<Block> FORGE_GLASS_PANE = createForgeTag("glass_panes");

    private static TagKey<Block> create(String pName) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, pName));
    }

    private static TagKey<Block> createForgeTag(String pName) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", pName));
    }
}
