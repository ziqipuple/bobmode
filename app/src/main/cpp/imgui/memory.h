#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <dirent.h>
#include <fcntl.h>
#include <vector>
#include <pthread.h>
#include <unistd.h>

// 定义内存范围和类型
#define RANGE_ALL 0
#define RANGE_C_HEAP 2
#define RANGE_JAVA_HEAP 8
#define RANGE_C_ALLOC 3
#define RANGE_C_DATA 4
#define RANGE_C_BSS 1
#define RANGE_ANONYMOUS 7
#define RANGE_B_BAD 6
#define RANGE_CODE_SYSTEM 10
#define RANGE_VIDEO 11
#define RANGE_STACK 5
#define RANGE_ASHMEM 12
#define RANGE_CODE_APP 9
#define RANGE_OTHER 13

#define TYPE_DWORD 1
#define TYPE_FLOAT 2
#define TYPE_DOUBLE 3

#define DWORD int32_t
#define FLOAT float
#define DOUBLE double

struct MemoryData {
    int pid;
    int range;
};

static bool freeze;

struct Table {
    char *value;
    unsigned long addr;
    int type;
};

using namespace std;

vector<unsigned long> sresult;
vector<unsigned long> oresult;
vector<Table> item;

MemoryData memoryData;

int getPackageNamePid(char *name);  // 获取包名对应的进程ID
void setPackageName(char *name);  // 设置包名
void setRange(int range);  // 设置内存范围
unsigned long getModuleAddress(char *name);  // 获取模块地址
unsigned long* pointer_jump(unsigned long baseAddress, unsigned long *offsets, int level, int bits);

void RangeMemorySearch(char *value, int type);  // 在内存范围内搜索
void RangeMemorySearchDword(int fd, DWORD value, unsigned long addr, int size);  // 搜索DWORD值
void RangeMemorySearchFloat(int fd, FLOAT value, unsigned long addr, int size);  // 搜索FLOAT值
void RangeMemorySearchDouble(int fd, DOUBLE value, unsigned long addr, int size);  // 搜索DOUBLE值

void MemoryOffset(char *value, int type, unsigned long offset);  // 根据偏移量读取内存
void MemoryOffsetDword(int fd, int value, unsigned long offset);  // 偏移量搜索DWORD
void MemoryOffsetFloat(int fd, float value, unsigned long offset);  // 偏移量搜索FLOAT
void MemoryOffsetDouble(int fd, double value, unsigned long offset);  // 偏移量搜索DOUBLE

void MemoryWrite(char *value, int type, unsigned long offset);  // 写入内存
void MemoryWriteDword(int fd, int value, unsigned long offset);  // 写入DWORD值
void MemoryWriteFloat(int fd, FLOAT value, unsigned long offset);  // 写入FLOAT值
void MemoryWriteDouble(int fd, DOUBLE value, unsigned long offset);  // 写入DOUBLE值

void setValue(char *value, unsigned long addr, int type);  // 设置内存值
void setValueDword(int fd, int value, unsigned long addr);  // 设置DWORD值
void setValueFloat(int fd, FLOAT value, unsigned long addr);  // 设置FLOAT值
void setValueDouble(int fd, DOUBLE value, unsigned long addr);  // 设置DOUBLE值

DWORD readDword(unsigned long addr);  // 读取DWORD值
FLOAT readFloat(unsigned long addr);  // 读取FLOAT值
unsigned long readLong(unsigned long addr);  // 读取长整型值

void clearResult();  // 清除搜索结果
int getResultCount();  // 获取搜索结果的数量
unsigned long *getResult(int count);  // 获取搜索结果的地址

void *memory_freeze_thread(void *arg);  // 冻结内存线程
void addListItem(char *value, unsigned long addr, int type);  // 添加冻结项
void removeListItem(unsigned long addr);  // 移除冻结项
void startFreeze();  // 启动内存冻结
void stopFreeze();  // 停止内存冻结

int getPackageNamePid(char *name) {
    int pid = -1;
    char cmdline[1024];
    char cmdname[1024];
    DIR *dir = opendir("/proc");
    struct dirent *entry;
    while ((entry = readdir(dir)) != NULL) {
        int id = atoi(entry->d_name);
        if (id != 0) {
            snprintf(cmdline, sizeof(cmdline), "/proc/%d/cmdline", id);
            FILE *fp = fopen(cmdline, "r");
            if (fp != NULL) {
                fgets(cmdname, sizeof(cmdname), fp);
                fclose(fp);
                if (strcmp(cmdname, name) == 0) {
                    pid = id;
                    break;
                }
            }
        }
    }
    closedir(dir);
    return pid;
}

