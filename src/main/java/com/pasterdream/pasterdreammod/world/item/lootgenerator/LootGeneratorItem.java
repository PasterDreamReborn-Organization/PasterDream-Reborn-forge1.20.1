package com.pasterdream.pasterdreammod.world.item.lootgenerator;

import com.pasterdream.pasterdreammod.world.block.ItemContainer.IItemContainerInventory;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.ItemContainerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Random;

public class LootGeneratorItem extends Item
{
    public LootGeneratorItem()
    {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Nullable
    public static ResourceLocation getToolLootTable(ItemStack stack)
    {
        if (stack.hasTag() && stack.getTag().contains("LootTable"))
        {
            return ResourceLocation.tryParse(stack.getTag().getString("LootTable"));
        }
        return null;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos blockPosition = context.getClickedPos();
        ItemStack itemStack = context.getItemInHand();

        BlockEntity blockEntity = level.getBlockEntity(blockPosition);

        if (!level.isClientSide && player instanceof ServerPlayer)
        {
            CompoundTag toolTag = itemStack.getTag();
            if(toolTag != null && toolTag.contains("LootTable"))
            {
                String lootTable = toolTag.getString("LootTable");
                if(blockEntity != null)
                {
                    CompoundTag containerTag = blockEntity.serializeNBT();
                    if(containerTag.contains("Items"))
                    {
                        containerTag.put("Items", new ListTag());
                    }
                        else
                        {
                            player.displayClientMessage(Component.translatable("message.pasterdream.loot_generator.not_container"), true);
                            return InteractionResult.FAIL;
                        }

                    if(containerTag.contains("LootTableSeed"))
                    {
                        containerTag.remove("LootTableSeed");
                    }
                    containerTag.putString("LootTable", lootTable);
                    blockEntity.load(containerTag);
                    blockEntity.setChanged();
                    player.displayClientMessage(Component.translatable("message.pasterdream.loot_generator.loot_table_set", lootTable), true);
                    return InteractionResult.SUCCESS;
                }
                    else
                    {
                        player.displayClientMessage(Component.translatable("message.pasterdream.loot_generator.not_container"), true);
                        return InteractionResult.FAIL;
                    }
            }
                else
                {
                    player.displayClientMessage(Component.translatable("message.pasterdream.loot_generator.no_loot_table"), true);
                    return InteractionResult.FAIL;
                }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.loot_generator.usage.shift"));

        ResourceLocation lootTable = getToolLootTable(stack);
        if (lootTable != null)
        {
            tooltip.add(Component.translatable("tooltip.pasterdream.当前设置战利品表：")
                    .append(Component.literal(lootTable.toString()).withStyle(style ->
                            style.withColor(0x55FFFF).withItalic(true))));
        }
        else
        {
            tooltip.add(Component.translatable("tooltip.pasterdream.当前设置战利品表：")
                    .append(Component.translatable("tooltip.pasterdream.loot_table.not_set")));
        }
    }
}
