@echo off
setlocal enabledelayedexpansion
chcp 65001 >nul
title JAR to DLL Converter
color 0A

echo ╔══════════════════════════════════════════════════════════════════════════════╗
echo ║                            JAR TO DLL CONVERTER                             ║
echo ║                          Minecraft Mod Converter                            ║
echo ╚══════════════════════════════════════════════════════════════════════════════╝
echo.

REM Check if input.jar exists
if not exist "input.jar" (
    echo [❌ ERROR] input.jar not found!
    echo.
    echo 📁 Please place your Minecraft mod JAR file in this folder and rename it to "input.jar"
    echo.
    echo Example:
    echo   - Download your mod (e.g., fabric-api-0.92.2.jar)
    echo   - Copy it to: %~dp0
    echo   - Rename to: input.jar
    echo.
    pause
    exit /b 1
)
echo [✅ INFO] Found input.jar

REM Auto-detect Visual Studio installation
echo.
echo [🔍 STEP 1/6] Searching for Visual Studio...
set "VS_PATH="
set "VS_VERSION="

REM Check for VS 2022
if exist "C:\Program Files\Microsoft Visual Studio\2022\Community\Common7\Tools\VsDevCmd.bat" (
    set "VS_PATH=C:\Program Files\Microsoft Visual Studio\2022\Community\Common7\Tools\VsDevCmd.bat"
    set "VS_VERSION=2022 Community"
    goto :vs_found
)
if exist "C:\Program Files\Microsoft Visual Studio\2022\Professional\Common7\Tools\VsDevCmd.bat" (
    set "VS_PATH=C:\Program Files\Microsoft Visual Studio\2022\Professional\Common7\Tools\VsDevCmd.bat"
    set "VS_VERSION=2022 Professional"
    goto :vs_found
)
if exist "C:\Program Files\Microsoft Visual Studio\2022\Enterprise\Common7\Tools\VsDevCmd.bat" (
    set "VS_PATH=C:\Program Files\Microsoft Visual Studio\2022\Enterprise\Common7\Tools\VsDevCmd.bat"
    set "VS_VERSION=2022 Enterprise"
    goto :vs_found
)

REM Check for VS 2019
if exist "C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\Common7\Tools\VsDevCmd.bat" (
    set "VS_PATH=C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\Common7\Tools\VsDevCmd.bat"
    set "VS_VERSION=2019 Community"
    goto :vs_found
)
if exist "C:\Program Files (x86)\Microsoft Visual Studio\2019\Professional\Common7\Tools\VsDevCmd.bat" (
    set "VS_PATH=C:\Program Files (x86)\Microsoft Visual Studio\2019\Professional\Common7\Tools\VsDevCmd.bat"
    set "VS_VERSION=2019 Professional"
    goto :vs_found
)

echo [❌ ERROR] Visual Studio not found!
echo.
echo 📥 Please install one of the following:
echo   - Visual Studio 2022 Community (Recommended)
echo   - Visual Studio 2019 Community
echo.
echo 🔗 Download from: https://visualstudio.microsoft.com/vs/community/
echo ⚠️  Make sure to install "Desktop development with C++" workload
echo.
pause
exit /b 1

:vs_found
echo [✅ INFO] Found Visual Studio !VS_VERSION!
echo [📁 PATH] !VS_PATH!

REM Check Java
echo.
echo [🔍 STEP 2/6] Checking Java installation...
java -version >nul 2>&1
if errorlevel 1 (
    echo [❌ ERROR] Java not found!
    echo.
    echo 📥 Please install Java 8 or higher
    echo 🔗 Download from: https://adoptium.net/
    echo.
    pause
    exit /b 1
)
echo [✅ INFO] Java found

REM Check CMake
echo.
echo [🔍 STEP 3/6] Checking CMake installation...
cmake --version >nul 2>&1
if errorlevel 1 (
    echo [❌ ERROR] CMake not found!
    echo.
    echo 📥 Please install CMake 3.10 or higher
    echo 🔗 Download from: https://cmake.org/download/
    echo ⚠️  Make sure to add CMake to your PATH during installation
    echo.
    pause
    exit /b 1
)
echo [✅ INFO] CMake found

REM Compile Java components
echo.
echo [⚙️ STEP 4/6] Compiling Java components...
cd ..
if not exist "build" mkdir build

