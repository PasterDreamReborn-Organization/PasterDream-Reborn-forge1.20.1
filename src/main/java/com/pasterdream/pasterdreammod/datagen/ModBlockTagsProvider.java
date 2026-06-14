package com.pasterdream.pasterdreammod.datagen;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.level.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
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
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get());

        // 可以用锄头来挖掘
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.DYEDREAM_LEAVES.get())
                .add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get())
                .add(ModBlocks.PINK_MUSHROOM_BLOCK.get())
                .add(ModBlocks.PINK_MUSHROOM_STEM.get())
                .add(ModBlocks.PINK_MUSHROOM_PORES.get())
                .add(ModBlocks.PINK_SHROOMLIGHT.get());

        tag(BlockTags.WART_BLOCKS)
                .add(ModBlocks.PINK_MUSHROOM_BLOCK.get())
                .add(ModBlocks.PINK_MUSHROOM_STEM.get())
                .add(ModBlocks.PINK_MUSHROOM_PORES.get());

        // 可以用斧子来挖掘
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get());

        // 寻找树的新手引导
        tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get());

        // 加了这个之后，无需额外编写烧制木炭的配方
        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get());

        tag(BlockTags.LOGS)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get());


        tag(BlockTags.LEAVES)
                .add(ModBlocks.DYEDREAM_LEAVES.get())
                .add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get());

        tag(BlockTags.SAPLINGS)
                .add(ModBlocks.DYEDREAM_SAPLING.get());

        // ===== 染梦石英 =====

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.AMBER_CANDY_ORE.get())
                .add(ModBlocks.SMALL_DYEDREAM_BUD.get())
                .add(ModBlocks.MEDIUM_DYEDREAM_BUD.get())
                .add(ModBlocks.LARGE_DYEDREAM_BUD.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get());

        tag(Tags.Blocks.ORES)
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.AMBER_CANDY_ORE.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.SMALL_DYEDREAM_BUD.get())
                .add(ModBlocks.MEDIUM_DYEDREAM_BUD.get())
                .add(ModBlocks.LARGE_DYEDREAM_BUD.get());
        tag(BlockTags.STAIRS)
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get());

        tag(BlockTags.SLABS)
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get());

        // ===== 染梦木板建材系列 =====

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.DYEDREAM_PLANKS.get())
                .add(ModBlocks.DYEDREAM_PLANKS_STAIRS.get())
                .add(ModBlocks.DYEDREAM_PLANKS_SLAB.get())
                .add(ModBlocks.DYEDREAM_PLANKS_FENCE.get())
                .add(ModBlocks.DYEDREAM_PLANKS_FENCEGATE.get())
                .add(ModBlocks.DYEDREAM_PLANKS_PANE.get())
                .add(ModBlocks.DYEDREAM_PLANKS_DOOR.get())
                .add(ModBlocks.DYEDREAM_PLANKS_TRAPDOOR.get())
                .add(ModBlocks.DYEDREAM_PLANKS_PRESSURE_PLATE.get())
                .add(ModBlocks.DYEDREAM_PLANKS_BUTTON.get());

        tag(BlockTags.PLANKS)
                .add(ModBlocks.DYEDREAM_PLANKS.get());

        tag(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.DYEDREAM_PLANKS_STAIRS.get());

        tag(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.DYEDREAM_PLANKS_SLAB.get());

        tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.DYEDREAM_PLANKS_FENCE.get());

        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.DYEDREAM_PLANKS_FENCEGATE.get());

        tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.DYEDREAM_PLANKS_DOOR.get());

        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.DYEDREAM_PLANKS_TRAPDOOR.get());

        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.DYEDREAM_PLANKS_PRESSURE_PLATE.get());

        tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.DYEDREAM_PLANKS_BUTTON.get());
    }
}
