package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket.PicnicBasketBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest.ShadowChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.windmoorcrate.WindMoorCrateBlockEntity;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.desk.dyedreamdesk.DyedreamDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.desk.shadowdesk.ShadowDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollBlockEntity;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlockEntity;
import com.pasterdream.pasterdreammod.world.block.meltdreamcrystalchest.MeltDreamCrystalChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.openedmeltdreamcrystalchest.OpenedMeltDreamCrystalChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableAddonBlockEntity;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableBlockEntity;
import com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers.TheEndlessBookOfDreamSeekersBlockEntity;
import com.pasterdream.pasterdreammod.world.block.lifecrystal.LifeCrystalBlockEntity;
import com.pasterdream.pasterdreammod.world.block.lostswordtomb.LostSwordTombBlockEntity;
import com.pasterdream.pasterdreammod.world.block.goldenfoxsculpture.GoldenFoxSculptureBlockEntity;
import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombBlockEntity;
import com.pasterdream.pasterdreammod.world.block.foxsculpture.FoxSculptureBlockEntity;
import com.pasterdream.pasterdreammod.world.block.NippyEdelweissBlockEntity;
import com.pasterdream.pasterdreammod.world.block.shadowvortex.ShadowVortexTileEntity;
import com.pasterdream.pasterdreammod.world.block.twilightlantern.TwilightLanternBlockEntity;
import com.pasterdream.pasterdreammod.world.block.shadowbed.ShadowBedBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.anvil.WeaponWorkshopAnvilBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceAddonBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.coolerpot.WeaponWorkshopCoolerPotBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.grindstone.WeaponWorkshopGrindStoneBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PasterDreamMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<QYMDollBlockEntity>> QYM_DOLL = BLOCK_ENTITIES.register("qym_doll", () -> BlockEntityType.Builder.of(QYMDollBlockEntity::new, ModBlocks.QYM_DOLL.get()).build(null));
    public static final RegistryObject<BlockEntityType<UUZDollBlockEntity>> UUZ_DOLL = BLOCK_ENTITIES.register("uuz_doll", () -> BlockEntityType.Builder.of(UUZDollBlockEntity::new, ModBlocks.UUZ_DOLL.get()).build(null));
    public static final RegistryObject<BlockEntityType<ClaypanBlockEntity>> CLAYPAN = BLOCK_ENTITIES.register("claypan", () -> BlockEntityType.Builder.of(ClaypanBlockEntity::new, ModBlocks.CLAYPAN.get()).build(null));
    public static final RegistryObject<BlockEntityType<DreamCauldronBlockEntity>> DREAM_CAULDRON = BLOCK_ENTITIES.register("dream_cauldron", () -> BlockEntityType.Builder.of(DreamCauldronBlockEntity::new, ModBlocks.DREAM_CAULDRON.get()).build(null));
    public static final RegistryObject<BlockEntityType<DyedreamDeskBlockEntity>> DYEDREAM_DESK = BLOCK_ENTITIES.register("dyedream_desk", () -> BlockEntityType.Builder.of(DyedreamDeskBlockEntity::new, ModBlocks.DYEDREAM_DESK.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShadowDeskBlockEntity>> SHADOW_DESK = BLOCK_ENTITIES.register("shadow_desk", () -> BlockEntityType.Builder.of(ShadowDeskBlockEntity::new, ModBlocks.SHADOW_DESK.get()).build(null));
    public static final RegistryObject<BlockEntityType<PicnicBasketBlockEntity>> PICNIC_BASKET = BLOCK_ENTITIES.register("picnic_basket", () -> BlockEntityType.Builder.of(PicnicBasketBlockEntity::new, ModBlocks.PICNIC_BASKET.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShadowChestBlockEntity>> SHADOW_CHEST = BLOCK_ENTITIES.register("shadow_chest", () -> BlockEntityType.Builder.of(ShadowChestBlockEntity::new, ModBlocks.SHADOW_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<WindMoorCrateBlockEntity>> WIND_MOOR_CRATE = BLOCK_ENTITIES.register("wind_moor_crate", () -> BlockEntityType.Builder.of(WindMoorCrateBlockEntity::new, ModBlocks.WIND_MOOR_CRATE.get()).build(null));
    public static final RegistryObject<BlockEntityType<TheEndlessBookOfDreamSeekersBlockEntity>> THE_ENDLESS_BOOK_OF_DREAM_SEEKERS = BLOCK_ENTITIES.register("the_endless_book_of_dream_seekers", () -> BlockEntityType.Builder.of(TheEndlessBookOfDreamSeekersBlockEntity::new, ModBlocks.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get()).build(null));
    public static final RegistryObject<BlockEntityType<ResearchTableBlockEntity>> RESEARCH_TABLE = BLOCK_ENTITIES.register("research_table", () -> BlockEntityType.Builder.of(ResearchTableBlockEntity::new, ModBlocks.RESEARCH_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ResearchTableAddonBlockEntity>> RESEARCH_TABLE_ADDON = BLOCK_ENTITIES.register("research_table_addon", () -> BlockEntityType.Builder.of(ResearchTableAddonBlockEntity::new, ModBlocks.RESEARCH_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<LostSwordTombBlockEntity>> LOST_SWORD_TOMB = BLOCK_ENTITIES.register("lost_sword_tomb", () -> BlockEntityType.Builder.of(LostSwordTombBlockEntity::new, ModBlocks.LOST_SWORD_TOMB.get()).build(null));
    public static final RegistryObject<BlockEntityType<LifeCrystalBlockEntity>> LIFE_CRYSTAL = BLOCK_ENTITIES.register("life_crystal", () -> BlockEntityType.Builder.of(LifeCrystalBlockEntity::new, ModBlocks.LIFE_CRYSTAL.get()).build(null));
    public static final RegistryObject<BlockEntityType<GoldenFoxSculptureBlockEntity>> GOLDEN_FOX_SCULPTURE = BLOCK_ENTITIES.register("golden_fox_sculpture", () -> BlockEntityType.Builder.of(GoldenFoxSculptureBlockEntity::new, ModBlocks.GOLDEN_FOX_SCULPTURE.get()).build(null));
    public static final RegistryObject<BlockEntityType<DesertHeroTombBlockEntity>> DESERT_HERO_TOMB = BLOCK_ENTITIES.register("desert_hero_tomb", () -> BlockEntityType.Builder.of(DesertHeroTombBlockEntity::new, ModBlocks.DESERT_HERO_TOMB.get()).build(null));
    public static final RegistryObject<BlockEntityType<MeltDreamCrystalChestBlockEntity>> MELT_DREAM_CRYSTAL_CHEST = BLOCK_ENTITIES.register("melt_dream_crystal_chest", () -> BlockEntityType.Builder.of(MeltDreamCrystalChestBlockEntity::new, ModBlocks.MELT_DREAM_CRYSTAL_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<OpenedMeltDreamCrystalChestBlockEntity>> OPENED_MELT_DREAM_CRYSTAL_CHEST = BLOCK_ENTITIES.register("opened_melt_dream_crystal_chest", () -> BlockEntityType.Builder.of(OpenedMeltDreamCrystalChestBlockEntity::new, ModBlocks.OPENED_MELT_DREAM_CRYSTAL_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<FoxSculptureBlockEntity>> FOX_SCULPTURE = BLOCK_ENTITIES.register("fox_sculpture", () -> BlockEntityType.Builder.of(FoxSculptureBlockEntity::new, ModBlocks.FOX_SCULPTURE.get()).build(null));
    public static final RegistryObject<BlockEntityType<NippyEdelweissBlockEntity>> NIPPY_EDELWEISS = BLOCK_ENTITIES.register("nippy_edelweiss", () -> BlockEntityType.Builder.of(NippyEdelweissBlockEntity::new, ModBlocks.NIPPY_EDELWEISS.get()).build(null));
    public static final RegistryObject<BlockEntityType<DreamAccumulatorBlockEntity>> DREAM_ACCUMULATOR = BLOCK_ENTITIES.register("dream_accumulator", () -> BlockEntityType.Builder.of(DreamAccumulatorBlockEntity::new, ModBlocks.DREAM_ACCUMULATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShadowVortexTileEntity>> SHADOW_VORTEX = BLOCK_ENTITIES.register("shadow_vortex", () -> BlockEntityType.Builder.of(ShadowVortexTileEntity::new, ModBlocks.SHADOW_VORTEX.get()).build(null));
    public static final RegistryObject<BlockEntityType<WeaponWorkshopAnvilBlockEntity>> WEAPON_WORKSHOP_ANVIL = BLOCK_ENTITIES.register("weapon_workshop_anvil", () -> BlockEntityType.Builder.of(WeaponWorkshopAnvilBlockEntity::new, ModBlocks.WEAPON_WORKSHOP_ANVIL.get()).build(null));
    public static final RegistryObject<BlockEntityType<WeaponWorkshopCoolerPotBlockEntity>> WEAPON_WORKSHOP_COOLER_POT = BLOCK_ENTITIES.register("weapon_workshop_cooler_pot", () -> BlockEntityType.Builder.of(WeaponWorkshopCoolerPotBlockEntity::new, ModBlocks.WEAPON_WORKSHOP_COOLER_POT.get()).build(null));
    public static final RegistryObject<BlockEntityType<WeaponWorkshopGrindStoneBlockEntity>> WEAPON_WORKSHOP_GRIND_STONE = BLOCK_ENTITIES.register("weapon_workshop_grind_stone", () -> BlockEntityType.Builder.of(WeaponWorkshopGrindStoneBlockEntity::new, ModBlocks.WEAPON_WORKSHOP_GRIND_STONE.get()).build(null));
    public static final RegistryObject<BlockEntityType<WeaponWorkshopBlastFurnaceBlockEntity>> WEAPON_WORKSHOP_BLAST_FURNACE = BLOCK_ENTITIES.register("weapon_workshop_blast_furnace", () -> BlockEntityType.Builder.of(WeaponWorkshopBlastFurnaceBlockEntity::new, ModBlocks.WEAPON_WORKSHOP_BLAST_FURNACE.get()).build(null));
    public static final RegistryObject<BlockEntityType<WeaponWorkshopBlastFurnaceAddonBlockEntity>> WEAPON_WORKSHOP_BLAST_FURNACE_ADDON = BLOCK_ENTITIES.register("weapon_workshop_blast_furnace_addon", () -> BlockEntityType.Builder.of(WeaponWorkshopBlastFurnaceAddonBlockEntity::new, ModBlocks.WEAPON_WORKSHOP_BLAST_FURNACE.get()).build(null));
    public static final RegistryObject<BlockEntityType<TwilightLanternBlockEntity>> TWILIGHT_LANTERN = BLOCK_ENTITIES.register("twilight_lantern", () -> BlockEntityType.Builder.of(TwilightLanternBlockEntity::new, ModBlocks.TWILIGHT_LANTERN.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShadowBedBlockEntity>> SHADOW_BED = BLOCK_ENTITIES.register("shadow_bed", () -> BlockEntityType.Builder.of(ShadowBedBlockEntity::new, ModBlocks.SHADOW_BED.get()).build(null));

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
