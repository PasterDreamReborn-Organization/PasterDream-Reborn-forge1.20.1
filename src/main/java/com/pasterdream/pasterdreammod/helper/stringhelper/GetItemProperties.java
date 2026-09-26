package com.pasterdream.pasterdreammod.helper.stringhelper;

import com.pasterdream.pasterdreammod.helper.fluidcontainercapability.FluidContainerRegistry;
import com.pasterdream.pasterdreammod.helper.potionhelper.PotionHelper;
import com.pasterdream.pasterdreammod.world.item.drinkandfooditem.PasterDreamDrinkItem;
import com.pasterdream.pasterdreammod.world.item.drinkandfooditem.PasterDreamFoodItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GetItemProperties
{
    public static String getItemProperties(ItemStack itemStack)
    {
        StringBuilder stringBuilder = new StringBuilder();

        Item item = itemStack.getItem();

        stringBuilder.append(Component.translatable("message.pasterdream.物品名称:").getString()).append(itemStack.getHoverName().getString()).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.物品ID:").getString()).append(BuiltInRegistries.ITEM.getKey(item)).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.最大堆叠数量:").getString()).append(itemStack.getMaxStackSize()).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.是否防火:").getString()).append(item.isFireResistant()).append('\n');
        if (itemStack.isDamageableItem())
        {
            stringBuilder.append(Component.translatable("message.pasterdream.最大耐久:").getString()).append(itemStack.getMaxDamage()).append('\n');
        }

        stringBuilder.append(Component.translatable("message.pasterdream.使用动画:").getString()).append(item.getUseAnimation(itemStack)).append('\n');

        FoodProperties foodProperties = item.getFoodProperties(itemStack, null);
        if (foodProperties != null)
        {
            stringBuilder.append(Component.translatable("message.pasterdream.饱食度:").getString()).append(foodProperties.getNutrition()).append('\n');
            stringBuilder.append(Component.translatable("message.pasterdream.饱和度:").getString()).append(2 * foodProperties.getSaturationModifier() * foodProperties.getNutrition()).append('\n');
            if(item instanceof PasterDreamDrinkItem pasterDreamDrinkItem)
            {
                stringBuilder.append(Component.translatable("message.pasterdream.SAN值回复:").getString()).append(pasterDreamDrinkItem.sanAdd).append('\n');
                stringBuilder.append(Component.translatable("message.pasterdream.融梦能量回复:").getString()).append(pasterDreamDrinkItem.meltDreamEnergyAdd).append('\n');

                FluidContainerRegistry.ContainerEntry entry = FluidContainerRegistry.getEntryForFillToEmpty(new ItemStack(itemStack.getItem()));
                stringBuilder.append(Component.translatable("message.pasterdream.饮用后返还:").getString()).append(entry != null ? entry.emptyContainerItemStack.getHoverName().getString() : "null").append('\n');
            }
            else
                if(item instanceof PasterDreamFoodItem pasterDreamFoodItem)
                {
                    stringBuilder.append(Component.translatable("message.pasterdream.SAN值回复:").getString()).append(pasterDreamFoodItem.sanAdd).append('\n');
                    stringBuilder.append(Component.translatable("message.pasterdream.融梦能量回复:").getString()).append(pasterDreamFoodItem.meltDreamEnergyAdd).append('\n');
                }

            stringBuilder.append(Component.translatable("message.pasterdream.食用时长:").getString()).append(itemStack.getUseDuration()).append("tick\n");
            stringBuilder.append(Component.translatable("message.pasterdream.是否可一直食用:").getString()).append(foodProperties.canAlwaysEat()).append('\n');
            stringBuilder.append(Component.translatable("message.pasterdream.是否为肉类:").getString()).append(foodProperties.isMeat()).append('\n');
            if (!foodProperties.getEffects().isEmpty())
            {
                stringBuilder.append(Component.translatable("message.pasterdream.食用后获得效果:").getString()).append('\n');
                foodProperties.getEffects().forEach(pair -> stringBuilder.append(PotionHelper.formatTime(pair.getFirst().getDuration())).append(Component.translatable(pair.getFirst().getDescriptionId()).getString()).append(PotionHelper.toRoman(pair.getFirst().getAmplifier() + 1)).append(Component.translatable("message.pasterdream.,概率:").getString()).append(pair.getSecond()).append('\n'));
            }
        }

        var holder = item.builtInRegistryHolder();
        List<ResourceLocation> tagIds = holder.tags().map(TagKey::location).sorted().toList();
        StringBuilder buffer = new StringBuilder("ItemTag:\n");
        if (tagIds.isEmpty())
        {
            buffer.append("null");
        }
            else
            {
                for (ResourceLocation id : tagIds)
                {
                    buffer.append("#").append(id).append('\n');
                }
            }

        stringBuilder.append(buffer);

        return stringBuilder.toString();
    }
}
