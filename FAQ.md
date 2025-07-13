# ❓ Frequently Asked Questions / Часто задаваемые вопросы

## 🎯 General Questions / Общие вопросы

### Q: What is the difference between Jar2DLL and traditional mod loaders? / В: В чем разница между Jar2DLL и традиционными загрузчиками модов?

**English**: Traditional mod loaders (Fabric Loader, Forge) require installation and modify the game startup process. Jar2DLL converts mods into injectable DLLs that can be loaded into an already running Minecraft process without any game modification.

**Русский**: Традиционные загрузчики модов (Fabric Loader, Forge) требуют установки и изменяют процесс запуска игры. Jar2DLL конвертирует моды в инжектируемые DLL, которые можно загрузить в уже запущенный процесс Minecraft без каких-либо изменений игры.

### Q: Is this safe to use? / В: Безопасно ли это использовать?

**English**: The tool itself is safe and doesn't modify game files. However, DLL injection may trigger antivirus warnings (false positives). Always use mods from trusted sources and comply with game terms of service.

**Русский**: Сам инструмент безопасен и не изменяет файлы игры. Однако инжекция DLL может вызвать предупреждения антивируса (ложные срабатывания). Всегда используйте моды из надежных источников и соблюдайте условия использования игры.

### Q: Can I use this on multiplayer servers? / В: Могу ли я использовать это на многопользовательских серверах?

**English**: Use with extreme caution. Many servers prohibit client-side mods and may ban players for using them. Only use on servers that explicitly allow client mods, or in single-player mode.

**Русский**: Используйте с особой осторожностью. Многие серверы запрещают клиентские моды и могут забанить игроков за их использование. Используйте только на серверах, которые явно разрешают клиентские моды, или в одиночном режиме.

## 🔧 Technical Questions / Технические вопросы

### Q: Why do I need Visual Studio? / В: Зачем мне нужна Visual Studio?

**English**: Visual Studio provides the C++ compiler (MSVC) needed to build the native DLL. The conversion process compiles C++ code that interfaces with the Java Virtual Machine through JNI.

**Русский**: Visual Studio предоставляет компилятор C++ (MSVC), необходимый для сборки нативной DLL. Процесс конвертации компилирует код C++, который взаимодействует с виртуальной машиной Java через JNI.

### Q: Can I use other compilers like GCC or Clang? / В: Могу ли я использовать другие компиляторы, такие как GCC или Clang?

**English**: Currently, the build system is optimized for MSVC (Visual Studio). While technically possible to adapt for other compilers, it would require significant modifications to the CMake configuration.

**Русский**: В настоящее время система сборки оптимизирована для MSVC (Visual Studio). Хотя технически возможно адаптировать для других компиляторов, это потребует значительных изменений в конфигурации CMake.

### Q: What Minecraft versions are supported? / В: Какие версии Minecraft поддерживаются?

**English**: The tool supports Minecraft 1.16.5 through 1.20.x with corresponding mod loader versions. Older versions may work but are not officially tested.

**Русский**: Инструмент поддерживает Minecraft 1.16.5 до 1.20.x с соответствующими версиями загрузчиков модов. Более старые версии могут работать, но официально не тестируются.

## 🐛 Troubleshooting / Устранение неполадок

### Q: "Visual Studio not found" error / В: Ошибка "Visual Studio не найдена"

