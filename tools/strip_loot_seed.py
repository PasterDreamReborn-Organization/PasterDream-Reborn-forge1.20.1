#!/usr/bin/env python3
"""
NBT 战利品种子清除工具 — PasterDream-Reborn

移除结构 NBT 文件中所有箱子方块实体的 LootTableSeed 标签。
删除后，每次放置结构时战利品将随机生成，而非固定不变。

用法：
    python strip_loot_seed.py                         # 交互模式（处理同目录所有 .nbt）
    python strip_loot_seed.py -i input.nbt            # 处理单个文件
    python strip_loot_seed.py -i input.nbt -o out.nbt # 指定输出路径
    python strip_loot_seed.py --dry-run               # 仅扫描，不修改
"""

import argparse
import sys
from pathlib import Path

import nbtlib


def strip_loot_seeds(nbt):
    """遍历 blocks 列表，删除其中所有方块实体 NBT 中的 LootTableSeed。返回移除数量。"""
    blocks = nbt["blocks"]
    removed = 0
    for block_entry in blocks:
        if "nbt" not in block_entry:
            continue
        nbt_data = block_entry["nbt"]
        if "LootTableSeed" in nbt_data:
            del nbt_data["LootTableSeed"]
            removed += 1
    return removed


def scan_seeds(nbt):
    """扫描并返回包含 LootTableSeed 的方块信息列表。"""
    blocks = nbt["blocks"]
    found = []
    for block_entry in blocks:
        if "nbt" not in block_entry:
            continue
        nbt_data = block_entry["nbt"]
        if "LootTableSeed" in nbt_data:
            pos = block_entry.get("pos", "unknown")
            loot_table = nbt_data.get("LootTable", "unknown")
            seed = nbt_data["LootTableSeed"]
            found.append((pos, loot_table, seed))
    return found


def process_file(input_path, output_path, dry_run=False):
    """处理单个 .nbt 文件。"""
    nbt = nbtlib.load(str(input_path))
    found = scan_seeds(nbt)

    if not found:
        print(f"  [跳过] 未找到 LootTableSeed — {input_path.name}")
        return 0

    print(f"  发现 {len(found)} 个含种子的方块：")
    for pos, loot_table, seed in found:
        coord = f"({pos})" if pos != "unknown" else ""
        print(f"    - LootTable: {loot_table}, Seed: {seed} {coord}")

    if dry_run:
        print(f"  (dry-run) 将移除 {len(found)} 个 LootTableSeed")
        return 0

    removed = strip_loot_seeds(nbt)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    nbt.save(str(output_path))
    print(f"  [OK] 已移除 {removed} 个 LootTableSeed -> {output_path.name}")
    return removed


def scan_nbt_files(directory):
    """扫描目录下的所有 .nbt 文件，返回排序后的 Path 列表。"""
    return sorted(directory.glob("*.nbt"))


def run_interactive():
    """交互模式：列出 .nbt 文件，让用户选择处理。"""
    try:
        sys.stdout.reconfigure(encoding="utf-8", errors="replace")
    except Exception:
        pass

    cwd = Path.cwd()
    print("=" * 55)
    print("  PasterDream-Reborn  —  NBT 战利品种子清除工具")
    print("=" * 55)
    print(f"  当前目录: {cwd}")
    print()

    nbt_files = scan_nbt_files(cwd)

    if not nbt_files:
        print("在当前目录未找到任何 .nbt 文件。")
        return

    print(f"找到 {len(nbt_files)} 个 .nbt 文件：")
    for i, f in enumerate(nbt_files, 1):
        size_kb = f.stat().st_size / 1024
        print(f"  [{i:2d}] {f.name}  ({size_kb:.1f} KB)")

    print()
    print("输入数字处理单个文件，输入 a 处理全部，输入 q 退出。")

    while True:
        try:
            choice = input("请选择 > ").strip()
        except (EOFError, KeyboardInterrupt):
            print()
            break

        if choice.lower() in ("q", "quit", "exit"):
            print("已退出。")
            break

        if choice.lower() == "a":
            total = 0
            for f in nbt_files:
                output_path = cwd / f.name
                total += process_file(f, output_path)
            print()
            print(f"全部完成！共移除 {total} 个 LootTableSeed。")
            break

        try:
            idx = int(choice) - 1
        except ValueError:
            print("请输入有效数字、a 或 q。")
            continue

        if 0 <= idx < len(nbt_files):
            selected = nbt_files[idx]
            output_path = cwd / selected.name
            process_file(selected, output_path)
        else:
            print(f"请输入 1 到 {len(nbt_files)} 之间的数字。")


def main():
    parser = argparse.ArgumentParser(
        description="PasterDream-Reborn NBT 战利品种子清除工具"
    )
    parser.add_argument(
        "-i", "--input",
        type=Path,
        default=None,
        help="输入 .nbt 文件（指定后跳过交互，直接处理）",
    )
    parser.add_argument(
        "-o", "--output",
        type=Path,
        default=None,
        help="输出 .nbt 文件（默认覆盖原文件所在目录下的同名文件）",
    )
    parser.add_argument(
        "--dry-run",
        action="store_true",
        help="仅扫描并显示含种子的方块，不实际修改",
    )

    args = parser.parse_args()

    if args.input is not None:
        if not args.input.exists():
            print(f"错误：输入文件不存在 — {args.input}")
            sys.exit(1)

        if args.output is None:
            args.output = args.input.resolve().parent / args.input.name

        process_file(args.input, args.output, dry_run=args.dry_run)
    else:
        run_interactive()


if __name__ == "__main__":
    main()
