package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.TerraBladeItem;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class TerraFloatingIslandItem extends Item implements ICurioItem {
    public TerraFloatingIslandItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.terra_floating_island.desc1"));
        list.add(Component.translatable("tooltip.pasterdream.terra_floating_island.desc2"));
        list.add(Component.translatable("tooltip.pasterdream.terra_floating_island.desc3"));
        list.add(Component.translatable("tooltip.pasterdream.terra_floating_island.desc4"));
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // Wave damage is scaled at launch and has the wave as its direct source.
        if (!event.getEntity().level().isClientSide()
                && event.getSource().is(DamageTypes.PLAYER_ATTACK)
                && event.getSource().getEntity() instanceof Player player
                && event.getSource().getDirectEntity() == player
                && player.getMainHandItem().getItem() instanceof TerraBladeItem
                && CuriosApi.getCuriosInventory(player)
                        .map(handler -> handler.findFirstCurio(ModItems.TERRA_FLOATING_ISLAND.get()).isPresent())
                        .orElse(false)) {
            event.setAmount(event.getAmount() * 1.3F);
        }
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
