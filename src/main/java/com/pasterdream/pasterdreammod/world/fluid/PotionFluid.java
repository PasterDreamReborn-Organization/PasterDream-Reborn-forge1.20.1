package com.pasterdream.pasterdreammod.world.fluid;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraftforge.fluids.FluidType;

/**
 * 通用「药水」流体 —— 单一流体实例，具体是哪瓶药水由 FluidStack 的 NBT（"Potion" 键）承载，
 * 与 {@code ElixirBottleOfPotionItem} 上的药水 NBT 保持一致。
 */
public class PotionFluid extends PasterDreamBaseFluid
{
    @Override
    public FluidType getFluidType()
    {
        return ModFluids.POTION_TYPE.get();
    }
}
