package com.pasterdream.pasterdreammod.client.network;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import com.pasterdream.pasterdreammod.world.block.geckolibblock.AnimatableSync;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 仅客户端加载的网络包处理辅助类。
 * 所有引用 net.minecraft.client 的逻辑都必须放在这里，
 * 避免在专用服务器加载网络包类时触发客户端类加载。
 */
public class ClientPacketHandlers
{
    public static void handleFluidSync(int containerId, FluidStack[] fluids)
    {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.containerMenu instanceof AbstractContainerMenuWithFluidSlot menu && menu.containerId == containerId)
        {
            menu.syncFluidsFromClient(fluids);
        }
    }

    public static void handleFluidSound(ResourceLocation fluidId, boolean isFill)
    {
        Level level = Minecraft.getInstance().level;
        if (level == null)
        {
            return;
        }

        Player player = Minecraft.getInstance().player;
        if (player == null)
        {
            return;
        }

        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
        if (fluid != null)
        {
            FluidType fluidType = fluid.getFluidType();
            SoundAction action = isFill ? SoundActions.BUCKET_FILL : SoundActions.BUCKET_EMPTY;
            SoundEvent sound = fluidType.getSound(action);
            if (sound == null)
            {
                sound = isFill ? SoundEvents.BUCKET_FILL : SoundEvents.BUCKET_EMPTY;
            }

            player.playSound(sound, 1.0F, 1.0F);
        }
    }

    public static void handleSanSync(double sanValue)
    {
        Player player = Minecraft.getInstance().player;
        if (player != null)
        {
            player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
            {
                capability.setSanValue(sanValue);
            });
        }
    }

    public static void handleMaxSanSync(double maxSanValue)
    {
        Player player = Minecraft.getInstance().player;
        if (player != null)
        {
            player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
            {
                capability.setMaxSanValue(maxSanValue);
            });
        }
    }

    public static void handleIsSanEnableSync(boolean isEnabled)
    {
        Player player = Minecraft.getInstance().player;
        if (player != null)
        {
            player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
            {
                capability.setIsSanEnable(isEnabled);
            });
        }
    }

    public static void handleMeltDreamEnergySync(double meltDreamEnergy)
    {
        Player player = Minecraft.getInstance().player;
        if (player != null)
        {
            player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
            {
                capability.setMeltDreamEnergy(meltDreamEnergy);
            });
        }
    }

    public static void handleMaxMeltDreamEnergySync(double maxMeltDreamEnergy)
    {
        Player player = Minecraft.getInstance().player;
        if (player != null)
        {
            player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
            {
                capability.setMaxMeltDreamEnergy(maxMeltDreamEnergy);
            });
        }
    }

    public static void handleIsNotNeedSync(boolean isNotNeed)
    {
        Player player = Minecraft.getInstance().player;
        if (player != null)
        {
            player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
            {
                capability.setIsOrNotNeedConsumeDreamEnergy(isNotNeed);
            });
        }
    }

    public static void handleMortarNbtSync(CompoundTag nbt)
    {
        Player player = Minecraft.getInstance().player;
        if (player != null)
        {
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof MortarItem)
            {
                stack.setTag(nbt);
            }
        }
    }

    public static void handleAnimationStateChange(BlockPos pos, int animationState)
    {
        if (Minecraft.getInstance().level != null)
        {
            BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(pos);
            if (blockEntity instanceof AnimatableSync sync)
            {
                sync.setAnimationState(animationState);
            }
        }
    }

    public static void handleCurioActivation(Item item)
    {
        Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(item));
    }
}