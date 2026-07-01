package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket.PicnicBasketBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest.ShadowChestBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers.TheEndlessBookOfDreamSeekersBlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModBlockEntityRenderer
{
    public static void FMLClientSetupEventRegister(FMLClientSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            BlockEntityRenderers.register(ModBlockEntities.CLAYPAN.get(), ClaypanBlockEntityRenderer::new);
        });
    }

    public static void EntityRenderersEventRegister(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntities.QYM_DOLL.get(), QYMDollBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.UUZ_DOLL.get(), UUZDollBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.DREAM_CAULDRON.get(), DreamCauldronBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SHADOW_CHEST.get(), ShadowChestBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PICNIC_BASKET.get(), PicnicBasketBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get(), TheEndlessBookOfDreamSeekersBlockEntityRenderer::new);
    }
}
