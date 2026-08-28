package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class BobojiCurioItem extends Item implements ICurioItem {
    private static final UUID BLINK_CD_UUID = UUID.fromString("aacc6573-c216-4d94-a67b-e72377bbdd32");
    private static final UUID BLINK_RANGE_UUID = UUID.fromString("a3cad91f-ed19-4ea5-a904-f65078ecf73e");
    private static final UUID BLINK_CONSUME_UUID = UUID.fromString("32144ee0-f98a-48cf-964e-3c6b19c89350");
    private static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("7673dac1-b6fb-4ca0-90dd-fd3787c501e4");
    private static final String TAG_LAST_BLINK = "bobojiLastBlink";

    public BobojiCurioItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.LEGENDARY));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance blinkCd = slotContext.entity().getAttribute(ModAttributes.BLINK_CD.get());
            if (blinkCd != null && blinkCd.getModifier(BLINK_CD_UUID) == null) {
                blinkCd.addPermanentModifier(new AttributeModifier(BLINK_CD_UUID,
                        "Boboji blink cd", -0.2, AttributeModifier.Operation.ADDITION));
            }
            AttributeInstance blinkRange = slotContext.entity().getAttribute(ModAttributes.BLINK_RANGE.get());
            if (blinkRange != null && blinkRange.getModifier(BLINK_RANGE_UUID) == null) {
                blinkRange.addPermanentModifier(new AttributeModifier(BLINK_RANGE_UUID,
                        "Boboji blink range", 0.1, AttributeModifier.Operation.ADDITION));
            }
            AttributeInstance blinkConsume = slotContext.entity().getAttribute(ModAttributes.BLINK_CONSUME.get());
            if (blinkConsume != null && blinkConsume.getModifier(BLINK_CONSUME_UUID) == null) {
                blinkConsume.addPermanentModifier(new AttributeModifier(BLINK_CONSUME_UUID,
                        "Boboji blink consume", -0.4, AttributeModifier.Operation.ADDITION));
            }
            AttributeInstance moveSpeed = slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED);
            if (moveSpeed != null && moveSpeed.getModifier(MOVEMENT_SPEED_UUID) == null) {
                moveSpeed.addPermanentModifier(new AttributeModifier(MOVEMENT_SPEED_UUID,
                        "Boboji movement speed", 0.05, AttributeModifier.Operation.MULTIPLY_BASE));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance blinkCd = slotContext.entity().getAttribute(ModAttributes.BLINK_CD.get());
            if (blinkCd != null) blinkCd.removeModifier(BLINK_CD_UUID);
            AttributeInstance blinkRange = slotContext.entity().getAttribute(ModAttributes.BLINK_RANGE.get());
            if (blinkRange != null) blinkRange.removeModifier(BLINK_RANGE_UUID);
            AttributeInstance blinkConsume = slotContext.entity().getAttribute(ModAttributes.BLINK_CONSUME.get());
            if (blinkConsume != null) blinkConsume.removeModifier(BLINK_CONSUME_UUID);
            AttributeInstance moveSpeed = slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED);
            if (moveSpeed != null) moveSpeed.removeModifier(MOVEMENT_SPEED_UUID);
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (level.isClientSide()) return;

        var entity = slotContext.entity();
        var evasion = entity.getEffect(ModEffects.EVASION.get());

        // Detect fresh blink: only match BlinkSkill's original 6-tick evasion
        if (evasion != null && evasion.getDuration() == 6) {
            long now = entity.level().getGameTime();
            long lastBlink = stack.getOrCreateTag().getLong(TAG_LAST_BLINK);
            if (now - lastBlink > 10) {
                stack.getOrCreateTag().putLong(TAG_LAST_BLINK, now);
                onBlink(level, entity);
            }
        }

        // Spawn particle trail while evasion is active
        if (evasion != null && level instanceof ServerLevel sl) {
            double x = entity.getX();
            double y = entity.getY() + 1.0;
            double z = entity.getZ();
            sl.sendParticles(ModParticleTypes.SPORE_PARTICLE.get(), x, y, z, 4, 0.4, 0.8, 0.4, 0.1);
            sl.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(), x, y, z, 4, 0.4, 0.8, 0.4, 0.1);
        }
    }

    private void onBlink(Level level, net.minecraft.world.entity.LivingEntity entity) {
        level.playSound(null, entity.blockPosition(), SoundEvents.PARROT_FLY, SoundSource.NEUTRAL, 1.0F, 1.0F);
        level.playSound(null, entity.blockPosition(), SoundEvents.PARROT_AMBIENT, SoundSource.NEUTRAL, 1.0F, 1.0F);

        Vec3 center = entity.position();
        List<Player> nearby = level.getEntitiesOfClass(Player.class,
                AABB.ofSize(center, 15, 15, 15), e -> true);
        if (!nearby.isEmpty()) {
            for (Player p : nearby) {
                p.addEffect(new MobEffectInstance(ModEffects.EVASION.get(), 10, 0, false, false));
            }
        } else {
            entity.addEffect(new MobEffectInstance(ModEffects.EVASION.get(), 5, 0, false, false));
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity()).map(handler ->
                            handler.findFirstCurio(stack.getItem()).isEmpty())
                    .orElse(true);
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.stat.speed"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.stat.range"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.stat.cd"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.stat.consume"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.effect.elytra"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.effect.evasion"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.effect.share"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.effect.flavor"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.lore"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.dedication"));
    }
}
