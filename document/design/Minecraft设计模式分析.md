# Minecraft 1.20.1 软件设计模式分析

> 基于 Minecraft 1.20.1 (mojmap) 反编译源码分析整理

---

## 一、创建型模式 (Creational Patterns)

### 1.1 Singleton (单例模式)

**核心思想：** 确保一个类只有一个实例，并提供全局访问点。

**典型应用：**

| 类 | 说明 |
|---|------|
| `Minecraft` | 游戏客户端主类，全局单例，通过 `Minecraft.getInstance()` 访问 |
| `BuiltInRegistries` | 内置注册表集合，所有方块、物品、实体类型等注册表的集中持有者 |
| `GameRenderer` | 游戏渲染器单例 |
| `SoundManager` | 音效管理器单例 |

**为什么使用：** 游戏引擎的核心子系统（渲染、音效、注册表）在整个生命周期内只需一份实例，保证状态一致性和全局可访问性。

---

### 1.2 Builder (建造者模式)

**核心思想：** 将复杂对象的构造过程与其表示分离，支持链式调用。

**典型应用：**

| 类 | 说明 |
|---|------|
| `BufferBuilder` | 顶点缓冲区构建，逐步添加顶点数据后 `end()` 生成 `BufferUploader` |
| `ShapedRecipeBuilder` / `ShapelessRecipeBuilder` | JSON配方数据生成，通过 `.pattern()` `.define()` `.unlockedBy()` 链式构建 |
| `CubeListBuilder` | 实体模型的立方体列表构建器 |
| `RegistrySetBuilder` | 注册表集合的声明式构建，用于数据生成 |
| `ChatReportBuilder` | 聊天举报数据的逐步构建 |
| `ChatReportContextBuilder` | 聊天举报上下文的构建 |

**为什么使用：** Minecraft 数据生成(data generation)系统大量使用 Builder 模式，使得 JSON 配方的代码生成可读性好且不易出错。

---

### 1.3 Factory (工厂模式)

**核心思想：** 定义创建对象的接口，由子类决定实例化哪个类。

**典型应用：**

| 类 | 说明 |
|---|------|
| `ChunkProgressListenerFactory` | 创建不同类型的世界加载进度监听器 |
| `NamedThreadFactory` | 创建带自定义名称的线程，便于调试 |
| `GsonAdapterFactory` | 战利品表条件的 Gson 类型适配器工厂 |
| `LowerCaseEnumTypeAdapterFactory` | 将枚举序列化为小写字符串的 Gson 适配器工厂 |
| `PositionalRandomFactory` | 基于位置的随机数生成器工厂（用于世界生成） |

**为什么使用：** 将对象创建逻辑集中管理，客户端/服务端可以注入不同的工厂实现。

---

### 1.4 Prototype (原型模式)

**核心思想：** 通过克隆原型实例来创建新对象。

**典型应用：**

| 类 | 说明 |
|---|------|
| `EntityType` | 通过内置的 `Supplier<Entity>` 工厂创建实体实例（本质是原型克隆） |
| `BlockEntityType` | 通过 `BlockEntityType.BlockEntitySupplier` 创建方块实体 |

**为什么使用：** 实体和方块实体需要频繁创建/销毁，使用工厂方法结合注册表可以实现高效的按类型创建。

---

### 1.5 Object Pool (对象池模式)

**核心思想：** 复用已创建的对象，避免频繁创建/销毁开销。

**典型应用：**

| 类 | 说明 |
|---|------|
| `ChunkBufferBuilderPack` | 区块渲染缓冲区构建器的对象池 |
| 网络包缓冲区 | 网络包字节缓冲区的池化复用 |

**为什么使用：** 渲染和网络是性能热点，对象池避免了 GC 压力和内存分配开销。

---

## 二、结构型模式 (Structural Patterns)

### 2.1 Registry / Service Locator (注册表 / 服务定位器)

**核心思想：** 通过集中注册表查找服务/对象，而非硬编码依赖。

**典型应用：**

