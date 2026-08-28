package com.pasterdream.pasterdreammod.world.fluidtype;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.potionhelper.PotionHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;

public class PotionFluidType extends PasterDreamBaseFluidType
{
    public static final FluidType TYPE = new PotionFluidType();

    private PotionFluidType()
    {
        super(Properties.create().density(1000).viscosity(1000).temperature(300).canExtinguish(true).canSwim(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY), ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_still"), ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_flow"), 0xFF9F41D0);
    }

    @Override
    public String getDescriptionId()
    {
        return "fluid." + PasterDreamMod.MOD_ID + ".potion";
    }

    //流体名称
    @Override
    public Component getDescription(FluidStack fluidStack)
    {
        return PotionHelper.getPotionName(PotionHelper.getEffectType(fluidStack));
    }

    //流体颜色
    @Override
    protected int getStackTintColor(FluidStack fluidStack)
    {
        return PotionHelper.getMixingPotionColor(PotionHelper.getEffectType(fluidStack));
    }
}
