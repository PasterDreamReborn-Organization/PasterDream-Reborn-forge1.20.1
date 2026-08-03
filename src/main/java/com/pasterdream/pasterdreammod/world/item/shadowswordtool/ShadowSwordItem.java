package com.pasterdream.pasterdreammod.world.item.shadowswordtool;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

public class ShadowSwordItem extends SwordItem {

    private static final UUID SAN_MODIFIER_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");

    public ShadowSwordItem(Tier tier, int damage, float speed) {
        super(tier, damage, speed, new Properties().fireResistant().rarity(ModRarities.LEGENDARY));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(super.getAttributeModifiers(slot, stack));
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(ModAttributes.SAN_VARIABILITY.get(),
                    new AttributeModifier(SAN_MODIFIER_UUID, "pasterdream.shadowsword.san_variability",
                            -3.6, AttributeModifier.Operation.ADDITION));
            if (stack.getOrCreateTag().contains("sanRatio")) {
                double sanRatio = stack.getOrCreateTag().getDouble("sanRatio");
                builder.put(Attributes.ATTACK_SPEED,
                        new AttributeModifier(SAN_MODIFIER_UUID, "pasterdream.shadowsword.attack_speed",
                                0.5 * (1.0 - sanRatio), AttributeModifier.Operation.MULTIPLY_BASE));
                builder.put(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(SAN_MODIFIER_UUID, "pasterdream.shadowsword.attack_damage",
                                0.75 * (1.0 - sanRatio), AttributeModifier.Operation.MULTIPLY_BASE));
            }
        }
        return builder.build();
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(ModRarities.qualityTooltip(ModRarities.LEGENDARY));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.passive_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.desc2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.desc3"));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.flavor"));
    }

    @Override
    public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity) {
        boolean retval = super.onEntitySwing(itemstack, entity);
        if (entity instanceof Player player) {
            Level level = entity.level();
            if (player.isCreative()) {
                playShadowSwordSound(level, entity);
            } else if (player instanceof ServerPlayer sp) {
                if (!SanHelper.getIsSanEnabled(sp)) {
                    sp.displayClientMessage(
                            Component.translatable("message.pasterdream.shadow_sword.san_disabled"), false);
                    sp.hurt(sp.level().damageSources().fellOutOfWorld(), (float) (sp.getHealth() - 1));
                    sp.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 0));
                    return retval;
                }
                playShadowSwordSound(level, entity);
            }
            SkillCooldownHelper.applySharedCooldown(player, 40);
            if (player instanceof ServerPlayer sp
                    && SanHelper.getIsSanEnabled(sp)
                    && SanHelper.getPlayerSan(sp) <= 0) {
                sp.hurt(sp.level().damageSources().magic(), Math.max(0, sp.getHealth() - 1));
            }
        }
        return retval;
    }

    private void playShadowSwordSound(Level level, Entity entity) {
        if (!level.isClientSide()) {
            level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()),
                    ModSounds.SHADOW_SWORD.get(), SoundSource.PLAYERS, 1, 1);
        } else {
            level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(),
                    ModSounds.SHADOW_SWORD.get(), SoundSource.PLAYERS, 1, 1, false);
        }
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (!world.isClientSide && entity instanceof ServerPlayer sp) {
            if (SanHelper.getIsSanEnabled(sp)) {
                if (selected) {
                    double maxSan = SanHelper.getPlayerMaxSan(sp);
                    double newRatio = maxSan > 0.0 ? SanHelper.getPlayerSan(sp) / maxSan : 1.0;
                    boolean hasKey = itemstack.getOrCreateTag().contains("sanRatio");
                    if (!hasKey || Math.abs(newRatio - itemstack.getOrCreateTag().getDouble("sanRatio")) > 0.01) {
                        itemstack.getOrCreateTag().putDouble("sanRatio", newRatio);
                    }
                }
            } else {
                itemstack.getOrCreateTag().remove("sanRatio");
            }
        }
    }
}
