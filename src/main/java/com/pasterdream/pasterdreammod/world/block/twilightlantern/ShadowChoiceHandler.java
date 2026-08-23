package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShadowChoiceHandler {

    private static final ResourceLocation SHADOW_CHOICE =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_choice");
    private static final ResourceLocation TALENT_LIGHT =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/talent_light");
    private static final ResourceLocation TALENT_SHADOW =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/talent_shadow");

    /** 分支对话状态机 NBT 键（存于玩家持久数据，随玩家 tick 推进） */
    private static final String NBT_BRANCH = "pasterdream_shadow_choice_branch";
    private static final String NBT_LINE = "pasterdream_shadow_choice_line";
    private static final String NBT_TICK = "pasterdream_shadow_choice_tick";

    private static final int LINE_INTERVAL = 40;
    private static final int BRANCH_LINES = 4;

    private ShadowChoiceHandler() {
    }

    public static void choose(ServerPlayer player, int buttonId) {
        player.closeContainer();

        AdvancementHelper.grant(player, SHADOW_CHOICE, "make_choice");

        player.level().playSound(null, player.blockPosition(), ModSounds.SHADOW_DOOR.get(), SoundSource.NEUTRAL, 1, 1);

        String branch;
        if (buttonId == 0) {
            // 选影
            AdvancementHelper.grant(player, TALENT_SHADOW, "choose_shadow");
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModItems.SHADOW_HILT.get()));
            branch = "shadow";
        } else {
            // 选灯
            AdvancementHelper.grant(player, TALENT_LIGHT, "choose_light");
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModItems.WHITE_CRYSTAL.get()));
            branch = "light";
        }

        // 第一行立即发送，其余由玩家 tick 状态机推进
        player.displayClientMessage(Component.translatable("dialogue.pasterdream.nameless." + branch + "_1"), false);
        CompoundTag data = player.getPersistentData();
        data.putString(NBT_BRANCH, branch);
        data.putInt(NBT_LINE, 2);
        data.putInt(NBT_TICK, LINE_INTERVAL);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if (player.level().isClientSide()) return;

        CompoundTag data = player.getPersistentData();
        if (!data.contains(NBT_BRANCH)) return;

        int tick = data.getInt(NBT_TICK) - 1;
        if (tick > 0) {
            data.putInt(NBT_TICK, tick);
            return;
        }

        int line = data.getInt(NBT_LINE);
        if (line <= BRANCH_LINES) {
            player.displayClientMessage(
                    Component.translatable("dialogue.pasterdream.nameless." + data.getString(NBT_BRANCH) + "_" + line), false);
            data.putInt(NBT_LINE, line + 1);
            data.putInt(NBT_TICK, LINE_INTERVAL);
        } else {
            data.remove(NBT_BRANCH);
            data.remove(NBT_LINE);
            data.remove(NBT_TICK);
        }
    }
}