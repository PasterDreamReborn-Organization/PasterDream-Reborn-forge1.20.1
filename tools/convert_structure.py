#!/usr/bin/env python3
"""
NBT 结构方块转换工具 — PasterDream-Reborn

交互式运行：启动时列出同目录下的 .nbt 文件，用户选择后按 mapping.json 进行转换。
mapping.json 必须与程序同目录，否则拒绝启动。

用法：
    python convert_structure.py                    # 交互模式
    python convert_structure.py -i input.nbt       # 命令行模式
    python convert_structure.py -h                 # 查看帮助
"""

import argparse
import json
import sys
from pathlib import Path
from collections import OrderedDict, Counter

import nbtlib
from nbtlib.tag import Compound, List, String, Int

# --- 映射表中的特殊标记 ---
DELETE_MARKER = "删除"
PENDING_MARKER = "待搬运"


def get_program_dir():
    """获取程序所在目录（兼容 PyInstaller 打包和直接运行 Python 脚本）。"""
    if getattr(sys, "frozen", False):
        return Path(sys.executable).resolve().parent
    else:
        return Path(__file__).resolve().parent


def find_mapping(program_dir):
    """查找 mapping.json。必须与程序同目录，否则报错退出。"""
    candidates = [
        program_dir / "mapping.json",
        program_dir / "document" / "design" / "mapping.json",
        program_dir.parent / "document" / "design" / "mapping.json",   # 从 tools/ 运行时
    ]
    for mapping_path in candidates:
        if mapping_path.exists():
            return mapping_path

    print("=" * 55)
    print("  错误：缺少映射文件！")
    print()
    print("  mapping.json 必须与 convert_structure.exe 放在同一目录下：")
    print(f"    {program_dir}")
    print()
    print("  请运行 document/design/generate_id_mapping.py 生成映射文件，")
    print("  然后将 mapping.json 复制到上述目录后重试。")
    print("=" * 55)
    sys.exit(1)


def load_block_mapping(path):
    """加载 mapping.json 并返回 (blocks 映射, 属性覆盖)。"""
    with open(path, "r", encoding="utf-8") as f:
        data = json.load(f)
    return data.get("blocks", {}), data.get("block_properties", {})


def scan_nbt_files(directory):
    """扫描目录下的所有 .nbt 文件，返回排序后的 Path 列表。"""
    return sorted(directory.glob("*.nbt"))


def palette_key(entry):
    """生成 palette 条目的哈希键（名称 + 排序后的属性）。"""
    name = str(entry["Name"])
    props = None
    if "Properties" in entry:
        props = tuple(sorted((k, str(v)) for k, v in entry["Properties"].items()))
    return (name, props)


