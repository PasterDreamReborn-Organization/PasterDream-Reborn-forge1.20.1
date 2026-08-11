package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest.ShadowChestBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.meltdreamcrystalchest.MeltDreamCrystalChestBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.openedmeltdreamcrystalchest.OpenedMeltDreamCrystalChestBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.lifecrystal.LifeCrystalBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers.TheEndlessBookOfDreamSeekersBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.goldenfoxsculpture.GoldenFoxSculptureBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.foxsculpture.FoxSculptureBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.shadowdungeonportal.BrokenShadowDungeonPortalTileRenderer;
import com.pasterdream.pasterdreammod.world.block.shadowdungeonportal.ShadowDungeonPortalTileRenderer;
import com.pasterdream.pasterdreammod.world.block.shadowvortex.ShadowVortexTileRenderer;
import com.pasterdream.pasterdreammod.world.block.shadowhandtrap.ShadowHandTrapTileRenderer;
import com.pasterdream.pasterdreammod.world.block.shadowbrazier.ShadowBrazierBlockRenderer;
import com.pasterdream.pasterdreammod.world.block.twilightlantern.TwilightLanternTileRenderer;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.coolerpot.WeaponWorkshopCoolerPotBlockEntityRenderer;
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
            BlockEntityRenderers.register(ModBlockEntities.WEAPON_WORKSHOP_COOLER_POT.get(), WeaponWorkshopCoolerPotBlockEntityRenderer::new);
        });
    }

    public static void EntityRenderersEventRegister(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntities.QYM_DOLL.get(), QYMDollBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.UUZ_DOLL.get(), UUZDollBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.DREAM_CAULDRON.get(), DreamCauldronBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SHADOW_CHEST.get(), ShadowChestBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get(), TheEndlessBookOfDreamSeekersBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.LIFE_CRYSTAL.get(), LifeCrystalBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.GOLDEN_FOX_SCULPTURE.get(), GoldenFoxSculptureBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.DESERT_HERO_TOMB.get(), DesertHeroTombBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MELT_DREAM_CRYSTAL_CHEST.get(), MeltDreamCrystalChestBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.OPENED_MELT_DREAM_CRYSTAL_CHEST.get(), OpenedMeltDreamCrystalChestBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.FOX_SCULPTURE.get(), FoxSculptureBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.DREAM_ACCUMULATOR.get(), DreamAccumulatorBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BROKEN_SHADOW_DUNGEON_PORTAL.get(), BrokenShadowDungeonPortalTileRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SHADOW_DUNGEON_PORTAL.get(), ShadowDungeonPortalTileRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SHADOW_VORTEX.get(), ShadowVortexTileRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SHADOW_HAND_TRAP.get(), ShadowHandTrapTileRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SHADOW_BRAZIER.get(), ShadowBrazierBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.TWILIGHT_LANTERN.get(), TwilightLanternTileRenderer::new);
    }
}
