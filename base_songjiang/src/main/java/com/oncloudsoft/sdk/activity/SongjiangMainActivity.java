package com.oncloudsoft.sdk.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.view.BaseTitleView;
import com.oncloudsoft.sdk.yunxin.session.activity.TeamListFragment;

/**
 * 首页--主页
 */
public class SongjiangMainActivity extends BaseActivity {
    private TeamListFragment teamListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songjiang_activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setStatusBarLightcolor(SongjiangMainActivity.this, R.color.maincolor_01);
        BaseTitleView baseTitleView = findViewById(R.id.bt_title);
        baseTitleView.setOnLeftImageClickListener(this::finish);
        initView();
        initPermission();
    }


    private void initPermission() {
        requestPermission(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_CONTACTS}, 0x0001);
    }

    private void initView() {
        teamListFragment = new TeamListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_container, teamListFragment);
        fragmentTransaction.show(teamListFragment);
        fragmentTransaction.commit();
    }


    public static void start(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, SongjiangMainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        teamListFragment.upDate();
    }


}
