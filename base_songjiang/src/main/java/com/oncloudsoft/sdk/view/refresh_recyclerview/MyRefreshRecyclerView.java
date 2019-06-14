package com.oncloudsoft.sdk.view.refresh_recyclerview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.oncloudsoft.sdk.R;

/**
 * 作者 黄继栋
 * 创建时间 2019/1/16 10:17
 * 描述
 */

public class MyRefreshRecyclerView extends RelativeLayout {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LoadMoreWrapper loadMoreWrapper;

    public MyRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public MyRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.refresh_recyclerview_layout, this);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));


        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null) {
                    listener.refresh();
                    loadMoreWrapper.setLoadState(LoadMoreWrapper.LOADMORE_NORMAL);
                }


            }
        });

        // 设置加载更多监听
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (listener != null) {
                    loadMoreWrapper.setLoadState(LoadMoreWrapper.LOADING);
                    listener.loadMore();
                }

            }
        });
    }

    public void adapter(Activity activity, RecyclerView.Adapter recyclerViewAdapter) {
        // 模拟获取数据
        loadMoreWrapper = new LoadMoreWrapper(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(loadMoreWrapper);
        recyclerViewAdapter.notifyDataSetChanged();


    }

    public void setlistener(Listener listener) {
        this.listener = listener;

    }

    private Listener listener;

    public void notifyDataSetChanged() {
        loadMoreWrapper.notifyDataSetChanged();

    }

    public void setRefreshing(boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }


    public void setLoadMorState(int state) {
        loadMoreWrapper.setLoadState(state);
    }

    public interface Listener {
        void loadMore();

        void refresh();

    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setLoadMoreComplete() {
        setLoadMorState(LoadMoreWrapper.LOADING_COMPLETE);
    }

    public void setLoadMoreEnd() {
        setLoadMorState(LoadMoreWrapper.LOADING_END);
    }
}
