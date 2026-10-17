package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.world.entity.FoxFireEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

/**
 * 战技范围伤害的仆从过滤工具。
 *
 * <p>各类范围/连锁类战技（狱炎剑熔岩爆炸、风雷矛连锁、泰拉剑气、草薙毒刺等）在结算伤害前
 * 都应排除施放者自己的仆从、召唤物与同队盟友，避免误伤己方单位。此处集中该判定，
 * 取代原先散落于各武器/射弹类中的重复实现。
 */
public final class MinionFilterHelper {

    private MinionFilterHelper() {}

    /** 是否为玩家自己的仆从/召唤物/同队盟友（范围战技不应命中）。 */
    public static boolean isOwnedMinion(LivingEntity entity, @Nullable Player owner) {
        if (owner == null) return false;
        if (entity instanceof OwnableEntity ownable) {
            return ownable.getOwner() == owner;
        }
        if (entity instanceof FoxFireEntity foxFire) {
            return foxFire.resolveOwner() == owner;
        }
        return entity.isAlliedTo(owner);
    }
}
