package com.oncloudsoft.sdk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.entity.CaseDetailData;
import com.oncloudsoft.sdk.view.BaseItemTextView;
import com.oncloudsoft.sdk.view.LabTitleLayout;

import java.util.List;

/**
 * 当事人列表
 */
public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.ViewHolder> {

    private List<CaseDetailData.ExecutivePersonal> partyList;
    private Context mContext;
    private int type;  //type为1 被执行人       0 申请人
    private String ajfl;  //不为null  或者非“” 则为结案
    private List<String> ajlb;  // 0 不动 4 原案申请人/ 异议人（被执行人）

    public PartyAdapter(List<CaseDetailData.ExecutivePersonal> partyList, Context mContext, int type, String ajfl, List<String> ajlb_type) {
        this.partyList = partyList;
        this.mContext = mContext;
        this.type = type;
        this.ajfl = ajfl;
        this.ajlb = ajlb_type;
    }

    @Override
    public PartyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();

        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.party_itemlayout, parent, false);

        return new PartyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartyAdapter.ViewHolder holder, int position) {
        CaseDetailData.ExecutivePersonal executivePersonal = partyList.get(position);

        if (type == 1) {
            holder.labTitleLayout.setTitleName("被执行人");
        }
        if (ajfl != null && ajfl.equals("0")) {
            holder.labTitleLayout.setTitleName("原告");
        }
        if (ajfl != null && ajfl.equals("1")) {
            holder.labTitleLayout.setTitleName("被告");
        }
        if (ajlb != null && ajlb.size() > 0 && ajlb.get(0).equals("0")) {
            holder.labTitleLayout.setTitleName("原案申请人");
        }
        if (ajlb != null && ajlb.size() > 0 && ajlb.get(0).equals("1")) {
            holder.labTitleLayout.setTitleName("异议人(被执行人)");
        }
        holder.mTvName.setEndText(executivePersonal.getDsrmc());
        holder.mTvDoucment.setEndText(executivePersonal.getDsrsfz());
        holder.mTvType.setEndText(executivePersonal.getDsrlx());
        holder.mTvAddress.setEndText(executivePersonal.getDsrdz());
        holder.mTvConnection.setEndText(executivePersonal.getDsrdh());
    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private BaseItemTextView mTvName;
        private BaseItemTextView mTvDoucment;
        private BaseItemTextView mTvType;
        private BaseItemTextView mTvAddress;
        private BaseItemTextView mTvConnection;
        private LabTitleLayout labTitleLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.bt_name);
            mTvDoucment = itemView.findViewById(R.id.bt_document);
            mTvType = itemView.findViewById(R.id.bt_type);
            mTvAddress = itemView.findViewById(R.id.bt_address);
            mTvConnection = itemView.findViewById(R.id.bt_connection);
            labTitleLayout = itemView.findViewById(R.id.lb_title);
        }
    }
}
