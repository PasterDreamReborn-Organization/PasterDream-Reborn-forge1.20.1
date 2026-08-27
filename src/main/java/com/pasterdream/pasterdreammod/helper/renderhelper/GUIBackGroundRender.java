package com.pasterdream.pasterdreammod.helper.renderhelper;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class GUIBackGroundRender
{
    public static ResourceLocation PASTERDREAM_INVENTORY_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/pasterdream_inventory.png");
    public static ResourceLocation DYEDREAM_DESK_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/desk/dyedream_desk.png");
    public static ResourceLocation SHADOW_DESK_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/desk/shadow_desk.png");
    public static ResourceLocation WIND_MOOR_DESK_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/desk/wind_moor_desk.png");
    public static ResourceLocation STORAGE_BAG_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/storage_bag.png");
    public static ResourceLocation BIG_STORAGE_BAG_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/big_storage_bag.png");
    public static ResourceLocation MELT_DREAM_CRYSTAL_CHEST_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/melt_dream_crystal_chest.png");
    public static ResourceLocation PICNIC_BASKET_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/picnic_basket.png");
    public static ResourceLocation SHADOW_CHEST_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/shadow_chest.png");
    public static ResourceLocation WIND_MOOR_CRATE_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/windmoor_crate.png");
    public static ResourceLocation BLUEPRINT_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/blueprint.png");
    public static ResourceLocation DREAM_ACCUMULATOR_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_accumulator.png");
    public static ResourceLocation RESEARCH_TABLE_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/research_table.png");
    public static ResourceLocation WEAPON_WORKSHOP_CRAFTING_TABLE_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/weapon_workshop/weapon_workshop_crafting_table.png");
    public static ResourceLocation WEAPON_WORKSHOP_ANVIL_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/weapon_workshop/weapon_workshop_anvil.png");
    public static ResourceLocation WEAPON_WORKSHOP_BLAST_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/weapon_workshop/weapon_workshop_blast_furnace.png");
    public static ResourceLocation WEAPON_WORKSHOP_BLAST_LAVA_AMOUNT_BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/weapon_workshop/weapon_workshop_blast_furnace_lava_amount_bar.png");
    public static ResourceLocation WEAPON_WORKSHOP_COOLER_POT_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/weapon_workshop/weapon_workshop_cooler_pot.png");
    public static ResourceLocation WEAPON_WORKSHOP_GRIND_STONE_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/weapon_workshop/weapon_workshop_grind_stone.png");

    public static ResourceLocation DREAM_CAULDRON_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_cauldron/dream_cauldron.png");
    public static ResourceLocation DREAM_CAULDRON_MELT_DREAM_LIQUID_AMOUNT_BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_cauldron/dream_cauldron_melt_dream_liquid_amount_bar.png");
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

    public static ResourceLocation EMPTY_FLUID_SLOT_BUCKET_SHAPE = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/empty_fluid_slot_bucket_shape.png");

    public static ResourceLocation DEVELOPER_NAME_LIST = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/developer_name_list.png");
    public static ResourceLocation DYEDREAM_CRACK = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/dyedream_crack.png");
    public static ResourceLocation DYEDREAM_WORLD = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/dyedream_world.png");
    public static ResourceLocation PINK_SLIME = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/pink_slime.png");
    public static ResourceLocation WHITE_COROLLA = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/white_corolla.png");
    public static ResourceLocation PALE_BONE_NEEDLE = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/pale_bone_needle.png");
    public static ResourceLocation DREAM_FERTILIZER = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/dream_fertilizer.png");
    public static ResourceLocation DREAM_ACCUMULATOR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/dream_accumulator.png");

    public static ResourceLocation THE_LURKERS_IN_THE_SHADOW = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/the_lurkers_in_the_shadow.png");
    public static ResourceLocation INFESTED_CHURCH = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/infested_church.png");
    public static ResourceLocation DEPOSITION_SHADOW = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/deposition_shadow.png");
    public static ResourceLocation LAMP_SHADOW_TRAVELOGUE_1 = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/lamp_shadow_travelogue_1.png");
    public static ResourceLocation SHADOW_DUNGEON = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/shadow_dungeon.png");
    public static ResourceLocation SCARE = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/scare.png");

    public static ResourceLocation NOT_HAVE_WINGS_BIRD_HAVE_SPREAD_WINGS_DREAM_TOO = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/not_have_wings_bird_has_spread_wings_dream_too.png");

    public static ResourceLocation DREAM_NOTES_TEST = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes/dream_notes_test.png");

    public static ResourceLocation UP_BUTTON = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/arrow_button/up_button.png");
    public static ResourceLocation UP_BUTTON_HOVERED = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/arrow_button/up_button_hover.png");
    public static ResourceLocation DOWN_BUTTON = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/arrow_button/down_button.png");
    public static ResourceLocation DOWN_BUTTON_HOVERED = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/arrow_button/down_button_hover.png");
    public static ResourceLocation LEFT_BUTTON = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/arrow_button/left_button.png");
    public static ResourceLocation LEFT_BUTTON_HOVERED = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/arrow_button/left_button_hover.png");
    public static ResourceLocation RIGHT_BUTTON = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/arrow_button/right_button.png");
    public static ResourceLocation RIGHT_BUTTON_HOVERED = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/arrow_button/right_button_hover.png");

    public static final ResourceLocation MINECRAFT_GUI_LEFT_TOP = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/minecraft_original_gui/left_top.png");
    public static final ResourceLocation MINECRAFT_GUI_TOP = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/minecraft_original_gui/multipart_top.png");
    public static final ResourceLocation MINECRAFT_GUI_RIGHT_TOP = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/minecraft_original_gui/right_top.png");
    public static final ResourceLocation MINECRAFT_GUI_LEFT = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/minecraft_original_gui/multipart_left.png");
    public static final ResourceLocation MINECRAFT_GUI_RIGHT = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/minecraft_original_gui/multipart_right.png");
    public static final ResourceLocation MINECRAFT_GUI_LEFT_BOTTOM = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/minecraft_original_gui/left_bottom.png");
    public static final ResourceLocation MINECRAFT_GUI_BOTTOM = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/minecraft_original_gui/multipart_bottom.png");
    public static final ResourceLocation MINECRAFT_GUI_RIGHT_BOTTOM = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/minecraft_original_gui/right_bottom.png");

    public static ResourceLocation EMPTY_NOTES = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes_book/empty_notes.png");
    public static ResourceLocation DREAM_NOTES_BOOK_DYEDREAM_WORLD_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes_book/dream_notes_book_dyedream_world.png");
    public static ResourceLocation DREAM_NOTES_BOOK_OVERWORLD_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes_book/dream_notes_book_overworld.png");
    public static ResourceLocation DREAM_NOTES_BOOK_LAMP_SHADOW_WORLD_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes_book/dream_notes_book_lamp_shadow_world.png");
    public static ResourceLocation SHADOW_DYED_DREAM_NOTES_BOOK_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes_book/shadow_dyed_dream_notes_book.png");
    public static ResourceLocation DREAM_NOTES_BOOK_WIND_JOURNEY_WORLD_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_notes_book/dream_notes_book_wind_journey_world.png");

    public static ResourceLocation USE_GUIDE = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "use_guide");
    public static ResourceLocation DREAM_NOTES_BOOK_OVERWORLD = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_notes_book_overworld");
    public static ResourceLocation DREAM_NOTES_BOOK_DYEDREAM_WORLD = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_notes_book_dyedream_world");
    public static ResourceLocation DREAM_NOTES_BOOK_LAMP_SHADOW_WORLD = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_notes_book_lamp_shadow_world");
    public static ResourceLocation SHADOW_DYED_DREAM_NOTES_BOOK_LAMP_SHADOW_WORLD = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dyed_dream_notes_book_lamp_shadow_world");
    public static ResourceLocation DREAM_NOTES_BOOK_WIND_JOURNEY_WORLD = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_notes_book_wind_journey_world");

    public static ResourceLocation AARONCOS_HAND_BOSS_BAR_LEFT_BACKGROUND = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/aaroncos_hand_boss_bar/aaroncos_hand_boss_bar_left_background.png");
    public static ResourceLocation AARONCOS_HAND_BOSS_BAR_LEFT = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/aaroncos_hand_boss_bar/aaroncos_hand_boss_bar_left.png");
    public static ResourceLocation AARONCOS_HAND_BOSS_BAR_RIGHT_BACKGROUND = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/aaroncos_hand_boss_bar/aaroncos_hand_boss_bar_right_background.png");
    public static ResourceLocation AARONCOS_HAND_BOSS_BAR_RIGHT = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/aaroncos_hand_boss_bar/aaroncos_hand_boss_bar_right.png");

    public static ResourceLocation WIND_KNIGHT_BOSS_BAR_EMPTY = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/wind_knight_boss_bar/wind_knight_boss_bar_empty.png");
    public static ResourceLocation WIND_KNIGHT_BOSS_BAR_FULL = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/wind_knight_boss_bar/wind_knight_boss_bar_full.png");

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

    public static void rendWindMoorDeskGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(WIND_MOOR_DESK_GUI, x, y, 0, 0, 28, 51, 28, 51);
    }

    public static void rendStorageBagGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(STORAGE_BAG_GUI, x, y, 0, 0, 86, 73, 86, 73);
    }

    public static void rendBigStorageBagGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(BIG_STORAGE_BAG_GUI, x, y, 0, 0, 128, 110, 128, 110);
    }

    public static void rendMeltDreamCrystalChestGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(MELT_DREAM_CRYSTAL_CHEST_GUI, x, y, 0, 0, 66, 66, 66, 66);
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

    public static void rendWeaponWorkshopCraftingTableGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(WEAPON_WORKSHOP_CRAFTING_TABLE_GUI, x, y, 0, 0, 174, 86, 174, 86);
    }

    public static void rendWeaponWorkshopAnvilGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(WEAPON_WORKSHOP_ANVIL_GUI, x, y, 0, 0, 154, 74, 154, 74);
    }

    public static void rendWeaponWorkshopBlastFurnaceGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(WEAPON_WORKSHOP_BLAST_GUI, x, y, 0, 0, 128, 105, 128, 105);
    }

    public static void rendWeaponWorkshopBlastFurnaceLavaAmountBar(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(WEAPON_WORKSHOP_BLAST_LAVA_AMOUNT_BAR, x, y + (int)(0.5 + (24 * (1 - percent))), 0, (int)(0.5 + (24 * (1 - percent))), 7, (int)(0.49999999 + (24 * percent)), 7, 24);
    }

    public static void rendWeaponWorkshopCoolerPotGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(WEAPON_WORKSHOP_COOLER_POT_GUI, x, y, 0, 0, 82, 46, 82, 46);
    }

    public static void rendWeaponWorkshopGrindStoneGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(WEAPON_WORKSHOP_GRIND_STONE_GUI, x, y, 0, 0, 82, 46, 82, 46);
    }

    public static void rendDreamCauldronGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(DREAM_CAULDRON_GUI, x, y, 0, 0, 171, 93, 171, 93);
    }

    public static void rendDreamCauldronMeltDreamLiquidBar(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(DREAM_CAULDRON_MELT_DREAM_LIQUID_AMOUNT_BAR, x, y + (int)(0.5 + (47 * (1 - percent))), 0, (int)(0.5 + (47 * (1 - percent))), 5, (int)(0.49999999 + (47 * percent)), 5, 47);
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
        guiGraphics.blit(SHADOW_BLAST_FURNACE_PROCESS_BAR, x, y, 0, 0, 38, (int)(34 * percent), 38, 34);
    }

    public static void rendShadowBlastFurnaceShadowLiquidAmountBar(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(SHADOW_BLAST_FURNACE_SHADOW_LIQUID_AMOUNT_BAR, x, y + (int)(0.5 + (35 * (1 - percent))), 0, (int)(0.5 + (35 * (1 - percent))), 10, (int)(0.49999999 + (35 * percent)), 10, 35);
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
        guiGraphics.blit(MORTAR_GUI, x, y, 0, 0, 128, 90, 128, 90);
    }

    public static void rendEmptyFluidSlotBucketShape(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(EMPTY_FLUID_SLOT_BUCKET_SHAPE, x, y, 0, 0, 16, 16, 16, 16);
    }

    public static void rendDreamNotes(GuiGraphics guiGraphics, ResourceLocation dreamNotesResourceLocation, int x, int y, int width, int height, float scale)
    {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1);
        guiGraphics.blit(dreamNotesResourceLocation, (int)(x / scale), (int)(y / scale), 0, 0, width, height, width, height);
        guiGraphics.pose().popPose();
        RenderSystem.disableBlend();
    }

    public static void rendUpButton(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(UP_BUTTON, x, y, 0, 0, 15, 22, 15, 22);
    }

    public static void rendUpButtonHovered(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(UP_BUTTON_HOVERED, x, y, 0, 0, 15, 22, 15, 22);
    }

    public static void rendDownButton(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(DOWN_BUTTON, x, y, 0, 0, 15, 22, 15, 22);
    }

    public static void rendDownButtonHovered(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(DOWN_BUTTON_HOVERED, x, y, 0, 0, 15, 22, 15, 22);
    }

    public static void rendLeftButton(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(LEFT_BUTTON, x, y, 0, 0, 22, 15, 22, 15);
    }

    public static void rendLeftButtonHovered(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(LEFT_BUTTON_HOVERED, x, y, 0, 0, 22, 15, 22, 15);
    }

    public static void rendRightButton(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(RIGHT_BUTTON, x, y, 0, 0, 22, 15, 22, 15);
    }

    public static void rendRightButtonHovered(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(RIGHT_BUTTON_HOVERED, x, y, 0, 0, 22, 15, 22, 15);
    }

    public static void rendMinecraftGUIBackground(GuiGraphics guiGraphics, int x, int y, int width, int height)
    {
        guiGraphics.blit(MINECRAFT_GUI_LEFT_TOP, x, y, 0, 0, 4, 4, 4, 4);
        guiGraphics.blit(MINECRAFT_GUI_TOP, x + 4, y, 0, 0, width - 8, 4, 1, 4);
        guiGraphics.blit(MINECRAFT_GUI_RIGHT_TOP, x + width - 4, y, 0, 0, 4, 4, 4, 4);
        guiGraphics.blit(MINECRAFT_GUI_LEFT, x, y + 4, 0, 0, 4, height - 8, 4, 1);
        guiGraphics.fill(x + 4, y + 4, x + width - 4, y + height - 4, 0xFFC6C6C6);
        guiGraphics.blit(MINECRAFT_GUI_RIGHT, x + width - 4, y + 4, 0, 0, 4, height- 8, 4, 1);
        guiGraphics.blit(MINECRAFT_GUI_LEFT_BOTTOM, x, y + height - 4, 0, 0, 4, 4, 4, 4);
        guiGraphics.blit(MINECRAFT_GUI_BOTTOM, x + 4, y + height - 4, 0, 0, width - 8, 4, 1, 4);
        guiGraphics.blit(MINECRAFT_GUI_RIGHT_BOTTOM, x + width - 4, y + height - 4, 0, 0, 4, 4, 4, 4);
    }

    public static void rendDreamNotesBookGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(DREAM_NOTES_BOOK_DYEDREAM_WORLD_GUI, x, y, 0, 0, 140, 180, 140, 180);
    }

    public static void rendAaroncosHandBossBarLeftBackground(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(AARONCOS_HAND_BOSS_BAR_LEFT_BACKGROUND, x, y, 0, 0, 116, 21, 116, 21);
    }

    public static void rendAaroncosHandBossBarLeft(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(AARONCOS_HAND_BOSS_BAR_LEFT, x, y, 0, 0, (int)(106 * percent), 5, 106, 5);
    }

    public static void rendAaroncosHandBossBarRightBackground(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(AARONCOS_HAND_BOSS_BAR_RIGHT_BACKGROUND, x, y, 0, 0, 116, 21, 116, 21);
    }

    public static void rendAaroncosHandBossBarRight(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(AARONCOS_HAND_BOSS_BAR_RIGHT, x, y, 0, 0, (int)(106 * percent), 5, 106, 5);
    }

    public static void rendWindKnightBossBarEmpty(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(WIND_KNIGHT_BOSS_BAR_EMPTY, x, y, 22, 1, 196, 23, 256, 32);
    }

    public static void rendWindKnightBossBarFull(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        int w = (int) Math.round(196 * percent);
        if (w > 0)
            guiGraphics.blit(WIND_KNIGHT_BOSS_BAR_FULL, x, y, 22, 1, w, 23, 256, 32);
    }
}
