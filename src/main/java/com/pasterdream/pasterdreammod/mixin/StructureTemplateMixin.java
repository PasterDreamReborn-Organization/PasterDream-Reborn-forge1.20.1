package com.pasterdream.pasterdreammod.mixin;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructureTemplate.class)
public class StructureTemplateMixin {

    private static final ThreadLocal<Boolean> FORCE_NO_KEEP_LIQUIDS = new ThreadLocal<>();

    @Inject(method = "placeInWorld", at = @At("HEAD"))
    private void setNoKeepLiquids(CallbackInfoReturnable<Boolean> cir) {
        FORCE_NO_KEEP_LIQUIDS.set(true);
    }

    @Inject(method = "placeInWorld", at = @At("RETURN"))
    private void clearNoKeepLiquids(CallbackInfoReturnable<Boolean> cir) {
        FORCE_NO_KEEP_LIQUIDS.remove();
    }

    @Redirect(method = "placeInWorld",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;shouldKeepLiquids()Z"))
    private boolean forceNoKeepLiquids(StructurePlaceSettings settings) {
        if (FORCE_NO_KEEP_LIQUIDS.get() != null) {
            return false;
        }
        return settings.shouldKeepLiquids();
    }
}
