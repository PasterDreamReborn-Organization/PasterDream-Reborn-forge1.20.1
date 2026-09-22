package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 料理（cook）效果：满饱食度进食时（cook 允许进食任意食物，见
 * {@code mixin/PlayerCanEatMixin}），把真正溢出的饱食度/饱和度转化为生命值；
 * 生命值已满时转化为原版吸收（黄心）。
 *
 * <p>溢出量 = 未吃进去的 nutrition + 被 20 上限卡掉的 saturation。
 * 黄心上限 = (cook 等级 + 1) * 10，即 I=20 / II=30 / III=40；
 * 加法时把总量钳到上限，永不降低，不单独追踪本效果贡献。
 *
 * <p>规则详见 {@code document/rule/program/料理状态效果.md}。
 */
public class CookOverflowHandler
{
    private static final float MAX_FOOD_LEVEL = 20.0F;

    /** 记录进食前 [foodLevel, saturation]，用于在 Finish 时计算真实溢出量。 */
    private static final Map<UUID, float[]> EAT_BEFORE = new HashMap<>();

    public static void onItemUseStart(LivingEntityUseItemEvent.Start event)
    {
        if (!(event.getEntity() instanceof ServerPlayer player))
        {
            return;
        }
        ItemStack stack = event.getItem();
        if (!stack.isEdible())
        {
            return;
        }
        EAT_BEFORE.put(player.getUUID(), new float[]{
                player.getFoodData().getFoodLevel(),
                player.getFoodData().getSaturationLevel()
        });
    }

    public static void onItemUseStop(LivingEntityUseItemEvent.Stop event)
    {
        EAT_BEFORE.remove(event.getEntity().getUUID());
    }

    public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event)
    {
        if (!(event.getEntity() instanceof ServerPlayer player))
        {
            return;
        }
        float[] before = EAT_BEFORE.remove(player.getUUID());
        if (before == null)
        {
            return;
        }

        ItemStack stack = event.getItem();
        FoodProperties food = stack.getFoodProperties(player);
        if (food == null)
        {
            return;
        }

        MobEffectInstance cook = player.getEffect(ModEffects.COOK.get());
        if (cook == null)
        {
            return;
        }
        if (before[0] < MAX_FOOD_LEVEL)
        {
            return;
        }

        float overflow = computeOverflow(food, before[0], before[1]);
        if (overflow <= 0.0F)
        {
            return;
        }

        int cap = (cook.getAmplifier() + 1) * 10;

        float healed = Math.min(overflow, player.getMaxHealth() - player.getHealth());
        if (healed > 0.0F)
        {
            player.heal(healed);
        }

        float remaining = overflow - healed;
        if (remaining > 0.0F)
        {
            float current = player.getAbsorptionAmount();
            float add = Math.min(remaining, Math.max(0.0F, cap - current));
            if (add > 0.0F)
            {
                player.setAbsorptionAmount(current + add);
            }
        }
    }

    /** 依据原版 FoodData.eat 的封顶规则，计算真正被浪费的 nutrition + saturation。 */
    private static float computeOverflow(FoodProperties food, float beforeFood, float beforeSaturation)
    {
        float nutrition = food.getNutrition();
        float saturationGain = nutrition * food.getSaturationModifier() * 2.0F;

        float postFood = Math.min(beforeFood + nutrition, MAX_FOOD_LEVEL);
        float postSaturation = Math.min(beforeSaturation + saturationGain, postFood);

        float nutritionOverflow = beforeFood + nutrition - postFood;
        float saturationOverflow = beforeSaturation + saturationGain - postSaturation;

        return Math.max(0.0F, nutritionOverflow) + Math.max(0.0F, saturationOverflow);
    }
}