| 类 | 说明 |
|---|------|
| `Registry<T>` | 泛型注册表接口 |
| `MappedRegistry<T>` | 基于 Map 的注册表实现，支持按 ID 和名称查找 |
| `DefaultedRegistry<T>` | 带默认值的注册表 |
| `DefaultedMappedRegistry<T>` | 两者结合的注册表 |
| `RegistryAccess` | 多层注册表访问入口 |
| `LayeredRegistryAccess` | 支持世界/数据包层的注册表访问 |
| `BuiltInRegistries` | 所有内置注册表的持有者（`BLOCK`、`ITEM`、`ENTITY_TYPE` 等） |

**为什么使用：** 这是 Minecraft 最核心的架构模式之一。一切内容（方块、物品、实体、附魔、药水效果、生物群系等）都通过注册表管理，支持数据包动态覆盖和模组扩展。

---

### 2.2 Flyweight (享元模式)

**核心思想：** 共享大量细粒度对象以节省内存。

**典型应用一：`BlockState`**

`StateDefinition<O, S>` 为每种方块预生成所有可能的状态组合（如方向、含水、充能等属性的笛卡尔积），每个状态都是一个不可变的享元对象，在整个世界的所有区块中共享引用。

**典型应用二：`ResourceLocation`**

资源标识符（命名空间:路径）在内部使用字符串池化，避免在成千上万的方块状态、模型引用中存储重复字符串。

**为什么使用：** 一个世界可能有数百万个方块，如果每个方块都独立存储完整状态对象，内存将迅速耗尽。享元模式使得所有相同状态的方块共享同一个 `BlockState` 实例。

---

### 2.3 Adapter (适配器模式)

**核心思想：** 将一个接口转换成客户端期望的另一个接口。

**典型应用：**

| 类 | 说明 |
|---|------|
| `LevelEntityGetterAdapter` | 将不同 Level 实现适配为统一的实体获取接口 |
| `ProfilerSamplerAdapter` | 性能采样器数据格式适配 |
| `GsonAdapterFactory` | 为战利品表条件序列化提供自定义 Gson 适配器 |
| `LowerCaseEnumTypeAdapterFactory` | 枚举 -> 小写字符串的 Gson 适配器 |

**为什么使用：** 将第三方库（Gson、性能分析器）的接口适配为 Minecraft 内部需要的格式。

---

### 2.4 Decorator (装饰器模式)

**核心思想：** 动态地给对象添加额外的职责。

**典型应用：**

| 类 | 说明 |
|---|------|
| `TreeDecorator` | 树木装饰器抽象基类 |
| `CocoaDecorator` | 给树木添加可可豆 |
| `BeehiveDecorator` | 给树木添加蜂巢 |
| `AlterGroundDecorator` | 修改树木周围地面 |
| `AttachedToLeavesDecorator` | 在树叶上附加藤蔓等 |
| `LeaveVineDecorator` | 树叶藤蔓装饰 |
| `TrunkVineDecorator` | 树干藤蔓装饰 |
| `ChatDecorator` | 聊天消息装饰器 |

**为什么使用：** 树木生成时，基础树形由 `TreeFeature` 生成，而可可豆、蜂巢、藤蔓等附加特性通过装饰器链在树生成后叠加，使得每种附加特性独立且可组合。

---

### 2.5 Composite (组合模式)

**核心思想：** 将对象组合成树形结构以表示"部分-整体"层次结构。

**典型应用一：GUI 组件系统**

GUI 容器可以包含子组件，形成嵌套的组件树。事件沿树传播，渲染按树遍历。

**典型应用二：聊天文本组件**

`Component` / `MutableComponent` 可包含子 `Component`，构成文本样式树（颜色、粗体、点击事件、悬浮事件等嵌套应用）。

**为什么使用：** 组合模式使 GUI 布局和富文本渲染可以采用统一的递归算法处理任意深度的嵌套。

---

### 2.6 Facade (外观模式)

**核心思想：** 为子系统中的一组接口提供一个统一的高层接口。

**典型应用：**

