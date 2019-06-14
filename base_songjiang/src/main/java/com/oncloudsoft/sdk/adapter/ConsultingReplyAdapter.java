package com.oncloudsoft.sdk.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.entity.ConsultingReplyData;

import java.util.List;

/**
 * 作者 Mingduo Cao
 * 创建时间 2019/6/11 16:10
 * <p>
 * 描述
 */

public class ConsultingReplyAdapter extends RecyclerView.Adapter<ConsultingReplyAdapter.MyViewHolder> {
    private List<ConsultingReplyData> list;
    int type;

    public ConsultingReplyAdapter(List<ConsultingReplyData> list, int type) {
        this.type = type;
        this.list = list;

    }

    @Override
    public ConsultingReplyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consulting_reply, parent, false);
        return new ConsultingReplyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsultingReplyAdapter.MyViewHolder holder, int position) {
        int visibility = type == 0 ? View.GONE : View.VISIBLE;
        holder.tvReply.setVisibility(visibility);
        holder.tvDsr.setText(list.get(position).getDsr());
        holder.tvDsrSend.setText(list.get(position).getDsrMessage());
        holder.tvDsrTime.setText(list.get(position).getDsrtime());
        if (type == 0) {
            holder.relativeFg.setVisibility(View.VISIBLE);
            holder.tvFg.setText(list.get(position).getFg());
            holder.tvFgSend.setText(list.get(position).getFgMessage());
            holder.tvFgTime.setText(list.get(position).getFgtime());
        }else{
            holder.relativeFg.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDsrSend, tvDsr, tvFg, tvFgSend, tvReply,tvDsrTime,tvFgTime;
        RelativeLayout relativeFg, relativeDsr;

        public MyViewHolder(View itemView) {
            super(itemView);
            relativeFg = itemView.findViewById(R.id.relative_fg);
            relativeDsr = itemView.findViewById(R.id.relative_dsr);
            tvReply = itemView.findViewById(R.id.tv_reply);
            tvDsr = itemView.findViewById(R.id.tv_dsr);
            tvDsrTime = itemView.findViewById(R.id.tv_dsr_time);
            tvDsrSend = itemView.findViewById(R.id.tv_dsr_send);
            tvFg = itemView.findViewById(R.id.tv_fg);
            tvFgSend = itemView.findViewById(R.id.tv_fg_send);
            tvFgTime = itemView.findViewById(R.id.tv_fg_time);

        }
    }
}
