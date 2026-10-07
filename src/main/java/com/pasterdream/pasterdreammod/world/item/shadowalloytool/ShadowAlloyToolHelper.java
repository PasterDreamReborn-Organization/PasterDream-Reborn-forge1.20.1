package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.network.san.SanSyncPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
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
 * 主手时提供理智光环 -1.2、方块触及 +1（继承影蚀工具），
 * 并提供低 SAN 档位加成倍率。
 * <p>
 * 自带「噬心修补」（内置被动，非真实附魔）：位于背包中时消耗理智修复耐久。
 * <p>
 * 数值均为占位，待 runClient 原型调整（见 document/design/item/暗影合金工具.md）。
 */
final class ShadowAlloyToolHelper {

    // ==== 主手属性（继承自影蚀） ====
    private static final UUID SAN_VARIABILITY_UUID = UUID.fromString("daf6c47c-454a-48e4-a05d-4d7fb0deb673");
    private static final String SAN_VARIABILITY_NAME = "pasterdream.shadow_alloy.san_variability";
    private static final double SAN_VARIABILITY_AMOUNT = -1.2;
    private static final UUID BLOCK_REACH_UUID = UUID.fromString("4828780d-fdf1-4ed4-b6ce-71925026f828");
    private static final String BLOCK_REACH_NAME = "pasterdream.shadow_alloy.block_reach";
    private static final double BLOCK_REACH_AMOUNT = 1;

    // ==== 低 SAN 档位加成倍率（占位，待调） ====
    /** 中档（40%~60%）倍率 */
    static final float LOW_SAN_BONUS_MID = 1.15F;
    /** 高档（20%~40%）倍率 */
    static final float LOW_SAN_BONUS_HIGH = 1.30F;
    /** 最高档（<=20%）倍率 */
    static final float LOW_SAN_BONUS_MAX = 1.50F;

    // ==== 自带「噬心修补」（内置被动） ====
    /** 修复 1 点耐久的理智消耗 */
    private static final double REPAIR_COST = 0.1D;
    /** 噬心修补处理间隔（tick） */
    private static final int REPAIR_INTERVAL = 10;

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
     * 自带「噬心修补」（内置被动，非真实附魔，效仿融梦水晶系列）：
     * 工具位于背包中时，每 {@link #REPAIR_INTERVAL} tick 消耗 {@link #REPAIR_COST} 理智修复 1 点耐久。
     * 创造模式不消耗理智；理智不足则停止修复。
     */
    static void onInventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide || !(entity instanceof ServerPlayer player)) return;
        if (player.tickCount % REPAIR_INTERVAL != 0) return;
        if (stack.getDamageValue() < 1) return;

        player.getCapability(ModCapabilities.SAN).ifPresent(san -> {
            if (!san.getIsSanEnabled()) return;
            boolean free = player.isCreative();
            if (!free && san.getSanValue() < REPAIR_COST) return;
            if (!free) {
                san.addSanValue(-REPAIR_COST);
                SanSyncPacket.sendToPlayer(player, san);
            }
            stack.setDamageValue(stack.getDamageValue() - 1);
        });
    }

    static void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.passive"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.repair_header"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.repair"));
    }
}
