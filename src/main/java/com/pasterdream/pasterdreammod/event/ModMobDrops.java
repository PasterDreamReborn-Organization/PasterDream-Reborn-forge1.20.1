package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.helper.ShadowDifficultyHelper;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.ArrayList;
import java.util.List;

public class ModMobDrops {

    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getEntity() instanceof Warden) {
            event.getDrops().add(new ItemEntity(
                    event.getEntity().level(),
                    event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                    new ItemStack(ModItems.SCULK_HEART.get())));
        }
        if (event.getEntity() instanceof ElderGuardian) {
            event.getDrops().add(new ItemEntity(
                    event.getEntity().level(),
                    event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                    new ItemStack(ModItems.ELDER_GUARDIAN_SCALE.get())));
        }
        if (event.getEntity().getType().is(ModEntityTypeTags.SHADOW_MOB)) {
            double multiplier = ShadowDifficultyHelper.getLootMultiplier(event.getEntity().level());
            if (multiplier > 1.0) {
                double extraChance = multiplier - 1.0;
                List<ItemEntity> extraDrops = new ArrayList<>();
                for (ItemEntity drop : event.getDrops()) {
                    if (event.getEntity().getRandom().nextDouble() < extraChance) {
                        ItemStack copy = drop.getItem().copy();
                        extraDrops.add(new ItemEntity(
                                event.getEntity().level(),
                                drop.getX(), drop.getY(), drop.getZ(),
                                copy));
                    }
                }
                event.getDrops().addAll(extraDrops);
            }
        }
    }
}
