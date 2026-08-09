package com.pasterdream.pasterdreammod.helper.pixelcalculator;

import java.util.ArrayList;
import java.util.List;

public class PixelCalculator
{
    public static List<List<Float>> calculate(float minX, float maxX, float minZ, float maxZ)
    {
        List<Float> east = new ArrayList<>();
        east.add(minX);
        east.add(maxX);
        east.add(minZ);
        east.add(maxZ);

        List<Float> south = new ArrayList<>();
        south.add(1 - maxZ);
        south.add(1 - minZ);
        south.add(minX);
        south.add(maxX);

        List<Float> west = new ArrayList<>();
        west.add(1 - maxX);
        west.add(1 - minX);
        west.add(1 - maxZ);
        west.add(1 - minZ);

        List<Float> north = new ArrayList<>();
        north.add(minZ);
        north.add(maxZ);
        north.add(1 - maxX);
        north.add(1 - minX);

        List<List<Float>> result = new ArrayList<>();
        result.add(east);
        result.add(south);
        result.add(west);
        result.add(north);

        return result;
    }
}
