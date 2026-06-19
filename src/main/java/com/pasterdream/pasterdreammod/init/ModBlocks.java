package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.behavior.ModTreeGrowers;
import com.pasterdream.pasterdreammod.world.block.DyedreamGrassBlock;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlock;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlock;
import com.pasterdream.pasterdreammod.world.block.fluidblock.MeltDreamLiquidBlock;
import com.pasterdream.pasterdreammod.world.block.fluidblock.ShadowLiquidBlock;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.material.PushReaction;
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
    public static final RegistryObject<Block> AMBER_CANDY_ORE = BLOCKS.register("amber_candy_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.CALCITE).strength(3f)));
    public static final RegistryObject<Block> TITANIUM_ORE = BLOCKS.register("titanium_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(9f, 5f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = BLOCKS.register("deepslate_titanium_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE).strength(9f, 5f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOLTEN_GOLD_ORE = BLOCKS.register("molten_gold_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.NETHER_GOLD_ORE).strength(6f, 3f).requiresCorrectToolForDrops()));
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
    public static final RegistryObject<Block> PINK_SLIME_BLOCK = BLOCKS.register("pink_slime_block", () -> new FallingBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.SLIME_BLOCK).strength(0.2f, 0.1f).friction(0.7f).speedFactor(0.9f).jumpFactor(0.7f).noOcclusion()));
    public static final RegistryObject<Block> PINK_MUSHROOM_BLOCK = BLOCKS.register("pink_mushroom_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(0.3f, 0.1f).jumpFactor(1.2f)));
    public static final RegistryObject<Block> PINK_MUSHROOM_STEM = BLOCKS.register("pink_mushroom_stem", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(0.3f, 0.1f)));
    public static final RegistryObject<Block> PINK_MUSHROOM_PORES = BLOCKS.register("pink_mushroom_pores", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(0.3f, 0.1f)));
    public static final RegistryObject<Block> PINK_SHROOMLIGHT = BLOCKS.register("pink_shroomlight", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.SHROOMLIGHT).strength(0.5f, 0.2f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> PINK_MUSHROOM = BLOCKS.register("pink_mushroom", () -> new FlowerBlock(() -> MobEffects.MOVEMENT_SLOWDOWN, 100, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instabreak().noCollission().noOcclusion().sound(SoundType.FUNGUS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> TALL_PINK_MUSHROOM = BLOCKS.register("tall_pink_mushroom", () -> new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instabreak().noCollission().noOcclusion().sound(SoundType.FUNGUS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).replaceable()));
    // ===== 染梦玻璃系列 =====
    public static final RegistryObject<Block> DYEDREAM_SAND = BLOCKS.register("dyedream_sand", () -> new FallingBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.SNARE).sound(SoundType.SAND).strength(0.5f)));
    public static final RegistryObject<Block> DYEDREAM_GLASS = BLOCKS.register("dyedream_glass", () -> new GlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_GLASS_PANE = BLOCKS.register("dyedream_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> CARVE_DYEDREAM_GLASS = BLOCKS.register("carve_dyedream_glass", () -> new GlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> CARVE_DYEDREAM_GLASS_PANE = BLOCKS.register("carve_dyedream_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> GOLD_CARVE_DYEDREAM_GLASS = BLOCKS.register("gold_carve_dyedream_glass", () -> new GlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> GOLD_CARVE_DYEDREAM_GLASS_PANE = BLOCKS.register("gold_carve_dyedream_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    // ===== 染梦晶芽系列 =====
    public static final RegistryObject<Block> SMALL_DYEDREAM_BUD = BLOCKS.register("small_dyedream_bud", () -> new AmethystClusterBlock(4, 4, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).lightLevel(s -> 10).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> MEDIUM_DYEDREAM_BUD = BLOCKS.register("medium_dyedream_bud", () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).lightLevel(s -> 10).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> LARGE_DYEDREAM_BUD = BLOCKS.register("large_dyedream_bud", () -> new AmethystClusterBlock(9, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).lightLevel(s -> 10).requiresCorrectToolForDrops().noOcclusion()));
    // ===== 染梦水晶建材系列 =====
    public static final RegistryObject<Block> DYEDREAM_BUD_BLOCK = BLOCKS.register("dyedream_bud_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_BUD_STAIRS = BLOCKS.register("dyedream_bud_stairs", () -> new StairBlock(DYEDREAM_BUD_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).requiresCorrectToolForDrops().dynamicShape().noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_BUD_SLAB = BLOCKS.register("dyedream_bud_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).requiresCorrectToolForDrops().dynamicShape().noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_BUD_WALL = BLOCKS.register("dyedream_bud_wall", () -> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).requiresCorrectToolForDrops().noOcclusion().dynamicShape().forceSolidOn()));
    // ===== 染梦冰与水晶灯 =====
    public static final RegistryObject<Block> DYEDREAM_ICE = BLOCKS.register("dyedream_ice", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.5f).friction(0.98f).noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_PACKED_ICE = BLOCKS.register("dyedream_packed_ice", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.5f).friction(0.98f).noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_LARTERN = BLOCKS.register("dyedream_lartern", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.3f).lightLevel(s -> 14)));

    // ===== 云朵 =====
    public static final RegistryObject<Block> CLOUD = BLOCKS.register("cloud", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().sound(SoundType.WOOL).strength(0.2f, 0f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
    public static final RegistryObject<Block> DARK_CLOUD = BLOCKS.register("dark_cloud", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).ignitedByLava().sound(SoundType.WOOL).strength(0.2f, 0f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));

    // ===== 巨型泡泡 =====
    public static final RegistryObject<Block> BIG_BUBBLE = BLOCKS.register("big_bubble", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).sound(new SoundType(1.0f, 1.0f,
            SoundEvents.BUBBLE_COLUMN_BUBBLE_POP,
            SoundEvents.BUBBLE_COLUMN_BUBBLE_POP,
            SoundEvents.BUBBLE_COLUMN_BUBBLE_POP,
            SoundEvents.BUBBLE_COLUMN_BUBBLE_POP,
            SoundEvents.BUBBLE_COLUMN_BUBBLE_POP)).strength(0.1f).noCollission().noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));


    // ===== 冰晶岩与冰凌晶芽 =====
    public static final RegistryObject<Block> ICE_STONE = BLOCKS.register("ice_stone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.CALCITE).strength(0.8f, 0.2f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ICE_BUD = BLOCKS.register("ice_bud", () -> new AmethystClusterBlock(9, 4, BlockBehaviour.Properties.of().mapColor(MapColor.ICE).sound(SoundType.AMETHYST_CLUSTER).strength(1f, 0f).lightLevel(s -> 9).requiresCorrectToolForDrops().noOcclusion()));

    // ===== 染梦合金块 =====
    public static final RegistryObject<Block> DYEDREAM_ALLOY_BLOCK = BLOCKS.register("dyedream_alloy_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.METAL).strength(11f, 7f).requiresCorrectToolForDrops()));

    // ===== 方解石系列 =====
    public static final RegistryObject<Block> POLISHED_CALCITE = BLOCKS.register("polished_calcite", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.CALCITE).strength(0.75f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_CALCITE_STAIRS = BLOCKS.register("polished_calcite_stairs", () -> new StairBlock(POLISHED_CALCITE.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.POLISHED_DEEPSLATE).strength(0.75f).requiresCorrectToolForDrops().noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> POLISHED_CALCITE_SLAB = BLOCKS.register("polished_calcite_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.POLISHED_DEEPSLATE).strength(0.75f).requiresCorrectToolForDrops().noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> POLISHED_CALCITE_WALL = BLOCKS.register("polished_calcite_wall", () -> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.POLISHED_DEEPSLATE).strength(0.75f).requiresCorrectToolForDrops().noOcclusion().dynamicShape().forceSolidOn()));
    public static final RegistryObject<Block> CALCITE_TILES = BLOCKS.register("calcite_tiles", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.CALCITE).strength(0.75f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CALCITE_TILES_STAIRS = BLOCKS.register("calcite_tiles_stairs", () -> new StairBlock(CALCITE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.POLISHED_DEEPSLATE).strength(0.75f).requiresCorrectToolForDrops().noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> CALCITE_TILES_SLAB = BLOCKS.register("calcite_tiles_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.POLISHED_DEEPSLATE).strength(0.75f).requiresCorrectToolForDrops().noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> CALCITE_TILES_WALL = BLOCKS.register("calcite_tiles_wall", () -> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.POLISHED_DEEPSLATE).strength(0.75f).requiresCorrectToolForDrops().noOcclusion().dynamicShape().forceSolidOn()));

    //流体方块
    public static final RegistryObject<Block> MELTDREAM_LIQUID = BLOCKS.register("meltdream_liquid", MeltDreamLiquidBlock::new);
    public static final RegistryObject<Block> SHADOW_LIQUID = BLOCKS.register("shadow_liquid", ShadowLiquidBlock::new);

    //方块实体对应方块
    public static final RegistryObject<Block> CLAYPAN = BLOCKS.register("claypan", () -> new ClaypanBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.5F, 10.0F).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((blockState, blockGetter, blockPosition) -> false)));
    public static final RegistryObject<Block> DREAM_CAULDRON = BLOCKS. register("dream_cauldron", () -> new DreamCauldronBlock(BlockBehaviour.Properties.of().sound(SoundType.CALCITE).strength(2.0F).noOcclusion().isRedstoneConductor((blockState, blockGetter, blockPos) -> false)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
