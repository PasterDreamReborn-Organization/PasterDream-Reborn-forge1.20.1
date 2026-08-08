package com.pasterdream.pasterdreammod.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ShadowHandLanternItem extends Item implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private String animationprocedure = "empty";
    public static ItemDisplayContext transformType;

    public ShadowHandLanternItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new ShadowHandLanternItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    public void getTransformType(ItemDisplayContext type) {
        ShadowHandLanternItem.transformType = type;
    }

    private PlayState idlePredicate(AnimationState<ShadowHandLanternItem> event) {
        if (transformType != null) {
            if (this.animationprocedure.equals("empty")) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationState<ShadowHandLanternItem> event) {
        if (transformType != null) {
            if (!this.animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
                if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                    this.animationprocedure = "empty";
                    event.getController().forceAnimationReset();
                }
            } else if (this.animationprocedure.equals("empty")) {
                return PlayState.STOP;
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        AnimationController<ShadowHandLanternItem> procedureController = new AnimationController<>(this, "procedureController", 0, this::procedurePredicate);
        data.add(procedureController);
        AnimationController<ShadowHandLanternItem> idleController = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        data.add(idleController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("tooltip.pasterdream.shadow_hand_lantern.description.1"));
        list.add(Component.translatable("tooltip.pasterdream.shadow_hand_lantern.description.2"));
        list.add(Component.translatable("tooltip.pasterdream.shadow_hand_lantern.description.3"));
        list.add(Component.translatable("tooltip.pasterdream.shadow_hand_lantern.description.4"));
        list.add(Component.translatable("tooltip.pasterdream.shadow_hand_lantern.description.5"));
    }


    private static final UUID SAN_VARIABILITY_UUID = UUID.fromString("332e7034-93a3-4506-afe6-52dc5c511f48");

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
            builder.put(ModAttributes.SAN_VARIABILITY.get(),
                    new AttributeModifier(SAN_VARIABILITY_UUID, "pasterdream.shadow_hand_lantern.san_variability", 1.2, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ItemStack itemstack = ar.getObject();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        if (entity == null)
            return ar;

        entity.getCooldowns().addCooldown(itemstack.getItem(), 160);

        if (itemstack.getItem() instanceof ShadowHandLanternItem)
            itemstack.getOrCreateTag().putString("geckoAnim", "1");

        if (!world.isClientSide()) {
            world.playSound(null, BlockPos.containing(x, y, z),
                    ModSounds.SHADOW_HAND_LANTERN.get(), SoundSource.PLAYERS, 1, 1);
        }

        if (world instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.END_ROD, x, y, z, 128, 4, 0.8, 4, 0.03);
            level.sendParticles(ParticleTypes.ASH, x, y, z, 256, 4, 0.8, 4, 0.03);
        }

        {
            final Vec3 center = new Vec3(x, y, z);
            List<net.minecraft.world.entity.Entity> entfound = world.getEntitiesOfClass(net.minecraft.world.entity.Entity.class,
                    new AABB(center, center).inflate(15 / 2d), e -> true)
                    .stream().sorted(Comparator.comparingDouble(entcnd -> entcnd.distanceToSqr(center))).toList();
            for (net.minecraft.world.entity.Entity entityiterator : entfound) {
                if (entityiterator.getType().is(TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE,
                        ResourceLocation.fromNamespaceAndPath("pasterdream", "shadow_mob")))) {
                    if (entityiterator instanceof LivingEntity living && !living.level().isClientSide())
                        living.addEffect(new net.minecraft.world.effect.MobEffectInstance(ModEffects.VULNERABILITY_BUFF.get(), 300, 1, false, false));
                }
            }
        }

        if (entity instanceof ServerPlayer sp) {
            SanHelper.addPlayerSanAndSync(sp, -1);
        }

        return ar;
    }
}
