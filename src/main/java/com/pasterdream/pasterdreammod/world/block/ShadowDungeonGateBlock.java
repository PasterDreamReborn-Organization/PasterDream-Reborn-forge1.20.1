package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShadowDungeonGateBlock extends Block {
    private static final VoxelShape SHAPE = box(0, 7, 0, 16, 9, 16);

    public ShadowDungeonGateBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(SoundType.CHAIN)
                .strength(-1, 3600000)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return 0;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, world, pos, oldState, moving);
        BlockState shellState = ModBlocks.SHADOW_DUNGEON_GATE_SHELL.get().defaultBlockState();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) continue;
                BlockPos target = pos.offset(dx, 0, dz);
                if (world.getBlockState(target).canBeReplaced()) {
                    world.setBlock(target, shellState, 3);
                }
            }
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        boolean result = super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
        destroyGate(world, pos);
        return result;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        world.playSound(null, pos, ModSounds.SHADOW_DOOR.get(), SoundSource.BLOCKS, 1, 1);
        if (player.getMainHandItem().getItem() == ModItems.SHADOW_DUNGEON_KEY.get()) {
            if (!world.isClientSide) {
                player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.AIR));
                destroyGate(world, pos);
            }
            return InteractionResult.SUCCESS;
        }
        if (!world.isClientSide) {
            player.displayClientMessage(Component.literal("需要在本层寻找暗影地牢钥匙以打开大门"), true);
        }
        return InteractionResult.SUCCESS;
    }

    static void destroyGate(Level world, BlockPos center) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos target = center.offset(dx, 0, dz);
                if (dx == 0 && dz == 0) {
                    if (world.getBlockState(target).getBlock() instanceof ShadowDungeonGateBlock) {
                        world.destroyBlock(target, false);
                    }
                } else if (world.getBlockState(target).getBlock() instanceof ShadowDungeonGateShellBlock) {
                    world.destroyBlock(target, false);
                }
            }
        }
    }
}
