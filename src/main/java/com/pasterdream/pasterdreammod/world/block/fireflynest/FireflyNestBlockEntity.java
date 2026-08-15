package com.pasterdream.pasterdreammod.world.block.fireflynest;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FireflyNestBlockEntity extends BlockEntity {
    private boolean charged = false;

    public FireflyNestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FIREFLY_NEST.get(), pos, state);
    }

    public boolean isCharged() {
        return charged;
    }

    public void setCharged(boolean charged) {
        this.charged = charged;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("Charged", charged);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        charged = tag.getBoolean("Charged");
    }
}
