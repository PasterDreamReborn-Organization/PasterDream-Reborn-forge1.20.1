package com.pasterdream.pasterdreammod.world.block.portal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class DyedreamCrackBlock extends DirectionalBlock
{
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final ResourceKey<Level> DYEDREAM_WORLD = ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_world"));

    public DyedreamCrackBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        Direction facing = state.getValue(FACING);
        if(facing == Direction.NORTH || facing == Direction.SOUTH)
        {
            return box(-8, -8, 7, 24, 24, 9);
        }
            else
            {
                return box(7, -8, -8, 9, 24, 24);
            }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        if (!level.isClientSide && entity instanceof ServerPlayer player)
        {
            if (player.getPortalCooldown() > 0)
            {
                return;
            }

            teleportToDreamDimension(player, state, pos);

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
}
