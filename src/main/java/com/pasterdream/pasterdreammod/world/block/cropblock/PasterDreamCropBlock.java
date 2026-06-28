package com.pasterdream.pasterdreammod.world.block.cropblock;

import com.pasterdream.pasterdreammod.init.ModCropRelation;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.Tags;

public class PasterDreamCropBlock extends BushBlock implements IPlantable
{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;

    public PasterDreamCropBlock(Properties properties)
    {
        super(properties.sound(SoundType.GRASS));
        registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(AGE);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(AGE) < 1;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (level.getRawBrightness(pos, 0) >= 9)
        {
            if (state.getValue(AGE) < 1 && random.nextInt(10) == 0)
            {
                level.setBlock(pos, state.setValue(AGE, 1), 2);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        return box(4, 0, 4, 12, 12, 12);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos below = pos.below();
        BlockState groundState = level.getBlockState(below);
        return groundState.canSustainPlant(level, below, Direction.UP, this) || super.canSurvive(state, level, pos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        CropBindingContent binding = ModCropRelation.getBinding(this);
        return binding != null && state.is(binding.plantableOnTag());
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience)
    {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        ItemStack held = player.getItemInHand(hand);
        if (held.is(ModItems.DREAM_FERTILIZER.get()) && state.getValue(AGE) < 1)
        {
            if (!level.isClientSide)
            {
                level.setBlock(pos, state.setValue(AGE, 1), 2);
                if(!player.isCreative())
                {
                    held.shrink(1);
                }
                level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (state.getValue(AGE) == 1 && held.is(Tags.Items.SHEARS))
        {
            if (!level.isClientSide)
            {
                CropBindingContent binding = ModCropRelation.getBinding(this);
                if (binding != null)
                {
                    popResource(level, pos, new ItemStack(binding.productItem(), binding.productCount()));
                    level.setBlock(pos, state.setValue(AGE, 0), 2);
                    held.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                    level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        CropBindingContent binding = ModCropRelation.getBinding(this);
        if (state.getValue(AGE) == 1 && binding != null && binding.matureItem() != null)
        {
            return new ItemStack(binding.matureItem());
        }
        return new ItemStack(this);
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos)
    {
        return PlantType.CROP;
    }

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos)
    {
        BlockState state = level.getBlockState(pos);
        return state.is(this) ? state : defaultBlockState();
    }
}
