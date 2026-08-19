package com.pasterdream.pasterdreammod.helper;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

/**
 * 梦境维度的游戏模式保存/恢复工具。
 *
 * 进入需要切换游戏模式的场景（如亚伦柯斯竞技场改为冒险模式）前，先 {@link #saveAndSetAdventure}
 * 记录玩家原游戏模式；当玩家通过死亡重生、胜利传送或骨针类物品传送离开梦境维度后，
 * 调用 {@link #restorePreDreamGameMode} 恢复原模式，避免玩家被永久卡在冒险模式。
 */
public final class GameModeHelper {
    private GameModeHelper() {}

    private static final String PRE_DREAM_GAMEMODE_KEY = "pasterdream_pre_dream_gamemode";

    /** 记录进入前的原游戏模式（已有记录则保留首次记录），随后切换为冒险模式 */
    public static void saveAndSetAdventure(ServerPlayer player) {
        if (player.getPersistentData().getString(PRE_DREAM_GAMEMODE_KEY).isEmpty()) {
            player.getPersistentData().putString(PRE_DREAM_GAMEMODE_KEY,
                    player.gameMode.getGameModeForPlayer().getName());
        }
        player.setGameMode(GameType.ADVENTURE);
    }

    /** 恢复进入梦境前的原游戏模式；无记录则不动作，返回是否恢复 */
    public static boolean restorePreDreamGameMode(ServerPlayer player) {
        String name = player.getPersistentData().getString(PRE_DREAM_GAMEMODE_KEY);
        if (name.isEmpty())
            return false;
        player.setGameMode(GameType.byName(name));
        player.getPersistentData().remove(PRE_DREAM_GAMEMODE_KEY);
        return true;
    }

    /**
     * 玩家死亡重生时新实体不会继承 persistentData（游戏模式本身会被 restoreFrom 继承，
     * 但本 key 不在其列）。此方法把记录从旧实体转移到新实体并立即恢复，避免被永久卡在冒险模式。
     */
    public static void handlePlayerDeathClone(Player original, Player newPlayer) {
        String saved = original.getPersistentData().getString(PRE_DREAM_GAMEMODE_KEY);
        if (saved.isEmpty())
            return;
        original.getPersistentData().remove(PRE_DREAM_GAMEMODE_KEY);
        if (newPlayer instanceof ServerPlayer sp) {
            sp.getPersistentData().putString(PRE_DREAM_GAMEMODE_KEY, saved);
            restorePreDreamGameMode(sp);
        }
    }
}

