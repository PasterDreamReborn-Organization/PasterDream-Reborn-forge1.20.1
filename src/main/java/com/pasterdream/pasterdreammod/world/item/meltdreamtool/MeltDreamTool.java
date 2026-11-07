package com.pasterdream.pasterdreammod.world.item.meltdreamtool;

/**
 * 融梦水晶工具系列的标记接口。
 * <p>
 * 用于统一识别「融梦工具」（剑/镐/斧/锹/锄/弓），通过 {@code item instanceof MeltDreamTool} 判定。
 * 共享行为（融梦修补、tooltip）已抽取到 {@link MeltDreamToolHelper}，本接口不承载方法，
 * 因为接口 default 方法无法覆盖父类（各工具基类）的具体方法。
 */
public interface MeltDreamTool {
}
