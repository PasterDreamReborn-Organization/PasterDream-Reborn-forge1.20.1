package com.pasterdream.pasterdreammod.world.block.doll.qymdoll;

import com.pasterdream.pasterdreammod.world.block.doll.DollItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class QYMDollItem extends DollItem
{
    public QYMDollItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(new IClientItemExtensions()
        {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                if (renderer == null)
                {
                    renderer = new QYMDollItemRenderer();
                }
                return renderer;
            }
        });
    }
}
