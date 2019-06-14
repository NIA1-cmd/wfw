package com.oncloudsoft.sdk.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.oncloudsoft.sdk.R;

import java.util.List;

public class LabItemAdapter extends RecyclerView.Adapter<LabItemAdapter.ViewHolder> {
    private Activity mContext;
    private List<String> list;
    private OnLabItemClickListener itemClickListener;

    public LabItemAdapter(Activity mContext, List<String> list, OnLabItemClickListener listener) {
        this.mContext = mContext;
        this.list = list;
        this.itemClickListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lab_itemlayout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String s = list.get(position);
        holder.labName.setText(s);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView labName;

        public ViewHolder(View itemView) {
            super(itemView);
            labName = itemView.findViewById(R.id.tv_text);
        }
    }

    public interface OnLabItemClickListener {
        void onClick(int pos);
    }
}
