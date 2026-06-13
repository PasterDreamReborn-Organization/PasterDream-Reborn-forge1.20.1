package com.pasterdream.pasterdreammod.world.level.block;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.level.block.custom.DyedreamGrassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, PasterDreamMod.MOD_ID);

    public static final RegistryObject<Block> DYEDREAM_DIRT = BLOCKS.register("dyedream_dirt",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_PINK)
                    .sound(SoundType.GRAVEL)
                    .strength(0.75F, 0.5F)));

    public static final RegistryObject<Block> DYEDREAM_GRASS_BLOCK = BLOCKS.register("dyedream_grass_block",
            DyedreamGrassBlock::new);

    public static final RegistryObject<Block> DYEDREAM_LOG = BLOCKS.register("dyedream_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .sound(SoundType.WOOD)
                    .strength(2.0F, 2.0F)
                    .ignitedByLava()));

    public static final RegistryObject<Block> DYEDREAM_LEAVES = BLOCKS.register("dyedream_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .sound(SoundType.GRASS)
                    .strength(0.2F, 0.2F)
                    .noOcclusion()
                    .ignitedByLava()));

    public static final RegistryObject<Block> DYEDREAM_SAPLING = BLOCKS.register("dyedream_sapling",
            () -> new SaplingBlock(ModTreeGrowers.DYEDREAM, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .instabreak()
                    .randomTicks()
                    .sound(SoundType.GRASS)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
