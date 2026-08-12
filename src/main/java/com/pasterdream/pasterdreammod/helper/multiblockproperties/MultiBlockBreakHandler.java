package com.pasterdream.pasterdreammod.helper.multiblockproperties;

import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition._2x4x2_CalculatePartPosition;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition._3x3x3_CalculatePartPosition;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceBlock;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "pasterdream", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MultiBlockBreakHandler
{
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event)
    {
        Player player = event.getPlayer();
        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();

        if (!(state.getBlock() instanceof ShadowBlastFurnaceBlock) && !(state.getBlock() instanceof WeaponWorkshopBlastFurnaceBlock))
        {
            return;
        }

        EnumProperty<?> partProperty = null;
        if (state.getBlock() instanceof ShadowBlastFurnaceBlock)
        {
            partProperty = ShadowBlastFurnaceBlock.PART;
        }
        else
            if (state.getBlock() instanceof WeaponWorkshopBlastFurnaceBlock)
            {
                partProperty = WeaponWorkshopBlastFurnaceBlock.PART;
            }
        if (partProperty == null)
        {
            return;
        }

        Direction facing = state.getValue(ShadowBlastFurnaceBlock.FACING);
        String partName = state.getValue(partProperty).getSerializedName();
        boolean isMain = partName.equals("main");

        if (isMain)
        {
            return;
        }

        event.setCanceled(true);

        BlockPos mainPos = null;
        if (state.getBlock() instanceof ShadowBlastFurnaceBlock)
        {
            mainPos = _3x3x3_CalculatePartPosition.getMainPosFromAddon(pos, facing, (_3x3x3Part) state.getValue(partProperty));
        }
        else
            if (state.getBlock() instanceof WeaponWorkshopBlastFurnaceBlock)
            {
                mainPos = _2x4x2_CalculatePartPosition.getMainPosFromAddon(pos, facing, (_2x4x2Part) state.getValue(partProperty));
            }

        if (mainPos == null)
        {
            return;
        }

        BlockState mainState = level.getBlockState(mainPos);
        if (mainState.getBlock() == state.getBlock())
        {
            level.destroyBlock(mainPos, !player.isCreative());
        }
    }
}