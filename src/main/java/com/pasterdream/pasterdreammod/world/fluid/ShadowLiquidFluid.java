package com.pasterdream.pasterdreammod.world.fluid;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
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

    /**
     * 与水一致：流动的熔融阴影若有两个相邻的同种源方块、且下方为固体，则转化为新的源方块。
     * 是否生效遵循水的源转换游戏规则（waterSourceConversion），默认开启。
     */
    @Override
    public boolean canConvertToSource(FluidState state, Level level, BlockPos pos)
    {
        return level.getGameRules().getBoolean(GameRules.RULE_WATER_SOURCE_CONVERSION);
    }

    @Override
    public FluidType getFluidType()
    {
        return ModFluids.SHADOW_LIQUID_TYPE.get();
    }
}
