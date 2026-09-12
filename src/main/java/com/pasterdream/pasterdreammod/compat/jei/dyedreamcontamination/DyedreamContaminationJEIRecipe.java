package com.pasterdream.pasterdreammod.compat.jei.dyedreamcontamination;

import com.pasterdream.pasterdreammod.world.block.portal.DyedreamContaminationRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

/** JEI 展示用的染梦侵染配方：输入方块物品列表 → 输出方块物品。 */
public class DyedreamContaminationJEIRecipe {
    private final List<ItemStack> inputs;
    private final ItemStack output;

    public DyedreamContaminationJEIRecipe(DyedreamContaminationRecipe recipe) {
        this.output = new ItemStack(recipe.getOutput().getBlock());
        this.inputs = new ArrayList<>();
        java.util.List<Block> display = recipe.getDisplayInputs();
        if (display != null) {
            // 服务端同步时已解析好的方块列表（联机/单机都走这条）。
            for (Block block : display) {
                addBlock(block);
            }
        } else if (recipe.getInputBlock() != null) {
            addBlock(recipe.getInputBlock());
        } else if (recipe.getInputTag() != null) {
            // 兜底：本地标签解析。MappedRegistry.getTag 用 IdentityHashMap 按身份查，
            // 这里遍历已绑定标签按 equals 匹配。
            BuiltInRegistries.BLOCK.getTags()
                    .filter(entry -> entry.getFirst().equals(recipe.getInputTag()))
                    .forEach(entry -> entry.getSecond().forEach(holder -> addBlock(holder.value())));
            // long boundTags = BuiltInRegistries.BLOCK.getTagNames().count();
            // int before = inputs.size();
            // LOGGER.info("[PasterDream] contamination JEI fallback tag={} boundTagCount={} added={}",
            //         recipe.getInputTag(), boundTags, inputs.size() - before);
        }
    }

    private void addBlock(Block block) {
        Item item = block.asItem();
        if (item != Items.AIR) {
            inputs.add(new ItemStack(item));
        }
    }

    public List<ItemStack> getInputs() {
        return inputs;
    }

    public ItemStack getOutput() {
        return output;
    }
}
