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

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "shadow_stones")))
                .add(ModItems.SHADOW_STONE.get())
                .add(ModItems.SHADOW_STONE_BRICK.get())
                .add(ModItems.NARROW_SHADOW_STONE_BRICK.get())
                .add(ModItems.SHADOW_STONE_TILES.get())
                .add(ModItems.CRACKED_SHADOW_STONE_BRICK.get())
                .add(ModItems.CHISELED_SHADOW_STONE_BRICK.get());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "shadow_stems")))
                .add(ModItems.SHADOW_STEM.get())
                .add(ModItems.SHADOW_HYPHAE.get())
                .add(ModItems.STRIPPED_SHADOW_STEM.get())
                .add(ModItems.STRIPPED_SHADOW_HYPHAE.get());

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

        tag(ItemTags.NON_FLAMMABLE_WOOD)
                .add(ModBlocks.SHADOW_STEM.get().asItem())
                .add(ModBlocks.SHADOW_HYPHAE.get().asItem())
                .add(ModBlocks.STRIPPED_SHADOW_STEM.get().asItem())
                .add(ModBlocks.STRIPPED_SHADOW_HYPHAE.get().asItem())
                .add(ModBlocks.SHADOW_PLANKS.get().asItem())
                .add(ModBlocks.SHADOW_STAIRS.get().asItem())
                .add(ModBlocks.SHADOW_SLAB.get().asItem())
                .add(ModBlocks.SHADOW_FENCE.get().asItem())
                .add(ModBlocks.SHADOW_FENCE_GATE.get().asItem())
                .add(ModBlocks.SHADOW_PANE.get().asItem())
                .add(ModBlocks.SHADOW_DOOR.get().asItem())
                .add(ModBlocks.SHADOW_TRAPDOOR.get().asItem())
                .add(ModBlocks.SHADOW_PRESSURE_PLATE.get().asItem())
                .add(ModBlocks.SHADOW_BUTTON.get().asItem());

        // 工具标签
        tag(ItemTags.SWORDS)
                .add(ModItems.MOLTEN_GOLD_SWORD.get())
                .add(ModItems.COPPER_SWORD.get())
                .add(ModItems.TITANIUM_SWORD.get())
                .add(ModItems.DYEDREAM_SWORD.get())
                .add(ModItems.HELLFIRE_SWORD.get())
                .add(ModItems.INFERNO_SWORD.get())
                .add(ModItems.TIDE_SWORD.get())
                .add(ModItems.BEIHAI_RUO_TIDE_SWORD.get())
                .add(ModItems.KUSANAGI.get())
                .add(ModItems.MURAKUMO_KUSANAGI.get())
                .add(ModItems.SWORD_EMBRYO.get())
                .add(ModItems.DESERT_SWORD.get())
                .add(ModItems.CHENJINGMEN_DESERT_SWORD.get())
                .add(ModItems.BROKEN_HERO_SWORD.get())
                .add(ModItems.TERRA_BLADE.get());
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

        tag(ModItemTags.COMPOSTABLE)
                // 蘑菇建材
                .add(ModBlocks.PINK_MUSHROOM_BLOCK.get().asItem())
                .add(ModBlocks.PINK_MUSHROOM_STEM.get().asItem())
                .add(ModBlocks.PINK_SHROOMLIGHT.get().asItem())
                .add(ModBlocks.PINK_MUSHROOM_PORES.get().asItem())
                .add(ModItems.SHADOW_WART_BLOCK.get())
                .add(ModItems.SHADOW_SHROOMLIGHT.get())
                // 植物栏 —— 花
                .add(ModItems.GOLDENROD.get())
                .add(ModItems.FERRARIA_CRISPA.get())
                .add(ModItems.EUSTOMA.get())
                .add(ModItems.MALVA_SINENSIS_CAVAN.get())
                .add(ModItems.LINHT_FLOWER.get())
                .add(ModItems.DYEDREAM_LILY_OF_THE_VALLEY.get())
                .add(ModItems.WHITE_ORCHID_FLOWER.get())
                .add(ModItems.EDELWEISS.get())
                .add(ModItems.NIPPY_EDELWEISS.get())
                .add(ModItems.BLAZE_FLOWER.get())
                .add(ModItems.FOURLEAF_CLOVER.get())
                // 植物栏 —— 大型花
                .add(ModItems.DREAMING_LOTUS.get())
                .add(ModItems.MISTY_DREAMING_LOTUS.get())
                .add(ModItems.DYEDREAM_LOTUS.get())
                // 植物栏 —— 草/蕨/藤/苔/芦苇
                .add(ModItems.STEM_GRASS.get())
                .add(ModItems.TALL_STEM_GRASS.get())
                .add(ModItems.DYEDREAM_MOSS.get())
                .add(ModItems.SINGULARITY_FERN.get())
                .add(ModItems.DYEDREAM_VINE.get())
                .add(ModItems.DYEDREAM_SEAGRASS.get())
                .add(ModItems.REED.get())
                .add(ModItems.RYE.get())
                .add(ModItems.OATS.get())
                .add(ModItems.JUNGLE_SPORANGIUM.get())
                .add(ModItems.CRIMSON_THORNS.get())
                // 植物栏 —— 菌类
                .add(ModItems.PINK_MUSHROOM.get())
                .add(ModItems.TALL_PINK_MUSHROOM.get())
                // 植物栏 —— 树苗/睡莲
                .add(ModItems.DYEDREAM_SAPLING.get())
                .add(ModItems.DYEDREAM_LILY_PAD.get())
                // 植物栏 —— 阴影植物
                .add(ModItems.SHADOW_SHORT_ROOTS.get())
                .add(ModItems.SHADOW_ROOTS.get())
                .add(ModItems.SHADOW_STEM_FERN.get())
                .add(ModItems.SHADOW_SPROUTS.get())
                .add(ModItems.SHADOW_FERN.get())
                .add(ModItems.SHADOW_FUNGUS.get());

        //植株底下的方块
        tag(TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_dirt")))
                .add(ModItems.DYEDREAM_DIRT.get())
                .add(ModItems.DYEDREAM_GRASS_BLOCK.get());

        tag(TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "white_corolla_can_place_on")))
                .add(Items.SNOW_BLOCK)
                .addTag(ItemTags.DIRT);

        tag(TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "dream_notes")))
                .add(ModItems.DREAM_NOTES_DYEDREAM_WORLD.get())
                .add(ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get())
                .add(ModItems.DREAM_NOTES_WIND_JOURNEY_WORLD.get())
                .add(ModItems.BLUE_PRINT.get());

        tag(TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("forge", "pen")))
                .add(ModItems.FEATHER_PEN.get());

        // 技能冷却标签，该标签下的武器共用冷却
        tag(ModItemTags.SKILL_COOLDOWN)
                .add(ModItems.MURAKUMO_KUSANAGI.get())
                .add(ModItems.INFERNO_SWORD.get())
                .add(ModItems.SHARP_MELT_DREAM_SWORD.get())
                .add(ModItems.BEIHAI_RUO_TIDE_SWORD.get())
                .add(ModItems.CHENJINGMEN_DESERT_SWORD.get())
                .add(ModItems.TERRA_BLADE.get());

        // 占星者的祈愿钓竿标签 —— 手持此类钓竿时深海秘宝必出 super 变体
        tag(TagKey.create(Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "star_fishing_rods")))
                .add(ModItems.STAR_WISH_ROD.get());

        tag(TagKey.create(Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "galaxy_jelly_boost")))
                .add(Items.ELYTRA);

        // 狐狸食物
        tag(ItemTags.FOX_FOOD)
                .add(ModItems.DYEDREAM_FRUIT.get());

        // 音乐唱片
        tag(ItemTags.MUSIC_DISCS)
                .add(ModItems.SWEET_DREAM_MUSIC_DISC.get())
                .add(ModItems.SNOWFALL_DREAM_MUSIC_DISC.get());
    }
}
