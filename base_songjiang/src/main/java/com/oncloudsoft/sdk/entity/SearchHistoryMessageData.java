package com.oncloudsoft.sdk.entity;

/**
 * 作者 黄继栋
 * 创建时间 2019/2/21 10:10
 * 描述  历史聊天记录查询
 */

public class SearchHistoryMessageData extends BaseEntity {
    private String body;//文本内容 消息内容，字符串类型

    private String eventType;//值为1，表示是会话类型的消息

    private String fromAccount;//消息发送者的用户账号，字符串类型

    private String fromNick;//发送方昵称，字符串类型

    private String msgTimestamp;//消息发送时间，时间戳

    private String msgType;//会话具体类型PERSON、TEAM对应的通知消息类型：TEXT、PICTURE、 AUDIO、VIDEO、LOCATION 、NOTIFICATION、 FILE、 //文件消息NETCALL_AUDIO、 //网络电话音频聊天 NETCALL_VEDIO、//网络电话视频聊天DATATUNNEL_NEW、 //新的数据通道请求通知 TIPS、 //提示类型消息CUSTOM //自定义消息

    private String attach;//附加消息，字符串类型

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getFromNick() {
        return fromNick;
    }

    public void setFromNick(String fromNick) {
        this.fromNick = fromNick;
    }

    public String getMsgTimestamp() {
        return msgTimestamp;
    }

    public void setMsgTimestamp(String msgTimestamp) {
        this.msgTimestamp = msgTimestamp;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}
