package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItemModels;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModEntityRenderer;
import com.pasterdream.pasterdreammod.init.ModBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import com.pasterdream.pasterdreammod.world.item.PotionBottleRegistry;
import com.pasterdream.pasterdreammod.world.item.curio.RedDewRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.StrikeRingItem;
import com.pasterdream.pasterdreammod.world.item.prophecycard.ProphecyCardItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 仅客户端注册的模组事件（MOD 总线）。
 * 持有客户端类引用的监听器必须放在这里，
 * 避免在专用服务器加载 PasterDreamMod 时触发客户端类加载。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents
{
    @SubscribeEvent
    public static void registerItemModels(ModelEvent.ModifyBakingResult event)
    {
        ModItemModels.register(event);
    }

    @SubscribeEvent
    public static void getItemModels(ModelEvent.BakingCompleted event)
    {
        ModItemModels.getBakedModel(event);
    }

    @SubscribeEvent
    public static void AddOverlays(RegisterGuiOverlaysEvent event)
    {
        event.registerAboveAll("melt_dream_energy", MeltDreamEnergyTank.MELT_DREAM_ENERGY_TANK);
        event.registerAboveAll("san", SanTank.SAN_TANK);
        event.registerBelowAll("lose_mind", LoseMind.GUI_OVERLAY);
        event.registerAboveAll("aaroncos_hand_boss_bar", AaroncosHandBossBar.OVERLAY);
        event.registerAboveAll("wind_knight_boss_bar", WindKnightBossBar.OVERLAY);
        event.registerBelowAll("cloud_mist_hud", CloudMistHud.GUI_OVERLAY);
    }

    @SubscribeEvent
    public static void AddEntityRenderersEvent(EntityRenderersEvent.RegisterRenderers event)
    {
        ModBlockEntityRenderer.EntityRenderersEventRegister(event);
        ModEntityRenderer.registerRenderers(event);
    }

    @SubscribeEvent
    public static void AddRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        ModEntityRenderer.registerLayerDefinitions(event);
    }

    public static void registerItemProperties()
    {
        // 星者祈愿钓竿出杆切换模型（原版 cast predicate 仅注册在 Items.FISHING_ROD 上）
        ItemProperties.register(
                ModItems.STAR_WISH_ROD.get(),
                ResourceLocation.parse("cast"),
                (stack, level, entity, seed) -> {
                    if (entity == null) return 0.0F;
                    boolean held = entity.getMainHandItem() == stack || entity.getOffhandItem() == stack;
                    if (entity instanceof net.minecraft.world.entity.player.Player player) {
                        return held && player.fishing != null ? 1.0F : 0.0F;
                    }
                    return 0.0F;
                }
        );

        ItemProperties.register(
                ModItems.RED_DEW_RING.get(),
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lv"),
                (stack, level, entity, seed) -> RedDewRingItem.getPredicateValue(RedDewRingItem.getLv(stack))
        );

        ItemProperties.register(
                ModItems.STRIKE_RING.get(),
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lv"),
                (stack, level, entity, seed) -> StrikeRingItem.getPredicateValue(StrikeRingItem.getLv(stack))
        );

        // 预言卡：按 NBT Type 切换纹理
        ItemProperties.register(
                ModItems.PROPHECY_CARD.get(),
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "type"),
                (stack, level, entity, seed) -> ProphecyCardItem.getPredicateValue(stack)
        );

        // 药剂瓶：按 NBT PotionType 切换纹理
        ItemProperties.register(
                PotionBottleRegistry.POTION_BOTTLE.get(),
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "type"),
                (stack, level, entity, seed) -> PotionBottleItem.getPredicateValue(stack)
        );
    }
}