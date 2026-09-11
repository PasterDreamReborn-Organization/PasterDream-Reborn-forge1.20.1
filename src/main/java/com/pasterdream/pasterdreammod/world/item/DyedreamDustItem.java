package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.world.block.portal.DyedreamWorldPortalShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * 染梦粉尘。除作为合成材料外，右键染梦世界跃迁石框架内部可点亮染梦世界传送门。
 * 生存模式消耗 1 个，创造模式不消耗。
 */
public class DyedreamDustItem extends Item {
    private static final int SCAN_RADIUS = 3;
    private static final double RAY_LENGTH = 8.0;

    public DyedreamDustItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clicked = context.getClickedPos();
        BlockPos faceAdjacent = clicked.relative(context.getClickedFace());
        Optional<DyedreamWorldPortalShape> shape = findShape(level, faceAdjacent)
                .or(() -> findShape(level, clicked));
        if (shape.isEmpty() && context.getPlayer() != null) {
            shape = findShape(level, context.getPlayer().blockPosition());
        }
        if (shape.isEmpty()) {
            return InteractionResult.PASS;
        }
        return light(level, clicked, shape.get(), context.getPlayer(), context.getItemInHand());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Vec3 eye = player.getEyePosition();
        Vec3 end = eye.add(player.getLookAngle().scale(RAY_LENGTH));
        BlockHitResult hit = level.clip(new ClipContext(eye, end,
                ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
        BlockPos center = hit.getType() == HitResult.Type.MISS
                ? player.blockPosition()
                : hit.getBlockPos().relative(hit.getDirection());
        Optional<DyedreamWorldPortalShape> shape = findShape(level, center);
        if (shape.isEmpty()) {
            return InteractionResultHolder.pass(stack);
        }
        if (!level.isClientSide) {
            shape.get().createPortalBlocks();
            playActivationSound(level, center);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    private InteractionResult light(Level level, BlockPos pos, DyedreamWorldPortalShape shape,
                                    Player player, ItemStack stack) {
        if (!level.isClientSide) {
            shape.createPortalBlocks();
            playActivationSound(level, pos);
            if (player == null || !player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private void playActivationSound(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 0.8F, 1.0F);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    12, 0.6, 0.6, 0.6, 0.05);
        }
    }

    private Optional<DyedreamWorldPortalShape> findShape(Level level, BlockPos center) {
        Optional<DyedreamWorldPortalShape> direct = DyedreamWorldPortalShape.findEmptyPortalShape(
                level, center, Direction.Axis.X);
        if (direct.isPresent()) {
            return direct;
        }
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int dy = -SCAN_RADIUS; dy <= SCAN_RADIUS; dy++) {
            for (int dx = -SCAN_RADIUS; dx <= SCAN_RADIUS; dx++) {
                for (int dz = -SCAN_RADIUS; dz <= SCAN_RADIUS; dz++) {
                    mutable.set(center.getX() + dx, center.getY() + dy, center.getZ() + dz);
                    Optional<DyedreamWorldPortalShape> found =
                            DyedreamWorldPortalShape.findEmptyPortalShape(level, mutable, Direction.Axis.X);
                    if (found.isPresent()) {
                        return found;
                    }
                }
            }
        }
        return Optional.empty();
    }
}
