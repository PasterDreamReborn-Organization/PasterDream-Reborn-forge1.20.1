package com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot;

import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.fluidslot.FluidSyncPacket;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractContainerMenuWithFluidSlot extends AbstractContainerMenu
{
    private final List<FluidSlot> fluidSlots = new ArrayList<>();
    private FluidStack[] lastFluids;    //检测变化
    private FluidStack[] remoteFluids;  //用于同步
    private final Int2ObjectMap<FluidSlot> fluidSlotMap = new Int2ObjectOpenHashMap<>();

    protected AbstractContainerMenuWithFluidSlot(MenuType<?> type, int id)
    {
        super(type, id);
    }

    protected abstract FluidContainer getFluidContainer();

    @Override
    public abstract boolean stillValid(Player player);

    public FluidSlot addFluidSlot(FluidSlot slot)
    {
        Slot placeholder = this.addSlot(new Slot(new SimpleContainer(1), 0, slot.x + 1, slot.y + 1)
        {
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
            public boolean isActive()
            {
                return true;
            }

            @Override
            public boolean isHighlightable()
            {
                return false;
            }
        });
        fluidSlotMap.put(placeholder.index, slot);
        fluidSlots.add(slot);
        return slot;
    }

    public void reBuildLastFluids()
    {
        int size = fluidSlots.size();
        lastFluids = new FluidStack[size];
        remoteFluids = new FluidStack[size];
        for (int i = 0; i < size; i++)
        {
            lastFluids[i] = fluidSlots.get(i).getFluid().copy();
            remoteFluids[i] = lastFluids[i].copy();
        }
    }

    public List<FluidSlot> getFluidSlots()
    {
        return fluidSlots;
    }

    public FluidSlot getFluidSlot(int index)
    {
        return fluidSlots.get(index);
    }

    @Override
    public void broadcastChanges()
    {
        super.broadcastChanges();
        ContainerSynchronizer sync = synchronizer;  //此处已在访问变换器中将AbstractContainerMenu.synchronizer访问权限修改为public
        if (sync == null)
        {
            return;
        }

        if (sync instanceof ServerPlayer player)
        {
            boolean changed = false;
            for (int i = 0; i < fluidSlots.size(); i++)
            {
                FluidStack current = fluidSlots.get(i).getFluid();
                FluidStack prev = lastFluids[i];

                if (!current.equals(prev))
                {
                    changed = true;
                    lastFluids[i] = current.copy();
                }
            }

            if (changed)
            {
                FluidStack[] allFluids = fluidSlots.stream().map(FluidSlot::getFluid).toArray(FluidStack[]::new);
                ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new FluidSyncPacket(this.containerId, allFluids));
            }
        }
    }

    public void syncFluidsFromClient(FluidStack[] fluids)
    {
        for (int i = 0; i < Math.min(fluids.length, fluidSlots.size()); i++)
        {
            fluidSlots.get(i).setFluid(fluids[i]);
        }

        for (int i = 0; i < fluidSlots.size(); i++)
        {
            remoteFluids[i] = fluidSlots.get(i).getFluid().copy();
        }
    }

    @Override
    public void clicked(int slotIndex, int button, ClickType clickType, Player player)
    {
        if (clickType == ClickType.PICKUP && fluidSlotMap.containsKey(slotIndex))
        {
            FluidSlot target = fluidSlotMap.get(slotIndex);
            handleFluidSlotClick(target, button, player);
            return;
        }
        super.clicked(slotIndex, button, clickType, player);
    }

    private void handleFluidSlotClick(FluidSlot slot, int button, Player player)
    {
        if (player.level().isClientSide)
        {   //客户端不执行
            return;
        }

        ItemStack heldItem = player.containerMenu.getCarried();
        if (heldItem.isEmpty())
        {
            return;
        }

        FluidStack current = slot.getFluid();
        int capacity = slot.getMaxCapacity();
        IFluidHandler handler = new SingleFluidHandler(current, capacity);

        FluidActionResult result;
        if (button == 0)
        {
            if (!slot.mayPickup(player))
            {
                return;
            }
            result = FluidUtil.tryFillContainer(heldItem, handler, Integer.MAX_VALUE, null, true);
        }
            else
            {
                if(!slot.mayPlace(FluidUtil.getFluidContained(heldItem).orElse(FluidStack.EMPTY)))
                {
                    return;
                }
                result = FluidUtil.tryEmptyContainer(heldItem, handler, Integer.MAX_VALUE, null, true);
            }

        if (result.isSuccess())
        {
            if(heldItem.getCount() == 1)
            {
                player.containerMenu.setCarried(result.getResult());
            }
                else
                {
                    player.containerMenu.setCarried(new ItemStack(heldItem.getItem(), heldItem.getCount() - 1));
                    ItemStack newItem = result.getResult();
                    if (!newItem.isEmpty())
                    {
                        if (!player.getInventory().add(newItem))
                        {
                            player.drop(newItem, false);
                        }
                    }
                }

            slot.setFluid(handler.getFluidInTank(0));

            FluidStack[] allFluids = getFluidSlots().stream().map(FluidSlot::getFluid).toArray(FluidStack[]::new);
            ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new FluidSyncPacket(this.containerId, allFluids));
        }
    }

    private static class SingleFluidHandler implements IFluidHandler
    {
        private FluidStack fluid;
        private final int capacity;

        public SingleFluidHandler(FluidStack fluid, int capacity)
        {
            this.fluid = fluid;
            this.capacity = capacity;
        }

        @Override
        public int getTanks()
        {
            return 1;
        }

        @Override
        public FluidStack getFluidInTank(int tank)
        {
            return fluid;
        }

        @Override
        public int getTankCapacity(int tank)
        {
            return capacity;
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack)
        {
            return true;
        }

        //由流体容器倒入流体槽位
        @Override
        public int fill(FluidStack resource, FluidAction action)
        {
            if (resource.isEmpty())
            {
                return 0;
            }

            int space = capacity - fluid.getAmount();
            //可交互量
            int amount = Math.min(resource.getAmount(), space);
            if (amount <= 0)
            {
                return 0;
            }

            if(action.execute())
            {
                if (fluid.isEmpty())
                {
                    fluid = resource.copy();
                }
                    else
                    {
                        fluid.grow(amount);
                    }
            }
            return amount;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action)
        {
            if (resource.isEmpty() || !fluid.isFluidEqual(resource))
            {
                return FluidStack.EMPTY;
            }
            int amount = Math.min(resource.getAmount(), fluid.getAmount());
            FluidStack drained = new FluidStack(fluid.getFluid(), amount);
            if(action.execute())
            {
                if (amount == fluid.getAmount())
                {
                    fluid = FluidStack.EMPTY;
                }
                    else
                    {
                        fluid.shrink(amount);
                    }
            }
            return drained;
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action)
        {
            if (fluid.isEmpty())
            {
                return FluidStack.EMPTY;
            }
            int amount = Math.min(maxDrain, fluid.getAmount());
            FluidStack drained = new FluidStack(fluid.getFluid(), amount);
            if(action.execute())
            {
                if (amount == fluid.getAmount())
                {
                    fluid = FluidStack.EMPTY;
                }
                    else
                    {
                        fluid.shrink(amount);
                    }
            }
            return drained;
        }
    }
}
