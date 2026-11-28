#!/usr/bin/env python3
"""从 client-extra.jar 生成原版物品 ID 清单（供 check_item_ids.py 离线使用）。

CI 上没有 Minecraft jar，所以把 1.20.1 的物品 ID 固化成
`tools/data/vanilla_1_20_1_ids.json`。升级 MC 版本或怀疑清单过期时，
在本地重新跑一遍本脚本即可。

数据来源（client-extra.jar 内）：
  * `assets/minecraft/models/item/*.json`  —— 有物品模型的原版物品
  * `assets/minecraft/lang/en_us.json` 的 `item.minecraft.*` —— 无模型物品（刷怪蛋等）
  * `assets/minecraft/lang/en_us.json` 的 `block.minecraft.*` —— 方块；其中带 BlockItem 的
    方块同样可以作为物品引用（原版语言文件不为 BlockItem 生成 `item.minecraft.*` 键）

用法：
  python tools/gen_vanilla_ids.py
  python tools/gen_vanilla_ids.py --jar "C:/path/to/client-extra.jar" --out tools/data/vanilla_1_20_1_ids.json

不带 --jar 时依次尝试：
  $MC_JAR、GRADLE_USER_HOME/.gradle/caches/forge_gradle/minecraft_repo/versions/<ver>/client-extra.jar
"""

from __future__ import annotations

import argparse
import json
import os
import pathlib
import sys
import zipfile

ROOT = pathlib.Path(__file__).resolve().parents[1]
DEFAULT_OUT = ROOT / "tools" / "data" / "vanilla_1_20_1_ids.json"
MC_VERSION = "1.20.1"

# 注册了方块但没有对应物品（BlockItem）的方块，不能出现在配方的 "item" 字段里。
# 注意 air 不在此列：原版注册了 `minecraft:air`（AirItem），
# 自定义配方里常用它占位表示「空槽位」。
NON_ITEM_BLOCKS = (
    "bubble_column",
    "cave_air",
    "end_gateway",
    "end_portal",
    "fire",
    "frosted_ice",
    "lava",
    "moving_piston",
    "nether_portal",
    "piston_head",
    "soul_fire",
    "void_air",
    "water",
)

MODEL_PREFIX = "assets/minecraft/models/item/"
LANG_PATH = "assets/minecraft/lang/en_us.json"


def default_jars() -> list[pathlib.Path]:
    candidates = []
    env_jar = os.environ.get("MC_JAR")
    if env_jar:
        candidates.append(pathlib.Path(env_jar))
    gradle_home = os.environ.get("GRADLE_USER_HOME")
    if gradle_home:
        candidates.append(
            pathlib.Path(gradle_home)
            / "caches"
            / "forge_gradle"
            / "minecraft_repo"
            / "versions"
            / MC_VERSION
            / "client-extra.jar"
        )
    candidates.append(
        pathlib.Path.home()
        / ".gradle"
        / "caches"
        / "forge_gradle"
        / "minecraft_repo"
        / "versions"
        / MC_VERSION
        / "client-extra.jar"
    )
    return candidates


def collect(jar: pathlib.Path) -> tuple[list[str], list[str]]:
    with zipfile.ZipFile(jar) as zf:
        names = zf.namelist()
        item_ids = {
            name[len(MODEL_PREFIX) : -len(".json")]
            for name in names
            if name.startswith(MODEL_PREFIX)
            and name.endswith(".json")
            and "/" not in name[len(MODEL_PREFIX) :]
        }
        lang = json.loads(zf.read(LANG_PATH).decode("utf-8"))

    for key in lang:
        if key.startswith("item.minecraft."):
            item_ids.add(key[len("item.minecraft.") :])
        elif key.startswith("block.minecraft."):
            item_ids.add(key[len("block.minecraft.") :])

    for block in NON_ITEM_BLOCKS:
        item_ids.discard(block)

    return sorted(item_ids), sorted(NON_ITEM_BLOCKS)


def render(ids: list[str], excluded: list[str]) -> bytes:
    lines = ["{", f'  "minecraft_version": "{MC_VERSION}",']
    lines.append('  "generated_by": "tools/gen_vanilla_ids.py",')
    lines.append(
        '  "source": "client-extra.jar: assets/minecraft/models/item/*.json + lang/en_us.json",'
    )
    for field, values in (("non_item_blocks_excluded", list(excluded)), ("item_ids", ids)):
        lines.append(f'  "{field}": [')
        for i, value in enumerate(values):
            comma = "," if i + 1 < len(values) else ""
            lines.append(f'    "{value}"{comma}')
        lines.append("  ]," if field != "item_ids" else "  ]")
    lines.append("}")
    return ("\r\n".join(lines) + "\r\n").encode("utf-8")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--jar", type=pathlib.Path, help="client-extra.jar 路径")
    parser.add_argument("--out", type=pathlib.Path, default=DEFAULT_OUT, help="输出 JSON 路径")
    args = parser.parse_args()

    jar = args.jar
    if jar is None:
        for candidate in default_jars():
            if candidate.is_file():
                jar = candidate
                break
    if jar is None or not jar.is_file():
        print("找不到 client-extra.jar，请用 --jar 指定路径。", file=sys.stderr)
        for candidate in default_jars():
            print(f"  已尝试: {candidate}", file=sys.stderr)
        return 1

    ids, excluded = collect(jar)
    args.out.parent.mkdir(parents=True, exist_ok=True)
    args.out.write_bytes(render(ids, excluded))
    print(f"来源: {jar}")
    print(f"输出: {args.out}")
    print(f"原版物品 ID: {len(ids)} 条，排除的无物品方块: {len(excluded)} 个")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
