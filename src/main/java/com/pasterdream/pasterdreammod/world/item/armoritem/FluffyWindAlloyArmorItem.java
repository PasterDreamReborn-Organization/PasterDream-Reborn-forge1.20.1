package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.client.renderer.FluffyWindAlloyArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

/**
 * 萦风合金盔甲 —— 风之旅途装备（搬运占位，当前为空壳，仅用于验证 GEO 模型渲染）。
 *
 * <p>四个部位共用 {@code geo/fluffy_wind_alloy_armor.geo.json} 一个模型，
 * 由 {@link GeoArmorRenderer} 按装备槽（HEAD/CHEST/LEGS/FEET）
 * 与约定骨骼名（armorHead / armorBody / armorRightArm / armorLeftArm /
 * armorRightLeg / armorLeftLeg / armorRightBoot / armorLeftBoot）自动裁剪显示。
 *
 * <p>套装效果与属性加成待设计确定后补全。
 */
public class FluffyWindAlloyArmorItem extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FluffyWindAlloyArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
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
