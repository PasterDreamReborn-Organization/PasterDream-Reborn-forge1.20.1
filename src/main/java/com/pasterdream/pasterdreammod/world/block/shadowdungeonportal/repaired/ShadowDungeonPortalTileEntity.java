package com.pasterdream.pasterdreammod.world.block.shadowdungeonportal.repaired;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.init.ModShadowDungeonStructureSet;
import com.pasterdream.pasterdreammod.network.animationstatechange.AnimationStateChangePacket;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import com.pasterdream.pasterdreammod.world.block.geckolibblock.AnimatableSync;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

public class ShadowDungeonPortalTileEntity extends BlockEntity implements GeoBlockEntity, AnimatableSync
{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int animationState = 0;
    private int tickCounter = 0;
    private List<Player> playerList = new ArrayList<>();
    private int progress = 0;//0:第一次进入，1:第二次进入，2:后续刷材料
    private int allFloorCounts = 0;

    public boolean isEntry = true;
    public BlockPos targetPosition = new BlockPos(0, 0, 0);

    public ShadowDungeonPortalTileEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.SHADOW_DUNGEON_PORTAL.get(), pos, state);
    }

    public void activeShadowDungeonPortal(BlockPos blockPosition, Player player)
    {
        Level level = this.level;
        if (level == null || level.isClientSide)
        {
            return;
        }

        if (isEntry)
        {
            if (blockPosition.getY() >= level.getMinBuildHeight() + 70)
            {
                if (!checkIsHavePlayer())
                {
                    animationState = 1;
                    setChangedAndSync();
                }
                    else
                    {
                        player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.此暗影地牢中存在其他玩家"), false);
                    }
            }
                else
                {
                    player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.too_low"), false);
                }
        }
            else
            {
                animationState = 1;
                setChangedAndSync();
            }
    }

    private boolean checkIsHavePlayer()
    {
        Level level = this.level;
        BlockPos entryPosition = this.worldPosition;

        AABB checkBox = new AABB(entryPosition.getX() - 12, level.getMinBuildHeight() + 2, entryPosition.getZ() - 12, entryPosition.getX() + 12, level.getMinBuildHeight() + 65, entryPosition.getZ() + 12);
        List<Player> players = level.getEntitiesOfClass(Player.class, checkBox);
        return !players.isEmpty();
    }

    private List<ItemEntity> checkIsHaveLostItem()
    {
        Level level = this.level;
        BlockPos entryPosition = this.worldPosition;

        AABB checkBox = new AABB(entryPosition.getX() - 12, level.getMinBuildHeight() + 2, entryPosition.getZ() - 12, entryPosition.getX() + 12, level.getMinBuildHeight() + 65, entryPosition.getZ() + 12);
        return level.getEntitiesOfClass(ItemEntity.class, checkBox);
    }

    private List<LivingEntity> checkIsHaveLivingEntity()
    {
        Level level = this.level;
        BlockPos entryPosition = this.worldPosition;

        AABB checkBox = new AABB(entryPosition.getX() - 12, level.getMinBuildHeight() + 2, entryPosition.getZ() - 12, entryPosition.getX() + 12, level.getMinBuildHeight() + 65, entryPosition.getZ() + 12);
        return level.getEntitiesOfClass(LivingEntity.class, checkBox);
    }

    private List<Player> getNearlyPlayer(BlockPos portalPos)
    {
        if ((level instanceof ServerLevel))
        {
            AABB box = new AABB(portalPos.getX() - 7, portalPos.getY() - 2, portalPos.getZ() - 7, portalPos.getX() + 7, portalPos.getY() + 6, portalPos.getZ() + 7);
            return level.getEntitiesOfClass(Player.class, box);
        }
            else
            {
                return new ArrayList<>();
            }
    }

    private void teleportPlayers(List<Player> playerList, double x, double y, double z)
    {
        if (!(level instanceof ServerLevel serverLevel))
        {
            return;
        }

        for (Player player : playerList)
        {
            if (player instanceof ServerPlayer serverPlayer)
            {
                serverPlayer.teleportTo(serverLevel, x, y, z, 90, 30);
            }
        }
    }

    private void placeStructure(ResourceLocation resourceLocation, int x, int y, int z)
    {
        if(!level.isClientSide())
        {
            StructureTemplateManager manager = ((ServerLevel)level).getStructureManager();

            manager.get(resourceLocation).ifPresent(template ->
            {
                BlockPos structureOrigin = new BlockPos(x, y, z);
                template.placeInWorld((ServerLevel)level, structureOrigin, structureOrigin, new StructurePlaceSettings(), level.getRandom(), 2);
            });
        }
    }

    public static void tick(Level level, BlockPos blockPosition, ShadowDungeonPortalTileEntity blockEntity)
    {
        if (level.isClientSide)
        {
            return;
        }

        if(blockEntity.animationState == 1)
        {
            if(blockEntity.isEntry)
            {
                switch (blockEntity.tickCounter)
                {
                    case 0 ->
                    {
                        blockEntity.playerList = blockEntity.getNearlyPlayer(blockPosition);
                        List<ItemEntity> itemEntities = blockEntity.checkIsHaveLostItem();
                        List<LivingEntity> livingEntities = blockEntity.checkIsHaveLivingEntity();

                        if (itemEntities.isEmpty() && livingEntities.isEmpty())
                        {
                            for (Player player : blockEntity.playerList)
                            {
                                player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.传送倒计时：").append("3"), false);
                                Advancement _1timesAdvancement = ((ServerPlayer)player).server.getAdvancements().getAdvancement(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_npc_first_dialogue"));
                                if (_1timesAdvancement != null && ((ServerPlayer)player).getAdvancements().getOrStartProgress(_1timesAdvancement).isDone())
                                {
                                    blockEntity.progress = 1;
                                }

                                Advancement finishedAdvancement = ((ServerPlayer)player).server.getAdvancements().getAdvancement(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_choice"));
                                if (finishedAdvancement != null && ((ServerPlayer)player).getAdvancements().getOrStartProgress(finishedAdvancement).isDone())
                                {
                                    blockEntity.progress = 2;
                                }
                            }
                        }
                            else
                            {
                                for(ItemEntity itemEntity : itemEntities)
                                {
                                    itemEntity.setPos(blockPosition.getX() + 0.5, blockPosition.getY() - 1, blockPosition.getZ() + 0.5);
                                }

                                for(LivingEntity livingEntity : livingEntities)
                                {
                                    if(livingEntity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_mob"))))
                                    {
                                        livingEntity.discard();
                                    }
                                        else
                                        {
                                            livingEntity.setPos(blockPosition.getX() + 0.5, blockPosition.getY() + 1, blockPosition.getZ() + 0.5);
                                        }
                                }

                                for (Player player : blockEntity.playerList)
                                {
                                    player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.已传送出暗影地牢内的掉落物"), false);
                                    player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.已传送出或清除暗影地牢内的其他生物"), false);
                                }
                                blockEntity.animationState = 0;
                                blockEntity.tickCounter = -1;
                                blockEntity.setChangedAndSync();
                            }

                        blockEntity.allFloorCounts = ModShadowDungeonStructureSet.ALL_FLOORS.size();
                    }

                    case 1 ->
                    {
                        blockEntity.placeStructure(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_shadow_bed"), blockPosition.getX() - 12, level.getMinBuildHeight() + 1, blockPosition.getZ() - 12);

                        if (level.getBlockEntity(new BlockPos(blockPosition.getX(), level.getMinBuildHeight() + 15, blockPosition.getZ() - 3)) instanceof ShadowDungeonPortalTileEntity exitPortal)
                        {
                            exitPortal.isEntry = false;
                            exitPortal.targetPosition = blockPosition;
                            exitPortal.setChangedAndSync();
                        }
                    }

                    case 2 ->
                    {
                        if(blockEntity.allFloorCounts >= 1)
                        {
                            blockEntity.placeStructure(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_single_floor_frame"), blockPosition.getX() - 12, level.getMinBuildHeight() + 17, blockPosition.getZ() - 12);
                        }
                    }

                    case 3 ->
                    {
                        if(blockEntity.allFloorCounts >= 2)
                        {
                            blockEntity.placeStructure(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_single_floor_frame"), blockPosition.getX() - 12, level.getMinBuildHeight() + 26, blockPosition.getZ() - 12);
                        }
                    }

                    case 4 ->
                    {
                        if(blockEntity.allFloorCounts >= 3)
                        {
                            blockEntity.placeStructure(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_single_floor_frame"), blockPosition.getX() - 12, level.getMinBuildHeight() + 35, blockPosition.getZ() - 12);
                        }
                    }

                    case 5 ->
                    {
                        if(blockEntity.allFloorCounts >= 4)
                        {
                            blockEntity.placeStructure(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_single_floor_frame"), blockPosition.getX() - 12, level.getMinBuildHeight() + 44, blockPosition.getZ() - 12);
                        }
                    }

                    case 6 ->
                    {
                        if(blockEntity.allFloorCounts >= 5)
                        {
                            blockEntity.placeStructure(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_single_floor_frame"), blockPosition.getX() - 12, level.getMinBuildHeight() + 53, blockPosition.getZ() - 12);
                        }
                    }

                    case 7 ->
                    {
                        if(blockEntity.allFloorCounts >= 6)
                        {
                            blockEntity.placeStructure(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_single_floor_frame"), blockPosition.getX() - 12, level.getMinBuildHeight() + 62, blockPosition.getZ() - 12);
                        }
                    }

                    case 8 ->
                    {
                        if(blockEntity.allFloorCounts >= 7)
                        {
                            blockEntity.placeStructure(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_single_floor_frame"), blockPosition.getX() - 12, level.getMinBuildHeight() + 71, blockPosition.getZ() - 12);
                        }
                    }

                    case 9 ->
                    {
                        if(blockEntity.allFloorCounts >= 8)
                        {
                            blockEntity.placeStructure(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_single_floor_frame"), blockPosition.getX() - 12, level.getMinBuildHeight() + 80, blockPosition.getZ() - 12);
                        }
                    }

                    case 10 ->
                    {
                        if(blockEntity.allFloorCounts >= 9)
                        {
                            blockEntity.placeStructure(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_single_floor_frame"), blockPosition.getX() - 12, level.getMinBuildHeight() + 89, blockPosition.getZ() - 12);
                        }
                    }

                    case 11 ->
                    {
                        if(blockEntity.allFloorCounts >= 10)
                        {
                            blockEntity.placeStructure(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_single_floor_frame"), blockPosition.getX() - 12, level.getMinBuildHeight() + 98, blockPosition.getZ() - 12);
                        }
                    }

                    case 12 ->
                    {
                        if(blockEntity.allFloorCounts >= 11)
                        {
                            blockEntity.placeStructure(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_single_floor_frame"), blockPosition.getX() - 12, level.getMinBuildHeight() + 107, blockPosition.getZ() - 12);
                        }
                    }

                    case 13 -> blockEntity.placeStructure(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon_start_room"), blockPosition.getX() - 12, level.getMinBuildHeight() + (9 * blockEntity.allFloorCounts + 17), blockPosition.getZ() - 12);

                    case 20 ->
                    {
                        for (Player player : blockEntity.playerList)
                        {
                            player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.传送倒计时：").append("2"), false);
                        }
                    }

                    case 30 ->
                    {
                        if(blockEntity.allFloorCounts >= 1)
                        {
                            List<ResourceLocation> floorStructureSets = ModShadowDungeonStructureSet.ALL_FLOORS.get(0);
                            blockEntity.placeStructure(floorStructureSets.get(blockEntity.progress % floorStructureSets.size()), blockPosition.getX() - 11, level.getMinBuildHeight() + 18, blockPosition.getZ() - 11);
                        }
                    }

                    case 31 ->
                    {
                        if(blockEntity.allFloorCounts >= 2)
                        {
                            List<ResourceLocation> floorStructureSets = ModShadowDungeonStructureSet.ALL_FLOORS.get(1);
                            blockEntity.placeStructure(floorStructureSets.get(level.getRandom().nextInt(floorStructureSets.size())), blockPosition.getX() - 11, level.getMinBuildHeight() + 27, blockPosition.getZ() - 11);
                        }
                    }

                    case 32 ->
                    {
                        if(blockEntity.allFloorCounts >= 3)
                        {
                            List<ResourceLocation> floorStructureSets = ModShadowDungeonStructureSet.ALL_FLOORS.get(2);
                            blockEntity.placeStructure(floorStructureSets.get(level.getRandom().nextInt(floorStructureSets.size())), blockPosition.getX() - 11, level.getMinBuildHeight() + 36, blockPosition.getZ() - 11);
                        }
                    }

                    case 33 ->
                    {
                        if(blockEntity.allFloorCounts >= 4)
                        {
                            List<ResourceLocation> floorStructureSets = ModShadowDungeonStructureSet.ALL_FLOORS.get(3);
                            blockEntity.placeStructure(floorStructureSets.get(level.getRandom().nextInt(floorStructureSets.size())), blockPosition.getX() - 11, level.getMinBuildHeight() + 45, blockPosition.getZ() - 11);
                        }
                    }

                    case 34 ->
                    {
                        if(blockEntity.allFloorCounts >= 5)
                        {
                            List<ResourceLocation> floorStructureSets = ModShadowDungeonStructureSet.ALL_FLOORS.get(4);
                            blockEntity.placeStructure(floorStructureSets.get(level.getRandom().nextInt(floorStructureSets.size())), blockPosition.getX() - 11, level.getMinBuildHeight() + 54, blockPosition.getZ() - 11);
                        }
                    }

                    case 35 ->
                    {
                        if(blockEntity.allFloorCounts >= 6)
                        {
                            List<ResourceLocation> floorStructureSets = ModShadowDungeonStructureSet.ALL_FLOORS.get(5);
                            blockEntity.placeStructure(floorStructureSets.get(level.getRandom().nextInt(floorStructureSets.size())), blockPosition.getX() - 11, level.getMinBuildHeight() + 63, blockPosition.getZ() - 11);
                        }
                    }

                    case 36 ->
                    {
                        if(blockEntity.allFloorCounts >= 7)
                        {
                            List<ResourceLocation> floorStructureSets = ModShadowDungeonStructureSet.ALL_FLOORS.get(6);
                            blockEntity.placeStructure(floorStructureSets.get(level.getRandom().nextInt(floorStructureSets.size())), blockPosition.getX() - 11, level.getMinBuildHeight() + 72, blockPosition.getZ() - 11);
                        }
                    }

                    case 37 ->
                    {
                        if(blockEntity.allFloorCounts >= 8)
                        {
                            List<ResourceLocation> floorStructureSets = ModShadowDungeonStructureSet.ALL_FLOORS.get(7);
                            blockEntity.placeStructure(floorStructureSets.get(level.getRandom().nextInt(floorStructureSets.size())), blockPosition.getX() - 11, level.getMinBuildHeight() + 81, blockPosition.getZ() - 11);
                        }
                    }

                    case 38 ->
                    {
                        if(blockEntity.allFloorCounts >= 9)
                        {
                            List<ResourceLocation> floorStructureSets = ModShadowDungeonStructureSet.ALL_FLOORS.get(8);
                            blockEntity.placeStructure(floorStructureSets.get(level.getRandom().nextInt(floorStructureSets.size())), blockPosition.getX() - 11, level.getMinBuildHeight() + 90, blockPosition.getZ() - 11);
                        }
                    }

                    case 39 ->
                    {
                        if(blockEntity.allFloorCounts >= 10)
                        {
                            List<ResourceLocation> floorStructureSets = ModShadowDungeonStructureSet.ALL_FLOORS.get(9);
                            blockEntity.placeStructure(floorStructureSets.get(level.getRandom().nextInt(floorStructureSets.size())), blockPosition.getX() - 11, level.getMinBuildHeight() + 99, blockPosition.getZ() - 11);
                        }
                    }

                    case 40 ->
                    {
                        for (Player player : blockEntity.playerList)
                        {
                            player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.传送倒计时：").append("1"), false);
                        }

                        if(blockEntity.allFloorCounts >= 11)
                        {
                            List<ResourceLocation> floorStructureSets = ModShadowDungeonStructureSet.ALL_FLOORS.get(10);
                            blockEntity.placeStructure(floorStructureSets.get(level.getRandom().nextInt(floorStructureSets.size())), blockPosition.getX() - 11, level.getMinBuildHeight() + 108, blockPosition.getZ() - 11);
                        }
                    }

                    case 60 ->
                    {
                        List<ItemEntity> itemEntities = blockEntity.checkIsHaveLostItem();
                        for(ItemEntity itemEntity : itemEntities)
                        {
                            itemEntity.discard();
                        }

                        blockEntity.animationState = 0;
                        blockEntity.tickCounter = -1;
                        blockEntity.setChangedAndSync();
                        for (Player player : blockEntity.playerList)
                        {
                            if (player instanceof ServerPlayer serverPlayer)
                            {
                                AdvancementHelper.grant(serverPlayer, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_prisoner"), "enter_shadow_dungeon");
                            }
                        }
                        if(blockEntity.progress == 1)
                        {
                            blockEntity.teleportPlayers(blockEntity.playerList, blockPosition.getX() + 4.5, level.getMinBuildHeight() + 19, blockPosition.getZ() + 0.5);
                        }
                            else
                            {
                                blockEntity.teleportPlayers(blockEntity.playerList, blockPosition.getX() + 4.5, level.getMinBuildHeight() + 63, blockPosition.getZ() + 0.5);
                            }
                    }
                }
            }
            else
                {
                    switch (blockEntity.tickCounter)
                    {
                        case 0 ->
                        {
                            blockEntity.playerList = blockEntity.getNearlyPlayer(blockPosition);
                            for (Player player : blockEntity.playerList)
                            {
                                player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.传送倒计时：").append("3"), false);
                            }
                        }

                        case 20 ->
                        {
                            for (Player player : blockEntity.playerList)
                            {
                                player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.传送倒计时：").append("2"), false);
                            }
                        }

                        case 40 ->
                        {
                            for (Player player : blockEntity.playerList)
                            {
                                player.displayClientMessage(Component.translatable("message.pasterdream.broken_portal.传送倒计时：").append("1"), false);
                            }
                        }

                        case 60 ->
                        {
                            blockEntity.animationState = 0;
                            blockEntity.tickCounter = -1;
                            blockEntity.setChangedAndSync();
                            blockEntity.teleportPlayers(blockEntity.playerList, blockEntity.targetPosition.getX() + 3.5, blockEntity.targetPosition.getY() - 1, blockEntity.targetPosition.getZ() + 0.5);
                        }
                    }
                }

            blockEntity.tickCounter++;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers)
    {
        controllers.add(new AnimationController<>(this, "state", 0, this::stateController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache()
    {
        return cache;
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putBoolean("isEntry", isEntry);
        tag.putInt("targetX", targetPosition.getX());
        tag.putInt("targetY", targetPosition.getY());
        tag.putInt("targetZ", targetPosition.getZ());
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        isEntry = tag.getBoolean("isEntry");
        int targetX = tag.getInt("targetX");
        int targetY = tag.getInt("targetY");
        int targetZ = tag.getInt("targetZ");
        targetPosition = new BlockPos(targetX, targetY, targetZ);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void setChangedAndSync()
    {
        setChanged();
        if (level != null && !level.isClientSide)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            sendAnimationSync();
        }
    }

    private PlayState stateController(AnimationState<ShadowDungeonPortalTileEntity> state)
    {
        AnimationController<ShadowDungeonPortalTileEntity> controller = state.getController();

        if(animationState == 0)
        {
            controller.setAnimation(RawAnimation.begin().thenLoop("0"));
        }
        else
        {
            controller.setAnimation(RawAnimation.begin().thenPlay(String.valueOf(animationState)));
        }

        return PlayState.CONTINUE;
    }

    public void setAnimationState(int state)
    {
        animationState = state;
        if (level != null && !level.isClientSide)
        {
            sendAnimationSync();
        }
    }

    private void sendAnimationSync()
    {
        AnimationStateChangePacket packet = new AnimationStateChangePacket(worldPosition, animationState);
        ModNetwork.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), packet);
    }

    public int getAnimationState()
    {
        return animationState;
    }
}
