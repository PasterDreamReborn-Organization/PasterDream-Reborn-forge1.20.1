package com.pasterdream.pasterdreammod.world.item.StorgeBagItem;

import com.pasterdream.pasterdreammod.init.ModMenus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class LargeStorageBagMenu extends AbstractContainerMenu {

    private final ItemStack bagStack;
    private final InteractionHand hand;
    private final int lockedSlotIndex;
    private final ItemStackHandler bagInventory;

    public LargeStorageBagMenu(int id, Inventory playerInventory, ItemStack bagStack, InteractionHand hand) {
        super(ModMenus.LARGE_STORAGE_BAG.get(), id);
        this.bagStack = bagStack;
        this.hand = hand;
        this.bagInventory = createInventory(bagStack);

        if (hand == InteractionHand.MAIN_HAND) {
            lockedSlotIndex = LargeStorageBagItem.SLOT_COUNT + 27 + playerInventory.selected;
        } else {
            lockedSlotIndex = -1;
        }

        // 大储物袋槽位 5×5
        int bagTexLeft = 24;
        int bagStartX = bagTexLeft + (128 - 5 * 18) / 2 - 2;
        int bagStartY = 10 - 2;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                addSlot(new SlotItemHandler(bagInventory, row * 5 + col,
                        bagStartX + col * 18, bagStartY + row * 18) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return !isStorageBag(stack);
                    }
                });
            }
        }

        // 玩家背包 3×9
        int invStartX = 5;
        int invStartY = 124 + 5;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        invStartX + col * 18, invStartY + row * 18));
            }
        }

        // 快捷栏 1×9
        int hotbarY = invStartY + 3 * 18 + 4;
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, invStartX + col * 18, hotbarY));
        }
    }

    public LargeStorageBagMenu(int id, Inventory playerInventory, FriendlyByteBuf data) {
        super(ModMenus.LARGE_STORAGE_BAG.get(), id);
        byte handId = data.readByte();
        this.hand = handId == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        this.bagStack = playerInventory.player.getItemInHand(this.hand);
        this.bagInventory = createInventory(this.bagStack);

        if (this.hand == InteractionHand.MAIN_HAND) {
            lockedSlotIndex = LargeStorageBagItem.SLOT_COUNT + 27 + playerInventory.selected;
        } else {
            lockedSlotIndex = -1;
        }

        // 大储物袋槽位 5×5
        int bagTexLeft = 24;
        int bagStartX = bagTexLeft + (128 - 5 * 18) / 2 + 2;
        int bagStartY = 10 + 2;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                addSlot(new SlotItemHandler(bagInventory, row * 5 + col,
                        bagStartX + col * 18, bagStartY + row * 18) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return !isStorageBag(stack);
                    }
                });
            }
        }

        // 玩家背包 3×9
        int invStartX = 5;
        int invStartY = 124 + 5;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        invStartX + col * 18, invStartY + row * 18));
            }
        }

        // 快捷栏 1×9
        int hotbarY = invStartY + 3 * 18 + 4;
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, invStartX + col * 18, hotbarY));
        }
    }

    private static ItemStackHandler createInventory(ItemStack stack) {
        ItemStackHandler handler = new ItemStackHandler(LargeStorageBagItem.SLOT_COUNT);
        ListTag items = LargeStorageBagItem.getInventoryTag(stack);
        for (int i = 0; i < items.size(); i++) {
            CompoundTag tag = items.getCompound(i);
            if (tag.contains("SlotIndex")) {
                int slotIndex = tag.getInt("SlotIndex");
                if (slotIndex >= 0 && slotIndex < LargeStorageBagItem.SLOT_COUNT) {
                    handler.setStackInSlot(slotIndex, ItemStack.of(tag));
                }
            }
        }
        return handler;
    }

    private void save() {
        ListTag items = new ListTag();
        for (int i = 0; i < bagInventory.getSlots(); i++) {
            ItemStack stack = bagInventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                CompoundTag tag = stack.save(new CompoundTag());
                tag.putInt("SlotIndex", i);
                items.add(tag);
            }
        }
        LargeStorageBagItem.saveInventoryTag(bagStack, items);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        int bagSlots = LargeStorageBagItem.SLOT_COUNT;

        if (index < bagSlots) {
            if (!moveItemStackTo(stack, bagSlots, bagSlots + 36, true)) return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, 0, bagSlots, false)) return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getItemInHand(hand) == bagStack;
    }

    @Override
    public void clicked(int slotIndex, int button, ClickType clickType, Player player) {
        if (slotIndex == lockedSlotIndex && clickType != ClickType.QUICK_MOVE) return;
        if (clickType == ClickType.QUICK_MOVE && slotIndex >= 0) {
            Slot slot = this.slots.get(slotIndex);
            if (slot.hasItem() && (slot.getItem().getItem() instanceof StorageBagItem
                    || slot.getItem().getItem() instanceof LargeStorageBagItem)) return;
        }
        super.clicked(slotIndex, button, clickType, player);
    }

    @Override
    public void removed(Player player) {
        save();
        super.removed(player);
    }

    @Override
    public void broadcastChanges() {
        save();
        super.broadcastChanges();
    }

    private static boolean isStorageBag(ItemStack stack) {
        return stack.getItem() instanceof StorageBagItem || stack.getItem() instanceof LargeStorageBagItem;
    }
}
