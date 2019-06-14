package com.oncloudsoft.sdk.yunxin.uikit.common;

import android.app.Activity;

import com.oncloudsoft.sdk.app.ActivityHelper;


public class ToastHelper {



    private ToastHelper(){

    }

    public static void showToast(Activity context , String text){
        ActivityHelper.getInstance().showToast(context,text);
    }

    public static void showToast(Activity context , int stringId){

        ActivityHelper.getInstance().showToast(context,context.getString(stringId));

    }


    public static void showToastLong(Activity context , String text ){

        ActivityHelper.getInstance().showToast(context,text);

    }

    public static void showToastLong(Activity context , int stringId){
        ActivityHelper.getInstance().showToast(context,context.getString(stringId));

    }


}
