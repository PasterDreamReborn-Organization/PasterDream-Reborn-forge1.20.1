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
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemIngredient
{
    @Nullable
    private final Item item;
    @Nullable
    private final TagKey<Item> tag;
    private final int count;
    @Nullable
    private final CompoundTag nbt;

    private ItemIngredient(@Nullable Item item, @Nullable TagKey<Item> tag, int count, @Nullable CompoundTag nbt)
    {
        this.item = item;
        this.tag = tag;
        this.count = count;
        this.nbt = nbt;
    }

    public static ItemIngredient of(Item item, int count)
    {
        return new ItemIngredient(item, null, count, null);
    }

    public static ItemIngredient of(TagKey<Item> tag, int count)
    {
        return new ItemIngredient(null, tag, count, null);
    }

    public static ItemIngredient of(Item item, int count, CompoundTag nbt)
    {
        return new ItemIngredient(item, null, count, nbt);
    }

    public static ItemIngredient of(TagKey<Item> tag, int count, CompoundTag nbt)
    {
        return new ItemIngredient(null, tag, count, nbt);
    }

    public boolean test(ItemStack stack)
    {
        if (stack.getCount() < count)
        {
            return false;
        }

        if (item != null)
        {
            return stack.getItem() == item;
        }

        if (tag != null)
        {
            return stack.getItem().builtInRegistryHolder().is(tag);
        }

        if (nbt != null)
        {
            return nbt.equals(stack.getTag());
        }

        return true;
    }

    public static ItemIngredient fromJson(JsonElement json)
    {
        JsonObject obj = json.getAsJsonObject();
        int count = obj.has("count") ? obj.get("count").getAsInt() : 1;

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
                    System.err.println("物品NBT解析出错，" + e);
                }
        }

        if (obj.has("item"))
        {
            String itemName = obj.get("item").getAsString();
            Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(itemName));
            if (item == null)
            {
                throw new IllegalArgumentException("无法解析的物品：" + itemName);
            }

            return ItemIngredient.of(item, count, nbt);
        }
        else
            if (obj.has("tag"))
            {
                String tagName = obj.get("tag").getAsString();
                TagKey<Item> tag = TagKey.create(Registries.ITEM, ResourceLocation.parse(tagName));
                return ItemIngredient.of(tag, count, nbt);
            }
                else
                {
                    throw new IllegalArgumentException("输入物品必须填写\"item\"或\"tag\"");
                }
    }

    public static ItemIngredient fromNetwork(FriendlyByteBuf buffer)
    {
        boolean isTag = buffer.readBoolean();
        ResourceLocation id = buffer.readResourceLocation();
        int count = buffer.readInt();
        boolean hasNbt = buffer.readBoolean();
        CompoundTag nbt = hasNbt ? buffer.readNbt() : null;

        if (isTag)
        {
            TagKey<Item> tag = TagKey.create(Registries.ITEM, id);
            return ItemIngredient.of(tag, count, nbt);
        }
            else
            {
                Item item = ForgeRegistries.ITEMS.getValue(id);
                return ItemIngredient.of(item, count, nbt);
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
                buffer.writeResourceLocation(ForgeRegistries.ITEMS.getKey(item));
            }

        buffer.writeInt(count);

        boolean hasNbt = (this.nbt != null && !this.nbt.isEmpty());
        buffer.writeBoolean(hasNbt);
        if (hasNbt)
        {
            buffer.writeNbt(this.nbt);
        }
    }

    public int getCount()
    {
        return count;
    }

    public Item getItem()
    {
        return item;
    }

    public TagKey<Item> getTag()
    {
        return tag;
    }

    public CompoundTag getNbt()
    {
        return nbt;
    }

    public ItemStack getItemStack()
    {
        return new ItemStack(item, count, nbt);
    }

    public List<ItemStack> getListItemStackFromTag()
    {
        List<ItemStack> itemStacks = new ArrayList<>();

        if(tag != null)
        {
            var tagItem = ForgeRegistries.ITEMS.tags().getTag(tag);
            if (tagItem != null)
            {
                for (Item item : tagItem)
                {
                    itemStacks.add(new ItemStack(item, count, nbt));
                }
            }
        }
        return itemStacks;
    }

    public boolean isEmpty()
    {
        return item == null && tag == null;
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
