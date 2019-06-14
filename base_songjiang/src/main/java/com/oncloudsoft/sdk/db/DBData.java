package com.oncloudsoft.sdk.db;

/**
 * 作者 Mingduo Cao
 * 创建时间 2019/5/5 17:10
 * <p>
 * 描述
 */

public class DBData {
    String whiteId;
    String id;
    String lastTime;
    String sendType;
    String lastMessage;
    String lastMessageType;
    int unreadCount;
    String lastName;
    int excludeNumber;

    public int getExcludeNumber() {
        return excludeNumber;
    }

    public void setExcludeNumber(int excludeNumber) {
        this.excludeNumber = excludeNumber;
    }

    public String getWhiteId() {
        return whiteId;
    }

    public void setWhiteId(String whiteId) {
        this.whiteId = whiteId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageType() {
        return lastMessageType;
    }

    public void setLastMessageType(String lastMessageType) {
        this.lastMessageType = lastMessageType;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
