package com.pasterdream.pasterdreammod.world.item.moltengoldtool;

/**
 * 炙焰金工具系列的标记接口。
 * <p>
 * 用于统一识别「炙焰金工具」（剑/镐/斧/锹/锄），通过 {@code item instanceof MoltenGoldTool} 判定。
 * 共享行为（右键急迫、tooltip）已抽取到 {@link MoltenGoldToolHelper}，本接口不承载方法，
 * 因为接口 default 方法无法覆盖父类（各工具基类）的具体方法。
 */
public interface MoltenGoldTool {
}
