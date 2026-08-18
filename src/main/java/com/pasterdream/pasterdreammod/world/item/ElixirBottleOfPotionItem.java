package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.fluid.PotionFluidHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 灵药瓶（装药水）—— 单一物品实例，直接持有通用 {@link FluidTank}，
 * tank 里装的是带 NBT 的药水 {@link net.minecraftforge.fluids.FluidStack}（见 {@link PotionFluidHelper}）。
 * <p>
 * 液体颜色由 ItemColor（见 {@code PasterDreamMod#registerItemColors}）按 {@link PotionUtils#getColor} 对模型 layer1 染色；
 * 饮用时从 tank 抽出 250mB 并施加对应药水效果，抽空后返还空灵药瓶。
 */
public class ElixirBottleOfPotionItem extends Item {

    // 可饮用次数；用流体量（mB）计数，不启用原版 durability，避免被附魔「耐久」和「经验修补」
    public static final int MAX_USES = 4;
    /** 每次可饮用次数对应的药水流体量（mB），供流体容器能力换算使用 */
    public static final int FLUID_AMOUNT_PER_USE = 250;
    /** 满瓶容量（mB） */
    public static final int CAPACITY = MAX_USES * FLUID_AMOUNT_PER_USE;
    /** 物品 NBT 中承载 FluidTank 的键 */
    public static final String TAG_FLUID = "Fluid";
    // 耐久条颜色（粉色）
    private static final int BAR_COLOR = 0xFFFF69B4;

    public ElixirBottleOfPotionItem() {
        // 不调用 durability()：原版耐久会让物品成为 DamageableItem，从而可被附魔「耐久/经验修补」
        super(new Item.Properties().stacksTo(1));
    }

    /** 从物品 NBT 构建流体罐；NBT 未写入时为空罐。 */
    public static FluidTank getFluidTank(ItemStack stack) {
        FluidTank tank = new FluidTank(CAPACITY, fluid -> fluid.getFluid() == ModFluids.POTION.get());
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TAG_FLUID, Tag.TAG_COMPOUND)) {
            tank.readFromNBT(tag.getCompound(TAG_FLUID));
        }
        return tank;
    }

    /** 把流体罐写回物品 NBT。 */
    public static void saveFluidTank(ItemStack stack, FluidTank tank) {
        stack.getOrCreateTag().put(TAG_FLUID, tank.writeToNBT(new CompoundTag()));
    }

    /** 从 tank 中的药水流体解析药水；缺失/未知回退为「空」药水。 */
    public static Potion getPotion(ItemStack stack) {
        Potion potion = PotionFluidHelper.getPotion(getFluidTank(stack).getFluid());
        return potion != null ? potion : Potions.EMPTY;
    }

    /** 剩余饮用次数；按当前流体量换算（每 250mB = 1 次）。 */
    public static int getRemainingUses(ItemStack stack) {
        return getFluidTank(stack).getFluidAmount() / FLUID_AMOUNT_PER_USE;
    }

    /** 构建装满指定药水的满瓶灵药瓶（供创造栏/默认实例使用）。 */
    public static ItemStack withPotion(Potion potion) {
        ItemStack stack = new ItemStack(ModItems.ELIXIR_BOTTLE_OF_POTION.get());
        FluidTank tank = getFluidTank(stack);
        tank.fill(PotionFluidHelper.createStack(potion, CAPACITY), FluidAction.EXECUTE);
        saveFluidTank(stack, tank);
        return stack;
    }

    @Override
    public ItemStack getDefaultInstance() {
        return withPotion(Potions.WATER);
    }

    @Override
    public Component getName(ItemStack stack) {
        Potion potion = getPotion(stack);
        Component typeName = null;

        List<MobEffectInstance> effects = potion.getEffects();
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
        return Math.round(13.0F * getFluidTank(stack).getFluidAmount() / (float) CAPACITY);
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
            for (MobEffectInstance effect : getPotion(stack).getEffects()) {
                if (effect.getEffect().isInstantenous()) {
                    effect.getEffect().applyInstantenousEffect(player, player, entity, effect.getAmplifier(), 1.0D);
                } else {
                    entity.addEffect(new MobEffectInstance(effect));
                }
            }
        }

        if (player == null || !player.getAbilities().instabuild) {
            FluidTank tank = getFluidTank(stack);
            tank.drain(FLUID_AMOUNT_PER_USE, FluidAction.EXECUTE);
            saveFluidTank(stack, tank);
            if (tank.getFluidAmount() <= 0) {
                // 喝完最后一次：返还空灵药瓶
                return new ItemStack(ModItems.ELIXIR_BOTTLE.get());
            }
        }
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.pasterdreammod.elixir_bottle_of_potion.uses", getRemainingUses(stack)));
        // 复用原版药水效果 tooltip 格式
        ItemStack potionStack = PotionUtils.setPotion(new ItemStack(Items.POTION), getPotion(stack));
        PotionUtils.addPotionTooltip(potionStack, tooltip, 1.0F);
    }
}
