package com.pasterdream.pasterdreammod.world.item.meltdreamtool;

/**
 * 融梦水晶工具系列的标记接口。
 * <p>
 * 用于统一识别「融梦工具」（剑/镐/斧/锹/锄/弓），通过 {@code item instanceof MeltDreamTool} 判定。
 * 共享行为（融梦修补、公共 tooltip）由 {@code mixin/ItemSharedToolBehaviorMixin} 与
 * {@code mixin/ItemStackSharedToolBehaviorMixin} 按独立 instanceof 分支统一派发，具体工具类无需再写
 * {@code inventoryTick} / {@code appendHoverText} override。
 * <p>
 * 不承载 default 方法：接口 default 既压不过父类的具体方法（JLS「class wins」），
 * 也无法让一件工具同时叠加多个系列的被动（多个同签名 default 会冲突）。
 */
public interface MeltDreamTool {
}
