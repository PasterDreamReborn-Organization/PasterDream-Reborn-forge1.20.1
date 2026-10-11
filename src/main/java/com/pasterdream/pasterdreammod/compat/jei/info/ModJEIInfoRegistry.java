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
    }
}
