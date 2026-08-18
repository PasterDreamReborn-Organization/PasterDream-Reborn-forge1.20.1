package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

public class ShadowChoiceHandler {

    private static final ResourceLocation SHADOW_CHOICE =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_choice");
    private static final ResourceLocation TALENT_LIGHT =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/talent_light");
    private static final ResourceLocation TALENT_SHADOW =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/talent_shadow");

    private static final int LINE_INTERVAL = 40;

    private ShadowChoiceHandler() {
    }

    public static void choose(ServerPlayer player, int buttonId) {
        player.closeContainer();

        AdvancementHelper.grant(player, SHADOW_CHOICE, "make_choice");

        player.level().playSound(null, player.blockPosition(), ModSounds.SHADOW_DOOR.get(), SoundSource.NEUTRAL, 1, 1);

        if (buttonId == 0) {
            // 选影
            AdvancementHelper.grant(player, TALENT_SHADOW, "choose_shadow");
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModItems.SHADOW_HILT.get()));
            playBranchDialogue(player, "shadow");
        } else {
            // 选灯
            AdvancementHelper.grant(player, TALENT_LIGHT, "choose_light");
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModItems.WHITE_CRYSTAL.get()));
            playBranchDialogue(player, "light");
        }
    }

    private static void playBranchDialogue(ServerPlayer player, String branch) {
        player.displayClientMessage(Component.translatable("dialogue.pasterdream.nameless." + branch + "_1"), false);
        for (int i = 2; i <= 4; i++) {
            final int line = i;
            PasterDreamMod.queueServerWork((i - 1) * LINE_INTERVAL,
                    () -> player.displayClientMessage(
                            Component.translatable("dialogue.pasterdream.nameless." + branch + "_" + line), false));
        }
    }
}
