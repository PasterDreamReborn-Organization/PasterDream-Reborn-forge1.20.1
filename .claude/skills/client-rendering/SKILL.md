---
name: client-rendering
description: 客户端渲染（RenderType、HUD、粒子、音效、GeckoLib 动画模型）。覆盖渲染层注册、粒子行为类、音效注册、Geo 模型/动画文件。当用户要加粒子/音效/渲染层、做 GeckoLib 动画方块或实体、改 HUD 时使用。
---

# 客户端渲染

核心规范见 `document/rule/program/架构规范.md`（客户端分离 / 粒子系统 / GeckoLib 集成规范）。

---

## 渲染层

- cutout / translucent 渲染统一在 `client/ClientSetRenderLayer.java` 注册
- **不要**在 BlockStateProvider 手动设置 `renderType`

---

## 粒子

三层：类型注册、行为实现、纹理映射。

- `init/ModParticleTypes.java` 注册 `SimpleParticleType`
- `client/particle/` 下创建行为类（继承 `TextureSheetParticle`）+ 内部 Provider
- `client/particle/ModParticles.java` 注册 `RegisterParticleProvidersEvent`
- 纹理 JSON 手动维护 `assets/pasterdream/particles/`（**不走 datagen**）
- 群系引用：`BiomeSpecialEffects.Builder.ambientParticle(new AmbientParticleSettings(particleType, probability))`

---

## 音效

- `init/ModSounds.java` 注册 `SoundEvent`
- `assets/pasterdream/sounds.json` + `sounds/<name>.ogg` 手动维护
- 调用 `level.playSound()`，优先复用原版 `SoundEvents`

---

## GeckoLib 动画

- 动画实体/方块用 `GeoEntity` / `GeoBlockEntity` 接口
- 渲染器继承 `GeoEntityRenderer` / `GeoBlockEntityRenderer`
- 模型类继承 `GeoModel`
- `.geo.json` → `assets/pasterdream/geo/`；`.animation.json` → `assets/pasterdream/animations/`
- 显示设置 → `models/displaysettings/`；特殊模型 → `models/custom/`

---

## HUD

- `client/MeltDreamEnergyTank.java`、`client/SanTank.java`、`client/LoseMind.java`

---

## 关键约束

- 客户端代码放 `client/`，`@EventBusSubscriber(value = Dist.CLIENT)`，服务端不可用的类（`Minecraft`、渲染器）**绝不**出现在通用路径

---

## 文件速查

| 用途 | 路径 |
|------|------|
| 渲染层 | `client/ClientSetRenderLayer.java` |
| 粒子行为 | `client/particle/` |
| 粒子注册 | `init/ModParticleTypes.java` |
| 音效注册 | `init/ModSounds.java` |
| Geo 模型 | `assets/pasterdream/geo/` |
| 动画文件 | `assets/pasterdream/animations/` |
| 模型 | `client/model/` |
| 渲染器 | `client/renderer/` |
| GUI 渲染辅助 | `helper/renderhelper/` |
| GUI 组件 | `component/` |

---

## 引用文档

- `document/rule/program/架构规范.md` — 客户端分离、粒子系统、GeckoLib 集成
