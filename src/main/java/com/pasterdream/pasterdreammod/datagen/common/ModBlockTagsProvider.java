package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import com.pasterdream.pasterdreammod.util.BuildingBlockFamily;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.DIRT)
                .add(ModBlocks.DYEDREAM_DIRT.get())
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get());

        // 可以用铲子来挖掘
        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.DYEDREAM_DIRT.get())
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get())
                .add(ModBlocks.SOUL_ORE.get())
                .add(ModBlocks.SALT_BLOCK.get());

        // 可以用锄头来挖掘
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.DYEDREAM_LEAVES.get())
                .add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get())
                .add(ModBlocks.PINK_MUSHROOM_BLOCK.get())
                .add(ModBlocks.PINK_MUSHROOM_STEM.get())
                .add(ModBlocks.PINK_MUSHROOM_PORES.get())
                .add(ModBlocks.PINK_SHROOMLIGHT.get());

        // 可以用斧子来挖掘
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get())
                .add(ModBlocks.PINK_MUSHROOM_BLOCK.get())
                .add(ModBlocks.PINK_MUSHROOM_STEM.get())
                .add(ModBlocks.PINK_MUSHROOM_PORES.get())
                .add(ModBlocks.PINK_SHROOMLIGHT.get())
                .add(ModBlocks.DYEDREAM_DESK.get())
                .add(ModBlocks.SHADOW_DESK.get())
                .add(ModBlocks.PICNIC_BASKET.get())
                .add(ModBlocks.WIND_MOOR_CRATE.get());

        // 可以用镐来挖掘

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.AMBER_CANDY_ORE.get())
                .add(ModBlocks.SMALL_DYEDREAM_BUD.get())
                .add(ModBlocks.MEDIUM_DYEDREAM_BUD.get())
                .add(ModBlocks.LARGE_DYEDREAM_BUD.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
                .add(ModBlocks.MOLTEN_GOLD_ORE.get())
                .add(ModBlocks.DYEDREAM_ICE.get())
                .add(ModBlocks.DYEDREAM_PACKED_ICE.get())
                .add(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get())
                .add(ModBlocks.DYEDREAM_LANTERN.get())
                .add(ModBlocks.ICE_STONE.get())
                .add(ModBlocks.ICE_BUD.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get())
                .add(ModBlocks.CLAYPAN.get())
                .add(ModBlocks.DREAM_CAULDRON.get())
                .add(ModBlocks.DYEDREAM_ALLOY_BLOCK.get())
                .add(ModBlocks.RAW_TITANIUM_BLOCK.get())
                .add(ModBlocks.TITANIUM_BLOCK.get())
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get())
                .add(ModBlocks.CHARGED_AMETHYST_BLOCK.get())
                .add(ModBlocks.SHADOW_CHEST.get());

        //整个树的结构（包含原木，木头，树叶，菌柄，菌核，菌疣）
        tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get());

        //可以燃烧的木头（可制成木炭，可作为燃料）
        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get());

        tag(BlockTags.LOGS)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get());

        tag(ModBlockTags.STRIPPED_LOGS)
                .add(ModBlocks.DYEDREAM_LOG.get());

        tag(ModBlockTags.STRIPPED_WOOD)
                .add(ModBlocks.DYEDREAM_WOOD.get());


        tag(BlockTags.LEAVES)
                .add(ModBlocks.DYEDREAM_LEAVES.get())
                .add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get());

        tag(BlockTags.SAPLINGS)
                .add(ModBlocks.DYEDREAM_SAPLING.get());

        tag(BlockTags.FLOWERS)
                .add(ModBlocks.GOLDENROD.get())
                .add(ModBlocks.FERRARIA_CRISPA.get())
                .add(ModBlocks.EUSTOMA.get())
                .add(ModBlocks.MALVA_SINENSIS_CAVAN.get());

        tag(BlockTags.SMALL_FLOWERS)
                .add(ModBlocks.GOLDENROD.get())
                .add(ModBlocks.FERRARIA_CRISPA.get())
                .add(ModBlocks.EUSTOMA.get())
                .add(ModBlocks.MALVA_SINENSIS_CAVAN.get());




        // 需要石头工具挖掘
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.ICE_BUD.get())
                .add(ModBlocks.CHARGED_AMETHYST_BLOCK.get());


        // 需要铁工具挖掘
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.SMALL_DYEDREAM_BUD.get())
                .add(ModBlocks.MEDIUM_DYEDREAM_BUD.get())
                .add(ModBlocks.LARGE_DYEDREAM_BUD.get())
                .add(ModBlocks.MOLTEN_GOLD_ORE.get())
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get());
        // 需要钻石工具挖掘
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.DYEDREAM_ALLOY_BLOCK.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
                .add(ModBlocks.RAW_TITANIUM_BLOCK.get())
                .add(ModBlocks.TITANIUM_BLOCK.get());


        // 可作为信标基座
        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.DYEDREAM_ALLOY_BLOCK.get())
                .add(ModBlocks.TITANIUM_BLOCK.get())
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get());

        tag(Tags.Blocks.ORES)
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.AMBER_CANDY_ORE.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
                .add(ModBlocks.MOLTEN_GOLD_ORE.get());


        tag(BlockTags.STAIRS)
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get());

        tag(BlockTags.SLABS)
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get());

        // 建材系列批量添加
        var budFamily = new BuildingBlockFamily(ModBlocks.DYEDREAM_BUD_BLOCK, ModBlocks.DYEDREAM_BUD_STAIRS, ModBlocks.DYEDREAM_BUD_SLAB, ModBlocks.DYEDREAM_BUD_WALL);
        var polishedCalciteFamily = new BuildingBlockFamily(ModBlocks.POLISHED_CALCITE, ModBlocks.POLISHED_CALCITE_STAIRS, ModBlocks.POLISHED_CALCITE_SLAB, ModBlocks.POLISHED_CALCITE_WALL);
        var calciteTilesFamily = new BuildingBlockFamily(ModBlocks.CALCITE_TILES, ModBlocks.CALCITE_TILES_STAIRS, ModBlocks.CALCITE_TILES_SLAB, ModBlocks.CALCITE_TILES_WALL);

        addBuildingFamilyToTags(budFamily);
        addBuildingFamilyToTags(polishedCalciteFamily);
        addBuildingFamilyToTags(calciteTilesFamily);

        // 染梦晶芽建材需要铁镐
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DYEDREAM_BUD_BLOCK.get())
                .add(ModBlocks.DYEDREAM_BUD_STAIRS.get())
                .add(ModBlocks.DYEDREAM_BUD_SLAB.get())
                .add(ModBlocks.DYEDREAM_BUD_WALL.get());




        // ===== 染梦木板建材系列 =====

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.DYEDREAM_PLANKS.get())
                .add(ModBlocks.DYEDREAM_STAIRS.get())
                .add(ModBlocks.DYEDREAM_SLAB.get())
                .add(ModBlocks.DYEDREAM_FENCE.get())
                .add(ModBlocks.DYEDREAM_FENCE_GATE.get())
                .add(ModBlocks.DYEDREAM_PANE.get())
                .add(ModBlocks.DYEDREAM_DOOR.get())
                .add(ModBlocks.DYEDREAM_TRAPDOOR.get())
                .add(ModBlocks.DYEDREAM_PRESSURE_PLATE.get())
                .add(ModBlocks.DYEDREAM_BUTTON.get());

        tag(BlockTags.PLANKS)
                .add(ModBlocks.DYEDREAM_PLANKS.get());

        tag(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.DYEDREAM_STAIRS.get());

        tag(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.DYEDREAM_SLAB.get());

        tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.DYEDREAM_FENCE.get());

        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.DYEDREAM_FENCE_GATE.get());

        tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.DYEDREAM_DOOR.get());

        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.DYEDREAM_TRAPDOOR.get());

        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.DYEDREAM_PRESSURE_PLATE.get());

        tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.DYEDREAM_BUTTON.get());

        // ===== 模组玻璃板 =====

        tag(ModBlockTags.MOD_GLASS_PANE)
                .add(ModBlocks.DYEDREAM_GLASS_PANE.get())
                .add(ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get())
                .add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get());

        tag(ModBlockTags.FORGE_GLASS_PANE)
                .addTag(ModBlockTags.MOD_GLASS_PANE);


        //植株底下的方块
        tag(TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_dirt")))
                .add(ModBlocks.DYEDREAM_DIRT.get())
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get());

        tag(TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "white_corolla_can_place_on")))
                .add(Blocks.SNOW_BLOCK);
    }

    private void addBuildingFamilyToTags(BuildingBlockFamily family) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(family.base().get())
                .add(family.stairs().get())
                .add(family.slab().get())
                .add(family.wall().get());
        this.tag(BlockTags.STAIRS).add(family.stairs().get());
        this.tag(BlockTags.SLABS).add(family.slab().get());
        this.tag(BlockTags.WALLS).add(family.wall().get());
    }
}
