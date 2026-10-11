package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

/**
 * 萦风合金盔甲套装逻辑（满套「御风之誓」）：
 * <ul>
 *   <li>非晴朗天气时，单件的移动速度 / 战技冷却增益翻倍（额外挂一组临时属性修饰器）；</li>
 *   <li>免疫雷电伤害；</li>
 *   <li>雨天受到伤害 -10%，雷雨 -20%（最终减伤）；</li>
 *   <li>被弹射物命中时 50% 概率弹开（视同对弹射物免疫，弹射物归属不变）；</li>
 *   <li>下落途中按住空格可缓降（手感等同原版缓降效果），持续消耗饥饿度。</li>
 * </ul>
 * 拉弓 / 弩装填 -25% 由 Mixin（{@code UsingItemSpeedMixin}，加速使用计时器，逻辑与动画同步）实现。
 * 单件 +5% 移速 / -5% 冷却由 {@link FluffyWindAlloyArmorItem#getAttributeModifiers} 提供。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class FluffyWindAlloyArmorHandler {

    /** 天气翻倍：额外的移速加成（4 件 × +5%） */
    private static final double WEATHER_SPEED_BONUS = 0.20;
    /** 天气翻倍：额外的战技冷却降低（4 件 × -5%） */
    private static final double WEATHER_CD_BONUS = -0.20;

    private static final UUID WEATHER_SPEED_UUID = UUID.fromString("f1e2d3c4-1001-4b01-8a01-000000001001");
    private static final UUID WEATHER_CD_UUID = UUID.fromString("f1e2d3c4-1002-4b01-8a01-000000001002");

    private static final float RAIN_DAMAGE_MULTIPLIER = 0.90F;
    private static final float THUNDER_DAMAGE_MULTIPLIER = 0.80F;
    private static final double DEFLECT_CHANCE = 0.5D;

    /** 原版缓降：空中重力 0.08 → 0.01（每刻少受 0.07 重力），下落自然减速、横向按原版摩擦衰减。 */
    private static final double SLOW_FALL_GRAVITY_OFFSET = 0.07D;
    private static final float SLOW_FALL_EXHAUSTION = 0.05F;

    /** 套装标记效果刷新时长（刻） */
    private static final int OATH_DURATION = 25;

    /** 跳跃键状态（客户端由 {@code ModKeyMappings} 本地写入并经 {@code WindHoverInputPacket} 同步服务端） */
    public static final String NBT_HOVER_JUMP = "pasterdream:fluffy_wind_alloy_hover_jump";

    private FluffyWindAlloyArmorHandler() {
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;

        // 客户端本地玩家：本地复刻缓降，使横向速度完全不受服务端逐 tick 同步干扰。
        if (player.isLocalPlayer()) {
            if (FluffyWindAlloyArmorItem.hasFullSet(player)) {
                applySlowFall(player, false);
            }
            if (player.onGround()) {
                player.getPersistentData().putBoolean(NBT_HOVER_JUMP, false);
            }
            return;
        }

        if (!(player instanceof ServerPlayer serverPlayer)) return;

        boolean fullSet = FluffyWindAlloyArmorItem.hasFullSet(serverPlayer);
        applyWeatherBonus(serverPlayer, fullSet && serverPlayer.level().isRaining());
        if (fullSet) {
            serverPlayer.addEffect(new MobEffectInstance(ModEffects.WIND_THUNDER_BOON.get(), OATH_DURATION, 0,
                    true, false, false));
            applySlowFall(serverPlayer, true);
        }
        if (serverPlayer.onGround()) {
            serverPlayer.getPersistentData().putBoolean(NBT_HOVER_JUMP, false);
        }
    }

    /** 非晴朗天气时额外挂一组属性修饰器，使单件增益翻倍。 */
    private static void applyWeatherBonus(Player player, boolean active) {
        toggleModifier(player.getAttribute(Attributes.MOVEMENT_SPEED), WEATHER_SPEED_UUID,
                "pasterdream.wind_weather_speed", WEATHER_SPEED_BONUS, active);
        toggleModifier(player.getAttribute(ModAttributes.SKILL_COOLDOWN_RATE.get()), WEATHER_CD_UUID,
                "pasterdream.wind_weather_cooldown", WEATHER_CD_BONUS, active);
    }

    private static void toggleModifier(AttributeInstance instance, UUID uuid, String name,
                                       double amount, boolean active) {
        if (instance == null) return;
        if (active) {
            if (instance.getModifier(uuid) == null) {
                instance.addTransientModifier(new AttributeModifier(uuid, name, amount,
                        AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        } else {
            instance.removeModifier(uuid);
        }
    }

    /**
     * 缓降结算（服务端与客户端本地玩家各自调用，保证两侧位置预测一致）。
     *
     * <p>复刻原版缓降物理：仅削弱重力（空中 0.08 → 0.01），每刻把正常重力多扣掉的 0.07
     * 补回 {@code deltaMovement.y}，不做速度钳制、不留横向惯性补偿，横向速度完全不动，
     * 因此无需写 {@code hurtMarked}，不会触发原版逐 tick 的整向量速度同步导致横向突变。
     *
     * @param consumeHunger 仅服务端为 {@code true}，客户端不重复消耗饥饿度。
     */
    public static void applySlowFall(Player player, boolean consumeHunger) {
        if (!player.getPersistentData().getBoolean(NBT_HOVER_JUMP)) return;
        if (player.onGround() || player.isInWater() || player.isInLava() || player.onClimbable()
                || player.isFallFlying() || player.getAbilities().flying || player.isPassenger()) {
            return;
        }
        if (player.getFoodData().getFoodLevel() <= 0) return;
        Vec3 motion = player.getDeltaMovement();
        if (motion.y >= 0) return;

        if (consumeHunger) {
            player.causeFoodExhaustion(SLOW_FALL_EXHAUSTION);
        }
        player.setDeltaMovement(motion.x, motion.y + SLOW_FALL_GRAVITY_OFFSET, motion.z);
        player.fallDistance = 0.0F;
    }

    /** 免疫雷电伤害。 */
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getEntity() instanceof Player player)) return;
        if (!FluffyWindAlloyArmorItem.hasFullSet(player)) return;
        if (event.getSource().is(DamageTypes.LIGHTNING_BOLT)) {
            event.setCanceled(true);
        }
    }

    /** 雨天 -10% / 雷雨 -20% 最终减伤。 */
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide()) return;
        if (!FluffyWindAlloyArmorItem.hasFullSet(player)) return;
        if (player.level().isThundering()) {
            event.setAmount(event.getAmount() * THUNDER_DAMAGE_MULTIPLIER);
        } else if (player.level().isRaining()) {
            event.setAmount(event.getAmount() * RAIN_DAMAGE_MULTIPLIER);
        }
    }

    /** 50% 概率弹开命中自己的弹射物（视同对弹射物免疫，归属不变）。 */
    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        Projectile projectile = event.getProjectile();
        if (projectile.level().isClientSide()) return;
        if (!(event.getRayTraceResult() instanceof EntityHitResult entityHitResult)) return;
        if (!(entityHitResult.getEntity() instanceof ServerPlayer player)) return;
        if (!FluffyWindAlloyArmorItem.hasFullSet(player)) return;
        if (player.getRandom().nextDouble() >= DEFLECT_CHANCE) return;

        event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
    }

    /** 重新登录时清除残留的缓降输入，避免跨会话误触。 */
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        event.getEntity().getPersistentData().putBoolean(NBT_HOVER_JUMP, false);
    }
}
