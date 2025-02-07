package com.ld.myapplication.t3yz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MyUtils {

    //Post请求1，参数俩
    public static String Post(String ur, String byteString) {
        String fh = "";
        try {
            URL url = new URL(ur);
            HttpURLConnection HttpURLConnection = (HttpURLConnection) url.openConnection();
            HttpURLConnection.setReadTimeout(5000);
            HttpURLConnection.setRequestMethod("POST");
            OutputStream outputStream = HttpURLConnection.getOutputStream();

            outputStream.write(byteString.getBytes());
            BufferedReader BufferedReader = new BufferedReader(new InputStreamReader(HttpURLConnection.getInputStream()));
            String String = "";
            StringBuffer StringBuffer = new StringBuffer();
            while ((String = BufferedReader.readLine()) != null) {
                StringBuffer.append(String);
            }
            fh = StringBuffer.toString();


        } catch (IOException e) {
        }
        return fh;
    }

    //Post请求1，无参数
    public static String Post(String string) throws IOException {
        URL url = new URL(string);
        HttpURLConnection HttpURLConnection = (HttpURLConnection) url.openConnection();
        HttpURLConnection.setReadTimeout(5000);
        HttpURLConnection.setRequestMethod("POST");
        BufferedReader BufferedReader = new BufferedReader(new InputStreamReader(HttpURLConnection.getInputStream()));
        String String = "";
        StringBuffer StringBuffer = new StringBuffer();
        while ((String = BufferedReader.readLine()) != null) {
            StringBuffer.append(String);
        }
        return StringBuffer.toString();
    }


    //获取设备全部信息，且加密md5,当设备码
    public static String GetAllDeviceInformation() {
        String hardwareInfo = android.os.Build.BOARD + android.os.Build.BRAND + android.os.Build.DEVICE + android.os.Build.DISPLAY + android.os.Build.HOST + android.os.Build.ID + android.os.Build.MANUFACTURER + android.os.Build.MODEL + android.os.Build.PRODUCT + android.os.Build.TAGS + android.os.Build.TYPE + android.os.Build.USER;
        return MyMD5.CalcMD5(hardwareInfo);
    }

}
