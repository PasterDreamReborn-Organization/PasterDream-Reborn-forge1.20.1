package com.pasterdream.pasterdreammod.world.enchantment;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.IMeltDreamEnergy;
import com.pasterdream.pasterdreammod.init.ModEnchantment;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MeltDreamEnergySyncPacket;
import com.pasterdream.pasterdreammod.world.item.armoritem.MeltDreamCrystalArmorItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import top.theillusivec4.curios.api.CuriosApi;

/**
 * 「融梦修补」附魔处理器：携带（背包 / 护甲 / 饰品栏）且附有该附魔、有损伤的物品，
 * 每 10 tick 消耗玩家 0.01 融梦能量修复 1 点耐久；创造模式/免消耗标记不扣能量。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class MeltDreamRepairHandler {

    /** 每次修复 1 点耐久的融梦能量消耗 */
    private static final double REPAIR_COST = 0.01;
    /** 处理间隔（tick） */
    private static final int INTERVAL = 10;

    private MeltDreamRepairHandler() {
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) return;
        if (player.tickCount % INTERVAL != 0) return;

        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(energy -> {
            boolean free = !energy.getIsOrNotNeedConsumeDreamEnergy() || player.isCreative();
            boolean[] repaired = {false};

            for (ItemStack stack : player.getInventory().items) {
                if (repair(stack, energy, free)) repaired[0] = true;
            }
            for (ItemStack stack : player.getInventory().armor) {
                if (repair(stack, energy, free)) repaired[0] = true;
            }
            for (ItemStack stack : player.getInventory().offhand) {
                if (repair(stack, energy, free)) repaired[0] = true;
            }

            CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                    handler.getCurios().values().forEach(stacksHandler -> {
                        IItemHandler stacks = stacksHandler.getStacks();
                        for (int i = 0; i < stacks.getSlots(); i++) {
                            if (repair(stacks.getStackInSlot(i), energy, free)) repaired[0] = true;
                        }
                    }));

            if (repaired[0]) {
                MeltDreamEnergySyncPacket.sendToPlayer(player, energy);
            }
        });
    }

    /** 尝试消耗能量为单个物品修复 1 点耐久，返回是否发生修复。 */
    private static boolean repair(ItemStack stack, IMeltDreamEnergy energy, boolean free) {
        if (stack.isEmpty() || !stack.isDamageableItem() || stack.getDamageValue() < 1) return false;

        // 融梦水晶护甲由 MeltDreamCrystalArmorHandler 自行修补（满套半价），此处跳过避免重复扣费
        if (stack.getItem() instanceof MeltDreamCrystalArmorItem) return false;

        Enchantment enchantment = ModEnchantment.MELT_DREAM_REPAIR_ENCHANTMENT.get();
        if (stack.getEnchantmentLevel(enchantment) < 1) return false;

        if (!free) {
            if (energy.getMeltDreamEnergy() <= REPAIR_COST) return false;
            energy.addMeltDreamEnergy(-REPAIR_COST);
        }
        stack.setDamageValue(stack.getDamageValue() - 1);
        return true;
    }
}
