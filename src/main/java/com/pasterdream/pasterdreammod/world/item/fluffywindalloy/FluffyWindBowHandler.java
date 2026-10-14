package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.world.entity.FluffyWindArrowEntity;
import com.pasterdream.pasterdreammod.world.entity.WindAlloyLightningEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 萦风弓「引雷」命中效果：附魔「引雷」的萦风弓，其强化箭命中生物时在该处落下闪电，
 * 造成范围雷电伤害。
 * <p>
 * 监听 {@link LivingDamageEvent}（护甲/吸收结算之后的最终伤害）取本次箭矢最终伤害，
 * 落雷伤害 = max(最终伤害×25%, 5)。落雷使用萦风雷矛同款 {@link WindAlloyLightningEntity}
 * （单道，AOE 自动跳过射手及其仆从）；火矢附魔时使其范围内目标燃烧。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class FluffyWindBowHandler {

    /** 落雷伤害占箭矢最终伤害的比例上限 */
    private static final float LIGHTNING_DAMAGE_RATIO = 0.25F;
    /** 落雷伤害下限 */
    private static final float MIN_LIGHTNING_DAMAGE = 5.0F;

    private FluffyWindBowHandler() {
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (!(event.getSource().getDirectEntity() instanceof FluffyWindArrowEntity arrow)) {
            return;
        }
        // 仅附魔「引雷」的强化箭会落雷（生成时由弓写入标记）
        if (!arrow.hasChannelingLightning()) {
            return;
        }
        LivingEntity victim = event.getEntity();
        if (!(victim.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (!(event.getSource().getEntity() instanceof Player owner)) {
            return;
        }

        float strikeDamage = Math.max(event.getAmount() * LIGHTNING_DAMAGE_RATIO, MIN_LIGHTNING_DAMAGE);
        int flame = arrow.channelingFlameLevel();

        WindAlloyLightningEntity lightning = ModEntities.WIND_ALLOY_LIGHTNING.get().create(serverLevel);
        if (lightning != null) {
            lightning.setStrikes(1); // 与雷矛引雷一致：单道落雷
            lightning.init(owner, victim, strikeDamage, victim.position(), 0, 0, flame);
            lightning.moveTo(victim.getX(), victim.getY(), victim.getZ());
            serverLevel.addFreshEntity(lightning);
        }
    }
}
