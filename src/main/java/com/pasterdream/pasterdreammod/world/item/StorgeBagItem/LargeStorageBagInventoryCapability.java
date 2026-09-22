package com.pasterdream.pasterdreammod.world.item.StorgeBagItem;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 大便携储物袋的 IItemHandler capability，直接读写物品 NBT 中的 BagItems 标签，
 * 与 {@link LargeStorageBagMenu} 使用同一套存储格式，供调试工具等外部系统访问。
 */
public class LargeStorageBagInventoryCapability implements ICapabilityProvider {

    private final LazyOptional<ItemStackHandler> holder;

    public LargeStorageBagInventoryCapability(ItemStack stack) {
        this.holder = LazyOptional.of(() -> new BagItemStackHandler(stack));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return holder.cast();
        }
        return LazyOptional.empty();
    }

    private static class BagItemStackHandler extends ItemStackHandler {

        private final ItemStack bagStack;
        private boolean suppressSave = true;

        BagItemStackHandler(ItemStack bagStack) {
            super(LargeStorageBagItem.SLOT_COUNT);
            this.bagStack = bagStack;
            load();
            suppressSave = false;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            if (!suppressSave) {
                save();
            }
        }

        private void load() {
            ListTag items = LargeStorageBagItem.getInventoryTag(bagStack);
            for (int i = 0; i < items.size(); i++) {
                CompoundTag tag = items.getCompound(i);
                if (tag.contains("SlotIndex")) {
                    int slotIndex = tag.getInt("SlotIndex");
                    if (slotIndex >= 0 && slotIndex < getSlots()) {
                        setStackInSlot(slotIndex, ItemStack.of(tag));
                    }
                }
            }
        }

        private void save() {
            ListTag items = new ListTag();
            for (int i = 0; i < getSlots(); i++) {
                ItemStack stack = getStackInSlot(i);
                if (!stack.isEmpty()) {
                    CompoundTag tag = stack.save(new CompoundTag());
                    tag.putInt("SlotIndex", i);
                    items.add(tag);
                }
            }
            LargeStorageBagItem.saveInventoryTag(bagStack, items);
        }
    }
}
