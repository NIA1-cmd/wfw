package com.oncloudsoft.sdk.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.webview.LongImageWebActivity;
import com.oncloudsoft.sdk.adapter.PartyAdapter;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.CaseDetailData;
import com.oncloudsoft.sdk.fragment.base_fragment.BaseFragment;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.view.BaseItemTextView;

import java.util.ArrayList;
import java.util.List;


public class CaseInfoFragment extends BaseFragment {

    private BaseItemTextView mTvCaseOrigin;
    private BaseItemTextView mTvCaseReason;
    private BaseItemTextView mTvCaseData;

    private BaseItemTextView mTvCaseType;
    private BaseItemTextView mTvCaseUnit;
    private BaseItemTextView mTvCaseNumber;
    private BaseItemTextView bt_overdata;
    private TextView mTvCaseAccount;


    private RecyclerView mRvApply;
    private RecyclerView mRvExecuted;

    private PartyAdapter applyPartyAdapter;
    private PartyAdapter executedPartyAdapter;

    private List<CaseDetailData.ExecutivePersonal> applyList = new ArrayList<>();
    private List<CaseDetailData.ExecutivePersonal> executedList = new ArrayList<>();

    private ScrollView scrollView;
    //0是申请执行人  1是被执行人
    private int type = 0;
    private String ajfl;//案件分类 0 执行类案件 1 审判类案件     1的时候没有执行依据和执行标的   审判类案件显示裁判文书
    private String ajfl1, ajfl0;

    private String ajlb;
    private LinearLayout linear_ajyj;
    private TextView tv_startpdf;
    private CaseDetailData caseDetailData;

    private List<String> data = new ArrayList<String>();
    private List<String> data1 = new ArrayList<String>();
    private RelativeLayout relative_zxbd;
    private View view_head_bg;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caseinfo, container, false);

        String caseId = getArguments().getString(Global.CASEID);
        ajfl = getArguments().getString(Global.AJFL);
        ajlb = getArguments().getString(Global.AJLB);

        view_head_bg = view.findViewById(R.id.view_head_bg);
        linear_ajyj = view.findViewById(R.id.linear_ajyj);
        relative_zxbd = view.findViewById(R.id.relative_zxbd);
        scrollView = view.findViewById(R.id.scrollView);
        mTvCaseOrigin = view.findViewById(R.id.bt_caseorigins);
        mTvCaseReason = view.findViewById(R.id.bt_casereasons);
        mTvCaseData = view.findViewById(R.id.bt_casedatas);
        tv_startpdf = view.findViewById(R.id.tv_startpdf);
        bt_overdata = view.findViewById(R.id.bt_overdata);
        mTvCaseType = view.findViewById(R.id.bt_type);
        mTvCaseUnit = view.findViewById(R.id.bt_unit);
        mTvCaseNumber = view.findViewById(R.id.bt_number);
        mTvCaseAccount = view.findViewById(R.id.bt_amount);
        mRvApply = view.findViewById(R.id.rv_apply);
        mRvExecuted = view.findViewById(R.id.rv_executed);
        tv_startpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LongImageWebActivity.start(getActivity(), caseDetailData.getCpws().getUrl(), getResources().getString(R.string.verdict));

            }
        });
        if (ajfl != null && ajfl.equals("1")) {
            ajfl0 = "0";
            ajfl1 = "1";
        }

        HttpParams params = HttpParams.HttpParams().add("ajbs", caseId).add("ajlb",ajlb);
        /*if (ajfl != null) {
            params.add("ajfl", ajfl);
        }*/

        //禁止recycle上下滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        applyPartyAdapter = new PartyAdapter(applyList, getActivity(), 0, ajfl0, data);
        mRvApply.setLayoutManager(linearLayoutManager);
        mRvApply.setAdapter(applyPartyAdapter);

        //禁止recycle上下滑动
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        executedPartyAdapter = new PartyAdapter(executedList, getActivity(), 1, ajfl1, data1);
        mRvExecuted.setLayoutManager(linearLayoutManager1);
        mRvExecuted.setAdapter(executedPartyAdapter);

        MyOkhttpClient.getOkhttpInstance().sentGet(getActivity(), Global.URL_CASEINFO, params.build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) {
                caseDetailData = JSON.parseObject(json, CaseDetailData.class);
                uodataUI(caseDetailData);
            }


            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);

        return view;
    }

    private void uodataUI(final CaseDetailData caseDetailData) {
        ActivityHelper.getInstance().getUiThread(getActivity(), new Runnable() {
            @Override
            public void run() {

                String ajlb = caseDetailData.getLa().getAjlb();
                if (ajlb != null && ajlb.equals("4")) {
                    List<String> list = new ArrayList<String>();
                    list.add("0");
                    List<String> list1 = new ArrayList<String>();
                    list1.add("1");
                    data.clear();
                    data1.clear();
                    data.addAll(list);
                    data1.addAll(list1);
                }


                mTvCaseOrigin.setEndText(caseDetailData.getLa().getAjly());
                mTvCaseReason.setEndText(caseDetailData.getLa().getLaay());
                mTvCaseData.setEndText(caseDetailData.getLa().getLarq());
                if (ajfl == null || ajfl.equals("0")) {
                    mTvCaseType.setEndText(caseDetailData.getZxyj().getZxyjlx());
                    mTvCaseUnit.setEndText(caseDetailData.getZxyj().getZxyjdw());
                    mTvCaseNumber.setEndText(caseDetailData.getZxyj().getZxyjwh());
                }
                else {
                    linear_ajyj.setVisibility(View.GONE);
                    bt_overdata.setVisibility(View.VISIBLE);
                    relative_zxbd.setVisibility(View.GONE);
                    bt_overdata.setEndText(caseDetailData.getLa().getJarq());
                }
                mTvCaseAccount.setText(caseDetailData.getZxbd());

                applyList.clear();
                executedList.clear();
                if (ajfl == null || ajfl.equals("0")) {
                    applyList.addAll(caseDetailData.getZxzt().getSqrList());
                    executedList.addAll(caseDetailData.getZxzt().getBzxrList());
                }
                else {
                    applyList.addAll(caseDetailData.getZxzt().getYgList());
                    executedList.addAll(caseDetailData.getZxzt().getBgList());
                    tv_startpdf.setVisibility(View.VISIBLE);
                }
                applyPartyAdapter.notifyDataSetChanged();
                executedPartyAdapter.notifyDataSetChanged();


            }
        });

    }
}
