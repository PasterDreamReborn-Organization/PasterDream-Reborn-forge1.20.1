package com.pasterdream.pasterdreammod.helper.potionhelper;

import com.pasterdream.pasterdreammod.helper.fluidcontainercapability.FluidContainerRegistry;
import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.material.Fluids;
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

    public static String formatTime(int ticks)
    {
        if (ticks == 0 || ticks == 1)
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

        if (listEffect.isEmpty())
        {
            return new FluidStack(Fluids.WATER, amount);
        }

        CompoundTag NBT = new CompoundTag();
        NBT.put("EffectList", listEffect);

        FluidStack fluidStack = new FluidStack(ModFluids.POTION.get(), amount);
        fluidStack.setTag(NBT);

        return fluidStack;
    }

    public static List<GenericMobEffect> getListGenericMobEffectFromPotion(ItemStack itemStack)
    {
        Potion potion = PotionUtils.getPotion(itemStack);
        List<MobEffectInstance> potionEffects = (potion == Potions.EMPTY ? PotionUtils.getCustomEffects(itemStack) : potion.getEffects());
        return GenericMobEffect.fromListMobEffectInstanceToListGenericMobEffect(potionEffects);
    }

    public static List<GenericMobEffect> getListGenericMobEffectFromPotion(Potion potion)
    {
        List<MobEffectInstance> potionEffects = potion.getEffects();
        return GenericMobEffect.fromListMobEffectInstanceToListGenericMobEffect(potionEffects);
    }

    public static Potion getPotionFromListGenericMobEffect(List<GenericMobEffect> effectList)
    {
        for (Potion potion : BuiltInRegistries.POTION)
        {
            if (getListGenericMobEffectFromPotion(potion).equals(effectList))
            {
                return potion;
            }
        }

        return null;
    }

    public static ItemStack getCustomEffectPotion(List<GenericMobEffect> effectList)
    {
        List<MobEffectInstance> potionEffects = new ArrayList<>();
        for(GenericMobEffect effect : effectList)
        {
            potionEffects.add(GenericMobEffect.fromGenericMobEffectToEffectInstance(effect));
        }

        return PotionUtils.setCustomEffects(new ItemStack(Items.POTION), potionEffects);
    }

    public static ItemStack getWaterBottle()
    {
        ItemStack waterBottle = new ItemStack(Items.POTION);
        CompoundTag NBT = new CompoundTag();
        NBT.putString("Potion", "minecraft:water");
        waterBottle.setTag(NBT);

        return waterBottle;
    }

    /** 等级 → 罗马数字（原版 potency 语言键只覆盖到 V，更高等级需自行生成） */
    private static final int[] ROMAN_VALUES = {100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] ROMAN_SYMBOLS =
            {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static String toRoman(int level)
    {
        if (level <= 0)
        {
            return String.valueOf(level);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ROMAN_VALUES.length; i++)
        {
            while (level >= ROMAN_VALUES[i])
            {
                stringBuilder.append(ROMAN_SYMBOLS[i]);
                level -= ROMAN_VALUES[i];
            }
        }
        return stringBuilder.toString();
    }
}
