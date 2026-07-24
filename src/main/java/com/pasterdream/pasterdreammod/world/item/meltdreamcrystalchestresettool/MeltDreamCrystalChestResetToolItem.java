package com.pasterdream.pasterdreammod.world.item.meltdreamcrystalchestresettool;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.helper.meltdreamenergycostcalculator.MeltDreamEnergyCostCalculator;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.world.block.meltdreamcrystalchest.MeltDreamCrystalChestBlock;
import com.pasterdream.pasterdreammod.world.block.meltdreamcrystalchest.MeltDreamCrystalChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MeltDreamCrystalChestResetToolItem extends Item
{
    public MeltDreamCrystalChestResetToolItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(itemStack, level, tooltip, flag);

        tooltip.add(Component.translatable("tooltip.pasterdream.按住Shift+右键点击打开的融梦水晶箱可复位（写入此物品NBT中包含的战利品列表）"));
        CompoundTag tag = itemStack.getTag();
        if(tag != null && tag.contains("LootTables"))
        {
            ListTag listTag = tag.getList("LootTables", Tag.TAG_COMPOUND);
            CompoundTag normalLootTable = !listTag.isEmpty() ? listTag.getCompound(0) : null;
            CompoundTag rareLootTable = listTag.size() > 1 ? listTag.getCompound(1) : null;
            CompoundTag legendLootTable = listTag.size() > 2 ? listTag.getCompound(2) : null;

            if(normalLootTable != null)
            {
                tooltip.add(Component.translatable("tooltip.pasterdream.normal_loot_table", normalLootTable.getString("LootTable"), normalLootTable.getInt("weight"), normalLootTable.getDouble("luck_multiplier_increase")));
                if(rareLootTable != null)
                {
                    tooltip.add(Component.translatable("tooltip.pasterdream.rare_loot_table", rareLootTable.getString("LootTable"), rareLootTable.getInt("weight"), rareLootTable.getDouble("luck_multiplier_increase")));
                    if(legendLootTable != null)
                    {
                        tooltip.add(Component.translatable("tooltip.pasterdream.legend_loot_table", legendLootTable.getString("LootTable"), legendLootTable.getInt("weight"), legendLootTable.getDouble("luck_multiplier_increase")));
                    }
                }
                tooltip.add(Component.translatable("tooltip.pasterdream.reset_cost_melt_dream_energy", MeltDreamEnergyCostCalculator.calculate(itemStack.getTag().getList("LootTables", Tag.TAG_COMPOUND))));
            }
        }
            else
            {
                tooltip.add(Component.translatable("tooltip.pasterdream.not_have_loot_table"));
            }
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack meltDreamCrystalChestResetTool = context.getItemInHand();
        BlockState state = level.getBlockState(pos);

        if (!state.is(ModBlocks.OPENED_MELT_DREAM_CRYSTAL_CHEST.get()))
        {
            player.displayClientMessage(Component.translatable("message.pasterdream.融梦水晶箱重置工具只能重置打开的融梦水晶箱"), true);
            return InteractionResult.PASS;
        }

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer)
        {
            CompoundTag itemTag = meltDreamCrystalChestResetTool.getTag();
            if (itemTag != null && itemTag.contains("LootTables", Tag.TAG_LIST))
            {
                ListTag lootTableListTag = itemTag.getList("LootTables", Tag.TAG_COMPOUND);

                if(player != null && !player.isCreative())
                {
                    MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(serverPlayer, -MeltDreamEnergyCostCalculator.calculate(lootTableListTag));
                    meltDreamCrystalChestResetTool.shrink(1);
                }

                Direction facing = state.getValue(MeltDreamCrystalChestBlock.FACING);

                BlockState blockState = ModBlocks.MELT_DREAM_CRYSTAL_CHEST.get().defaultBlockState().setValue(MeltDreamCrystalChestBlock.FACING, facing);
                level.setBlock(pos, blockState, 3);

                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof MeltDreamCrystalChestBlockEntity meltDreamCrystalChest)
                {
                    meltDreamCrystalChest.setLootTablesNbt(lootTableListTag);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }
}
