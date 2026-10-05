package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEnchantment;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 融梦修补附魔书的村民交易价格修正。
 * <p>
 * 原版 {@code VillagerTrades$EnchantBookForEmeralds} 的价格公式仅由附魔等级与宝藏标记决定，
 * 完全不读 {@link Enchantment#getMinCost(int)} / {@link Enchantment#getMaxCost(int)}，
 * 单级融梦修补只能刷出 [10, 38] 绿宝石。此处仅在 RETURN 处替换该附魔书的 offer 价格，
 * 将基础价改为 [34, 64]，其余附魔书保持原版公式不变。
 * <p>
 * 使用 {@code targets} 字符串定位，因为 {@code EnchantBookForEmeralds} 为包私有嵌套类，
 * 外部包无法直接引用。采用 {@code @Inject(RETURN)} 而非 {@code @Overwrite}，
 * 以兼容其它模组对同一方法的注入。
 */
@Mixin(targets = "net.minecraft.world.entity.npc.VillagerTrades$EnchantBookForEmeralds")
public class EnchantBookForEmeraldsMixin {

    @Inject(method = "getOffer", at = @At("RETURN"), cancellable = true)
    private void pasterdream$setMeltDreamRepairBookPrice(Entity entity, RandomSource random,
                                                         CallbackInfoReturnable<MerchantOffer> cir) {
        MerchantOffer offer = cir.getReturnValue();
        if (offer == null) return;

        Enchantment target = ModEnchantment.MELT_DREAM_REPAIR_ENCHANTMENT.get();
        if (!EnchantmentHelper.getEnchantments(offer.getResult()).containsKey(target)) return;

        ItemStack emerald = offer.getBaseCostA().copy();
        emerald.setCount(34 + random.nextInt(31));
        cir.setReturnValue(new MerchantOffer(emerald, offer.getCostB(), offer.getResult(),
                offer.getMaxUses(), offer.getXp(), offer.getPriceMultiplier()));
    }
}
