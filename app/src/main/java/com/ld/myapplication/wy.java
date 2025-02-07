package com.ld.myapplication;

import android.content.Context;
import android.os.Environment;
import de.robv.android.xposed.XposedBridge;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Handler;
import android.os.Looper;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.UUID;

public class wy {
    // 项目相关配置
    private static final String WY_URL = "https://wy.llua.cn"; // API接口
    private static final String WY_APPID = "76117"; // 项目ID
    private static final String WY_APPKEY = "p432a9da174cba3e76cfe64a8f3c0"; // 项目KEY
    private static final String WY_RC4KEY = "p9520f84737dc7f821c"; // RC4密钥
    public static final String 当前版本号 = "1.1"; // 当前版本号

    // 登录回调接口
    public interface LoginCallback {
        void onLoginSuccess(String message); // 登录成功
        void onLoginFailure(String errorMessage); // 登录失败
    }

    // 通用回调接口
    public interface Callback {
        void onResult(String result); // 操作成功
        void onError(Exception e); // 操作失败
    }

    /**
     * 登录方法
     */
public static void 登录(final String 卡密, final Context context, final LoginCallback callback) {
    new Thread(() -> {
        String sign = WY_URL + "/api/?id=kmlogin"; // 卡密登录接口
        String random = UUID.randomUUID().toString().replace("-", "") + WY_APPKEY + 获取机器码(context); // 随机数
        long time = System.currentTimeMillis() / 1000; // 时间戳
        String signs = encodeMD5("kami=" + 卡密 + "&markcode=" + 获取机器码(context) + "&t=" + time + "&" + WY_APPKEY);

        String body = "&app=" + WY_APPID + "&kami=" + 卡密 + "&markcode=" + 获取机器码(context) + "&t=" + time + "&sign=" + signs;
        String 提交内容 = sign + body;

        try {
            String data = "data=" + RC4Util.encryRC4String(body, WY_RC4KEY, "UTF-8");
            String content = RC4Util.decryRC4(UrlPost(提交内容, data + "&value=" + random), WY_RC4KEY, "UTF-8");

            JSONObject jsonObject = new JSONObject(content);
            String code = jsonObject.getString("yc6172a55672b075c2f945162a3474c82");
            String message = jsonObject.getString("b400412eef21edfc7000e1e3d9848285b");
            String check = jsonObject.optString("x6d7026dd1c6b07ada40d3592e5f75d1e");
            long serverTime = jsonObject.optLong("b8546e4217a67b601b7f33d8de0081a45");

            Handler mainHandler = new Handler(Looper.getMainLooper()); // 确保在主线程操作

            if (code.equals("63248")) { // 登录成功
                JSONObject json = new JSONObject(message);
                if (!check.equals(encodeMD5(serverTime + WY_APPKEY + random))) {
                    mainHandler.post(() -> callback.onLoginFailure("非法操作"));
                } else if (Math.abs(serverTime - time) > 30) {
                    mainHandler.post(() -> callback.onLoginFailure("数据过期"));
                } else {
                    long vip = json.optLong("p66c1c9a956dc2c99aa34b90a5d00f391");
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTimeInMillis(vip * 1000);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    写入(context.getFilesDir().getAbsolutePath() + "/km", 卡密); // 写入到私有目录
                    String successMessage = "登录成功\n到期时间: " + df.format(gc.getTime());
                    mainHandler.post(() -> callback.onLoginSuccess(successMessage));
                }
            } else {
                mainHandler.post(() -> callback.onLoginFailure(message));
            }
        } catch (JSONException e) {
            new Handler(Looper.getMainLooper()).post(() -> callback.onLoginFailure("JSON解析错误: " + e.getMessage()));
        } catch (Exception e) {
            new Handler(Looper.getMainLooper()).post(() -> callback.onLoginFailure("网络或系统错误: " + e.getMessage()));
        }
    }).start();
}



// 获取远程变量
public static void getValue(final String key, final String kami, final Context context, final Callback callback) {
    if (kami == null || kami.isEmpty()) {
        callback.onError(new Exception("卡密不能为空"));
        return;
    }

    new Thread(() -> {
        String signUrl = WY_URL + "/api/?id=getvalue&app="; // 远程变量接口地址
        long currentTime = System.currentTimeMillis() / 1000; // 当前时间戳
        String random = UUID.randomUUID().toString().replace("-", "") + WY_APPKEY + 获取机器码(context); // 随机数
        String signs = encodeMD5("kami=" + kami + "&markcode=" + 获取机器码(context) + "&t=" + currentTime + "&" + WY_APPKEY); // 生成签名

        // 请求体
        String body = "&app=" + WY_APPID + "&kami=" + kami + "&markcode=" + 获取机器码(context) + "&t=" + currentTime + "&sign=" + signs + "&value=" + key;
        String 提交内容 = signUrl + body;

        try {
            // 加密请求体
            String data = "data=" + RC4Util.encryRC4String(body, WY_RC4KEY, "UTF-8");

            // 发送请求并解密响应
            String response = RC4Util.decryRC4(UrlPost(提交内容, data), WY_RC4KEY, "UTF-8");

            // 解析 JSON 响应
            JSONObject jsonObject = new JSONObject(response);
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("msg");
            String check = jsonObject.optString("check");
            long serverTime = jsonObject.optLong("time");

            Handler mainHandler = new Handler(Looper.getMainLooper()); // 确保在主线程操作

            if (code.equals("200")) { // 成功返回远程变量
                String localCheck = encodeMD5(serverTime + WY_APPKEY + key);
                if (!localCheck.equals(check)) {
                    mainHandler.post(() -> callback.onError(new Exception("校验失败")));
                } else {
                    mainHandler.post(() -> callback.onResult(message)); // 返回变量内容
                }
            } else {
                mainHandler.post(() -> callback.onError(new Exception(message))); // 返回错误信息
            }
        } catch (JSONException e) {
            new Handler(Looper.getMainLooper()).post(() -> callback.onError(new Exception("JSON解析错误: " + e.getMessage())));
        } catch (Exception e) {
            new Handler(Looper.getMainLooper()).post(() -> callback.onError(new Exception("网络或系统错误: " + e.getMessage())));
        }
    }).start();
}




