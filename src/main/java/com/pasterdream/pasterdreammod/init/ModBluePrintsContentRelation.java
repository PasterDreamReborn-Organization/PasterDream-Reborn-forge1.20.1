package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.helper.localnbtreader.LocalNBTReader;
import com.pasterdream.pasterdreammod.world.item.blueprints.BluePrintRegistry;
import net.minecraft.network.chat.Component;

public class ModBluePrintsContentRelation
{
    public static void registerBluePrintsContentRelation()
    {
        BluePrintRegistry.register("精铸工坊", Component.translatable("book.pasterdream.title.精铸工坊"), LocalNBTReader.WEAPON_WORKSHOP_MATERIAL, LocalNBTReader.WEAPON_WORKSHOP_RESULT);
        BluePrintRegistry.register("暗影高炉", Component.translatable("book.pasterdream.title.暗影高炉"), LocalNBTReader.SHADOW_BLAST_FURNACE_MATERIAL, LocalNBTReader.SHADOW_BLAST_FURNACE_RESULT);
    }
}
