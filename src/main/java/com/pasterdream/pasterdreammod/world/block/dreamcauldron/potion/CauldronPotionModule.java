package com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion;

import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 法术工厂（融梦釜）药水模块核心 —— 与原模组釜结构严格对齐，并与原有通用配方系统共存。
 *
 * 原模组釜（DreamCauldronBlockEntity，由 Menu 渲染坐标 + NBT 键名双重证实）：
 *   液罐 0（2000mb，GUI 右上 x=162 + 泉涌量条，NBT 键 FluidTank0）＝ 右槽：融梦泉涌输入
 *   液罐 1（8000mb，GUI 左侧 x=9，NBT 键 FluidTank1）　　　　　＝ 左槽：融合基底成品药水输入
 *   液罐 2（1000mb，GUI 中间 x=94，NBT 键 OutputFluidTank）　　＝ 流体输出槽：成品药水产出 / 装瓶来源
 *   物品槽 0/1/2 = 槽A（染梦果）/ 槽B（催化剂）/ 槽C（增强剂）；物品槽 3 归原模组配方输出。
 *
 * 由 {@link DreamCauldronBlockEntity#craft()} HEAD 调用 tryHandle()；
 * 命中酿造/融合/勾兑 → HANDLED 取消原配方；未命中 → PASS 交还原模组流程。
 *
 * 量纲统一（对齐原模组灵药瓶每口 250ml）：
 *   基础酿造：直接产出 250ml，保持原版/模组配方默认时长；
 *   融合：输出 = 左槽 + 250ml（典型 250 → 500ml），消耗 250ml 泉涌，
 *         每种效果保留其自身等级与时长（不再平均分摊）；
 *   勾兑：容量变化，效果与时长均不变。
 * 融梦泉涌只用于融合与勾兑，不参与基础酿造延长时长。
 * NBT 中每条效果 time 即"喝一口（250ml）"的时长，装瓶/饮用直接复用。
 */
public final class CauldronPotionModule
{
    // ===== 液罐索引（原模组真实布局，勿改！）=====
    public static final int TANK_RIGHT = 0;     // 右槽：融梦泉涌输入（2000mb，泉涌量条）
    public static final int TANK_LEFT = 1;      // 左槽：融合基底成品药水输入（8000mb）
    public static final int TANK_OUTPUT = 2;    // 流体输出槽：成品药水（1000mb，OutputFluidTank）

    // ===== 物品槽索引 =====
    public static final int SLOT_BASE = 0;      // 槽A：染梦果
    public static final int SLOT_CATALYST = 1;  // 槽B：催化剂
    public static final int SLOT_ENHANCE = 2;   // 槽C：增强剂

    public static final int DRINK_MB = 250;       // 一口 = 250ml（对齐原模组灵药瓶）
    public static final int SPRING_MB = 250;      // 泉涌投入量 = 250ml
    public static final int FUSE_MIN_LEFT = 250;  // 融合需左槽 ≥ 250ml
    public static final int FUSE_ADD_MB = 250;    // 融合新增液量 = 250ml（典型 250 → 500ml）
    public static final int OUTPUT_LIMIT = 1000;  // 输出槽上限

    public enum Outcome { PASS, HANDLED }

    /** HANDLED：已由模块处理（message 可为提示）；PASS：不属于本模块，交还原模组 */
    public record ActionResult(Outcome outcome, Component message)
    {
        public static ActionResult pass() { return new ActionResult(Outcome.PASS, null); }
        public static ActionResult handled(Component msg) { return new ActionResult(Outcome.HANDLED, msg); }
    }

    private CauldronPotionModule() {}

    // ============ 入口（craft() HEAD） ============
    public static ActionResult tryHandle(DreamCauldronBlockEntity be)
    {
        ItemStack base = itemOf(be, SLOT_BASE);
        ItemStack catalyst = itemOf(be, SLOT_CATALYST);
        ItemStack enhance = itemOf(be, SLOT_ENHANCE);
        boolean anyItem = !base.isEmpty() || !catalyst.isEmpty() || !enhance.isEmpty();

        FluidStack left = fluidOf(be, TANK_LEFT);
        FluidStack right = fluidOf(be, TANK_RIGHT);
        boolean leftPotion = PotionEffectCodec.isPotionFluid(left);

        // ---- 物品槽全空：唯一可能操作 = 勾兑 ----
        if (!anyItem)
        {
            if (leftPotion && !right.isEmpty())
            {
                if (isSpringFluid(right))
                {
                    return tryBlend(be, left, right);
                }
                if (PotionEffectCodec.isPotionFluid(right))
                {
                    if (!sameEffects(left, right))
                    {
                        return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.blend_mismatch"));
                    }
                    return tryBlend(be, left, right);
                }
                return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.blend_right_invalid"));
            }
            return ActionResult.pass();
        }

        // ---- 物品槽有内容：槽A必须是染梦果（模块签名），否则交还原模组 ----
        if (!CauldronPotionTags.isBase(base))
        {
            return ActionResult.pass();
        }

        if (left.isEmpty())
        {
            return tryBrew(be, catalyst, enhance);
        }
        if (leftPotion)
        {
            return tryFuse(be, catalyst, enhance, left, right);
        }
        // 左槽有液体但不是成品药水（纯水/粗制药水/泉涌/外来流体）
        return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.brew_left_not_empty"));
    }

    // ============ 基础酿造：产物写入流体输出槽（tank2）；融梦泉涌不参与，保持配方原始时长 ============
    private static ActionResult tryBrew(DreamCauldronBlockEntity be,
                                        ItemStack catalyst, ItemStack enhance)
    {
        // 增强剂冲突：槽B 与槽C 各放一种不同增强剂
        Optional<EnhanceChoice> choice = resolveEnhance(catalyst, enhance);
        if (choice.isEmpty())
        {
            return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.enhance_conflict"));
        }

        VanillaBrewingAdapter.BrewOutcome brew =
                VanillaBrewingAdapter.brew(catalyst, choice.get().vanilla());
        if (brew.status() == VanillaBrewingAdapter.BrewStatus.INVALID_CATALYST)
        {
            return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.invalid_catalyst"));
        }
        if (brew.status() == VanillaBrewingAdapter.BrewStatus.ENHANCE_UNSUPPORTED)
        {
            return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.enhance_unsupported"));
        }

        FluidStack output = fluidOf(be, TANK_OUTPUT);
        if (!output.isEmpty())
        {
            return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.output_not_empty"));
        }

        int outMb = DRINK_MB;
        setTank(be, TANK_OUTPUT, PotionEffectCodec.createPotionFluid(outMb, brew.effects()));
        consumeItems(be, choice.get().consumeEnhance());
        sync(be);
        return ActionResult.handled(Component.translatable("pasterdream.cauldron.brew", outMb));
    }

    // ============ 融合：左槽药水 + 新效果 → 输出槽 ============
    private static ActionResult tryFuse(DreamCauldronBlockEntity be,
                                        ItemStack catalyst, ItemStack enhance,
                                        FluidStack left, FluidStack right)
    {
        if (left.getAmount() < FUSE_MIN_LEFT)
        {
            return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.fuse_left_too_little"));
        }
        if (!hasSpring(right))
        {
            return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.fuse_need_spring"));
        }

        Optional<EnhanceChoice> choice = resolveEnhance(catalyst, enhance);
        if (choice.isEmpty())
        {
            return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.enhance_conflict"));
        }
        VanillaBrewingAdapter.BrewOutcome brew =
                VanillaBrewingAdapter.brew(catalyst, choice.get().vanilla());
        if (brew.status() == VanillaBrewingAdapter.BrewStatus.INVALID_CATALYST)
        {
            return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.invalid_catalyst"));
        }
        if (brew.status() == VanillaBrewingAdapter.BrewStatus.ENHANCE_UNSUPPORTED)
        {
            return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.enhance_unsupported"));
        }

        FluidStack output = fluidOf(be, TANK_OUTPUT);
        if (!output.isEmpty())
        {
            return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.output_not_empty"));
        }

        int total = left.getAmount() + FUSE_ADD_MB;
        if (total > OUTPUT_LIMIT)
        {
            return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.over_capacity"));
        }

        // 合并左槽已有 + 新酿造效果（同效果取高等级），每种效果保留自身等级与时长
        List<MobEffectInstance> merged = mergeEffects(PotionEffectCodec.readFluidEffects(left), brew.effects());

        setTank(be, TANK_OUTPUT, PotionEffectCodec.createPotionFluid(total, merged));
        setTank(be, TANK_LEFT, FluidStack.EMPTY);
        right.shrink(SPRING_MB);
        setTank(be, TANK_RIGHT, right);
        consumeItems(be, choice.get().consumeEnhance());
        sync(be);
        return ActionResult.handled(Component.translatable("pasterdream.cauldron.fuse", total));
    }

    // ============ 勾兑：左槽药水 + 右槽泉涌（或同效果药水）→ 输出槽 ============
    private static ActionResult tryBlend(DreamCauldronBlockEntity be, FluidStack left, FluidStack right)
    {
        FluidStack output = fluidOf(be, TANK_OUTPUT);
        if (!output.isEmpty())
        {
            return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.output_not_empty"));
        }

        int total = left.getAmount() + right.getAmount();
        int outMb = Math.min(total, OUTPUT_LIMIT);
        // 勾兑只改变容量，效果与时长均不变
        List<MobEffectInstance> effects = PotionEffectCodec.readFluidEffects(left);

        setTank(be, TANK_OUTPUT, PotionEffectCodec.createPotionFluid(outMb, effects));
        setTank(be, TANK_LEFT, FluidStack.EMPTY);
        int usedRight = outMb - left.getAmount();
        right.shrink(usedRight);
        setTank(be, TANK_RIGHT, right);
        sync(be);
        return ActionResult.handled(Component.translatable("pasterdream.cauldron.blend", outMb));
    }

    // ============ 装瓶（空灵药瓶右键釜：输出槽药水灌入灵药瓶，瓶容量 1000ml） ============
    public static ActionResult tryBottle(DreamCauldronBlockEntity be,
                                         net.minecraft.world.entity.player.Player player,
                                         net.minecraft.world.InteractionHand hand)
    {
        ItemStack held = player.getItemInHand(hand);
        if (held.getItem() != ModItems.ELIXIR_BOTTLE.get())
        {
            return ActionResult.pass();
        }
        // 必须是空灵药瓶（已装液体的瓶子交还原逻辑，打开釜界面）
        if (!com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle.ElixirBottleItem
                .getElixirBottleFluidStack(held).isEmpty())
        {
            return ActionResult.pass();
        }
        FluidStack output = fluidOf(be, TANK_OUTPUT);
        if (!PotionEffectCodec.isPotionFluid(output) || output.isEmpty())
        {
            return ActionResult.handled(Component.translatable("pasterdream.cauldron.error.bottle_empty"));
        }

        // 灵药瓶容量 1000ml，饮用由灵药瓶按每口 250ml 结算
        int take = Math.min(output.getAmount(), 1000);
        List<MobEffectInstance> effects = PotionEffectCodec.readFluidEffects(output);

        output.shrink(take);
        setTank(be, TANK_OUTPUT, output);
        sync(be);

        ItemStack bottle = com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle.
                ElixirBottleWithFluidNBTBuilder.builder(PotionEffectCodec.createPotionFluid(take, effects));
        if (!player.getAbilities().instabuild)
        {
            held.shrink(1);
        }
        if (!player.addItem(bottle))
        {
            player.drop(bottle, false);
        }
        return ActionResult.handled(Component.translatable("pasterdream.cauldron.bottle", take));
    }

    // ============ 工具 ============
    static ItemStack itemOf(DreamCauldronBlockEntity be, int slot)
    {
        return be.getItemHandler().getStackInSlot(slot);
    }

    static FluidStack fluidOf(DreamCauldronBlockEntity be, int tank)
    {
        return be.getFluidTank(tank).getFluid();
    }

    static void setTank(DreamCauldronBlockEntity be, int tank, FluidStack stack)
    {
        be.getFluidTank(tank).setFluid(stack == null || stack.isEmpty() ? FluidStack.EMPTY : stack);
    }

    /** setChanged + 方块数据同步 */
    static void sync(DreamCauldronBlockEntity be)
    {
        be.setChanged();
        if (be.getLevel() != null && !be.getLevel().isClientSide())
        {
            be.getLevel().sendBlockUpdated(be.getBlockPos(), be.getBlockState(), be.getBlockState(), 3);
        }
    }

    static boolean isSpringFluid(FluidStack fs)
    {
        return fs != null && !fs.isEmpty() && fs.getFluid() == ModFluids.MELT_DREAM_LIQUID.get();
    }

    /** 右槽是否有 ≥250ml 融梦泉涌 */
    static boolean hasSpring(FluidStack right)
    {
        return isSpringFluid(right) && right.getAmount() >= SPRING_MB;
    }

    /** 融合效果合并：同效果 ID 取较高等级，新效果追加；各效果保留自身时长 */
    static List<MobEffectInstance> mergeEffects(List<MobEffectInstance> existing, List<MobEffectInstance> added)
    {
        List<MobEffectInstance> out = new ArrayList<>(existing);
        for (MobEffectInstance add : added)
        {
            boolean merged = false;
            for (int i = 0; i < out.size(); i++)
            {
                if (out.get(i).getEffect() == add.getEffect())
                {
                    if (add.getAmplifier() > out.get(i).getAmplifier())
                    {
                        out.set(i, add);
                    }
                    merged = true;
                    break;
                }
            }
            if (!merged)
            {
                out.add(add);
            }
        }
        return out;
    }

    /** 勾兑效果一致判定：效果 ID + 等级完全一致（时长忽略） */
    static boolean sameEffects(FluidStack a, FluidStack b)
    {
        List<MobEffectInstance> ea = PotionEffectCodec.readFluidEffects(a);
        List<MobEffectInstance> eb = PotionEffectCodec.readFluidEffects(b);
        if (ea.size() != eb.size()) return false;
        for (MobEffectInstance x : ea)
        {
            boolean found = false;
            for (MobEffectInstance y : eb)
            {
                if (x.getEffect() == y.getEffect() && x.getAmplifier() == y.getAmplifier())
                {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    /** 增强剂解析：槽C 空/红石类/荧石类；槽B 误放增强剂或槽B与槽C放不同增强剂 → 冲突 */
    record EnhanceChoice(VanillaBrewingAdapter.Enhance vanilla, boolean consumeEnhance) {}

    static Optional<EnhanceChoice> resolveEnhance(ItemStack catalyst, ItemStack enhance)
    {
        boolean catRed = CauldronPotionTags.isRedstoneEnhance(catalyst);
        boolean catGlow = CauldronPotionTags.isGlowstoneEnhance(catalyst);
        boolean enhRed = CauldronPotionTags.isRedstoneEnhance(enhance);
        boolean enhGlow = CauldronPotionTags.isGlowstoneEnhance(enhance);

        // 槽B 与槽C 各放一种不同的增强剂 → 冲突
        if ((catRed || catGlow) && (enhRed || enhGlow))
        {
            boolean catIsRed = catRed;
            boolean enhIsRed = enhRed;
            if (catIsRed != enhIsRed)
            {
                return Optional.empty();
            }
        }

        if (enhance.isEmpty())
        {
            return Optional.of(new EnhanceChoice(VanillaBrewingAdapter.Enhance.NONE, false));
        }
        if (enhRed)
        {
            return Optional.of(new EnhanceChoice(VanillaBrewingAdapter.Enhance.REDSTONE, true));
        }
        if (enhGlow)
        {
            return Optional.of(new EnhanceChoice(VanillaBrewingAdapter.Enhance.GLOWSTONE, true));
        }
        // 槽C 放了非增强剂物品 → 视为无效配方组合
        return Optional.of(new EnhanceChoice(VanillaBrewingAdapter.Enhance.NONE, false));
    }

    static void consumeItems(DreamCauldronBlockEntity be, boolean consumeEnhance)
    {
        shrinkOne(be, SLOT_BASE);
        shrinkOne(be, SLOT_CATALYST);
        if (consumeEnhance)
        {
            shrinkOne(be, SLOT_ENHANCE);
        }
    }

    static void shrinkOne(DreamCauldronBlockEntity be, int slot)
    {
        ItemStack stack = itemOf(be, slot);
        stack.shrink(1);
        be.getItemHandler().setStackInSlot(slot, stack);
    }

    /** craft() 无玩家上下文，取 8 格内最近玩家作为操作者 */
    public static net.minecraft.server.level.ServerPlayer nearestPlayer(DreamCauldronBlockEntity be)
    {
        if (!(be.getLevel() instanceof net.minecraft.server.level.ServerLevel sl)) return null;
        net.minecraft.core.BlockPos pos = be.getBlockPos();
        net.minecraft.world.phys.AABB box = new net.minecraft.world.phys.AABB(pos).inflate(8);
        List<net.minecraft.server.level.ServerPlayer> players =
                sl.getEntities(net.minecraft.world.level.entity.EntityTypeTest.forClass(net.minecraft.server.level.ServerPlayer.class), box, p -> true);
        net.minecraft.server.level.ServerPlayer best = null;
        double bestDist = 8 * 8 + 1;
        for (net.minecraft.server.level.ServerPlayer p : players)
        {
            double d = p.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            if (d < bestDist) { bestDist = d; best = p; }
        }
        return best;
    }
}
