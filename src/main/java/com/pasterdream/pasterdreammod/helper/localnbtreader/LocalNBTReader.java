package com.pasterdream.pasterdreammod.helper.localnbtreader;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.InputStream;
import java.util.Optional;

public class LocalNBTReader
{
    public static final ResourceLocation WEAPON_WORKSHOP_MATERIAL = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "structures/weapon_workshop_material.nbt");
    public static final ResourceLocation SHADOW_BLAST_FURNACE_MATERIAL = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "structures/shadow_blast_furnace_material.nbt");

    public static final ResourceLocation WEAPON_WORKSHOP_RESULT = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "structures/weapon_workshop_result.nbt");
    public static final ResourceLocation SHADOW_BLAST_FURNACE_RESULT = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "structures/shadow_blast_furnace_result.nbt");

    public static CompoundTag getCompoundTag(ResourceLocation resourceLocation)
    {
        try
        {
            ResourceManager manager = Minecraft.getInstance().getResourceManager();
            Optional<Resource> resource = manager.getResource(resourceLocation);
            if (resource.isPresent())
            {
                try (InputStream stream = resource.get().open())
                {
                    return NbtIo.readCompressed(stream);
                }
            }
        }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        return null;
    }
}
