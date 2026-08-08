package com.pasterdream.pasterdreammod.world.item.prophecycard;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmptyProphecyCardItem extends Item {

    private static final Random RANDOM = new Random();

    public EmptyProphecyCardItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // 获取所有已注册种类（内置 + 其他模组注册的）
        List<String> types = new ArrayList<>(ProphecyCardItem.getRegisteredTypes());
        if (types.isEmpty()) {
            return InteractionResultHolder.fail(stack);
        }

        // 随机抽取一种
        String randomType = types.get(RANDOM.nextInt(types.size()));

        // 创建对应种类的预言卡
        ItemStack card = ProphecyCardItem.createCard(ModItems.PROPHECY_CARD.get(), randomType);

        // 音效
        if (!level.isClientSide()) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 0.5f, 1.0f);
        }

        // 消耗空白卡（创造模式不消耗）
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        // 给玩家预言卡
        if (!player.getInventory().add(card)) {
            // 背包满了就丢地上
            player.drop(card, false);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
