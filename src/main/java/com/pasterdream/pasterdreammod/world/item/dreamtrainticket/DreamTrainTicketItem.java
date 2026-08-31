package com.pasterdream.pasterdreammod.world.item.dreamtrainticket;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 逐梦列车车票：在玩家头顶（列车自然生成高度）召唤一辆逐梦列车并消耗车票。
 * <p>
 * 默认每个玩家在每个维度只能使用一次（记录在玩家持久 NBT 中），包括染梦世界；
 * 配置项 trainTicketOnePerDimension 开启后，改为每个维度全服只能有 1 人使用（记录在存档 SavedData 中）。
 */
public class DreamTrainTicketItem extends Item {

    /** 列车自然生成的 start_height（绝对高度），与 ModStructureConfig 中 dream_train 一致 */
    private static final int TRAIN_START_HEIGHT = 145;
    /** 玩家持久数据中记录已使用车票的维度列表 key */
    private static final String USED_DIMENSIONS_KEY = "pasterdream:used_train_ticket_dims";
    /** 列车结构高度（dream_train.nbt y 方向尺寸），用于越界钳制 */
    private static final int TRAIN_HEIGHT = 44;

    private static final DecimalFormat COORD_FORMAT = new DecimalFormat("#");

    public DreamTrainTicketItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide()) {
            return InteractionResultHolder.consume(stack);
        }
        ServerLevel serverLevel = (ServerLevel) level;
        String dimensionKey = level.dimension().location().toString();

        if (Config.trainTicketOnePerDimension) {
            TrainTicketUsedData usedData = TrainTicketUsedData.get(serverLevel);
            if (usedData.isDimensionUsed(dimensionKey)) {
                player.displayClientMessage(Component.translatable("message.pasterdream.dream_train_ticket.dimension_used"), true);
                return InteractionResultHolder.fail(stack);
            }
            spawnTrain(serverLevel, player);
            usedData.markDimensionUsed(dimensionKey);
        } else {
            ListTag usedDimensions = player.getPersistentData().getList(USED_DIMENSIONS_KEY, Tag.TAG_STRING);
            for (Tag tag : usedDimensions) {
                if (tag.getAsString().equals(dimensionKey)) {
                    player.displayClientMessage(Component.translatable("message.pasterdream.dream_train_ticket.already_used"), true);
                    return InteractionResultHolder.fail(stack);
                }
            }
            spawnTrain(serverLevel, player);
            usedDimensions.add(StringTag.valueOf(dimensionKey));
            player.getPersistentData().put(USED_DIMENSIONS_KEY, usedDimensions);
        }

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(stack, false);
    }

    private void spawnTrain(ServerLevel level, Player player) {
        int x = player.blockPosition().getX();
        int z = player.blockPosition().getZ();
        int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
        int trainY = Mth.clamp(TRAIN_START_HEIGHT + surfaceY,
                level.getMinBuildHeight(), level.getMaxBuildHeight() - TRAIN_HEIGHT);

        Rotation rotation = switch (Direction.fromYRot(player.getYRot())) {
            case WEST -> Rotation.CLOCKWISE_90;
            case NORTH -> Rotation.CLOCKWISE_180;
            case EAST -> Rotation.COUNTERCLOCKWISE_90;
            default -> Rotation.NONE;
        };

        StructureTemplate template = level.getStructureManager()
                .getOrCreate(ResourceLocation.fromNamespaceAndPath("pasterdream", "dream_train"));
        BlockPos origin = new BlockPos(x, trainY, z);
        template.placeInWorld(level, origin, origin,
                new StructurePlaceSettings().setRotation(rotation)
                        .setMirror(Mirror.NONE).setIgnoreEntities(false),
                level.random, 3);

        var playerList = level.getServer().getPlayerList();
        playerList.broadcastSystemMessage(
                Component.translatable("message.pasterdream.dream_train.train_pass"), false);
        playerList.broadcastSystemMessage(
                Component.translatable("message.pasterdream.dream_train.location_info",
                        COORD_FORMAT.format(x), COORD_FORMAT.format(z)),
                false);
        player.playNotifySound(ModSounds.WIND_CHIME.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
    }

    /** 存档级记录：开启 trainTicketOnePerDimension 后，每个维度全服只能使用 1 次车票 */
    public static class TrainTicketUsedData extends SavedData {

        private static final String DATA_NAME = "pasterdream_dream_train_ticket_used";
        private final Set<String> usedDimensions = new HashSet<>();

        public static TrainTicketUsedData load(CompoundTag tag) {
            TrainTicketUsedData data = new TrainTicketUsedData();
            ListTag list = tag.getList("usedDimensions", Tag.TAG_STRING);
            for (Tag t : list) {
                data.usedDimensions.add(t.getAsString());
            }
            return data;
        }

        @Override
        public CompoundTag save(CompoundTag tag) {
            ListTag list = new ListTag();
            for (String dim : usedDimensions) {
                list.add(StringTag.valueOf(dim));
            }
            tag.put("usedDimensions", list);
            return tag;
        }

        public static TrainTicketUsedData get(ServerLevel level) {
            return level.getDataStorage().computeIfAbsent(
                    TrainTicketUsedData::load, TrainTicketUsedData::new, DATA_NAME);
        }

        public boolean isDimensionUsed(String dimensionKey) {
            return usedDimensions.contains(dimensionKey);
        }

        public void markDimensionUsed(String dimensionKey) {
            usedDimensions.add(dimensionKey);
            setDirty();
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.dream_train_ticket"));
        tooltip.add(Component.translatable("tooltip.pasterdream.dream_train_ticket.usage"));
    }
}