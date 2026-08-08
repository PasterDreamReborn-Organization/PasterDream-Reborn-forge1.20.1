package com.pasterdream.pasterdreammod.world.block.researchtable;

import com.pasterdream.pasterdreammod.helper.multiblockproperties._2Part;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.MultiBlockProperties;
import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.blockentity.HorizontalDirectionalBlockBenchBaseEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.Containers.dropItemStack;

public class ResearchTableBlock extends HorizontalDirectionalBlockBenchBaseEntityBlock
{
    public static final EnumProperty<_2Part> PART = MultiBlockProperties._2PART;

    public ResearchTableBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART, _2Part.MAIN));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        if (blockState.getValue(PART) == _2Part.MAIN)
        {
            return new ResearchTableBlockEntity(blockPosition, blockState);
        }
            else
            {
                return new ResearchTableAddonBlockEntity(blockPosition, blockState);
            }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, PART);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        return box(0, 0, 0, 16, 15, 16);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPosition, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        if (!level.isClientSide)
        {
            _2Part part = blockState.getValue(PART);
            BlockPos targetPosition = blockPosition;

            if (part == _2Part.ADDON)
            {
                Direction facing = blockState.getValue(FACING);
                Direction toMain = facing.getClockWise();
                targetPosition = blockPosition.relative(toMain);
            }

            BlockEntity blockEntity = level.getBlockEntity(targetPosition);
            if (blockEntity instanceof ResearchTableBlockEntity researchTable)
            {
                final BlockPos finalTargetPosition = targetPosition;
                NetworkHooks.openScreen((ServerPlayer) player, researchTable, buf -> buf.writeBlockPos(finalTargetPosition));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type)
    {
        return null;
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPosition, BlockState newState, boolean movedByPiston)
    {
        if (!blockState.is(newState.getBlock()))
        {
            _2Part part = blockState.getValue(PART);

            if (part == _2Part.MAIN)
            {
                BlockEntity blockEntity = level.getBlockEntity(blockPosition);
                if (blockEntity instanceof ResearchTableBlockEntity researchTable)
                {
                    for (int i = 0; i < 6; i++)
                    {
                        dropItemStack(level, blockPosition.getX() + 0.5, blockPosition.getY() + 0.5, blockPosition.getZ() + 0.5, researchTable.getItemHandler().getStackInSlot(i));
                    }
                    level.updateNeighbourForOutputSignal(blockPosition, this);
                }
            }

            Direction facing = blockState.getValue(FACING);
            Direction otherDirection = (part == _2Part.MAIN) ? facing.getCounterClockWise() : facing.getClockWise();
            BlockPos otherPos = blockPosition.relative(otherDirection);

            BlockState otherState = level.getBlockState(otherPos);
            if (otherState.getBlock() == this)
            {
                level.destroyBlock(otherPos, false);
            }

            super.onRemove(blockState, level, blockPosition, newState, movedByPiston);
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        Direction facing = state.getValue(FACING);
        Direction addonDirection = facing.getCounterClockWise();
        BlockPos addonPos = pos.relative(addonDirection);

        if (!level.getBlockState(addonPos).canBeReplaced())
        {
            return;
        }

        super.setPlacedBy(level, pos, state, placer, stack);

        if (!level.isClientSide)
        {
            BlockState addonState = state.setValue(PART, _2Part.ADDON);
            level.setBlock(addonPos, addonState, 3);
            level.updateNeighborsAt(addonPos, this);
        }
    }
}
