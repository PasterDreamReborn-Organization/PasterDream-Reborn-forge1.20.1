# ID 映射表

> 源模组旧 ID → 重置模组新 ID 对照表  
> 每次搬运方块/物品时**必须**登记，无论是否修改 ID

---

## 使用说明

- 搬运一个方块/物品时，先在此表中查找是否已登记
- 未登记的，按实际搬运情况填写
- 变更说明须简洁明了：`不改` / `重命名` / `合并变体` / `拆分` 等

---

## 方块映射

| 旧 ID | 新 ID | 变更说明 | 客制化类处理 |
|-------|-------|---------|------------|
| `dyedream_dirt` | `dyedream_dirt` | 不改 | 无需客制化类，直接用 `Block` |
| `dyedream_grass` | `dyedream_grass_block` | 重命名，加 `_block` 后缀 | 客制化类 `DyedreamGrassBlock`，仅覆写 `canSustainPlant` |
| `dyedream_log` | `dyedream_log` | 不改 | 无需客制化类，直接用 `RotatedPillarBlock` |
| `dyedream_leaves` | `dyedream_leaves` | 不改 | 无需客制化类，直接用 `LeavesBlock` |
| `dyedream_sapling` | `dyedream_sapling` | 不改 | 无需客制化类，直接用 `SaplingBlock`，移除不必要的 BlockEntity |

---

## 物品映射

| 旧 ID | 新 ID | 变更说明 | 客制化类处理 |
|-------|-------|---------|------------|
| | | | |

---

## 实体映射

| 旧 ID | 新 ID | 变更说明 |
|-------|-------|---------|
| | | |

---

## 配方文件名映射

| 旧配方名 | 新配方名 | 产物 |
|----------|---------|------|
| `crafting_0.json` | | |
