package com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.blockentity.HorizontalDirectionalGeckolibBaseEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class TheEndlessBookOfDreamSeekersBlock extends HorizontalDirectionalGeckolibBaseEntityBlock
{
    public TheEndlessBookOfDreamSeekersBlock(Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        return new TheEndlessBookOfDreamSeekersBlockEntity(blockPosition, blockState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPosition, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        if (!level.isClientSide)
        {
            BlockEntity blockEntity = level.getBlockEntity(blockPosition);
            if (blockEntity instanceof TheEndlessBookOfDreamSeekersBlockEntity theEndlessBookOfDreamSeekersBlockEntity)
            {
                LazyOptional<IItemHandler> capability = theEndlessBookOfDreamSeekersBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER);
                capability.ifPresent(handler ->
                {
                    ItemStack stackInSlot = handler.getStackInSlot(0);
                    if (!stackInSlot.isEmpty())
                    {
                        theEndlessBookOfDreamSeekersBlockEntity.setAnimationState(1);
                        theEndlessBookOfDreamSeekersBlockEntity.triggerUseParticles(stackInSlot.copy());
                    }
                });
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type)
    {
        return type == ModBlockEntities.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get()
            ? (level.isClientSide ? null : (lvl, pos, state, be) -> ((TheEndlessBookOfDreamSeekersBlockEntity) be).serverTick(lvl, pos))
            : null;
    }
}
