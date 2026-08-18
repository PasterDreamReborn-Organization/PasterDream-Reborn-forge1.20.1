---
name: shadow-difficulty
description: 修改暗影难度系统（暗影生物数值缩放、特殊行为门控、低理智刷怪、击杀回理智）。覆盖难度查询链、每玩家覆盖、配置项。当用户要调整暗影生物强度、难度分级、低 SAN 刷怪或相关 gamerule 时使用。
---

# 暗影难度系统

控制暗影生物的数值强度、特殊行为和低理智生成（灵感来自《饥荒》理智-暗影机制）。完整设计见 `document/rule/program/暗影难度系统.md`。

---

## 难度查询链

玩家有效难度优先级（高→低）：

1. **个人覆盖**：`Entity.getPersistentData()` 的 `"pasterdream.shadowDifficulty"`（int 0-3）
2. **默认玩家难度**：`playerShadowDifficulty` gamerule
3. **世界难度**：`shadowDifficulty` gamerule（仅实体无玩家上下文时回退）

实体上下文（无明确玩家）按：`ShadowSourcePlayer` UUID tag → `getTarget()` → 最近玩家（64 格）→ 世界 gamerule。

难度等级：0 极简单 / 1 简单 / 2 普通 / 3 困难。

---

## 三个作用层级

| 层级 | 触发点 | 说明 |
|------|--------|------|
| 实体属性缩放 | `event/ModMobSpawnEvents.java` | `#pasterdream:shadow_mob` 实体 `EntityJoinLevel` 时按倍率改 HP/攻击/移速，仅对新生成实体 |
| 特殊行为控制 | 各实体类 | AoE/SAN 抽取/召唤/吼叫/极低理智扣血，按 `getDifficultyContext()` 门控 |
| 低理智刷怪 | `event/LowSanSpawnHandler.java` | 独立于状态效果，按 SAN 比率 + 玩家难度刷怪，写入 `ShadowSourcePlayer` tag |

---

## 关键约束

- 难度查询统一走 `helper/ShadowDifficultyHelper.java`，**不要**在实体里手写查询逻辑
- 玩家个人覆盖用 `persistentData`（仅一个 int、仅服务端、无需同步），**不用** Capability
- 配置项全部在 `Config.java`，倍率/阈值用 4 值数组对应 4 个难度
- 击杀回理智在 `TerrorbeakEntity.die()`，击杀者为玩家且 SAN≤20 时触发
- 低 SAN 视觉开关（overlay/jitter/sound）经 `LowSanWorldData.java`（SavedData）持久化

---

## 文件速查

| 用途 | 路径 |
|------|------|
| 难度查询核心 | `helper/ShadowDifficultyHelper.java` |
| 配置项 | `Config.java` |
| gamerule 注册 | `init/ModGameRules.java` |
| 属性缩放 | `event/ModMobSpawnEvents.java` |
| 战利品加成 | `event/ModMobDrops.java` |
| 低理智刷怪 | `event/LowSanSpawnHandler.java` |
| 难度命令 | `command/san/ShadowDifficulty.java` |
| 低 SAN 视觉命令 | `command/san/LowSanEffect.java` |
| 视觉持久化 | `command/san/LowSanWorldData.java` |
| 视觉同步 | `network/san/LowSanConfigSyncPacket.java` |
| 暗影生物标签 | `tag/ModEntityTypeTags.java`（`SHADOW_MOB`） |

---

## 引用文档

- `document/rule/program/暗影难度系统.md` — 完整设计、倍率表、区间边界、刷怪配置
- `.claude/skills/capability/SKILL.md` — SAN 能力系统（难度刷怪依赖 SAN 比率）
