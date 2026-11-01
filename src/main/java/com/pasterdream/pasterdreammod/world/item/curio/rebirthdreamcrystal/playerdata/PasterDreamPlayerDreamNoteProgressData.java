package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata;

import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookProgressRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PasterDreamPlayerDreamNoteProgressData
{
    public static ItemStack getItem(Player player)
    {
        CompoundTag persistentData = player.getPersistentData();
        if(persistentData.contains("pasterdream_player_dream_note_progress_data", Tag.TAG_COMPOUND))
        {
            CompoundTag compoundTag = persistentData.getCompound("pasterdream_player_dream_note_progress_data");
            if(compoundTag.contains("uploadSlot"))
            {
                return ItemStack.of(compoundTag.getCompound("uploadSlot"));
            }
        }

        return ItemStack.EMPTY;
    }

    public static void setItem(Player player, ItemStack itemStack)
    {
        CompoundTag persistentData = player.getPersistentData();

        CompoundTag pasterDreamPlayerDreamNoteProgressData;
        if(persistentData.contains("pasterdream_player_dream_note_progress_data"))
        {
            pasterDreamPlayerDreamNoteProgressData = persistentData.getCompound("pasterdream_player_dream_note_progress_data");
        }
            else
            {
                pasterDreamPlayerDreamNoteProgressData = new CompoundTag();
                persistentData.put("pasterdream_player_dream_note_progress_data", pasterDreamPlayerDreamNoteProgressData);
            }

        CompoundTag itemCompoundTag = itemStack.isEmpty() ? new CompoundTag() : itemStack.save(new CompoundTag());
        pasterDreamPlayerDreamNoteProgressData.put("uploadSlot", itemCompoundTag);
    }

    public static List<Boolean> getProgressData(Player player, String dimension)
    {
        CompoundTag persistentData = player.getPersistentData();
        List<String> singleDimensionBooks = DreamNotesBookProgressRegistry.getNotesByDimension(dimension);
        List<Boolean> progressData = new ArrayList<>();

        if(persistentData.contains("pasterdream_player_dream_note_progress_data", Tag.TAG_COMPOUND))
        {
            CompoundTag compoundTag = persistentData.getCompound("pasterdream_player_dream_note_progress_data");
            if(compoundTag.contains("progress"))
            {
                ListTag progress = compoundTag.getList("progress", Tag.TAG_COMPOUND);

                for(int i = 0; i < progress.size(); i++)
                {
                    CompoundTag singleDimension = progress.getCompound(i);
                    if(singleDimension.getString("dimension").equals(dimension))
                    {
                        ListTag singleDimensionProgress = singleDimension.getList("single_dimension_progress", Tag.TAG_COMPOUND);
                        for(String contentName : singleDimensionBooks)
                        {
                            boolean singleProgress = false;
                            for(int j = 0; j < singleDimensionProgress.size(); j++)
                            {
                                CompoundTag singleProgressCompoundTag = singleDimensionProgress.getCompound(j);

                                if(singleProgressCompoundTag.getString("name").equals(contentName))
                                {
                                    singleProgress = singleProgressCompoundTag.getBoolean("is_read");
                                    break;
                                }
                            }
                            progressData.add(singleProgress);
                        }
                        return progressData;
                    }
                }
            }
        }

        for(String contentName : singleDimensionBooks)
        {
            progressData.add(false);
            return progressData;
        }
        return List.of();
    }

    public static void setProgressData(Player player, String dimension, String dreamNotesName, boolean isRead)
    {
        CompoundTag persistentData = player.getPersistentData();

        CompoundTag pasterDreamPlayerDreamNoteProgressData;
        if(persistentData.contains("pasterdream_player_dream_note_progress_data"))
        {
            pasterDreamPlayerDreamNoteProgressData = persistentData.getCompound("pasterdream_player_dream_note_progress_data");
        }
            else
            {
                pasterDreamPlayerDreamNoteProgressData = new CompoundTag();
                persistentData.put("pasterdream_player_dream_note_progress_data", pasterDreamPlayerDreamNoteProgressData);
            }

        ListTag progress;
        if(pasterDreamPlayerDreamNoteProgressData.contains("progress"))
        {
            progress = pasterDreamPlayerDreamNoteProgressData.getList("progress", Tag.TAG_COMPOUND);
        }
            else
            {
                progress = new ListTag();
                pasterDreamPlayerDreamNoteProgressData.put("progress", progress);
            }

        CompoundTag singleDimension = null;
        for(int i = 0; i < progress.size(); i++)
        {
            CompoundTag singleDimensionBuffer = progress.getCompound(i);
            if(singleDimensionBuffer.contains("dimension") && singleDimensionBuffer.getString("dimension").equals(dimension))
            {
                singleDimension = singleDimensionBuffer;
                break;
            }
        }

        if(singleDimension == null)
        {
            singleDimension = new CompoundTag();
            singleDimension.putString("dimension", dimension);
            progress.add(singleDimension);
        }

        ListTag singleDimensionProgress;
        if(singleDimension.contains("single_dimension_progress"))
        {
            singleDimensionProgress = singleDimension.getList("single_dimension_progress", Tag.TAG_COMPOUND);
        }
            else
            {
                singleDimensionProgress = new ListTag();
                singleDimension.put("single_dimension_progress", singleDimensionProgress);
            }

        CompoundTag singleProgress = null;
        for(int i = 0; i < singleDimensionProgress.size(); i++)
        {
            CompoundTag singleProgressBuffer = singleDimensionProgress.getCompound(i);
            if(singleProgressBuffer.contains("name") && singleProgressBuffer.getString("name").equals(dreamNotesName))
            {
                singleProgress = singleProgressBuffer;
                break;
            }
        }

        if(singleProgress == null)
        {
            singleProgress = new CompoundTag();
            singleProgress.putString("name", dreamNotesName);
            singleDimensionProgress.add(singleProgress);
        }

        singleProgress.putBoolean("is_read", isRead);
    }
}
