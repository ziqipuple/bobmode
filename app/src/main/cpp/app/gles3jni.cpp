#include <jni.h>
#include <android/native_window_jni.h> // 必须引入 ANativeWindow 支持
#include "Includes.h"


int screenWidth = 0;
int screenHeight = 0;
bool g_Initialized = false;
ImGuiWindow* g_window = NULL;

// 配置
struct sConfig {
    bool IsWindowVisible;
};
sConfig Config{true};

extern "C" {
    JNIEXPORT void JNICALL Java_com_ld_myapplication_GLES3JNIView_init(JNIEnv* env, jclass cls, jobject surface);
    JNIEXPORT void JNICALL Java_com_ld_myapplication_GLES3JNIView_resize(JNIEnv* env, jobject obj, jint width, jint height);
    JNIEXPORT void JNICALL Java_com_ld_myapplication_GLES3JNIView_step(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_ld_myapplication_GLES3JNIView_imgui_1Shutdown(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_ld_myapplication_GLES3JNIView_MotionEventClick(JNIEnv* env, jobject obj, jboolean down, jfloat PosX, jfloat PosY);
    JNIEXPORT jstring JNICALL Java_com_ld_myapplication_GLES3JNIView_getWindowRect(JNIEnv *env, jobject thiz);
    JNIEXPORT void JNICALL Java_com_ld_myapplication_GLES3JNIView_real(JNIEnv* env, jobject obj, jint width, jint height);
};


JNIEXPORT jboolean JNICALL
Java_com_ld_myapplication_HOOK_isImGuiComponentTouched(JNIEnv *env, jclass clazz, jfloat x, jfloat y) {
    // 获取动态窗口边界
    float windowBounds[4] = {0.0f, 0.0f, 0.0f, 0.0f};
    for (int i = 0; i < ImGui::GetCurrentContext()->Windows.Size; ++i) {
        ImGuiWindow* window = ImGui::GetCurrentContext()->Windows[i];
        if (window->Active) {
            windowBounds[0] = window->Pos.x;
            windowBounds[1] = window->Pos.y;
            windowBounds[2] = window->Pos.x + window->Size.x;
            windowBounds[3] = window->Pos.y + window->Size.y;
            break; // 假设只需要处理一个窗口
        }
    }

    // 判断触摸点是否在边界内
    if (x >= windowBounds[0] && x <= windowBounds[2] &&
        y >= windowBounds[1] && y <= windowBounds[3]) {
        return JNI_TRUE;
    }
    return JNI_FALSE;
}



extern "C"
JNIEXPORT jfloatArray JNICALL
Java_com_ld_myapplication_HOOK_nativeGetImGuiWindowBounds(JNIEnv *env, jclass clazz) {
    // 实现内容

    // 创建一个 float 数组用于存储边界值
    jfloatArray bounds = env->NewFloatArray(4);
    if (bounds == nullptr) return nullptr;

    float windowBounds[4] = {0.0f, 0.0f, 0.0f, 0.0f};

    // 遍历所有的 ImGui 窗口，找到活动的窗口并获取边界
    for (int i = 0; i < ImGui::GetCurrentContext()->Windows.Size; ++i) {
        ImGuiWindow* window = ImGui::GetCurrentContext()->Windows[i];
        if (window->Active) {
            windowBounds[0] = window->Pos.x;                      // left
            windowBounds[1] = window->Pos.y;                      // top
            windowBounds[2] = window->Pos.x + window->Size.x;     // right
            windowBounds[3] = window->Pos.y + window->Size.y;     // bottom
            break; // 假设只有一个目标窗口
        }
    }

    // 将边界数据设置到 jfloatArray 中
    env->SetFloatArrayRegion(bounds, 0, 4, windowBounds);

    return bounds;
}




JNIEXPORT void JNICALL
Java_com_ld_myapplication_GLES3JNIView_init(JNIEnv* env, jclass cls, jobject surface) {
    if (g_Initialized) return;

    IMGUI_CHECKVERSION();
    ImGui::CreateContext();
    ImGuiIO& io = ImGui::GetIO();

    io.IniFilename = NULL; // 禁用保存 ini 文件
    ImGui::StyleColorsClassic(); // 设置经典风格

    // 获取 ANativeWindow 对象
    ANativeWindow* nativeWindow = ANativeWindow_fromSurface(env, surface);
    if (!nativeWindow) {
        __android_log_print(ANDROID_LOG_ERROR, "IMGUI", "Failed to get ANativeWindow from Surface");
        return;
    }

    // 初始化 ImGui 的 Android 和 OpenGL 后端
    ImGui_ImplAndroid_Init(nativeWindow);
    ImGui_ImplOpenGL3_Init("#version 300 es");

    // 使用嵌入的字体数据
    if (zt_ttf_len > 0) {
        ImFont* font = io.Fonts->AddFontFromMemoryTTF((void*)zt_ttf, zt_ttf_len, 45.0f, NULL, io.Fonts->GetGlyphRangesChineseFull());
        IM_ASSERT(font != NULL); // 确保字体加载成功
    } else {
        __android_log_print(ANDROID_LOG_ERROR, "IMGUI", "Embedded font data is empty");
    }

    // 样式调整
    ImGui::GetStyle().ScaleAllSizes(3.0f); // 放大样式比例
    ImGuiStyle& style = ImGui::GetStyle();
    style.WindowRounding = 5.3f;
    style.FrameRounding = 2.3f;
    style.ScrollbarRounding = 0;

    g_Initialized = true;
}

JNIEXPORT void JNICALL
Java_com_ld_myapplication_GLES3JNIView_resize(JNIEnv* env, jobject obj, jint width, jint height) {
    screenWidth = (int) width;
    screenHeight = (int) height;
    glViewport(0, 0, width, height);
    ImGuiIO& io = ImGui::GetIO();
    io.DisplaySize = ImVec2((float)width, (float)height);
}

void DrawFloatingWindow() {
    static bool isCollapsed = false;

    // 设置悬浮窗口位置和大小，动态适配屏幕
    float windowWidth = screenWidth * 0.8f;
    float windowHeight = screenHeight * 0.6f;
    ImGui::SetNextWindowPos(ImVec2(screenWidth * 0.1f, screenHeight * 0.2f), ImGuiCond_FirstUseEver);
    ImGui::SetNextWindowSize(ImVec2(windowWidth, windowHeight), ImGuiCond_FirstUseEver);

    ImGuiWindowFlags windowFlags = ImGuiWindowFlags_NoSavedSettings;
    ImGui::Begin("悬浮窗口", nullptr, windowFlags);
    ImGui::End();
}

JNIEXPORT void JNICALL
Java_com_ld_myapplication_GLES3JNIView_step(JNIEnv* env, jobject obj) {
    if (!Config.IsWindowVisible) return;

    ImGuiIO& io = ImGui::GetIO();
    ImGui_ImplOpenGL3_NewFrame();
    ImGui_ImplAndroid_NewFrame();
    ImGui::NewFrame();

    // 绘制单一悬浮窗口
    DrawFloatingWindow();

    ImGui::Render();
    glClear(GL_COLOR_BUFFER_BIT);
    ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());
}

JNIEXPORT void JNICALL Java_com_ld_myapplication_GLES3JNIView_imgui_1Shutdown(JNIEnv* env, jobject obj) {
    if (!g_Initialized) return;

    ImGui_ImplOpenGL3_Shutdown();
    ImGui_ImplAndroid_Shutdown();
    ImGui::DestroyContext();
    g_Initialized = false;
}

JNIEXPORT void JNICALL Java_com_ld_myapplication_GLES3JNIView_MotionEventClick(JNIEnv* env, jobject obj, jboolean down, jfloat PosX, jfloat PosY) {
    ImGuiIO &io = ImGui::GetIO();
    io.MouseDown[0] = down;
    io.MousePos = ImVec2(PosX, PosY);
}

JNIEXPORT jstring JNICALL Java_com_ld_myapplication_GLES3JNIView_getWindowRect(JNIEnv *env, jobject thiz) {
    char result[256] = "0|0|0|0";
    if (g_window) {
        sprintf(result, "%d|%d|%d|%d", (int)g_window->Pos.x, (int)g_window->Pos.y, (int)g_window->Size.x, (int)g_window->Size.y);
    }
    return env->NewStringUTF(result);
}

JNIEXPORT void JNICALL Java_com_ld_myapplication_GLES3JNIView_real(JNIEnv* env, jobject obj, jint w, jint h) {
    screenWidth = (int) w;
    screenHeight = (int) h;
}