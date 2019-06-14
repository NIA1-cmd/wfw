package com.oncloudsoft.sdk.yunxin.session.extension;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 系统消息 父类
 */
public class SysMsgAttachment extends CustomAttachment {

    private String msgType;//  消息类型
    private String msgTitle;// 消息标题
    private JSONArray msgAuth;// 消息权限
    private String msgData;// 消息data


    private static String MSGTITLE = "title";
    private static String MSGDATA = "data";
    private static String MSGXXLX = "xxlx";
    private static String MSGAUTH = "nauth";


    public SysMsgAttachment(int type) {
        super(type);
    }

    @Override
    protected void parseData(JSONObject data) {


        msgTitle = data.getString(MSGTITLE);
        msgType = data.getString(MSGXXLX);

        msgAuth = data.getJSONArray(MSGAUTH);

        msgData = data.getString(MSGDATA);

    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put(MSGXXLX, msgType);
        data.put(MSGTITLE, msgTitle);
        data.put(MSGAUTH, msgAuth);
        data.put(MSGDATA, msgData);

        return data;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public JSONArray getMsgAuth() {
        return msgAuth;
    }

    public void setMsgAuth(JSONArray msgAuth) {
        this.msgAuth = msgAuth;
    }

    public String getMsgData() {
        return msgData;
    }

    public void setMsgData(String msgData) {
        this.msgData = msgData;
    }

}
