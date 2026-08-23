package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.entity.PotionBottleEffectEntity;
import com.pasterdream.pasterdreammod.world.entity.RejuvenationBottleEntity;
import com.pasterdream.pasterdreammod.world.entity.ThrownPotionBottle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
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
 * 多阶段时序（闪电落雷/毒气波/冰冻脉冲/狱火波）由 {@link PotionBottleEffectEntity} 的 tick 状态机驱动。
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

                    // t=0: 释放乌云 + 充能音效
                    serverLevel.playSound(null, hitPos.x, hitPos.y, hitPos.z,
                            ModSounds.LIGHTNING_CHARGE.get(), SoundSource.NEUTRAL,
                            1.0F, 1.0F);
                    serverLevel.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                            hitPos.x, hitPos.y, hitPos.z, 64, 2.0, 0.5, 2.0, 0.0);
                    serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                            hitPos.x, hitPos.y, hitPos.z, 32, 2.0, 0.5, 2.0, 0.0);

                    // 电火花分时刷出 + 4 道落雷 + t=86 召唤祭坛：由效果实体驱动
                    serverLevel.addFreshEntity(new PotionBottleEffectEntity(serverLevel,
                            hitPos.x, hitPos.y, hitPos.z, PotionBottleEffectEntity.TYPE_LIGHTNING, thrower));
                });
    }

    // ===== 剧毒药剂瓶：多轮毒气波 =====

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

                    // 3 轮毒气波：由效果实体驱动
                    sl.addFreshEntity(new PotionBottleEffectEntity(sl,
                            hitPos.x, hitPos.y, hitPos.z, PotionBottleEffectEntity.TYPE_TOXIC, thrower));
                });
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

                    // 治疗音效
                    sl.playSound(null, hitPos.x, hitPos.y, hitPos.z,
                            SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.NEUTRAL, 0.2F, 1.0F);
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
                                            ModEffects.BERSERK.get(), 60, 0)));
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

                    // 5 波脉冲 (t=10,20,30,40,50)：由效果实体驱动
                    sl.addFreshEntity(new PotionBottleEffectEntity(sl,
                            hitPos.x, hitPos.y, hitPos.z, PotionBottleEffectEntity.TYPE_FROZEN, thrower));
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

                    // 5 轮伤害波 (t=10,30,50,70,90)：由效果实体驱动
                    sl.addFreshEntity(new PotionBottleEffectEntity(sl,
                            hitPos.x, hitPos.y, hitPos.z, PotionBottleEffectEntity.TYPE_INFERNO, thrower));
                });
    }
}