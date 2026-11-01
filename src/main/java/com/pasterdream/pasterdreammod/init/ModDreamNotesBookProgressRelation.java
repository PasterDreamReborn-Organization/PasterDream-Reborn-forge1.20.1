package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookProgressRegistry;

public class ModDreamNotesBookProgressRelation
{
    public static void registerDreamNotesBookProgressRelation()
    {
        String OVERWORLD = "overworld";
        String DYEDREAM_WORLD = "dyedream_world";
        String LAMP_SHADOW_WORLD = "lamp_shadow_world";
        String WIND_JOURNEY_WORLD = "wind_journey_world";

        DreamNotesBookProgressRegistry.dimensionListRegister(OVERWORLD);
        DreamNotesBookProgressRegistry.dimensionListRegister(DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.dimensionListRegister(LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.dimensionListRegister(WIND_JOURNEY_WORLD);

        DreamNotesBookProgressRegistry.dimensionGUIRegister(OVERWORLD, GUIBackGroundRender.DREAM_NOTES_BOOK_OVERWORLD_GUI);
        DreamNotesBookProgressRegistry.dimensionGUIRegister(DYEDREAM_WORLD, GUIBackGroundRender.DREAM_NOTES_BOOK_DYEDREAM_WORLD_GUI);
        DreamNotesBookProgressRegistry.dimensionGUIRegister(LAMP_SHADOW_WORLD, GUIBackGroundRender.DREAM_NOTES_BOOK_LAMP_SHADOW_WORLD_GUI);
        DreamNotesBookProgressRegistry.dimensionGUIRegister(WIND_JOURNEY_WORLD, GUIBackGroundRender.DREAM_NOTES_BOOK_WIND_JOURNEY_WORLD_GUI);

        DreamNotesBookProgressRegistry.notesDimensionRegister("探求秘辛", OVERWORLD);

        DreamNotesBookProgressRegistry.notesDimensionRegister("花园解密:迷梦冶梦莲", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("花园解密:凛冽雪绒花", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("花园解密:九尾狐", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("染梦裂隙", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("染梦游记 其一", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("染梦游记 其二", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("染梦游记 其三", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("染梦教堂 其一", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("染梦教堂 其二", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("染梦教堂 其三", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("染梦水晶球", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("祈愿树", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("温暖的“寒风”", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("粉顶蘑菇屋", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("融梦涌泉井", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("浮空岛日记", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("落叶归根 裂荚归冠", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("梦境漂泊", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("气泡生态球", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("染梦实验室", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("来往于梦", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("染梦世界树", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("代达罗斯之翼与浮空岛", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("星河果冻和浮空岛", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("融梦釜与融梦炼金术", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("魔法使的记录 其一", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("关于美梦世界的研究", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("染梦世界跃迁石", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("梦境世界的本质", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("苍白雪莲与苍白骨针", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("逐梦列车", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("灵魂石的现世", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("灵魂石的现世（假）", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("魔法使建家记", DYEDREAM_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("侵染教堂-亮面", DYEDREAM_WORLD);

        DreamNotesBookProgressRegistry.notesDimensionRegister("研究笔记:黑金属", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("灯影游记 其二", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("黑暗之地", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("阴影小木屋", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("阴影地下工作室", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("-被阴影浸染的字迹潦草的笔记-", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("关于黑暗之手的随笔", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("与黑色双手的决战和败北", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("侵染教堂-黑面", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("暮影之笼", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("暗影图书馆", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("沉淀阴影", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("灯影游记 其一", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("暗影地牢", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("暗影地牢入口", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("欺诈", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("交易", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("破碎", LAMP_SHADOW_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("魔法使的记录 其三", LAMP_SHADOW_WORLD);

        DreamNotesBookProgressRegistry.notesDimensionRegister("无翼鸟也有展翅的梦", WIND_JOURNEY_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("破风的骑士", WIND_JOURNEY_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("清晨的新风", WIND_JOURNEY_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("魔法使的记录 其二", WIND_JOURNEY_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("空岛的圣诞树", WIND_JOURNEY_WORLD);
        DreamNotesBookProgressRegistry.notesDimensionRegister("风铃笼", WIND_JOURNEY_WORLD);
    }
}
