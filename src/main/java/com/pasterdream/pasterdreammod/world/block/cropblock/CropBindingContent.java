package com.pasterdream.pasterdreammod.world.block.cropblock;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record CropBindingContent(Item productItem, int productCount, TagKey<Block> plantableOnTag){}
