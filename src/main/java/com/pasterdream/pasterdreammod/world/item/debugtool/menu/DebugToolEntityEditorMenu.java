package com.pasterdream.pasterdreammod.world.item.debugtool.menu;

import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.item.debugtool.slot.ActiveStatusChangeableSlot;
import com.pasterdream.pasterdreammod.world.item.debugtool.slot.DelegatedSlot;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class DebugToolEntityEditorMenu extends AbstractContainerMenu
{
    private final Player player;
    private final int entityId;
    private CompoundTag serverEntityNbt;
    private final List<Consumer<CompoundTag>> nbtListeners = new CopyOnWriteArrayList<>();
    private int curiosSlotCount = 0;
    private int itemHandlerSlotCount = 0;

    public DebugToolEntityEditorMenu(int id, Inventory playerInventory, int entityId)
    {
        super(ModMenus.DEBUG_TOOL_ENTITY_EDITOR.get(), id);
        this.player = playerInventory.player;
        this.entityId = entityId;

        Entity entity = player.level().getEntity(entityId);

        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(playerInventory, col, 5 + col * 18, 217));
        }

        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 5 + col * 18, 159 + row * 18));
            }
        }

        buildEntitySlots(entity);
    }

    public DebugToolEntityEditorMenu(int id, Inventory playerInventory, FriendlyByteBuf buffer)
    {
        this(id, playerInventory, buffer.readVarInt());
        this.serverEntityNbt = buffer.readNbt();
    }

    private void buildEntitySlots(Entity entity)
    {
        if(entity instanceof LivingEntity livingEntity)
        {
            addSlot(new Slot(new SimpleContainer(1), 0, 152, 12)
            {
                @Override
                public ItemStack getItem()
                {
                    return getSpawnEggStack();
                }

                @Override
                public void set(ItemStack stack)
                {

                }

                @Override
                public ItemStack remove(int amount)
                {
                    return ItemStack.EMPTY;
                }

                @Override
                public boolean mayPlace(ItemStack stack)
                {
                    return false;
                }

                @Override
                public boolean mayPickup(Player player)
                {
                    return false;
                }

                @Override
                public void setChanged()
                {

                }
            });

            addSlot(new DelegatedSlot(livingEntity::getMainHandItem, itemStack -> livingEntity.setItemInHand(InteractionHand.MAIN_HAND, itemStack), 98, 51));
            addSlot(new DelegatedSlot(livingEntity::getOffhandItem, itemStack -> livingEntity.setItemInHand(InteractionHand.OFF_HAND, itemStack), 116, 51));

            EquipmentSlot[] armorSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
            for (int i = 0; i < armorSlots.length; i++)
            {
                EquipmentSlot slot = armorSlots[i];
                addSlot(new DelegatedSlot(() -> livingEntity.getItemBySlot(slot), itemStack -> livingEntity.setItemSlot(slot, itemStack), 152 + 18 * i, 51));
            }

            CuriosApi.getCuriosInventory(livingEntity).ifPresent(curios ->
            {
                for (var entry : curios.getCurios().entrySet())
                {
                    IItemHandler handler = entry.getValue().getStacks();
                    int slots = handler.getSlots();
                    for (int i = 0; i < slots; i++)
                    {
                        this.addSlot(new ActiveStatusChangeableSlot(handler, i, 80 + 18 * (i % 6), 89));
                    }
                    curiosSlotCount += slots;
                }
            });

            IItemHandler itemHandler = entity.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve().orElse(null);
            if (itemHandler != null)
            {
                itemHandlerSlotCount = itemHandler.getSlots();
                for (int i = 0; i < itemHandlerSlotCount; i++)
                {
                    this.addSlot(new ActiveStatusChangeableSlot(itemHandler, i, 80 + 18 * (i % 6), 128));
                }
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        Slot slot = slots.get(index);
        if (!slot.hasItem())
        {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if ((index >= 37 && index <= 42 + curiosSlotCount + itemHandlerSlotCount))
        {   //从机器移出到背包
            if (!this.moveItemStackTo(stack, 0, 36, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else
            if((index >= 0 && index <= 35))
            {   //从背包移入输入槽
                if (!(this.moveItemStackTo(stack, 37, 43 + curiosSlotCount + itemHandlerSlotCount, false)))
                {
                    return ItemStack.EMPTY;
                }
            }

        if (stack.isEmpty())
        {
            slot.set(ItemStack.EMPTY);
        }
            else
            {
                slot.setChanged();
            }
        return copy;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    private ItemStack getSpawnEggStack()
    {
        Entity entity = player.level().getEntity(entityId);
        if (entity != null)
        {
            SpawnEggItem spawnEgg = ForgeSpawnEggItem.fromEntityType(entity.getType());
            if(spawnEgg != null)
            {
                ItemStack itemStack = new ItemStack(spawnEgg);

                CompoundTag entityTag = new CompoundTag();
                entity.saveWithoutId(entityTag);

                entityTag.remove("Pos");
                entityTag.remove("Motion");
                entityTag.remove("Rotation");
                entityTag.remove("FallDistance");
                entityTag.remove("PortalCooldown");
                entityTag.remove("HurtTime");
                entityTag.remove("HurtByTimestamp");
                entityTag.remove("Fire");
                entityTag.remove("UUID");

                if (entityTag.contains("Attributes", Tag.TAG_LIST))
                {
                    ListTag attributes = entityTag.getList("Attributes", Tag.TAG_COMPOUND);
                    for (int i = 0; i < attributes.size(); i++)
                    {
                        CompoundTag attribute = attributes.getCompound(i);
                        if (attribute.contains("Modifiers", Tag.TAG_LIST))
                        {
                            ListTag modifiers = attribute.getList("Modifiers", Tag.TAG_COMPOUND);
                            modifiers.removeIf(tag ->
                            {
                                if(tag instanceof CompoundTag modifier)
                                {
                                    "Random spawn bonus".equals(modifier.getString("Name"));
                                }
                                return false;
                            });
                            if (modifiers.isEmpty())
                            {
                                attribute.remove("Modifiers");
                            }
                        }
                    }
                }

                itemStack.getOrCreateTag().put("EntityTag", entityTag);

                return itemStack;
            }
        }
        return ItemStack.EMPTY;
    }

    public void setServerEntityNbt(CompoundTag nbt)
    {
        this.serverEntityNbt = nbt;
        for (Consumer<CompoundTag> listener : nbtListeners)
        {
            listener.accept(nbt);
        }
    }

    public void addNbtListener(Consumer<CompoundTag> listener)
    {
        nbtListeners.add(listener);
    }

    public void clearNbtListeners()
    {
        nbtListeners.clear();
    }

    public CompoundTag getEntityNBT()
    {
        return serverEntityNbt;
    }

    public int getEntityId()
    {
        return entityId;
    }

    public int getCuriosSlotCount()
    {
        return curiosSlotCount;
    }

    public int getItemHandlerSlotCount()
    {
        return itemHandlerSlotCount;
    }
}
