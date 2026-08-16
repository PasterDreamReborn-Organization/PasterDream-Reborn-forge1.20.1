package com.pasterdream.pasterdreammod.helper.blueprint;

import com.pasterdream.pasterdreammod.world.item.blueprints.BluePrintItem;
import com.pasterdream.pasterdreammod.world.item.blueprints.BluePrintNBTSerializer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.nbt.CompoundTag;

import java.util.*;

public class BlueprintPlacementManager
{
    private record PlacementData(CompoundTag materialNBT, CompoundTag resultNBT) {}
    private static final Map<UUID, PlacementData> ACTIVE_PLACEMENTS = new HashMap<>();
    private static Map<Item, Integer> requiredCounts;

    public static void startPlacement(ServerPlayer player, CompoundTag materialNBT, CompoundTag resultNBT)
    {
        ACTIVE_PLACEMENTS.put(player.getUUID(), new PlacementData(materialNBT, resultNBT));
    }

    public static void tryPlace(ServerPlayer player, BlockPos targetPos)
    {
        PlacementData data = ACTIVE_PLACEMENTS.remove(player.getUUID());
        if (data == null)
        {
            return;
        }

        CompoundTag materialNBT = data.materialNBT();
        CompoundTag resultNBT = data.resultNBT();

        if (!player.isCreative())
        {
            if (!hasRequiredMaterials(player, materialNBT))
            {
                player.displayClientMessage(Component.translatable("message.pasterdream.材料不足"), true);
                return;
            }
        }

        Direction facing = player.getDirection();
        Rotation rotation = switch (facing)
        {
            case EAST  -> Rotation.NONE;
            case SOUTH -> Rotation.CLOCKWISE_90;
            case WEST  -> Rotation.CLOCKWISE_180;
            case NORTH -> Rotation.COUNTERCLOCKWISE_90;
            default    -> Rotation.NONE;
        };

        boolean placed = placeResultStructure(player.serverLevel(), targetPos.above(), resultNBT, rotation);
        if (placed)
        {
            if (!player.isCreative())
            {
                consumeMaterials(player, materialNBT);
            }
            player.displayClientMessage(Component.translatable("message.pasterdream.已放置蓝图"), true);
        }
            else
            {
                player.displayClientMessage(Component.translatable("message.pasterdream.蓝图放置失败"), true);
            }

        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.getItem() instanceof BluePrintItem)
        {
            heldItem.getOrCreateTag().putBoolean("isPlacing", false);
            player.inventoryMenu.broadcastChanges();
        }
    }

    private static boolean hasRequiredMaterials(Player player, CompoundTag materialNbt)
    {
        List<List<List<ItemStack>>> ListListListItemStack = BluePrintNBTSerializer.serialize(materialNbt);
        if (ListListListItemStack == null)
        {
            return false;
        }

        requiredCounts = new HashMap<>();
        for (List<List<ItemStack>> ListListItemStack : ListListListItemStack)
        {
            for (List<ItemStack> ListItemStack : ListListItemStack)
            {
                for (ItemStack itemStack : ListItemStack)
                {
                    if (!itemStack.isEmpty())
                    {
                        requiredCounts.merge(itemStack.getItem(), itemStack.getCount(), Integer::sum);
                    }
                }
            }
        }

        Map<Item, Integer> requiredCountsCopy = new HashMap<>(requiredCounts);
        for (ItemStack inventoryItemStack : player.getInventory().items)
        {
            if (!inventoryItemStack.isEmpty())
            {
                Integer need = requiredCountsCopy.get(inventoryItemStack.getItem());
                if (need != null)
                {
                    int have = inventoryItemStack.getCount();
                    if (have >= need)
                    {
                        requiredCountsCopy.remove(inventoryItemStack.getItem());
                    }
                        else
                        {
                            requiredCountsCopy.put(inventoryItemStack.getItem(), need - have);
                        }
                }
            }
        }

        return requiredCountsCopy.isEmpty();
    }

    private static void consumeMaterials(Player player, CompoundTag materialNbt)
    {
        List<List<List<ItemStack>>> requiredGrid = BluePrintNBTSerializer.serialize(materialNbt);
        if (requiredGrid == null)
        {
            return;
        }


        for (int i = 0; i < player.getInventory().items.size(); i++)
        {
            ItemStack invStack = player.getInventory().items.get(i);
            if (!invStack.isEmpty())
            {
                Item item = invStack.getItem();
                Integer needed = requiredCounts.get(item);
                if (needed != null && needed > 0)
                {
                    int toRemove = Math.min(needed, invStack.getCount());
                    invStack.shrink(toRemove);
                    needed -= toRemove;
                    if (needed <= 0)
                    {
                        requiredCounts.remove(item);
                    }
                        else
                        {
                            requiredCounts.put(item, needed);
                        }
                }
            }
        }
    }

    private static boolean placeResultStructure(ServerLevel level, BlockPos targetPos, CompoundTag resultNBT, Rotation rotation)
    {
        level.getStructureManager().readStructure(resultNBT).placeInWorld(level, targetPos, targetPos, new StructurePlaceSettings().setRotation(rotation), level.random, 2);
        return true;
    }
}
