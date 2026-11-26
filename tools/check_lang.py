#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""语言文件生成器校验（合并门禁）。

校验 src/main/java/.../datagen/lang/ 下的 en_us / zh_cn 语言生成器：

  E01 文件缺失 / 不是 UTF-8
  E02 带 BOM
  E03 行尾不是 CRLF，或文件末尾缺少换行
  E04 add(...) 解析失败（多行、未闭合、结尾不是 );）
  E05 字符串键格式异常
  E06 同一文件内字符串字面量键重复
  E07 两种语言的 add(...) 条目数不一致
  E10 zh_cn 出现 ASCII 点串（应统一为「……」）
  E11 zh_cn 的「…」连写长度不是 2
  E12 en_us 出现「…」
  E13 en_us 的 ASCII 点串长度不是 3
  E14 zh_cn 自然文本出现半角括号（应统一为「（）」）
  E15 en_us 出现全角括号
  E16 Patchouli 宏 $(...) 损坏或内容非法
  E17 成对括号数量不匹配（多页书籍 ``book.*_第N页`` 允许跨页拆分，不查此项）
  E19 非法转义序列

  W01 两种语言的字符串字面量键集合不一致（可能只是注册名形式的写法不同）
  W02 值里出现「【」等占位符/非常规方括号
  W03 值以空白字符结尾
  W04 翻译值为空（多为 Patchouli 空白页，需人工确认）

退出码：0 通过；1 有错误。CI（.github/workflows/lang-check.yml）与 `gradlew check`
据此拦截合并。规则来源：document/rule/program/程序规范文档.md「多语言文本规范」。

用法：
    python tools/check_lang.py
    python tools/check_lang.py --lang-dir <dir>   # 指定语言生成器目录（自测用）
