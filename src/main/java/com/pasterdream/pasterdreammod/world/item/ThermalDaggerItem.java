package com.pasterdream.pasterdreammod.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import java.util.List;
import java.util.UUID;

public class ThermalDaggerItem extends SwordItem {
    private static final UUID SWIM_SPEED_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    private final Multimap<Attribute, AttributeModifier> offhandModifiers;

    public ThermalDaggerItem(Properties properties) {
        super(ModToolTiers.THERMAL_DAGGER, 3, -2.3f, properties.fireResistant());
        AttributeModifier swimSpeed = new AttributeModifier(SWIM_SPEED_UUID, "Thermal Dagger swim speed", 0.5f,
                AttributeModifier.Operation.ADDITION);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(super.getDefaultAttributeModifiers(EquipmentSlot.MAINHAND));
        builder.put(ForgeMod.SWIM_SPEED.get(), swimSpeed);
        this.defaultModifiers = builder.build();
        this.offhandModifiers = ImmutableMultimap.of(ForgeMod.SWIM_SPEED.get(), swimSpeed);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return switch (slot) {
            case MAINHAND -> this.defaultModifiers;
            case OFFHAND -> this.offhandModifiers;
            default -> super.getAttributeModifiers(slot, stack);
        };
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean retval = super.hurtEnemy(stack, target, attacker);
        Level level = attacker.level();
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, BlockPos.containing(target.getX(), target.getY(), target.getZ()),
                    ModSounds.THERMAL_DAGGER.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            serverLevel.sendParticles(ParticleTypes.BUBBLE, target.getX(), target.getY(), target.getZ(),
                    64, 1.2, 1.5, 1.2, 0.15);
        }
        return retval;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.thermal_dagger.swim"));
        tooltip.add(Component.translatable("tooltip.pasterdream.thermal_dagger"));
    }
}
