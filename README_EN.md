# Jar2DLL - Complete English Documentation

## 🎯 Overview

Jar2DLL is an advanced tool designed to convert Minecraft mod JAR files into injectable Windows DLL files. This revolutionary approach allows you to load mods dynamically into a running Minecraft process without requiring pre-installed mod loaders like Fabric Loader or Forge.

## 🔧 Technical Architecture

### Core Components

1. **Java Analysis Engine** (`src/main/java/com/jar2dll/tools/`)
   - `ModTypeDetector.java`: Automatically detects Fabric/Forge mods by analyzing metadata
   - `HeaderConverter.java`: Converts Java bytecode to C++ header files

2. **Injection Framework** (`src/main/java/com/jar2dll/injectors/`)
   - `FabricInjector.java`: Specialized injector for Fabric mods
   - `ForgeInjector.java`: Specialized injector for Forge mods

3. **Native DLL Core** (`src/main/cpp/`)
   - `main.cpp`: DLL entry point and thread management
   - `injector.cpp`: JNI interface and mod loading logic
   - `include/`: JNI headers and interfaces

### How It Works

1. **JAR Analysis Phase**:
   - Scans JAR file for `fabric.mod.json`, `META-INF/mods.toml`, or `mcmod.info`
   - Analyzes class files to determine mod framework
   - Extracts mod metadata and dependencies

2. **Bytecode Conversion Phase**:
   - Converts Java class files to C++ byte arrays
   - Generates header files with embedded mod data
   - Creates framework-specific injector headers

3. **Native Compilation Phase**:
   - Compiles C++ DLL with embedded mod data
   - Links against JNI libraries
   - Optimizes for runtime injection

4. **Runtime Injection Phase**:
   - DLL attaches to target Minecraft process
   - Locates JVM instance through process memory
   - Injects mod classes into appropriate ClassLoader
   - Initializes mod according to framework requirements

## 🚀 Installation & Setup

### Prerequisites

1. **Windows 10/11** (64-bit)
2. **Visual Studio 2022 Community** (free)
   - Download: https://visualstudio.microsoft.com/vs/community/
   - Required workload: "Desktop development with C++"
   - Components needed: MSVC v143, Windows 10/11 SDK, CMake tools

