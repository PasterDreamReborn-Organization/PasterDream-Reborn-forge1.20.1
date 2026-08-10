package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import com.pasterdream.pasterdreammod.world.behavior.ModTreeGrowers;
import com.pasterdream.pasterdreammod.world.block.*;
import com.pasterdream.pasterdreammod.world.block.dreamspawner.DreamSpawnerBlock;
import com.pasterdream.pasterdreammod.world.block.dreamspawner.FadedDreamSpawnerBlock;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceBlock;
import com.pasterdream.pasterdreammod.world.block.shadowdungeongate.barrier.ShadowDungeonBarrierBlock;
import com.pasterdream.pasterdreammod.world.block.shadowdungeongate.gate.ShadowDungeonGateBlock;
import com.pasterdream.pasterdreammod.world.block.shadowdungeonportal.BrokenShadowDungeonPortalBlock;
import com.pasterdream.pasterdreammod.world.block.shadowdungeonportal.ShadowDungeonPortalBlock;
import com.pasterdream.pasterdreammod.world.block.shadowvortex.ShadowVortexBlock;
import com.pasterdream.pasterdreammod.world.block.shadowhandtrap.ShadowHandTrapBlock;
import com.pasterdream.pasterdreammod.world.block.lifecrystal.LifeCrystalBlock;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorBlock;
import com.pasterdream.pasterdreammod.world.block.shadow.*;
import com.pasterdream.pasterdreammod.world.block.twilightlantern.TwilightLanternBlock;
import com.pasterdream.pasterdreammod.world.block.shadowbed.ShadowBedBlock;
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
import com.pasterdream.pasterdreammod.world.block.meltdreamcrystalchest.MeltDreamCrystalChestBlock;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.openedmeltdreamcrystalchest.OpenedMeltDreamCrystalChestBlock;
import com.pasterdream.pasterdreammod.world.block.portal.DyedreamCrackBlock;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableBlock;
import com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers.TheEndlessBookOfDreamSeekersBlock;
import com.pasterdream.pasterdreammod.world.block.lostswordtomb.LostSwordTombBlock;

import com.pasterdream.pasterdreammod.world.block.goldenfoxsculpture.GoldenFoxSculptureBlock;
import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombBlock;
import com.pasterdream.pasterdreammod.world.block.dreamtrain.DreamTrainStructureBlock;
import com.pasterdream.pasterdreammod.world.block.foxsculpture.FoxSculptureBlock;
import com.pasterdream.pasterdreammod.world.block.ClayPotBlock;
import com.pasterdream.pasterdreammod.world.block.PebbleBlock;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.anvil.WeaponWorkshopAnvilBlock;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceBlock;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.coolerpot.WeaponWorkshopCoolerPotBlock;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableBlock;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.grindstone.WeaponWorkshopGrindStoneBlock;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.hammer.WeaponWorkshopHammerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
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

