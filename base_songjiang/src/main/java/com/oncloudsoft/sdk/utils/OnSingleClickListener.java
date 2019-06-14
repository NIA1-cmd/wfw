package com.oncloudsoft.sdk.utils;

import android.view.View;

/**
 * 作者 黄继栋
 * 创建时间 2019/3/19 10:52
 * 描述  按钮的单点监听
 */

public abstract class OnSingleClickListener implements View.OnClickListener {

    private static long mLastClickTime;
    private static long timeInterval = 1000L;//双击的判断标准  相隔时间




    @Override
    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > timeInterval) {
            // 单次点击事件
            onSingleClick();
            mLastClickTime = nowTime;
        } else {

            MyLog.d("onViewFastClick");
//            // 快速点击事件
//            onFastClick();
        }
    }

    protected abstract void onSingleClick();



    /**
     * 判断该次点击是不是双击
     * @return  是否是双击
     */
    public static boolean isDoubleClick() {
        boolean flag = false;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - mLastClickTime) < timeInterval) {
            flag = true;
            MyLog.d("isDoubleClick");

        }
        mLastClickTime = currentClickTime;
        return flag;
    }
}
