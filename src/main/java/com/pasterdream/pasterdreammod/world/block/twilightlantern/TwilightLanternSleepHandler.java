package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class TwilightLanternSleepHandler {

    private static final ResourceLocation BASTION_GUARD_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/bastion_guard");

    @SubscribeEvent
    public static void onPlayerSleepInBed(PlayerSleepInBedEvent event) {
        Level world = event.getEntity().level();
        BlockPos pos = event.getPos();
        BlockPos lanternPos = pos.above(2);

        // Check: block above bed is twilight_lantern
        if (!world.getBlockState(lanternPos).is(ModBlocks.TWILIGHT_LANTERN.get())) return;

        // Check: player has completed bastion guard event
        if (event.getEntity() instanceof ServerPlayer sp && !hasBastionGuard(sp)) return;

        // If in lamp_shadow_world, do NOT trigger the bed teleport
        if (world.dimension() == LampShadowWorldTeleporter.getLampShadowWorld()) return;

        if (event.getEntity() instanceof ServerPlayer player) {
            PasterDreamMod.queueServerWork(95, () -> {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                world.setBlock(pos, Blocks.BLACK_BED.defaultBlockState(), 3);
                SanHelper.addPlayerSanAndSync(player, -10);
                LampShadowWorldTeleporter.execute(world, player);
            });
        }
    }

    private static boolean hasBastionGuard(ServerPlayer player) {
        Advancement adv = player.server.getAdvancements().getAdvancement(BASTION_GUARD_ADV);
        return adv != null && player.getAdvancements().getOrStartProgress(adv).isDone();
    }
}
