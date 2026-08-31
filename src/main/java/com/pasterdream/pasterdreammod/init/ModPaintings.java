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
    public static final RegistryObject<PaintingVariant> PASTERDREAM_PORTAL =
            PAINTINGS.register("pasterdream_portal", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> PASTERDREAM_TELESCOPE =
            PAINTINGS.register("pasterdream_telescope", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> PASTERDREAM_DYEDREAM_FLOWER =
            PAINTINGS.register("pasterdream_dyedream_flower", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> PASTERDREAM_DYEDREAM_STATION =
            PAINTINGS.register("pasterdream_dyedream_station", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> PASTERDREAM_PINK_YELLOW_HOUSE =
            PAINTINGS.register("pasterdream_pink_yellow_house", () -> new PaintingVariant(32, 16));

    public static void register(IEventBus eventBus) {
        PAINTINGS.register(eventBus);
    }
}