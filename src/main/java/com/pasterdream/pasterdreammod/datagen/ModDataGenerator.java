package com.pasterdream.pasterdreammod.datagen;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.datagen.common.*;
import com.pasterdream.pasterdreammod.datagen.lang.ModEnUsLangProvider;
import com.pasterdream.pasterdreammod.datagen.lang.ModZhCnLangProvider;
import com.pasterdream.pasterdreammod.datagen.common.ModWorldGenProvider;
import com.pasterdream.pasterdreammod.helper.structuregenerate.StructureGenerationConfig;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomeModifierProvider;
import com.pasterdream.pasterdreammod.worldgen.structures.ModStructureConfig;
import com.pasterdream.pasterdreammod.worldgen.structures.ModStructureProvider;
import com.pasterdream.pasterdreammod.worldgen.structures.ModStructureSetProvider;
import com.pasterdream.pasterdreammod.worldgen.structures.ModTemplatePoolProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // 服务端数据生成
        generator.addProvider(event.includeServer(),
                new ModWorldGenProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModRecipesProvider(packOutput));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTablesProvider::new, LootContextParamSets.BLOCK)
        )));

        BlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(),
                new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(),
                new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBiomeModifierProvider(packOutput, lookupProvider));

        List<StructureGenerationConfig> structures = ModStructureConfig.getStructureConfig();
        generator.addProvider(event.includeServer(), new ModTemplatePoolProvider(packOutput, structures));
        generator.addProvider(event.includeServer(), new ModStructureProvider(packOutput, structures));
        generator.addProvider(event.includeServer(), new ModStructureSetProvider(packOutput, structures));

        // 客户端数据生成
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelsProvider(packOutput, existingFileHelper));
        GenerateBucketItem.generateBucketItem(generator, generator.getPackOutput());
        generator.addProvider(event.includeClient(), new ModEnUsLangProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModZhCnLangProvider(packOutput));
    }

}
