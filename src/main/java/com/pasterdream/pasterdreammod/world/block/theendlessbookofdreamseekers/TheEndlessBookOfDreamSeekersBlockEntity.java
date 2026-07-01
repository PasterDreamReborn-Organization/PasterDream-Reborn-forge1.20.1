package com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TheEndlessBookOfDreamSeekersBlockEntity extends BlockEntity implements GeoBlockEntity
{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public TheEndlessBookOfDreamSeekersBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get(), pos, state);
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(1)
    {
        @Override
        protected void onContentsChanged(int slotIndex)
        {
            setChangedAndSync();
        }

        @Override
        public boolean isItemValid(int slotIndex, ItemStack stack)
        {
            return true;
        }
    };

    private final LazyOptional<IItemHandler> itemHandlerCap = LazyOptional.of(() -> itemHandler);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
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
        itemHandlerCap.invalidate();
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
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put("Inventory", itemHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache()
    {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers)
    {
        controllers.add(new AnimationController<GeoAnimatable>(this, "state", 0, event -> PlayState.STOP));
    }

    public ItemStackHandler getItemHandler()
    {
        return itemHandler;
    }
}
