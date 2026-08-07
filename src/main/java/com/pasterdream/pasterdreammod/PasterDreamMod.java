package com.pasterdream.pasterdreammod;

import com.pasterdream.pasterdreammod.client.*;
import com.pasterdream.pasterdreammod.config.PasterDreamClientConfig;
import com.pasterdream.pasterdreammod.helper.fluidhandler.FluidHandlerResolvers;
import com.pasterdream.pasterdreammod.helper.sanbiomeratemanager.SanBiomeRateManager;
import com.pasterdream.pasterdreammod.helper.tooltipadder.AddToolTip;
import com.pasterdream.pasterdreammod.event.ModMobDrops;
import com.pasterdream.pasterdreammod.event.ModMobSpawnEvents;
import com.pasterdream.pasterdreammod.datagen.common.ModRaidRewardsProvider;
import com.pasterdream.pasterdreammod.event.ModWorldGenEvents;
import com.pasterdream.pasterdreammod.event.PlayerEvents;
import com.pasterdream.pasterdreammod.init.*;
import com.pasterdream.pasterdreammod.world.item.curio.RedDewRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.StrikeRingItem;
import com.pasterdream.pasterdreammod.world.entity.RejuvenationBottleEntity;
import com.pasterdream.pasterdreammod.world.item.prophecycard.ProphecyCardItem;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.AngelWingItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.ForsakensWingItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.MachineLightWingItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.qym.QymArmorEvents;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolActions;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import com.pasterdream.pasterdreammod.world.item.ModToolTiers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib.GeckoLib;

@Mod(PasterDreamMod.MOD_ID)
public class PasterDreamMod
{
    public static final String MOD_ID = "pasterdream";

    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> WORK_QUEUE = new ConcurrentLinkedQueue<>();
    private static final UUID SWIFT_STRIKE_ATTACK_SPEED_UUID = UUID.fromString("bdf05f70-b53d-4828-8e37-9a502bde0ec1");

    public static void queueServerWork(int tick, Runnable action) {
        WORK_QUEUE.add(new AbstractMap.SimpleEntry<>(action, tick));
    }

