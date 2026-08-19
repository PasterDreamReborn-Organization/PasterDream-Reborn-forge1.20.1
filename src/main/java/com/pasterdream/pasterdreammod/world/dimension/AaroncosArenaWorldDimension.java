package com.pasterdream.pasterdreammod.world.dimension;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public final class AaroncosArenaWorldDimension {
    private AaroncosArenaWorldDimension() {}

    public static final ResourceKey<Level> AARONCOS_ARENA_WORLD = ResourceKey.create(
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("minecraft", "dimension")),
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena_world"));

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class DimensionSpecialEffectsHandler {
        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
            DimensionSpecialEffects customEffect = new DimensionSpecialEffects(
                    Float.NaN,                          // cloudLevel: 无云
                    true,                               // hasGround
                    DimensionSpecialEffects.SkyType.NONE, // 无天空
                    false,                              // forceBrightLightmap
                    false                               // constantAmbientLight
            ) {
                @Override
                public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                    return new Vec3(0.2, 0.2, 0.2);
                }

                @Override
                public boolean isFoggyAt(int x, int y) {
                    return true;
                }
            };
            event.register(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena_world"), customEffect);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getTo().equals(AARONCOS_ARENA_WORLD))
            return;
        if (!(event.getEntity() instanceof ServerPlayer sp))
            return;

        // 延迟到下一 tick 放置竞技场结构：跨维度传送期间区块尚未就绪，立即 placeInWorld 会静默失败
        PasterDreamMod.queueServerWork(1, () -> {
            ServerLevel arena = sp.server.getLevel(AARONCOS_ARENA_WORLD);
            if (arena == null)
                return;
            if (arena.players().size() < 2) {
                // 强制加载结构所在区块
                arena.getChunkSource().getChunk(-35 >> 4, -35 >> 4, true);
                arena.getChunkSource().getChunk(35 >> 4, 35 >> 4, true);
                StructureTemplate template = arena.getStructureManager().getOrCreate(
                        ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena"));
                BlockPos origin = new BlockPos(-35, 0, -35);
                template.placeInWorld(arena, origin, origin,
                        new StructurePlaceSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false),
                        arena.random, 3);
                Vec3 center = new Vec3(0, 50, 0);
                for (Entity e : arena.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(49.5),
                        e -> !(e instanceof Player))) {
                    e.discard();
                }
            }
            sp.teleportTo(arena, 0.5, 47, -0.5, sp.getYRot(), sp.getXRot());
        });
    }
}
