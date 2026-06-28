package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // copy(BlockTag, ItemTag) 将 BlockTagsProvider 中注册的方块标签自动同步到对应物品标签
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.LOGS, ItemTags.LOGS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.FLOWERS, ItemTags.FLOWERS);
        copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);

        copy(ModBlockTags.STRIPPED_LOGS, ModItemTags.STRIPPED_LOGS);
        copy(ModBlockTags.STRIPPED_WOOD, ModItemTags.STRIPPED_WOOD);

        // 玻璃板 - copy 方块标签到物品标签，再添加到 forge:glass_panes
        copy(ModBlockTags.MOD_GLASS_PANE, ModItemTags.MOD_GLASS_PANE);

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "glass_panes")))
                .addTag(ModItemTags.MOD_GLASS_PANE);

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "mushrooms")))
                .add(ModBlocks.PINK_MUSHROOM.get().asItem())
                .add(ModBlocks.TALL_PINK_MUSHROOM.get().asItem());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "flour")))
                .add(ModItems.FLOUR.get().asItem());
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "flour/wheat")))
                .add(ModItems.FLOUR.get().asItem());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "dough")))
                .add(ModItems.DOUGH.get().asItem());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "dough/wheat")))
                .add(ModItems.DOUGH.get().asItem());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "eggs")))
                .add(ModItems.PINK_EGG.get().asItem());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "fruits/fig")))
                .add(ModItems.FIG.get().asItem());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "cooked_eggs")))
                .add(ModItems.FRIED_EGG.get().asItem());


        // 工具标签
        tag(ItemTags.SWORDS)
                .add(ModItems.MOLTEN_GOLD_SWORD.get())
                .add(ModItems.COPPER_SWORD.get())
                .add(ModItems.TITANIUM_SWORD.get())
                .add(ModItems.DYEDREAM_SWORD.get())
                .add(ModItems.HELLFIRE_SWORD.get())
                .add(ModItems.INFERNO_SWORD.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.MOLTEN_GOLD_PICKAXE.get())
                .add(ModItems.COPPER_PICKAXE.get())
                .add(ModItems.TITANIUM_PICKAXE.get())
                .add(ModItems.DYEDREAM_PICKAXE.get())
                .add(ModItems.HELLFIRE_PICKAXE.get());
        tag(ItemTags.AXES)
                .add(ModItems.MOLTEN_GOLD_AXE.get())
                .add(ModItems.COPPER_AXE.get())
                .add(ModItems.TITANIUM_AXE.get())
                .add(ModItems.DYEDREAM_AXE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.MOLTEN_GOLD_SHOVEL.get())
                .add(ModItems.COPPER_SHOVEL.get())
                .add(ModItems.TITANIUM_SHOVEL.get())
                .add(ModItems.DYEDREAM_SHOVEL.get());
        tag(ItemTags.HOES)
                .add(ModItems.MOLTEN_GOLD_HOE.get())
                .add(ModItems.COPPER_HOE.get())
                .add(ModItems.TITANIUM_HOE.get())
                .add(ModItems.DYEDREAM_HOE.get());

        //植株底下的方块
        tag(TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_dirt")))
                .add(ModItems.DYEDREAM_DIRT.get())
                .add(ModItems.DYEDREAM_GRASS_BLOCK.get());

        tag(TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "white_corolla_can_place_on")))
                .add(Items.SNOW_BLOCK);
    }
}
