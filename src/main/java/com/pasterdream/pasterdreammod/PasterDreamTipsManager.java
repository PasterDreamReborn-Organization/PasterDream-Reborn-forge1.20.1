package com.pasterdream.pasterdreammod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 加载界面 TIPS 数据管理器（双端通用）。
 * <p>
 * 默认包含原模组的 22 条中文 TIPS，可通过 {@link #addTip(String)} 等方法在运行时修改。
 * KubeJS 兼容层通过 {@code PasterDreamTips} 绑定暴露此 API。
 */
public class PasterDreamTipsManager {

    public static final PasterDreamTipsManager INSTANCE = new PasterDreamTipsManager();

    private static final List<String> DEFAULT_TIPS = List.of(
            "默认按[C]键使用瞬身术！",
            "瞬身术有一段极短的回避无敌帧，利用好它！",
            "融梦能量条和精神值条的位置可以在配置文件调整！",
            "厚翅甲虫会后空翻，前提是你得给它取个特别的名字！",
            "幸运值是真实有用的！",
            "海岸会刷新一些渔民小屋",
            "在特定的群系和环境下，可以在海洋里钓出来一些深海的宝藏！",
            "遗迹可不会把箱子摆到特别明显的位置...你应该仔细寻找！",
            "有什么不懂的就去查MC百科吧！",
            "我会一直更新这个模组！直到...",
            "反馈模组bug请给开发者看崩溃/游戏日志！",
            "拜托朋友，开心起来  你真的很棒！",
            "珍惜那些爱你的人！",
            "去试试魔法金属吧！",
            "去试试极光幽境吧！",
            "咩咩狼的尾巴有多长？",
            "想来一起开发帕斯特之梦吗？",
            "想不想在帕斯特里留下自己的遗迹建筑呢？来试试看吧！",
            "琴雨梦是我的赛博亲女儿！",
            "琴雨梦敲可爱！",
            "幼幼紫也敲可爱！",
            "生日是2002/11/28！",
            // RE
            "本模组不包含任何bug，例如浮空草一类的均为游戏特性（）",
            "染梦世界是没有危险的…除非你非得去作死。",
            "极光幽境2：孩子们问了吗？",
            "本模组部分代码是某个白狐狸拿扬声器吼出来的。",
            "请对琴雨梦玩偶好一点。",
            "AAA急需美工",
            "Give me this shock to the heart. ",
            "I believe in my dream！",
            "旧梦归引会给你很大的帮助！",
            "你知道你其实是可以不拔剑的吗？",
            "本重制版已击毙盲肠人逆天代码。",
            "请不要吃过多星河果冻，除非你想被摔成粉末。",
            "/kill @e[type=minecraft:bat]",
            "人类坠入梦境。",
            "你知道站在营火边可以让你恢复san吗？",
            "部分食物会给予恢复san的增益。"
    );

    private final List<String> customTips = new ArrayList<>();

    private PasterDreamTipsManager() {}

    /**
     * 返回当前有效的 TIPS 列表。
     * 如果有自定义 TIPS（通过 KubeJS 等途径添加），返回自定义列表；
     * 否则返回默认列表。
     */
    public List<String> getActiveTips() {
        return customTips.isEmpty() ? DEFAULT_TIPS : Collections.unmodifiableList(customTips);
    }

    /** 返回原模组的默认 TIPS（不可修改）。 */
    public List<String> getDefaultTips() {
        return DEFAULT_TIPS;
    }

    /** 返回自定义 TIPS 的可变列表（供外部直接操作）。 */
    public List<String> getCustomTips() {
        return customTips;
    }

    /** 添加一条自定义 TIP。 */
    public void addTip(String tip) {
        customTips.add(tip);
    }

    /** 通过索引移除自定义 TIP。 */
    public void removeTip(int index) {
        if (index >= 0 && index < customTips.size()) {
            customTips.remove(index);
        }
    }

    /** 清空所有自定义 TIPS（之后将回退到默认 TIPS）。 */
    public void clearCustomTips() {
        customTips.clear();
    }

    /** 等同 {@link #clearCustomTips()}，语义上表示"恢复默认"。 */
    public void resetToDefaults() {
        customTips.clear();
    }

    /** 从当前有效列表中随机选取一条 TIP。 */
    public String getRandomTip(Random random) {
        List<String> tips = getActiveTips();
        return tips.get(random.nextInt(tips.size()));
    }
}
