package com.pasterdream.pasterdreammod.world.item.blueprints;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record BluePrintInfo(Component title, ResourceLocation materialNBT, ResourceLocation resultNBT) {}
