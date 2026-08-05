package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.world.dimension.DyedreamDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
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

    // 暮影之笼结构（下界基岩层上方）
    private static final ResourceLocation SHADOW_WORLD_DOOR =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "shadow_world_door");
    private static final int SHADOW_DOOR_Y = 128;
    private static final int SHADOW_DOOR_RANGE = 2000;

    private static final ResourceLocation WORLDTREE_TOP =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_worldtree_top");
    private static final ResourceLocation WORLDTREE_BOTTOM =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_worldtree_bottom");

    private static volatile boolean worldtreeNeedsPlacement = false;
    private static volatile boolean shadowDoorNeedsPlacement = false;

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        if (serverLevel.dimension().equals(DyedreamDimension.DYEDREAM_WORLD)) {
            WorldtreePlacedData data = WorldtreePlacedData.get(serverLevel);
            if (!data.isPlaced()) {
                worldtreeNeedsPlacement = true;
            }
        }

        if (serverLevel.dimension().equals(Level.NETHER)) {
            ShadowWorldDoorPlacedData data = ShadowWorldDoorPlacedData.get(serverLevel);
            if (!data.isPlaced()) {
                shadowDoorNeedsPlacement = true;
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (worldtreeNeedsPlacement) {
            ServerLevel dyedream = event.getServer().getLevel(DyedreamDimension.DYEDREAM_WORLD);
            if (dyedream != null) {
                WorldtreePlacedData data = WorldtreePlacedData.get(dyedream);
                if (!data.isPlaced()) {
                    worldtreeNeedsPlacement = false;
                    placeWorldtree(dyedream);
                    data.setPlaced();
                } else {
                    worldtreeNeedsPlacement = false;
                }
            }
        }

        if (shadowDoorNeedsPlacement) {
            ServerLevel nether = event.getServer().getLevel(Level.NETHER);
            if (nether != null) {
                ShadowWorldDoorPlacedData data = ShadowWorldDoorPlacedData.get(nether);
                if (!data.isPlaced()) {
                    shadowDoorNeedsPlacement = false;
                    placeShadowWorldDoor(nether, data);
                } else {
                    shadowDoorNeedsPlacement = false;
                }
            }
        }
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

        BlockPos topOrigin = new BlockPos(WORLDTREE_X, serverLevel.getHeight(Heightmap.Types.WORLD_SURFACE_WG, WORLDTREE_X, WORLDTREE_Z), WORLDTREE_Z);

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

    private static void placeShadowWorldDoor(ServerLevel serverLevel, ShadowWorldDoorPlacedData data) {
        StructureTemplate template = serverLevel.getStructureManager()
                .get(SHADOW_WORLD_DOOR).orElse(null);
        if (template == null) return;

        RandomSource random = serverLevel.getRandom();
        int x = random.nextIntBetweenInclusive(-SHADOW_DOOR_RANGE, SHADOW_DOOR_RANGE);
        int z = random.nextIntBetweenInclusive(-SHADOW_DOOR_RANGE, SHADOW_DOOR_RANGE);

        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        serverLevel.getChunkSource().getChunk(chunkX, chunkZ, true);

        BlockPos origin = new BlockPos(x - 22, SHADOW_DOOR_Y, z - 21);
        StructurePlaceSettings settings = new StructurePlaceSettings();
        template.placeInWorld(serverLevel, origin, origin, settings, random, 3);

        data.setPlaced(x, z);
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

    public static class ShadowWorldDoorPlacedData extends SavedData {
        private static final String DATA_NAME = "pasterdream_shadow_world_door";
        private boolean placed = false;
        private int posX;
        private int posZ;

        public ShadowWorldDoorPlacedData() {}

        public static ShadowWorldDoorPlacedData load(CompoundTag tag) {
            ShadowWorldDoorPlacedData data = new ShadowWorldDoorPlacedData();
            data.placed = tag.getBoolean("placed");
            data.posX = tag.getInt("posX");
            data.posZ = tag.getInt("posZ");
            return data;
        }

        @Override
        public CompoundTag save(CompoundTag tag) {
            tag.putBoolean("placed", this.placed);
            tag.putInt("posX", this.posX);
            tag.putInt("posZ", this.posZ);
            return tag;
        }

        public static ShadowWorldDoorPlacedData get(ServerLevel level) {
            return level.getDataStorage().computeIfAbsent(
                    ShadowWorldDoorPlacedData::load, ShadowWorldDoorPlacedData::new, DATA_NAME);
        }

        public boolean isPlaced() {
            return this.placed;
        }

        public int getPosX() {
            return posX;
        }

        public int getPosZ() {
            return posZ;
        }

        public void setPlaced(int x, int z) {
            this.placed = true;
            this.posX = x;
            this.posZ = z;
            setDirty();
        }
    }
}
