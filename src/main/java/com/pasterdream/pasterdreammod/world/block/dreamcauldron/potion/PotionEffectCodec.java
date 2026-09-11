package com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion;

import com.pasterdream.pasterdreammod.helper.potionhelper.GenericMobEffect;
import com.pasterdream.pasterdreammod.helper.potionhelper.PotionHelper;
import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 成品药水流体编解码 —— 完全走原模组 PasterDream-Reborn 体系。
 * 效果存于流体 Tag 的 "EffectList:[{id,lvl,time}]"：
 * 构造用 {@link PotionHelper#createNBTPotion}，解析用 {@link PotionHelper#getEffectType}，
 * 与原模组配方 JSON、釜 GUI 染色、灵药瓶饮用完全互通。
 * NBT 中每条效果 time 即"喝一口"的单次时长。
 */
public final class PotionEffectCodec
{
    private PotionEffectCodec() {}

    /** 读取成品药水全部效果；非药水/无 EffectList 返回空列表 */
    public static List<MobEffectInstance> readFluidEffects(FluidStack stack)
    {
        if (stack == null || stack.isEmpty()) return List.of();
        List<MobEffectInstance> effects = new ArrayList<>();
        for (GenericMobEffect g : PotionHelper.getEffectType(stack))
        {
            if (g.effectType() != null)
            {
                effects.add(GenericMobEffect.fromGenericMobEffectToEffectInstance(g));
            }
        }
        return effects;
    }

    /** 构造成品药水流体（原模组 PotionHelper 生成，EffectList 逐字节一致）。effects 为空时原模组返回水，调用方须先保证非空 */
    public static FluidStack createPotionFluid(int amountMb, List<MobEffectInstance> effects)
    {
        List<GenericMobEffect> generic =
                GenericMobEffect.fromListMobEffectInstanceToListGenericMobEffect(effects);
        return PotionHelper.createNBTPotion(generic, amountMb);
    }

    /** 成品药水判定：必须为原模组 potion 流体且 EffectList 非空 */
    public static boolean isPotionFluid(FluidStack stack)
    {
        if (stack == null || stack.isEmpty() || stack.getFluid() != ModFluids.POTION.get())
        {
            return false;
        }
        return !PotionHelper.getEffectType(stack).isEmpty();
    }
}
