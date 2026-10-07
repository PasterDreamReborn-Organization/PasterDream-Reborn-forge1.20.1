package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MeltDreamEnergySyncPacket;
import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * 机械光翼：背饰，佩戴后获得飞行能力，但持续消耗融梦能量。
 * 护甲数值：+7 护甲值，+1 护甲韧性。
 */
public class MachineLightWingItem extends Item implements GeoItem, ICurioItem {

    /** 飞行时每次结算消耗的融梦能量 */
    private static final double FLY_COST = 0.02;
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation FLY = RawAnimation.begin().thenLoop("fly");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MachineLightWingItem() {
        super(new Properties().stacksTo(1).rarity(ModRarities.MASTER));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.pasterdream.machine_light_wing.flight"));
        tooltip.add(Component.translatable("tooltip.pasterdream.machine_light_wing.energy"));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = ArrayListMultimap.create();
        modifiers.put(Attributes.ARMOR, new AttributeModifier(uuid, "Machine Light Wing armor bonus", 7.0, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Machine Light Wing toughness bonus", 1.0, AttributeModifier.Operation.ADDITION));
        return modifiers;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof ServerPlayer player)) return;

        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(energy -> {
            boolean free = !energy.getIsOrNotNeedConsumeDreamEnergy() || player.isCreative();
            double effectiveCost = MeltDreamEnergyHelper.getEffectiveConsumeCost(energy, FLY_COST);
            if (free || energy.getMeltDreamEnergy() >= effectiveCost) {
                player.getAbilities().mayfly = true;
                if (!free && player.getAbilities().flying && player.tickCount % 20 == 0) {
                    energy.addMeltDreamEnergy(-effectiveCost);
                    MeltDreamEnergySyncPacket.sendToPlayer(player, energy);
                }
            } else {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
            }
            player.onUpdateAbilities();
        });
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (!(slotContext.entity() instanceof ServerPlayer player)) return;
        if (player.isCreative() || player.isSpectator()) return;
        player.getAbilities().mayfly = false;
        player.getAbilities().flying = false;
        player.onUpdateAbilities();
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

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        var entity = new IndestructibleItemEntity(level, location.getX(), location.getY(), location.getZ(), stack);
        entity.setDefaultPickUpDelay();
        entity.setDeltaMovement(location.getDeltaMovement());
        return entity;
    }
}
