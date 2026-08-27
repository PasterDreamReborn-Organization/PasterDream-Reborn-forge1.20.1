package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {

    public static final DeferredRegister<PaintingVariant> PAINTINGS =
            DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, PasterDreamMod.MOD_ID);

    public static final RegistryObject<PaintingVariant> PASTERDREAM_TITLE =
            PAINTINGS.register("pasterdream_title", () -> new PaintingVariant(64, 64));
    public static final RegistryObject<PaintingVariant> PASTERDREAM_START =
            PAINTINGS.register("pasterdream_start", () -> new PaintingVariant(64, 64));
    public static final RegistryObject<PaintingVariant> PASTERDREAM_AEROLITE_DUST =
            PAINTINGS.register("pasterdream_aerolite_dust", () -> new PaintingVariant(256, 256));

    public static void register(IEventBus eventBus) {
        PAINTINGS.register(eventBus);
    }
}