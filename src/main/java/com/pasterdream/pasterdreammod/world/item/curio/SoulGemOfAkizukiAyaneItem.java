package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class SoulGemOfAkizukiAyaneItem extends Item implements ICurioItem {

    private static final double ENERGY_PER_SEC = 1.5;
    private static final double FRAGILE_ENERGY_THRESHOLD = 30.0;
    private static final int NO_CONSUME_TICKS = 2400; // 2 分钟
    private static final int ACTIVATION_COOLDOWN_TICKS = 3600; // 3 分钟

    private static final UUID SKILL_DAMAGE_UUID = UUID.fromString("c8f0a1b2-3c4d-4e5f-6a7b-8c9d0e1f2a3b");
    private static final UUID MAGIC_DAMAGE_UUID = UUID.fromString("d9e1b2c3-4d5e-6f7a-8b9c-0d1e2f3a4b5c");

    public SoulGemOfAkizukiAyaneItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.MIRACLE));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof ServerPlayer player)) return;
        if (player.tickCount % 20 != 0) return;
        MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(player, ENERGY_PER_SEC);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity == null || entity.level().isClientSide()) return;

        AttributeInstance skillDmg = entity.getAttribute(ModAttributes.SKILL_DAMAGE_RATE.get());
        if (skillDmg != null && skillDmg.getModifier(SKILL_DAMAGE_UUID) == null) {
            skillDmg.addPermanentModifier(new AttributeModifier(SKILL_DAMAGE_UUID,
                    "Soul Gem of Akizuki Ayane skill damage", 0.4, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        AttributeInstance magicDmg = entity.getAttribute(ModAttributes.MAGIC_DAMAGE_RATE.get());
        if (magicDmg != null && magicDmg.getModifier(MAGIC_DAMAGE_UUID) == null) {
            magicDmg.addPermanentModifier(new AttributeModifier(MAGIC_DAMAGE_UUID,
                    "Soul Gem of Akizuki Ayane magic damage", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        if (entity instanceof ServerPlayer sp) {
            MeltDreamEnergyHelper.setPlayerMeltDreamEnergyConsumeDoubled(sp, true);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity == null || entity.level().isClientSide()) return;

        AttributeInstance skillDmg = entity.getAttribute(ModAttributes.SKILL_DAMAGE_RATE.get());
        if (skillDmg != null) {
            skillDmg.removeModifier(SKILL_DAMAGE_UUID);
        }
        AttributeInstance magicDmg = entity.getAttribute(ModAttributes.MAGIC_DAMAGE_RATE.get());
        if (magicDmg != null) {
            magicDmg.removeModifier(MAGIC_DAMAGE_UUID);
        }
        if (entity instanceof ServerPlayer sp) {
            MeltDreamEnergyHelper.setPlayerMeltDreamEnergyConsumeDoubled(sp, false);
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity()).map(handler ->
                            handler.findFirstCurio(stack.getItem()).isEmpty())
                    .orElse(true);
        }
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!player.isShiftKeyDown()) {
            return InteractionResultHolder.pass(stack);
        }

        if (!level.isClientSide() && player instanceof ServerPlayer sp) {
            if (player.getCooldowns().isOnCooldown(this)) {
                player.displayClientMessage(
                        Component.translatable("tooltip.pasterdream.soul_gem_of_akizuki_ayane.cooldown"), true);
                return InteractionResultHolder.fail(stack);
            }

            MeltDreamEnergyHelper.setPlayerMeltDreamEnergyAndSync(sp,
                    MeltDreamEnergyHelper.getPlayerMaxMeltDreamEnergy(sp));
            MeltDreamEnergyHelper.setPlayerMeltDreamEnergyIsNeed(sp, false);
            sp.getPersistentData().putInt("pasterdream.soul_gem_no_consume_ticks", NO_CONSUME_TICKS);
            player.getCooldowns().addCooldown(this, ACTIVATION_COOLDOWN_TICKS);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    public static boolean isWearing(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.SOUL_GEM_OF_AKIZUKI_AYANE.get()).isPresent())
                .orElse(false);
    }

    public static boolean isFragile(ServerPlayer player) {
        return MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player) < FRAGILE_ENERGY_THRESHOLD;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.soul_gem_of_akizuki_ayane.1"));
        list.add(Component.translatable("tooltip.pasterdream.soul_gem_of_akizuki_ayane.2"));
        list.add(Component.translatable("tooltip.pasterdream.soul_gem_of_akizuki_ayane.3"));
        list.add(Component.translatable("tooltip.pasterdream.soul_gem_of_akizuki_ayane.4"));
        list.add(Component.translatable("tooltip.pasterdream.soul_gem_of_akizuki_ayane.5"));
        list.add(Component.translatable("tooltip.pasterdream.soul_gem_of_akizuki_ayane.6"));
        list.add(Component.translatable("tooltip.pasterdream.soul_gem_of_akizuki_ayane.7"));
        list.add(Component.translatable("tooltip.pasterdream.soul_gem_of_akizuki_ayane.8"));
        list.add(Component.translatable("tooltip.pasterdream.soul_gem_of_akizuki_ayane.9"));
        list.add(Component.translatable("tooltip.pasterdream.soul_gem_of_akizuki_ayane.10"));
    }
}
