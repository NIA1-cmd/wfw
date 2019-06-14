package com.oncloudsoft.sdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.app.Global;


/**
 * 作者 Mingduo Cao
 * 创建时间 2019/6/7 11:22
 * <p>
 * 描述 终本约谈详情
 */

public class FinalInterviewDetailsActivity extends BaseActivity{

    public static void start(Activity activity, String id) {
        Intent intent = new Intent();
        intent.setClass(activity, FinalInterviewDetailsActivity.class);
        intent.putExtra(Global.ID, id);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_details);

    }
}
