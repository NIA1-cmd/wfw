package com.oncloudsoft.sdk.fragment;

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

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.adapter.ConsultingReplyAdapter;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.ConsultingReplyData;
import com.oncloudsoft.sdk.fragment.base_fragment.BaseFragment;

import java.util.ArrayList;


public class ConsultingReplyFragment extends BaseFragment {

    private ArrayList<ConsultingReplyData> list;
    private TextView tvEmpty;
    private RelativeLayout rlEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_baselayout, container, false);
        String caseId = getArguments().getString(Global.CASEID);
        int type = getArguments().getInt(Global.TYPE);
        RecyclerView recyclerView = view.findViewById(R.id.rv_base);
        rlEmpty = view.findViewById(R.id.rl_empty);
        tvEmpty = view.findViewById(R.id.tv_empty);
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ConsultingReplyAdapter(list,type));

        return view;
    }

    private void initData() {
        list = new ArrayList<ConsultingReplyData>();
        rlEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText("暂无数据!");
        /*ConsultingReplyData consultingReplyData = new ConsultingReplyData();
        consultingReplyData.setDsr("当事人");
        consultingReplyData.setDsrtime(" 20190-07-11");
        consultingReplyData.setDsrMessage("法官您好，我的案件怎么样了?");
        consultingReplyData.setFg("法官");
        consultingReplyData.setFgtime(" 20190-07-11");
        consultingReplyData.setFgMessage("正在处理中，请稍后");

        ConsultingReplyData consultingReplyData1 = new ConsultingReplyData();
        consultingReplyData1.setDsr("当事人");
        consultingReplyData1.setDsrtime(" 20190-07-11");
        consultingReplyData1.setDsrMessage("法官您好，我的案件怎么样了?");
        consultingReplyData1.setFg("法官");
        consultingReplyData1.setFgtime(" 20190-07-11");
        consultingReplyData1.setFgMessage("正在处理中，请稍后");
        list.add(consultingReplyData);
        list.add(consultingReplyData1);*/
    }
}
