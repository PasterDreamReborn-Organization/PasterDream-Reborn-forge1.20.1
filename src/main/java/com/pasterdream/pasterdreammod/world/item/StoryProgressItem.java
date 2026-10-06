package com.pasterdream.pasterdreammod.world.item;

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

import java.util.HashMap;
import java.util.Map;

/**
 * 已解析的笔记：右键后按各自剧情解析线顺序发放下一本笔记书，并同步授予对应进度。
 * 灯影之下与染梦世界各持有独立的 {@link Line} 配置。
 */
public class StoryProgressItem extends Item {

    /** 一条剧情解析线：入场门槛进度 + 按顺序发放的笔记书与对应进度。 */
    public record Line(ResourceLocation entryAdvancement,
                       ResourceLocation[] grantAdvancements,
                       String[] nextNoteBookContents,
                       String notEnteredMessageKey,
                       String allDoneMessageKey) {

        ResourceLocation allDoneAdvancement() {
            return grantAdvancements[grantAdvancements.length - 1];
        }
    }

    /** 灯影之下剧情解析线。 */
    public static final Line LAMP_SHADOW = new Line(
            id("story/enter_lamp_shadow_world"),
            new ResourceLocation[]{
                    id("story/deposition_shadow"),
                    id("story/lamp_shadow_travelogue_1"),
                    id("story/shadow_dungeon"),
                    id("story/deception"),
                    id("story/bargain"),
                    id("story/shattered")
            },
            new String[]{"沉淀阴影", "灯影游记 其一", "暗影地牢", "欺诈", "交易", "破碎"},
            "message.pasterdream.story_guide.not_entered_lamp_shadow",
            "message.pasterdream.story_guide.all_done");

    /** 染梦世界剧情解析线。 */
    public static final Line DYEDREAM = new Line(
            id("story/dyedream_world"),
            new ResourceLocation[]{
                    id("story/research_on_sweet_dream_world"),
                    id("story/essence_of_dream_world"),
                    id("story/pale_snow_lotus_and_boneneeedle")
            },
            new String[]{"关于美梦世界的研究", "梦境世界的本质", "苍白雪莲与苍白骨针"},
            "message.pasterdream.story_guide.not_entered_dyedream",
            "message.pasterdream.story_guide.all_done_dyedream");

    /** 打开剧情笔记书时授予的剧情进度（content 键 → 进度ID），两条线共用。 */
    private static final Map<String, ResourceLocation> NOTE_OPEN_ADVANCEMENTS = new HashMap<>();

    static {
        registerLine(LAMP_SHADOW);
        registerLine(DYEDREAM);
    }

    private static void registerLine(Line line) {
        for (int i = 0; i < line.nextNoteBookContents().length; i++) {
            NOTE_OPEN_ADVANCEMENTS.put(line.nextNoteBookContents()[i], line.grantAdvancements()[i]);
        }
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", path);
    }

    private final Line line;

    public StoryProgressItem(Properties properties, Line line) {
        super(properties);
        this.line = line;
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

        // 1. 检查是否踏入对应梦境
        if (!isAdvancementDone(serverPlayer, line.entryAdvancement())) {
            serverPlayer.displayClientMessage(
                    Component.translatable(line.notEnteredMessageKey()), true);
            return InteractionResultHolder.fail(stack);
        }

        // 2. 全部完成？
        if (isAdvancementDone(serverPlayer, line.allDoneAdvancement())) {
            serverPlayer.displayClientMessage(
                    Component.translatable(line.allDoneMessageKey()), true);
            return InteractionResultHolder.fail(stack);
        }

        // 3. 从后往前找最高已完成的前置进度，发放下一本笔记书并同步授予进度
        ResourceLocation[] grants = line.grantAdvancements();
        int grantIndex = 0;
        for (int i = grants.length - 1; i >= 0; i--) {
            if (isAdvancementDone(serverPlayer, grants[i])) {
                grantIndex = i + 1;
                break;
            }
        }

        ItemStack note = DreamNotesBookWithNBTToCreativeModeTab.buildNBT(line.nextNoteBookContents()[grantIndex]);
        if (!player.getInventory().add(note)) {
            player.drop(note, false);
        }

        grantAdvancement(serverPlayer, grants[grantIndex]);

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

    /**
     * 打开剧情笔记书时调用：授予对应的剧情进度（幂等），并由进度联动解锁帕秋莉对应词条。
     * 笔记书可在多人间流转，实际打开者获得进度，利好多人模式。
     */
    public static void grantProgressOnNoteOpened(ServerPlayer player, String content) {
        ResourceLocation advancementId = NOTE_OPEN_ADVANCEMENTS.get(content);
        if (advancementId != null) {
            grantAdvancement(player, advancementId);
        }
    }

    private static boolean isAdvancementDone(ServerPlayer player, ResourceLocation id) {
        Advancement adv = player.server.getAdvancements().getAdvancement(id);
        return adv != null && player.getAdvancements().getOrStartProgress(adv).isDone();
    }
}
