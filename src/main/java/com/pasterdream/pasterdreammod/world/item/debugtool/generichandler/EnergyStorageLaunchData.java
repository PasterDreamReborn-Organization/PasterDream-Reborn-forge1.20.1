package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import net.minecraftforge.energy.IEnergyStorage;

public record EnergyStorageLaunchData(IEnergyStorage handler, Runnable onChanged){}
