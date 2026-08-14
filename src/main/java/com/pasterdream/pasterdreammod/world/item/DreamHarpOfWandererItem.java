package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DreamHarpOfWandererItem extends Item {

    private static final double ENERGY_COST = 3.0;
    private static final int COOLDOWN_TICKS = 600;
    private static final int EFFECT_DURATION = 1200;
    private static final double EFFECT_RADIUS = 2.5;

    public DreamHarpOfWandererItem() {
        super(new Item.Properties().durability(0).rarity(Rarity.UNCOMMON).fireResistant());
    }

    @Override
    public float getDestroySpeed(ItemStack itemstack, BlockState blockstate) {
        return 1;
    }

    @Override
    public boolean mineBlock(ItemStack itemstack, Level world, BlockState blockstate, BlockPos pos, LivingEntity entity) {
        itemstack.hurtAndBreak(1, entity, i -> i.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        itemstack.hurtAndBreak(2, entity, i -> i.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            double currentEnergy = MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(serverPlayer);
            if (player.isCreative() || currentEnergy >= ENERGY_COST) {
                if (!player.isCreative()) {
                    MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(serverPlayer, -ENERGY_COST);
                }
                SanHelper.addPlayerSanAndSync(serverPlayer, 5);

                // Heart particles in 3 waves
                net.minecraft.server.MinecraftServer server = level.getServer();
                spawnHeartParticles((ServerLevel) level, player.getX(), player.getY(), player.getZ());
                server.tell(new net.minecraft.server.TickTask(server.getTickCount() + 5, () -> {
                    spawnHeartParticles((ServerLevel) level, player.getX(), player.getY(), player.getZ());
                    server.tell(new net.minecraft.server.TickTask(server.getTickCount() + 5, () -> {
                        spawnHeartParticles((ServerLevel) level, player.getX(), player.getY(), player.getZ());
                    }));
                }));

                // Play sound
                level.playSound(null, BlockPos.containing(player.getX(), player.getY(), player.getZ()),
                        ModSounds.DREAM_HARP_OF_WANDERER.get(), SoundSource.PLAYERS, 0.7f, 1.0f);

                // Cooldown
                player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);

                // Apply effect to nearby players
                Vec3 center = new Vec3(player.getX(), player.getY(), player.getZ());
                List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class,
                        new AABB(center, center).inflate(EFFECT_RADIUS),
                        p -> true);
                for (Player nearby : nearbyPlayers) {
                    nearby.addEffect(new MobEffectInstance(ModEffects.DREAM_HARP_OF_WANDERER_BUFF.get(),
                            EFFECT_DURATION, 0));
                }
            } else {
                player.displayClientMessage(Component.translatable("message.pasterdream.dream_harp_of_wanderer.no_energy"), true);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private void spawnHeartParticles(ServerLevel level, double x, double y, double z) {
        level.sendParticles(ParticleTypes.HEART, x, y, z, 7, 0.45, 0.8, 0.45, 0.5);
    }

    @Override
    public int getEnchantmentValue() {
        return 2;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID,
                    "Tool modifier", 3f, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID,
                    "Tool modifier", -3, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("tooltip.pasterdream.dream_harp_of_wanderer.line1"));
        list.add(Component.translatable("tooltip.pasterdream.dream_harp_of_wanderer.line2"));
        list.add(Component.translatable("tooltip.pasterdream.dream_harp_of_wanderer.line3"));
        list.add(Component.translatable("tooltip.pasterdream.dream_harp_of_wanderer.line4"));
        list.add(Component.translatable("tooltip.pasterdream.dream_harp_of_wanderer.line5"));
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        var entity = new IndestructibleItemEntity(level, location.getX(), location.getY(), location.getZ(), stack);
        entity.setDefaultPickUpDelay();
        entity.setDeltaMovement(location.getDeltaMovement());
        return entity;
    }
}
