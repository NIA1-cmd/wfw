package com.oncloudsoft.sdk.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class SreenUtil {

    public static int getWidth(Context context){
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
//        float density1 = dm.density;
//        int width3 =
//        int height3 = dm.heightPixels;

    }
    public static int getHeight(Context context){
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
//        float density1 = dm.density;
//        int width3 =
//        int height3 = dm.;

    }


}
