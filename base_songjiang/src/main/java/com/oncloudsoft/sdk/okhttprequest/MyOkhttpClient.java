package com.oncloudsoft.sdk.okhttprequest;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.oncloudsoft.sdk.activity.DialogActivity;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.utils.FileUtil;
import com.oncloudsoft.sdk.utils.MyLog;
import com.oncloudsoft.sdk.utils.NetWorkUtils;
import com.oncloudsoft.sdk.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;


public class MyOkhttpClient {
    private OkHttpClient client = null;
    private static MyOkhttpClient myOkhttpClient = null;

    public static MyOkhttpClient getOkhttpInstance() {
        if (myOkhttpClient == null) {
            return new MyOkhttpClient();

        } else {
            return myOkhttpClient;
        }
    }

//-----------------------------------post方法开始---------------------------------------------------

    /**
     * 发送一个普通的 post请求
     *
     * @param url               请求url
     * @param json              json 请求参数对象
     * @param myOkhttpCallBack  请求回调
     * @param haveAuthorization 是否需要校验
     */
    public void sentPost(Context context, String url, String json, MyOkhttpCallBack myOkhttpCallBack, boolean haveAuthorization, String showReqiestDialog, boolean returnAll) {


        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(mediaType, json);

        Request.Builder postRequestBuilder = new Request.Builder()//创建Request 对象。
                .url(url)
                .post(requestBody);//传递请求体

        getRequest(context, postRequestBuilder, haveAuthorization, myOkhttpCallBack, showReqiestDialog, returnAll);

    }

    /**
     * 发送一个不需要 请求弹框的post请求
     *
     * @param url               请求url
     * @param json              json 请求参数对象
     * @param myOkhttpCallBack  请求回调
     * @param haveAuthorization 是否需要校验
     */
    public void sentPost(Context context, String url, String json, MyOkhttpCallBack myOkhttpCallBack, boolean haveAuthorization) {
        sentPost(context, url, json, myOkhttpCallBack, haveAuthorization, null, false);
    }

    public void sentPost(Activity activity, String url, String json, MyOkhttpCallBack myOkhttpCallBack, boolean haveAuthorization, boolean returnAll) {
        sentPost(activity, url, json, myOkhttpCallBack, haveAuthorization, null, returnAll);
    }

//-----------------------------------get方法开始---------------------------------------------------


    /**
     * 发起一个最普通的不需要弹框的 Get请求
     *
     * @param url               请求url
     * @param map               请求参数
     * @param myOkhttpCallBack  请求回调
     * @param haveAuthorization 是否需要token校验
     */
    public void sentGet(Context context, String url, HashMap<String, String> map, MyOkhttpCallBack myOkhttpCallBack, boolean haveAuthorization) {
        sentGet(context, url, map, myOkhttpCallBack, haveAuthorization, null, false);
    }

    public void sentGet(Activity activity, String url, HashMap<String, String> map, MyOkhttpCallBack myOkhttpCallBack, boolean haveAuthorization, boolean returnAll) {
        sentGet(activity, url, map, myOkhttpCallBack, haveAuthorization, null, returnAll);
    }

    /**
     * 发起一个最普通的 Get请求
     *
     * @param url               请求url
     * @param map               请求参数
     * @param myOkhttpCallBack  请求回调
     * @param haveAuthorization 是否需要token校验
     * @param showRequestDialog 是否需要显示请求时的请求弹框
     */
    public void sentGet(Context context, String url, HashMap<String, String> map, MyOkhttpCallBack myOkhttpCallBack, boolean haveAuthorization, String showRequestDialog, boolean returnAll) {

        if (map != null && map.size() > 0) {//没有参数

            url = url + "?";
            for (String key : map.keySet()) {
                // 获得key
                //获得key值对应的value
                Object o = map.get(key);
                url = url + key + "=" + o + "&";
            }


            url = url.substring(0, url.length() - 1);
        }
        Request.Builder getRequestBuilder = new Request.Builder()
                .url(url)
                .get();
        getRequest(context, getRequestBuilder, haveAuthorization, myOkhttpCallBack, showRequestDialog, returnAll);


    }


//-----------------------------------单文件上传方法开始---------------------------------------------------


