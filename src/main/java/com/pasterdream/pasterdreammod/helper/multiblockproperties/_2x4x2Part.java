package com.pasterdream.pasterdreammod.helper.multiblockproperties;

import net.minecraft.util.StringRepresentable;

public enum _2x4x2Part implements StringRepresentable
{
    MAIN("main"),
    ADDON_0_0_1("addon_0_0_1"),
    ADDON_1_0_0("addon_1_0_0"),
    ADDON_1_0_1("addon_1_0_1"),
    ADDON_0_1_0("addon_0_1_0"),
    ADDON_0_1_1("addon_0_1_1"),
    ADDON_1_1_0("addon_1_1_0"),
    ADDON_1_1_1("addon_1_1_1"),
    ADDON_0_2_0("addon_0_2_0"),
    ADDON_0_2_1("addon_0_2_1"),
    ADDON_1_2_0("addon_1_2_0"),
    ADDON_1_2_1("addon_1_2_1"),
    ADDON_0_3_0("addon_0_3_0"),
    ADDON_0_3_1("addon_0_3_1"),
    ADDON_1_3_0("addon_1_3_0"),
    ADDON_1_3_1("addon_1_3_1");

    private final String name;

    private _2x4x2Part(String name)
    {
        this.name = name;
    }

    public String getSerializedName()
    {
        return this.name;
    }
}
