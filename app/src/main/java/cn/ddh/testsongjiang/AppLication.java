package cn.ddh.testsongjiang;

import android.app.Application;

import com.oncloudsoft.sdk.SongJiangSDk;

/**
 * 文件描述：
 * 作者：黄继栋
 * 创建时间：2019/6/7
 */
public class AppLication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SongJiangSDk.songjiangInit(getApplicationContext());
    }
}
