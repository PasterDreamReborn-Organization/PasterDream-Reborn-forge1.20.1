package com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

/**
 * 法术工厂（融梦釜）药水模块使用的流体标签。
 * 与物品标签同理：数据包可在对应 fluid tag 追加任意流体，
 * JEI 液体槽会自动遍历该标签内全部流体（连同设定量/效果 NBT）循环轮换展示。
 * 默认内置映射（见数据生成 ModFluidTagsProvider）：
 *   - cauldron_spring = 融梦泉涌（pasterdream:melt_dream_liquid）→ 右槽泉涌输入
 *   - cauldron_potion = 成品药水（pasterdream:potion）→ 左槽基底药水输入
 */
public final class CauldronPotionFluidTags
{
    private CauldronPotionFluidTags() {}

    private static TagKey<Fluid> tag(String path)
    {
        return TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path));
    }

    /** 右槽融梦泉涌输入（JEI 轮换展示） */
    public static final TagKey<Fluid> SPRING = tag("cauldron_spring");

    /** 左槽基底成品药水输入（JEI 轮换展示） */
    public static final TagKey<Fluid> POTION = tag("cauldron_potion");
}