| 类 | 说明 |
|---|------|
| `GlStateManager` | 封装复杂的 OpenGL 状态管理调用链 |
| `Minecraft` | 游戏引擎的核心门面，整合渲染、输入、网络、资源管理等所有子系统 |

**为什么使用：** OpenGL 原始 API 非常冗长且易出错，`GlStateManager` 将其封装为语义化的 Java 方法调用。

---

### 2.7 Proxy (代理模式)

**核心思想：** 为另一个对象提供替身以控制对其的访问。

**典型应用：**

| 场景 | 说明 |
|---|------|
| 实体同步 | 服务端持有"真实"实体，客户端持有"代理"实体（`RemoteEntity`/同步实体），客户端通过追踪数据操作代理 |
| `BlockStatePredictionHandler` | 客户端预测方块状态变化的代理处理器 |
| 惰性加载 | 区块、资源等按需从磁盘或网络加载的代理 |

**为什么使用：** C/S 架构下客户端不能直接操作服务端对象，代理模式提供了透明的远程访问机制。惰性代理则减少了启动时的加载压力。

---

## 三、行为型模式 (Behavioral Patterns)

### 3.1 State (状态模式)

**核心思想：** 允许对象在内部状态改变时改变其行为。

**典型应用：`BlockState` 系统**

| 类 | 说明 |
|---|------|
| `StateDefinition<O, S>` | 定义某个方块类型的所有可能状态 |
| `StateHolder<O, S>` | 持有具体属性值的状态实例 |
| `BlockBehaviour.BlockStateBase` | 方块状态的基类，所有行为方法根据当前状态属性改变行为 |
| `Property<?>` | 状态属性（`BooleanProperty`、`IntegerProperty`、`EnumProperty`、`DirectionProperty` 等） |

每个方块类型在初始化时，`StateDefinition` 会生成所有属性的笛卡尔积，将所有可能的状态预计算为不可变的 `BlockState` 实例。方块的碰撞箱、光照、红石信号等行为都取决于当前 `BlockState`。

**为什么使用：** 避免了大量 if-else 判断属性值，每种状态组合都是独立的对象，行为内聚在状态对象内部。

---

### 3.2 Observer / Listener (观察者/监听器模式)

**核心思想：** 定义对象间的一对多依赖，当一个对象状态改变时，所有依赖者自动收到通知。

**典型应用：**

| 类/接口 | 说明 |
|---|------|
| `GuiEventListener` | GUI 事件监听器接口（鼠标点击、键盘输入、焦点变化） |
| `ChatListener` | 聊天消息接收监听 |
| `ClientPacketListener` | 客户端网络包监听器（处理服务端发来的各种数据包） |
| `ServerGamePacketListenerImpl` | 服务端网络包监听器（处理客户端发来的各种数据包） |
| `RecipeUpdateListener` | 配方书数据更新监听 |
| `StatsUpdateListener` | 统计信息更新监听 |
| `RecipeShownListener` | 配方显示通知监听 |
| `SpectatorMenuListener` | 旁观者模式菜单监听 |

**为什么使用：** Minecraft 是事件驱动的游戏，GUI 交互、网络通信、状态同步都依赖观察者模式进行松耦合通信。

---

### 3.3 Command (命令模式)

**核心思想：** 将请求封装为对象，支持参数化、队列化、日志化和撤销操作。

**典型应用：**

| 类 | 说明 |
|---|------|
| `Commands` | 命令注册和调度中心 |
| `CommandDispatcher` | Brigadier 命令调度器（Mojang 的命令解析框架） |
| `CommandSourceStack` | 命令执行上下文（执行者、位置、权限级别） |
| `CommandFunction` | 函数式命令（数据包中的 .mcfunction 文件） |

每个 `/` 命令都是一个 Command 对象，Brigadier 将其解析、验证参数、然后在 `CommandSourceStack` 上下文中执行。

**为什么使用：** 命令模式天然支持参数解析、权限检查、命令方块链式执行和数据包函数系统。

---

### 3.4 Visitor (访问者模式)

**核心思想：** 将算法与对象结构分离，在不修改对象结构的前提下添加新操作。

**典型应用：NBT 序列化系统**

