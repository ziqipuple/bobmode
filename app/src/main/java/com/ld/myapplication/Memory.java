package com.ld.myapplication;

public class Memory {

    public static final String TAG = "memory";

    // 内存范围常量
    public final int RANGE_ALL = 0;            // 全部内存
    public final int RANGE_ANONYMOUS = 7;      // 匿名内存
    public final int RANGE_ASHMEM = 12;        // ASHMEM共享内存
    public final int RANGE_B_BAD = 6;          // "B"内存（保留范围）
    public final int RANGE_C_ALLOC = 3;        // C分配内存
    public final int RANGE_C_BSS = 1;          // C BSS段内存
    public final int RANGE_C_DATA = 4;         // C数据段内存
    public final int RANGE_C_HEAP = 2;         // C堆内存
    public final int RANGE_JAVA_HEAP = 8;      // Java堆内存
    public final int RANGE_OTHER = 13;         // 其他内存
    public final int RANGE_STACK = 5;          // 堆栈内存
    public final int RANGE_VIDEO = 11;         // 视频内存
    public final int RANGE_CODE_SYSTEM = 10;   // 系统代码内存
    public final int RANGE_CODE_APP = 9;       // 应用代码内存

    // 数据类型常量
    public final int TYPE_DWORD = 1;           // DWORD类型 (32位整数)
    public final int TYPE_FLOAT = 2;           // FLOAT类型 (单精度浮点数)
    public final int TYPE_DOUBLE = 3;          // DOUBLE类型 (双精度浮点数)

    // 加载C++层的共享库
    static {
        System.loadLibrary("IMGUI");
    }

    /**
     * 获取包名对应的进程ID
     * @param name 包名
     * @return 进程ID
     */
    public native int getPackageNamePid(String name);

    /**
     * 设置包名
     * @param name 包名
     */
    public native void setPackageName(String name);

    /**
     * 设置内存范围
     * @param range 内存范围常量
     */
    public native void setRange(int range);

    /**
     * 获取指定模块的内存地址
     * @param module 模块名称
     * @return 模块的起始内存地址
     */
    public native long getModuleAddress(String module);
    //指针跳转
    public native long[] pointerJump(long baseAddress, long[] offsets, int bits);

    /**
     * 在指定内存范围内搜索特定值
     * @param value 要搜索的值
     * @param type 值的类型 (DWORD, FLOAT, DOUBLE)
     */
    public native void RangeMemorySearch(String value, int type);

    /**
     * 根据偏移量从内存中读取值
     * @param value 要读取的初始值
     * @param type 值的类型 (DWORD, FLOAT, DOUBLE)
     * @param offset 偏移量
     */
    public native void MemoryOffset(String value, int type, long offset);

    /**
     * 将值写入内存
     * @param value 要写入的值
     * @param type 值的类型 (DWORD, FLOAT, DOUBLE)
     * @param offset 偏移量
     */
    public native void MemoryWrite(String value, int type, long offset);

    /**
     * 设置指定地址的内存值
     * @param value 要设置的值
     * @param addr 目标内存地址
     * @param type 值的类型 (DWORD, FLOAT, DOUBLE)
     */
    public native void setValue(String value, long addr, int type);

    /**
     * 从指定地址读取DWORD类型的值
     * @param addr 要读取的内存地址
     * @return DWORD类型的值
     */
    public native int readDword(long addr);

    /**
     * 从指定地址读取FLOAT类型的值
     * @param addr 要读取的内存地址
     * @return FLOAT类型的值
     */
    public native float readFloat(long addr);

    /**
     * 从指定地址读取长整型值
     * @param addr 要读取的内存地址
     * @return 长整型值
     */
    public native long readLong(long addr);

    /**
     * 清除搜索结果
     */
    public native void clearResult();

    /**
     * 获取当前搜索结果的数量
     * @return 搜索结果数量
     */
    public native int getResultCount();

    /**
     * 获取搜索结果的地址列表
     * @param count 要获取的结果数量
     * @return 搜索结果的地址数组
     */
    public native long[] getResult(int count);

    /**
     * 启动内存冻结功能
     */
    public native void startFreeze();

    /**
     * 停止内存冻结功能
     */
    public native void stopFreeze();

    /**
     * 添加冻结项到冻结列表
     * @param value 要冻结的值
     * @param addr 目标内存地址
     * @param type 值的类型 (DWORD, FLOAT, DOUBLE)
     */
    public native void addListItem(String value, long addr, int type);

    /**
     * 从冻结列表中移除冻结项
     * @param addr 要移除的内存地址
     */
    public native void removeListItem(long addr);
    
    public native void searchAndModifyMemory(String packageName, String value, int type, int range, String modifyValue, int modifyCount);

    
}
