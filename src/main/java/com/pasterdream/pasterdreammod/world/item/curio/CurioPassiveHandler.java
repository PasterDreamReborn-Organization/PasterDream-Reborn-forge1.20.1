package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.network.curio.CurioActivationPacket;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import com.pasterdream.pasterdreammod.world.entity.ThrownPotionBottle;
import com.pasterdream.pasterdreammod.world.item.armoritem.qym.QymCatEarsItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class CurioPassiveHandler {


    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (event.getNewTarget() instanceof Player player
                && CuriosApi.getCuriosInventory(player)
                    .map(h -> h.findFirstCurio(ModItems.QYM_BUTTERFLY_STAR_HAIRPIN.get()).isPresent())
                    .orElse(false)) {
            // 允许反击：如果玩家刚刚攻击过该生物则不取消
            if (event.getEntity().getLastHurtByMob() == player) return;
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        // 塞西莉娅的加护：攻击无法命中
        if (event.getEntity().hasEffect(ModEffects.CECILIA_BLESSING_BUFF.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingKnockBack(LivingKnockBackEvent event) {
        // 塞西莉娅的加护：免疫击退
        if (event.getEntity().hasEffect(ModEffects.CECILIA_BLESSING_BUFF.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onMobEffectRemove(MobEffectEvent.Remove event) {
        // 佩戴塞西莉娅的加护 / 失色的塞西莉娅的加护时，效果不可被移除
        var instance = event.getEffectInstance();
        if (instance == null || instance.getEffect() != ModEffects.CECILIA_BLESSING_BUFF.get()) return;
        if (event.getEntity() instanceof Player player
                && (CuriosApi.getCuriosInventory(player).map(h ->
                    h.findFirstCurio(ModItems.BLESSING_OF_CECILIA.get()).isPresent()
                    || h.findFirstCurio(ModItems.FADED_BLESSING_OF_CECILIA.get()).isPresent()
                ).orElse(false)
                || QymCatEarsItem.hasFullSet(player))) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // 塞西莉娅的加护生效中：取消所有伤害
        if (event.getEntity().hasEffect(ModEffects.CECILIA_BLESSING_BUFF.get())) {
            event.setCanceled(true);
            return;
        }

        // 塞西莉娅的加护：常驻强制20%减伤
        if (event.getEntity() instanceof Player player
                && CuriosApi.getCuriosInventory(player)
                    .map(h -> h.findFirstCurio(ModItems.BLESSING_OF_CECILIA.get()).isPresent())
                    .orElse(false)) {
            event.setAmount(event.getAmount() * 0.8F);
        }

        // QYM套装：常驻强制80%减伤
        if (event.getEntity() instanceof Player player
                && QymCatEarsItem.hasFullSet(player)) {
            event.setAmount(event.getAmount() * 0.2F);
        }

        // QYM套装：攻击附带目标当前生命值5%的魔法伤害（20tick冷却）
        if (event.getSource().getEntity() instanceof Player player
                && event.getSource().getEntity() != event.getEntity()
                && QymCatEarsItem.hasFullSet(player)) {
            long gameTime = player.level().getGameTime();
            long lastProc = player.getPersistentData().getLong("pasterdream.qym_magic_last");
            if (gameTime - lastProc >= 20) {
                player.getPersistentData().putLong("pasterdream.qym_magic_last", gameTime);
                LivingEntity target = event.getEntity();
                float magicDamage = target.getHealth() * 0.05F;
                target.invulnerableTime = 0;
                target.hurt(target.level().damageSources().magic(), magicDamage);
            }
        }

        // 塞西莉娅的加护：拦截致命伤害
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.getHealth() + player.getAbsorptionAmount() - event.getAmount() > 0.0F) return; // 非致命伤害

        boolean hasCharm = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.BLESSING_OF_CECILIA.get()).isPresent())
                .orElse(false);
        if (!hasCharm) return;

        // 取消致命伤害
        event.setCanceled(true);
        player.setHealth(1.0F);

        // 在原槽位替换为失色版本
        CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                handler.findFirstCurio(ModItems.BLESSING_OF_CECILIA.get()).ifPresent(slotResult ->
                        handler.setEquippedCurio(slotResult.slotContext().identifier(),
                                slotResult.slotContext().index(),
                                new ItemStack(ModItems.FADED_BLESSING_OF_CECILIA.get()))));

        // 饰品激活动画（发包至客户端） + 音效
        ModNetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                new CurioActivationPacket());
        player.level().playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE,
                SoundSource.NEUTRAL, 1.0F, 1.0F);

        // 粒子
        if (player.level() instanceof ServerLevel sl) {
            sl.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(),
                    player.getX(), player.getY(), player.getZ(), 64, 1, 1, 1, 0.2);
            sl.sendParticles(ModParticleTypes.SPORE_PARTICLE.get(),
                    player.getX(), player.getY(), player.getZ(), 64, 1, 1, 1, 0.2);
            sl.sendParticles(ModParticleTypes.BUFF_0_PARTICLE.get(),
                    player.getX(), player.getY(), player.getZ(), 32, 1, 1, 1, 0.2);
        }

        // 施加效果：无敌（5秒），抗性V、回复X、吸收V、速度II（10秒）
        player.addEffect(new MobEffectInstance(ModEffects.CECILIA_BLESSING_BUFF.get(), 100, 0, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 4, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 9, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 4, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1, false, false));

    }

    /**
     * 战旗饰品：杀敌后给予战旗 buff（攻击力+回血），持续时间和等级可叠加
     */
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide) return;

        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof Player player)) return;

        // 检测玩家是否装备了战旗饰品
        boolean hasWarFlag = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.WAR_FLAG.get()).isPresent())
                .orElse(false);
        if (!hasWarFlag) return;

        // 获取当前已有的战旗 buff 等级，叠加 1 级
        MobEffectInstance existing = player.getEffect(ModEffects.WAR_FLAG_BUFF.get());
        int newAmplifier = (existing != null) ? existing.getAmplifier() + 1 : 0;

        // 上限 Ⅲ 级（amplifier=2）
        if (newAmplifier > 2) newAmplifier = 2;

        // 等级越高持续时间越短：Ⅰ=60秒, Ⅱ=30秒, Ⅲ=15秒
        int duration = switch (newAmplifier) {
            case 0 -> 1200; // 60秒
            case 1 -> 600;  // 30秒
            case 2 -> 300;  // 15秒
            default -> 1200;
        };
        player.addEffect(new MobEffectInstance(ModEffects.WAR_FLAG_BUFF.get(), duration, newAmplifier,
                false, false, true));
    }

    /**
     * 卡莱调料瓶：佩戴时食用速度提升 40%（对所有食物有效）
     * 每 3 tick 中额外减少 2 tick，等效 1.667x 速度
     */
    @SubscribeEvent
    public static void onItemUseTick(LivingEntityUseItemEvent.Tick event) {
        if (!(event.getEntity() instanceof Player player)) return;
        // 只对食物加速
        if (!event.getItem().isEdible()) return;
        // 检查是否佩戴了卡莱调料瓶
        boolean hasBottle = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.CALAIS_SPICE_BOTTLE.get()).isPresent())
                .orElse(false);
        if (!hasBottle) return;

        // 每 3 tick 中 2 tick 额外减少 1 点 duration，即 5/3 = 1.667x 速度 ≈ -40% 时间
        long gameTime = player.level().getGameTime();
        if (gameTime % 3 != 0) {
            event.setDuration(event.getDuration() - 1);
        }
    }

    /**
     * 卡莱调料瓶：攻击命中敌人时消耗 1 级增益
     */
    @SubscribeEvent
    public static void onCalaisSpiceAttack(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        if (event.getSource().getEntity() == event.getEntity()) return; // 跳过自伤

        MobEffectInstance buff = player.getEffect(ModEffects.CALAIS_SPICE_BOTTLE_BUFF.get());
        if (buff == null) return;

        int level = buff.getAmplifier() + 1; // 1-10 级
        player.removeEffect(ModEffects.CALAIS_SPICE_BOTTLE_BUFF.get());
        if (level > 1) {
            // 降级
            player.addEffect(new MobEffectInstance(ModEffects.CALAIS_SPICE_BOTTLE_BUFF.get(),
                    -1, level - 2, false, false, true));
        } else {
            // Ⅰ 级被消耗 → 枯竭，必须通过进食才能恢复
            player.getPersistentData().putBoolean("pasterdream.calais_depleted", true);
        }

        // === 层数消耗后随机触发一种效果（仅服务端，权重见 Config） ===
        if (!player.level().isClientSide()) {
            // 加权随机选择效果
            List<? extends Double> weights = Config.calaisSpiceBottleWeights;
            double totalWeight = 0;
            for (double w : weights) totalWeight += w;
            if (totalWeight <= 0) return;

            double r = player.getRandom().nextDouble() * totalWeight;
            double cumulative = 0;
            int roll = -1;
            for (int i = 0; i < weights.size(); i++) {
                cumulative += weights.get(i);
                if (r < cumulative) {
                    roll = i;
                    break;
                }
            }
            if (roll < 0) roll = weights.size() - 1; // 浮点精度兜底

            switch (roll) {
                case 0 -> {
                    // 随机增益（15 秒，可叠加，上限 Ⅲ 级，效果池见 Config）
                    List<MobEffect> buffPool = Config.getCalaisSpiceBottleBuffs();
                    if (buffPool.isEmpty()) return;
                    MobEffect picked = buffPool.get(player.getRandom().nextInt(buffPool.size()));
                    MobEffectInstance existingBuff = player.getEffect(picked);
                    int newAmp = existingBuff != null ? Math.min(existingBuff.getAmplifier() + 1, 2) : 0;
                    player.addEffect(new MobEffectInstance(picked, 300, newAmp, false, true, true));
                }
                case 1 -> {
                    if (player instanceof ServerPlayer sp) {
                        double s = Config.calaisSpiceBottleSanMin + sp.getRandom().nextDouble()
                                * (Config.calaisSpiceBottleSanMax - Config.calaisSpiceBottleSanMin);
                        SanHelper.addPlayerSanAndSync(sp, s);
                    }
                }
                case 2 -> {
                    float amount = (float)(Config.calaisSpiceBottleHealMin + player.getRandom().nextDouble()
                            * (Config.calaisSpiceBottleHealMax - Config.calaisSpiceBottleHealMin));
                    player.heal(amount);
                }
                case 3 -> {
                    // 随机负面效果（效果池见 Config）
                    List<MobEffect> debuffPool = Config.getCalaisSpiceBottleDebuffs();
                    if (debuffPool.isEmpty()) return;
                    MobEffect picked = debuffPool.get(player.getRandom().nextInt(debuffPool.size()));
                    LivingEntity target = event.getEntity();
                    target.addEffect(new MobEffectInstance(picked,
                            Config.calaisSpiceBottleDebuffDuration,
                            Config.calaisSpiceBottleDebuffAmplifier,
                            false, true, true));
                }
                case 4 -> {
                    player.level().playSound(null, player.blockPosition(), ModSounds.EVASION.get(),
                            SoundSource.PLAYERS, 1.0F, 1.0F);
                    player.addEffect(new MobEffectInstance(ModEffects.EVASION_BUFF.get(),
                            Config.calaisSpiceBottleEvasionDuration, 0,
                            false, false, false));
                }
                case 5 -> {
                    player.level().playSound(null, player.blockPosition(), ModSounds.DOLL.get(),
                            SoundSource.PLAYERS, 1.0F, 1.0F);
                    player.displayClientMessage(Component.literal("?"), true);
                }
            }
        }
    }

    /**
     * 卡莱调料瓶：进食后根据恢复的饥饿值叠加增益层数（每 3 饥饿度 +1 级，上限 Ⅹ）
     */
    @SubscribeEvent
    public static void onCalaisSpiceEat(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!event.getItem().isEdible()) return;

        boolean hasBottle = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.CALAIS_SPICE_BOTTLE.get()).isPresent())
                .orElse(false);
        if (!hasBottle) return;

        var foodProps = event.getItem().getFoodProperties(player);
        if (foodProps == null) return;
        int nutrition = foodProps.getNutrition();
        if (nutrition < 3) return; // 不足 3 饥饿度不叠层

        int levelsGained = nutrition / 3;
        MobEffectInstance existing = player.getEffect(ModEffects.CALAIS_SPICE_BOTTLE_BUFF.get());
        int currentLevel = existing != null ? existing.getAmplifier() + 1 : 0;
        int newLevel = Math.min(currentLevel + levelsGained, 10);

        if (existing != null) {
            player.removeEffect(ModEffects.CALAIS_SPICE_BOTTLE_BUFF.get());
        }
        player.addEffect(new MobEffectInstance(ModEffects.CALAIS_SPICE_BOTTLE_BUFF.get(),
                -1, newLevel - 1, false, false, true));
        // 进食后清除枯竭标记，使 buff 可以正常恢复
        player.getPersistentData().remove("pasterdream.calais_depleted");
    }

    /**
     * 鬼魂之面：投射物无视无敌帧（对所有佩戴鬼魂之面玩家发射的投射物生效，含其它模组枪械等）
     */
    @SubscribeEvent
    public static void onGhostFaceProjectileAttack(LivingAttackEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof Projectile projectile)) return;
        if (!(projectile.getOwner() instanceof Player player)) return;
        boolean hasGhostFace = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.GHOST_FACE.get()).isPresent())
                .orElse(false);
        if (!hasGhostFace) return;
        event.getEntity().invulnerableTime = 0;
    }

    /**
     * 鬼魂之面：侦测任意投射物生成，对其它模组（枪械等）的弹射物也生效
     * 弓/弩由 ArrowLooseEvent 单独处理以获得精确附魔数据
     */
    @SubscribeEvent
    public static void onProjectileJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof Projectile projectile)) return;
        if (!(projectile.getOwner() instanceof Player player)) return;
        // 防止递归：跳过鬼魂之面自身生成的箭矢
        if (projectile.getPersistentData().getBoolean("pasterdream_ghost_face_spawned")) return;

        boolean hasGhostFace = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.GHOST_FACE.get()).isPresent())
                .orElse(false);
        if (!hasGhostFace) return;

        // 硬编码黑名单：末影珍珠不应被复制
        if (projectile instanceof net.minecraft.world.entity.projectile.ThrownEnderpearl) return;
        if (projectile instanceof net.minecraft.world.entity.projectile.ThrownPotion) return;
        // 配置文件黑名单
        if (Config.isGhostFaceProjectileBlacklisted(projectile.getType())) return;

        CompoundTag pd = player.getPersistentData();
        // 如果 ArrowLooseEvent 已设置数据（弓/弩），用实际箭矢类型覆盖默认的 Arrow 类型
        if (pd.contains("pasterdream_ghost_face_delay")) {
            if (!(projectile instanceof ThrownTrident)) {
                pd.putInt("pasterdream_ghost_face_type", 2);
                pd.putString("pasterdream_ghost_face_clone",
                        BuiltInRegistries.ENTITY_TYPE.getKey(projectile.getType()).toString());
                if (projectile instanceof AbstractArrow arrowProj) {
                    pd.putDouble("pasterdream_ghost_face_damage", arrowProj.getBaseDamage());
                }
                if (projectile instanceof ThrownPotionBottle potionBottle) {
                    pd.putString("pasterdream_ghost_face_potion_type",
                            PotionBottleItem.getPotionType(potionBottle.getItem()));
                }
            }
            return;
        }

        // 非弓/弩投射物：读取当前速度与伤害
        Vec3 motion = projectile.getDeltaMovement();
        float velocity = (float) motion.length();
        if (velocity < 0.1F) return;

        int extra = 1 + (player.getRandom().nextFloat() < 0.2F ? 1 : 0);

        // 读取原始投射物伤害（AbstractArrow 系）
        double baseDamage = 2.0;
        if (projectile instanceof AbstractArrow arrowProj) {
            baseDamage = arrowProj.getBaseDamage();
        }

        // 记录投射物类型：1=三叉戟(特殊构造), 2=克隆原始类型
        int projectileType;
        String cloneTypeId = "";
        if (projectile instanceof ThrownTrident) {
            projectileType = 1;
        } else {
            projectileType = 2;
            cloneTypeId = BuiltInRegistries.ENTITY_TYPE.getKey(projectile.getType()).toString();
        }

        pd.putInt("pasterdream_ghost_face_delay", 1);
        pd.putInt("pasterdream_ghost_face_extra", extra);
        pd.putFloat("pasterdream_ghost_face_velocity", velocity);
        pd.putDouble("pasterdream_ghost_face_damage", baseDamage);
        pd.putInt("pasterdream_ghost_face_punch", 0);
        pd.putBoolean("pasterdream_ghost_face_flame", false);
        pd.putBoolean("pasterdream_ghost_face_crit", false);
        pd.putInt("pasterdream_ghost_face_type", projectileType);
        if (projectileType == 2) {
            pd.putString("pasterdream_ghost_face_clone", cloneTypeId);
        }
        if (projectile instanceof ThrownPotionBottle potionBottle) {
            pd.putString("pasterdream_ghost_face_potion_type",
                    PotionBottleItem.getPotionType(potionBottle.getItem()));
        }
    }

    /**
     * 鬼魂之面：弓/弩 — 投射物 +1，20% 再 +1
     * 保持 ArrowLooseEvent 以获得精确的蓄力速度和附魔数据
     */
    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        boolean hasGhostFace = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.GHOST_FACE.get()).isPresent())
                .orElse(false);
        if (!hasGhostFace) return;

        ItemStack bow = event.getBow();

        // 计算速度：弩固定全速，弓按蓄力比例（原版公式）
        float f;
        if (bow.getItem() instanceof CrossbowItem) {
            f = 1.0F;
        } else {
            f = event.getCharge() / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;
            if (f > 1.0F) f = 1.0F;
        }
        float velocity = f * 3.0F;

        // 力量附魔加成（原版公式）
        double damage = 2.0;
        int powerLevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.POWER_ARROWS, bow);
        if (powerLevel > 0) {
            damage += powerLevel * 0.5 + 0.5;
        }

        // 冲击附魔
        int punch = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PUNCH_ARROWS, bow);
        // 火矢附魔
        boolean flame = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FLAMING_ARROWS, bow) > 0;
        // 是否为满蓄力暴击
        boolean crit = f >= 1.0F;

        // 额外投射物数量
        int extra = 1 + (player.getRandom().nextFloat() < 0.2F ? 1 : 0);

        CompoundTag pd = player.getPersistentData();
        pd.putInt("pasterdream_ghost_face_delay", 1);
        pd.putInt("pasterdream_ghost_face_extra", extra);
        pd.putFloat("pasterdream_ghost_face_velocity", velocity);
        pd.putDouble("pasterdream_ghost_face_damage", damage);
        pd.putInt("pasterdream_ghost_face_punch", punch);
        pd.putBoolean("pasterdream_ghost_face_flame", flame);
        pd.putBoolean("pasterdream_ghost_face_crit", crit);
        pd.putInt("pasterdream_ghost_face_type", 0); // 箭矢类型
    }

    /**
     * 鬼魂之面：延迟生成额外投射物（tick 处理）
     * 根据原始投射物类型生成对应实体：箭矢 / 三叉戟
     */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if (player.level().isClientSide) return;

        CompoundTag pd = player.getPersistentData();
        int delay = pd.getInt("pasterdream_ghost_face_delay");
        if (delay <= 0) return;

        delay--;
        if (delay > 0) {
            pd.putInt("pasterdream_ghost_face_delay", delay);
            return;
        }

        // 再次确认玩家仍佩戴鬼魂之面
        boolean hasGhostFace = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.GHOST_FACE.get()).isPresent())
                .orElse(false);
        if (!hasGhostFace) {
            clearGhostFaceData(pd);
            return;
        }

        int extra = pd.getInt("pasterdream_ghost_face_extra");
        float velocity = pd.getFloat("pasterdream_ghost_face_velocity");
        double damage = pd.contains("pasterdream_ghost_face_damage") ? pd.getDouble("pasterdream_ghost_face_damage") : 2.0;
        int punch = pd.getInt("pasterdream_ghost_face_punch");
        boolean flame = pd.getBoolean("pasterdream_ghost_face_flame");
        boolean crit = pd.getBoolean("pasterdream_ghost_face_crit");
        int projectileType = pd.getInt("pasterdream_ghost_face_type"); // 0=箭矢, 1=三叉戟, 2=克隆

        for (int i = 0; i < extra; i++) {
            switch (projectileType) {
                case 1 -> {
                    // 额外三叉戟（特殊构造）
                    ThrownTrident trident = new ThrownTrident(player.level(), player, new ItemStack(Items.TRIDENT));
                    trident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, velocity, 1.0F);
                    trident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    trident.getPersistentData().putBoolean("pasterdream_ghost_face_spawned", true);
                    player.level().addFreshEntity(trident);
                }
                case 2 -> {
                    // 克隆原始投射物类型（特殊箭/其它模组弹射物）
                    spawnClonedProjectile(player, pd, velocity, damage, punch, flame, crit && i == 0);
                }
                default -> {
                    // 额外箭矢（弓/弩，附魔加成）
                    Arrow arrow = new Arrow(player.level(), player);
                    arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, velocity, 1.0F);
                    arrow.setBaseDamage(damage);
                    arrow.setCritArrow(crit && i == 0);
                    if (punch > 0) arrow.setKnockback(punch);
                    if (flame) arrow.setSecondsOnFire(100);
                    arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    arrow.getPersistentData().putBoolean("pasterdream_ghost_face_spawned", true);
                    player.level().addFreshEntity(arrow);
                }
            }
        }

        clearGhostFaceData(pd);
    }

    /**
     * 通过 EntityType.create() 克隆原始投射物（用于其它模组弹射物）
     */
    private static void spawnClonedProjectile(Player player, CompoundTag pd, float velocity,
                                               double damage, int punch, boolean flame, boolean crit) {
        String typeIdStr = pd.getString("pasterdream_ghost_face_clone");
        if (typeIdStr.isEmpty()) return;
        ResourceLocation typeId = ResourceLocation.tryParse(typeIdStr);
        if (typeId == null) return;
        var entityType = BuiltInRegistries.ENTITY_TYPE.get(typeId);
        if (entityType == null) return;

        Entity copy = entityType.create(player.level());
        if (!(copy instanceof Projectile copyProj)) return;

        copyProj.setOwner(player);
        copy.moveTo(player.getX(), player.getEyeY() - 0.1, player.getZ(), player.getYRot(), player.getXRot());
        copyProj.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, velocity, 1.0F);
        if (copy instanceof AbstractArrow copyArrow) {
            copyArrow.setBaseDamage(damage);
            copyArrow.setCritArrow(crit);
            if (punch > 0) copyArrow.setKnockback(punch);
            if (flame) copyArrow.setSecondsOnFire(100);
            copyArrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }
        if (copy instanceof ThrownPotionBottle potionClone) {
            String potionType = pd.getString("pasterdream_ghost_face_potion_type");
            if (!potionType.isEmpty()) {
                ItemStack stack = potionClone.getItem().copy();
                PotionBottleItem.setPotionType(stack, potionType);
                potionClone.setItem(stack);
            }
        }
        copy.getPersistentData().putBoolean("pasterdream_ghost_face_spawned", true);
        player.level().addFreshEntity(copy);
    }

    private static void clearGhostFaceData(CompoundTag pd) {
        pd.remove("pasterdream_ghost_face_delay");
        pd.remove("pasterdream_ghost_face_extra");
        pd.remove("pasterdream_ghost_face_velocity");
        pd.remove("pasterdream_ghost_face_damage");
        pd.remove("pasterdream_ghost_face_punch");
        pd.remove("pasterdream_ghost_face_flame");
        pd.remove("pasterdream_ghost_face_crit");
        pd.remove("pasterdream_ghost_face_type");
        pd.remove("pasterdream_ghost_face_clone");
        pd.remove("pasterdream_ghost_face_potion_type");
    }
}
