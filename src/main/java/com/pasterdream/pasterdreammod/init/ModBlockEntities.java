package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket.PicnicBasketBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest.ShadowChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.windmoorcrate.WindMoorCrateBlockEntity;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.desk.dyedreamdesk.DyedreamDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.desk.shadowdesk.ShadowDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PasterDreamMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<ClaypanBlockEntity>> CLAYPAN = BLOCK_ENTITIES.register("claypan", () -> BlockEntityType.Builder.of(ClaypanBlockEntity::new, ModBlocks.CLAYPAN.get()).build(null));
    public static final RegistryObject<BlockEntityType<DreamCauldronBlockEntity>> DREAM_CAULDRON = BLOCK_ENTITIES.register("dream_cauldron", () -> BlockEntityType.Builder.of(DreamCauldronBlockEntity::new, ModBlocks.DREAM_CAULDRON.get()).build(null));
    public static final RegistryObject<BlockEntityType<DyedreamDeskBlockEntity>> DYEDREAM_DESK = BLOCK_ENTITIES.register("dyedream_desk", () -> BlockEntityType.Builder.of(DyedreamDeskBlockEntity::new, ModBlocks.DYEDREAM_DESK.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShadowDeskBlockEntity>> SHADOW_DESK = BLOCK_ENTITIES.register("shadow_desk", () -> BlockEntityType.Builder.of(ShadowDeskBlockEntity::new, ModBlocks.SHADOW_DESK.get()).build(null));
    public static final RegistryObject<BlockEntityType<PicnicBasketBlockEntity>> PICNIC_BASKET = BLOCK_ENTITIES.register("picnic_basket", () -> BlockEntityType.Builder.of(PicnicBasketBlockEntity::new, ModBlocks.PICNIC_BASKET.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShadowChestBlockEntity>> SHADOW_CHEST = BLOCK_ENTITIES.register("shadow_chest", () -> BlockEntityType.Builder.of(ShadowChestBlockEntity::new, ModBlocks.SHADOW_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<WindMoorCrateBlockEntity>> WIND_MOOR_CRATE = BLOCK_ENTITIES.register("wind_moor_crate", () -> BlockEntityType.Builder.of(WindMoorCrateBlockEntity::new, ModBlocks.WIND_MOOR_CRATE.get()).build(null));

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }

}
