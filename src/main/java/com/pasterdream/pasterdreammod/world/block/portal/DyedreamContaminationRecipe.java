package com.pasterdream.pasterdreammod.world.block.portal;

import com.pasterdream.pasterdreammod.init.ModRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * 染梦侵染配方：染梦传送门随机刻时把 {@code from}（方块或方块标签）转换为 {@code to} 方块状态。
 * <p>
 * JSON 示例：
 * <pre>
 * {
 *   "type": "pasterdream:dyedream_contamination",
 *   "from": "#pasterdream:dyedream_contamination_to_dirt",
 *   "to": "pasterdream:dyedream_dirt"
 * }
 * </pre>
 */
public class DyedreamContaminationRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    @Nullable
    private final TagKey<Block> inputTag;
    @Nullable
    private final Block inputBlock;
    private final BlockState output;
    /** 客户端 JEI 展示用：服务端同步时已把标签解析为具体方块列表。 */
    @Nullable
    private final java.util.List<Block> displayInputs;

    public DyedreamContaminationRecipe(ResourceLocation id, @Nullable TagKey<Block> inputTag,
                                       @Nullable Block inputBlock, BlockState output) {
        this(id, inputTag, inputBlock, output, null);
    }

    public DyedreamContaminationRecipe(ResourceLocation id, @Nullable TagKey<Block> inputTag,
                                       @Nullable Block inputBlock, BlockState output,
                                       @Nullable java.util.List<Block> displayInputs) {
        this.id = id;
        this.inputTag = inputTag;
        this.inputBlock = inputBlock;
        this.output = output;
        this.displayInputs = displayInputs;
    }

    @Nullable
    public TagKey<Block> getInputTag() {
        return inputTag;
    }

    @Nullable
    public Block getInputBlock() {
        return inputBlock;
    }

    @Nullable
    public java.util.List<Block> getDisplayInputs() {
        return displayInputs;
    }

    public BlockState getOutput() {
        return output;
    }

    public boolean matches(BlockState state) {
        if (inputTag != null) {
            return state.is(inputTag);
        }
        return inputBlock != null && state.is(inputBlock);
    }

    /** 输出方块若带积雪属性，则继承来源方块的积雪状态。 */
    public BlockState getOutputState(BlockState input) {
        BlockState result = output;
        if (result.hasProperty(SnowyDirtBlock.SNOWY) && input.hasProperty(SnowyDirtBlock.SNOWY)) {
            result = result.setValue(SnowyDirtBlock.SNOWY, input.getValue(SnowyDirtBlock.SNOWY));
        }
        return result;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.DYEDREAM_CONTAMINATION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.DYEDREAM_CONTAMINATION.get();
    }
}
