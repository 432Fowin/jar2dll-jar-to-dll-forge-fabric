# 🔄 Convert Folder - Conversion Workspace / 🔄 Папка Convert - Рабочая область конвертации

## 📁 What is this folder? / 📁 Что это за папка?

**English**: This is your **conversion workspace** where you place Minecraft mod JAR files to convert them into injectable DLL files.

**Русский**: Это ваша **рабочая область конвертации**, где вы размещаете JAR файлы модов Minecraft для конвертации их в инжектируемые DLL файлы.

## 🚀 Quick Start / 🚀 Быстрый старт

### Step 1: Place Your Mod / Шаг 1: Разместите ваш мод

**English**:
1. Download any Minecraft mod (`.jar` file)
2. Copy it to this folder
3. **Rename it to `input.jar`** (exactly this name!)

**Русский**:
1. Скачайте любой мод Minecraft (файл `.jar`)
2. Скопируйте его в эту папку
3. **Переименуйте в `input.jar`** (точно это имя!)

### Step 2: Run Conversion / Шаг 2: Запустите конвертацию

**English**:
1. Double-click `convert.bat`
2. Wait for the process to complete
3. Find your `output.dll` in this folder

**Русский**:
1. Дважды щелкните `convert.bat`
2. Дождитесь завершения процесса
3. Найдите ваш `output.dll` в этой папке

### Step 3: Inject DLL / Шаг 3: Инжектируйте DLL

**English**: Use any DLL injector to inject `output.dll` into Minecraft process.

**Русский**: Используйте любой DLL инжектор для инжекции `output.dll` в процесс Minecraft.

## 📋 Supported Mod Types / 📋 Поддерживаемые типы модов

**English**:

| Mod Type | Metadata File | Status |
|----------|---------------|--------|
| Fabric | `fabric.mod.json` | ✅ Fully Supported |
| Forge (Modern) | `META-INF/mods.toml` | ✅ Fully Supported |
| Forge (Legacy) | `mcmod.info` | ✅ Fully Supported |
| Quilt | `quilt.mod.json` | ⚠️ Experimental |

**Русский**:

| Тип мода | Файл метаданных | Статус |
|----------|-----------------|--------|
| Fabric | `fabric.mod.json` | ✅ Полная поддержка |
| Forge (Современный) | `META-INF/mods.toml` | ✅ Полная поддержка |
| Forge (Устаревший) | `mcmod.info` | ✅ Полная поддержка |
| Quilt | `quilt.mod.json` | ⚠️ Экспериментально |

## 🔍 Example Mods to Test / 🔍 Примеры модов для тестирования

### Fabric Mods / Моды Fabric
- Fabric API
- Sodium
- Lithium
- Iris Shaders
- JEI (Just Enough Items)

### Forge Mods / Моды Forge
- JEI (Just Enough Items)
- Optifine (as Forge mod)
- Biomes O' Plenty
- Tinkers' Construct
- Applied Energistics 2

## 📊 Conversion Process / 📊 Процесс конвертации

**English**:
```
input.jar → [Analysis] → [Conversion] → [Compilation] → output.dll
    ↓           ↓            ↓             ↓
  Your Mod   Detect Type   Java→C++    Native DLL
```

**Русский**:
```
input.jar → [Анализ] → [Конвертация] → [Компиляция] → output.dll
    ↓          ↓           ↓              ↓
  Ваш мод   Определение  Java→C++    Нативная DLL
            типа
```

## 📸 Screenshots / 📸 Скриншоты

**English**: Visual proof of successful mod conversions and injections.

**Русский**: Визуальное доказательство успешных конвертаций и инжекций модов.

![Fabric Mod Demo](../shots/converted%20mod%20demonstration%20fabric.jpg)
*Fabric mod successfully converted and injected / Мод Fabric успешно конвертирован и инжектирован*

