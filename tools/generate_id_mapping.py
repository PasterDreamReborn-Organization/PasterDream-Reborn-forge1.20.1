"""
从 ID映射表.md 生成 mapping.json，供后续 NBT 转换工具使用。

用法：
    python tools/generate_id_mapping.py

输出：mapping.json
    {
      "blocks": { "旧ID": "新ID", ... },
      "items":  { "旧ID": "新ID", ... },
      "entities": { ... },
      "dimensions": { ... },
      "biomes": { ... },
      "effects": { ... }
    }
"""

import json
import re
from pathlib import Path

SCRIPT_DIR = Path(__file__).resolve().parent          # tools/
PROJECT_ROOT = SCRIPT_DIR.parent                      # 项目根目录
MD_PATH = PROJECT_ROOT / "document" / "design" / "ID映射表.md"
OUT_PATH = PROJECT_ROOT / "document" / "design" / "mapping.json"

SECTION_HEADERS = {
    "方块映射": "blocks",
    "物品映射": "items",
    "实体映射": "entities",
    "维度映射": "dimensions",
    "群系映射": "biomes",
    "状态效果映射": "effects",
    "地物映射": "features",
}


HEADER_TEXTS = {"旧 id", "旧 ID", "旧配方名", "新 id", "新 ID", "变更说明", "客制化类处理", "备注"}


def is_header_row(first_cell: str) -> bool:
    """检查第一个单元格是否为表头。"""
    clean = first_cell.strip("` ").strip()
    return clean in HEADER_TEXTS or not clean


def parse_table(lines: list[str], col_old: int, col_new: int) -> dict[str, str]:
    """从 markdown 表格行中提取 旧ID -> 新ID 的映射。col 为 0-based 列索引。"""
    mapping = {}
    for line in lines:
        # 跳过标题分隔行（如 |---|---|）
        if re.match(r"^\s*\|[\s\-:|]+\|", line):
            continue
        cells = [c.strip() for c in line.split("|")]
        cells = cells[1:-1] if len(cells) > 2 and cells[-1] == "" else cells[1:]
        if len(cells) <= max(col_old, col_new):
            continue
        old_id = cells[col_old].strip("` ").strip()
        new_id = cells[col_new].strip("` ").strip()
        if is_header_row(old_id):
            continue
        if old_id and new_id:
            mapping[old_id] = new_id
    return mapping


def main():
    content = MD_PATH.read_text(encoding="utf-8")
    lines = content.split("\n")

    result: dict[str, dict[str, str]] = {
        "blocks": {},
        "items": {},
        "entities": {},
        "dimensions": {},
        "biomes": {},
        "effects": {},
        "features": {},
    }

    current_section: str | None = None
    section_lines: list[str] = []

    for line in lines:
        # 检测 ## 标题
        m = re.match(r"^##\s+(.*)", line)
        if m:
            # 处理上一节
            if current_section and section_lines:
                key = SECTION_HEADERS.get(current_section)
                if key:
                    result[key].update(parse_table(section_lines, 0, 1))
            current_section = m.group(1).strip()
            section_lines = []
        elif current_section:
            section_lines.append(line)

    # 最后一节
    if current_section and section_lines:
        key = SECTION_HEADERS.get(current_section)
        if key:
            result[key].update(parse_table(section_lines, 0, 1))

    # 保留现有 mapping.json 中的手动维护字段（如 block_properties）
    preserved_sections = {"block_properties"}
    if OUT_PATH.exists():
        existing = json.loads(OUT_PATH.read_text(encoding="utf-8"))
        for section in preserved_sections:
            if section in existing:
                result[section] = existing[section]

    # 统计
    for k, v in result.items():
        if v:
            print(f"  {k}: {len(v)} entries")

    OUT_PATH.write_text(
        json.dumps(result, ensure_ascii=False, indent=2), encoding="utf-8"
    )
    print(f"\nWritten: {OUT_PATH}")


if __name__ == "__main__":
    main()
