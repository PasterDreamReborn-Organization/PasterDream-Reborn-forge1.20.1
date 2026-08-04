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
import com.pasterdream.pasterdreammod.world.entity.ghost.SquealWaveProjectileEntity;
import com.pasterdream.pasterdreammod.world.item.armoritem.qym.QymCatEarsItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.CrossbowItem;
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
     * 鬼魂之面：幽魂射弹无视无敌帧（仅对佩戴鬼魂之面的玩家发射的怨魂音波生效）
     */
    @SubscribeEvent
    public static void onGhostFaceProjectileAttack(LivingAttackEvent event) {
        // 只处理怨魂音波射弹
        if (!(event.getSource().getDirectEntity() instanceof SquealWaveProjectileEntity projectile)) return;
        // 检查射弹主人是否为佩戴鬼魂之面的玩家
        if (!(projectile.getOwner() instanceof Player player)) return;
        boolean hasGhostFace = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.GHOST_FACE.get()).isPresent())
                .orElse(false);
        if (!hasGhostFace) return;
        // 清除无敌帧，使幽魂射弹伤害无视无敌
        event.getEntity().invulnerableTime = 0;
    }

    /**
     * 鬼魂之面：使用远程武器时，额外发射幽魂射弹（怨魂同款），20% 再发射一发
     * 延迟 2 tick 发射，避免幽魂射弹先于箭矢命中导致箭矢被无敌帧弹开
     */
    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        boolean hasGhostFace = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.GHOST_FACE.get()).isPresent())
                .orElse(false);
        if (!hasGhostFace) return;

        // 弩的 charge 固定为 1，需特殊处理；弓按蓄力比例计算
        float velocity;
        if (event.getBow().getItem() instanceof CrossbowItem) {
            velocity = 3.0F; // 弩始终全速发射
        } else {
            velocity = (event.getCharge() / 20.0F) * 3.0F;
        }
        float power = velocity / 2.0F;
        int shots = 1 + (player.getRandom().nextFloat() < 0.2F ? 1 : 0);

        // 力量附魔加成：沿用原版公式 level * 0.5 + 0.5
        double damage = 3.0;
        int powerLevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.POWER_ARROWS, event.getBow());
        if (powerLevel > 0) {
            damage += powerLevel * 0.5 + 0.5;
        }

        // 延迟发射，让箭矢先飞出去
        CompoundTag pd = player.getPersistentData();
        pd.putInt("pasterdream_ghost_face_delay", 2);
        pd.putInt("pasterdream_ghost_face_shots", shots);
        pd.putFloat("pasterdream_ghost_face_power", power);
        pd.putDouble("pasterdream_ghost_face_damage", damage);
    }

    /**
     * 鬼魂之面：延迟发射幽魂射弹的 tick 处理
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
            pd.remove("pasterdream_ghost_face_delay");
            pd.remove("pasterdream_ghost_face_shots");
            pd.remove("pasterdream_ghost_face_power");
            pd.remove("pasterdream_ghost_face_damage");
            return;
        }

        int shots = pd.getInt("pasterdream_ghost_face_shots");
        float power = pd.getFloat("pasterdream_ghost_face_power");
        double damage = pd.contains("pasterdream_ghost_face_damage") ? pd.getDouble("pasterdream_ghost_face_damage") : 3.0;
        for (int i = 0; i < shots; i++) {
            SquealWaveProjectileEntity.shoot(player.level(), player, player.getRandom(), power, damage, 0);
        }

        // 清理
        pd.remove("pasterdream_ghost_face_delay");
        pd.remove("pasterdream_ghost_face_shots");
        pd.remove("pasterdream_ghost_face_power");
        pd.remove("pasterdream_ghost_face_damage");
    }
}
