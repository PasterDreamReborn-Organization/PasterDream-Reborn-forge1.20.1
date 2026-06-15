package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.fluid.*;
import com.pasterdream.pasterdreammod.world.fluidtype.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids
{
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, PasterDreamMod.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, PasterDreamMod.MOD_ID);

    public static final RegistryObject<FluidType> MELT_DREAM_LIQUID_TYPE = FLUID_TYPES.register("melt_dream_liquid", () -> MeltDreamLiquidFluidType.TYPE);
    public static final RegistryObject<FluidType> SHADOW_LIQUID_TYPE = FLUID_TYPES.register("shadow_liquid", () -> ShadowLiquidFluidType.TYPE);

    public static final RegistryObject<FluidType> APPLE_JUICE_TYPE = FLUID_TYPES.register("apple_juice", () -> AppleJuiceFluidType.TYPE);
    public static final RegistryObject<FluidType> DREAM_JUICE_TYPE = FLUID_TYPES.register("dream_juice", () -> DreamJuiceFluidType.TYPE);
    public static final RegistryObject<FluidType> DYEDREAM_FLOWER_TEA_TYPE = FLUID_TYPES.register("dyedream_flower_tea", () -> DyedreamFlowerTeaFluidType.TYPE);
    public static final RegistryObject<FluidType> DYEDREAM_JUICE_TYPE = FLUID_TYPES.register("dyedream_juice", () -> DyedreamJuiceFluidType.TYPE);
    public static final RegistryObject<FluidType> DYEDREAM_PERFUME_TYPE = FLUID_TYPES.register("dyedream_perfume", () -> DyedreamPerfumeFluidType.TYPE);
    public static final RegistryObject<FluidType> GOLDENROD_TEA_TYPE = FLUID_TYPES.register("goldenrod_tea", () -> GoldenrodTeaFluidType.TYPE);
    public static final RegistryObject<FluidType> GUIDING_DRUG_TYPE = FLUID_TYPES.register("guiding_drug", () -> GuidingDrugFluidType.TYPE);
    public static final RegistryObject<FluidType> HONEY_JUICE_TYPE = FLUID_TYPES.register("honey_juice", () -> HoneyJuiceFluidType.TYPE);
    public static final RegistryObject<FluidType> RAGE_ELIXIR_TYPE = FLUID_TYPES.register("rage_elixir", () -> RageElixirFluidType.TYPE);
    public static final RegistryObject<FluidType> UNCOOKED_DYEDREAM_FLOWER_TEA_TYPE = FLUID_TYPES.register("uncooked_dyedream_flower_tea", () -> UncookedDyedreamFlowerTeaFluidType.TYPE);
    public static final RegistryObject<FluidType> WATERMELON_JUICE_TYPE = FLUID_TYPES.register("watermelon_juice", () -> WatermelonJuiceFluidType.TYPE);
    public static final RegistryObject<FluidType> WIND_PLANT_EXTRACT_TYPE = FLUID_TYPES.register("wind_plant_extract", () -> WindPlantExtractFluidType.TYPE);
    public static final RegistryObject<FluidType> YEAST_TYPE = FLUID_TYPES.register("yeast", () -> YeastFluidType.TYPE);

    public static final RegistryObject<Fluid> FLOWING_MELT_DREAM_LIQUID = FLUIDS.register("flowing_melt_dream_liquid", MeltDreamLiquidFluid.Flowing::new);
    public static final RegistryObject<Fluid> MELT_DREAM_LIQUID = FLUIDS.register("melt_dream_liquid", MeltDreamLiquidFluid.Source::new);
    public static final RegistryObject<Fluid> FLOWING_SHADOW_LIQUID = FLUIDS.register("flowing_shadow_liquid", ShadowLiquidFluid.Flowing::new);
    public static final RegistryObject<Fluid> SHADOW_LIQUID = FLUIDS.register("shadow_liquid", ShadowLiquidFluid.Source::new);

    public static final RegistryObject<Fluid> APPLE_JUICE = FLUIDS.register("apple_juice", AppleJuiceFluid::new);
    public static final RegistryObject<Fluid> DREAM_JUICE = FLUIDS.register("dream_juice", DreamJuiceFluid::new);
    public static final RegistryObject<Fluid> DYEDREAM_FLOWER_TEA = FLUIDS.register("dyedream_flower_tea", DyedreamFlowerTeaFluid::new);
    public static final RegistryObject<Fluid> DYEDREAM_JUICE = FLUIDS.register("dyedream_juice", DyedreamJuiceFluid::new);
    public static final RegistryObject<Fluid> DYEDREAM_PERFUME = FLUIDS.register("dyedream_perfume", DyedreamPerfumeFluid::new);
    public static final RegistryObject<Fluid> GOLDENROD_TEA = FLUIDS.register("goldenrod_tea", GoldenrodTeaFluid::new);
    public static final RegistryObject<Fluid> GUIDING_DRUG = FLUIDS.register("guiding_drug", GuidingDrugFluid::new);
    public static final RegistryObject<Fluid> HONEY_JUICE = FLUIDS.register("honey_juice", HoneyJuiceFluid::new);
    public static final RegistryObject<Fluid> RAGE_ELIXIR = FLUIDS.register("rage_elixir", RageElixirFluid::new);
    public static final RegistryObject<Fluid> UNCOOKED_DYEDREAM_FLOWER_TEA = FLUIDS.register("uncooked_dyedream_flower_tea", UncookedDyedreamFlowerTeaFluid::new);
    public static final RegistryObject<Fluid> WATERMELON_JUICE = FLUIDS.register("watermelon_juice", WatermelonJuiceFluid::new);
    public static final RegistryObject<Fluid> WIND_PLANT_EXTRACT = FLUIDS.register("wind_plant_extract", WindPlantExtractFluid::new);
    public static final RegistryObject<Fluid> YEAST = FLUIDS.register("yeast", YeastFluid::new);

    public static void register(IEventBus eventBus)
    {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }
}
