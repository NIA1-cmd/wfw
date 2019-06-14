package com.oncloudsoft.sdk.activity.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.view.BaseTitleView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者 黄继栋
 * 创建时间 2019/1/21 16:39
 * 描述  预览pdf页面
 */

public class TestWevActivity extends BaseWebViewActivity {


    /**
     * 启动预览pdf界面方法
     *
     * @param activity 上下文对象
     * @param url      pdf的绝对路径
     * @param title    pdf中的标题
     * @param intent   传递参数
     */
    public static void start(Context activity, String url, String title, @NonNull Intent intent) {

        intent.putExtra(Global.URL, Global.URL_PDF + "?file=" + url);
        intent.putExtra(Global.TITLE, title);
        intent.setClass(activity, TestWevActivity.class);
        activity.startActivity(intent);


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBtTitle(getIntent().getStringExtra(Global.TITLE));


        String pdfId = getIntent().getStringExtra(Global.PDF_ID);
        if (pdfId != null && !pdfId.equals("")) {//和解记录  pdf中 待确认过来的

            reconciliationProtocolDetail(pdfId);


        }
    }

    /**
     * 和解详情中的审批
     *
     * @param pdfId 和解协议的id
     */
    private void reconciliationProtocolApproval(String pdfId) {
        MyOkhttpClient.getOkhttpInstance().sentGet(this, Global.URL_HEJE_SHENGPI, HttpParams.HttpParams().add("hjxyId", pdfId).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) throws JSONException {

                ActivityHelper.getInstance().getUiThread(TestWevActivity.this, new Runnable() {
                    @Override
                    public void run() {
                        finish();
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
     * 查看和解协议详情
     *
     * @param pdfId
     */
    private void reconciliationProtocolDetail(String pdfId) {
        MyOkhttpClient.getOkhttpInstance().sentGet(this, Global.URL_HEJE_DETALE, HttpParams.HttpParams().add("id", pdfId).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) throws JSONException {
                JSONObject jsonObject = new JSONObject(json);
                ActivityHelper.getInstance().getUiThread(TestWevActivity.this, new Runnable() {
                    @Override
                    public void run() {
                        int hjzt = jsonObject.optInt("hjzt");//和解状态 0 当事人待确认 1 法官待确认 2 和解成功 3 和解失败 仅当改字段值为1时需要法官审批

                        if (hjzt == 1) {
                            btTitle.setRightTexts("确认");
                            btTitle.setOnRightTextClickListener(new BaseTitleView.onRightTextClickListener() {
                                @Override
                                public void onClick() {
                                    reconciliationProtocolApproval(pdfId);
                                }
                            });
                        }
                    }
                });


            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);
    }

}
