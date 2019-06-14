package com.oncloudsoft.sdk.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.BigImagActivity;
import com.oncloudsoft.sdk.activity.LargeImageActivity;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.entity.Media;
import com.oncloudsoft.sdk.utils.GlidelUtil;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.sys.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片预览adapter
 */
public class ImageSelectAdapter extends RecyclerView.Adapter<ImageSelectAdapter.ViewHolder> {

    private List<Media> urlList;
    private Activity context;
    private RecyclerView recyclerView;
    private int margin;
    private boolean intentType;

    /**
     * @param urlList      路径list
     * @param context      上下文对象
     * @param recyclerView recyclerView
     * @param margin       recyclerView距离外面边距之和 单位dp
     * @param intentType   跳转类型 是否跳转到轮播
     */
    public ImageSelectAdapter(List<Media> urlList, Activity context, RecyclerView recyclerView, int margin, boolean intentType) {
        this.margin = margin;
        this.recyclerView = recyclerView;
        this.urlList = urlList;
        this.context = context;
        this.intentType = intentType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_image, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Media media = urlList.get(position);
        GlidelUtil.loadImag(context, media.getUrl(), holder.ivImg);

        int screenWidth = ScreenUtil.getDisplayWidth();
        int gap = 2;//每张图片之间的间隙
        int Margin = ScreenUtil.dip2px(margin);//布局中设置的外边距

        screenWidth = screenWidth - Margin;
        GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        int sp = gridLayoutManager.getSpanCount();
        int num = 2 * sp;//中间的间隙数量

        screenWidth = screenWidth - (gap * num);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(holder.rlParent.getLayoutParams());
        lp.width = lp.height = screenWidth / sp;
        lp.setMargins(gap, gap, gap, gap);
        holder.rlParent.setLayoutParams(lp);
        View.OnClickListener listener;

        if (intentType) {//跳转轮播
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> imageUrls = new ArrayList<>();
                    for (int i = 0; i < urlList.size(); i++) {
                        Media media1 = urlList.get(i);
                        imageUrls.add(media1.getUrl());
                    }
                    LargeImageActivity.startBigImg(context, imageUrls, position);
                }
            };
        } else {
            switch (media.getType()) {
                case "PICTURE":
                    listener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BigImagActivity.startBigImg(context, media.getUrl());
                        }
                    };
                    break;
                default:
                    listener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityHelper.getInstance().showToast(context, "媒体路径有误");
                        }
                    };
                    break;
            }
        }
        holder.itemView.setOnClickListener(listener);
        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImg;
        ImageView ivRemove;
        RelativeLayout rlParent;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            ivRemove = itemView.findViewById(R.id.iv_remove);
            rlParent = itemView.findViewById(R.id.ll_parent);

        }
    }


}