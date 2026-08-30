package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.helper.fluidcontainercapability.FluidContainerRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import com.mojang.logging.LogUtils;

import java.util.*;

/**
 * 容器返还平衡工具。
 * 当配方产物本身也是填充容器时，自动取消对应数量的输入容器 remainder，
 * 避免容器数量凭空增加。
 * <p>
 * 检测容器类型时优先使用 {@code craftRemainder}，其次查询 {@link FluidContainerRegistry}。
 */
public final class ContainerBalanceHelper {

    private static final org.slf4j.Logger LOGGER = LogUtils.getLogger();

    private ContainerBalanceHelper() {}

    /**
     * 获取物品对应的空容器类型。先查 craftRemainder，再查 FluidContainerRegistry。
     * 若都不是容器则返回 null。
     */
    private static Item getEmptyContainerType(ItemStack itemStack) {
        ItemStack remainder = itemStack.getItem().getCraftingRemainingItem(itemStack);
        if (!remainder.isEmpty()) {
            return remainder.getItem();
        }
        FluidContainerRegistry.ContainerEntry entry = FluidContainerRegistry.getEntryForFillToEmpty(itemStack);
        if (entry != null) {
            return entry.emptyContainerItemStack.getItem();
        }
        return null;
    }

    /**
     * 平衡 getRemainingItems() 的返回值。
     * 使用配方原料列表来确定实际消耗的数量，避免批量合成时容器中堆叠数被错误计入。
     *
     * @param container         合成台容器（用于定位被取消余料的槽位）
     * @param result            配方产物
     * @param defaultRemaining  原版/Forge 默认的 remainders
     * @param ingredients       配方的原料列表
     * @return 平衡后的 remainders
     */
    public static NonNullList<ItemStack> balance(CraftingContainer container,
                                                  ItemStack result,
                                                  NonNullList<ItemStack> defaultRemaining,
                                                  List<Ingredient> ingredients) {
        Item outputContainerType = getEmptyContainerType(result);
        if (outputContainerType == null) {
            return defaultRemaining;
        }

        int outputCount = result.getCount();

        // 基于配方原料列表统计消耗量，而非容器内全部物品的堆叠数
        int emptyInputs = 0;  // E: 原料中的空容器数量
        int filledInputs = 0; // F: 原料中 craftRemainder 返回该容器类型的数量

        for (Ingredient ingredient : ingredients) {
            ItemStack[] matching = ingredient.getItems();
            if (matching.length == 0) continue;
            ItemStack match = matching[0];
            if (match.is(outputContainerType)) {
                emptyInputs++;
            } else {
                ItemStack remainder = match.getItem().getCraftingRemainingItem(match);
                if (!remainder.isEmpty() && remainder.is(outputContainerType)) {
                    filledInputs++;
                }
            }
        }

        int cancelCount = Math.min(filledInputs, Math.max(0, outputCount - emptyInputs));

        LOGGER.info("[PasterDream] Container balance for {}x {}: E={}, F={}, O={}, cancel={}",
                outputCount, result.getHoverName().getString(),
                emptyInputs, filledInputs, outputCount, cancelCount);

        if (cancelCount < outputCount - emptyInputs) {
            LOGGER.warn("[PasterDream] Container balance IMPOSSIBLE: output needs {} containers "
                    + "but recipe only provides E={} F={} (total {})",
                    outputCount, emptyInputs, filledInputs, emptyInputs + filledInputs);
        }

        // 取消容器中前 cancelCount 个填充容器槽位的 remainder
        NonNullList<ItemStack> balanced = NonNullList.withSize(defaultRemaining.size(), ItemStack.EMPTY);
        for (int i = 0; i < defaultRemaining.size(); i++) {
            balanced.set(i, defaultRemaining.get(i).copy());
        }

        int cancelled = 0;
        for (int i = 0; i < balanced.size() && cancelled < cancelCount; i++) {
            ItemStack rem = balanced.get(i);
            if (!rem.isEmpty() && rem.is(outputContainerType)) {
                balanced.set(i, ItemStack.EMPTY);
                cancelled++;
            }
        }

        return balanced;
    }

    /**
     * 校验配方是否能配平。无法配平时抛出 IllegalStateException。
     *
     * @param recipeName 配方名称（用于错误信息）
     * @param result     配方产物
     * @param inputs     配方输入物品列表
     * @throws IllegalStateException 如果完全无法配平
     */
    public static void validateOrThrow(String recipeName, ItemStack result, ItemStack... inputs) {
        Item outputContainerType = getEmptyContainerType(result);
        if (outputContainerType == null) {
            return;
        }

        int outputCount = result.getCount();

        int emptyInputs = 0;
        int filledInputs = 0;

        for (ItemStack input : inputs) {
            if (input.isEmpty()) continue;
            if (input.is(outputContainerType)) {
                emptyInputs += input.getCount();
            }
            ItemStack remainder = input.getItem().getCraftingRemainingItem(input);
            if (!remainder.isEmpty() && remainder.is(outputContainerType)) {
                filledInputs += input.getCount();
            }
        }

        int totalContainersAvailable = emptyInputs + filledInputs;
        if (outputCount > totalContainersAvailable) {
            throw new IllegalStateException(
                    String.format("[PasterDream] Recipe '%s' container balance IMPOSSIBLE: "
                            + "output needs %d containers of type '%s' but inputs only provide %d (E=%d, F=%d)",
                            recipeName, outputCount, outputContainerType.getDescription().getString(),
                            totalContainersAvailable, emptyInputs, filledInputs));
        }
    }
}
