package com.oncloudsoft.sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * @author lichong@7moor.com
 * @version V1.0
 * @Description:
 * @date 2016/11/30  10:46
 * @powered by 容联七陌
 */

public class NetWorkUtils {
    private static final String TAG = NetWorkUtils.class.getSimpleName();
    /**
     * 检测网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        boolean isConn=false;
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null&&mNetworkInfo.isConnected()) {
                isConn=true;
            }else{
                Log.w(TAG,"无网络连接，请设置网络！");
            }
        }

        return isConn;
    }
}
