package com.oncloudsoft.sdk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.fragment.CaseInfoFragment;
import com.oncloudsoft.sdk.view.BaseTitleView;


public class CaseInfoActivity extends BaseActivity {
    private BaseTitleView mBtTitle;
    //0是申请执行人  1是被执行人
    private int type = 0;
    private String ajfl;//案件分类 0 执行类案件 1 审判类案件     1的时候没有执行依据和执行标的
    private String ajlb;
   // private FrameLayout frameLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_caseinfo);

        setStatusBarLightcolor(CaseInfoActivity.this,R.color.message_bc);


        String caseId = getIntent().getStringExtra(Global.CASEID);
        ajfl = getIntent().getStringExtra(Global.AJFL);
        ajlb = getIntent().getStringExtra(Global.AJLB);
        //frameLayout = findViewById(R2.id.frameLayout);
        CaseInfoFragment caseInfoFragment = new CaseInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Global.CASEID,caseId);
        bundle.putString(Global.AJFL,ajfl);
        bundle.putString(Global.AJLB,ajlb);
        caseInfoFragment.setArguments(bundle);
        //获取管理者
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        //提交事务
        fragmentTransaction.add(R.id.frameLayout, caseInfoFragment).commit();

        mBtTitle = findViewById(R.id.bt_title);
        mBtTitle.setBg(getResources().getColor(R.color.message_bc));
        mBtTitle.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }


    public static void start(Context caseHandlingActivity, String caseId,String ajfl,String ajlb) {
        Intent intent = new Intent();
        intent.putExtra(Global.CASEID, caseId);
        intent.putExtra(Global.AJFL, ajfl);
        intent.putExtra(Global.AJLB, ajlb);
        intent.setClass(caseHandlingActivity, CaseInfoActivity.class);
        caseHandlingActivity.startActivity(intent);
    }
}
