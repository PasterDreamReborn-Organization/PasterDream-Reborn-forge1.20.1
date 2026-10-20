package com.pasterdream.pasterdreammod.compat.jei.info;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModItems;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

/**
 * 集中登记 JEI 信息页（玩家对物品按 R/U 查看描述）。
 * 新增信息页只需在此加一条 addItemStackInfo / addIngredientInfo。
 */
public final class ModJEIInfoRegistry
{
    private ModJEIInfoRegistry()
    {
    }

    public static void register(IRecipeRegistration registration)
    {
        // 『天丛云』草薙：使用草薙击杀 N 个生物进化获得
        registration.addItemStackInfo(
                new ItemStack(ModItems.MURAKUMO_KUSANAGI.get()),
                Component.translatable("jei.pasterdream.murakumo_kusanagi.info",
                        Config.TheNumberofKillEnemytoEvolve));
        // 脉冲风行者水晶：由破风骑士掉落
        registration.addItemStackInfo(
                new ItemStack(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get()),
                Component.translatable("jei.pasterdream.pulse_wind_runner_crystal.info"));
        // 折翼天使雕像：由破风骑士概率掉落
        registration.addItemStackInfo(
                new ItemStack(ModItems.FRACTURED_ANGEL_STATUE.get()),
                Component.translatable("jei.pasterdream.fractured_angel_statue.info"));
        // 失落之剑：携带力量II的玩家从失落剑冢拔出
        registration.addItemStackInfo(
                new ItemStack(ModItems.SWORD_EMBRYO.get()),
                Component.translatable("jei.pasterdream.sword_embryo.info"));
        // 失落剑冢：可在丛林中发现
        registration.addItemStackInfo(
                new ItemStack(ModItems.LOST_SWORD_TOMB.get()),
                Component.translatable("jei.pasterdream.lost_sword_tomb.info"));
        // 朔漠大剑：接取荒漠英雄的三个任务即可获得
        registration.addItemStackInfo(
                new ItemStack(ModItems.DESERT_SWORD.get()),
                Component.translatable("jei.pasterdream.desert_sword.info"));
        // 『沉荆门』朔漠：完成荒漠英雄的三个任务后由朔漠大剑升级
        registration.addItemStackInfo(
                new ItemStack(ModItems.CHENJINGMEN_DESERT_SWORD.get()),
                Component.translatable("jei.pasterdream.chenjingmen_desert_sword.info"));
        // 断裂英雄剑：开启融梦水晶箱有概率获得
        registration.addItemStackInfo(
                new ItemStack(ModItems.BROKEN_HERO_SWORD.get()),
                Component.translatable("jei.pasterdream.broken_hero_sword.info"));
        // 雪下的誓言：开启融梦水晶箱或圣诞树岛开箱获取
        registration.addItemStackInfo(
                new ItemStack(ModItems.SNOW_VOW_HEAD.get()),
                Component.translatable("jei.pasterdream.snow_vow_head.info"));
        // 怀中御守：与金狐狸交易获得
        registration.addItemStackInfo(
                new ItemStack(ModItems.KAICHU_OMAMORI.get()),
                Component.translatable("jei.pasterdream.kaichu_omamori.info"));
    }
}
