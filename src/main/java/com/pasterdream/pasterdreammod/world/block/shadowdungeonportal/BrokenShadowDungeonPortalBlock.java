package com.pasterdream.pasterdreammod.world.block.shadowdungeonportal;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BrokenShadowDungeonPortalBlock extends BaseEntityBlock {
    public static final IntegerProperty ANIMATION = IntegerProperty.create("animation", 0, 2);

    private static final ResourceLocation SHADOW_DUNGEON_ADV = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_dungeon");

    public BrokenShadowDungeonPortalBlock() {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.STONE)
                .strength(-1, 3600000)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BrokenShadowDungeonPortalTileEntity(pos, state);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlock(pos, ModBlocks.SHADOW_DUNGEON_PORTAL.get().defaultBlockState(), 3);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return box(3, 3, 3, 13, 13, 13);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ANIMATION);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        if (pos.getY() <= 20) {
            player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.too_low"), false);
            return InteractionResult.SUCCESS;
        }

        if (player.getAbilities().instabuild) {
            world.setBlock(pos, state.setValue(ANIMATION, 1), 3);
            world.playSound(null, pos, SoundEvents.SMITHING_TABLE_USE, SoundSource.NEUTRAL, 1, 1);
            if (world instanceof ServerLevel sl)
                sl.sendParticles(ParticleTypes.END_ROD, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 24, 1, 1, 1, 0.3);
            player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.creative_repaired"), false);
            player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.repaired"), true);
            world.scheduleTick(pos, this, 20);
            return InteractionResult.SUCCESS;
        }

        // 检查是否已阅读暗影地牢笔记
        if (!hasShadowDungeonKnowledge(player)) {
            player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.lack_knowledge"), true);
            return InteractionResult.SUCCESS;
        }

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        boolean hasIngredients = (mainHand.is(ModBlocks.SHADOW_LIGHT.get().asItem()) && offHand.is(ModItems.BLACK_METAL_INGOT.get()))
                || (mainHand.is(ModItems.BLACK_METAL_INGOT.get()) && offHand.is(ModBlocks.SHADOW_LIGHT.get().asItem()));

        if (hasIngredients) {
            world.setBlock(pos, state.setValue(ANIMATION, 1), 3);
            world.playSound(null, pos, SoundEvents.SMITHING_TABLE_USE, SoundSource.NEUTRAL, 1, 1);
            if (world instanceof ServerLevel sl)
                sl.sendParticles(ParticleTypes.END_ROD, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 24, 1, 1, 1, 0.3);
            clearItem(player, ModItems.BLACK_METAL_INGOT.get());
            clearItem(player, ModBlocks.SHADOW_LIGHT.get().asItem());
            player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.repaired"), true);
            world.scheduleTick(pos, this, 20);
        } else {
            player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.need_materials"), true);
        }

        return InteractionResult.SUCCESS;
    }

    private boolean hasShadowDungeonKnowledge(Player player) {
        if (player instanceof ServerPlayer sp) {
            var adv = sp.server.getAdvancements().getAdvancement(SHADOW_DUNGEON_ADV);
            return adv != null && sp.getAdvancements().getOrStartProgress(adv).isDone();
        }
        return false;
    }

    private void clearItem(Player player, net.minecraft.world.item.Item item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(item)) {
                stack.shrink(1);
                if (stack.isEmpty())
                    player.getInventory().setItem(i, ItemStack.EMPTY);
                return;
            }
        }
    }
}
