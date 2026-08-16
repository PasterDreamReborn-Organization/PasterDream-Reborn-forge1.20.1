package com.pasterdream.pasterdreammod.world.item.blueprints;

import com.pasterdream.pasterdreammod.helper.localnbtreader.LocalNBTReader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Rotation;

public class ClientBluePrintPlacement
{
    private static Vec3i size = null;
    private static String cachedContent = "";

    public static Vec3i getSize()
    {
        return size;
    }

    public static Rotation getRotationFromPlayer()
    {
        if (Minecraft.getInstance().player == null)
        {
            return Rotation.NONE;
        }

        Direction facing = Minecraft.getInstance().player.getDirection();
        return switch (facing)
        {
            case EAST  -> Rotation.NONE;
            case SOUTH -> Rotation.CLOCKWISE_90;
            case WEST  -> Rotation.CLOCKWISE_180;
            case NORTH -> Rotation.COUNTERCLOCKWISE_90;
            default    -> Rotation.NONE;
        };
    }

    public static void tick()
    {
        if (Minecraft.getInstance().player == null)
        {
            return;
        }

        ItemStack heldItem = Minecraft.getInstance().player.getMainHandItem();

        if ((heldItem.getItem() instanceof BluePrintItem))
        {
            CompoundTag tag = heldItem.getTag();
            String content = (tag != null && tag.contains("content")) ? tag.getString("content") : "";
            if (!content.equals(cachedContent))
            {
                cachedContent = content;
                loadSize(content);
            }
        }
            else
            {
                size = null;
                cachedContent = "";
            }
    }

    private static void loadSize(String content)
    {
        if (content.isEmpty())
        {
            size = null;
            return;
        }
        BluePrintInfo info = BluePrintRegistry.getInfo(content);
        if (info != null)
        {
            CompoundTag resultNBT = LocalNBTReader.getCompoundTag(info.resultNBT());
            if (resultNBT != null && resultNBT.contains("size"))
            {
                ListTag sizeTag = resultNBT.getList("size", Tag.TAG_INT);
                size = new Vec3i(sizeTag.getInt(0), sizeTag.getInt(1), sizeTag.getInt(2));
            }
                else
                {
                    size = null;
                }
        }
            else
            {
                size = null;
            }
    }

    public static void cancel()
    {
        size = null;
        cachedContent = "";
    }
}
