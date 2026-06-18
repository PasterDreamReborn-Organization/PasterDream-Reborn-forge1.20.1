package com.pasterdream.pasterdreammod.world.block.dreamcauldron;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.FluidHandler.IFluidHandlerProvider;
import com.pasterdream.pasterdreammod.helper.recipematchresult.DreamCauldronRecipeMatchResult;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.dreamcauldron.DreamCauldronRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DreamCauldronBlockEntity extends BlockEntity implements MenuProvider, IFluidHandlerProvider
{
    private static final int FLUID0_CAPACITY = 2000;
    private static final int FLUID1_CAPACITY = 8000;
    private static final int ITEM_INPUT_SLOTS = 3;

    public DreamCauldronBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.DREAM_CAULDRON.get(), pos, state);
    }

    private final FluidTank[] fluidTanks =
    {
        new FluidTank(FLUID0_CAPACITY)
        {
            protected void onContentsChanged()
            {
                setChangedAndSync();
            }
        },
        new FluidTank(FLUID1_CAPACITY)
        {
            protected void onContentsChanged()
            {
                setChangedAndSync();
            }
        }
    };

    private final ItemStackHandler itemHandler = new ItemStackHandler(ITEM_INPUT_SLOTS + 1)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChangedAndSync();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return slot != ITEM_INPUT_SLOTS;
        }
    };

    private final LazyOptional<IItemHandler> itemHandlerCap = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IFluidHandler> fluidTank0Cap = LazyOptional.of(() -> fluidTanks[0]);
    private final LazyOptional<IFluidHandler> fluidTank1Cap = LazyOptional.of(() -> fluidTanks[1]);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.FLUID_HANDLER)
        {
            if (side == Direction.NORTH || side == Direction.SOUTH || side == Direction.EAST || side == Direction.WEST)
            {
                return fluidTank1Cap.cast();
            }
                else
                {
                    return fluidTank0Cap.cast();
                }
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER)
        {
            return itemHandlerCap.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();
        fluidTank0Cap.invalidate();
        fluidTank1Cap.invalidate();
        itemHandlerCap.invalidate();
    }

    public boolean canCraft()
    {
        DreamCauldronRecipeMatchResult result = findMatchingRecipe();
        if (result == null)
        {
            return false;
        }

        ItemStack output = result.recipe().getResultItem(null);
        return canFitOutput(output);
    }

    public void craft()
    {
        if (level == null || level.isClientSide)
        {
            return;
        }

        DreamCauldronRecipeMatchResult result = findMatchingRecipe();
        if (result == null)
        {
            return;
        }

        DreamCauldronRecipe recipe = result.recipe();

        //消耗流体
        for (int i = 0; i < recipe.getFluidIngredients().size(); i++)
        {
            int slot = result.fluidSlotMap()[i];
            int amount = recipe.getFluidIngredients().get(i).getAmount();
            fluidTanks[slot].drain(amount, IFluidHandler.FluidAction.EXECUTE);
        }

        //消耗物品（每个输入槽消耗1个）
        for (int i = 0; i < recipe.getItemIngredients().size(); i++)
        {
            int slot = result.itemSlotMap()[i];
            itemHandler.extractItem(slot, 1, false);
        }

        //产出
        ItemStack output = recipe.getResultItem(null);
        if (itemHandler.getStackInSlot(ITEM_INPUT_SLOTS).isEmpty())
        {
            itemHandler.setStackInSlot(ITEM_INPUT_SLOTS, output.copy());
        }
            else
            {
                //堆叠
                ItemStack existing = itemHandler.getStackInSlot(ITEM_INPUT_SLOTS);
                if (ItemStack.isSameItemSameTags(existing, output) && existing.getCount() + output.getCount() <= existing.getMaxStackSize())
                {
                    existing.grow(output.getCount());
                    itemHandler.setStackInSlot(ITEM_INPUT_SLOTS, existing);
                }
            }
        setChangedAndSync();
    }

    //查找匹配的配方，返回匹配结果
    @Nullable
    private DreamCauldronRecipeMatchResult findMatchingRecipe()
    {
        if (level == null)
        {
            return null;
        }

        //收集非空流体
        List<FluidStack> fluidStacks = new ArrayList<>();
        for (FluidTank tank : fluidTanks)
        {
            FluidStack stack = tank.getFluid();
            if (!stack.isEmpty())
            {
                fluidStacks.add(stack);
            }
        }

        //收集非空物品
        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < ITEM_INPUT_SLOTS; i++)
        {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty())
            {
                itemStacks.add(stack);
            }
        }

        //遍历配方
        return level.getRecipeManager().getAllRecipesFor(ModRecipes.DREAM_CAULDRON.get()).stream().map(recipe -> recipe.matchesWithSlots(fluidStacks, itemStacks)).filter(Optional::isPresent).map(Optional::get).findFirst().orElse(null);
    }

    //检查输出槽是否能容纳
    private boolean canFitOutput(ItemStack output)
    {
        ItemStack existing = itemHandler.getStackInSlot(ITEM_INPUT_SLOTS);
        if (existing.isEmpty())
        {
            return true;
        }

        return ItemStack.isSameItemSameTags(existing, output) && existing.getCount() + output.getCount() <= existing.getMaxStackSize();
    }

    private void setChangedAndSync()
    {
        setChanged();
        if (level != null && !level.isClientSide)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag)
    {
        load(tag);
    }

    //菜单
    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".dream_cauldron");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            return new DreamCauldronMenu(id, inventory, this, serverPlayer);
        }
        return new DreamCauldronMenu(id, inventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put("FluidTank0", fluidTanks[0].writeToNBT(new CompoundTag()));
        tag.put("FluidTank1", fluidTanks[1].writeToNBT(new CompoundTag()));
        tag.put("Inventory", itemHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        fluidTanks[0].readFromNBT(tag.getCompound("FluidTank0"));
        fluidTanks[1].readFromNBT(tag.getCompound("FluidTank1"));
        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        CompoundTag tag = pkt.getTag();
        if (tag != null)
        {
            load(tag);
        }
    }
    public ItemStackHandler getItemHandler()
    {
        return itemHandler;
    }

    @Override
    public IFluidHandler getFluidHandler(int tankIndex)
    {
        return fluidTanks[tankIndex];
    }

    public FluidTank getFluidTank(int tankIndex)
    {
        return fluidTanks[tankIndex];
    }
}
