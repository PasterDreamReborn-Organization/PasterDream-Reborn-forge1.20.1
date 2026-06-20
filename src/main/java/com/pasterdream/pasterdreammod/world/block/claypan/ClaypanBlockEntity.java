package com.pasterdream.pasterdreammod.world.block.claypan;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.FluidHandler.IFluidHandlerProvider;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModRecipes;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ClaypanBlockEntity extends BlockEntity implements MenuProvider, IFluidHandlerProvider
{
    private static final int FLUID_CAPACITY = 1000;

    public ClaypanBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.CLAYPAN.get(), pos, state);
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(1)
    {
        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return false;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            return stack;
        }

        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
            if (level != null && !level.isClientSide)
            {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    };

    private final FluidTank fluidTank = new FluidTank(FLUID_CAPACITY)
    {
        @Override
        protected void onContentsChanged()
        {
            setChanged();
            if (level != null && !level.isClientSide)
            {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    };

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

    private int progress = 0;
    private int maxProgress = 0;
    private FluidStack recipeRequiredFluid;
    private ItemStack currentRecipeOutput = ItemStack.EMPTY;
    private LazyOptional<IItemHandler> itemHandlerCap = LazyOptional.of(() -> itemHandler);
    private LazyOptional<IFluidHandler> fluidHandlerCap = LazyOptional.of(() -> fluidTank);

    public void tick()
    {
        if (level == null || level.isClientSide)
        {
            return;
        }

        if (maxProgress == 0)
        {
            if (itemHandler.getStackInSlot(0).isEmpty())
            {
                Optional<ClaypanRecipe> recipe = findMatchingRecipe();
                if (recipe.isPresent())
                {
                    ClaypanRecipe claypanRecipe = recipe.get();
                    recipeRequiredFluid = claypanRecipe.getInputFluidIngredients().get(0).getFluidStack();
                    if (fluidTank.getFluidAmount() >= recipeRequiredFluid.getAmount())
                    {
                        currentRecipeOutput = claypanRecipe.getOutputItemIngredients().get(0).getItems()[0];
                        maxProgress = claypanRecipe.getProcessingTime();
                        progress = 0;
                        setChanged();
                    }
                }
            }
        }

        if (maxProgress > 0)
        {
            if (recipeRequiredFluid == null || fluidTank.getFluidAmount() < recipeRequiredFluid.getAmount() || fluidTank.getFluid().getFluid() != recipeRequiredFluid.getFluid())
            {
                resetProgress();
                return;
            }
            progress++;

            setChanged();
            if (progress >= maxProgress)
            {
                fluidTank.drain(recipeRequiredFluid.getAmount(), IFluidHandler.FluidAction.EXECUTE);
                itemHandler.setStackInSlot(0, currentRecipeOutput.copy());
                resetProgress();
            }
        }
    }

    private Optional<ClaypanRecipe> findMatchingRecipe()
    {
        if (level == null)
        {
            return Optional.empty();
        }

        FluidStack fluid = fluidTank.getFluid();
        if (fluid.isEmpty())
        {
            return Optional.empty();
        }

        return level.getRecipeManager().getAllRecipesFor(ModRecipes.CLAYPAN.get()).stream().filter(recipe -> recipe.getInputFluidIngredients().get(0).getFluid() == fluid.getFluid()).filter(recipe -> fluidTank.getFluidAmount() >= recipe.getInputFluidIngredients().get(0).getAmount()).findFirst();
    }

    private void resetProgress()
    {
        progress = 0;
        maxProgress = 0;
        currentRecipeOutput = ItemStack.EMPTY;
        recipeRequiredFluid = null;
        setChanged();
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        fluidTank.readFromNBT(tag.getCompound("FluidTank"));
        progress = tag.getInt("Progress");
        maxProgress = tag.getInt("MaxProgress");
        if (tag.contains("RecipeRequiredFluid"))
        {
            recipeRequiredFluid = FluidStack.loadFluidStackFromNBT(tag.getCompound("RecipeRequiredFluid"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.put("FluidTank", fluidTank.writeToNBT(new CompoundTag()));
        tag.putInt("Progress", progress);
        tag.putInt("MaxProgress", maxProgress);
        if (recipeRequiredFluid != null)
        {
            tag.put("RecipeRequiredFluid", recipeRequiredFluid.writeToNBT(new CompoundTag()));
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
        {
            return itemHandlerCap.cast();
        }

        if (cap == ForgeCapabilities.FLUID_HANDLER)
        {
            return fluidHandlerCap.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();
        itemHandlerCap.invalidate();
        fluidHandlerCap.invalidate();
    }

    //菜单
    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".claypan");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            return new ClaypanMenu(id, inventory, this, serverPlayer);
        }
        return new ClaypanMenu(id, inventory, this);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket clientboundBlockEntityDataPacket)
    {
        handleUpdateTag(clientboundBlockEntityDataPacket.getTag());
    }

    @Override
    public IFluidHandler getFluidHandler(int tankIndex)
    {
        return fluidTank;
    }

    public IItemHandler getItemHandler()
    {
        return itemHandler;
    }

    public int getProgress()
    {
        return progress;
    }

    public int getMaxProgress()
    {
        return maxProgress;
    }

    public FluidTank getFluidTank()
    {
        return fluidTank;
    }
}
