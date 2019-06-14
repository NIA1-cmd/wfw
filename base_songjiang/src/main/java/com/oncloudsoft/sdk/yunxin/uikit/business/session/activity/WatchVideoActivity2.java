package com.oncloudsoft.sdk.yunxin.uikit.business.session.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.netease.nimlib.sdk.msg.attachment.VideoAttachment;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.yunxin.uikit.api.wrapper.NimToolBarOptions;
import com.oncloudsoft.sdk.yunxin.uikit.common.activity.ToolBarOptions;
import com.oncloudsoft.sdk.yunxin.uikit.common.activity.UI;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.sys.TimeUtil;

import cn.jzvd.JzvdStd;

/**
 * 视频播放界面
 * <p/>
 * Created by huangjun on 2015/4/11.
 */
public class WatchVideoActivity2 extends UI{
    public static final String INTENT_EXTRA_DATA = "EXTRA_DATA";
    public static final String INTENT_EXTRA_MENU = "EXTRA_MENU";


    // context

    private Handler handlerTimes = new Handler();

    private ActionBar actionBar;

    private IMMessage message;

    private JzvdStd videoplayer;
    private boolean isShowMenu;
    private String url;

    public static void start(Context context, IMMessage message) {
        Intent intent = new Intent();
        intent.putExtra(WatchVideoActivity2.INTENT_EXTRA_DATA, message);
        intent.setClass(context, WatchVideoActivity2.class);
        context.startActivity(intent);
    }

    public static void start(Context context, IMMessage message,String url, boolean isShowMenu) {
        Intent intent = new Intent();
        intent.putExtra(WatchVideoActivity2.INTENT_EXTRA_DATA, message);
        intent.putExtra(INTENT_EXTRA_MENU, isShowMenu);
        intent.putExtra("url", url);
        intent.setClass(context, WatchVideoActivity2.class);
        context.startActivity(intent);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nim_watch_video_activity2);

        ToolBarOptions options = new NimToolBarOptions();
        options.navigateId = R.drawable.nim_actionbar_white_back_icon;
        setToolBar(R.id.toolbar, options);

        parseIntent();
        findViews();
        initActionbar();
    }

    public void onResume() {
        super.onResume();
        try {
            JzvdStd.goOnPlayOnResume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            JzvdStd.goOnPlayOnPause();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            JzvdStd.releaseAllVideos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseIntent() {
        message = (IMMessage) getIntent().getSerializableExtra(INTENT_EXTRA_DATA);
        url = getIntent().getStringExtra("url");
        //setTitle(String.format("视频发送于%s", TimeUtil.getDateString(message.getTime())));
        if (url == null || url.equals("")) {
            setTitle(String.format("", TimeUtil.getDateString(message.getTime())));
        }else{
            setTitle("");
        }
        isShowMenu = getIntent().getBooleanExtra(INTENT_EXTRA_MENU, true);
    }

    private void findViews() {
        videoplayer = findView(R.id.videoplayer);
        actionBar = getSupportActionBar();
        if (url != null && !url.equals("")){
            videoplayer.setUp(url, "", JzvdStd.SCREEN_WINDOW_NORMAL);
        }else {
            videoplayer.setUp(((VideoAttachment) message.getAttachment()).getUrl(), "", JzvdStd.SCREEN_WINDOW_NORMAL);
        }
        videoplayer.startVideo();
    }

    private void initActionbar() {
        TextView menuBtn = findView(R.id.actionbar_menu);
        if (isShowMenu) {
            menuBtn.setVisibility(View.VISIBLE);
            menuBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    WatchPicAndVideoMenuActivity.startActivity(WatchVideoActivity2.this, message);
                }
            });
        } else {
            menuBtn.setVisibility(View.GONE);
        }
    }
}
