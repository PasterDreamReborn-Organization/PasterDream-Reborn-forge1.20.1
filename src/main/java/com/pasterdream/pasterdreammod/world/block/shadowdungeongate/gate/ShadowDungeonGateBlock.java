package com.pasterdream.pasterdreammod.world.block.shadowdungeongate.gate;

import com.pasterdream.pasterdreammod.helper.multiblockproperties.MultiBlockProperties;
import com.pasterdream.pasterdreammod.helper.multiblockproperties._3x3Part;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition._3x3_HorizontalCalculatePartPosition;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.block.shadowdungeongate.barrier.ShadowDungeonBarrierBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
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

public class ShadowDungeonGateBlock extends Block
{
    public static final EnumProperty<_3x3Part> PART = MultiBlockProperties._3x3PART;

    public ShadowDungeonGateBlock()
    {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.CHAIN).strength(-1, 3600000).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, _3x3Part.MAIN));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        return box(0, 7, 0, 16, 9, 16);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(PART);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPosition, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        level.playSound(null, blockPosition, ModSounds.SHADOW_DOOR.get(), SoundSource.BLOCKS, 1, 1);
        if (!level.isClientSide)
        {
            if (player.getMainHandItem().getItem() == ModItems.SHADOW_DUNGEON_KEY.get())
            {
                if (!player.isCreative())
                {
                    player.getMainHandItem().shrink(1);
                }

                level.destroyBlock(blockPosition, false, player);
                return InteractionResult.SUCCESS;
            }
                else
                {
                    player.displayClientMessage(Component.translatable("message.pasterdream.需要在本层寻找暗影地牢钥匙以打开大门"), true);
                    return InteractionResult.FAIL;
                }
        }
            else
            {
                return InteractionResult.SUCCESS;
            }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPosition, BlockState newState, boolean movedByPiston)
    {
        if (!blockState.is(newState.getBlock()))
        {
            _3x3Part part = blockState.getValue(PART);

            if(part == _3x3Part.MAIN)
            {
                for (_3x3Part eachPart : _3x3Part.values())
                {
                    if (eachPart == _3x3Part.MAIN)
                    {
                        continue;
                    }

                    BlockPos addonPos = _3x3_HorizontalCalculatePartPosition.getPartPos(blockPosition, Direction.EAST, eachPart);
                    BlockState addonState = level.getBlockState(addonPos);
                    if (addonState.getBlock() instanceof ShadowDungeonGateBlock)
                    {
                        level.destroyBlock(addonPos, false);
                    }
                }
            }
                else
                {
                    BlockPos mainPos = _3x3_HorizontalCalculatePartPosition.getMainPosFromAddon(blockPosition, Direction.EAST, part);
                    BlockState mainState = level.getBlockState(mainPos);
                    if (mainState.getBlock() instanceof ShadowDungeonGateBlock)
                    {
                        level.destroyBlock(mainPos, false);
                    }
                }
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos BlockPosition, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if (!level.isClientSide)
        {
            for (_3x3Part part : _3x3Part.values())
            {
                if (part != _3x3Part.MAIN)
                {
                    BlockPos partPos = _3x3_HorizontalCalculatePartPosition.getPartPos(BlockPosition, Direction.EAST, part);
                    level.setBlock(partPos, this.defaultBlockState().setValue(PART, part), 3);
                    level.updateNeighborsAt(partPos, this);
                }
            }
        }
    }
}
