package com.pasterdream.pasterdreammod.capability.san;

import net.minecraft.util.Mth;

public class San implements ISan
{
    private double sanValue = 100.0;
    private boolean isEnabled = true;
    private double maxSanValue = 100.0;

    @Override
    public double getSanValue()
    {
        return sanValue;
    }

    @Override
    public void setSanValue(double sanValue)
    {
        this.sanValue = sanValue;
    }

    @Override
    public void addSanValue(double delta)
    {
        if(!isEnabled) return;
        setSanValue(Mth.clamp(sanValue + delta, 0.0, maxSanValue));
    }

    @Override
    public void setIsSanEnable(boolean isEnabled)
    {
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean getIsSanEnabled()
    {
        return isEnabled;
    }

    @Override
    public double getMaxSanValue()
    {
        return maxSanValue;
    }

    @Override
    public void setMaxSanValue(double maxSanValue)
    {
        this.maxSanValue = maxSanValue;
    }

    @Override
    public void addMaxSanValue(double delta)
    {
        setMaxSanValue(delta + maxSanValue);
    }

    @Override
    public void copyValueFromOtherSan(ISan other)
    {
        this.sanValue = other.getSanValue();
        this.maxSanValue = other.getMaxSanValue();
        this.isEnabled = other.getIsSanEnabled();
    }
}