// 多级偏移跳转方法，支持32位和64位

// 多级指针跳转函数
unsigned long* pointer_jump(unsigned long baseAddress, unsigned long *offsets, int level, int bits) {
    if (level <= 0) {
        fprintf(stderr, "Invalid offset chain\n");
        return NULL;
    }

    unsigned long *results = (unsigned long *)malloc(sizeof(unsigned long) * (level + 1));
    if (!results) {
        fprintf(stderr, "Memory allocation failed\n");
        return NULL;
    }

    unsigned long currentAddress = baseAddress;
    results[0] = currentAddress; // 保存初始基地址

    // 开始多级指针跳转
    for (int i = 0; i < level; i++) {
        if (currentAddress == 0) {
            fprintf(stderr, "Pointer jumping failed at level %d\n", i);
            results[i + 1] = 0; // 记录失败
            break;
        }

        if (i == 0) {
            // 第一个偏移量：直接加上偏移量
            currentAddress += offsets[i];
        } else if (i == level - 1) {
            // 倒数第二层：跳指针后加偏移量
            if (bits == 64) {
                currentAddress = *((unsigned long *)currentAddress);  // 读取 64 位指针
            } else if (bits == 32) {
                currentAddress = *((unsigned int *)currentAddress);   // 读取 32 位指针
            } else {
                fprintf(stderr, "Invalid bits value\n");
                results[i + 1] = 0;
                break;
            }

            // 检查是否为空指针
            if (currentAddress == 0) {
                fprintf(stderr, "Null pointer encountered at level %d\n", i);
                results[i + 1] = 0;
                break;
            }

            currentAddress += offsets[i];
        } else {
            // 中间层：读取指针值，然后加上偏移量
            if (bits == 64) {
                currentAddress = *((unsigned long *)currentAddress);
            } else if (bits == 32) {
                currentAddress = *((unsigned int *)currentAddress);
            } else {
                fprintf(stderr, "Invalid bits value\n");
                results[i + 1] = 0;
                break;
            }

            // 检查是否为空指针
            if (currentAddress == 0) {
                fprintf(stderr, "Null pointer encountered at level %d\n", i);
                results[i + 1] = 0;
                break;
            }

            currentAddress += offsets[i];
        }

        // 保存当前跳转地址
        results[i + 1] = currentAddress;
    }

    return results;
}




void setPackageName(char *name) {
    memoryData.pid = getPackageNamePid(name);
}

void setRange(int range) {
    memoryData.range = range;
}

unsigned long getModuleAddress(const char *module) {
    char mapPath[1024];
    char mapLine[1024];
    unsigned long addr = 0;
    snprintf(mapPath, sizeof(mapPath), "/proc/%d/maps", memoryData.pid);
    FILE *fp = fopen(mapPath, "r");
    if (!fp) {
        perror("Failed to open maps file");
        return 0;
    }

    // 分离模块名和段名
    char moduleName[256];
    char *segment = NULL;
    strncpy(moduleName, module, sizeof(moduleName));
    char *colon = strchr(moduleName, ':');
    if (colon) {
        *colon = '\0';      // 分隔出模块名
        segment = colon + 1; // 获取段名
    }

    while (fgets(mapLine, sizeof(mapLine), fp)) {
        // 检查模块名
        if (strstr(mapLine, moduleName)) {
            // 如果没有指定段名，直接返回第一个匹配的模块地址
            if (!segment) {
                sscanf(mapLine, "%lx", &addr);  // 提取地址
                break;
            }

            // 如果指定了段名，检查段信息
            if (strstr(mapLine, segment)) {
                sscanf(mapLine, "%lx", &addr);  // 提取地址
                break;
            }
        }
    }
    fclose(fp);
    return addr;
}


