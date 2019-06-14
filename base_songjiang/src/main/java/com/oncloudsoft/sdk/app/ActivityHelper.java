package com.oncloudsoft.sdk.app;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.oncloudsoft.sdk.SongJiangSDk;
import com.oncloudsoft.sdk.activity.DialogActivity;
import com.oncloudsoft.sdk.activity.SongjiangWelcomActivity;
import com.oncloudsoft.sdk.db.DBContext;
import com.oncloudsoft.sdk.db.DBData;
import com.oncloudsoft.sdk.entity.LoginData;
import com.oncloudsoft.sdk.entity.UserData;
import com.oncloudsoft.sdk.event.TeamEvent;
import com.oncloudsoft.sdk.okhttprequest.HttpJsonParams;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.utils.JSONParser;
import com.oncloudsoft.sdk.utils.MyLog;
import com.oncloudsoft.sdk.utils.aes.AuthHelper1;
import com.oncloudsoft.sdk.yunxin.session.SessionHelper;
import com.oncloudsoft.sdk.yunxin.uikit.api.NimUIKit;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.activity.TeamMessageActivity;
import com.oncloudsoft.sdk.yunxin.uikit.common.badger.Badger;
import com.oncloudsoft.sdk.yunxin.uikit.impl.customization.DefaultRecentCustomization;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityHelper {

    private Toast toast;
    private TextView tvMessage;
    private DBContext dbContext = MyApplication.dbContext;
    public ActivityHelper() {
    }

    private List<Activity> activityList = new ArrayList<>();


    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public void setEditextCursorColor(EditText editext, int drawable) {
        try {
            java.lang.reflect.Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(editext, drawable);
        } catch (Exception e) {

        }

    }

    private static class ActivityHolder {
        private static final ActivityHelper INSTANCE = new ActivityHelper();
    }

    public static ActivityHelper getInstance() {
        return ActivityHolder.INSTANCE;
    }

    public List<Activity> getActivitys() {
        return activityList;
    }

    /**
     * 关闭所有的窗口
     */
    public void finishAll() {
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i);
            activity.finish();
        }

    }


    public void showToast(final Context context, final String mes) {
        Message message = new Message();
        message.what = handler_toast;
        Bundle bundle = new Bundle();
        bundle.putString("toastmsg", mes);
        message.setData(bundle);
        handler.sendMessage(message);


    }


    public void getUiThread(Activity activity, Runnable runnable) {
        if (activity != null) {
            activity.runOnUiThread(runnable);
        }
    }


    public void setStateWhiteTextBlack(Activity activity, int color) {

        setStatusBarColor(activity, color);
        setStatusTextColor(activity);

    }

    /**
     * 设置状态栏未白色背景
     *
     * @param activity
     * @param colorId
     */
    private static void setStatusBarColor(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        }
