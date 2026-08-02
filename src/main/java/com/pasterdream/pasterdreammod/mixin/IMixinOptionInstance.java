package com.pasterdream.pasterdreammod.mixin;

import net.minecraft.client.OptionInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(OptionInstance.class)
public interface IMixinOptionInstance<T> {
    @Accessor("value")
    void pasterdream_setValueWithoutCheck(T value);

    @Accessor("value")
    T pasterdream_getValue();
}
