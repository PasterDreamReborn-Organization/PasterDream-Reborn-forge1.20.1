package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.client.renderer.ShadowAlloyArmorRenderer;
import com.pasterdream.pasterdreammod.world.item.ModArmorMaterials;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * 暗影合金盔甲 —— 灯影之下毕业装备（单件 +4 最大生命 / +1 攻击力），使用 GeckoLib GEO 模型渲染。
 *
 * <p>四个部位共用 {@code geo/shadow_alloy_armor.geo.json} 一个模型，
 * 由 {@link GeoArmorRenderer} 按装备槽（HEAD/CHEST/LEGS/FEET）
 * 与约定骨骼名（armorHead / armorBody / armorRightArm / armorLeftArm /
 * armorRightLeg / armorLeftLeg / armorRightBoot / armorLeftBoot）自动裁剪显示，
 * 无需每个部位单独建模型或单独做动画。
 *
 * <p>套装效果「黑暗守望」由 {@code ShadowAlloyArmorHandler} 在事件中按满套结算。
 */
public class ShadowAlloyArmorItem extends ArmorItem implements GeoItem {

    /** 单件最大生命值加成 */
    public static final double HEALTH_PER_PIECE = 4.0;
    /** 单件攻击力加成 */
    public static final double ATTACK_PER_PIECE = 1.0;

    private static final UUID HEAD_HEALTH_MODIFIER_UUID = UUID.fromString("1a2b3c4d-5e6f-4a1b-8c2d-3e4f5a6b7c8d");
    private static final UUID CHEST_HEALTH_MODIFIER_UUID = UUID.fromString("2b3c4d5e-6f7a-4b2c-9d3e-4f5a6b7c8d9e");
    private static final UUID LEGS_HEALTH_MODIFIER_UUID = UUID.fromString("3c4d5e6f-7a8b-4c3d-ae4f-5a6b7c8d9e0f");
    private static final UUID FEET_HEALTH_MODIFIER_UUID = UUID.fromString("4d5e6f7a-8b9c-4d4e-bf5a-6b7c8d9e0f1a");

    private static final UUID HEAD_ATTACK_MODIFIER_UUID = UUID.fromString("5e6f7a8b-9c0d-4e5f-ca6b-7c8d9e0f1a2b");
    private static final UUID CHEST_ATTACK_MODIFIER_UUID = UUID.fromString("6f7a8b9c-0d1e-4f60-db7c-8d9e0f1a2b3c");
    private static final UUID LEGS_ATTACK_MODIFIER_UUID = UUID.fromString("7a8b9c0d-1e2f-4061-ec8d-9e0f1a2b3c4d");
    private static final UUID FEET_ATTACK_MODIFIER_UUID = UUID.fromString("8b9c0d1e-2f3a-4162-fd9e-0f1a2b3c4d5e");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ShadowAlloyArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties.fireResistant());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(getDefaultAttributeModifiers(slot));
        if (slot == this.type.getSlot()) {
            builder.put(Attributes.MAX_HEALTH,
                    new AttributeModifier(getHealthModifierUuid(slot),
                            "pasterdream.shadow_alloy_armor.health", HEALTH_PER_PIECE,
                            AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(getAttackModifierUuid(slot),
                            "pasterdream.shadow_alloy_armor.attack", ATTACK_PER_PIECE,
                            AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    private static UUID getHealthModifierUuid(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> HEAD_HEALTH_MODIFIER_UUID;
            case CHEST -> CHEST_HEALTH_MODIFIER_UUID;
            case LEGS -> LEGS_HEALTH_MODIFIER_UUID;
            case FEET -> FEET_HEALTH_MODIFIER_UUID;
            default -> throw new IllegalArgumentException("Unexpected armor slot: " + slot);
        };
    }

    private static UUID getAttackModifierUuid(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> HEAD_ATTACK_MODIFIER_UUID;
            case CHEST -> CHEST_ATTACK_MODIFIER_UUID;
            case LEGS -> LEGS_ATTACK_MODIFIER_UUID;
            case FEET -> FEET_ATTACK_MODIFIER_UUID;
            default -> throw new IllegalArgumentException("Unexpected armor slot: " + slot);
        };
    }

    /** 是否穿满一整套暗影合金盔甲。 */
    public static boolean hasFullSet(LivingEntity entity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.isArmor()) {
                ItemStack stack = entity.getItemBySlot(slot);
                if (!(stack.getItem() instanceof ArmorItem armorItem
                        && armorItem.getMaterial() == ModArmorMaterials.SHADOW_ALLOY)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_armor.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_armor.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_armor.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_armor.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_armor.5"));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack,
                                                          EquipmentSlot slot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    this.renderer = new ShadowAlloyArmorRenderer();
                }
                this.renderer.prepForRender(entity, stack, slot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
