package com.pasterdream.pasterdreammod.helper.itemwithnbt.spawneggwithnbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class GetSpawnEgg
{
    public static ItemStack getSpawnEgg(Entity entity)
    {
        if(entity != null)
        {
            if(entity instanceof LivingEntity)
            {
                SpawnEggItem spawnEgg = ForgeSpawnEggItem.fromEntityType(entity.getType());
                if(spawnEgg != null) {
                    ItemStack itemStack = new ItemStack(spawnEgg);

                    CompoundTag entityTag = new CompoundTag();
                    entity.saveWithoutId(entityTag);

                    entityTag.remove("Pos");
                    entityTag.remove("Motion");
                    entityTag.remove("Rotation");
                    entityTag.remove("FallDistance");
                    entityTag.remove("PortalCooldown");
                    entityTag.remove("HurtTime");
                    entityTag.remove("HurtByTimestamp");
                    entityTag.remove("Fire");
                    entityTag.remove("UUID");

                    if (entityTag.contains("Attributes", Tag.TAG_LIST)) {
                        ListTag attributes = entityTag.getList("Attributes", Tag.TAG_COMPOUND);
                        for (int i = 0; i < attributes.size(); i++) {
                            CompoundTag attribute = attributes.getCompound(i);
                            if (attribute.contains("Modifiers", Tag.TAG_LIST)) {
                                ListTag modifiers = attribute.getList("Modifiers", Tag.TAG_COMPOUND);
                                modifiers.removeIf(tag ->
                                {
                                    if (tag instanceof CompoundTag modifier) {
                                        "Random spawn bonus".equals(modifier.getString("Name"));
                                    }
                                    return false;
                                });
                                if (modifiers.isEmpty()) {
                                    attribute.remove("Modifiers");
                                }
                            }
                        }
                    }

                    itemStack.getOrCreateTag().put("EntityTag", entityTag);

                    return itemStack;
                }
            }
                else
                {
                    ItemStack baseItem = entity.getPickResult();

                    if (baseItem != null && !(baseItem.isEmpty()))
                    {
                        CompoundTag entityTag = new CompoundTag();
                        entity.saveWithoutId(entityTag);

                        entityTag.remove("Pos");
                        entityTag.remove("Motion");
                        entityTag.remove("Rotation");
                        entityTag.remove("FallDistance");
                        entityTag.remove("PortalCooldown");
                        entityTag.remove("HurtTime");
                        entityTag.remove("HurtByTimestamp");
                        entityTag.remove("Fire");
                        entityTag.remove("UUID");

                        baseItem.getOrCreateTag().put("EntityTag", entityTag);
                        return baseItem;
                    }
                }
        }
        return ItemStack.EMPTY;
    }
}
