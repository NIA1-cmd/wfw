package com.oncloudsoft.sdk.activity.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.oncloudsoft.sdk.app.Global;

/**
 * 作者 caomingduo
 * 创建时间 2019/1/8 20:39
 * 描述 审判文书
 */

public class LongImageWebActivity extends BaseWebViewActivity {


    public static void start(Context activity, String url,String title){
        Intent intent=new Intent();
        intent.setClass(activity,LongImageWebActivity.class);
        intent.putExtra(Global.URL,url);
        intent.putExtra(Global.TITLE,title);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setBtTitle(getIntent().getStringExtra(Global.TITLE));


//        BridgeWebView webview = getWebview();
        webview.loadUrl("file:///android_asset/photo.html");
        webview.setWebChromeClient(new chromClient());


    }
    private class chromClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress==100){
                //页面加载完成执行的操作
                String action="javascript:add('"+getIntent().getStringExtra(Global.URL)+"')";
                runWebView(action);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
    private void runWebView(final String url){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webview.loadUrl(url);
            }
        });
    }
}
