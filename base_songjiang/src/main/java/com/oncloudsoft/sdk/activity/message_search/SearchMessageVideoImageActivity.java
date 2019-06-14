package com.oncloudsoft.sdk.activity.message_search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.adapter.SearchMessageVideoImagAdapter;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.QueryImgVideoData;
import com.oncloudsoft.sdk.entity.SearchHistoryMessageData;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.utils.JSONParser;
import com.oncloudsoft.sdk.view.BaseTitleView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 黄继栋
 * 创建时间 2019/1/29 15:13
 * 描述 历史消息搜索 ------按图片 视屏 类型搜索
 */

public class SearchMessageVideoImageActivity extends BaseActivity {


    private BaseTitleView btTitle;
    private RecyclerView recyclerView;
    private TextView tvHead;
    private SearchMessageVideoImagAdapter adapter;
    List<QueryImgVideoData> mapsList = new ArrayList<QueryImgVideoData>();
    private String starLong;
    private String endLong;
    private int mDistanceY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_message_img);
        btTitle = findViewById(R.id.base_title);
        recyclerView = findViewById(R.id.recycler);
        tvHead = findViewById(R.id.tv_head);

        setStatusBarLightcolor(SearchMessageVideoImageActivity.this, R.color.message_bc);

        btTitle.setTitleType(BaseTitleView.BULE2);
        btTitle.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        adapter = new SearchMessageVideoImagAdapter(this, mapsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchMessageVideoImageActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                //int allItems = l.getItemCount();
                String headValue = mapsList.get(firstVisibleItemPosition).getTime();

               /* //滑动的距离
                mDistanceY += dy;
                //tvHead的高度
                int toolbarHeight = tvHead.getBottom();

                //改变tvHead背景色的透明度
                if (mDistanceY <= toolbarHeight) {
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale * 255;
                    tvHead.setBackgroundColor(Color.argb((int) alpha, 25, 41, 77));
                }
                else {
                    tvHead.setBackgroundResource(R.color.color_cc19294d);
                }*/

                tvHead.setText(headValue);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //静止状态
                    tvHead.setVisibility(View.GONE);
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    //滚动状态
                    tvHead.setVisibility(View.VISIBLE);
                }
            }
        });
        starLong = getIntent().getStringExtra(Global.START);
        endLong = getIntent().getStringExtra(Global.END);
        request(getIntent().getStringExtra(Global.CASEID));
    }

    private void request(String caseId) {

        MyOkhttpClient.getOkhttpInstance().sentGet(SearchMessageVideoImageActivity.this, Global.URL_HISTORYMESSAGE, HttpParams.HttpParams().add("ajbs", caseId).add("type", "1").add("startDate", starLong).add("endDate", endLong).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) throws JSONException {
                List<SearchHistoryMessageData> searchHistoryMessageDataList = JSON.parseArray(json, SearchHistoryMessageData.class);
                mapsList.clear();
                try {
                    List<QueryImgVideoData> list = JSONParser.parserSearchMessage(searchHistoryMessageDataList);
                    mapsList.addAll(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ActivityHelper.getInstance().getUiThread(SearchMessageVideoImageActivity.this, new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);
    }

    public static void start(Activity activity, String caseId, String startTime, String endTime) {
        Intent intent = new Intent();
        intent.setClass(activity, SearchMessageVideoImageActivity.class);
        intent.putExtra(Global.CASEID, caseId);
        intent.putExtra(Global.START, startTime);
        intent.putExtra(Global.END, endTime);
        activity.startActivity(intent);


    }
}
