package com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * 法术工厂（融梦釜）药水模块使用的物品标签。
 * 全部走标签判定，便于玩家在数据包里自定义：把任意物品加入对应 tag 即可充当基底/增强剂。
 * 默认内置映射（见数据生成 ModItemTagsProvider）：
 *   - cauldron_base              = 染梦果（pasterdream:dyedream_fruit）→ 充当 Awkward 基底
 *   - cauldron_enhance_redstone  = 红石（minecraft:redstone）→ 延长
 *   - cauldron_enhance_glowstone = 萤石粉（minecraft:glowstone_dust）→ 强化
 */
public final class CauldronPotionTags
{
    private CauldronPotionTags() {}

    private static TagKey<Item> tag(String path)
    {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path));
    }

    /** 染梦果基底座（相当于原版 Awkward 药水基底） */
    public static final TagKey<Item> BASE = tag("cauldron_base");

    /** 增强剂·延长（默认红石） */
    public static final TagKey<Item> ENHANCE_REDSTONE = tag("cauldron_enhance_redstone");

    /** 增强剂·强化（默认萤石粉） */
    public static final TagKey<Item> ENHANCE_GLOWSTONE = tag("cauldron_enhance_glowstone");

    /** 常规催化剂（槽B）：原版从 Awkward 基底可酿造效果药水的材料（JEI 展示 + 数据包自定义） */
    public static final TagKey<Item> CATALYST = tag("cauldron_catalyst");

    /** 增强剂合并（红石延长 + 萤石强化，JEI 模板页展示用） */
    public static final TagKey<Item> ENHANCE = tag("cauldron_enhance");

    /** 判基底座 */
    public static boolean isBase(ItemStack stack)
    {
        return stack != null && !stack.isEmpty() && stack.is(BASE);
    }

    /** 判延长增强剂（默认红石，可自定义） */
    public static boolean isRedstoneEnhance(ItemStack stack)
    {
        return stack != null && !stack.isEmpty() && stack.is(ENHANCE_REDSTONE);
    }

    /** 判强化增强剂（默认萤石粉，可自定义） */
    public static boolean isGlowstoneEnhance(ItemStack stack)
    {
        return stack != null && !stack.isEmpty() && stack.is(ENHANCE_GLOWSTONE);
    }
}
