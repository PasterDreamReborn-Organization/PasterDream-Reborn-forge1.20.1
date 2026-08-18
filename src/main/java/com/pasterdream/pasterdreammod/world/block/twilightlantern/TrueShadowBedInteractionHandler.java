package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import com.pasterdream.pasterdreammod.world.dimension.LampShadowDimension;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class TrueShadowBedInteractionHandler {

    private static final ResourceLocation BASTION_GUARD_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/bastion_guard");

    private static final ResourceLocation SECOND_DIALOGUE_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_npc_second_dialogue");

    private static final ResourceLocation SHADOW_CHOICE_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_choice");

    public static void execute(Level world, BlockPos pos, Player entity) {
        if (entity == null) return;
        if (world.isClientSide()) return;

        // Swing main hand
        entity.swing(InteractionHand.MAIN_HAND, true);

        if (!(entity instanceof ServerPlayer sp)) return;

        // 灯影之下：第二次对话已完成且尚未做出选择 → 打开灯与影选择界面；否则返回主世界
        if (world.dimension() == LampShadowDimension.LAMP_SHADOW_WORLD) {
            if (shouldOpenChoice(sp)) {
                openChoiceScreen(sp, pos);
            } else {
                LampShadowWorldTeleporter.teleportToOverworld(sp, pos);
            }
            return;
        }

        // 主世界/下界：完成据点守卫事件后才能入眠
        if (!hasBastionGuard(sp)) {
            return;
        }

        // Drain 10 San and teleport to lamp_shadow_world
        SanHelper.addPlayerSanAndSync(sp, -10);
        sp.server.execute(() -> LampShadowWorldTeleporter.execute(sp.level(), sp));
    }

    private static boolean shouldOpenChoice(ServerPlayer player) {
        return AdvancementHelper.isDone(player, SECOND_DIALOGUE_ADV)
                && !AdvancementHelper.isDone(player, SHADOW_CHOICE_ADV);
    }

    private static void openChoiceScreen(ServerPlayer player, BlockPos pos) {
        NetworkHooks.openScreen(player, new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.literal("ShadowSelectEnd");
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                return new ShadowSelectEndMenu(id, inventory);
            }
        }, pos);
    }

    private static boolean hasBastionGuard(ServerPlayer player) {
        Advancement adv = player.server.getAdvancements().getAdvancement(BASTION_GUARD_ADV);
        return adv != null && player.getAdvancements().getOrStartProgress(adv).isDone();
    }
}
