# PasterDream: Reborn

[![Visits](https://starry-trace-sky-moe-counter.vercel.app/get/@PasterDream-Reborn?theme=rule34)](https://github.com/PasterDreamReborn-Organization/PasterDream-Reborn-forge1.20.1)

![Language](https://img.shields.io/badge/Language-Java_17-blue.svg?style=for-the-badge)
![Build Tool](https://img.shields.io/badge/Build_Tool-Gradle-green.svg?style=for-the-badge)
![MC Version](https://img.shields.io/badge/MC_Version-1.20.1-yellow.svg?style=for-the-badge)
![Mod Loader](https://img.shields.io/badge/Mod_Loader-Forge_47.4.10-orange.svg?style=for-the-badge)

<p align="center">
  <span>English</span> |
  <a href="README.md">简体中文</a>
</p>

A community rewrite of the original PasterDream mod, rebuilt from scratch on Minecraft Forge 1.20.1.

## Authorization

This project is released with permission from the original author "Aerolite_Dust." The mod ID `pasterdream` is retained. See [PERMISSION_GRANTED.md](PERMISSION_GRANTED.md) for authorization proof. Most art assets (textures, models, sounds, etc.) are from the original mod, licensed under All Rights Reserved, copyright by the original author and contributors. Some textures have been redrawn or newly created by the rewrite team, copyright by the rewrite team. See [ASSETS_MANIFEST.md](src/main/resources/ASSETS_MANIFEST.md).

## License

- **Code**: [MIT](LICENSE.md) — Free to use, modify, and distribute
- **Art Assets**: [ARR](src/main/resources/LICENSE_ASSETS.md) — All Rights Reserved. Original mod assets copyright by the original author; rewrite team assets copyright by the rewrite team. See [Asset Manifest](src/main/resources/ASSETS_MANIFEST.md)

## Installation

Clone the repository and open it in your IDE. IntelliJ IDEA is recommended.

If libraries are missing or you encounter issues, run:

```bash
./gradlew --refresh-dependencies  # Refresh local cache
./gradlew clean                   # Reset build (does not affect code)
```

> **Note**: If Chinese comments appear garbled, set your IDE project encoding to **UTF-8** (File → Settings → Editor → File Encodings → set both Project Encoding and Default encoding for properties files to UTF-8).

## Build

```bash
./gradlew build       # Build Jar
./gradlew runClient   # Launch client
./gradlew runData     # Generate data files
```

## Dependencies

| Dependency | Version Requirement |
|------------|-------------------|
| Minecraft Forge | 47+ |
| Curios | 5.14.1+ |
| GeckoLib | 4.8.3+ |
| Patchouli | 1.20.1-84+ |
| JEI (Optional) | 15.20.0.112+ |
| KubeJS (Optional) | 2001.6.5+ |

## Documentation

Project documentation is available in the [`document/`](document/) directory:

- [`document/design/`](document/design/) — Design documents, ID mapping, naming conventions, new content list
- [`document/rule/program/`](document/rule/program/) — Code architecture guidelines, programming standards, directory structure, recipe balance system
- [`document/reference/`](document/reference/) — Original mod structural analysis and reference files
- [`document/patchouli_books/`](document/patchouli_books/) — PasterDream guidebook design

## Credits

- **Original Author**: Aerolite_Dust — Thanks for authorization and support
- **Design**: ShiLiuYinYu
- **Programming**: 2884omgpy, ShiLiuYinYu, Garam
- **Art**: GQ2529, Garam, Vulmoon_XwX
- **Writing**: Yumihoshi, bei_xu, Akizuki Ayane, 月琴语
- **Special Thanks**: 福米花_flow

<a href="https://github.com/PasterDreamReborn-Organization/PasterDream-Reborn-forge1.20.1/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=PasterDreamReborn-Organization/PasterDream-Reborn-forge1.20.1" />
</a>

## Other Resources

Forge Community Docs: https://docs.minecraftforge.net/  
Forge Discord: https://discord.minecraftforge.net/
