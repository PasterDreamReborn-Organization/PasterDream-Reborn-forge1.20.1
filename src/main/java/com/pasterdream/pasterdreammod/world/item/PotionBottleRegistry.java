package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.MagicDamageHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.block.windknightaltar.WindKnightAltarProcedure;
import com.pasterdream.pasterdreammod.world.entity.RejuvenationBottleEntity;
import com.pasterdream.pasterdreammod.world.entity.ThrownPotionBottle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 药剂瓶物品注册 + 效果绑定。
 * 从主类抽出，与 {@link PotionBottleItem} 放在同一目录下。
 */
public class PotionBottleRegistry {

    // ===== 物品注册 =====

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PasterDreamMod.MOD_ID);

    public static final RegistryObject<Item> POTION_BOTTLE = ITEMS.register("potion_bottle",
            () -> new PotionBottleItem(""));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    // ===== 效果注册入口 =====

    /** 在 commonSetup 中调用，绑定所有内置药剂瓶的砸碎效果 */
    public static void registerAllEffects() {
        registerLightningBottleEffect();
        registerHighlyToxicBottleEffect();
        registerRejuvenationBottleEffect();
        registerBerserkBottleEffect();
        registerFrozenBottleEffect();
        registerInfernoBottleEffect();
    }

    /** 注册发射器行为 */
    public static void registerDispenserBehavior() {
        net.minecraft.world.level.block.DispenserBlock.registerBehavior(
                POTION_BOTTLE.get(),
                new net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior() {
                    @Override
                    protected net.minecraft.world.entity.projectile.Projectile getProjectile(
                            Level level, net.minecraft.core.Position pos, ItemStack stack) {
                        ThrownPotionBottle bottle = new ThrownPotionBottle(level, pos.x(), pos.y(), pos.z());
                        bottle.setItem(stack.copy());
                        return bottle;
                    }
                });
    }

    // ===== 闪电药剂瓶 =====

    private static void registerLightningBottleEffect() {
        PotionBottleItem.registerEffect(PotionBottleItem.TYPE_LIGHTNING,
                (stack, level, thrower, hitPos) -> {
                    if (!(level instanceof ServerLevel serverLevel)) return;

                    // t=2: 释放乌云 + 充能音效
                    PasterDreamMod.queueServerWork(2, () -> {
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
                        PasterDreamMod.queueServerWork(sparkTicks[i], () -> serverLevel.sendParticles(
                                ParticleTypes.ELECTRIC_SPARK,
                                hitPos.x, hitPos.y, hitPos.z, count, 2.0, 0.5, 2.0, 0.0));
                    }

                    // t=55, 65, 75, 85: 4道随机落雷
                    for (int delay : new int[]{55, 65, 75, 85}) {
                        PasterDreamMod.queueServerWork(delay, () -> spawnLightningBolt(serverLevel, hitPos));
                    }
                    // t=86: 检测附近祭坛并召唤
                    PasterDreamMod.queueServerWork(86, () -> WindKnightAltarProcedure.trySummon(serverLevel, hitPos));
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
        PasterDreamMod.queueServerWork(offset + TOXIC_DAMAGE_TICKS[0], () -> {
            sl.sendParticles(ModParticleTypes.POISON_GAS_PARTICLE.get(),
                    pos.x, pos.y + 1, pos.z, 64, 2.0, 1.0, 2.0, 0.01);
            applyToxicDebuff(sl, pos, thrower);
        });
        // 后续伤害波：32粒子 + debuff
        for (int i = 1; i < TOXIC_DAMAGE_TICKS.length; i++) {
            int dt = TOXIC_DAMAGE_TICKS[i];
            PasterDreamMod.queueServerWork(offset + dt, () -> {
                sl.sendParticles(ModParticleTypes.POISON_GAS_PARTICLE.get(),
                        pos.x, pos.y + 1, pos.z, 32, 2.0, 1.0, 2.0, 0.01);
                applyToxicDebuff(sl, pos, thrower);
            });
        }
        // 纯粒子爆发：32粒子
        for (int pt : TOXIC_PARTICLE_TICKS) {
            PasterDreamMod.queueServerWork(offset + pt, () -> sl.sendParticles(
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
                    PasterDreamMod.queueServerWork(2, () -> sl.playSound(null, hitPos.x, hitPos.y, hitPos.z,
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
                            new AABB(
                                    hitPos.x - 8, hitPos.y - 8, hitPos.z - 8,
                                    hitPos.x + 8, hitPos.y + 8, hitPos.z + 8),
                            e -> true)
                            .forEach(e -> e.addEffect(
                                    new MobEffectInstance(
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
                        PasterDreamMod.queueServerWork(t, () -> {
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

    // ===== 狱火药剂瓶 =====

    private static void registerInfernoBottleEffect() {
        PotionBottleItem.registerEffect(PotionBottleItem.TYPE_INFERNO,
                (stack, level, thrower, hitPos) -> {
                    if (!(level instanceof ServerLevel sl)) return;

                    // t=0: 狱火爆发音效 + 粒子
                    sl.playSound(null, hitPos.x, hitPos.y, hitPos.z,
                            ModSounds.INFERNO_IMPACT.get(), SoundSource.NEUTRAL,
                            1.0F, 1.0F);
                    // 下层火焰粒子
                    sl.sendParticles(ParticleTypes.FLAME,
                            hitPos.x, hitPos.y + 0.5, hitPos.z, 80, 3.0, 0.5, 3.0, 0.03);
                    // 上层狱火粒子
                    sl.sendParticles(ModParticleTypes.INFERNO_PARTICLE.get(),
                            hitPos.x, hitPos.y + 1.5, hitPos.z, 60, 3.0, 1.0, 3.0, 0.02);

                    // 5 轮伤害波
                    final LivingEntity t = thrower;
                    for (int dt : new int[]{10, 30, 50, 70, 90}) {
                        PasterDreamMod.queueServerWork(dt, () -> {
                            sl.sendParticles(ModParticleTypes.INFERNO_PARTICLE.get(),
                                    hitPos.x, hitPos.y + 1.5, hitPos.z, 32, 3.0, 1.0, 3.0, 0.02);
                            sl.sendParticles(ParticleTypes.FLAME,
                                    hitPos.x, hitPos.y + 0.5, hitPos.z, 24, 3.0, 0.5, 3.0, 0.03);
                            sl.sendParticles(ParticleTypes.LAVA,
                                    hitPos.x, hitPos.y + 1.0, hitPos.z, 16, 2.0, 0.5, 2.0, 0.02);
                            applyInfernoPulse(sl, hitPos, t);
                        });
                    }
                });
    }

    // ===== 辅助方法 =====

    /** 脉冲：对 6x6 范围敌人（投掷者除外）造成魔法伤害+点燃+易伤叠加（最高3级） */
    private static void applyInfernoPulse(ServerLevel sl, Vec3 pos, LivingEntity thrower) {
        double r = 3.0;
        sl.getEntitiesOfClass(LivingEntity.class,
                new AABB(
                        pos.x - r, pos.y - r, pos.z - r,
                        pos.x + r, pos.y + r, pos.z + r),
                e -> e != thrower)
                .forEach(e -> {
                    float magic = 3.0f;
                    if (thrower instanceof Player pl) {
                        magic *= MagicDamageHelper.getMagicDamageMultiplier(pl);
                    }
                    e.hurt(e.damageSources().magic(), magic);
                    e.setSecondsOnFire(4);
                    // 易伤叠加，每波+1级，最高3级
                    var vuln = ModEffects.VULNERABILITY_BUFF.get();
                    int currentLv = e.hasEffect(vuln)
                            ? e.getEffect(vuln).getAmplifier() + 1 : 0;
                    int newLv = Math.min(currentLv, 2); // amplifier 2 = 等级3
                    e.addEffect(new MobEffectInstance(
                            vuln, 200, newLv, false, true));
                });
    }

    /** 5 格半径内所有实体（投掷者除外）施加冰冻 */
    private static void applyFrozenDebuff(ServerLevel sl, Vec3 pos, LivingEntity thrower) {
        double r = 5.0;
        sl.getEntitiesOfClass(LivingEntity.class,
                new AABB(
                        pos.x - r, pos.y - r, pos.z - r,
                        pos.x + r, pos.y + r, pos.z + r),
                e -> e != thrower)
                .forEach(e -> e.addEffect(
                        new MobEffectInstance(
                                ModEffects.FROZEN_BUFF.get(), 200, 0)));
    }

    /** 7 格半径内所有实体（投掷者除外）施加 剧毒IV + 虚弱 + 减速（10秒） */
    private static void applyToxicDebuff(ServerLevel sl, Vec3 pos, LivingEntity thrower) {
        double r = 7.0;
        sl.getEntitiesOfClass(LivingEntity.class,
                new AABB(
                        pos.x - r, pos.y - r, pos.z - r,
                        pos.x + r, pos.y + r, pos.z + r),
                e -> e != thrower)
                .forEach(e -> {
                    e.addEffect(new MobEffectInstance(
                            ModEffects.HIGHLY_TOXIC_BUFF.get(), 200, 3)); // 剧毒 IV
                    e.addEffect(new MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 0));
                    e.addEffect(new MobEffectInstance(
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
}
