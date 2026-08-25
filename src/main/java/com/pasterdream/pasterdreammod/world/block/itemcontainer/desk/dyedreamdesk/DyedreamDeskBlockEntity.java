package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.DeskBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class DyedreamDeskBlockEntity extends DeskBlockEntity
{
    public DyedreamDeskBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.DYEDREAM_DESK.get(), pos, state, "block." + PasterDreamMod.MOD_ID + ".dyedream_desk");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
    {
        return new DyedreamDeskMenu(id, inv, this);
    }
}
