package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

import java.util.List;

/**
 * 仅客户端加载的暗影吐息物品提示文本辅助类。
 * 持有 net.minecraft.client 引用的逻辑必须放在这里，
 * 避免在专用服务器加载 ShadowBreathItem 时触发客户端类加载。
 */
public class ShadowBreathClientTooltip
{
    /** 按住 Shift 时展示基于玩家当前理智的实时加成。 */
    public static void addCurrentBonusTooltip(List<Component> tooltip)
    {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        player.getCapability(ModCapabilities.SAN).ifPresent(capability -> {
            if (!capability.getIsSanEnabled()) {
                tooltip.add(Component.translatable("tooltip.pasterdream.shadow_breath.current.disabled"));
                return;
            }
            double san = capability.getSanValue();
            double maxSan = capability.getMaxSanValue();
            AttributeInstance attr = player.getAttribute(ModAttributes.MAX_SAN_EXTRA.get());
            if (attr != null) {
                maxSan += attr.getValue();
            }
            double ratio = maxSan > 0 ? san / maxSan : 0.0;
            int damageTier = ShadowBreathItem.getDamageTier(ratio);
            int armorTier = ShadowBreathItem.getArmorTier(ratio);
            int regenTier = ShadowBreathItem.getRegenTier(ratio);

            tooltip.add(Component.translatable("tooltip.pasterdream.shadow_breath.current.header"));
            boolean hasBonus = false;
            if (damageTier > 0) {
                tooltip.add(Component.translatable("tooltip.pasterdream.shadow_breath.current.attack",
                        damageTier * 4, damageTier * 4));
                hasBonus = true;
            }
            if (armorTier > 0) {
                tooltip.add(Component.translatable("tooltip.pasterdream.shadow_breath.current.armor", armorTier * 2));
                hasBonus = true;
            }
            if (regenTier >= 0) {
                tooltip.add(Component.translatable("tooltip.pasterdream.shadow_breath.current.regen",
                        ShadowBreathItem.getRegenLevelName(regenTier)));
                hasBonus = true;
            }
            if (!hasBonus) {
                tooltip.add(Component.translatable("tooltip.pasterdream.shadow_breath.current.none"));
            }
        });
    }
}