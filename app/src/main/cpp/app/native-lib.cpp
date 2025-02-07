#ifndef MEMORY_HANDLER_H
#define MEMORY_HANDLER_H

#include <jni.h>
#include <vector>  
#include "../imgui/memory.h"  
#include "../imgui/inlineHook.h"
#ifdef __cplusplus
extern "C" {
#endif

// 获取包名对应的进程ID
JNIEXPORT jint JNICALL Java_com_ld_myapplication_Memory_getPackageNamePid(
        JNIEnv *env, jobject obj, jstring name) {
    const char* str = env->GetStringUTFChars(name, nullptr);
    int pid = getPackageNamePid(strdup(str));
    env->ReleaseStringUTFChars(name, str);
    return pid;
}

// 设置包名
JNIEXPORT void JNICALL Java_com_ld_myapplication_Memory_setPackageName(
        JNIEnv *env, jobject obj, jstring name) {
    const char* str = env->GetStringUTFChars(name, nullptr);
    setPackageName(strdup(str));
    env->ReleaseStringUTFChars(name, str);
}

// 设置内存范围
JNIEXPORT void JNICALL Java_com_ld_myapplication_Memory_setRange(
        JNIEnv *env, jobject obj, jint range) {
    setRange(range);
}

// 获取模块地址
JNIEXPORT jlong JNICALL Java_com_ld_myapplication_Memory_getModuleAddress(
        JNIEnv *env, jobject obj, jstring module) {
    // 将 Java 字符串转换为 C 字符串
    const char* str = env->GetStringUTFChars(module, nullptr);

    // 调用 getModuleAddress 方法解析模块地址
    unsigned long address = getModuleAddress(str);

    // 释放 Java 字符串
    env->ReleaseStringUTFChars(module, str);

    // 返回结果给 Java
    return (jlong)address;
}

//指针跳转

JNIEXPORT jlongArray JNICALL Java_com_ld_myapplication_Memory_pointerJump(
        JNIEnv *env, jobject obj, jlong baseAddress, jlongArray offsets, jint bits) {
    jsize level = env->GetArrayLength(offsets);
    if (level <= 0) {
        fprintf(stderr, "Invalid offset chain\n");
        return NULL;
    }

    jlong *nativeOffsets = env->GetLongArrayElements(offsets, NULL);
    unsigned long *cOffsets = (unsigned long *)nativeOffsets;

    // 调用多级指针跳转实现
    unsigned long *results = pointer_jump((unsigned long)baseAddress, cOffsets, level, bits);

    env->ReleaseLongArrayElements(offsets, nativeOffsets, JNI_ABORT);

    if (!results) {
        return NULL; // 跳转失败
    }

    // 转换为 Java 层数组
    jlongArray resultArray = env->NewLongArray(level + 1);
    env->SetLongArrayRegion(resultArray, 0, level + 1, (jlong *)results);

    free(results);
    return resultArray;
}





// 在内存范围内搜索
JNIEXPORT void JNICALL Java_com_ld_myapplication_Memory_RangeMemorySearch(
        JNIEnv *env, jobject obj, jstring value, jint type) {
    const char* str = env->GetStringUTFChars(value, nullptr);
    RangeMemorySearch(strdup(str), type);
    env->ReleaseStringUTFChars(value, str);
}

// 根据偏移量读取内存
JNIEXPORT void JNICALL Java_com_ld_myapplication_Memory_MemoryOffset(
        JNIEnv *env, jobject obj, jstring value, jint type, jlong offset) {
    const char* str = env->GetStringUTFChars(value, nullptr);
    MemoryOffset(strdup(str), type, offset);
    env->ReleaseStringUTFChars(value, str);
}

// 写入内存
JNIEXPORT void JNICALL Java_com_ld_myapplication_Memory_MemoryWrite(
        JNIEnv *env, jobject obj, jstring value, jint type, jlong offset) {
    const char* str = env->GetStringUTFChars(value, nullptr);
    MemoryWrite(strdup(str), type, offset);
    env->ReleaseStringUTFChars(value, str);
}

// 设置内存值
JNIEXPORT void JNICALL Java_com_ld_myapplication_Memory_setValue(
        JNIEnv *env, jobject obj, jstring value, jlong addr, jint type) {
    const char* str = env->GetStringUTFChars(value, nullptr);
    setValue(strdup(str), addr, type);
    env->ReleaseStringUTFChars(value, str);
}

// 读取DWORD值
JNIEXPORT jint JNICALL Java_com_ld_myapplication_Memory_readDword(
        JNIEnv *env, jobject obj, jlong addr) {
    return readDword(addr);
}

// 读取FLOAT值
JNIEXPORT jfloat JNICALL Java_com_ld_myapplication_Memory_readFloat(
        JNIEnv *env, jobject obj, jlong addr) {
    return readFloat(addr);
}

// 读取长整型值
JNIEXPORT jlong JNICALL Java_com_ld_myapplication_Memory_readLong(
        JNIEnv *env, jobject obj, jlong addr) {
    return readLong(addr);
}

// 清除搜索结果
JNIEXPORT void JNICALL Java_com_ld_myapplication_Memory_clearResult(
        JNIEnv *env, jobject obj) {
    clearResult();
}

// 获取搜索结果的数量
JNIEXPORT jint JNICALL Java_com_ld_myapplication_Memory_getResultCount(
        JNIEnv *env, jobject obj) {
    return getResultCount();
}

// 获取搜索结果的地址列表
JNIEXPORT jlongArray JNICALL Java_com_ld_myapplication_Memory_getResult(
        JNIEnv *env, jobject obj, jint count) {
    unsigned long *results = getResult(count);
    jlongArray jResultArray = env->NewLongArray(count);
    env->SetLongArrayRegion(jResultArray, 0, count, reinterpret_cast<jlong*>(results));
    free(results);
    return jResultArray;
}

// 启动内存冻结
JNIEXPORT void JNICALL Java_com_ld_myapplication_Memory_startFreeze(
        JNIEnv *env, jobject obj) {
    startFreeze();
}

// 停止内存冻结
JNIEXPORT void JNICALL Java_com_ld_myapplication_Memory_stopFreeze(
        JNIEnv *env, jobject obj) {
    stopFreeze();
}

// 添加冻结项
JNIEXPORT void JNICALL Java_com_ld_myapplication_Memory_addListItem(
        JNIEnv *env, jobject obj, jstring value, jlong addr, jint type) {
    const char* str = env->GetStringUTFChars(value, nullptr);
    addListItem(strdup(str), addr, type);
    env->ReleaseStringUTFChars(value, str);
}

// 移除冻结项
JNIEXPORT void JNICALL Java_com_ld_myapplication_Memory_removeListItem(
        JNIEnv *env, jobject obj, jlong addr) {
    removeListItem(addr);
}

#ifdef __cplusplus
}
#endif

#endif // MEMORY_HANDLER_H
