package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.FoodValueTooltips;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.Map;

/**
 * 原版食物（蘑菇煲 / 甜菜根汤 / 兔肉煲）回 SAN 与料理 buff。
 *
 * <p>原版食物无法修改其 {@code FoodProperties}，故通过进食结束事件在服务端补加
 * SAN 值（带网络同步）与 {@code ModEffects.COOK}（料理）效果；客户端 ToolTip 追加
 * SAN 值行，格式与本模组食物一致。
 *
 * <p>数值集中在下方 {@link #VANILLA_FOODS}，按需调整。
 */
public class VanillaFoodSanHandler
{
    /**
     * @param sanAdd       进食后 SAN 加成（正回、负扣）
     * @param cookTicks    料理 buff 持续 tick（20 tick = 1 秒）
     * @param cookAmplifier 料理 buff 等级（0 = 料理 I）
     */
    private record FoodSan(double sanAdd, int cookTicks, int cookAmplifier)
    {
    }

    private static final Map<Item, FoodSan> VANILLA_FOODS = Map.of(
            Items.MUSHROOM_STEW, new FoodSan(1, 1200, 0), // 蘑菇煲：回 1 SAN，料理 I 1 分钟
            Items.BEETROOT_SOUP, new FoodSan(1, 1200, 0), // 甜菜根汤：回 1 SAN，料理 I 1 分钟
            Items.RABBIT_STEW, new FoodSan(2, 2400, 0),    // 兔肉煲：回 2 SAN，料理 I 2 分钟
            Items.SUSPICIOUS_STEW, new FoodSan(1, 2400, 0)
    );

    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event)
    {
        if (event.getEntity().level().isClientSide())
        {
            return;
        }
        if (!(event.getEntity() instanceof ServerPlayer player))
        {
            return;
        }
        FoodSan food = VANILLA_FOODS.get(event.getItem().getItem());
        if (food == null)
        {
            return;
        }

        if (food.sanAdd() != 0)
        {
            SanHelper.addPlayerSanAndSync(player, food.sanAdd());
        }
        player.addEffect(new MobEffectInstance(ModEffects.COOK.get(), food.cookTicks(), food.cookAmplifier()));
    }

    public static void onItemTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        FoodSan food = VANILLA_FOODS.get(stack.getItem());
        if (food == null)
        {
            return;
        }
        FoodValueTooltips.appendCookTooltip(event.getToolTip(), food.cookTicks(), food.cookAmplifier());
        FoodValueTooltips.appendSanTooltip(event.getToolTip(),food.sanAdd());
    }
}
