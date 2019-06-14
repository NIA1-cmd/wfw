package com.oncloudsoft.sdk.utils;

import android.util.Log;

import com.oncloudsoft.sdk.BuildConfig;
import com.oncloudsoft.sdk.app.config.ConfigUtils;

public class MyLog {


    /**
     * 保证版本是 debug 并且是在 测试环境下输出log
     * @return
     */
    private static boolean getLogOpen(){
        return !BuildConfig.API_HOST.equals("release")&& ConfigUtils.OpenSheck;

    }
    public static String TAG = "log_paramters";

    public static void e(String mes) {
        e(TAG, mes);
    }

    public static void e(String TAG, String mes) {
        if (getLogOpen()){
            Log.e(TAG, mes);

        }
    }


    public static void d(String mes) {
        d(TAG, mes);
    }

    public static void d(String TAG, String mes) {
        if (getLogOpen()){
            Log.d(TAG, mes);

        }
    }


    public static void i(String mes) {
        i(TAG, mes);
    }

    public static void i(String TAG, String mes) {

        if (getLogOpen()){
            Log.i(TAG, mes);

        }
    }
}
