package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 灵药瓶（装药水）—— 单一物品实例，通过 NBT 承载任意原版药水。
 * <p>
 * 液体颜色由 ItemColor（见 {@code PasterDreamMod#registerItemColors}）按
 * {@link PotionUtils#getColor} 对模型 layer1 染色；饮用时施加药水效果并返还空灵药瓶。
 */
public class ElixirBottleOfPotionItem extends Item {

    // 可饮用次数；用自定义 NBT 计数，不启用原版 durability，避免被附魔「耐久」和「经验修补」
    private static final int MAX_USES = 4;
    private static final String TAG_USES = "ElixirUses";
    // 耐久条颜色（粉色）
    private static final int BAR_COLOR = 0xFFFF69B4;

    public ElixirBottleOfPotionItem() {
        // 不调用 durability()：原版耐久会让物品成为 DamageableItem，从而可被附魔「耐久/经验修补」
        super(new Item.Properties().stacksTo(1));
    }

    /** 剩余饮用次数；NBT 未写入时视为满次数（新做出来的瓶子天然是满的）。 */
    private static int getRemainingUses(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return (tag != null && tag.contains(TAG_USES)) ? tag.getInt(TAG_USES) : MAX_USES;
    }

    @Override
    public ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.WATER);
    }

    @Override
    public Component getName(ItemStack stack) {
        Potion potion = PotionUtils.getPotion(stack);
        Component typeName = null;

        List<MobEffectInstance> effects = PotionUtils.getMobEffects(stack);
        if (!effects.isEmpty()) {
            // 有药水效果：用首个效果的显示名（如「夜视」「力量」）
            typeName = effects.get(0).getEffect().getDisplayName();
        } else {
            // 无效果基底药水（水/平凡/浓稠/粗制/空）：用注册名
            ResourceLocation key = BuiltInRegistries.POTION.getKey(potion);
            if (key != null && !"empty".equals(key.getPath())) {
                typeName = Component.literal(key.getPath());
            }
        }

        if (typeName == null) {
            return super.getName(stack);
        }
        // 「药水灵药瓶-夜视」这种格式
        return super.getName(stack).copy().append(Component.literal("-")).append(typeName);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        // 始终显示耐久条，用于展示剩余饮用次数
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F * getRemainingUses(stack) / (float) MAX_USES);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BAR_COLOR;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        Player player = entity instanceof Player p ? p : null;
        if (player instanceof ServerPlayer sp) {
            CriteriaTriggers.CONSUME_ITEM.trigger(sp, stack);
        }

        if (!level.isClientSide) {
            for (MobEffectInstance effect : PotionUtils.getMobEffects(stack)) {
                if (effect.getEffect().isInstantenous()) {
                    effect.getEffect().applyInstantenousEffect(player, player, entity, effect.getAmplifier(), 1.0D);
                } else {
                    entity.addEffect(new MobEffectInstance(effect));
                }
            }
        }

        if (player == null || !player.getAbilities().instabuild) {
            int remaining = getRemainingUses(stack);
            if (remaining <= 1) {
                // 喝完最后一次：返还空灵药瓶
                return new ItemStack(ModItems.ELIXIR_BOTTLE.get());
            }
            stack.getOrCreateTag().putInt(TAG_USES, remaining - 1);
        }
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.pasterdreammod.elixir_bottle_of_potion.uses", getRemainingUses(stack)));
        PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
    }
}
