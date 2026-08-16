package com.pasterdream.pasterdreammod.capability.meltdreamenergy;

public class MeltDreamEnergy implements IMeltDreamEnergy
{
    private double meltDreamEnergy = 0.0;
    private boolean isNeedConsume = true;
    private boolean isConsumeDoubled = false;
    private double maxMeltDreamEnergy = 100.0;

    @Override
    public double getMeltDreamEnergy()
    {
        return meltDreamEnergy;
    }

    @Override
    public void setMeltDreamEnergy(double meltDreamEnergy)
    {
        this.meltDreamEnergy = meltDreamEnergy;
    }

    @Override
    public void addMeltDreamEnergy(double delta)
    {
        if(delta < 0)
        {
            if(isNeedConsume)
            {
                if(isConsumeDoubled)
                {
                    delta *= 2;
                }
                setMeltDreamEnergy(meltDreamEnergy + delta);
            }
        }
        else
            if(delta > 0)
            {
                if(meltDreamEnergy + delta <= maxMeltDreamEnergy)
                {
                    setMeltDreamEnergy(meltDreamEnergy + delta);
                }
                    else
                    {
                        setMeltDreamEnergy(maxMeltDreamEnergy);
                    }
            }
    }

    @Override
    public void setIsOrNotNeedConsumeDreamEnergy(boolean isNeedConsume)
    {
        this.isNeedConsume = isNeedConsume;
    }

    @Override
    public boolean getIsOrNotNeedConsumeDreamEnergy()
    {
        return isNeedConsume;
    }

    @Override
    public void setIsConsumeDoubled(boolean isConsumeDoubled)
    {
        this.isConsumeDoubled = isConsumeDoubled;
    }

    @Override
    public boolean isConsumeDoubled()
    {
        return isConsumeDoubled;
    }

    @Override
    public double getMaxMeltDreamEnergy()
    {
        return maxMeltDreamEnergy;
    }

    @Override
    public void setMaxMeltDreamEnergy(double maxMeltDreamEnergy)
    {
        this.maxMeltDreamEnergy = maxMeltDreamEnergy;
    }

    @Override
    public void addMaxMeltDreamEnergy(double delta)
    {
        setMaxMeltDreamEnergy(delta + maxMeltDreamEnergy);
    }

    @Override
    public void copyValueFromOtherMeltDreamEnergy(IMeltDreamEnergy other)
    {
        this.meltDreamEnergy = other.getMeltDreamEnergy();
        this.isNeedConsume = other.getIsOrNotNeedConsumeDreamEnergy();
        this.isConsumeDoubled = other.isConsumeDoubled();
        this.maxMeltDreamEnergy = other.getMaxMeltDreamEnergy();
    }
}
