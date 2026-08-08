package com.pasterdream.pasterdreammod.world.block.dreamaccumulator;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.dreamaccumulatorrecipe.DreamAccumulatorRecipeMatch;
import com.pasterdream.pasterdreammod.recipe.dreamaccumulatorrecipe.DreamAccumulatorRecipeMatchedData;
import com.pasterdream.pasterdreammod.recipe.dreamaccumulatorrecipe.DreamAccumulatorRecipeProcessor;
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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
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

public class DreamAccumulatorBlockEntity extends BlockEntity implements MenuProvider, GeoBlockEntity
{
    private double progress = 0;
    private int maxProgress = 0;
    private ItemStack catalyst = ItemStack.EMPTY;
    private ItemStack legacyCatalyst = ItemStack.EMPTY;
    private double catalystMultiplier = 0;
    private ItemStack output = ItemStack.EMPTY;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public DreamAccumulatorBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.DREAM_ACCUMULATOR.get(), pos, state);
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(2)
    {
        @Override
        protected void onContentsChanged(int slotIndex)
        {
            setChangedAndSync();
        }

        @Override
        public boolean isItemValid(int slotIndex, ItemStack stack)
        {
            return slotIndex == 1;
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
            if(slotIndex == 0)
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

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
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
            DreamAccumulatorRecipeMatchedData matchedRecipe = DreamAccumulatorRecipeMatch.matches(new DreamAccumulatorRecipeMatchedData(level.getBiome(worldPosition), 0, itemHandler.getStackInSlot(1), 0, itemHandler.getStackInSlot(0)), level.getRecipeManager().getAllRecipesFor(ModRecipes.DREAM_ACCUMULATOR.get()));
            if(matchedRecipe != null)
            {
                output = matchedRecipe.output();
                if(DreamAccumulatorRecipeProcessor.processor(output.copy(), itemHandler.getStackInSlot(0).copy()) != null)
                {
                    maxProgress = matchedRecipe.time();
                    catalyst = matchedRecipe.catalyst() != null ? matchedRecipe.catalyst() : ItemStack.EMPTY;
                    catalystMultiplier = matchedRecipe.catalystMultiplier();
                }
                    else
                    {
                        maxProgress = 0;
                    }
            }
        }

        if (maxProgress > 0)
        {
            if(!ItemStack.isSameItem(itemHandler.getStackInSlot(1), legacyCatalyst))
            {
                DreamAccumulatorRecipeMatchedData matchedRecipe = DreamAccumulatorRecipeMatch.matches(new DreamAccumulatorRecipeMatchedData(level.getBiome(worldPosition), 0, itemHandler.getStackInSlot(1), 0, itemHandler.getStackInSlot(0)), level.getRecipeManager().getAllRecipesFor(ModRecipes.DREAM_ACCUMULATOR.get()));
                if(matchedRecipe != null)
                {
                    if(matchedRecipe.time() == maxProgress && ItemStack.isSameItem(matchedRecipe.output(), output))
                    {
                        catalyst = matchedRecipe.catalyst() != null ? matchedRecipe.catalyst() : ItemStack.EMPTY;
                        catalystMultiplier = matchedRecipe.catalystMultiplier();
                    }
                        else
                        {
                            output = matchedRecipe.output();
                            if(DreamAccumulatorRecipeProcessor.processor(output.copy(), itemHandler.getStackInSlot(0).copy()) != null)
                            {
                                maxProgress = matchedRecipe.time();
                                catalyst = matchedRecipe.catalyst() != null ? matchedRecipe.catalyst() : ItemStack.EMPTY;
                                catalystMultiplier = matchedRecipe.catalystMultiplier();
                            }
                                else
                                {
                                    maxProgress = 0;
                                }
                        }
                }
            }

            if(catalyst != null && itemHandler.getStackInSlot(1) != ItemStack.EMPTY && ItemStack.isSameItem(catalyst, itemHandler.getStackInSlot(1)))
            {
                progress += catalystMultiplier;
                legacyCatalyst = itemHandler.getStackInSlot(1);
                if(itemHandler.getStackInSlot(1).hasTag() && itemHandler.getStackInSlot(1).getTag().contains("Damage"))
                {
                    itemHandler.getStackInSlot(1).hurt(1, level.getRandom(), null);
                }
            }
                else
                {
                    progress++;
                }

            setChanged();
            if (progress >= maxProgress)
            {
                ItemStack result = DreamAccumulatorRecipeProcessor.processor(output, itemHandler.getStackInSlot(0));
                if (result != null)
                {
                    itemHandler.setStackInSlot(0, result);
                    progress = 0;
                    maxProgress = 0;
                    output = ItemStack.EMPTY;
                    catalyst = ItemStack.EMPTY;
                    catalystMultiplier = 1.0f;
                    setChanged();
                }
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
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".dream_accumulator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new DreamAccumulatorMenu(id, inventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put("Inventory", itemHandler.serializeNBT());
        if (!output.isEmpty())
        {
            tag.put("Output", output.serializeNBT());
        }
        if (!catalyst.isEmpty())
        {
            tag.put("Catalyst", catalyst.serializeNBT());
            tag.put("LegacyCatalyst", legacyCatalyst.serializeNBT());
        }
        tag.putDouble("CatalystMultiplier", catalystMultiplier);
        tag.putDouble("Progress", progress);
        tag.putInt("MaxProgress", maxProgress);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        if (tag.contains("Output"))
        {
            output = ItemStack.of(tag.getCompound("Output"));
        }
            else
            {
                output = ItemStack.EMPTY;
            }
        if (tag.contains("Catalyst"))
        {
            catalyst = ItemStack.of(tag.getCompound("Catalyst"));
            legacyCatalyst = ItemStack.of(tag.getCompound("LegacyCatalyst"));
        }
            else
            {
                catalyst = ItemStack.EMPTY;
                legacyCatalyst = ItemStack.EMPTY;
            }
        catalystMultiplier = tag.getDouble("CatalystMultiplier");
        progress = tag.getDouble("Progress");
        maxProgress = tag.getInt("MaxProgress");
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

    private PlayState stateController(AnimationState<DreamAccumulatorBlockEntity> state)
    {
        AnimationController<DreamAccumulatorBlockEntity> controller = state.getController();
        controller.setAnimation(RawAnimation.begin().thenLoop("0"));
        return PlayState.CONTINUE;
    }

    public double getProgress()
    {
        return progress;
    }

    public int getMaxProgress()
    {
        return maxProgress;
    }

    public double getCatalystMultiplier()
    {
        return catalystMultiplier;
    }

    public ItemStackHandler getItemHandler()
    {
        return itemHandler;
    }
}
