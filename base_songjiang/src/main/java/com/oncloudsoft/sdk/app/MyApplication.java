package com.oncloudsoft.sdk.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.MixPushConfig;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.NotificationAttachment;
import com.netease.nimlib.sdk.msg.constant.NotificationType;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.model.IMMessageFilter;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.db.DBContext;
import com.oncloudsoft.sdk.utils.SharedPreferencesUtils;
import com.oncloudsoft.sdk.utils.SreenUtil;
import com.oncloudsoft.sdk.yunxin.session.NimDemoLocationProvider;
import com.oncloudsoft.sdk.yunxin.session.SessionHelper;
import com.oncloudsoft.sdk.yunxin.uikit.api.NimUIKit;
import com.oncloudsoft.sdk.yunxin.uikit.business.custom.TeamExpansionInfo;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class MyApplication {
    private static Context mContext;
    public static DBContext dbContext;
    public static TeamExpansionInfo.TeamExpansionData teamExpansionData;


    public static TeamExpansionInfo.TeamExpansionData getTeamExpansionData() {
        return teamExpansionData;
    }

    public static void setTeamExpansionData(TeamExpansionInfo.TeamExpansionData teamExpansionData) {
        MyApplication.teamExpansionData = teamExpansionData;
    }

    public static Context getContext() {
        return mContext;
    }


    public static void startApp(Context context) {
        mContext = context;

        initYunXin(context);
        initSQLite(context);
        removeTempFromPref(context);

    }




    /**
     * 初始化网易云信
     */
    private static void initYunXin(Context context) {

        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
//        NimUIKit.init(context);
        NIMClient.init(context, loginInfo(), options(context));
        if (NIMUtil.isMainProcess(context)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            // 2、相关Service调用
            initUiKit(context);
        }

    }


    /**
     * 移除多选图片时的偏好设置
     */
    private static void removeTempFromPref(Context context) {
        SharedPreferences sp = context.getSharedPreferences("myApp", MODE_PRIVATE);
        sp.edit().remove("pref_temp_images").commit();
    }

    private static void initSQLite(Context context) {
        dbContext = new DBContext(context);
    }

    private static void initUiKit(Context context) {

        // 初始化
        NimUIKit.init(context);

        // 可选定制项
        // 注册定位信息提供者类（可选）,如果需要发送地理位置消息，必须提供。
        // demo中使用高德地图实现了该提供者，开发者可以根据自身需求，选用高德，百度，google等任意第三方地图和定位SDK。
        NimUIKit.setLocationProvider(new NimDemoLocationProvider());

        // 会话窗口的定制: 示例代码可详见demo源码中的SessionHelper类。
        // 1.注册自定义消息附件解析器（可选）
        // 2.注册各种扩展消息类型的显示ViewHolder（可选）
        // 3.设置会话中点击事件响应处理（一般需要）
        SessionHelper.init();

        // 通讯录列表定制：示例代码可详见demo源码中的ContactHelper类。
        // 1.定制通讯录列表中点击事响应处理（一般需要，UIKit 提供默认实现为点击进入聊天界面)
//        ContactHelper.init();

        // 在线状态定制初始化。
//        NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());
        NIMClient.getService(MsgService.class).registerIMMessageFilter(new IMMessageFilter() {
            @Override
            public boolean shouldIgnore(IMMessage message) {
                if (message.getAttachment() instanceof NotificationAttachment) {//需要过滤


                    NotificationType type = ((NotificationAttachment) message.getAttachment()).getType();
                    if (type == NotificationType.InviteMember || type == NotificationType.KickMember) {
                        return false;//显示
                    } else {
                        return true;//不显示
                    }


                } else {
                    return true;//不显示
                }

//                return false;
//                //返回 true 表示过滤（那么 SDK 将不存储此消息，上层也不会收到此消息），默认 false 即不过滤（默认存储到 db 并通知上层）
            }
        });

    }

    // 如果返回值为 null，则全部使用默认参数。
    private static SDKOptions options(Context context) {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        /*StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = SongjiangMainActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.drawable.app_logo;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;*/
//        String string = getResources().getString(R.string.yunxin_appkey);

        MixPushConfig mixPushConfig = new MixPushConfig();
        mixPushConfig.hwCertificateName = "Songjiang";

        options.mixPushConfig = mixPushConfig;
        options.appKey = getYunXinKey(context);
        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用采用默认路径作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        String sdkPath = getAppCacheDir(mContext) + "/nim"; // 可以不设置，那么将采用默认路径
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        options.thumbnailSize = SreenUtil.getWidth(mContext) / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId, SessionTypeEnum sessionType) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionType, String sessionId) {
                return null;
            }
        };
        return options;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private static LoginInfo loginInfo() {
        String account = (String) SharedPreferencesUtils.getParam(Global.YUNXIN_ACCONT, "");
        String token = (String) SharedPreferencesUtils.getParam(Global.YUNXIN_PASSWORD, "");

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    /**
     * 配置 APP 保存图片/语音/文件/log等数据的目录
     * 这里示例用SD卡的应用扩展存储目录
     */
    static String getAppCacheDir(Context context) {
        String storageRootPath = null;
        try {
            // SD卡应用扩展存储区(APP卸载后，该目录下被清除，用户也可以在设置界面中手动清除)，请根据APP对数据缓存的重要性及生命周期来决定是否采用此缓存目录.
            if (context.getExternalCacheDir() != null) {
                storageRootPath = context.getExternalCacheDir().getCanonicalPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(storageRootPath)) {
            // SD卡应用公共存储区(APP卸载后，该目录不会被清除，下载安装APP后，缓存数据依然可以被加载。SDK默认使用此目录)，该存储区域需要写权限!
            storageRootPath = Environment.getExternalStorageDirectory() + "/" + "com.oncloudsoft";
        }

        return storageRootPath;
    }


    private static String getYunXinKey(Context context) {
//
//        int key = R.string.yunxin_test_appkey;//默认是test环境
//        switch (ConfigUtils.getCurrentPath()) {
//            case ConfigUtils.PATH_CS://测试地址
//                key = R.string.yunxin_test_appkey;
//                break;
//            case ConfigUtils.PATH_XS://线上地址
//                key = R.string.yunxin_product_appkey;
//                break;
//        }
        return context.getResources().getString(R.string.yunxin_test_appkey);
    }

}