![Forge Mod Demo](../shots/converted%20mod%20demostration%20forge.jpg)
*Forge mod successfully converted and injected / Мод Forge успешно конвертирован и инжектирован*

![Injection Proof](../shots/proof%20inject%20work.jpg)
*DLL injection process verification / Проверка процесса инжекции DLL*

## 🛠️ Files in this folder / 🛠️ Файлы в этой папке

**English**:
- `convert.bat` - Main conversion script
- `input.jar` - Your mod JAR file (place here)
- `output.dll` - Generated DLL (after conversion)
- `README.md` - This file

**Русский**:
- `convert.bat` - Основной скрипт конвертации
- `input.jar` - Ваш JAR файл мода (поместите сюда)
- `output.dll` - Сгенерированная DLL (после конвертации)
- `README.md` - Этот файл

## ⚠️ Important Notes / ⚠️ Важные замечания

**English**:
1. **File Naming**: Your mod MUST be named exactly `input.jar`
2. **One at a time**: Convert one mod at a time
3. **Backup**: Keep original mod files as backup
4. **Antivirus**: Add this folder to antivirus exclusions if needed

**Русский**:
1. **Именование файлов**: Ваш мод ДОЛЖЕН называться точно `input.jar`
2. **По одному**: Конвертируйте по одному моду за раз
3. **Резервная копия**: Сохраняйте оригинальные файлы модов как резервную копию
4. **Антивирус**: Добавьте эту папку в исключения антивируса при необходимости

## 🔧 Troubleshooting / 🔧 Устранение неполадок

### "input.jar not found" / "input.jar не найден"

**English**:
- Make sure your mod file is named exactly `input.jar`
- Check the file is in this folder (`convert/`)

**Русский**:
- Убедитесь, что ваш файл мода называется точно `input.jar`
- Проверьте, что файл находится в этой папке (`convert/`)

### "Conversion failed" / "Конвертация не удалась"

**English**:
- Ensure the JAR file is a valid Minecraft mod
- Check if the mod is compatible with your Minecraft version
- Try with a different mod to test the system

**Русский**:
- Убедитесь, что JAR файл является действительным модом Minecraft
- Проверьте совместимость мода с вашей версией Minecraft
- Попробуйте с другим модом для тестирования системы

### "DLL injection failed" / "Инжекция DLL не удалась"

**English**:
- Run your DLL injector as Administrator
- Make sure Minecraft is running with the correct mod loader
- Verify the target process is 64-bit

**Русский**:
- Запустите ваш DLL инжектор от имени Администратора
- Убедитесь, что Minecraft запущен с правильным загрузчиком модов
- Проверьте, что целевой процесс 64-битный

## 📞 Need Help? / 📞 Нужна помощь?

**English**: Check the main documentation:
- [📖 Main README](../README.md) - Complete bilingual guide
- [📖 English Documentation](../README_EN.md)
- [📖 FAQ](../FAQ.md) - Frequently asked questions

**Русский**: Проверьте основную документацию:
- [📖 Основной README](../README.md) - Полное билингвальное руководство
- [📖 Русская документация](../README_RU.md)
- [📖 FAQ](../FAQ.md) - Часто задаваемые вопросы

## 🎯 Pro Tips / 🎯 Профессиональные советы

**English**:
1. **Test with simple mods first** (like Fabric API)
2. **Use Process Hacker** for reliable DLL injection
3. **Run as Administrator** when injecting
4. **Check mod compatibility** with your Minecraft version
5. **Keep antivirus exclusions** for this folder

**Русский**:
1. **Сначала тестируйте с простыми модами** (например, Fabric API)
2. **Используйте Process Hacker** для надежной инжекции DLL
3. **Запускайте от имени Администратора** при инжекции
4. **Проверяйте совместимость мода** с вашей версией Minecraft
5. **Сохраняйте исключения антивируса** для этой папки

---

**Made with ❤️ for the Minecraft modding community / Сделано с ❤️ для сообщества моддинга Minecraft**
