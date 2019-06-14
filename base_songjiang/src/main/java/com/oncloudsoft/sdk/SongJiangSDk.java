package com.oncloudsoft.sdk;

import android.content.Context;
import com.oncloudsoft.sdk.app.ActivityHelper;

/**
 * 文件描述：  启动松江SDk
 * 作者：黄继栋
 * 创建时间：2019/6/7
 */
public class SongJiangSDk {


    /**
     * 提供 登录方法
     *
     * @param context                 当前窗口
     * @param onSongJiangLoginListener 登录监听回调
     */
    public static void songJiangLogin(Context context, String fgbs, onSongJiangOprationListener onSongJiangLoginListener) {
        ActivityHelper.getInstance().loginSongJiang(context, fgbs, onSongJiangLoginListener);
    }

    /**
     * 提供 退出登录方法
     */
    public static void songJiangLogout(Context context, onSongJiangOprationListener onSongJiangOprationListener) {
        ActivityHelper.getInstance().logoutSongJiang(context, onSongJiangOprationListener);
    }

    /**
     * 提供 打开方法
     *
     * @param context 上下文对象
     * @param caseName 案号
     * @param cbfgbs 承办人标识
     * @param caseId 案号 案件id
     *
     */
    public static void songJiangOpenTeam(Context context, String caseName, String cbfgbs,String caseId,onSongJiangOprationListener onSongJiangOprationListener) {
        ActivityHelper.getInstance().openTeamSongJiang(context, cbfgbs,caseName,caseId ,onSongJiangOprationListener);
    }

    /**
     * 初始化 松江sdk
     *
     * @param context
     */
    public static void songjiangInit(Context context) {
        ActivityHelper.getInstance().initSongJiang(context);
    }
    /**
     * 打开松江sdk
     *
     * @param context 上下文
     */
    public static void songjiangStart(Context context) {
        ActivityHelper.getInstance().startSongJiang(context);
    }

    public interface onSongJiangOprationListener {
        void onSuccess(String msg);

        void onException(String error);

    }


    /**
     * 获取某个案件的未读数量
     * @param context 上下文对象
     * @param caseName 案号
     * @param onSongJiangOprationListener 获取监听
     */
    public static void songjiangGetUnreadCount(Context context,String caseName,onSongJiangOprationListener onSongJiangOprationListener){
        ActivityHelper.getInstance().getUnreadCountSongJiang(context,caseName,onSongJiangOprationListener);
    }

    /**
     * 注册观察者
     * @param b true注册观察者  false反注册
     */
    public static void registerObservers(boolean b){
        ActivityHelper.getInstance().registerObservers(b);
    }

}
