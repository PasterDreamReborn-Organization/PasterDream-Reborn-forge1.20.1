package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.shadowdesk;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.DeskBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class ShadowDeskBlockEntity extends DeskBlockEntity
{
    public ShadowDeskBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.SHADOW_DESK.get(), pos, state, "block." + PasterDreamMod.MOD_ID + ".shadow_desk");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
    {
        return new ShadowDeskMenu(id, inv, this);
    }
}
