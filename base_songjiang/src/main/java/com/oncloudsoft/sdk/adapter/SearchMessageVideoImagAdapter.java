package com.oncloudsoft.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.entity.QueryImgVideoData;

import java.util.List;

/**
 * 作者 黄继栋
 * 创建时间 2019/1/30 17:28
 * 描述  历史聊天记录 图片视屏 搜索
 */

public class SearchMessageVideoImagAdapter extends RecyclerView.Adapter<SearchMessageVideoImagAdapter.ViewHolder> {

    private Context context;
    private List<QueryImgVideoData> maps;

    public SearchMessageVideoImagAdapter(Context context, List<QueryImgVideoData> mapsList) {
        this.context = context;
        this.maps = mapsList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_imag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QueryImgVideoData queryImgVideoData = maps.get(position);
        holder.tvItemHead.setText(queryImgVideoData.getTime());
        BrowseImageAdapter adapter = new BrowseImageAdapter(queryImgVideoData.getList(), (Activity) context, holder.recycler, 1, false);
        holder.recycler.setLayoutManager(new GridLayoutManager(context, 4));
        holder.recycler.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return maps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemHead;
        RecyclerView recycler;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemHead = itemView.findViewById(R.id.tv_item_head);
            recycler = itemView.findViewById(R.id.recycler);
        }
    }
}
