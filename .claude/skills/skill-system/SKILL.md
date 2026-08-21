---
name: skill-system
description: 新增/修改玩家技能（以瞬身术 Blink 为参考架构）。覆盖按键绑定→网络包→技能核心→特效处理→效果标记的完整链路。当用户要加主动技能、改技能冷却/消耗/范围时使用。
---

# 玩家技能系统

瞬身术（Blink）是首个实现的技能，作为所有新技能的参考架构。链路：

```
ModKeyMappings (按键) → BlinkPacket (网络) → BlinkSkill (逻辑) → EvasionEffectHandler (特效)
                                                                    ↓
                                            EvasionEffect + BlinkCooldownEffect (效果标记)
```

---

## 各层职责

| 层 | 类 | 职责 |
|----|-----|------|
| 按键绑定 | `init/ModKeyMappings.java` | 注册按键，客户端检测按下 |
| 网络传输 | `network/skill/` | 客户端→服务端，携带必要参数 |
| 技能核心 | `world/skill/` | 条件检查（冷却/消耗/维度/骑乘）、位移/伤害计算、冷却设置、buff 施加 |
| 特效处理 | `world/skill/` | 粒子爆发、音效、冲刺、尾迹 |
| 效果标记 | `world/effect/` | 无敌帧、冷却计时等状态效果 |

---

## 关键约束

- 自定义属性（cd / consume / range 等）注册在 `init/ModAttributes.java`，通过 `ModAttributes.XXX` 读取，可被饰品/效果动态修正
- 冷却与战技锁定复用 `helper/cooldown/`（`SkillCooldownHelper`、`SkillLockHelper`）
- 网络包注册在 `init/ModNetwork.java`
- 技能逻辑放 `world/skill/`，纯效果定义放 `world/effect/`（业务逻辑不要塞进 MobEffect 类）

---

## 新技能实现步骤

1. `init/ModKeyMappings.java` 注册按键
2. `init/ModAttributes.java` 定义技能参数属性
3. `network/skill/` 新建数据包，在 `ModNetwork` 注册
4. `world/skill/` 写技能核心逻辑（条件检查 + 执行）
5. `world/effect/` 定义效果标记，注册到 `init/ModEffects.java`
6. 特效（粒子/音效）复用原版 `SoundEvents` 或 `ModParticleTypes`

---

## 文件速查

| 用途 | 路径 |
|------|------|
| 技能核心 | `world/skill/` |
| 技能网络包 | `network/skill/` |
| 按键绑定 | `init/ModKeyMappings.java` |
| 自定义属性 | `init/ModAttributes.java` |
| 效果实现 | `world/effect/` |
| 效果注册 | `init/ModEffects.java` |
| 冷却/锁定辅助 | `helper/cooldown/` |
| 网络注册 | `init/ModNetwork.java` |

---

## 引用文档

- `document/rule/program/架构规范.md` — 技能系统架构段
