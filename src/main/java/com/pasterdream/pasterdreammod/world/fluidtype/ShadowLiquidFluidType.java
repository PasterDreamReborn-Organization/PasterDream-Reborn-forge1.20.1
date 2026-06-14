package com.pasterdream.pasterdreammod.world.fluidtype;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

public class ShadowLiquidFluidType extends PasterDreamBaseFluidType
{
    public static final FluidType TYPE = new ShadowLiquidFluidType();

    public ShadowLiquidFluidType()
    {
        super(Properties.create().fallDistanceModifier(0.0F).canExtinguish(true).supportsBoating(true).canHydrate(true).motionScale(0.007).temperature(100).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH), ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "block/shadow_liquid_still"), ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,  "block/shadow_liquid_flowing"), 0xFFFFFFFF);
    }

    @Override
    public String getDescriptionId()
    {
        return "fluid." + PasterDreamMod.MOD_ID + ".shadow_liquid";
    }
}
