package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class ShadowBreathItem extends Item implements ICurioItem {
    private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("a1b2c3d4-0001-4e5f-8a9b-0c1d2e3f4a5b");
    private static final UUID MAGIC_DAMAGE_UUID = UUID.fromString("a1b2c3d4-0002-4e5f-8a9b-0c1d2e3f4a5b");
    private static final UUID ARMOR_UUID = UUID.fromString("a1b2c3d4-0003-4e5f-8a9b-0c1d2e3f4a5b");

    private static final String ATTACK_DAMAGE_NAME = "Shadow Breath attack damage";
    private static final String MAGIC_DAMAGE_NAME = "Shadow Breath magic damage";
    private static final String ARMOR_NAME = "Shadow Breath armor";

    /** 生命恢复效果的刷新时长（5 秒），足够覆盖每次恢复节拍。 */
    private static final int REGEN_DURATION = 100;
    /** 生命恢复效果即将到期（1 tick）时重新施加，保证恢复节奏不断档。 */
    private static final int REGEN_REFRESH_THRESHOLD = 1;
    /** 生命恢复等级对应的罗马数字显示名。 */
    private static final String[] REGEN_LEVELS = {"I", "II", "III"};

    public ShadowBreathItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.SUPERIOR));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity == null || entity.level().isClientSide()) return;
        updateState(entity);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity == null || entity.level().isClientSide()) return;
        updateState(entity);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity == null || entity.level().isClientSide()) return;
        removeAllBonuses(entity);
    }

    /**
     * 根据当前理智比例刷新加成：
     * 理智 ≥ 50%：每增加 10% 理智，攻击力 +4%、魔法伤害 +4%（最高 +20%）；
     * 理智 < 50%：每降低 10% 理智，护甲值 +2（最高 +10）；理智 ≤ 40% 时获得生命恢复 I，每降低 10% 理智，生命恢复 +1 级（最高 III）。
     * 理智系统使能关闭时不生效。
     */
    private static void updateState(LivingEntity entity) {
        if (!(entity instanceof ServerPlayer player)) return;
        if (!SanHelper.getIsSanEnabled(player)) {
            removeAllBonuses(player);
            return;
        }

        double maxSan = SanHelper.getPlayerMaxSanEffective(player);
        int damageTier = 0;
        int armorTier = 0;
        int regenTier = -1;
        if (maxSan > 0) {
            double ratio = SanHelper.getPlayerSan(player) / maxSan;
            damageTier = getDamageTier(ratio);
            armorTier = getArmorTier(ratio);
            regenTier = getRegenTier(ratio);
        }

        if (damageTier > 0) {
            setModifier(player, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_UUID, ATTACK_DAMAGE_NAME,
                    damageTier * 0.04, AttributeModifier.Operation.MULTIPLY_TOTAL);
            setModifier(player, ModAttributes.MAGIC_DAMAGE_RATE.get(), MAGIC_DAMAGE_UUID, MAGIC_DAMAGE_NAME,
                    damageTier * 0.04, AttributeModifier.Operation.MULTIPLY_TOTAL);
        } else {
            setModifier(player, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_UUID, ATTACK_DAMAGE_NAME,
                    0.0, AttributeModifier.Operation.MULTIPLY_TOTAL);
            setModifier(player, ModAttributes.MAGIC_DAMAGE_RATE.get(), MAGIC_DAMAGE_UUID, MAGIC_DAMAGE_NAME,
                    0.0, AttributeModifier.Operation.MULTIPLY_TOTAL);
        }
        setModifier(player, Attributes.ARMOR, ARMOR_UUID, ARMOR_NAME,
                armorTier * 2.0, AttributeModifier.Operation.ADDITION);
        if (regenTier >= 0) {
            applyRegeneration(player, regenTier);
        } else {
            removeRegeneration(player);
        }
    }

    /** 理智 ≥ 50%：每 +10% 理智 1 档，最高 5 档（+20%）。 */
    private static int getDamageTier(double ratio) {
        return ratio >= 0.5 ? Math.min((int) ((ratio - 0.5) * 10.0), 5) : 0;
    }

    /** 理智 < 50%：每 -10% 理智 1 档，最高 5 档（+10 护甲）。 */
    private static int getArmorTier(double ratio) {
        return ratio < 0.5 ? Math.min((int) ((0.5 - ratio) * 10.0), 5) : 0;
    }

    /** 理智 ≤ 40%：每 -10% 理智 1 级，最高 3 级（生命恢复 III）；40% 以上无生命恢复。 */
    private static int getRegenTier(double ratio) {
        return ratio <= 0.4 ? Math.min((int) ((0.4 - ratio) * 10.0), 2) : -1;
    }

    private static void setModifier(LivingEntity entity, Attribute attribute, UUID id, String name,
                                    double amount, AttributeModifier.Operation operation) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance == null) return;
        AttributeModifier existing = instance.getModifier(id);
        if (existing != null && existing.getAmount() == amount) return;
        if (existing != null) instance.removeModifier(id);
        if (amount != 0.0) {
            instance.addPermanentModifier(new AttributeModifier(id, name, amount, operation));
        }
    }

    private static void applyRegeneration(LivingEntity entity, int amplifier) {
        MobEffectInstance existing = entity.getEffect(MobEffects.REGENERATION);
        if (existing == null) {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, REGEN_DURATION, amplifier, false, false, true));
        } else if (existing.getDuration() <= REGEN_REFRESH_THRESHOLD
                || (existing.getAmplifier() != amplifier && existing.getDuration() <= REGEN_DURATION)) {
            entity.removeEffect(MobEffects.REGENERATION);
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, REGEN_DURATION, amplifier, false, false, true));
        }
    }

    private static void removeRegeneration(LivingEntity entity) {
        MobEffectInstance existing = entity.getEffect(MobEffects.REGENERATION);
        if (existing != null && existing.getDuration() <= REGEN_DURATION) {
            entity.removeEffect(MobEffects.REGENERATION);
        }
    }

    private static void removeAllBonuses(LivingEntity entity) {
        setModifier(entity, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_UUID, ATTACK_DAMAGE_NAME,
                0.0, AttributeModifier.Operation.MULTIPLY_TOTAL);
        setModifier(entity, ModAttributes.MAGIC_DAMAGE_RATE.get(), MAGIC_DAMAGE_UUID, MAGIC_DAMAGE_NAME,
                0.0, AttributeModifier.Operation.MULTIPLY_TOTAL);
        setModifier(entity, Attributes.ARMOR, ARMOR_UUID, ARMOR_NAME,
                0.0, AttributeModifier.Operation.ADDITION);
        removeRegeneration(entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(ModRarities.qualityTooltip(ModRarities.SUPERIOR));
        if (Screen.hasShiftDown() && level != null && level.isClientSide()) {
            addCurrentBonusTooltip(tooltip);
        } else {
            for (int i = 0; i < 9; i++) {
                tooltip.add(Component.translatable("tooltip.pasterdream.shadow_breath." + i));
            }
            tooltip.add(Component.translatable("tooltip.pasterdream.shadow_breath.hint"));
        }
    }

    /** 按住 Shift 时展示基于玩家当前理智的实时加成。 */
    private static void addCurrentBonusTooltip(List<Component> tooltip) {
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
            int damageTier = getDamageTier(ratio);
            int armorTier = getArmorTier(ratio);
            int regenTier = getRegenTier(ratio);

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
                        REGEN_LEVELS[regenTier]));
                hasBonus = true;
            }
            if (!hasBonus) {
                tooltip.add(Component.translatable("tooltip.pasterdream.shadow_breath.current.none"));
            }
        });
    }
}