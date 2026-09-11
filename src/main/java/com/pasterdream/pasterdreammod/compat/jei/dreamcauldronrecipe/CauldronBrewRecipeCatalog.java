package com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronRecipe;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.CauldronPotionFluidTags;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.CauldronPotionModule;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.CauldronPotionTags;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.PotionEffectCodec;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.VanillaBrewingAdapter;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.VanillaBrewingAdapter.BrewOutcome;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.VanillaBrewingAdapter.BrewStatus;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.VanillaBrewingAdapter.Enhance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * 法术工厂药水模块 JEI 模板页。
 * 直接复用原模组 DreamCauldronRecipeCategory 界面：物品输入槽纵向排布（基底 / 催化剂 / 增强剂），
 * 流体槽 0 = 右槽融梦泉涌、流体槽 1 = 左槽基底药水、输出 = 成品药水。
 * 共三类页：① 基础酿造（无融梦）② 融合（右槽泉涌 + 左槽药水，各效果保留自身等级与时长）③ 勾兑（无物品）。
 *
 * 基础酿造为「全量配方」：遍历原版/模组酿造注册表内全部催化剂 × 增强剂（无 / 红石延长 / 萤石强化），
 * 每一种可从 Awkward 基底成功酿造的催化剂都独立生成一条 JEI 配方，玩家无需再去翻酿造台查配方。
 * 催化剂槽显示具体物品，基底（染梦果）与增强剂走标签，数据包可自由扩展。
 * 融梦泉涌 / 基底药水液体输入同样走流体标签（pasterdream:cauldron_spring / cauldron_potion），JEI 遍历标签内流体循环展示。
 * 受 Config.dreamCauldronPotionEnabled 总开关控制：关闭时返回空列表，隐藏全部模板页。
 */
public final class CauldronBrewRecipeCatalog
{
    private static final int DRINK_MB = CauldronPotionModule.DRINK_MB;    // 250ml
    private static final int SPRING_MB = CauldronPotionModule.SPRING_MB;  // 250ml
    private static final int BOTTLE_CAPACITY = 1000;                       // 灵药瓶满瓶容量

    private CauldronBrewRecipeCatalog() {}

    public static List<DreamCauldronRecipe> createAll()
    {
        // 药水模块总开关关闭：不注册任何 JEI 模板页
        if (!Config.dreamCauldronPotionEnabled)
        {
            return List.of();
        }

        List<DreamCauldronRecipe> recipes = new ArrayList<>();

        // ① 基础酿造（无融梦）：遍历酿造注册表内全部催化剂，每催化剂一条配方（增强剂槽位标签可选）
        addBasicBrewRecipes(recipes);

        // ② 融合（右槽泉涌 + 左槽药水）：左槽 250ml 药水 + 右槽 250ml 泉涌 + 染梦果 + 催化剂
        //    → 500ml 多效药水，各效果保留自身等级与时长（示例：迅捷 + 力量）
        List<MobEffectInstance> speed = brewEffects(new ItemStack(Items.SUGAR), Enhance.NONE);
        List<MobEffectInstance> strength = brewEffects(new ItemStack(Items.BLAZE_POWDER), Enhance.NONE);
        List<ItemIngredient> fuseItems = List.of(
                ItemIngredient.of(CauldronPotionTags.BASE, 1),
                ItemIngredient.of(CauldronPotionTags.CATALYST, 1),
                ItemIngredient.of(CauldronPotionTags.ENHANCE, 1));
        recipes.add(build("cauldron_potion/fuse",
                List.of(spring(), potion(DRINK_MB, speed)), fuseItems,
                DRINK_MB + CauldronPotionModule.FUSE_ADD_MB, merge(speed, strength)));

        // ③ 勾兑（无物品）：左槽药水 + 右槽泉涌（或同效果药水）→ 容量合并，效果与时长不变
        recipes.add(build("cauldron_potion/blend",
                List.of(spring(), potion(DRINK_MB, speed)), List.of(),
                DRINK_MB + SPRING_MB, speed));

        return recipes;
    }

