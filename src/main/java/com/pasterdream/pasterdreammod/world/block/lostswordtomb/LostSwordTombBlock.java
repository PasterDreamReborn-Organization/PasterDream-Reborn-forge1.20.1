package com.pasterdream.pasterdreammod.world.block.lostswordtomb;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LostSwordTombBlock extends HorizontalDirectionalBlock implements EntityBlock {

    private static final VoxelShape SHAPE = Shapes.or(
            box(0, 0, 0, 16, 2, 16),
            box(4, 2, 4, 12, 9, 12));

    public LostSwordTombBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(SoundType.STONE)
                .strength(1.5f, 6.0f)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
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
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return 0;
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        world.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(),
                x, y, z, 8, 0.5, 0.6, 0.5, 0.02);
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        ServerLevel world = (ServerLevel) level;

        boolean hasStrength = player.hasEffect(MobEffects.DAMAGE_BOOST)
                && player.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier() >= 1;

        if (!hasStrength) {
            player.displayClientMessage(Component.translatable("message.pasterdream.lost_sword_tomb.lack_strength"), true);
            return InteractionResult.SUCCESS;
        }

        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof LostSwordTombBlockEntity tombBe)) {
            return InteractionResult.SUCCESS;
        }

        if (tombBe.getPullCount() >= 5) {
            world.destroyBlock(pos, false);
            ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    new ItemStack(ModItems.SWORD_EMBRYO.get()));
            itemEntity.setPickUpDelay(5);
            world.addFreshEntity(itemEntity);
            ItemEntity mossyEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    new ItemStack(Items.MOSSY_COBBLESTONE));
            mossyEntity.setPickUpDelay(5);
            world.addFreshEntity(mossyEntity);
            world.playSound(null, pos, ModSounds.SKILL0.get(), SoundSource.NEUTRAL, 1, 1);
            world.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(),
                    pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5,
                    16, 0.3, 0.2, 0.3, 0.5);
            return InteractionResult.SUCCESS;
        }

        tombBe.incrementPullCount();
        BlockState bs = world.getBlockState(pos);
        world.sendBlockUpdated(pos, bs, bs, 3);

        world.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(),
                pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5,
                16, 0, 0.2, 0, 0.4);

        world.playSound(null, pos, net.minecraft.sounds.SoundEvents.DEEPSLATE_BREAK, SoundSource.NEUTRAL, 1, 1);

        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LostSwordTombBlockEntity(pos, state);
    }
}
