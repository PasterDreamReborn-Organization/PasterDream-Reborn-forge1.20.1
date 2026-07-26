package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.block.dreamtrain.DreamTrainStructureProcedure;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructureTemplateManager.class)
public class StructureTemplateManagerMixin {

    @Inject(method = "getOrCreate", at = @At("RETURN"))
    private void onGetOrCreateReturn(ResourceLocation id, CallbackInfoReturnable<StructureTemplate> cir) {
        DreamTrainStructureProcedure.registerTemplateId(cir.getReturnValue(), id);
    }
}