    public PasterDreamMod(FMLJavaModLoadingContext context)
    {
        GeckoLib.initialize();

        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);             //注册物品
        ModCreativeModeTabs.register(modEventBus);  //注册创造模式物品栏
        ModParticleTypes.register(modEventBus);     //注册粒子
        ModBlocks.register(modEventBus);            //注册方块
        ModBlockEntities.register(modEventBus);     //注册方块实体
        ModFluids.register(modEventBus);            //注册流体
        ModMenus.register(modEventBus);             //注册菜单
        ModRecipes.register(modEventBus);           //注册配方
        ModSounds.register(modEventBus);            //注册音效
        ModEffects.register(modEventBus);           //注册药水效果
        ModPotions.register(modEventBus);           //注册药水类型
        ModAttributes.register(modEventBus);        //注册属性
        ModTreeDecoratorTypes.register(modEventBus); //注册树木装饰器类型
        ModFeatures.register(modEventBus);          //注册自定义特征
                ModEntities.register(modEventBus);          //注册实体
        ModLootTables.register(modEventBus);        //注册自定义战利品函数类型
        ModNetwork.register();                      //注册网络包
        ModEnchantment.register(modEventBus);       //注册附魔
        ModCriteriaTriggers.init();                 //注册自定义进度触发器

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.addListener(this::AddItemTooltip);
        MinecraftForge.EVENT_BUS.addListener(this::AddCommand);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onHoeTill);
        MinecraftForge.EVENT_BUS.addListener(ModMobDrops::onLivingDrops);
        MinecraftForge.EVENT_BUS.addListener(ModRaidRewardsProvider::onLootTableLoad);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onLivingHurt);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onFoxFireVulnerableHurt);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onLivingHurt);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerSleepInBed);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onAttackEntity);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerChangedDimension);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerLoggedIn);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onAdvancementEarned);
        MinecraftForge.EVENT_BUS.addListener(QymArmorEvents::onEquipChange);
        MinecraftForge.EVENT_BUS.addListener(MachineLightWingItem::onEquipChange);
        MinecraftForge.EVENT_BUS.addListener(AngelWingItem::onEquipChange);
        MinecraftForge.EVENT_BUS.addListener(ForsakensWingItem::onEquipChange);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onItemAttributeModifier);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onShelterLivingHurt);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onGuardLivingHurt);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onRapidReactionFall);
        MinecraftForge.EVENT_BUS.addListener(ModWorldGenEvents::onLevelLoad);
        MinecraftForge.EVENT_BUS.addListener(ModWorldGenEvents::onServerTick);
        MinecraftForge.EVENT_BUS.addListener(ModMobSpawnEvents::onEntityJoinLevel);
        modEventBus.addListener(this::AddOverlays);
        modEventBus.addListener(this::AddEntityRenderersEvent);
        modEventBus.addListener(this::AddRegisterLayerDefinitions);
        MinecraftForge.EVENT_BUS.addListener(this::onAddReloadListeners);

        MinecraftForge.EVENT_BUS.register(this);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);     //加载配置文件
        context.registerConfig(ModConfig.Type.CLIENT, PasterDreamClientConfig.SPEC, "PasterDream-Client.toml");
    }

    //在这里输入通用端注册内容
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        FluidHandlerResolvers.FluidHandlerResolverRegister();
        ModFluidContainerRelation.registerFluidContainerRelation();
        ModDreamNotesContentRelation.registerDreamNotesContentRelation();
        ModDreamNotesBookContentRelation.registerDreamNotesBookContentRelation();
        ModCropRelation.registerCropRelation();
        ProphecyCardItem.registerAllCardEffects();

        // 药剂瓶砸碎效果绑定
        registerLightningBottleEffect();
        registerHighlyToxicBottleEffect();
        registerRejuvenationBottleEffect();
        registerBerserkBottleEffect();
        registerFrozenBottleEffect();
    }

    //在这里输入客户端注册内容
    private void clientSetup(final FMLClientSetupEvent event)
    {
        ClientSetRenderLayer.register();
        ModScreens.register(event);
        ModBlockEntityRenderer.FMLClientSetupEventRegister(event);
        event.enqueueWork(this::registerItemProperties);
    }

    private void registerItemProperties()
    {
        // 星者祈愿钓竿出杆切换模型（原版 cast predicate 仅注册在 Items.FISHING_ROD 上）
        ItemProperties.register(
                ModItems.STAR_WISH_ROD.get(),
                ResourceLocation.parse("cast"),
                (stack, level, entity, seed) -> {
                    if (entity == null) return 0.0F;
                    boolean held = entity.getMainHandItem() == stack || entity.getOffhandItem() == stack;
                    if (entity instanceof net.minecraft.world.entity.player.Player player) {
                        return held && player.fishing != null ? 1.0F : 0.0F;
                    }
                    return 0.0F;
                }
        );

        ItemProperties.register(
                ModItems.RED_DEW_RING.get(),
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "lv"),
                (stack, level, entity, seed) -> RedDewRingItem.getPredicateValue(RedDewRingItem.getLv(stack))
        );

        ItemProperties.register(
                ModItems.STRIKE_RING.get(),
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "lv"),
                (stack, level, entity, seed) -> StrikeRingItem.getPredicateValue(StrikeRingItem.getLv(stack))
        );

        // 预言卡：按 NBT Type 切换纹理
        ItemProperties.register(
                ModItems.PROPHECY_CARD.get(),
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "type"),
                (stack, level, entity, seed) -> ProphecyCardItem.getPredicateValue(stack)
        );

        // 药剂瓶：按 NBT PotionType 切换纹理
        ItemProperties.register(
                ModItems.POTION_BOTTLE.get(),
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "type"),
                (stack, level, entity, seed) -> PotionBottleItem.getPredicateValue(stack)
        );
    }

    // ===== 药剂瓶效果注册 =====

    // ===== 闪电药剂瓶 =====
    private static void registerLightningBottleEffect() {
        PotionBottleItem.registerEffect(PotionBottleItem.TYPE_LIGHTNING,
                (stack, level, thrower, hitPos) -> {
                    if (!(level instanceof ServerLevel serverLevel)) return;

                    // t=2: 释放乌云 + 充能音效
                    queueServerWork(2, () -> {
                        serverLevel.playSound(null, hitPos.x, hitPos.y, hitPos.z,
                                ModSounds.LIGHTNING_CHARGE.get(), SoundSource.NEUTRAL,
                                1.0F, 1.0F);
                        serverLevel.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                                hitPos.x, hitPos.y, hitPos.z, 64, 2.0, 0.5, 2.0, 0.0);
                        serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                                hitPos.x, hitPos.y, hitPos.z, 32, 2.0, 0.5, 2.0, 0.0);
                    });

                    // 电火花分10次刷出，总计32颗
                    int[] sparkTicks = {2, 7, 12, 17, 22, 27, 32, 37, 42, 47};
                    for (int i = 0; i < sparkTicks.length; i++) {
                        int count = 3 + (i < 2 ? 1 : 0); // 前2次4颗，后8次3颗 → 4+4+8×3=32
                        queueServerWork(sparkTicks[i], () -> serverLevel.sendParticles(
                                ParticleTypes.ELECTRIC_SPARK,
                                hitPos.x, hitPos.y, hitPos.z, count, 2.0, 0.5, 2.0, 0.0));
                    }

                    // t=55, 65, 75, 85: 4道随机落雷
                    for (int delay : new int[]{55, 65, 75, 85}) {
                        queueServerWork(delay, () -> spawnLightningBolt(serverLevel, hitPos));
                    }
                });
    }

    // ===== 剧毒药剂瓶：多轮毒气波 =====

    /** 伤害波 tick：每轮 5 次伤害波 + 粒子 + debuff */
    private static final int[] TOXIC_DAMAGE_TICKS = {2, 22, 42, 62, 82};
    /** 纯粒子 tick */
    private static final int[] TOXIC_PARTICLE_TICKS = {12, 32, 52, 72};
    /** 每轮起点偏移：3 轮，间隔约 80tick */
    private static final int[] TOXIC_WAVE_OFFSETS = {0, 81, 161};

    private static void registerHighlyToxicBottleEffect() {
        PotionBottleItem.registerEffect(PotionBottleItem.TYPE_HIGHLY_TOXIC,
                (stack, level, thrower, hitPos) -> {
                    if (!(level instanceof ServerLevel sl)) return;

                    // t=0: 初始毒雾爆发 + 音效
                    sl.playSound(null, hitPos.x, hitPos.y, hitPos.z,
                            SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL,
                            0.7F, 1.0F);
                    sl.sendParticles(ModParticleTypes.POISON_GAS_PARTICLE.get(),
                            hitPos.x, hitPos.y + 1, hitPos.z, 72, 2.0, 1.0, 2.0, 0.01);

                    for (int offset : TOXIC_WAVE_OFFSETS) {
                        scheduleToxicWave(sl, hitPos, offset, thrower);
                    }
                });
    }

    private static void scheduleToxicWave(ServerLevel sl, Vec3 pos, int offset, LivingEntity thrower) {
        // 首波伤害：64粒子 + debuff
        queueServerWork(offset + TOXIC_DAMAGE_TICKS[0], () -> {
            sl.sendParticles(ModParticleTypes.POISON_GAS_PARTICLE.get(),
                    pos.x, pos.y + 1, pos.z, 64, 2.0, 1.0, 2.0, 0.01);
            applyToxicDebuff(sl, pos, thrower);
        });
        // 后续伤害波：32粒子 + debuff
        for (int i = 1; i < TOXIC_DAMAGE_TICKS.length; i++) {
            int dt = TOXIC_DAMAGE_TICKS[i];
            queueServerWork(offset + dt, () -> {
                sl.sendParticles(ModParticleTypes.POISON_GAS_PARTICLE.get(),
                        pos.x, pos.y + 1, pos.z, 32, 2.0, 1.0, 2.0, 0.01);
                applyToxicDebuff(sl, pos, thrower);
            });
        }
        // 纯粒子爆发：32粒子
        for (int pt : TOXIC_PARTICLE_TICKS) {
            queueServerWork(offset + pt, () -> sl.sendParticles(
                    ModParticleTypes.POISON_GAS_PARTICLE.get(),
                    pos.x, pos.y + 1, pos.z, 32, 2.0, 1.0, 2.0, 0.01));
        }
    }

    // ===== 回春药剂瓶 =====

    private static void registerRejuvenationBottleEffect() {
        PotionBottleItem.registerEffect(PotionBottleItem.TYPE_REJUVENATION,
                (stack, level, thrower, hitPos) -> {
                    if (!(level instanceof ServerLevel sl)) return;

                    // t=0: 初始粒子爆发 + 音效
                    sl.playSound(null, hitPos.x, hitPos.y, hitPos.z,
                            SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    sl.sendParticles(ModParticleTypes.REJUVENATION_PARTICLE.get(),
                            hitPos.x, hitPos.y + 1, hitPos.z, 64, 2.0, 1.0, 2.0, 0.05);
                    sl.sendParticles(ModParticleTypes.YELLOW_SMOKE_PARTICLE.get(),
                            hitPos.x, hitPos.y + 0.5, hitPos.z, 32, 2.0, 1.0, 2.0, 0.05);

                    // 生成治疗实体（400tick自删，每tick刷粒子+治疗）
                    RejuvenationBottleEntity entity = new RejuvenationBottleEntity(sl,
                            hitPos.x, hitPos.y, hitPos.z);
                    sl.addFreshEntity(entity);

                    // t=2 治疗音效
                    queueServerWork(2, () -> sl.playSound(null, hitPos.x, hitPos.y, hitPos.z,
                            SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.NEUTRAL, 0.2F, 1.0F));
                });
    }

    // ===== 狂暴药剂瓶 =====

    private static void registerBerserkBottleEffect() {
        PotionBottleItem.registerEffect(PotionBottleItem.TYPE_BERSERK,
                (stack, level, thrower, hitPos) -> {
                    if (!(level instanceof ServerLevel sl)) return;

                    // t=0: 粒子爆发 + 音效
                    sl.playSound(null, hitPos.x, hitPos.y, hitPos.z,
                            SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.2F, 1.0F);

                    // 1. 附魔符文 — 高层
                    sl.sendParticles(ParticleTypes.ENCHANT,
                            hitPos.x, hitPos.y + 3, hitPos.z, 100, 2.5, 1.0, 2.5, 0.02);
                    // 2. 龙息 — 低层
                    sl.sendParticles(ParticleTypes.DRAGON_BREATH,
                            hitPos.x, hitPos.y + 0.8, hitPos.z, 100, 2.5, 0.3, 2.5, 0.01);
                    // 3. 狂暴专属粒子 — 中层
                    sl.sendParticles(ModParticleTypes.BERSERK_PARTICLE.get(),
                            hitPos.x, hitPos.y + 2, hitPos.z, 12, 2.5, 1.0, 2.5, 0.02);
                    // 4. 末地烛闪光 — 点缀
                    sl.sendParticles(ParticleTypes.END_ROD,
                            hitPos.x, hitPos.y + 2, hitPos.z, 6, 2.5, 1.0, 2.5, 0.02);

                    // 8格半径内所有玩家施加狂暴buff（60tick=3秒）
                    sl.getEntitiesOfClass(Player.class,
                            new net.minecraft.world.phys.AABB(
                                    hitPos.x - 8, hitPos.y - 8, hitPos.z - 8,
                                    hitPos.x + 8, hitPos.y + 8, hitPos.z + 8),
                            e -> true)
                            .forEach(e -> e.addEffect(
                                    new net.minecraft.world.effect.MobEffectInstance(
                                            ModEffects.BERSERK_BUFF.get(), 60, 0)));
                });
    }

    // ===== 冰冻药剂瓶 =====

    private static void registerFrozenBottleEffect() {
        PotionBottleItem.registerEffect(PotionBottleItem.TYPE_FROZEN,
                (stack, level, thrower, hitPos) -> {
                    if (!(level instanceof ServerLevel sl)) return;

                    double x = hitPos.x + 0.5, y = hitPos.y, z = hitPos.z + 0.5;

                    // t=0: 冰冻冲击音效
                    sl.playSound(null, hitPos.x, hitPos.y, hitPos.z,
                            ModSounds.FROZEN_SHOCK.get(), SoundSource.NEUTRAL,
                            1.0F, 1.0F);

                    // t=0: 落地爆发 496 颗雪花
                    sl.sendParticles(ParticleTypes.SNOWFLAKE,
                            x, y + 1.5, z, 240, 2.5, 1.5, 2.5, 0.1);
                    sl.sendParticles(ModParticleTypes.SNOWFLAKE_0_PARTICLE.get(),
                            x, y + 2, z, 128, 2.5, 1.5, 2.5, 0.1);
                    sl.sendParticles(ModParticleTypes.SNOWFLAKE_1_PARTICLE.get(),
                            x, y + 2, z, 128, 2.5, 1.5, 2.5, 0.1);

                    // 5 波脉冲 (t=10,20,30,40,50)，每波 48+32+32
                    for (int t : new int[]{10, 20, 30, 40, 50}) {
                        queueServerWork(t, () -> {
                            sl.sendParticles(ParticleTypes.SNOWFLAKE,
                                    x, y + 1.5, z, 48, 2.5, 1.5, 2.5, 0.1);
                            sl.sendParticles(ModParticleTypes.SNOWFLAKE_0_PARTICLE.get(),
                                    x, y + 2, z, 32, 2.5, 1.5, 2.5, 0.1);
                            sl.sendParticles(ModParticleTypes.SNOWFLAKE_1_PARTICLE.get(),
                                    x, y + 2, z, 32, 2.5, 1.5, 2.5, 0.1);
                            applyFrozenDebuff(sl, hitPos, thrower);
                        });
                    }
                });
    }

    /** 5 格半径内所有实体（投掷者除外）施加冰冻 */
    private static void applyFrozenDebuff(ServerLevel sl, Vec3 pos, LivingEntity thrower) {
        double r = 5.0;
        sl.getEntitiesOfClass(LivingEntity.class,
                new net.minecraft.world.phys.AABB(
                        pos.x - r, pos.y - r, pos.z - r,
                        pos.x + r, pos.y + r, pos.z + r),
                e -> e != thrower)
                .forEach(e -> e.addEffect(
                        new net.minecraft.world.effect.MobEffectInstance(
                                ModEffects.FROZEN_BUFF.get(), 200, 0)));
    }

    /** 7 格半径内所有实体（投掷者除外）施加 剧毒IV + 虚弱 + 减速（10秒） */
    private static void applyToxicDebuff(ServerLevel sl, Vec3 pos, LivingEntity thrower) {
        double r = 7.0;
        sl.getEntitiesOfClass(LivingEntity.class,
                new net.minecraft.world.phys.AABB(
                        pos.x - r, pos.y - r, pos.z - r,
                        pos.x + r, pos.y + r, pos.z + r),
                e -> e != thrower)
                .forEach(e -> {
                    e.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                            ModEffects.HIGHLY_TOXIC_BUFF.get(), 200, 3)); // 剧毒 IV
                    e.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 0));
                    e.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 200, 0));
                });
    }

    /** 召唤闪电实体方法 */
    private static void spawnLightningBolt(ServerLevel level, Vec3 center) {
        double x = center.x + (level.random.nextDouble() - 0.5) * 4;
        double z = center.z + (level.random.nextDouble() - 0.5) * 4;
        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt != null) {
            bolt.moveTo(x, center.y, z);
            level.addFreshEntity(bolt);
        }
    }

    private void AddItemTooltip(ItemTooltipEvent event)
    {
        AddToolTip.addTooltip(event);
    }

    private void AddCommand(RegisterCommandsEvent event)
    {
        ModCommands.register(event.getDispatcher());
    }

    private void AddOverlays(RegisterGuiOverlaysEvent event)
    {
        event.registerAboveAll("melt_dream_energy", MeltDreamEnergyTank.MELT_DREAM_ENERGY_TANK);
        event.registerAboveAll("san", SanTank.SAN_TANK);
        event.registerBelowAll("lose_mind", LoseMind.GUI_OVERLAY);
        event.registerAboveAll("aaroncos_hand_boss_bar", AaroncosHandBossBar.OVERLAY);
    }

    private void AddEntityRenderersEvent(EntityRenderersEvent.RegisterRenderers event)
    {
        ModBlockEntityRenderer.EntityRenderersEventRegister(event);
        ModEntityRenderer.registerRenderers(event);
    }

    private void AddRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        ModEntityRenderer.registerLayerDefinitions(event);
    }

    private void onAddReloadListeners(AddReloadListenerEvent event)
    {
        event.addListener(SanBiomeRateManager.INSTANCE);
    }

    // 染梦耕地相关
    public static void onHoeTill(BlockEvent.BlockToolModificationEvent event) {
        if (event.getToolAction() != ToolActions.HOE_TILL) return;
        Block block = event.getState().getBlock();
        if (block == ModBlocks.DYEDREAM_GRASS_BLOCK.get() || block == ModBlocks.DYEDREAM_DIRT.get()) {
            event.setFinalState(ModBlocks.DYEDREAM_FARMLAND.get().defaultBlockState());
        }
    }

    // 染梦工具增强：持有染梦(染梦合金与融梦水晶)工具时伤害 +50%
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player
                && player.hasEffect(ModEffects.DYEDREAM_UP_BUFF.get())) {
            ItemStack weapon = player.getMainHandItem();
            if (weapon.getItem() instanceof TieredItem tiered
                    && (tiered.getTier() == ModToolTiers.DYEDREAM
                            || tiered.getTier() == ModToolTiers.MELT_DREAM)) {
                event.setAmount(event.getAmount() * 1.5f);
            }
        }
    }

    // 狐火易伤：被狐火立场标记的生物受到 +20% 伤害
    public static void onFoxFireVulnerableHurt(LivingHurtEvent event) {
        if (event.getEntity().getPersistentData().getBoolean("pasterdream:fox_fire_vulnerable")) {
            event.getEntity().getPersistentData().remove("pasterdream:fox_fire_vulnerable");
            event.setAmount(event.getAmount() * 1.2f);
        }
    }

    // 疾风连击：根据附魔等级增加攻击速度（剑每级+6%，斧每级+4%）
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        if (event.getSlotType() != EquipmentSlot.MAINHAND) return;

        var swiftStrike = ModEnchantment.SWIFT_STRIKE_ENCHANTMENT.get();

        ItemStack stack = event.getItemStack();
        int level = stack.getEnchantmentLevel(swiftStrike);
        if (level > 0) {
            double multiplier = stack.getItem() instanceof AxeItem ? 0.04 : 0.06;
            event.addModifier(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                            SWIFT_STRIKE_ATTACK_SPEED_UUID,
                            "Swift Strike attack speed bonus",
                            level * multiplier,
                            AttributeModifier.Operation.MULTIPLY_BASE
                    )
            );
        }
    }

    // 庇护：每级-2%受到的伤害（全身护甲叠加）
    public static void onShelterLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        LivingEntity entity = event.getEntity();

        var shelter = ModEnchantment.SHELTER_ENCHANTMENT.get();

        int totalLevel = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack armor = entity.getItemBySlot(slot);
            if (!armor.isEmpty()) {
                totalLevel += armor.getEnchantmentLevel(shelter);
            }
        }

        if (totalLevel > 0) {
            event.setAmount(event.getAmount() * (1.0f - totalLevel * 0.02f));
        }
    }

    // 守护：若受到超过最大生命值30%的伤害，超出部分减少60%（在护甲减伤之前应用）
    public static void onGuardLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!player.hasEffect(ModEffects.GUARD_BUFF.get())) return;

        float maxHealth = player.getMaxHealth();
        float threshold = (float) (maxHealth * Config.healthpercentguardneed);
        float postDamage = event.getAmount();
        float armor = (float) player.getArmorValue();
        float toughness = (float) player.getAttributeValue(Attributes.ARMOR_TOUGHNESS);

        // 迭代反推护甲减伤前的原始伤害
        float rawDamage = postDamage;
        for (int i = 0; i < 3; i++) {
            float afterArmor = CombatRules.getDamageAfterAbsorb(rawDamage, armor, toughness);
            float armorRatio = rawDamage > 0.001f ? afterArmor / rawDamage : 1.0f;
            if (armorRatio < 0.01f) armorRatio = 0.01f;
            rawDamage = postDamage / armorRatio;
        }

        // 计算护甲之后的其他减伤系数（附魔保护、抗性提升等）
        float postArmorOfRaw = CombatRules.getDamageAfterAbsorb(rawDamage, armor, toughness);
        float otherFactor = postArmorOfRaw > 0.001f ? postDamage / postArmorOfRaw : 1.0f;

        // 对原始伤害应用守护减伤
        if (rawDamage > threshold) {
            float excess = rawDamage - threshold;
            float guardedRaw = (float) (threshold + excess * (1.0-Config.resistdamage));
            // 重新应用护甲减伤
            float newAfterArmor = CombatRules.getDamageAfterAbsorb(guardedRaw, armor, toughness);
            // 重新应用其他减伤
            float newDamage = newAfterArmor * otherFactor;
            event.setAmount(Math.max(0.0f, newDamage));
        }
    }

    // 高速反射：免疫摔落伤害
    public static void onRapidReactionFall(LivingFallEvent event) {
        if (event.getEntity().hasEffect(ModEffects.RAPID_REACTION_BUFF.get())) {
            event.setDistance(0);
            event.setDamageMultiplier(0);
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
            WORK_QUEUE.forEach(work -> {
                work.setValue(work.getValue() - 1);
                if (work.getValue() == 0)
                    actions.add(work);
            });
            actions.forEach(e -> e.getKey().run());
            WORK_QUEUE.removeAll(actions);
        }
    }
}
