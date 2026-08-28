package com.pasterdream.pasterdreammod.helper.potionhelper;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class PotionHelper
{
    //从流体NBT中读取effect效果
    public static List<GenericMobEffect> getEffectType(FluidStack fluidStack)
    {
        List<GenericMobEffect> mobEffectList = new ArrayList<>();

        CompoundTag nbt = fluidStack.getTag();

        if (nbt != null && nbt.contains("EffectList"))
        {
            ListTag effectList = nbt.getList("EffectList", Tag.TAG_COMPOUND);

            for(int i = 0; i < effectList.size(); i++)
            {
                CompoundTag effect = effectList.getCompound(i);
                String id = effect.getString("id");
                MobEffect effectType = BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.parse(id));
                int level = effect.getInt("lvl");
                int time = effect.getInt("time");
                mobEffectList.add(new GenericMobEffect(effectType, level, time));
            }
        }
            else
            {
                return new ArrayList<>();
            }

        return mobEffectList;
    }

    //构建药水名称
    public static Component getPotionName(List<GenericMobEffect> effectList)
    {
        if (effectList.isEmpty())
        {
            return Component.literal("null").append(Component.translatable("fluid.pasterdream.potion"));
        }

        MutableComponent result = Component.empty();
        for (GenericMobEffect effect : effectList)
        {
            result.append(formatTime(effect.time()));
            result.append(Component.translatable(effect.effectType().getDescriptionId()));
            if(effect.level() != 0)
            {
                result.append(Component.translatable("enchantment.level." + (effect.level() + 1)));
            }
            result.append(Component.literal(" - "));
        }

        result.append(Component.translatable("fluid.pasterdream.potion"));
        return result;
    }

    private static String formatTime(int ticks)
    {
        if (ticks == 0)
        {
            return "";
        }
        else
            if (ticks < 72000)
            {
                return (String.format("%02d", (ticks / 1200)) + ":" + String.format("%02d", ((ticks % 1200) / 20)));
            }
                else
                {
                    return (String.format("%02d", (ticks / 72000)) + ":" + String.format("%02d", ((ticks % 72000) / 1200)) + ":" + String.format("%02d", ((ticks % 1200) / 20)));
                }
    }

    public static int getMixingPotionColor(List<GenericMobEffect> effectList)
    {
        long totalRed = 0;
        long totalGreen = 0;
        long totalBlue = 0;
        int totalLevel = 0;

        for(GenericMobEffect effect : effectList)
        {
            int singleColor = effect.effectType().getColor();

            int level = effect.level() + 1;
            totalLevel += level;
            totalRed += (long)(singleColor & 0x00FF0000) * level;
            totalGreen += (long)(singleColor & 0x0000FF00) * level;
            totalBlue += (long)(singleColor & 0x000000FF) * level;
        }

        return effectList.isEmpty() ? 0xFF000000 : 0xFF000000 | (int)(totalRed / totalLevel) + (int)(totalGreen / totalLevel) + (int)(totalBlue / totalLevel);
    }

    public static FluidStack createNBTPotion(List<GenericMobEffect> effectList, int amount)
    {
        ListTag listEffect = new ListTag();

        for (GenericMobEffect effect : effectList)
        {
            CompoundTag singleEffect = new CompoundTag();

            singleEffect.putString("id", String.valueOf(BuiltInRegistries.MOB_EFFECT.getKey(effect.effectType())));
            singleEffect.putInt("lvl", effect.level());
            singleEffect.putInt("time", effect.time());

            listEffect.add(singleEffect);
        }

        CompoundTag NBT = new CompoundTag();
        NBT.put("EffectList", listEffect);

        FluidStack fluidStack = new FluidStack(ModFluids.POTION.get(), amount);
        fluidStack.setTag(NBT);

        return fluidStack;
    }
}
