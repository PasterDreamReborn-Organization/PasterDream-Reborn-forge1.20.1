package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** 为自定义弓补上原版仅对 Items.BOW 生效的蓄力视角缩放。 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public final class BowFovHandler {

    private BowFovHandler() {
    }

    @SubscribeEvent
    public static void onComputeFovModifier(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        if (!player.isUsingItem()) {
            return;
        }

        ItemStack stack = player.getUseItem();
        if (!stack.is(ModItems.MELT_DREAM_BOW.get()) && !stack.is(ModItems.FLUFFY_WIND_BOW.get())) {
            return;
        }

        float progress = (float) (stack.getUseDuration() - player.getUseItemRemainingTicks()) / 20.0F;
        progress = Math.min(Math.max(progress, 0.0F), 1.0F);
        if (progress < 1.0F) {
            progress *= progress;
        }

        float effectScale = Minecraft.getInstance().options.fovEffectScale().get().floatValue();
        event.setNewFovModifier(event.getNewFovModifier()
                - event.getFovModifier() * progress * 0.15F * effectScale);
    }
}