**English**: 
1. Ensure Visual Studio 2019 or 2022 is installed
2. Install "Desktop development with C++" workload
3. Check installation path: `C:\Program Files\Microsoft Visual Studio\`
4. Try running the script as Administrator

**Русский**:
1. Убедитесь, что установлена Visual Studio 2019 или 2022
2. Установите рабочую нагрузку "Разработка классических приложений на C++"
3. Проверьте путь установки: `C:\Program Files\Microsoft Visual Studio\`
4. Попробуйте запустить скрипт от имени Администратора

### Q: "Java not found" error / В: Ошибка "Java не найдена"

**English**:
1. Install Java 8 or higher from [Adoptium](https://adoptium.net/)
2. Ensure Java is added to system PATH
3. Restart Command Prompt after installation
4. Verify with `java -version` command

**Русский**:
1. Установите Java 8 или выше с [Adoptium](https://adoptium.net/)
2. Убедитесь, что Java добавлена в системный PATH
3. Перезапустите Командную строку после установки
4. Проверьте командой `java -version`

### Q: "CMake not found" error / В: Ошибка "CMake не найден"

**English**:
1. Download CMake from [cmake.org](https://cmake.org/download/)
2. During installation, check "Add CMake to system PATH"
3. If already installed, manually add to PATH: `C:\Program Files\CMake\bin`
4. Restart Command Prompt

**Русский**:
1. Скачайте CMake с [cmake.org](https://cmake.org/download/)
2. При установке отметьте "Добавить CMake в системный PATH"
3. Если уже установлен, вручную добавьте в PATH: `C:\Program Files\CMake\bin`
4. Перезапустите Командную строку

### Q: Antivirus blocks the DLL / В: Антивирус блокирует DLL

**English**:
1. Add the project folder to antivirus exclusions
2. Temporarily disable real-time protection during build
3. Submit false positive report to your antivirus vendor
4. Use Windows Defender (generally more permissive)

**Русский**:
1. Добавьте папку проекта в исключения антивируса
2. Временно отключите защиту в реальном времени во время сборки
3. Отправьте отчет о ложном срабатывании поставщику антивируса
4. Используйте Windows Defender (обычно более лояльный)

### Q: DLL injection fails / В: Инжекция DLL не удается

**English**:
1. Run DLL injector as Administrator
2. Ensure Minecraft is running with correct mod loader
3. Verify target process is 64-bit
4. Check that DLL file exists and isn't corrupted
5. Try different injection method/tool

**Русский**:
1. Запустите DLL инжектор от имени Администратора
2. Убедитесь, что Minecraft запущен с правильным загрузчиком модов
3. Проверьте, что целевой процесс 64-битный
4. Убедитесь, что файл DLL существует и не поврежден
5. Попробуйте другой метод/инструмент инжекции

## 🎮 Mod-Specific Questions / Вопросы по конкретным модам

### Q: My mod doesn't work after injection / В: Мой мод не работает после инжекции

**English**:
1. Verify mod compatibility with your Minecraft version
2. Check if mod requires specific dependencies
3. Ensure correct mod loader is running (Fabric/Forge)
4. Try with a simpler mod first (like Fabric API)
5. Check conversion logs for errors

**Русский**:
1. Проверьте совместимость мода с вашей версией Minecraft
2. Убедитесь, что мод не требует специфических зависимостей
3. Убедитесь, что запущен правильный загрузчик модов (Fabric/Forge)
4. Попробуйте сначала с более простым модом (например, Fabric API)
5. Проверьте логи конвертации на наличие ошибок

### Q: Can I convert mod packs? / В: Могу ли я конвертировать паки модов?

**English**: Currently, the tool converts one mod at a time. For mod packs, you need to convert each mod individually. Batch processing is planned for future releases.

**Русский**: В настоящее время инструмент конвертирует по одному моду за раз. Для паков модов нужно конвертировать каждый мод отдельно. Пакетная обработка планируется в будущих релизах.

### Q: Do converted mods work with original mod loaders? / В: Работают ли конвертированные моды с оригинальными загрузчиками модов?

**English**: Yes! The converted DLL injects into the existing mod loader environment. You still need Fabric Loader for Fabric mods or Forge for Forge mods running in Minecraft.

**Русский**: Да! Конвертированная DLL инжектируется в существующую среду загрузчика модов. Вам все еще нужен Fabric Loader для модов Fabric или Forge для модов Forge, запущенных в Minecraft.

## 🔄 Development Questions / Вопросы разработки

### Q: Can I contribute to the project? / В: Могу ли я внести вклад в проект?

**English**: Absolutely! Check the [CONTRIBUTING.md](CONTRIBUTING.md) guide for development setup and contribution guidelines.

**Русский**: Конечно! Ознакомьтесь с руководством [CONTRIBUTING.md](CONTRIBUTING.md) для настройки разработки и рекомендаций по участию.

### Q: How can I add support for new mod frameworks? / В: Как я могу добавить поддержку новых фреймворков модов?

**English**: You'll need to:
1. Extend `ModTypeDetector.java` to recognize the new framework
2. Create a new injector class in `src/main/java/com/jar2dll/injectors/`
3. Update the conversion script to handle the new type
4. Test thoroughly with mods from the new framework

**Русский**: Вам нужно будет:
1. Расширить `ModTypeDetector.java` для распознавания нового фреймворка
2. Создать новый класс инжектора в `src/main/java/com/jar2dll/injectors/`
3. Обновить скрипт конвертации для обработки нового типа
4. Тщательно протестировать с модами из нового фреймворка

---

**Still have questions? / Остались вопросы?**

**English**: Open an issue on GitHub or check the detailed documentation in [README_EN.md](README_EN.md).

**Русский**: Откройте issue на GitHub или ознакомьтесь с подробной документацией в [README_RU.md](README_RU.md).
