package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 传送门屏幕叠加层换贴图。
 * <p>
 * 保留原版反胃（CONFUSION）驱动的 {@code spinningEffectIntensity} 扭曲与透明度逻辑不变，
 * 仅把 {@code Gui.renderPortalOverlay} 中获取叠加贴图用的原版下界传送门方块，
 * 重定向为染梦世界传送门，使屏幕叠加层使用染梦世界的粉色门材质。
 */
@Mixin(Gui.class)
public class GuiPortalOverlayMixin {

    @Redirect(
            method = "renderPortalOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/block/BlockModelShaper;getParticleIcon(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"
            )
    )
    private TextureAtlasSprite pasterdream$useDyedreamPortalIcon(BlockModelShaper shaper, BlockState state) {
        return shaper.getParticleIcon(ModBlocks.DYEDREAM_WORLD_PORTAL.get().defaultBlockState());
    }
}
