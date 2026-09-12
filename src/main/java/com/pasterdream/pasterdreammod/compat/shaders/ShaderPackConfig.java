package com.pasterdream.pasterdreammod.compat.shaders;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Properties;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 读取当前生效光影包里的配置信息。
 * <p>
 * 只支持光影包 {@code shaders/} 目录下的文件，例如
 * {@code block.properties}（方块 ID / 渲染层映射）、{@code shaders.properties}（全局指令）。
 * 支持未压缩的文件夹包与 {@code .zip} 包两种形式。
 * <p>
 * 只有在 {@link ShaderCompat#isShaderPackInUse()} 为 true 时才有意义。
 */
@OnlyIn(Dist.CLIENT)
public final class ShaderPackConfig
{
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final String SHADERS_DIR = "shaders";
    private static final String BLOCK_PREFIX = "block.";

    private ShaderPackConfig() {}

    private record PackSource(Path path, boolean zip) {}

    /** 定位当前生效光影包。 */
    private static Optional<PackSource> currentPack()
    {
        if (!ShaderCompat.isShaderPackInUse()) return Optional.empty();

        Optional<String> name = ShaderCompat.getConfiguredPackName();
        Optional<Path> dir = ShaderCompat.getShaderpacksDirectory();
        if (name.isEmpty() || dir.isEmpty()) return Optional.empty();

        Path resolved = dir.get().resolve(name.get());
        if (Files.isDirectory(resolved))
        {
            return Optional.of(new PackSource(resolved, false));
        }
        if (Files.isRegularFile(resolved) && name.get().toLowerCase(Locale.ROOT).endsWith(".zip"))
        {
            return Optional.of(new PackSource(resolved, true));
        }
        return Optional.empty();
    }

    /** 读取当前光影包 {@code shaders/} 目录下的文本文件（如 {@code "block.properties"}）。 */
    public static Optional<String> read(String fileName)
    {
        return currentPack().flatMap(source -> readFromSource(source, fileName));
    }

    /** 解析当前光影包 {@code shaders/} 目录下的 .properties 文件（标准 java.util.Properties）。 */
    public static Optional<Properties> readProperties(String fileName)
    {
        return read(fileName).map(text ->
        {
            Properties props = new Properties();
            try (StringReader reader = new StringReader(text))
            {
                props.load(reader);
            }
            catch (IOException e)
            {
                LOGGER.warn("[ShaderPackConfig] 解析 {} 失败", fileName, e);
            }
            return props;
        });
    }

    /**
     * 解析 {@code block.properties} 为 {@code shader ID -> 方块 token 列表}。
     * <p>
     * 光影包的写法与标准 Properties 不同，这里自行解析以正确处理：
     * <ul>
     *     <li>反斜杠续行；</li>
     *     <li>重复的 {@code block.<id>} 键（多个条目会合并到同一 ID）；</li>
     *     <li>省略命名空间的原版方块名（{@code oak_leaves} 等价于 {@code minecraft:oak_leaves}）；</li>
     *     <li>带方块属性谓词的 token（如 {@code tall_grass:half=lower}）。</li>
     * </ul>
     */
    public static Map<Integer, List<String>> readBlockMap()
    {
        return read("block.properties").map(ShaderPackConfig::parseBlockMap).orElse(Collections.emptyMap());
    }

    private static Map<Integer, List<String>> parseBlockMap(String text)
    {
        Map<Integer, List<String>> map = new TreeMap<>();
        String joined = text.replaceAll("\\\\\\s*\\r?\\n\\s*", " ");

        for (String raw : joined.split("\\r?\\n|\\r"))
        {
            String line = raw.trim();
            if (line.isEmpty() || line.charAt(0) == '#' || line.charAt(0) == '!') continue;

            int eq = line.indexOf('=');
            if (eq < 0) continue;

            String key = line.substring(0, eq).trim();
            if (!key.startsWith(BLOCK_PREFIX)) continue;

            int id;
            try
            {
                id = Integer.parseInt(key.substring(BLOCK_PREFIX.length()).trim());
            }
            catch (NumberFormatException ex)
            {
                continue;
            }

            String value = line.substring(eq + 1).trim();
            if (value.isEmpty()) continue;

            List<String> tokens = map.computeIfAbsent(id, k -> new ArrayList<>());
            for (String token : value.split("\\s+"))
            {
                if (!token.isEmpty()) tokens.add(token);
            }
        }
        return map;
    }

    /** 解析后的 token：{@code namespace:name} 与可选属性谓词（如 {@code half=upper}，多个以 "," 分隔）。 */
    private record TokenParts(String name, String properties) {}

    private static TokenParts parseToken(String token)
    {
        int firstColon = token.indexOf(':');
        if (firstColon < 0) return new TokenParts("minecraft:" + token, null);

        String afterFirst = token.substring(firstColon + 1);
        int secondColon = afterFirst.indexOf(':');
        if (secondColon >= 0)
        {
            // namespace:name:prop=val
            String name = token.substring(0, firstColon + 1) + afterFirst.substring(0, secondColon);
            return new TokenParts(name, afterFirst.substring(secondColon + 1));
        }
        if (afterFirst.indexOf('=') >= 0 || afterFirst.indexOf('[') >= 0)
        {
            // name:prop=val
            return new TokenParts("minecraft:" + token.substring(0, firstColon), afterFirst);
        }
        // namespace:name
        return new TokenParts(token, null);
    }

    /**
     * 返回无属性谓词的归一化方块名；带谓词的 token（如 {@code grass:snowy=true}、{@code tall_grass:half=lower}）
     * 返回 {@code null}，避免误匹配到同名的方块（例如草方块 vs 矮草）。
     */
    private static String plainName(String token)
    {
        TokenParts parts = parseToken(token);
        return parts.properties() == null ? parts.name() : null;
    }

    private static String namespaceOf(String token)
    {
        String name = parseToken(token).name();
        int colon = name.indexOf(':');
        return colon < 0 ? "minecraft" : name.substring(0, colon);
    }

    /**
     * 查找带指定属性谓词的方块（如 {@code minecraft:tall_grass} 的 {@code half=upper}）。
     */
    public static OptionalInt findBlockIdWithProperties(Map<Integer, List<String>> blockMap,
                                                        String namespacedBlock,
                                                        String requiredProperty)
    {
        for (Map.Entry<Integer, List<String>> entry : blockMap.entrySet())
        {
            for (String token : entry.getValue())
            {
                TokenParts parts = parseToken(token);
                if (parts.properties() == null || !parts.name().equals(namespacedBlock)) continue;

                for (String property : parts.properties().split(","))
                {
                    if (property.trim().equals(requiredProperty))
                    {
                        return OptionalInt.of(entry.getKey());
                    }
                }
            }
        }
        return OptionalInt.empty();
    }

    /**
     * 在已解析的 block map 中查找指定方块（如 {@code minecraft:oak_leaves}）对应的 shader 方块 ID。
     */
    public static OptionalInt findBlockId(Map<Integer, List<String>> blockMap, String namespacedBlock)
    {
        for (Map.Entry<Integer, List<String>> entry : blockMap.entrySet())
        {
            for (String token : entry.getValue())
            {
                if (namespacedBlock.equals(plainName(token)))
                {
                    return OptionalInt.of(entry.getKey());
                }
            }
        }
        return OptionalInt.empty();
    }

    /** 在 {@code block.properties} 中查找指定方块名对应的 shader 方块 ID（便捷方法，会重新读取文件）。 */
    public static OptionalInt findBlockId(String namespacedBlock)
    {
        return findBlockId(readBlockMap(), namespacedBlock);
    }

    /**
     * 查找属于指定命名空间（如 {@code pasterdream}）的 shader 方块 ID。
     *
     * @return shader 方块 ID -> 方块名（按 ID 升序）
     */
    public static Map<Integer, String> findBlockIdsByNamespace(String namespace)
    {
        Map<Integer, String> result = new TreeMap<>();
        readBlockMap().forEach((id, tokens) ->
        {
            for (String token : tokens)
            {
                if (namespaceOf(token).equals(namespace))
                {
                    result.put(id, token);
                    break;
                }
            }
        });
        return result;
    }

    private static Optional<String> readFromSource(PackSource source, String fileName)
    {
        String relative = SHADERS_DIR + "/" + fileName;
        try
        {
            if (!source.zip())
            {
                Path file = source.path().resolve(relative);
                if (!Files.isRegularFile(file)) return Optional.empty();
                return Optional.of(Files.readString(file, StandardCharsets.ISO_8859_1));
            }

            try (ZipFile zip = new ZipFile(source.path().toFile()))
            {
                ZipEntry entry = findEntry(zip, SHADERS_DIR + "/" + fileName);
                if (entry == null) return Optional.empty();
                try (InputStream in = zip.getInputStream(entry))
                {
                    return Optional.of(new String(in.readAllBytes(), StandardCharsets.ISO_8859_1));
                }
            }
        }
        catch (IOException e)
        {
            LOGGER.warn("[ShaderPackConfig] 读取光影包文件 {} 失败", relative, e);
            return Optional.empty();
        }
    }

    /**
     * 在 zip 内查找 {@code shaders/<fileName>}。
     * 光影包 zip 可能把 {@code shaders/} 直接放在根目录（{@code shaders/block.properties}），
     * 也可能整体套一层文件夹（如 Photon 的 {@code photon-main/shaders/block.properties}），
     * 这里两种都支持。
     */
    private static ZipEntry findEntry(ZipFile zip, String path)
    {
        String suffix = "/" + path;
        ZipEntry match = null;
        var entries = zip.entries();
        while (entries.hasMoreElements())
        {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) continue;

            String name = entry.getName().replace('\\', '/');
            if (name.equals(path))
            {
                return entry;
            }
            if (match == null && name.endsWith(suffix))
            {
                match = entry;
            }
        }
        return match;
    }
}
