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
 * 资源包位于 jar 内的 {@code resourcepacks/pasterdream_legacy_textures}。
 */
public final class ModResourcePacks
{
    public static final String LEGACY_TEXTURES_PACK_ID = PasterDreamMod.MOD_ID + ":legacy_textures";
    private static final String LEGACY_TEXTURES_PATH = "resourcepacks/pasterdream_legacy_textures";

    private ModResourcePacks() {}

    public static void onAddPackFinders(AddPackFindersEvent event)
    {
        if (event.getPackType() != PackType.CLIENT_RESOURCES)
            return;

        IModFileInfo modFileInfo = ModList.get().getModFileById(PasterDreamMod.MOD_ID);
        if (modFileInfo == null)
            return;

        Path packRoot = modFileInfo.getFile().findResource(LEGACY_TEXTURES_PATH);
        if (packRoot == null || !Files.exists(packRoot))
            return;

        event.addRepositorySource(consumer ->
        {
            Pack pack = Pack.readMetaAndCreate(
                    LEGACY_TEXTURES_PACK_ID,
                    Component.translatable("pack.pasterdream.legacy_textures"),
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
