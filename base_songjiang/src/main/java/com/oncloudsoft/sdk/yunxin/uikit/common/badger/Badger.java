package com.oncloudsoft.sdk.yunxin.uikit.common.badger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.oncloudsoft.sdk.event.SongJiangEventData;
import com.oncloudsoft.sdk.utils.MyLog;
import com.oncloudsoft.sdk.yunxin.uikit.api.NimUIKit;
import com.oncloudsoft.sdk.yunxin.uikit.common.framework.infra.Handlers;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * APP图标未读数红点更新接口
 * https://github.com/leolin310148/ShortcutBadger
 * <p>
 * Created by huangjun on 2017/7/25.
 */

public class Badger {
    private static final String TAG = "Badger";

    private static Handler handler;

    private static boolean support = false;

    static {
        support = Build.VERSION.SDK_INT < Build.VERSION_CODES.O;
    }

    //    public static void updateBadgerCount(final int unreadCount) {
//        if (!support) {
//            return; // O版本及以上不再支持
//        }
//
//        if (handler == null) {
//            handler = Handlers.sharedInstance().newHandler("Badger");
//        }
//
//        handler.removeCallbacksAndMessages(null);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                int badgerCount = unreadCount;
//                if (badgerCount < 0) {
//                    badgerCount = 0;
//                } else if (badgerCount > 99) {
//                    badgerCount = 99;
//                }
//
//                boolean res = ShortcutBadger.applyCount(NimUIKit.getContext(), badgerCount);
//                if (!res) {
//                    support = false; // 如果失败就不要再使用了!
//                }
//                Log.i(TAG, "update badger count " + (res ? "success" : "failed"));
//            }
//        }, 200);
//    }
    public static void setBadger(int count) {
        SongJiangEventData songJiangEventData = new SongJiangEventData();
        songJiangEventData.setType(99);
        songJiangEventData.setUnReadCount(count);
        EventBus.getDefault().postSticky(songJiangEventData);


    }

    public static String getLaunchIntentForPackage(Context context) {
        return context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
    }

    static final String UNABLE_TO_RESOLVE_INTENT_ERROR_ = "unable to resolve intent: ";

    public static boolean canResolveBroadcast(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> receivers = packageManager.queryBroadcastReceivers(intent, 0);
        return receivers != null && receivers.size() > 0;
    }

}
