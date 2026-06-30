"""
从 mapping.json 查旧 ID ->新 ID。
用法：
    python tools/lookup_id.py flower_16          # 单个查询
    python tools/lookup_id.py flower_1 flower_2  # 批量查询
    python tools/lookup_id.py -f list.txt        # 从文件读取
    python tools/lookup_id.py -a                 # 列出全部
"""
import json
import sys
from pathlib import Path

SCRIPT_DIR = Path(__file__).resolve().parent          # tools/
PROJECT_ROOT = SCRIPT_DIR.parent                      # 项目根目录
MAPPING = PROJECT_ROOT / "dist" / "mapping.json"      # 与 exe 同目录


def load():
    return json.loads(MAPPING.read_text(encoding="utf-8"))


def lookup(mapping, old_id):
    for category, entries in mapping.items():
        if old_id in entries:
            return category, entries[old_id]
    return None, None


def main():
    mapping = load()

    args = sys.argv[1:]
    if not args:
        print("用法: python lookup_id.py <旧ID> [...]")
        print("      python lookup_id.py -a  列出全部")
        print("      python lookup_id.py -f <文件>")
        return

    if args[0] == "-a":
        for cat, entries in mapping.items():
            if entries:
                print(f"\n## {cat} ({len(entries)})")
                for old, new in sorted(entries.items()):
                    print(f"  {old} -> {new}")
        return

    if args[0] == "-f" and len(args) > 1:
        ids = Path(args[1]).read_text(encoding="utf-8").split()
    else:
        ids = args

    for old_id in ids:
        cat, new_id = lookup(mapping, old_id.strip())
        if new_id:
            print(f"[{cat}] {old_id} ->{new_id}")
        else:
            print(f"[NOT FOUND] {old_id}")


if __name__ == "__main__":
    main()
