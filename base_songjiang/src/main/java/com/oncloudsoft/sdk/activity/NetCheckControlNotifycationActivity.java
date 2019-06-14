package com.oncloudsoft.sdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.adapter.NetCheckControlAdapter;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.NetCheckControlData;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.view.BaseTitleView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述：  网络查控告知书  通过群聊中总对总反馈进入
 * 作者：黄继栋
 * 创建时间：2019/4/30
 */
public class NetCheckControlNotifycationActivity extends BaseActivity {

    private TextView tvContent;
    private BaseTitleView btTitle;
    private RecyclerView rvNewList;
    private RecyclerView rvHistoryList;


    private NetCheckControlAdapter netCheckControlnewAdapter;
    private NetCheckControlAdapter netCheckControlhistoryAdapter;
    private List<NetCheckControlData.NetCheckControl> newList = new ArrayList<>();
    private List<NetCheckControlData.NetCheckControl> historyList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_netcheckcontrol_notifycation);
        findViews();

        setStatusBarLightcolor(NetCheckControlNotifycationActivity.this, R.color.white);


        rvNewList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvHistoryList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        netCheckControlnewAdapter = new NetCheckControlAdapter(newList, NetCheckControlNotifycationActivity.this);
        netCheckControlhistoryAdapter = new NetCheckControlAdapter(historyList, NetCheckControlNotifycationActivity.this);
        rvNewList.setAdapter(netCheckControlnewAdapter);
        rvHistoryList.setAdapter(netCheckControlhistoryAdapter);

        btTitle.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        request();


    }

    private void findViews() {
        tvContent = findViewById(R.id.tv_content);
        btTitle = findViewById(R.id.bt_title);
        rvNewList = findViewById(R.id.rv_new_list);
        rvHistoryList = findViewById(R.id.rv_history_list);
    }

    private void request() {
        MyOkhttpClient.getOkhttpInstance().sentGet(NetCheckControlNotifycationActivity.this, Global.URL_NETCHAECK_CONTROL, HttpParams.HttpParams().add("id", getIntent().getStringExtra(Global.ID)).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) throws JSONException {
                NetCheckControlData netCheckControlData = JSON.parseObject(json, NetCheckControlData.class);
                newList.clear();
                newList.addAll(netCheckControlData.getNewList());
                historyList.clear();
                historyList.addAll(netCheckControlData.getHistory());
                NetCheckControlData.Content tsjl = netCheckControlData.getTsjl();

                ActivityHelper.getInstance().getUiThread(NetCheckControlNotifycationActivity.this, new Runnable() {
                    @Override
                    public void run() {
                        tvContent.setText("现将自" + tsjl.getLarq() + "至" + tsjl.getTssj() + ",法院已经收到反馈的执行案件财产查询情况公开告知如下：被执行人" + tsjl.getBzxrmc() + "，反馈存款" + tsjl.getCunKuan() + "元，查到房产" + tsjl.getFangChan() + "处，车辆" + tsjl.getCheLiang() + "辆。详细情况如下：");

                        netCheckControlnewAdapter.notifyDataSetChanged();
                        netCheckControlhistoryAdapter.notifyDataSetChanged();
                    }
                });


            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);

    }

    public static void start(Activity activity, String id) {
        Intent intent = new Intent();
        intent.setClass(activity, NetCheckControlNotifycationActivity.class);
        intent.putExtra(Global.ID, id);
        activity.startActivity(intent);

    }
}
