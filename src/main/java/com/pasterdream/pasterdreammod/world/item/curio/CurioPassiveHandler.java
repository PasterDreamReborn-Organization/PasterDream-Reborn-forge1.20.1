package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.network.curio.CurioActivationPacket;
import com.pasterdream.pasterdreammod.world.item.armoritem.qym.QymCatEarsItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;

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

        // QYM套装：攻击附带额外50%虚空伤害
        if (event.getSource().getEntity() instanceof Player player
                && event.getSource().getEntity() != event.getEntity()
                && QymCatEarsItem.hasFullSet(player)) {
            float voidDamage = event.getAmount() * 0.5F;
            LivingEntity target = event.getEntity();
            target.invulnerableTime = 0;
            target.hurt(target.level().damageSources().fellOutOfWorld(), voidDamage);
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
}
