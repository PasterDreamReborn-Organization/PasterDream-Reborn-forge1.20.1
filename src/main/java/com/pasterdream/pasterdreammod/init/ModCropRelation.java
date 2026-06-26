package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.block.cropblock.CropBindingContent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class ModCropRelation
{
    private static final Map<Block, CropBindingContent> BINDINGS = new HashMap<>();

    public static void register(Block cropBlock, CropBindingContent binding)
    {
        BINDINGS.put(cropBlock, binding);
    }

    public static CropBindingContent getBinding(Block cropBlock)
    {
        return BINDINGS.get(cropBlock);
    }

    public static void registerCropRelation()
    {
        register(ModBlocks.DYEDREAM_COROLLA_CROP.get(), new CropBindingContent(ModItems.DYEDREAM_COROLLA.get(), 1, TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_dirt"))));
        register(ModBlocks.WHITE_COROLLA_CROP.get(), new CropBindingContent(ModItems.WHITE_COROLLA.get(), 1, TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "white_corolla_can_place_on"))));
        register(ModBlocks.LIGHT_BALL_CROP.get(), new CropBindingContent(ModItems.LIGHT_BALL.get(), 1, TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_dirt"))));
        register(ModBlocks.CLOUD_CROP.get(), new CropBindingContent(ModItems.CLOUD.get(), 5, TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_dirt"))));
        register(ModBlocks.COTTON_CROP.get(), new CropBindingContent(ModItems.COTTON.get(), 1, BlockTags.DIRT));
    }
}
