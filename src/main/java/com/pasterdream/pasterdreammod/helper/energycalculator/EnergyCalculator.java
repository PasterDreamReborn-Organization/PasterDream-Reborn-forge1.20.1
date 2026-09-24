package com.pasterdream.pasterdreammod.helper.energycalculator;

import net.minecraftforge.energy.IEnergyStorage;

public class EnergyCalculator
{
    public static void calculate(IEnergyStorage dischargeItemEnergyStorage, IEnergyStorage chargeItemEnergyStorage, int energyAmount)
    {
        if(energyAmount != 0 && dischargeItemEnergyStorage != null && chargeItemEnergyStorage != null)
        {
            if(energyAmount < 0)
            {
                energyAmount = -energyAmount;
            }

            int canDischargeEnergyAmount = dischargeItemEnergyStorage.extractEnergy(energyAmount, true);
            int canChargeEnergyAmount = chargeItemEnergyStorage.receiveEnergy(energyAmount, true);
            if(canDischargeEnergyAmount != 0 && canChargeEnergyAmount != 0)
            {
                int transmitEnergyAmount = Math.min(canDischargeEnergyAmount, canChargeEnergyAmount);
                dischargeItemEnergyStorage.extractEnergy(transmitEnergyAmount, false);
                chargeItemEnergyStorage.receiveEnergy(transmitEnergyAmount, false);
            }
        }
    }
}
