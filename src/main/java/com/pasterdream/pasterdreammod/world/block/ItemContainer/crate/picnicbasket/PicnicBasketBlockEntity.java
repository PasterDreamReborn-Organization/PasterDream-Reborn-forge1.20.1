package com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.CrateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class PicnicBasketBlockEntity extends CrateBlockEntity
{
    public PicnicBasketBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.PICNIC_BASKET.get(), pos, state, "block." + PasterDreamMod.MOD_ID + ".picnic_basket");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
    {
        return new PicnicBasketMenu(id, inv, this);
    }
}
