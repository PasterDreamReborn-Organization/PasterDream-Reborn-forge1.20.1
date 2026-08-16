package com.pasterdream.pasterdreammod.capability.san;

/**
 * 物品对 SAN（理智）系统的声明式交互。
 * <p>
 * 实现此接口的物品（护甲 / Curios 饰品）由 {@link SanHelper#getEquippedSanModifiers}
 * 统一收集，供 SanAuraHandler 每 tick 修正 SAN 变化率与阈值效果，
 * 避免在系统内硬编码具体物品检测。
 */
public interface ISanModifier {

    /** 是否冻结 SAN 变化（保持上限），如猫耳 */
    default boolean freezesSan() {
        return false;
    }

    /** 修改环境 SAN 变化率（群系 + 光照），返回修改后的值，如白厄花胸针拦截负向 */
    default double modifyEnvRate(double envRate) {
        return envRate;
    }

    /** 是否免疫负面 SAN 阈值效果（不振 / 恍惚 / 疯狂），如堕落者之印 */
    default boolean immuneToNegativeEffects() {
        return false;
    }
}