import java.util.LinkedHashMap;
import java.util.Map;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, PasterDreamMod.MOD_ID);

    public static final RegistryObject<Block> DYEDREAM_DIRT = BLOCKS.register("dyedream_dirt", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GRAVEL).strength(0.75F, 0.5F)));
    public static final RegistryObject<Block> DYEDREAM_GRASS_BLOCK = BLOCKS.register("dyedream_grass_block", DyedreamGrassBlock::new);
    public static final RegistryObject<Block> DYEDREAM_FARMLAND = BLOCKS.register("dyedream_farmland", DyedreamFarmlandBlock::new);
    public static final RegistryObject<Block> DYEDREAM_LOG = BLOCKS.register("dyedream_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).sound(SoundType.WOOD).strength(2.0F, 2.0F).ignitedByLava()));
    public static final RegistryObject<Block> DYEDREAM_WOOD = BLOCKS.register("dyedream_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).sound(SoundType.WOOD).strength(2.0F, 2.0F).ignitedByLava()));
    public static final RegistryObject<Block> DYEDREAM_LEAVES = BLOCKS.register("dyedream_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GRASS).strength(0.2F, 0.2F).noOcclusion().ignitedByLava().isRedstoneConductor((blockState, level, blockPos) -> false).isSuffocating((blockState, level, blockPos) -> false).isViewBlocking((blockState, level, blockPos) -> false)));
    public static final RegistryObject<Block> DYEDREAM_WORLDTREE_LEAVES = BLOCKS.register("dyedream_worldtree_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GRASS).strength(0.2F, 0.2F).noOcclusion().ignitedByLava().isRedstoneConductor((blockState, level, blockPos) -> false).isSuffocating((blockState, level, blockPos) -> false).isViewBlocking((blockState, level, blockPos) -> false))
    {
        @Override
        public boolean isRandomlyTicking(BlockState state) {
            return false;
        }
    });
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
    // ===== 黑金属系列 =====
    public static final RegistryObject<Block> BLACK_METAL_BLOCK = BLOCKS.register("black_metal_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(20f, 10f).requiresCorrectToolForDrops()));
    // ===== 锈蚀金属系列 =====
    public static final RegistryObject<Block> RUST_BLACK_METAL_BLOCK = BLOCKS.register("rust_black_metal_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.COPPER).strength(8f, 5f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> RUST_BLACK_METAL_WALL = BLOCKS.register("rust_black_metal_wall", () -> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.COPPER).strength(8f, 5f).requiresCorrectToolForDrops().noOcclusion().dynamicShape().forceSolidOn()));
    public static final RegistryObject<Block> RUST_BLACK_METAL_BARS = BLOCKS.register("rust_black_metal_bars", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.COPPER).strength(7f, 5f).requiresCorrectToolForDrops().noOcclusion()));
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
    public static final RegistryObject<Block> DYEDREAM_GLASS = BLOCKS.register("dyedream_glass", () -> new GlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion().isRedstoneConductor((blockState, level, blockPos) -> false).isSuffocating((blockState, level, blockPos) -> false).isViewBlocking((blockState, level, blockPos) -> false)));
    public static final RegistryObject<Block> DYEDREAM_GLASS_PANE = BLOCKS.register("dyedream_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> CARVE_DYEDREAM_GLASS = BLOCKS.register("carve_dyedream_glass", () -> new GlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion().isRedstoneConductor((blockState, level, blockPos) -> false).isSuffocating((blockState, level, blockPos) -> false).isViewBlocking((blockState, level, blockPos) -> false)));
    public static final RegistryObject<Block> CARVE_DYEDREAM_GLASS_PANE = BLOCKS.register("carve_dyedream_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion()));
    public static final RegistryObject<Block> GOLD_CARVE_DYEDREAM_GLASS = BLOCKS.register("gold_carve_dyedream_glass", () -> new GlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.1f, 0f).lightLevel(s -> 15).noOcclusion().isRedstoneConductor((blockState, level, blockPos) -> false).isSuffocating((blockState, level, blockPos) -> false).isViewBlocking((blockState, level, blockPos) -> false)));
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
    public static final RegistryObject<Block> DYEDREAM_ICE = BLOCKS.register("dyedream_ice", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.5f).friction(0.98f).noOcclusion().isRedstoneConductor((blockState, level, blockPos) -> false).isSuffocating((blockState, level, blockPos) -> false).isViewBlocking((blockState, level, blockPos) -> false)));
    public static final RegistryObject<Block> DYEDREAM_PACKED_ICE = BLOCKS.register("dyedream_packed_ice", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.5f).friction(0.98f).noOcclusion().isRedstoneConductor((blockState, level, blockPos) -> false).isSuffocating((blockState, level, blockPos) -> false).isViewBlocking((blockState, level, blockPos) -> false)));
    public static final RegistryObject<Block> DYEDREAM_CRYSTAL_LANTERN = BLOCKS.register("dyedream_crystal_lantern", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.GLASS).strength(0.3f).lightLevel(s -> 14).isRedstoneConductor((blockState, level, blockPos) -> false).isSuffocating((blockState, level, blockPos) -> false).isViewBlocking((blockState, level, blockPos) -> false)));
    public static final RegistryObject<Block> DYEDREAM_LANTERN = BLOCKS.register("dyedream_lantern", () -> new LanternBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.LANTERN).strength(0.3f).lightLevel(s -> 15).noOcclusion()));

    // ===== 云朵 =====
    public static final RegistryObject<Block> CLOUD = BLOCKS.register("cloud", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().sound(SoundType.WOOL).strength(0.2f, 0f).noOcclusion().isRedstoneConductor((blockState, level, blockPos) -> false).isSuffocating((blockState, level, blockPos) -> false).isViewBlocking((blockState, level, blockPos) -> false)));
    public static final RegistryObject<Block> DARK_CLOUD = BLOCKS.register("dark_cloud", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).ignitedByLava().sound(SoundType.WOOL).strength(0.2f, 0f).noOcclusion().isRedstoneConductor((blockState, level, blockPos) -> false).isSuffocating((blockState, level, blockPos) -> false).isViewBlocking((blockState, level, blockPos) -> false)));

    // ===== 巨型泡泡 =====
    public static final RegistryObject<Block> BIG_BUBBLE = BLOCKS.register("big_bubble", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).sound(new SoundType(1.0f, 1.0f,
            SoundEvents.BUBBLE_COLUMN_BUBBLE_POP,
            SoundEvents.BUBBLE_COLUMN_BUBBLE_POP,
            SoundEvents.BUBBLE_COLUMN_BUBBLE_POP,
            SoundEvents.BUBBLE_COLUMN_BUBBLE_POP,
            SoundEvents.BUBBLE_COLUMN_BUBBLE_POP)).strength(0.1f).noCollission().noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));


    // ===== 阴影方块 =====
    public static final RegistryObject<Block> SHADOW = BLOCKS.register("shadow", ShadowBlock::new);
    public static final RegistryObject<Block> THICK_SHADOW = BLOCKS.register("thick_shadow", ThickShadowBlock::new);
    public static final RegistryObject<Block> SHADOW_STONE = BLOCKS.register("shadow_stone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    // ===== 阴影石砖建材系列 =====
    public static final RegistryObject<Block> SHADOW_STONE_BRICK = BLOCKS.register("shadow_stone_brick", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SHADOW_STONE_BRICK_STAIRS = BLOCKS.register("shadow_stone_brick_stairs", () -> new StairBlock(SHADOW_STONE_BRICK.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops().dynamicShape()));
    public static final RegistryObject<Block> SHADOW_STONE_BRICK_SLAB = BLOCKS.register("shadow_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops().dynamicShape()));
    public static final RegistryObject<Block> SHADOW_STONE_BRICK_WALL = BLOCKS.register("shadow_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops().dynamicShape().forceSolidOn()));
    // ===== 细阴影石砖建材系列 =====
    public static final RegistryObject<Block> NARROW_SHADOW_STONE_BRICK = BLOCKS.register("narrow_shadow_stone_brick", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> NARROW_SHADOW_STONE_BRICK_STAIRS = BLOCKS.register("narrow_shadow_stone_brick_stairs", () -> new StairBlock(NARROW_SHADOW_STONE_BRICK.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops().dynamicShape()));
    public static final RegistryObject<Block> NARROW_SHADOW_STONE_BRICK_SLAB = BLOCKS.register("narrow_shadow_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops().dynamicShape()));
    public static final RegistryObject<Block> NARROW_SHADOW_STONE_BRICK_WALL = BLOCKS.register("narrow_shadow_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops().dynamicShape().forceSolidOn()));
    // ===== 阴影石瓦建材系列 =====
    public static final RegistryObject<Block> SHADOW_STONE_TILES = BLOCKS.register("shadow_stone_tiles", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SHADOW_STONE_TILES_STAIRS = BLOCKS.register("shadow_stone_tiles_stairs", () -> new StairBlock(SHADOW_STONE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops().dynamicShape()));
    public static final RegistryObject<Block> SHADOW_STONE_TILES_SLAB = BLOCKS.register("shadow_stone_tiles_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops().dynamicShape()));
    public static final RegistryObject<Block> SHADOW_STONE_TILES_WALL = BLOCKS.register("shadow_stone_tiles_wall", () -> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops().dynamicShape().forceSolidOn()));
    // ===== 裂阴影石砖 / 錾制阴影石砖 =====
    public static final RegistryObject<Block> CRACKED_SHADOW_STONE_BRICK = BLOCKS.register("cracked_shadow_stone_brick", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CHISELED_SHADOW_STONE_BRICK = BLOCKS.register("chiseled_shadow_stone_brick", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    // ===== 阴影石符文系列 =====
    public static final RegistryObject<Block> SHADOW_STONE_CAGE_RUNE = BLOCKS.register("shadow_stone_cage_rune", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE).strength(-1.0F, 3600000.0F)));
    public static final RegistryObject<Block> SHADOW_STONE_HOLY_GRAIL_RUNE = BLOCKS.register("shadow_stone_holy_grail_rune", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE).strength(-1.0F, 3600000.0F)));
    public static final RegistryObject<Block> SHADOW_STONE_OBLATION_RUNE = BLOCKS.register("shadow_stone_oblation_rune", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE).strength(-1.0F, 3600000.0F)));
    public static final RegistryObject<Block> SHADOW_STONE_TRIPOD_CAULDRON_RUNE = BLOCKS.register("shadow_stone_tripod_cauldron_rune", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE).strength(-1.0F, 3600000.0F)));
    // ===== 暗影地牢方块系列 =====
    public static final RegistryObject<Block> SHADOW_DUNGEON_STONE = BLOCKS.register("shadow_dungeon_stone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(-1.0F, 3600000.0F)));
    public static final RegistryObject<Block> CHISELED_SHADOW_DUNGEON_BRICKS = BLOCKS.register("chiseled_shadow_dungeon_bricks", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(-1.0F, 3600000.0F)));
    public static final RegistryObject<Block> SHADOW_DUNGEON_BRICKS = BLOCKS.register("shadow_dungeon_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(-1.0F, 3600000.0F)));
    public static final RegistryObject<Block> CRACKED_SHADOW_DUNGEON_BRICKS = BLOCKS.register("cracked_shadow_dungeon_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(-1.0F, 3600000.0F)));
    public static final RegistryObject<Block> FRACTURED_SHADOW_DUNGEON_BRICKS = BLOCKS.register("fractured_shadow_dungeon_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(-1.0F, 3600000.0F)));
    public static final RegistryObject<Block> SHADOW_DUNGEON_BRICK_STAIRS = BLOCKS.register("shadow_dungeon_brick_stairs", () -> new StairBlock(SHADOW_DUNGEON_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(-1.0F, 3600000.0F).dynamicShape()));
    public static final RegistryObject<Block> SHADOW_DUNGEON_BRICK_SLAB = BLOCKS.register("shadow_dungeon_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(-1.0F, 3600000.0F).dynamicShape()));
    public static final RegistryObject<Block> SHATTERED_SHADOW_DUNGEON_BRICKS = BLOCKS.register("shattered_shadow_dungeon_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(10.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SHADOW_DUNGEON_GATE = BLOCKS.register("shadow_dungeon_gate", ShadowDungeonGateBlock::new);
    public static final RegistryObject<Block> SHADOW_DUNGEON_BARRIER = BLOCKS.register("shadow_dungeon_barrier", ShadowDungeonBarrierBlock::new);
    // ===== 灯影竞技场 / 裂隙方块 =====
    public static final RegistryObject<Block> SHADOW_ARENA_BLOCK = BLOCKS.register("shadow_arena_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(-1.0F, 3600000.0F)));
    public static final RegistryObject<Block> SHADOW_FISSURE_0 = BLOCKS.register("shadow_fissure_0", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.GLASS).strength(-1.0F, 3600000.0F).lightLevel(s -> 4).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true)));
    public static final RegistryObject<Block> SHADOW_FISSURE_1 = BLOCKS.register("shadow_fissure_1", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.GLASS).strength(-1.0F, 3600000.0F).lightLevel(s -> 4).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).noOcclusion()));
    public static final RegistryObject<Block> SHADOW_FISSURE_2 = BLOCKS.register("shadow_fissure_2", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.GLASS).strength(-1.0F, 3600000.0F).lightLevel(s -> 4).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true)));
    public static final RegistryObject<Block> SHADOW_FISSURE_3 = BLOCKS.register("shadow_fissure_3", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.GLASS).strength(-1.0F, 3600000.0F).lightLevel(s -> 4).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).noOcclusion()));
    public static final RegistryObject<Block> SHADOW_FISSURE_4 = BLOCKS.register("shadow_fissure_4", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.GLASS).strength(-1.0F, 3600000.0F).lightLevel(s -> 4).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true)));
    public static final RegistryObject<Block> SHADOW_FISSURE_5 = BLOCKS.register("shadow_fissure_5", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.GLASS).strength(-1.0F, 3600000.0F).lightLevel(s -> 4).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).noOcclusion()));
    public static final RegistryObject<Block> BROKEN_SHADOW_DUNGEON_PORTAL = BLOCKS.register("broken_shadow_dungeon_portal", BrokenShadowDungeonPortalBlock::new);
    public static final RegistryObject<Block> DREAM_SPAWNER = BLOCKS.register("dream_spawner", DreamSpawnerBlock::new);
    public static final RegistryObject<Block> FADED_DREAM_SPAWNER = BLOCKS.register("faded_dream_spawner", FadedDreamSpawnerBlock::new);
    public static final RegistryObject<Block> SHADOW_DUNGEON_PORTAL = BLOCKS.register("shadow_dungeon_portal", ShadowDungeonPortalBlock::new);
    // 暗影漩涡 (亚伦柯斯之触技能)
    public static final RegistryObject<Block> SHADOW_VORTEX = BLOCKS.register("shadow_vortex", ShadowVortexBlock::new);
    // 暗影之手陷阱
    public static final RegistryObject<Block> SHADOW_HAND_TRAP = BLOCKS.register("shadow_hand_trap", ShadowHandTrapBlock::new);

    // ===== 阴影菌岩 / 影灯 / 阴影菌光体 =====
    public static final RegistryObject<Block> SHADOW_NYLIUM = BLOCKS.register("shadow_nylium", () -> new ShadowNyliumBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).sound(SoundType.NYLIUM).strength(0.4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SHADOW_LIGHT = BLOCKS.register("shadow_light", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).strength(1f, 0.5f).lightLevel(s -> 15).requiresCorrectToolForDrops().hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true)));
    public static final RegistryObject<Block> SHADOW_SHROOMLIGHT = BLOCKS.register("shadow_shroomlight", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().sound(SoundType.SHROOMLIGHT).strength(1f).lightLevel(s -> 12)));
    public static final RegistryObject<Block> SHADOW_CANDLE = BLOCKS.register("shadow_candle", ShadowCandleBlock::new);
    // ===== 阴影植物系列 =====
    public static final RegistryObject<Block> SHADOW_SHORT_ROOTS = BLOCKS.register("shadow_short_roots",
            () -> new ShadowPlantBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).sound(SoundType.ROOTS)
                    .instabreak().noCollission().noOcclusion()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> SHADOW_ROOTS = BLOCKS.register("shadow_roots",
            () -> new ShadowPlantBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).sound(SoundType.ROOTS)
                    .instabreak().noCollission().noOcclusion()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> SHADOW_STEM_FERN = BLOCKS.register("shadow_stem_fern",
            () -> new ShadowStemFernBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).sound(SoundType.ROOTS)
                    .instabreak().noCollission().noOcclusion()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)
                    .replaceable()));

    public static final RegistryObject<Block> SHADOW_SPROUTS = BLOCKS.register("shadow_sprouts",
            () -> new ShadowPlantBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).sound(SoundType.ROOTS)
                    .instabreak().noCollission().noOcclusion()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> SHADOW_FERN = BLOCKS.register("shadow_fern",
            () -> new ShadowPlantBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).sound(SoundType.ROOTS)
                    .instabreak().noCollission().noOcclusion()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> SHADOW_FUNGUS = BLOCKS.register("shadow_fungus",
            () -> new ShadowFungusBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).sound(SoundType.FUNGUS)
                    .instabreak().noCollission().noOcclusion()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    // ===== 阴影巨型菌系列 =====
    public static final RegistryObject<Block> SHADOW_WART_BLOCK = BLOCKS.register("shadow_wart_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.WART_BLOCK).strength(1.0f)));
    public static final RegistryObject<Block> STRIPPED_SHADOW_STEM = BLOCKS.register("stripped_shadow_stem", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.STEM).strength(2.0f)));
    public static final RegistryObject<Block> STRIPPED_SHADOW_HYPHAE = BLOCKS.register("stripped_shadow_hyphae", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.STEM).strength(2.0f)));
    public static final RegistryObject<Block> SHADOW_STEM = BLOCKS.register("shadow_stem", () -> new StrippableRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.STEM).strength(2.0f), () -> STRIPPED_SHADOW_STEM.get()));
    public static final RegistryObject<Block> SHADOW_HYPHAE = BLOCKS.register("shadow_hyphae", () -> new StrippableRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.STEM).strength(2.0f), () -> STRIPPED_SHADOW_HYPHAE.get()));
    // ===== 阴影木板建材系列 =====
    public static final RegistryObject<Block> SHADOW_PLANKS = BLOCKS.register("shadow_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f)));
    public static final RegistryObject<Block> SHADOW_STAIRS = BLOCKS.register("shadow_stairs", () -> new StairBlock(SHADOW_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape()));
    public static final RegistryObject<Block> SHADOW_SLAB = BLOCKS.register("shadow_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape()));
    public static final RegistryObject<Block> SHADOW_FENCE = BLOCKS.register("shadow_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape().forceSolidOn()));
    public static final RegistryObject<Block> SHADOW_FENCE_GATE = BLOCKS.register("shadow_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).dynamicShape().forceSolidOn(), WoodType.OAK));
    public static final RegistryObject<Block> SHADOW_PANE = BLOCKS.register("shadow_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> SHADOW_DOOR = BLOCKS.register("shadow_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).noOcclusion().dynamicShape(), BlockSetType.OAK));
    public static final RegistryObject<Block> SHADOW_TRAPDOOR = BLOCKS.register("shadow_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).noOcclusion().dynamicShape(), BlockSetType.OAK));
    public static final RegistryObject<Block> SHADOW_PRESSURE_PLATE = BLOCKS.register("shadow_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).noCollission().dynamicShape().forceSolidOn(), BlockSetType.OAK));
    public static final RegistryObject<Block> SHADOW_BUTTON = BLOCKS.register("shadow_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f).noCollission().dynamicShape(), BlockSetType.OAK, 30, true));

    // ===== 阴影书架系列 =====
    public static final RegistryObject<Block> SHADOW_BOOKSHELF = BLOCKS.register("shadow_bookshelf", () -> new ShadowBookshelfBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f)));
    public static final RegistryObject<Block> WORN_SHADOW_BOOKSHELF = BLOCKS.register("worn_shadow_bookshelf", () -> new ShadowBookshelfBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f)));
    public static final RegistryObject<Block> COBWEB_SHADOW_BOOKSHELF = BLOCKS.register("cobweb_shadow_bookshelf", () -> new ShadowBookshelfBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f)));
    public static final RegistryObject<Block> KEY_SHADOW_BOOKSHELF = BLOCKS.register("key_shadow_bookshelf", () -> new KeyShadowBookshelfBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 3f)));

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
                    return belowState.is(ModBlockTags.BLAZE_FLOWER_CAN_PLACE_ON);
                }
            });
    public static final RegistryObject<Block> WHITE_ORCHID_FLOWER = BLOCKS.register("white_orchid_flower",
            () -> new FlowerBlock(() -> MobEffects.MOVEMENT_SPEED, 0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).lightLevel(s->7)) {
                @Override
                public boolean mayPlaceOn(BlockState groundState, BlockGetter worldIn, BlockPos pos) {
                    return super.mayPlaceOn(groundState, worldIn, pos)
                            || groundState.is(Blocks.CRIMSON_NYLIUM)
                            || groundState.is(Blocks.WARPED_NYLIUM)
                            || groundState.is(ModBlocks.SHADOW_NYLIUM.get());
                }

                @Override
                public boolean canSurvive(BlockState blockstate, LevelReader worldIn, BlockPos pos) {
                    BlockPos blockpos = pos.below();
                    BlockState groundState = worldIn.getBlockState(blockpos);
                    return this.mayPlaceOn(groundState, worldIn, blockpos);
                }
            });

    public static final RegistryObject<Block> EDELWEISS = BLOCKS.register("edelweiss",
            () -> new EdelweissBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> NIPPY_EDELWEISS = BLOCKS.register("nippy_edelweiss",
            () -> new NippyEdelweissBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).randomTicks()
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).lightLevel(s -> 5).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> DREAMING_LOTUS = BLOCKS.register("dreaming_lotus", () -> new DreamingLotusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).replaceable()));

    public static final RegistryObject<Block> MISTY_DREAMING_LOTUS = BLOCKS.register("misty_dreaming_lotus", () -> new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).replaceable()) {
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
            Vec3 offset = state.getOffset(world, pos);
            return box(2, 0, 2, 14, 16, 14).move(offset.x, offset.y, offset.z);
        }
    });

    public static final RegistryObject<Block> LIGHT_BALL = BLOCKS.register("light_ball", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.SWEET_BERRY_BUSH).instabreak().lightLevel(s -> 13).noCollission().noOcclusion().hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true)
            .isRedstoneConductor((bs, br, bp) -> false)){
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
            return box(5, 5, 5, 11, 11, 11);
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
                    .replaceable()
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
                    .replaceable()

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
                    return belowState.is(ModBlockTags.CRIMSON_THORNS_CAN_PLACE_ON);
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
                @Override
                public BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, Mob mob) {
                    return BlockPathTypes.DAMAGE_FIRE;
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

    public static final RegistryObject<Block> FOURLEAF_CLOVER = BLOCKS.register("fourleaf_clover",
            () -> new FlowerBlock(() -> MobEffects.LUCK, 0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN)
                    .instabreak().noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)) {
                @Override
                public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
                    Vec3 offset = state.getOffset(world, pos);
                    return box(4, 0, 4, 12, 5, 12).move(offset.x, offset.y, offset.z);
                }
            });
    //作物方块
    public static final RegistryObject<Block> DYEDREAM_COROLLA_CROP = BLOCKS.register("dyedream_corolla_crop", () -> new PasterDreamCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).noCollission().randomTicks().instabreak()));
    public static final RegistryObject<Block> WHITE_COROLLA_CROP = BLOCKS.register("white_corolla_crop", () -> new PasterDreamCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().randomTicks().instabreak()));
    public static final RegistryObject<Block> LIGHT_BALL_CROP = BLOCKS.register("light_ball_crop", () -> new PasterDreamCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).lightLevel(blockState -> 12).noCollission().randomTicks().instabreak()));
    public static final RegistryObject<Block> CLOUD_CROP = BLOCKS.register("cloud_crop", () -> new PasterDreamCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().randomTicks().instabreak()));
    public static final RegistryObject<Block> COTTON_CROP = BLOCKS.register("cotton_crop", () -> new PasterDreamCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().randomTicks().instabreak()));

    //流体方块
    public static final RegistryObject<Block> MELTDREAM_LIQUID = BLOCKS.register("melt_dream_liquid", MeltDreamLiquidBlock::new);
    public static final RegistryObject<Block> SHADOW_LIQUID = BLOCKS.register("shadow_liquid", ShadowLiquidBlock::new);

    //方块实体对应方块
    public static final RegistryObject<Block> QYM_DOLL = BLOCKS.register("qym_doll", () -> new QYMDollBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.WOOD).strength(0F, 2147483647F).noOcclusion()));
    public static final RegistryObject<Block> UUZ_DOLL = BLOCKS.register("uuz_doll", () -> new UUZDollBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).sound(SoundType.WOOD).strength(0F, 2147483647F).noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_CRACK = BLOCKS.register("dyedream_crack", () -> new DyedreamCrackBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(-1.0F, 2147483647F).noOcclusion().noCollission().lightLevel(state -> 15)));
    public static final RegistryObject<Block> CLAYPAN = BLOCKS.register("claypan", () -> new ClaypanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.5F, 10.0F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> DREAM_CAULDRON = BLOCKS.register("dream_cauldron", () -> new DreamCauldronBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.CALCITE).strength(2.0F).noOcclusion()));
    public static final RegistryObject<Block> DYEDREAM_DESK = BLOCKS.register("dyedream_desk", () -> new DyedreamDeskBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(1F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> SHADOW_DESK = BLOCKS.register("shadow_desk", () -> new ShadowDeskBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(1F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> PICNIC_BASKET = BLOCKS.register("picnic_basket", () -> new PicnicBasketBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).ignitedByLava().sound(SoundType.SCAFFOLDING).strength(0.4F).noOcclusion()));
    public static final RegistryObject<Block> SHADOW_CHEST = BLOCKS.register("shadow_chest", () -> new ShadowChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.DEEPSLATE_TILES).strength(1F, 0.5F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> WIND_MOOR_CRATE = BLOCKS.register("wind_moor_crate", () -> new WindMoorCrateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(1F).noOcclusion()));
    public static final RegistryObject<Block> THE_ENDLESS_BOOK_OF_DREAM_SEEKERS = BLOCKS.register("the_endless_book_of_dream_seekers", () -> new TheEndlessBookOfDreamSeekersBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(-1F, 2147483647F).noOcclusion().noCollission().lightLevel(state -> 15)));
    public static final RegistryObject<Block> RESEARCH_TABLE = BLOCKS.register("research_table", () -> new ResearchTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1F, 0.2F).noOcclusion()));
    public static final RegistryObject<Block> MELT_DREAM_CRYSTAL_CHEST = BLOCKS.register("melt_dream_crystal_chest", () -> new MeltDreamCrystalChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).sound(SoundType.STONE).strength(-1F, 2147483647F).noOcclusion().lightLevel(state -> 12)));
    public static final RegistryObject<Block> OPENED_MELT_DREAM_CRYSTAL_CHEST = BLOCKS.register("opened_melt_dream_crystal_chest", () -> new OpenedMeltDreamCrystalChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).sound(SoundType.STONE).strength(10f).lightLevel(state -> 12).noOcclusion()));
    public static final RegistryObject<Block> DREAM_ACCUMULATOR = BLOCKS.register("dream_accumulator", () -> new DreamAccumulatorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.CALCITE).strength(1f).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> WEAPON_WORKSHOP_CRAFTING_TABLE = BLOCKS.register("weapon_workshop_crafting_table", () -> new WeaponWorkshopCraftingTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.ANVIL).strength(2f, 10f).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> WEAPON_WORKSHOP_ANVIL = BLOCKS.register("weapon_workshop_anvil", () -> new WeaponWorkshopAnvilBlock(BlockBehaviour.Properties.copy(Blocks.ANVIL)));
    public static final RegistryObject<Block> WEAPON_WORKSHOP_COOLER_POT = BLOCKS.register("weapon_workshop_cooler_pot", () -> new WeaponWorkshopCoolerPotBlock(BlockBehaviour.Properties.copy(Blocks.CAULDRON)));
    public static final RegistryObject<Block> WEAPON_WORKSHOP_HAMMER = BLOCKS.register("weapon_workshop_hammer", () -> new WeaponWorkshopHammerBlock(BlockBehaviour.Properties.copy(Blocks.ANVIL)));
    public static final RegistryObject<Block> WEAPON_WORKSHOP_GRIND_STONE = BLOCKS.register("weapon_workshop_grind_stone", () -> new WeaponWorkshopGrindStoneBlock(BlockBehaviour.Properties.copy(Blocks.GRINDSTONE).noOcclusion()));
    public static final RegistryObject<Block> WEAPON_WORKSHOP_BLAST_FURNACE = BLOCKS.register("weapon_workshop_blast_furnace", () -> new WeaponWorkshopBlastFurnaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.5F).noOcclusion()));
    public static final RegistryObject<Block> SHADOW_BLAST_FURNACE = BLOCKS.register("shadow_blast_furnace", () -> new ShadowBlastFurnaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.DEEPSLATE).strength(10f).requiresCorrectToolForDrops().noOcclusion()));

    //失落剑冢
    public static final RegistryObject<Block> LOST_SWORD_TOMB = BLOCKS.register("lost_sword_tomb", LostSwordTombBlock::new);

    //生命水晶
    public static final RegistryObject<Block> LIFE_CRYSTAL = BLOCKS.register("life_crystal", LifeCrystalBlock::new);

    //狐狸雕像
    public static final RegistryObject<Block> GOLDEN_FOX_SCULPTURE = BLOCKS.register("golden_fox_sculpture", GoldenFoxSculptureBlock::new);
    public static final RegistryObject<Block> FOX_SCULPTURE = BLOCKS.register("fox_sculpture", FoxSculptureBlock::new);
    // ===== 陶罐 =====
    public static final RegistryObject<Block> CLAY_POT = BLOCKS.register("clay_pot", ClayPotBlock::new);
    // ===== 小石子 =====
    public static final RegistryObject<Block> PEBBLE = BLOCKS.register("pebble", PebbleBlock::new);
    //暮影之笼 / 暮影长床
    public static final RegistryObject<Block> TWILIGHT_LANTERN = BLOCKS.register("twilight_lantern", TwilightLanternBlock::new);
    public static final RegistryObject<Block> SHADOW_BED = BLOCKS.register("shadow_bed", ShadowBedBlock::new);
    //荒漠英雄之墓
    public static final RegistryObject<Block> DESERT_HERO_TOMB = BLOCKS.register("desert_hero_tomb", DesertHeroTombBlock::new);
    //逐梦列车结构
    public static final RegistryObject<Block> DREAM_TRAIN_STRUCTURE = BLOCKS.register("dream_train_structure", DreamTrainStructureBlock::new);

    //破碎粒子提供方块
    public static final RegistryObject<Block> MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0 = BLOCKS.register("model_break_particle_provider_block_0", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(0,2147483647)));
    public static final RegistryObject<Block> MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1 = BLOCKS.register("model_break_particle_provider_block_1", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(0,2147483647)));
    public static final RegistryObject<Block> MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2 = BLOCKS.register("model_break_particle_provider_block_2", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(0,2147483647).noOcclusion()));
    public static final RegistryObject<Block> MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3 = BLOCKS.register("model_break_particle_provider_block_3", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0,2147483647)));

    // ===== 盆栽植物 =====
    // plant → potted mapping, populated by registerPottedPlant()
    public static final Map<RegistryObject<Block>, RegistryObject<Block>> POTTED_PLANTS = new LinkedHashMap<>();
    public static final java.util.Set<RegistryObject<Block>> POTTED_CROPS = new java.util.HashSet<>();
    /** potted crop → (seedlingItem, matureItem) for age-conditional loot tables */
    public static final Map<RegistryObject<Block>, CropPottedItems> POTTED_CROP_ITEMS = new LinkedHashMap<>();

    public record CropPottedItems(RegistryObject<net.minecraft.world.item.Item> seedling,
                                   RegistryObject<net.minecraft.world.item.Item> mature) {}

    private static final BlockBehaviour.Properties POT_PROPERTIES = BlockBehaviour.Properties.of()
            .instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);

    public static final RegistryObject<Block> POTTED_STEM_GRASS = registerPotted(STEM_GRASS, "potted_stem_grass");
    public static final RegistryObject<Block> POTTED_PINK_MUSHROOM = registerPotted(PINK_MUSHROOM, "potted_pink_mushroom");
    public static final RegistryObject<Block> POTTED_DYEDREAM_MOSS = registerPotted(DYEDREAM_MOSS, "potted_dyedream_moss", 7);
    public static final RegistryObject<Block> POTTED_LINHT_FLOWER = registerPotted(LINHT_FLOWER, "potted_linht_flower", 12);
    public static final RegistryObject<Block> POTTED_DYEDREAM_LILY_OF_THE_VALLEY = registerPotted(DYEDREAM_LILY_OF_THE_VALLEY, "potted_dyedream_lily_of_the_valley");
    public static final RegistryObject<Block> POTTED_SINGULARITY_FERN = registerPotted(SINGULARITY_FERN, "potted_singularity_fern");
    public static final RegistryObject<Block> POTTED_FERRARIA_CRISPA = registerPotted(FERRARIA_CRISPA, "potted_ferraria_crispa");
    public static final RegistryObject<Block> POTTED_EUSTOMA = registerPotted(EUSTOMA, "potted_eustoma");
    public static final RegistryObject<Block> POTTED_JUNGLE_SPORANGIUM = registerPotted(JUNGLE_SPORANGIUM, "potted_jungle_sporangium", 6);
    public static final RegistryObject<Block> POTTED_MALVA_SINENSIS_CAVAN = registerPotted(MALVA_SINENSIS_CAVAN, "potted_malva_sinensis_cavan");
    public static final RegistryObject<Block> POTTED_GOLDENROD = registerPotted(GOLDENROD, "potted_goldenrod");
    public static final RegistryObject<Block> POTTED_FOURLEAF_CLOVER = registerPotted(FOURLEAF_CLOVER, "potted_fourleaf_clover");
    public static final RegistryObject<Block> POTTED_BLAZE_FLOWER = registerPotted(BLAZE_FLOWER, "potted_blaze_flower");
    public static final RegistryObject<Block> POTTED_WHITE_ORCHID_FLOWER = registerPotted(WHITE_ORCHID_FLOWER, "potted_white_orchid_flower", 7);
    public static final RegistryObject<Block> POTTED_SHADOW_SHORT_ROOTS = registerPotted(SHADOW_SHORT_ROOTS, "potted_shadow_short_roots");
    public static final RegistryObject<Block> POTTED_SHADOW_ROOTS = registerPotted(SHADOW_ROOTS, "potted_shadow_roots");
    public static final RegistryObject<Block> POTTED_SHADOW_SPROUTS = registerPotted(SHADOW_SPROUTS, "potted_shadow_sprouts");
    public static final RegistryObject<Block> POTTED_SHADOW_FERN = registerPotted(SHADOW_FERN, "potted_shadow_fern");
    public static final RegistryObject<Block> POTTED_SHADOW_FUNGUS = registerPotted(SHADOW_FUNGUS, "potted_shadow_fungus");
    public static final RegistryObject<Block> POTTED_EDELWEISS = registerPotted(EDELWEISS, "potted_edelweiss");
    public static final RegistryObject<Block> POTTED_NIPPY_EDELWEISS = registerPotted(NIPPY_EDELWEISS, "potted_nippy_edelweiss", 5);
    public static final RegistryObject<Block> POTTED_DYEDREAM_SAPLING = registerPotted(DYEDREAM_SAPLING, "potted_dyedream_sapling");
    public static final RegistryObject<Block> POTTED_REED = registerPotted(REED, "potted_reed");
    public static final RegistryObject<Block> POTTED_RYE = registerPotted(RYE, "potted_rye");
    public static final RegistryObject<Block> POTTED_OATS = registerPotted(OATS, "potted_oats");
    public static final RegistryObject<Block> POTTED_DYEDREAM_COROLLA_CROP = registerPottedCrop(DYEDREAM_COROLLA_CROP, "potted_dyedream_corolla_crop", ModItems.DYEDREAM_COROLLA, 1, ModItems.DYEDREAM_COROLLA_CROP_AGE_0, ModItems.DYEDREAM_COROLLA_CROP_AGE_1);
    public static final RegistryObject<Block> POTTED_WHITE_COROLLA_CROP = registerPottedCrop(WHITE_COROLLA_CROP, "potted_white_corolla_crop", ModItems.WHITE_COROLLA, 1, ModItems.WHITE_COROLLA_CROP_AGE_0, ModItems.WHITE_COROLLA_CROP_AGE_1);
    public static final RegistryObject<Block> POTTED_LIGHT_BALL_CROP = registerPottedCrop(LIGHT_BALL_CROP, "potted_light_ball_crop", ModItems.LIGHT_BALL, 1, ModItems.LIGHT_BALL_CROP_AGE_0, ModItems.LIGHT_BALL_CROP_AGE_1, 12);
    public static final RegistryObject<Block> POTTED_CLOUD_CROP = registerPottedCrop(CLOUD_CROP, "potted_cloud_crop", ModItems.CLOUD, 5, ModItems.CLOUD_CROP_AGE_0, ModItems.CLOUD_CROP_AGE_1);
    public static final RegistryObject<Block> POTTED_COTTON_CROP = registerPottedCrop(COTTON_CROP, "potted_cotton_crop", ModItems.COTTON, 1, ModItems.COTTON_CROP_AGE_0, ModItems.COTTON_CROP_AGE_1);

    private static BlockBehaviour.Properties potLight(int light) {
        return BlockBehaviour.Properties.of()
                .instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)
                .lightLevel(s -> light);
    }

    private static RegistryObject<Block> registerPotted(RegistryObject<Block> plant, String name) {
        return registerPotted(plant, name, 0);
    }

    private static RegistryObject<Block> registerPotted(RegistryObject<Block> plant, String name, int light) {
        BlockBehaviour.Properties props = light > 0 ? potLight(light) : POT_PROPERTIES;
        RegistryObject<Block> potted = BLOCKS.register(name,
                () -> new FlowerPotBlock(plant.get(), props));
        POTTED_PLANTS.put(plant, potted);
        return potted;
    }

    private static RegistryObject<Block> registerPottedCrop(RegistryObject<Block> plant, String name,
                                                              RegistryObject<net.minecraft.world.item.Item> harvestItem, int harvestCount,
                                                              RegistryObject<net.minecraft.world.item.Item> seedlingItem,
                                                              RegistryObject<net.minecraft.world.item.Item> matureItem) {
        return registerPottedCrop(plant, name, harvestItem, harvestCount, seedlingItem, matureItem, 0);
    }

    private static RegistryObject<Block> registerPottedCrop(RegistryObject<Block> plant, String name,
                                                              RegistryObject<net.minecraft.world.item.Item> harvestItem, int harvestCount,
                                                              RegistryObject<net.minecraft.world.item.Item> seedlingItem,
                                                              RegistryObject<net.minecraft.world.item.Item> matureItem,
                                                              int light) {
        BlockBehaviour.Properties props = light > 0 ? potLight(light) : POT_PROPERTIES;
        RegistryObject<Block> potted = BLOCKS.register(name,
                () -> {
                    PottedCropBlock block = new PottedCropBlock(plant.get(), harvestItem, harvestCount,
                            seedlingItem, matureItem, props);
                    CROP_TO_POTTED.put(plant.get(), block);
                    return block;
                });
        POTTED_PLANTS.put(plant, potted);
        POTTED_CROPS.add(potted);
        POTTED_CROP_ITEMS.put(potted, new CropPottedItems(seedlingItem, matureItem));
        return potted;
    }

    /** Maps crop Block → its PottedCropBlock. Used by FlowerPotBlockMixin. */
    public static final Map<Block, PottedCropBlock> CROP_TO_POTTED = new java.util.HashMap<>();

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