// 处理 C 堆内存
void range_c_heap(char *value, int type) {
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);
    int fd = open(memPath, O_RDONLY);
    if (fd != -1) {
        char mapPath[1024];
        sprintf(mapPath, "/proc/%d/maps", memoryData.pid);
        FILE *fp = fopen(mapPath, "r");
        if (fp) {
            char mapLine[1024];
            unsigned long start;
            unsigned long end;
            while (fgets(mapLine, sizeof(mapLine), fp)) {
                if (strstr(mapLine, "rw") && strstr(mapLine, "[heap]")) {
                    sscanf(mapLine, "%lx-%lx", &start, &end);
                    int size = end - start;
                    switch (type) {
                    case TYPE_DWORD:
                        RangeMemorySearchDword(fd, atoi(value), start, size);
                        break;
                    case TYPE_FLOAT:
                        RangeMemorySearchFloat(fd, atof(value), start, size);
                        break;
                    case TYPE_DOUBLE:
                        RangeMemorySearchDouble(fd, atof(value), start, size);
                        break;
                    }
                }
            }
            fclose(fp);
        }
        close(fd);
    }
}

// 处理匿名内存
void range_anonymous(char *value, int type) {
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);
    int fd = open(memPath, O_RDONLY);
    if (fd != -1) {
        char mapPath[1024];
        sprintf(mapPath, "/proc/%d/maps", memoryData.pid);
        FILE *fp = fopen(mapPath, "r");
        if (fp) {
            char mapLine[1024];
            char module[256];
            unsigned long start;
            unsigned long end;
            while (fgets(mapLine, sizeof(mapLine), fp)) {
                if (strstr(mapLine, "rw")) {
                    memset(module, 0, sizeof(module));
                    sscanf(mapLine, "%lx-%lx %*s %*lx %*s %*d%s", &start, &end, module);
                    if (strcmp(module, "") == 0) {
                        int size = end - start;
                        switch (type) {
                        case TYPE_DWORD:
                            RangeMemorySearchDword(fd, atoi(value), start, size);
                            break;
                        case TYPE_FLOAT:
                            RangeMemorySearchFloat(fd, atof(value), start, size);
                            break;
                        case TYPE_DOUBLE:
                            RangeMemorySearchDouble(fd, atof(value), start, size);
                            break;
                        }
                    }
                }
            }
            fclose(fp);
        }
        close(fd);
    }
}

// 处理所有内存范围
void range_all(char *value, int type) {
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);
    int fd = open(memPath, O_RDONLY);
    if (fd != -1) {
        char mapPath[1024];
        sprintf(mapPath, "/proc/%d/maps", memoryData.pid);
        FILE *fp = fopen(mapPath, "r");
        if (fp) {
            char mapLine[1024];
            unsigned long start;
            unsigned long end;
            while (fgets(mapLine, sizeof(mapLine), fp)) {
                sscanf(mapLine, "%lx-%lx", &start, &end);
                int size = end - start;
                switch (type) {
                case TYPE_DWORD:
                    RangeMemorySearchDword(fd, atoi(value), start, size);
                    break;
                case TYPE_FLOAT:
                    RangeMemorySearchFloat(fd, atof(value), start, size);
                    break;
                case TYPE_DOUBLE:
                    RangeMemorySearchDouble(fd, atof(value), start, size);
                    break;
                }
            }
            fclose(fp);
        }
        close(fd);
    }
}

// 处理 Java 堆内存
void range_java_heap(char *value, int type) {
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);
    int fd = open(memPath, O_RDONLY);
    if (fd != -1) {
        char mapPath[1024];
        sprintf(mapPath, "/proc/%d/maps", memoryData.pid);
        FILE *fp = fopen(mapPath, "r");
        if (fp) {
            char mapLine[1024];
            unsigned long start;
            unsigned long end;
            while (fgets(mapLine, sizeof(mapLine), fp)) {
                if (strstr(mapLine, "rw") && strstr(mapLine, "[heap]")) {
                    sscanf(mapLine, "%lx-%lx", &start, &end);
                    int size = end - start;
                    switch (type) {
                    case TYPE_DWORD:
                        RangeMemorySearchDword(fd, atoi(value), start, size);
                        break;
                    case TYPE_FLOAT:
                        RangeMemorySearchFloat(fd, atof(value), start, size);
                        break;
                    case TYPE_DOUBLE:
                        RangeMemorySearchDouble(fd, atof(value), start, size);
                        break;
                    }
                }
            }
            fclose(fp);
        }
        close(fd);
    }
}

