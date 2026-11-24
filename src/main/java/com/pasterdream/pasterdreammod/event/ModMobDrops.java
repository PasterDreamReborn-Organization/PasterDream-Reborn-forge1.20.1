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

    /** 暗影生物额外掉落灯影之下笔记碎片的概率 */
    private static final float SHADOW_BROKEN_NOTE_DROP_CHANCE = 0.8F;

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
            double multiplier;
            if (event.getSource().getEntity() instanceof net.minecraft.world.entity.player.Player player) {
                multiplier = ShadowDifficultyHelper.getLootMultiplier(player);
            } else {
                multiplier = ShadowDifficultyHelper.getLootMultiplier(event.getEntity().level());
            }
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
            // 暗影生物额外掉落 1 个灯影之下笔记碎片（标签排除的实体除外；不受难度掉落倍率影响）
            if (!event.getEntity().getType().is(ModEntityTypeTags.LAMP_SHADOW_BROKEN_NOTE_DROP_EXCLUDED)
                    && event.getEntity().getRandom().nextFloat() < SHADOW_BROKEN_NOTE_DROP_CHANCE) {
                event.getDrops().add(new ItemEntity(
                        event.getEntity().level(),
                        event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                        new ItemStack(ModItems.LAMP_SHADOW_BROKEN_NOTE.get())));
            }
        }
    }
}
