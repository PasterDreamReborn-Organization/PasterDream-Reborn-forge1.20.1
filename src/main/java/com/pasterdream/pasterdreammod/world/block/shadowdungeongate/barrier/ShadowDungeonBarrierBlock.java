package com.pasterdream.pasterdreammod.world.block.shadowdungeongate.barrier;

import com.pasterdream.pasterdreammod.helper.multiblockproperties.MultiBlockProperties;
import com.pasterdream.pasterdreammod.helper.multiblockproperties._3x3Part;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition._3x3_VerticalCalculatePartPosition;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.voxelshapecalculator.VoxelShapeCalculator;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.block.HorizontalDirectionalGenericBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShadowDungeonBarrierBlock extends HorizontalDirectionalGenericBlock
{
    public static final EnumProperty<_3x3Part> PART = MultiBlockProperties._3x3PART;

    public ShadowDungeonBarrierBlock()
    {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.STONE).strength(-1, 3600000).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART, _3x3Part.MAIN));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        List<VoxelShape> ListVoxelShape = VoxelShapeCalculator.calculateAllDirectionVoxelShapeFromEastVoxelShape(4 / 16.0, 0, 0, 12 / 16.0, 16 / 16.0, 16 / 16.0);
        Direction facing = state.getValue(FACING);
        return switch (facing)
        {
            case EAST  -> ListVoxelShape.get(0);
            case SOUTH -> ListVoxelShape.get(1);
            case WEST  -> ListVoxelShape.get(2);
            case NORTH -> ListVoxelShape.get(3);
            default -> box(0, 0, 0, 16, 16, 16);
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, PART);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        world.playSound(null, pos, ModSounds.SHADOW_DOOR.get(), SoundSource.BLOCKS, 1, 1);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPosition, BlockState newState, boolean movedByPiston)
    {
        if (!blockState.is(newState.getBlock()))
        {
            Direction facing = blockState.getValue(FACING);
            _3x3Part part = blockState.getValue(PART);

            if(part == _3x3Part.MAIN)
            {
                for (_3x3Part eachPart : _3x3Part.values())
                {
                    if (eachPart == _3x3Part.MAIN)
                    {
                        continue;
                    }

                    BlockPos addonPos = _3x3_VerticalCalculatePartPosition.getPartPos(blockPosition, facing, eachPart);
                    BlockState addonState = level.getBlockState(addonPos);
                    if (addonState.getBlock() instanceof ShadowDungeonBarrierBlock)
                    {
                        level.setBlock(addonPos, Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
                else
                {
                    BlockPos mainPos = _3x3_VerticalCalculatePartPosition.getMainPosFromAddon(blockPosition, facing, part);
                    BlockState mainState = level.getBlockState(mainPos);
                    if (mainState.getBlock() instanceof ShadowDungeonBarrierBlock)
                    {
                        level.removeBlock(mainPos, true);
                    }
                }
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos BlockPosition, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        Direction facing = state.getValue(FACING);
        BlockPosition = BlockPosition.relative(Direction.UP, 1);

        if (!level.isClientSide)
        {
            for (_3x3Part part : _3x3Part.values())
            {
                if (part == _3x3Part.MAIN)
                {
                    level.setBlock(BlockPosition, this.defaultBlockState().setValue(PART, _3x3Part.MAIN).setValue(FACING, facing), 3);
                    level.updateNeighborsAt(BlockPosition, this);
                }
                    else
                    {
                        BlockPos partPos = _3x3_VerticalCalculatePartPosition.getPartPos(BlockPosition, facing, part);
                        level.setBlock(partPos, this.defaultBlockState().setValue(PART, part).setValue(FACING, facing), 3);
                        level.updateNeighborsAt(partPos, this);
                    }
            }
        }
    }
}
