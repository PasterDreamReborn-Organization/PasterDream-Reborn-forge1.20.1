package com.pasterdream.pasterdreammod.world.fluidtype;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

public class AppleJuiceFluidType extends PasterDreamBaseFluidType
{
    public static final FluidType TYPE = new AppleJuiceFluidType();

    private AppleJuiceFluidType()
    {
        super(Properties.create().density(1000).viscosity(1000).temperature(300).canExtinguish(true).canSwim(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY), ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_still"), ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_flow"), 0x7FFF5E69);
    }

    @Override
    public String getDescriptionId()
    {
        return "fluid." + PasterDreamMod.MOD_ID + ".apple_juice";
    }
}
