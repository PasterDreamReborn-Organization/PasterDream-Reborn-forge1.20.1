package com.pasterdream.pasterdreammod.helper.multiblockproperties.voxelshapecalculator;

import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class MultipartVoxelShapeCalculator
{
    public static List<List<List<List<VoxelShape>>>> calculateAllDirectionMultipartVoxelShapeFromEastVoxelShape(double startX, double startY, double startZ, double finalX, double finalY, double finalZ)
    {
        int startBlockX = (int)startX;
        int startBlockY = (int)startY;
        int startBlockZ = (int)startZ;
        int finalBlockX = (int)finalX + finalX % 1 == 0 ? 0 : 1;
        int finalBlockY = (int)finalY + finalY % 1 == 0 ? 0 : 1;
        int finalBlockZ = (int)finalZ + finalZ % 1 == 0 ? 0 : 1;

        List<List<List<List<VoxelShape>>>> ListListListListVoxelShape = new ArrayList<>();
        for(int x = startBlockX; x <= finalBlockX; x++)
        {
            List<List<List<VoxelShape>>> ListListListVoxelShape = new ArrayList<>();
            for(int y = startBlockY; y <= finalBlockY; y++)
            {
                List<List<VoxelShape>> ListListVoxelShape = new ArrayList<>();
                for(int z = startBlockZ; z <= finalBlockZ; z++)
                {
                    List<VoxelShape> ListVoxelShape = VoxelShapeCalculator.calculateAllDirectionVoxelShapeFromEastVoxelShape(x == startBlockX ? startX : 0, y == startBlockY ? startY : 0, z == startBlockZ ? startZ : 0, x == finalBlockX ? (finalX % 1 == 0 ? 1 : finalX % 1) : 1, y == finalBlockY ? (finalY % 1 == 0 ? 1 : finalY % 1) : 1, z == finalBlockZ ? (finalZ % 1 == 0 ? 1 : finalZ % 1) : 1);
                    ListListVoxelShape.add(ListVoxelShape);
                }
                ListListListVoxelShape.add(ListListVoxelShape);
            }
            ListListListListVoxelShape.add(ListListListVoxelShape);
        }
        return ListListListListVoxelShape;
    }
}
