package com.pasterdream.pasterdreammod.world.enchantment;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModEnchantment;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 「噬心诅咒」效果处理器：玩家受伤时按原始伤害扣除等量理智，每件附带诅咒的护甲各自叠加。
 * <p>
 * 使用 {@link EventPriority#HIGHEST} 在其它伤害修正（如「庇护」）之前读取原始伤害值。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class HeartDevourCurseHandler {

    private HeartDevourCurseHandler() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        Enchantment curse = ModEnchantment.HEART_DEVOUR_ENCHANTMENT.get();

        int totalLevel = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack armor = player.getItemBySlot(slot);
            if (!armor.isEmpty()) {
                totalLevel += armor.getEnchantmentLevel(curse);
            }
        }

        if (totalLevel <= 0) return;

        double sanLoss = event.getAmount() * totalLevel;
        if (sanLoss <= 0) return;

        SanHelper.addPlayerSanAndSync(player, -sanLoss);
    }
}
