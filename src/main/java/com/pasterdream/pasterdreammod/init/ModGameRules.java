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

    public static final GameRules.Key<GameRules.IntegerValue> SHADOW_DIFFICULTY =
            GameRules.register("shadowDifficulty", GameRules.Category.MISC,
                    GameRules.IntegerValue.create(1));
}