//        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
//
//            transparencyBar(activity);
//            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(colorId);
//        }
    }

    /**
     * 设置状态栏中字体颜色为深色
     */
    private static void setStatusTextColor(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    public void registerObservers(boolean b) {
        registerObserver(b);
    }

    /**
     *注册/注销消息观察者
     *
    /
     /**
     * ********************** 收消息，处理状态变化 ************************
     */
    private void registerObserver(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeRecentContact(messageObserver, register);
        service.observeReceiveMessage(incomingMessageObserver, register);
    }

    private ContentValues values;
    private String contactId;
    private String lastMessage, lastTime, lastName;
    private SessionTypeEnum sessionType;
    Observer<List<IMMessage>> incomingMessageObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> messages) {
            // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。\
            int unreadCount = messages.size();//消息总数
            int excludeNumber = 0;//消息中被剔除的消息数量
            IMMessage imMessage = null;
            for (int j = 0; j < messages.size(); j++) {
                imMessage = messages.get(j);
                contactId = imMessage.getSessionId();
                lastTime = imMessage.getTime() + "";
                lastName = imMessage.getFromNick();
                if (lastName.equals("")) {
                    lastName = "系统";
                }
                sessionType = imMessage.getSessionType();
                lastMessage = new DefaultRecentCustomization().getTitle(imMessage);
            }
            int unCountNumber = 0;
            List<DBData> dbData = MyApplication.dbContext.whereQuery("*", "lastMessage", "id", contactId);
            if (dbData.size() == 0) {//数据库不存在这条数据  重新插入  存在则更新
                try {
                    JSONArray jsonArray1 = JSONParser.parserRecentContact(imMessage, unreadCount, excludeNumber);
                    unCountNumber = unreadCount - excludeNumber;
                    MyApplication.dbContext.insertLastMessage("lastMessage", jsonArray1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                int unCount = dbData.get(0).getUnreadCount();
                unCountNumber = unreadCount + unCount;
                updataSql(unreadCount + unCount, 0);
            }
            updataList(unCountNumber);
        }
    };

        /*
        * 有新消息的时候会执行此方法
        * 将新消息存sqlite  展示未读消息
        * */
    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> recentContacts) {
            lastName = "";
            RecentContact recentContact = recentContacts.get(0);
            int unreadCount = recentContact.getUnreadCount();
            contactId = recentContacts.get(0).getContactId();
            lastMessage = new DefaultRecentCustomization().getDefaultDigest(recentContacts.get(0));
            lastTime = recentContacts.get(0).getTime() + "";
            lastName = recentContacts.get(0).getFromNick();
            if (lastName.equals("")) {
                lastName = "系统";
            }
            sessionType = recentContacts.get(0).getSessionType();

            List<DBData> dbData = MyApplication.dbContext.whereQuery("*", "lastMessage","id", recentContacts.get(0).getContactId());
            if (dbData.size() == 0) {
                try {
                    JSONArray jsonArray1 = JSONParser.parserRecentContact(recentContact);
                    MyApplication.dbContext.insertLastMessage("lastMessage", jsonArray1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                updataSql(unreadCount, 0);
            }
            if (sessionType == SessionTypeEnum.Team) {
                //upDate();
                EventBus.getDefault().postSticky("");
            } else if (sessionType == SessionTypeEnum.P2P) {
                EventBus.getDefault().postSticky(new TeamEvent(contactId, "" + unreadCount));
            }
            updataList(unreadCount);
        }
    };

    private void updataSql(int unreadCount, int excludeNumber) {
        values = new ContentValues();
        values.put("unreadCount", unreadCount + "");
        values.put("lastTime", lastTime);
        values.put("lastMessage", lastMessage);
        values.put("lastName", lastName);
        values.put("excludeNumber", excludeNumber);
        MyApplication.dbContext.update(values, "lastMessage", contactId);
    }
    private void updataList(int unreadCount) {
        if (sessionType == SessionTypeEnum.Team) {
            //upDate();
            EventBus.getDefault().postSticky("");
        }
        else if (sessionType == SessionTypeEnum.P2P) {
            EventBus.getDefault().postSticky(new TeamEvent(contactId, "" + unreadCount));
        }
    }
    /**
     * 登录方法
     *
     * @param context
     * @param onSongJiangLoginListener
     */
    public void loginSongJiang(Context context, String fgbs, SongJiangSDk.onSongJiangOprationListener onSongJiangLoginListener) {
        long currentTimeMillis = System.currentTimeMillis();

        if (!isInit) {
            onSongJiangLoginListener.onException("没有初始化");
            return;
        }
        final UserData userData = new UserData();

        String build1 = HttpJsonParams.HttpParams().add("f", AuthHelper1.encode(fgbs, currentTimeMillis)).add("t", currentTimeMillis + "").build();
        MyOkhttpClient.getOkhttpInstance().sentPost(context, Global.URL_LOGIN, build1,
                new MyOkhttpClient.MyOkhttpCallBack() {
                    @Override
                    public void onRequestSccess(String json) {
                        MyLog.d("logingActivity-->onRequestSccess:" + json);

                        final LoginData loginData = JSON.parseObject(json, LoginData.class);
                        NimUIKit.login(new LoginInfo(loginData.getYxAccid(), loginData.getYxToken()), new RequestCallback<LoginInfo>() {
                            @Override
                            public void onSuccess(LoginInfo param) {
                                MyLog.d("logingActivity-->onSuccess-->Account:" + param.getAccount() + "AppKey:" + param.getAppKey() + "Token:" + param.getToken());
                                userData.setToken(loginData.getToken());
                                userData.setFgbs(loginData.getFgbs());
                                userData.setFgmc(loginData.getFgmc());
                                userData.setYxAccid(loginData.getYxAccid());
                                userData.setYxToken(loginData.getYxToken());
                                userData.setFgzw(loginData.getFgzw());
                                userData.setFyid(loginData.getFyid());
                                userData.setFgmc(loginData.getFgmc());
                                userData.setFymc(loginData.getFymc());
                                UserInfo.setUserData(userData);
                                registerObservers(true);//注册观察者
                                onSongJiangLoginListener.onSuccess("登录成功");
                            }

                            @Override
                            public void onFailed(int code) {
                                onSongJiangLoginListener.onException("云信异常,code:" + code);
//
                            }

                            @Override
                            public void onException(Throwable exception) {
                                onSongJiangLoginListener.onException("登录异常，" + exception.getMessage());

                            }
                        });

                    }

                    @Override
                    public boolean onRequestFaild(String mes) {
                        MyLog.d("logingActivity-->onRequestFaild:" + mes);

                        DialogActivity.finishDialog();
                        onSongJiangLoginListener.onException("登录失败," + mes);


                        return false;
                        //  onLoginDone();
                    }
                }, false);
    }

    private final int handler_exit = 445;//退出登录
    private final int handler_toast = 447;//toast


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case handler_exit://告知退出登录
                    registerObservers(false);//反注册观察者
                    NIMClient.getService(AuthService.class).logout();
                    NimUIKit.logout();//退出网易云信
                    Badger.setBadger(0);
                    UserInfo.cleanUserData();//清除本地缓存数据
                    ActivityHelper.getInstance().finishAll();//关闭所有页面
                    break;
                case handler_toast://弹出toast
                    Bundle data = msg.getData();

                    Toast.makeText(MyApplication.getContext(), data.getString("toastmsg"), Toast.LENGTH_SHORT).show();


                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 用户退出登录
     */
    public void UserExit() {
        Message message = new Message();
        message.what = handler_exit;
        handler.sendMessage(message);


    }

    /**
     * @param context
     * @param onSongJiangOprationListener
     */
    public void logoutSongJiang(Context context, SongJiangSDk.onSongJiangOprationListener onSongJiangOprationListener) {
        MyOkhttpClient.getOkhttpInstance().sentGet(context, Global.URL_LOGOUT, HttpParams.HttpParams().build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) throws JSONException {
                UserExit();
                onSongJiangOprationListener.onSuccess("退出成功" + json);

            }

            @Override
            public boolean onRequestFaild(String mes) {
                onSongJiangOprationListener.onException("退出失败" + mes);

                return false;
            }
        }, true);

    }

    /**
     * 打开一个案件
     *
     * @param caseName                    案件名称
     * @param caseId
     * @param onSongJiangOprationListener
     */
    public void openTeamSongJiang(Context context, String cbfgbs, String caseName, String caseId, SongJiangSDk.onSongJiangOprationListener onSongJiangOprationListener) {
        if (!isInit) {
            Toast.makeText(context, "没有初始化", Toast.LENGTH_SHORT).show();
            return;
        }
        MyOkhttpClient.getOkhttpInstance().sentPost(context, Global.URL_CREATETEAM, HttpJsonParams.HttpParams().add("ajid", caseId).add("ah", caseName).add("cbrbs", cbfgbs).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) throws JSONException {
                org.json.JSONObject jsonObject = new org.json.JSONObject(json);
                String tid = jsonObject.optString("tid");
                if (tid == null || tid.isEmpty()) {
                    onSongJiangOprationListener.onException("打开失败，tid未获取");
                } else {
                    SessionHelper.startTeamSession(context, tid);
                    TeamMessageActivity.setOpenListener(onSongJiangOprationListener);
                }

            }

            @Override
            public boolean onRequestFaild(String mes) {
                onSongJiangOprationListener.onException("打开失败" + mes);
                return false;
            }
        }, true);

    }

    public void initSongJiang(Context context) {
        isInit = true;
        MyApplication.startApp(context);
    }

    private boolean isInit = false;

    public void startSongJiang(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SongjiangWelcomActivity.class);
        context.startActivity(intent);
    }

    public void getUnreadCountSongJiang(Context context, String caseName, SongJiangSDk.onSongJiangOprationListener listener) {
        if (caseName == null || android.text.TextUtils.isEmpty(caseName)) {
            listener.onException("案号为空");
        } else {
            MyOkhttpClient.getOkhttpInstance().sentGet(context, Global.URL_GETUNREADCOUNT, HttpParams.HttpParams().add("ah",caseName).build(), new MyOkhttpClient.MyOkhttpCallBack() {
                @Override
                public void onRequestSccess(String json) throws JSONException {

                    JSONObject jsonObject = new JSONObject(json);
                    String tid = jsonObject.optString("tid");
                    if (tid == null || android.text.TextUtils.isEmpty(tid)) {
                        listener.onException("获取id失败");
                    } else {
                        int i = MyApplication.dbContext.whereQueryCount("*", "lastmessage", "id", tid);
                        listener.onSuccess(i + "");
                    }
                }

                @Override
                public boolean onRequestFaild(String mes) {
                    listener.onException(mes);
                    return false;
                }
            }, true);

        }

    }
}
