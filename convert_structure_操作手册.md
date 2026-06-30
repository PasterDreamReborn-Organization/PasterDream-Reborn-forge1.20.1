# convert_structure 操作手册

## 简介

`convert_structure.exe` 用于将旧模组（帕斯特之梦）的 `.nbt` 结构文件中的方块 ID 按照 `mapping.json` 转换为新模组（帕斯特之梦: 重生）的方块 ID。原版 `minecraft:` 方块不受影响。

## 快速开始

```bash
convert_structure.exe -i 旧结构.nbt -o 新结构.nbt
```

exe 会自动在以下位置查找 `mapping.json`：
1. exe 同级目录下的 `mapping.json`
2. exe 同级目录下的 `document/design/mapping.json`
3. exe 上级目录下的 `document/design/mapping.json`（适配 exe 放在 `dist/` 的情况）
4. 当前工作目录下的 `mapping.json`
5. 当前工作目录下的 `document/design/mapping.json`

也可显式指定：

```bash
convert_structure.exe -i input.nbt -o output.nbt -m D:\path\to\mapping.json
```

## 参数说明

| 参数 | 简写 | 必填 | 说明 |
|------|------|------|------|
| `--input` | `-i` | 是 | 输入的旧版 `.nbt` 结构文件路径 |
| `--output` | `-o` | 是 | 输出的新版 `.nbt` 结构文件路径（目录不存在时自动创建） |
| `--mapping` | `-m` | 否 | `mapping.json` 路径，不填则自动探测 |
| `--help` | `-h` | 否 | 显示帮助信息 |

## 转换规则

### 1. 原版方块 — 忽略

以 `minecraft:` 开头的方块不做任何处理。

```
minecraft:stone       → 不变
minecraft:oak_planks  → 不变
minecraft:air         → 不变
```

### 2. 有映射 — 替换名称

在 `mapping.json` 的 `blocks` 节中找到映射关系的，替换为对应的新名称。方块的 `Properties`（如 facing、axis、waterlogged 等状态属性）完整保留。

```
pasterdream:dyedream_planks_stairs → pasterdream:dyedream_stairs
pasterdream:pinkagaric_0           → pasterdream:pink_mushroom_block
pasterdream:dyedream_grass         → pasterdream:dyedream_grass_block
```

### 3. 映射值为 `删除` — 替换为空气

在 mapping 中标记为"删除"的方块会被替换为 `minecraft:air`，原有 Properties 会被清除。

```json
"claypan_1": "删除",
"claypan_2": "删除"
```

```
pasterdream:claypan_1 → minecraft:air
pasterdream:claypan_2 → minecraft:air
```

### 4. 映射值为 `待搬运` — 保留原样

标记为"待搬运"的方块不做替换，但会输出警告信息，提醒该方块尚未完成搬运。

### 5. 无映射 — 保留原样

在 mapping 中完全找不到的方块不做替换，但会输出警告信息，提醒需要补充映射。

## 输出解读

### 正常转换

```
[CONVERTED] 14 unique block type mapping(s) (total 35 palette entries):
  dyedream_planks_stairs -> dyedream_stairs [8x]
  dyedream_planks_fence -> dyedream_fence [8x]
  pinkagaric_1 -> pink_mushroom_stem [3x]
  ...
[DONE] Saved to: output.nbt
```

- 方括号内的数字 `[8x]` 表示该映射在 palette 中命中了 8 次（通常是同一方块的不同朝向/状态变体）
- "unique block type" 是去重后的映射种类数，"total palette entries" 是实际替换的 palette 条目总数

### 有未映射的方块

```
[WARNING] 5 unmapped mod block(s) left unchanged:
  shadow_stone
  windmoor_log
  ...
```

这些方块在 mapping 中找不到，需要手动补充映射关系后重新转换。

### 有待搬运的方块

```
[WARNING] 2 block(s) marked as '待搬运' left unchanged:
  biome_shadow_0
  ...
```

这些方块在 mapping 中有条目但标记为"待搬运"，暂时保留原样。

### palette 自动合并

```
[INFO] Removed 3 duplicate palette entries after conversion.
```

两个不同的旧方块映射到了同一个新方块，且 Properties 相同，工具自动合并为一条 palette 条目并更新了所有 block 引用索引。

## 工作流程

### 搬运单个结构

1. 确认 `mapping.json` 在 exe 同级目录（或使用 `-m` 指定路径）
2. 运行转换命令
3. 检查输出中的 `[WARNING]`，确认是否有未映射的 mod 方块
4. 如有未映射方块，将新方块名称补充到 `mapping.json` 中
5. 重新运行转换，确认无警告
6. 将输出的 `.nbt` 文件放入新模组的 structures 目录

### 补充映射

编辑 `document/design/mapping.json`，在 `"blocks"` 节中添加：

```json
"旧方块短名": "新方块短名"
```

如需标记删除：

```json
"旧方块短名": "删除"
```

暂时跳过（保留原样并给警告）：

```json
"旧方块短名": "待搬运"
```

## 注意事项

- 不会修改原文件，输出到 `-o` 指定的新路径
- 原版方块（`minecraft:` 开头）完全不受影响
- 方块的 Properties（facing、axis、waterlogged 等）在名称替换时完整保留
- exe 可在任意目录运行，不依赖 Python 环境
- 输出目录不存在时会自动创建

## 重新编译

修改 `convert_structure.py` 源码后：

```bash
pip install pyinstaller nbtlib
pyinstaller --onefile --name convert_structure convert_structure.py
```

编译产物在 `dist/convert_structure.exe`。
