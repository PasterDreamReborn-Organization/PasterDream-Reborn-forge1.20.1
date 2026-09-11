package com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * 酿造配方适配器（对应《需求/法术工厂.md》第二节配方继承规则）。
 * 染梦果 ≈ 原版地狱疣槽位（Awkward 基底转化），槽B催化剂 = 原版效果物，
 * 槽C增强剂 = 红石延长 / 荧石强化。
 * 经 Forge {@link BrewingRecipeRegistry} 统一查询，天然继承原版酿造配方
 * 及任何通过 addRecipe 注册到酿造台 API 的模组配方（不再受 PotionBrewing 原版白名单限制）。
 */
public final class VanillaBrewingAdapter
{
    public enum Enhance { NONE, REDSTONE, GLOWSTONE }

    public enum BrewStatus { OK, INVALID_CATALYST, ENHANCE_UNSUPPORTED }

    /** 酿造结果：OK 时 effects 为最终药水的全部效果 */
    public record BrewOutcome(BrewStatus status, Potion potion, List<MobEffectInstance> effects)
    {
        static BrewOutcome fail(BrewStatus status) { return new BrewOutcome(status, null, List.of()); }
    }

    private VanillaBrewingAdapter() {}

    /**
     * 从 Awkward 基底出发：催化剂 →（可选增强剂）→ 效果药水。
     * 催化剂无法作用（含槽B空/无效果产物）→ INVALID_CATALYST；
     * 催化剂有效但增强剂无对应配方 → ENHANCE_UNSUPPORTED。
     */
    public static BrewOutcome brew(ItemStack catalyst, Enhance enhance)
    {
        if (catalyst == null || catalyst.isEmpty())
        {
            return BrewOutcome.fail(BrewStatus.INVALID_CATALYST);
        }

        ItemStack current = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD);
        ItemStack out = BrewingRecipeRegistry.getOutput(current, catalyst);
        if (out.isEmpty())
        {
            return BrewOutcome.fail(BrewStatus.INVALID_CATALYST);
        }
        current = out;

        if (enhance != Enhance.NONE)
        {
            ItemStack enhancer = enhance == Enhance.REDSTONE
                    ? new ItemStack(Items.REDSTONE)
                    : new ItemStack(Items.GLOWSTONE_DUST);
            ItemStack enhanced = BrewingRecipeRegistry.getOutput(current, enhancer);
            if (enhanced.isEmpty())
            {
                return BrewOutcome.fail(BrewStatus.ENHANCE_UNSUPPORTED);
            }
            current = enhanced;
        }

        Potion potion = PotionUtils.getPotion(current);
        if (potion == null || potion.getEffects().isEmpty())
        {
            return BrewOutcome.fail(BrewStatus.INVALID_CATALYST);
        }
        return new BrewOutcome(BrewStatus.OK, potion, new ArrayList<>(potion.getEffects()));
    }
}
