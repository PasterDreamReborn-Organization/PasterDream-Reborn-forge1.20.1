package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DreamingLotusBlock extends DoublePlantBlock implements BonemealableBlock {

    private static final ResourceKey<Level> DYEDREAM_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world"));

    /**
     * 5x5 pattern offsets around the center dreaming_lotus, checked during conversion.
     * Pattern layout (top-down, z-2=north, z+2=south):
     *   dyedream_lily  stem_grass  cloud_crop
     *   stem_grass      [LOTUS]    stem_grass
     *   light_ball_crop stem_grass pink_mushroom
     */
    private static final PatternEntry[] PATTERN = {
            new PatternEntry(-2, -2, ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY),
            new PatternEntry(+2, -2, ModBlocks.CLOUD_CROP),
            new PatternEntry(+2, +2, ModBlocks.PINK_MUSHROOM),
            new PatternEntry(-2, +2, ModBlocks.LIGHT_BALL_CROP),
            new PatternEntry(+2,  0, ModBlocks.STEM_GRASS),
            new PatternEntry(-2,  0, ModBlocks.STEM_GRASS),
            new PatternEntry( 0, +2, ModBlocks.STEM_GRASS),
            new PatternEntry( 0, -2, ModBlocks.STEM_GRASS),
    };

    public DreamingLotusBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(world, pos);
        return box(2, 0, 2, 14, 16, 14).move(offset.x, offset.y, offset.z);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
                                  InteractionHand hand, BlockHitResult hit) {
        // 点击上半部分时，转换到下半部分的坐标
        BlockPos bottomPos = state.getValue(HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos;
        if (tryConvert(world, bottomPos)) {
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        // 转化未触发时返回 PASS，让手持物品（如骨粉）继续处理本次交互
        return InteractionResult.PASS;
    }

    private static boolean tryConvert(Level world, BlockPos pos) {
        // 维度检查：必须在染梦世界
        if (world.dimension() != DYEDREAM_WORLD) return false;

        // 底座检查：下方3格必须是染梦书桌
        BlockPos deskPos = pos.offset(0, -3, 0);
        if (!world.getBlockState(deskPos).is(ModBlocks.DYEDREAM_DESK.get())) return false;

        // 5x5 图案验证
        if (!matchesPattern(world, pos)) return false;

        // 摧毁8个图案方块
        for (PatternEntry entry : PATTERN) {
            world.destroyBlock(pos.offset(entry.offset()), false);
        }

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        // 粒子效果
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ModParticleTypes.CRACK_0_PARTICLE.get(),
                    x + 0.5, y + 1, z + 0.5, 32, 0.5, 1, 0.5, 0.05);
            serverLevel.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(),
                    x + 0.5, y + 1, z + 0.5, 128, 2, 2, 2, 0.1);
        }

        // 音效
        world.playSound(null, pos, ModSounds.AWAKE.get(), SoundSource.BLOCKS, 1, 1);

        // 替换为 misty_dreaming_lotus（上下两半）
        Block oldBlock = world.getBlockState(pos).getBlock();
        world.setBlock(pos, ModBlocks.MISTY_DREAMING_LOTUS.get().defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER), 3);
        BlockPos upperPos = pos.above();
        if (world.getBlockState(upperPos).is(oldBlock)) {
            world.setBlock(upperPos, ModBlocks.MISTY_DREAMING_LOTUS.get().defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), 3);
        }

        // 摧毁书桌（可配置）
        if (Config.destroyDeskOnDreamingLotusConversion) {
            world.destroyBlock(deskPos, false);
        }
        return true;
    }

    private static boolean matchesPattern(Level world, BlockPos pos) {
        for (PatternEntry entry : PATTERN) {
            if (!world.getBlockState(pos.offset(entry.offset())).is(entry.block.get())) {
                return false;
            }
        }
        return true;
    }

    private record PatternEntry(int dx, int dz, java.util.function.Supplier<net.minecraft.world.level.block.Block> block) {
        Vec3i offset() {
            return new Vec3i(dx, 0, dz);
        }
    }

    // ---- BonemealableBlock ----

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        popResource(level, pos, new ItemStack(this));
    }
}
