package com.pasterdream.pasterdreammod.world.item.debugtool.menu;

import com.pasterdream.pasterdreammod.helper.nbthelper.WrappedNBTBlockItem;
import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.DebugItemEditorMenu;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.FluidHandlerLaunchData;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.ItemHandlerLaunchData;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.PlayerEditorSlotData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class DebugToolBlockEditorMenu extends AbstractContainerMenu implements DebugItemEditorMenu
{
    private final Container editorContainer;
    private final Player player;
    private final Level level;
    private final BlockPos blockPosition;
    private CompoundTag serverBlockNbt;
    private final List<Consumer<CompoundTag>> nbtListeners = new CopyOnWriteArrayList<>();
    private final List<Consumer<String>> blockStateListeners = new CopyOnWriteArrayList<>();

    public DebugToolBlockEditorMenu(int id, Inventory playerInventory, BlockPos blockPosition)
    {
        super(ModMenus.DEBUG_TOOL_BLOCK_EDITOR.get(), id);

        player = playerInventory.player;
        level = player.level();
        this.blockPosition = blockPosition;

        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(playerInventory, col, 5 + col * 18, 217));
        }

        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 5 + col * 18, 159 + row * 18));
            }
        }

        this.editorContainer = new SimpleContainer(2);

        ItemStack stored = PlayerEditorSlotData.get(player, 1);
        if (!stored.isEmpty())
        {
            editorContainer.setItem(1, stored.copy());
        }

        addSlot(new Slot(editorContainer, 0, 152, 12)
        {
            @Override
            public void set(ItemStack itemStack)
            {

            }

            @Override
            public ItemStack getItem()
            {
                return getBlockItemStack();
            }

            @Override
            public boolean mayPlace(ItemStack itemStack)
            {
                return false;
            }

            @Override
            public boolean mayPickup(Player player)
            {
                return false;
            }
        });

        addSlot(new Slot(editorContainer, 1, 152, 128)
        {
            @Override
            public void set(ItemStack itemStack)
            {
                super.set(itemStack);
                PlayerEditorSlotData.set(player, itemStack, 1);
            }
        });
    }

    public DebugToolBlockEditorMenu(int id, Inventory playerInventory, FriendlyByteBuf buffer)
    {
        this(id, playerInventory, buffer.readBlockPos());
        if (buffer.readBoolean())
        {
            this.serverBlockNbt = buffer.readNbt();
        }
    }

    private ItemStack getBlockItemStack()
    {
        BlockState blockState = level.getBlockState(blockPosition);
        if (blockState.isAir())
        {
            return ItemStack.EMPTY;
        }

        Item item = blockState.getBlock().asItem();
        if (item == Items.AIR)
        {
            return ItemStack.EMPTY;
        }

        ItemStack itemStack = new ItemStack(item);

        BlockEntity blockEntity = level.getBlockEntity(blockPosition);
        if (blockEntity != null)
        {
            CompoundTag nbt = blockEntity.saveWithId();
            if (!nbt.isEmpty())
            {
                itemStack.setTag(WrappedNBTBlockItem.wrapper(nbt));
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        Slot slot = slots.get(index);
        if (!slot.hasItem())
        {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if ((index == 37))
        {   //从机器移出到背包
            if (!this.moveItemStackTo(stack, 0, 36, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else
            if((index >= 0 && index <= 35))
            {   //从背包移入输入槽
                if (!(this.moveItemStackTo(stack, 37, 38, false)))
                {
                    return ItemStack.EMPTY;
                }
            }

        if (stack.isEmpty())
        {
            slot.set(ItemStack.EMPTY);
        }
            else
            {
                slot.setChanged();
            }
        return copy;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    public void addNbtListener(Consumer<CompoundTag> listener)
    {
        nbtListeners.add(listener);
    }

    public void clearNbtListeners()
    {
        nbtListeners.clear();
    }

    public void setServerBlockNbt(CompoundTag nbt)
    {
        this.serverBlockNbt = nbt;
        for (Consumer<CompoundTag> listener : nbtListeners)
        {
            listener.accept(nbt);
        }
    }

    public void addBlockStateListener(Consumer<String> listener)
    {
        blockStateListeners.add(listener);
    }

    public void clearBlockStateListeners()
    {
        blockStateListeners.clear();
    }

    public void setServerBlockStateString(String stateString)
    {
        for (Consumer<String> listener : blockStateListeners)
        {
            listener.accept(stateString);
        }
    }

    @Override
    public MenuProvider asMenuProvider()
    {
        return new MenuProvider()
        {
            @Override
            public Component getDisplayName()
            {
                return Component.empty();
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player)
            {
                return new DebugToolBlockEditorMenu(id, playerInventory, blockPosition);
            }
        };
    }

    @Override
    public ItemHandlerLaunchData provideItemHandlerLaunch()
    {
        BlockEntity blockEntity = level.getBlockEntity(blockPosition);
        if (blockEntity != null)
        {
            IItemHandler handler = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve().orElse(null);
            if (handler != null)
            {
                Runnable onChanged = () ->
                {
                    blockEntity.setChanged();
                    level.sendBlockUpdated(blockPosition, blockEntity.getBlockState(), blockEntity.getBlockState(), 3);
                };
                return new ItemHandlerLaunchData(handler, onChanged);
            }
        }
        return null;
    }

    @Override
    public @Nullable FluidHandlerLaunchData provideFluidHandlerLaunch()
    {
        BlockEntity blockEntity = level.getBlockEntity(blockPosition);
        if (blockEntity != null)
        {
            IFluidHandler handler = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER).resolve().orElse(null);
            if (handler != null)
            {
                Runnable onChanged = () ->
                {
                    blockEntity.setChanged();
                    level.sendBlockUpdated(blockPosition, blockEntity.getBlockState(), blockEntity.getBlockState(), 3);
                };
                return new FluidHandlerLaunchData(handler, onChanged);
            }
        }
        return null;
    }

    public BlockEntity getBlockEntity()
    {
        return level.getBlockEntity(blockPosition);
    }

    public BlockPos getBlockPosition()
    {
        return blockPosition;
    }

    public CompoundTag getServerBlockNbt()
    {
        return serverBlockNbt;
    }

    public BlockState getBlockState()
    {
        return level.getBlockState(blockPosition);
    }

    public Level getLevel()
    {
        return level;
    }
}
