package com.pasterdream.pasterdreammod.world.item.prophecycard;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProphecyCardItem extends Item {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProphecyCardItem.class);

    // ===== NBT Key =====
    public static final String TAG_TYPE = "Type";

    // ===== 内置预言卡种类常量 =====
    public static final String TYPE_BALANCE = "balance";
    public static final String TYPE_CHAOS = "chaos";
    public static final String TYPE_CONFLICT = "conflict";
    public static final String TYPE_GRAVEYARD = "graveyard";
    public static final String TYPE_GUARD = "guard";
    public static final String TYPE_HOLY_GRAIL = "holy_grail";
    public static final String TYPE_SIN = "sin";
    public static final String TYPE_SPRINT = "sprint";
    public static final String TYPE_WIELDING_SWORD = "wielding_sword";

    /**
     * 已注册的种类 → predicate 浮点值（开放给外部模组/魔改注册新种类）。
     * 内置种类: 0.100 ~ 0.900（9 个，间隔 0.1）
     * 外部注册: 0.901 ~ 0.999（每 0.001 一步，约 99 个槽位）
     * 哈希兜底: 0.001 ~ 0.099（未注册字符串自动 fallback）
     */
    public static final Map<String, Float> TYPE_PREDICATES = new LinkedHashMap<>();
    private static float nextPredicate = 0.901f;

    static {
        TYPE_PREDICATES.put(TYPE_BALANCE, 0.1f);
        TYPE_PREDICATES.put(TYPE_CHAOS, 0.2f);
        TYPE_PREDICATES.put(TYPE_CONFLICT, 0.3f);
        TYPE_PREDICATES.put(TYPE_GRAVEYARD, 0.4f);
        TYPE_PREDICATES.put(TYPE_GUARD, 0.5f);
        TYPE_PREDICATES.put(TYPE_HOLY_GRAIL, 0.6f);
        TYPE_PREDICATES.put(TYPE_SIN, 0.7f);
        TYPE_PREDICATES.put(TYPE_SPRINT, 0.8f);
        TYPE_PREDICATES.put(TYPE_WIELDING_SWORD, 0.9f);
    }

    /**
     * 卡片类型 → 右键效果（开放给外部模组注册自定义效果）
     */
    private static final Map<String, ProphecyCardEffect> CARD_EFFECTS = new LinkedHashMap<>();

    public ProphecyCardItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    // ===== 效果注册 API =====

    /**
     * 为指定种类注册右键效果。同一类型只能注册一个效果（后注册覆盖先注册）。
     * 外部模组在注册种类后调用即可。
     *
     * @param type   种类名（需先通过 registerCardType 注册）
     * @param effect 右键效果，传 null 可清除
     */
    public static void registerCardEffect(String type, @Nullable ProphecyCardEffect effect) {
        if (effect == null) {
            CARD_EFFECTS.remove(type);
        } else {
            CARD_EFFECTS.put(type, effect);
            LOGGER.info("Registered effect for ProphecyCard type '{}'", type);
        }
    }

    /**
     * 获取指定类型已注册的效果，未注册返回 null
     */
    @Nullable
    public static ProphecyCardEffect getCardEffect(String type) {
        return CARD_EFFECTS.get(type);
    }

    // ===== 开放式 API：供外部模组/魔改注册新种类 =====

    /**
     * 注册一个新的预言卡种类，自动分配 predicate 值。
     * 其他模组在 FMLCommonSetupEvent 或 mod 构造器中调用即可。
     *
     * @param typeName 种类名称（建议全小写+下划线，如 "my_card_type"）
     * @return 分配的 predicate 浮点值，用于编写模型 JSON 的 overrides
     * @throws IllegalStateException 如果 predicate 槽位已满（超过 0.99）
     */
    public static float registerCardType(String typeName) {
        if (TYPE_PREDICATES.containsKey(typeName)) {
            LOGGER.debug("ProphecyCard type '{}' already registered, skipping.", typeName);
            return TYPE_PREDICATES.get(typeName);
        }
        if (nextPredicate > 0.999f) {
            throw new IllegalStateException(
                "ProphecyCard predicate slots exhausted (>999)! Cannot register type '" + typeName + "'");
        }
        float value = nextPredicate;
        TYPE_PREDICATES.put(typeName, value);
        nextPredicate += 0.001f;
        LOGGER.info("Registered ProphecyCard type '{}' with predicate value {}", typeName, value);
        return value;
    }

    /**
     * 根据种类名获取 predicate 值（含 fallback 哈希兜底）。
     * 内置种类返回固定值，外部注册种类返回分配的 slot 值，
     * 未注册的字符串通过 hashCode 计算确定性 fallback 值（供纯资源包添加纹理）。
     */
    public static float getPredicateForType(String typeName) {
        if (typeName.isEmpty()) return 0.0f;
        Float registered = TYPE_PREDICATES.get(typeName);
        if (registered != null) return registered;
        // 哈希兜底：任何未注册字符串映射到 0.001~0.099（避开内置和 API 注册区）
        // 资源包作者可计算此值来编写模型 JSON overrides
        return 0.001f + Math.abs(typeName.hashCode() % 99) / 1000.0f;
    }

    // ===== NBT 读写 =====

    /**
     * 设置预言卡种类
     */
    public static void setType(ItemStack stack, String type) {
        stack.getOrCreateTag().putString(TAG_TYPE, type);
    }

    /**
     * 获取预言卡种类，未设置时返回空字符串
     */
    @NotNull
    public static String getType(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TAG_TYPE, Tag.TAG_STRING)) {
            return tag.getString(TAG_TYPE);
        }
        return "";
    }

    /**
     * 该 ItemStack 是否有种类
     */
    public static boolean hasCardType(ItemStack stack) {
        return !getType(stack).isEmpty();
    }

    /**
     * 获取用于 ItemProperties / 模型 predicate 的浮点值
     */
    public static float getPredicateValue(ItemStack stack) {
        return getPredicateForType(getType(stack));
    }

    /**
     * 创建一个指定种类的预言卡 ItemStack
     */
    public static ItemStack createCard(Item item, String type) {
        ItemStack stack = new ItemStack(item);
        setType(stack, type);
        return stack;
    }

    // ===== 根据 NBT 显示不同名字 =====

    /**
     * 判断类型字符串是否为已识别的有效类型（内置或 API 注册）
     */
    public static boolean isKnownType(String type) {
        return !type.isEmpty() && TYPE_PREDICATES.containsKey(type);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        String type = getType(stack);
        if (!type.isEmpty()) {
            if (isKnownType(type)) {
                // 有效类型：正常翻译
                return Component.translatable("item.pasterdream.prophecy_card." + type);
            }
            // NBT 存在但类型未知 → 显示原始类型名 + 红色标记
            return Component.translatable("item.pasterdream.prophecy_card.unknown", type)
                    .withStyle(ChatFormatting.RED);
        }
        // 无 NBT：空白卡
        return Component.translatable("item.pasterdream.prophecy_card");
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level,
                                @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        String type = getType(stack);
        if (type.isEmpty()) {
            // 空白卡提示
            tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.empty")
                    .withStyle(ChatFormatting.GRAY));
        } else if (isKnownType(type)) {
            // 有效类型
            tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.type." + type)
                    .withStyle(ChatFormatting.AQUA));
        } else {
            // 未知类型：警告
            tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.unknown", type)
                    .withStyle(ChatFormatting.RED));
        }
        switch (type) {
            case TYPE_BALANCE-> tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.balance.description"));
            case TYPE_CHAOS-> {
                tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.chaos.description.1"));
                tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.chaos.description.2"));
            }
            case TYPE_CONFLICT-> {
                tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.conflict.description.1"));
                tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.conflict.description.2"));
            }
            case TYPE_GRAVEYARD-> tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.graveyard.description",Config.graveyarddamage).withStyle(ChatFormatting.BLUE));
            case TYPE_GUARD-> {
                tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.guard.description.1"));
                tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.guard.description.2",(Config.healthpercentguardneed*100),(Config.resistdamage*100)).withStyle(ChatFormatting.BLUE));
            }
            case TYPE_HOLY_GRAIL-> tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.holy_grail.description"));
            case TYPE_SIN-> {
                tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.sin.description.1"));
                tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.sin.description.2"));
                tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.sin.description.3"));
                tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.sin.description.4"));
            }
            case TYPE_SPRINT-> tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.sprint.description"));
            case TYPE_WIELDING_SWORD-> tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.wielding_sword.description"));
            default->{
                tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.unknown.description.1"));
                tooltip.add(Component.translatable("tooltip.pasterdream.prophecy_card.unknown.description.2"));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        String type = getType(stack);

        // 1. 优先分发到已注册的效果
        ProphecyCardEffect effect = CARD_EFFECTS.get(type);
        if (effect != null) {
            return effect.use(world, player, hand, stack);
        }

        // 2. 未注册效果（含已知类型 + 空卡）：空挥，仅返回成功
        if (type.isEmpty() || isKnownType(type)) {
            return InteractionResultHolder.success(stack);
        }

        // 3. 异常 NBT：发送错误提示
        if (!world.isClientSide()) {
            player.displayClientMessage(
                Component.translatable("message.pasterdream.prophecy_card.invalid", type)
                    .withStyle(ChatFormatting.RED), false);
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        // 准心指向方块时也触发卡牌效果，避免抬头/指向方块时范围不生效
        InteractionResultHolder<ItemStack> result = use(context.getLevel(), context.getPlayer(), context.getHand());
        return result.getResult();
    }

    // ===== 工具方法：帮助资源包作者计算哈希 predicate 值 =====

    /**
     * [开发用] 在日志中打印所有已注册种类的 predicate 值
     */
    public static void logRegisteredTypes() {
        LOGGER.info("=== Registered ProphecyCard Types ===");
        for (var entry : TYPE_PREDICATES.entrySet()) {
            LOGGER.info("  {} -> predicate={}", entry.getKey(), entry.getValue());
        }
        LOGGER.info("Next available slot: {}", nextPredicate);
    }

    // ===== 内置卡牌效果注册 =====

    /**
     * 注册所有内置卡牌的右键效果。
     * 在 {@code FMLCommonSetupEvent} 中调用。
     */
    public static void registerAllCardEffects() {
        for (var entry : TYPE_PREDICATES.entrySet()) {
            String type = entry.getKey();
            ProphecyCardItem.registerCardEffect(type, switch (type) {
                case TYPE_BALANCE  -> balanceEffect();
                case TYPE_CHAOS    -> chaosEffect();
                case TYPE_CONFLICT -> conflictEffect();
                case TYPE_GUARD    -> guardEffect();
                case TYPE_SPRINT   -> sprintEffect();
                case TYPE_HOLY_GRAIL   -> holygrailEffect();
                case TYPE_SIN -> sinEffect();
                case TYPE_GRAVEYARD -> graveyardEffect();
                case TYPE_WIELDING_SWORD -> wieldingswordEffect();
                // 未匹配的不注册效果（右键空挥）
                default -> null;
            });
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void showTotemEffect(ItemStack stack) {
        Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
    }

    // 允许生效的buff（可在配置文件修改）
    private static boolean isEffectAllowed(MobEffectInstance effect) {
        var list = Config.balanceAllowedEffects;
        // 空列表 = 允许全部
        if (list.isEmpty()) return true;

        ResourceLocation id = ForgeRegistries.MOB_EFFECTS.getKey(effect.getEffect());
        return id != null && list.contains(id.toString());
    }

    private static ProphecyCardEffect balanceEffect() {
        return (level, player, hand, stack) -> {
            if (!level.isClientSide()) {
                for (MobEffectInstance effect : player.getActiveEffects()) {
                    int duration = effect.getDuration();

                    // 跳过永久/超长效果
                    if (duration > Config.maxtakeeffectduration * 20) continue;

                    // 过滤：仅允许配置列表中的药水效果
                    if (!isEffectAllowed(effect)) continue;

                    // 等级翻倍 + 时间减半
                    int newAmplifier = effect.getAmplifier() * 2 + 1;
                    int newDuration = duration / 2;
                    if (newDuration < Config.mintakeeffectduration * 20) continue;
                    if (effect.getAmplifier() <= Config.maxlevel) {
                        player.removeEffect(effect.getEffect());
                        player.addEffect(new MobEffectInstance(
                                effect.getEffect(), newDuration, newAmplifier,
                                effect.isAmbient(), effect.isVisible(), effect.showIcon()
                        ));
                    }
                }

                // 音效
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.EVASION.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                // 消耗
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                // 客户端：图腾贴图爆出特效
                showTotemEffect(stack);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        };
    }

    private static ProphecyCardEffect chaosEffect() {
        return (level, player, hand, stack) -> {
            if (!level.isClientSide()) {
                // 以自身为中心，7x7 范围
                Vec3 center = player.position();
                AABB area = AABB.ofSize(center, 7, 7, 7);

                // 获取范围内所有生物（排除玩家自身及其他玩家）
                List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area,
                        e -> e != player && !(e instanceof Player));

                // 对每个敌人施加混乱效果，持续 10 秒（200 ticks）
                for (LivingEntity target : entities) {
                    target.addEffect(new MobEffectInstance(ModEffects.CONFUSION_BUFF.get(), 10 * 20, 0));
                }

                // 音效
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.EVASION.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                // 消耗
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                showTotemEffect(stack);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        };
    }

    private static ProphecyCardEffect conflictEffect() {
        return (level, player, hand, stack) -> {
            // 射线追踪寻找玩家准星指向的实体（双端执行，客户端用于判断图腾特效）
            double reach = Config.conflictCardReach;
            Vec3 eyePos = player.getEyePosition();
            Vec3 lookVec = player.getViewVector(1.0F);
            Vec3 endPos = eyePos.add(lookVec.scale(reach));
            AABB searchBox = new AABB(eyePos, endPos).inflate(1.0);

            List<LivingEntity> candidates = level.getEntitiesOfClass(LivingEntity.class, searchBox,
                    e -> e != player && e.isAlive());

            LivingEntity target = null;
            double closestDist = reach;
            for (LivingEntity entity : candidates) {
                // 黑名单检查
                if (Config.isConflictMarkBlacklisted(entity.getType())) {
                    continue;
                }
                Optional<Vec3> clip = entity.getBoundingBox().inflate(0.3).clip(eyePos, endPos);
                if (clip.isPresent()) {
                    double dist = eyePos.distanceToSqr(clip.get());
                    if (dist < closestDist * closestDist) {
                        closestDist = Math.sqrt(dist);
                        target = entity;
                    }
                }
            }

            if (target != null) {
                if (!level.isClientSide()) {
                    // 施加纷争标记效果，持续 120 秒
                    target.addEffect(new MobEffectInstance(
                            ModEffects.CONFLICT_MARK.get(), 120 * 20, 0));
                    // 音效
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            ModSounds.EVASION.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    // 提示玩家
                    player.displayClientMessage(
                            Component.translatable("message.pasterdream.prophecy_card.conflict.marked",
                                    target.getName()).withStyle(ChatFormatting.GOLD), true);
                    // 消耗
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                } else {
                    showTotemEffect(stack);
                }
            } else {
                if (!level.isClientSide()) {
                    // 未找到有效目标
                    player.displayClientMessage(
                            Component.translatable("message.pasterdream.prophecy_card.conflict.no_target")
                                    .withStyle(ChatFormatting.RED), true);
                }
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        };
    }

    private static ProphecyCardEffect wieldingswordEffect() {
        return (level, player, hand, stack) -> {
            if (!level.isClientSide()) {
                // 服务端：给予 120 秒怒气爆发
                player.addEffect(new MobEffectInstance(ModEffects.FLARE_UP_BUFF.get(), 120 * 20, 0));
                // 音效
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.EVASION.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                // 消耗
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                showTotemEffect(stack);
            }
            return InteractionResultHolder.success(stack);
        };
    }

    private static ProphecyCardEffect sinEffect() {
        return (level, player, hand, stack) -> {
            if (!level.isClientSide()) {
                // 以自身为中心，19x100x19 范围
                Vec3 center = player.position();
                AABB area = AABB.ofSize(center, 19, 100, 19);

                ServerLevel serverLevel = (ServerLevel) level;

                // 获取范围内所有生物（排除自身）
                List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area,
                        e -> e != player);

                // 归属于玩家的火焰伤害源（亡灵/灾厄村民用）
                DamageSource fireSource = new DamageSource(
                        level.registryAccess()
                                .registryOrThrow(Registries.DAMAGE_TYPE)
                                .getHolderOrThrow(DamageTypes.IN_FIRE),
                        player);
                // 归属于玩家的通用伤害源（秒杀用，不会被火焰免疫拦截）
                DamageSource genericSource = new DamageSource(
                        level.registryAccess()
                                .registryOrThrow(Registries.DAMAGE_TYPE)
                                .getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD),
                        player);

                for (LivingEntity target : entities) {
                    if (Config.isSinInstakillTarget(target.getType())) {
                        // 配置列表中指定的实体：直接秒杀（通用伤害，免疫无效）
                        target.hurt(genericSource, target.getMaxHealth() * 2);
                    } else if (target instanceof ZombieVillager zombieVillager) {
                        // 僵尸村民：转化为普通村民，不受伤害（含小僵尸村民）
                        convertZombieVillager(serverLevel, zombieVillager);
                    } else if (target instanceof Zombie zombie && zombie.isBaby()) {
                        // 小僵尸（非僵尸村民）：直接秒杀（通用伤害，免疫无效）
                        zombie.hurt(genericSource, zombie.getMaxHealth() * 2);
                    } else if (target.getMobType() == MobType.UNDEAD
                            || target instanceof Pillager
                            || target instanceof Vindicator
                            || target instanceof Evoker
                            || target instanceof Ravager) {
                        // 亡灵生物和灾厄村民：引燃 15 秒 + 25 点火焰伤害
                        target.setSecondsOnFire(15);
                        target.hurt(fireSource, 25);
                    }
                }

                // 音效
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.EVASION.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                // 消耗
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                showTotemEffect(stack);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        };
    }

    /**
     * 将僵尸村民立即转化为普通村民，保留原交易项目。
     * 若僵尸村民是由村民被感染而来，其 NBT 中会保存原村民的交易/职业数据，
     * 此处将其提取并恢复到新生成的村民上。
     */
    private static void convertZombieVillager(ServerLevel serverLevel, ZombieVillager zombieVillager) {
        double x = zombieVillager.getX();
        double y = zombieVillager.getY();
        double z = zombieVillager.getZ();
        float yRot = zombieVillager.getYRot();
        float xRot = zombieVillager.getXRot();

        // 保存僵尸村民完整 NBT（含被感染前的村民交易/职业数据）
        CompoundTag zombieData = zombieVillager.serializeNBT();
        zombieVillager.discard();

        Entity entity = EntityType.VILLAGER.spawn(serverLevel,
                BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
        if (entity != null) {
            // 获取新村民当前 NBT，并覆盖村民相关的键
            CompoundTag villagerData = entity.serializeNBT();
            if (zombieData.contains("Offers")) {
                villagerData.put("Offers", zombieData.get("Offers"));
            }
            if (zombieData.contains("VillagerData")) {
                villagerData.put("VillagerData", zombieData.get("VillagerData"));
            }
            if (zombieData.contains("Gossips")) {
                villagerData.put("Gossips", zombieData.get("Gossips"));
            }
            if (zombieData.contains("Xp")) {
                villagerData.putInt("Xp", zombieData.getInt("Xp"));
            }

            entity.deserializeNBT(villagerData);
            entity.setYRot(yRot);
            entity.setXRot(xRot);
        }
    }

    private static ProphecyCardEffect guardEffect() {
        return (level, player, hand, stack) -> {
            if (!level.isClientSide()) {
                // 服务端：给予 120 秒伤害吸收 III + 60秒守护
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 120 * 20, 2));
                player.addEffect(new MobEffectInstance(ModEffects.GUARD_BUFF.get(), 60 * 20, 0));
                // 音效
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.EVASION.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                // 消耗
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                showTotemEffect(stack);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        };
    }

    private static ProphecyCardEffect sprintEffect() {
        return (level, player, hand, stack) -> {
            if (!level.isClientSide()) {
                // 服务端：给予 120 秒速度 III + 跳跃提升 II + 高速反射
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 120 * 20, 2));
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 120 * 20, 1));
                player.addEffect(new MobEffectInstance(ModEffects.RAPID_REACTION_BUFF.get(), 120 * 20, 0));
                // 音效
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.EVASION.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                // 消耗
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                showTotemEffect(stack);
            }
            return InteractionResultHolder.success(stack);
        };
    }

    private static ProphecyCardEffect holygrailEffect() {
        return (level, player, hand, stack) -> {
            if (!level.isClientSide()) {
                // 服务端：给予 120 秒圣杯
                player.addEffect(new MobEffectInstance(ModEffects.HOLY_GRAIL_BUFF.get(), 120 * 20, 0));
                // 音效
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.EVASION.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                // 消耗
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                showTotemEffect(stack);
            }
            return InteractionResultHolder.success(stack);
        };
    }
    private static ProphecyCardEffect graveyardEffect() {
        return (level, player, hand, stack) -> {
            if (!level.isClientSide()) {
                // 以自身为中心，7x7x20 范围
                Vec3 center = player.position();
                AABB area = AABB.ofSize(center, 7, 20, 7);

                List<Monster> entities = level.getEntitiesOfClass(Monster.class, area,
                        e -> true);

                DamageSource VoidSource = new DamageSource(
                        level.registryAccess()
                                .registryOrThrow(Registries.DAMAGE_TYPE)
                                .getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD),
                        player);
                for (LivingEntity target : entities) {
                    target.hurt(VoidSource, Config.graveyarddamage.floatValue());
                }

                // 音效
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.EVASION.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                // 消耗
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                showTotemEffect(stack);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        };
    }
}
