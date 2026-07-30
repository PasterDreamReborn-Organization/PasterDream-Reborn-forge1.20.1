package com.pasterdream.pasterdreammod.helper.multiblockproperties;

import net.minecraft.util.StringRepresentable;

public enum _2Part implements StringRepresentable
{
    MAIN("main"),
    ADDON("addon");

    private final String name;

    private _2Part(String name)
    {
        this.name = name;
    }

    public String getSerializedName()
    {
        return this.name;
    }
}
