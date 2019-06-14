package com.oncloudsoft.sdk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.view.BaseTitleView;

/**
 * 作者 黄继栋
 * 创建时间 2019/1/9 10:48
 * 描述  办案指引中的
 */

public class GuideHandCasesActivity extends BaseActivity {
    private BaseTitleView btTitle;
    private TextView tvTitlename;
    private TextView tvContent;
    private TextView btBack;
    private View Line;


    public static void start(Context activity, @NonNull String title, String secTitle, String content) {
        Intent intent = new Intent();
        intent.putExtra(Global.TITLE, title);
        intent.putExtra(Global.TYPE, secTitle);
        intent.putExtra(Global.CONTENT, content);
        intent.setClass(activity, GuideHandCasesActivity.class);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidehandcases);
        findViews();
        String title = getIntent().getStringExtra(Global.TITLE);
        String secTitle = getIntent().getStringExtra(Global.TYPE);
        String content = getIntent().getStringExtra(Global.CONTENT);
        tvTitlename.setText(secTitle);
        btTitle.setTitleText(title);
        if (secTitle != null && !secTitle.equals("")) {
            tvTitlename.setVisibility(View.VISIBLE);
            Line.setVisibility(View.VISIBLE);
        } else {
            tvTitlename.setVisibility(View.GONE);
            Line.setVisibility(View.GONE);
        }
        tvContent.setText(content);

        btTitle.setOnLeftImageClickListener(this::finish);


    }

    private void findViews() {

        btTitle = findViewById(R.id.bt_title);
        tvTitlename = findViewById(R.id.tv_titlename);
        tvContent = findViewById(R.id.tv_content);
        btBack = findViewById(R.id.tv_back);
        Line = findViewById(R.id.ic_line);

        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }


}
