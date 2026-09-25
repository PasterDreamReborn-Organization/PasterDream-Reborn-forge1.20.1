package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillLockHelper;
import com.pasterdream.pasterdreammod.world.entity.FoxFireEntity;
import com.pasterdream.pasterdreammod.world.entity.WindThunderSpearEntity;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * 萦风雷矛 —— 近战 / 突进 / 投掷 三形态长柄武器。
 *
 * 左键 · 近战：物理伤害 + 附带雷伤（不连锁），吃移速，耗 1 耐久。
 * 普通右键蓄力 · 突进：原版激流式前冲（冲量 + 自旋攻击），冷却 3s。
 * Shift+右键蓄力 · 投掷：蓄满投出「虚影」（本体始终留在手中），命中/落地触发连锁闪电后飞回；
 *                       虚影返回前武器处于冷却，最长 10s 兜底（避免掉落进虚空 / 投掷后死亡丢武器）。
 *
 * 被动 · 雷随疾风：移动速度越高伤害越高，并附带雷电伤害；掉落物被风卷至身边。
 */
public class WindwreathedThunderSpearItem extends SwordItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    /** 施加被动自算伤害期间置位，防止被动对自身伤害再次套用形成递归 */
    private static final String APPLYING_TAG = "pasterdream:thunder_spear_applying";

    // ===== 蓄力 =====
    private static final int CHARGE_THRESHOLD = 10; // 蓄满所需 tick（原版三叉戟同为 10）

    // ===== 突进 · 破风（原版激流式） =====
    private static final int DASH_COOLDOWN_TICKS = 60;      // 基础冷却 3s
    private static final int DASH_COOLDOWN_PER_RIPTIDE_LEVEL = 20; // 每级激流延长突进冷却 1s
    private static final int DASH_COOLDOWN_RIPTIDE_MAX_LEVELS = 3;  // 激流最多计入 3 级（冷却最多 +3s）
    private static final int DASH_RIPTIDE_LEVEL = 2;        // 基础冲量档位(0~3)，决定冲量
    private static final int DASH_MAX_RIPTIDE_LEVEL = 5;    // 计入激流附魔后的冲量档位上限
    private static final int DASH_SPIN_TICKS = 20;          // 自旋攻击时长(tick)，原版激流同为 20
    private static final double DASH_DAMAGE_BONUS = 1.5;    // 突进伤害倍率（原版自旋为 1.0）
    private static final double DASH_SWEEP_BASE = 1.5;      // 溅射基础半径(格)
    private static final double DASH_SWEEP_PER_LEVEL = 0.5; // 横扫之刃每级溅射半径加成
    private static final double DASH_SWEEP_MIN_RATIO = 0.4; // 溅射距离衰减：边缘保留 40%（中心 100%）
    private static final int DASH_SWEEP_MAX_TARGETS = 4;    // 突进溅射最多命中数（主目标以外，按距离取最近）
    private static final double RIPTIDE_DASH_DAMAGE_PER_LEVEL = 0.25; // 激流每级突进伤害加成

    // ===== 融梦能量 =====
    private static final double SKILL_ENERGY_COST = 1.0;      // 基础消耗：投掷固定 1.0；突进基础 1.0
    private static final double DASH_ENERGY_PER_RIPTIDE_LEVEL = 1.0; // 每级激流增加突进能量消耗
    private static final String NO_ENERGY_KEY = "message.pasterdream.windwreathed_thunder_spear.no_energy";

    // ===== 投掷 · 萦风投雷 =====
    private static final float THROW_SPEED = 2.5F;            // 投掷速度（原版三叉戟同为 2.5）
    private static final double THROW_DAMAGE_FACTOR = 1.5;    // 投掷伤害 = (1+移速)×攻击力×1.5
    private static final int THROW_COOLDOWN_TICKS = 200;      // 虚影冷却 10s（虚影返回立即解除）

    // ===== 被动 · 雷随疾风 =====
    private static final double PASSIVE_LIGHTNING_RATIO = 0.1; // 附带攻击力×0.1 雷电伤害

    // ===== 附魔加成 =====
    private static final double SHARPNESS_DAMAGE_BONUS = 0.5;  // 锋利每级攻击力加成
    private static final float SMITE_BANE_DAMAGE = 2.5F;       // 亡灵/节肢杀手每级额外伤害

    // ===== 触及距离 =====
    private static final UUID REACH_MODIFIER_UUID = UUID.fromString("b2c3d4e5-f6a7-8901-bcde-f23456789012");
    private static final double EXTRA_ENTITY_REACH = 2.0;      // 长柄额外触及(格)

    public WindwreathedThunderSpearItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    // ==================== 客户端渲染 ====================

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new WindwreathedThunderSpearItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    // ==================== 蓄力与触发 ====================

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        ItemStack stack = player.getItemInHand(hand);
        if (SkillLockHelper.isSkillLocked(player)) return InteractionResultHolder.fail(stack);
        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) return InteractionResultHolder.fail(stack);

        // 冷却中禁止进入蓄力：突进冷却（共享战技冷却）与投掷虚影冷却共用矛的物品冷却，
        // 冷却结束前无法再次使用任一形态（虚影返回会立即解除投掷冷却）。
        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            return InteractionResultHolder.fail(stack);
        }
        // 融梦能量不足（基础 1.0）：双端预检避免蓄力后无反馈；突进的激流附加消耗由 dash() 实时校验
        if (!player.isCreative() && MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player) < SKILL_ENERGY_COST) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable(NO_ENERGY_KEY), true);
            }
            return InteractionResultHolder.fail(stack);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;
        int charge = this.getUseDuration(stack) - timeLeft;
        if (charge < CHARGE_THRESHOLD) return;

        // 释放瞬间按 shift 判定形态
        if (player.isShiftKeyDown()) {
            // 投掷：冷却中不投掷；虚影只在服务端生成
            if (player.getCooldowns().isOnCooldown(stack.getItem())) return;
            if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
                throwSpear(stack, serverPlayer);
            }
        } else {
            // 突进：冷却中不突进
            if (player.getCooldowns().isOnCooldown(stack.getItem())) return;
            // 原版激流位移在双端执行（客户端预测），否则服务端冲量会被客户端移动包覆盖
            dash(stack, player, level);
        }
    }

    // ==================== 突进 · 破风（原版激流式） ====================

    private void dash(ItemStack stack, Player player, Level level) {
        // 激流附魔增强冲量：基础档位 + 激流等级，封顶
        int riptide = stack.getEnchantmentLevel(Enchantments.RIPTIDE);
        // 能量消耗 = 1.0 + 每级激流 1.0；双端校验，服务端结算
        double energyCost = SKILL_ENERGY_COST + riptide * DASH_ENERGY_PER_RIPTIDE_LEVEL;
        if (!player.isCreative() && MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player) < energyCost) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable(NO_ENERGY_KEY), true);
            }
            return;
        }
        if (!level.isClientSide && !player.isCreative() && player instanceof ServerPlayer serverPlayer) {
            MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(serverPlayer, -energyCost);
        }
        int powerLevel = Math.min(DASH_RIPTIDE_LEVEL + riptide, DASH_MAX_RIPTIDE_LEVEL);

        float yRot = player.getYRot();
        float xRot = player.getXRot();
        float dx = -Mth.sin(yRot * 0.017453292F) * Mth.cos(xRot * 0.017453292F);
        float dy = -Mth.sin(xRot * 0.017453292F);
        float dz = Mth.cos(yRot * 0.017453292F) * Mth.cos(xRot * 0.017453292F);
        float len = Mth.sqrt(dx * dx + dy * dy + dz * dz);
        float power = 3.0F * ((1.0F + powerLevel) / 4.0F);
        dx *= power / len;
        dy *= power / len;
        dz *= power / len;

        player.push(dx, dy, dz);
        player.startAutoSpinAttack(DASH_SPIN_TICKS);
        if (player.onGround()) {
            player.move(MoverType.SELF, new Vec3(0.0, 1.2, 0.0));
        }
        SoundEvent sound = powerLevel >= 3 ? SoundEvents.TRIDENT_RIPTIDE_3
                : powerLevel == 2 ? SoundEvents.TRIDENT_RIPTIDE_2 : SoundEvents.TRIDENT_RIPTIDE_1;
        level.playSound(null, player, sound, SoundSource.PLAYERS, 1.0F, 1.0F);

        // 伤害不在此处结算：由自旋接触触发（ThunderSpearPassiveHandler），覆盖整条冲刺路径
        // 接入战技共享冷却（随 SKILL_COOLDOWN_RATE 缩放，并联动其它战技武器）
        // 激流每级延长冷却 1s（最多 +3s）
        int cooldownTicks = DASH_COOLDOWN_TICKS
                + Math.min(riptide, DASH_COOLDOWN_RIPTIDE_MAX_LEVELS) * DASH_COOLDOWN_PER_RIPTIDE_LEVEL;
        SkillCooldownHelper.applySharedCooldown(player, cooldownTicks);
        player.awardStat(Stats.ITEM_USED.get(this));
    }

    // ==================== 投掷 · 萦风投雷 ====================

    private void throwSpear(ItemStack stack, ServerPlayer player) {
        Level level = player.level();
        boolean creative = player.isCreative();
        if (!creative) {
            // 融梦能量不足：不投掷（use() 已预检，此处为服务端权威兜底）
            if (MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player) < SKILL_ENERGY_COST) {
                player.displayClientMessage(Component.translatable(NO_ENERGY_KEY), true);
                return;
            }
            MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(player, -SKILL_ENERGY_COST);
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
        }

        double speed = Math.max(player.getAttributeValue(Attributes.MOVEMENT_SPEED),
                player.getDeltaMovement().horizontalDistance());
        double atk = player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                * SkillCooldownHelper.getSkillDamageMultiplier(player);
        atk += stack.getEnchantmentLevel(Enchantments.SHARPNESS) * SHARPNESS_DAMAGE_BONUS;
        float damage = (float) ((1 + speed) * atk * THROW_DAMAGE_FACTOR);

        WindThunderSpearEntity spear = new WindThunderSpearEntity(level, player, stack);
        spear.init(player, damage,
                stack.getEnchantmentLevel(Enchantments.SMITE),
                stack.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS),
                stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT));
        spear.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, THROW_SPEED, 1.0F);
        level.addFreshEntity(spear);
        level.playSound(null, spear, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);

        // 本体留在手中：投掷出的只是虚影。虚影返回前武器冷却，10s 后兜底解除。
        player.getCooldowns().addCooldown(stack.getItem(), THROW_COOLDOWN_TICKS);
    }

    /** 虚影返回时调用：立即解除本次投掷冷却。 */
    public static void clearThrowCooldown(Player player, ItemStack spearStack) {
        if (spearStack.isEmpty()) return;
        player.getCooldowns().removeCooldown(spearStack.getItem());
    }

    /** 投掷虚影的冷却时长（tick），供弹射物侧兜底参考。 */
    public static int getThrowCooldownTicks() {
        return THROW_COOLDOWN_TICKS;
    }

    // ==================== 触及距离 ====================

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);
        if (slot != EquipmentSlot.MAINHAND) {
            return modifiers;
        }
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(modifiers);
        builder.put(ForgeMod.ENTITY_REACH.get(),
                new AttributeModifier(REACH_MODIFIER_UUID, "pasterdream.thunder_spear.reach",
                        EXTRA_ENTITY_REACH, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    // ==================== 附魔 ====================

    /** 允许在附魔台获得「激流」与「引雷」（长矛视为可投掷/可激流武器）。
     *  二者按原版规则互斥（{@code TridentRiptideEnchantment.checkCompatibility}），
     *  即同一把矛只能二选一：激流=突进流，引雷=投掷流。 */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.CHANNELING || enchantment == Enchantments.RIPTIDE) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    // ==================== 被动 · 雷随疾风 ====================

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ThunderSpearPassiveHandler {
        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (!(event.getSource().getEntity() instanceof Player player)) return;
            if (!(player.getMainHandItem().getItem() instanceof WindwreathedThunderSpearItem)) return;
            if (player.getPersistentData().getBoolean(APPLYING_TAG)) return;
            if (event.getSource().getDirectEntity() != player) return;

            // 突进自旋接触：把原版自旋伤害替换为突进伤害（激流加成伤害、横扫之刃扩大溅射）
            if (player.isAutoSpinAttack()) {
                applyDashOnTouch(player, event);
                return;
            }

            LivingEntity target = event.getEntity();
            float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float critRatio = atk > 0 ? event.getAmount() / atk : 1.0f; // 保留暴击/附魔比例
            double speed = Math.max(player.getAttributeValue(Attributes.MOVEMENT_SPEED),
                    player.getDeltaMovement().horizontalDistance());
            float mainDamage = (float) ((1 + speed) * atk) * critRatio;
            float lightningDamage = atk * (float) PASSIVE_LIGHTNING_RATIO;

            event.setCanceled(true);
            player.getPersistentData().putBoolean(APPLYING_TAG, true);
            target.invulnerableTime = 0;
            target.hurt(target.level().damageSources().playerAttack(player), mainDamage);
            target.invulnerableTime = 0;
            target.hurt(target.level().damageSources().lightningBolt(), lightningDamage);
            player.getPersistentData().remove(APPLYING_TAG);
        }
    }

    /** 突进自旋接触结算：把原版自旋伤害替换为突进伤害（激流加成伤害、横扫之刃扩大溅射范围）。 */
    private static void applyDashOnTouch(Player player, LivingHurtEvent event) {
        LivingEntity target = event.getEntity();
        if (isOwnedMinion(target, player)) {
            event.setCanceled(true);
            return;
        }
        ItemStack stack = player.getMainHandItem();
        int riptide = stack.getEnchantmentLevel(Enchantments.RIPTIDE);
        int sweeping = stack.getEnchantmentLevel(Enchantments.SWEEPING_EDGE);
        // 突进伤害吃移速：取移速属性与瞬时速度的较大值（突进冲量也计入，故激流/高移速下爆发更高）
        double moveSpeed = Math.max(player.getAttributeValue(Attributes.MOVEMENT_SPEED),
                player.getDeltaMovement().horizontalDistance());
        double atk = player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                * SkillCooldownHelper.getSkillDamageMultiplier(player);
        atk += stack.getEnchantmentLevel(Enchantments.SHARPNESS) * SHARPNESS_DAMAGE_BONUS;
        double riptideMult = 1.0 + riptide * RIPTIDE_DASH_DAMAGE_PER_LEVEL;
        float damage = (float) (atk * DASH_DAMAGE_BONUS * (1 + moveSpeed) * riptideMult);
        if (target.getMobType() == MobType.UNDEAD) damage += stack.getEnchantmentLevel(Enchantments.SMITE) * SMITE_BANE_DAMAGE;
        if (target.getMobType() == MobType.ARTHROPOD) damage += stack.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS) * SMITE_BANE_DAMAGE;
        event.setAmount(damage);

        // 横扫之刃扩大突进溅射范围；溅射最多命中 DASH_SWEEP_MAX_TARGETS 个（按距离取最近），
        // 且伤害由中心向边缘线性衰减至 40%，避免高移速/高附魔下一发横扫清场
        if (sweeping > 0 && player.level() instanceof ServerLevel sl) {
            double radius = DASH_SWEEP_BASE + Math.min(sweeping, 3) * DASH_SWEEP_PER_LEVEL;
            Vec3 center = target.position();
            List<LivingEntity> candidates = sl.getEntitiesOfClass(LivingEntity.class,
                    target.getBoundingBox().inflate(radius),
                    e -> e != player && e != target && e.isAlive() && !isOwnedMinion(e, player));
            candidates.sort(Comparator.comparingDouble(e -> e.distanceToSqr(center)));
            player.getPersistentData().putBoolean(APPLYING_TAG, true);
            int hit = 0;
            for (LivingEntity nearby : candidates) {
                if (hit >= DASH_SWEEP_MAX_TARGETS) break;
                double ratio = 1.0 - (1.0 - DASH_SWEEP_MIN_RATIO)
                        * Math.min(Math.sqrt(nearby.distanceToSqr(center)) / radius, 1.0);
                nearby.invulnerableTime = 0;
                nearby.hurt(sl.damageSources().playerAttack(player), (float) (damage * ratio));
                hit++;
            }
            player.getPersistentData().remove(APPLYING_TAG);
        }
    }

    /** 是否为玩家自己的仆从/召唤物/同队盟友（战技不应命中） */
    private static boolean isOwnedMinion(LivingEntity e, @Nullable Player owner) {
        if (owner == null) return false;
        if (e instanceof OwnableEntity ownable) {
            return ownable.getOwner() == owner;
        }
        if (e instanceof FoxFireEntity fe) {
            return fe.resolveOwner() == owner;
        }
        return e.isAlliedTo(owner);
    }

    // ==================== 其余 ====================

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.windwreathed_thunder_spear.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.windwreathed_thunder_spear.desc_dash_header"));
        tooltip.add(Component.translatable("tooltip.pasterdream.windwreathed_thunder_spear.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.windwreathed_thunder_spear.desc_throw_header"));
        tooltip.add(Component.translatable("tooltip.pasterdream.windwreathed_thunder_spear.desc2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.windwreathed_thunder_spear.passive_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.windwreathed_thunder_spear.passive1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.windwreathed_thunder_spear.passive2"));
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
