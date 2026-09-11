package com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe;

import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.PotionEffectCodec;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 让 JEI 按灵药瓶的药水效果区分物品（与原版药水 Elixir 同款机制）。
 *
 * 灵药瓶的所有药都是同一物品 pasterdream:elixir_bottle，JEI 默认忽略 NBT，导致 R 查任意药水
 * 都命中"所有药水的总配方"。给该物品注册 subtype interpreter 后，JEI 会把每种效果不同的药水晶瓶
 * 视为独立条目（并自动在 JEI 物品列表里展开），R 查询精确匹配到"那一瓶药水"对应的酿造配方。
 * 非药水（空瓶 / 泉涌等）返回 NONE，不参与任何药水配方匹配。
 */
public final class ElixirBottleSubtypeInterpreter
        implements IIngredientSubtypeInterpreter<ItemStack>
{
    public static final ElixirBottleSubtypeInterpreter INSTANCE = new ElixirBottleSubtypeInterpreter();

    private ElixirBottleSubtypeInterpreter() {}

    @Override
    public String apply(ItemStack stack, UidContext context)
    {
        FluidStack fs = fluid(stack);
        if (!PotionEffectCodec.isPotionFluid(fs))
        {
            return NONE;
        }
        List<MobEffectInstance> effects = PotionEffectCodec.readFluidEffects(fs);
        if (effects.isEmpty())
        {
            return NONE;
        }
        StringBuilder id = new StringBuilder();
        for (MobEffectInstance inst : effects)
        {
            // 仅按"是哪种效果"区分，忽略等级与时长：不同等级/时长的同一效果药水共享同一配方锚点
            id.append(inst.getEffect().getDescriptionId()).append(';');
        }
        return id.toString();
    }

    /** 读取瓶内液体；无流体能力 / 空瓶时返回 EMPTY（判为非药水 → NONE）。 */
    private static FluidStack fluid(ItemStack stack)
    {
        AtomicReference<FluidStack> ref = new AtomicReference<>(FluidStack.EMPTY);
        stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(h ->
                ref.set(h.getFluidInTank(0)));
        return ref.get();
    }
}
