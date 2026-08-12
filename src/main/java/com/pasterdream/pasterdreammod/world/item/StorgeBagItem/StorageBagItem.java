package com.pasterdream.pasterdreammod.world.item.StorgeBagItem;

import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StorageBagItem extends Item {

    public static final int SLOT_COUNT = 9; // 3x3
    private static final String TAG_INVENTORY = "BagItems";

    public StorageBagItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    /**
     * 从物品 NBT 读取库存，每个条目包含 SlotIndex 标识槽位，空槽位不存储
     */
    public static ListTag getInventoryTag(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(TAG_INVENTORY)) {
            tag.put(TAG_INVENTORY, new ListTag());
        }
        return tag.getList(TAG_INVENTORY, 10); // 10 = CompoundTag
    }

    /**
     * 将库存 ListTag 保存到物品 NBT
     */
    public static void saveInventoryTag(ItemStack stack, ListTag items) {
        stack.getOrCreateTag().put(TAG_INVENTORY, items);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, level, tooltip, flag);
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.pasterdream.introduction.tooltip"));
            tooltip.add(Component.translatable("tooltip.pasterdream.storage_bag.tooltip"));
        }
        else {
            tooltip.add(Component.translatable("tooltip.pasterdream.hold_shift"));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        if (entity instanceof ServerPlayer serverPlayer) {
            ItemStack stack = entity.getItemInHand(hand);
            world.playSound(null, entity.blockPosition(), ModSounds.ZIPPER.get(), SoundSource.NEUTRAL, 0.2f, 1f);
            NetworkHooks.openScreen(serverPlayer,
                    new SimpleMenuProvider(
                            (id, inv, player) -> new StorageBagMenu(id, inv, stack, hand),
                            Component.empty()
                    ),
                    buf -> buf.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1)
            );
        }
        return InteractionResultHolder.sidedSuccess(entity.getItemInHand(hand), world.isClientSide);
    }

}
