package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 暗影合金盔甲（GEO 模型）穿戴时隐藏玩家身体对应部位，避免盔甲与玩家皮肤（含外层）穿模。
 *
 * 盔甲是独立的渲染层：隐藏玩家模型部位不会影响盔甲本身。
 * 仅处理本模组这套盔甲，且只在 RenderPlayerEvent.Pre 隐藏、Post 还原——
 * PlayerModel 是共享实例，必须成对还原，否则会污染其它实体的渲染。
 *
 * 映射：头盔→头；胸甲→身+双臂；护腿→双腿（均连同皮肤外层）。靴子无独立脚部件，暂不隐藏。
 *
 * ============================================================================
 * ⚠ 临时方案（TODO）：本类只服务于「当前这版 shadow_alloy_armor.geo.json」。
 * 建模师未按玩家皮肤外层尺寸建模（部分网格与玩家内层皮肤等大甚至更小），导致穿模，
 * 故用「隐藏玩家部位」规避。**换模型或给盔甲网格加 inflate 后，请删除本类**
 * （并移除 PasterDreamMod 中对应的客户端事件订阅）。
 * 设计背景与后续计划见 `document/design/item/暗影合金盔甲.md`。
 * ============================================================================
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ShadowAlloyArmorSkinHider {

    @SubscribeEvent
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        PlayerModel<?> model = event.getRenderer().getModel();
        setAllVisible(model);
        Player player = event.getEntity();

        // 头盔：头 + 皮肤外层（hat 在模型里是独立部件，不随 head 隐藏）
        if (wears(player, EquipmentSlot.HEAD, ModItems.SHADOW_ALLOY_HELMET.get())) {
            model.head.visible = false;
            model.hat.visible = false;
        }
        // 胸甲：躯干 + 双臂 + 外层（夹克 / 左右袖）
        if (wears(player, EquipmentSlot.CHEST, ModItems.SHADOW_ALLOY_CHESTPLATE.get())) {
            model.body.visible = false;
            model.rightArm.visible = false;
            model.leftArm.visible = false;
            model.jacket.visible = false;
            model.rightSleeve.visible = false;
            model.leftSleeve.visible = false;
        }
        // 护腿：双腿 + 外层（左右裤脚）
        if (wears(player, EquipmentSlot.LEGS, ModItems.SHADOW_ALLOY_LEGGINGS.get())) {
            model.rightLeg.visible = false;
            model.leftLeg.visible = false;
            model.rightPants.visible = false;
            model.leftPants.visible = false;
        }
    }

    @SubscribeEvent
    public static void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        setAllVisible(event.getRenderer().getModel());
    }

    private static void setAllVisible(PlayerModel<?> model) {
        model.head.visible = true;
        model.hat.visible = true;
        model.body.visible = true;
        model.rightArm.visible = true;
        model.leftArm.visible = true;
        model.rightLeg.visible = true;
        model.leftLeg.visible = true;
        model.jacket.visible = true;
        model.rightSleeve.visible = true;
        model.leftSleeve.visible = true;
        model.rightPants.visible = true;
        model.leftPants.visible = true;
    }

    private static boolean wears(Player player, EquipmentSlot slot, Item item) {
        ItemStack stack = player.getItemBySlot(slot);
        return !stack.isEmpty() && stack.getItem() == item;
    }
}
