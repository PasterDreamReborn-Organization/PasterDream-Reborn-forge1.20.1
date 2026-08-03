package com.pasterdream.pasterdreammod.helper.multiblockproperties.voxelshapecalculator;


import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class SingleFloorVoxelShapeCalculator
{
    public static List<List<List<VoxelShape>>> calculateAllDirectionSingleFloorVoxelShapeFromEastVoxelShape(double startX, double startY, double startZ, double finalX, double finalY, double finalZ)
    {
        int startBlockX = (int)startX;
        int startBlockZ = (int)startZ;
        int finalBlockX = (int)finalX + finalX % 1 == 0 ? 0 : 1;
        int finalBlockZ = (int)finalZ + finalZ % 1 == 0 ? 0 : 1;

        List<List<List<VoxelShape>>> ListListListVoxelShape = new ArrayList<>();
        for(int x = startBlockX; x <= finalBlockX; x++)
        {
            List<List<VoxelShape>> ListListVoxelShape = new ArrayList<>();
            for(int z = startBlockZ; z <= finalBlockZ; z++)
            {
                List<VoxelShape> ListVoxelShape = VoxelShapeCalculator.calculateAllDirectionVoxelShapeFromEastVoxelShape(x == startBlockX ? startX : 0, startY, z == startBlockZ ? startZ : 0, x == finalBlockX ? (finalX % 1 == 0 ? 1 : finalX % 1) : 1, finalY, z == finalBlockZ ? (finalZ % 1 == 0 ? 1 : finalZ % 1) : 1);
                ListListVoxelShape.add(ListVoxelShape);
            }
            ListListListVoxelShape.add(ListListVoxelShape);
        }
        return ListListListVoxelShape;
    }
}
