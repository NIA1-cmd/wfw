package com.oncloudsoft.sdk.app.config;

import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.utils.SharedPreferencesUtils;

/**
 * 作者 黄继栋
 * 创建时间 2019/4/3 10:32
 * 描述 环境管理
 */
public class ConfigUtils {

    /*
     * 1 测试环境
     * 2 线上环境
     * */
    public static final int PATH_CS = 1;
    public static final int PATH_XS = 2;


    //摇一摇开关,一般设置成false
    public static final boolean OpenSheck = true;



//    /**
//     * 获取当前请求的主地址
//     *
//     * @return
//     */
//    public static String getBaseUrl() {
//        String value = Global.baseUrl.URL_BASE_TEST;//默认是测试地址
//        switch (getCurrentPath()) {
//            case PATH_CS://测试地址
//                value = Global.baseUrl.URL_BASE_TEST;
//                break;
//            case PATH_XS://线上地址
//                value = Global.baseUrl.URL_BASE_PRODUCT;
//                break;
//        }
//        return value;
//    }

//    /**
//     * 获取当前环境的名称
//     *
//     * @return
//     */
//    public static String getCurrentEnvironment() {
//        String value = "测试环境";//默认是测试地址
//        switch (getCurrentPath()) {
//            case PATH_CS://测试地址
//                value = "测试环境";
//                break;
//            case PATH_XS://线上地址
//                value = "线上环境";
//                break;
//        }
//        return value;
//    }

//    /**
//     *
//     * @return
//     */
//    public static int getCurrentPath(){
//        return OpenSheck ? (int) SharedPreferencesUtils.getParam(Global.SP_PATH, PATH_CS) : PATH_XS;
//    }


}
