package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import com.pasterdream.pasterdreammod.world.entity.ThrownPotionBottle;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 药剂瓶物品 —— 单一物品实例，通过 NBT 中的 string 区分不同药剂类型。
 * 类型由 {@link #registerPotionType(String)} 注册，效果由 {@link #registerEffect(String, PotionBottleEffect)}
 * 在 commonSetup 中绑定。模型 predicate 由 NBT 字符串的 hashCode 直接计算（哈希兜底）。
 */
public class PotionBottleItem extends Item {

    private static final Logger LOGGER = LoggerFactory.getLogger(PotionBottleItem.class);

    /** NBT标签键：药剂类型 */
    public static final String TAG_POTION_TYPE = "PotionType";

    // === 内置药剂类型常量 ===
    public static final String TYPE_BERSERK = "berserk";
    public static final String TYPE_FROZEN = "frozen";
    public static final String TYPE_HIGHLY_TOXIC = "highly_toxic";
    public static final String TYPE_LIGHTNING = "lightning";
    public static final String TYPE_REJUVENATION = "rejuvenation";

    // === 效果绑定（commonSetup 中注册） ===

    /** 药剂类型 → 砸碎效果的映射 */
    private static final Map<String, PotionBottleEffect> EFFECTS = new LinkedHashMap<>();

    /**
     * 药剂瓶砸碎时触发的效果接口。
     * <p>
     * {@link #onBottleBreak} 在命中瞬间立即执行；
     * {@link #getDelayedActions()} 返回按 tick（从命中瞬间起算）排期的延迟动作，
     * 由投掷物实体自动调度执行，全部完成后实体移除。
     */
    @FunctionalInterface
    public interface PotionBottleEffect {
        /**
         * 药瓶砸碎时立即触发。
         *
         * @param stack   药剂瓶物品堆（含 NBT 类型信息）
         * @param level   所在世界（服务端）
         * @param thrower 投掷者（Player 或其它模组实体）
         * @param hitPos  命中坐标
         */
        void onBottleBreak(ItemStack stack, Level level, LivingEntity thrower, Vec3 hitPos);

        /**
         * 返回延迟触发的动作映射：key=tick（从命中瞬间起算），value=该tick要执行的动作。
         * 默认无延迟动作。所有动作执行完毕后实体自动移除。
         */
        default Map<Integer, Runnable> getDelayedActions() {
            return Map.of();
        }
    }

    /**
     * 为指定药剂类型绑定砸碎效果。在 commonSetup 中调用。
     *
     * @param type   药剂类型字符串
     * @param effect 砸碎效果，传 null 可清除
     */
    public static void registerEffect(String type, @Nullable PotionBottleEffect effect) {
        if (effect == null) {
            EFFECTS.remove(type);
        } else {
            EFFECTS.put(type, effect);
            LOGGER.info("Registered effect for PotionBottle type '{}'", type);
        }
    }

    /** 获取指定类型已绑定的效果，未绑定返回 null */
    @Nullable
    public static PotionBottleEffect getEffect(String type) {
        return EFFECTS.get(type);
    }

    // === 类型注册 ===

    /** 已注册的药剂类型集合 */
    private static final Set<String> REGISTERED_TYPES = new LinkedHashSet<>();

    static {
        REGISTERED_TYPES.add(TYPE_BERSERK);
        REGISTERED_TYPES.add(TYPE_FROZEN);
        REGISTERED_TYPES.add(TYPE_HIGHLY_TOXIC);
        REGISTERED_TYPES.add(TYPE_LIGHTNING);
        REGISTERED_TYPES.add(TYPE_REJUVENATION);
    }

    private final String potionType;

    /**
     * @param potionType 药剂类型标识字符串，传空字符串表示基础物品（不自动附加 NBT）
     */
    public PotionBottleItem(String potionType) {
        super(new Item.Properties().stacksTo(8));
        this.potionType = potionType;
    }

    /** 获取此物品实例绑定的药剂类型字符串（可能为空） */
    public String getPotionType() {
        return potionType;
    }

    /**
     * 注册一个新的药剂类型。
     * 其它模组在 FMLCommonSetupEvent 或 mod 构造器中调用即可。
     *
     * @param typeName 类型名称（建议全小写+下划线，如 "my_potion"）
     * @return true 为新注册，false 为已存在
     */
    public static boolean registerPotionType(String typeName) {
        if (REGISTERED_TYPES.contains(typeName)) {
            LOGGER.debug("PotionBottle type '{}' already registered, skipping.", typeName);
            return false;
        }
        REGISTERED_TYPES.add(typeName);
        LOGGER.info("Registered PotionBottle type '{}' (total: {})", typeName, REGISTERED_TYPES.size());
        return true;
    }

    /** 获取所有已注册的类型的不可变视图（供 KJS / 拓展模组遍历） */
    public static Set<String> getRegisteredTypes() {
        return java.util.Collections.unmodifiableSet(REGISTERED_TYPES);
    }

    /** 检查一个类型名是否已被注册 */
    public static boolean isKnownType(String typeName) {
        return !typeName.isEmpty() && REGISTERED_TYPES.contains(typeName);
    }

    // ===== 模型 predicate（由 NBT 字符串 hashCode 直接计算） =====

    /**
     * 根据类型名字符串直接计算 predicate 值。
     * 使用 Java {@link String#hashCode()} 取绝对值后映射到 [0.1, 0.999]，
     * 资源包作者可计算相应字符串的此值来编写模型 JSON overrides。
     */
    public static float getPredicateForType(String typeName) {
        if (typeName.isEmpty()) return 0.0f;
        // & 0x7FFFFFFF 清除符号位避免 Math.abs(Integer.MIN_VALUE) 的边界问题
        return 0.1f + ((typeName.hashCode() & 0x7FFFFFFF) % 900) / 1000.0f;
    }

    /**
     * 获取用于 ItemProperties / 模型 predicate 的浮点值
     */
    public static float getPredicateValue(ItemStack stack) {
        return getPredicateForType(getPotionType(stack));
    }

    // ===== NBT 读写 =====

    /** 从 ItemStack 的 NBT 中读取药剂类型 */
    @NotNull
    public static String getPotionType(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TAG_POTION_TYPE, Tag.TAG_STRING)) {
            return tag.getString(TAG_POTION_TYPE);
        }
        return "";
    }

    /** 向 ItemStack 的 NBT 中写入药剂类型 */
    public static void setPotionType(ItemStack stack, String type) {
        stack.getOrCreateTag().putString(TAG_POTION_TYPE, type);
    }

    /** 创建一个绑定指定药剂类型NBT的ItemStack */
    public static ItemStack createWithType(Item item, String potionType) {
        ItemStack stack = new ItemStack(item);
        setPotionType(stack, potionType);
        return stack;
    }

    /** 默认实例：仅当构造时绑定了有效类型时才自动附加 NBT */
    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        if (!this.potionType.isEmpty()) {
            setPotionType(stack, this.potionType);
        }
        return stack;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        String type = getPotionType(stack);
        if (isKnownType(type)) {
            return Component.translatable("item.pasterdream.potion_bottle." + type);
        }
        return super.getName(stack);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player,
                                                           @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            ThrownPotionBottle bottle = new ThrownPotionBottle(level, player);
            bottle.setItem(stack.copy());
            bottle.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.0F, 1.0F);
            level.addFreshEntity(bottle);
        }
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level,
                                @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        String type = getPotionType(stack);
        switch (type) {
            case TYPE_BERSERK-> {
                tooltip.add(Component.translatable("tooltip.pasterdream.potion_bottle.berserk.description.1"));
                tooltip.add(Component.translatable("tooltip.pasterdream.potion_bottle.berserk.description.2"));
            }
            case TYPE_FROZEN->{
                tooltip.add(Component.translatable("tooltip.pasterdream.potion_bottle.frozen.description.1"));
                tooltip.add(Component.translatable("tooltip.pasterdream.potion_bottle.frozen.description.2"));
            }
            case TYPE_HIGHLY_TOXIC->
                tooltip.add(Component.translatable("tooltip.pasterdream.potion_bottle.highly_toxic.description"));
            case TYPE_LIGHTNING->{
                tooltip.add(Component.translatable("tooltip.pasterdream.potion_bottle.lightning.description.1"));
                tooltip.add(Component.translatable("tooltip.pasterdream.potion_bottle.lightning.description.2"));
            }
            case TYPE_REJUVENATION->
                tooltip.add(Component.translatable("tooltip.pasterdream.potion_bottle.rejuvenation.description"));
        }
    }

}

