package com.oncloudsoft.sdk.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.entity.NetCheckControlData;

import java.util.List;

/**
 * 文件描述：
 * 作者：黄继栋
 * 创建时间：2019/4/30
 */
public class NetCheckControlAdapter extends RecyclerView.Adapter<NetCheckControlAdapter.ViewHolder> {

    private List<NetCheckControlData.NetCheckControl> list;

    private Activity activity;

    public NetCheckControlAdapter(List<NetCheckControlData.NetCheckControl> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_netcheckcontrol_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NetCheckControlData.NetCheckControl netCheckControl = list.get(position);
        holder.tvExecuteUnit.setText(netCheckControl.getXzdw());
        holder.tvFeedbackResult.setText(netCheckControl.getFkjg());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvExecuteUnit;
        TextView tvFeedbackResult;

        public ViewHolder(View itemView) {
            super(itemView);
            tvExecuteUnit = itemView.findViewById(R.id.tv_execute_unit);
            tvFeedbackResult = itemView.findViewById(R.id.tv_feedback_result);

        }
    }
}
