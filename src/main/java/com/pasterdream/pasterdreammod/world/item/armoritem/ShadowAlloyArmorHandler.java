package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 暗影合金盔甲套装逻辑（满套「黑暗守望」）：
 * <ul>
 *   <li>灯影之下的武器（{@link ModItemTags#LAMP_SHADOW_WEAPON}）造成伤害 +30%；</li>
 *   <li>受到攻击时获得 10s 反击（复用 {@code counter_attack}），冷却 30s；</li>
 *   <li>受到来自暗影生物（{@link ModEntityTypeTags#SHADOW_MOB}）的攻击时，最终伤害 ×0.5。</li>
 * </ul>
 * 单件 +4 最大生命 / +1 攻击力由 {@link ShadowAlloyArmorItem#getAttributeModifiers} 提供。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class ShadowAlloyArmorHandler {

    /** 反击冷却就绪时间戳（游戏刻），存于玩家持久数据 */
    private static final String NBT_COUNTER_READY = "pasterdream:shadow_alloy_counter_ready";

    private static final int DARK_WATCH_DURATION = 25;
    private static final int COUNTER_DURATION = 200;
    private static final int COUNTER_COOLDOWN = 600;
    private static final float LAMP_SHADOW_WEAPON_MULTIPLIER = 1.30f;
    private static final float SHADOW_DAMAGE_MULTIPLIER = 0.5f;

    private ShadowAlloyArmorHandler() {
    }

    /** 满套时挂「黑暗守望」效果，用于向玩家展示套装已激活。 */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) return;
        if (!ShadowAlloyArmorItem.hasFullSet(player)) return;
        player.addEffect(new MobEffectInstance(ModEffects.DARK_WATCH.get(), DARK_WATCH_DURATION, 0,
                true, false, false));
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // 灯影武器增伤
        if (event.getSource().getEntity() instanceof Player attacker
                && !attacker.level().isClientSide()
                && ShadowAlloyArmorItem.hasFullSet(attacker)) {
            ItemStack weapon = attacker.getMainHandItem();
            if (weapon.is(ModItemTags.LAMP_SHADOW_WEAPON)) {
                event.setAmount(event.getAmount() * LAMP_SHADOW_WEAPON_MULTIPLIER);
            }
        }

        // 受击反击
        if (!(event.getEntity() instanceof ServerPlayer victim)) return;
        if (event.getAmount() <= 0) return;
        if (!ShadowAlloyArmorItem.hasFullSet(victim)) return;

        long now = victim.level().getGameTime();
        long ready = victim.getPersistentData().getLong(NBT_COUNTER_READY);
        if (now < ready) return;

        victim.getPersistentData().putLong(NBT_COUNTER_READY, now + COUNTER_COOLDOWN);
        victim.addEffect(new MobEffectInstance(ModEffects.COUNTER_ATTACK.get(), COUNTER_DURATION, 0,
                false, false));
    }

    /** 对来自暗影生物的攻击 50% 最终减伤（护甲/附魔结算之后）。 */
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide()) return;
        if (!ShadowAlloyArmorItem.hasFullSet(player)) return;

        var sourceEntity = event.getSource().getEntity();
        if (sourceEntity == null || !sourceEntity.getType().is(ModEntityTypeTags.SHADOW_MOB)) return;

        event.setAmount(event.getAmount() * SHADOW_DAMAGE_MULTIPLIER);
    }
}
