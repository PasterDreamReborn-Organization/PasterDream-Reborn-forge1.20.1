package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.client.renderer.MachineLightWingRenderer;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MeltDreamEnergySyncPacket;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class MachineLightWingItem extends ArmorItem implements GeoItem {

    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation FLY = RawAnimation.begin().thenLoop("fly");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MachineLightWingItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private MachineLightWingRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                     EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new MachineLightWingRenderer();
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.machine_light_wing.flight"));
        tooltip.add(Component.translatable("tooltip.pasterdream.machine_light_wing.energy"));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (level.isClientSide || !(entity instanceof ServerPlayer player)) return;
        if (player.getItemBySlot(EquipmentSlot.CHEST) != stack) return;

        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(energy -> {
            boolean free = !energy.getIsOrNotNeedConsumeDreamEnergy() || player.isCreative();
            if (free || energy.getMeltDreamEnergy() >= 0.02) {
                player.getAbilities().mayfly = true;
                if (player.tickCount % 20 == 0 && !free) {
                    energy.addMeltDreamEnergy(-0.02);
                    MeltDreamEnergySyncPacket.sendToPlayer(player, energy);
                }
            } else {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
            }
            player.onUpdateAbilities();
        });

        if (!player.onGround()) {
            stack.getOrCreateTag().putString("geckoAnim", "fly");
        }
    }

    /**
     * 由 {@code LivingEquipmentChangeEvent} 调用，卸下时回收飞行能力。
     */
    public static void onEquipChange(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || player.isCreative()) return;
        if (event.getSlot() != EquipmentSlot.CHEST) return;
        boolean wasEquipped = event.getFrom().getItem() instanceof MachineLightWingItem;
        boolean isEquipped = event.getTo().getItem() instanceof MachineLightWingItem;
        if (wasEquipped && !isEquipped) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, state -> {
            Entity entity = state.getData(software.bernie.geckolib.constant.DataTickets.ENTITY);
            if (entity != null && !entity.onGround()) {
                return state.setAndContinue(FLY);
            }
            return state.setAndContinue(IDLE);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
