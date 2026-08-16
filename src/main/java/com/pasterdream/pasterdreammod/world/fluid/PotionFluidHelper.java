package com.pasterdream.pasterdreammod.world.fluid;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

/**
 * 通用「药水」流体的构建与读取。
 * <p>
 * 流体 NBT 同时记录药水 id（"Potion"）与效果列表（"CustomPotionEffects"，与原版 PotionUtils 约定一致），
 * 使流体自描述其药水与效果。
 */
public final class PotionFluidHelper
{
    public static final String TAG_POTION = "Potion";
    public static final String TAG_EFFECTS = "CustomPotionEffects";

    private PotionFluidHelper() {}

    /** 由药水构建药水流体堆，NBT 记录药水 id 与效果列表 */
    public static FluidStack createStack(Potion potion, int amount)
    {
        FluidStack stack = new FluidStack(ModFluids.POTION.get(), amount);
        ResourceLocation key = BuiltInRegistries.POTION.getKey(potion);
        if (key != null)
        {
            stack.getOrCreateTag().putString(TAG_POTION, key.toString());
        }
        ListTag effects = new ListTag();
        for (MobEffectInstance effect : potion.getEffects())
        {
            effects.add(effect.save(new CompoundTag()));
        }
        if (!effects.isEmpty())
        {
            stack.getOrCreateTag().put(TAG_EFFECTS, effects);
        }
        return stack;
    }

    /** 从流体 NBT 读取药水，缺失或未知返回 null */
    @Nullable
    public static Potion getPotion(FluidStack stack)
    {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(TAG_POTION))
        {
            return null;
        }
        ResourceLocation key = ResourceLocation.tryParse(tag.getString(TAG_POTION));
        if (key == null || !BuiltInRegistries.POTION.containsKey(key))
        {
            return null;
        }
        return BuiltInRegistries.POTION.get(key);
    }
}
