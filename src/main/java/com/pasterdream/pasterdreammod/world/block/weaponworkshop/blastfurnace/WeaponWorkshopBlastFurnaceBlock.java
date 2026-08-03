package com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace;

import com.pasterdream.pasterdreammod.helper.multiblockproperties.MultiBlockProperties;
import com.pasterdream.pasterdreammod.helper.multiblockproperties._2x4x2Part;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition.CalculatePartPosition;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.voxelshapecalculator.SingleFloorVoxelShapeCalculator;
import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.blockentity.HorizontalDirectionalBlockBenchBaseEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeaponWorkshopBlastFurnaceBlock extends HorizontalDirectionalBlockBenchBaseEntityBlock
{
    public static final EnumProperty<_2x4x2Part> PART = MultiBlockProperties._2x4x2PART;

    public WeaponWorkshopBlastFurnaceBlock(Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        if(blockState.getValue(PART) == _2x4x2Part.MAIN)
        {
            return new WeaponWorkshopBlastFurnaceBlockEntity(blockPosition, blockState);
        }
            else
            {
                return new WeaponWorkshopBlastFurnaceAddonBlockEntity(blockPosition, blockState);
            }
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        Direction facing = blockState.getValue(FACING);
        VoxelShape shape = null;

        List<List<List<VoxelShape>>> Floor0ListListListVoxelShape = SingleFloorVoxelShapeCalculator.calculateAllDirectionSingleFloorVoxelShapeFromEastVoxelShape(1 / 16.0 ,0, 1 / 16.0, 32 / 16.0, 16 / 16.0, 31 / 16.0);
        List<List<List<VoxelShape>>> Floor1ListListListVoxelShape = SingleFloorVoxelShapeCalculator.calculateAllDirectionSingleFloorVoxelShapeFromEastVoxelShape(3 / 16.0 ,0, 2 / 16.0, 22 / 16.0, 16 / 16.0, 30 / 16.0);
        List<List<List<VoxelShape>>> Floor2ListListListVoxelShape = SingleFloorVoxelShapeCalculator.calculateAllDirectionSingleFloorVoxelShapeFromEastVoxelShape(6 / 16.0 ,0, 9 / 16.0, 18 / 16.0, 16 / 16.0, 23 / 16.0);
        List<List<List<VoxelShape>>> Floor3ListListListVoxelShape = SingleFloorVoxelShapeCalculator.calculateAllDirectionSingleFloorVoxelShapeFromEastVoxelShape(4 / 16.0 ,0, 8 / 16.0, 20 / 16.0, 7 / 16.0, 24 / 16.0);

        switch (blockState.getValue(PART))
        {
            case MAIN ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor0ListListListVoxelShape.get(1).get(1).get(0);
                    case SOUTH -> shape = Floor0ListListListVoxelShape.get(1).get(1).get(1);
                    case WEST  -> shape = Floor0ListListListVoxelShape.get(1).get(1).get(2);
                    case NORTH -> shape = Floor0ListListListVoxelShape.get(1).get(1).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_0_1 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor0ListListListVoxelShape.get(0).get(1).get(0);
                    case SOUTH -> shape = Floor0ListListListVoxelShape.get(0).get(1).get(1);
                    case WEST  -> shape = Floor0ListListListVoxelShape.get(0).get(1).get(2);
                    case NORTH -> shape = Floor0ListListListVoxelShape.get(0).get(1).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_0_0 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor0ListListListVoxelShape.get(1).get(0).get(0);
                    case SOUTH -> shape = Floor0ListListListVoxelShape.get(1).get(0).get(1);
                    case WEST  -> shape = Floor0ListListListVoxelShape.get(1).get(0).get(2);
                    case NORTH -> shape = Floor0ListListListVoxelShape.get(1).get(0).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_0_1 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor0ListListListVoxelShape.get(0).get(0).get(0);
                    case SOUTH -> shape = Floor0ListListListVoxelShape.get(0).get(0).get(1);
                    case WEST  -> shape = Floor0ListListListVoxelShape.get(0).get(0).get(2);
                    case NORTH -> shape = Floor0ListListListVoxelShape.get(0).get(0).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_1_0 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor1ListListListVoxelShape.get(1).get(1).get(0);
                    case SOUTH -> shape = Floor1ListListListVoxelShape.get(1).get(1).get(1);
                    case WEST  -> shape = Floor1ListListListVoxelShape.get(1).get(1).get(2);
                    case NORTH -> shape = Floor1ListListListVoxelShape.get(1).get(1).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_1_1 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor1ListListListVoxelShape.get(0).get(1).get(0);
                    case SOUTH -> shape = Floor1ListListListVoxelShape.get(0).get(1).get(1);
                    case WEST  -> shape = Floor1ListListListVoxelShape.get(0).get(1).get(2);
                    case NORTH -> shape = Floor1ListListListVoxelShape.get(0).get(1).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_1_0 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor1ListListListVoxelShape.get(1).get(0).get(0);
                    case SOUTH -> shape = Floor1ListListListVoxelShape.get(1).get(0).get(1);
                    case WEST  -> shape = Floor1ListListListVoxelShape.get(1).get(0).get(2);
                    case NORTH -> shape = Floor1ListListListVoxelShape.get(1).get(0).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_1_1 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor1ListListListVoxelShape.get(0).get(0).get(0);
                    case SOUTH -> shape = Floor1ListListListVoxelShape.get(0).get(0).get(1);
                    case WEST  -> shape = Floor1ListListListVoxelShape.get(0).get(0).get(2);
                    case NORTH -> shape = Floor1ListListListVoxelShape.get(0).get(0).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_2_0 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor2ListListListVoxelShape.get(1).get(1).get(0);
                    case SOUTH -> shape = Floor2ListListListVoxelShape.get(1).get(1).get(1);
                    case WEST  -> shape = Floor2ListListListVoxelShape.get(1).get(1).get(2);
                    case NORTH -> shape = Floor2ListListListVoxelShape.get(1).get(1).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_2_1 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor2ListListListVoxelShape.get(0).get(1).get(0);
                    case SOUTH -> shape = Floor2ListListListVoxelShape.get(0).get(1).get(1);
                    case WEST  -> shape = Floor2ListListListVoxelShape.get(0).get(1).get(2);
                    case NORTH -> shape = Floor2ListListListVoxelShape.get(0).get(1).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_2_0 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor2ListListListVoxelShape.get(1).get(0).get(0);
                    case SOUTH -> shape = Floor2ListListListVoxelShape.get(1).get(0).get(1);
                    case WEST  -> shape = Floor2ListListListVoxelShape.get(1).get(0).get(2);
                    case NORTH -> shape = Floor2ListListListVoxelShape.get(1).get(0).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_2_1 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor2ListListListVoxelShape.get(0).get(0).get(0);
                    case SOUTH -> shape = Floor2ListListListVoxelShape.get(0).get(0).get(1);
                    case WEST  -> shape = Floor2ListListListVoxelShape.get(0).get(0).get(2);
                    case NORTH -> shape = Floor2ListListListVoxelShape.get(0).get(0).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_3_0 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor3ListListListVoxelShape.get(1).get(1).get(0);
                    case SOUTH -> shape = Floor3ListListListVoxelShape.get(1).get(1).get(1);
                    case WEST  -> shape = Floor3ListListListVoxelShape.get(1).get(1).get(2);
                    case NORTH -> shape = Floor3ListListListVoxelShape.get(1).get(1).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_0_3_1 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor3ListListListVoxelShape.get(0).get(1).get(0);
                    case SOUTH -> shape = Floor3ListListListVoxelShape.get(0).get(1).get(1);
                    case WEST  -> shape = Floor3ListListListVoxelShape.get(0).get(1).get(2);
                    case NORTH -> shape = Floor3ListListListVoxelShape.get(0).get(1).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_3_0 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor3ListListListVoxelShape.get(1).get(0).get(0);
                    case SOUTH -> shape = Floor3ListListListVoxelShape.get(1).get(0).get(1);
                    case WEST  -> shape = Floor3ListListListVoxelShape.get(1).get(0).get(2);
                    case NORTH -> shape = Floor3ListListListVoxelShape.get(1).get(0).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
                }
            }
            case ADDON_1_3_1 ->
            {
                switch (facing)
                {
                    case EAST  -> shape = Floor3ListListListVoxelShape.get(0).get(0).get(0);
                    case SOUTH -> shape = Floor3ListListListVoxelShape.get(0).get(0).get(1);
                    case WEST  -> shape = Floor3ListListListVoxelShape.get(0).get(0).get(2);
                    case NORTH -> shape = Floor3ListListListVoxelShape.get(0).get(0).get(3);
                    default    -> shape = box(0, 0, 0, 16, 16, 16);
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
    public void onRemove(BlockState blockState, Level level, BlockPos blockPosition, BlockState newState, boolean movedByPiston)
    {
        if (!blockState.is(newState.getBlock()))
        {
            Direction facing = blockState.getValue(FACING);
            _2x4x2Part part = blockState.getValue(PART);

            if (part == _2x4x2Part.MAIN)
            {
                for (_2x4x2Part eachPart : _2x4x2Part.values())
                {
                    if (eachPart == _2x4x2Part.MAIN)
                    {
                        continue;
                    }

                    BlockPos addonPos = CalculatePartPosition.getPartPos(blockPosition, facing, eachPart);
                    BlockState addonState = level.getBlockState(addonPos);
                    if (addonState.getBlock() instanceof WeaponWorkshopBlastFurnaceBlock)
                    {
                        level.setBlock(addonPos, Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
                else
                {
                    BlockPos mainPos = CalculatePartPosition.getMainPosFromAddon(blockPosition, facing, part);
                    BlockState mainState = level.getBlockState(mainPos);
                    if (mainState.getBlock() instanceof WeaponWorkshopBlastFurnaceBlock)
                    {
                        level.destroyBlock(mainPos, true);
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
            for (_2x4x2Part part : _2x4x2Part.values())
            {
                if (part == _2x4x2Part.MAIN)
                {
                    continue;
                }
                BlockPos partPos = CalculatePartPosition.getPartPos(BlockPosition, facing, part);
                level.setBlock(partPos, this.defaultBlockState().setValue(PART, part).setValue(FACING, facing), 3);
                level.updateNeighborsAt(partPos, this);
            }
        }
    }
}
