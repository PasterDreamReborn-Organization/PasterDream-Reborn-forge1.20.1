# PasterDream: Reborn 自定义协议（PDR 协议）

PasterDream: Reborn Custom License (PDR License)

Copyright (c) 2026 PasterDream: Reborn Team (ShiLiuYinYu, 2884omgpy, Garam)

> 本协议适用于本项目的**源代码与文档**，以及**对本模组的使用、分发、附属创作与相关内容发布行为**。美术资源的版权及复制、分发、修改许可另行适用 [LICENSE_ASSETS.md](src/main/resources/LICENSE_ASSETS.md)（保留所有权利 ARR，版权归原作者"异星之尘"(Aerolite_Dust) 及重写团队所有）；但本协议第 2 条（禁止行为）、第 4 条（内容规范）及其他关于使用行为的条款，同样适用于涉及这些美术资源的内容发布。

---

## 0. 定义

- **本模组**：PasterDream: Reborn 的官方发布版本及其源代码、文档与资源。
- **公开 API（Public API）**：本模组明确对外提供、允许第三方调用或实现的接口，包括官方文档列明的类与方法、事件、注册扩展点、KubeJS 等脚本接口，以及数据包/资源包格式。**未被官方文档列明或在代码中标注为公开的部分，均不属于公开 API。**
- **附属模组（Addon）**：与 Minecraft 及本模组一同加载运行，并在运行时扩展、修改或依赖本模组内容、机制、数据或实现的任何模组、插件、数据包、资源包或脚本。**是否复制本模组代码或资源，不影响其被认定为附属模组。**
- **独立程序**：不以扩展或修改本模组为目的、不随 Minecraft 或本模组一同加载，仅通过公开 API（如外部数据查询、构建工具）访问本模组的独立软件。
- **条款优先级**：本协议第 3 条优先于第 1 条。任何符合「附属模组」定义的作品，一律适用第 3 条，不因第 1 条「独立程序」的授权而豁免事先书面许可与名单登记要求。

## 1. 授权范围（无需事先许可即可）

在遵守本协议全部条款的前提下，无需事先获得书面许可：

- **下载与游玩**：安装并使用本模组。
- **整合包收录**：在整合包中分发**未经修改的官方发布版本**，建议标注模组来源。
- **个人学习**：出于个人学习、研究目的阅读、分析或反编译代码。
- **独立程序**：编写符合第 0 条「独立程序」定义、仅通过公开 API 依赖本模组的独立程序。**本条不适用于任何附属模组。**

## 2. 完全禁止（任何情况下均禁止）

以下行为**任何情况下均禁止**，版权所有者不会授予许可：

- **商业利用**：直接出售本模组，或以本模组为载体牟利（直接出售模组本身亦为 Minecraft EULA 明令禁止）。整合包内收录未修改官方版本不视为商业利用。
- **名义使用**：将 "PasterDream: Reborn"、"帕斯特之梦：重生" 或其近似名称用于与本模组及其他 PasterDream: Reborn 移植版无关的任何内容。
- **整合包收费**：完全免费的整合包可使用本模组；禁止包含本模组的整合包正式发布版本进行任何形式的收费与变相收费。

## 3. 需事先书面许可

以下行为**必须事先获得版权所有者书面许可**，否则禁止：

- **附属模组**：制作、发布基于或扩展本模组内容的附属模组，包括但不限于复制本模组代码、资源或改动其机制。**即使该附属模组仅通过公开 API 实现、未复制任何代码或资源，亦不例外。** 除事先获得书面许可外，还必须满足下方第 3.1 条的名单登记要求。
- **移植**：将本模组（或其任何部分）移植到其他 Minecraft 版本或模组加载器（Forge、NeoForge、Fabric、Quilt 等）。
- **再分发修改版**：禁止修改代码后以任何形式再分发，包括所谓的"修复版""改进版"等。若出于玩法扩展等目的，请以**附属模组**的形式实现，并通过 Mixin 等技术在运行时修改，**不要修改模组本体**。发布资源包/光影包（其中可能包含翻译文件、物品/方块材质等）须经版权所有者书面许可。
- **含本模组的地图出售**：出售使用、包含或展示本模组的地图、存档等制品，需事先获得书面许可。
- **OC 二创**：对本模组剧情中的原创角色（OC）绫苒、青岚进行二次创作，需事先获得书面许可。

