package com.pasterdream.pasterdreammod.world.block.doll;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class SetDollInvincible
{
    @SubscribeEvent
    public static void setDollInvincible(EntityJoinLevelEvent event)
    {
        if (event.getEntity() instanceof ItemEntity itemEntity)
        {
            if (itemEntity.getItem().getItem() instanceof DollItem)
            {
                itemEntity.setInvulnerable(true);
                itemEntity.setUnlimitedLifetime();
            }
        }
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent event)
    {
        if (event.getEntity() instanceof ItemEntity itemEntity)
        {
            if (itemEntity.getItem().getItem() instanceof DollItem)
            {
                Level level = event.getLevel();
                if (itemEntity.getY() < level.getMinBuildHeight())
                {
                    if (!level.isClientSide)
                    {
                        ServerLevel overworld = level.getServer().getLevel(Level.OVERWORLD);
                        if (overworld != null)
                        {
                            ItemStack stack = itemEntity.getItem().copy();
                            ItemEntity newEntity = new ItemEntity(overworld, 0, 321, 0, stack);
                            newEntity.setInvulnerable(true);
                            newEntity.setUnlimitedLifetime();
                            newEntity.setPickUpDelay(40);
                            overworld.addFreshEntity(newEntity);
                        }
                    }
                }
            }
        }
    }
}
