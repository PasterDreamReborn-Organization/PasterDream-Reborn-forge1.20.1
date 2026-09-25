package com.pasterdream.pasterdreammod.world.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;

/**
 * 眠椰块的方块实体渲染器：像原版头颅那样，在客户端按 blockstate 的角度旋转烘焙模型。
 * <p>
 * 站立形态读取 {@code rotation}（16 段 × 22.5°）；贴墙形态的模型已按 {@code facing} 预置朝向，无需再转。
 * 旋转绕方块竖直中心轴进行，碰撞箱（静态 VoxelShape）不受影响。
 * </p>
 * <p>
 * 走 {@code ModelBlockRenderer.tesselateBlock}（与普通方块同一套渲染），因此面朝向明暗与环境光遮蔽（AO）都正确；
 * 不能用只接受单一光源的 {@code renderModel}，否则会偏亮。
 * </p>
 */
public class SlumberPalmBlockEntityRenderer implements BlockEntityRenderer<SlumberPalmBlockEntity> {

    private final RandomSource random = RandomSource.create();

    public SlumberPalmBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SlumberPalmBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        if (level == null) {
            return;
        }

        BlockState state = blockEntity.getBlockState();
        float yaw = state.getBlock() instanceof SlumberPalmBlock
                ? RotationSegment.convertToDegrees(state.getValue(SlumberPalmBlock.ROTATION))
                : 0.0F;

        Minecraft minecraft = Minecraft.getInstance();
        BakedModel model = minecraft.getBlockRenderer().getBlockModel(state);
        BlockPos pos = blockEntity.getBlockPos();

        poseStack.pushPose();
        poseStack.translate(0.5D, 0.0D, 0.5D);
        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
        poseStack.translate(-0.5D, 0.0D, -0.5D);
        minecraft.getBlockRenderer().getModelRenderer().tesselateBlock(
                level, model, state, pos, poseStack,
                bufferSource.getBuffer(RenderType.cutout()),
                true, this.random, state.getSeed(pos), packedOverlay);
        poseStack.popPose();
    }
}