    /**
     * 上传单个文件
     *
     * @param mActivity      当前窗口
     * @param mFile          需要上传的文件
     * @param type           文件的类型
     * @param mMultipartBody 出去文件其余的MultipartBody
     * @param url            请求地址
     * @param mMyCallback    请求回调
     */
    public void upSingleFile(final Activity mActivity, File mFile, String type, MultipartBody.Builder mMultipartBody, String url, final MyOkhttpCallBack mMyCallback, boolean haveAuthorization, String showRequestDialog, boolean returnAll) {
        mMultipartBody.addFormDataPart("file", FileUtil.getEnglishName(mFile), createCustomRequestBody(MediaType.parse(type), mFile, new ProgressListener() {
            @Override
            public void onProgress(long totalBytes, long remainingBytes, boolean done) {

            }
        }));

        getRequest(mActivity, new Request.Builder().post(mMultipartBody.build()).url(url), haveAuthorization, mMyCallback, showRequestDialog, returnAll);


    }

    /**
     * 上传单个 不需要弹框 文件
     *
     * @param mActivity      当前窗口
     * @param mFile          需要上传的文件
     * @param type           文件的类型
     * @param mMultipartBody 出去文件其余的MultipartBody
     * @param url            请求地址
     * @param mMyCallback    请求回调
     */
    public void upSingleFile(final Activity mActivity, File mFile, String type, MultipartBody.Builder mMultipartBody, String url, final MyOkhttpCallBack mMyCallback, boolean haveAuthorization, boolean returnAll) {

        upSingleFile(mActivity, mFile, type, mMultipartBody, url, mMyCallback, haveAuthorization, null, returnAll);


    }

//-----------------------------------多文件上传方法开始---------------------------------------------------


    /**
     * 发起上传多文件 的不需要带请求弹框的普通请求
     *
     * @param mActivity         上下文对象
     * @param url               请求url
     * @param builder           请求文件builder
     * @param haveAuthorization 是否校验token
     * @param mMyCallback       请求回调
     */
    public void upLoadMultipartBody(final Activity mActivity, String url, MultipartBody.Builder builder, boolean haveAuthorization, final MyOkhttpCallBack mMyCallback) {
        upLoadMultipartBody(mActivity, url, builder, haveAuthorization, mMyCallback, null);
    }

    /**
     * 发起上传多文件 的普通请求
     *
     * @param mActivity         上下文对象
     * @param url               请求url
     * @param builder           请求文件builder
     * @param haveAuthorization 是否校验token
     * @param mMyCallback       请求回调
     */
    public void upLoadMultipartBody(final Activity mActivity, String url, MultipartBody.Builder builder, boolean haveAuthorization, final MyOkhttpCallBack mMyCallback, String showRequestDialog) {

        getRequest(mActivity, new Request.Builder().post(builder.build()).url(url), haveAuthorization, mMyCallback, showRequestDialog, false);
    }


//-----------------------------------判断是否添加token---------------------------------------------------


    /**
     * 添加Token
     *
     * @param builder
     * @param haveAuthorization
     * @return
     */
    private void getRequest(Context context, Request.Builder builder, boolean haveAuthorization, MyOkhttpCallBack myOkhttpCallBack, String showReqestDialog, boolean returnall) {

        if (haveAuthorization) {//需要验证的时候

            String token = (String) SharedPreferencesUtils.getParam(Global.TOKEN, "");

            if (token != null && !TextUtils.isEmpty(token)) {
                builder.addHeader("token", token);
                baseRequest(context, builder.build(), myOkhttpCallBack, showReqestDialog, returnall);
            } else {//没有token 但是需要token的时候  一般情况下是首次登录或者用户退出登录
                myOkhttpCallBack.onRequestFaild("用户未登录");
                ActivityHelper.getInstance().UserExit();
            }

        } else {//不需要token校验
            baseRequest(context, builder.build(), myOkhttpCallBack, showReqestDialog, returnall);

        }


    }


    //-----------------------------------请求基类---------------------------------------------------

