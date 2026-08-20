package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.PasterDreamMod;
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
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class MeltDreamCrystalArmorHandler {

    /** 理智光环 SAN_VARIABILITY 修正 UUID */
    private static final UUID SAN_AURA_UUID = UUID.fromString("5b4c3d2e-1f6a-4b7c-9d8e-0f1a2b3c4d5e");

    private static final double REPAIR_COST = 0.01;
    private static final double REPAIR_COST_FULL_SET = 0.005;
    private static final double SAN_AURA_HIGH = 1.2;
    private static final double SAN_AURA_LOW = 0.6;

    private MeltDreamCrystalArmorHandler() {
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) return;
        if (!player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).isPresent()) return;
        onTick(player);
    }

    private static void onTick(ServerPlayer player) {
        boolean fullSet = countPieces(player) >= 4;

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
        double max = MeltDreamEnergyHelper.getPlayerMaxMeltDreamEnergyEffective(player);
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