"""

from __future__ import annotations

import argparse
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DEFAULT_LANG_DIR = ROOT / "src" / "main" / "java" / "com" / "pasterdream" / "pasterdreammod" / "datagen" / "lang"

PROVIDERS = [
    ("en_us", "ModEnUsLangProvider.java"),
    ("zh_cn", "ModZhCnLangProvider.java"),
]

ELLIPSIS = {"en_us": "...", "zh_cn": "……"}

# 整段是代码/命令的文案，允许保留半角括号与 ASCII 点串（键名以字面量形式书写）
CODE_ALLOWLIST = {
    "book.pasterdream.content.使用说明",
    "tooltip.pasterdream.debug_tool.我将赐予你操控万物的能力",
    "tooltip.pasterdream.debug_tool.拿在手上右键点击打开屏幕",
}

# 跨页对话/长文：引号、括号可能开在一页、闭在下一页，按页拆分后单条不配对
MULTIPAGE_BOOK_RE = re.compile(r"^book\.pasterdream\..*_第\d+页$")

FULLWIDTH_PAIRS = [("（", "）"), ("「", "」"), ("『", "』"), ("《", "》"), ("【", "】")]
ASCII_PAIRS = [("(", ")"), ("[", "]")]
MACRO_RE = re.compile(r"\$\(([^()]*)\)")
BOM = b"\xef\xbb\xbf"

issues: list[tuple[str, str, str, int, str]] = []  # (severity, rule, file, line, message)


def add_issue(severity: str, rule: str, file: str, line: int, message: str) -> None:
    issues.append((severity, rule, file, line, message))


def decode_literal(raw: str) -> str:
    """把字符串字面量内部的原始文本（不含两端引号）解码为实际值。"""
    out: list[str] = []
    i = 0
    n = len(raw)
    while i < n:
        c = raw[i]
        if c != "\\":
            out.append(c)
            i += 1
            continue
        if i + 1 >= n:
            raise ValueError("悬空反斜杠")
        e = raw[i + 1]
        simple = {"n": "\n", "t": "\t", "r": "\r", '"': '"', "'": "'", "\\": "\\"}
        if e in simple:
            out.append(simple[e])
            i += 2
            continue
        if e == "u":
            hexs = raw[i + 2 : i + 6]
            if len(hexs) != 4 or not re.fullmatch(r"[0-9a-fA-F]{4}", hexs):
                raise ValueError("非法的 \\u 转义")
            out.append(chr(int(hexs, 16)))
            i += 6
            continue
        raise ValueError(f"未知转义 \\{e}")
    return "".join(out)


def scan_literals(line: str) -> list[tuple[str, str, int, int]]:
    """返回 [(解码值, 原始内部文本, 起始引号下标, 结束引号下标), ...]。"""
    lits: list[tuple[str, str, int, int]] = []
    i = 0
    n = len(line)
    while i < n:
        if line[i] != '"':
            i += 1
            continue
        q1 = i
        j = i + 1
        while True:
            if j >= n:
                raise ValueError("字符串未闭合")
            c = line[j]
            if c == "\\":
                if j + 1 >= n:
                    raise ValueError("悬空反斜杠")
                j += 6 if line[j + 1] == "u" else 2
                continue
            if c == '"':
                break
            if c in "\r\n":
                raise ValueError("字符串内出现真实换行")
            j += 1
        raw = line[q1 + 1 : j]
        lits.append((decode_literal(raw), raw, q1, j))
        i = j + 1
    return lits


def parse_add(line: str) -> tuple[str | None, str, str | None]:
    """解析一行 add(...)。返回 (字面量键或 None, 翻译值, 错误信息或 None)。"""
    m = re.match(r"[ \t]*add\(", line)
    if not m:
        return None, "", "不是 add( 开头"
    try:
        lits = scan_literals(line)
    except ValueError as exc:
        return None, "", f"字符串解析失败：{exc}"
    if not lits:
        return None, "", "add(...) 没有字符串参数"
    value, _raw, _q1, q2 = lits[-1]
    tail = line[q2 + 1 :]
    if not re.match(r"^[ \t]*\);[ \t]*$", tail):
        return None, "", "结尾不是 );，或 add(...) 跨行"
    key = None
    if len(lits) >= 2:
        first = lits[0]
        between = line[m.end() : first[2]]
        after = line[first[3] + 1 :]
        if between.strip() == "" and re.match(r"^[ \t]*,", after):
            key = first[0]
    return key, value, None


def check_encoding(path: Path, display: str) -> str:
    data = path.read_bytes()
    if data.startswith(BOM):
        add_issue("error", "E02", display, 0, "文件带 UTF-8 BOM")
        data = data[len(BOM) :]
    try:
        text = data.decode("utf-8")
    except UnicodeDecodeError as exc:
        add_issue("error", "E01", display, 0, f"不是合法 UTF-8：{exc}")
        return data.decode("utf-8", "replace")
    return text


def check_eol(text: str, display: str) -> None:
    line_no = 1
    idx = 0
    while True:
        pos = text.find("\n", idx)
        if pos < 0:
            break
        if pos == 0 or text[pos - 1] != "\r":
            add_issue("error", "E03", display, line_no, "行尾是 LF，项目要求 CRLF")
        line_no += 1
        idx = pos + 1
    if not text.endswith("\r\n"):
        add_issue("error", "E03", display, max(line_no, 1), "文件末尾缺少 CRLF 换行")
    for m in re.finditer(r"\r(?!\n)", text):
        add_issue("error", "E03", display, text.count("\n", 0, m.start()) + 1, "出现单独的 CR")


def check_key(key: str, display: str, line_no: int) -> None:
    if key != key.strip():
        add_issue("error", "E05", display, line_no, f"键首尾有空白：{key!r}")
    if key.startswith(".") or key.endswith("."):
        add_issue("error", "E05", display, line_no, f"键以点开头或结尾：{key!r}")
    if ".." in key:
        add_issue("error", "E05", display, line_no, f"键中出现连续两个点：{key!r}")


def check_value(lang: str, key: str | None, value: str, display: str, line_no: int) -> None:
    if value == "":
        add_issue("warning", "W04", display, line_no, "翻译值为空（确认是否为有意的空白页）")
        return
    if key is not None and key in CODE_ALLOWLIST:
        return

    # Patchouli 宏
    if "$（" in value or "$)" in value or "$（" in value:
        add_issue("error", "E16", display, line_no, "Patchouli 宏被写成了全角括号：$(...)")
    for m in MACRO_RE.finditer(value):
        if not re.fullmatch(r"[A-Za-z0-9#]{1,16}", m.group(1)):
            add_issue("error", "E16", display, line_no, f"Patchouli 宏内容异常：{m.group(0)!r}")
    stripped = MACRO_RE.sub("", value)
    if "$(" in stripped:
        add_issue("error", "E16", display, line_no, "存在未闭合的 $( 宏")

    # 省略号
    if lang == "zh_cn":
        if re.search(r"\.{2,}", value):
            add_issue("error", "E10", display, line_no, f"中文省略号应写成「……」：{_excerpt(value)}")
        for m in re.finditer(r"…+", value):
            if len(m.group(0)) != 2:
                add_issue("error", "E11", display, line_no, f"「…」连写长度应为 2：{_excerpt(value)}")
    else:
        if "…" in value:
            add_issue("error", "E12", display, line_no, f"英文省略号应写成 \"...\"，不要用「…」：{_excerpt(value)}")
        for m in re.finditer(r"\.{2,}", value):
            if len(m.group(0)) != 3:
                add_issue("error", "E13", display, line_no, f"英文省略号应恰好 3 个点：{_excerpt(value)}")

    # 括号
    if lang == "zh_cn":
        if "(" in stripped or ")" in stripped:
            add_issue("error", "E14", display, line_no, f"中文文本里的括号应写成「（）」：{_excerpt(value)}")
        pairs = FULLWIDTH_PAIRS + ASCII_PAIRS
    else:
        if "（" in value or "）" in value:
            add_issue("error", "E15", display, line_no, f"英文文本不要用全角括号：{_excerpt(value)}")
        pairs = ASCII_PAIRS

    if key is not None and MULTIPAGE_BOOK_RE.match(key):
        return
    for left, right in pairs:
        if value.count(left) != value.count(right):
            add_issue(
                "error",
                "E17",
                display,
                line_no,
                f"括号不成对：{left}={value.count(left)}，{right}={value.count(right)}：{_excerpt(value)}",
            )


def _excerpt(value: str, limit: int = 60) -> str:
    flat = value.replace("\n", "\\n")
    return flat if len(flat) <= limit else flat[:limit] + "…"


def check_provider(lang: str, filename: str, lang_dir: Path) -> tuple[int, set[str]]:
    path = lang_dir / filename
    display = f"{lang}/{filename}"
    if not path.is_file():
        add_issue("error", "E01", display, 0, "文件不存在")
        return 0, set()

    text = check_encoding(path, display)
    check_eol(text, display)

    seen: dict[str, list[int]] = {}
    entries = 0
    for line_no, line in enumerate(text.split("\n"), 1):
        if not re.match(r"[ \t]*add\(", line):
            continue
        entries += 1
        key, value, err = parse_add(line.rstrip("\r"))
        if err:
            add_issue("error", "E04", display, line_no, err)
            continue
        if key is not None:
            check_key(key, display, line_no)
            seen.setdefault(key, []).append(line_no)
        if value.endswith((" ", "\t")):
            add_issue("warning", "W03", display, line_no, f"值以空白结尾：{_excerpt(value)}")
        if "【" in value:
            add_issue("warning", "W02", display, line_no, f"值含「【」，疑似占位符残留：{_excerpt(value)}")
        check_value(lang, key, value, display, line_no)

    for key, lines in seen.items():
        if len(lines) > 1:
            add_issue("error", "E06", display, lines[0], f"键重复：{key!r}（行 {', '.join(map(str, lines))}）")
    return entries, set(seen)


def main(argv: list[str]) -> int:
    for stream in (sys.stdout, sys.stderr):
        try:
            stream.reconfigure(encoding="utf-8", errors="replace")
        except (AttributeError, ValueError):
            pass
    parser = argparse.ArgumentParser(description="校验语言文件生成器")
    parser.add_argument("--lang-dir", default=str(DEFAULT_LANG_DIR), help="语言生成器目录（自测用）")
    args = parser.parse_args(argv)
    lang_dir = Path(args.lang_dir)

    counts: dict[str, int] = {}
    keys: dict[str, set[str]] = {}
    for lang, filename in PROVIDERS:
        counts[lang], keys[lang] = check_provider(lang, filename, lang_dir)
        add_issue("info", "-", lang, 0, f"共 {counts[lang]} 条 add(...)")

    if counts["en_us"] != counts["zh_cn"]:
        add_issue(
            "error",
            "E07",
            "lang",
            0,
            f"两种语言条目数不一致：en_us={counts['en_us']}，zh_cn={counts['zh_cn']}",
        )
    only_en = keys["en_us"] - keys["zh_cn"]
    only_zh = keys["zh_cn"] - keys["en_us"]
    if only_en or only_zh:
        add_issue("warning", "W01", "lang", 0, f"字面量键集合不同：en 独有 {len(only_en)} 个，zh 独有 {len(only_zh)} 个")
        for key in sorted(only_en)[:10]:
            add_issue("info", "-", "en_us", 0, f"仅 en 独有键：{key}")
        for key in sorted(only_zh)[:10]:
            add_issue("info", "-", "zh_cn", 0, f"仅 zh 独有键：{key}")

    for severity, rule, file, line, message in issues:
        tag = {"error": "错误", "warning": "警告", "info": "信息"}[severity]
        where = f"{file}:{line}" if line else file
        print(f"[{tag}] {rule} {where} {message}")

    errors = sum(1 for i in issues if i[0] == "error")
    warnings = sum(1 for i in issues if i[0] == "warning")
    print()
    print(f"结果：错误 {errors} 个，警告 {warnings} 个")
    return 1 if errors else 0


if __name__ == "__main__":
    sys.exit(main(sys.argv[1:]))
