package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

/**
 * 折翼天使雕像全局被动处理器。
 * 每次受到伤害时按配置概率获得短暂无敌（概率/时长由 Config 控制，触发时播放破风骑士无敌被动同款 SCRAPE 粒子）；
 * 佩戴期间免疫摔落伤害与鞘翅飞行撞击动能伤害。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class FracturedAngelStatueHandler {

    private static final String INVULN_UNTIL_TAG = "pasterdream:fractured_angel_statue_invuln_until";

    /** 检查玩家是否在饰品栏装备了折翼天使雕像 */
    public static boolean isWearing(ServerPlayer player) {
        return CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.FRACTURED_ANGEL_STATUE.get()).isPresent())
                .orElse(false);
    }

    private static boolean isInvulnerableActive(ServerPlayer player) {
        return player.getPersistentData().getLong(INVULN_UNTIL_TAG) > player.level().getGameTime();
    }

    /**
     * 使用瞬身术时：若主手为未装填的弩，则立即按原版逻辑为其装填弹射物（含多重射击/创造模式不消耗等）。
     */
    public static void onBlink(ServerPlayer player) {
        if (!isWearing(player)) return;

        ItemStack crossbow = player.getMainHandItem();
        if (!(crossbow.getItem() instanceof CrossbowItem)) return;
        if (CrossbowItem.isCharged(crossbow)) return;

        if (CrossbowItem.tryLoadProjectiles(player, crossbow)) {
            CrossbowItem.setCharged(crossbow, true);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.CROSSBOW_LOADING_END, SoundSource.PLAYERS, 1.0F,
                    1.0F / (player.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    /** 无敌窗口内拦截所有伤害，并免疫摔落 / 鞘翅撞击动能伤害 */
    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!isWearing(player)) return;

        DamageSource source = event.getSource();
        if (source.is(DamageTypes.FALL) || source.is(DamageTypes.FLY_INTO_WALL)
                || isInvulnerableActive(player)) {
            event.setCanceled(true);
        }
    }

    /** 受到伤害时：按配置概率获得短暂无敌（同破风骑士无敌被动粒子） */
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!isWearing(player)) return;

        DamageSource source = event.getSource();
        if (source.is(DamageTypes.FALL) || source.is(DamageTypes.FLY_INTO_WALL)) return;
        if (isInvulnerableActive(player)) return;

        if (player.getRandom().nextFloat() < Config.fracturedAngelStatueInvulnerableChance) {
            player.getPersistentData().putLong(INVULN_UNTIL_TAG,
                    player.level().getGameTime() + Config.fracturedAngelStatueInvulnerableTicks);
            if (player.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.SCRAPE, player.getX(),
                        player.getY() + player.getBbHeight() * 0.6, player.getZ(),
                        24, 1.2, 1.0, 1.2, 0.1);
            }
        }
    }
}