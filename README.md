# Jar2DLL - Minecraft Mod to DLL Converter

## 🎯 What is Jar2DLL? / 🎯 Что такое Jar2DLL?

**English**: Jar2DLL is a revolutionary tool that converts Minecraft mod JAR files into injectable Windows DLL files. This breakthrough technology allows you to load mods dynamically into a running Minecraft process without requiring pre-installed mod loaders like Fabric Loader or Forge.

**Русский**: Jar2DLL - это революционный инструмент, который конвертирует JAR файлы модов Minecraft в инжектируемые DLL файлы Windows. Эта прорывная технология позволяет динамически загружать моды в запущенный процесс Minecraft без необходимости предустановленных загрузчиков модов, таких как Fabric Loader или Forge.

## 🚀 Why Use Jar2DLL? / 🚀 Зачем использовать Jar2DLL?

**English**:
- **No Mod Loader Required**: Inject mods directly into vanilla Minecraft
- **Universal Compatibility**: Works with both Fabric and Forge mods
- **Runtime Loading**: Add mods to already running Minecraft instances
- **Stealth Operation**: Mods appear as native game features
- **Development Freedom**: Test mods without modifying game installation

**Русский**:
- **Не требует загрузчика модов**: Инжектируйте моды прямо в ванильный Minecraft
- **Универсальная совместимость**: Работает с модами Fabric и Forge
- **Загрузка во время выполнения**: Добавляйте моды в уже запущенные экземпляры Minecraft
- **Скрытая работа**: Моды выглядят как нативные функции игры
- **Свобода разработки**: Тестируйте моды без изменения установки игры

## ✨ Key Features / ✨ Ключевые особенности

**English**:
- 🔄 **Universal Mod Support**: Fabric, Forge, and legacy mod formats
- 🤖 **Intelligent Detection**: Automatically identifies mod framework and version
- 🚀 **Runtime Injection**: Load mods into running Minecraft processes
- 🔧 **Smart Build System**: Auto-detects Visual Studio installations
- 🎯 **JNI Integration**: Seamless Java-C++ interoperability
- 📦 **One-Click Conversion**: Simple drag-and-drop workflow
- 🛡️ **Memory Safe**: Advanced memory management and error handling
- 🔍 **Debug Support**: Comprehensive logging and error reporting

**Русский**:
- 🔄 **Универсальная поддержка модов**: Fabric, Forge и устаревшие форматы модов
- 🤖 **Интеллектуальное определение**: Автоматически определяет фреймворк и версию мода
- 🚀 **Инжекция во время выполнения**: Загружайте моды в запущенные процессы Minecraft
- 🔧 **Умная система сборки**: Автоматически обнаруживает установки Visual Studio
- 🎯 **Интеграция JNI**: Бесшовное взаимодействие Java-C++
- 📦 **Конвертация в один клик**: Простой рабочий процесс перетаскивания
- 🛡️ **Безопасность памяти**: Продвинутое управление памятью и обработка ошибок
- 🔍 **Поддержка отладки**: Комплексное логирование и отчеты об ошибках

## 🔧 Technical Architecture / 🔧 Техническая архитектура

**English**:
Jar2DLL uses a sophisticated multi-stage conversion process:

1. **JAR Analysis Engine**: Scans mod metadata, dependencies, and class structure
2. **Framework Detection**: Identifies Fabric/Forge through signature analysis
3. **Bytecode Converter**: Transforms Java classes into C++ byte arrays
4. **Native Compiler**: Builds optimized DLL with embedded mod data
5. **JNI Injector**: Runtime injection into target JVM process
6. **ClassLoader Integration**: Seamless integration with mod framework loaders

**Русский**:
Jar2DLL использует сложный многоэтапный процесс конвертации:

1. **Движок анализа JAR**: Сканирует метаданные мода, зависимости и структуру классов
2. **Определение фреймворка**: Идентифицирует Fabric/Forge через анализ сигнатур
3. **Конвертер байт-кода**: Преобразует Java классы в C++ массивы байтов
4. **Нативный компилятор**: Собирает оптимизированную DLL с встроенными данными мода
5. **JNI инжектор**: Инжекция во время выполнения в целевой процесс JVM
6. **Интеграция ClassLoader**: Бесшовная интеграция с загрузчиками фреймворка модов

## 🚀 Quick Start Guide / 🚀 Руководство быстрого старта

### Prerequisites / Предварительные требования

