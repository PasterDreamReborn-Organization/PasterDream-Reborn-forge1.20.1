package com.pasterdream.pasterdreammod.world.fluidtype;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

public class MeltdreamLiquidFluidType extends PasterDreamBaseFluidType
{
    public static final FluidType TYPE = new MeltdreamLiquidFluidType();

    public MeltdreamLiquidFluidType()
    {
        super(Properties.create().canSwim(false).canDrown(false).pathType(BlockPathTypes.LAVA).adjacentPathType(null).motionScale(0.007).lightLevel(12).viscosity(100).temperature(10).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH), ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "block/meltdream_liquid_still"), ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "block/meltdream_liquid_flowing"), 0XFFFFFFFF);
    }

    @Override
    public String getDescriptionId()
    {
        return "fluid." + PasterDreamMod.MOD_ID + ".meltdream_liquid";
    }
}
