package com.pasterdream.pasterdreammod.helper.stringhelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import java.util.List;

public class GetBlockProperties
{
    public static String getBlockProperties(BlockState blockState, BlockGetter level, BlockPos pos)
    {
        Block block = blockState.getBlock();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(Component.translatable("message.pasterdream.方块名称:").getString()).append(block.getName().getString()).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.方块ID:").getString()).append(BuiltInRegistries.BLOCK.getKey(block)).append('\n');

        Item blockItem = block.asItem();
        String itemId = (blockItem != Items.AIR ? BuiltInRegistries.ITEM.getKey(blockItem).toString() : "null");
        stringBuilder.append(Component.translatable("message.pasterdream.对应物品:").getString()).append(itemId).append('\n');

        stringBuilder.append(Component.translatable("message.pasterdream.自发光亮度:").getString()).append(blockState.getLightEmission()).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.亮度遮挡:").getString()).append(blockState.getLightBlock(level, pos)).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.是否遮挡天空光照:").getString()).append(!block.propagatesSkylightDown(blockState, level, pos)).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.硬度:").getString()).append(blockState.getDestroySpeed(level, pos)).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.抗爆系数:").getString()).append(blockState.getExplosionResistance(level, pos, null)).append('\n');

        boolean isFlammable = blockState.isFlammable(level, pos, null);
        stringBuilder.append(Component.translatable("message.pasterdream.是否可燃:").getString()).append(isFlammable).append('\n');
        if(isFlammable)
        {
            stringBuilder.append(Component.translatable("message.pasterdream.点燃概率:").getString()).append(blockState.getFlammability(level, pos, null)).append('\n');
        }

        stringBuilder.append(Component.translatable("message.pasterdream.摩擦系数:").getString()).append(block.getFriction()).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.速度系数:").getString()).append(block.getSpeedFactor()).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.跳跃提升系数:").getString()).append(block.getJumpFactor()).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.是否具有随机刻:").getString()).append(blockState.isRandomlyTicking()).append('\n');
        stringBuilder.append(Component.translatable("message.pasterdream.声音类型:").getString()).append(block.getSoundType(blockState).getBreakSound().getLocation()).append('\n');

        MapColor mapColor = blockState.getMapColor(level, pos);
        stringBuilder.append(Component.translatable("message.pasterdream.地图颜色:").getString()).append("0x").append(String.format("%06X", mapColor.calculateRGBColor(MapColor.Brightness.HIGH) & 0xFFFFFF)).append('\n');

        var holder = block.builtInRegistryHolder();
        List<ResourceLocation> tagIds = holder.tags().map(TagKey::location).sorted().toList();
        StringBuilder buffer = new StringBuilder("BlockTag:\n");
        if (tagIds.isEmpty())
        {
            buffer.append("null");
        }
            else
            {
                for (ResourceLocation id : tagIds)
                {
                    buffer.append("#").append(id).append('\n');
                }
            }

        stringBuilder.append(buffer);

        return stringBuilder.toString();
    }
}
