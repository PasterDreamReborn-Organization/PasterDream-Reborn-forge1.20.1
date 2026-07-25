package com.pasterdream.pasterdreammod.world.block.doll.uuzdoll;

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

public class UUZDollBlockEntity extends DollBlockEntity
{
    public UUZDollBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.UUZ_DOLL.get(), pos, state, "block." + PasterDreamMod.MOD_ID + ".uuz_doll");
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, UUZDollBlockEntity blockEntity)
    {
        if (level.getGameTime() % 80 != 0) return;

        int range = 5;
        AABB area = new AABB(pos).inflate(range);
        for (Player player : level.getEntitiesOfClass(Player.class, area))
        {
            player.addEffect(new MobEffectInstance(ModEffects.REST_BUFF.get(),
                    200, 1, false, false));
        }
    }
}
