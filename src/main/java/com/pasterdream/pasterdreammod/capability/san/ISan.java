package com.pasterdream.pasterdreammod.capability.san;

public interface ISan
{
    double getSanValue();
    void setSanValue(double energy);
    void addSanValue(double delta);

    void setIsSanEnable(boolean isEnabled);
    boolean getIsSanEnabled();

    double getMaxSanValue();
    void setMaxSanValue(double maxSanValue);
    void addMaxSanValue(double delta);

    /** 最终总变化率（每 tick）。由服务端每 tick 计算后同步，仅用于 HUD 显示。 */
    double getSanRate();
    void setSanRate(double sanRate);

    void copyValueFromOtherSan(ISan other);
}
