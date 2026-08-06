package com.pasterdream.pasterdreammod.world.block.shadowbed;

import com.pasterdream.pasterdreammod.helper.multiblockproperties.MultiBlockProperties;
import com.pasterdream.pasterdreammod.helper.multiblockproperties._2Part;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.voxelshapecalculator.VoxelShapeCalculator;
import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.blockentity.HorizontalDirectionalBlockBenchBaseEntityBlock;
import com.pasterdream.pasterdreammod.world.block.twilightlantern.TrueShadowBedInteractionHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShadowBedBlock extends HorizontalDirectionalBlockBenchBaseEntityBlock
{
    public static final EnumProperty<_2Part> PART = MultiBlockProperties._2PART;

    public ShadowBedBlock()
    {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(-1, 3600000).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART, _2Part.MAIN));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        List<VoxelShape> MainListVoxelShape = VoxelShapeCalculator.calculateAllDirectionVoxelShapeFromEastVoxelShape(0 / 16.0, 0, -3 / 16.0, 19 / 16.0, 11 / 16.0, 19 / 16.0);
        List<VoxelShape> AddonListVoxelShape = VoxelShapeCalculator.calculateAllDirectionVoxelShapeFromEastVoxelShape(-3 / 16.0, 0, -3 / 16.0, 16 / 16.0, 11 / 16.0, 19 / 16.0);

        Direction facing = blockState.getValue(FACING);
        switch (blockState.getValue(PART))
        {
            case MAIN ->
            {
                return switch (facing)
                {
                    case EAST  -> MainListVoxelShape.get(0);
                    case SOUTH -> MainListVoxelShape.get(1);
                    case WEST  -> MainListVoxelShape.get(2);
                    case NORTH -> MainListVoxelShape.get(3);
                    default -> box(0, 0, 0, 16, 16, 16);
                };
            }
            case ADDON ->
            {
                return switch (facing)
                {
                    case EAST  -> AddonListVoxelShape.get(0);
                    case SOUTH -> AddonListVoxelShape.get(1);
                    case WEST  -> AddonListVoxelShape.get(2);
                    case NORTH -> AddonListVoxelShape.get(3);
                    default -> box(0, 0, 0, 16, 16, 16);
                };
            }
            default ->
            {
                return box(0, 0, 0, 16, 16, 16);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, PART);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        if (blockState.getValue(PART) == _2Part.MAIN)
        {
            return new ShadowBedBlockEntity(blockPosition, blockState);
        }
            else
            {
                return null;
            }
    }

    @Override
    public InteractionResult use(BlockState blockState, Level world, BlockPos blockPosition, Player entity, InteractionHand hand, BlockHitResult hit)
    {
        _2Part part = blockState.getValue(PART);
        if (part == _2Part.ADDON)
        {
            blockPosition = blockPosition.relative(blockState.getValue(FACING));
        }

        TrueShadowBedInteractionHandler.execute(world, blockPosition, entity);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPosition, BlockState newState, boolean movedByPiston)
    {
        if (!blockState.is(newState.getBlock()))
        {
            _2Part part = blockState.getValue(PART);

            Direction facing = blockState.getValue(FACING);
            Direction otherDirection = (part == _2Part.MAIN) ? facing.getOpposite() : facing;
            BlockPos otherPos = blockPosition.relative(otherDirection);

            BlockState otherState = level.getBlockState(otherPos);
            if (otherState.getBlock() == this)
            {
                level.removeBlock(otherPos, false);
            }

            super.onRemove(blockState, level, blockPosition, newState, movedByPiston);
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        Direction facing = state.getValue(FACING);
        Direction addonDirection = facing.getOpposite();
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
