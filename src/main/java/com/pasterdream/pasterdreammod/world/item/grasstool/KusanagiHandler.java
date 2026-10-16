package com.pasterdream.pasterdreammod.world.item.grasstool;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "pasterdream", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KusanagiHandler {

    /** 毒刺斩杀时读取发射武器上的抢夺等级。 */
    @SubscribeEvent
    public static void onLootingLevel(LootingLevelEvent event) {
        CompoundTag data = event.getEntity().getPersistentData();
        if (data.contains("pasterdream:stinger_execute_looting")) {
            event.setLootingLevel(data.getInt("pasterdream:stinger_execute_looting"));
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide) return;

        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof Player player)) return;

        ItemStack mainHand = player.getMainHandItem();
        ItemStack stack = null;
        if (mainHand.getItem() instanceof KusanagiItem) {
            stack = mainHand;
        } else {
            ItemStack offHand = player.getOffhandItem();
            if (offHand.getItem() instanceof KusanagiItem) {
                stack = offHand;
            }
        }

        if (stack == null) return;

        int killed = (int) stack.getOrCreateTag().getDouble("killed");
        stack.getOrCreateTag().putDouble("killed", killed + 1);
    }
}
