package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.helper.itemwithnbt.dreamnoteswithnbt.DreamNotesWithNBT;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.advancements.Advancement;
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

    private static final ResourceLocation[] STORY_ADVANCEMENTS = {
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/deposition_shadow"),
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/shadow_travelogue"),
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/shadow_dungeon"),
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/scare")
    };

    private static final String[] STORY_CONTENTS = {
            "depositionShadow",
            "shadowTravelogue",
            "shadowDungeon",
            "scare"
    };

    private static final String[] CRITERION_KEYS = {
            "read_deposition_shadow_note",
            "read_shadow_travelogue_note",
            "read_shadow_dungeon_note",
            "read_scare_note"
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

        for (int i = 0; i < STORY_ADVANCEMENTS.length; i++) {
            Advancement adv = serverPlayer.getServer().getAdvancements()
                    .getAdvancement(STORY_ADVANCEMENTS[i]);
            if (adv == null) continue;

            if (!serverPlayer.getAdvancements().getOrStartProgress(adv).isDone()) {
                serverPlayer.getAdvancements().award(adv, CRITERION_KEYS[i]);

                ItemStack notes = DreamNotesWithNBT.dreamNotesWithNBT(
                        ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get(),
                        "content",
                        STORY_CONTENTS[i]);

                if (!player.getInventory().add(notes)) {
                    player.drop(notes, false);
                }

                level.playSound(null, player.blockPosition(),
                        SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);

                stack.shrink(1);
                return InteractionResultHolder.consume(stack);
            }
        }

        return InteractionResultHolder.pass(stack);
    }
}
