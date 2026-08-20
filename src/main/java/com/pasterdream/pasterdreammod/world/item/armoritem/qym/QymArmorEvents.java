package com.pasterdream.pasterdreammod.world.item.armoritem.qym;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;

public class QymArmorEvents {

    public static void onEquipChange(LivingEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof ServerPlayer pl)) return;

        if (event.getSlot() == EquipmentSlot.CHEST && !pl.isCreative()) {
            boolean wasEquipped = event.getFrom().getItem() instanceof QymWindShirtItem;
            boolean isEquipped = event.getTo().getItem() instanceof QymWindShirtItem;
            if (wasEquipped != isEquipped) {
                pl.getAbilities().mayfly = isEquipped;
                if (!isEquipped) {
                    pl.getAbilities().flying = false;
                }
                pl.onUpdateAbilities();
            }
        }

        if (event.getSlot() == EquipmentSlot.LEGS) {
            boolean wasEquipped = event.getFrom().getItem() instanceof QymSwayingSkirtItem;
            boolean isEquipped = event.getTo().getItem() instanceof QymSwayingSkirtItem;
            if (wasEquipped != isEquipped) {
                MeltDreamEnergyHelper.setPlayerMeltDreamEnergyIsNeed(pl, !isEquipped);
                if (isEquipped) {
                    MeltDreamEnergyHelper.setPlayerMeltDreamEnergyAndSync(pl,
                            MeltDreamEnergyHelper.getPlayerMaxMeltDreamEnergyEffective(pl));
                }
            }
        }
    }
}
