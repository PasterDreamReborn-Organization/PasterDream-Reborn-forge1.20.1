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
                .add(ModBlocks.DYEDREAM_LEAVES.get());

        // 可以用斧子来挖掘
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.DYEDREAM_LOG.get());

        // 寻找树的新手引导
        tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL)
                .add(ModBlocks.DYEDREAM_LOG.get());

        // 加了这个之后，无需额外编写烧制木炭的配方
        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.DYEDREAM_LOG.get());

        // 无去皮版本，但仍需标记以兼容其他模组
//        tag(Tags.Blocks.STRIPPED_LOGS)
//                .add(ModBlocks.DYEDREAM_LOG.get());

        tag(BlockTags.LOGS)
                .add(ModBlocks.DYEDREAM_LOG.get());


        tag(BlockTags.LEAVES)
                .add(ModBlocks.DYEDREAM_LEAVES.get());

        tag(BlockTags.SAPLINGS)
                .add(ModBlocks.DYEDREAM_SAPLING.get());
    }
}
