package com.pasterdream.pasterdreammod.helper.recipematchresult;

import com.pasterdream.pasterdreammod.recipe.dreamcauldron.DreamCauldronRecipe;

public record RecipeMatchResult(DreamCauldronRecipe recipe, int[] fluidSlotMap, int[] itemSlotMap)
{
    //fluidSlotMap[i] : 第i个流体输入对应的流体槽索引
    //itemSlotMap[j] : 第j个物品输入对应的物品槽索引
}
