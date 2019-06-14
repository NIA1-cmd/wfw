//package com.oncloudsoft.sdk.yunxin.uikit.common.badger;
//
//import android.app.Activity;
//import android.content.Intent;
//
//import static com.oncloudsoft.sdk.yunxin.uikit.common.badger.Badger.UNABLE_TO_RESOLVE_INTENT_ERROR_;
//import static com.oncloudsoft.sdk.yunxin.uikit.common.badger.Badger.canResolveBroadcast;
//import static com.oncloudsoft.sdk.yunxin.uikit.common.badger.Badger.getLaunchIntentForPackage;
//
///**
// * 没有找到官方文档说明，只有网上的方法
// * <p>
// * Galaxy S8/SM-G9500/android 8.0
// * Galaxy Galaxy Note8/SM-N9500/android 8.0
// */
//public class SamsungModelImpl implements IconBadgeNumModel {
//    private static final String NOTIFICATION_ERROR = "not support : samsung";
//
//
//
//    @Override
//    public void setIconBadgeNum(Activity activity, int count) throws Exception {
//        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
//        intent.putExtra("badge_count", count);
//        intent.putExtra("badge_count_package_name", activity.getPackageName());
//        intent.putExtra("badge_count_class_name",getLaunchIntentForPackage(activity));
//
//        if (canResolveBroadcast(activity, intent)) {
//            activity.sendBroadcast(intent);
//        } else {
//            throw new Exception(UNABLE_TO_RESOLVE_INTENT_ERROR_ + intent.toString());
//        }
//    }
//}
