package com.oncloudsoft.sdk.activity;

import android.app.Activity;
import android.content.Context;
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
import com.oncloudsoft.sdk.entity.PunishmentData;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.utils.OnSingleClickListener;
import com.oncloudsoft.sdk.view.BaseTitleView;

import org.json.JSONException;

import java.util.List;

/**
 * 作者 黄继栋
 * 创建时间 2019/1/23 15:41
 * 描述  惩戒详情
 */

public class PunishmentDetailActivity extends BaseActivity {

    private BaseTitleView btTitle;
    private TextView tvTitle;
    private TextView tvApply;
    private TextView tvExecuted;
    private TextView tvMatters;
    private TextView tvFactsReasons;
    private TextView tvLastapply;
    private TextView tvTime;
    private Button btRefuse;
    private Button btPass;
    private LinearLayout llState;
    private TextView tvState;
    private TextView tvRefuse;
    private LinearLayout llRefuse;
    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punishmentdetail);
        findViews();

        btTitle.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });


        setStatusBarLightcolor(PunishmentDetailActivity.this, R.color.white);
        id = getIntent().getStringExtra(Global.ID);
        queryData();

    }

    private void findViews() {
        btTitle = findViewById(R.id.bt_title);
        tvTitle = findViewById(R.id.tv_actiontitle);
        tvApply = findViewById(R.id.tv_apply);
        tvExecuted = findViewById(R.id.tv_executed);
        tvMatters = findViewById(R.id.tv_matters);
        tvFactsReasons = findViewById(R.id.tv_facts_reasons);
        tvLastapply = findViewById(R.id.tv_lastapply);
        tvTime = findViewById(R.id.tv_time);
        btRefuse = findViewById(R.id.bt_refuse);
        btPass = findViewById(R.id.bt_pass);
        llState = findViewById(R.id.ll_state);
        tvState = findViewById(R.id.tv_state);
        tvRefuse = findViewById(R.id.tv_refuse);
        llRefuse = findViewById(R.id.ll_refuse);

        btRefuse.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
                DialogActivity.showEditDialog(PunishmentDetailActivity.this, "拒绝事由", new DialogActivity.OnEditListener() {
                    @Override
                    public void onConfirm(Activity activity, String text) {
                        activity.finish();
                        handlerPunishment(text);
                    }
                });
            }
        });
        btPass.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
                handlerPunishment("");

            }
        });


    }

    public static void start(Context activity, String id) {
        Intent intent = new Intent();
        intent.setClass(activity, PunishmentDetailActivity.class);
        intent.putExtra(Global.ID, id);

        activity.startActivity(intent);


    }

    /**
     * 请求数据
     */
    private void queryData() {
        MyOkhttpClient.getOkhttpInstance().sentGet(PunishmentDetailActivity.this, Global.URL_PUNISHMENT_DETAIL,
                HttpParams.HttpParams().add("id", id).build(), new MyOkhttpClient.MyOkhttpCallBack() {
                    @Override
                    public void onRequestSccess(String json) throws JSONException {
                        PunishmentData.ItemPunishmentData punishmentData = JSON.parseObject(json, PunishmentData.ItemPunishmentData.class);
                        ActivityHelper.getInstance().getUiThread(PunishmentDetailActivity.this, new Runnable() {
                            @Override
                            public void run() {
                                upDataUi(punishmentData);
                            }
                        });
                    }

                    @Override
                    public boolean onRequestFaild(String mes) {
                        return false;
                    }
                }, true);
    }


    /**
     * 处理审批
     *
     * @param content 有内容则代表拒绝
     *                审批       1 通过 2 拒绝
     */
    private void handlerPunishment(String content) {
        HttpParams params = HttpParams.HttpParams();
        params.add("id", id);

        if (content.equals("")) {//1 通过 2 拒绝
            params.add("sp", "1");
        } else {//拒绝
            params.add("sp", "2");
            params.add("jjsy", content);

        }

        MyOkhttpClient.getOkhttpInstance().sentGet(PunishmentDetailActivity.this, Global.URL_PUNISHMENT_HANDLER,
                params.build(), new MyOkhttpClient.MyOkhttpCallBack() {
                    @Override
                    public void onRequestSccess(String json) throws JSONException {
                        finish();
                    }

                    @Override
                    public boolean onRequestFaild(String mes) {
                        return false;
                    }
                }, true);

    }

    /**
     * 请求完的数据界面更新
     *
     * @param punishmentData 惩戒对象数据
     */
    private void upDataUi(PunishmentData.ItemPunishmentData punishmentData) {

        if (punishmentData == null) {
            ActivityHelper.getInstance().showToast(PunishmentDetailActivity.this, "数据异常");
            return;
        }

        tvTitle.setText(PunishmentData.getPunishName(punishmentData.getCjlx()));

        List<String> bzxrInfo = punishmentData.getBzxrInfo();
        List<String> sqrInfo = punishmentData.getSqrInfo();
        String apply = sqrInfo.toString();
        String executed = bzxrInfo.toString();

        apply = apply.replace("[", "");
        apply = apply.replace("]", "");
        executed = executed.replace("[", "");
        executed = executed.replace("]", "");

        tvApply.setText(apply);
        tvExecuted.setText(executed);
        tvMatters.setText("\u3000" + punishmentData.getSqsx());
        tvFactsReasons.setText("\u3000" + punishmentData.getSsyly());
        tvLastapply.setText(punishmentData.getSqrmc());
        tvTime.setText(punishmentData.getCreateTime());

        switch (punishmentData.getSqzt()) {//状态   0 待审核 1 通过   2 拒绝
            case "0"://待审核
                setVis(true);
                break;
            case "1"://通过
                setVis(false);
                tvState.setText(getStringById(R.string.approval_passed));
                break;
            case "2"://拒绝
                setVis(false);

                tvState.setText(getStringById(R.string.approval_refuseed));
                String jjsy = punishmentData.getJjsy();
                if (jjsy != null && !jjsy.equals("")) {//有理由
                    llRefuse.setVisibility(View.VISIBLE);
                    tvRefuse.setText(jjsy);

                }


                break;
        }


    }

    private void setVis(boolean isUn) {
        if (isUn) {//未审核

            llState.setVisibility(View.VISIBLE);
            tvState.setVisibility(View.GONE);
        } else {
            llState.setVisibility(View.GONE);
            tvState.setVisibility(View.VISIBLE);
        }

    }
}
