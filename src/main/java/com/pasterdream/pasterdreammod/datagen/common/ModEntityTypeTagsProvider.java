package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_mob")))
                .add(ModEntities.SHADOW_GOLEM.get())
                .add(ModEntities.TERRORBEAK.get())
                .add(ModEntities.CRAZY_TERRORBEAK.get())
                .add(ModEntities.WEAKENESS_TERRORBEAK.get())
                .add(ModEntities.SHADOW_HAND.get())
                .add(ModEntities.SHADOW_GHOST.get())
                .add(ModEntities.SHADOW_SQUEAL_GHOST.get())
                .add(ModEntities.WAILING_SHADOW_GHOST.get())
                .add(ModEntities.FRIENDLY_SHADOW_GHOST.get())
                .add(ModEntities.BLACK_BEETLE.get())
                .add(ModEntities.BLACK_BEETLE_MOTHER.get())
                .add(ModEntities.SHADOW_MAGICBALL.get())
                .add(ModEntities.SHADOW_TUNE_TOTEM.get())
                .add(ModEntities.AARONCOS_LEFT_HAND.get())
                .add(ModEntities.AARONCOS_RIGHT_HAND.get());

        tag(ModEntityTypeTags.SPECIAL_ENTITY)
                .add(EntityType.ITEM)
                .add(EntityType.EXPERIENCE_ORB)
                .add(EntityType.ITEM_FRAME)
                .add(EntityType.GLOW_ITEM_FRAME)
                .add(ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get())
                .add(ModEntities.TERRASWORD_WAVE.get())
                .add(ModEntities.SHAKING_CRYSTAL.get())
                .add(ModEntities.FOX_FIRE.get())
                .add(ModEntities.SHADOW_TUNE_TOTEM.get());

        tag(ModEntityTypeTags.WIND_KNIGHT_MINION)
                .add(ModEntities.THUNDERCLOUD.get())
                .add(ModEntities.HIGHVOLTAGE_THUNDERCLOUD.get());
    }
}
