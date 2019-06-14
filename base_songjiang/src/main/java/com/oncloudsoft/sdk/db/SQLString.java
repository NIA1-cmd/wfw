package com.oncloudsoft.sdk.db;

/**
 * Created by JX on 2018/5/30.
 */

public class SQLString {

    public static final String LASTMESSAGE = "create table lastMessage(whiteId integer primary key AUTOINCREMENT,`id` char(16),`lastTime` char(16),`sendType` char(16),`lastMessage` char(16),`lastMessageType` char(16),`unreadCount` char(16),`lastName` char(16),`excludeNumber` char(16))";//创建最后一条消息表
}
