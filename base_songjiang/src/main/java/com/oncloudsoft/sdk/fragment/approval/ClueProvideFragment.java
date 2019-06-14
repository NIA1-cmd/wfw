package com.oncloudsoft.sdk.fragment.approval;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.adapter.approval.ClueProvideAdapter;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.approval.ClueProvideData;
import com.oncloudsoft.sdk.fragment.base_fragment.BaseFragment;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 黄继栋
 * 创建时间 2019/1/31 10:55
 * 描述 线索提供Fragment
 */

public class ClueProvideFragment extends BaseFragment {

    private ClueProvideAdapter clueProvideAdapter;
    private List<ClueProvideData> clueProvideDataList=new ArrayList<>();

    private RecyclerView recyclerView;
    private RelativeLayout rl_empty;
    private TextView tv_empty;
    private String caseId;
    private String type;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_baselayout, container, false);
        recyclerView = view.findViewById(R.id.rv_base);
        rl_empty = view.findViewById(R.id.rl_empty);
        tv_empty = view.findViewById(R.id.tv_empty);
        caseId=getArguments().getString(Global.CASEID);
        type=getArguments().getString(Global.TYPE);//审核状态   0 审核中   1 通过   2 拒绝
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));



        clueProvideAdapter = new ClueProvideAdapter(getActivity(), clueProvideDataList);
        recyclerView.setAdapter(clueProvideAdapter);


        return view;

    }

    private void request() {
        MyOkhttpClient.getOkhttpInstance().sentGet(getActivity(), Global.URL_CLUEPROVIDE_QUERY,
                HttpParams.HttpParams().add("shzt", type).add("ajbs", caseId).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) throws JSONException {

                clueProvideDataList.clear();
                clueProvideDataList.addAll(  JSON.parseArray(json,ClueProvideData.class));


                ActivityHelper.getInstance().getUiThread(getActivity(), new Runnable() {
                    @Override
                    public void run() {
                        if (clueProvideDataList.size() == 0){
                            rl_empty.setVisibility(View.VISIBLE);
                            tv_empty.setText("暂无数据!");
                        }else {
                            rl_empty.setVisibility(View.GONE);

                        }
                        clueProvideAdapter.notifyDataSetChanged();
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
    public void onResume() {
        super.onResume();

        request();

    }
}
