package com.ld.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.alibaba.fastjson.JSONObject;
import com.ld.myapplication.t3yz.MyBase64;
import com.ld.myapplication.t3yz.MyData;
import com.ld.myapplication.t3yz.MyMD5;
import com.ld.myapplication.t3yz.MySignature;
import com.ld.myapplication.t3yz.MyUtils;
import com.ld.myapplication.t3yz.RC4;
import com.ld.myapplication.t3yz.SpUtils;
import com.ld.myapplication.t3yz.RSAUtils;
import de.robv.android.xposed.XposedBridge;
import java.security.PrivateKey;
import java.security.PublicKey;

public class t3 {

    // 验证相关变量
    private boolean MyState = false; // 验证卡密是否激活状态
    private String MyCamilo, Mytime, MyMachineCode, MyTheAnnouncement, MyUpdate, MyStamp, MyTheSignature, MyReturn;
    public t3(Context context) {
        MyBase64.setbase(MyData.apib);
        MyMachineCode = MyUtils.GetAllDeviceInformation();
        // 获取 APK 的签名 MD5 值
        MyTheSignature = MyMD5.CalcMD5(MySignature.getMD5(context));
        Log.d("日志：", String.valueOf(MyTheSignature));
    }

    /**
     * 动态解密方法
     */
    


    private static final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n"
            + "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCRPLJj8xKnOq0p9gaUu4Rth7QT\n"
            + "NW0DxCwPu8X8GzCGMBoEXJBQk53/vHWEAIKwAZ2g+HMdEIMluoBKFPmIM9KfzZ16\n"
            + "kIpesMe79LXq+8FNKntwcjsbB1nx0aTg9RJZKYZJIfTjGHIfFdWPUrSnOjebKuTd\n"
            + "G7K1+1tU5CtJwq3EkQIDAQAB\n"
            + "-----END PUBLIC KEY-----";

    private static final String PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----\n"
            + "MIICXgIBAAKBgQCRPLJj8xKnOq0p9gaUu4Rth7QTNW0DxCwPu8X8GzCGMBoEXJBQ\n"
            + "k53/vHWEAIKwAZ2g+HMdEIMluoBKFPmIM9KfzZ16kIpesMe79LXq+8FNKntwcjsb\n"
            + "B1nx0aTg9RJZKYZJIfTjGHIfFdWPUrSnOjebKuTdG7K1+1tU5CtJwq3EkQIDAQAB\n"
            + "AoGBAI2GbfcDiCu4+8Cl4yfPym3JDOqi5wYChUUXnwfZnuFDwjZ51I8QKahohsMI\n"
            + "lFDaYjXXpKSv0HxVTmniIn5csWJT8bCiDfygqocutvmbI/byXUM5twSfC1vjSR/z\n"
            + "qeI0rQhpIkCceWdqpPhZefOr8ZT4plb8qqyuQh4v969spiqZAkEA63nfmiJmVHnf\n"
            + "F/3R43AMCYArby9h/cGQGtHQef8Ur9N5nl2sDOPJrO9vBMm0JTo3VeeOKqQ0x2cy\n"
            + "SVznsWi+fwJBAJ3lVzvG+Ocy8wZmfy27uUAix8k1RmoA0bR+0mAtjHXabXxpnRw9\n"
            + "amQMAwwopV7vrT7P+uZ/zsAb0adgt4/+FO8CQH4de+B1ZBECZLXYvzzsmcXM23Zl\n"
            + "Djp2HNQAY4OzVkUFIjHrQWZ16WGxqugJj24Qy+o6Zi34XDwfzRL1qzGdQ8kCQQCX\n"
            + "QsCp7HhADGgShphpeIAEeA1KrTXSkQxUTHY/WwNHV8QxPoAbMJ6+T1+QA1RBOXyP\n"
            + "QvEKZf/ru/rBzP6s++yTAkEA2iDhU+U12/+GFOcJyjmPXSfjpYsHhu6HvnS5wOUZ\n"
            + "E8vxkVaPsSXw22Gu+WAIV+mS9z6KBPp9H+WaK6gsceD1yA==\n"
            + "-----END RSA PRIVATE KEY-----";

    /**
     * 动态解密方法，支持 Base64、RC4、RSA
     */
    private String dynamicDecrypt(String data) {
    try {
        // 检查是否是 JSON 原始数据
        if (data.startsWith("{") && data.endsWith("}")) {
            XposedBridge.log("数据是原始 JSON 格式，无需解密：" + data);
            return data; // 明文直接返回
        }

        // 尝试 Base64 解码
        try {
            String base64Decoded = MyBase64.decodeBase64str(data);
            if (base64Decoded.startsWith("{") && base64Decoded.endsWith("}")) {
                XposedBridge.log("Base64 解密成功：" + base64Decoded);
                return base64Decoded;
            }
        } catch (IllegalArgumentException e) {
            XposedBridge.log("Base64 解密失败：" + e.getMessage());
        }

        // 尝试 RSA 解密
        try {
            PublicKey PublicKey = RSAUtils.loadPublicKey(PRIVATE_KEY);
            String rsaDecoded = RSAUtils.decryptWithPublicKey(data, PublicKey);
            if (rsaDecoded.startsWith("{") && rsaDecoded.endsWith("}")) {
                XposedBridge.log("RSA 解密成功：" + rsaDecoded);
                return rsaDecoded;
            }
        } catch (Exception e) {
            XposedBridge.log("RSA 解密失败：" + e.getMessage());
        }

    } catch (Exception e) {
        XposedBridge.log("动态解密过程中发生未知错误：" + e.getMessage());
        XposedBridge.log(e);
    }

    XposedBridge.log("动态解密失败，返回原始数据：" + data);
    return data; // 如果所有解密失败，返回原始数据
}





