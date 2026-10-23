package com.pasterdream.pasterdreammod.capability;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.IMeltDreamEnergy;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyProvider;
import com.pasterdream.pasterdreammod.capability.san.ISan;
import com.pasterdream.pasterdreammod.capability.san.SanProvider;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MaxMeltDreamEnergySyncPacket;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MeltDreamEnergySyncPacket;
import com.pasterdream.pasterdreammod.network.san.IsSanEnableSyncPacket;
import com.pasterdream.pasterdreammod.network.san.MaxSanSyncPacket;
import com.pasterdream.pasterdreammod.network.san.SanSyncPacket;
import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.world.item.meltdreamcrystalchestresettool.MeltDreamCrystalChestResetCooldown;

import com.pasterdream.pasterdreammod.tag.ModItemTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;

public class EventRegister
{
    //注册能力
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public class CapabilityEvents
    {
        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event)
        {
            event.register(IMeltDreamEnergy.class);
            event.register(ISan.class);
        }
    }

    //在FORGE总线中为玩家附加能力
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class PlayerAttachHandler
    {
        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event)
        {
            if (event.getObject() instanceof Player)
            {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_energy"), new MeltDreamEnergyProvider());
                event.addCapability(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "san"), new SanProvider());
            }
        }

        @SubscribeEvent
        public static void onPlayerClone(PlayerEvent.Clone event)
        {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(oldCapability -> event.getEntity().getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(newCapability -> newCapability.copyValueFromOtherMeltDreamEnergy(oldCapability)));
            event.getOriginal().getCapability(ModCapabilities.SAN).ifPresent(oldCapability -> event.getEntity().getCapability(ModCapabilities.SAN).ifPresent(newCapability -> newCapability.copyValueFromOtherSan(oldCapability)));
            MeltDreamCrystalChestResetCooldown.copyOnPlayerClone(event.getOriginal(), event.getEntity());
            event.getOriginal().invalidateCaps();
            sync(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
        {
            sync(event.getEntity());
            syncChestResetCooldown(event.getEntity());
            givePatchouliBookOnFirstJoin(event);
        }

        @SubscribeEvent
        public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
        {
            sync(event.getEntity());
            syncChestResetCooldown(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event)
        {
            sync(event.getEntity());
            syncChestResetCooldown(event.getEntity());
        }

        private static void syncChestResetCooldown(Player player)
        {
            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer)
            {
                MeltDreamCrystalChestResetCooldown.syncToClient(serverPlayer);
            }
        }

        private static final String PATCHOULI_BOOK_TAG = "pasterdream_received_book";

        private static void givePatchouliBookOnFirstJoin(PlayerEvent.PlayerLoggedInEvent event)
        {
            if (!Config.givePatchouliBookOnFirstJoin) return;

            Player player = event.getEntity();
            if (player.level().isClientSide) return;
            if (player.getTags().contains(PATCHOULI_BOOK_TAG)) return;

            ItemStack book = vazkii.patchouli.api.PatchouliAPI.get().getBookStack(
                    ResourceLocation.fromNamespaceAndPath("pasterdream", "seniors_dream"));
            if (!player.getInventory().add(book)) {
                player.drop(book, false);
            }

            player.addTag(PATCHOULI_BOOK_TAG);
        }

        private static void sync(Player player)
        {
            if (!player.level().isClientSide)
            {
                player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability -> MeltDreamEnergySyncPacket.sendToPlayer(player, capability));
                player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability -> MaxMeltDreamEnergySyncPacket.sendToPlayer(player, capability));
                player.getCapability(ModCapabilities.SAN).ifPresent(capability -> SanSyncPacket.sendToPlayer(player, capability));
                player.getCapability(ModCapabilities.SAN).ifPresent(capability -> MaxSanSyncPacket.sendToPlayer(player, capability));
                player.getCapability(ModCapabilities.SAN).ifPresent(capability -> IsSanEnableSyncPacket.sendToPlayer(player, capability));
            }
        }
    }

    /** 在标签加载/更新后，将 compostable 标签中的物品自动注册到堆肥桶 */
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ComposterHandler {
        @SubscribeEvent
        public static void onTagsUpdated(TagsUpdatedEvent event) {
            BuiltInRegistries.ITEM.getTagOrEmpty(ModItemTags.COMPOSTABLE)
                    .forEach(holder -> ComposterBlock.COMPOSTABLES.put(holder.value(), 0.65f));
        }
    }

}
