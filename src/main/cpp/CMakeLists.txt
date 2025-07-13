cmake_minimum_required(VERSION 3.10)
project(jar-to-dll)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

include_directories(${CMAKE_CURRENT_SOURCE_DIR}/include)

set(SOURCES
    src/main.cpp
    src/injector.cpp
)

add_library(output SHARED ${SOURCES})

set_target_properties(output PROPERTIES OUTPUT_NAME "output")

if(WIN32)
    target_link_libraries(output user32)
    set_target_properties(output PROPERTIES
        LINK_FLAGS "/SUBSYSTEM:WINDOWS"
    )
endif()

if(MSVC)
    target_compile_options(output PRIVATE /W3)
else()
    target_compile_options(output PRIVATE -Wall -Wextra)
endif()
