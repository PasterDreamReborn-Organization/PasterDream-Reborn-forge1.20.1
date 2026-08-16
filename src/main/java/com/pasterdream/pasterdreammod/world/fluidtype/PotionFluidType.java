package com.pasterdream.pasterdreammod.world.fluidtype;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.fluid.PotionFluidHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;

public class PotionFluidType extends PasterDreamBaseFluidType
{
    public static final FluidType TYPE = new PotionFluidType();

    private PotionFluidType()
    {
        super(Properties.create().density(1000).viscosity(1000).temperature(300).canExtinguish(true).canSwim(true)
                        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY),
                ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_still"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_flow"),
                0xFF9F41D0);
    }

    @Override
    public String getDescriptionId()
    {
        return "fluid." + PasterDreamMod.MOD_ID + ".potion";
    }

    /** 显示名按流体 NBT 携带的药水效果类型动态生成，如「药水（夜视）」 */
    @Override
    public Component getDescription(FluidStack stack)
    {
        Potion potion = PotionFluidHelper.getPotion(stack);
        if (potion != null && !potion.getEffects().isEmpty())
        {
            return Component.translatable("fluid." + PasterDreamMod.MOD_ID + ".potion.with_effect",
                    potion.getEffects().get(0).getEffect().getDisplayName());
        }
        return super.getDescription(stack);
    }

    /** 按流体 NBT 携带的药水渲染原版药水颜色（PotionUtils.getColor） */
    @Override
    protected int getStackTintColor(FluidStack stack)
    {
        Potion potion = PotionFluidHelper.getPotion(stack);
        if (potion != null)
        {
            // PotionUtils.getColor 返回 0xRRGGBB，流体染色需补全 alpha
            return 0xFF000000 | PotionUtils.getColor(potion);
        }
        return super.getStackTintColor(stack);
    }
}
