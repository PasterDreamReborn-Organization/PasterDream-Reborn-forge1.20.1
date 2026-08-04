package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.FoxFireEntity;
import com.pasterdream.pasterdreammod.world.entity.GoldenFoxEntity;
import com.pasterdream.pasterdreammod.world.entity.MeltDreamCrystalEntityEntity;
import com.pasterdream.pasterdreammod.world.entity.PinkChickenEntity;
import com.pasterdream.pasterdreammod.world.entity.PinkSlimeEntity;
import com.pasterdream.pasterdreammod.world.entity.TerraswordWaveEntity;
import com.pasterdream.pasterdreammod.world.entity.WhiteSwordRainProjectileEntity;
import com.pasterdream.pasterdreammod.world.entity.ShadowGolemEntity;
import com.pasterdream.pasterdreammod.world.entity.ThrownPinkEgg;
import com.pasterdream.pasterdreammod.world.entity.PebbleProjectile;
import com.pasterdream.pasterdreammod.world.entity.terrorbeak.TerrorbeakEntity;
import com.pasterdream.pasterdreammod.world.entity.ShadowHandEntity;
import com.pasterdream.pasterdreammod.world.entity.ShadowMagicballEntity;
import com.pasterdream.pasterdreammod.world.entity.ShadowTuneTotemEntity;
import com.pasterdream.pasterdreammod.world.entity.AaroncosLeftHandEntity;
import com.pasterdream.pasterdreammod.world.entity.AaroncosRightHandEntity;
import com.pasterdream.pasterdreammod.world.entity.ghost.ShadowGhostEntity;
import com.pasterdream.pasterdreammod.world.entity.ghost.WailingShadowGhostEntity;
import com.pasterdream.pasterdreammod.world.entity.ghost.FriendlyShadowGhostEntity;
import com.pasterdream.pasterdreammod.world.entity.ghost.SquealWaveProjectileEntity;
import com.pasterdream.pasterdreammod.world.entity.beetle.BlackBeetleEntity;
import com.pasterdream.pasterdreammod.world.entity.beetle.BlackBeetleMotherEntity;
import com.pasterdream.pasterdreammod.world.entity.shakingcrystal.ShakingCrystalEntity;
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

    public static final RegistryObject<EntityType<PebbleProjectile>> PEBBLE_PROJECTILE = register("pebble_projectile",
            EntityType.Builder.<PebbleProjectile>of(PebbleProjectile::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(PebbleProjectile::new)
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

    public static final RegistryObject<EntityType<TerrorbeakEntity>> TERRORBEAK = register("terrorbeak",
            EntityType.Builder.<TerrorbeakEntity>of(TerrorbeakEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(TerrorbeakEntity::new)
                    .sized(1.8f, 3.5f));

    public static final RegistryObject<EntityType<TerrorbeakEntity>> CRAZY_TERRORBEAK = register("crazy_terrorbeak",
            EntityType.Builder.<TerrorbeakEntity>of(TerrorbeakEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(TerrorbeakEntity::new)
                    .fireImmune()
                    .sized(1.8f, 4f));

    public static final RegistryObject<EntityType<TerrorbeakEntity>> WEAKENESS_TERRORBEAK = register("weakeness_terrorbeak",
            EntityType.Builder.<TerrorbeakEntity>of(TerrorbeakEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(TerrorbeakEntity::new)
                    .fireImmune()
                    .sized(1.5f, 3f));

    public static final RegistryObject<EntityType<ShadowHandEntity>> SHADOW_HAND = register("shadow_hand",
            EntityType.Builder.<ShadowHandEntity>of(ShadowHandEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(ShadowHandEntity::new)
                    .fireImmune()
                    .sized(0.6f, 0.8f));

    public static final RegistryObject<EntityType<ShadowMagicballEntity>> SHADOW_MAGICBALL = register("shadow_magicball",
            EntityType.Builder.<ShadowMagicballEntity>of(ShadowMagicballEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(ShadowMagicballEntity::new)
                    .fireImmune()
                    .sized(1f, 1.5f));

    public static final RegistryObject<EntityType<ShadowTuneTotemEntity>> SHADOW_TUNE_TOTEM = register("shadow_tune_totem",
            EntityType.Builder.<ShadowTuneTotemEntity>of(ShadowTuneTotemEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(ShadowTuneTotemEntity::new)
                    .fireImmune()
                    .sized(2f, 8f));

    public static final RegistryObject<EntityType<AaroncosLeftHandEntity>> AARONCOS_LEFT_HAND = register("aaroncos_left_hand",
            EntityType.Builder.<AaroncosLeftHandEntity>of(AaroncosLeftHandEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(AaroncosLeftHandEntity::new)
                    .fireImmune()
                    .sized(3.5f, 3.5f));

    public static final RegistryObject<EntityType<AaroncosRightHandEntity>> AARONCOS_RIGHT_HAND = register("aaroncos_right_hand",
            EntityType.Builder.<AaroncosRightHandEntity>of(AaroncosRightHandEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(AaroncosRightHandEntity::new)
                    .fireImmune()
                    .sized(3.5f, 3.5f));

    public static final RegistryObject<EntityType<ShadowGhostEntity>> SHADOW_GHOST = register("shadow_ghost",
            EntityType.Builder.<ShadowGhostEntity>of(ShadowGhostEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(ShadowGhostEntity::new)
                    .fireImmune()
                    .sized(0.7f, 1.2f));

    public static final RegistryObject<EntityType<ShadowGhostEntity>> SHADOW_SQUEAL_GHOST = register("shadow_squeal_ghost",
            EntityType.Builder.<ShadowGhostEntity>of(ShadowGhostEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(ShadowGhostEntity::new)
                    .fireImmune()
                    .sized(0.7f, 1.2f));

    public static final RegistryObject<EntityType<WailingShadowGhostEntity>> WAILING_SHADOW_GHOST = register("wailing_shadow_ghost",
            EntityType.Builder.<WailingShadowGhostEntity>of(WailingShadowGhostEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(WailingShadowGhostEntity::new)
                    .fireImmune()
                    .sized(0.8f, 1.3f));

    public static final RegistryObject<EntityType<FriendlyShadowGhostEntity>> FRIENDLY_SHADOW_GHOST = register("friendly_shadow_ghost",
            EntityType.Builder.<FriendlyShadowGhostEntity>of(FriendlyShadowGhostEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(FriendlyShadowGhostEntity::new)
                    .fireImmune()
                    .sized(0.7f, 1.2f));

    public static final RegistryObject<EntityType<SquealWaveProjectileEntity>> SQUEAL_WAVE_PROJECTILE = register("squeal_wave_projectile",
            EntityType.Builder.<SquealWaveProjectileEntity>of(SquealWaveProjectileEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(1)
                    .setCustomClientFactory(SquealWaveProjectileEntity::new)
                    .sized(0.5f, 0.5f));

    public static final RegistryObject<EntityType<WhiteSwordRainProjectileEntity>> WHITE_SWORD_RAIN_PROJECTILE = register("white_sword_rain_projectile",
            EntityType.Builder.<WhiteSwordRainProjectileEntity>of(WhiteSwordRainProjectileEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(WhiteSwordRainProjectileEntity::new)
                    .sized(0.25f, 0.25f));

    public static final RegistryObject<EntityType<BlackBeetleEntity>> BLACK_BEETLE = register("black_beetle",
            EntityType.Builder.<BlackBeetleEntity>of(BlackBeetleEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(48)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(BlackBeetleEntity::new)
                    .sized(0.6f, 0.5f));

    public static final RegistryObject<EntityType<BlackBeetleMotherEntity>> BLACK_BEETLE_MOTHER = register("black_beetle_mother",
            EntityType.Builder.<BlackBeetleMotherEntity>of(BlackBeetleMotherEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(48)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(BlackBeetleMotherEntity::new)
                    .sized(2f, 1f));

    public static final RegistryObject<EntityType<ShakingCrystalEntity>> SHAKING_CRYSTAL = register("shaking_crystal",
            EntityType.Builder.<ShakingCrystalEntity>of(ShakingCrystalEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(ShakingCrystalEntity::new)
                    .fireImmune()
                    .sized(0.6f, 0.8f));

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
        event.enqueueWork(TerrorbeakEntity::init);
        event.enqueueWork(ShadowHandEntity::init);
        event.enqueueWork(ShadowGhostEntity::init);
        event.enqueueWork(WailingShadowGhostEntity::init);
        event.enqueueWork(FriendlyShadowGhostEntity::init);
        event.enqueueWork(BlackBeetleEntity::init);
        event.enqueueWork(BlackBeetleMotherEntity::init);
        event.enqueueWork(ShakingCrystalEntity::init);
        event.enqueueWork(ShadowMagicballEntity::init);
        event.enqueueWork(ShadowTuneTotemEntity::init);
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
        event.put(TERRORBEAK.get(), TerrorbeakEntity.createTerrorbeakAttributes().build());
        event.put(CRAZY_TERRORBEAK.get(), TerrorbeakEntity.createCrazyTerrorbeakAttributes().build());
        event.put(WEAKENESS_TERRORBEAK.get(), TerrorbeakEntity.createWeakenessTerrorbeakAttributes().build());
        event.put(SHADOW_HAND.get(), ShadowHandEntity.createAttributes().build());
        event.put(SHADOW_GHOST.get(), ShadowGhostEntity.createShadowGhostAttributes().build());
        event.put(SHADOW_SQUEAL_GHOST.get(), ShadowGhostEntity.createShadowSquealGhostAttributes().build());
        event.put(WAILING_SHADOW_GHOST.get(), WailingShadowGhostEntity.createAttributes().build());
        event.put(FRIENDLY_SHADOW_GHOST.get(), FriendlyShadowGhostEntity.createAttributes().build());
        event.put(BLACK_BEETLE.get(), BlackBeetleEntity.createAttributes().build());
        event.put(BLACK_BEETLE_MOTHER.get(), BlackBeetleMotherEntity.createAttributes().build());
        event.put(SHAKING_CRYSTAL.get(), ShakingCrystalEntity.createAttributes().build());
        event.put(SHADOW_MAGICBALL.get(), ShadowMagicballEntity.createAttributes().build());
        event.put(SHADOW_TUNE_TOTEM.get(), ShadowTuneTotemEntity.createAttributes().build());
        event.put(AARONCOS_LEFT_HAND.get(), AaroncosLeftHandEntity.createAttributes().build());
        event.put(AARONCOS_RIGHT_HAND.get(), AaroncosRightHandEntity.createAttributes().build());
    }
}