| 类 | 说明 |
|---|------|
| `TagVisitor` | NBT 标签访问者接口 |
| `StreamTagVisitor` | 流式 NBT 访问者（二进制格式序列化） |
| `SnbtPrinterTagVisitor` | SNBT 格式打印访问者（人类可读的[逗号分隔]NBT格式） |
| `StringTagVisitor` | 字符串转换访问者 |
| `TextComponentTagVisitor` | 文本组件 NBT 序列化访问者 |

**为什么使用：** NBT 数据格式需要在多种表示之间转换（二进制网络传输、磁盘存储、SNBT 调试输出、JSON 数据包），访问者模式允许在不修改 Tag 类层次结构的前提下添加新的序列化方式。

---

### 3.5 Strategy (策略模式)

**核心思想：** 定义一系列算法，将每个算法封装起来并使它们可以相互替换。

**典型应用：**

| 类 | 说明 |
|---|------|
| `RequirementsStrategy` | 进度要求检查策略（AND/OR 组合逻辑） |
| `RepeatedDelayStrategy` | Realms 重复请求的延迟策略（固定/递增等待） |
| `PositionalRandomFactory` | 基于世界坐标的随机数生成策略 |
| 战利品表条件 (`LootItemCondition`) | 各种战利品生成条件的策略实现 |
| AI 目标 (`Goal`) | 实体 AI 行为的不同策略 |

**为什么使用：** 策略模式使得 AI 行为、进度条件、世界生成规则等可以灵活组合和替换，支持数据包自定义。

---

### 3.6 Template Method (模板方法模式)

**核心思想：** 在父类中定义算法骨架，将某些步骤延迟到子类实现。

**典型应用：**

| 基类 | 说明 |
|---|------|
| `AbstractContainerMenu` | 定义容器菜单的数据槽同步、快速移动等算法骨架 |
| `AbstractFurnaceBlockEntity` | 定义熔炉类方块实体的烧炼逻辑骨架（`canSmelt()`、`getBurnDuration()` 由子类覆盖） |
| `AbstractBlock` | 定义方块的行为骨架（碰撞检测、渲染形状、交互逻辑等钩子方法） |
| `AbstractArrow` | 定义弓箭/投掷物的运动、碰撞检测骨架 |

**为什么使用：** Minecraft 有大量"同类变体"（各种熔炉、各种容器、各种方块），模板方法确保了核心行为一致性的同时允许子类定制差异部分。

---

### 3.7 Chain of Responsibility (责任链模式)

**核心思想：** 将请求沿处理者链传递，直到某个处理者处理它。

**典型应用：DataFixer 系统**

| 类 | 说明 |
|---|------|
| `DataFixers` | 数据修复器注册中心 |
| `DataFixTypes` | 定义不同类型数据的修复链 |
| `BlendingDataFix` | 混合数据修复 |
| `SpawnerDataFix` | 刷怪笼数据修复 |
| `VillagerDataFix` | 村民数据修复 |
| `ChunkDeleteIgnoredLightDataFix` | 区块光照数据修复 |
| `MemoryExpiryDataFix` | 记忆过期数据修复 |

当加载旧版本存档时，数据依次经过版本号从小到大的 DataFix 链逐级转换，直至当前版本。

**为什么使用：** Minecraft 版本迭代频繁，存档需要向前兼容。责任链模式使每个版本的迁移规则独立且可复用，新版本只需在链尾追加新的 DataFix。

---

### 3.8 Memento (备忘录模式)

**核心思想：** 在不破坏封装的前提下捕获对象内部状态，以便后续恢复。

**典型应用：**

| 场景 | 说明 |
|---|------|
| 世界存档 | 世界状态序列化为 NBT 快照保存到磁盘，加载时从 NBT 恢复 |
| `PlayerData` | 玩家数据（背包、末影箱、进度、生命值等）的保存与恢复 |
| 区块持久化 | 区块数据从内存序列化到 region 文件，加载时反序列化恢复 |

**为什么使用：** 游戏必须支持存档/读档，NBT 格式天然适合备忘录模式的序列化存储。

---

