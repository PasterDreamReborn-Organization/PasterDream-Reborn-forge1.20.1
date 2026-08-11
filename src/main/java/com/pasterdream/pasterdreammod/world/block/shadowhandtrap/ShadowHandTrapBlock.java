package com.pasterdream.pasterdreammod.world.block.shadowhandtrap;

import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ShadowHandTrapBlock extends BaseEntityBlock {
    public static final IntegerProperty ANIMATION = IntegerProperty.create("animation", 0, 1);

    private static final VoxelShape SHAPE = box(1, 0, 1, 15, 8, 15);

    public ShadowHandTrapBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .sound(SoundType.STONE)
                .strength(1.5f, 6.0f)
                .noCollission()
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ANIMATION, 0));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShadowHandTrapBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ANIMATION);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(state, world, pos, random);
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof ShadowHandTrapBlockEntity trap) {
            trap.onServerTick();
        }

        // 触发后 25 tick 自毁
        if (be != null && be.getPersistentData().getBoolean("triggered")) {
            world.destroyBlock(pos, false);
            return;
        }

        world.scheduleTick(pos, this, 20);
    }

    /**
     * 踩上陷阱的玩家触发效果：
     * 播放攻击动画 + 音效 → 造成魔法伤害 + 扣 SAN + 黑暗/缓慢效果 → 25tick 后自毁
     */
    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        super.entityInside(state, world, pos, entity);
        if (world.isClientSide()) return;
        if (!(entity instanceof Player player)) return;

        BlockEntity be = world.getBlockEntity(pos);
        if (be == null) return;

        // 防止重复触发
        boolean triggered = be.getPersistentData().getBoolean("triggered");
        if (triggered) return;

        be.getPersistentData().putBoolean("triggered", true);
        world.sendBlockUpdated(pos, state, state, 3);

        // 播放攻击动画
        if (state.hasProperty(ANIMATION) && state.getValue(ANIMATION) == 0) {
            world.setBlock(pos, state.setValue(ANIMATION, 1), 3);
        }

        // 播放音效
        world.playSound(null, pos, ModSounds.SHADOW0.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        // 5 点魔法伤害
        player.hurt(new DamageSource(world.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(DamageTypes.MAGIC)), 5.0F);

        // 扣 SAN
        if (player instanceof ServerPlayer serverPlayer) {
            SanHelper.addPlayerSanAndSync(serverPlayer, -5.0);
        }

        // 负面效果
        player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 80, 0));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));

        // 定身效果
        player.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));

        // 25 tick 后生成暗影之手并自毁
        world.scheduleTick(pos, this, 25);
    }

    /**
     * 方块被玩家破坏时生成暗影之手实体
     */
    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, net.minecraft.world.level.material.FluidState fluid) {
        boolean retval = super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
        // 精准采集挖掘不生成暗影之手
        if (!player.isCreative()&&!world.isClientSide() && world instanceof ServerLevel serverLevel
                && player.getMainHandItem().getEnchantmentLevel(Enchantments.SILK_TOUCH) == 0) {
            var shadowHand = ModEntities.SHADOW_HAND.get().spawn(serverLevel,
                    BlockPos.containing(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5),
                    MobSpawnType.MOB_SUMMONED);
            if (shadowHand != null) {
                shadowHand.setYRot(world.getRandom().nextFloat() * 360F);
            }
            if (player instanceof ServerPlayer serverPlayer) {
                SanHelper.addPlayerSanAndSync(serverPlayer, -1.0);
            }
        }
        return retval;
    }
}
