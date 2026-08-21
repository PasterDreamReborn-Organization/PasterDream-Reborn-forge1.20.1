package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.world.block.twilightlantern.LampShadowWorldTeleporter;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class NamelessDialogueHandler {

    private static final ResourceLocation FIRST_DIALOGUE =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_npc_first_dialogue");
    private static final ResourceLocation INTRUDE_COMPLETE =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_intrude_complete");
    private static final ResourceLocation SECOND_DIALOGUE =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_npc_second_dialogue");

    private static final int LINE_INTERVAL = 40;
    private static final int FIRST_LINES = 12;
    private static final int SECOND_LINES = 10;

    private NamelessDialogueHandler() {
    }

    public static void onInteract(NamelessEntity entity, ServerPlayer player) {
        entity.setAnimation("say");

        if (!AdvancementHelper.isDone(player, FIRST_DIALOGUE)) {
            playFirstDialogue(player);
        } else if (!AdvancementHelper.isDone(player, INTRUDE_COMPLETE)) {
            sendLine(player, "dialogue.pasterdream.nameless.wait");
        } else if (!AdvancementHelper.isDone(player, SECOND_DIALOGUE)) {
            playSecondDialogue(player);
        } else {
            sendLine(player, "dialogue.pasterdream.nameless.wait");
        }
    }

    private static void playFirstDialogue(ServerPlayer player) {
        for (int i = 1; i <= FIRST_LINES; i++) {
            final int line = i;
            PasterDreamMod.queueServerWork(i * LINE_INTERVAL,
                    () -> sendLine(player, "dialogue.pasterdream.nameless.first_" + line));
        }
        PasterDreamMod.queueServerWork((FIRST_LINES + 1) * LINE_INTERVAL, () -> finishFirstDialogue(player));
    }

    private static void finishFirstDialogue(ServerPlayer player) {
        AdvancementHelper.grant(player, FIRST_DIALOGUE, "first_dialogue");
        LampShadowWorldTeleporter.teleportToOverworld(player, player.blockPosition());
        // TODO Phase F: shadow_e_0 门控留占位（击败亚伦柯斯之触后不再施加）
        player.addEffect(new MobEffectInstance(ModEffects.SHADOW_SPYON.get(), 32000, 0, false, false));
    }

    private static void playSecondDialogue(ServerPlayer player) {
        for (int i = 1; i <= SECOND_LINES; i++) {
            final int line = i;
            PasterDreamMod.queueServerWork(i * LINE_INTERVAL,
                    () -> sendLine(player, "dialogue.pasterdream.nameless.second_" + line));
        }
        PasterDreamMod.queueServerWork((SECOND_LINES + 1) * LINE_INTERVAL,
                () -> AdvancementHelper.grant(player, SECOND_DIALOGUE, "second_dialogue"));
    }

    private static void sendLine(ServerPlayer player, String key) {
        player.displayClientMessage(Component.translatable(key), false);
    }
}
