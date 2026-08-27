package com.pasterdream.pasterdreammod.world.item.curio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.san.ISanModifier;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class SealOfTheCorruptedItem extends Item implements ICurioItem, ISanModifier {

    private static final UUID ENTITY_REACH_UUID = UUID.fromString("b84e7f10-74e3-43f5-95f9-968877248549");
    private static final UUID BLOCK_REACH_UUID = UUID.fromString("f55792ba-17c1-43bb-86ac-ff805d06ce3c");
    private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("4b798e40-aac3-43a2-b93b-c927ec3a2c59");
    private static final ResourceLocation TALENT_SHADOW_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/talent_shadow");

    public SealOfTheCorruptedItem() {
        super(new Properties().stacksTo(1).rarity(ModRarities.EPIC));
    }

    @Override
    public boolean immuneToNegativeEffects() {
        return true;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity())
                    .map(handler -> handler.findFirstCurio(stack.getItem()).isEmpty())
                    .orElse(true);
        }
        return true;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (!(slotContext.entity() instanceof ServerPlayer player)) return;
        if (player.isCreative()) return;
        if (AdvancementHelper.isDone(player, TALENT_SHADOW_ADV)) return;
        player.displayClientMessage(Component.translatable("message.pasterdream.seal_of_the_corrupted.rejected"), true);
        clearEquippedCurio(player, stack.getItem());
        player.hurt(player.level().damageSources().generic(), 5.0F);
        if (player.level() instanceof ServerLevel serverLevel) {
            ItemEntity itemEntity = new IndestructibleItemEntity(serverLevel,
                    player.getX(), player.getY() + 0.5, player.getZ(), new ItemStack(stack.getItem()));
            itemEntity.setPickUpDelay(20);
            itemEntity.setDeltaMovement(0, 0.2, 0);
            serverLevel.addFreshEntity(itemEntity);
        }
    }

    private static void clearEquippedCurio(ServerPlayer player, Item item) {
        CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                handler.findFirstCurio(item).ifPresent(result ->
                        handler.setEquippedCurio(result.slotContext().identifier(),
                                result.slotContext().index(), ItemStack.EMPTY)
                )
        );
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                         UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(ForgeMod.ENTITY_REACH.get(),
                new AttributeModifier(ENTITY_REACH_UUID, "seal_of_the_corrupted.entity_reach",
                        1.0, AttributeModifier.Operation.ADDITION));
        modifiers.put(ForgeMod.BLOCK_REACH.get(),
                new AttributeModifier(BLOCK_REACH_UUID, "seal_of_the_corrupted.block_reach",
                        1.0, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(ATTACK_DAMAGE_UUID, "seal_of_the_corrupted.attack_damage",
                        2.0, AttributeModifier.Operation.ADDITION));
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.seal_of_the_corrupted.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.seal_of_the_corrupted.effect2"));
        list.add(Component.translatable("tooltip.pasterdream.seal_of_the_corrupted.effect3"));
        list.add(Component.translatable("tooltip.pasterdream.seal_of_the_corrupted.flavor"));
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
