package com.pasterdream.pasterdreammod.world.block.shadowdungeonportal.broken;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BrokenShadowDungeonPortalBlock extends BaseEntityBlock
{
    private static final ResourceLocation SHADOW_DUNGEON_ADV = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_dungeon");

    public BrokenShadowDungeonPortalBlock()
    {
        super(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(-1, 3600000).noOcclusion());
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new BrokenShadowDungeonPortalTileEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type)
    {
        return type == ModBlockEntities.BROKEN_SHADOW_DUNGEON_PORTAL.get() ? (blockLevel, blockPos, state, blockEntity) -> BrokenShadowDungeonPortalTileEntity.tick(blockLevel, blockPos, (BrokenShadowDungeonPortalTileEntity)blockEntity) : null;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        return box(3, 3, 3, 13, 13, 13);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPosition, Player player, InteractionHand interactionHand, BlockHitResult hitResult)
    {
        if (!level.isClientSide())
        {
            if(player.isCreative())
            {
                player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.creative_repaired"), false);
                repairShadowDungeonPortal(level, blockPosition);
            }
            else
                if (hasShadowDungeonKnowledge(player))
                {
                    if (isPlayerHave(player, ModItems.SHADOW_LIGHT.get().asItem()) && isPlayerHave(player, ModItems.BLACK_METAL_INGOT.get().asItem()))
                    {
                        player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.repaired"), true);
                        repairShadowDungeonPortal(level, blockPosition);
                        clearItem(player, ModItems.SHADOW_LIGHT.get().asItem());
                        clearItem(player, ModItems.BLACK_METAL_INGOT.get().asItem());
                    }
                        else
                        {
                            player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.need_materials"), true);
                        }
                }
                    else
                    {
                        player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.lack_knowledge"), true);
                    }
        }
        return InteractionResult.SUCCESS;
    }

    private void repairShadowDungeonPortal(Level level, BlockPos blockPosition)
    {
        level.playSound(null, blockPosition, SoundEvents.SMITHING_TABLE_USE, SoundSource.NEUTRAL, 1, 1);
        ((ServerLevel)level).sendParticles(ParticleTypes.END_ROD, blockPosition.getX() + 0.5, blockPosition.getY() + 0.5, blockPosition.getZ() + 0.5, 24, 1, 1, 1, 0.3);
        if (!level.isClientSide && level.getBlockEntity(blockPosition) instanceof BrokenShadowDungeonPortalTileEntity brokenShadowDungeonPortal)
        {
            brokenShadowDungeonPortal.repair();
        }
    }

    private boolean hasShadowDungeonKnowledge(Player player)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(SHADOW_DUNGEON_ADV);
            return advancement != null && serverPlayer.getAdvancements().getOrStartProgress(advancement).isDone();
        }
        return false;
    }

    private boolean isPlayerHave(Player player, Item item)
    {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++)
        {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == item)
            {
                return true;
            }
        }
        return false;
    }

    private void clearItem(Player player, Item item)
    {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++)
        {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == item)
            {
                stack.shrink(1);
                return;
            }
        }
    }
}