// 处理 C 分配内存
void range_c_alloc(char *value, int type) {
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);
    int fd = open(memPath, O_RDONLY);
    if (fd != -1) {
        char mapPath[1024];
        sprintf(mapPath, "/proc/%d/maps", memoryData.pid);
        FILE *fp = fopen(mapPath, "r");
        if (fp) {
            char mapLine[1024];
            unsigned long start;
            unsigned long end;
            while (fgets(mapLine, sizeof(mapLine), fp)) {
                if (strstr(mapLine, "[heap]")) {
                    sscanf(mapLine, "%lx-%lx", &start, &end);
                    int size = end - start;
                    switch (type) {
                    case TYPE_DWORD:
                        RangeMemorySearchDword(fd, atoi(value), start, size);
                        break;
                    case TYPE_FLOAT:
                        RangeMemorySearchFloat(fd, atof(value), start, size);
                        break;
                    case TYPE_DOUBLE:
                        RangeMemorySearchDouble(fd, atof(value), start, size);
                        break;
                    }
                }
            }
            fclose(fp);
        }
        close(fd);
    }
}

// 处理 C 数据段内存
void range_c_data(char *value, int type)
{
	char memPath[1024];
	sprintf(memPath, "/proc/%d/mem", memoryData.pid);
	int fd = open(memPath, O_RDONLY);
	if (fd != -1)
	{
		char mapPath[1024];
		sprintf(mapPath, "/proc/%d/maps", memoryData.pid);
		FILE *fp = fopen(mapPath, "r");
		if (fp)
		{
			char mapLine[1024];
			unsigned long start;
			unsigned long end;
			while (fgets(mapLine, sizeof(mapLine), fp))
			{
				if (strstr(mapLine, "rw") && strstr(mapLine, "/data/app/"))
				{
					sscanf(mapLine, "%lx-%lx", &start, &end);
					int size = end - start;
					switch (type)
					{
					case TYPE_DWORD:
						RangeMemorySearchDword(fd, atoi(value), start, size);
						break;
					case TYPE_FLOAT:
						RangeMemorySearchFloat(fd, atof(value), start, size);
						break;
					case TYPE_DOUBLE:
						RangeMemorySearchDouble(fd, atof(value), start, size);
						break;
					}
				}
			}
		}
	}
}

// 处理 C BSS 段内存
void range_c_bss(char *value, int type) {
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);
    int fd = open(memPath, O_RDONLY);
    if (fd != -1) {
        char mapPath[1024];
        sprintf(mapPath, "/proc/%d/maps", memoryData.pid);
        FILE *fp = fopen(mapPath, "r");
        if (fp) {
            char mapLine[1024];
            unsigned long start;
            unsigned long end;
            while (fgets(mapLine, sizeof(mapLine), fp)) {
                if (strstr(mapLine, "rw") && strstr(mapLine, "[bss]")) {
                    sscanf(mapLine, "%lx-%lx", &start, &end);
                    int size = end - start;
                    switch (type) {
                    case TYPE_DWORD:
                        RangeMemorySearchDword(fd, atoi(value), start, size);
                        break;
                    case TYPE_FLOAT:
                        RangeMemorySearchFloat(fd, atof(value), start, size);
                        break;
                    case TYPE_DOUBLE:
                        RangeMemorySearchDouble(fd, atof(value), start, size);
                        break;
                    }
                }
            }
            fclose(fp);
        }
        close(fd);
    }
}
// 处理 Xa 内存 (REGION_CODE_APP)
void range_code_app(char *value, int type) {
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);  // 打开目标进程内存文件
    int fd = open(memPath, O_RDONLY);
    if (fd != -1) {
        char mapPath[1024];
        sprintf(mapPath, "/proc/%d/maps", memoryData.pid);  // 打开目标进程映射文件
        FILE *fp = fopen(mapPath, "r");
        if (fp) {
            char mapLine[1024];
            unsigned long start;
            unsigned long end;
            while (fgets(mapLine, sizeof(mapLine), fp)) {
                if (strstr(mapLine, "r-xp") && strstr(mapLine, "/data/app/")) {  // 过滤 r-xp（可读、可执行）段
                    sscanf(mapLine, "%lx-%lx", &start, &end);
                    int size = end - start;
                    switch (type) {
                    case TYPE_DWORD:
                        RangeMemorySearchDword(fd, atoi(value), start, size);  // 搜索 DWORD 类型值
                        break;
                    case TYPE_FLOAT:
                        RangeMemorySearchFloat(fd, atof(value), start, size);  // 搜索 FLOAT 类型值
                        break;
                    case TYPE_DOUBLE:
                        RangeMemorySearchDouble(fd, atof(value), start, size);  // 搜索 DOUBLE 类型值
                        break;
                    }
                }
            }
            fclose(fp);
        }
        close(fd);
    }
}

