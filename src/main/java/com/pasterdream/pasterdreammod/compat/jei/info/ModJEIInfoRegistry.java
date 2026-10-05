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
    }
}
