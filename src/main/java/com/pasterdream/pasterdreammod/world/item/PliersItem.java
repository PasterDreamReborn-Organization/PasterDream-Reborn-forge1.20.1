package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModLootTables;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

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
        tooltip.add(Component.translatable("tooltip.pasterdream.pliers.3"));
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
            } else if (player.isShiftKeyDown() && state.is(BlockTags.LEAVES)) {
                cutLeaves(level, player, context.getHand(), stack, pos, state);
            }
        }
        level.playSound(null, pos, ModSounds.PLIERS.get(), SoundSource.PLAYERS, 0.5f, 1);
        return InteractionResult.SUCCESS;
    }

    // shift+右键剪碎树叶：破坏树叶并走模组战利品表 pliers_cutting/<命名空间>/<树叶注册名>
    private void cutLeaves(Level level, Player player, InteractionHand hand, ItemStack stack, BlockPos pos, BlockState state) {
        if (level.isClientSide) {
            return;
        }
        ServerLevel serverLevel = (ServerLevel) level;
        ResourceLocation lootTable = ModLootTables.pliersCuttingLoot(state.getBlock());
        // 只对已定义 pliers_cutting 战利品表的树叶生效，避免破坏后无掉落
        if (serverLevel.getServer().getLootData().getLootTable(lootTable) == LootTable.EMPTY) {
            return;
        }
        stack.hurtAndBreak(2, player, (e) -> e.broadcastBreakEvent(hand));
        LootParams params = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                .create(LootContextParamSets.CHEST);
        for (ItemStack loot : serverLevel.getServer().getLootData().getLootTable(lootTable).getRandomItems(params)) {
            ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, loot);
            entity.setPickUpDelay(10);
            level.addFreshEntity(entity);
        }
        serverLevel.destroyBlock(pos, false);
    }
}
