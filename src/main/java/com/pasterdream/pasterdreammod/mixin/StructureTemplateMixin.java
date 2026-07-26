package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.block.dreamtrain.DreamTrainStructureProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
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
    private void onPlaceInWorldHead(ServerLevelAccessor level, BlockPos pos, BlockPos pivot,
                                     StructurePlaceSettings settings, RandomSource random, int flags,
                                     CallbackInfoReturnable<Boolean> cir) {
        FORCE_NO_KEEP_LIQUIDS.set(true);
        ResourceLocation id = DreamTrainStructureProcedure.getTemplateId((StructureTemplate) (Object) this);
        if (id != null && id.getNamespace().equals("pasterdream")
                && id.getPath().equals("dream_train_marker")) {
            MinecraftServer server = level.getServer();
            ResourceKey<Level> dim = level.getLevel().dimension();
            server.execute(() -> {
                ServerLevel serverLevel = server.getLevel(dim);
                if (serverLevel != null) {
                    DreamTrainStructureProcedure.placeTrain(serverLevel, pos);
                }
            });
        }
    }

    @Inject(method = "placeInWorld", at = @At("RETURN"))
    private void onPlaceInWorldReturn(ServerLevelAccessor level, BlockPos pos, BlockPos pivot,
                                       StructurePlaceSettings settings, RandomSource random, int flags,
                                       CallbackInfoReturnable<Boolean> cir) {
        FORCE_NO_KEEP_LIQUIDS.remove();
    }

    @Redirect(method = "placeInWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;shouldKeepLiquids()Z"))
    private boolean forceNoKeepLiquids(StructurePlaceSettings settings) {
        if (FORCE_NO_KEEP_LIQUIDS.get() != null) {
            return false;
        }
        return settings.shouldKeepLiquids();
    }
}