def convert_one(input_path, mapping, block_properties, output_path, delete_unmapped=False):
    """
    转换单个 .nbt 结构文件。

    参数:
        mapping:           旧名 → 新名 的映射字典
        block_properties:  旧名 → {属性: 值} 的属性覆盖字典
        delete_unmapped:   True 则把未映射的模组方块也替换为空气。
    返回:
        (changes, unchanged, pending, deleted_unmapped)
    """
    nbt = nbtlib.load(str(input_path))
    palette = nbt["palette"]
    blocks = nbt["blocks"]

    changes = Counter()              # (旧名, 新名) → 次数
    unchanged_unmapped = set()       # 未在 mapping 中找到的方块短名
    pending_blocks = set()           # 标记为"待搬运"的方块短名
    deleted_unmapped_count = 0       # 被强制替换为空气的未映射方块 palette 条目数

    # --- 阶段 1：重映射 palette 名称 ---
    for entry in palette:
        full_name = str(entry["Name"])

        # 原版方块：跳过
        if full_name.startswith("minecraft:"):
            continue

        # 拆出命名空间和短名
        if ":" in full_name:
            namespace, short_name = full_name.split(":", 1)
        else:
            namespace, short_name = "pasterdream", full_name

        # 在 mapping 中查找
        if short_name not in mapping:
            if delete_unmapped:
                entry["Name"] = String("minecraft:air")
                if "Properties" in entry:
                    del entry["Properties"]
                deleted_unmapped_count += 1
                changes[(short_name, "minecraft:air")] += 1
            else:
                unchanged_unmapped.add(short_name)
            continue

        new_short = mapping[short_name]

        if new_short == DELETE_MARKER:
            entry["Name"] = String("minecraft:air")
            if "Properties" in entry:
                del entry["Properties"]
            changes[(short_name, "minecraft:air")] += 1
        elif new_short == PENDING_MARKER:
            if delete_unmapped:
                entry["Name"] = String("minecraft:air")
                if "Properties" in entry:
                    del entry["Properties"]
                changes[(short_name, "minecraft:air")] += 1
            else:
                pending_blocks.add(short_name)
        else:
            if new_short != short_name:
                entry["Name"] = String(f"{namespace}:{new_short}")
                changes[(short_name, new_short)] += 1

        # 应用 block_properties 中的属性覆盖
        if short_name in block_properties:
            props_tag = entry.get("Properties")
            if props_tag is None:
                props_tag = Compound()
                entry["Properties"] = props_tag
            for prop_key, prop_val in block_properties[short_name].items():
                props_tag[prop_key] = String(prop_val)

    # --- 阶段 2：合并重复的 palette 条目 ---
    seen = OrderedDict()
    original_entries = {}
    index_map = {}

    for i, entry in enumerate(palette):
        key = palette_key(entry)
        if key in seen:
            index_map[i] = seen[key]
        else:
            new_idx = len(seen)
            seen[key] = new_idx
            original_entries[new_idx] = entry
            index_map[i] = new_idx

    new_palette = List[Compound]()
    for idx in range(len(original_entries)):
        new_palette.append(original_entries[idx])
    nbt["palette"] = new_palette

    # --- 阶段 3：更新方块引用索引 ---
    for block_entry in blocks:
        old_idx = int(block_entry["state"])
        block_entry["state"] = Int(index_map.get(old_idx, old_idx))

    # --- 阶段 4：保存 ---
    output_path.parent.mkdir(parents=True, exist_ok=True)
    nbt.save(str(output_path))

    # --- 输出报告 ---
    if changes:
        print(f"  已转换 {len(changes)} 种方块类型（共 {sum(changes.values())} 个 palette 条目）：")
        for (old, new), count in changes.most_common():
            if new == "minecraft:air":
                print(f"    {old} -> {new}（已删除）[{count}次]")
            else:
                print(f"    {old} -> {new} [{count}次]")
    else:
        print("  该文件中没有需要转换的模组方块。")

    if unchanged_unmapped:
        print(f"  [!] 提示：{len(unchanged_unmapped)} 个模组方块未在 mapping 中找到，保持原样：")
        for b in sorted(unchanged_unmapped):
            print(f"    - {b}")

    if pending_blocks:
        print(f"  [!] 提示：{len(pending_blocks)} 个方块在 mapping 中标记为「待搬运」，保持原样：")
        for b in sorted(pending_blocks):
            print(f"    - {b}")

    removed = len(palette) - len(new_palette)
    if removed > 0:
        print(f"  已合并 {removed} 个重复的 palette 条目。")

    return changes, unchanged_unmapped, pending_blocks


