package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class QymButterflyStarHairpinItem extends Item implements ICurioItem {
    private static final int CLEAR_RANGE = 32;

    public QymButterflyStarHairpinItem() {
        super(new Item.Properties().stacksTo(1).fireResistant().rarity(ModRarities.MIRACLE));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        clearNearbyMobTargets(slotContext);
    }

    private void clearNearbyMobTargets(SlotContext slotContext) {
        var player = slotContext.entity();
        AABB range = new AABB(player.blockPosition()).inflate(CLEAR_RANGE);
        List<Mob> mobs = player.level().getEntitiesOfClass(Mob.class, range,
                m -> m.getTarget() == player && m.getLastHurtByMob() != player);
        for (Mob mob : mobs) {
            mob.setTarget(null);
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

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.qym_butterfly_star_hairpin.effect"));
        list.add(Component.translatable("tooltip.pasterdream.qym_butterfly_star_hairpin.lore"));
    }
}
