package com.pasterdream.pasterdreammod.world.item;

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
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