// 请根据实际需求继续补全其他范围...
// 转换为 JNI 格式的函数
extern "C" JNIEXPORT void JNICALL
Java_com_ld_myapplication_Memory_searchAndModifyMemory(JNIEnv *env, jobject obj,
                                                        jstring packageName, jstring value,
                                                        jint type, jint range,
                                                        jstring modifyValue, jint modifyCount) {
    const char *pkgName = env->GetStringUTFChars(packageName, nullptr); // 获取 Java 字符串
    const char *searchValue = env->GetStringUTFChars(value, nullptr);
    const char *modifyValueStr = env->GetStringUTFChars(modifyValue, nullptr);

    // 设置包名
    setPackageName(strdup(pkgName));
    // 设置内存范围
    setRange(range);

    // 根据不同的内存范围进行搜索
    switch (range) {
        case 0: // RANGE_ALL
            range_all((char *)searchValue, type);
            break;
        case 1: // RANGE_C_HEAP
            range_c_heap((char *)searchValue, type);
            break;
        case 2: // RANGE_JAVA_HEAP
            range_java_heap((char *)searchValue, type);
            break;
        case 3: // RANGE_C_ALLOC
            range_c_alloc((char *)searchValue, type);
            break;
        case 4: // RANGE_C_DATA
            range_c_data((char *)searchValue, type);
            break;
        case 5: // RANGE_C_BSS
            range_c_bss((char *)searchValue, type);
            break;
        case 6: // RANGE_ANONYMOUS
            range_anonymous((char *)searchValue, type);
            break;
        case 9: // RANGE_CODE_APP
            range_code_app((char *)searchValue, type);
            break;
        default:
            printf("Unsupported memory range\n");
            return;
    }

    // 获取当前的搜索结果数量
    int resultCount = getResultCount();
    if (resultCount == 0) {
        printf("No results found for value: %s\n", searchValue);
        // 释放资源
        env->ReleaseStringUTFChars(packageName, pkgName);
        env->ReleaseStringUTFChars(value, searchValue);
        env->ReleaseStringUTFChars(modifyValue, modifyValueStr);
        return;
    }

    // 限制修改的个数（最多修改 `modifyCount` 个结果）
    int count = (modifyCount < resultCount) ? modifyCount : resultCount;

    // 获取搜索结果的地址
    unsigned long *results = getResult(count);

    // 根据类型执行内存修改
    for (int i = 0; i < count; i++) {
        unsigned long addr = results[i];
        if (addr != 0) {
            // 根据类型修改内存值
            switch (type) {
                case 1: // TYPE_DWORD
                    setValueDword(getpid(), atoi(modifyValueStr), addr);
                    break;
                case 2: // TYPE_FLOAT
                    setValueFloat(getpid(), atof(modifyValueStr), addr);
                    break;
                case 3: // TYPE_DOUBLE
                    setValueDouble(getpid(), atof(modifyValueStr), addr);
                    break;
                default:
                    printf("Unsupported data type\n");
                    break;
            }
            printf("Modified value at address: 0x%lx\n", addr);
        }
    }

    // 释放 JNI 字符串资源
    env->ReleaseStringUTFChars(packageName, pkgName);
    env->ReleaseStringUTFChars(value, searchValue);
    env->ReleaseStringUTFChars(modifyValue, modifyValueStr);
}

// 其他范围函数省略了重复的实现逻辑

void RangeMemorySearch(char *value, int type) {
    switch (memoryData.range) {
    case RANGE_C_HEAP:       // C 堆内存
        range_c_heap(value, type);
        break;
    case RANGE_ANONYMOUS:    // 匿名内存
        range_anonymous(value, type);
        break;
    case RANGE_ALL:          // 所有内存
        range_all(value, type);
        break;
    case RANGE_C_ALLOC:      // C 分配内存
        range_c_alloc(value, type);
        break;
    case RANGE_C_DATA:       // C 数据段
        range_c_data(value, type);
        break;
    case RANGE_C_BSS:        // C BSS 段
        range_c_bss(value, type);
        break;
    case RANGE_JAVA_HEAP:    // Java 堆内存
        range_java_heap(value, type);
        break;
    case RANGE_CODE_APP:     // Xa 内存（危险）
        range_code_app(value, type);
        break;
    // 根据需求可以扩展其他范围
    default:
        fprintf(stderr, "Unknown memory range specified: %d\n", memoryData.range);
        break;
    }
}


