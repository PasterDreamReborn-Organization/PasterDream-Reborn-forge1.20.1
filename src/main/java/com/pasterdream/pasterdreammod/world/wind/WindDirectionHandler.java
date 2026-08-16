package com.pasterdream.pasterdreammod.world.wind;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModGameRules;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.dimension.WindJourneyDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

/**
 * 风之旅途维度风向机制：
 * 每昼夜随机换向（gamerule pasterdreamWindDirection，0~7），并按玩家朝向判定顺风/逆风。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class WindDirectionHandler {

    /** 玩家风向判定节流间隔（tick），对应原作 config player total tick update 默认值 5 */
    private static final int TICK_INTERVAL = 5;

    private WindDirectionHandler() {}

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel level)) return;
        if (!level.dimension().equals(WindJourneyDimension.WIND_JOURNEY_WORLD)) return;
        tickWindChange(level);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if (player.level().isClientSide()) return;

        // 进入保底：主世界高空 Y > 310 且有迷梦效果时立即传送进风之旅途
        if (player.level().dimension().equals(Level.OVERWORLD)) {
            if (player.getY() > 310 && player.hasEffect(ModEffects.MISTY_DREAM_BUFF.get()) && player instanceof ServerPlayer sp) {
                teleportToWindJourney(sp);
            }
            return;
        }

        if (!player.level().dimension().equals(WindJourneyDimension.WIND_JOURNEY_WORLD)) return;

        // 在风之旅途持续给予云霞效果（HUD 进度显示）
        player.addEffect(new MobEffectInstance(ModEffects.CLOUD_MIST_BUFF.get(), 200, 0, false, false));

        // 退出保底：坠入虚空 Y < 0 立即传送回主世界
        if (player.getY() < 0 && player instanceof ServerPlayer sp) {
            teleportBackToOverworld(sp);
            return;
        }

        if (player.tickCount % TICK_INTERVAL != 0) return;
        if (player.hasEffect(ModEffects.WINDPROOF_BUFF.get())) return;
        applyWind(player);
    }

    private static void teleportToWindJourney(ServerPlayer player) {
        if (player.level().dimension().equals(WindJourneyDimension.WIND_JOURNEY_WORLD)) return;
        ServerLevel next = player.server.getLevel(WindJourneyDimension.WIND_JOURNEY_WORLD);
        if (next != null) {
            player.teleportTo(next, player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
        }
    }

    private static void teleportBackToOverworld(ServerPlayer player) {
        ServerLevel overworld = player.server.getLevel(Level.OVERWORLD);
        if (overworld == null) return;
        player.teleportTo(overworld, player.getX(), 304, player.getZ(), player.getYRot(), player.getXRot());
    }

    private static void tickWindChange(ServerLevel level) {
        long dayTime = level.getDayTime();

        if (dayTime % 24000 == 0) {
            GameRules.IntegerValue rule = level.getGameRules().getRule(ModGameRules.WIND_DIRECTION);
            int direction = level.getRandom().nextInt(8);
            rule.set(direction, level.getServer());

            // 换向播报（延迟 2 tick）
            PasterDreamMod.queueServerWork(2, () -> {
                for (ServerPlayer p : level.players()) {
                    p.displayClientMessage(
                            Component.translatable("message.pasterdream.wind_direction.announce." + direction), false);
                }
            });

            // 换向音效 + 羽毛粒子
            for (ServerPlayer p : level.players()) {
                level.playSound(null, BlockPos.containing(p.getX(), p.getY(), p.getZ()),
                        ModSounds.WIND_CHIME.get(), SoundSource.WEATHER, 1, 1);
                level.sendParticles(ModParticleTypes.FEATHER_WHITE_PARTICLE.get(),
                        p.getX(), p.getY() + 2, p.getZ(), 48, 3, 3, 3, 0.05);
            }

            // 微风音效（延迟 79 tick）
            PasterDreamMod.queueServerWork(79, () -> {
                for (ServerPlayer p : level.players()) {
                    level.playSound(null, BlockPos.containing(p.getX(), p.getY(), p.getZ()),
                            ModSounds.BREEZE_WIND.get(), SoundSource.WEATHER, 1, 1);
                }
            });
        }

        if (dayTime == 1 || dayTime == 5) {
            for (ServerPlayer p : level.players()) {
                level.sendParticles(ModParticleTypes.FEATHER_WHITE_PARTICLE.get(),
                        p.getX(), p.getY() + 2, p.getZ(), 48, 3, 3, 3, 0.05);
            }
        }
    }

    private static void applyWind(Player player) {
        int direction = player.level().getGameRules().getInt(ModGameRules.WIND_DIRECTION);

        // 顺风：面向风的下游
        if (isTailwind(direction, player.getYRot())) {
            applyTailwind(player);
        }

        // 逆风：面向风的上游（相反方向的顺风区间）
        if (isTailwind((direction + 4) & 7, player.getYRot())) {
            if (hasWindKnightFlag(player)) {
                applyTailwind(player);
            } else {
                applyDeadwind(player);
            }
        }
    }

    private static void applyTailwind(Player player) {
        int amplifier = (int) player.getPersistentData().getDouble("player_tailwind_force");
        player.addEffect(new MobEffectInstance(ModEffects.TAILWIND_BUFF.get(), 20, amplifier));
    }

    private static void applyDeadwind(Player player) {
        int amplifier = (int) player.getPersistentData().getDouble("player_deadwind_force");
        player.addEffect(new MobEffectInstance(ModEffects.DEADWIND_BUFF.get(), 20, amplifier));
    }

    private static boolean hasWindKnightFlag(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.WIND_KNIGHT_FLAG.get()).isPresent())
                .orElse(false);
    }

    private static boolean isTailwind(int direction, float yaw) {
        switch (direction & 7) {
            case 0: return yaw <= 35 && yaw >= -35;
            case 1: return yaw <= 70 && yaw >= 10;
            case 2: return yaw <= 125 && yaw >= 55;
            case 3: return yaw <= 170 && yaw >= 100;
            case 4: return (yaw <= 180 && yaw >= 145) || (yaw <= -145 && yaw >= -180);
            case 5: return yaw <= -100 && yaw >= -170;
            case 6: return yaw <= -55 && yaw >= -125;
            case 7: return yaw <= -10 && yaw >= -80;
            default: return false;
        }
    }
}
