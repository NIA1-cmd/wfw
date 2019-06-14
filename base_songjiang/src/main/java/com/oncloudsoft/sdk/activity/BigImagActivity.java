package com.oncloudsoft.sdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.utils.GlidelUtil;

public class BigImagActivity extends BaseActivity {

    private ImageView mIv;


    public static void startBigImg(Activity activity, String url) {
        Intent intent = new Intent();
        intent.putExtra(Global.URL, url);
        intent.setClass(activity, BigImagActivity.class);
        activity.startActivity(intent);


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigimg);
        mIv = findViewById(R.id.iv_bigimg);

        String stringExtra = getIntent().getStringExtra(Global.URL);
        GlidelUtil.loadImag(BigImagActivity.this, stringExtra, mIv);
        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
    }
}
