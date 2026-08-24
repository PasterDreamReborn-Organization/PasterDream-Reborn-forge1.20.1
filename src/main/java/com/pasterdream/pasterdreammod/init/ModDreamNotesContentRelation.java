package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.world.item.dreamnotes.DreamNotesRegistry;

public class ModDreamNotesContentRelation
{
    public static void registerDreamNotesContentRelation()
    {
        DreamNotesRegistry.register("developerNameList", 786, 1024, GUIBackGroundRender.DEVELOPER_NAME_LIST);
        DreamNotesRegistry.register("dyedreamCreak", 786, 1024, GUIBackGroundRender.DYEDREAM_CRACK);
        DreamNotesRegistry.register("dyedreamWorld", 786, 1024, GUIBackGroundRender.DYEDREAM_WORLD);
        DreamNotesRegistry.register("pinkSlime", 768, 1024, GUIBackGroundRender.PINK_SLIME);
        DreamNotesRegistry.register("whiteCorolla", 768, 1024, GUIBackGroundRender.WHITE_COROLLA);
        DreamNotesRegistry.register("paleBoneNeedle", 768, 1024, GUIBackGroundRender.PALE_BONE_NEEDLE);
        DreamNotesRegistry.register("dreamFertilizer", 768, 1024, GUIBackGroundRender.DREAM_FERTILIZER);
        DreamNotesRegistry.register("dreamAccumulator", 768, 1024, GUIBackGroundRender.DREAM_ACCUMULATOR);

        DreamNotesRegistry.register("theLurkersInTheShadow", 786, 1024, GUIBackGroundRender.THE_LURKERS_IN_THE_SHADOW);
        DreamNotesRegistry.register("infestedChurch", 786, 1024, GUIBackGroundRender.INFESTED_CHURCH);
        DreamNotesRegistry.register("depositionShadow", 786, 1024, GUIBackGroundRender.DEPOSITION_SHADOW);
        DreamNotesRegistry.register("lampShadowTravelogue1", 786, 1024, GUIBackGroundRender.LAMP_SHADOW_TRAVELOGUE_1);
        DreamNotesRegistry.register("shadowDungeon", 786, 1024, GUIBackGroundRender.SHADOW_DUNGEON);
        DreamNotesRegistry.register("scare", 786, 1024, GUIBackGroundRender.SCARE);

        DreamNotesRegistry.register("notHaveWingsBirdHaveSpreadWingsDreamToo", 786, 1024, GUIBackGroundRender.NOT_HAVE_WINGS_BIRD_HAVE_SPREAD_WINGS_DREAM_TOO);
    }
}
