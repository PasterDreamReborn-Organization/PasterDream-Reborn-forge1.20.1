package com.pasterdream.pasterdreammod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pasterdream.pasterdreammod.client.model.ForsakensWingModel;
import com.pasterdream.pasterdreammod.world.item.curio.ForsakensWingItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class ForsakensWingRenderer extends GeoArmorRenderer<ForsakensWingItem> implements ICurioRenderer {

    public ForsakensWingRenderer() {
        super(new ForsakensWingModel());
        this.head = new GeoBone(null, "armorHead", false, 0.0, false, false);
        this.body = new GeoBone(null, "armorBody", false, 0.0, false, false);
        this.rightArm = new GeoBone(null, "armorRightArm", false, 0.0, false, false);
        this.leftArm = new GeoBone(null, "armorLeftArm", false, 0.0, false, false);
        this.rightLeg = new GeoBone(null, "armorRightLeg", false, 0.0, false, false);
        this.leftLeg = new GeoBone(null, "armorLeftLeg", false, 0.0, false, false);
        this.rightBoot = new GeoBone(null, "armorRightBoot", false, 0.0, false, false);
        this.leftBoot = new GeoBone(null, "armorLeftBoot", false, 0.0, false, false);
    }

    @Override
    public RenderType getRenderType(ForsakensWingItem animatable, ResourceLocation texture,
                                    MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack, SlotContext slotContext, PoseStack poseStack,
            RenderLayerParent<T, M> renderLayerParent, MultiBufferSource bufferSource,
            int light, float limbSwing, float limbSwingAmount, float partialTicks,
            float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(stack.getItem() instanceof ForsakensWingItem item)) return;
        LivingEntity entity = slotContext.entity();
        if (entity == null || !(renderLayerParent.getModel() instanceof HumanoidModel<?> baseModel)) return;

        this.prepForRender(entity, stack, EquipmentSlot.CHEST, baseModel);
        poseStack.pushPose();
        RenderType renderType = this.getRenderType(item, this.getTextureLocation(item), bufferSource, partialTicks);
        VertexConsumer buffer = ItemRenderer.getArmorFoilBuffer(bufferSource, renderType, false, stack.hasFoil());
        this.defaultRender(poseStack, item, bufferSource, null, buffer, 0.0F, partialTicks, light);
        poseStack.popPose();
    }
}