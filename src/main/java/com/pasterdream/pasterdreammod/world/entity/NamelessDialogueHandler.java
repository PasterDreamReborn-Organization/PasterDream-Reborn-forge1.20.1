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

    public static final int LINE_INTERVAL = 40;
    public static final int FIRST_LINES = 12;
    public static final int SECOND_LINES = 10;

    private NamelessDialogueHandler() {
    }

    public static void onInteract(NamelessEntity entity, ServerPlayer player) {
        entity.setAnimation("say");

        if (!AdvancementHelper.isDone(player, FIRST_DIALOGUE)) {
            entity.startDialogue(player, NamelessEntity.DialoguePhase.FIRST);
        } else if (!AdvancementHelper.isDone(player, INTRUDE_COMPLETE)) {
            entity.startDialogue(player, NamelessEntity.DialoguePhase.FIRST);
        } else if (!AdvancementHelper.isDone(player, SECOND_DIALOGUE)) {
            entity.startDialogue(player, NamelessEntity.DialoguePhase.SECOND);
        } else {
            sendLine(player, "dialogue.pasterdream.nameless.wait");
        }
    }

    public static String lineKey(NamelessEntity.DialoguePhase phase, int line) {
        String prefix = phase == NamelessEntity.DialoguePhase.FIRST ? "first_" : "second_";
        return "dialogue.pasterdream.nameless." + prefix + line;
    }

    public static void finishDialogue(NamelessEntity entity, ServerPlayer player, NamelessEntity.DialoguePhase phase) {
        if (phase == NamelessEntity.DialoguePhase.FIRST) {
            AdvancementHelper.grant(player, FIRST_DIALOGUE, "first_dialogue");
            LampShadowWorldTeleporter.teleportToOverworld(player, player.blockPosition());
            // TODO Phase F: shadow_e_0 门控留占位（击败亚伦柯斯之触后不再施加）
            player.addEffect(new MobEffectInstance(ModEffects.SHADOW_SPYON.get(), 32000, 0, false, false));
        } else {
            AdvancementHelper.grant(player, SECOND_DIALOGUE, "second_dialogue");
        }
    }

    public static void sendLine(ServerPlayer player, String key) {
        player.displayClientMessage(Component.translatable(key), false);
    }
}