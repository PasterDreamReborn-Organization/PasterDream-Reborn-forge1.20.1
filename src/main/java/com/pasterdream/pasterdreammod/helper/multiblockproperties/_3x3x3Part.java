package com.pasterdream.pasterdreammod.helper.multiblockproperties;

import net.minecraft.util.StringRepresentable;

public enum _3x3x3Part implements StringRepresentable
{
    MAIN("main"),
    ADDON_0_0_0("addon_0_0_0"),
    ADDON_0_0_1("addon_0_0_1"),
    ADDON_0_0_2("addon_0_0_2"),
    ADDON_1_0_0("addon_1_0_0"),
    ADDON_1_0_2("addon_1_0_2"),
    ADDON_2_0_0("addon_2_0_0"),
    ADDON_2_0_1("addon_2_0_1"),
    ADDON_2_0_2("addon_2_0_2"),
    ADDON_0_1_0("addon_0_1_0"),
    ADDON_0_1_1("addon_0_1_1"),
    ADDON_0_1_2("addon_0_1_2"),
    ADDON_1_1_0("addon_1_1_0"),
    ADDON_1_1_1("addon_1_1_1"),
    ADDON_1_1_2("addon_1_1_2"),
    ADDON_2_1_0("addon_2_1_0"),
    ADDON_2_1_1("addon_2_1_1"),
    ADDON_2_1_2("addon_2_1_2"),
    ADDON_0_2_0("addon_0_2_0"),
    ADDON_0_2_1("addon_0_2_1"),
    ADDON_0_2_2("addon_0_2_2"),
    ADDON_1_2_0("addon_1_2_0"),
    ADDON_1_2_1("addon_1_2_1"),
    ADDON_1_2_2("addon_1_2_2"),
    ADDON_2_2_0("addon_2_2_0"),
    ADDON_2_2_1("addon_2_2_1"),
    ADDON_2_2_2("addon_2_2_2");

    private final String name;

    private _3x3x3Part(String name)
    {
        this.name = name;
    }

    public String getSerializedName()
    {
        return this.name;
    }
}
