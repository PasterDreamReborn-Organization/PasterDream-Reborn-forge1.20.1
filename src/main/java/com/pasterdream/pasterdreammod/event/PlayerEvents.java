package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.command.san.LowSanEffect;
import com.pasterdream.pasterdreammod.helper.itemwithnbt.dreamnoteswithnbt.DreamNotesWithNBT;
import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import com.pasterdream.pasterdreammod.world.skill.EvasionEffectHandler;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class PlayerEvents {

    private static final ResourceKey<Level> DYEDREAM_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_world"));
    private static final String NOTE_DELAY_TAG = "pasterdream:dream_note_delay";
    private static final ResourceLocation FIRST_CONTACT_DYEDREAM_CRACK_ADV = ResourceLocation.fromNamespaceAndPath("pasterdream", "story/first_contact_dyedream_crack");
    private static final ResourceLocation DYEDREAM_CRACK_ADV = ResourceLocation.fromNamespaceAndPath("pasterdream", "story/dyedream_crack");
    private static final ResourceLocation DYEDREAM_WORLD_ADV = ResourceLocation.fromNamespaceAndPath("pasterdream", "story/dyedream_world");
    private static final ResourceLocation ROOT_DYEDREAM_TREASURE_ADV = ResourceLocation.fromNamespaceAndPath("pasterdream", "treasure/root_dyedream_treasure");
    private static final ResourceLocation PURE_AND_FLAWLESS_ADV = ResourceLocation.fromNamespaceAndPath("pasterdream", "story/pure_and_flawless");
    private static final ResourceLocation DREAM_FERTILIZER_ADV = ResourceLocation.fromNamespaceAndPath("pasterdream", "story/dream_fertilizer");
    private static final ResourceLocation LOOK_AT_PINK_SHEEP_ADV = ResourceLocation.fromNamespaceAndPath("pasterdream", "story/look_at_pink_sheep");

    /** 进度 ID → 笔记 content 键列表 的映射 */
    private static final java.util.Map<ResourceLocation, java.util.List<String>> ADVANCEMENT_NOTE_CONTENT = java.util.Map.of(
            PURE_AND_FLAWLESS_ADV, java.util.List.of("whiteCorolla", "paleBoneNeedle"),
            DREAM_FERTILIZER_ADV, java.util.List.of("dreamFertilizer")
    );

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        EvasionEffectHandler.onPlayerTick(player);

        if (!player.level().isClientSide()) {
            // 笔记发放倒计时
            tickNoteDelay(player);

            int dreamTeleportTicks = player.getPersistentData().getInt("pasterdream:dream_teleport_ticks");
            if (dreamTeleportTicks > 0) {
                dreamTeleportTicks--;
                if (dreamTeleportTicks <= 0) {
                    if (player instanceof ServerPlayer serverPlayer
                            && !player.level().dimension().equals(DYEDREAM_WORLD)) {
                        // 重置床的 OCCUPIED 状态
                        CompoundTag data = player.getPersistentData();
                        if (data.contains("pasterdream:dream_bed_x")) {
                            BlockPos bedPos = new BlockPos(
                                    data.getInt("pasterdream:dream_bed_x"),
                                    data.getInt("pasterdream:dream_bed_y"),
                                    data.getInt("pasterdream:dream_bed_z"));
                            var bedState = player.level().getBlockState(bedPos);
                            if (bedState.hasProperty(BedBlock.OCCUPIED)) {
                                player.level().setBlock(bedPos,
                                        bedState.setValue(BedBlock.OCCUPIED, false), 3);
                            }
                        }
                        ServerLevel dyedream = serverPlayer.server.getLevel(DYEDREAM_WORLD);
                        if (dyedream != null) {
                            serverPlayer.teleportTo(dyedream, 0.5, 108, 0.5,
                                    serverPlayer.getYRot(), serverPlayer.getXRot());
                        }
                    }
                    player.getPersistentData().remove("pasterdream:dream_teleport_ticks");
                    player.getPersistentData().remove("pasterdream:dream_bed_x");
                    player.getPersistentData().remove("pasterdream:dream_bed_y");
                    player.getPersistentData().remove("pasterdream:dream_bed_z");
                } else {
                    player.getPersistentData().putInt("pasterdream:dream_teleport_ticks", dreamTeleportTicks);
                }
            }

            // 检查玩家是否在染梦维度注视粉色羊（已获得成就则跳过）
            if (player instanceof ServerPlayer serverPlayer
                    && player.level().dimension().equals(DYEDREAM_WORLD)
                    && player.tickCount % 20 == 0
                    && !isAdvancementDone(serverPlayer, LOOK_AT_PINK_SHEEP_ADV)) {
                Vec3 eyePos = serverPlayer.getEyePosition(1.0F);
                Vec3 lookVec = serverPlayer.getViewVector(1.0F);
                AABB nearby = serverPlayer.getBoundingBox().inflate(16.0);
                var nearbySheep = serverPlayer.level().getEntitiesOfClass(
                        Sheep.class, nearby,
                        s -> s.getColor() == DyeColor.PINK);
                for (Sheep sheep : nearbySheep) {
                    Vec3 toSheep = sheep.getEyePosition(1.0F).subtract(eyePos).normalize();
                    if (lookVec.dot(toSheep) > 0.95) {
                        ModCriteriaTriggers.LOOK_AT_PINK_SHEEP.trigger(serverPlayer);
                        break;
                    }
                }
            }
        }
    }

    public static void onLivingHurt(LivingHurtEvent event) {
        // 白厄剑对暗影生物伤害+50%（剑雨已在弹射物中标记，避免重复加成）
        if (event.getSource().getEntity() instanceof Player player
                && player.getMainHandItem().is(ModItems.WHITE_SWORD.get())
                && event.getEntity().getType().is(ModEntityTypeTags.SHADOW_MOB)) {
            if (!event.getEntity().getPersistentData().getBoolean("pasterdream:white_sword_boosted")) {
                event.setAmount(event.getAmount() * 1.5f);
            }
            event.getEntity().getPersistentData().remove("pasterdream:white_sword_boosted");
        }

        if (!(event.getEntity() instanceof Player player)) return;
        if (!player.hasEffect(ModEffects.EVASION_BUFF.get())) return;

        var effect = player.getEffect(ModEffects.EVASION_BUFF.get());
        if (effect == null) return;

        int amplifier = effect.getAmplifier();
        int duration = effect.getDuration();
        player.removeEffect(ModEffects.EVASION_BUFF.get());

        // multi-level evasion: consume one level, keep remainder
        if (amplifier > 0) {
            player.addEffect(new MobEffectInstance(ModEffects.EVASION_BUFF.get(),
                    duration, amplifier - 1, false, false));
        }

        event.setAmount(0);
        event.setCanceled(true);

        // 反击戒指：成功闪避时获得反击 buff
        if (CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.COUNTER_RING.get()).isPresent())
                .orElse(false)) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0, false, false));
            player.addEffect(new MobEffectInstance(ModEffects.COUNTER_ATTACK_BUFF.get(), 200, 0, false, false));
        }

        if (player.level() instanceof ServerLevel serverLevel) {
            EvasionEffectHandler.execute(serverLevel, player);
        }
    }

    /** 反击 buff 命中后移除 + 白厄剑近战沉默暗影生物 */
    public static void onAttackEntity(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        // 白厄剑近战：攻击暗影生物前施加沉默+束缚，确保第一刀压制受击技能
        if (player.getMainHandItem().is(ModItems.WHITE_SWORD.get())
                && event.getTarget() instanceof LivingEntity target
                && target.getType().is(ModEntityTypeTags.SHADOW_MOB)) {
            target.addEffect(new MobEffectInstance(ModEffects.SHADOW_SILENCE_BUFF.get(), 200, 0));
            target.addEffect(new MobEffectInstance(ModEffects.BIND_BUFF.get(), 40, 0));
        }

        if (!player.hasEffect(ModEffects.COUNTER_ATTACK_BUFF.get())) return;
        player.removeEffect(ModEffects.COUNTER_ATTACK_BUFF.get());
        player.removeEffect(MobEffects.DAMAGE_BOOST);
    }

    public static void onPlayerSleepInBed(PlayerSleepInBedEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        // 躺下给予3分钟休憩效果
        player.addEffect(new MobEffectInstance(ModEffects.REST_BUFF.get(),
                3600, 0, false, false));

        // 玩家接触过染梦裂隙但尚未获得染梦裂隙笔记时，睡觉触发笔记发放倒计时
        if (player instanceof ServerPlayer serverPlayer)
        {
            Advancement firstContactAdv = serverPlayer.server.getAdvancements().getAdvancement(FIRST_CONTACT_DYEDREAM_CRACK_ADV);
            if (firstContactAdv != null && serverPlayer.getAdvancements().getOrStartProgress(firstContactAdv).isDone())
            {
                Advancement crackAdv = serverPlayer.server.getAdvancements().getAdvancement(DYEDREAM_CRACK_ADV);
                if (crackAdv == null || !serverPlayer.getAdvancements().getOrStartProgress(crackAdv).isDone())
                {
                    player.getPersistentData().putInt(NOTE_DELAY_TAG, 40);
                }
            }
        }

        if (!player.hasEffect(ModEffects.DREAM_WISH_BUFF.get())) return;

        BlockPos pos = event.getPos();
        CompoundTag data = player.getPersistentData();
        data.putInt("pasterdream:dream_teleport_ticks", 60);
        data.putInt("pasterdream:dream_bed_x", pos.getX());
        data.putInt("pasterdream:dream_bed_y", pos.getY());
        data.putInt("pasterdream:dream_bed_z", pos.getZ());
    }

    private static void tickNoteDelay(Player player)
    {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(NOTE_DELAY_TAG))
        {
            return;
        }

        int delay = data.getInt(NOTE_DELAY_TAG) - 1;
        if (delay > 0)
        {
            data.putInt(NOTE_DELAY_TAG, delay);
            return;
        }

        data.remove(NOTE_DELAY_TAG);

        if (!(player instanceof ServerPlayer serverPlayer) || !serverPlayer.isAlive())
        {
            return;
        }

        ItemStack note = DreamNotesWithNBT.dreamNotesWithNBT(
                ModItems.DREAM_NOTES_DYEDREAM_WORLD.get(), "content", "dyedreamCreak");
        if (!serverPlayer.getInventory().add(note))
        {
            serverPlayer.drop(note, false);
        }

        serverPlayer.displayClientMessage(
                Component.translatable("message.pasterdream.sleep.dream_of_crack.1"), false);
        serverPlayer.displayClientMessage(
                Component.translatable("message.pasterdream.sleep.dream_of_crack.2"), false);
        serverPlayer.displayClientMessage(
                Component.translatable("message.pasterdream.sleep.dream_of_crack.3"), false);
    }

    /** 玩家首次进入染梦世界时，授予进度并给予笔记。 */
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer))
        {
            return;
        }

        if (!event.getTo().equals(DYEDREAM_WORLD))
        {
            return;
        }

        // 进入染梦维度 → 授予"染梦珍藏"进度（仅弹窗，不显示在聊天栏）
        Advancement treasureAdv = serverPlayer.server.getAdvancements().getAdvancement(ROOT_DYEDREAM_TREASURE_ADV);
        if (treasureAdv != null && !serverPlayer.getAdvancements().getOrStartProgress(treasureAdv).isDone())
        {
            AdvancementProgress treasureProgress = serverPlayer.getAdvancements().getOrStartProgress(treasureAdv);
            for (String criteria : treasureProgress.getRemainingCriteria())
            {
                serverPlayer.getAdvancements().award(treasureAdv, criteria);
            }
        }

        Advancement worldAdv = serverPlayer.server.getAdvancements().getAdvancement(DYEDREAM_WORLD_ADV);
        boolean alreadyGranted = worldAdv != null && serverPlayer.getAdvancements().getOrStartProgress(worldAdv).isDone();

        if (alreadyGranted)
        {
            return;
        }

        // 首次进入染梦维度 → 授予"哥德堡安眠曲"进度
        if (worldAdv != null)
        {
            AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(worldAdv);
            for (String criteria : progress.getRemainingCriteria())
            {
                serverPlayer.getAdvancements().award(worldAdv, criteria);
            }
        }

        // 发放笔记
        ItemStack note = DreamNotesWithNBT.dreamNotesWithNBT(
                ModItems.DREAM_NOTES_DYEDREAM_WORLD.get(), "content", "dyedreamWorld");
        if (!serverPlayer.getInventory().add(note))
        {
            serverPlayer.drop(note, false);
        }

        serverPlayer.displayClientMessage(
                Component.translatable("message.pasterdream.dyedream_world.found_note"), false);
    }

    /** 玩家获得指定进度时，发放对应笔记。 */
    public static void onAdvancementEarned(AdvancementEvent.AdvancementEarnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        Advancement advancement = event.getAdvancement();
        if (advancement == null) {
            return;
        }

        java.util.List<String> contents = ADVANCEMENT_NOTE_CONTENT.get(advancement.getId());
        if (contents == null) {
            return;
        }

        for (String content : contents) {
            ItemStack note = DreamNotesWithNBT.dreamNotesWithNBT(
                    ModItems.DREAM_NOTES_DYEDREAM_WORLD.get(), "content", content);
            if (!serverPlayer.getInventory().add(note)) {
                serverPlayer.drop(note, false);
            }
        }

        serverPlayer.displayClientMessage(
                Component.translatable("message.pasterdream." + advancement.getId().getPath().replace('/', '.') + ".found_note"), false);
    }

    private static boolean isAdvancementDone(ServerPlayer player, ResourceLocation id) {
        var adv = player.server.getAdvancements().getAdvancement(id);
        return adv != null && player.getAdvancements().getOrStartProgress(adv).isDone();
    }

    /** 玩家登录时从世界数据恢复 lowSan 配置并同步到客户端 */
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        LowSanEffect.restoreFromWorld(player.serverLevel());
        com.pasterdream.pasterdreammod.network.san.LowSanConfigSyncPacket.syncToPlayer(player,
                com.pasterdream.pasterdreammod.Config.lowSanOverlay,
                com.pasterdream.pasterdreammod.Config.lowSanJitter,
                com.pasterdream.pasterdreammod.Config.lowSanSound);
    }
}