    /**
     * 所有的请求都走这里
     *
     * @param request
     * @param myOkhttpCallBack
     */
    private void baseRequest(final Context context, final Request request, final MyOkhttpCallBack myOkhttpCallBack, String showRequestDialog, boolean retuenAll) {



        if (!NetWorkUtils.isNetWorkConnected(context)) {
            myOkhttpCallBack.onRequestFaild("网络未连接");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (showRequestDialog != null && !TextUtils.isEmpty(showRequestDialog)) {//有弹窗的时候显示

                    DialogActivity.showRequestDialog(context, showRequestDialog);

                }


                getClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        showMsg(context, "客户端请求失败", 000, call.request().url().toString(), myOkhttpCallBack);
                        DialogActivity.finishDialog();
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {


                        String url = response.request().url().toString();
                        DialogActivity.finishDialog();
                        String body = response.body().string();
                        if (retuenAll) {
                            try {
                                myOkhttpCallBack.onRequestSccess(body);
                            } catch (JSONException e) {
                                myOkhttpCallBack.onRequestFaild("json解析异常");
                                e.printStackTrace();
                            }

                        } else {
                            if (response.code() == 200 && response.isSuccessful()) {//只有这个是成功

                                MyLog.d("requestData", body);
                                try {
                                    JSONObject jsonObject = new JSONObject(body);

                                    switch (jsonObject.optString("_code")) {
                                        case "1"://成功
                                            if (showRequestDialog != null && !TextUtils.isEmpty(showRequestDialog)) {//有弹窗的时候关闭
                                                //子线程中使用handler需要加looper
                                                Looper.prepare();
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            myOkhttpCallBack.onRequestSccess(jsonObject.optString("_data"));
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }, 500);
                                                Looper.loop();
                                            } else {
                                                myOkhttpCallBack.onRequestSccess(jsonObject.optString("_data"));
                                            }
                                            break;

                                        case "-9"://token问题异常
                                            showMsg(context, "登录验证失败，请重新登录", 000, url, myOkhttpCallBack);
                                            ActivityHelper.getInstance().UserExit();
                                            break;
                                        case "-4"://参数错误
                                            showMsg(context, "参数错误", 000, url, myOkhttpCallBack);

                                            break;

                                        default:
                                            showMsg(context, jsonObject.optString("_msg"), response.code(), url, myOkhttpCallBack);
                                            break;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    showMsg(context, "解析异常", 000, url, myOkhttpCallBack);

                                }
                            } else if (response.code() == 500) {
                                showMsg(context, "请求失败", 500, url, myOkhttpCallBack);

                            } else if ((response.code() == 404)) {
                                showMsg(context, "访问异常", 404, url, myOkhttpCallBack);

                            } else if ((response.code() == 502)) {//服务正在重启...
                                showMsg(context, "请求失败", 502, url, myOkhttpCallBack);

                            } else {
                                showMsg(context, "服务异常", 999, url, myOkhttpCallBack);

                            }
                        }
                    }

                });
            }
        }).start();


    }


    /**
     * 请求回调
     */
    public interface MyOkhttpCallBack {
        void onRequestSccess(String json) throws JSONException;

        boolean onRequestFaild(String mes);//false  代表 不需要重写将里面的内容交给Muokhttp处理    ture 需要重写
    }


    private OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient();
        }


        return client;
    }

    //-----------------------------------请求基类---------------------------------------------------

    private static RequestBody createCustomRequestBody(final MediaType contentType, final File file, final ProgressListener listener) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    //sink.writeAll(source);
                    Buffer buf = new Buffer();
                    Long remaining = contentLength();
                    for (long readCount; (readCount = source.read(buf, 1024)) != -1; ) {
                        sink.write(buf, readCount);
                        listener.onProgress(contentLength(), remaining -= readCount, remaining == 0);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public interface ProgressListener {
        void onProgress(long totalBytes, long remainingBytes, boolean done);
    }

    /*
     *请求错误的情况下给用户的提示
     */
    private void showMsg(Context context, String errorMsg, int errorCode, String requestUrl, MyOkhttpCallBack callBack) {

        String msg = errorMsg + "状态码:" + errorCode + "请求地址：" + requestUrl;

        boolean showToast = callBack.onRequestFaild(msg);
        if (showToast) {   //   b  默认是false
//            if (ConfigUtils.getCurrentPath() == ConfigUtils.PATH_XS) {//线上环境
//                ActivityHelper.getInstance().showToast(activity, errorMsg + errorCode);
//
//            } else {//测试环境
//                ActivityHelper.getInstance().showToast(activity, msg);
//
//            }
            ActivityHelper.getInstance().showToast(context, msg);
        }
    }
}
