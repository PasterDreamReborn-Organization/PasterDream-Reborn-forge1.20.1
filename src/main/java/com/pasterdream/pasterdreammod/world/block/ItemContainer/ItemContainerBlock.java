package com.pasterdream.pasterdreammod.world.block.ItemContainer;

import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.blockentity.HorizontalDirectionalBlockBenchBaseEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

public abstract class ItemContainerBlock extends HorizontalDirectionalBlockBenchBaseEntityBlock
{
    public ItemContainerBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public abstract VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context);

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPosition, Player player, InteractionHand hand, BlockHitResult result)
    {
        if (!level.isClientSide)
        {
            BlockEntity blockEntity = level.getBlockEntity(blockPosition);
            if (blockEntity instanceof ItemContainerBlockEntity containerBlockEntity)
            {
                // 在打开容器前先尝试从战利品表生成物品
                containerBlockEntity.unpackLootTable(player);
            }
            if (blockEntity instanceof MenuProvider menu)
            {
                NetworkHooks.openScreen((ServerPlayer) player, menu, buf -> buf.writeBlockPos(blockPosition));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return null;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved)
    {
        if (!state.is(newState.getBlock()))
        {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof IItemContainerInventory Crate)
            {
                ItemStackHandler handler = Crate.getItemHandler();
                for (int i = 0; i < handler.getSlots(); i++)
                {
                    ItemStack stack = handler.getStackInSlot(i);
                    if (!stack.isEmpty())
                    {
                        Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
                    }
                }
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, moved);
        }
    }
}
