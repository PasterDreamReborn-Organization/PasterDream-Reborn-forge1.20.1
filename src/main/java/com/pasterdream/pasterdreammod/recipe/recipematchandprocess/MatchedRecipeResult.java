package com.pasterdream.pasterdreammod.recipe.recipematchandprocess;

public record MatchedRecipeResult<T extends IProcessingRecipe>(T recipe, MachineInventory matchedRecipeInputsAndOutputs) {}