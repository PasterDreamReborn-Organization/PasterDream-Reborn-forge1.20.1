package com.pasterdream.pasterdreammod.world.block.shadowblastfurnace;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.fluidhandler.IFluidHandlerProvider;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShadowBlastFurnaceBlockEntity extends BlockEntity implements MenuProvider, IFluidHandlerProvider
{
    private static final int FLUID_CAPACITY = 9000;
    private int progress = 0;
    private int maxProgress = 0;
    private List<ItemStack> currentRecipeOutput = new ArrayList<>();

    public ShadowBlastFurnaceBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.SHADOW_BLAST_FURNACE.get(), pos, state);
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

    private final ItemStackHandler itemHandler = new ItemStackHandler(4)
    {
        @Override
        protected void onContentsChanged(int slotIndex)
        {
            setChangedAndSync();
        }

        @Override
        public boolean isItemValid(int slotIndex, ItemStack stack)
        {
            return slotIndex < 2;
        }
    };

    private final IItemHandler externalHandler = new IItemHandler()
    {
        @Override
        public int getSlots()
        {
            return itemHandler.getSlots();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slotIndex)
        {
            return itemHandler.getStackInSlot(slotIndex);
        }

        @Override
        public @NotNull ItemStack insertItem(int slotIndex, @NotNull ItemStack itemStack, boolean isSimulate)
        {
            return itemHandler.insertItem(slotIndex, itemStack, isSimulate);
        }

        @Override
        public @NotNull ItemStack extractItem(int slotIndex, int amount, boolean isSimulate)
        {
            if(slotIndex == 2 || slotIndex == 3)
            {
                return itemHandler.extractItem(slotIndex, amount, isSimulate);
            }
                else
                {
                    return ItemStack.EMPTY;
                }
        }

        @Override
        public int getSlotLimit(int slotIndex)
        {
            return itemHandler.getSlotLimit(slotIndex);
        }

        @Override
        public boolean isItemValid(int slotIndex, @NotNull ItemStack itemStack)
        {
            return itemHandler.isItemValid(slotIndex, itemStack);
        }
    };

    private final LazyOptional<IItemHandler> itemHandlerCap = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IItemHandler> externalHandlerCap = LazyOptional.of(() -> externalHandler);
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
            if (side == null)
            {
                return itemHandlerCap.cast();
            }
                else
                {
                    return externalHandlerCap.cast();
                }
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

    public void tick()
    {
        if (level == null || level.isClientSide)
        {
            return;
        }

        if (maxProgress == 0)
        {
            matchRecipe();
        }

        if (maxProgress > 0)
        {
            progress++;
            setChanged();

            if (progress >= maxProgress)
            {
                generateProduct();
            }
        }
    }

    public void matchRecipe()
    {
        if (level == null || level.isClientSide)
        {
            return;
        }

        List<ShadowBlastFurnaceRecipe> recipes = level.getRecipeManager().getAllRecipesFor(ModRecipes.SHADOW_BLAST_FURNACE.get());

        List<ItemStack> inputItems = new ArrayList<>(2);
        inputItems.add(itemHandler.getStackInSlot(0).copy());
        inputItems.add(itemHandler.getStackInSlot(1).copy());

        List<ItemStack> outputItems = new ArrayList<>(2);
        outputItems.add(itemHandler.getStackInSlot(2).copy());
        outputItems.add(itemHandler.getStackInSlot(3).copy());

        List<FluidStack> inputFluids = new ArrayList<>(1);
        inputFluids.add(fluidTanks[0].getFluid().copy());

        //配方匹配
        MatchedRecipeResult<ShadowBlastFurnaceRecipe> matched = RecipeMatcher.match(inputItems, inputFluids, recipes);
        if (matched == null)
        {
            return;
        }

        ShadowBlastFurnaceRecipe recipe = matched.recipe();
        MachineInventory matchedRecipeInputsAndOutputs = matched.matchedRecipeInputsAndOutputs();

        List<ItemStack> requiredItems = matchedRecipeInputsAndOutputs.inputItemStacks();
        List<FluidStack> requiredFluids = matchedRecipeInputsAndOutputs.inputFluidStacks();
        List<ItemStack> outputItemsRecipe = matchedRecipeInputsAndOutputs.outputItemStacks();

        MachineInventory recipeInventory = new MachineInventory(requiredItems, requiredFluids, outputItemsRecipe, new ArrayList<>());
        MachineInventoryWithFluidSlotMaxStackSize machineData = new MachineInventoryWithFluidSlotMaxStackSize(inputItems.stream().map(ItemStack::copy).collect(Collectors.toList()), inputFluids.stream().map(FluidStack::copy).collect(Collectors.toList()), outputItems.stream().map(ItemStack::copy).collect(Collectors.toList()), new ArrayList<>(), 9000);
        MachineInventory result = RecipeProcesser.recipeProcessor(recipeInventory, machineData);

        if (result == null)
        {
            return;
        }

        //获取结果
        List<ItemStack> currentRecipeInput = result.inputItemStacks();
        currentRecipeOutput = result.outputItemStacks();

        itemHandler.setStackInSlot(0, currentRecipeInput.get(0));
        itemHandler.setStackInSlot(1, currentRecipeInput.get(1));

        List<FluidStack> newInputFluids = result.inputFluidStacks();

        fluidTanks[0].setFluid(newInputFluids.get(0));

        maxProgress = recipe.getProcessingTime();
        //同步
        setChangedAndSync();
    }

    private void generateProduct()
    {
        itemHandler.setStackInSlot(2, currentRecipeOutput.get(0));
        itemHandler.setStackInSlot(3, currentRecipeOutput.get(1));

        progress = 0;
        maxProgress = 0;
        //同步
        setChangedAndSync();
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
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put("FluidTank", fluidTanks[0].writeToNBT(new CompoundTag()));
        tag.put("Inventory", itemHandler.serializeNBT());
        ListTag outputList = new ListTag();
        for (ItemStack itemStack : currentRecipeOutput)
        {
            if (!itemStack.isEmpty())
            {
                outputList.add(itemStack.save(new CompoundTag()));
            }
        }
        tag.put("CurrentOutputs", outputList);
        tag.putInt("Progress", progress);
        tag.putInt("MaxProgress", maxProgress);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        fluidTanks[0].readFromNBT(tag.getCompound("FluidTank"));
        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        currentRecipeOutput = new ArrayList<>();
        ListTag outputList = tag.getList("CurrentOutputs", 10);
        for (int i = 0; i < outputList.size(); i++)
        {
            ItemStack itemStack = ItemStack.of(outputList.getCompound(i));
            if (!itemStack.isEmpty())
            {
                currentRecipeOutput.add(itemStack);
            }
        }
        progress = tag.getInt("Progress");
        maxProgress = tag.getInt("MaxProgress");
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".shadow_blast_furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new ShadowBlastFurnaceMenu(id, inventory, this);
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

    public int getProgress()
    {
        return progress;
    }

    public int getMaxProgress()
    {
        return maxProgress;
    }
}
