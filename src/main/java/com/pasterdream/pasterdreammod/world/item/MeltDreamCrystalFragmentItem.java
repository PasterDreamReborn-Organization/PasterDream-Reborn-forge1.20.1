package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.world.entity.MeltDreamCrystalEntityEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class MeltDreamCrystalFragmentItem extends Item {
    public MeltDreamCrystalFragmentItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.EPIC));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        var player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        // 仅潜行时触发
        if (!player.isShiftKeyDown()) return InteractionResult.PASS;

        BlockPos placePos = context.getClickedPos().above();
        double x = placePos.getX() + 0.5;
        double y = placePos.getY();
        double z = placePos.getZ() + 0.5;

        if (!level.getBlockState(placePos).is(Blocks.AIR)) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            context.getItemInHand().shrink(1);
            var entity = new MeltDreamCrystalEntityEntity(
                    ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get(), level);
            entity.setPos(x, y, z);
            entity.setYRot(level.getRandom().nextFloat() * 360F);
            level.addFreshEntity(entity);
        }

        if (!level.isClientSide()) {
            level.playSound(null, context.getClickedPos(),
                    ForgeRegistries.SOUND_EVENTS.getValue(
                            ResourceLocation.parse("block.amethyst_block.place")),
                    SoundSource.NEUTRAL, 0.8f, 1.0f);
        } else {
            level.playLocalSound(context.getClickedPos().getX(),
                    context.getClickedPos().getY(), context.getClickedPos().getZ(),
                    ForgeRegistries.SOUND_EVENTS.getValue(
                            ResourceLocation.parse("block.amethyst_block.place")),
                    SoundSource.NEUTRAL, 0.8f, 1.0f, false);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        var entity = new IndestructibleItemEntity(level, location.getX(), location.getY(), location.getZ(), stack);
        entity.setDefaultPickUpDelay();
        entity.setDeltaMovement(location.getDeltaMovement());
        return entity;
    }
}
