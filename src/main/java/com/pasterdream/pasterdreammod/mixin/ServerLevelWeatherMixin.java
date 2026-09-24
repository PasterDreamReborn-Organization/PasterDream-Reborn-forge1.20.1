package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.dimension.LampShadowDimension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 1.20.1 中非主世界维度的 {@code ServerLevelData} 是 {@code DerivedLevelData}：天气 setter
 * （setRaining/setRainTime/setThunderTime/setThundering/setClearWeatherTime）均为空实现，getter 读取主世界，
 * 天气实际只在主世界存一份。因此 {@code /weather} 在自定义维度执行时（对命令来源所在维度调用
 * {@code setWeatherParameters}）改不动天气。
 * <p>
 * {@code setWeatherParameters} 只被 {@code /weather} 指令与 GameTest 调用，不在每 tick 的
 * {@code advanceWeatherCycle}/{@code resetWeatherCycle} 中（后者逐 tick 调用的是单个 setter），
 * 所以在这里把非主世界维度的设置转发到主世界即可，不会造成天气计时多倍递减。
 */
@Mixin(ServerLevel.class)
public class ServerLevelWeatherMixin {

    @Inject(method = "setWeatherParameters", at = @At("HEAD"), cancellable = true)
    private void pasterdream$applyWeatherGlobally(int clearTime, int rainTime, boolean raining,
                                                  boolean thundering, CallbackInfo ci) {
        ServerLevel self = (ServerLevel) (Object) this;
        MinecraftServer server = self.getServer();
        ServerLevel overworld = server == null ? null : server.overworld();
        if (overworld != null && overworld != self) {
            overworld.setWeatherParameters(clearTime, rainTime, raining, thundering);
            ci.cancel();
        }
    }

    /**
     * 原版 {@code advanceWeatherCycle} 仅在 {@code dimensionType().hasSkyLight()} 为真时才推进
     * 降雨/雷暴等级；否则 {@code rainLevel} 恒为 0，客户端永远收不到降雨等级、也就下不了雨。
     * 灯影之下为了保持漆黑刻意关闭了天光（hasSkyLight=false），因此需要在此放行天气推进。
     * 该维度的 {@code serverLevelData} 是 DerivedLevelData（写入为空实现、读取主世界），
     * 所以放行后只会镜像主世界天气，不会产生独立的天气循环。
     */
    @Redirect(method = "advanceWeatherCycle", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/dimension/DimensionType;hasSkyLight()Z"))
    private boolean pasterdream$allowWeatherWithoutSkyLight(DimensionType dimensionType) {
        if (dimensionType.hasSkyLight()) {
            return true;
        }
        ServerLevel self = (ServerLevel) (Object) this;
        return self.dimension().equals(LampShadowDimension.LAMP_SHADOW_WORLD);
    }
}
