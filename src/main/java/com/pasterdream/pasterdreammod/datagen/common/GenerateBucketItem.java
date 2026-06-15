package com.pasterdream.pasterdreammod.datagen.common;

import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.RegistryObject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GenerateBucketItem
{
    public static void generateBucketItem(DataGenerator generator, PackOutput output)
    {
        FluidBucketModelProvider bucketModels = new FluidBucketModelProvider(output);
        bucketModels.addBucket(ModItems.MELT_DREAM_LIQUID_BUCKET, ModFluids.MELT_DREAM_LIQUID);
        bucketModels.addBucket(ModItems.SHADOW_LIQUID_BUCKET, ModFluids.SHADOW_LIQUID);
        generator.addProvider(true, bucketModels);
    }

    //流体桶物品模型
    public static class FluidBucketModelProvider implements DataProvider
    {
        private final PackOutput output;
        private final List<BucketModelInfo> buckets = new ArrayList<>();

        public static class BucketModelInfo
        {
            public final ResourceLocation bucketItemId;
            public final ResourceLocation fluidId;

            public BucketModelInfo(ResourceLocation bucketItemId, ResourceLocation fluidId)
            {
                this.bucketItemId = bucketItemId;
                this.fluidId = fluidId;
            }
        }

        public FluidBucketModelProvider(PackOutput output)
        {
            this.output = output;
        }

        public void addBucket(RegistryObject<? extends Item> bucketItem, RegistryObject<? extends Fluid> fluid)
        {
            addBucket(bucketItem.getId(), fluid.getId());
        }

        public void addBucket(ResourceLocation bucketItemId, ResourceLocation fluidId)
        {
            buckets.add(new BucketModelInfo(bucketItemId, fluidId));
        }

        @Override
        public CompletableFuture<?> run(CachedOutput cache)
        {
            Path modelFolder = output.getOutputFolder()
                    .resolve("assets/" + PasterDreamMod.MOD_ID + "/models/item");

            List<CompletableFuture<?>> futures = new ArrayList<>();
            for (BucketModelInfo info : buckets)
            {
                JsonObject json = new JsonObject();
                json.addProperty("parent", "forge:item/bucket_drip");
                json.addProperty("loader", "forge:fluid_container");
                json.addProperty("fluid", info.fluidId.toString());

                Path path = modelFolder.resolve(info.bucketItemId.getPath() + ".json");
                futures.add(DataProvider.saveStable(cache, json, path));
            }
            return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        }

        @Override
        public String getName()
        {
            return "Fluid Bucket Models";
        }
    }
}
