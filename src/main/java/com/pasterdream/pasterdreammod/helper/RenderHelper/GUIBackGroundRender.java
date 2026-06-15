package com.pasterdream.pasterdreammod.helper.RenderHelper;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class GUIBackGroundRender
{
    public static ResourceLocation PASTERDREAM_INVENTORY_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/pasterdream_inventory.png");
    public static ResourceLocation DYEDREAM_DESK_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dyedream_desk.png");
    public static ResourceLocation SHADOW_DESK_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/shadow_desk.png");
    public static ResourceLocation STORAGE_BAG_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/storage_bag.png");
    public static ResourceLocation BIG_STORAGE_BAG_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/big_storage_bag.png");
    public static ResourceLocation MELT_DREAM_CHEST_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/meltdream_chest.png");
    public static ResourceLocation PICNIC_BASKET_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/picnic_basket.png");
    public static ResourceLocation SHADOW_CHEST_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/shadow_chest.png");
    public static ResourceLocation WIND_MOOR_CRATE_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/windmoor_crate.png");
    public static ResourceLocation BLUEPRINT_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/blueprint.png");
    public static ResourceLocation DREAM_ACCUMULATOR_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_accumulator.png");
    public static ResourceLocation RESEARCH_TABLE_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/research_table.png");
    public static ResourceLocation WEAPON_WORKSHOP_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/workshop/weapon_workshop.png");
    public static ResourceLocation WORKSHOP_ANVIL_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/workshop/workshop_anvil.png");
    public static ResourceLocation WORKSHOP_BLAST_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/workshop/workshop_blast.png");
    public static ResourceLocation WORKSHOP_BLAST_LAVA_AMOUNT_BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/workshop/workshop_blast_lava_amount_bar.png");

    public static ResourceLocation DREAM_CAULDRON_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_cauldron/dream_cauldron.png");
    public static ResourceLocation DREAM_CAULDRON_MELT_DREAM_LIQUID_AMOUNT_BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_cauldron/dream_cauldron_meltdream_liquid_amount_bar.png");
    public static ResourceLocation DREAM_CAULDRON_BUTTON_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_cauldron/dream_cauldron_button.png");
    public static ResourceLocation DREAM_CAULDRON_BUTTON_HOVERED_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_cauldron/dream_cauldron_button_hovered.png");

    public static ResourceLocation SHADOW_BLAST_FURNACE_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/shadow_blast_furnace/shadow_blast_furnace.png");
    public static ResourceLocation SHADOW_BLAST_FURNACE_PROCESS_BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/shadow_blast_furnace/shadow_blast_furnace_process.png");
    public static ResourceLocation SHADOW_BLAST_FURNACE_SHADOW_LIQUID_AMOUNT_BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/shadow_blast_furnace/shadow_blast_furnace_shadow_liquid_amount_bar.png");

    public static ResourceLocation MELT_DREAM_ENERGY_BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/melt_dream_energy/melt_dream_energy_bar.png");
    public static ResourceLocation MELT_DREAM_ENERGY_AMOUNT_BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/melt_dream_energy/melt_dream_energy_amount_bar.png");

    public static ResourceLocation SAN_BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/san/san_bar.png");
    public static ResourceLocation SAN_AMOUNT_BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/san/san_amount_bar.png");

    public static ResourceLocation CLAYPAN_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/claypan/claypan.png");
    public static ResourceLocation ARROW = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/claypan/arrow.png");

    public static ResourceLocation MORTAR_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/mortar/mortar.png");

    public static void rendPasterDreamInventoryGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(PASTERDREAM_INVENTORY_GUI, x, y, 0, 0, 170, 84, 170, 84);
    }

    public static void rendDyedreamDeskGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(DYEDREAM_DESK_GUI, x, y, 0, 0, 28, 51, 28, 51);
    }

    public static void rendShadowDeskGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(SHADOW_DESK_GUI, x, y, 0, 0, 28, 51, 28, 51);
    }

    public static void rendStorageBagGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(STORAGE_BAG_GUI, x, y, 0, 0, 86, 73, 86, 73);
    }

    public static void rendBigStorageBagGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(BIG_STORAGE_BAG_GUI, x, y, 0, 0, 128, 110, 128, 110);
    }

    public static void rendMeltDreamChestGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(MELT_DREAM_CHEST_GUI, x, y, 0, 0, 66, 66, 66, 66);
    }

    public static void rendPicnicBasketGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(PICNIC_BASKET_GUI, x, y, 0, 0, 104, 68, 104, 68);
    }

    public static void rendShadowChestGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(SHADOW_CHEST_GUI, x, y, 0, 0, 96, 71, 96, 71);
    }

    public static void rendWindMoorCrateGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(WIND_MOOR_CRATE_GUI, x, y, 0, 0, 114, 96, 114, 96);
    }

    public static void rendBlueprintGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(BLUEPRINT_GUI, x, y, 0, 0, 94, 94, 94, 94);
    }

    public static void rendDreamAccumulatorGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(DREAM_ACCUMULATOR_GUI, x, y, 0, 0, 28, 81, 28, 81);
    }

    public static void rendResearchTableGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(RESEARCH_TABLE_GUI, x, y, 0, 0, 197, 115, 197, 115);
    }

    public static void rendWeaponWorkshopGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(WEAPON_WORKSHOP_GUI, x, y, 0, 0, 174, 86, 174, 86);
    }

    public static void rendWorkshopAnvilGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(WORKSHOP_ANVIL_GUI, x, y, 0, 0, 154, 74, 154, 74);
    }

    public static void rendWorkshopBlastGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(WORKSHOP_BLAST_GUI, x, y, 0, 0, 128, 105, 128, 105);
    }

    public static void rendWorkshopLavaAmountBar(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(WORKSHOP_BLAST_LAVA_AMOUNT_BAR, x, y + (int)(24 * (1 - percent)), 0, (int)(24 * (1 - percent)), 7, (int)(24 * percent), 7, 24);
    }

    public static void rendDreamCauldronGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(DREAM_CAULDRON_GUI, x, y, 0, 0, 171, 93, 171, 93);
    }

    public static void rendDreamCauldronMeltDreamLiquidBar(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(DREAM_CAULDRON_MELT_DREAM_LIQUID_AMOUNT_BAR, x, y + (int)(47 * (1 - percent)), 0, (int)(47 * (1 - percent)), 5, (int)(47 * percent), 5, 47);
    }

    public static void rendDreamCauldronButtonGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(DREAM_CAULDRON_BUTTON_GUI, x, y, 0, 0, 38, 15, 38, 15);
    }

    public static void rendDreamCauldronButtonHoveredGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(DREAM_CAULDRON_BUTTON_HOVERED_GUI, x, y, 0, 0, 38, 15, 38, 15);
    }

    public static void rendShadowBlastFurnaceGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(SHADOW_BLAST_FURNACE_GUI, x, y, 0, 0, 126, 103, 126, 103);
    }

    public static void rendShadowBlastFurnaceProcessBar(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(SHADOW_BLAST_FURNACE_PROCESS_BAR, x, y + (int)(34 * (1 - percent)), 0, (int)(34 * (1 - percent)), 38, (int)(34 * percent), 38, 34);
    }

    public static void rendShadowBlastFurnaceShadowLiquidAmountBar(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(SHADOW_BLAST_FURNACE_SHADOW_LIQUID_AMOUNT_BAR, x, y + (int)(35 * (1 - percent)), 0, (int)(35 * (1 - percent)), 10, (int)(35 * percent), 10, 35);
    }

    public static void rendMeltDreamEnergyBar(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(MELT_DREAM_ENERGY_BAR, x, y, 0, 0, 72, 12, 72, 12);
    }

    public static void rendMeltDreamEnergyAmountBar(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(MELT_DREAM_ENERGY_AMOUNT_BAR, x, y, 0, 0, (int)(66 * percent), 4, 66, 4);
    }

    public static void rendSanBar(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(SAN_BAR, x, y, 0, 0, 28, 26, 28, 26);
    }

    public static void rendSanAmountBar(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(SAN_AMOUNT_BAR, x, y + (int)(26 * (1 - percent)), 0, (int)(26 * (1 - percent)), 28, (int)(26 * percent), 28, 26);
    }

    public static void rendClaypanGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(CLAYPAN_GUI, x, y, 0, 0, 82, 28, 82, 28);
    }

    public static void rendArrow(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(ARROW, x, y, 0, 0, (int)(22 * percent), 15, 22, 15);
    }

    public static void rendMortarGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(MORTAR_GUI, x, y, 0, 0, 175, 46, 175, 46);
    }
}
