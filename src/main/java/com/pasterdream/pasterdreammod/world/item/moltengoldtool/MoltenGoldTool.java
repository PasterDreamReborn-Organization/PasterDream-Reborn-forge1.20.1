package com.pasterdream.pasterdreammod.world.item.moltengoldtool;

/**
 * 炙焰金工具系列的标记接口。
 * <p>
 * 用于统一识别「炙焰金工具」（剑/镐/斧/锹/锄），通过 {@code item instanceof MoltenGoldTool} 判定。
 * 公共 tooltip 由 {@code mixin/ItemStackSharedToolBehaviorMixin} 按 instanceof 分支统一派发；
 * 右键急迫战技仍由各工具类覆写 {@code use} 实现（炙焰金剑没有该战技，mixin 中对剑做了排除）。
 * <p>
 * 不承载 default 方法：接口 default 既压不过父类的具体方法（JLS「class wins」），
 * 也无法让一件工具同时叠加多个系列的被动（多个同签名 default 会冲突）。
 */
public interface MoltenGoldTool {
}
