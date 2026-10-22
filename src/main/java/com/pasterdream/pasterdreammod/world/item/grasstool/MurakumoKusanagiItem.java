package com.pasterdream.pasterdreammod.world.item.grasstool;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillLockHelper;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.skill.KusanagiVenomStingerPacket;
import com.pasterdream.pasterdreammod.world.entity.KusanagiVenomStingerEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * 『天丛云』草薙。
 *
 * <p>被动「剧毒之刺」：每次挥砍向准心方向发射一枚毒刺射弹（冷却 0.5s）。
 * 射弹命中叠加剧毒（等级 +1、时长 +5s，上限 III 级 / 60s），并造成攻击力 50% 的魔法伤害
 * （受锋利/亡灵杀手/截肢杀手/抢夺加成）。
 * 近战命中同样叠加剧毒。
 *
 * <p>发射由客户端左键事件经 {@link KusanagiVenomStingerPacket} 触发，
 * 而非 {@code ServerPlayer#swing} 钩子，以避免 Q 丢物品等动画误触发。
 */
public class MurakumoKusanagiItem extends SwordItem {

    private static final int LAUNCH_COOLDOWN_TICKS = 10; // 0.5s

    public MurakumoKusanagiItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.level().isClientSide()) {
            KusanagiVenomHelper.applyStacks(target);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    /** 服务端：主手为『天丛云』草薙且不在冷却中时发射一枚毒刺射弹。 */
    public static void onSwing(ServerPlayer player) {
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof MurakumoKusanagiItem)) {
            return;
        }
        if (SkillLockHelper.isSkillLockedSilent(player)) {
            return;
        }
        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            return;
        }
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        KusanagiVenomStingerEntity stinger = new KusanagiVenomStingerEntity(serverLevel, player);
        stinger.setWeapon(player, stack);
        serverLevel.addFreshEntity(stinger);
        // 接入战技共享冷却（随 SKILL_COOLDOWN_REDUCTION 缩放，并联动其它战技武器）
        SkillCooldownHelper.applySharedCooldown(player, LAUNCH_COOLDOWN_TICKS);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.1", Config.TheNumberofKillEnemytoEvolve));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.5"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.6"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.7"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.8"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.9"));
    }

    /** 仅客户端：监听左键（空挥 / 破坏方块 / 攻击实体）发送发射请求。 */
    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientHandler {
        @SubscribeEvent
        public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
            sendPacket(event.getEntity());
        }

        @SubscribeEvent
        public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
            sendPacket(event.getEntity());
        }

        @SubscribeEvent
        public static void onAttackEntity(AttackEntityEvent event) {
            sendPacket(event.getEntity());
        }

        private static void sendPacket(Player player) {
            if (player.getMainHandItem().getItem() instanceof MurakumoKusanagiItem) {
                ModNetwork.CHANNEL.sendToServer(new KusanagiVenomStingerPacket());
            }
        }
    }
}
