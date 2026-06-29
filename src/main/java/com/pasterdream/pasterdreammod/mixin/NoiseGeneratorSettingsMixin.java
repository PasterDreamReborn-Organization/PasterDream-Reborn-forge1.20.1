package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(NoiseGeneratorSettings.class)
public class NoiseGeneratorSettingsMixin
{
    @Inject(method = "structureSets", at = @At("RETURN"), cancellable = true)
    private void onGetStructureSets(CallbackInfoReturnable<HolderSet<StructureSet>> cir)
    {
        HolderSet<StructureSet> original = cir.getReturnValue();

        Registry<StructureSet> registry = (Registry<StructureSet>)BuiltInRegistries.REGISTRY.get(ResourceLocation.parse("worldgen/structure_set"));
        if (registry == null)
        {
            return;
        }

        List<Holder<StructureSet>> combined = new ArrayList<>(original.stream().toList());

        for (ResourceLocation id : registry.keySet())
        {
            if (id.getNamespace().equals(PasterDreamMod.MOD_ID))
            {
                StructureSet set = registry.get(id);
                if (set != null)
                {
                    combined.add(Holder.direct(set));
                }
            }
        }

        cir.setReturnValue(HolderSet.direct(combined));
    }
}
