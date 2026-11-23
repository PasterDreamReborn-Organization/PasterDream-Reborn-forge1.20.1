package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.helper.PiglinBarterHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

/**
 * 扩展猪灵交易：支持自定义货币（{@code pasterdream:piglin_bartering_currency} 标签）
 * 与每种货币独立的战利品表。
 *
 * <p>原版链路的问题：</p>
 * <ul>
 *   <li>{@code PiglinAi.canAdmire} 依赖私有的 {@code isBarterCurrency}，只认金锭；</li>
 *   <li>{@code stopHoldingOffHandItem} 在判定前清空副手，
 *       且掉落物写死 {@code BuiltInLootTables.PIGLIN_BARTERING}。</li>
 * </ul>
 *
 * <p>这里不碰 {@code isBarterCurrency}：直接在 {@code canAdmire} 头部放开自定义货币，
 * 在 {@code stopHoldingOffHandItem} 头部接管「自定义货币（非金锭）」的交易，
 * 按货币约定的表抽取并抛出产物。金锭与非交易场景完全交给原版逻辑。</p>
 *
 * @see PiglinBarterHelper
 */
@Mixin(PiglinAi.class)
public abstract class PiglinBarterMixin {

    /**
     * 让自定义货币同样能被右键检视（原版 {@code canAdmire} 只认金锭）。
     */
    @Inject(method = "canAdmire", at = @At("HEAD"), cancellable = true)
    private static void pasterdream$canAdmireCustomCurrency(Piglin piglin, ItemStack stack,
                                                            CallbackInfoReturnable<Boolean> cir) {
        if (!PiglinBarterHelper.isCurrency(stack) || !piglin.isAdult()) {
            return;
        }
        if (piglin.getBrain().hasMemoryValue(MemoryModuleType.ADMIRING_ITEM)
                || piglin.getBrain().hasMemoryValue(MemoryModuleType.ADMIRING_DISABLED)) {
            return;
        }
        cir.setReturnValue(true);
    }

    /**
     * 接管自定义货币的交易：清空副手、按货币表抽取产物并抛给玩家。
     * 金锭走原版分支，未配置表的货币退回原版（物品进猪灵背包）。
     */
    @Inject(method = "stopHoldingOffHandItem", at = @At("HEAD"), cancellable = true)
    private static void pasterdream$barterWithCustomCurrency(Piglin piglin, boolean barter, CallbackInfo ci) {
        ItemStack currency = piglin.getOffhandItem();
        if (!barter || currency.isEmpty() || !PiglinBarterHelper.isCurrency(currency)
                || currency.is(Items.GOLD_INGOT) || !piglin.isAdult()) {
            return;
        }

        List<ItemStack> loot = pasterdream$rollBarterLoot(currency, piglin);
        if (loot == null) {
            return;
        }

        piglin.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        pasterdream$throwItems(piglin, loot);
        ci.cancel();
    }

    /**
     * 按货币约定的战利品表抽取产物。
     *
     * @return 抽取结果；表缺失/为空时返回 {@code null}，由调用方退回原版逻辑
     */
    private static List<ItemStack> pasterdream$rollBarterLoot(ItemStack currency, Piglin piglin) {
        ResourceLocation tableId = PiglinBarterHelper.tableFor(currency);
        if (tableId == null) {
            return null;
        }
        if (!(piglin.level() instanceof ServerLevel serverLevel) || serverLevel.getServer() == null) {
            return null;
        }

        LootTable table = serverLevel.getServer().getLootData().getLootTable(tableId);
        if (table == LootTable.EMPTY) {
            return null;
        }

        List<ItemStack> result = table.getRandomItems(new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.THIS_ENTITY, piglin)
                .create(LootContextParamSets.PIGLIN_BARTER));
        return result.isEmpty() ? null : result;
    }

    /**
     * 等价于原版私有的 {@code PiglinAi.throwItems}：优先抛向最近的可见玩家，否则抛向自身位置。
     */
    private static void pasterdream$throwItems(Piglin piglin, List<ItemStack> stacks) {
        if (stacks.isEmpty()) {
            return;
        }
        Optional<Player> player = piglin.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        Vec3 target = player.map(Player::position).orElse(piglin.position()).add(0.0, 1.0, 0.0);
        piglin.swing(InteractionHand.OFF_HAND);
        for (ItemStack stack : stacks) {
            BehaviorUtils.throwItem(piglin, stack, target);
        }
    }
}
