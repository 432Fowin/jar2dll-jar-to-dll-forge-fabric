# 📝 Changelog / История изменений

## 🎯 Version 1.0.0 - Initial Release / Версия 1.0.0 - Первый релиз

### ✨ New Features / Новые функции

**English**:
- **Universal Mod Support**: Full support for Fabric and Forge mods
- **Automatic Framework Detection**: Smart detection of mod type from metadata
- **Intelligent Build System**: Auto-detection of Visual Studio installations
- **One-Click Conversion**: Simple `convert.bat` script for easy use
- **Multi-Language Documentation**: Complete English and Russian documentation
- **JNI Integration**: Seamless Java-C++ interoperability through JNI
- **Memory Safe Injection**: Advanced memory management and error handling
- **Debug Support**: Comprehensive logging and error reporting

**Русский**:
- **Универсальная поддержка модов**: Полная поддержка модов Fabric и Forge
- **Автоматическое определение фреймворка**: Умное определение типа мода из метаданных
- **Интеллектуальная система сборки**: Автоопределение установок Visual Studio
- **Конвертация в один клик**: Простой скрипт `convert.bat` для легкого использования
- **Многоязычная документация**: Полная документация на английском и русском языках
- **Интеграция JNI**: Бесшовное взаимодействие Java-C++ через JNI
- **Безопасная инжекция памяти**: Продвинутое управление памятью и обработка ошибок
- **Поддержка отладки**: Комплексное логирование и отчеты об ошибках

### 🔧 Technical Implementation / Техническая реализация

**English**:
- **C++ DLL Core**: Native Windows DLL with JNI interface
- **Java Analysis Engine**: Bytecode analysis and conversion tools
- **CMake Build System**: Cross-compatible build configuration
- **Gradle Integration**: Java component compilation and management
- **Framework-Specific Injectors**: Specialized injection logic for each mod framework

**Русский**:
- **Ядро C++ DLL**: Нативная Windows DLL с JNI интерфейсом
- **Движок анализа Java**: Инструменты анализа и конвертации байт-кода
- **Система сборки CMake**: Кроссплатформенная конфигурация сборки
- **Интеграция Gradle**: Компиляция и управление Java компонентами
- **Инжекторы для конкретных фреймворков**: Специализированная логика инжекции для каждого фреймворка модов

### 🎮 Supported Mod Frameworks / Поддерживаемые фреймворки модов

**English**:
- ✅ **Fabric** (1.16.5 - 1.20.x) - Full support with `fabric.mod.json` detection
- ✅ **Forge Modern** (1.16.5 - 1.20.x) - Full support with `META-INF/mods.toml` detection
- ✅ **Forge Legacy** (1.12.2 - 1.16.4) - Full support with `mcmod.info` detection
- ⚠️ **Quilt** (1.18.x - 1.20.x) - Experimental support

**Русский**:
- ✅ **Fabric** (1.16.5 - 1.20.x) - Полная поддержка с определением `fabric.mod.json`
- ✅ **Forge Современный** (1.16.5 - 1.20.x) - Полная поддержка с определением `META-INF/mods.toml`
- ✅ **Forge Устаревший** (1.12.2 - 1.16.4) - Полная поддержка с определением `mcmod.info`
- ⚠️ **Quilt** (1.18.x - 1.20.x) - Экспериментальная поддержка

### 📁 Project Structure / Структура проекта

**English**:
- **Clean Architecture**: Organized source code structure
- **Conversion Workspace**: Dedicated `convert/` folder for user operations
- **Comprehensive Documentation**: Multiple README files for different audiences
- **Screenshot Gallery**: Visual proof of functionality in `shots/` folder
- **Developer Resources**: Contributing guidelines and project structure documentation

**Русский**:
- **Чистая архитектура**: Организованная структура исходного кода
- **Рабочая область конвертации**: Выделенная папка `convert/` для пользовательских операций
- **Комплексная документация**: Множество README файлов для разных аудиторий
- **Галерея скриншотов**: Визуальное доказательство функциональности в папке `shots/`
- **Ресурсы для разработчиков**: Руководства по участию и документация структуры проекта

### 🛠️ System Requirements / Системные требования

**English**:
- **Operating System**: Windows 10/11 (64-bit)
- **Development Environment**: Visual Studio 2019/2022 with C++ tools
- **Runtime**: Java 8+ (JDK or JRE)
- **Build Tools**: CMake 3.10+
- **Target**: Minecraft 1.16.5 - 1.20.x with corresponding mod loaders

**Русский**:
- **Операционная система**: Windows 10/11 (64-bit)
- **Среда разработки**: Visual Studio 2019/2022 с инструментами C++
- **Среда выполнения**: Java 8+ (JDK или JRE)
- **Инструменты сборки**: CMake 3.10+
- **Цель**: Minecraft 1.16.5 - 1.20.x с соответствующими загрузчиками модов

### 🔍 Tested Mods / Протестированные моды

**English**:
Successfully tested with popular mods including:
- Fabric API, Sodium, Lithium, Iris Shaders, JEI
- Biomes O' Plenty, Tinkers' Construct, Applied Energistics 2
- And many more community favorites

**Русский**:
Успешно протестировано с популярными модами, включая:
- Fabric API, Sodium, Lithium, Iris Shaders, JEI
- Biomes O' Plenty, Tinkers' Construct, Applied Energistics 2
- И многие другие любимые моды сообщества

### 📚 Documentation / Документация

**English**:
- **README.md**: Main documentation with language selection
- **README_EN.md**: Complete English documentation
- **README_RU.md**: Complete Russian documentation
- **FAQ.md**: Frequently asked questions
- **CONTRIBUTING.md**: Developer contribution guide
- **PROJECT_STRUCTURE.md**: Technical project overview

**Русский**:
- **README.md**: Основная документация с выбором языка
- **README_EN.md**: Полная документация на английском
- **README_RU.md**: Полная документация на русском
- **FAQ.md**: Часто задаваемые вопросы
- **CONTRIBUTING.md**: Руководство для разработчиков
- **PROJECT_STRUCTURE.md**: Технический обзор проекта

---

## 🚀 Future Roadmap / Планы на будущее

### Version 1.1.0 (Planned) / Версия 1.1.0 (Планируется)
- **NeoForge Support**: Full support for NeoForge mods
- **Batch Processing**: Convert multiple mods at once
- **GUI Interface**: User-friendly graphical interface
- **Dependency Resolution**: Automatic handling of mod dependencies

### Version 1.2.0 (Planned) / Версия 1.2.0 (Планируется)
- **Cross-Platform Support**: Linux and macOS compatibility
- **Performance Optimizations**: Faster conversion and injection
- **Plugin System**: Extensible architecture for custom injectors
- **Mod Pack Support**: Direct conversion of mod pack files

---

**Release Date / Дата релиза**: 2025-01-14
**Stability / Стабильность**: Stable / Стабильная
**License / Лицензия**: MIT with additional terms / MIT с дополнительными условиями
