package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * 云雾 buff：标记效果，在风之旅途持续给予，用于显示退出维度的云雾进度。
 * 实际传送由 WindDirectionHandler 的保底判定（Y<0）触发。
 * 不产生粒子、不在 HUD/物品栏显示、无法被牛奶等物品主动移除。
 */
public class CloudMistEffect extends MobEffect {

    public CloudMistEffect() {
        super(MobEffectCategory.NEUTRAL, -2687745);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return Collections.emptyList();
    }

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInGui(MobEffectInstance effect) {
                return false;
            }

            @Override
            public boolean isVisibleInInventory(MobEffectInstance effect) {
                return false;
            }
        });
    }
}
