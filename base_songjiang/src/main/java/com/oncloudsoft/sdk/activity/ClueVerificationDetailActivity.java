package com.oncloudsoft.sdk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.adapter.BrowseImageAdapter;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.Media;
import com.oncloudsoft.sdk.entity.verification.VerificationData;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.utils.OnSingleClickListener;
import com.oncloudsoft.sdk.view.BaseTitleView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者 caomingduo
 * 创建时间 2019/1/31 10:01
 * 描述 案件线索详情
 */

public class ClueVerificationDetailActivity extends BaseActivity {

    private BaseTitleView baseTitle;
    private TextView tvHeadTitle;
    private TextView tvHouseAddress;
    private TextView tvHousesType;
    private TextView tvHousesMj;
    private RecyclerView recycler;
    private RecyclerView rvEvidence;
    private LinearLayout llParent;
    private List<Media> list = new ArrayList<>();
    private List<Media> rvidenceList = new ArrayList<>();
    private TextView tvAddress;
    private TextView tvType;
    private TextView tvMj;
    private TextView tvMc;
    private LinearLayout linearSubmit;
    private LinearLayout llHouseandcar;
    private TextView tvContent;
    private TextView tvState;
    private TextView tvRemark;
    private TextView tvResult;
    private LinearLayout line;
    private LinearLayout llRemark;
    private LinearLayout llResult;
    private String id;
    private int shzt;
    private VerificationData verificationData;
    private BrowseImageAdapter adapter, evidenceAdapter;
    private String cllx;


    public static int oprationRequestCode = 5078;


    public static void startVerification(Context activity, String id) {
        Intent intent = new Intent();
        intent.setClass(activity, ClueVerificationDetailActivity.class);
        intent.putExtra(Global.ID, id);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        initViews();

        cllx = "";

        id = getIntent().getStringExtra(Global.ID);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new BrowseImageAdapter(list, this, recycler, 20, true);
        recycler.setAdapter(adapter);
        rvEvidence.setLayoutManager(new GridLayoutManager(this, 3));
        evidenceAdapter = new BrowseImageAdapter(rvidenceList, this, rvEvidence, 20, true);
        rvEvidence.setAdapter(evidenceAdapter);


        baseTitle.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });


    }

    private void initViews() {
        baseTitle = findViewById(R.id.base_title);
        tvHeadTitle = findViewById(R.id.tv_head_title);
        tvHouseAddress = findViewById(R.id.tv_house_address);
        tvHousesType = findViewById(R.id.tv_houses_type);
        tvHousesMj = findViewById(R.id.tv_houses_mj);
        recycler = findViewById(R.id.recycler);
        rvEvidence = findViewById(R.id.rv_evidence);
        llParent = findViewById(R.id.ll_parent);
        tvAddress = findViewById(R.id.tv_address);
        tvType = findViewById(R.id.tv_type);
        tvMj = findViewById(R.id.tv_mj);
        tvMc = findViewById(R.id.tv_mc);
        linearSubmit = findViewById(R.id.linear_submit);
        llHouseandcar = findViewById(R.id.ll_houseandcar);
        tvContent = findViewById(R.id.tv_content);
        tvState = findViewById(R.id.tv_state);
        tvRemark = findViewById(R.id.tv_remark);
        tvResult = findViewById(R.id.tv_result);
        line = findViewById(R.id.line);
        llRemark = findViewById(R.id.ll_remark);
        llResult = findViewById(R.id.ll_result);
        findViewById(R.id.tv_verification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnSingleClickListener.isDoubleClick()) {
                    return;
                }
                ClueVerificationOperatingActivity.startActivityForResult(ClueVerificationDetailActivity.this, id, oprationRequestCode);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        initData();
    }

    private void updateView() {

        if (shzt == 0) {//	审核状态    0 待审核 1 通过 2 拒绝
            linearSubmit.setVisibility(View.VISIBLE);
            tvState.setVisibility(View.GONE);

        } else {

            llResult.setVisibility(View.VISIBLE);
            linearSubmit.setVisibility(View.GONE);
            tvState.setVisibility(View.VISIBLE);
            switch (shzt) {
                case 1:
                    tvResult.setText("属实");
                    tvState.setText(getStringById(R.string.verification_passed));
                    break;
                case 2:
                    tvResult.setText("不实");
                    tvState.setText(getStringById(R.string.verification_rejected));
                    break;
            }
            String shbz = verificationData.getShbz();
            if (shbz != null && !shbz.equals("")) {
                llRemark.setVisibility(View.VISIBLE);
                tvRemark.setText(verificationData.getShbz());
            }

        }


        switch (cllx) {//0 房屋   1 车辆      2 其他
            case "0":
                tvHouseAddress.setText(verificationData.getFwwz());
                tvHousesType.setText(verificationData.getFwlx());
                tvHousesMj.setText(verificationData.getFwmj());
                tvMc.setText(verificationData.getSyqr());
                tvContent.setVisibility(View.GONE);
                llHouseandcar.setVisibility(View.VISIBLE);
                break;
            case "1":
                tvHeadTitle.setText(getStringById(R.string.car_info));
                tvAddress.setText(getStringById(R.string.car_number));
                tvType.setText(getStringById(R.string.car_type));
                tvMj.setText(getStringById(R.string.find_place));
                tvHouseAddress.setText(verificationData.getCph());
                tvHousesType.setText(verificationData.getCllx());
                tvHousesMj.setText(verificationData.getClwz());
                tvMc.setText(verificationData.getSyqr());
                tvContent.setVisibility(View.GONE);
                llHouseandcar.setVisibility(View.VISIBLE);
                break;
            case "2":
                line.setVisibility(View.VISIBLE);
                tvContent.setVisibility(View.VISIBLE);
                llHouseandcar.setVisibility(View.GONE);
                tvContent.setText(verificationData.getXsms());
                break;
        }

        if (list != null && list.size() > 0) {
            recycler.setVisibility(View.VISIBLE);
        } else {
            recycler.setVisibility(View.GONE);

        }
        if (rvidenceList != null && rvidenceList.size() > 0) {
            rvEvidence.setVisibility(View.VISIBLE);
        } else {
            rvEvidence.setVisibility(View.GONE);

        }


    }


    private void initData() {
        MyOkhttpClient.getOkhttpInstance().sentGet(this, Global.URL_VERIFICATION_DETALE, HttpParams.HttpParams().add("id", id).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) throws JSONException {
                verificationData = JSON.parseObject(json, VerificationData.class);
                List<String> wjList = verificationData.getWjList();
                List<String> shwjList = verificationData.getShwjList();
                cllx = verificationData.getCclx();
                shzt = verificationData.getShzt();
                list.clear();
                rvidenceList.clear();

                for (int i = 0; i < wjList.size(); i++) {
                    String s = wjList.get(i);
                    Media media = new Media();
                    media.setUrl(s);
                    list.add(media);
                }
                for (int i = 0; i < shwjList.size(); i++) {
                    String s = shwjList.get(i);
                    Media media = new Media();
                    media.setUrl(s);
                    rvidenceList.add(media);
                }

                ActivityHelper.getInstance().getUiThread(ClueVerificationDetailActivity.this, new Runnable() {
                    @Override
                    public void run() {
                        updateView();
                        adapter.notifyDataSetChanged();
                        evidenceAdapter.notifyDataSetChanged();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == ClueVerificationDetailActivity.oprationRequestCode) {//代表是审核通过之后返回的

                finish();
            }


        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
