package com.pasterdream.pasterdreammod.capability.meltdreamenergy;

public interface IMeltDreamEnergy
{
    double getMeltDreamEnergy();
    void setMeltDreamEnergy(double meltDreamEnergy);
    void addMeltDreamEnergy(double delta);

    void setIsOrNotNeedConsumeDreamEnergy(boolean isNotNeed);
    boolean getIsOrNotNeedConsumeDreamEnergy();

    void setIsConsumeDoubled(boolean isConsumeDoubled);
    boolean isConsumeDoubled();

    double getMaxMeltDreamEnergy();
    void setMaxMeltDreamEnergy(double maxMeltDreamEnergy);
    void addMaxMeltDreamEnergy(double delta);

    void copyValueFromOtherMeltDreamEnergy(IMeltDreamEnergy other);
}
