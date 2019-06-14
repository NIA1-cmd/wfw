package com.oncloudsoft.sdk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.adapter.BankDetaleAdapter;
import com.oncloudsoft.sdk.adapter.CarAdapter;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.bank.BankData;
import com.oncloudsoft.sdk.entity.bank.Xxxq;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.view.BaseTitleView;
import com.oncloudsoft.sdk.yunxin.session.extension.CustomAttachmentType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Administrator on 2018/12/22.
 */

public class BankDetaleActivity extends BaseActivity {
    RecyclerView recyclerView;

    BaseTitleView titleView;
    private BankData bankData;
    private BankDetaleAdapter adapter;
    private String msgId;
    private List<Xxxq> list;
    private com.oncloudsoft.sdk.entity.car.BankData bankData1;
    private String type;
    private CarAdapter carAdapter;
    private String title;

    public static void start(Context activity, String msgId, String title){
        Intent intent=new Intent();
        intent.setClass(activity, BankDetaleActivity.class);
        intent.putExtra("msgId",msgId);
        intent.putExtra("titleMessage",title);
        activity.startActivity(intent);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_detale);
        recyclerView= findViewById(R.id.recycler);
        titleView= findViewById(R.id.title);
        msgId = getIntent().getStringExtra("msgId");
        title = getIntent().getStringExtra("titleMessage");
        titleView.setTitleText(title);
        titleView.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        initData();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (type.equals(CustomAttachmentType.sys_bank)) {
            adapter = new BankDetaleAdapter(this, bankData);
            recyclerView.setAdapter(adapter);
        }
        else if (type.equals(CustomAttachmentType.sys_car)) {
            carAdapter = new CarAdapter(this, bankData1);
            recyclerView.setAdapter(carAdapter);
        }
    }

    private void initData() {
        MyOkhttpClient.getOkhttpInstance().sentGet(BankDetaleActivity.this, Global.URL_SYSTEM_DELETE, HttpParams.HttpParams().add("id", msgId).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) {
                type = jsonParser(json);
                if (type.equals(CustomAttachmentType.sys_bank)) {
                    bankData = JSON.parseObject(json, BankData.class);
                }
                else if (type.equals(CustomAttachmentType.sys_car)) {
                    bankData1 = JSON.parseObject(json, com.oncloudsoft.sdk.entity.car.BankData.class);
                }
                ActivityHelper.getInstance().getUiThread(BankDetaleActivity.this, new Runnable() {
                    @Override
                    public void run() {
                        initView();
                    }
                });
            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);
    }

    private String jsonParser(String json) {
        String type = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObject1 = jsonObject.getJSONObject("jbxx");
            type = jsonObject1.getString("xxlx");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return type;
    }
}
