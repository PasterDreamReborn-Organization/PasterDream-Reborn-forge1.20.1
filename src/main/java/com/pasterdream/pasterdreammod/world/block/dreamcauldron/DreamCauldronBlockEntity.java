package com.pasterdream.pasterdreammod.world.block.dreamcauldron;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.fluidhandler.IFluidHandlerProvider;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.network.animationstatechange.AnimationStateChangePacket;
import com.pasterdream.pasterdreammod.network.DreamCauldronMessagePacket;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.*;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.CauldronPotionModule;
import com.pasterdream.pasterdreammod.world.block.geckolibblock.AnimatableSync;
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

public class DreamCauldronBlockEntity extends BlockEntity implements MenuProvider, IFluidHandlerProvider, GeoBlockEntity, AnimatableSync
{
    private static final int FLUID0_CAPACITY = 2000;
    private static final int FLUID1_CAPACITY = 8000;
    private static final int OUTPUT_FLUID_CAPACITY = 1000;

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
        },
        new FluidTank(OUTPUT_FLUID_CAPACITY)
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

    //自定义外部流体能力
    private class DreamCauldronFluidHandler implements IFluidHandler
    {
        @Override
        public int getTanks()
        {
            return fluidTanks.length;
        }

        @Override
        public FluidStack getFluidInTank(int tank)
        {
            if (tank < 0 || tank >= fluidTanks.length) return FluidStack.EMPTY;
            return fluidTanks[tank].getFluid();
        }

        @Override
        public int getTankCapacity(int tank)
        {
            if (tank < 0 || tank >= fluidTanks.length)
            {
                return 0;
            }
                else
                {
                    return fluidTanks[tank].getCapacity();
                }
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack)
        {
            return tank != 2;   //0和1号槽位允许输入
        }

        @Override
        public int fill(FluidStack resource, FluidAction action)
        {
            if (resource.isEmpty())
            {
                return 0;
            }
            //先尝试填充0号槽位，若不成功则尝试1号槽位
            int filled = fillTank(0, resource, action);
            if (filled > 0)
            {
                return filled;
            }
            return fillTank(1, resource, action);
        }

        private int fillTank(int index, FluidStack resource, FluidAction action)
        {
            FluidTank tank = fluidTanks[index];
            //输出槽禁止填充
            if (index == 2)
            {
                return 0;
            }

            //检查流体是否有效
            if (!tank.isFluidValid(resource))
            {
                return 0;
            }
            FluidStack current = tank.getFluid();

            //槽为空或流体类型匹配，尝试填充
            if (current.isEmpty() || current.isFluidEqual(resource))
            {
                return tank.fill(resource, action);
            }
            return 0;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action)
        {
            return fluidTanks[2].drain(resource, action);   //只允许从输出槽抽取
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action)
        {
            return fluidTanks[2].drain(maxDrain, action);
        }
    }

    private final LazyOptional<IItemHandler> itemHandlerCap = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IItemHandler> externalHandlerCap = LazyOptional.of(() -> externalHandler);
    private final LazyOptional<IFluidHandler> fluidHandlerCap = LazyOptional.of(DreamCauldronFluidHandler::new);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.FLUID_HANDLER)
        {
            return fluidHandlerCap.cast();
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
        fluidHandlerCap.invalidate();
        itemHandlerCap.invalidate();
        externalHandlerCap.invalidate();
    }

    public void craft(ServerPlayer player)
    {
        if (level == null || level.isClientSide)
        {
            return;
        }

        // 法术工厂药水模块优先处理（酿造/融合/勾兑）；未命中时交还原通用配方流程
        if (Config.dreamCauldronPotionEnabled)
        {
            CauldronPotionModule.ActionResult result = CauldronPotionModule.tryHandle(this);
            if (result.outcome() == CauldronPotionModule.Outcome.HANDLED)
            {
                if (result.message() != null && player != null)
                {
                    ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                            new DreamCauldronMessagePacket(result.message()));
                }
                return;
            }
        }

        List<DreamCauldronRecipe> recipes = level.getRecipeManager().getAllRecipesFor(ModRecipes.DREAM_CAULDRON.get());

        List<ItemStack> inputItems = new ArrayList<>(3);
        inputItems.add(itemHandler.getStackInSlot(0).copy());
        inputItems.add(itemHandler.getStackInSlot(1).copy());
        inputItems.add(itemHandler.getStackInSlot(2).copy());

        List<FluidStack> inputFluids = new ArrayList<>(2);
        inputFluids.add(fluidTanks[0].getFluid().copy());
        inputFluids.add(fluidTanks[1].getFluid().copy());

        List<ItemStack> outputItems = new ArrayList<>(1);
        outputItems.add(itemHandler.getStackInSlot(3).copy());

        List<FluidStack> outputFluids = new ArrayList<>(1);
        outputFluids.add(fluidTanks[2].getFluid().copy());

        GenericRecipeInventory matchedResult = GenericRecipeMatcher.match(inputItems, inputFluids, recipes);
        if(matchedResult != null)
        {
            GenericRecipeInventory processedResult = GenericRecipeProcesser.processing(matchedResult, new GenericRecipeInventory(inputItems, inputFluids, outputItems, outputFluids, 0, 1000));
            if(processedResult != null)
            {
                for(int i = 0; i < processedResult.inputItemStacks().size(); i++)
                {
                    itemHandler.setStackInSlot(i, processedResult.inputItemStacks().get(i));
                }

                for(int i = 0; i < processedResult.inputFluidStacks().size(); i++)
                {
                    fluidTanks[i].setFluid(processedResult.inputFluidStacks().get(i));
                }

                for(int i = 0; i < processedResult.outputItemStacks().size(); i++)
                {
                    itemHandler.setStackInSlot(i + 3, processedResult.outputItemStacks().get(i));
                }

                for(int i = 0; i < processedResult.outputFluidStacks().size(); i++)
                {
                    fluidTanks[i + 2].setFluid(processedResult.outputFluidStacks().get(i));
                }

                //同步
                setChangedAndSync();
                setAnimationState(1);
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
        tag.put("OutputFluidTank", fluidTanks[2].writeToNBT(new CompoundTag()));
        tag.put("Inventory", itemHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        fluidTanks[0].readFromNBT(tag.getCompound("FluidTank0"));
        fluidTanks[1].readFromNBT(tag.getCompound("FluidTank1"));
        fluidTanks[2].readFromNBT(tag.getCompound("OutputFluidTank"));
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
