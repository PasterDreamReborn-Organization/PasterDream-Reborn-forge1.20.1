package com.pasterdream.pasterdreammod.helper.multiblockproperties.voxelshapecalculator;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class VoxelShapeCalculator
{
    public static List<VoxelShape> calculateAllDirectionVoxelShapeFromEastVoxelShape(double startX, double startY, double startZ, double finalX, double finalY, double finalZ)
    {
        List<VoxelShape> list = new ArrayList<>();

        VoxelShape eastVoxelShape = Shapes.box(startX, startY, startZ, finalX, finalY, finalZ);
        VoxelShape southVoxelShape = Shapes.box(1 - finalZ, startY, startX, 1 - startZ, finalY, finalX);
        VoxelShape westVoxelShape = Shapes.box(1 - finalX, startY, 1 - finalZ, 1 - startX, finalY, 1 - startZ);
        VoxelShape northVoxelShape = Shapes.box(startZ, startY, 1 - finalX, finalZ, finalY, 1 - startX);

        list.add(eastVoxelShape);
        list.add(southVoxelShape);
        list.add(westVoxelShape);
        list.add(northVoxelShape);
        return list;
    }
}
