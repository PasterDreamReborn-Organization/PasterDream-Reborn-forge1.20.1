package com.pasterdream.pasterdreammod.mixin;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Mixin(ItemStack.class)
public class ItemStackGetAttributeModifiersMixin
{
    private static final UUID ATTACK_BOOST_UUID = UUID.nameUUIDFromBytes("pasterdream:attack_boost".getBytes(StandardCharsets.UTF_8));
    private static final UUID LUCK_BOOST_UUID = UUID.nameUUIDFromBytes("pasterdream:luck_boost".getBytes(StandardCharsets.UTF_8));

    @Inject(method = "getAttributeModifiers", at = @At("RETURN"), cancellable = true)
    private void addAttackBoostToAttributeModifiers(EquipmentSlot slot, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> cir)
    {
        if (slot != EquipmentSlot.MAINHAND)
        {
            return;
        }

        ItemStack stack = (ItemStack)(Object)this;
        CompoundTag tag = stack.getTag();

        if (tag != null)
        {
            Multimap<Attribute, AttributeModifier> map = cir.getReturnValue();
            Multimap<Attribute, AttributeModifier> newMap = HashMultimap.create(map);

            if (tag.contains("AttackBoost", Tag.TAG_DOUBLE))
            {
                double AttackBoost = tag.getDouble("AttackBoost");
                newMap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_BOOST_UUID, "AttackBoost", AttackBoost, AttributeModifier.Operation.ADDITION));
            }

            if (tag.contains("LuckBoost", Tag.TAG_DOUBLE))
            {
                double LuckBoost = tag.getDouble("LuckBoost");
                newMap.put(Attributes.LUCK, new AttributeModifier(LUCK_BOOST_UUID, "LuckBoost", LuckBoost, AttributeModifier.Operation.ADDITION));
            }

            cir.setReturnValue(newMap);
        }
    }
}
