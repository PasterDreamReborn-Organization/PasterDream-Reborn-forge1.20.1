package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.helper.itemwithnbt.spawneggwithnbt.GetSpawnEgg;
import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolEntityEditorMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin
{
    @Shadow
    public ServerPlayer player;

    @Inject(method = "handleInteract", at = @At("HEAD"), cancellable = true)
    private void pasterdream$handleInteract(ServerboundInteractPacket packet, CallbackInfo ci)
    {
        ServerboundInteractPacket.ActionType actionType = packet.action.getType();

        //左键
        if(actionType == ServerboundInteractPacket.ActionType.ATTACK)
        {
            Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (item instanceof DebugToolItem)
            {
                if (!player.level().isClientSide)
                {
                    Entity target = packet.getTarget(player.serverLevel());
                    if(target != null)
                    {
                        if(player != null)
                        {
                            ItemStack itemStack = GetSpawnEgg.getSpawnEgg(target);
                            if (!itemStack.isEmpty())
                            {
                                if (!player.getInventory().add(itemStack))
                                {
                                    player.drop(itemStack, false);
                                }
                            }
                            target.discard();
                            player.sendSystemMessage(Component.translatable("已删除实体: " + target.getName().getString()));
                        }
                    }
                }
                ci.cancel();
            }
        }
            //右键
            else
            {
                Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                if (item instanceof DebugToolItem)
                {
                    if (!player.level().isClientSide)
                    {
                        Entity target = packet.getTarget(player.serverLevel());
                        int entityId = target.getId();

                        NetworkHooks.openScreen(player, new MenuProvider()
                        {
                            @Override
                            public Component getDisplayName()
                            {
                                return Component.empty();
                            }

                            @Override
                            public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player)
                            {
                                return new DebugToolEntityEditorMenu(id, playerInventory, entityId);
                            }
                        }, buffer ->
                        {
                            buffer.writeVarInt(entityId);
                            CompoundTag nbt = new CompoundTag();
                            target.saveWithoutId(nbt);
                            buffer.writeNbt(nbt);
                        });
                    }
                    ci.cancel();
                }
            }
    }
}
