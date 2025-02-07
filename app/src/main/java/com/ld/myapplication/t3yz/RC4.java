package com.ld.myapplication.t3yz;

public class RC4 {

    /**
     * RC4 加密解密方法
     * @param data 待加密或解密的数据
     * @param key 加密密钥
     * @return 加密或解密后的结果
     */
    public static byte[] rc4(byte[] data, String key) {
        if (data == null || key == null) {
            throw new IllegalArgumentException("Data or key cannot be null");
        }

        byte[] keyBytes = key.getBytes();
        int[] s = new int[256];
        byte[] result = new byte[data.length];

        // 初始化 S 数组
        for (int i = 0; i < 256; i++) {
            s[i] = i;
        }

        // 使用密钥对 S 数组进行混淆
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + s[i] + keyBytes[i % keyBytes.length]) & 0xFF;
            swap(s, i, j);
        }

        // 加解密数据
        int i = 0, k = 0;
        for (int n = 0; n < data.length; n++) {
            i = (i + 1) & 0xFF;
            k = (k + s[i]) & 0xFF;
            swap(s, i, k);

            int t = (s[i] + s[k]) & 0xFF;
            result[n] = (byte) (data[n] ^ s[t]);
        }

        return result;
    }

    /**
     * RC4 加密解密方法（字符串）
     * @param data 待加密或解密的字符串
     * @param key 加密密钥
     * @return 加密或解密后的结果
     */
    public static String rc4(String data, String key) {
        if (data == null || key == null) {
            throw new IllegalArgumentException("Data or key cannot be null");
        }

        byte[] dataBytes = data.getBytes();
        byte[] resultBytes = rc4(dataBytes, key);
        return new String(resultBytes);
    }

    /**
     * 交换数组中的两个元素
     */
    private static void swap(int[] s, int i, int j) {
        int temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }

}
