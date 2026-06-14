package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class FluidHandlerResolvers
{
    private static final Map<ResourceLocation, FluidHandlerResolver> RESOLVERS = new HashMap<>();

    @FunctionalInterface
    public interface FluidHandlerResolver
    {
        @Nullable
        IFluidHandler resolve(Level level, FriendlyByteBuf extraData);
    }

    public static void register(ResourceLocation key, FluidHandlerResolver resolver)
    {
        RESOLVERS.put(key, resolver);
    }

    @Nullable
    public static FluidHandlerResolver get(ResourceLocation key)
    {
        return RESOLVERS.get(key);
    }


    public static void FluidHandlerResolverRegister()
    {
        FluidHandlerResolvers.register(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "block_entity"), (level, data) ->
        {
            BlockPos blockPos = data.readBlockPos();
            int tankIndex = data.readInt();
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof IFluidHandlerProvider provider)
            {
                return provider.getFluidHandler(tankIndex);
            }
            return null;
        });

        //item内容暂时留空
    }
}
