package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MeltDreamEnergySyncPacket;
import com.pasterdream.pasterdreammod.world.item.ModArmorMaterials;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

/**
 * 融梦水晶套装逻辑：
 * <p>
 * 单件：最大生命+2（由物品属性提供）；融梦能量+0.2/min；融梦能量上限+5；
 * 拥有与融梦水晶工具相同的融梦修补。
 * <p>
 * 全套（融梦守护）：融梦修补费用-50%；生命恢复II；染梦工具增强；
 * 融梦能量&gt;50%时理智光环（SAN_VARIABILITY）+1.2，否则+0.6。
 */
final class MeltDreamCrystalArmorHandler {

    /** 已应用的融梦能量上限加成（件数×5）跟踪键 */
    private static final String TAG_MAX_BONUS = "pasterdream.melt_dream_armor.max_bonus";
    /** 理智光环 SAN_VARIABILITY 修正 UUID */
    private static final UUID SAN_AURA_UUID = UUID.fromString("5b4c3d2e-1f6a-4b7c-9d8e-0f1a2b3c4d5e");

    private static final double ENERGY_PER_MIN_PER_PIECE = 0.2;
    private static final double MAX_ENERGY_PER_PIECE = 5.0;
    private static final double REPAIR_COST = 0.01;
    private static final double REPAIR_COST_FULL_SET = 0.005;
    private static final double SAN_AURA_HIGH = 1.2;
    private static final double SAN_AURA_LOW = 0.6;

    private MeltDreamCrystalArmorHandler() {
    }

    static void onTick(ServerPlayer player) {
        int pieces = countPieces(player);
        boolean fullSet = pieces >= 4;

        adjustMaxEnergy(player, pieces);
        regenEnergy(player, pieces);
        if (player.tickCount % 10 == 0) {
            repairMeltArmor(player, fullSet ? REPAIR_COST_FULL_SET : REPAIR_COST);
        }

        if (fullSet) {
            applyFullSetBuffs(player);
            applySanAura(player);
        } else {
            removeSanAura(player);
        }
    }

    private static int countPieces(ServerPlayer player) {
        int count = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.isArmor()) {
                ItemStack stack = player.getItemBySlot(slot);
                if (stack.getItem() instanceof ArmorItem armorItem
                        && armorItem.getMaterial() == ModArmorMaterials.MELT_DREAM) {
                    count++;
                }
            }
        }
        return count;
    }

    /** 动态调整融梦能量上限：每穿一件 +5，卸下自动扣除。 */
    private static void adjustMaxEnergy(ServerPlayer player, int pieces) {
        int targetBonus = pieces * (int) MAX_ENERGY_PER_PIECE;
        int prevBonus = player.getPersistentData().getInt(TAG_MAX_BONUS);
        if (targetBonus == prevBonus) {
            return;
        }
        MeltDreamEnergyHelper.addPlayerMaxMeltDreamEnergyAndSync(player, targetBonus - prevBonus);
        player.getPersistentData().putInt(TAG_MAX_BONUS, targetBonus);
    }

    /** 每件每 60 秒回复 0.2 融梦能量（每 20 tick 累加 0.2/60）。 */
    private static void regenEnergy(ServerPlayer player, int pieces) {
        if (pieces <= 0 || player.tickCount % 20 != 0) {
            return;
        }
        double perInterval = pieces * ENERGY_PER_MIN_PER_PIECE / 60.0;
        MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(player, perInterval);
    }

    /** 与融梦水晶工具相同的融梦修补：消耗融梦能量修复耐久。 */
    private static void repairMeltArmor(ServerPlayer player, double cost) {
        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(energy -> {
            boolean free = energy.getIsOrNotNeedConsumeDreamEnergy() || player.isCreative();
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (!slot.isArmor()) {
                    continue;
                }
                ItemStack stack = player.getItemBySlot(slot);
                if (!(stack.getItem() instanceof MeltDreamCrystalArmorItem)) {
                    continue;
                }
                if (stack.getDamageValue() < 1) {
                    continue;
                }
                if (!free && energy.getMeltDreamEnergy() <= cost) {
                    continue;
                }
                if (!free) {
                    energy.addMeltDreamEnergy(-cost);
                    MeltDreamEnergySyncPacket.sendToPlayer(player, energy);
                }
                if (stack.hurt(-1, RandomSource.create(), null)) {
                    stack.shrink(1);
                    stack.setDamageValue(0);
                }
            }
        });
    }

    /** 全套：融梦守护 + 染梦工具增强（染梦合金与融梦水晶工具伤害+50%）。 */
    private static void applyFullSetBuffs(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(ModEffects.MELT_DREAM_CRYSTAL_ARMOR_BUFF.get(), 25, 0,
                true, false, false));
        player.addEffect(new MobEffectInstance(ModEffects.DYEDREAM_UP_BUFF.get(), 25, 0,
                true, false, false));
    }

    /** 理智光环：融梦能量&gt;50% 时 SAN_VARIABILITY +1.2，否则 +0.6。 */
    private static void applySanAura(ServerPlayer player) {
        double energy = MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player);
        double max = MeltDreamEnergyHelper.getPlayerMaxMeltDreamEnergy(player);
        double amount = (max > 0 && energy / max > 0.5) ? SAN_AURA_HIGH : SAN_AURA_LOW;

        AttributeInstance attr = player.getAttribute(ModAttributes.SAN_VARIABILITY.get());
        if (attr == null) {
            return;
        }
        AttributeModifier existing = attr.getModifier(SAN_AURA_UUID);
        if (existing != null && existing.getAmount() == amount) {
            return;
        }
        attr.removeModifier(SAN_AURA_UUID);
        attr.addTransientModifier(new AttributeModifier(SAN_AURA_UUID,
                "pasterdream.melt_dream_armor.san_aura", amount, AttributeModifier.Operation.ADDITION));
    }

    private static void removeSanAura(ServerPlayer player) {
        AttributeInstance attr = player.getAttribute(ModAttributes.SAN_VARIABILITY.get());
        if (attr != null) {
            attr.removeModifier(SAN_AURA_UUID);
        }
    }
}
