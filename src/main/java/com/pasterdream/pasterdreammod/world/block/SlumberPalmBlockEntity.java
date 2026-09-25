package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 眠椰块方块实体。
 * <p>
 * 角度存在 blockstate（{@code rotation} / {@code facing}）里，这里不保存任何数据，
 * 只是为了挂载 {@link SlumberPalmBlockEntityRenderer} 来做 16 向贴图旋转。
 * </p>
 */
public class SlumberPalmBlockEntity extends BlockEntity {

    public SlumberPalmBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SLUMBER_PALM.get(), pos, state);
    }
}
