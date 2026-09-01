package com.pasterdream.pasterdreammod.recipe.genericrecipe;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class MatchSingleFluid
{
    public static FluidStack match(FluidIngredient fluidIngredient, FluidStack matchFluid)
    {
        if(fluidIngredient.isTag())
        {
            List<FluidStack> ListFluidStackFromTag = fluidIngredient.getListFluidStackFromTag();
            for(FluidStack fluidStack : ListFluidStackFromTag)
            {
                if(fluidIngredient.isSameFluidSameTags(fluidStack, matchFluid))
                {
                    return fluidStack;
                }
            }
            return null;
        }
            else
            {
                FluidStack fluidStack = fluidIngredient.getFluidStack();
                if(fluidIngredient.isSameFluidSameTags(fluidStack, matchFluid))
                {
                    return fluidStack;
                }
                    else
                    {
                        return null;
                    }
            }
    }
}
