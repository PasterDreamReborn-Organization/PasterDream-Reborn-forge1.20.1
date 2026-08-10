package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.helper.itemwithnbt.dreamnoteswithnbt.DreamNotesWithNBT;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StoryProgressItem extends Item {

    /** 入场检测：玩家是否进入过灯影之下 */
    private static final ResourceLocation ENTER_LAMP_SHADOW_WORLD =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/enter_lamp_shadow_world");

    /** 剧情线终点 */
    private static final ResourceLocation SCARE_ADV =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/scare");

    /**
     * 前置进度 → 下一本笔记 content 键，按剧情顺序排列。
     * infestedChurch（侵染教堂-黑面）需玩家自行探索获取，不在此列。
     */
    private static final ResourceLocation[] PREREQUISITE_ADVANCEMENTS = {
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/lamp_shadow_root"),
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/deposition_shadow"),
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/shadow_travelogue"),
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/shadow_dungeon")
    };

    private static final String[] NEXT_NOTE_CONTENTS = {
            "depositionShadow",
            "shadowTravelogue",
            "shadowDungeon",
            "scare"
    };

    public StoryProgressItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) {
            return InteractionResultHolder.success(stack);
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResultHolder.fail(stack);
        }

        // 1. 检查是否进入过灯影之下
        if (!isAdvancementDone(serverPlayer, ENTER_LAMP_SHADOW_WORLD)) {
            serverPlayer.displayClientMessage(
                    Component.translatable("message.pasterdream.story_guide.not_entered_lamp_shadow"), true);
            return InteractionResultHolder.fail(stack);
        }

        // 2. 全部完成？
        if (isAdvancementDone(serverPlayer, SCARE_ADV)) {
            serverPlayer.displayClientMessage(
                    Component.translatable("message.pasterdream.story_guide.all_done"), true);
            return InteractionResultHolder.fail(stack);
        }

        // 3. 从后往前找最高已完成的前置进度，发放下一本笔记
        for (int i = PREREQUISITE_ADVANCEMENTS.length - 1; i >= 0; i--) {
            if (isAdvancementDone(serverPlayer, PREREQUISITE_ADVANCEMENTS[i])) {
                ItemStack note = DreamNotesWithNBT.dreamNotesWithNBT(
                        ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get(), "content", NEXT_NOTE_CONTENTS[i]);

                if (!player.getInventory().add(note)) {
                    player.drop(note, false);
                }

                level.playSound(null, player.blockPosition(),
                        SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);

                stack.shrink(1);
                return InteractionResultHolder.consume(stack);
            }
        }

        // 4. 已入场但 lamp_shadow_root 未完成 → 还需自行寻找侵染教堂-黑面
        serverPlayer.displayClientMessage(
                Component.translatable("message.pasterdream.story_guide.need_infested_church"), true);
        return InteractionResultHolder.fail(stack);
    }

    private static boolean isAdvancementDone(ServerPlayer player, ResourceLocation id) {
        Advancement adv = player.server.getAdvancements().getAdvancement(id);
        return adv != null && player.getAdvancements().getOrStartProgress(adv).isDone();
    }
}
