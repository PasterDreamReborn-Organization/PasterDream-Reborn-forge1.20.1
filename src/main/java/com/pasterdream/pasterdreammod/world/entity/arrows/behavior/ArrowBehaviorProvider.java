package com.pasterdream.pasterdreammod.world.entity.arrows.behavior;

/**
 * 由自定义箭矢物品实现，用于把物品与其 {@link ArrowBehavior} 关联。
 * <p>
 * 武器强化拖尾箭只持有弹药 {@code ItemStack}，通过 {@link ArrowBehaviors#get(net.minecraft.world.item.ItemStack)}
 * 解析出对应行为，从而在替换实体类后仍能复现箭矢特性。
 */
public interface ArrowBehaviorProvider {

    /** 本箭矢对应的行为。 */
    ArrowBehavior arrowBehavior();
}
