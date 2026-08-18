---
name: advancement-notes
description: 新增/修改进度与梦笔记分发。覆盖进度定义、四种笔记分发模式（获得物品/仅 XP/读笔记/饮用）、自定义触发器、睡眠笔记门禁、语言条目。当用户要加进度、做笔记发放、改笔记读取触发时使用。
---

# 进度与梦笔记系统

核心规范见 `.claude/skills/port-content/SKILL.md`「步骤 15：进度搬运」。所有进度统一在 `datagen/common/ModAdvancementProvider.java` 定义。

---

## 四种笔记分发模式

| 模式 | 触发 | 适用 |
|------|------|------|
| A：获得物品→进度→发笔记 | `AdvancementEvent` 驱动，`ADVANCEMENT_NOTE_CONTENT` 映射 | 原作 `inventory_changed` + loot reward 发笔记 |
| B：获得物品→进度（仅 XP） | 只在 Provider 定义进度 + 语言 | 原作可见进度无 loot |
| C：交互→发笔记→读笔记→进度 | `ReadDreamNoteTrigger.forContent()` 驱动 | 原作 `impossible` 触发，实际读笔记获得 |
| D：饮用/食用→进度 | `consume_item` 触发 | 自定义 `finishUsingItem` 需手动 `CriteriaTriggers.CONSUME_ITEM.trigger` + `awardStat` |

---

## 关键约束

- 模式 A：在 `event/PlayerEvents.java` 的 `ADVANCEMENT_NOTE_CONTENT` 映射注册 `进度ID → contentKey`，`onAdvancementEarned` 自动查表发笔记 + 消息
- 消息 key 动态拼接：`message.pasterdream.<path用.替换/>.found_note`
- 模式 C：方块/物品交互代码中发笔记（检查已有进度 + 背包已有笔记）
- 睡眠笔记（`dyedream_crack`）有门禁：玩家须先身体接触染梦裂隙方块（`entityInside()` → `first_contact_dyedream_crack`），首次睡觉不可直接触发
- Frame 对照：task→`TASK`、goal→`GOAL`、challenge→`CHALLENGE`
- 自定义触发器放 `advancement/critereon/`，注册在 `init/ModCriteriaTriggers.java`

---

## 文件速查

| 用途 | 路径 |
|------|------|
| 进度定义 | `datagen/common/ModAdvancementProvider.java` |
| 笔记分发表 | `event/PlayerEvents.java` → `ADVANCEMENT_NOTE_CONTENT` |
| 笔记分发事件 | `event/PlayerEvents.java` → `onAdvancementEarned()` |
| 自定义触发器 | `advancement/critereon/` |
| 触发器注册 | `init/ModCriteriaTriggers.java` |
| 语言文件 | `datagen/lang/ModZhCnLangProvider.java` / `ModEnUsLangProvider.java` |
| 梦笔记物品 | `world/item/dreamnotes/` |

---

## 引用文档

- `.claude/skills/port-content/SKILL.md` — 步骤 15 进度搬运完整说明