def run_interactive():
    """交互模式：扫描 .nbt 文件，列出让用户选择。"""
    try:
        sys.stdout.reconfigure(encoding="utf-8", errors="replace")
    except Exception:
        pass

    program_dir = get_program_dir()
    mapping_path = find_mapping(program_dir)
    mapping, block_properties = load_block_mapping(mapping_path)

    output_base = program_dir / "converted"

    print("=" * 55)
    print("  PasterDream-Reborn  —  NBT 结构方块转换工具")
    print("=" * 55)
    print(f"  程序目录: {program_dir}")
    print(f"  输出目录: {output_base}")
    print(f"  映射文件: {mapping_path} ({len(mapping)} 个方块映射)")
    print()

    nbt_files = scan_nbt_files(program_dir)

    if not nbt_files:
        print("在当前目录未找到任何 .nbt 文件。")
        print("请将要转换的 .nbt 结构文件放到与 convert_structure.exe 相同的目录下。")
        print()
        input("按回车键退出...")
        return

    print(f"找到 {len(nbt_files)} 个 .nbt 文件：")
    for i, f in enumerate(nbt_files, 1):
        size_kb = f.stat().st_size / 1024
        marker = ""
        if (output_base / f.name).exists():
            marker = "  <- 已转换"
        print(f"  [{i:2d}] {f.name}  ({size_kb:.1f} KB){marker}")

    print()
    print("输入数字选择要转换的文件，输入 q 退出。")

    while True:
        try:
            choice = input("请选择 > ").strip()
        except (EOFError, KeyboardInterrupt):
            print()
            print("已退出。")
            break

        if choice.lower() in ("q", "quit", "exit"):
            print("已退出。")
            break

        try:
            idx = int(choice) - 1
        except ValueError:
            print("请输入有效数字或 q 退出。")
            continue

        if 0 <= idx < len(nbt_files):
            selected = nbt_files[idx]
            output_path = output_base / selected.name

            # --- 预扫描：统计未映射和待搬运的方块 ---
            nbt = nbtlib.load(str(selected))
            unmapped = set()
            pending = set()
            for entry in nbt["palette"]:
                full_name = str(entry["Name"])
                if full_name.startswith("minecraft:"):
                    continue
                if ":" in full_name:
                    _, short_name = full_name.split(":", 1)
                else:
                    short_name = full_name
                if short_name not in mapping:
                    unmapped.add(short_name)
                elif mapping[short_name] == PENDING_MARKER:
                    pending.add(short_name)

            delete_unmapped = False
            if unmapped or pending:
                print()
                print(f">> 正在转换: {selected.name} ...")
                print()
                print("  ── 预扫描结果 ──")

                if unmapped:
                    print(f"  未在 mapping 中找到的方块（{len(unmapped)} 种）：")
                    for b in sorted(unmapped):
                        print(f"    - {b}")

                if pending:
                    print(f"  标记为「待搬运」的方块（{len(pending)} 种）：")
                    for b in sorted(pending):
                        print(f"    - {b}")

                print()
                print("  是否将以上方块全部替换为空气（minecraft:air）？")
                answer = input("  替换为空气？[y/N] > ").strip().lower()
                if answer in ("y", "yes"):
                    delete_unmapped = True
                    print()
                    print("  已选择：将未映射 / 待搬运的方块替换为空气。")
                else:
                    print()
                    print("  已选择：保持原样。")

                print()
                print(f">> 正在转换: {selected.name} ...")
            else:
                print()
                print(f">> 正在转换: {selected.name} ...")

            convert_one(selected, mapping, block_properties, output_path, delete_unmapped=delete_unmapped)
            print()
            print(f"[OK] 转换完成！已保存至: {output_path}")
        else:
            print(f"请输入 1 到 {len(nbt_files)} 之间的数字。")


def main():
    try:
        sys.stdout.reconfigure(encoding="utf-8", errors="replace")
    except Exception:
        pass

    parser = argparse.ArgumentParser(
        description="PasterDream-Reborn NBT 结构方块转换工具"
    )
    parser.add_argument(
        "-i", "--input",
        type=Path,
        default=None,
        help="输入 .nbt 文件（指定后跳过交互，直接转换）",
    )
    parser.add_argument(
        "-o", "--output",
        type=Path,
        default=None,
        help="输出 .nbt 文件（仅在指定 -i 时有效）",
    )
    parser.add_argument(
        "--delete-unmapped",
        action="store_true",
        help="将未映射和待搬运的模组方块也替换为空气（仅在指定 -i 时有效）",
    )

    args = parser.parse_args()

    if args.input is not None:
        program_dir = get_program_dir()
        mapping_path = find_mapping(program_dir)
        mapping, block_properties = load_block_mapping(mapping_path)

        if not args.input.exists():
            print(f"错误：输入文件不存在 — {args.input}")
            sys.exit(1)

        if args.output is None:
            output_dir = args.input.resolve().parent / "converted"
            output_path = output_dir / args.input.name
        else:
            output_path = args.output

        # 预扫描
        nbt = nbtlib.load(str(args.input))
        unmapped = set()
        pending = set()
        for entry in nbt["palette"]:
            full_name = str(entry["Name"])
            if full_name.startswith("minecraft:"):
                continue
            if ":" in full_name:
                _, short_name = full_name.split(":", 1)
            else:
                short_name = full_name
            if short_name not in mapping:
                unmapped.add(short_name)
            elif mapping[short_name] == PENDING_MARKER:
                pending.add(short_name)

        if args.delete_unmapped:
            if unmapped:
                print(f"  --delete-unmapped: 将 {len(unmapped)} 种未映射方块替换为空气")
            if pending:
                print(f"  --delete-unmapped: 将 {len(pending)} 种待搬运方块替换为空气")

        print(f">> 正在转换: {args.input.name} ...")
        convert_one(args.input, mapping, block_properties, output_path, delete_unmapped=args.delete_unmapped)
        print(f"[OK] 转换完成！已保存至: {output_path}")
    else:
        run_interactive()


if __name__ == "__main__":
    main()
