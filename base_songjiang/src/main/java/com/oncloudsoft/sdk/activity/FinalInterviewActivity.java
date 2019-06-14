package com.oncloudsoft.sdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.adapter.FinalInterviewAdapter;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.FinalInterviewData;
import com.oncloudsoft.sdk.view.BaseTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 Mingduo Cao
 * 创建时间 2019/6/7 11:22
 * <p>
 * 描述 终本约谈
 */

public class FinalInterviewActivity extends BaseActivity implements FinalInterviewAdapter.finalCallback {
    private BaseTitleView btTitle;
    private ImageView ivEmpty;
    private TextView tvEmpty;
    private RelativeLayout rlEmpty;
    private RecyclerView rvBase;
    private LinearLayout llParent;
    private List<FinalInterviewData> list = new ArrayList();
    private FinalInterviewAdapter adapter;
    List<FinalInterviewData> list1 = new ArrayList<>();
    private Button btnCreate;

    public static void start(Activity activity, String caseId) {
        Intent intent = new Intent();
        intent.setClass(activity, FinalInterviewActivity.class);
        intent.putExtra(Global.CASEID, caseId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finallnter_view);
        findViews();
        initData();
        btTitle.setTitleText("终本约谈");
        btTitle.setVisibility(View.VISIBLE);
        btTitle.setOnLeftImageClickListener(this::finish);
        adapter = new FinalInterviewAdapter(this, list, this);
        rvBase.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvBase.setAdapter(adapter);
    }

    private void findViews() {
        btTitle = findViewById(R.id.bt_title);
        ivEmpty = findViewById(R.id.iv_empty);
        tvEmpty = findViewById(R.id.tv_empty);
        rlEmpty = findViewById(R.id.rl_empty);
        rvBase = findViewById(R.id.rv_base);
        llParent = findViewById(R.id.ll_parent);
        btnCreate = findViewById(R.id.btn_create);
    }

    private void initData() {
     /*   for (int i = 0; i < 10; i++) {
            FinalInterviewData finalInterviewData = new FinalInterviewData();
            finalInterviewData.setId("2");
            finalInterviewData.setTime("1559441422000");
            finalInterviewData.setName("终本约谈告知书");
            list.add(finalInterviewData);
        }*/
        rlEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText("未完成终本约谈前置执行措施，无法生成约谈笔录");
        btnCreate.setVisibility(View.GONE);
       /* MyOkhttpClient.getOkhttpInstance().sentGet(this, Global.URL_FINALINTERVIEW, HttpParams.HttpParams().add("ajbs", "").build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) {
                list.clear();
                list1 = JSONObject.parseArray(json, FinalInterviewData.class);
                list.addAll(list1);
                if (list.size() == 0) {
                    rlEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setText("未完成终本约谈前置执行措施，无法生成约谈笔录");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);*/
    }

    @Override
    public void finalCallback(int position, boolean b) {
        updateCheck(position, b);
    }

    private void updateCheck(int position, boolean b) {
        list1.clear();
        list1.addAll(list);
        for (int i = 0; i < list1.size(); i++) {
            boolean b1 = i == position ? b : false;
            list1.get(i).setCheck(b1);
        }
        list.clear();
        list.addAll(list1);
        adapter.notifyDataSetChanged();
    }
}
