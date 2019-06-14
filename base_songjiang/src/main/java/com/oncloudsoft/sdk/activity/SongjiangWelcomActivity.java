package com.oncloudsoft.sdk.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;

public class SongjiangWelcomActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songjiang_activity_welcom);
        init();



    }

    private void init() {
        //判断用户是否存在token和账户名
//
//        MyLog.d("welconactivity-->canAutoLogin():"+canAutoLogin()+"");
//
//        if (canAutoLogin()) {
//            SongjiangMainActivity.start(SongjiangWelcomActivity.this,"wel");
//        } else {
//            LoginActivity.start(SongjiangWelcomActivity.this);
//        }

        SongjiangMainActivity.start(SongjiangWelcomActivity.this);

        finish();

    }

//    /**
//     * 检查用户名和密码是否存在
//     * 已经登陆过，自动登陆
//     */
//    private boolean canAutoLogin() {
//
//
//
//        String account = (String) SharedPreferencesUtils.getParam(Global.PHONE, "");
//        String token = (String) SharedPreferencesUtils.getParam(Global.TOKEN, "");
//
//
//        MyLog.d("welconactivity-->canAutoLogin()-->account:"+account+"token"+token);
//
//        return !TextUtils.isEmpty(account) && !TextUtils.isEmpty(token);
//    }
}
