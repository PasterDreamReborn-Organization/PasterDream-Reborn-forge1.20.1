package com.pasterdream.pasterdreammod.world.item.dreamnotes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class NBTBookRegisterToCreativeModTab
{
    public class GardenDecryption
    {
        public static ItemStack MistyDreamingLotus()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.花园解密-迷梦冶梦莲_第1页"))));
            return buildBookNbt("花园解密-迷梦冶梦莲", "琴雨梦", bookContent);
        }

        public static ItemStack PiercingEdelweiss()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.花园解密-凛冽雪绒花_第1页"))));
            return buildBookNbt("花园解密-凛冽雪绒花", "琴雨梦", bookContent);
        }

        public static ItemStack GoldenFox()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.花园解密-九尾狐_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.花园解密-九尾狐_第2页"))));
            return buildBookNbt("花园解密-九尾狐", "琴雨梦", bookContent);
        }
    }

    public class DyedreamTravelogue
    {
        public static ItemStack DyedreamTravelogue_0()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦游记 其一_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦游记 其一_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦游记 其一_第3页"))));
            return buildBookNbt("染梦游记 其一", "琴雨梦", bookContent);
        }

        public static ItemStack DyedreamTravelogue_1()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦游记 其二_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦游记 其二_第2页"))));
            return buildBookNbt("染梦游记 其二", "琴雨梦", bookContent);
        }

        public static ItemStack DyedreamTravelogue_2()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦游记 其三_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦游记 其三_第2页"))));
            return buildBookNbt("染梦游记 其三", "琴雨梦", bookContent);
        }
    }

    public class DreamChurch
    {
        public static ItemStack DreamChurch_0()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦教堂 其一_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦教堂 其一_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦教堂 其一_第3页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦教堂 其一_第4页"))));
            return buildBookNbt("染梦教堂 其一", "琴雨梦", bookContent);
        }

        public static ItemStack DreamChurch_1()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦教堂 其二_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦教堂 其二_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦教堂 其二_第3页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦教堂 其二_第4页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦教堂 其二_第5页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦教堂 其二_第6页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦教堂 其二_第7页"))));
            return buildBookNbt("染梦教堂 其二", "夜岚", bookContent);
        }

        public static ItemStack DreamChurch_2()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦教堂 其三_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦教堂 其三_第2页"))));
            return buildBookNbt("染梦教堂 其三", "卡莱", bookContent);
        }
    }

    public class DyedreamWorld
    {
        public static ItemStack CrystalBall()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦水晶球_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦水晶球_第2页"))));
            return buildBookNbt("染梦水晶球", "琴雨梦", bookContent);
        }

        public static ItemStack DreamWishingTree()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.祈愿树_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.祈愿树_第2页"))));
            return buildBookNbt("祈愿树", "琴雨梦", bookContent);
        }

        public static ItemStack WarmColdWind()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.温暖的“寒风”_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.温暖的“寒风”_第2页"))));
            return buildBookNbt("温暖的“寒风”", "琴雨梦", bookContent);
        }

        public static ItemStack PinkAgalicHouse()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.粉顶蘑菇屋_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.粉顶蘑菇屋_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.粉顶蘑菇屋_第3页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.粉顶蘑菇屋_第4页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.粉顶蘑菇屋_第5页"))));
            return buildBookNbt("粉顶蘑菇屋", "琴雨梦", bookContent);
        }

        public static ItemStack MeltDreamLiquidWell()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.融梦涌泉井_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.融梦涌泉井_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.融梦涌泉井_第3页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.融梦涌泉井_第4页"))));
            return buildBookNbt("融梦涌泉井", "琴雨梦", bookContent);
        }

        public static ItemStack FloatingIslandDiary()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.浮空岛日记_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.浮空岛日记_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.浮空岛日记_第3页"))));
            return buildBookNbt("浮空岛日记", "琴雨梦", bookContent);
        }

        public static ItemStack FallingLeavesReturnToTheirRoots_CrackedPodsReturnToTheirCrowns()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.落叶归根 裂荚归冠_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.落叶归根 裂荚归冠_第2页"))));
            return buildBookNbt("落叶归根 裂荚归冠", "琴雨梦", bookContent);
        }

        public static ItemStack DreamFloating()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.梦境漂泊_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.梦境漂泊_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.梦境漂泊_第3页"))));
            return buildBookNbt("梦境漂泊", "琴雨梦", bookContent);
        }

        public static ItemStack BigBubble()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.气泡生态球_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.气泡生态球_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.气泡生态球_第3页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.气泡生态球_第4页"))));
            return buildBookNbt("气泡生态球", "千夜", bookContent);
        }

        public static ItemStack DreamLaboratory()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦实验室_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦实验室_第2页"))));
            return buildBookNbt("染梦实验室", "彗星", bookContent);
        }

        public static ItemStack InteractingWithDream()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.来往于梦_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.来往于梦_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.来往于梦_第3页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.来往于梦_第4页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.来往于梦_第5页"))));
            return buildBookNbt("来往于梦", "流华", bookContent);
        }

        public static ItemStack DyedreamWorldTree()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦世界树_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦世界树_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦世界树_第3页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦世界树_第4页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.染梦世界树_第5页"))));
            return buildBookNbt("染梦世界树", "极星", bookContent);
        }

        public static ItemStack WingsOfDaiDaLuoSiAndFloatingIsland()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.代达罗斯之翼与浮空岛_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.代达罗斯之翼与浮空岛_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.代达罗斯之翼与浮空岛_第3页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.代达罗斯之翼与浮空岛_第4页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.代达罗斯之翼与浮空岛_第5页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.代达罗斯之翼与浮空岛_第6页"))));
            return buildBookNbt("代达罗斯之翼与浮空岛", "极星", bookContent);
        }
    }

    public class OverWorld
    {
        public static ItemStack DesertCottage()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.探求秘辛_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.探求秘辛_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.探求秘辛_第3页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.探求秘辛_第4页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.探求秘辛_第5页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.探求秘辛_第6页"))));
            return buildBookNbt("探求秘辛", "极星", bookContent);
        }
    }

    public class lampShadowWorld
    {
        public static ItemStack ResearchNotes_BlackMetal()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.研究笔记-黑金属_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.研究笔记-黑金属_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.研究笔记-黑金属_第3页"))));
            return buildBookNbt("研究笔记-黑金属", "琴雨梦", bookContent);
        }

        public static ItemStack LampShadowTravelogue_1()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.灯影游记 其二_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.灯影游记 其二_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.灯影游记 其二_第3页"))));
            return buildBookNbt("灯影游记 其二", "琴雨梦", bookContent);
        }

        public static ItemStack ShadowPlace()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.黑暗之地_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.黑暗之地_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.黑暗之地_第3页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.黑暗之地_第4页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.黑暗之地_第5页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.黑暗之地_第6页"))));
            return buildBookNbt("黑暗之地", "琴雨梦", bookContent);
        }

        public static ItemStack ShadowFungusHouse()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.阴影小木屋_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.阴影小木屋_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.阴影小木屋_第3页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.阴影小木屋_第4页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.阴影小木屋_第5页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.阴影小木屋_第6页"))));
            return buildBookNbt("阴影小木屋", "极星", bookContent);
        }

        public static ItemStack ShadowUndergroundWorkroom()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.阴影地下工作室_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.阴影地下工作室_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.阴影地下工作室_第3页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.阴影地下工作室_第4页"))));
            return buildBookNbt("阴影地下工作室", "极星", bookContent);
        }

        public static ItemStack ShadowDyedSloppyHandwritingNotes()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.>被阴影浸染的字迹潦草的笔记<_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.>被阴影浸染的字迹潦草的笔记<_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.>被阴影浸染的字迹潦草的笔记<_第3页"))));
            return buildBookNbt(">被阴影浸染的字迹潦草的笔记<", ">作者名字模糊不清<", bookContent);
        }

        public static ItemStack RegardingShadowHandsEssay()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.关于黑暗之手的随笔_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.关于黑暗之手的随笔_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.关于黑暗之手的随笔_第3页"))));
            return buildBookNbt("关于黑暗之手的随笔", "极星", bookContent);
        }

        public static ItemStack DecisiveBattleWithShadowHandsAndFail()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.与黑色双手的决战和败北_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.与黑色双手的决战和败北_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.与黑色双手的决战和败北_第3页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.与黑色双手的决战和败北_第4页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.与黑色双手的决战和败北_第5页"))));
            return buildBookNbt("与黑色双手的决战和败北", "千夜", bookContent);
        }
    }

    public class WindJourneyWorld
    {
        public static ItemStack WindBreakKnight()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.破风的骑士_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.破风的骑士_第2页"))));
            return buildBookNbt("破风的骑士", "琴雨梦", bookContent);
        }

        public static ItemStack MorningNewWind()
        {
            List<StringTag> bookContent = new ArrayList<>();
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.清晨的新风_第1页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.清晨的新风_第2页"))));
            bookContent.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("book.pasterdream.清晨的新风_第3页"))));
            return buildBookNbt("清晨的新风", "琴雨梦", bookContent);
        }
    }

    public static ItemStack buildBookNbt(String title, String author, List<StringTag> bookContent)
    {
        ItemStack itemStack = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag nbt = new CompoundTag();
        nbt.putString("author", author);
        ListTag pages = new ListTag();
        for(StringTag singlePageContent : bookContent)
        {
            pages.add(singlePageContent);
        }
        nbt.put("pages", pages);
        nbt.putByte("resolved", (byte)1);
        nbt.putString("title", title);
        itemStack.setTag(nbt);
        return itemStack;
    }
}
