package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.client.renderer.WindRunnerCloakRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
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

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

/**
 * 风行者披风 —— 胸甲槽装备的 GEO 模型披风。
 *
 * <ul>
 *   <li>装备后获得鞘翅飞行能力（覆写 {@link #canElytraFly}/{@link #elytraFlightTick}）；</li>
 *   <li>飞行中按住空格助力加速，无冷却、无消耗（服务端 {@link WindRunnerCloakHandler} 结算）；</li>
 *   <li>由 {@link WindRunnerCloakRenderer} 按原版 {@code CapeLayer} 公式实时驱动 {@code body1} 骨骼飘动。</li>
 * </ul>
 */
public class WindRunnerCloakItem extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public WindRunnerCloakItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    /** 装备于胸甲槽时，无需额外鞘翅即可开始鞘翅飞行。 */
    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return true;
    }

    /** 持续鞘翅飞行，不消耗耐久。 */
    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.wind_runner_cloak.flight"));
        tooltip.add(Component.translatable("tooltip.pasterdream.wind_runner_cloak.boost"));
        tooltip.add(Component.translatable("tooltip.pasterdream.wind_runner_cloak.kinetic"));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack,
                                                          EquipmentSlot slot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    this.renderer = new WindRunnerCloakRenderer();
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
