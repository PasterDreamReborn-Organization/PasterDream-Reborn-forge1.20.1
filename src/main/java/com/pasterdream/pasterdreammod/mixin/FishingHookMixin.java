package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 让熔融阴影（shadow_liquid）在不拥有 minecraft:water 标签的情况下也能钓鱼。
 * <p>
 * 原理：在 FishingHook 的流体检查点，当检查 FluidTags.WATER 时，
 * 将熔融阴影也视为"符合条件"的流体。
 * @see FishingHook#tick()
 */
@Mixin(FishingHook.class)
public abstract class FishingHookMixin {

    /**
     * 判断流体是否是熔融阴影（源或流动形态皆可）
     */
    private static boolean isShadowLiquid(FluidState state) {
        return state.getType() == ModFluids.SHADOW_LIQUID.get()
            || state.getType() == ModFluids.FLOWING_SHADOW_LIQUID.get();
    }

    /**
     * 带标签检查的流体判定：在检查 FluidTags.WATER 时，熔融阴影也返回 true
     */
    private static boolean isFishableFluid(FluidState state, TagKey<Fluid> tag) {
        if (tag == FluidTags.WATER && isShadowLiquid(state)) {
            return true;
        }
        return state.is(tag);
    }

    // ===================== tick() 流体检查 =====================
    //
    // tick() 中有多处 fluidState.is(FluidTags.WATER) 调用：
    //   ordinal 0 — 鱼漂进入流体（FLYING → BOBBING 状态转移）
    //   ordinal 1 — 鱼漂在流体中停留、气泡粒子生成
    //
    // 如果运行时因 ordinal 不存在而报错，可通过 require = 0 安全跳过，
    // 也可根据实际字节码增加更多 ordinal。

    @Redirect(method = "tick",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z",
            ordinal = 0))
    private boolean pasterdream$tickWaterCheck0(FluidState instance, TagKey<Fluid> tag) {
        return isFishableFluid(instance, tag);
    }

    @Redirect(method = "tick",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z",
            ordinal = 1))
    private boolean pasterdream$tickWaterCheck1(FluidState instance, TagKey<Fluid> tag) {
        return isFishableFluid(instance, tag);
    }

    // ===================== getOpenWaterTypeForBlock 开阔水域检查 =====================
    //
    // 原版逻辑：
    //   fluidstate.is(FluidTags.WATER) && fluidstate.isSource() && 碰撞箱为空
    //     → INSIDE_WATER（算开阔水域）
    //
    // 此处仅拦截 .is(FluidTags.WATER) 部分，.isSource() 等其他条件照常生效。

    @Redirect(method = "getOpenWaterTypeForBlock",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean pasterdream$openWaterFluidCheck(FluidState instance, TagKey<Fluid> tag) {
        return isFishableFluid(instance, tag);
    }
}
