package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

/**
 * 萦风合金系列标记接口：剑 / 斧 / 镐 / 锹 / 锄 与萦风雷矛均实现本接口，统一获得「疾风过境」被动：
 * 攻击附带雷电伤害、移动速度越快挖掘与攻击越高、破坏方块或击杀生物时掉落物被风卷至身边
 * （分别由 {@link FluffyWindAlloyAttackHandler}、{@code FluffyWindAlloyMiningSpeedHandler}、
 * {@code FluffyWindAlloyDropSweepHandler} 结算；公共词条由 {@code ItemStackSharedToolBehaviorMixin} 追加）。
 * <p>
 * 不承载 default 方法：接口 default 既压不过父类的具体方法（JLS「class wins」），
 * 也无法让一件工具同时叠加多个系列的被动（多个同签名 default 会冲突）。
 */
public interface FluffyWindAlloyTool {
}
