package com.pasterdream.pasterdreammod.world.block.shadowblastfurnace;

import com.pasterdream.pasterdreammod.helper.multiblockproperties.MultiBlockProperties;
import com.pasterdream.pasterdreammod.helper.multiblockproperties._3x3x3Part;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition._3x3x3_CalculatePartPosition;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.voxelshapecalculator.SingleFloorVoxelShapeCalculator;
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
import net.minecraft.world.level.block.Block;
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

import java.util.List;

import static net.minecraft.world.Containers.dropItemStack;

public class ShadowBlastFurnaceBlock extends HorizontalDirectionalBlockBenchBaseEntityBlock
{
    public static final EnumProperty<_3x3x3Part> PART = MultiBlockProperties._3x3x3PART;

    public ShadowBlastFurnaceBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART, _3x3x3Part.MAIN));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        if(blockState.getValue(PART) == _3x3x3Part.MAIN)
        {
            return new ShadowBlastFurnaceBlockEntity(blockPosition, blockState);
        }
            else
            {
                return new ShadowBlastFurnaceAddonBlockEntity(blockPosition, blockState);
            }
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        Direction facing = blockState.getValue(FACING);
        VoxelShape shape = null;

        List<List<List<VoxelShape>>> Floor0ListListListVoxelShape = SingleFloorVoxelShapeCalculator.calculateAllDirectionSingleFloorVoxelShapeFromEastVoxelShape(7 / 16.0 ,0, 7 / 16.0, 41 / 16.0, 16 / 16.0, 41 / 16.0);
        List<List<List<VoxelShape>>> Floor1ListListListVoxelShape = SingleFloorVoxelShapeCalculator.calculateAllDirectionSingleFloorVoxelShapeFromEastVoxelShape(11 / 16.0 ,0, 11 / 16.0, 37 / 16.0, 16 / 16.0, 37 / 16.0);
        List<List<List<VoxelShape>>> Floor2ListListListVoxelShape = SingleFloorVoxelShapeCalculator.calculateAllDirectionSingleFloorVoxelShapeFromEastVoxelShape(13 / 16.0 ,0, 13 / 16.0, 35 / 16.0, 11 / 16.0, 35 / 16.0);

        switch (blockState.getValue(PART))
        {
            case ADDON_0_0_0 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor0ListListListVoxelShape.get(2).get(2).get(0);
                    case SOUTH -> shape = Floor0ListListListVoxelShape.get(2).get(2).get(1);
                    case WEST -> shape = Floor0ListListListVoxelShape.get(2).get(2).get(2);
                    case NORTH -> shape = Floor0ListListListVoxelShape.get(2).get(2).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_0_1 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor0ListListListVoxelShape.get(1).get(2).get(0);
                    case SOUTH -> shape = Floor0ListListListVoxelShape.get(1).get(2).get(1);
                    case WEST -> shape = Floor0ListListListVoxelShape.get(1).get(2).get(2);
                    case NORTH -> shape = Floor0ListListListVoxelShape.get(1).get(2).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_0_2 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor0ListListListVoxelShape.get(0).get(2).get(0);
                    case SOUTH -> shape = Floor0ListListListVoxelShape.get(0).get(2).get(1);
                    case WEST -> shape = Floor0ListListListVoxelShape.get(0).get(2).get(2);
                    case NORTH -> shape = Floor0ListListListVoxelShape.get(0).get(2).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_0_0 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor0ListListListVoxelShape.get(2).get(1).get(0);
                    case SOUTH -> shape = Floor0ListListListVoxelShape.get(2).get(1).get(1);
                    case WEST -> shape = Floor0ListListListVoxelShape.get(2).get(1).get(2);
                    case NORTH -> shape = Floor0ListListListVoxelShape.get(2).get(1).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case MAIN ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor0ListListListVoxelShape.get(1).get(1).get(0);
                    case SOUTH -> shape = Floor0ListListListVoxelShape.get(1).get(1).get(1);
                    case WEST -> shape = Floor0ListListListVoxelShape.get(1).get(1).get(2);
                    case NORTH -> shape = Floor0ListListListVoxelShape.get(1).get(1).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_0_2 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor0ListListListVoxelShape.get(0).get(1).get(0);
                    case SOUTH -> shape = Floor0ListListListVoxelShape.get(0).get(1).get(1);
                    case WEST -> shape = Floor0ListListListVoxelShape.get(0).get(1).get(2);
                    case NORTH -> shape = Floor0ListListListVoxelShape.get(0).get(1).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_2_0_0 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor0ListListListVoxelShape.get(2).get(0).get(0);
                    case SOUTH -> shape = Floor0ListListListVoxelShape.get(2).get(0).get(1);
                    case WEST -> shape = Floor0ListListListVoxelShape.get(2).get(0).get(2);
                    case NORTH -> shape = Floor0ListListListVoxelShape.get(2).get(0).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_2_0_1 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor0ListListListVoxelShape.get(1).get(0).get(0);
                    case SOUTH -> shape = Floor0ListListListVoxelShape.get(1).get(0).get(1);
                    case WEST -> shape = Floor0ListListListVoxelShape.get(1).get(0).get(2);
                    case NORTH -> shape = Floor0ListListListVoxelShape.get(1).get(0).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_2_0_2 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor0ListListListVoxelShape.get(0).get(0).get(0);
                    case SOUTH -> shape = Floor0ListListListVoxelShape.get(0).get(0).get(1);
                    case WEST -> shape = Floor0ListListListVoxelShape.get(0).get(0).get(2);
                    case NORTH -> shape = Floor0ListListListVoxelShape.get(0).get(0).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_1_0 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor1ListListListVoxelShape.get(2).get(2).get(0);
                    case SOUTH -> shape = Floor1ListListListVoxelShape.get(2).get(2).get(1);
                    case WEST -> shape = Floor1ListListListVoxelShape.get(2).get(2).get(2);
                    case NORTH -> shape = Floor1ListListListVoxelShape.get(2).get(2).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_1_1 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor1ListListListVoxelShape.get(1).get(2).get(0);
                    case SOUTH -> shape = Floor1ListListListVoxelShape.get(1).get(2).get(1);
                    case WEST -> shape = Floor1ListListListVoxelShape.get(1).get(2).get(2);
                    case NORTH -> shape = Floor1ListListListVoxelShape.get(1).get(2).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_1_2 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor1ListListListVoxelShape.get(0).get(2).get(0);
                    case SOUTH -> shape = Floor1ListListListVoxelShape.get(0).get(2).get(1);
                    case WEST -> shape = Floor1ListListListVoxelShape.get(0).get(2).get(2);
                    case NORTH -> shape = Floor1ListListListVoxelShape.get(0).get(2).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_1_0 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor1ListListListVoxelShape.get(2).get(1).get(0);
                    case SOUTH -> shape = Floor1ListListListVoxelShape.get(2).get(1).get(1);
                    case WEST -> shape = Floor1ListListListVoxelShape.get(2).get(1).get(2);
                    case NORTH -> shape = Floor1ListListListVoxelShape.get(2).get(1).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_1_1 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor1ListListListVoxelShape.get(1).get(1).get(0);
                    case SOUTH -> shape = Floor1ListListListVoxelShape.get(1).get(1).get(1);
                    case WEST -> shape = Floor1ListListListVoxelShape.get(1).get(1).get(2);
                    case NORTH -> shape = Floor1ListListListVoxelShape.get(1).get(1).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_1_2 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor1ListListListVoxelShape.get(0).get(1).get(0);
                    case SOUTH -> shape = Floor1ListListListVoxelShape.get(0).get(1).get(1);
                    case WEST -> shape = Floor1ListListListVoxelShape.get(0).get(1).get(2);
                    case NORTH -> shape = Floor1ListListListVoxelShape.get(0).get(1).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_2_1_0 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor1ListListListVoxelShape.get(2).get(0).get(0);
                    case SOUTH -> shape = Floor1ListListListVoxelShape.get(2).get(0).get(1);
                    case WEST -> shape = Floor1ListListListVoxelShape.get(2).get(0).get(2);
                    case NORTH -> shape = Floor1ListListListVoxelShape.get(2).get(0).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_2_1_1 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor1ListListListVoxelShape.get(1).get(0).get(0);
                    case SOUTH -> shape = Floor1ListListListVoxelShape.get(1).get(0).get(1);
                    case WEST -> shape = Floor1ListListListVoxelShape.get(1).get(0).get(2);
                    case NORTH -> shape = Floor1ListListListVoxelShape.get(1).get(0).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_2_1_2 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor1ListListListVoxelShape.get(0).get(0).get(0);
                    case SOUTH -> shape = Floor1ListListListVoxelShape.get(0).get(0).get(1);
                    case WEST -> shape = Floor1ListListListVoxelShape.get(0).get(0).get(2);
                    case NORTH -> shape = Floor1ListListListVoxelShape.get(0).get(0).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_2_0 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor2ListListListVoxelShape.get(2).get(2).get(0);
                    case SOUTH -> shape = Floor2ListListListVoxelShape.get(2).get(2).get(1);
                    case WEST -> shape = Floor2ListListListVoxelShape.get(2).get(2).get(2);
                    case NORTH -> shape = Floor2ListListListVoxelShape.get(2).get(2).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_2_1 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor2ListListListVoxelShape.get(1).get(2).get(0);
                    case SOUTH -> shape = Floor2ListListListVoxelShape.get(1).get(2).get(1);
                    case WEST -> shape = Floor2ListListListVoxelShape.get(1).get(2).get(2);
                    case NORTH -> shape = Floor2ListListListVoxelShape.get(1).get(2).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_2_2 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor2ListListListVoxelShape.get(0).get(2).get(0);
                    case SOUTH -> shape = Floor2ListListListVoxelShape.get(0).get(2).get(1);
                    case WEST -> shape = Floor2ListListListVoxelShape.get(0).get(2).get(2);
                    case NORTH -> shape = Floor2ListListListVoxelShape.get(0).get(2).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_2_0 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor2ListListListVoxelShape.get(2).get(1).get(0);
                    case SOUTH -> shape = Floor2ListListListVoxelShape.get(2).get(1).get(1);
                    case WEST -> shape = Floor2ListListListVoxelShape.get(2).get(1).get(2);
                    case NORTH -> shape = Floor2ListListListVoxelShape.get(2).get(1).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_2_1 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor2ListListListVoxelShape.get(1).get(1).get(0);
                    case SOUTH -> shape = Floor2ListListListVoxelShape.get(1).get(1).get(1);
                    case WEST -> shape = Floor2ListListListVoxelShape.get(1).get(1).get(2);
                    case NORTH -> shape = Floor2ListListListVoxelShape.get(1).get(1).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_2_2 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor2ListListListVoxelShape.get(0).get(1).get(0);
                    case SOUTH -> shape = Floor2ListListListVoxelShape.get(0).get(1).get(1);
                    case WEST -> shape = Floor2ListListListVoxelShape.get(0).get(1).get(2);
                    case NORTH -> shape = Floor2ListListListVoxelShape.get(0).get(1).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_2_2_0 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor2ListListListVoxelShape.get(2).get(0).get(0);
                    case SOUTH -> shape = Floor2ListListListVoxelShape.get(2).get(0).get(1);
                    case WEST -> shape = Floor2ListListListVoxelShape.get(2).get(0).get(2);
                    case NORTH -> shape = Floor2ListListListVoxelShape.get(2).get(0).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_2_2_1 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor2ListListListVoxelShape.get(1).get(0).get(0);
                    case SOUTH -> shape = Floor2ListListListVoxelShape.get(1).get(0).get(1);
                    case WEST -> shape = Floor2ListListListVoxelShape.get(1).get(0).get(2);
                    case NORTH -> shape = Floor2ListListListVoxelShape.get(1).get(0).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_2_2_2 ->
            {
                switch (facing)
                {
                    case EAST -> shape = Floor2ListListListVoxelShape.get(0).get(0).get(0);
                    case SOUTH -> shape = Floor2ListListListVoxelShape.get(0).get(0).get(1);
                    case WEST -> shape = Floor2ListListListVoxelShape.get(0).get(0).get(2);
                    case NORTH -> shape = Floor2ListListListVoxelShape.get(0).get(0).get(3);
                    default -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
        }
            return shape;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type)
    {
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, PART);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPosition, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        if (!level.isClientSide)
        {
            _3x3x3Part part = blockState.getValue(PART);
            BlockPos targetPosition = blockPosition;

            if(part != _3x3x3Part.MAIN)
            {
                targetPosition = _3x3x3_CalculatePartPosition.getMainPosFromAddon(blockPosition, blockState.getValue(ShadowBlastFurnaceBlock.FACING), blockState.getValue(ShadowBlastFurnaceBlock.PART));
            }

            BlockEntity blockEntity = level.getBlockEntity(targetPosition);
            if (blockEntity instanceof ShadowBlastFurnaceBlockEntity shadowBlastFurnace)
            {
                final BlockPos finalTargetPosition = targetPosition;
                NetworkHooks.openScreen((ServerPlayer) player, shadowBlastFurnace, buf -> buf.writeBlockPos(finalTargetPosition));
            }

        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPosition, BlockState newState, boolean movedByPiston)
    {
        if (!blockState.is(newState.getBlock()))
        {
            Direction facing = blockState.getValue(FACING);
            _3x3x3Part part = blockState.getValue(PART);

            if (part == _3x3x3Part.MAIN)
            {
                for (_3x3x3Part eachPart : _3x3x3Part.values())
                {
                    if (eachPart == _3x3x3Part.MAIN)
                    {
                        continue;
                    }

                    BlockPos addonPos = _3x3x3_CalculatePartPosition.getPartPos(blockPosition, facing, eachPart);
                    BlockState addonState = level.getBlockState(addonPos);
                    if (addonState.getBlock() instanceof ShadowBlastFurnaceBlock)
                    {
                        level.destroyBlock(addonPos, false);
                    }
                }

                BlockEntity blockEntity = level.getBlockEntity(blockPosition);
                if(blockEntity instanceof ShadowBlastFurnaceBlockEntity shadowBlastFurnace)
                {
                    for (int i = 0; i < 4; i++)
                    {
                        dropItemStack(level, blockPosition.getX() + 0.5, blockPosition.getY() + 0.5, blockPosition.getZ() + 0.5, shadowBlastFurnace.getItemHandler().getStackInSlot(i));
                    }
                    level.updateNeighbourForOutputSignal(blockPosition, this);
                }
            }
                else
                {
                    BlockPos mainPos = _3x3x3_CalculatePartPosition.getMainPosFromAddon(blockPosition, facing, part);
                    BlockState mainState = level.getBlockState(mainPos);
                    if (mainState.getBlock() instanceof ShadowBlastFurnaceBlock)
                    {
                        level.destroyBlock(mainPos, false);
                    }
                }
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos BlockPosition, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        Direction facing = state.getValue(FACING);

        if (!level.isClientSide)
        {
            super.setPlacedBy(level, BlockPosition, state, placer, stack);
            for (_3x3x3Part part : _3x3x3Part.values())
            {
                if (part == _3x3x3Part.MAIN)
                {
                    continue;
                }
                BlockPos partPos = _3x3x3_CalculatePartPosition.getPartPos(BlockPosition, facing, part);
                level.setBlock(partPos, this.defaultBlockState().setValue(PART, part).setValue(FACING, facing), 3);
                level.updateNeighborsAt(partPos, this);
            }
        }
    }
}
