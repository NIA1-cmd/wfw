package com.oncloudsoft.sdk.app;


import com.oncloudsoft.sdk.entity.UserData;
import com.oncloudsoft.sdk.utils.SharedPreferencesUtils;

public class UserInfo {

    public static void setUserData(UserData userData) {
        SharedPreferencesUtils.setParam(Global.TOKEN, userData.getToken());
        SharedPreferencesUtils.setParam(Global.PHONE, userData.getFgmc());
        SharedPreferencesUtils.setParam(Global.YUNXIN_ACCONT, userData.getYxAccid());
        SharedPreferencesUtils.setParam(Global.YUNXIN_PASSWORD, userData.getYxToken());
        SharedPreferencesUtils.setParam(Global.FGZW, userData.getFgzw());
        SharedPreferencesUtils.setParam(Global.FYID, userData.getFyid());
        SharedPreferencesUtils.setParam(Global.FYMC, userData.getFymc());
        SharedPreferencesUtils.setParam(Global.FGMC, userData.getFgmc());

    }


    public static void cleanUserData() {
        SharedPreferencesUtils.setParam(Global.TOKEN, "");
        SharedPreferencesUtils.setParam(Global.PHONE, "");
        SharedPreferencesUtils.setParam(Global.YUNXIN_ACCONT,"");
        SharedPreferencesUtils.setParam(Global.YUNXIN_PASSWORD, "");
        SharedPreferencesUtils.setParam(Global.FGZW, "");
        SharedPreferencesUtils.setParam(Global.FYID, "");
        SharedPreferencesUtils.setParam(Global.FYMC, "");
        SharedPreferencesUtils.setParam(Global.FGMC, "");
    }


}
