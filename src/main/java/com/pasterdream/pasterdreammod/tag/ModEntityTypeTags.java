package com.pasterdream.pasterdreammod.tag;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeTags {

    public static final TagKey<EntityType<?>> SHADOW_MOB = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_mob"));

}
