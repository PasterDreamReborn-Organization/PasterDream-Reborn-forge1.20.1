package com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition;

import com.pasterdream.pasterdreammod.helper.multiblockproperties._3x3x3Part;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class _3x3x3_CalculatePartPosition
{
    public static BlockPos getMainPosFromAddon(BlockPos addonPos, Direction facing, _3x3x3Part part)
    {
        int[] xyz = parseAddonCoords(part);
        Direction right = facing.getClockWise();
        return addonPos.relative(right, xyz[0]).relative(Direction.UP, -xyz[1]).relative(facing, xyz[2]);
    }

    private static int[] parseAddonCoords(_3x3x3Part part)
    {
        String[] parts = part.getSerializedName().split("_");
        int dx = Integer.parseInt(parts[1]) - 1;
        int dy = Integer.parseInt(parts[2]);
        int dz = Integer.parseInt(parts[3]) - 1;
        return new int[]{dx, dy, dz};
    }

    public static BlockPos getPartPos(BlockPos mainPos, Direction facing, _3x3x3Part part)
    {
        int[] xyz = parseAddonCoords(part);
        Direction right = facing.getClockWise();
        return mainPos.relative(right, -xyz[0]).relative(Direction.UP, xyz[1]).relative(facing, -xyz[2]);
    }
}
