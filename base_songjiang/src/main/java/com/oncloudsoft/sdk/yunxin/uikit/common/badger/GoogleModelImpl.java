//package com.oncloudsoft.sdk.yunxin.uikit.common.badger;
//
//import android.app.Activity;
//import android.content.Intent;
//
///**
// *
// */
//public class GoogleModelImpl implements IconBadgeNumModel {
//    private static final String NOTIFICATION_ERROR = "google not support before API O";
//
//
//    @Override
//    public void setIconBadgeNum(Activity activity, int count) {
//        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
//            try {
//                throw new Exception(NOTIFICATION_ERROR);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
//        intent.putExtra("badge_count", count);
//        intent.putExtra("badge_count_package_name", activity.getPackageName());
//        intent.putExtra("badge_count_class_name", Badger.getLaunchIntentForPackage(activity)); // com.test. badge.MainActivity is your apk main activity
//
////        if (Utils.getInstance().canResolveBroadcast(context, intent)) {
//        activity.sendBroadcast(intent);
////            throw new Exception(UNABLE_TO_RESOLVE_INTENT_ERROR_ + intent.toString());
////        }
//    }
//}
