package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;

/**
 * 猪灵交易（bartering）扩展的公共逻辑：货币判定与战利品表 id 约定。
 *
 * <p>货币由物品标签 {@link ModItemTags#PIGLIN_BARTERING_CURRENCY} 决定，该标签同时
 * 被嵌套进 {@code minecraft:piglin_loved}（见 {@code ModItemTagsProvider}），
 * 因此掉落在地上的货币也会被猪灵当作喜爱物品捡起、检视并交易。</p>
 *
 * <p>产物表按货币物品分流：金锭沿用原版 {@code minecraft:gameplay/piglin_bartering}，
 * 其它货币使用约定路径
 * {@code pasterdream:gameplay/piglin_bartering/<物品命名空间>/<物品路径>}；
 * 该表缺失时保留原版表的抽取结果。</p>
 */
public final class PiglinBarterHelper {

    /** 战利品表目录（数据包路径前缀） */
    private static final String TABLE_DIRECTORY = "gameplay/piglin_bartering";

    private PiglinBarterHelper() {
    }

    /**
     * 物品是否为猪灵认可的交易货币。
     */
    public static boolean isCurrency(ItemStack stack) {
        return !stack.isEmpty() && stack.is(ModItemTags.PIGLIN_BARTERING_CURRENCY);
    }

    /**
     * 返回该货币专属战利品表 id。
     *
     * @return 金锭返回 {@code null}（沿用原版表）；其它物品返回约定 id
     */
    @Nullable
    public static ResourceLocation tableFor(ItemStack stack) {
        if (stack.isEmpty() || stack.is(Items.GOLD_INGOT)) {
            return null;
        }
        return tableId(stack.getItem());
    }

    /**
     * 按「命名空间/路径」生成某物品对应的约定战利品表 id。
     */
    public static ResourceLocation tableId(Item item) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                TABLE_DIRECTORY + "/" + itemId.getNamespace() + "/" + itemId.getPath());
    }
}
