package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.world.dimension.DyedreamDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

import java.util.List;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModWorldGenEvents {

    private static final int WORLDTREE_X = 2002;
    private static final int WORLDTREE_Z = 1128;
    private static final int HEIGHT_OFFSET = -25;
    private static final ResourceLocation WORLDTREE_TOP =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_worldtree_top");
    private static final ResourceLocation WORLDTREE_BOTTOM =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_worldtree_bottom");
    private static final TagKey<Biome> OCEAN_BIOMES = TagKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_fishing_biomes"));

    private static volatile boolean worldtreeNeedsPlacement = false;

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (!serverLevel.dimension().equals(DyedreamDimension.DYEDREAM_WORLD)) return;

        WorldtreePlacedData data = WorldtreePlacedData.get(serverLevel);
        if (!data.isPlaced()) {
            worldtreeNeedsPlacement = true;
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!worldtreeNeedsPlacement) return;

        ServerLevel dyedream = event.getServer().getLevel(DyedreamDimension.DYEDREAM_WORLD);
        if (dyedream == null) return;

        WorldtreePlacedData data = WorldtreePlacedData.get(dyedream);
        if (data.isPlaced()) {
            worldtreeNeedsPlacement = false;
            return;
        }

        worldtreeNeedsPlacement = false;
        placeWorldtree(dyedream);
        data.setPlaced();
    }

    private static void placeWorldtree(ServerLevel serverLevel) {
        StructureTemplate topTemplate = serverLevel.getStructureManager()
                .get(WORLDTREE_TOP).orElse(null);
        StructureTemplate bottomTemplate = serverLevel.getStructureManager()
                .get(WORLDTREE_BOTTOM).orElse(null);
        if (topTemplate == null || bottomTemplate == null) return;

        int chunkX = WORLDTREE_X >> 4;
        int chunkZ = WORLDTREE_Z >> 4;
        serverLevel.getChunkSource().getChunk(chunkX, chunkZ, true);

        StructurePlaceSettings settings = new StructurePlaceSettings();
        RandomSource random = serverLevel.getRandom();

        Holder<Biome> biome = serverLevel.getBiome(new BlockPos(WORLDTREE_X, 64, WORLDTREE_Z));
        boolean isOcean = biome.is(OCEAN_BIOMES);
        int surfaceY = serverLevel.getHeight(Heightmap.Types.WORLD_SURFACE_WG, WORLDTREE_X, WORLDTREE_Z);
        int baseY = isOcean ? serverLevel.getSeaLevel() : surfaceY;
        int offsetY = isOcean ? 0 : HEIGHT_OFFSET;
        BlockPos topOrigin = new BlockPos(WORLDTREE_X, baseY + offsetY, WORLDTREE_Z);

        topTemplate.placeInWorld(serverLevel, topOrigin, topOrigin, settings, random, 2);

        StructureBlockInfo topJigsaw = findFirstJigsaw(topTemplate);
        if (topJigsaw == null || topJigsaw.nbt() == null) {
            Vec3i topSize = topTemplate.getSize();
            BlockPos bottomOrigin = topOrigin.offset(0, -topSize.getY(), 0);
            bottomTemplate.placeInWorld(serverLevel, bottomOrigin, bottomOrigin, settings, random, 2);
            return;
        }

        CompoundTag topJigsawTag = topJigsaw.nbt();

        FrontAndTop orientation = topJigsaw.state().getValue(JigsawBlock.ORIENTATION);
        Direction facing = orientation.front();

        String finalStateStr = topJigsawTag.getString("final_state");
        Block replacementBlock = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(finalStateStr));
        if (replacementBlock == null) replacementBlock = Blocks.AIR;

        BlockPos topJigsawWorldPos = topOrigin.offset(topJigsaw.pos());
        BlockPos connectionPoint = topJigsawWorldPos.relative(facing);

        String targetName = topJigsawTag.getString("target");
        StructureBlockInfo bottomJigsaw = findJigsawByName(bottomTemplate, targetName);

        BlockPos bottomOrigin;
        if (bottomJigsaw != null) {
            bottomOrigin = connectionPoint.subtract(bottomJigsaw.pos());
        } else {
            Vec3i topSize = topTemplate.getSize();
            bottomOrigin = topOrigin.offset(0, -topSize.getY(), 0);
        }

        bottomTemplate.placeInWorld(serverLevel, bottomOrigin, bottomOrigin, settings, random, 2);

        serverLevel.setBlock(topJigsawWorldPos, replacementBlock.defaultBlockState(), 3);
        if (bottomJigsaw != null) {
            BlockPos bottomJigsawWorldPos = bottomOrigin.offset(bottomJigsaw.pos());
            serverLevel.setBlock(bottomJigsawWorldPos, replacementBlock.defaultBlockState(), 3);
        }
    }

    private static StructureBlockInfo findFirstJigsaw(StructureTemplate template) {
        List<StructureBlockInfo> blocks = template.filterBlocks(
                BlockPos.ZERO, new StructurePlaceSettings(), Blocks.JIGSAW, false);
        return blocks.isEmpty() ? null : blocks.get(0);
    }

    private static StructureBlockInfo findJigsawByName(StructureTemplate template, String targetName) {
        List<StructureBlockInfo> blocks = template.filterBlocks(
                BlockPos.ZERO, new StructurePlaceSettings(), Blocks.JIGSAW, false);
        for (StructureBlockInfo block : blocks) {
            if (block.nbt() != null && block.nbt().getString("name").equals(targetName)) {
                return block;
            }
        }
        return null;
    }

    public static class WorldtreePlacedData extends SavedData {
        private static final String DATA_NAME = "pasterdream_worldtree_placed";
        private boolean placed = false;

        public WorldtreePlacedData() {}

        public static WorldtreePlacedData load(CompoundTag tag) {
            WorldtreePlacedData data = new WorldtreePlacedData();
            data.placed = tag.getBoolean("placed");
            return data;
        }

        @Override
        public CompoundTag save(CompoundTag tag) {
            tag.putBoolean("placed", this.placed);
            return tag;
        }

        public static WorldtreePlacedData get(ServerLevel level) {
            return level.getDataStorage().computeIfAbsent(
                    WorldtreePlacedData::load, WorldtreePlacedData::new, DATA_NAME);
        }

        public boolean isPlaced() {
            return this.placed;
        }

        public void setPlaced() {
            this.placed = true;
            setDirty();
        }
    }
}
