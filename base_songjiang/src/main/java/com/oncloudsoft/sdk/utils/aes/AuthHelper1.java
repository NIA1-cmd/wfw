package com.oncloudsoft.sdk.utils.aes;


import android.util.Log;

/**
 * @author zhangsi
 * @date 2019-06-08 9:46
 */
public class AuthHelper1 {

    private static final String KEY = "qRYapRXCCreSOIO_JHP01iyEdNepQkJX";

    public static String encode(String fgbs, long time) {

        if (fgbs == null || "".equals(fgbs.trim())) return null;

        fgbs = time + fgbs;

        byte[] cipherBytes = AesKit.encrypt(fgbs, KEY);

        return Base64Kit.encode(cipherBytes);
    }

    public static String decode(String base64) {

        byte[] bytes = Base64Kit.decode(base64);

        return AesKit.decryptToStr(bytes, KEY);
    }


//    public static void main(String[] args) {
//
//        String fgbs = "21A000121200160";
//
//        String encode = encode(fgbs);
//
//        System.out.println(encode);
//
//        String decode = decode(encode);
//
//        System.out.println(decode);
//    }
}
