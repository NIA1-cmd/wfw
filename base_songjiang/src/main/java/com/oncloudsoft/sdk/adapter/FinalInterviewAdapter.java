package com.oncloudsoft.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.FinalInterviewDetailsActivity;
import com.oncloudsoft.sdk.entity.FinalInterviewData;
import com.oncloudsoft.sdk.utils.DateUtil;

import java.util.List;

/**
 * 作者 Mingduo Cao
 * 创建时间 2019/6/7 12:39
 * <p>
 * 描述
 */

public class FinalInterviewAdapter extends RecyclerView.Adapter<FinalInterviewAdapter.MyViewHolder> {

    private Context context;
    private List<FinalInterviewData> list;
    private FinalInterviewAdapter.finalCallback finalCallback;

    public FinalInterviewAdapter(Context context, List<FinalInterviewData> list, FinalInterviewAdapter.finalCallback finalCallback) {
        this.context = context;
        this.list = list;
        this.finalCallback = finalCallback;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finallnterview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getName());
        holder.tvTime.setText(DateUtil.longToTime(Long.parseLong(list.get(position).getTime()), DateUtil.DateFormat.YearmonthdayHourminutesseconds_02));
        boolean isChek = list.get(position).isCheck();
        boolean b = isChek == true ? false : true;
        Drawable drawable = isChek == true ? context.getResources().getDrawable(R.drawable.ck_select_red) : context.getResources().getDrawable(R.drawable.ck_normal_red);
        holder.ivChek.setBackground(drawable);
        holder.ivChek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalCallback.finalCallback(position, b);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FinalInterviewDetailsActivity.start((Activity) context, "");
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivChek;
        TextView tvName;
        TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivChek = itemView.findViewById(R.id.iv_check);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }

    public interface finalCallback {
        void finalCallback(int position, boolean b);
    }
}