    /**
     * 登录验证方法
     */
    public boolean login(Context context, String key) {
    MyCamilo = key;

    if (MyState) {
        showToast(context, "卡密已经激活，无需重复验证");
        XposedBridge.log("卡密已经激活，无需重复验证");
        return true;
    }

    new Thread(() -> {
        Long startTs = System.currentTimeMillis() / 1000;
        MyStamp = String.valueOf(startTs);
        try {
            // 使用 RSA 公钥加密数据
            PublicKey publicKey = RSAUtils.loadPublicKey(PUBLIC_KEY);

            String encryptedTime = RSAUtils.encryptWithPublicKey(MyStamp, publicKey);
            String encryptedCamilo = RSAUtils.encryptWithPublicKey(MyCamilo, publicKey);
            String encryptedMachineCode = RSAUtils.encryptWithPublicKey(MyMachineCode, publicKey);

            XposedBridge.log("加密后的时间戳 (Mytime): " + encryptedTime);
            XposedBridge.log("加密后的卡密 (Camilo): " + encryptedCamilo);
            XposedBridge.log("加密后的机器码 (MachineCode): " + encryptedMachineCode);

            // 构造加密后的请求参数
            String requestData = "kami=" + encryptedCamilo + "&imei=" + encryptedMachineCode + "&t=" + encryptedTime + "&" + MyData.apia + "&print=json";

            // 网络请求
            MyReturn = MyUtils.Post(MyData.api1, requestData);
            XposedBridge.log("服务器返回的原始数据：" + MyReturn);

            // 动态解密服务器返回的数据
            MyReturn = dynamicDecrypt(MyReturn);

            // 解析 JSON 数据
            JSONObject jsonObject = JSONObject.parseObject(MyReturn);
            XposedBridge.log("解析后的 JSON 数据：" + jsonObject.toJSONString());

            String code = jsonObject.getString("code");
            String endtime = jsonObject.getString("end_time");
            String timee = jsonObject.getString("time");

            if ("200".equals(code)) {
                int NumSmp = Math.abs(Integer.parseInt(timee) - Integer.parseInt(MyStamp));
                XposedBridge.log("服务器时间差：" + NumSmp + " 秒");

                if (NumSmp >= 0 && NumSmp <= 5) {
                    MyState = true;
                    SpUtils.setParam(context, "卡密", MyCamilo);
                    showToast(context, "登录成功！到期时间：" + endtime);
                    XposedBridge.log("登录成功！到期时间：" + endtime);
                } else {
                    MyState = false;
                    showToast(context, "请求超时：" + NumSmp + " 秒");
                    XposedBridge.log("登录失败，请求超时：" + NumSmp + " 秒");
                }
            } else {
                String msg = jsonObject.getString("msg");
                showToast(context, "登录失败：" + msg);
                XposedBridge.log("登录失败，服务器返回的错误消息：" + msg);
            }
        } catch (Exception e) {
            XposedBridge.log("网络请求或数据解析时发生异常：" + e.getMessage());
            XposedBridge.log(e);
            showToast(context, "服务器返回数据异常");
        }
    }).start();

    return false;
}


    /**
     * 显示公告
     */
//    public void showAnnouncement(Context context) {
//    new Thread(() -> {
//        MyTheAnnouncement = MyUtils.Post(MyData.api2, "&t=" + Mytime);
//        MyTheAnnouncement = dynamicDecrypt(MyTheAnnouncement);
//
//        new android.os.Handler(context.getMainLooper()).post(() -> new AlertDialog.Builder(context)
//                .setTitle("官网公告：")
//                .setMessage(MyTheAnnouncement)
//                .setPositiveButton("确定", null)
//                .create()
//                .show());
//    }).start();
//}
//
//public void checkUpdate(Context context) {
//    new Thread(() -> {
//        MyUpdate = MyUtils.Post(MyData.api3, "&t=" + Mytime);
//        MyUpdate = dynamicDecrypt(MyUpdate);
//
//        new android.os.Handler(context.getMainLooper()).post(() -> {
//            if ("1000".equals(MyUpdate)) {
//                showToast(context, "当前已是最新版");
//            } else {
//                showToast(context, "有新版本，请更新！");
//            }
//        });
//    }).start();
//}
//


    /**
     * 显示 Toast 的辅助方法
     */
    private void showToast(Context context, String message) {
        new android.os.Handler(context.getMainLooper()).post(() -> 
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        );
    }
}
