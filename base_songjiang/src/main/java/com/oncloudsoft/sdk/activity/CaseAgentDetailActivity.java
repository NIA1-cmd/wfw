package com.oncloudsoft.sdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.approval.AgentData;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.view.BaseItemTextView;
import com.oncloudsoft.sdk.view.BaseTitleView;

import org.json.JSONException;

public class CaseAgentDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvCaseNumber;

    private BaseItemTextView mBlExecutedBody;
    private BaseItemTextView mBlExecutedState;
    private BaseItemTextView mBlExecutedNumber;

    private BaseItemTextView mBlAgentType;
    private BaseItemTextView mBlApply;
    private BaseItemTextView mBlApplyNumber;
    private BaseItemTextView mBlApplyUnit;
    private BaseTitleView base_title;
    private BaseItemTextView mBlApplyState;
    private LinearLayout mLLHanlder;

    private Button mBtPass;
    private Button mBtrefuse;

    private TextView mTvState;

    private String id;//代理Id

    public static void startAgnetDetail(Activity activity, String agentId) {
        Intent intent = new Intent();
        intent.setClass(activity, CaseAgentDetailActivity.class);
        intent.putExtra(Global.ID, agentId);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_caseagentdetail);
        base_title = findViewById(R.id.base_title);
        mBlExecutedBody = findViewById(R.id.bt_executebody);
        mBlExecutedState = findViewById(R.id.bt_executestate);
        mBlExecutedNumber = findViewById(R.id.bt_executenumber);
        mBlAgentType = findViewById(R.id.bt_agenttype);
        mBlApply = findViewById(R.id.bt_applyname);
        mBlApplyNumber = findViewById(R.id.bt_idnumber);
        mBlApplyUnit = findViewById(R.id.bt_unitname);
        mBlApplyState = findViewById(R.id.bt_connection);
        mTvCaseNumber = findViewById(R.id.tv_casename);
        mLLHanlder = findViewById(R.id.ll_state);
        mBtPass = findViewById(R.id.bt_pass);
        mBtrefuse = findViewById(R.id.bt_refuse);
        mTvState = findViewById(R.id.tv_state);
        mBtPass.setOnClickListener(this);
        mBtrefuse.setOnClickListener(this);
        id = getIntent().getStringExtra(Global.ID);
        base_title.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        request();
    }

    private void request() {
        MyOkhttpClient.getOkhttpInstance().sentGet(CaseAgentDetailActivity.this, Global.URL_AGENTDETAIL_URL, HttpParams.HttpParams().add("id", id).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) throws JSONException {
                final AgentData.AgentDetailData detailData = JSON.parseObject(json, AgentData.AgentDetailData.class);

                ActivityHelper.getInstance().getUiThread(CaseAgentDetailActivity.this, new Runnable() {
                    @Override
                    public void run() {


                        upDataUI(detailData);

                    }
                });


            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_pass) {
            handlerCase("1");

        }
        else if (i == R.id.bt_refuse) {
            handlerCase("3");
        }
    }

    private void upDataUI(AgentData.AgentDetailData agentDetailData) {
        if (agentDetailData==null){
            ActivityHelper.getInstance().showToast(CaseAgentDetailActivity.this,"数据异常");
            return;
        }
        mTvCaseNumber.setText(agentDetailData.getAh());
        mBlExecutedBody.setEndText(agentDetailData.getZxztmc());

        String state = "";
        switch (agentDetailData.getDsrssdw()) {//当事人诉讼地位 0 申请人 1 被执行人
            case "0"://申请人
                state =getStringById(R.string.apply_people);
                break;

            case "1"://被执行人
                state = getStringById(R.string.executed_people);

                break;
        }
        mBlExecutedState.setEndText(state);
        mBlExecutedNumber.setEndText(agentDetailData.getDsrsfzh());


        String agentTyoe = "";//代理类型 0 律师,基层法律服务者 1 近亲属或者工作人员 2 所在社区，单位以及有关社会团体推荐的公民
        switch (agentDetailData.getDllx()) {
            case "0":
                agentTyoe = "律师,基层法律服务者";

                break;

            case "1":
                agentTyoe = "近亲属或者工作人员";

                break;
            case "2":
                agentTyoe = "所在社区，单位以及有关社会团体推荐的公民";
                break;
        }


        mBlAgentType.setEndText(agentTyoe);


        mBlApply.setEndText(agentDetailData.getDlrmc());
        mBlApplyNumber.setEndText(agentDetailData.getDlrsfz());
        mBlApplyUnit.setEndText(agentDetailData.getDwmc());
        mBlApplyState.setEndText(agentDetailData.getLxfs());


        switch (agentDetailData.getDlzt()) {//代理状态 0 待审批 1 已通过 3 已拒绝
            case "0":
                mLLHanlder.setVisibility(View.VISIBLE);
                mTvState.setVisibility(View.GONE);


                break;
            case "1":
                mTvState.setVisibility(View.VISIBLE);
                mTvState.setText(getStringById(R.string.approval_passed));
                mLLHanlder.setVisibility(View.GONE);
                break;
            case "3":
                mTvState.setVisibility(View.VISIBLE);
                mTvState.setText(getStringById(R.string.approval_rejected));
                mLLHanlder.setVisibility(View.GONE);

                break;
        }


    }

    private void handlerCase(String type) {

        MyOkhttpClient.getOkhttpInstance().sentGet(CaseAgentDetailActivity.this, Global.URL_AGENTHANDLER, HttpParams.HttpParams().add("sp", type).add("id", id).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) throws JSONException {
                ActivityHelper.getInstance().showToast(CaseAgentDetailActivity.this, getStringById(R.string.operationed));

                finish();
            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);

    }
}
