package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.entity.ShadowHandEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class ShadowHandFishingHandler {

    private static final ResourceKey<Level> LAMP_SHADOW_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));

    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        Player player = event.getEntity();
        if (player == null) return;

        if (player instanceof FakePlayer) return;

        if (player.getMainHandItem().getItem() instanceof StarWishRodItem
                || player.getOffhandItem().getItem() instanceof StarWishRodItem) {
            return;
        }

        Level level = player.level();
        if (level.isClientSide()) return;
        if (!level.dimension().equals(LAMP_SHADOW_WORLD)) return;

        // 10% 概率发出阴影傀儡吼叫声，但什么都不生成
        if (level.getRandom().nextDouble() < 0.10) {
            level.playSound(player, player.blockPosition(),
                    ModSounds.ROAR0.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
        }

        // 15% 概率生成阴影之手
        if (level.getRandom().nextDouble() < 0.15) {
            ShadowHandEntity shadowHand = new ShadowHandEntity(ModEntities.SHADOW_HAND.get(), level);
            double spawnX = player.getX() + (level.getRandom().nextDouble() - 0.5) * 4;
            double spawnY = player.getY() + 1.5;
            double spawnZ = player.getZ() + (level.getRandom().nextDouble() - 0.5) * 4;
            shadowHand.setPos(spawnX, spawnY, spawnZ);
            shadowHand.setTarget(player);
            level.addFreshEntity(shadowHand);

            level.playSound(player, player.blockPosition(),
                    SoundEvents.GUARDIAN_AMBIENT, SoundSource.HOSTILE, 1.0F, 0.5F);

            event.damageRodBy(2);
        }
    }
}
