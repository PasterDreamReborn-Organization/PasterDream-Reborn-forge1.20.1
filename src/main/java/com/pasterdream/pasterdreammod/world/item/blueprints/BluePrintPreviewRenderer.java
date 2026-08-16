package com.pasterdream.pasterdreammod.world.item.blueprints;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "pasterdream", value = Dist.CLIENT)
public class BluePrintPreviewRenderer
{
    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event)
    {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES || ClientBluePrintPlacement.getSize() == null)
        {
            return;
        }

        if (Minecraft.getInstance().level == null || Minecraft.getInstance().player == null)
        {
            return;
        }

        HitResult hit = Minecraft.getInstance().hitResult;
        if (!(hit instanceof BlockHitResult blockHit))
        {
            return;
        }

        BlockPos targetPos = blockHit.getBlockPos();

        Vec3i size = ClientBluePrintPlacement.getSize();
        Rotation rotation = ClientBluePrintPlacement.getRotationFromPlayer();

        BlockPos origin = targetPos.above();
        AABB box = switch (rotation)
        {
            case NONE -> new AABB(origin.getX(), origin.getY(), origin.getZ(), origin.getX() + size.getX(), origin.getY() + size.getY(), origin.getZ() + size.getZ());
            case CLOCKWISE_90 -> new AABB(origin.getX() - size.getZ() + 1, origin.getY(), origin.getZ(), origin.getX() + 1, origin.getY() + size.getY(), origin.getZ() + size.getX());
            case CLOCKWISE_180 -> new AABB(origin.getX() - size.getX() + 1, origin.getY(), origin.getZ() - size.getZ() + 1, origin.getX() + 1, origin.getY() + size.getY(), origin.getZ() + 1);
            case COUNTERCLOCKWISE_90 -> new AABB(origin.getX(), origin.getY(), origin.getZ() - size.getX() + 1, origin.getX() + size.getZ(), origin.getY() + size.getZ(), origin.getZ() + 1);
        };

        PoseStack poseStack = event.getPoseStack();

        Vec3 camPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        poseStack.pushPose();
        poseStack.translate(-camPos.x, -camPos.y, -camPos.z);

        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.lines());

        LevelRenderer.renderLineBox(poseStack, vertexConsumer, box.move(0, 0, 0), 1f, 1f, 1f, 1f);

        bufferSource.endBatch(RenderType.lines());
    }
}
