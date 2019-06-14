package com.oncloudsoft.sdk.adapter.approval;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.ClueVerificationDetailActivity;
import com.oncloudsoft.sdk.entity.approval.ClueProvideData;
import com.oncloudsoft.sdk.utils.DateUtil;

import java.util.List;


/**
 * 作者 黄继栋
 * 创建时间 2019/1/25 10:05
 * 描述 线索登记审核列表adapter
 */

public class ClueProvideAdapter extends RecyclerView.Adapter<ClueProvideAdapter.ViewHolder> {


    private Context mContext;
    private List<ClueProvideData> list;

    //
    public ClueProvideAdapter(Context mContext, List<ClueProvideData> mList) {
        this.mContext = mContext;
        this.list = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.caseagent_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClueProvideData clueProvideData = list.get(position);
        //ShadowDrawable.setPublicShadow(holder.rlParent);

        holder.tvTitle.setText(ClueProvideData.getClueProvideName(clueProvideData.getCclx()));
        holder.tvApplytime.setText(DateUtil.showDate(clueProvideData.getTimestamp() + ""));
        switch (clueProvideData.getShzt() + "") {
            case "1": //	状态   0 待审核  1 通过    2 拒绝
                holder.ivState.setVisibility(View.VISIBLE);
                holder.ivState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_caican_true));
                break;
            case "2":
                holder.ivState.setVisibility(View.VISIBLE);
                holder.ivState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_caican_false));
                break;
            default:
                holder.ivState.setVisibility(View.GONE);
                break;
        }

        if (clueProvideData.getDsrmc() != null && !clueProvideData.getDsrmc().equals("")) {
            holder.tvApply.setText(mContext.getResources().getString(R.string.apply_people_) + clueProvideData.getDsrmc());
            holder.tvApply.setVisibility(View.VISIBLE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClueVerificationDetailActivity.startVerification(mContext, clueProvideData.getId() + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvApply;
        TextView tvApplytime;
        ImageView ivState;
        TextView tvExecuted;
        RelativeLayout rlParent;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_casename);
            tvApply = itemView.findViewById(R.id.tv_apply);
            tvApplytime = itemView.findViewById(R.id.tv_applytime);
            ivState = itemView.findViewById(R.id.iv_state);
            tvExecuted = itemView.findViewById(R.id.tv_executed);

        }
    }
}
