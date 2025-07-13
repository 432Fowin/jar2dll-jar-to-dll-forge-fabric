# 🤝 Contributing to Jar2DLL

## 🎯 Welcome Contributors!

Thank you for your interest in contributing to Jar2DLL! This project aims to make Minecraft mod injection accessible and reliable.

## 🚀 Quick Start for Developers

### Prerequisites
- Windows 10/11
- Visual Studio 2022 with C++ tools
- Java 8+
- CMake 3.10+
- Git

### Setup Development Environment

1. **Fork and Clone**
   ```bash
   git clone https://github.com/yourusername/jar2dll.git
   cd jar2dll
   ```

2. **Test the Build**
   ```bash
   # Test Java compilation
   gradlew.bat compileInjectors compileTools
   
   # Test with a sample mod
   # Place a mod as convert/input.jar
   convert/convert.bat
   ```

## 📁 Project Structure

```
src/main/
├── cpp/           # C++ DLL core
│   ├── src/       # Implementation files
│   ├── include/   # Header files
│   └── CMakeLists.txt
└── java/com/jar2dll/
    ├── injectors/ # Framework-specific injectors
    └── tools/     # Build and analysis tools
```

## 🔧 Development Areas

### 1. Java Injectors (`src/main/java/com/jar2dll/injectors/`)
- **FabricInjector.java**: Handles Fabric mod injection
- **ForgeInjector.java**: Handles Forge mod injection
- **Improvements needed**: Better error handling, more injection methods

### 2. Analysis Tools (`src/main/java/com/jar2dll/tools/`)
- **ModTypeDetector.java**: Detects mod framework type
- **HeaderConverter.java**: Converts bytecode to C++ headers
- **Improvements needed**: Support for more mod types, better metadata parsing

### 3. C++ Core (`src/main/cpp/`)
- **main.cpp**: DLL entry point
- **injector.cpp**: JNI interface and injection logic
- **Improvements needed**: Better error handling, memory management

### 4. Build System
- **convert.bat**: Main conversion script
- **build.gradle**: Java build configuration
- **CMakeLists.txt**: C++ build configuration

## 🎯 Contribution Guidelines

### Code Style

**Java:**
- Use 4 spaces for indentation
- Follow standard Java naming conventions
- Add JavaDoc comments for public methods
- Handle exceptions gracefully

**C++:**
- Use 2 spaces for indentation
- Follow Google C++ Style Guide
- Use RAII for resource management
- Add comments for complex logic

**Batch Scripts:**
- Use clear variable names
- Add comments for each major step
- Provide user-friendly error messages

### Testing

Before submitting a PR:

1. **Test with multiple mod types**:
   - Fabric mods (simple and complex)
   - Forge mods (modern and legacy)
   - Different Minecraft versions

2. **Test build process**:
   - Clean environment test
   - Different Visual Studio versions
   - Various Java versions

3. **Test injection**:
   - Different DLL injectors
   - Various Minecraft launchers
   - Both single-player and compatible multiplayer scenarios

### Pull Request Process

1. **Create Feature Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make Changes**
   - Follow code style guidelines
   - Add appropriate tests
   - Update documentation if needed

3. **Test Thoroughly**
   - Test with multiple mods
   - Verify build process works
   - Check injection functionality

4. **Submit PR**
   - Clear description of changes
   - Reference any related issues
   - Include test results

## 🐛 Bug Reports

When reporting bugs, please include:

1. **Environment**:
   - Windows version
   - Visual Studio version
   - Java version
   - CMake version

2. **Mod Information**:
   - Mod name and version
   - Mod type (Fabric/Forge)
   - Minecraft version

3. **Error Details**:
   - Full error messages
   - Steps to reproduce
   - Expected vs actual behavior

4. **Logs**:
   - Build output (if build fails)
   - Injection logs (if injection fails)

## 💡 Feature Requests

We welcome feature requests! Consider:

### High Priority
- Support for more mod frameworks (Quilt, NeoForge)
- Better error messages and debugging
- GUI interface for conversion
- Automatic mod dependency resolution

### Medium Priority
- Support for mod packs
- Integration with popular launchers
- Cross-platform support (Linux, macOS)
- Performance optimizations

### Low Priority
- Plugin system for custom injectors
- Mod compatibility database
- Automated testing framework

## 📚 Documentation

Help improve documentation:

1. **README files**: Keep them up-to-date and clear
2. **Code comments**: Explain complex logic
3. **User guides**: Add more examples and troubleshooting
4. **Screenshots**: Update with new features

## 🔒 Security Considerations

When contributing:

1. **No malicious code**: All code is reviewed for security
2. **Safe practices**: Follow secure coding guidelines
3. **Responsible disclosure**: Report security issues privately
4. **User safety**: Consider impact on user systems

## 🌍 Internationalization

We support multiple languages:

- **English**: Primary language for code and main docs
- **Russian**: Full documentation translation
- **Other languages**: Contributions welcome!

## 📞 Getting Help

- **Issues**: Use GitHub Issues for bugs and features
- **Discussions**: Use GitHub Discussions for questions
- **Documentation**: Check README files first

## 🎉 Recognition

Contributors will be:
- Listed in project credits
- Mentioned in release notes
- Given appropriate GitHub repository permissions

Thank you for helping make Jar2DLL better! 🚀