void RangeMemorySearchDword(int fd, DWORD value, unsigned long addr, int size) {
    DWORD *buf = (DWORD *)malloc(size);
    int ret = pread64(fd, buf, size, addr);
    if (ret == size) {
        int index = 0;
        while (index < size / sizeof(DWORD)) {
            if (buf[index] == value) {
                sresult.push_back(addr + index * sizeof(DWORD));
            }
            index++;
        }
    }
    free(buf);
}

void RangeMemorySearchFloat(int fd, FLOAT value, unsigned long addr, int size) {
    FLOAT *buf = (FLOAT *)malloc(size);
    int ret = pread64(fd, buf, size, addr);
    if (ret == size) {
        int index = 0;
        while (index < size / sizeof(FLOAT)) {
            if (buf[index] == value) {
                sresult.push_back(addr + index * sizeof(FLOAT));
            }
            index++;
        }
    }
    free(buf);
}

void RangeMemorySearchDouble(int fd, DOUBLE value, unsigned long addr, int size) {
    DOUBLE *buf = (DOUBLE *)malloc(size);
    int ret = pread64(fd, buf, size, addr);
    if (ret == size) {
        int index = 0;
        while (index < size / sizeof(DOUBLE)) {
            if (buf[index] == value) {
                sresult.push_back(addr + index * sizeof(DOUBLE));
            }
            index++;
        }
    }
    free(buf);
}

void MemoryOffset(char *value, int type, unsigned long offset) {
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);
    int fd = open(memPath, O_RDONLY);
    if (fd != -1) {
        switch (type) {
        case TYPE_DWORD:
            MemoryOffsetDword(fd, atoi(value), offset);
            break;
        case TYPE_FLOAT:
            MemoryOffsetFloat(fd, atof(value), offset);
            break;
        case TYPE_DOUBLE:
            MemoryOffsetDouble(fd, atof(value), offset);
            break;
        }
    }
    close(fd);
}

void MemoryOffsetDword(int fd, DWORD value, unsigned long offset) {
    DWORD *buf = (DWORD *)malloc(sizeof(DWORD));
    oresult.clear();
    for (size_t i = 0; i < sresult.size(); i++) {
        int ret = pread64(fd, buf, sizeof(DWORD), sresult[i] + offset);
        if (ret == sizeof(DWORD)) {
            if (*buf == value) {
                oresult.push_back(sresult[i]);
            }
        }
    }
    sresult = oresult;
    free(buf);
}

void MemoryOffsetFloat(int fd, FLOAT value, unsigned long offset) {
    FLOAT *buf = (FLOAT *)malloc(sizeof(FLOAT));
    oresult.clear();
    for (size_t i = 0; i < sresult.size(); i++) {
        int ret = pread64(fd, buf, sizeof(FLOAT), sresult[i] + offset);
        if (ret == sizeof(FLOAT)) {
            if (*buf == value) {
                oresult.push_back(sresult[i]);
            }
        }
    }
    sresult = oresult;
    free(buf);
}

void MemoryOffsetDouble(int fd, DOUBLE value, unsigned long offset) {
    DOUBLE *buf = (DOUBLE *)malloc(sizeof(DOUBLE));
    oresult.clear();
    for (size_t i = 0; i < sresult.size(); i++) {
        int ret = pread64(fd, buf, sizeof(DOUBLE), sresult[i] + offset);
        if (ret == sizeof(DOUBLE)) {
            if (*buf == value) {
                oresult.push_back(sresult[i]);
            }
        }
    }
    sresult = oresult;
    free(buf);
}

void MemoryWrite(char *value, int type, unsigned long offset) {
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);
    int fd = open(memPath, O_WRONLY);
    if (fd != -1) {
        switch (type) {
        case TYPE_DWORD:
            MemoryWriteDword(fd, atoi(value), offset);
            break;
        case TYPE_FLOAT:
            MemoryWriteFloat(fd, atof(value), offset);
            break;
        case TYPE_DOUBLE:
            MemoryWriteDouble(fd, atof(value), offset);
            break;
        }
    }
    close(fd);
}

void MemoryWriteDword(int fd, DWORD value, unsigned long offset) {
    for (size_t i = 0; i < sresult.size(); i++) {
        pwrite64(fd, &value, sizeof(DWORD), sresult[i] + offset);
    }
}

