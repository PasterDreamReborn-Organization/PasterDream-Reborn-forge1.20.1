package com.pasterdream.pasterdreammod.command.san;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * 持久化低 San 视觉效果的三个开关（overlay / jitter / sound）。
 * 以世界为单位存储，确保重进存档后配置不丢失。
 */
public class LowSanWorldData extends SavedData {

    private static final String NAME = "pasterdream_lowsan";
    private static final String KEY_OVERLAY = "overlay";
    private static final String KEY_JITTER = "jitter";
    private static final String KEY_SOUND = "sound";

    private boolean overlay = true;
    private boolean jitter = true;
    private boolean sound = true;

    public boolean overlay() { return overlay; }
    public boolean jitter()  { return jitter; }
    public boolean sound()   { return sound; }

    public void setOverlay(boolean v) { overlay = v; setDirty(); }
    public void setJitter(boolean v)  { jitter  = v; setDirty(); }
    public void setSound(boolean v)   { sound   = v; setDirty(); }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putBoolean(KEY_OVERLAY, overlay);
        tag.putBoolean(KEY_JITTER, jitter);
        tag.putBoolean(KEY_SOUND, sound);
        return tag;
    }

    public static LowSanWorldData load(CompoundTag tag) {
        LowSanWorldData data = new LowSanWorldData();
        if (tag.contains(KEY_OVERLAY)) data.overlay = tag.getBoolean(KEY_OVERLAY);
        if (tag.contains(KEY_JITTER))  data.jitter  = tag.getBoolean(KEY_JITTER);
        if (tag.contains(KEY_SOUND))   data.sound   = tag.getBoolean(KEY_SOUND);
        return data;
    }

    public static LowSanWorldData get(ServerLevel overworld) {
        return overworld.getDataStorage().computeIfAbsent(
                LowSanWorldData::load, LowSanWorldData::new, NAME);
    }
}
