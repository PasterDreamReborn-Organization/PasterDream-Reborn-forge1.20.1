package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 风行者披风「空格助力」服务端结算：
 * 装备披风且处于鞘翅飞行时，按住空格（由 {@code WindHoverInputPacket} 同步的跳跃键状态）沿视线方向持续加速，
 * 无冷却、无消耗，最高不超过 {@link #BOOST_TARGET_SPEED} 格/tick。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class WindRunnerCloakHandler {

    /** 助力目标速度（格/tick），达到后不再加速。 */
    private static final double BOOST_TARGET_SPEED = 1.5D;
    /** 每 tick 最大加速度（格/tick）。 */
    private static final double BOOST_ACCELERATION = 0.06D;

    private WindRunnerCloakHandler() {
    }

    /** 是否在胸甲槽装备风行者披风。 */
    public static boolean isWearing(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.WIND_RUNNER_CLOAK.get());
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        if (!player.isFallFlying()) return;
        if (!isWearing(player)) return;
        if (!player.getPersistentData().getBoolean(FluffyWindAlloyArmorHandler.NBT_HOVER_JUMP)) return;

        Vec3 look = player.getLookAngle();
        Vec3 motion = player.getDeltaMovement();
        double forward = motion.dot(look);
        double delta = Math.min(BOOST_TARGET_SPEED - forward, BOOST_ACCELERATION);
        if (delta <= 0.0D) return;

        player.setDeltaMovement(motion.add(look.scale(delta)));
        player.hurtMarked = true;
    }

    /**
     * 装备披风时免疫鞘翅飞行撞击的动能伤害（{@link DamageTypes#FLY_INTO_WALL}），
     * 机制与折翼天使雕像一致。
     */
    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!isWearing(player)) return;
        DamageSource source = event.getSource();
        if (source.is(DamageTypes.FLY_INTO_WALL)) {
            event.setCanceled(true);
        }
    }
}
