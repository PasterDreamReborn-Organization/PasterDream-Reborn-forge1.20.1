package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import com.pasterdream.pasterdreammod.world.entity.AaroncosLeftHandEntity;
import com.pasterdream.pasterdreammod.world.entity.AaroncosRightHandEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

/**
 * 堕落者之印全局被动处理器。
 * 实现暗影生物中立、低SAN刷怪协助作战。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class SealOfTheCorruptedHandler {

    private static final String FRIENDLY_TAG = "pasterdream:seal_friendly";

    /** 检查玩家是否在饰品栏装备了堕落者之印 */
    public static boolean hasSeal(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.SEAL_OF_THE_CORRUPTED.get()).isPresent())
                .orElse(false);
    }

    /**
     * 拦截暗影生物对佩戴封印玩家的目标切换。
     * 友善暗影生物（低SAN刷怪）会被重定向到附近敌对生物，其余直接取消。
     */
    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (!(event.getNewTarget() instanceof Player player)) return;
        if (!hasSeal(player)) return;
        if (!event.getEntity().getType().is(ModEntityTypeTags.SHADOW_MOB)) return;
        // 亚伦柯斯BOSS不受堕落者之印影响，始终攻击佩戴者
        if (event.getEntity() instanceof AaroncosLeftHandEntity || event.getEntity() instanceof AaroncosRightHandEntity) return;

        if (event.getEntity() instanceof Mob mob
                && event.getEntity().getPersistentData().getBoolean(FRIENDLY_TAG)) {
            LivingEntity altTarget = findNearbyHostile(mob, player, 20);
            if (altTarget != null) {
                event.setNewTarget(altTarget);
                return;
            }
        }
        event.setCanceled(true);
    }

    /**
     * 友善暗影生物每 20 tick 检查一次目标。
     * 当前目标无效（死亡/为封印玩家）时，重新搜索附近敌对生物协助玩家。
     * 若源玩家已离线或卸下封印，移除友善标记。
     */
    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingTickEvent event) {
        if (!event.getEntity().getPersistentData().getBoolean(FRIENDLY_TAG)) return;
        if (!(event.getEntity() instanceof Mob mob)) return;
        if (mob.tickCount % 20 != 0) return;

        LivingEntity currentTarget = mob.getTarget();
        if (currentTarget != null && currentTarget.isAlive()
                && !(currentTarget instanceof Player p && hasSeal(p))) {
            return;
        }
        if (currentTarget != null) {
            mob.setTarget(null);
        }

        if (!event.getEntity().getPersistentData().hasUUID("ShadowSourcePlayer")) return;
        Player sourcePlayer = mob.level().getPlayerByUUID(
                event.getEntity().getPersistentData().getUUID("ShadowSourcePlayer"));
        if (sourcePlayer == null || !hasSeal(sourcePlayer)) {
            event.getEntity().getPersistentData().remove(FRIENDLY_TAG);
            return;
        }

        LivingEntity altTarget = findNearbyHostile(mob, sourcePlayer, 16);
        if (altTarget != null) {
            mob.setTarget(altTarget);
        }
    }

    /**
     * 在玩家附近搜索一个非暗影的战斗目标。
     * 优先选择正在以玩家为目标的生物（含被激怒的中立生物），其次选择天生敌对生物。
     */
    private static LivingEntity findNearbyHostile(Mob self, Player player, double range) {
        AABB area = self.getBoundingBox().inflate(range);
        List<Mob> candidates = self.level().getEntitiesOfClass(Mob.class, area,
                e -> e.isAlive()
                        && e.canAttack(e)
                        && !e.getType().is(ModEntityTypeTags.SHADOW_MOB)
                        && e != self);

        // 优先：正在攻击玩家的生物（含被激怒的中立生物如狼、末影人等）
        for (Mob m : candidates) {
            if (m.getTarget() == player) return m;
        }
        // 其次：天生敌对生物 (Monster 子类)
        for (Mob m : candidates) {
            if (m instanceof Monster) return m;
        }
        return null;
    }
}
