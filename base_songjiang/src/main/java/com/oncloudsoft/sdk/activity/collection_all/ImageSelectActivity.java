package com.oncloudsoft.sdk.activity.collection_all;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.adapter.ImageSelectAdapter;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.Media;
import com.oncloudsoft.sdk.view.BaseTitleView;

import java.io.Serializable;
import java.util.List;

/**
 * 作者 Mingduo Cao
 * 创建时间 2019/3/14 11:11
 * <p>
 * 描述
 */

public class ImageSelectActivity extends BaseActivity {
    private BaseTitleView btTitle;
    private ImageView ivEmpty;
    private TextView tvEmpty;
    private RelativeLayout rlEmpty;
    private RecyclerView rvBase;
    private LinearLayout llParent;
    private List<Media> list;

    public static void startImageSelect(Activity activity, List<Media> mediaList) {
        Intent intent = new Intent();
        intent.setClass(activity, ImageSelectActivity.class);
        intent.putExtra(Global.CONTENT, (Serializable) mediaList);
        activity.startActivityForResult(intent, 100);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_baselayout);
        initViews();
//        ButterKnife.bind(this);
        setStatusBarLightcolor(this, R.color.white);
        btTitle.setVisibility(View.VISIBLE);
        btTitle.setTitleType(BaseTitleView.WHITE);
        btTitle.setTitleText("预览");
        btTitle.setRightTexts("完成");
        btTitle.setRightTextVisibility(View.VISIBLE);
        btTitle.setRightTextColor(getResources().getColor(R.color.black));
        btTitle.setOnLeftTextClickListener(new BaseTitleView.onLeftTextClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        btTitle.setOnRightTextClickListener(new BaseTitleView.onRightTextClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent();
                intent.putExtra("data", (Serializable) list);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        list = (List<Media>) getIntent().getSerializableExtra(Global.CONTENT);
        ImageSelectAdapter adapter = new ImageSelectAdapter(list, this, rvBase, 1, false);
        rvBase.setLayoutManager(new GridLayoutManager(this, 4));
        rvBase.setAdapter(adapter);

    }

    private void initViews() {
        btTitle = findViewById(R.id.bt_title);
        ivEmpty = findViewById(R.id.iv_empty);
        tvEmpty = findViewById(R.id.tv_empty);
        rlEmpty = findViewById(R.id.rl_empty);
        rvBase = findViewById(R.id.rv_base);
        llParent = findViewById(R.id.ll_parent);
    }
}
