package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.FoxFireEntity;
import com.pasterdream.pasterdreammod.world.entity.GoldenFoxEntity;
import com.pasterdream.pasterdreammod.world.entity.MeltDreamCrystalEntityEntity;
import com.pasterdream.pasterdreammod.world.entity.PinkChickenEntity;
import com.pasterdream.pasterdreammod.world.entity.PinkSlimeEntity;
import com.pasterdream.pasterdreammod.world.entity.TerraswordWaveEntity;
import com.pasterdream.pasterdreammod.world.entity.ShadowGolemEntity;
import com.pasterdream.pasterdreammod.world.entity.ThrownPinkEgg;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PasterDreamMod.MOD_ID);

    public static final RegistryObject<EntityType<TerraswordWaveEntity>> TERRASWORD_WAVE = register("terrasword_wave",
            EntityType.Builder.<TerraswordWaveEntity>of(TerraswordWaveEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(TerraswordWaveEntity::new)
                    .fireImmune()
                    .sized(0.1f, 0.1f));

    public static final RegistryObject<EntityType<FoxFireEntity>> FOX_FIRE = register("fox_fire",
            EntityType.Builder.<FoxFireEntity>of(FoxFireEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(FoxFireEntity::new)
                    .fireImmune()
                    .sized(1f, 1f));

    public static final RegistryObject<EntityType<MeltDreamCrystalEntityEntity>> MELT_DREAM_CRYSTAL_ENTITY = register("melt_dream_crystal_entity",
            EntityType.Builder.<MeltDreamCrystalEntityEntity>of(MeltDreamCrystalEntityEntity::new, MobCategory.CREATURE)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(MeltDreamCrystalEntityEntity::new)
                    .fireImmune()
                    .sized(0.6f, 1f));

    public static final RegistryObject<EntityType<PinkChickenEntity>> PINK_CHICKEN = register("pink_chicken",
            EntityType.Builder.<PinkChickenEntity>of(PinkChickenEntity::new, MobCategory.CREATURE)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(PinkChickenEntity::new)
                    .sized(0.4f, 0.7f));

    public static final RegistryObject<EntityType<PinkSlimeEntity>> PINK_SLIME = register("pink_slime",
            EntityType.Builder.<PinkSlimeEntity>of(PinkSlimeEntity::new, MobCategory.CREATURE)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(PinkSlimeEntity::new)
                    .sized(0.5f, 0.5f));

    public static final RegistryObject<EntityType<ThrownPinkEgg>> THROWN_PINK_EGG = register("thrown_pink_egg",
            EntityType.Builder.<ThrownPinkEgg>of(ThrownPinkEgg::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(ThrownPinkEgg::new)
                    .sized(0.25f, 0.25f));

    public static final RegistryObject<EntityType<GoldenFoxEntity>> GOLDEN_FOX = register("golden_fox",
            EntityType.Builder.<GoldenFoxEntity>of(GoldenFoxEntity::new, MobCategory.CREATURE)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(GoldenFoxEntity::new)
                    .sized(0.6f, 0.6f));

    public static final RegistryObject<EntityType<ShadowGolemEntity>> SHADOW_GOLEM = register("shadow_golem",
            EntityType.Builder.<ShadowGolemEntity>of(ShadowGolemEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(ShadowGolemEntity::new)
                    .sized(2.2f, 3.5f));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return REGISTRY.register(name, () -> builder.build(name));
    }

    public static void register(net.minecraftforge.eventbus.api.IEventBus bus) {
        REGISTRY.register(bus);
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(TerraswordWaveEntity::init);
        event.enqueueWork(FoxFireEntity::init);
        event.enqueueWork(MeltDreamCrystalEntityEntity::init);
        event.enqueueWork(PinkChickenEntity::init);
        event.enqueueWork(PinkSlimeEntity::init);
        event.enqueueWork(GoldenFoxEntity::init);
        event.enqueueWork(ShadowGolemEntity::init);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(TERRASWORD_WAVE.get(), TerraswordWaveEntity.createAttributes().build());
        event.put(FOX_FIRE.get(), FoxFireEntity.createAttributes().build());
        event.put(MELT_DREAM_CRYSTAL_ENTITY.get(), MeltDreamCrystalEntityEntity.createAttributes().build());
        event.put(PINK_CHICKEN.get(), PinkChickenEntity.createAttributes().build());
        event.put(PINK_SLIME.get(), PinkSlimeEntity.createAttributes().build());
        event.put(GOLDEN_FOX.get(), GoldenFoxEntity.createAttributes().build());
        event.put(SHADOW_GOLEM.get(), ShadowGolemEntity.createAttributes().build());
    }
}
