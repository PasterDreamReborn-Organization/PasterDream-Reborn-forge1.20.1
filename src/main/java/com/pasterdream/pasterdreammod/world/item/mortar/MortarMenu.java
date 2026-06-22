package com.pasterdream.pasterdreammod.world.item.mortar;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidContainer;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.network.fluidslot.FluidSyncPacket;
import com.pasterdream.pasterdreammod.recipe.GenericPasterDreamRecipe;
import com.pasterdream.pasterdreammod.recipe.GenericPasterDreamRecipeMatchResult;
import com.pasterdream.pasterdreammod.recipe.recipematchandprocess.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MortarMenu extends AbstractContainerMenuWithFluidSlot
{
    private final MortarInventory mortarInventory;
    private final Inventory playerInventory;
    private final ItemStack thisMortarItem;
    private final InteractionHand interactionHand;
    private final int lockedSlotIndex;

    public MortarMenu(int id, Inventory playerInventory, ItemStack thisMortarItem, InteractionHand interactionHand)
    {
        super(ModMenus.MORTAR.get(), id);
        this.playerInventory = playerInventory;
        this.thisMortarItem = thisMortarItem;
        mortarInventory = new MortarInventory(new MortarDataHandler(this.thisMortarItem));
        this.interactionHand = interactionHand;

        if (interactionHand == InteractionHand.MAIN_HAND)
        {   //快捷栏槽位在容器中的起始索引是18
            lockedSlotIndex = 18 + playerInventory.selected;
        }
            else
            {
                lockedSlotIndex = -1;
            }

        addFluidSlot(new FluidSlot(mortarInventory, 0, 26, 5));
        addFluidSlot(new FluidSlot(mortarInventory, 1, 44, 5));
        addFluidSlot(new FluidSlot(mortarInventory, 2, 26, 23));
        addFluidSlot(new FluidSlot(mortarInventory, 3, 44, 23));
        addFluidSlot(new FluidSlot(mortarInventory, 4, 26, 67)
        {
            @Override
            public boolean mayPlace(FluidStack stack)
            {
                return false;
            }
        });
        addFluidSlot(new FluidSlot(mortarInventory, 5, 44, 67)
        {
            @Override
            public boolean mayPlace(FluidStack stack)
            {
                return false;
            }
        });

        for (int row = 0; row < 2; row++)
        {
            for (int col = 0; col < 4; col++)
            {
                int index = row * 4 + col;
                addSlot(new SlotItemHandler(mortarInventory.getItemHandler(), index, 73 + col * 18, 6 + row * 18));
            }
        }

        for (int col = 0; col < 4; col++)
        {
            int index = 8 + col;
            addSlot(new SlotItemHandler(mortarInventory.getItemHandler(), index, 73 + col * 18, 68)
            {
                @Override
                public boolean mayPlace(ItemStack stack)
                {
                    return false;
                }
            });
        }

        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(playerInventory, col, 5 + col * 18, 159));
        }
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 5 + col * 18, 101 + row * 18));
            }
        }
        reBuildLastFluids();
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

        if ((index >= 6 && index <= 17))
        {   //从机器移出到背包
            if (!this.moveItemStackTo(stack, 18, 54, true))
            {
                return ItemStack.EMPTY;
            }
        }
        else
            if((index >= 18 && index <= 53) && index != lockedSlotIndex)
            {   //从背包移入输入槽
                if (!this.moveItemStackTo(stack, 6, 14, false))
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

    @Override
    public FluidContainer getFluidContainer()
    {
        return mortarInventory;
    }

    @Override
    public void removed(Player player)
    {
        if (!player.level().isClientSide)
        {
            player.setItemInHand(interactionHand, thisMortarItem);
        }
        super.removed(player);
    }

    @Override
    public void clicked(int slotIndex, int button, ClickType clickType, Player player)
    {
        if (slotIndex == lockedSlotIndex)
        {
            return;
        }
        super.clicked(slotIndex, button, clickType, player);
    }

    public void craft()
    {
        if (playerInventory.player.level().isClientSide)
        {
            return;
        }

        List<MortarRecipe> recipes = playerInventory.player.level().getRecipeManager().getAllRecipesFor(ModRecipes.MORTAR.get());

        ItemStackHandler itemHandler = mortarInventory.getItemHandler();
        FluidTank[] tanks = mortarInventory.getFluidTanks();

        List<ItemStack> inputItems = new ArrayList<>(8);
        for (int i = 0; i < 8; i++)
        {
            inputItems.add(itemHandler.getStackInSlot(i).copy());
        }

        List<ItemStack> outputItems = new ArrayList<>(4);
        for (int i = 8; i < 12; i++)
        {
            outputItems.add(itemHandler.getStackInSlot(i).copy());
        }

        List<FluidStack> inputFluids = new ArrayList<>(4);
        for (int i = 0; i < 4; i++)
        {
            inputFluids.add(tanks[i].getFluid().copy());
        }

        List<FluidStack> outputFluids = new ArrayList<>(2);
        for (int i = 4; i < 6; i++)
        {
            outputFluids.add(tanks[i].getFluid().copy());
        }

        //配方匹配
        MatchedRecipeResult<MortarRecipe> matched = RecipeMatcher.match(inputItems, inputFluids, recipes);
        if (matched == null)
        {
            return;
        }

        MortarRecipe recipe = matched.recipe();

        List<ItemStack> requiredItems = recipe.getInputItemIngredients().stream().map(ItemIngredient::getItemStack).map(ItemStack::copy).collect(Collectors.toList());
        List<FluidStack> requiredFluids = recipe.getInputFluidIngredients().stream().map(FluidIngredient::getFluidStack).map(FluidStack::copy).collect(Collectors.toList());
        List<ItemStack> outputItemsRecipe = recipe.getOutputItemIngredients().stream().map(ItemIngredient::getItemStack).map(ItemStack::copy).collect(Collectors.toList());
        List<FluidStack> outputFluidsRecipe = recipe.getOutputFluidIngredients().stream().map(FluidIngredient::getFluidStack).map(FluidStack::copy).collect(Collectors.toList());

        MachineInventory recipeInventory = new MachineInventory(requiredItems, requiredFluids, outputItemsRecipe, outputFluidsRecipe);
        MachineInventoryWithFluidSlotMaxStackSize machineData = new MachineInventoryWithFluidSlotMaxStackSize(inputItems.stream().map(ItemStack::copy).collect(Collectors.toList()), inputFluids.stream().map(FluidStack::copy).collect(Collectors.toList()), outputItems.stream().map(ItemStack::copy).collect(Collectors.toList()), outputFluids.stream().map(FluidStack::copy).collect(Collectors.toList()), 1000);
        MachineInventory result = RecipeProcesser.recipeProcessor(recipeInventory, machineData);

        if (result.inputItemStacks() == inputItems && result.inputFluidStacks() == inputFluids)
        {
            return;
        }

        //获取结果
        List<ItemStack> newInputItems = result.inputItemStacks();
        List<ItemStack> newOutputItems = result.outputItemStacks();
        for (int i = 0; i < 8; i++)
        {
            itemHandler.setStackInSlot(i, newInputItems.get(i));
        }
        for (int i = 8; i < 12; i++)
        {
            itemHandler.setStackInSlot(i, newOutputItems.get(i - 8));
        }

        List<FluidStack> newInputFluids = result.inputFluidStacks();
        List<FluidStack> newOutputFluids = result.outputFluidStacks();
        for (int i = 0; i < 4; i++)
        {
            tanks[i].setFluid(newInputFluids.get(i));
        }
        for (int i = 4; i < 6; i++)
        {
            tanks[i].setFluid(newOutputFluids.get(i - 4));
        }

        //同步
        broadcastChanges();
        FluidStack[] allFluids = getFluidSlots().stream().map(FluidSlot::getFluid).toArray(FluidStack[]::new);
        ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) playerInventory.player), new FluidSyncPacket(this.containerId, allFluids));
    }

    @Nullable
    private GenericPasterDreamRecipeMatchResult findMatchingRecipe()
    {
        Level level = playerInventory.player.level();

        //收集所有输入流体
        List<FluidStack> fluidStacks = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            FluidStack fluidStack = mortarInventory.getFluidTanks()[i].getFluid();
            if (!fluidStack.isEmpty())
            {
                fluidStacks.add(fluidStack.copy());
            }
        }

        //收集所有输入物品
        List<ItemStack> itemStacks = new ArrayList<>();
        ItemStackHandler handler = mortarInventory.getItemHandler();
        for (int i = 0; i < 4; i++)
        {
            ItemStack itemStack = handler.getStackInSlot(i);
            if (!itemStack.isEmpty())
            {
                itemStacks.add(itemStack.copy());
            }
        }

        //遍历配方
        return level.getRecipeManager().getAllRecipesFor(ModRecipes.MORTAR.get()).stream().map(recipe -> recipe.matchesWithSlots(fluidStacks, itemStacks)).filter(Optional::isPresent).map(Optional::get).findFirst().orElse(null);
    }
}
