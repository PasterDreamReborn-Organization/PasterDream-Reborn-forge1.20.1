package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamDimensionTask;
import net.minecraft.resources.ResourceLocation;

public record DreamNotesDimensionInfo(ResourceLocation GUI, int color, IPasterDreamDimensionTask dimensionTasks){}
