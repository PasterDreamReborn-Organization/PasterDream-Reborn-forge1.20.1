package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * 暗影合金弩被动「暗影飞矢」命中效果：
 * <ul>
 *   <li>被其箭矢命中的生物必定获得 10s 易伤；</li>
 *   <li>并随机获得一个配置白名单内的 5s 负面效果（默认缓慢/挖掘疲劳/失明/虚弱/中毒/凋零/飘浮/混乱/束缚，等级 I）。</li>
 *   <li>若装填的是**含烟火之星的烟花**，烟花爆炸伤害命中的所有生物同样触发（群体生效）。</li>
 * </ul>
 * 通过弹射物持久数据标记（{@link #PROJECTILE_TAG}）识别来源，作用于除射手外的所有生物（含玩家）。
 * <p>
 * 箭矢走 {@link ProjectileImpactEvent}（实体命中）；烟花不合成本方爆炸（{@code FireworkRocketEntity}
 * 直接调用 {@code LivingEntity#hurt}），故走 {@link LivingHurtEvent} 对每个被烟花伤害的生物分别结算。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class ShadowAlloyCrossbowHandler {

    /** 弹射物持久数据标记，用于在命中/爆炸伤害时识别来源 */
    public static final String PROJECTILE_TAG = "pasterdream:shadow_alloy_crossbow_projectile";

    /** 必施加的易伤持续时间（tick），10 秒 */
    private static final int VULNERABILITY_DURATION = 200;

    private ShadowAlloyCrossbowHandler() {
    }

    /** 箭矢命中实体。 */
    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;
        if (!arrow.getPersistentData().getBoolean(PROJECTILE_TAG)) return;
        if (arrow.level().isClientSide) return;
        if (!(event.getRayTraceResult() instanceof EntityHitResult entityHit)) return;

        Entity hit = entityHit.getEntity();
        if (!(hit instanceof LivingEntity target)) return;
        if (target == arrow.getOwner()) return;

        applyDebuffs(target);
    }

    /** 含烟火之星的烟花爆炸伤害（{@code FireworkRocketEntity} 逐个 {@code hurt}）——群体生效。 */
    @SubscribeEvent
    public static void onFireworkHurt(LivingHurtEvent event) {
        if (event.isCanceled()) return;
        LivingEntity victim = event.getEntity();
        if (victim.level().isClientSide) return;

        Entity direct = event.getSource().getDirectEntity();
        if (!(direct instanceof FireworkRocketEntity firework)) return;
        if (!firework.getPersistentData().getBoolean(PROJECTILE_TAG)) return;
        if (victim == event.getSource().getEntity()) return;

        applyDebuffs(victim);
    }

    /** 施加必选易伤 + 随机白名单负面。 */
    private static void applyDebuffs(LivingEntity target) {
        target.addEffect(new MobEffectInstance(ModEffects.VULNERABILITY.get(), VULNERABILITY_DURATION, 0, false, true));

        List<MobEffect> pool = Config.getShadowAlloyCrossbowDebuffs();
        if (!pool.isEmpty()) {
            MobEffect effect = pool.get(target.getRandom().nextInt(pool.size()));
            target.addEffect(new MobEffectInstance(effect, Config.shadowAlloyCrossbowDebuffDuration,
                    Config.shadowAlloyCrossbowDebuffAmplifier, false, true));
        }
    }
}
