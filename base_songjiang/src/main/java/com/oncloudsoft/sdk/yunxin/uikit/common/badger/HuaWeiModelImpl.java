//package com.oncloudsoft.sdk.yunxin.uikit.common.badger;
//
//import android.app.Activity;
//import android.net.Uri;
//import android.os.Bundle;
//
//
///**
// * https://developer.huawei.com/consumer/cn/devservice/doc/30802
// * <p>
// * max:99
// */
//public class HuaWeiModelImpl implements IconBadgeNumModel {
//
//
//    @Override
//    public void setIconBadgeNum(Activity activity, int count) {
//        Bundle bunlde = new Bundle();
//        bunlde.putString("package", activity.getPackageName()); // com.test.badge is your package name
//        bunlde.putString("class", getLaunchIntentForPackage(activity)); // com.test. badge.MainActivity is your apk main activity
//        bunlde.putInt("badgenumber", count);
//        activity.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde);
//
//    }
//
//}
