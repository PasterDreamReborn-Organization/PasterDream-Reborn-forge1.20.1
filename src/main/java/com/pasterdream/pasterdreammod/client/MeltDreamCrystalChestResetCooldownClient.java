package com.pasterdream.pasterdreammod.client;

public final class MeltDreamCrystalChestResetCooldownClient
{
    private static volatile long endTimeMillis;

    private MeltDreamCrystalChestResetCooldownClient()
    {
    }

    public static void setRemainingTicks(int remainingTicks)
    {
        endTimeMillis = remainingTicks > 0 ? System.currentTimeMillis() + remainingTicks * 50L : 0L;
    }

    public static long getRemainingTicks()
    {
        long end = endTimeMillis;
        if (end == 0L)
        {
            return 0L;
        }

        long remaining = end - System.currentTimeMillis();
        return remaining > 0L ? (remaining + 49L) / 50L : 0L;
    }
}
