package com.pasterdream.pasterdreammod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 1.20.1 中非主世界维度的 {@code ServerLevelData} 是 {@code DerivedLevelData}：
 * 其 {@code setDayTime} 为空实现、{@code getDayTime} 直接读取主世界数据。因此 {@code ServerLevel#tick}
 * 里「睡醒后跳过夜晚」的 {@code this.setDayTime(...)} 在染梦世界等具有昼夜循环的模组维度中完全无效，
 * 导致在这些维度睡觉时时间不会改变。
 * <p>
 * 这里只把 tick 中该处 setDayTime 重定向到主世界（全局时间持有者），使跳夜真正生效；
 * 不改动 {@code tickTime()} 的每 tick 递增，否则每个非主世界维度都会再加一次时间。
 */
@Mixin(ServerLevel.class)
public class ServerLevelSleepTimeMixin {

    @WrapOperation(method = "tick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V"))
    private void pasterdream$applySleepTimeGlobally(ServerLevel level, long time, Operation<Void> original) {
        MinecraftServer server = level.getServer();
        ServerLevel overworld = server == null ? null : server.overworld();
        if (overworld != null) {
            overworld.setDayTime(time);
        } else {
            original.call(level, time);
        }
    }
}
