package com.pasterdream.pasterdreammod.world.block.researchtable;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.fluidhandler.IFluidHandlerProvider;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.researchtableinventory.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ResearchTableBlockEntity extends BlockEntity implements MenuProvider, IFluidHandlerProvider
{
    private static final int FLUID_CAPACITY = 250;

    public ResearchTableBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.RESEARCH_TABLE.get(), pos, state);
    }

    private final FluidTank[] fluidTanks =
    {
        new FluidTank(FLUID_CAPACITY)
        {
            protected void onContentsChanged()
            {
                setChangedAndSync();
            }
        }
    };

    private final ItemStackHandler itemHandler = new ItemStackHandler(6)
    {
        @Override
        protected void onContentsChanged(int slotIndex)
        {
            setChangedAndSync();
        }

        @Override
        public boolean isItemValid(int slotIndex, ItemStack stack)
        {
            return slotIndex < 5;
        }
    };

    private final LazyOptional<IItemHandler> itemHandlerCap = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IFluidHandler> fluidTankCap = LazyOptional.of(() -> fluidTanks[0]);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.FLUID_HANDLER)
        {
            return fluidTankCap.cast();
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
        fluidTankCap.invalidate();
        itemHandlerCap.invalidate();
    }

    public void copyNote()
    {
        ResearchTableCopyRecipeInventory matchResult = ResearchTableCopyRecipeMatch.matches(new ResearchTableCopyRecipeInventory(fluidTanks[0].getFluid(), itemHandler.getStackInSlot(0), itemHandler.getStackInSlot(1), itemHandler.getStackInSlot(2)), level.getRecipeManager().getAllRecipesFor(ModRecipes.RESEARCH_TABLE_COPY.get()));
        if(matchResult != null)
        {
            ResearchTableCopyRecipeInventoryWithOutputSlot result = ResearchTableCopyRecipeProcessor.processing(matchResult, new ResearchTableCopyRecipeInventoryWithOutputSlot(fluidTanks[0].getFluid(), itemHandler.getStackInSlot(0), itemHandler.getStackInSlot(1), itemHandler.getStackInSlot(2), itemHandler.getStackInSlot(3)), level.getRandom());
            if(result != null)
            {
                fluidTanks[0].setFluid(result.fluidStack());
                itemHandler.setStackInSlot(0, result.pen());
                itemHandler.setStackInSlot(1, result.sourceBook());
                itemHandler.setStackInSlot(2, result.material());
                itemHandler.setStackInSlot(3, result.output());
            }
        }
    }

    public void researchNote()
    {
        ResearchTableResearchRecipeInventory matchResult = ResearchTableResearchRecipeMatch.matches(new ResearchTableResearchRecipeInventory(fluidTanks[0].getFluid(), itemHandler.getStackInSlot(0), itemHandler.getStackInSlot(4), itemHandler.getStackInSlot(5)), level.getRecipeManager().getAllRecipesFor(ModRecipes.RESEARCH_TABLE_RESEARCH.get()));
        if(matchResult != null)
        {
            ResearchTableResearchRecipeInventory result = ResearchTableResearchRecipeProcessor.processing(matchResult, new ResearchTableResearchRecipeInventory(fluidTanks[0].getFluid(), itemHandler.getStackInSlot(0), itemHandler.getStackInSlot(4), itemHandler.getStackInSlot(5)), level.getRandom());
            if(result != null)
            {
                fluidTanks[0].setFluid(result.fluidStack());
                itemHandler.setStackInSlot(0, result.pen());
                itemHandler.setStackInSlot(4, result.unresolvedNote());
                itemHandler.setStackInSlot(5, result.output());
            }
        }
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

    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".research_table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new ResearchTableMenu(id, inventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put("FluidTank", fluidTanks[0].writeToNBT(new CompoundTag()));
        tag.put("Inventory", itemHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        fluidTanks[0].readFromNBT(tag.getCompound("FluidTank"));
        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet)
    {
        CompoundTag tag = packet.getTag();
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

    public FluidTank[] getFluidTanks()
    {
        return fluidTanks;
    }
}
