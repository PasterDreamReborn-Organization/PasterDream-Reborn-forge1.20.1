package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.client.renderer.FluffyWindAlloyArmorRenderer;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModArmorMaterials;
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
 * 萦风合金盔甲 —— 风之旅途毕业护甲（与暗影合金同档，由 BOSS 材料合成）。
 *
 * <p>四个部位共用 {@code geo/fluffy_wind_alloy_armor.geo.json} 一个模型，
 * 由 {@link GeoArmorRenderer} 按装备槽（HEAD/CHEST/LEGS/FEET）
 * 与约定骨骼名（armorHead / armorBody / armorRightArm / armorLeftArm /
 * armorRightLeg / armorLeftLeg / armorRightBoot / armorLeftBoot）自动裁剪显示。
 *
 * <p>单件效果：移动速度 +5%、战技冷却 -5%（属性修正）。
 * 满套套装效果「御风之誓」由 {@code FluffyWindAlloyArmorHandler} 结算：
 * 拉弓/弩装填时间 -25%（经 {@link ModAttributes#RANGED_CHARGE_TIME_REDUCTION} 属性提供）、
 * 免疫雷电、非晴朗天气单件增益翻倍、雨天/雷雨最终减伤、
 * 50% 弹开弹射物、下落按住空格缓降（消耗饥饿度）。
 */
public class FluffyWindAlloyArmorItem extends ArmorItem implements GeoItem {

    /** 单件移动速度加成（MULTIPLY_TOTAL） */
    public static final double MOVE_SPEED_PER_PIECE = 0.05;
    /** 单件战技冷却缩减（ADDITION，正数 = 减 CD） */
    public static final double SKILL_COOLDOWN_PER_PIECE = 0.05;

    private static final UUID HELMET_SPEED_UUID = UUID.fromString("f1e2d3c4-0001-4b01-8a01-000000000001");
    private static final UUID CHEST_SPEED_UUID = UUID.fromString("f1e2d3c4-0002-4b01-8a01-000000000002");
    private static final UUID LEGS_SPEED_UUID = UUID.fromString("f1e2d3c4-0003-4b01-8a01-000000000003");
    private static final UUID BOOTS_SPEED_UUID = UUID.fromString("f1e2d3c4-0004-4b01-8a01-000000000004");

    private static final UUID HELMET_CD_UUID = UUID.fromString("f1e2d3c4-0005-4b01-8a01-000000000005");
    private static final UUID CHEST_CD_UUID = UUID.fromString("f1e2d3c4-0006-4b01-8a01-000000000006");
    private static final UUID LEGS_CD_UUID = UUID.fromString("f1e2d3c4-0007-4b01-8a01-000000000007");
    private static final UUID BOOTS_CD_UUID = UUID.fromString("f1e2d3c4-0008-4b01-8a01-000000000008");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FluffyWindAlloyArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties.fireResistant());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(getDefaultAttributeModifiers(slot));
        if (slot == this.type.getSlot()) {
            builder.put(Attributes.MOVEMENT_SPEED,
                    new AttributeModifier(getSpeedUuid(slot),
                            "pasterdream.fluffy_wind_alloy_armor.speed", MOVE_SPEED_PER_PIECE,
                            AttributeModifier.Operation.MULTIPLY_TOTAL));
            builder.put(ModAttributes.SKILL_COOLDOWN_REDUCTION.get(),
                    new AttributeModifier(getCooldownUuid(slot),
                            "pasterdream.fluffy_wind_alloy_armor.skill_cooldown_reduction", SKILL_COOLDOWN_PER_PIECE,
                            AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    private static UUID getSpeedUuid(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> HELMET_SPEED_UUID;
            case CHEST -> CHEST_SPEED_UUID;
            case LEGS -> LEGS_SPEED_UUID;
            case FEET -> BOOTS_SPEED_UUID;
            default -> throw new IllegalArgumentException("Unexpected armor slot: " + slot);
        };
    }

    private static UUID getCooldownUuid(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> HELMET_CD_UUID;
            case CHEST -> CHEST_CD_UUID;
            case LEGS -> LEGS_CD_UUID;
            case FEET -> BOOTS_CD_UUID;
            default -> throw new IllegalArgumentException("Unexpected armor slot: " + slot);
        };
    }

    /** 是否穿满一整套萦风合金盔甲。 */
    public static boolean hasFullSet(LivingEntity entity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.isArmor()) {
                ItemStack stack = entity.getItemBySlot(slot);
                if (!(stack.getItem() instanceof ArmorItem armorItem
                        && armorItem.getMaterial() == ModArmorMaterials.FLUFFY_WIND_ALLOY)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        for (int i = 1; i <= 8; i++) {
            tooltip.add(Component.translatable("tooltip.pasterdreammod.fluffy_wind_alloy_armor." + i));
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack,
                                                          EquipmentSlot slot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    this.renderer = new FluffyWindAlloyArmorRenderer();
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
