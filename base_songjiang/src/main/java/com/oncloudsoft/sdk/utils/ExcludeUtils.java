package com.oncloudsoft.sdk.utils;

import com.alibaba.fastjson.JSONArray;
import com.oncloudsoft.sdk.app.Global;


/**
 * 作者 Mingduo Cao
 * 创建时间 2019/5/17 13:53
 * <p>
 * 描述 筛选消息类型
 */

public class ExcludeUtils {

    public static boolean getExclude(JSONArray msgAuth) {
        try {
            for (int i = 0; i < msgAuth.size(); i++) {
                String o = (String) msgAuth.get(i);
                if (o.equals(SharedPreferencesUtils.getParam(Global.YUNXIN_ACCONT, ""))) {
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }
}
