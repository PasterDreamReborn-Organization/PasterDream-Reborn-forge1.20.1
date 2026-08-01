package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.common.Mod;

/**
 * 自定义游戏规则注册。
 * 类加载时通过静态字段初始化器自动向 {@link GameRules} 注册所有规则。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModGameRules {

    /**
     * 世界暗影难度：实体属性缩放 / 无玩家上下文时的回退值。
     * 此版本 API 不支持创建世界菜单的数值边界，但运行时 ShadowDifficultyHelper 会自动钳制到 0-3。
     */
    public static final GameRules.Key<GameRules.IntegerValue> SHADOW_DIFFICULTY =
            GameRules.register("shadowDifficulty", GameRules.Category.MISC,
                    GameRules.IntegerValue.create(1));

    /**
     * 默认玩家暗影难度：新玩家没有个人覆盖时使用此值。
     * 此版本 API 不支持创建世界菜单的数值边界，但运行时 ShadowDifficultyHelper 会自动钳制到 0-3。
     */
    public static final GameRules.Key<GameRules.IntegerValue> PLAYER_SHADOW_DIFFICULTY =
            GameRules.register("playerShadowDifficulty", GameRules.Category.MISC,
                    GameRules.IntegerValue.create(1));
}