**English**:
- Windows 10/11 (64-bit)
- [Visual Studio 2022 Community](https://visualstudio.microsoft.com/vs/community/) (free, with C++ tools)
- [Java 8+](https://adoptium.net/) (JDK or JRE)
- [CMake 3.10+](https://cmake.org/download/) (added to PATH)

**Русский**:
- Windows 10/11 (64-bit)
- [Visual Studio 2022 Community](https://visualstudio.microsoft.com/vs/community/) (бесплатно, с инструментами C++)
- [Java 8+](https://adoptium.net/) (JDK или JRE)
- [CMake 3.10+](https://cmake.org/download/) (добавлен в PATH)

### Step-by-Step Conversion / Пошаговая конвертация

**English**:
1. **Prepare Your Mod**:
   - Download any Minecraft mod (`.jar` file)
   - Place it in the `convert/` folder
   - Rename to exactly `input.jar`

2. **Run Conversion**:
   - Double-click `convert/convert.bat`
   - Wait for automatic build process
   - Find `output.dll` in convert folder

3. **Inject and Play**:
   - Use Process Hacker or similar DLL injector
   - Inject `output.dll` into Minecraft process
   - Enjoy your mod!

**Русский**:
1. **Подготовьте ваш мод**:
   - Скачайте любой мод Minecraft (файл `.jar`)
   - Поместите его в папку `convert/`
   - Переименуйте точно в `input.jar`

2. **Запустите конвертацию**:
   - Дважды щелкните `convert/convert.bat`
   - Дождитесь автоматического процесса сборки
   - Найдите `output.dll` в папке convert

3. **Инжектируйте и играйте**:
   - Используйте Process Hacker или аналогичный DLL инжектор
   - Инжектируйте `output.dll` в процесс Minecraft
   - Наслаждайтесь вашим модом!

## 📁 Project Structure / 📁 Структура проекта

**English**:
```
jar2dll/
├── convert/               # 🔄 Conversion workspace
│   ├── convert.bat       # 🚀 Main conversion script
│   ├── input.jar         # 📥 Your mod JAR (place here)
│   └── output.dll        # 📤 Generated DLL
├── src/main/
│   ├── cpp/              # 🔧 C++ DLL source code
│   │   ├── src/          # Implementation files
│   │   ├── include/      # Header files and JNI
│   │   └── CMakeLists.txt # Build configuration
│   └── java/com/jar2dll/ # ☕ Java components
│       ├── injectors/    # Framework-specific injectors
│       └── tools/        # Analysis and conversion tools
├── shots/                # 📸 Screenshots and demos
└── docs/                 # 📚 Documentation files
```

**Русский**:
```
jar2dll/
├── convert/               # 🔄 Рабочая область конвертации
│   ├── convert.bat       # 🚀 Основной скрипт конвертации
│   ├── input.jar         # 📥 Ваш JAR мода (поместите сюда)
│   └── output.dll        # 📤 Сгенерированная DLL
├── src/main/
│   ├── cpp/              # 🔧 Исходный код C++ DLL
│   │   ├── src/          # Файлы реализации
│   │   ├── include/      # Заголовочные файлы и JNI
│   │   └── CMakeLists.txt # Конфигурация сборки
│   └── java/com/jar2dll/ # ☕ Java компоненты
│       ├── injectors/    # Инжекторы для конкретных фреймворков
│       └── tools/        # Инструменты анализа и конвертации
├── shots/                # 📸 Скриншоты и демонстрации
└── docs/                 # 📚 Файлы документации
```

## 🔍 Supported Mod Types / 🔍 Поддерживаемые типы модов

**English**:

| Mod Framework | Metadata File | Minecraft Versions | Status |
|---------------|---------------|-------------------|--------|
| **Fabric** | `fabric.mod.json` | 1.16.5 - 1.20.x | ✅ Full Support |
| **Forge (Modern)** | `META-INF/mods.toml` | 1.16.5 - 1.20.x | ✅ Full Support |
| **Forge (Legacy)** | `mcmod.info` | 1.12.2 - 1.16.4 | ✅ Full Support |
| **Quilt** | `quilt.mod.json` | 1.18.x - 1.20.x | ⚠️ Experimental |
| **NeoForge** | `META-INF/neoforge.mods.toml` | 1.20.x+ | 🔄 In Development |

**Русский**:

| Фреймворк мода | Файл метаданных | Версии Minecraft | Статус |
|----------------|-----------------|------------------|--------|
| **Fabric** | `fabric.mod.json` | 1.16.5 - 1.20.x | ✅ Полная поддержка |
| **Forge (Современный)** | `META-INF/mods.toml` | 1.16.5 - 1.20.x | ✅ Полная поддержка |
| **Forge (Устаревший)** | `mcmod.info` | 1.12.2 - 1.16.4 | ✅ Полная поддержка |
| **Quilt** | `quilt.mod.json` | 1.18.x - 1.20.x | ⚠️ Экспериментально |
| **NeoForge** | `META-INF/neoforge.mods.toml` | 1.20.x+ | 🔄 В разработке |

## 🛠️ Advanced Features / 🛠️ Продвинутые функции

**English**:
- **Dependency Resolution**: Automatically handles mod dependencies
- **Version Compatibility**: Smart version matching for Minecraft/mod compatibility
- **Memory Optimization**: Efficient memory usage during injection
- **Error Recovery**: Graceful handling of injection failures
- **Debug Mode**: Detailed logging for troubleshooting
- **Batch Processing**: Convert multiple mods (planned feature)
- **GUI Interface**: User-friendly graphical interface (planned)

**Русский**:
- **Разрешение зависимостей**: Автоматически обрабатывает зависимости модов
- **Совместимость версий**: Умное сопоставление версий для совместимости Minecraft/мод
- **Оптимизация памяти**: Эффективное использование памяти во время инжекции
- **Восстановление после ошибок**: Корректная обработка сбоев инжекции
- **Режим отладки**: Подробное логирование для устранения неполадок
- **Пакетная обработка**: Конвертация нескольких модов (планируемая функция)
- **GUI интерфейс**: Удобный графический интерфейс (планируется)

## 🎮 Popular Mods Tested / 🎮 Популярные протестированные моды

**English**:

### Fabric Mods ✅
- **Fabric API** - Core Fabric library
- **Sodium** - Performance optimization
- **Lithium** - Server performance
- **Iris Shaders** - Shader support
- **JEI (Just Enough Items)** - Recipe viewer
- **Mod Menu** - Mod configuration
- **REI (Roughly Enough Items)** - Alternative recipe viewer

### Forge Mods ✅
- **JEI (Just Enough Items)** - Recipe and item management
- **Biomes O' Plenty** - World generation
- **Tinkers' Construct** - Tool crafting system
- **Applied Energistics 2** - Storage and automation
- **Thermal Expansion** - Technology and machinery
- **Botania** - Magic and nature mod
- **Iron Chests** - Enhanced storage

**Русский**:

### Моды Fabric ✅
- **Fabric API** - Основная библиотека Fabric
- **Sodium** - Оптимизация производительности
- **Lithium** - Производительность сервера
- **Iris Shaders** - Поддержка шейдеров
- **JEI (Just Enough Items)** - Просмотрщик рецептов
- **Mod Menu** - Конфигурация модов
- **REI (Roughly Enough Items)** - Альтернативный просмотрщик рецептов

### Моды Forge ✅
- **JEI (Just Enough Items)** - Управление рецептами и предметами
- **Biomes O' Plenty** - Генерация мира
- **Tinkers' Construct** - Система создания инструментов
- **Applied Energistics 2** - Хранение и автоматизация
- **Thermal Expansion** - Технологии и механизмы
- **Botania** - Мод магии и природы
- **Iron Chests** - Улучшенное хранение

## 📸 Screenshots / 📸 Скриншоты

**English**: Visual proof of successful mod conversions and injections.

**Русский**: Визуальное доказательство успешных конвертаций и инжекций модов.

![Fabric Mod Demo](shots/converted%20mod%20demonstration%20fabric.jpg)
*Fabric mod successfully injected and running*

![Forge Mod Demo](shots/converted%20mod%20demostration%20forge.jpg)
*Forge mod successfully injected and running*

![Injection Proof](shots/proof%20inject%20work.jpg)
*DLL injection process verification*

## 📚 Documentation / 📚 Документация

**English**:
- [📖 Complete English Guide](README_EN.md) - Detailed documentation
- [❓ FAQ](FAQ.md) - Frequently asked questions
- [📖 Conversion Workspace Guide](convert/README.md) - Convert folder instructions
- [📖 Project Structure](PROJECT_STRUCTURE.md) - Technical overview
- [📖 Contributing Guide](CONTRIBUTING.md) - For developers

**Русский**:
- [📖 Полное русское руководство](README_RU.md) - Подробная документация
- [❓ FAQ](FAQ.md) - Часто задаваемые вопросы
- [📖 Руководство по рабочей области](convert/README.md) - Инструкции для папки convert
- [📖 Структура проекта](PROJECT_STRUCTURE.md) - Технический обзор
- [📖 Руководство по участию](CONTRIBUTING.md) - Для разработчиков

## 🤝 Community / 🤝 Сообщество

**English**:
- **Issues**: Report bugs and request features
- **Discussions**: Ask questions and share experiences
- **Pull Requests**: Contribute code improvements
- **Wiki**: Community-maintained documentation

**Русский**:
- **Issues**: Сообщайте об ошибках и запрашивайте функции
- **Discussions**: Задавайте вопросы и делитесь опытом
- **Pull Requests**: Вносите улучшения в код
- **Wiki**: Документация, поддерживаемая сообществом

## ⚠️ Important Notes / ⚠️ Важные замечания

**English**:
- This tool is for **educational and research purposes only**
- Ensure compliance with Minecraft's Terms of Service
- Respect mod authors' licenses and permissions
- Use responsibly in multiplayer environments
- Some antivirus software may flag DLLs as suspicious (false positive)

**Русский**:
- Этот инструмент предназначен **только для образовательных и исследовательских целей**
- Обеспечьте соблюдение Условий использования Minecraft
- Уважайте лицензии и разрешения авторов модов
- Используйте ответственно в многопользовательских средах
- Некоторые антивирусы могут помечать DLL как подозрительные (ложное срабатывание)

## 📄 License / 📄 Лицензия

**English**: This project is licensed under the MIT License with additional terms for responsible use. See [LICENSE](LICENSE) for details.

**Русский**: Этот проект лицензирован под лицензией MIT с дополнительными условиями для ответственного использования. См. [LICENSE](LICENSE) для подробностей.

---

**Made with ❤️ for the Minecraft modding community / Сделано с ❤️ для сообщества моддинга Minecraft**
