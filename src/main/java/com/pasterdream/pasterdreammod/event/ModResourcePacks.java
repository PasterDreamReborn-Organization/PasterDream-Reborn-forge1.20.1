package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.resource.PathPackResources;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 将模组自带的可选资源包注册为内置资源包，使其在正式环境中也能被资源包界面检测到。
 * 资源包位于 jar 内的 {@code resourcepacks/} 目录下。
 */
public final class ModResourcePacks
{
    public static final String LEGACY_TEXTURES_PACK_ID = PasterDreamMod.MOD_ID + ":legacy_textures";
    private static final String LEGACY_TEXTURES_PATH = "resourcepacks/pasterdream_legacy_textures";

    public static final String VANILLA_UI_PACK_ID = PasterDreamMod.MOD_ID + ":vanilla_ui";
    private static final String VANILLA_UI_PATH = "resourcepacks/paster_vanilla_ui";

    private ModResourcePacks() {}

    public static void onAddPackFinders(AddPackFindersEvent event)
    {
        if (event.getPackType() != PackType.CLIENT_RESOURCES)
            return;

        IModFileInfo modFileInfo = ModList.get().getModFileById(PasterDreamMod.MOD_ID);
        if (modFileInfo == null)
            return;

        registerPack(event, modFileInfo, LEGACY_TEXTURES_PACK_ID, LEGACY_TEXTURES_PATH,
                "pack.pasterdream.legacy_textures");
        registerPack(event, modFileInfo, VANILLA_UI_PACK_ID, VANILLA_UI_PATH,
                "pack.pasterdream.vanilla_ui");
    }

    private static void registerPack(AddPackFindersEvent event, IModFileInfo modFileInfo,
                                     String packId, String path, String titleKey)
    {
        Path packRoot = modFileInfo.getFile().findResource(path);
        if (packRoot == null || !Files.exists(packRoot))
            return;

        event.addRepositorySource(consumer ->
        {
            Pack pack = Pack.readMetaAndCreate(
                    packId,
                    Component.translatable(titleKey),
                    false,
                    id -> new PathPackResources(id, true, packRoot),
                    PackType.CLIENT_RESOURCES,
                    Pack.Position.TOP,
                    PackSource.BUILT_IN);
            if (pack != null)
                consumer.accept(pack);
        });
    }
}
