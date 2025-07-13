# 📁 Jar2DLL Project Structure

## 🎯 Overview

This document describes the final, clean structure of the Jar2DLL project after reorganization.

## 📂 Directory Structure

```
jar2dll/
├── 📄 README.md                    # Main documentation with language selection
├── 📄 README_EN.md                 # Detailed English documentation  
├── 📄 README_RU.md                 # Detailed Russian documentation
├── 📄 LICENSE                      # MIT License with additional terms
├── 📄 PROJECT_STRUCTURE.md         # This file
├── 📄 .gitignore                   # Git ignore rules
├── 📄 build.gradle                 # Gradle build configuration
├── 📄 settings.gradle              # Gradle settings
├── 📄 gradlew                      # Gradle wrapper (Unix)
├── 📄 gradlew.bat                  # Gradle wrapper (Windows)
│
├── 📁 gradle/                      # Gradle wrapper files
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
│
├── 📁 convert/                     # 🔄 CONVERSION WORKSPACE
│   ├── 📄 README.md               # Conversion guide
│   ├── 🚀 convert.bat             # Main conversion script
│   ├── 📥 input.jar               # Your mod JAR (place here)
│   └── 📤 output.dll              # Generated DLL (after conversion)
│
├── 📁 shots/                       # 📸 Screenshots
│   ├── 📄 README.md               # Screenshot descriptions
│   ├── 🖼️ converted mod demonstration fabric.jpg
│   ├── 🖼️ converted mod demostration forge.jpg
│   └── 🖼️ proof inject work.jpg
│
└── 📁 src/main/                    # 💻 Source code
    ├── cpp/                        # C++ DLL source
    │   ├── 📄 CMakeLists.txt      # CMake build configuration
    │   ├── include/               # Header files
    │   │   ├── 📄 injector.h     # Main injector header
    │   │   └── jvm/              # JNI headers
    │   │       ├── 📄 jni.h
    │   │       ├── 📄 jni_md.h
    │   │       └── 📄 jvmti.h
    │   ├── src/                   # Source files
    │   │   ├── 📄 main.cpp       # DLL entry point
    │   │   └── 📄 injector.cpp   # Injection logic
    │   └── stub_classes/          # Stub headers for development
    │       ├── 📄 injector.h
    │       └── 📄 jar.h
    │
    └── java/com/jar2dll/          # Java source
        ├── injectors/             # Mod injectors
        │   ├── 📄 FabricInjector.java
        │   └── 📄 ForgeInjector.java
        └── tools/                 # Build tools
            ├── 📄 HeaderConverter.java
            └── 📄 ModTypeDetector.java
```

## 🎯 Key Folders

### 📁 `convert/` - Your Workspace
- **Purpose**: Main conversion workspace
- **Usage**: Place your mod JAR here as `input.jar`, run `convert.bat`
- **Output**: Get `output.dll` ready for injection

### 📁 `src/main/cpp/` - C++ DLL Core
- **Purpose**: Native DLL source code
- **Components**: JNI interface, injection logic, CMake build
- **Output**: Compiled into injectable DLL

### 📁 `src/main/java/` - Java Components
- **Purpose**: Mod analysis and injection logic
- **Components**: Framework-specific injectors, build tools
- **Output**: Compiled to support DLL generation

### 📁 `shots/` - Documentation
- **Purpose**: Visual proof and documentation
- **Contents**: Screenshots of successful conversions
- **Usage**: Reference for troubleshooting

## 🚀 Workflow

1. **Place mod** → `convert/input.jar`
2. **Run script** → `convert/convert.bat`
3. **Get DLL** → `convert/output.dll`
4. **Inject** → Use DLL injector on Minecraft

## 🔧 Build Process

The conversion script automatically:
1. **Detects** Visual Studio installation
2. **Compiles** Java components with Gradle
3. **Analyzes** mod type (Fabric/Forge)
4. **Converts** bytecode to C++ headers
5. **Builds** native DLL with CMake
6. **Outputs** ready-to-inject DLL

## 📝 Documentation Levels

1. **README.md** - Quick overview with language selection
2. **README_EN.md** - Complete English documentation
3. **README_RU.md** - Complete Russian documentation
4. **convert/README.md** - Conversion workspace guide
5. **shots/README.md** - Screenshot descriptions

## 🎯 Design Principles

- ✅ **Simplicity**: One-click conversion process
- ✅ **Clarity**: Clear folder structure and naming
- ✅ **Automation**: Auto-detection of tools and dependencies
- ✅ **Documentation**: Comprehensive multi-language docs
- ✅ **Separation**: Clean separation of concerns
- ✅ **User-friendly**: Intuitive workflow for end users

## 🔄 Git Workflow

The `.gitignore` is configured to:
- ✅ Include source code and documentation
- ❌ Exclude build artifacts and temporary files
- ❌ Exclude user's input/output files in `convert/`
- ❌ Exclude IDE-specific files

This ensures a clean repository focused on the core functionality.
