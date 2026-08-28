package com.pasterdream.pasterdreammod.world.item.curio;

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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class BroochOfWhiteOrchidItem extends Item implements ICurioItem, ISanModifier {

    public BroochOfWhiteOrchidItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.EPIC));
    }

    private static final ResourceLocation TALENT_LIGHT_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/talent_light");

    @Override
    public double modifyEnvRate(double envRate) {
        return Math.max(envRate, 0.0);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (!(slotContext.entity() instanceof ServerPlayer player)) return;
        if (player.isCreative()) return;
        if (AdvancementHelper.isDone(player, TALENT_LIGHT_ADV)) return;
        player.displayClientMessage(Component.translatable("message.pasterdream.brooch_of_white_orchid.rejected"), true);
        clearEquippedCurio(player, stack.getItem());
        player.hurt(player.level().damageSources().generic(), 1.0F);
        if (player.level() instanceof ServerLevel serverLevel) {
            ItemEntity itemEntity = new IndestructibleItemEntity(serverLevel,
                    player.getX(), player.getY() + 0.5, player.getZ(), new ItemStack(stack.getItem()));
            itemEntity.setPickUpDelay(40);
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
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.brooch_of_white_orchid.effect"));
        list.add(Component.translatable("tooltip.pasterdream.brooch_of_white_orchid.effect2"));
        list.add(Component.translatable("tooltip.pasterdream.brooch_of_white_orchid.effect3"));
        list.add(Component.translatable("tooltip.pasterdream.brooch_of_white_orchid.flavor"));
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
