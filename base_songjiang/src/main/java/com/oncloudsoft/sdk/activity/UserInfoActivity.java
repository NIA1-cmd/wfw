package com.oncloudsoft.sdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.UserInfoData;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.utils.OnSingleClickListener;
import com.oncloudsoft.sdk.view.BaseTitleView;
import com.oncloudsoft.sdk.yunxin.session.SessionHelper;

import org.json.JSONException;

/**
 * 作者 caomingduo
 * 创建时间 2019/3/4 15:59
 * 描述 用户个人信息页面https://pro.modao.cc/app/bd112389ed678903bff622d3cebedd7625f69bb7#screen=s8fde6892bf2c6b28547676
 */

public class UserInfoActivity extends BaseActivity {
    private ImageView ivPhoto;
    private TextView tvName;
    private TextView tvType;
    private TextView tvSfz;
    private TextView tvAddress;
    private TextView tvTelphone;
    private LinearLayout linearSend;
    private BaseTitleView btTitle;


    private String accid;
    private UserInfoData userInfoData;
    private String caseId;

    public static void startUserInfoActivity(Activity activity, String id, String ajbs) {
        Intent intent = new Intent(activity, UserInfoActivity.class);
        intent.putExtra(Global.ACCID, id);
        intent.putExtra(Global.CASEID, ajbs);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        init();
        accid = getIntent().getStringExtra(Global.ACCID);
        caseId = getIntent().getStringExtra(Global.CASEID);
        if (accid.indexOf("fg") != -1) {
            tvSfz.setVisibility(View.GONE);
            tvTelphone.setVisibility(View.GONE);
        }
        if (caseId.equals("")) {
            tvTelphone.setVisibility(View.GONE);
        }
        setStatusBarLightcolor(this, R.color.white);
        getUserInfo();

        btTitle.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    private void init() {
        ivPhoto = findViewById(R.id.iv_photo);
        tvName = findViewById(R.id.tv_name);
        tvType = findViewById(R.id.tv_type);
        tvSfz = findViewById(R.id.tv_sfz);
        tvAddress = findViewById(R.id.tv_address);
        tvTelphone = findViewById(R.id.tv_telphone);
        linearSend = findViewById(R.id.linear_send);
        btTitle = findViewById(R.id.bt_title);
        linearSend.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
                SessionHelper.startP2PSession(UserInfoActivity.this, accid);
            }
        });

    }


    public void getUserInfo() {
        MyOkhttpClient.getOkhttpInstance().sentGet(UserInfoActivity.this, Global.URL_USER_INFO, HttpParams.HttpParams().add("yxAccid", accid).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) throws JSONException {
                userInfoData = JSON.parseObject(json, UserInfoData.class);
                ActivityHelper.getInstance().getUiThread(UserInfoActivity.this, new Runnable() {
                    @Override
                    public void run() {
                        update();
                    }
                });
            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);


    }

    private void update() {
        try {
            String name, telphone, address, sfz, type;
            String dianHua;
            Drawable drawable = getDrawableById(R.drawable.icon_team_fg);
            if (accid.indexOf("fg") != -1) {
                name = userInfoData.getFg().getFgmc();
                type = "职务:  " + userInfoData.getFg().getZw();
                address = "单位:  " + userInfoData.getFg().getFymc();
                drawable = getDrawableById(R.drawable.icon_team_fg);
            } else {
                name = userInfoData.getDsr().getDsrmc();
                String ssdwbs = userInfoData.getDsr().getSsdwbs();
                sfz = userInfoData.getDsr().getDsrsfz();


                type = "类        型:  " + userInfoData.getDsr().getDsrlx();
                telphone = userInfoData.getDsr().getDsrdh();

                address = userInfoData.getDsr().getDsrdz();
                tvTelphone.setVisibility(View.VISIBLE);
                tvAddress.setVisibility(View.VISIBLE);
                tvSfz.setVisibility(View.VISIBLE);

                dianHua = telphone == null || TextUtils.isEmpty(telphone) ? "暂无" : telphone;
                address = "地        址:  " + (address == null || TextUtils.isEmpty(address) ? "暂无" : address);


                tvTelphone.setText("联系电话:  " + dianHua);
                tvSfz.setText("证件号码:  " + sfz);
                if (ssdwbs.equals("0")) {
                    //申请人
                    drawable = getDrawableById(R.drawable.icon_team_sqr);
                } else if (ssdwbs.equals("1")) {
                    //被执行人
                    drawable = getDrawableById(R.drawable.icon_team_bzxr);
                }
            }
            tvAddress.setText(address);
            tvType.setText(type);
            tvName.setText(name);
            ivPhoto.setImageDrawable(drawable);

        } catch (Exception e) {
            ActivityHelper.getInstance().showToast(UserInfoActivity.this, "数据异常");
        }

    }
}
