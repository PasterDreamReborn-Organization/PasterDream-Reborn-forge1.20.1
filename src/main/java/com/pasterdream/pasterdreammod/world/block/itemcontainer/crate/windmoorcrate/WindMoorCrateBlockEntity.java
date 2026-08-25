package com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.windmoorcrate;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.CrateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class WindMoorCrateBlockEntity extends CrateBlockEntity
{
    public WindMoorCrateBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.WIND_MOOR_CRATE.get(), pos, state, "block." + PasterDreamMod.MOD_ID + ".wind_moor_crate");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
    {
        return new WindMoorCrateMenu(id, inv, this);
    }
}
