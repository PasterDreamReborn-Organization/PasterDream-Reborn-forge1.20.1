package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PasterDreamMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PASTERDREAM_TAB = CREATIVE_MODE_TABS.register("pasterdream_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_tab"))
                    .icon(() -> new ItemStack(ModItems.DYEDREAM_DIRT.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.DYEDREAM_DIRT.get());
                        output.accept(ModItems.DYEDREAM_GRASS_BLOCK.get());
                        output.accept(ModItems.DYEDREAM_LOG.get());
                        output.accept(ModItems.DYEDREAM_WOOD.get());
                        output.accept(ModItems.DYEDREAM_LEAVES.get());
                        output.accept(ModItems.DYEDREAM_WORLDTREE_LEAVES.get());
                        output.accept(ModItems.DYEDREAM_SAPLING.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_ORE.get());
                        output.accept(ModItems.DYEDREAM_DUST_ORE.get());
                        output.accept(ModItems.AMBER_CANDY_ORE.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.BRICKS_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.PILLAR_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK_STAIRS.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK_WALL.get());
                        output.accept(ModItems.DYEDREAM_PLANKS.get());
                        output.accept(ModItems.DYEDREAM_PLANKS_STAIRS.get());
                        output.accept(ModItems.DYEDREAM_PLANKS_SLAB.get());
                        output.accept(ModItems.DYEDREAM_PLANKS_FENCE.get());
                        output.accept(ModItems.DYEDREAM_PLANKS_FENCEGATE.get());
                        output.accept(ModItems.DYEDREAM_PLANKS_PANE.get());
                        output.accept(ModItems.DYEDREAM_PLANKS_DOOR.get());
                        output.accept(ModItems.DYEDREAM_PLANKS_TRAPDOOR.get());
                        output.accept(ModItems.DYEDREAM_PLANKS_PRESSURE_PLATE.get());
                        output.accept(ModItems.DYEDREAM_PLANKS_BUTTON.get());
                        output.accept(ModItems.PINK_SLIME_BLOCK.get());
                        output.accept(ModItems.PINK_MUSHROOM_BLOCK.get());
                        output.accept(ModItems.PINK_MUSHROOM_STEM.get());
                        output.accept(ModItems.PINK_MUSHROOM_PORES.get());
                        output.accept(ModItems.PINK_SHROOMLIGHT.get());
                        output.accept(ModItems.PINK_MUSHROOM.get());
                        output.accept(ModItems.TALL_PINK_MUSHROOM.get());
                        output.accept(ModItems.DYEDREAM_SAND.get());
                        output.accept(ModItems.DYEDREAM_GLASS.get());
                        output.accept(ModItems.DYEDREAM_GLASSPANE.get());
                        output.accept(ModItems.CARVE_DYEDREAM_GLASS.get());
                        output.accept(ModItems.CARVE_DYEDREAM_GLASSPANE.get());
                        output.accept(ModItems.GOLD_CARVE_DYEDREAM_GLASS.get());
                        output.accept(ModItems.GOLD_CARVE_DYEDREAM_GLASSPANE.get());
                        output.accept(ModItems.SMALL_DYEDREAM_BUD.get());
                        output.accept(ModItems.MEDIUM_DYEDREAM_BUD.get());
                        output.accept(ModItems.LARGE_DYEDREAM_BUD.get());
                        output.accept(ModItems.DYEDREAM_BUD_BLOCK.get());
                        output.accept(ModItems.DYEDREAM_BUD_STAIRS.get());
                        output.accept(ModItems.DYEDREAM_BUD_SLAB.get());
                        output.accept(ModItems.DYEDREAM_BUD_WALL.get());
                        output.accept(ModItems.DYEDREAM_ICE.get());
                        output.accept(ModItems.DYEDREAM_PACKED_ICE.get());
                        output.accept(ModItems.DYEDREAM_LARTERN.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> PASTERDREAM_MATERIALS_TAB = CREATIVE_MODE_TABS.register("pasterdream_materials_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_materials_tab"))
                    .icon(() -> new ItemStack(ModItems.DYEDREAM_QUARTZ.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.DYEDREAM_QUARTZ.get());
                        output.accept(ModItems.DYEDREAM_DUST.get());
                        output.accept(ModItems.DYEDREAM_DUST_PIECE.get());
                        output.accept(ModItems.AMBER_CANDY.get());
                        output.accept(ModItems.PINK_SLIMEBALL.get());
                        output.accept(ModItems.MORTAR.get());
                        output.accept(ModItems.DYEDREAM_DYE.get());
                        output.accept(ModItems.DYEDREAM_BUD_NUGGET.get());
                        output.accept(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get());
                        output.accept(ModItems.DYEDREAM_ALLOY_INGOT.get());
                        output.accept(ModItems.DYEDREAM_ALLOY_NUGGET.get());
                        output.accept(ModItems.RAW_TITANIUM.get());
                        output.accept(ModItems.TITANIUM_INGOT.get());
                        output.accept(ModItems.TITANIUM_NUGGET.get());
                        output.accept(ModItems.RAW_MOLTEN_GOLD.get());
                        output.accept(ModItems.MOLTEN_GOLD_INGOT.get());
                        output.accept(ModItems.MOLTEN_GOLD_NUGGET.get());
                        output.accept(ModItems.GLASS_JAR.get());
                        output.accept(ModItems.GLASS_JAR_OF_WATER.get());
                        output.accept(ModItems.GLASS_JAR_OF_MILK.get());
                        output.accept(ModItems.GLASS_JAR_OF_DREAM_JUICE.get());
                        output.accept(ModItems.GLASS_JAR_OF_GOLDENROD_TEA.get());
                        output.accept(ModItems.YEAST.get());
                        output.accept(ModItems.GUIDING_DRUG.get());
                        output.accept(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get());
                        output.accept(ModItems.MELT_DREAM_LIQUID_BUCKET.get());
                        output.accept(ModItems.SHADOW_LIQUID_BUCKET.get());
                        output.accept(ModItems.GLASS_CUP.get());
                        output.accept(ModItems.GLASS_CUP_OF_APPLE_JUICE.get());
                        output.accept(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get());
                        output.accept(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get());
                        output.accept(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get());
                        output.accept(ModItems.GLASS_CUP_OF_HONEY_JUICE.get());
                        output.accept(ModItems.GLASS_CUP_OF_WATERMELON_JUICE.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
