package com.pasterdream.pasterdreammod.world.block.doll;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.voxelshapecalculator.VoxelShapeCalculator;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.network.san.SanSyncPacket;
import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.blockentity.HorizontalDirectionalGeckolibBaseEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public abstract class DollBlock extends HorizontalDirectionalGeckolibBaseEntityBlock
{
    public DollBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        List<VoxelShape> ListVoxelShape = VoxelShapeCalculator.calculateAllDirectionVoxelShapeFromEastVoxelShape(2 / 16.0, 0, 3 / 16.0, 11 / 16.0, 17 / 16.0, 13 / 16.0);
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
    public InteractionResult use(BlockState state, Level level, BlockPos blockPosition, Player player, InteractionHand hand, BlockHitResult result)
    {
        if (!level.isClientSide)
        {
            ServerPlayer serverPlayer = (ServerPlayer)player;

            serverPlayer.getCapability(ModCapabilities.SAN).ifPresent(capability ->
            {
                capability.addSanValue(1);
                SanSyncPacket.sendToPlayer(serverPlayer, capability);
                ((DollBlockEntity)level.getBlockEntity(blockPosition)).setAnimationState(1);
                level.playSound(null, blockPosition, ModSounds.DOLL.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            });
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return null;
    }
}
