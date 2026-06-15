package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.Objects;

public class ModItemModelsProvider extends ItemModelProvider {
    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.DYEDREAM_QUARTZ.get());

        basicItem(ModItems.DYEDREAM_DUST.get());
        basicItem(ModItems.DYEDREAM_DUST_PIECE.get());
        basicItem(ModItems.AMBER_CANDY.get());
        basicItem(ModItems.PINK_SLIMEBALL.get());

        basicItem(ModItems.MORTAR.get());
        basicItem(ModItems.DYEDREAM_DYE.get());
        basicItem(ModItems.DYEDREAM_BUD_NUGGET.get());
        basicItem(ModItems.DYEDREAM_ALLOY_INGOT.get());
        basicItem(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get());
        basicItem(ModItems.TITANIUM_INGOT.get());
        basicItem(ModItems.TITANIUM_NUGGET.get());
        basicItem(ModItems.RAW_TITANIUM.get());
        basicItem(ModItems.DYEDREAM_ALLOY_NUGGET.get());
        basicItem(ModItems.MOLTEN_GOLD_INGOT.get());
        basicItem(ModItems.MOLTEN_GOLD_NUGGET.get());
        basicItem(ModItems.RAW_MOLTEN_GOLD.get());
        basicItem(ModItems.GLASS_JAR.get());
        basicItem(ModItems.GLASS_JAR_OF_WATER.get());
        basicItem(ModItems.GLASS_JAR_OF_MILK.get());
        basicItem(ModItems.YEAST.get());
        basicItem(ModItems.GUIDING_DRUG.get());
        basicItem(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get());

        withExistingParent(ModItems.PINK_MUSHROOM.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/pink_mushroom"));
        withExistingParent(ModItems.TALL_PINK_MUSHROOM.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/pink_mushroom"));

        withExistingParent(ModItems.DYEDREAM_SAPLING.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_sapling"));

        fenceItem(ModBlocks.DYEDREAM_PLANKS_FENCE, ModBlocks.DYEDREAM_PLANKS);
        buttonItem(ModBlocks.DYEDREAM_PLANKS_BUTTON, ModBlocks.DYEDREAM_PLANKS);
        basicItem(ModItems.DYEDREAM_PLANKS_DOOR.get());
        withExistingParent(ModItems.DYEDREAM_PLANKS_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_planks_trapdoor"));

        wallItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL, ModBlocks.DYEDREAM_QUARTZ_BLOCK);

        wallItem(ModBlocks.DYEDREAM_BUD_WALL, ModBlocks.DYEDREAM_BUD_BLOCK);

        withExistingParent(ModItems.DYEDREAM_GLASSPANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_glass"));
        withExistingParent(ModItems.CARVE_DYEDREAM_GLASSPANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/carve_dyedream_glass"));
        withExistingParent(ModItems.GOLD_CARVE_DYEDREAM_GLASSPANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/gold_carve_dyedream_glass"));

        withExistingParent(ModItems.SMALL_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/small_dyedream_bud"));
        withExistingParent(ModItems.MEDIUM_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/medium_dyedream_bud"));
        withExistingParent(ModItems.LARGE_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/large_dyedream_bud"));


    }

    @Override
    public ItemModelBuilder basicItem(Item item) {
        return basicItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
    }

    @Override
    public ItemModelBuilder basicItem(ResourceLocation item) {
        String path = item.getPath();

        if (path.contains("/")) {
            path = folder + "/" + path;
        }
        return getBuilder(path)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.tryParse(item.getNamespace() + ":" + "item/" + item.getPath()));
    }

    private <T extends Block> void buttonItem(RegistryObject<T> block, RegistryObject<Block> base) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "block/" + base.getId().getPath()));
    }

    private <T extends Block> void fenceItem(RegistryObject<T> block, RegistryObject<Block> base) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "block/" + base.getId().getPath()));
    }

    private <T extends Block> void wallItem(RegistryObject<T> block, RegistryObject<Block> base) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "block/" + base.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "item/" + item.getId().getPath()));
    }



}