3. **Java Development Kit 8+**
   - Recommended: Eclipse Temurin (https://adoptium.net/)
   - Alternative: Oracle JDK, OpenJDK

4. **CMake 3.10+**
   - Download: https://cmake.org/download/
   - ⚠️ **Important**: Check "Add CMake to system PATH" during installation

### Verification

Open Command Prompt and verify installations:

```cmd
java -version
javac -version
cmake --version
cl
```

All commands should return version information without errors.

## 📦 Usage Guide

### Step 1: Prepare Your Mod

1. Download your desired Minecraft mod (`.jar` file)
2. Navigate to the `convert/` folder in the project
3. Copy your mod JAR file into this folder
4. Rename it to exactly `input.jar`

**Supported Mod Types:**
- ✅ Fabric mods (with `fabric.mod.json`)
- ✅ Forge mods (with `META-INF/mods.toml`)
- ✅ Legacy Forge mods (with `mcmod.info`)
- ⚠️ Quilt mods (experimental support)

### Step 2: Run Conversion

1. Double-click `convert/convert.bat`
2. The script will automatically:
   - Detect your Visual Studio installation
   - Verify Java and CMake
   - Compile Java components
   - Analyze your mod type
   - Convert bytecode to C++ headers
   - Build the native DLL

### Step 3: Inject the DLL

**Option A: Using Process Hacker (Recommended)**
1. Download Process Hacker: https://processhacker.sourceforge.io/
2. Run as Administrator
3. Find `javaw.exe` or `java.exe` (Minecraft process)
4. Right-click → Miscellaneous → Inject DLL
5. Select `convert/output.dll`

**Option B: Using Cheat Engine**
1. Open Cheat Engine
2. Select Minecraft process
3. Memory View → Tools → Inject DLL
4. Choose `convert/output.dll`

**Option C: Using DLL Injector Tools**
- Extreme Injector
- Xenos Injector
- Manual DLL injection tools

## 🔍 Troubleshooting

### Common Issues

**❌ "Visual Studio not found"**
- Ensure VS 2022/2019 is installed with C++ tools
- Check installation path: `C:\Program Files\Microsoft Visual Studio\`
- Reinstall with "Desktop development with C++" workload

**❌ "Java not found"**
- Add Java to system PATH
- Verify with `java -version` in Command Prompt
- Restart Command Prompt after PATH changes

**❌ "CMake not found"**
- Reinstall CMake with "Add to PATH" option
- Manually add to PATH: `C:\Program Files\CMake\bin`

**❌ "Failed to compile Java components"**
- Check Gradle wrapper: `gradlew.bat --version`
- Ensure Java 8+ is installed
- Try manual compilation: `gradlew.bat clean compileInjectors compileTools`

**❌ "Mod type detection failed"**
- Verify JAR file is a valid Minecraft mod
- Check file isn't corrupted
- Ensure mod supports your Minecraft version

**❌ "DLL injection failed"**
- Run injector as Administrator
- Ensure Minecraft is running with correct mod loader
- Check antivirus isn't blocking the DLL
- Verify target process architecture (x64)

### Debug Mode

For detailed error information, edit `convert/convert.bat`:
1. Remove `>nul 2>&1` from failing commands
2. Add `pause` after error points
3. Run script to see full error output

## 🎮 Minecraft Integration

### Fabric Mods
- Requires Fabric Loader to be installed in Minecraft
- DLL injects into Fabric's ClassLoader
- Mod initializes through Fabric's mod loading system
- Compatible with Fabric API dependencies

### Forge Mods
- Requires Minecraft Forge to be installed
- DLL injects into Forge's ModClassLoader
- Registers with Forge's event bus system
- Supports Forge mod lifecycle events

### Version Compatibility
- Minecraft 1.16.5 - 1.20.x
- Fabric Loader 0.14.x+
- Minecraft Forge 36.x+ (1.16.5+)

## 🛡️ Security Considerations

### Antivirus Detection
Some antivirus software may flag the generated DLL as suspicious due to:
- DLL injection techniques
- JNI memory manipulation
- Dynamic code loading

**Solutions:**
- Add project folder to antivirus exclusions
- Use Windows Defender (generally more permissive)
- Submit false positive reports to antivirus vendors

### Safe Usage
- Only use with mods from trusted sources
- Verify mod integrity before conversion
- Don't inject into online servers without permission
- Respect game terms of service

## 🔬 Advanced Usage

### Custom Build Configuration

Edit `build.gradle` for custom Java compilation:
```gradle
compileJava {
    options.compilerArgs += ['-Xlint:unchecked', '-Xlint:deprecation']
    options.encoding = 'UTF-8'
}
```

### Manual Compilation Steps

For development or debugging:

```cmd
# Compile Java components
gradlew.bat compileInjectors compileTools

# Detect mod type
java -cp build/tools tools.git.fowin.ModTypeDetector convert/input.jar

# Convert headers
java -cp build/tools tools.git.fowin.HeaderConverter injector [injector-class] src/main/cpp/classes/injector.h
java -cp build/tools tools.git.fowin.HeaderConverter input-jar convert/input.jar src/main/cpp/classes/jar.h

# Build DLL
mkdir build/cmake
cd build/cmake
cmake ../../src/main/cpp -G "Visual Studio 17 2022" -A x64
cmake --build . --config Release
```

## 📊 Performance Considerations

### Memory Usage
- DLL size: ~50-200KB (depending on mod size)
- Runtime memory: +10-50MB (mod dependent)
- Injection time: <1 second

### Compatibility
- Works with most single-player scenarios
- Limited multiplayer compatibility (server-side mods)
- Performance impact: Minimal (<5% overhead)

## 🤝 Contributing

### Development Setup
1. Fork the repository
2. Set up development environment
3. Make changes to source code
4. Test with various mod types
5. Submit pull request

### Code Structure
- Follow existing code style
- Add comments for complex logic
- Test with both Fabric and Forge mods
- Update documentation for new features

## 📄 License

This project is licensed under the MIT License. See LICENSE file for details.

## ⚠️ Legal Disclaimer

This tool is for educational and research purposes only. Users are responsible for:
- Complying with Minecraft's Terms of Service
- Respecting mod authors' licenses
- Following applicable laws and regulations
- Using responsibly in multiplayer environments

The developers are not responsible for any misuse of this tool.
