package com.pasterdream.pasterdreammod.world.enchantment;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

/**
 * 附魔效果处理器。
 * <ul>
 *     <li>疾风连击：剑每级 +6% 攻击速度、斧每级 +4%（MULTIPLY_BASE，最高 4 级）。</li>
 *     <li>聚梦：护甲每级 +0.25 融梦光环（{@code MELT_DREAM_VARIABILITY}，单位/分钟），等级不裁剪。</li>
 *     <li>庇护：每级 -2% 受到的伤害（全身护甲叠加；超过 12 级用反比例函数限制减伤上限）。</li>
 * </ul>
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class EnchantmentHandler {

    private static final UUID SWIFT_STRIKE_ATTACK_SPEED_UUID = UUID.fromString("bdf05f70-b53d-4828-8e37-9a502bde0ec1");

    /** 聚梦：按护甲槽位区分 UUID 以支持多件叠加 */
    private static final UUID GATHER_DREAM_AURA_HEAD_UUID = UUID.fromString("1a2b3c4d-5e6f-4a7b-8c9d-0e1f2a3b4c5d");
    private static final UUID GATHER_DREAM_AURA_CHEST_UUID = UUID.fromString("2b3c4d5e-6f7a-4b8c-9d0e-1f2a3b4c5d6e");
    private static final UUID GATHER_DREAM_AURA_LEGS_UUID = UUID.fromString("3c4d5e6f-7a8b-4c9d-0e1f-2a3b4c5d6e7f");
    private static final UUID GATHER_DREAM_AURA_FEET_UUID = UUID.fromString("4d5e6f7a-8b9c-4d0e-9f1a-2b3c4d5e6f7a");

    private EnchantmentHandler() {
    }

    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        if (event.getSlotType().getType() == EquipmentSlot.Type.ARMOR) {
            addGatherDreamAura(event);
            return;
        }
        if (event.getSlotType() != EquipmentSlot.MAINHAND) return;
        addSwiftStrike(event);
    }

    /** 疾风连击：剑每级 +6%、斧每级 +4% 攻击速度。 */
    private static void addSwiftStrike(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        int level = stack.getEnchantmentLevel(ModEnchantment.SWIFT_STRIKE_ENCHANTMENT.get());
        if (level <= 0) return;
        double multiplier = stack.getItem() instanceof AxeItem ? 0.04 : 0.06;
        event.addModifier(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(
                        SWIFT_STRIKE_ATTACK_SPEED_UUID,
                        "Swift Strike attack speed bonus",
                        level * multiplier,
                        AttributeModifier.Operation.MULTIPLY_BASE
                )
        );
    }

    /** 聚梦：护甲每级 +0.25 融梦光环；等级不裁剪，>5 级仍然生效。 */
    private static void addGatherDreamAura(ItemAttributeModifierEvent event) {
        if (!(event.getItemStack().getItem() instanceof ArmorItem armorItem)) return;

        // 同件护甲会被查询所有护甲槽位（如护腿的工具提示），只在其实际装备槽生成修饰器
        EquipmentSlot itemSlot = armorItem.getEquipmentSlot();
        if (event.getSlotType() != itemSlot) return;

        int level = event.getItemStack().getEnchantmentLevel(ModEnchantment.DREAM_GATHERING_ENCHANTMENT.get());
        if (level <= 0) return;
        event.addModifier(
                ModAttributes.MELT_DREAM_VARIABILITY.get(),
                new AttributeModifier(
                        gatherDreamAuraUuid(itemSlot),
                        "Dream Gathering melt dream aura",
                        level * 0.25,
                        AttributeModifier.Operation.ADDITION
                )
        );
    }

    private static UUID gatherDreamAuraUuid(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> GATHER_DREAM_AURA_HEAD_UUID;
            case CHEST -> GATHER_DREAM_AURA_CHEST_UUID;
            case LEGS -> GATHER_DREAM_AURA_LEGS_UUID;
            case FEET -> GATHER_DREAM_AURA_FEET_UUID;
            default -> throw new IllegalArgumentException("Unexpected armor slot: " + slot);
        };
    }

    /** 庇护：每级 -2% 受到的伤害（全身护甲叠加）。 */
    @SubscribeEvent
    public static void onShelterLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();

        var shelter = ModEnchantment.SHELTER_ENCHANTMENT.get();

        int totalLevel = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack armor = entity.getItemBySlot(slot);
            if (!armor.isEmpty()) {
                totalLevel += armor.getEnchantmentLevel(shelter);
            }
        }

        // 超过12级时，限制减伤上限，使用反比例函数，减伤理论上限不会超过25%
        if (totalLevel > 0) {
            if (totalLevel <= 12) {
                event.setAmount(event.getAmount() * (1.0f - totalLevel * 0.02f));
            } else {
                double dmgReduce = 25 - 1.2 / (totalLevel - 10.8);
                event.setAmount(event.getAmount() * (1.0f - (float) (dmgReduce * 0.01)));
            }
        }
    }
}
