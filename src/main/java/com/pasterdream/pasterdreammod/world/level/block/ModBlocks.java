package com.pasterdream.pasterdreammod.world.level.block;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.level.block.custom.DyedreamGrassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, PasterDreamMod.MOD_ID);

    public static final RegistryObject<Block> DYEDREAM_DIRT = BLOCKS.register("dyedream_dirt", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GRAVEL).strength(0.75F, 0.5F)));
    public static final RegistryObject<Block> DYEDREAM_GRASS_BLOCK = BLOCKS.register("dyedream_grass_block", DyedreamGrassBlock::new);
    public static final RegistryObject<Block> DYEDREAM_LOG = BLOCKS.register("dyedream_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).sound(SoundType.WOOD).strength(2.0F, 2.0F).ignitedByLava()));
    public static final RegistryObject<Block> DYEDREAM_WOOD = BLOCKS.register("dyedream_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).sound(SoundType.WOOD).strength(2.0F, 2.0F).ignitedByLava()));
    public static final RegistryObject<Block> DYEDREAM_LEAVES = BLOCKS.register("dyedream_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GRASS).strength(0.2F, 0.2F).noOcclusion().ignitedByLava()));
    public static final RegistryObject<Block> DYEDREAM_WORLDTREE_LEAVES = BLOCKS.register("dyedream_worldtree_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GRASS).strength(0.2F, 0.2F).noOcclusion().ignitedByLava()));
    public static final RegistryObject<Block> DYEDREAM_SAPLING = BLOCKS.register("dyedream_sapling", () -> new SaplingBlock(ModTreeGrowers.DYEDREAM, BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).noCollission().instabreak().randomTicks().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> DYEDREAM_QUARTZ_ORE = BLOCKS.register("dyedream_quartz_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.CALCITE).strength(3f)));
    public static final RegistryObject<Block> DYEDREAM_DUST_ORE = BLOCKS.register("dyedream_dust_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.CALCITE).strength(6f, 3f).requiresCorrectToolForDrops()));
    // ===== 染梦石英建材系列 =====
    public static final RegistryObject<Block> DYEDREAM_QUARTZ_BLOCK = BLOCKS.register("dyedream_quartz_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f)));
    public static final RegistryObject<Block> SMOOTH_DYEDREAM_QUARTZ_BLOCK = BLOCKS.register("smooth_dyedream_quartz_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f)));
    public static final RegistryObject<Block> BRICKS_DYEDREAM_QUARTZ_BLOCK = BLOCKS.register("bricks_dyedream_quartz_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f)));
    public static final RegistryObject<Block> PILLAR_DYEDREAM_QUARTZ_BLOCK = BLOCKS.register("pillar_dyedream_quartz_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f)));
    public static final RegistryObject<Block> CHISELED_DYEDREAM_QUARTZ_BLOCK = BLOCKS.register("chiseled_dyedream_quartz_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f)));
    public static final RegistryObject<Block> DYEDREAM_QUARTZ_BLOCK_STAIRS = BLOCKS.register("dyedream_quartz_block_stairs", () -> new StairBlock(DYEDREAM_QUARTZ_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f).dynamicShape().noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_QUARTZ_BLOCK_SLAB = BLOCKS.register("dyedream_quartz_block_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f).dynamicShape()));
    public static final RegistryObject<Block> DYEDREAM_QUARTZ_BLOCK_WALL = BLOCKS.register("dyedream_quartz_block_wall", () -> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f).noOcclusion().dynamicShape().forceSolidOn()));
    // ===== 染梦木板建材系列 =====
    public static final RegistryObject<Block> DYEDREAM_PLANKS = BLOCKS.register("dyedream_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f)));
    public static final RegistryObject<Block> DYEDREAM_PLANKS_STAIRS = BLOCKS.register("dyedream_planks_stairs", () -> new StairBlock(DYEDREAM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape()));
    public static final RegistryObject<Block> DYEDREAM_PLANKS_SLAB = BLOCKS.register("dyedream_planks_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape()));
    public static final RegistryObject<Block> DYEDREAM_PLANKS_FENCE = BLOCKS.register("dyedream_planks_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape().forceSolidOn()));
    public static final RegistryObject<Block> DYEDREAM_PLANKS_FENCEGATE = BLOCKS.register("dyedream_planks_fencegate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape().forceSolidOn(), WoodType.OAK));
    public static final RegistryObject<Block> DYEDREAM_PLANKS_PANE = BLOCKS.register("dyedream_planks_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> DYEDREAM_PLANKS_DOOR = BLOCKS.register("dyedream_planks_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).noOcclusion().dynamicShape(), BlockSetType.OAK));
    public static final RegistryObject<Block> DYEDREAM_PLANKS_TRAPDOOR = BLOCKS.register("dyedream_planks_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).noOcclusion().dynamicShape(), BlockSetType.OAK));
    public static final RegistryObject<Block> DYEDREAM_PLANKS_PRESSURE_PLATE = BLOCKS.register("dyedream_planks_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape().forceSolidOn(), BlockSetType.OAK));
    public static final RegistryObject<Block> DYEDREAM_PLANKS_BUTTON = BLOCKS.register("dyedream_planks_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape(), BlockSetType.OAK, 30, true));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
