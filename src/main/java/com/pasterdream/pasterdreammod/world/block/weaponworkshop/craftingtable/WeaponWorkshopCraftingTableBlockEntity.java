package com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.weaponworkshopcraftingtable.WeaponWorkshopCraftingTableRecipeInventory;
import com.pasterdream.pasterdreammod.recipe.weaponworkshopcraftingtable.WeaponWorkshopCraftingTableRecipeMatcher;
import com.pasterdream.pasterdreammod.recipe.weaponworkshopcraftingtable.WeaponWorkshopCraftingTableRecipeProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WeaponWorkshopCraftingTableBlockEntity extends BlockEntity implements MenuProvider
{
    public WeaponWorkshopCraftingTableBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.WEAPON_WORKSHOP_CRAFTING_TABLE.get(), pos, state);
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(7)
    {
        @Override
        protected void onContentsChanged(int slotIndex)
        {
            setChangedAndSync();
        }

        @Override
        public boolean isItemValid(int slotIndex, ItemStack stack)
        {
            return slotIndex != 6;
        }
    };

    private final IItemHandler externalHandler = new IItemHandler()
    {
        @Override
        public int getSlots()
        {
            return itemHandler.getSlots();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slotIndex)
        {
            return itemHandler.getStackInSlot(slotIndex);
        }

        @Override
        public @NotNull ItemStack insertItem(int slotIndex, @NotNull ItemStack itemStack, boolean isSimulate)
        {
            return itemHandler.insertItem(slotIndex, itemStack, isSimulate);
        }

        @Override
        public @NotNull ItemStack extractItem(int slotIndex, int amount, boolean isSimulate)
        {
            if(slotIndex == 6)
            {
                return itemHandler.extractItem(slotIndex, amount, isSimulate);
            }
                else
                {
                    return ItemStack.EMPTY;
                }
        }

        @Override
        public int getSlotLimit(int slotIndex)
        {
            return itemHandler.getSlotLimit(slotIndex);
        }

        @Override
        public boolean isItemValid(int slotIndex, @NotNull ItemStack itemStack)
        {
            return itemHandler.isItemValid(slotIndex, itemStack);
        }
    };

    private final LazyOptional<IItemHandler> itemHandlerCap = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IItemHandler> externalHandlerCap = LazyOptional.of(() -> externalHandler);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
        {
            if (side == null)
            {
                return itemHandlerCap.cast();
            }
                else
                {
                    return externalHandlerCap.cast();
                }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();
        itemHandlerCap.invalidate();
    }

    private void setChangedAndSync()
    {
        setChanged();
        if (level != null && !level.isClientSide)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag)
    {
        load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put("Inventory", itemHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".weapon_workshop_crafting_table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new WeaponWorkshopCraftingTableMenu(id, inventory, this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet)
    {
        CompoundTag tag = packet.getTag();
        if (tag != null)
        {
            load(tag);
        }
    }

    public ItemStackHandler getItemHandler()
    {
        return itemHandler;
    }

    public void craft(ServerPlayer player)
    {
        List<WeaponWorkshopCraftingTableRecipe> recipes = level.getRecipeManager().getAllRecipesFor(ModRecipes.WEAPON_WORKSHOP_CRAFTING_TABLE.get());

        List<ItemStack> inputItems = new ArrayList<>(5);
        for(int i = 0; i < 5; i++)
        {
            inputItems.add(itemHandler.getStackInSlot(i).copy());
        }
        ItemStack enhanceStone = itemHandler.getStackInSlot(5).copy();
        ItemStack outputItem = itemHandler.getStackInSlot(6).copy();
        WeaponWorkshopCraftingTableRecipeInventory inventory = new WeaponWorkshopCraftingTableRecipeInventory(inputItems, enhanceStone, outputItem);

        WeaponWorkshopCraftingTableRecipeInventory matchedResult = WeaponWorkshopCraftingTableRecipeMatcher.matches(inventory, recipes);
        if(matchedResult != null)
        {
            double playerLuckValue = 0;
            if(player != null)
            {
                playerLuckValue = player.getAttributeValue(Attributes.LUCK);
                if(player.getEffect(MobEffects.LUCK) != null)
                {
                    playerLuckValue += player.getEffect(MobEffects.LUCK).getAmplifier();
                }
            }

            WeaponWorkshopCraftingTableRecipeInventory processedResult = WeaponWorkshopCraftingTableRecipeProcessor.processing(matchedResult, inventory, level.getRandom(), playerLuckValue);

            if(processedResult != null)
            {
                for(int i = 0; i < processedResult.inputItemStacks().size(); i++)
                {
                    itemHandler.setStackInSlot(i, processedResult.inputItemStacks().get(i));
                }
                itemHandler.setStackInSlot(5, processedResult.enhanceStone());
                itemHandler.setStackInSlot(6, processedResult.outputItemStack());
            }
        }
    }
}
