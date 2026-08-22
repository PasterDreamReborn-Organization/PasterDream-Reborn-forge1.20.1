package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.mixin.BossHealthOverlayAccessor;
import com.pasterdream.pasterdreammod.world.entity.AaroncosLeftHandEntity;
import com.pasterdream.pasterdreammod.world.entity.AaroncosRightHandEntity;
import com.pasterdream.pasterdreammod.world.entity.WindKnightEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * 共享的自定义 BOSS 条布局分配器。所有自定义 BOSS 条通过本类获取纵向槽位，
 * 保证与原版 BOSS 条以及彼此之间互不重叠。
 * 每个槽位占 19px（10 + 9），原版条从 y=12 开始，自定义条排在其下方。
 */
public class ModBossBarLayout {

    /** 自定义 BOSS 条类型，按优先级排列。 */
    public enum BossBarSlot {
        AARONCOS_HANDS,
        WIND_KNIGHT
    }

    private static final List<BossBarSlot> PRIORITY = List.of(
            BossBarSlot.AARONCOS_HANDS,
            BossBarSlot.WIND_KNIGHT
    );

    /** 原版 BOSS 条下方第一条的基准 y 坐标。 */
    public static int baseY() {
        var bossOverlay = Minecraft.getInstance().gui.getBossOverlay();
        int vanillaCount = ((BossHealthOverlayAccessor) bossOverlay).pasterdream$getEvents().size();
        return 12 + vanillaCount * 19;
    }

    /** 指定类型当前应处的槽位（0 起），若未激活返回 -1。 */
    public static int slot(ClientLevel level, BossBarSlot target) {
        return activeSlots(level).indexOf(target);
    }

    /** 当前活跃的自定义 BOSS 条列表（按优先级排序）。 */
    public static List<BossBarSlot> activeSlots(ClientLevel level) {
        boolean aaroncos = false;
        boolean windKnight = false;
        for (var entity : level.entitiesForRendering()) {
            if (!aaroncos && (entity instanceof AaroncosLeftHandEntity || entity instanceof AaroncosRightHandEntity) && entity.isAlive()) {
                aaroncos = true;
            } else if (!windKnight && entity instanceof WindKnightEntity && entity.isAlive()) {
                windKnight = true;
            }
            if (aaroncos && windKnight)
                break;
        }

        List<BossBarSlot> result = new ArrayList<>();
        for (BossBarSlot slot : PRIORITY) {
            boolean active = switch (slot) {
                case AARONCOS_HANDS -> aaroncos;
                case WIND_KNIGHT -> windKnight;
            };
            if (active)
                result.add(slot);
        }
        return result;
    }
}