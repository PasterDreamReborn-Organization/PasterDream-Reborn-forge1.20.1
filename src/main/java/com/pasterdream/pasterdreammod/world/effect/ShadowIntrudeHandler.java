package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.entity.terrorbeak.TerrorbeakEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * 暗影入侵事件：由「暗影窥视」效果每 tick 驱动。
 * 主世界夜晚、亮度低时概率触发，在玩家附近生成虚弱恐惧喙 + 暗影之手；
 * 全部击杀后（或到白天）结束，移除暗影窥视并解锁与无名的第二次对话。
 */
public class ShadowIntrudeHandler {

    private static final String KEY_INTRUDE = "shadow_intrude";
    private static final String KEY_END = "shadow_intrude_end";
    private static final String KEY_NUMBER = "shadow_intrude_number";

    private static final ResourceLocation INTRUDE_COMPLETE_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_intrude_complete");

    // ===== 暗影入侵参数（可调） =====
    /** 触发所需最大环境亮度 */
    private static final int BRIGHTNESS_THRESHOLD = 7;
    /** 每 tick 触发入侵的概率（原 0.0005，调高使触发更稳定） */
    private static final double TRIGGER_CHANCE = 0.002;
    /** 入侵期间每 tick 刷怪的概率 */
    private static final double SPAWN_CHANCE = 0.02;
    /** 触发时施加的黑暗效果时长（tick） */
    private static final int DARKNESS_DURATION = 100;
    /** 刷怪的水平偏移半径 */
    private static final int SPAWN_OFFSET_RANGE = 9;
    /** 刷怪次数超过该阈值后可能结束入侵 */
    private static final int SPAWN_COUNT_THRESHOLD = 4;
    /** 超过刷怪次数阈值后结束入侵的概率 */
    private static final double INTRUDE_END_CHANCE = 0.5;
    /** 检查周围是否还有虚弱恐惧喙的范围 */
    private static final double WEAKENED_CHECK_RANGE = 32;

    private ShadowIntrudeHandler() {
    }

    public static void tick(LivingEntity entity) {
        Level world = entity.level();
        if (world.isClientSide() || world.dimension() != Level.OVERWORLD) {
            return;
        }

        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        BlockPos pos = BlockPos.containing(x, y, z);
        CompoundTag data = entity.getPersistentData();
        boolean intruding = data.getBoolean(KEY_INTRUDE);
        boolean intrudeEnd = data.getBoolean(KEY_END);

        if (!world.isDay()) {
            if (!intruding) {
                if (world.getMaxLocalRawBrightness(pos) <= BRIGHTNESS_THRESHOLD && Math.random() < TRIGGER_CHANCE) {
                    data.putBoolean(KEY_INTRUDE, true);
                    data.putBoolean(KEY_END, false);
                    sendMessage(entity, "message.pasterdream.shadow_intrude.start_1");
                    sendMessage(entity, "message.pasterdream.shadow_intrude.start_2");
                    entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, DARKNESS_DURATION, 0, false, false));
                    playShadowDoorSound(world, pos);
                }
            } else {
                if (Math.random() < SPAWN_CHANCE && !intrudeEnd) {
                    spawnShadowMobs(world, entity, x, y, z);
                    data.putInt(KEY_NUMBER, data.getInt(KEY_NUMBER) + 1);
                    if (data.getInt(KEY_NUMBER) > SPAWN_COUNT_THRESHOLD && Math.random() >= INTRUDE_END_CHANCE) {
                        data.putBoolean(KEY_END, true);
                    }
                    entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, DARKNESS_DURATION, 0));
                }
            }

            if (intrudeEnd && intruding) {
                AABB area = AABB.ofSize(new Vec3(x, y, z), WEAKENED_CHECK_RANGE, WEAKENED_CHECK_RANGE, WEAKENED_CHECK_RANGE);
                boolean allWeakenedGone = world.getEntitiesOfClass(TerrorbeakEntity.class, area,
                        e -> e.getType() == ModEntities.WEAKENESS_TERRORBEAK.get()).isEmpty();
                if (allWeakenedGone) {
                    finish(world, entity, data);
                }
            }
        } else if (intruding) {
            finish(world, entity, data);
        }
    }

    private static void spawnShadowMobs(Level world, LivingEntity entity, double x, double y, double z) {
        int offsetX = Mth.nextInt(world.getRandom(), -SPAWN_OFFSET_RANGE, SPAWN_OFFSET_RANGE);
        int offsetZ = Mth.nextInt(world.getRandom(), -SPAWN_OFFSET_RANGE, SPAWN_OFFSET_RANGE);
        BlockPos spawnPos = BlockPos.containing(x + offsetX, y, z + offsetZ);
        if (!world.isEmptyBlock(spawnPos.above(2))
                || !world.isEmptyBlock(spawnPos.above(1))
                || world.isEmptyBlock(spawnPos.below(1))) {
            return;
        }
        if (world instanceof ServerLevel serverLevel) {
            Entity terrorbeak = ModEntities.WEAKENESS_TERRORBEAK.get().spawn(serverLevel, spawnPos, MobSpawnType.MOB_SUMMONED);
            if (terrorbeak != null) {
                terrorbeak.setYRot(world.getRandom().nextFloat() * 360F);
            }
            Entity shadowHand = ModEntities.SHADOW_HAND.get().spawn(serverLevel, spawnPos, MobSpawnType.MOB_SUMMONED);
            if (shadowHand != null) {
                shadowHand.setYRot(world.getRandom().nextFloat() * 360F);
            }
        }
    }

    private static void finish(Level world, LivingEntity entity, CompoundTag data) {
        data.putBoolean(KEY_INTRUDE, false);
        data.putBoolean(KEY_END, false);
        data.putInt(KEY_NUMBER, 0);
        sendMessage(entity, "message.pasterdream.shadow_intrude.end");
        if (entity instanceof ServerPlayer player) {
            AdvancementHelper.grant(player, INTRUDE_COMPLETE_ADV, "intrude_complete");
        }
        sendMessage(entity, "message.pasterdream.shadow_intrude.end_1");
        sendMessage(entity, "message.pasterdream.shadow_intrude.end_2");
        entity.removeEffect(ModEffects.SHADOW_SPYON_BUFF.get());
    }

    private static void sendMessage(LivingEntity entity, String key) {
        if (entity instanceof Player player) {
            player.displayClientMessage(Component.translatable(key), false);
        }
    }

    private static void playShadowDoorSound(Level world, BlockPos pos) {
        world.playSound(null, pos, ModSounds.SHADOW_DOOR.get(), SoundSource.NEUTRAL, 1, 1);
    }
}
