package com.pasterdream.pasterdreammod.compat.shaders;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Optional;

/**
 * 光影（Oculus / Iris）检测的客户端兼容层。
 * <p>
 * 不硬依赖光影模组，全部通过反射访问 {@code net.irisshaders.iris.*}。
 * 只有当 {@link #isShaderPackInUse()} 为 true 时，才表示光影管线正在渲染。
 */
@OnlyIn(Dist.CLIENT)
public final class ShaderCompat
{
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final String MODID_OCULUS = "oculus";
    private static final String MODID_IRIS = "iris";

    private static final String CLASS_IRIS_API = "net.irisshaders.iris.api.v0.IrisApi";
    private static final String CLASS_IRIS_API_CONFIG = "net.irisshaders.iris.api.v0.IrisApiConfig";
    private static final String CLASS_IRIS = "net.irisshaders.iris.Iris";
    private static final String CLASS_IRIS_CONFIG = "net.irisshaders.iris.config.IrisConfig";

    private static Boolean modLoaded;
    private static boolean resolved;

    private static Method mApiGetInstance;
    private static Method mApiIsShaderPackInUse;
    private static Method mApiGetConfig;
    private static Method mConfigAreShadersEnabled;
    private static Method mIrisGetIrisConfig;
    private static Method mIrisConfigGetShaderPackName;
    private static Method mIrisGetShaderpacksDirectory;

    private ShaderCompat() {}

    /** 客户端是否装有 Oculus 或 Iris。 */
    public static boolean isShaderModLoaded()
    {
        if (modLoaded == null)
        {
            ModList mods = ModList.get();
            modLoaded = mods != null && (mods.isLoaded(MODID_OCULUS) || mods.isLoaded(MODID_IRIS));
        }
        return modLoaded;
    }

    private static synchronized boolean resolve()
    {
        if (resolved) return mApiGetInstance != null;
        resolved = true;

        if (!isShaderModLoaded()) return false;

        try
        {
            Class<?> api = Class.forName(CLASS_IRIS_API);
            Class<?> apiConfig = Class.forName(CLASS_IRIS_API_CONFIG);
            Class<?> iris = Class.forName(CLASS_IRIS);
            Class<?> irisConfig = Class.forName(CLASS_IRIS_CONFIG);

            mApiGetInstance = api.getMethod("getInstance");
            mApiIsShaderPackInUse = api.getMethod("isShaderPackInUse");
            mApiGetConfig = api.getMethod("getConfig");
            mConfigAreShadersEnabled = apiConfig.getMethod("areShadersEnabled");
            mIrisGetIrisConfig = iris.getMethod("getIrisConfig");
            mIrisConfigGetShaderPackName = irisConfig.getMethod("getShaderPackName");
            mIrisGetShaderpacksDirectory = iris.getMethod("getShaderpacksDirectory");
            return true;
        }
        catch (Throwable t)
        {
            LOGGER.warn("[ShaderCompat] 无法解析 Iris/Oculus API，光影检测不可用", t);
            return false;
        }
    }

    /** 光影管线当前是否真的在渲染（推荐用这个做分支判断）。 */
    public static boolean isShaderPackInUse()
    {
        if (!resolve()) return false;
        try
        {
            Object api = mApiGetInstance.invoke(null);
            return Boolean.TRUE.equals(mApiIsShaderPackInUse.invoke(api));
        }
        catch (Throwable t)
        {
            return false;
        }
    }

    /** 设置里的光影开关是否打开（包可能加载失败，不能代表光影已生效）。 */
    public static boolean areShadersEnabled()
    {
        if (!resolve()) return false;
        try
        {
            Object api = mApiGetInstance.invoke(null);
            Object config = mApiGetConfig.invoke(api);
            return config != null && Boolean.TRUE.equals(mConfigAreShadersEnabled.invoke(config));
        }
        catch (Throwable t)
        {
            return false;
        }
    }

    /** 当前配置选中的光影包名（文件夹名或 {@code xxx.zip}）。 */
    @SuppressWarnings("unchecked")
    public static Optional<String> getConfiguredPackName()
    {
        if (!resolve()) return Optional.empty();
        try
        {
            Object config = mIrisGetIrisConfig.invoke(null);
            if (config == null) return Optional.empty();
            Object value = mIrisConfigGetShaderPackName.invoke(config);
            if (value instanceof Optional<?> opt && opt.isPresent() && opt.get() instanceof String name)
            {
                return Optional.of(name);
            }
        }
        catch (Throwable t)
        {
            // 静默失败：光影信息读取失败不应影响游戏
        }
        return Optional.empty();
    }

    /** 获取 {@code shaderpacks} 目录。 */
    public static Optional<Path> getShaderpacksDirectory()
    {
        if (!resolve()) return Optional.empty();
        try
        {
            Object value = mIrisGetShaderpacksDirectory.invoke(null);
            if (value instanceof Path path) return Optional.of(path);
        }
        catch (Throwable t)
        {
            // 静默失败
        }
        return Optional.empty();
    }
}
