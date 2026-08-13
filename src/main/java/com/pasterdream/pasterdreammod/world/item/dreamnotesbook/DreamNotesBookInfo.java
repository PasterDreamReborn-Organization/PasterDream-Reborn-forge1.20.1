package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record DreamNotesBookInfo(Component title, String author, Component content, ResourceLocation GUI, int GUI_X, int GUI_Y, int contentStartX, int contentStartY, int contentFinalX, int contentFinalY, ResourceLocation itemTexture, int color) {}
