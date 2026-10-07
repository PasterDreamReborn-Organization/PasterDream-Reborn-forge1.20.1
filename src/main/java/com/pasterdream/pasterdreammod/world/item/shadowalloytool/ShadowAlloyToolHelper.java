package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import java.util.List;
import java.util.UUID;

/**
 * 暗影合金工具共享逻辑。
 * <p>
 * 继承影蚀工具的主手属性（SAN 波动 -1.2、方块触及 +1），
 * 并提供低 SAN 档位加成倍率与 SAN 代价（下限 20%）的公共逻辑。
 * <p>
 * 数值均为占位，待 runClient 原型调整（见 document/design/item/暗影合金工具.md）。
 */
final class ShadowAlloyToolHelper {

    // ==== 继承自影蚀的属性 ====
    private static final UUID SAN_VARIABILITY_UUID = UUID.fromString("daf6c47c-454a-48e4-a05d-4d7fb0deb673");
    private static final String SAN_VARIABILITY_NAME = "pasterdream.shadow_alloy.san_variability";
    private static final double SAN_VARIABILITY_AMOUNT = -1.2;
    private static final UUID BLOCK_REACH_UUID = UUID.fromString("4828780d-fdf1-4ed4-b6ce-71925026f828");
    private static final String BLOCK_REACH_NAME = "pasterdream.shadow_alloy.block_reach";
    private static final double BLOCK_REACH_AMOUNT = 1;

    // ==== 低 SAN 档位加成倍率（占位，待调） ====
    /** 中档（40%~60%）倍率 */
    static final float LOW_SAN_BONUS_MID = 1.05F;
    /** 高档（20%~40%）倍率 */
    static final float LOW_SAN_BONUS_HIGH = 1.15F;
    /** 最高档（<=20%）倍率 */
    static final float LOW_SAN_BONUS_MAX = 1.30F;

    // ==== SAN 代价（占位，待调） ====
    /** 持有扣 SAN 的周期（tick） */
    static final int HELD_DRAIN_INTERVAL_TICKS = 40;
    /** 持有每周期扣减的 SAN */
    static final double HELD_DRAIN_AMOUNT = 0.1D;
    /** 攻击命中扣减的 SAN */
    static final double ATTACK_DRAIN_AMOUNT = 1.0D;
    /** SAN 代价下限比例（保底 20%） */
    static final double SAN_FLOOR_RATIO = 0.2D;

    private ShadowAlloyToolHelper() {
    }

    static Multimap<Attribute, AttributeModifier> withSanVariability(
            EquipmentSlot slot, Multimap<Attribute, AttributeModifier> defaults) {
        if (slot != EquipmentSlot.MAINHAND) {
            return defaults;
        }
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(defaults);
        builder.put(ModAttributes.SAN_VARIABILITY.get(),
                new AttributeModifier(SAN_VARIABILITY_UUID, SAN_VARIABILITY_NAME,
                        SAN_VARIABILITY_AMOUNT, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.BLOCK_REACH.get(),
                new AttributeModifier(BLOCK_REACH_UUID, BLOCK_REACH_NAME,
                        BLOCK_REACH_AMOUNT, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    /** 依据当前 SAN 比例返回低 SAN 加成倍率（挖掘速度与攻击伤害共用）。 */
    static float getLowSanMultiplier(ServerPlayer player) {
        double maxSan = SanHelper.getPlayerMaxSanEffective(player);
        if (maxSan <= 0.0D) return 1.0F;
        double ratio = SanHelper.getPlayerSan(player) / maxSan;
        if (ratio <= 0.2D) return LOW_SAN_BONUS_MAX;
        if (ratio <= 0.4D) return LOW_SAN_BONUS_HIGH;
        if (ratio <= 0.6D) return LOW_SAN_BONUS_MID;
        return 1.0F;
    }

    /**
     * 扣除玩家 SAN，但保底 20%（工具不会把使用者逼到 20% 以下）。
     * 返回是否实际发生扣除。
     */
    static boolean drainSanWithFloor(ServerPlayer player, double amount) {
        if (amount <= 0.0D) return false;
        double maxSan = SanHelper.getPlayerMaxSanEffective(player);
        double floor = maxSan * SAN_FLOOR_RATIO;
        double current = SanHelper.getPlayerSan(player);
        if (current <= floor) return false;
        double actual = Math.min(amount, current - floor);
        SanHelper.addPlayerSanAndSync(player, -actual);
        return true;
    }

    static void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.3"));
    }
}
