package com.pasterdream.pasterdreammod.helper.fluidingredient;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class FluidIngredient
{
    @Nullable
    private final Fluid fluid;
    @Nullable
    private final TagKey<Fluid> tag;
    private final int amount;

    private FluidIngredient(@Nullable Fluid fluid, @Nullable TagKey<Fluid> tag, int amount)
    {
        this.fluid = fluid;
        this.tag = tag;
        this.amount = amount;
    }

    public static FluidIngredient of(Fluid fluid, int amount)
    {
        return new FluidIngredient(fluid, null, amount);
    }

    public static FluidIngredient of(TagKey<Fluid> tag, int amount)
    {
        return new FluidIngredient(null, tag, amount);
    }

    public boolean test(FluidStack stack)
    {
        if (stack.getAmount() < amount)
        {
            return false;
        }

        if (fluid != null)
        {
            return stack.getFluid() == fluid;
        }

        if (tag != null)
        {
            return stack.getFluid().is(tag);
        }

        return false;
    }

    public int getAmount()
    {
        return amount;
    }

    public Fluid getFluid()
    {
        return fluid;
    }

    public TagKey<Fluid> getTag()
    {
        return tag;
    }

    public boolean isEmpty()
    {
        return fluid == null && tag == null;
    }

    public boolean isTag()
    {
        return tag != null;
    }
}
