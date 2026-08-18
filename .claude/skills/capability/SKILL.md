---
name: capability
description: 新增/修改能力系统（融梦能量 MeltDreamEnergy、SAN 值）。覆盖 Capability 接口+实现+Provider 模式、网络同步、HUD 渲染、命令。当用户要加玩家数值能力、改 SAN/能量逻辑、做同步或 HUD 时使用。
---

# 能力系统（Capability）

覆盖融梦能量（`capability/meltdreamenergy/`）与 SAN 值（`capability/san/`）两套能力，均采用 Forge Capability「接口 + 实现 + Provider」模式。

---

## 关键约束

- 能力类型声明在 `capability/ModCapabilities.java`，事件注册在 `capability/EventRegister.java`
- 每个能力子包含：接口、实现、Provider、Helper（如 `SanHelper`）
- 网络同步走 `network/meltdreamenergy/` 与 `network/san/`（每包一功能，含 Sync/Max/Enable 等）
- HUD 渲染在 `client/`（`MeltDreamEnergyTank.java`、`SanTank.java`、`LoseMind.java`）
- 命令在 `command/`（`MeltDreamEnergy`、`San` 子命令）
- 仅一个 int 且无需同步/生命周期的状态，优先用 `Entity.getPersistentData()` 而非 Capability（见暗影难度系统的选择）

---

## 实现步骤

1. 定义接口 + 默认实现 + `ICapabilityProvider`
2. 在 `ModCapabilities.java` 声明 `Capability`，在 `EventRegister.java` 注册 attach 事件
3. Helper 封装读写（服务端读、客户端用同步值）
4. 网络包同步变更（`SimpleChannel` 注册在 `init/ModNetwork.java`）
5. HUD 在 `client/` 渲染，必要时加配置开关
6. 命令暴露调试入口

---

## 文件速查

| 用途 | 路径 |
|------|------|
| 能力类型声明 | `capability/ModCapabilities.java` |
| 能力事件注册 | `capability/EventRegister.java` |
| 融梦能量 | `capability/meltdreamenergy/` |
| SAN 值 | `capability/san/` |
| 融梦能量网络 | `network/meltdreamenergy/` |
| SAN 网络 | `network/san/` |
| 融梦能量 HUD | `client/MeltDreamEnergyTank.java` |
| SAN HUD | `client/SanTank.java` |
| 低 SAN 失智视觉 | `client/LoseMind.java` |
| 命令 | `command/MeltDreamEnergy.java`、`command/san/San.java` |
| SAN 群系倍率 | `helper/sanbiomeratemanager/` |

---

## 引用文档

- `document/rule/program/架构规范.md` — capability 模块职责、客户端分离
- `.claude/skills/shadow-difficulty/SKILL.md` — 依赖 SAN 的暗影难度系统
