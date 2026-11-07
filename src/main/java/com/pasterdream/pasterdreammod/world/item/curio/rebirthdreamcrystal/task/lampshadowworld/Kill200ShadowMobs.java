package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.lampshadowworld;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamSingleTask;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

public class Kill200ShadowMobs implements IPasterDreamSingleTask
{
    @Override
    public Component getTaskName()
    {
        return Component.translatable("task.pasterdream.击杀200个暗影生物");
    }

    @Override
    public boolean isTaskProgressShowWithIntOrDouble()
    {
        return true;
    }

    @Override
    public double getTaskProgress(Player player)
    {
        double totalKillMobCounts = 0;
        if(player instanceof ServerPlayer serverPlayer)
        {
            ITag<EntityType<?>> shadowMobTag = ForgeRegistries.ENTITY_TYPES.tags().getTag(TagKey.create(ForgeRegistries.Keys.ENTITY_TYPES, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_mob")));

            for (EntityType<?> entityType : shadowMobTag)
            {
                totalKillMobCounts += serverPlayer.getStats().getValue(Stats.ENTITY_KILLED, entityType);
            }
        }
        return totalKillMobCounts;
    }

    @Override
    public double getTaskMaxProgress(Player player)
    {
        return 200;
    }
}
