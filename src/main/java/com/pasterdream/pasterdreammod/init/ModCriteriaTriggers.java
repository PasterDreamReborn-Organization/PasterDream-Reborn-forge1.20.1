package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.advancement.critereon.EatGalaxyJellyAtHeightTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.FoundDesertFortressTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.FoundTombTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.HasAdvancementTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.LookAtPinkSheepTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.NewStandardSwordDrawingTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.OpenDyedreamCrystalChestTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.ReadDreamNoteTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.UseBoneNeedleTrigger;
import net.minecraft.advancements.CriteriaTriggers;

/**
 * 自定义进度触发器注册中心。
 * 在此声明所有自定义 CriterionTrigger 实例并在 {@link #init()} 中统一注册。
 */
public class ModCriteriaTriggers {

    /** 在梦维度中使用苍白骨针（支持普通使用 / 坠落使用两种条件） */
    public static final UseBoneNeedleTrigger USE_BONE_NEEDLE = new UseBoneNeedleTrigger();

    /** 进入失落剑冢结构 */
    public static final FoundTombTrigger FOUND_TOMB = new FoundTombTrigger();

    /** 新概念拔剑 */
    public static final NewStandardSwordDrawingTrigger NEW_STANDARD_SWORD_DRAWING = new NewStandardSwordDrawingTrigger();

    /** 阅读特定内容的寻梦者笔记 */
    public static final ReadDreamNoteTrigger READ_DREAM_NOTE = new ReadDreamNoteTrigger();

    /** 进入沙漠堡垒结构 */
    public static final FoundDesertFortressTrigger FOUND_DESERT_FORTRESS = new FoundDesertFortressTrigger();

    /** 在指定高度以上食用星河果冻 */
    public static final EatGalaxyJellyAtHeightTrigger EAT_GALAXY_JELLY_AT_HEIGHT = new EatGalaxyJellyAtHeightTrigger();

    /** 在染梦维度准星指向粉色羊 */
    public static final LookAtPinkSheepTrigger LOOK_AT_PINK_SHEEP = new LookAtPinkSheepTrigger();

    /** 在染梦世界打开融梦水晶箱 */
    public static final OpenDyedreamCrystalChestTrigger OPEN_DYEDREAM_CRYSTAL_CHEST = new OpenDyedreamCrystalChestTrigger();

    /** 玩家已完成指定进度（前置进度检查） */
    public static final HasAdvancementTrigger HAS_ADVANCEMENT = new HasAdvancementTrigger();

    public static void init() {
        CriteriaTriggers.register(USE_BONE_NEEDLE);
        CriteriaTriggers.register(FOUND_TOMB);
        CriteriaTriggers.register(NEW_STANDARD_SWORD_DRAWING);
        CriteriaTriggers.register(READ_DREAM_NOTE);
        CriteriaTriggers.register(FOUND_DESERT_FORTRESS);
        CriteriaTriggers.register(EAT_GALAXY_JELLY_AT_HEIGHT);
        CriteriaTriggers.register(LOOK_AT_PINK_SHEEP);
        CriteriaTriggers.register(OPEN_DYEDREAM_CRYSTAL_CHEST);
        CriteriaTriggers.register(HAS_ADVANCEMENT);
    }
}
