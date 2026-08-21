package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class RebirthDreamCrystalItem extends Item implements ICurioItem {

    private static final UUID SAN_VARIABILITY_UUID = UUID.fromString("7d4c9a2e-5b1f-4c8d-9e3a-1f6b2c8d4a10");

    public RebirthDreamCrystalItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.MIRACLE));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.isShiftKeyDown()) {
            return super.use(level, player, hand);
        }

        if (!level.isClientSide()) {
            List<Item> loot = Config.getRebirthDreamCrystalLoot();
            if (!loot.isEmpty()) {
                Item picked = loot.get(player.getRandom().nextInt(loot.size()));
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(picked));
            }
            level.playSound(null, player.blockPosition(), ModSounds.DING_0.get(), SoundSource.PLAYERS, 1, 1);
        }

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var sanAttr = entity.getAttribute(ModAttributes.SAN_VARIABILITY.get());
            if (sanAttr != null && sanAttr.getModifier(SAN_VARIABILITY_UUID) == null) {
                sanAttr.addPermanentModifier(new AttributeModifier(SAN_VARIABILITY_UUID, "Rebirth dream crystal sanity variability", 12.0, AttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var sanAttr = entity.getAttribute(ModAttributes.SAN_VARIABILITY.get());
            if (sanAttr != null) {
                sanAttr.removeModifier(SAN_VARIABILITY_UUID);
            }
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof ServerPlayer player)) return;
        if (player.tickCount % 200 != 0) return;
        player.addEffect(new MobEffectInstance(ModEffects.EVASION.get(), 240, 0, false, false));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity()).map(handler ->
                            handler.findFirstCurio(this).isEmpty())
                    .orElse(true);
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.1"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.2"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.3"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.4"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.5"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.6"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.7"));
    }

}
