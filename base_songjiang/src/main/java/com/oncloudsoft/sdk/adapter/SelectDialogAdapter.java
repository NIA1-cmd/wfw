package com.oncloudsoft.sdk.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.entity.select_dialog.Option;

import java.util.List;

/**
 * 作者 caomingduo
 * 创建时间 2019/2/12 17:02
 * 描述 拍卖选择适配器
 */

public class SelectDialogAdapter extends RecyclerView.Adapter<SelectDialogAdapter.ViewHolder> {
    Context context;
    List<Option> list;

    public SelectDialogAdapter(Context context, List<Option> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SelectDialogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvContent.setText(list.get(position).getName());
        holder.ivCheck.setImageDrawable(context.getResources().getDrawable(list.get(position).isCheck() ? R.drawable.checkbox_selected : R.drawable.checkbox_normal));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCheck;
        TextView tvContent;
        public ViewHolder(View itemView) {
            super(itemView);
            ivCheck = itemView.findViewById(R.id.iv_check);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
