package com.pasterdream.pasterdreammod.world.block.dreamaccumulator;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.Containers.dropItemStack;

public class DreamAccumulatorBlock extends BaseEntityBlock
{
    public DreamAccumulatorBlock(Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        return new DreamAccumulatorBlockEntity(blockPosition, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        return box(0, 0, 0, 16, 4, 16);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPosition, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        if (!level.isClientSide)
        {
            BlockEntity blockEntity = level.getBlockEntity(blockPosition);
            if (blockEntity instanceof DreamAccumulatorBlockEntity dreamAccumulator)
            {
                NetworkHooks.openScreen((ServerPlayer) player, dreamAccumulator, buf -> buf.writeBlockPos(blockPosition));
                level.playSound(null, blockPosition, ModSounds.DREAM_ACCUMULATOR.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type)
    {
        return createTickerHelper(type, ModBlockEntities.DREAM_ACCUMULATOR.get(), (lvl, blockPosition, state, blockEntity) -> blockEntity.tick());
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston)
    {
        if (!state.is(newState.getBlock()))
        {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof DreamAccumulatorBlockEntity dreamAccumulator)
            {
                for(int i = 0; i < 2; i++)
                {
                    dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, dreamAccumulator.getItemHandler().getStackInSlot(i));
                }

                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }
}
