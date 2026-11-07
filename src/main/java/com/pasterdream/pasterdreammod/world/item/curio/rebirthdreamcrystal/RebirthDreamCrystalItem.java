package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata.PasterDreamPlayerDreamNoteProgressData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkHooks;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class RebirthDreamCrystalItem extends Item implements ICurioItem {

    private static final UUID SAN_VARIABILITY_UUID = UUID.fromString("7d4c9a2e-5b1f-4c8d-9e3a-1f6b2c8d4a10");

    private static final UUID MAX_HEALTH_UUID = UUID.nameUUIDFromBytes("pasterdream.RebirthDreamCrystalItem.MAX_HEALTH".getBytes(StandardCharsets.UTF_8));
    private static final UUID MELT_DREAM_VARIABILITY_UUID = UUID.nameUUIDFromBytes("pasterdream.RebirthDreamCrystalItem.MELT_DREAM_VARIABILITY".getBytes(StandardCharsets.UTF_8));
    private static final UUID SKILL_DAMAGE_MULTIPLIER_UUID = UUID.nameUUIDFromBytes("pasterdream.RebirthDreamCrystalItem.SKILL_DAMAGE_MULTIPLIER".getBytes(StandardCharsets.UTF_8));
    private static final UUID MAX_ARMOR_UUID = UUID.nameUUIDFromBytes("pasterdream.RebirthDreamCrystalItem.MAX_ARMOR".getBytes(StandardCharsets.UTF_8));
    private static final UUID SPEED_MULTIPLIER_UUID = UUID.nameUUIDFromBytes("pasterdream.RebirthDreamCrystalItem.SPEED_MULTIPLIER".getBytes(StandardCharsets.UTF_8));
    private static final UUID SKILL_COOLDOWN_MULTIPLIER_UUID = UUID.nameUUIDFromBytes("pasterdream.RebirthDreamCrystalItem.SKILL_COOLDOWN_MULTIPLIER".getBytes(StandardCharsets.UTF_8));

    public RebirthDreamCrystalItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.MIRACLE));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemStack = player.getItemInHand(hand);
        if(!level.isClientSide())
        {
            if (player.isShiftKeyDown())
            {
                List<Item> loot = Config.getRebirthDreamCrystalLoot();
                if (!loot.isEmpty())
                {
                    Item picked = loot.get(player.getRandom().nextInt(loot.size()));
                    ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(picked));
                }
                level.playSound(null, player.blockPosition(), ModSounds.DING.get(), SoundSource.PLAYERS, 1, 1);

                if (!player.getAbilities().instabuild)
                {
                    itemStack.shrink(1);
                }
            }
                else
                {
                    NetworkHooks.openScreen((ServerPlayer) player, new MenuProvider()
                    {
                        @Override
                        public Component getDisplayName()
                        {
                            return Component.translatable("gui." + PasterDreamMod.MOD_ID + ".rebirth_dream_crystal");
                        }

                        @Override
                        public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
                        {
                            return new RebirthDreamCrystalMenu(id, inventory);
                        }
                    });
                }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack)
    {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide())
        {
            AttributeInstance sanAttr = entity.getAttribute(ModAttributes.SAN_VARIABILITY.get());
            if (sanAttr != null && sanAttr.getModifier(SAN_VARIABILITY_UUID) == null)
            {
                sanAttr.addPermanentModifier(new AttributeModifier(SAN_VARIABILITY_UUID, "Rebirth dream crystal sanity variability", 12.0, AttributeModifier.Operation.ADDITION));
            }

            if(entity instanceof Player player)
            {
                if(PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, "dyedream_world"))
                {
                    AttributeInstance maxHealthAttribute = entity.getAttribute(Attributes.MAX_HEALTH);
                    if(maxHealthAttribute != null && maxHealthAttribute.getModifier(MAX_HEALTH_UUID) == null)
                    {
                        maxHealthAttribute.addPermanentModifier(new AttributeModifier(MAX_HEALTH_UUID, "重生之梦水晶+16最大生命值", 16, AttributeModifier.Operation.ADDITION));
                        player.setHealth(player.getHealth() + 16);
                    }

                    AttributeInstance maxMeltDreamVariability = entity.getAttribute(ModAttributes.MELT_DREAM_VARIABILITY.get());
                    if(maxMeltDreamVariability != null && maxMeltDreamVariability.getModifier(MELT_DREAM_VARIABILITY_UUID) == null)
                    {
                        maxMeltDreamVariability.addPermanentModifier(new AttributeModifier(MELT_DREAM_VARIABILITY_UUID, "重生之梦水晶+8融梦光环", 8, AttributeModifier.Operation.ADDITION));
                    }
                }

                if(PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, "lamp_shadow_world"))
                {
                    AttributeInstance skillDamageMultiplier = entity.getAttribute(ModAttributes.SKILL_DAMAGE_RATE.get());
                    if(skillDamageMultiplier != null && skillDamageMultiplier.getModifier(SKILL_DAMAGE_MULTIPLIER_UUID) == null)
                    {
                        skillDamageMultiplier.addPermanentModifier(new AttributeModifier(SKILL_DAMAGE_MULTIPLIER_UUID, "重生之梦水晶+16%战技伤害", 1.16, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    }

                    AttributeInstance maxArmor = entity.getAttribute(Attributes.ARMOR);
                    if(maxArmor != null && maxArmor.getModifier(MAX_ARMOR_UUID) == null)
                    {
                        maxArmor.addPermanentModifier(new AttributeModifier(MAX_ARMOR_UUID, "重生之梦水晶+16护甲值", 16, AttributeModifier.Operation.ADDITION));
                    }
                }

                if(PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, "wind_journey_world"))
                {
                    AttributeInstance movementSpeedMultiplier = entity.getAttribute(Attributes.MOVEMENT_SPEED);
                    if(movementSpeedMultiplier != null && movementSpeedMultiplier.getModifier(SPEED_MULTIPLIER_UUID) == null)
                    {
                        movementSpeedMultiplier.addPermanentModifier(new AttributeModifier(SPEED_MULTIPLIER_UUID, "重生之梦水晶+16%速度", 1.16, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    }

                    AttributeInstance skillCooldownMultiplier = entity.getAttribute(ModAttributes.SKILL_COOLDOWN_REDUCTION.get());
                    if(skillCooldownMultiplier != null && skillCooldownMultiplier.getModifier(SKILL_COOLDOWN_MULTIPLIER_UUID) == null)
                    {
                        skillCooldownMultiplier.addPermanentModifier(new AttributeModifier(SKILL_COOLDOWN_MULTIPLIER_UUID, "重生之梦水晶-16%战技冷却", 0.16, AttributeModifier.Operation.ADDITION));
                    }
                }
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack)
    {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide())
        {
            AttributeInstance sanAttr = entity.getAttribute(ModAttributes.SAN_VARIABILITY.get());
            if (sanAttr != null)
            {
                sanAttr.removeModifier(SAN_VARIABILITY_UUID);
            }

            if(entity instanceof Player player)
            {
                if(PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, "dyedream_world"))
                {
                    AttributeInstance maxHealthAttribute = entity.getAttribute(Attributes.MAX_HEALTH);
                    if(maxHealthAttribute != null)
                    {
                        maxHealthAttribute.removeModifier(MAX_HEALTH_UUID);
                        player.setHealth(Math.min(player.getHealth(), player.getMaxHealth()));
                    }

                    AttributeInstance maxMeltDreamVariability = entity.getAttribute(ModAttributes.MELT_DREAM_VARIABILITY.get());
                    if(maxMeltDreamVariability != null)
                    {
                        maxMeltDreamVariability.removeModifier(MELT_DREAM_VARIABILITY_UUID);
                    }
                }

                if(PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, "lamp_shadow_world"))
                {
                    AttributeInstance skillDamageMultiplier = entity.getAttribute(ModAttributes.SKILL_DAMAGE_RATE.get());
                    if(skillDamageMultiplier != null)
                    {
                        skillDamageMultiplier.removeModifier(SKILL_DAMAGE_MULTIPLIER_UUID);
                    }

                    AttributeInstance maxArmor = entity.getAttribute(Attributes.ARMOR);
                    if(maxArmor != null)
                    {
                        maxArmor.removeModifier(MAX_ARMOR_UUID);
                    }
                }

                if(PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, "wind_journey_world"))
                {
                    AttributeInstance movementSpeedMultiplier = entity.getAttribute(Attributes.MOVEMENT_SPEED);
                    if(movementSpeedMultiplier != null)
                    {
                        movementSpeedMultiplier.removeModifier(SPEED_MULTIPLIER_UUID);
                    }

                    AttributeInstance skillCooldownMultiplier = entity.getAttribute(ModAttributes.SKILL_COOLDOWN_REDUCTION.get());
                    if(skillCooldownMultiplier != null)
                    {
                        skillCooldownMultiplier.removeModifier(SKILL_COOLDOWN_MULTIPLIER_UUID);
                    }
                }
            }
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack)
    {
        if (!(slotContext.entity() instanceof ServerPlayer player)) return;
        if (player.tickCount % 200 != 0) return;
        player.addEffect(new MobEffectInstance(ModEffects.EVASION.get(), 240, 0, false, false));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity()).map(handler ->
                            handler.findFirstCurio(this).isEmpty())
                    .orElse(true);
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.1"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.2"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.3"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.4"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.+16最大生命值(完成染梦世界任务后解锁)"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.+8融梦光环(完成染梦世界任务后解锁)"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.+16%战技伤害(完成灯影之下任务后解锁)"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.+16护甲值(完成灯影之下任务后解锁)"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.+16%速度(完成风之旅途任务后解锁)"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.-16%战技冷却(完成风之旅途任务后解锁)"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.5"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.6"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.7"));
    }

}
