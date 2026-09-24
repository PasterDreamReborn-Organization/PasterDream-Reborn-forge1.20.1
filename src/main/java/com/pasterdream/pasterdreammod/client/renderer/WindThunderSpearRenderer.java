package com.pasterdream.pasterdreammod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pasterdream.pasterdreammod.client.model.WindThunderSpearModel;
import com.pasterdream.pasterdreammod.world.entity.WindThunderSpearEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WindThunderSpearRenderer extends GeoEntityRenderer<WindThunderSpearEntity> {

    public WindThunderSpearRenderer(EntityRendererProvider.Context context) {
        super(context, new WindThunderSpearModel());
    }

    @Override
    protected void applyRotations(WindThunderSpearEntity animatable, PoseStack poseStack, float ageInTicks,
                                  float rotationYaw, float partialTick) {
        Vec3 velocity = animatable.getDeltaMovement();
        if (velocity.lengthSqr() > 1.0E-6) {
            float yaw = (float) (Mth.atan2(velocity.x, velocity.z) * (180.0 / Math.PI));
            float pitch = -(float) (Mth.atan2(velocity.y, velocity.horizontalDistance()) * (180.0 / Math.PI));
            // 模型沿 +Y 延伸：先绕 X 转 90° 把矛身放平，再对齐飞行方向（yaw + pitch）
            poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F + pitch));
            // 在局部空间下移半个矛身，使旋转轴接近矛的中心
            poseStack.translate(0.0, -1.6, 0.0);
        }
    }
}