许可申请方式：在本仓库 [GitHub Issues](https://github.com/PasterDreamReborn-Organization/PasterDream-Reborn-forge1.20.1/issues) 提出申请，或直接联系版权所有者。在获得明确书面答复前，请勿实施上述行为。

### 3.1 附属模组授权名单与留档

- **名单登记为必要条件**：附属模组必须由版权所有者或其授权维护者登记于[官方仓库](https://github.com/PasterDreamReborn-Organization/PasterDream-Reborn-forge1.20.1)维护的[已授权附属模组名单](AUTHORIZED_ADDONS.md)。**未列入该名单的附属模组，视为未取得有效授权**，即使持有单独的授权答复或授权文件，也不能替代名单登记。
- **授权文件留档可选**：授权书、聊天记录、邮件等证明材料可按需留档或在名单中附链接，不强制提交或公开；未附授权文件不影响已获书面许可且列入名单的附属模组的授权效力，也不免除事先取得书面许可的要求。
- **团队成员同样适用**：由本项目团队成员制作的附属模组也必须列入名单，团队成员身份不能替代授权登记。
- **授权范围**：授权仅适用于名单中明确标识的附属项目及获准范围。列入名单不豁免本协议其他条款，也不自动授予美术资源的使用权；美术资源仍遵循 [LICENSE_ASSETS.md](src/main/resources/LICENSE_ASSETS.md)。

## 4. 内容规范

以本模组为题材或载体发布的一切内容（包括但不限于附属模组、视频、图文等），均须遵守以下规范：

- **内容限制**：禁止出现任何引人不适的内容，包括但不限于LGBTQ、血腥、暴力、猎奇、成人或性暗示内容，以及其他包含任何 R-18G 要素的内容等。

## 5. 保留权利

本协议未明确授予的一切权利均归版权所有者所有。

## 6. 署名与版权声明

允许的复制与分发行为必须完整保留本协议全文、版权声明及出处信息，不得移除或篡改。

## 7. 协议终止

若违反本协议任何条款，本协议授予的许可将自动终止。终止后，相关方必须停止使用与分发，并删除其持有或控制的本项目副本（法律另有强制要求的情形除外）。终止不影响版权所有者追索违约责任的权利。

## 8. 免责声明

本项目按"现状"提供，不附带任何明示或默示的担保，包括但不限于适销性、特定用途适用性及不侵权担保。在任何情况下，版权所有者不对因使用或无法使用本项目而导致的任何直接、间接、偶然或后果性损害承担责任。

---

## English Version

This license applies to the **source code and documentation** of this project, as well as to **use and distribution of the mod, addon creation, and publication of related content**. Copyright in, and permission to copy, distribute, or modify, art assets are governed separately by [LICENSE_ASSETS.md](src/main/resources/LICENSE_ASSETS.md) (All Rights Reserved; copyright belongs to the original author "Aerolite_Dust" and the rewrite team). However, Section 2 (prohibited conduct), Section 4 (content regulations), and the other provisions governing use also apply to publication of content involving those art assets.

### 0. Definitions

- **This mod**: the official releases of PasterDream: Reborn and their source code, documentation, and assets.
- **Public API**: the interfaces this mod explicitly exposes for third-party use, including classes and methods listed in official documentation, events, registration extension points, script interfaces such as KubeJS, and data pack/resource pack formats. **Anything not listed in official documentation or marked public in the code is not Public API.**
- **Addon**: any mod, plugin, data pack, resource pack, or script that is loaded and run together with Minecraft and this mod and that extends, modifies, or depends on this mod's content, mechanics, data, or implementation at runtime. **Whether it copies this mod's code or assets does not affect its classification as an addon.**
- **Independent program**: standalone software that is not intended to extend or modify this mod, is not loaded together with Minecraft or this mod, and accesses this mod only through Public API (e.g., external data queries, build tooling).
- **Order of precedence**: Section 3 prevails over Section 1. Any work meeting the definition of an addon is governed exclusively by Section 3 and is not exempted from prior written permission or registration by the "independent programs" grant in Section 1.

### 1. Granted Rights (no prior permission required)

Subject to full compliance with this license:

- **Download and play**: install and use the mod.
- **Modpack inclusion**: distribute **unmodified official releases** within modpacks (credit appreciated).
- **Personal study**: read, analyze, or decompile the code for personal learning and research.
- **Independent programs**: create independent programs that meet the definition in Section 0 and depend on the mod only through Public API. **This clause does not apply to any addon.**

### 2. Absolutely Prohibited (no permission will be granted)

The following are **prohibited under all circumstances**; the copyright holder will not grant permission:

- **Commercial use**: selling the mod itself or profiting from it in any way (directly selling the mod is also prohibited by the Minecraft EULA). Including unmodified official releases in modpacks is not considered commercial use.
- **Name use**: using "PasterDream: Reborn", "Paster Dream: Reborn", or "帕斯特之梦：重生" (or similar) for any content unrelated to this mod or other PasterDream: Reborn ports.
- **Modpack charges**: completely free modpacks may use this mod; charging any fees, direct or disguised, for the official release version of a modpack that includes this mod is prohibited.

### 3. Requires Prior Written Permission

The following **require prior written permission** from the copyright holder; without it, they are prohibited:

- **Addon mods**: creating or distributing addons that extend or build upon this mod, including copying its code, assets, or modifying its mechanics. **This applies even if the addon is implemented solely through Public API and copies no code or assets.** In addition to prior written permission, addons must meet the registry requirement in Section 3.1 below.
- **Ports**: porting the mod (or any part of it) to other Minecraft versions or mod loaders (Forge, NeoForge, Fabric, Quilt, etc.).
- **Modified redistribution**: redistributing modified versions of the code is prohibited, including so-called "fixed" or "improved" versions. For gameplay extensions or similar purposes, please implement them as **addon mods** and modify behavior at runtime via Mixin or similar techniques — **do not modify the mod itself**. Publishing resource packs or shader packs (which may contain translation files, item/block textures, etc.) requires prior written permission from the copyright holder.
- **Map sales**: selling maps, worlds, or other creations that use, include, or feature this mod requires prior written permission.
- **OC derivative works**: creating derivative works of the original characters (OCs) 绫苒 and 青岚 in this mod's story requires prior written permission.

To request permission, open an issue on the [GitHub repository](https://github.com/PasterDreamReborn-Organization/PasterDream-Reborn-forge1.20.1/issues) or contact the copyright holder. Do not proceed until you receive an explicit written reply.

#### 3.1 Authorized Addon Registry and Optional Records

- **Registration is mandatory**: an addon must be entered by the copyright holder or an authorized maintainer in the [Authorized Addons list](AUTHORIZED_ADDONS.md) maintained in the [official repository](https://github.com/PasterDreamReborn-Organization/PasterDream-Reborn-forge1.20.1). **An addon absent from that list is deemed to have no valid authorization**, even if it has a separate permission reply or authorization document; such materials do not replace registration.
- **Archiving authorization documents is optional**: permission letters, chat records, emails, or other supporting materials may be archived or linked from the list, but their submission or publication is not required. The absence of an attached authorization document does not invalidate an addon that has written permission and is listed, nor does it waive the requirement to obtain prior written permission.
- **Team members are subject to the same rule**: addons created by members of this project's team must also be listed; team membership does not replace authorization registration.
- **Scope of authorization**: permission applies only to the addon project identified in the list and its approved scope. Listing does not waive any other license terms or automatically grant rights to use art assets, which remain subject to [LICENSE_ASSETS.md](src/main/resources/LICENSE_ASSETS.md).

### 4. Content Regulations

All content published using this mod as its subject or medium (including but not limited to addon mods, videos, livestreams, recorded gameplay, articles, and images) must comply with the following:

- **Content restrictions**: no content that may cause discomfort is permitted, including but not limited to LGBTQ, graphic gore, extreme grotesque content, adult content including sexual innuendo, or any other graphic R-18 content.

### 5. Reserved Rights

All rights not expressly granted by this license are reserved by the copyright holder.

### 6. Attribution

Any permitted copying or redistribution must retain this license in full, together with the copyright notice and source attribution, without removal or alteration.

### 7. Termination

Any violation of this license automatically terminates the permissions granted herein. Upon termination, you must cease use and distribution and delete all copies of the project in your possession or control (except where required by law). Termination does not affect the copyright holder's right to pursue liability for the breach.

### 8. Disclaimer

THIS PROJECT IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY ARISING FROM OR IN CONNECTION WITH THE USE OF OR INABILITY TO USE THIS PROJECT.