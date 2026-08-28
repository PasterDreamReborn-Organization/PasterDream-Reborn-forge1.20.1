package com.pasterdream.pasterdreammod.world.fluid;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class ShadowLiquidFluid extends ForgeFlowingFluid
{
    public static final ForgeFlowingFluid.Properties PROPERTIES = (new ForgeFlowingFluid.Properties(ModFluids.SHADOW_LIQUID_TYPE, ModFluids.SHADOW_LIQUID, ModFluids.FLOWING_SHADOW_LIQUID)).explosionResistance(100.0F).bucket(ModItems.SHADOW_LIQUID_BUCKET).block(() -> (LiquidBlock) ModBlocks.SHADOW_LIQUID.get());

    private ShadowLiquidFluid()
    {
        super(PROPERTIES);
    }

    public static class Source extends ShadowLiquidFluid
    {
        public int getAmount(FluidState state)
        {
            return 8;
        }

        public boolean isSource(FluidState state)
        {
            return true;
        }
    }

    public static class Flowing extends ShadowLiquidFluid
    {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder)
        {
            super.createFluidStateDefinition(builder);
            builder.add(new Property[]{LEVEL});
        }

        public int getAmount(FluidState state)
        {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state)
        {
            return false;
        }
    }

    @Override
    public FluidType getFluidType()
    {
        return ModFluids.SHADOW_LIQUID_TYPE.get();
    }
}