## 四、架构级模式 (Architectural Patterns)

### 4.1 Data-Driven Design (数据驱动设计)

Minecraft 大量使用 JSON 数据文件驱动的设计方式：

- **配方 (recipes)** — 通过 JSON 定义而非硬编码
- **战利品表 (loot tables)** — 方块掉落和宝箱内容的数据驱动
- **进度 (advancements)** — 进度触发器与条件的数据定义
- **标签 (tags)** — 动态分组机制
- **世界生成配置** — 生物群系、结构、地物等的 JSON 配置
- **Codec / MapCodec** — Mojang 的序列化框架，支持类型安全的（反）序列化

**为什么使用：** 数据驱动的设计使数据包(datapack)系统成为可能，玩家和模组作者无需修改代码即可定制游戏内容。

---

### 4.2 Client-Server Architecture (C/S 架构)

Minecraft 1.20.1 严格分离为：

- **逻辑服务端 (`server/`)** — 世界状态、游戏逻辑、存档管理
- **逻辑客户端 (`client/`)** — 渲染、输入、GUI、音效
- **共用 (`common/`)** — 网络协议、注册表、NBT、方块/物品定义

即使是单人游戏也运行内置服务端，确保单人/多人行为一致性。

---

### 4.3 ECS-Lite (实体-组件-系统 变体)

不是纯 ECS，但包含其核心思想：

- **Entity** — 所有游戏对象的基类（`Entity`）
- **Component** — 通过字段和辅助类附加数据（`EntityData`、`Inventory`、`MobEffect`）
- **System** — 系统化处理（`tick()` 更新系统、AI 系统、渲染系统、碰撞系统）

---

### 4.4 Immutability (不可变性模式)

| 类 | 说明 |
|---|------|
| `BlockState` | 所有可能状态预计算为不可变实例，全局共享 |
| `ResourceLocation` | 不可变资源标识符 |
| `ItemStack`（部分不可变） | 核心属性使用不可变设计 |

不可变对象天然线程安全、可缓存、可共享，提高了并发安全性和性能。

---

## 总结

| 分类 | 设计模式 | 使用范围 |
|------|---------|---------|
| 创建型 | Singleton | 核心子系统（游戏实例、渲染器、音效） |
| 创建型 | Builder | 数据生成（配方、模型、注册表） |
| 创建型 | Factory | 对象创建（线程、进度、适配器） |
| 创建型 | Prototype | 实体/方块实体创建 |
| 创建型 | Object Pool | 渲染缓冲区、网络包 |
| 结构型 | Registry/Service Locator | **核心架构** — 一切游戏内容的注册与查找 |
| 结构型 | Flyweight | **核心架构** — BlockState、ResourceLocation |
| 结构型 | Adapter | 第三方库接口适配 |
| 结构型 | Decorator | 树木生成装饰链、聊天装饰 |
| 结构型 | Composite | GUI 组件树、文本组件树 |
| 结构型 | Facade | OpenGL 状态管理封装 |
| 结构型 | Proxy | 客户端-服务端实体代理 |
| 行为型 | State | **核心架构** — BlockState 系统 |
| 行为型 | Observer/Listener | GUI 事件、网络包处理、状态监听 |
| 行为型 | Command | 命令系统和 Brigadier 框架 |
| 行为型 | Visitor | NBT 序列化/反序列化 |
| 行为型 | Strategy | AI 目标、进度条件、战利品条件 |
| 行为型 | Template Method | 容器菜单、熔炉、方块基类 |
| 行为型 | Chain of Responsibility | DataFixer 存档版本升级 |
| 行为型 | Memento | 世界存档/读档 |
| 架构级 | Data-Driven Design | 配方、战利品、进度、世界生成 |
| 架构级 | C/S Architecture | 单人/多人统一架构 |
| 架构级 | ECS-Lite | 实体系统设计 |
| 架构级 | Immutability | BlockState、ResourceLocation |

---

> 共计约 **25 种** 设计模式，涵盖 GoF 创建型、结构型、行为型三大类，以及 Minecraft 特有的游戏架构模式。
