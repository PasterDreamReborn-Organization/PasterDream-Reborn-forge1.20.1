package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.WindwreathedThunderSpearItem;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 萦风合金系列被动 · 疾风过境（攻击部分）：全系列工具（剑/斧/镐/锹/锄）与雷矛统一生效。
 * <p>
 * 近战命中时，主伤害按移动速度放大：{@code (1 + 移动速度) × 攻击力}（保留暴击/附魔比例），
 * 并额外造成 {@code 攻击力 × 0.1} 的雷电伤害。
 * <p>
 * 雷矛的激流突进自旋（{@code isAutoSpinAttack}）保留其专属突进结算
 * （{@link WindwreathedThunderSpearItem#applyDashOnTouch}：激流加成 / 横扫溅射），不走通用加伤分支。
 * <p>
 * 说明："移动速度" = max(移动速度属性, 进行攻击时的瞬时移动速度)。
 * 战技等自算伤害会置位 {@link FluffyWindAlloyToolHelper#APPLYING_TAG} 以跳过本被动，避免递归。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class FluffyWindAlloyAttackHandler {

    /** 附加雷电伤害占攻击力的比例 */
    private static final float LIGHTNING_DAMAGE_RATIO = 0.1F;

    private FluffyWindAlloyAttackHandler() {
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.isCanceled()) return;
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        if (!(player.getMainHandItem().getItem() instanceof FluffyWindAlloyTool)) return;
        if (player.getPersistentData().getBoolean(FluffyWindAlloyToolHelper.APPLYING_TAG)) return;
        if (event.getSource().getDirectEntity() != player) return;

        // 雷矛激流突进的自旋命中：保留专属突进结算（激流加成 / 横扫溅射），不走通用加伤
        if (player.isAutoSpinAttack()
                && player.getMainHandItem().getItem() instanceof WindwreathedThunderSpearItem) {
            WindwreathedThunderSpearItem.applyDashOnTouch(player, event);
            return;
        }

        LivingEntity target = event.getEntity();

        float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float critRatio = atk > 0 ? event.getAmount() / atk : 1.0F; // 保留暴击/附魔比例
        double speed = Math.max(player.getAttributeValue(Attributes.MOVEMENT_SPEED),
                player.getDeltaMovement().horizontalDistance());
        float mainDamage = (float) ((1 + speed) * atk) * critRatio;
        float lightningDamage = atk * LIGHTNING_DAMAGE_RATIO;

        event.setCanceled(true);
        player.getPersistentData().putBoolean(FluffyWindAlloyToolHelper.APPLYING_TAG, true);
        target.invulnerableTime = 0;
        target.hurt(target.level().damageSources().playerAttack(player), mainDamage);
        // 先造成攻击，再造成雷伤，取消无敌帧
        target.invulnerableTime = 0;
        target.hurt(lightningSource(target, player), lightningDamage);
        player.getPersistentData().remove(FluffyWindAlloyToolHelper.APPLYING_TAG);
    }

    /** 以玩家为击杀归属的雷电伤害源，避免无来源伤害导致击杀/抢夺/统计丢失。 */
    private static DamageSource lightningSource(LivingEntity target, Player player) {
        Holder<DamageType> holder = target.level().registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(DamageTypes.LIGHTNING_BOLT);
        return new DamageSource(holder, player, player);
    }
}
