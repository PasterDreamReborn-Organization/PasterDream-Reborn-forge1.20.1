package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class EdelweissBlock extends FlowerBlock {

    private static final RitualPattern[] PATTERN = {
            new RitualPattern(+5, 0, 0, ModBlocks.SINGULARITY_FERN),
            new RitualPattern(-5, 0, 0, ModBlocks.LINHT_FLOWER),
            new RitualPattern(0, 0, +5, ModBlocks.DYEDREAM_COROLLA_CROP),
            new RitualPattern(0, 0, -5, ModBlocks.DYEDREAM_SAPLING),
    };

    public EdelweissBlock(Properties properties) {
        super(() -> net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 0, properties);
    }

    @Override
    public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity,
                                  InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide()) {
            tryConvert(world, pos);
        }
        return InteractionResult.SUCCESS;
    }

    private static void tryConvert(Level world, BlockPos pos) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        // Desk 3 blocks below
        BlockPos deskPos = pos.offset(0, -3, 0);
        if (!world.getBlockState(deskPos).is(ModBlocks.DYEDREAM_DESK.get())) return;

        // 4 corner blocks at 5 distance
        if (!matchesPattern(world, pos)) return;

        // SnowGolem + Allay in 9x9x9
        AABB ritualArea = AABB.ofSize(pos.getCenter(), 9, 9, 9);
        if (world.getEntitiesOfClass(SnowGolem.class, ritualArea, e -> true).isEmpty()) return;
        if (world.getEntitiesOfClass(Allay.class, ritualArea, e -> true).isEmpty()) return;

        // Destroy corner blocks
        for (RitualPattern entry : PATTERN) {
            world.destroyBlock(pos.offset(entry.offset()), false);
        }

        // Drop nippy_edelweiss item and remove the center block
        Block.popResource(world, pos, new ItemStack(ModItems.NIPPY_EDELWEISS.get()));
        world.destroyBlock(pos, false);

        // Destroy desk (configurable)
        if (Config.destroyDeskOnEdelweissConversion) {
            world.destroyBlock(deskPos, false);
        }

        // Particles
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, x + 0.5, y + 0.5, z + 0.5, 16, 0.5, 0.5, 0.5, 0.05);
            serverLevel.sendParticles(ParticleTypes.ITEM_SNOWBALL, x + 0.5, y + 0.5, z + 0.5, 16, 0.5, 0.5, 0.5, 0.05);
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, x + 0.5, y + 1, z + 0.5, 64, 2, 1, 2, 0.1);
        }

        // Sound
        world.playSound(null, pos, ModSounds.AWAKE.get(), SoundSource.NEUTRAL, 1, 1);
    }

    private static boolean matchesPattern(Level world, BlockPos pos) {
        for (RitualPattern entry : PATTERN) {
            if (!world.getBlockState(pos.offset(entry.offset())).is(entry.block.get())) {
                return false;
            }
        }
        return true;
    }

    private record RitualPattern(int dx, int dy, int dz, Supplier<net.minecraft.world.level.block.Block> block) {
        Vec3i offset() {
            return new Vec3i(dx, dy, dz);
        }
    }
}
