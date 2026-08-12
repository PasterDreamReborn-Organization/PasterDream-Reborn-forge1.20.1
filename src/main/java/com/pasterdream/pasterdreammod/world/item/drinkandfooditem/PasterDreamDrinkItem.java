package com.pasterdream.pasterdreammod.world.item.drinkandfooditem;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.fluidcontainercapability.FluidContainerRegistry;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.PasterDreamDrinkAndFoodProperties;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MeltDreamEnergySyncPacket;
import com.pasterdream.pasterdreammod.network.san.SanSyncPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PasterDreamDrinkItem extends Item
{
    private final int useDuration;
    private final double sanAdd;
    private final double meltDreamEnergyAdd;

    public PasterDreamDrinkItem(PasterDreamDrinkAndFoodProperties properties)
    {
        super(properties);
        this.useDuration = properties.getUseDuration();
        this.sanAdd = properties.getSanAdd();
        this.meltDreamEnergyAdd = properties.getMeltDreamEnergyAdd();
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(itemStack, level, tooltip, flag);

        if(sanAdd != 0)
        {
            tooltip.add(Component.translatable("tooltip.pasterdreammod.san_value").withStyle(ChatFormatting.BLUE).append(Component.literal(formatValue(sanAdd)).withStyle(sanAdd < 0 ? ChatFormatting.RED : ChatFormatting.BLUE)));
        }

        if(meltDreamEnergyAdd != 0)
        {
            tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_energy").withStyle(ChatFormatting.BLUE).append(Component.literal(formatValue(meltDreamEnergyAdd))).withStyle(ChatFormatting.BLUE));
        }

        FoodProperties food = this.getFoodProperties();
        if (food != null)
        {
            for (var effectPair : food.getEffects())
            {
                MobEffectInstance instance = effectPair.getFirst();
                float probability = effectPair.getSecond();
                Component text = Component.translatable(instance.getDescriptionId());
                int amp = instance.getAmplifier();
                if (amp > 0)
                {
                    text = Component.translatable("potion.withAmplifier", text,
                            Component.translatable("potion.potency." + amp));
                }
                if (!instance.endsWithin(20))
                {
                    text = Component.translatable("potion.withDuration", text,
                            Component.literal(formatTickDuration(instance.getDuration())));
                }
                if (probability < 1.0f)
                {
                    text = Component.literal((int)(probability * 100) + "% ").append(text);
                }
                tooltip.add(text.copy().withStyle(instance.getEffect().getCategory().getTooltipFormatting()));
            }
        }
    }

    private static String formatValue(double value)
    {
        if (value == (long) value)
        {
            return String.format("%+d", (long) value);
        }
        return String.format("%+.1f", value);
    }

    private static String formatTickDuration(int ticks)
    {
        int seconds = ticks / 20;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return minutes > 0 ? String.format("%d:%02d", minutes, seconds) : String.format("0:%02d", seconds);
    }


    @Override
    public int getUseDuration(ItemStack stack)
    {
        return useDuration;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) //喝，为什么不喝？
    {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity)
    {
        FoodProperties food = stack.getFoodProperties(entity);
        if (food != null && entity instanceof Player player)
        {
            player.getFoodData().eat(food.getNutrition(), food.getSaturationModifier());
            for (var effectPair : food.getEffects())
            {
                if (level.random.nextFloat() < effectPair.getSecond())
                {
                    entity.addEffect(new MobEffectInstance(effectPair.getFirst()));
                }
            }
        }

        if (!level.isClientSide && entity instanceof ServerPlayer player)
        {
            if (sanAdd != 0)
            {
                SanHelper.addPlayerSanAndSync(player, sanAdd);
            }

            if (meltDreamEnergyAdd != 0)
            {
                MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(player, meltDreamEnergyAdd);
            }
        }

        if (!level.isClientSide)
        {
            if (entity instanceof Player player)
            {
                player.awardStat(Stats.ITEM_USED.get(this));
                if (player instanceof ServerPlayer serverPlayer)
                {
                    CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
                }
            }
            onDrinkSpecial(entity, level);
        }

        if (entity instanceof Player player && player.isCreative())
        {
            return stack;
        }

        var entry = FluidContainerRegistry.getEntryForFillToEmpty(this);
        Item emptyContainer = entry != null ? entry.emptyItem : null;
        stack.shrink(1);

        if (stack.isEmpty())
        {
            if (emptyContainer != null)
            {
                return new ItemStack(emptyContainer);
            }
            return ItemStack.EMPTY;
        }
            else
            {
                if (!level.isClientSide && emptyContainer != null)
                {
                    ItemStack containerStack = new ItemStack(emptyContainer);
                    if (entity instanceof Player player)
                    {
                        if (!player.getInventory().add(containerStack))
                        {
                            player.drop(containerStack, false);
                        }
                    }
                    else
                    {
                        entity.spawnAtLocation(containerStack);
                    }
                }
                return stack;
            }
    }

    protected void onDrinkSpecial(LivingEntity entity, Level level)
    {
        //默认无操作
    }
}

//在ModItem中，帕斯特之梦饮料物品注册示例：
//    public static final RegistryObject<Item> >此处填写Item注册名，例如"GLASS_JAR_OF_WATER"< =
//    ITEMS.register(">此处填写Item id，例如"glass_jar_of_water"<",
//    () -> new PasterDreamDrinkItem(new PasterDreamDrinkProperties()
//    .food(new FoodProperties  //必须添加
//          .Builder()          //必须添加
//          .alwaysEat()        //必须添加
//          .nutrition(2)       //饱食度2
//          .saturationMod(1)   //饱和度1
//          .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 16384, 4), 1) //使用后获得的效果，示例：抗性提升V，获得概率100%，持续时间16384tick
//          .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 32768, 0), 0.5)    //可叠加多个效果，示例：夜视I，获得概率50%，持续时间32768tick
//          .build())           //必须添加
//    .meltDreamEnergyAdd(0.2)  //增加0.2融梦能量值
//    .sanAdd(0.3)              //增加0.3精神值
//    .useDuration(24)          //使用时间为24tick(如果不设置默认32tick)
//    )                         //不需要额外添加返还的容器，因为已经在IFluidHandler中绑定好了，会自动返还（如果没有绑定，则不会返还）
//    {
//        @Override
//        protected void onDrinkSpecial(Player player, Level level)
//        {
//              //在这里添加使用后的特殊行为
//        }
//    });       //如果不需要特殊行为，则不需要添加
