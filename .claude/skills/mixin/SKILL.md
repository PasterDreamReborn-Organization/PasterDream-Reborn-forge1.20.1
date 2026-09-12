---
name: mixin
description: 新增/修改 Mixin 注入。覆盖使用原则（优先事件/继承，Mixin 为最后手段）、禁止优先用 @Overwrite 覆写方法、注入注解选择、mixins.json 注册、客户端/服务端分离、minecraft-dev-mcp 校验。当用户要注入原版行为、写 Mixin、处理 @Overwrite/@Inject/@Redirect 时使用。
---

# Mixin 注入

包路径 `com.pasterdream.pasterdreammod.mixin`，配置 `src/main/resources/pasterdream.mixins.json`。规范见 `document/rule/program/架构规范.md`「Mixin 注入」段。

---

## 关键约束

- **Mixin 是最后手段**：能通过 Forge 事件、`Entity.getPersistentData()`、原版子类覆写、数据驱动（标签/配方/战利品表）实现的，绝不写 Mixin。
- **绝不优先使用 `@Overwrite` 覆写方法实现功能**。`@Overwrite` 会整体替换原方法体，破坏其他模组对同一方法 `@Inject` 的注入（本项目已因此踩坑）。**优先**用 `@Inject`（`HEAD`/`RETURN`/`TAIL`，必要时 `cancellable = true` 短路）、`@Redirect`、`@ModifyArg`、`@ModifyVariable`、`@ModifyConstant`、`@ModifyExpressionValue` 等注入方式。
- 仅在**注入方式确实无法实现**时才考虑 `@Overwrite`，并在类注释中写明原因与影响范围。
- 读取原版私有字段/方法用 `@Accessor` / `@Invoker`，不要为取值整段覆写。
- 客户端专属行为（渲染、HUD、音乐、光照）必须放 `"client"` 段；核心行为放 `"mixins"` 段。类文件命名遵循 `XxxMixin`。

---

## 实现步骤

1. 先用 minecraft-dev-mcp（`analyze_mixin` / `search_minecraft_code`）确认目标方法签名、调用链与可注入点。
2. 建 `XxxMixin` 类，选最小侵入的注入注解，`@Inject` 优先。
3. 客户端专属加 `@Mixin` 后**登记到 mixins.json 的对应段**（`mixins` / `client` / `server`）。
4. 需要跨模组兼容时，确认不与其他模组的注入点冲突（用 `@Inject` 而非 `@Overwrite` 即天然兼容）。
5. 运行 `runClient` 验证注入成功；报 `InvalidInjectionException` / `InvalidAccessorException` 时优先检查方法签名与 refmap。

---

## 文件速查

| 用途 | 路径 |
|------|------|
| Mixin 注入类 | `src/main/java/com/pasterdream/pasterdreammod/mixin/` |
| Mixin 配置 | `src/main/resources/pasterdream.mixins.json` |
| 规范文档 | `document/rule/program/架构规范.md` |
| 易犯错误 | `document/rule/program/程序规范文档.md`「官方映射下前置模组 Mixin 兼容问题」 |

---

## 正反例

- 正例：`BlockElementMixin` 用 `@Inject(at = HEAD, cancellable)` 短路，保留原方法体与其他模组注入；注释明确说明「避免 @Overwrite 冲突」。
- 正例：`ItemElytraMixin` 不使用 `@Overwrite`，避免与其它模组对 `IForgeItem` 默认方法的注入冲突。
- 反例：为改一个判断就整段 `@Overwrite` 原方法体，导致机械动力等模组的 `@Inject` 失效。
