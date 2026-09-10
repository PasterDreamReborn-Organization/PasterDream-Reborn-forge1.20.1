package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModShadowDungeonStructureSet
{
    public static List<ResourceLocation> FLOOR_0 = new CopyOnWriteArrayList<>();
    public static List<ResourceLocation> FLOOR_1 = new CopyOnWriteArrayList<>();
    public static List<ResourceLocation> FLOOR_2 = new CopyOnWriteArrayList<>();
    public static List<ResourceLocation> FLOOR_3 = new CopyOnWriteArrayList<>();
    public static List<ResourceLocation> FLOOR_4 = new CopyOnWriteArrayList<>();

    public static List<List<ResourceLocation>> ALL_FLOORS = new CopyOnWriteArrayList<>();

    public static void register()
    {
        ALL_FLOORS.add(FLOOR_0);
        ALL_FLOORS.add(FLOOR_1);
        ALL_FLOORS.add(FLOOR_2);
        ALL_FLOORS.add(FLOOR_3);
        ALL_FLOORS.add(FLOOR_4);

        FLOOR_4.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_mezz_0"));
        FLOOR_4.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_mezz_1"));
        FLOOR_4.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_mezz_2"));
        FLOOR_4.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_mezz_3"));
        FLOOR_4.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_mezz_4"));
        FLOOR_4.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_mezz_5"));

        FLOOR_3.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_shadow_library_0"));
        FLOOR_3.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_shadow_library_1"));
        FLOOR_3.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_shadow_library_2"));
        FLOOR_3.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_shadow_library_3"));

        FLOOR_2.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_digging_0"));
        FLOOR_2.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_digging_1"));
        FLOOR_2.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_digging_2"));

        FLOOR_1.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_shadow_brazier"));
        FLOOR_1.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_black_beetle"));

        FLOOR_0.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_nameless_dyedream_world"));
        FLOOR_0.add(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_nameless_overworld"));
    }
}
