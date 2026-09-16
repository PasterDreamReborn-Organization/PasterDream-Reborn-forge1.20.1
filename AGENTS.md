# AGENTS.md

> PasterDream: Reborn — Forge 1.20.1 模组项目（源模组 PasterDream 的重置版）。
> 本文件是 AI 助手的顶层入口，只放**索引 + 硬性约束 + 收尾检查**。领域细节一律去对应 skill 或规范文档，**不在本文件复制**。

---

## 一、文档与 Skill 的分工（单一事实来源）

| 层级 | 位置 | 定位 |
|------|------|------|
| 权威规范 | `document/rule/program/` | 架构、目录结构、程序规范、配方平衡、暗影难度。**冲突时以此为准** |
| 策划/设计 | `document/design/` | 机制设计、ID 映射表、命名规范、搬运方案书 |
| 参考 | `document/reference/` | 源项目结构分析、原模组资料 |
| Skill | `.claude/skills/<name>/SKILL.md` | AI 可执行的任务流程与领域速查，**只做索引/速查/引用**，不复制大段规范 |

**规矩**：规范变更先改 `document/rule/program/`，skill 只保留「引用文档」链接与必要速查表。禁止让 skill 成为规范的第二份副本。

---

## 二、Skill 索引

| Skill | 何时用 | 路径 |
|-------|--------|------|
| `design-grill` | 动手前拷问模糊想法、理清设计意图（写规范/登记 ID/搬运之前） | `.claude/skills/design-grill/SKILL.md` |
| `port-content` | 从源模组搬运方块/物品/工具/护甲/实体（跨系统总流程编排） | `.claude/skills/port-content/SKILL.md` |
| `datagen` | 跑 runData、加 Provider、改标签/模型/配方生成逻辑 | `.claude/skills/datagen/SKILL.md` |
| `entity` | 加实体、改实体 AI/渲染/掉落/自然生成 | `.claude/skills/entity/SKILL.md` |
| `worldgen` | 加群系/维度/结构/地物、跨维度传送 | `.claude/skills/worldgen/SKILL.md` |
| `client-rendering` | 加粒子/音效/渲染层、GeckoLib 动画模型、改 HUD | `.claude/skills/client-rendering/SKILL.md` |
| `recipe-container` | 加配方、做容器类工艺方块、NBT 保留配方、JEI 兼容 | `.claude/skills/recipe-container/SKILL.md` |
| `curio` | 加饰品、改饰品效果/槽位 | `.claude/skills/curio/SKILL.md` |
| `capability` | 加玩家数值能力、改 SAN/融梦能量、同步/HUD/命令 | `.claude/skills/capability/SKILL.md` |
| `skill-system` | 加主动技能、改技能冷却/消耗/范围 | `.claude/skills/skill-system/SKILL.md` |
| `shadow-difficulty` | 调整暗影生物强度、难度分级、低 SAN 刷怪 | `.claude/skills/shadow-difficulty/SKILL.md` |
| `advancement-notes` | 加进度、做笔记发放、改笔记读取触发 | `.claude/skills/advancement-notes/SKILL.md` |
| `mixin` | 注入原版行为、写 Mixin、处理 `@Overwrite/@Inject/@Redirect` | `.claude/skills/mixin/SKILL.md` |

---

## 三、常用命令

| 目的 | 命令 |
|------|------|
| 编译检查 | `.\gradlew compileJava` |
| 数据生成 | `.\gradlew runData` |
| 客户端测试 | `.\gradlew runClient` |
| 构建 | `.\gradlew build` |
| 刷新 ID 映射 JSON | `cd tools; python generate_id_mapping.py` |

> 数据生成产物在 `src/generated/`，**不可手动编辑**，必须改 datagen Provider 后重跑 `runData`。

---

## 四、硬性约束

- `NOT_MODIFY/`（含 `reference/`、`reference-resources/`、`old-recipes/`）**只读，严禁修改**，只读取信息。
- 文件编码 **UTF-8 无 BOM**，行尾 **CRLF**。
- 命名遵循 Java 规范：类 PascalCase、方法 camelCase、常量 UPPER_SNAKE、包全小写（详见 `document/rule/program/程序规范文档.md`）。
- 注册方块**必须**设 `.mapColor()` 并补全标签（挖掘工具/材质/功能）。
- 搬运旧内容必须先登记 `document/design/ID映射表.md`（完全新设计/废案不登记）。
- 提交信息格式 `[type](scope): message`（type 见 `程序规范文档.md`）。**未经用户明确要求不得提交**。
- 易踩的坑先查 `程序规范文档.md` 的「易犯错误记录」。

---

## 五、收尾检查（Definition of Done）

每完成一个任务，**按触发条件**更新对应目标。不满足任何触发条件时，不要为「更新文档」而更新文档。

| 触发条件 | 更新目标 |
|----------|----------|
| 新增/变更架构层（新包、新注册体系、新链路） | `document/rule/program/架构规范.md` + `目录结构组织.md` |
| 某个 skill 覆盖的机制被改动 | 对应的 `.claude/skills/*/SKILL.md` |
| 踩到新坑 / 易犯错误 | `程序规范文档.md` 的「易犯错误记录」 |
| 出现新的搬运类型或流程变化 | `document/design/策划文档.md` §六 + `port-content` skill |
| 新增/改动配方平衡相关数值 | `document/rule/program/容器配方平衡系统.md` |
| 新增/改动暗影难度相关逻辑 | `document/rule/program/暗影难度系统.md` |
| 新增/重命名 ID | `document/design/ID映射表.md`，并刷新 `mapping.json` |
| 新增**非搬运**内容（全新设计 / 原作废案） | `document/design/新内容列表.md` |
| 新增或重绘**纹理** | `src/main/resources/ASSETS_MANIFEST.md`（纯重命名/直接搬运的原模组纹理不登记） |

> 判断准则：**规范是权威、skill 是索引**。机制变了改规范，skill 只需保持「能指向正确规范 + 速查路径正确」。
