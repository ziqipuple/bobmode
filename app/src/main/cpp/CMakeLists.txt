# 设置最低CMake版本
cmake_minimum_required(VERSION 3.10)

# 设置项目名称
project(IMGUI)

# 设置C++标准
set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# 添加编译选项
add_definitions(-Wno-error=format-security -w -fno-rtti -fno-exceptions -fpermissive)
add_definitions(-fms-extensions -Wno-error=c++11-narrowing -s)

# 定义OpenGL ES3宏
add_definitions(-DIMGUI_IMPL_OPENGL_ES3)

# 设置源文件目录
set(LOCAL_PATH ${CMAKE_SOURCE_DIR})

# 获取源文件
file(GLOB_RECURSE APP_FILES ${LOCAL_PATH}/app/*.cpp ${LOCAL_PATH}/app/*.c)
file(GLOB_RECURSE IMGUI_FILES ${LOCAL_PATH}/imgui/*.cpp ${LOCAL_PATH}/imgui/*.c)

# 合并源文件
set(SRC_FILES ${APP_FILES} ${IMGUI_FILES})

# 打印包含的源文件以调试
message(STATUS "Source Files: ${SRC_FILES}")

# 添加共享库
add_library(IMGUI SHARED ${SRC_FILES})

# 查找NDK库
find_library(LOG_LIB log)
find_library(ANDROID_LIB android)
find_library(EGL_LIB EGL)
find_library(GLESv3_LIB GLESv3)

# 链接库
target_link_libraries(IMGUI ${LOG_LIB} ${ANDROID_LIB} ${EGL_LIB} ${GLESv3_LIB})