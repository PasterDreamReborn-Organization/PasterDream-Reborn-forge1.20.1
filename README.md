# 帕斯特之梦: 重生 / PasterDream: Reborn

重写原版帕斯特之梦模组，基于 Minecraft Forge 1.20.1，从空 MDK 从零搭建。

A community rewrite of the original PasterDream mod, rebuilt from scratch on Minecraft Forge 1.20.1.

## 授权 / Authorization

本项目已获得原作者"异星之尘"的授权发布，沿用原模组 ID `pasterdream`。美术资源（纹理、模型、音效等）来自原模组，采用 ARR 协议，归原作版权方所有。

This project is released with permission from the original author "Aerolite_Dust." The mod ID `pasterdream` is retained. Art assets (textures, models, sounds, etc.) are from the original mod, licensed under All Rights Reserved by the original copyright holder.

## 协议 / License

- **代码 / Code**: [MIT](LICENSE.md) — 可自由使用、修改、分发
- **美术资源 / Art Assets**: [ARR](src/main/resources/LICENSE_ASSETS.md) — 保留所有权利，未经原作者书面许可不得使用

## 开发环境 / Setup

```bash
./gradlew genIntellijRuns   # IntelliJ IDEA
./gradlew genEclipseRuns    # Eclipse
./gradlew --refresh-dependencies  # 刷新依赖
```

## 依赖 / Dependencies

| 前置 | 版本要求 |
|------|----------|
| Minecraft Forge | 47+ |
| Curios | 5.14.1+ |
| GeckoLib | 4.8.3+ |
| JEI (可选) | 15.20.0.112+ |

## 文档 / Documentation

项目文档见 [`document/`](document/) 目录：

- [`document/design/`](document/design/) — 策划文档、ID 映射表、命名规范
- [`document/rule/`](document/rule/) — 代码架构规范
- [`document/reference/`](document/reference/) — 源模组参考文件

## 致谢 / Credits

- 原作: 异星之尘 (Aerolite_Dust) — 感谢授权和支持
- 重写: ShiLiuYinYu, 2884omgpy, Garam
