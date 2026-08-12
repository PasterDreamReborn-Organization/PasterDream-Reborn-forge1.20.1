package com.pasterdream.pasterdreammod.world.block.dreamcauldron;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.fluidhandler.IFluidHandlerProvider;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.network.animationstatechange.AnimationStateChangePacket;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.*;
import com.pasterdream.pasterdreammod.world.block.geckolibblock.AnimatableSync;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DreamCauldronBlockEntity extends BlockEntity implements MenuProvider, IFluidHandlerProvider, GeoBlockEntity, AnimatableSync
{
    private static final int FLUID0_CAPACITY = 2000;
    private static final int FLUID1_CAPACITY = 8000;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int animationState = 0;

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
            return slotIndex != 3;
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
            if(slotIndex == 3)
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
        fluidTank0Cap.invalidate();
        fluidTank1Cap.invalidate();
        itemHandlerCap.invalidate();
    }

    public void craft()
    {
        if (level == null || level.isClientSide)
        {
            return;
        }

        List<DreamCauldronRecipe> recipes = level.getRecipeManager().getAllRecipesFor(ModRecipes.DREAM_CAULDRON.get());

        List<ItemStack> inputItems = new ArrayList<>(3);
        inputItems.add(itemHandler.getStackInSlot(0).copy());
        inputItems.add(itemHandler.getStackInSlot(1).copy());
        inputItems.add(itemHandler.getStackInSlot(2).copy());

        List<ItemStack> outputItems = new ArrayList<>(1);
        outputItems.add(itemHandler.getStackInSlot(3).copy());

        List<FluidStack> inputFluids = new ArrayList<>(2);
        inputFluids.add(fluidTanks[0].getFluid().copy());
        inputFluids.add(fluidTanks[1].getFluid().copy());

        //配方匹配
        MatchedRecipeResult<DreamCauldronRecipe> matched = RecipeMatcher.match(inputItems, inputFluids, recipes);
        if (matched == null)
        {
            return;
        }

        DreamCauldronRecipe recipe = matched.recipe();

        MachineInventory matchedRecipeInputsAndOutputs = matched.matchedRecipeInputsAndOutputs();

        List<ItemStack> requiredItems = matchedRecipeInputsAndOutputs.inputItemStacks();
        List<FluidStack> requiredFluids = matchedRecipeInputsAndOutputs.inputFluidStacks();
        List<ItemStack> outputItemsRecipe = matchedRecipeInputsAndOutputs.outputItemStacks();

        MachineInventory recipeInventory = new MachineInventory(requiredItems, requiredFluids, outputItemsRecipe, new ArrayList<>());
        MachineInventoryWithFluidSlotMaxStackSize machineData = new MachineInventoryWithFluidSlotMaxStackSize(inputItems.stream().map(ItemStack::copy).collect(Collectors.toList()), inputFluids.stream().map(FluidStack::copy).collect(Collectors.toList()), outputItems.stream().map(ItemStack::copy).collect(Collectors.toList()), new ArrayList<>(), 2000);
        MachineInventory result = RecipeProcesser.recipeProcessor(recipeInventory, machineData);

        if (result == null)
        {
            return;
        }

        //获取结果
        List<ItemStack> newInputItems = result.inputItemStacks();
        List<ItemStack> newOutputItems = result.outputItemStacks();

        itemHandler.setStackInSlot(0, newInputItems.get(0));
        itemHandler.setStackInSlot(1, newInputItems.get(1));
        itemHandler.setStackInSlot(2, newInputItems.get(2));

        itemHandler.setStackInSlot(3, newOutputItems.get(0));

        List<FluidStack> newInputFluids = result.inputFluidStacks();

        fluidTanks[0].setFluid(newInputFluids.get(0));
        fluidTanks[1].setFluid(newInputFluids.get(1));

        //同步
        setChangedAndSync();
        setAnimationState(1);
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
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet)
    {
        CompoundTag tag = packet.getTag();
        if (tag != null)
        {
            load(tag);
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache()
    {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers)
    {
        controllers.add(new AnimationController<>(this, "state", 0, this::stateController));
    }

    private PlayState stateController(AnimationState<DreamCauldronBlockEntity> state)
    {
        AnimationController<DreamCauldronBlockEntity> controller = state.getController();

        if(animationState == 0)
        {
            controller.setAnimation(RawAnimation.begin().thenLoop("0"));
        }
            else
            {
                controller.setAnimation(RawAnimation.begin().thenPlay("1"));
                if(controller.getAnimationState() == AnimationController.State.STOPPED)
                {
                    animationState = 0;
                }
            }

        return PlayState.CONTINUE;
    }

    public void setAnimationState(int state)
    {
        this.animationState = state;
        if (level != null && !level.isClientSide)
        {
            sendAnimationSync();
        }
    }

    private void sendAnimationSync()
    {
        AnimationStateChangePacket packet = new AnimationStateChangePacket(this.worldPosition, this.animationState);
        ModNetwork.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(this.worldPosition)), packet);
    }

    public int getAnimationState()
    {
        return animationState;
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
