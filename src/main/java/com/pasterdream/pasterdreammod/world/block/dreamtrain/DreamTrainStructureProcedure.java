package com.pasterdream.pasterdreammod.world.block.dreamtrain;

import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DreamTrainStructureProcedure {

    private static final DecimalFormat COORD_FORMAT = new DecimalFormat("#");
    private static final Map<StructureTemplate, ResourceLocation> TEMPLATE_IDS = new ConcurrentHashMap<>();

    public static void registerTemplateId(StructureTemplate template, ResourceLocation id) {
        TEMPLATE_IDS.put(template, id);
    }

    public static ResourceLocation getTemplateId(StructureTemplate template) {
        return TEMPLATE_IDS.get(template);
    }

    public static void placeTrain(LevelAccessor world, BlockPos pos) {
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        if (world instanceof ServerLevel serverLevel) {
            StructureTemplate template = serverLevel.getStructureManager()
                    .getOrCreate(ResourceLocation.fromNamespaceAndPath("pasterdream", "dream_train"));
            template.placeInWorld(serverLevel, pos, pos,
                    new StructurePlaceSettings().setRotation(Rotation.NONE)
                            .setMirror(Mirror.NONE).setIgnoreEntities(false),
                    serverLevel.random, 3);
        }
        broadcast(world, pos.getX(), pos.getZ());
    }

    private static void broadcast(LevelAccessor world, double x, double z) {
        if (!world.isClientSide() && world.getServer() != null) {
            world.getServer().getPlayerList().broadcastSystemMessage(
                    Component.translatable("message.pasterdream.dream_train.train_pass"), false);
            world.getServer().getPlayerList().broadcastSystemMessage(
                    Component.translatable("message.pasterdream.dream_train.location_info",
                            COORD_FORMAT.format(x), COORD_FORMAT.format(z)),
                    false);
            for (ServerPlayer player : world.getServer().getPlayerList().getPlayers()) {
                player.playNotifySound(ModSounds.WIND_CHIME.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
            }
        }
    }
}
