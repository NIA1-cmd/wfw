//package com.oncloudsoft.yunxin.session.extension;
//
//import com.alibaba.fastjson.JSONObject;
//
//import org.json.JSONException;
//
///**
// * 系统消息--住房或者租赁
// */
//public class SysMsgHousAttachment extends CustomAttachment {
//    private String msgid;//  消息id
//    private String msgServerId;//
//    private String title;// 消息标题
//
//
//    public static String MSGID = "xxid";
//    public static String MSGTITLE = "title";
//    public static String MSGBODY = "data";
//    public static String MSG_SERVERID = "msgServerId";
//
//    public SysMsgHousAttachment(int type) {
//        super(type);
//    }
//
//    @Override
//    protected void parseData(JSONObject data) {
//        title = data.getString(MSGTITLE);
//        msgid = data.getString(MSGID);
//        String body = data.getString(MSGBODY);
//
//        try {
//            org.json.JSONObject jsonObject = new org.json.JSONObject(body);
//
//            msgServerId = jsonObject.getString(MSG_SERVERID);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//
//    }
//
//    @Override
//    protected JSONObject packData() {
//        JSONObject data = new JSONObject();
//        data.put(MSGID, msgid);
//        data.put(MSGTITLE, title);
//
//        JSONObject body = new JSONObject();
//        data.put(MSG_SERVERID, msgServerId);
//
//        data.put(MSGBODY, body);
//        return data;
//    }
//
//    public String getMsgid() {
//        return msgid;
//    }
//
//    public void setMsgid(String msgid) {
//        this.msgid = msgid;
//    }
//
//    public String getMsgServerId() {
//        return msgServerId;
//    }
//
//    public void setMsgServerId(String msgServerId) {
//        this.msgServerId = msgServerId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//}
