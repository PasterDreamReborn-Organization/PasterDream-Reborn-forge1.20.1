package com.pasterdream.pasterdreammod.helper.pasterdreamingredient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FluidIngredient
{
    @Nullable
    private final Fluid fluid;
    @Nullable
    private final TagKey<Fluid> tag;
    private final int amount;
    @Nullable
    private final CompoundTag nbt;

    private FluidIngredient(@Nullable Fluid fluid, @Nullable TagKey<Fluid> tag, int amount, @Nullable CompoundTag nbt)
    {
        this.fluid = fluid;
        this.tag = tag;
        this.amount = amount;
        this.nbt = nbt;
    }

    public static FluidIngredient of(Fluid fluid, int amount)
    {
        return new FluidIngredient(fluid, null, amount, null);
    }

    public static FluidIngredient of(TagKey<Fluid> tag, int amount)
    {
        return new FluidIngredient(null, tag, amount, null);
    }

    public static FluidIngredient of(Fluid fluid, int amount, CompoundTag nbt)
    {
        return new FluidIngredient(fluid, null, amount, nbt);
    }

    public static FluidIngredient of(TagKey<Fluid> tag, int amount, CompoundTag nbt)
    {
        return new FluidIngredient(null, tag, amount, nbt);
    }

    public boolean test(FluidStack stack)
    {
        if (stack.getAmount() < amount)
        {
            return false;
        }

        if (fluid != null)
        {
            return stack.getFluid() == fluid;
        }

        if (tag != null)
        {
            return stack.getFluid().is(tag);
        }

        if (nbt != null)
        {
            return nbt.equals(stack.getTag());
        }

        return true;
    }

    public static FluidIngredient fromJson(JsonElement json)
    {
        JsonObject obj = json.getAsJsonObject();
        int amount = obj.has("amount") ? obj.get("amount").getAsInt() : 1;

        CompoundTag nbt = null;
        if (obj.has("nbt"))
        {
            try
            {
                nbt = net.minecraft.nbt.TagParser.parseTag(obj.get("nbt").getAsString());
            }
                catch (Exception e)
                {
                    nbt = null;
                    System.err.println("NBT解析出错，" + e);
                }
        }

        if (obj.has("fluid"))
        {
            String fluidName = obj.get("fluid").getAsString();
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(ResourceLocation.parse(fluidName));
            if (fluid == null)
            {
                throw new IllegalArgumentException("无法解析的流体：" + fluidName);
            }

            return FluidIngredient.of(fluid, amount, nbt);
        }
        else
            if (obj.has("fluidTag"))
            {
                String tagName = obj.get("fluidTag").getAsString();
                TagKey<Fluid> tag = TagKey.create(Registries.FLUID, ResourceLocation.parse(tagName));
                return FluidIngredient.of(tag, amount, nbt);
            }
                else
                {
                    throw new IllegalArgumentException("输入流体必须填写\"fluid\"或\"fluidTag\"");
                }
    }

    public static FluidIngredient fromNetwork(FriendlyByteBuf buffer)
    {
        boolean isTag = buffer.readBoolean();
        ResourceLocation id = buffer.readResourceLocation();
        int amount = buffer.readInt();
        boolean hasNbt = buffer.readBoolean();
        CompoundTag nbt = hasNbt ? buffer.readNbt() : null;

        if (isTag)
        {
            TagKey<Fluid> tag = TagKey.create(Registries.FLUID, id);
            return FluidIngredient.of(tag, amount, nbt);
        }
            else
            {
                Fluid fluid = ForgeRegistries.FLUIDS.getValue(id);
                return FluidIngredient.of(fluid, amount, nbt);
            }
    }

    public void toNetwork(FriendlyByteBuf buffer)
    {
        boolean isTag = (this.tag != null);
        buffer.writeBoolean(isTag);

        if (isTag)
        {
            buffer.writeResourceLocation(tag.location());
        }
            else
            {
                buffer.writeResourceLocation(ForgeRegistries.FLUIDS.getKey(fluid));
            }

        buffer.writeInt(amount);

        boolean hasNbt = (this.nbt != null && !this.nbt.isEmpty());
        buffer.writeBoolean(hasNbt);
        if (hasNbt)
        {
            buffer.writeNbt(this.nbt);
        }
    }

    public int getAmount()
    {
        return amount;
    }

    public Fluid getFluid()
    {
        return fluid;
    }

    public TagKey<Fluid> getTag()
    {
        return tag;
    }

    public CompoundTag getNbt()
    {
        return nbt;
    }

    public FluidStack getFluidStack()
    {
        return new FluidStack(fluid, amount, nbt);
    }

    public List<FluidStack> getListFluidStackFromTag()
    {
        List<FluidStack> fluidStacks = new ArrayList<>();

        if(tag != null)
        {
            var tagFluid = ForgeRegistries.FLUIDS.tags().getTag(tag);
            if (tagFluid != null)
            {
                for (Fluid fluid : tagFluid)
                {
                    fluidStacks.add(new FluidStack(fluid, amount, nbt));
                }
            }
        }
        return fluidStacks;
    }

    public boolean isEmpty()
    {
        return fluid == null && tag == null;
    }

    public boolean isTag()
    {
        return tag != null;
    }

    public boolean hasNbt()
    {
        return nbt != null;
    }
}
