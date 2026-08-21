package com.pasterdream.pasterdreammod.world.block.doll.qymdoll;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.world.block.doll.DollBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class QYMDollBlockEntity extends DollBlockEntity
{
    public QYMDollBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.QYM_DOLL.get(), pos, state, "block." + PasterDreamMod.MOD_ID + ".qym_doll");
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, QYMDollBlockEntity blockEntity)
    {
        if (level.getGameTime() % 80 != 0) return;

        int range = 5;
        AABB area = new AABB(pos).inflate(range);
        for (Player player : level.getEntitiesOfClass(Player.class, area))
        {
            player.addEffect(new MobEffectInstance(ModEffects.REST.get(),
                    200, 1, false, false));
        }
    }
}
