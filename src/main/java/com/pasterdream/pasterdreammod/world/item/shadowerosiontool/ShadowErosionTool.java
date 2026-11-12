package com.pasterdream.pasterdreammod.world.item.shadowerosiontool;

/**
 * 标记接口：影蚀系列工具，用于在事件中统一识别这五件工具。
 * <p>
 * 公共 tooltip 由 {@code mixin/ItemStackSharedToolBehaviorMixin} 按 instanceof 分支统一派发；
 * 主手属性修正（{@code getAttributeModifiers}）仍由各工具类覆写。
 * <p>
 * 不承载 default 方法：接口 default 既压不过父类的具体方法（JLS「class wins」），
 * 也无法让一件工具同时叠加多个系列的被动（多个同签名 default 会冲突）。
 */
public interface ShadowErosionTool {
}
