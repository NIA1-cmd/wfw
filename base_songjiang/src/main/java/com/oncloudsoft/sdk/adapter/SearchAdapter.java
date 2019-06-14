package com.oncloudsoft.sdk.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;

import java.util.List;

/**
 * 搜索历史记录适配器
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context mContext;
    private List<String> itemList;
    private OnSearchClickListener mOnSearchClickListener;

    public SearchAdapter(Context mContext, List<String> itemList, OnSearchClickListener listener) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.mOnSearchClickListener = listener;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();

        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_itemlayout, parent, false);

        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, final int position) {

        String s = itemList.get(position);

        holder.mTvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSearchClickListener.textClick(position);
            }
        });
        holder.mTvHistory.setText(s);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvHistory;

        ViewHolder(View itemView) {
            super(itemView);
            mTvHistory = itemView.findViewById(R.id.tv_history);
        }
    }

    /**
     * 每个Item的点击事件处理接口
     */
    public interface OnSearchClickListener {
        void textClick(int pos);
    }

}
