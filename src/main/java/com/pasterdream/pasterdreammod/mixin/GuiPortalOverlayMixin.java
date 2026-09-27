package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 传送门屏幕叠加层按门类型换贴图。
 * <p>
 * 保留原版反胃（CONFUSION）驱动的 {@code spinningEffectIntensity} 扭曲与透明度逻辑不变，
 * 仅当玩家实际处于染梦世界传送门内时，把 {@code Gui.renderPortalOverlay} 获取叠加贴图用的
 * 原版下界传送门方块重定向为染梦世界传送门；进入下界传送门时仍使用原版材质。
 * <p>
 * {@code spinningEffectIntensity} 在离开门后会渐隐，此时玩家已不在门内，无法再扫描到门方块，
 * 故用 {@link #pasterdream$lastPortalWasDyedream} 记住最近一次进入的门类型，避免渐隐过程中贴图跳变。
 */
@Mixin(Gui.class)
public class GuiPortalOverlayMixin {

    private static boolean pasterdream$lastPortalWasDyedream;

    @Redirect(
            method = "renderPortalOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/block/BlockModelShaper;getParticleIcon(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"
            )
    )
    private TextureAtlasSprite pasterdream$useDyedreamPortalIcon(BlockModelShaper shaper, BlockState state) {
        Boolean dyedream = pasterdream$currentPortalIsDyedream();
        if (dyedream != null) {
            pasterdream$lastPortalWasDyedream = dyedream;
        }
        return pasterdream$lastPortalWasDyedream
                ? shaper.getParticleIcon(ModBlocks.DYEDREAM_WORLD_PORTAL.get().defaultBlockState())
                : shaper.getParticleIcon(state);
    }

    /**
     * 扫描玩家包围盒覆盖的方块以判定所处的传送门类型。
     *
     * @return {@code TRUE} 处于染梦世界传送门；{@code FALSE} 处于下界传送门；{@code null} 未处于任何传送门
     */
    private static Boolean pasterdream$currentPortalIsDyedream() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return null;
        }
        AABB box = player.getBoundingBox().inflate(-1.0E-4);
        BlockPos min = BlockPos.containing(box.minX, box.minY, box.minZ);
        BlockPos max = BlockPos.containing(box.maxX, box.maxY, box.maxZ);
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            BlockState state = player.level().getBlockState(pos);
            if (state.is(ModBlocks.DYEDREAM_WORLD_PORTAL.get())) {
                return Boolean.TRUE;
            }
            if (state.is(Blocks.NETHER_PORTAL)) {
                return Boolean.FALSE;
            }
        }
        return null;
    }
}