void MemoryWriteFloat(int fd, FLOAT value, unsigned long offset) {
    for (size_t i = 0; i < sresult.size(); i++) {
        pwrite64(fd, &value, sizeof(FLOAT), sresult[i] + offset);
    }
}

void MemoryWriteDouble(int fd, DOUBLE value, unsigned long offset) {
    for (size_t i = 0; i < sresult.size(); i++) {
        pwrite64(fd, &value, sizeof(DOUBLE), sresult[i] + offset);
    }
}

void setValue(char *value, unsigned long addr, int type) {
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);
    int fd = open(memPath, O_WRONLY);
    if (fd != -1) {
        switch (type) {
        case TYPE_DWORD:
            setValueDword(fd, atoi(value), addr);
            break;
        case TYPE_FLOAT:
            setValueFloat(fd, atof(value), addr);
            break;
        case TYPE_DOUBLE:
            setValueDouble(fd, atof(value), addr);
            break;
        }
    }
    close(fd);
}

void setValueDword(int fd, DWORD value, unsigned long addr) {
    pwrite64(fd, &value, sizeof(DWORD), addr);
}

void setValueFloat(int fd, FLOAT value, unsigned long addr) {
    pwrite64(fd, &value, sizeof(FLOAT), addr);
}

void setValueDouble(int fd, DOUBLE value, unsigned long addr) {
    pwrite64(fd, &value, sizeof(DOUBLE), addr);
}

DWORD readDword(unsigned long addr) {
    DWORD buf;
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);
    int fd = open(memPath, O_RDONLY);
    if (fd > 0) {
        pread64(fd, &buf, sizeof(DWORD), addr);
    }
    close(fd);
    return buf;
}

FLOAT readFloat(unsigned long addr) {
    FLOAT buf;
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);
    int fd = open(memPath, O_RDONLY);
    if (fd != -1) {
        pread64(fd, &buf, sizeof(FLOAT), addr);
    }
    close(fd);
    return buf;
}

DOUBLE readDouble(unsigned long addr) {
    DOUBLE buf;
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);
    int fd = open(memPath, O_RDONLY);
    if (fd != -1) {
        pread64(fd, &buf, sizeof(DOUBLE), addr);
    }
    close(fd);
    return buf;
}

unsigned long readLong(unsigned long addr) {
    unsigned long buf;
    char memPath[1024];
    sprintf(memPath, "/proc/%d/mem", memoryData.pid);
    int fd = open(memPath, O_RDONLY);
    if (fd != -1) {
        pread64(fd, &buf, sizeof(unsigned long), addr);
    }
    close(fd);
    return buf;
}

void clearResult() {
    sresult.clear();
}

int getResultCount() {
    return sresult.size();
}

unsigned long *getResult(int count) {
    unsigned long *result = (unsigned long *)malloc(count * sizeof(unsigned long));
    for (int i = 0; i < count; i++) {
        result[i] = sresult[i];
    }
    return result;
}

void *memory_freeze_thread(void *arg) {
    while (freeze) {
        int count = item.size();
        for (int i = 0; i < count; i++) {
            setValue(item[i].value, item[i].addr, item[i].type);
        }
        usleep(1000);  // 防止CPU占用过高，稍微休眠一下
    }
    return NULL;
}

void addListItem(char *value, unsigned long addr, int type) {
    Table table;
    table.value = value;
    table.addr = addr;
    table.type = type;
    item.push_back(table);
}

void removeListItem(unsigned long addr) {
    for (size_t i = 0; i < item.size(); i++) {
        if (addr == item[i].addr) {
            item.erase(item.begin() + i);
            break;
        }
    }
}

void startFreeze() {
    if (!freeze) {
        if (!item.empty()) {
            freeze = true;
            pthread_t t;
            pthread_create(&t, NULL, memory_freeze_thread, NULL);
        }
    }
}

void stopFreeze() {
    freeze = false;
}

void MemoryFreeze(const char* value, int type, long offset) {
    int count = getResultCount();
    for (int i = 0; i < count; i++) {
        unsigned long addr = sresult[i]; // 获取当前结果的地址

        // 将 const char* 转换为 char*，以匹配函数签名
        char* mutableValue = const_cast<char*>(value);

        MemoryWrite(mutableValue, type, offset); // 执行初始写入
        addListItem(mutableValue, addr + offset, type); // 添加到冻结列表中
    }
    
    // 启动冻结
    startFreeze();
}