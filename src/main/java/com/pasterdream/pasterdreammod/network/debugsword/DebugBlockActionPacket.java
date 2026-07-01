package com.pasterdream.pasterdreammod.network.debugsword;

import com.pasterdream.pasterdreammod.world.item.debugsword.DebugSwordItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class DebugBlockActionPacket
{
    private final BlockPos pos;
    private final int option;

    public DebugBlockActionPacket(BlockPos pos, int option)
    {
        this.pos = pos;
        this.option = option;
    }

    public static void encode(DebugBlockActionPacket msg, FriendlyByteBuf buf)
    {
        buf.writeBlockPos(msg.pos);
        buf.writeByte(msg.option);
    }

    public static DebugBlockActionPacket decode(FriendlyByteBuf buf)
    {
        return new DebugBlockActionPacket(buf.readBlockPos(), buf.readByte());
    }

    public static void handle(DebugBlockActionPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            ServerPlayer sender = ctx.get().getSender();
            if (sender != null)
            {
                if (sender.getMainHandItem().getItem() instanceof DebugSwordItem)
                {
                    executeAction(sender.serverLevel(), msg.pos, msg.option, sender);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public static void executeAction(ServerLevel level, BlockPos pos, int option, ServerPlayer player)
    {
        BlockState state = level.getBlockState(pos);
        if (state.isAir()) return;

        switch (option)
        {
            case 0 ->
            {   //模拟无工具破坏
                manualBreak(level, pos, player, ItemStack.EMPTY);
            }
            case 1 ->
            {   //模拟工具破坏
                manualBreak(level, pos, player, new ItemStack(Items.NETHERITE_PICKAXE));
            }
            case 2 ->
            {   //模拟工具时运III破坏
                ItemStack tool = new ItemStack(Items.NETHERITE_PICKAXE);
                tool.enchant(Enchantments.BLOCK_FORTUNE, 3);
                manualBreak(level, pos, player, tool);
            }
            case 3 ->
            {   //模拟工具精准采集破坏
                ItemStack tool = new ItemStack(Items.NETHERITE_PICKAXE);
                tool.enchant(Enchantments.SILK_TOUCH, 1);
                manualBreak(level, pos, player, tool);
            }
            case 4 ->
            {   //获取BlockItem并掉落，并将此方块设置为空气
                Item item = state.getBlock().asItem();
                if (item != Items.AIR)
                {
                    ItemStack drop = new ItemStack(item);
                    Block.popResource(level, pos, drop);
                }
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
            case 5 ->
            {   //在不触发方块更新的情况下将此方块设置为空气
                BlockState oldState = level.getBlockState(pos);
                LevelChunk chunk = level.getChunkAt(pos);
                int sectionIndex = chunk.getSectionIndex(pos.getY());
                LevelChunkSection section = chunk.getSection(sectionIndex);

                if (section != null)
                {
                    int x = pos.getX() & 0xF;
                    int y = pos.getY() & 0xF;
                    int z = pos.getZ() & 0xF;

                    section.setBlockState(x, y, z, Blocks.AIR.defaultBlockState());

                    chunk.removeBlockEntity(pos);
                    chunk.setUnsaved(true);
                }
                level.sendBlockUpdated(pos, oldState, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }

    private static void manualBreak(ServerLevel level, BlockPos pos, ServerPlayer player, ItemStack tool)
    {
        BlockState state = level.getBlockState(pos);
        List<ItemStack> drops;

        if (tool.isEmpty() && state.requiresCorrectToolForDrops())
        {
            drops = Collections.emptyList();
        }
            else
            {
                drops = Block.getDrops(state, level, pos, null, player, tool);
            }

        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        for (ItemStack drop : drops)
        {
            Block.popResource(level, pos, drop);
        }
    }
}
