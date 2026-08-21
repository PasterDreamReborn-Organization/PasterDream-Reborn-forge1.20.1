package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

import java.util.function.Consumer;

/**
 * 云霞 buff：标记效果，在风之旅途持续给予，用于显示退出维度的云霞进度。
 * 实际传送由 WindDirectionHandler 的保底判定（Y<0）触发。
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
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInGui(MobEffectInstance effect) {
                return false;
            }
        });
    }
}
