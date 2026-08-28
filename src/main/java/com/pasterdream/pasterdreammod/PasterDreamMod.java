package com.pasterdream.pasterdreammod;

import com.pasterdream.pasterdreammod.client.*;
import com.pasterdream.pasterdreammod.client.renderer.AngelWingRenderer;
import com.pasterdream.pasterdreammod.client.renderer.ForsakensWingRenderer;
import com.pasterdream.pasterdreammod.config.PasterDreamClientConfig;
import com.pasterdream.pasterdreammod.helper.fluidhandler.FluidHandlerResolvers;
import com.pasterdream.pasterdreammod.helper.sanbiomeratemanager.SanBiomeRateManager;
import com.pasterdream.pasterdreammod.helper.tooltipadder.AddToolTip;
import com.pasterdream.pasterdreammod.event.ModMobDrops;
import com.pasterdream.pasterdreammod.event.ModMobSpawnEvents;
import com.pasterdream.pasterdreammod.event.ModVillagerTrades;
import com.pasterdream.pasterdreammod.datagen.common.ModRaidRewardsProvider;
import com.pasterdream.pasterdreammod.event.ModWorldGenEvents;
import com.pasterdream.pasterdreammod.event.PlayerEvents;
import com.pasterdream.pasterdreammod.event.RecipeUnlockHandler;
import com.pasterdream.pasterdreammod.init.*;
import com.pasterdream.pasterdreammod.world.item.prophecycard.ProphecyCardItem;
import com.pasterdream.pasterdreammod.world.item.PotionBottleRegistry;
import com.pasterdream.pasterdreammod.world.item.armoritem.MachineLightWingItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.qym.QymArmorEvents;
import com.pasterdream.pasterdreammod.world.dimension.AaroncosArenaTeleporter;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ForgeMod;
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
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.UUID;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib.GeckoLib;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod(PasterDreamMod.MOD_ID)
public class PasterDreamMod
{
    public static final String MOD_ID = "pasterdream";

