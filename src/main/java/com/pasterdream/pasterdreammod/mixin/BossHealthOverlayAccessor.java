package com.pasterdream.pasterdreammod.mixin;

import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.UUID;

/**
 * 暴露 BossHealthOverlay 的 events 字段，供客户端 HUD 读取当前活跃的原版 BOSS 条数量，
 * 用于把亚伦柯斯之触组合 BOSS 条排在原版 BOSS 条下方。
 */
@Mixin(BossHealthOverlay.class)
public interface BossHealthOverlayAccessor {

    @Accessor("events")
    Map<UUID, LerpingBossEvent> pasterdream$getEvents();
}