echo   - Compiling Java tools and injectors...
call gradlew.bat compileInjectors compileTools >nul 2>&1
if errorlevel 1 (
    echo [❌ ERROR] Failed to compile Java components
    echo.
    echo 💡 Try running: gradlew.bat compileInjectors compileTools
    echo    to see detailed error messages
    pause
    exit /b 1
)
echo [✅ SUCCESS] Java components compiled

cd convert

REM Detect mod type
echo.
echo [🔍 STEP 5/6] Analyzing mod type...
for /f %%i in ('java -cp ..\build\tools tools.git.fowin.ModTypeDetector input.jar') do set MOD_TYPE=%%i
echo [📋 INFO] Detected mod type: !MOD_TYPE!

REM Convert to C++ headers
echo.
echo [🔄 STEP 6/6] Converting and building DLL...
if not exist "..\src\main\cpp\classes" mkdir ..\src\main\cpp\classes

echo   - Converting injector to C++ header...
if "!MOD_TYPE!"=="FABRIC" (
    echo   [📝 INFO] Using FabricInjector for Fabric mod
    java -cp ..\build\tools tools.git.fowin.HeaderConverter injector ..\build\injectors\com\jar2dll\injectors\FabricInjector.class ..\src\main\cpp\classes\injector.h >nul 2>&1
) else (
    echo   [📝 INFO] Using ForgeInjector for Forge/Unknown mod
    java -cp ..\build\tools tools.git.fowin.HeaderConverter injector ..\build\injectors\com\jar2dll\injectors\ForgeInjector.class ..\src\main\cpp\classes\injector.h >nul 2>&1
)
if errorlevel 1 (
    echo [❌ ERROR] Failed to convert injector
    pause
    exit /b 1
)
echo   [✅ SUCCESS] Injector header created

echo   - Converting JAR to C++ header...
java -cp ..\build\tools tools.git.fowin.HeaderConverter input-jar input.jar ..\src\main\cpp\classes\jar.h >nul 2>&1
if errorlevel 1 (
    echo [❌ ERROR] Failed to convert JAR
    pause
    exit /b 1
)
echo   [✅ SUCCESS] JAR header created

echo   - Setting up Visual Studio environment...
call "!VS_PATH!" >nul 2>&1
if errorlevel 1 (
    echo [❌ ERROR] Failed to setup Visual Studio environment
    pause
    exit /b 1
)
echo   [✅ SUCCESS] Visual Studio environment ready

echo   - Building DLL with CMake...
if not exist "..\build\cmake" mkdir ..\build\cmake
cd ..\build\cmake

cmake ..\..\src\main\cpp -G "Visual Studio 17 2022" -A x64 >nul 2>&1
if errorlevel 1 (
    cmake ..\..\src\main\cpp -G "Visual Studio 16 2019" -A x64 >nul 2>&1
    if errorlevel 1 (
        echo [❌ ERROR] CMake configuration failed
        cd ..\..\convert
        pause
        exit /b 1
    )
)
echo   [✅ SUCCESS] CMake configured

cmake --build . --config Release >nul 2>&1
if errorlevel 1 (
    echo [❌ ERROR] Build failed
    cd ..\..\convert
    pause
    exit /b 1
)
echo   [✅ SUCCESS] DLL compiled

cd ..\..\convert

echo   - Copying DLL to convert folder...
copy ..\build\cmake\Release\output.dll output.dll >nul 2>&1
if exist "output.dll" (
    echo   [✅ SUCCESS] DLL created: output.dll
) else (
    echo [⚠️ WARNING] DLL not found in expected location
)

echo.
echo [🧹 CLEANUP] Removing temporary files...
if exist "..\build\cmake" rmdir /s /q ..\build\cmake >nul 2>&1
if exist "..\src\main\cpp\classes" rmdir /s /q ..\src\main\cpp\classes >nul 2>&1
echo [✅ SUCCESS] Cleanup completed

echo.
echo ╔══════════════════════════════════════════════════════════════════════════════╗
echo ║                              🎉 BUILD SUCCESSFUL! 🎉                        ║
echo ╚══════════════════════════════════════════════════════════════════════════════╝
echo.
echo 📁 Generated files in convert folder:
echo   ✅ output.dll - Ready for injection
echo   📋 Mod type: !MOD_TYPE!
echo.
echo 🚀 Next steps:
echo   1. Use a DLL injector (like Process Hacker, Cheat Engine, etc.)
echo   2. Inject output.dll into your Minecraft process
echo   3. Your mod should be loaded automatically
echo.
echo ⚠️  IMPORTANT: Make sure Minecraft is running with the correct mod loader
echo    (Fabric for Fabric mods, Forge for Forge mods)
echo.
pause
