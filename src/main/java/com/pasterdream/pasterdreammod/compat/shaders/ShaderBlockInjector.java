package com.pasterdream.pasterdreammod.compat.shaders;

import com.mojang.logging.LogUtils;
import com.pasterdream.pasterdreammod.config.PasterDreamClientConfig;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.GrowingPlantBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

/**
 * 不修改光影包的前提下，让光影渲染器把本模组的植物/树叶当作「可动」方块。
 * <p>
 * 原理：{@code WorldRenderingSettings.INSTANCE.getBlockStateIds()} 是一个由光影包
 * {@code block.properties} 生成的可变 {@code Object2IntMap<BlockState>}，Iris 在构建地形网格时
 * 用它把方块 shader ID 写入顶点属性 {@code mc_Entity.x}（原版路径与 Sodium 路径都用同一张表）。
 * <p>
 * 我们把本模组植物/树叶的 BlockState 指向包内某个「肯定会被飘动」的原版方块（草 / 树叶）的
 * shader ID，这样 shader 的 {@code if (mc_Entity.x == 该ID)} 就会命中它们，动效与该原版方块一致。
 * <p>
 * 这是直接操作 Oculus/Iris 的内部状态，非官方 API，版本升级可能失效。
 */
@OnlyIn(Dist.CLIENT)
public final class ShaderBlockInjector
{
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final String CLASS_WORLD_RENDERING_SETTINGS =
            "net.irisshaders.iris.shaderpack.materialmap.WorldRenderingSettings";

    /** 树叶参考方块：抄它的 shader ID，让我们的树叶获得同样的飘动/摆动。 */
    private static final String[] LEAVES_REFERENCES = {
            "minecraft:oak_leaves",
            "minecraft:jungle_leaves",
            "minecraft:birch_leaves",
            "minecraft:spruce_leaves",
            "minecraft:acacia_leaves",
            "minecraft:dark_oak_leaves",
            "minecraft:azalea_leaves",
            "minecraft:flowering_azalea_leaves"
    };

    /** 十字植物参考方块：抄它的 shader ID。 */
    private static final String[] PLANT_REFERENCES = {
            "minecraft:grass",
            "minecraft:tall_grass",
            "minecraft:fern",
            "minecraft:large_fern",
            "minecraft:dandelion",
            "minecraft:poppy"
    };

    private static boolean resolved;
    private static Field fInstance;
    private static Method mGetBlockStateIds;

    /** 上一次注入过的 map 实例；换光影包 / 重载后 Iris 会换新 map，从而触发重新注入。 */
    private static Object lastMap;

    /** 连续失败次数；超过上限后停用，避免刷屏。 */
    private static int failures;
    private static boolean disabled;

    private ShaderBlockInjector() {}

    /** 幂等：每张 map 只注入一次。可在客户端 tick 中安全地反复调用。 */
    public static void tryInject()
    {
        if (disabled) return;
        if (!PasterDreamClientConfig.shaderBlockInjection) return;
        if (!ShaderCompat.isShaderPackInUse()) return;
        if (!resolve()) return;

        Object map = null;
        try
        {
            Object instance = fInstance.get(null);
            if (instance == null) return;

            map = mGetBlockStateIds.invoke(instance);
            if (map == null || map == lastMap) return;

            Map<Integer, List<String>> blockMap = ShaderPackConfig.readBlockMap();
            if (blockMap.isEmpty())
            {
                lastMap = map;
                LOGGER.info("[ShaderCompat] 当前光影包没有 block.properties，跳过方块动效注入");
                return;
            }

            OptionalInt leavesId = firstId(blockMap, LEAVES_REFERENCES);
            OptionalInt plantId = firstId(blockMap, PLANT_REFERENCES);

            if (leavesId.isEmpty() && plantId.isEmpty())
            {
                lastMap = map;
                LOGGER.info("[ShaderCompat] 当前光影包未给任何原版草/树叶分配 shader ID，跳过方块动效注入");
                return;
            }

            Method put = map.getClass().getMethod("put", Object.class, int.class);

            int states = 0;
            int blocks = 0;
            for (RegistryObject<Block> ro : ModBlocks.BLOCKS.getEntries())
            {
                Block block = ro.get();
                int id = referenceIdFor(block, leavesId, plantId);
                if (id <= 0) continue;

                blocks++;
                for (BlockState state : block.getStateDefinition().getPossibleStates())
                {
                    put.invoke(map, state, id);
                    states++;
                }
            }

            lastMap = map;
            failures = 0;
            LOGGER.info("[ShaderCompat] 已注入 {} 个 pasterdream 方块（{} 个方块状态）以启用光影动效（树叶ID={}, 植物ID={}）",
                    blocks, states, leavesId.orElse(-1), plantId.orElse(-1));
        }
        catch (Throwable t)
        {
            lastMap = null;
            if (++failures >= 3)
            {
                disabled = true;
                LOGGER.warn("[ShaderCompat] 注入光影方块动效连续失败，已停用该功能（不影响游戏）", t);
            }
            else
            {
                LOGGER.warn("[ShaderCompat] 注入光影方块动效失败，将重试（{}）", failures, t);
            }
        }
    }

    private static OptionalInt firstId(Map<Integer, List<String>> blockMap, String[] candidates)
    {
        for (String candidate : candidates)
        {
            OptionalInt id = ShaderPackConfig.findBlockId(blockMap, candidate);
            if (id.isPresent() && id.getAsInt() > 0) return id;
        }
        return OptionalInt.empty();
    }

    private static int referenceIdFor(Block block, OptionalInt leavesId, OptionalInt plantId)
    {
        if (block instanceof LeavesBlock) return leavesId.orElse(0);
        if (isWavingPlant(block)) return plantId.orElse(0);
        return 0;
    }

    private static boolean isWavingPlant(Block block)
    {
        return block instanceof BushBlock
                || block instanceof VineBlock
                || block instanceof SugarCaneBlock
                || block instanceof GrowingPlantBlock
                || block == ModBlocks.FIG_VINE.get()
                || block == ModBlocks.DYEDREAM_SEAGRASS.get();
    }

    private static boolean resolve()
    {
        if (resolved) return fInstance != null;
        resolved = true;

        try
        {
            Class<?> wrs = Class.forName(CLASS_WORLD_RENDERING_SETTINGS);
            fInstance = wrs.getField("INSTANCE");
            mGetBlockStateIds = wrs.getMethod("getBlockStateIds");
            return true;
        }
        catch (Throwable t)
        {
            LOGGER.warn("[ShaderCompat] 无法定位 WorldRenderingSettings，方块动效注入不可用", t);
            return false;
        }
    }
}
