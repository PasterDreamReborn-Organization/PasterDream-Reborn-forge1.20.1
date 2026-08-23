package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookWithNBTToCreativeModeTab;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
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

    /** 剧情线终点（发放完暗影地牢笔记书后即全部完成） */
    private static final ResourceLocation ALL_DONE_ADV =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/shadow_dungeon");

    /** 发放笔记书时同步授予的剧情进度，按顺序排列 */
    private static final ResourceLocation[] GRANT_ADVANCEMENTS = {
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/deposition_shadow"),
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/shadow_travelogue"),
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/shadow_dungeon")
    };

    /** 发放的笔记书 content 键，与 GRANT_ADVANCEMENTS 一一对应 */
    private static final String[] NEXT_NOTE_BOOK_CONTENTS = {
            "沉淀阴影",
            "阴影游记",
            "暗影地牢"
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
        if (isAdvancementDone(serverPlayer, ALL_DONE_ADV)) {
            serverPlayer.displayClientMessage(
                    Component.translatable("message.pasterdream.story_guide.all_done"), true);
            return InteractionResultHolder.fail(stack);
        }

        // 3. 从后往前找最高已完成的前置进度，发放下一本笔记书并同步授予进度
        int grantIndex = 0;
        for (int i = GRANT_ADVANCEMENTS.length - 1; i >= 0; i--) {
            if (isAdvancementDone(serverPlayer, GRANT_ADVANCEMENTS[i])) {
                grantIndex = i + 1;
                break;
            }
        }

        ItemStack note = DreamNotesBookWithNBTToCreativeModeTab.buildNBT(NEXT_NOTE_BOOK_CONTENTS[grantIndex]);
        if (!player.getInventory().add(note)) {
            player.drop(note, false);
        }

        grantAdvancement(serverPlayer, GRANT_ADVANCEMENTS[grantIndex]);

        level.playSound(null, player.blockPosition(),
                SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);

        stack.shrink(1);
        return InteractionResultHolder.consume(stack);
    }

    private static void grantAdvancement(ServerPlayer player, ResourceLocation id) {
        Advancement adv = player.server.getAdvancements().getAdvancement(id);
        if (adv == null) {
            return;
        }
        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(adv);
        for (String criteria : progress.getRemainingCriteria()) {
            player.getAdvancements().award(adv, criteria);
        }
    }

    private static boolean isAdvancementDone(ServerPlayer player, ResourceLocation id) {
        Advancement adv = player.server.getAdvancements().getAdvancement(id);
        return adv != null && player.getAdvancements().getOrStartProgress(adv).isDone();
    }
}