    /** 基础酿造：自动扫描全部注册物品，凡从 Awkward 基底经【酿造台注册表】能效力出效果药水的物品即为催化剂，每条一条 JEI 配方。
     *  与真实酿造釜判定完全一致（釜内 catalyst 不查 cauldron_catalyst 标签，直接走 VanillaBrewingAdapter/酿造注册表），
     *  因此模组药水 + 原版药水会一并列出。增强剂不拆分页码，用合并标签 cauldron_enhance（红石/萤石粉在增强槽位循环展示、可选添加）。 */
    private static void addBasicBrewRecipes(List<DreamCauldronRecipe> recipes)
    {
        ItemStack awkward = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD);
        for (Item item : ForgeRegistries.ITEMS.getValues())
        {
            ItemStack probe = new ItemStack(item);
            if (probe.isEmpty())
            {
                continue;
            }
            ItemStack mixed = BrewingRecipeRegistry.getOutput(awkward.copy(), probe);
            if (mixed.isEmpty())
            {
                continue;
            }
            Potion potion = PotionUtils.getPotion(mixed);
            if (potion == null || potion.getEffects().isEmpty())
            {
                continue; // 产物无效果（如粗/浑浊/延长空白）不算有效催化剂
            }
            recipes.add(build("cauldron_potion/brew/" + registryStem(item),
                    List.of(), baseBrewInputs(item), DRINK_MB, new ArrayList<>(potion.getEffects())));
        }
    }

    /** 基础酿造物品输入：基底（染梦果，标签）+ 具体催化剂 + 增强剂标签（红石 / 萤石粉可选，JEI 在槽位循环展示两种） */
    private static List<ItemIngredient> baseBrewInputs(Item catalyst)
    {
        return List.of(
                ItemIngredient.of(CauldronPotionTags.BASE, 1),
                ItemIngredient.of(catalyst, 1),
                ItemIngredient.of(CauldronPotionTags.ENHANCE, 1));
    }

    private static DreamCauldronRecipe build(String path,
                                             List<FluidIngredient> fluidInputs,
                                             List<ItemIngredient> itemInputs,
                                             int outMb,
                                             List<MobEffectInstance> effects)
    {
        FluidStack out = PotionEffectCodec.createPotionFluid(outMb, effects);
        return new DreamCauldronRecipe(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path),
                fluidInputs,
                itemInputs,
                List.of(FluidIngredient.of(ModFluids.POTION.get(), outMb, out.getTag())),
                List.of(outputBottle(effects)));
    }

    /**
     * 输出物品 = 装满 1000ml 成品药水的灵药瓶（与批量药水瓶同款 NBT，用于 JEI）。
     * 目的：JEI 对物品默认不看 NBT（除非注册 subtype），故给每条配方挂一个灵药瓶输出后，
     * 玩家对着任意灵药瓶按 R 即可命中对应酿造配方，无需去切流体视图。
     */
    private static ItemIngredient outputBottle(List<MobEffectInstance> effects)
    {
        ItemStack bottle = new ItemStack(ModItems.ELIXIR_BOTTLE.get());
        FluidHandlerItemStack handler = new FluidHandlerItemStack(bottle, BOTTLE_CAPACITY);
        handler.fill(PotionEffectCodec.createPotionFluid(BOTTLE_CAPACITY, effects),
                IFluidHandler.FluidAction.EXECUTE);
        CompoundTag tag = bottle.getTag();
        return tag == null
                ? ItemIngredient.of(ModItems.ELIXIR_BOTTLE.get(), 1)
                : ItemIngredient.of(ModItems.ELIXIR_BOTTLE.get(), 1, tag);
    }

    /** 右槽融梦泉涌输入：走流体标签，JEI 遍历标签内流体循环展示 */
    private static FluidIngredient spring()
    {
        return FluidIngredient.of(CauldronPotionFluidTags.SPRING, SPRING_MB);
    }

    /** 左槽基底成品药水：走流体标签 + 示例 EffectList NBT，JEI 遍历标签内流体循环展示 */
    private static FluidIngredient potion(int mb, List<MobEffectInstance> effects)
    {
        FluidStack fs = PotionEffectCodec.createPotionFluid(mb, effects);
        return FluidIngredient.of(CauldronPotionFluidTags.POTION, mb, fs.getTag());
    }

    /** 催化剂注册名拼接成唯一配方 id 片段（namespace_path，无冒号） */
    private static String registryStem(Item item)
    {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);
        return key == null ? "unknown" : key.getNamespace() + "_" + key.getPath();
    }

    /** 融合效果合并：同效果取高等级，新效果追加；各效果保留自身时长 */
    private static List<MobEffectInstance> merge(List<MobEffectInstance> a, List<MobEffectInstance> b)
    {
        List<MobEffectInstance> out = new ArrayList<>(a);
        for (MobEffectInstance add : b)
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

    /** 原版 Awkward 链酿造效果（供融合 / 勾兑模板示例使用） */
    private static List<MobEffectInstance> brewEffects(ItemStack catalyst, Enhance enhance)
    {
        BrewOutcome brew = VanillaBrewingAdapter.brew(catalyst, enhance);
        return brew.status() == BrewStatus.OK
                ? new ArrayList<>(brew.effects()) : new ArrayList<>();
    }
}
