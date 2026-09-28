package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolEntityEditorMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin
{
    @Inject(method = "interactOn", at = @At("HEAD"), cancellable = true)
    private void pasterdream$DebugToolInteract(Entity target, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir)
    {
        Player player = (Player)(Object)this;
        Item item = player.getItemInHand(hand).getItem();

        if (item instanceof DebugToolItem)
        {
            if (!player.level().isClientSide)
            {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                int entityId = target.getId();

                NetworkHooks.openScreen(serverPlayer, new MenuProvider()
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

            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