    /**
     * 解绑方法
     */
    public static void 解绑(final String 卡密, final Context context, final Callback callback) {
        new Thread(() -> {
            String sign = WY_URL + "/api/?id=kmdismiss"; // 卡密解绑接口
            String random = UUID.randomUUID().toString().replace("-", "") + WY_APPKEY + 获取机器码(context);
            long time = System.currentTimeMillis() / 1000;
            String signs = encodeMD5("kami=" + 卡密 + "&markcode=" + 获取机器码(context) + "&t=" + time + "&" + WY_APPKEY);

            String body = "&app=" + WY_APPID + "&kami=" + 卡密 + "&markcode=" + 获取机器码(context) + "&t=" + time + "&sign=" + signs;
            String 提交内容 = sign + body;

            try {
                String data = "data=" + RC4Util.encryRC4String(body, WY_RC4KEY, "UTF-8");
                String content = RC4Util.decryRC4(UrlPost(提交内容, data + "&value=" + random), WY_RC4KEY, "UTF-8");

                JSONObject jsonObject = new JSONObject(content);
                String code = jsonObject.getString("code");
                String message = jsonObject.getString("msg");

                if (code.equals("200")) { // 解绑成功
                    callback.onResult("解绑成功: " + message);
                } else {
                    callback.onResult("解绑失败: " + message);
                }
            } catch (Exception e) {
                callback.onError(e);
            }
        }).start();
    }

    /**
     * 获取更新信息
     */
    public static void 更新(final Context context, final VersionCallback callback) {
    new Thread(new Runnable() {
        @Override
        public void run() {
            String sign = "/api/?app=" + WY_APPID + "&id=ini"; // 更新接口
            try {
                // 请求服务器获取更新信息
                String content = RC4Util.decryRC4(UrlPost(WY_URL + sign, ""), WY_RC4KEY, "UTF-8");
                JSONObject jsonObject = new JSONObject(content);
                String data = jsonObject.getString("msg");
                JSONObject json = new JSONObject(data);

                // 提取最新版本号
                String version = json.getString("version");

                // 通过回调接口返回版本号
                if (callback != null) {
                    callback.onVersionReceived(version);
                }
            } catch (Exception e) {
                // 在发生异常时通过回调接口返回错误信息
                if (callback != null) {
                    callback.onError(e);
                }
            }
        }
    }).start();
}

/**
 * 回调接口，用于返回服务器版本号或处理错误信息
 */
public interface VersionCallback {
    void onVersionReceived(String version); // 返回服务器版本号
    void onError(Exception e);              // 返回错误信息
}


    /**
     * 公告方法
     */
    public static void 公告(final Context context, final Callback callback) {
        new Thread(() -> {
            String sign = "/api/?app=" + WY_APPID + "&id=notice"; // 公告接口
            try {
                String content = RC4Util.decryRC4(UrlPost(WY_URL + sign, ""), WY_RC4KEY, "UTF-8");
                JSONObject jsonObject = new JSONObject(content);
                String data = jsonObject.getString("msg");
                JSONObject json = new JSONObject(data);

                String app_gg = json.optString("app_gg");
                callback.onResult("公告: " + app_gg);
            } catch (Exception e) {
                callback.onError(e);
            }
        }).start();
    }

    // 工具方法
    public static String 获取机器码(Context context) {
        return android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }

    public static String encodeMD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
            byte[] messageDigest = md5.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", b));
            }
            return hexString.toString().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String UrlPost(String url, String byteString) {
        StringBuilder result = new StringBuilder();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setReadTimeout(9000);

            OutputStream os = connection.getOutputStream();
            os.write(byteString.getBytes());
            os.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void 写入(String path, String content) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
