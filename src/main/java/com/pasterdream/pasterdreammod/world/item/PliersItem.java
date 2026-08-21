package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class PliersItem extends Item {
    public PliersItem(Properties properties) {
        super(properties.durability(476));
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (state.is(ModBlockTags.PLIER_PLANTS)) {
            if (!level.isClientSide) {
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        new ItemStack(state.getBlock())));
            }
            level.playSound(null, pos, ModSounds.PLIERS.get(), SoundSource.PLAYERS, 0.5f, 1);
        }
        return super.mineBlock(stack, level, state, pos, entity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.PLIERS.get(), SoundSource.PLAYERS, 0.5f, 1);
        return super.use(level, player, hand);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        ItemStack retval = new ItemStack(this);
        retval.setDamageValue(itemstack.getDamageValue() + 1);
        if (retval.getDamageValue() >= retval.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return retval;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.pliers.1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.pliers.2"));
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        BlockState state = level.getBlockState(pos);

        if (player != null) {
            player.swing(context.getHand(), true);
            if (player.isShiftKeyDown() && state.is(ModBlockTags.PLIER_PLANTS)) {
                stack.hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(context.getHand()));
                if (!level.isClientSide) {
                    ItemEntity entityToSpawn = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            new ItemStack(state.getBlock()));
                    entityToSpawn.setPickUpDelay(10);
                    level.addFreshEntity(entityToSpawn);
                }
                level.destroyBlock(pos, false);
            }
        }
        level.playSound(null, pos, ModSounds.PLIERS.get(), SoundSource.PLAYERS, 0.5f, 1);
        return InteractionResult.SUCCESS;
    }
}
