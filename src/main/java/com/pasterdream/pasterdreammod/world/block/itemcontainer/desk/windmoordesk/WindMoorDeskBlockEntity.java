package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.windmoordesk;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.DeskBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class WindMoorDeskBlockEntity extends DeskBlockEntity
{
    public WindMoorDeskBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.WIND_MOOR_DESK.get(), pos, state, "block." + PasterDreamMod.MOD_ID + ".wind_moor_desk");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
    {
        return new WindMoorDeskMenu(id, inv, this);
    }
}
