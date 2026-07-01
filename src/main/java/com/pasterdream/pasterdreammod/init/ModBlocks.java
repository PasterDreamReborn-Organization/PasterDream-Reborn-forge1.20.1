package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.behavior.ModTreeGrowers;
import com.pasterdream.pasterdreammod.world.block.*;
import com.pasterdream.pasterdreammod.world.block.DyedreamFarmlandBlock;
import com.pasterdream.pasterdreammod.world.block.DyedreamGrassBlock;
import com.pasterdream.pasterdreammod.world.block.PinkMushroom;
import com.pasterdream.pasterdreammod.world.block.TallPinkMushroom;
import com.pasterdream.pasterdreammod.world.block.DyedreamLilyPadBlock;
import com.pasterdream.pasterdreammod.world.block.DyedreamLotusBlock;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket.PicnicBasketBlock;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest.ShadowChestBlock;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.windmoorcrate.WindMoorCrateBlock;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlock;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.desk.dyedreamdesk.DyedreamDeskBlock;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.desk.shadowdesk.ShadowDeskBlock;
import com.pasterdream.pasterdreammod.world.block.cropblock.PasterDreamCropBlock;
import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollBlock;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollBlock;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlock;
import com.pasterdream.pasterdreammod.world.block.fluidblock.MeltDreamLiquidBlock;
import com.pasterdream.pasterdreammod.world.block.fluidblock.ShadowLiquidBlock;
import com.pasterdream.pasterdreammod.world.block.portal.DyedreamCrackBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
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
    public static final RegistryObject<Block> DYEDREAM_FARMLAND = BLOCKS.register("dyedream_farmland", DyedreamFarmlandBlock::new);
    public static final RegistryObject<Block> DYEDREAM_LOG = BLOCKS.register("dyedream_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).sound(SoundType.WOOD).strength(2.0F, 2.0F).ignitedByLava()));
    public static final RegistryObject<Block> DYEDREAM_WOOD = BLOCKS.register("dyedream_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).sound(SoundType.WOOD).strength(2.0F, 2.0F).ignitedByLava()));
    public static final RegistryObject<Block> DYEDREAM_LEAVES = BLOCKS.register("dyedream_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GRASS).strength(0.2F, 0.2F).noOcclusion().ignitedByLava()));
    public static final RegistryObject<Block> DYEDREAM_WORLDTREE_LEAVES = BLOCKS.register("dyedream_worldtree_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GRASS).strength(0.2F, 0.2F).noOcclusion().ignitedByLava()));
    public static final RegistryObject<Block> DYEDREAM_SAPLING = BLOCKS.register("dyedream_sapling", () -> new SaplingBlock(ModTreeGrowers.DYEDREAM, BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).noCollission().instabreak().randomTicks().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> DYEDREAM_QUARTZ_ORE = BLOCKS.register("dyedream_quartz_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.CALCITE).strength(3f)));
    public static final RegistryObject<Block> DYEDREAM_DUST_ORE = BLOCKS.register("dyedream_dust_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.CALCITE).strength(6f, 3f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> AMBER_CANDY_ORE = BLOCKS.register("amber_candy_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.CALCITE).strength(3f)));
    public static final RegistryObject<Block> TITANIUM_ORE = BLOCKS.register("titanium_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.CALCITE).strength(9f, 5f).lightLevel(s->15).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = BLOCKS.register("deepslate_titanium_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE).strength(9f, 5f).lightLevel(s->15).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOLTEN_GOLD_ORE = BLOCKS.register("molten_gold_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.NETHER_GOLD_ORE).strength(6f, 3f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SOUL_ORE = BLOCKS.register("soul_ore", SoulOreBlock::new);
    public static final RegistryObject<Block> RAW_TITANIUM_BLOCK = BLOCKS.register("raw_titanium_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.POLISHED_DEEPSLATE).strength(10f, 6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SALT_BLOCK = BLOCKS.register("salt_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.SNARE).sound(SoundType.SAND).strength(0.5f, 0.1f)));
    public static final RegistryObject<Block> TITANIUM_BLOCK = BLOCKS.register("titanium_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).sound(SoundType.METAL).strength(11f, 7f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOLTEN_GOLD_BLOCK = BLOCKS.register("molten_gold_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).sound(SoundType.METAL).strength(7f, 4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CHARGED_AMETHYST_BLOCK = BLOCKS.register("charged_amethyst_block", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.HAT).sound(SoundType.AMETHYST).strength(2f, 1.5f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
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
    public static final RegistryObject<Block> DYEDREAM_STAIRS = BLOCKS.register("dyedream_stairs", () -> new StairBlock(DYEDREAM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape()));
    public static final RegistryObject<Block> DYEDREAM_SLAB = BLOCKS.register("dyedream_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape()));
    public static final RegistryObject<Block> DYEDREAM_FENCE = BLOCKS.register("dyedream_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape().forceSolidOn()));
    public static final RegistryObject<Block> DYEDREAM_FENCE_GATE = BLOCKS.register("dyedream_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape().forceSolidOn(), WoodType.OAK));
    public static final RegistryObject<Block> DYEDREAM_PANE = BLOCKS.register("dyedream_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> DYEDREAM_DOOR = BLOCKS.register("dyedream_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).noOcclusion().dynamicShape(), BlockSetType.OAK));
    public static final RegistryObject<Block> DYEDREAM_TRAPDOOR = BLOCKS.register("dyedream_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).noOcclusion().dynamicShape(), BlockSetType.OAK));
    public static final RegistryObject<Block> DYEDREAM_PRESSURE_PLATE = BLOCKS.register("dyedream_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).noCollission().dynamicShape().forceSolidOn(), BlockSetType.OAK));
    public static final RegistryObject<Block> DYEDREAM_BUTTON = BLOCKS.register("dyedream_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).noCollission().dynamicShape(), BlockSetType.OAK, 30, true));
    public static final RegistryObject<Block> PINK_SLIME_BLOCK = BLOCKS.register("pink_slime_block", () -> new FallingBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.SLIME_BLOCK).strength(0.2f, 0.1f).friction(0.7f).speedFactor(0.9f).jumpFactor(0.7f).noOcclusion()));
    public static final RegistryObject<Block> PINK_MUSHROOM_BLOCK = BLOCKS.register("pink_mushroom_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(0.3f, 0.1f).jumpFactor(1.2f)));
    public static final RegistryObject<Block> PINK_MUSHROOM_STEM = BLOCKS.register("pink_mushroom_stem", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(0.3f, 0.1f)));
    public static final RegistryObject<Block> PINK_MUSHROOM_PORES = BLOCKS.register("pink_mushroom_pores", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(0.3f, 0.1f)));
    public static final RegistryObject<Block> PINK_SHROOMLIGHT = BLOCKS.register("pink_shroomlight", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.SHROOMLIGHT).strength(0.5f, 0.2f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> PINK_MUSHROOM = BLOCKS.register("pink_mushroom", () -> new PinkMushroom(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instabreak().noCollission().noOcclusion().sound(SoundType.FUNGUS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> TALL_PINK_MUSHROOM = BLOCKS.register("tall_pink_mushroom", () -> new TallPinkMushroom(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instabreak().noCollission().noOcclusion().sound(SoundType.FUNGUS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).replaceable()));
    // ===== 染梦玻璃系列 =====
    public static final RegistryObject<Block> DYEDREAM_SAND = BLOCKS.register("dyedream_sand", () -> new FallingBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.SNARE).sound(SoundType.SAND).strength(0.5f)));
    public static final RegistryObject<Block> DYEDREAM_GLASS = BLOCKS.register("dyedream_glass", () -> new GlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_GLASS_PANE = BLOCKS.register("dyedream_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> CARVE_DYEDREAM_GLASS = BLOCKS.register("carve_dyedream_glass", () -> new GlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> CARVE_DYEDREAM_GLASS_PANE = BLOCKS.register("carve_dyedream_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> GOLD_CARVE_DYEDREAM_GLASS = BLOCKS.register("gold_carve_dyedream_glass", () -> new GlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> GOLD_CARVE_DYEDREAM_GLASS_PANE = BLOCKS.register("gold_carve_dyedream_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    // ===== 染梦晶芽系列 =====
    public static final RegistryObject<Block> DYEDREAM_BUDDING_BLOCK = BLOCKS.register("dyedream_budding_block", () -> new DyedreamBuddingBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1.5f, 1.5f).lightLevel(s -> 5).requiresCorrectToolForDrops().noOcclusion().randomTicks()));
    public static final RegistryObject<Block> SMALL_DYEDREAM_BUD = BLOCKS.register("small_dyedream_bud", () -> new DyedreamClusterBlock(4, 4, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).lightLevel(s -> 10).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> MEDIUM_DYEDREAM_BUD = BLOCKS.register("medium_dyedream_bud", () -> new DyedreamClusterBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).lightLevel(s -> 10).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> LARGE_DYEDREAM_BUD = BLOCKS.register("large_dyedream_bud", () -> new DyedreamClusterBlock(9, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).lightLevel(s -> 10).requiresCorrectToolForDrops().noOcclusion()));
    // ===== 染梦水晶建材系列 =====
    public static final RegistryObject<Block> DYEDREAM_BUD_BLOCK = BLOCKS.register("dyedream_bud_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_BUD_STAIRS = BLOCKS.register("dyedream_bud_stairs", () -> new StairBlock(DYEDREAM_BUD_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).requiresCorrectToolForDrops().dynamicShape().noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_BUD_SLAB = BLOCKS.register("dyedream_bud_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).requiresCorrectToolForDrops().dynamicShape().noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_BUD_WALL = BLOCKS.register("dyedream_bud_wall", () -> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).requiresCorrectToolForDrops().noOcclusion().dynamicShape().forceSolidOn()));
    // ===== 染梦冰与水晶灯 =====
    public static final RegistryObject<Block> DYEDREAM_ICE = BLOCKS.register("dyedream_ice", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.5f).friction(0.98f).noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_PACKED_ICE = BLOCKS.register("dyedream_packed_ice", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.5f).friction(0.98f).noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_CRYSTAL_LANTERN = BLOCKS.register("dyedream_crystal_lantern", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.3f).lightLevel(s -> 14)));
    public static final RegistryObject<Block> DYEDREAM_LANTERN = BLOCKS.register("dyedream_lantern", () -> new LanternBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.LANTERN).strength(0.3f).lightLevel(s -> 15).noOcclusion()));

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

    //植物系列（花）
    public static final RegistryObject<Block> GOLDENROD = BLOCKS.register("goldenrod",
            () -> new FlowerBlock(() -> MobEffects.MOVEMENT_SLOWDOWN, 0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW)
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> FERRARIA_CRISPA = BLOCKS.register("ferraria_crispa",
            () -> new FlowerBlock(() -> MobEffects.MOVEMENT_SPEED, 0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK)
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> EUSTOMA = BLOCKS.register("eustoma",
            () -> new FlowerBlock(() -> MobEffects.MOVEMENT_SPEED, 0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> MALVA_SINENSIS_CAVAN = BLOCKS.register("malva_sinensis_cavan",
            () -> new FlowerBlock(() -> MobEffects.MOVEMENT_SPEED, 0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK)
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> LINHT_FLOWER = BLOCKS.register("linht_flower",
            () -> new FlowerBlock(() -> MobEffects.MOVEMENT_SPEED, 0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK)
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).lightLevel(s->12)));

    public static final RegistryObject<Block> DYEDREAM_LILY_OF_THE_VALLEY = BLOCKS.register("dyedream_lily_of_the_valley",
            () -> new FlowerBlock(() -> MobEffects.MOVEMENT_SPEED, 0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> BLAZE_FLOWER = BLOCKS.register("blaze_flower",
            () -> new FlowerBlock(() -> MobEffects.MOVEMENT_SPEED, 0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED)
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY))
            {
                @Override
                public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
                    BlockPos belowPos = pPos.below();
                    BlockState belowState = pLevel.getBlockState(belowPos);
                    if (belowState.isAir()) return false;
                    return belowState.is(Blocks.CRIMSON_NYLIUM);
                }
            });
    public static final RegistryObject<Block> WHITE_ORCHID_FLOWER = BLOCKS.register("white_orchid_flower",
            () -> new FlowerBlock(() -> MobEffects.MOVEMENT_SPEED, 0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).lightLevel(s->7)));

    public static final RegistryObject<Block> EDELWEISS = BLOCKS.register("edelweiss",
            () -> new FlowerBlock(() -> MobEffects.MOVEMENT_SPEED, 0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> NIPPY_EDELWEISS = BLOCKS.register("nippy_edelweiss",
            () -> new FlowerBlock(() -> MobEffects.MOVEMENT_SPEED, 0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> DREAMING_LOTUS = BLOCKS.register("dreaming_lotus", () -> new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).replaceable()) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
            Vec3 offset = state.getOffset(world, pos);
            return box(2, 0, 2, 14, 16, 14).move(offset.x, offset.y, offset.z);
        }
    });

    public static final RegistryObject<Block> MISTY_DREAMING_LOTUS = BLOCKS.register("misty_dreaming_lotus", () -> new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).replaceable()) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
            Vec3 offset = state.getOffset(world, pos);
            return box(2, 0, 2, 14, 16, 14).move(offset.x, offset.y, offset.z);
        }
    });

    public static final RegistryObject<Block> DYEDREAM_LILY_PAD = BLOCKS.register("dyedream_lily_pad", DyedreamLilyPadBlock::new);
    public static final RegistryObject<Block> DYEDREAM_LOTUS = BLOCKS.register("dyedream_lotus", DyedreamLotusBlock::new);

    //植物系列（草）
    public static final RegistryObject<Block> DYEDREAM_MOSS = BLOCKS.register("dyedream_moss", () -> new BushBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)
                    .lightLevel(s -> 7)
                    .hasPostProcess((bs, br, bp) -> true)
                    .emissiveRendering((bs, br, bp) -> true)
    ) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
            Vec3 offset = state.getOffset(world, pos);
            return box(0, 0, 0, 16, 1, 16).move(offset.x, offset.y, offset.z);
        }
    });
    public static final RegistryObject<Block> STEM_GRASS = BLOCKS.register("stem_grass", () -> new BushBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)

    ));
    public static final RegistryObject<Block> TALL_STEM_GRASS = BLOCKS.register("tall_stem_grass", () -> new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).replaceable()) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
            Vec3 offset = state.getOffset(world, pos);
            return box(2, 0, 2, 14, 16, 14).move(offset.x, offset.y, offset.z);
        }
    });
    public static final RegistryObject<Block> CRIMSON_THORNS = BLOCKS.register("crimson_thorns", () ->
            new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK)
            .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY).replaceable())
            {
                @Override
                public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
                    return box(3, 0, 3, 13, 16, 13);
                }
                @Override
                public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
                    DoubleBlockHalf half = pState.getValue(DoublePlantBlock.HALF);
                    if (half == DoubleBlockHalf.UPPER) {
                        return super.canSurvive(pState, pLevel, pPos);
                    }
                    BlockPos belowPos = pPos.below();
                    BlockState belowState = pLevel.getBlockState(belowPos);
                    if (belowState.isAir()) return false;
                    return belowState.is(Blocks.CRIMSON_NYLIUM);
                }
                @Override
                public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
                    if (pLevel.isClientSide()) {
                        return;
                    }
                    if (pEntity instanceof LivingEntity livingEntity) {
                        if (!livingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0));
                        }
                            if (livingEntity.getRemainingFireTicks() < 20) {
                                livingEntity.setSecondsOnFire(1);
                        }
                    }
                }
            });
    public static final RegistryObject<Block> OATS = BLOCKS.register("oats", () -> new BushBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)

    ));

    public static final RegistryObject<Block> RYE = BLOCKS.register("rye", () -> new BushBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)

    ));

    public static final RegistryObject<Block> SINGULARITY_FERN = BLOCKS.register("singularity_fern", () -> new BushBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)
    ));

    public static final RegistryObject<Block> POLISHED_CALCITE_STALICRIPE = BLOCKS.register("polished_calcite_stalicripe", () -> new BushBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()
                    .sound(SoundType.CALCITE)
                    .pushReaction(PushReaction.DESTROY)
    ){
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
            Vec3 offset = state.getOffset(world, pos);
            return box(1, 0, 1, 15, 1, 15).move(offset.x, offset.y, offset.z);
        }
        @Override
        public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
            BlockPos belowPos = pPos.below();
            BlockState belowState = pLevel.getBlockState(belowPos);
            if (belowState.isAir()) return false;
            return belowState.is(Blocks.CALCITE);
        }
    });

    public static final RegistryObject<Block> SMALL_POLISHED_CALCITE_STALICRIPE = BLOCKS.register("small_polished_calcite_stalicripe", () -> new BushBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()
                    .sound(SoundType.CALCITE)
                    .pushReaction(PushReaction.DESTROY)
    ){
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
            Vec3 offset = state.getOffset(world, pos);
            return box(0, 0, 0, 16, 1, 16).move(offset.x, offset.y, offset.z);
        }
        @Override
        public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
            BlockPos belowPos = pPos.below();
            BlockState belowState = pLevel.getBlockState(belowPos);
            if (belowState.isAir()) return false;
            return belowState.is(Blocks.CALCITE);
        }
    }
    );
    public static final RegistryObject<Block> DYEDREAM_SEAGRASS = BLOCKS.register("dyedream_seagrass", DyedreamSeagrassBlock::new);

    public static final RegistryObject<Block> REED = BLOCKS.register("reed", () -> new BushBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)
    ){
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
            Vec3 offset = state.getOffset(world, pos);
            return box(0, 0, 0, 16, 8, 16).move(offset.x, offset.y, offset.z);
        }
    });
    public static final RegistryObject<Block> DYEDREAM_VINE = BLOCKS.register("dyedream_vine", DyedreamVineBlock::new);
    public static final RegistryObject<Block> JUNGLE_SPORANGIUM = BLOCKS.register("jungle_sporangium", () -> new BushBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)
                    .lightLevel(s->6)
    ){
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
            Vec3 offset = state.getOffset(world, pos);
            return box(4, 0, 4, 12, 12, 12).move(offset.x, offset.y, offset.z);
        }
    });
    //作物方块
    public static final RegistryObject<Block> DYEDREAM_COROLLA_CROP = BLOCKS.register("dyedream_corolla_crop", () -> new PasterDreamCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).noCollission().randomTicks().instabreak()));
    public static final RegistryObject<Block> WHITE_COROLLA_CROP = BLOCKS.register("white_corolla_crop", () -> new PasterDreamCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().randomTicks().instabreak()));
    public static final RegistryObject<Block> LIGHT_BALL_CROP = BLOCKS.register("light_ball_crop", () -> new PasterDreamCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).lightLevel(blockState -> 12).noCollission().randomTicks().instabreak()));
    public static final RegistryObject<Block> CLOUD_CROP = BLOCKS.register("cloud_crop", () -> new PasterDreamCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().randomTicks().instabreak()));
    public static final RegistryObject<Block> COTTON_CROP = BLOCKS.register("cotton_crop", () -> new PasterDreamCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().randomTicks().instabreak()));

    //流体方块
    public static final RegistryObject<Block> MELTDREAM_LIQUID = BLOCKS.register("meltdream_liquid", MeltDreamLiquidBlock::new);
    public static final RegistryObject<Block> SHADOW_LIQUID = BLOCKS.register("shadow_liquid", ShadowLiquidBlock::new);

    //方块实体对应方块
    public static final RegistryObject<Block> QYM_DOLL = BLOCKS.register("qym_doll", () -> new QYMDollBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.WOOD).strength(0.1F, 2147483647F).noOcclusion()));
    public static final RegistryObject<Block> UUZ_DOLL = BLOCKS.register("uuz_doll", () -> new UUZDollBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).sound(SoundType.WOOD).strength(0.1F, 2147483647F).noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_CRACK = BLOCKS.register("dyedream_crack", () -> new DyedreamCrackBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(-1.0F, 3600000.0F).noOcclusion().noCollission().lightLevel(state -> 15)));
    public static final RegistryObject<Block> CLAYPAN = BLOCKS.register("claypan", () -> new ClaypanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.5F, 10.0F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> DREAM_CAULDRON = BLOCKS.register("dream_cauldron", () -> new DreamCauldronBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.CALCITE).strength(2.0F).noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_DESK = BLOCKS.register("dyedream_desk", () -> new DyedreamDeskBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(1F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> SHADOW_DESK = BLOCKS.register("shadow_desk", () -> new ShadowDeskBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(1F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> PICNIC_BASKET = BLOCKS.register("picnic_basket", () -> new PicnicBasketBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).ignitedByLava().sound(SoundType.SCAFFOLDING).strength(0.4F).noOcclusion()));
    public static final RegistryObject<Block> SHADOW_CHEST = BLOCKS.register("shadow_chest", () -> new ShadowChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.DEEPSLATE_TILES).strength(1F, 0.5F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> WIND_MOOR_CRATE = BLOCKS.register("wind_moor_crate", () -> new WindMoorCrateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(1F).noOcclusion()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
