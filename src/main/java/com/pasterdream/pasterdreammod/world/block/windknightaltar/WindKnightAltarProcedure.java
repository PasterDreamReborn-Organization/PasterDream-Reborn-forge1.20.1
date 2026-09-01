package com.pasterdream.pasterdreammod.world.block.windknightaltar;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import com.pasterdream.pasterdreammod.world.item.PotionBottleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WindKnightAltarProcedure {
    private static final int SUMMON_RADIUS = 5;

    public static void use(Level world, BlockPos pos, Player player) {
        BlockState state = world.getBlockState(pos);
        int stage = state.getValue(WindKnightAltarBlock.STAGE);
        ItemStack mainHand = player.getMainHandItem();

        if (stage == 0) {
            if (mainHand.is(ModItems.WIND_RUNNER_CRYSTAL.get())) {
                world.setBlock(pos, state.setValue(WindKnightAltarBlock.STAGE, 1), 3);
                world.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS, 1f, 1f);
                if (world instanceof ServerLevel sl)
                    sl.sendParticles(ParticleTypes.SCRAPE, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 8, 0.5, 0.5, 0.5, 0.1);
                if (!player.getAbilities().instabuild)
                    mainHand.shrink(1);
            } else {
                player.displayClientMessage(Component.translatable("block.pasterdream.break_wind_knight_altar.need_crystal"), true);
            }
        } else if (stage == 1) {
            if (mainHand.is(ModItems.CONGEAL_WIND_IRON_INGOT.get())) {
                if (!player.getAbilities().instabuild)
                    mainHand.shrink(1);
                world.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1f, 1f);
                if (world instanceof ServerLevel sl)
                    sl.sendParticles(ParticleTypes.SCRAPE, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 12, 0.7, 0.7, 0.7, 0.1);
                world.setBlock(pos, state.setValue(WindKnightAltarBlock.STAGE, 2), 3);
            } else {
                player.displayClientMessage(Component.translatable("block.pasterdream.break_wind_knight_altar.need_torso"), true);
            }
        } else if (stage == 2) {
            if (mainHand.is(ModItems.CONGEAL_WIND_IRON_INGOT.get())) {
                world.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1f, 1f);
                if (world instanceof ServerLevel sl)
                    sl.sendParticles(ParticleTypes.SCRAPE, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 20, 0.9, 1, 0.9, 0.1);
                if (!player.getAbilities().instabuild)
                    mainHand.shrink(1);
                world.setBlock(pos, state.setValue(WindKnightAltarBlock.STAGE, 3), 3);
            } else {
                player.displayClientMessage(Component.translatable("block.pasterdream.break_wind_knight_altar.need_arms"), true);
            }
        } else if (stage == 3) {
            if (mainHand.is(ModItems.CONGEAL_WIND_IRON_INGOT.get())) {
                world.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1f, 1f);
                if (world instanceof ServerLevel sl)
                    sl.sendParticles(ParticleTypes.SCRAPE, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 24, 0.9, 1, 0.9, 0.1);
                if (!player.getAbilities().instabuild)
                    mainHand.shrink(1);
                world.setBlock(pos, state.setValue(WindKnightAltarBlock.STAGE, 4), 3);
            } else {
                player.displayClientMessage(Component.translatable("block.pasterdream.break_wind_knight_altar.need_head"), true);
            }
        } else if (stage == 4) {
            if (mainHand.is(PotionBottleRegistry.POTION_BOTTLE.get())
                    && PotionBottleItem.TYPE_LIGHTNING.equals(PotionBottleItem.getPotionType(mainHand))) {
                if (!player.getAbilities().instabuild)
                    mainHand.shrink(1);
                if (world instanceof ServerLevel sl) {
                    ItemStack stack = PotionBottleItem.createWithType(
                            PotionBottleRegistry.POTION_BOTTLE.get(), PotionBottleItem.TYPE_LIGHTNING);
                    Vec3 center = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                    var effect = PotionBottleItem.getEffect(PotionBottleItem.TYPE_LIGHTNING);
                    if (effect != null)
                        effect.onBottleBreak(stack, sl, player, center);
                }
            } else {
                player.displayClientMessage(Component.translatable("block.pasterdream.break_wind_knight_altar.throw_lightning"), true);
            }
        }
    }

    public static void trySummon(ServerLevel world, Vec3 hitPos) {
        BlockPos center = BlockPos.containing(hitPos);
        for (BlockPos p : BlockPos.betweenClosed(center.offset(-SUMMON_RADIUS, -SUMMON_RADIUS, -SUMMON_RADIUS),
                center.offset(SUMMON_RADIUS, SUMMON_RADIUS, SUMMON_RADIUS))) {
            BlockState state = world.getBlockState(p);
            if (state.is(ModBlocks.BREAK_WIND_KNIGHT_ALTAR.get()) && state.getValue(WindKnightAltarBlock.STAGE) == 4) {
                summonAt(world, p);
                return;
            }
        }
    }

    private static void summonAt(ServerLevel world, BlockPos p) {
        BlockState state = world.getBlockState(p);
        world.setBlock(p, state.setValue(WindKnightAltarBlock.STAGE, 0), 3);
        var boss = ModEntities.WIND_KNIGHT.get().spawn(world,
                BlockPos.containing(p.getX() + 0.5, p.getY() + 1, p.getZ() + 0.5), MobSpawnType.MOB_SUMMONED);
        if (boss != null)
            boss.setYRot(world.random.nextFloat() * 360F);
        spawnThundercloud(world, p.getX() + 6.5, p.getY() + 8, p.getZ() + 6.5);
        spawnThundercloud(world, p.getX() - 6.5, p.getY() + 8, p.getZ() + 6.5);
        spawnThundercloud(world, p.getX() + 6.5, p.getY() + 8, p.getZ() - 6.5);
        spawnThundercloud(world, p.getX() - 6.5, p.getY() + 8, p.getZ() - 6.5);
        world.playSound(null, p, ModSounds.SHADOW_DOOR.get(), SoundSource.MASTER, 1f, 1f);
    }

    private static void spawnThundercloud(ServerLevel level, double x, double y, double z) {
        var cloud = ModEntities.THUNDERCLOUD.get().spawn(level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
        if (cloud != null)
            cloud.setYRot(level.random.nextFloat() * 360F);
    }
}
