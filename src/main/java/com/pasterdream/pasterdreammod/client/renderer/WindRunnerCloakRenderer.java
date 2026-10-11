package com.pasterdream.pasterdreammod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pasterdream.pasterdreammod.client.model.WindRunnerCloakModel;
import com.pasterdream.pasterdreammod.world.item.armoritem.WindRunnerCloakItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

/**
 * 风行者披风渲染器。
 *
 * <p>披风飘动不依赖动画文件，而是在每帧遍历骨骼时按原版
 * {@code net.minecraft.client.renderer.entity.layers.CapeLayer} 的位移公式
 * 实时驱动 {@code body1} 骨骼的旋转，从而让布面随速度 / 转向 / 行走摆动。
 *
 * <p>与 {@code CapeLayer} 的差异：原版披风是一块竖挂的平面且其模型朝向相反，
 * 故公式里带一个 180° 的 Y 修正；本模型已按「披在背后」的姿态建模，
 * 因此去掉该 180°，仅保留动态分量与基础后倾。若观感方向不对，
 * 调整下面几个常量（含正负号）即可。
 */
public class WindRunnerCloakRenderer extends GeoArmorRenderer<WindRunnerCloakItem> {

    /** 驱动飘动的骨骼名（披风布料根骨）。 */
    private static final String SWING_BONE = "body1";

    /** 静止时的基础后倾角（对应原版公式的 6.0F）。 */
    private static final float BASE_TILT_DEG = 6.0F;

    /**
     * 动态分量方向系数。GeckoLib 骨骼旋转正负与原版 {@code Axis} 相反，
     * 故取 -1 让披风随速度向后扬起（原版方向）。若仍反向，改成 +1。
     */
    private static final float SWING_SIGN = -1.0F;

    /**
     * 鞘翅滑翔时的固定后掠角。原版公式的 {@code along} 项会把高速滑翔的披风抬成竖直
     * （速度 1.5 格/tick 时 along 触顶 150 → 单此一项就 +75°），而滑翔时身体本身已俯卧，
     * 披风只需轻微后掠贴住身体，故滑翔分支忽略 along，仅保留本常量与起降响应。
     */
    private static final float FLIGHT_TRAIL_DEG = 0.0F;

    public WindRunnerCloakRenderer() {
        super(new WindRunnerCloakModel());
    }

    @Override
    public void renderRecursively(PoseStack poseStack, WindRunnerCloakItem animatable, GeoBone bone,
                                  RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
                                  boolean isReRender, float partialTick, int packedLight, int packedOverlay,
                                  float red, float green, float blue, float alpha) {
        if (SWING_BONE.equals(bone.getName()) && getCurrentEntity() instanceof Player player) {
            applyCapeSwing(bone, player, partialTick);
        }
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer,
                isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    /**
     * 原版 {@code CapeLayer} 的位移→旋转换算，逐帧应用到披风骨骼上。
     * 数值与 clamp 范围照搬原版，仅去掉 180° 朝向修正；鞘翅滑翔时另走轻量分支（见
     * {@link #FLIGHT_TRAIL_DEG}）。
     */
    private static void applyCapeSwing(GeoBone bone, Player player, float partialTick) {
        double dx = Mth.lerp(partialTick, player.xCloakO, player.xCloak) - Mth.lerp(partialTick, player.xo, player.getX());
        double dy = Mth.lerp(partialTick, player.yCloakO, player.yCloak) - Mth.lerp(partialTick, player.yo, player.getY());
        double dz = Mth.lerp(partialTick, player.zCloakO, player.zCloak) - Mth.lerp(partialTick, player.zo, player.getZ());

        float bodyRot = Mth.rotLerp(partialTick, player.yBodyRotO, player.yBodyRot);
        double sin = Mth.sin(bodyRot * 0.017453292F);
        double cos = -Mth.cos(bodyRot * 0.017453292F);

        float forwardPull = (float) dy * 10.0F;
        forwardPull = Mth.clamp(forwardPull, -6.0F, 32.0F);

        float sideways = (float) (dx * cos - dz * sin) * 100.0F;
        sideways = Mth.clamp(sideways, -20.0F, 20.0F);

        // 鞘翅滑翔：身体已俯卧，披风随之向后垂落即可贴住身体，不再叠加 along 的抬头量。
        if (player.isFallFlying()) {
            bone.setRotX((FLIGHT_TRAIL_DEG + SWING_SIGN * forwardPull * 0.5F) * Mth.DEG_TO_RAD);
            bone.setRotZ(SWING_SIGN * sideways / 2.0F * Mth.DEG_TO_RAD);
            bone.setRotY(-SWING_SIGN * sideways / 2.0F * Mth.DEG_TO_RAD);
            return;
        }

        float along = (float) (dx * sin + dz * cos) * 100.0F;
        along = Mth.clamp(along, 0.0F, 150.0F);

        float bob = Mth.lerp(partialTick, player.oBob, player.bob);
        forwardPull += Mth.sin(Mth.lerp(partialTick, player.walkDistO, player.walkDist) * 6.0F) * 32.0F * bob;
        if (player.isCrouching()) {
            forwardPull += 25.0F;
        }

        bone.setRotX((BASE_TILT_DEG + SWING_SIGN * (along / 2.0F + forwardPull)) * Mth.DEG_TO_RAD);
        bone.setRotZ(SWING_SIGN * sideways / 2.0F * Mth.DEG_TO_RAD);
        bone.setRotY(-SWING_SIGN * sideways / 2.0F * Mth.DEG_TO_RAD);
    }
}
