package com.oncloudsoft.sdk.entity;

import java.util.List;

/**
 * 作者 caomingduo
 * 创建时间 2019/2/21 15:40
 * 描述 视频图片历史消息查询
 */

public class QueryImgVideoData {
    private String time;
    private List<Media> list;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Media> getList() {
        return list;
    }

    public void setList(List<Media> list) {
        this.list = list;
    }
}
