package com.oncloudsoft.sdk.activity.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;

import com.alibaba.fastjson.JSONObject;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.utils.JsonUtil;
import com.oncloudsoft.sdk.utils.MyLog;
import com.oncloudsoft.sdk.utils.SharedPreferencesUtils;
import com.oncloudsoft.sdk.view.BaseTitleView;

import java.util.HashMap;

/**
 * 作者 黄继栋
 * 创建时间 2019/1/14 13:16
 * 描述 全局的webviewActivity
 */

public class BaseWebViewActivity extends BaseActivity {
    BaseTitleView btTitle;
    BridgeWebView webview;

    private String url;

    private String playVoice;//播放音乐界面


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verdict);


        btTitle = findViewById(R.id.bt_title);
        webview = findViewById(R.id.webview);

        getExtra(getIntent());

        regiestWebview();
        initWebView(webview);

        initParams();

        setListener();


    }

    /**
     * 获取参数信息
     *
     * @param intent
     */
    private void getExtra(Intent intent) {
        url = intent.getStringExtra(Global.URL);
        playVoice = intent.getStringExtra(Global.PALYVOICE_ID);
    }

    /**
     * 其他设置
     */
    private void initParams() {

        setStatusBarLightcolor(BaseWebViewActivity.this, R.color.white);


    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        btTitle.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });


    }


    /**
     * js 调用android 的方法
     */
    private void regiestWebview() {
        webview.registerHandler("actionToOc", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                MyLog.d("huangjidongZX", data);
                String type = JsonUtil.jsonGetString(data, "type");
                if (type.equals("playAudio")) {//播放音频文件
                    HashMap<String, String> map = new HashMap<>();
                    map.put("id", playVoice);
                    map.put("type", getIntent().getStringExtra(Global.TYPE));
                    map.put("token", (String) SharedPreferencesUtils.getParam(Global.TOKEN, ""));
                    MyLog.d(JSONObject.toJSONString(map));
                    function.onCallBack(JSONObject.toJSONString(map));
                } else if (type.equals("pdfUrl")) {//预览pdf  该方法已废弃掉了
                    HashMap<String, String> map = new HashMap<>();
                    map.put("url", url);
                    MyLog.d(JSONObject.toJSONString(map));
                    function.onCallBack(JSONObject.toJSONString(map));
                }
            }

        });

    }


    public BaseTitleView getBtTitle() {
        return btTitle;
    }

    private void initWebView(WebView mWebView) {
        BridgeWebViewClient webViewClient = new BridgeWebViewClient(webview) {
            /**
             * 防止加载网页时调起系统浏览器
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return super.shouldOverrideUrlLoading(view, url);
            }


            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                HashMap<String, String> map = new HashMap<>();

                map.put("type", "pageFinished");

                String currentTiem = getIntent().getStringExtra(Global.SEC);
                String Keyword = getIntent().getStringExtra(Global.CONTENT);

                if (currentTiem != null && !currentTiem.equals("") && Keyword != null && !Keyword.equals("")) {
                    map.put("currentTime", currentTiem);
                    map.put("keyWords", Keyword);
                }


                getToJs(map);


            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        };


        WebSettings webSettings = mWebView.getSettings();
        //
        //
        //        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//启动加速
        //支持屏幕缩放
        webSettings.setUseWideViewPort(true);
        //
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        //        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        //        // 设置出现缩放工具
        //        webSettings.setBuiltInZoomControls(true);
        //        //隐藏缩放工具
        webSettings.setDisplayZoomControls(false);

        String ua = webSettings.getUserAgentString();
        webSettings.setUserAgentString(ua + ";songjiang_android");

        //
        //        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        //
        //        //允许webview对文件的操作
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        //        //用于js调用Android
        webSettings.setJavaScriptEnabled(true);
        //        //设置编码方式
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        //        mWebView.setDefaultHandler(new DefaultHandler());
        //
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);


        mWebView.loadUrl(url);


    }


    public BridgeWebView getWebview() {
        return webview;
    }

    /**
     * android 调用js方法
     */
    private void getToJs(HashMap<String, String> map) {

        String json = JSONObject.toJSONString(map);
        MyLog.d(json);
        webview.callHandler("actionToJs", json, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                MyLog.d(data);
            }
        });


    }

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
            this.openFileChooser(uploadMsg);
        }

        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
            this.openFileChooser(uploadMsg);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            pickFile();
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadMessageArray = filePathCallback;
            pickFile();
            return true;
        }


    };
    ValueCallback<Uri> mUploadMessage;

    ValueCallback<Uri[]> mUploadMessageArray;
    int RESULT_CODE = 0;

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    //    private boolean isOverrideWebView = false;


    public void setBtTitle(String title) {
        //        overrideTitle = true;
        btTitle.setTitleText(title);
    }


    public BaseTitleView getBtTitleView() {
        return btTitle;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage && null == mUploadMessageArray) {
                return;
            }
            if (null != mUploadMessage && null == mUploadMessageArray) {
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }

            if (null == mUploadMessage && null != mUploadMessageArray) {
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUploadMessageArray.onReceiveValue(new Uri[]{result});
                mUploadMessageArray = null;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.setWebChromeClient(null);
        webview.setWebViewClient(null);
        webview.clearCache(true);


        WebStorage.getInstance().deleteAllData();

        HashMap<String, String> map = new HashMap<>();
        map.put("type", "pageBack");
        getToJs(map);


    }
}
