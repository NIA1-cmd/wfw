package com.oncloudsoft.sdk.event;

/**
 * 文件描述： 用于eventBus  传递参数用
 * 作者：黄继栋
 * 创建时间：2019/6/8
 */
public class SongJiangEventData {
    private int type;//   2  节点采集  3   多媒体采集  99  未读数量

    private int unReadCount;

    private int postion;

    private String teamId;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }
}
