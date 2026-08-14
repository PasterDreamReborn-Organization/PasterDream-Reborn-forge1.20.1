package com.pasterdream.pasterdreammod.world.block.dreamtrain;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;

import java.text.DecimalFormat;

/**
 * 逐梦列车广播方块实体（兼作列车生成器）。
 *
 * 列车长 230 格，超出原版结构生成的 128 格引用半径，无法直接由 jigsaw 完整放置，
 * 因此 jigsaw 只放置这个 1 格标记方块；当有玩家靠近时，本实体直接调用 placeInWorld
 * 放置整列列车，广播一次提示，然后把标记自身置为空气。
 *
 * 触发条件是"玩家邻近"而非世界生成，所以不会被 Distant Horizons 等模组的区块预生成提前触发。
 */
public class DreamTrainBroadcastBlockEntity extends BlockEntity {

    private static final DecimalFormat COORD_FORMAT = new DecimalFormat("#");
    /** 玩家靠近多少格内触发列车生成与广播 */
    private static final double DETECT_RANGE = 200.0D;

    public DreamTrainBroadcastBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DREAM_TRAIN_BROADCAST.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, DreamTrainBroadcastBlockEntity be) {
        if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) {
            return;
        }
        if (!serverLevel.getBlockState(pos).is(state.getBlock())) {
            return;
        }
        AABB range = AABB.ofSize(pos.getCenter(), DETECT_RANGE * 2, DETECT_RANGE * 2, DETECT_RANGE * 2);
        boolean playerNearby = !serverLevel.getEntitiesOfClass(Player.class, range, p -> !p.isSpectator()).isEmpty();
        if (!playerNearby) {
            return;
        }
        serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        placeTrain(serverLevel, pos);
        broadcast(serverLevel, pos);
    }

    private static void placeTrain(ServerLevel level, BlockPos pos) {
        StructureTemplate template = level.getStructureManager()
                .getOrCreate(ResourceLocation.fromNamespaceAndPath("pasterdream", "dream_train"));
        template.placeInWorld(level, pos, pos,
                new StructurePlaceSettings().setRotation(Rotation.NONE)
                        .setMirror(Mirror.NONE).setIgnoreEntities(false),
                level.random, 3);
    }

    private static void broadcast(ServerLevel level, BlockPos pos) {
        var playerList = level.getServer().getPlayerList();
        playerList.broadcastSystemMessage(
                Component.translatable("message.pasterdream.dream_train.train_pass"), false);
        playerList.broadcastSystemMessage(
                Component.translatable("message.pasterdream.dream_train.location_info",
                        COORD_FORMAT.format(pos.getX()), COORD_FORMAT.format(pos.getZ())),
                false);
        for (ServerPlayer player : playerList.getPlayers()) {
            player.playNotifySound(ModSounds.WIND_CHIME.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
        }
    }
}