    private static final UUID SWIFT_STRIKE_ATTACK_SPEED_UUID = UUID.fromString("bdf05f70-b53d-4828-8e37-9a502bde0ec1");
    private static final UUID CONGEAL_WIND_IRON_REACH_UUID = UUID.fromString("7c2a9f4e-5d81-4b3a-9e6c-1f8d0a2b4c6e");

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
        ModPaintings.register(modEventBus);         //注册画作
        ModEffects.register(modEventBus);           //注册药水效果
        ModPotions.register(modEventBus);           //注册药水类型
        ModAttributes.register(modEventBus);        //注册属性
        ModTreeDecoratorTypes.register(modEventBus); //注册树木装饰器类型
        ModFoliagePlacerTypes.register(modEventBus); //注册树叶放置器类型
        ModFeatures.register(modEventBus);          //注册自定义特征
        ModEntities.register(modEventBus);          //注册实体
        ModLootTables.register(modEventBus);        //注册自定义战利品函数类型
        ModNetwork.register();                      //注册网络包
        ModEnchantment.register(modEventBus);       //注册附魔
        ModCriteriaTriggers.init();                 //注册自定义进度触发器
        PotionBottleRegistry.register(modEventBus); //注册药剂瓶物品

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerItemModels);
        modEventBus.addListener(this::getItemModels);
        MinecraftForge.EVENT_BUS.addListener(this::AddItemTooltip);
        MinecraftForge.EVENT_BUS.addListener(this::AddCommand);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onHoeTill);
        MinecraftForge.EVENT_BUS.addListener(ModMobDrops::onLivingDrops);
        MinecraftForge.EVENT_BUS.addListener(ModRaidRewardsProvider::onLootTableLoad);
        MinecraftForge.EVENT_BUS.addListener(ModVillagerTrades::onVillagerTrades);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onLivingHurt);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onFoxFireVulnerableHurt);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onVulnerabilityHurt);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onLivingHurt);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerSleepInBed);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onAttackEntity);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerChangedDimension);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerLoggedIn);
        MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onAdvancementEarned);
        MinecraftForge.EVENT_BUS.addListener(QymArmorEvents::onEquipChange);
        MinecraftForge.EVENT_BUS.addListener(MachineLightWingItem::onEquipChange);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onItemAttributeModifier);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onShelterLivingHurt);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onGuardLivingHurt);
        MinecraftForge.EVENT_BUS.addListener(PasterDreamMod::onRapidReactionFall);
        MinecraftForge.EVENT_BUS.addListener(ModWorldGenEvents::onLevelLoad);
        MinecraftForge.EVENT_BUS.addListener(ModWorldGenEvents::onServerTick);
        MinecraftForge.EVENT_BUS.addListener(ModWorldGenEvents::onCheckSpawn);
        MinecraftForge.EVENT_BUS.addListener(RecipeUnlockHandler::onBlockBreak);
        MinecraftForge.EVENT_BUS.addListener(ModMobSpawnEvents::onEntityJoinLevel);
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
        ModFluidPropertiesRelation.register();
        ModDreamNotesContentRelation.registerDreamNotesContentRelation();
        ModDreamNotesBookContentRelation.registerDreamNotesBookContentRelation();
        ModBluePrintsContentRelation.registerBluePrintsContentRelation();
        ModCropRelation.registerCropRelation();
        ProphecyCardItem.registerAllCardEffects();

        // 药剂瓶：发射器行为 + 砸碎效果绑定
        PotionBottleRegistry.registerDispenserBehavior();
        PotionBottleRegistry.registerAllEffects();
    }

    //在这里输入客户端注册内容
    private void clientSetup(final FMLClientSetupEvent event)
    {
        ClientSetRenderLayer.register();
        ModScreens.register(event);
        ModBlockEntityRenderer.FMLClientSetupEventRegister(event);
        event.enqueueWork(ClientModEvents::registerItemProperties);
    }

    private void registerItemModels(ModelEvent.ModifyBakingResult event)
    {
        ModItemModels.register(event);
    }

    private void getItemModels(ModelEvent.BakingCompleted event)
    {
        ModItemModels.getBakedModel(event);
        CuriosRendererRegistry.register(ModItems.ANGEL_WING.get(), AngelWingRenderer::new);
        CuriosRendererRegistry.register(ModItems.FORSAKENS_WING.get(), ForsakensWingRenderer::new);
    }

    private void AddItemTooltip(ItemTooltipEvent event)
    {
        AddToolTip.addTooltip(event);
    }

    private void AddCommand(RegisterCommandsEvent event)
    {
        ModCommands.register(event.getDispatcher());
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
                && player.hasEffect(ModEffects.DYEDREAM_UP.get())) {
            ItemStack weapon = player.getMainHandItem();
            if (weapon.getItem() instanceof TieredItem tiered
                    && (tiered.getTier() == ModToolTiers.DYEDREAM
                            || tiered.getTier() == ModToolTiers.MELT_DREAM)) {
                event.setAmount(event.getAmount() * 1.5f);
            }
        }
    }

    // 易伤 debuff：每级 +10% 所受伤害
    public static void onVulnerabilityHurt(LivingHurtEvent event) {
        var effect = ModEffects.VULNERABILITY.get();
        if (event.getEntity().hasEffect(effect)) {
            int amplifier = event.getEntity().getEffect(effect).getAmplifier();
            event.setAmount(event.getAmount() * (1.0f + 0.1f * (amplifier + 1)));
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

        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        // 凝风铁工具：+1 触及距离
        if (item == ModItems.CONGEAL_WIND_IRON_SWORD.get()
                || item == ModItems.CONGEAL_WIND_IRON_PICKAXE.get()
                || item == ModItems.CONGEAL_WIND_IRON_AXE.get()
                || item == ModItems.CONGEAL_WIND_IRON_SHOVEL.get()
                || item == ModItems.CONGEAL_WIND_IRON_HOE.get()) {
            event.addModifier(
                    ForgeMod.ENTITY_REACH.get(),
                    new AttributeModifier(
                            CONGEAL_WIND_IRON_REACH_UUID,
                            "Congeal Wind Iron reach bonus",
                            1.0,
                            AttributeModifier.Operation.ADDITION
                    )
            );
        }

        var swiftStrike = ModEnchantment.SWIFT_STRIKE_ENCHANTMENT.get();

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
        if (!player.hasEffect(ModEffects.GUARD.get())) return;

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
        if (event.getEntity().hasEffect(ModEffects.RAPID_REACTION.get())) {
            event.setDistance(0);
            event.setDamageMultiplier(0);
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            AaroncosArenaTeleporter.tickEntryCooldowns();
        }
    }

    @SubscribeEvent
    public void onFrozenEffectExpired(MobEffectEvent.Expired event) {
        if (event.getEffectInstance() != null
                && event.getEffectInstance().getEffect() == ModEffects.FROZEN.get()
                && event.getEntity() instanceof Mob mob
                && !event.getEntity().level().isClientSide) {
            mob.setNoAi(false);
        }
    }
}
