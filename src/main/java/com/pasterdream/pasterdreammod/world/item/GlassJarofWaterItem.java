package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class GlassJarofWaterItem extends Item {

    private static final FoodProperties FOOD = new FoodProperties.Builder()
            .nutrition(0).saturationMod(0).alwaysEat()
            .build();

    public GlassJarofWaterItem() {
        super(new Item.Properties());
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.DRINK;
    }//喝，为什么不喝？
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        super.finishUsingItem(stack, level, entity);
        if (level.isClientSide()) return stack;
        return ItemUtils.createFilledResult(stack, (Player) entity, new ItemStack(ModItems.GLASS_JAR.get()));
        //喝完返还玻璃罐
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 24;
    }
}