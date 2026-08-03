package com.pasterdream.pasterdreammod.world.block.portal;

import com.pasterdream.pasterdreammod.helper.multiblockproperties.voxelshapecalculator.VoxelShapeCalculator;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.block.HorizontalDirectionalGenericBlock;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class DyedreamCrackBlock extends HorizontalDirectionalGenericBlock
{
    private static final ResourceKey<Level> DYEDREAM_WORLD = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_world"));
    private static final ResourceLocation DYEDREAM_CRACK_ADV = ResourceLocation.fromNamespaceAndPath("pasterdream", "story/dyedream_crack");
    private static final ResourceLocation FIRST_CONTACT_ADV = ResourceLocation.fromNamespaceAndPath("pasterdream", "story/first_contact_dyedream_crack");

    public DyedreamCrackBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        List<VoxelShape> ListVoxelShape = VoxelShapeCalculator.calculateAllDirectionVoxelShapeFromEastVoxelShape(7 / 16.0, -8 / 16.0, -8 / 16.0, 9 / 16.0, 24 / 16.0, 24 / 16.0);
        Direction facing = state.getValue(FACING);
        return switch (facing)
        {
            case EAST  -> ListVoxelShape.get(0);
            case SOUTH -> ListVoxelShape.get(1);
            case WEST  -> ListVoxelShape.get(2);
            case NORTH -> ListVoxelShape.get(3);
            default -> box(0, 0, 0, 16, 16, 16);
        };
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, level, pos, oldState, moving);
        level.scheduleTick(pos, this, 10);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.sendParticles(ModParticleTypes.CRACK_0_PARTICLE.get(),
                pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1,
                12, 0.5, 0.5, 0.2, 0.1);
        if (random.nextFloat() < 0.2f) {
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 0.5f, 1.0f);
        }
        level.scheduleTick(pos, this, 10);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos blockPosition, Entity entity)
    {
        if (!level.isClientSide && entity instanceof ServerPlayer player)
        {
            if (player.getPortalCooldown() > 0)
            {
                return;
            }

            // 玩家在染梦世界中 → 尝试返回主世界
            if (level.dimension().equals(DYEDREAM_WORLD))
            {
                level.playSound(null, blockPosition, ModSounds.DYEDREAM_CRACK.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                teleportToDreamDimension(player, state, blockPosition);
                player.setPortalCooldown(20);
                return;
            }

            // 检查是否已解锁染梦裂隙进度 → 允许进入染梦世界
            Advancement crackAdv = player.server.getAdvancements().getAdvancement(DYEDREAM_CRACK_ADV);
            if (crackAdv != null && player.getAdvancements().getOrStartProgress(crackAdv).isDone())
            {
                level.playSound(null, blockPosition, ModSounds.DYEDREAM_CRACK.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                teleportToDreamDimension(player, state, blockPosition);
                player.setPortalCooldown(20);
                return;
            }

            // 首次接触 → 显示提示并授予标记进度
            Advancement firstContactAdv = player.server.getAdvancements().getAdvancement(FIRST_CONTACT_ADV);
            if (firstContactAdv != null)
            {
                AdvancementProgress progress = player.getAdvancements().getOrStartProgress(firstContactAdv);
                if (!progress.isDone())
                {
                    player.displayClientMessage(Component.translatable("message.pasterdream.dyedream_crack.first_contact.1"), false);
                    player.displayClientMessage(Component.translatable("message.pasterdream.dyedream_crack.first_contact.2"), false);
                    player.displayClientMessage(Component.translatable("message.pasterdream.dyedream_crack.first_contact.3"), false);
                    for (String criteria : progress.getRemainingCriteria())
                    {
                        player.getAdvancements().award(firstContactAdv, criteria);
                    }
                }
            }

            player.setPortalCooldown(20);
        }
    }

    private void teleportToDreamDimension(ServerPlayer player, BlockState state, BlockPos pos)
    {
        ServerLevel currentLevel = player.serverLevel();
        ResourceKey<Level> targetDim = currentLevel.dimension().equals(DYEDREAM_WORLD) ? Level.OVERWORLD : DYEDREAM_WORLD;
        ServerLevel targetLevel = currentLevel.getServer().getLevel(targetDim);
        if (targetLevel == null)
        {
            return;
        }

        if (!(targetLevel.getBlockState(pos).getBlock() instanceof DyedreamCrackBlock))
        {
            placePortalStructure(targetLevel, state, pos);
        }

        Direction direction = state.getValue(FACING);

        switch (direction)
        {
            case EAST  -> player.teleportTo(targetLevel, pos.getX() + 2.5, pos.getY() + 0.5, pos.getZ() + 0.5, 90, 30);
            case SOUTH -> player.teleportTo(targetLevel, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 2.5, 180, 30);
            case WEST  -> player.teleportTo(targetLevel, pos.getX() - 1.5, pos.getY() + 0.5, pos.getZ() + 0.5, 270, 30);
            case NORTH -> player.teleportTo(targetLevel, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() - 1.5, 0, 30);
        }
    }

    private void placePortalStructure(ServerLevel level, BlockState state, BlockPos portalPos)
    {
        Direction portalFacing = state.getValue(FACING);
        StructureTemplateManager manager = level.getStructureManager();
        ResourceLocation structureId = ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_crack");

        manager.get(structureId).ifPresent(template ->
        {
            BlockPos originalRelativePos = new BlockPos(9, 8, 7);

            Rotation rotation = switch (portalFacing)
            {
                case SOUTH -> Rotation.CLOCKWISE_180;
                case WEST  -> Rotation.COUNTERCLOCKWISE_90;
                case EAST  -> Rotation.CLOCKWISE_90;
                default    -> Rotation.NONE;
            };

            BlockPos rotatedRelativePos = originalRelativePos.rotate(rotation);

            BlockPos structureOrigin = portalPos.subtract(rotatedRelativePos);
            StructurePlaceSettings settings = new StructurePlaceSettings();
            settings.setRotation(rotation);
            template.placeInWorld(level, structureOrigin, structureOrigin, settings, level.getRandom(), 2);
        });
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext)
    {
        return false;
    }
}
