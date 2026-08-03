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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

public class ShadowSwordItem extends SwordItem {

    private static final UUID SAN_MODIFIER_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    private static final String NIGHTMARE_SLASH_TAG = "pasterdream:nightmare_slash";
    private static final double SKILL_SAN_COST = 5.0;
    private static final float SKILL_HP_COST = 5.0F;
    private static final int SKILL_COOLDOWN_TICKS = 40;

    public ShadowSwordItem(Tier tier, int damage, float speed) {
        super(tier, damage, speed, new Properties().fireResistant().rarity(ModRarities.LEGENDARY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide() && player instanceof ServerPlayer sp) {
            if (player.getCooldowns().isOnCooldown(stack.getItem())) {
                return InteractionResultHolder.fail(stack);
            }
            if (!player.isCreative()) {
                if (SanHelper.getIsSanEnabled(sp)) {
                    double currentSan = SanHelper.getPlayerSan(sp);
                    if (currentSan >= SKILL_SAN_COST) {
                        SanHelper.addPlayerSanAndSync(sp, -SKILL_SAN_COST);
                    } else {
                        float newHealth = Math.max(1.0F, player.getHealth() - SKILL_HP_COST);
                        player.setHealth(newHealth);
                    }
                } else {
                    float newHealth = Math.max(1.0F, player.getHealth() - SKILL_HP_COST);
                    player.setHealth(newHealth);
                }
            }
            player.getPersistentData().putBoolean(NIGHTMARE_SLASH_TAG, true);
            SkillCooldownHelper.applySharedCooldown(player, SKILL_COOLDOWN_TICKS);
            level.playSound(null, BlockPos.containing(player.getX(), player.getY(), player.getZ()),
                    SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 0.7f, 1.2f);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
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
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.skill_desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.skill_desc2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.skill_desc3"));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.passive_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.desc2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.desc3"));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_sword.flavor"));
    }

    @Override
    public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity) {
        boolean retval = super.onEntitySwing(itemstack, entity);
        if (entity instanceof ServerPlayer sp) {
            if (!sp.isCreative() && !SanHelper.getIsSanEnabled(sp)) {
                sp.displayClientMessage(
                        Component.translatable("message.pasterdream.shadow_sword.san_disabled"), false);
                sp.hurt(sp.level().damageSources().fellOutOfWorld(), (float) (sp.getHealth() - 1));
                sp.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 0));
                return retval;
            }
            if (!sp.isCreative() && SanHelper.getPlayerSan(sp) <= 0) {
                sp.hurt(sp.level().damageSources().magic(), Math.max(0, sp.getHealth() - 1));
            }
        }
        return retval;
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

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class NightmareSlashHandler {
        private static final String APPLYING_TAG = "pasterdream:nightmare_slash_applying";

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (!(event.getSource().getEntity() instanceof Player player)) return;
            if (!(player.getMainHandItem().getItem() instanceof ShadowSwordItem)) return;
            if (!player.getPersistentData().getBoolean(NIGHTMARE_SLASH_TAG)) return;
            if (player.getPersistentData().getBoolean(APPLYING_TAG)) return;

            player.getPersistentData().remove(NIGHTMARE_SLASH_TAG);
            player.getPersistentData().putBoolean(APPLYING_TAG, true);

            ItemStack sword = player.getMainHandItem();
            double sanRatio = sword.getOrCreateTag().contains("sanRatio")
                    ? sword.getOrCreateTag().getDouble("sanRatio") : 1.0;
            float baseDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float enchantBonus = EnchantmentHelper.getDamageBonus(sword, event.getEntity().getMobType());
            float effectiveAttack = baseDamage + enchantBonus;
            float critMultiplier = effectiveAttack > 0 ? event.getAmount() / effectiveAttack : 1.0f;
            if (critMultiplier < 1.3f) critMultiplier = 1.0f;
            else critMultiplier = 1.5f;
            float magicDamage = effectiveAttack * (float) (2.0 - sanRatio) * critMultiplier;

            Level level = player.level();
            event.setCanceled(true);
            event.getEntity().invulnerableTime = 0;
            event.getEntity().hurt(level.damageSources().magic(), magicDamage);

            level.playSound(null, BlockPos.containing(player.getX(), player.getY(), player.getZ()),
                    ModSounds.SHADOW_SWORD.get(), SoundSource.PLAYERS, 1.0f, 1.0f);

            int sweepingLevel = sword.getEnchantmentLevel(Enchantments.SWEEPING_EDGE);
            if (sweepingLevel > 0) {
                float sweepRatio = 1.0F - 1.0F / (float) (sweepingLevel + 1);
                float sweepDamage = magicDamage * sweepRatio;
                AABB area = event.getEntity().getBoundingBox().inflate(1.5, 0.5, 1.5);
                List<LivingEntity> nearby = level.getEntitiesOfClass(LivingEntity.class, area,
                        e -> e != player && e != event.getEntity() && e.isAlive());
                for (LivingEntity target : nearby) {
                    target.invulnerableTime = 0;
                    target.hurt(level.damageSources().magic(), sweepDamage);
                }
            }

            player.getPersistentData().remove(APPLYING_TAG);
        }
    }
}
